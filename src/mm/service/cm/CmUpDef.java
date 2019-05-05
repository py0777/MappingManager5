package mm.service.cm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import mm.common.DaoHandler;
import mm.common.IContextHeader;
import mm.repository.AbstractRepository;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.util.StringUtils;

import org.apache.log4j.Logger;



public class CmUpDef extends AbstractRepository {

	static Logger logger = Logger.getLogger(CmUpDef.class);
	private final String namespace = "mm.repository.mapper.UpDefMapper";


	public IDataSet inquireDef(IContextHeader ich, IDataSet requestData) {
		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		IDataSet dsMapTbl = null;
		IRecordSet rsMapTbl = null;
		IDataSet dsTbl = null;
		IRecordSet rsTbl = null;
		
		IRecordSet rsTblRtn = new RecordSet("RETURN_TBL");	
		StringBuffer mapIdSB = new StringBuffer();
		String[] inputMapIdTbl;
		String rtnMsg  = "";
		
		String today = "";
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss초 E(a)");

		try {
			Date d = gc.getTime();
			
			/*************************************************************
			 * 1.  테이블 조회
			 *************************************************************/
			today = sdformat.format(d);
			/*입력값 null 체크*/
			if(requestData.getField("TABLE_NAME").isEmpty() || "NULL".equals(requestData.getField("TABLE_NAME"))){
				rtnMsg = "테이블을 입력하세요";
			    responseData.putField("rtnMsg", today + " " +rtnMsg);
			    return responseData;
			}
			
			
			/*MAP_ID 값 설정*/
			inputMapIdTbl = requestData.getField("MAP_ID").split(",");
			
			if(StringUtils.isEmpty(inputMapIdTbl[0]) || "null".equals(inputMapIdTbl[0]) || "NULL".equals(inputMapIdTbl[0])){
				requestData.putField("MAP_ID", "'%'");
			}else{
				for(int i = 0 ; i <inputMapIdTbl.length; i++){
					String tobeMapId  = inputMapIdTbl[i]; /*ASIS*/
					
					mapIdSB.append("'");
					mapIdSB.append(tobeMapId);
					mapIdSB.append("'");
					mapIdSB.append(",");
				}
				
				if(mapIdSB.length() > 0){
					requestData.putField("MAP_ID", mapIdSB.delete(mapIdSB.length() - 1,  mapIdSB.length()).toString());
				}else{
					requestData.putField("MAP_ID", "'%'");					
				}
			}
			logger.debug(mapIdSB);
			dsMapTbl  = dh.selectSql(requestData, namespace+"."+"S002");
			rsMapTbl  =  dsMapTbl.getRecordSet("ResultSet");
			
			logger.debug(rsMapTbl);
			
			responseData.putField("rsMapCnt", rsMapTbl.getRecordCount());
			
			/*************************************************************
			 * 1.  MAP_ID 별 컬럼맵핑정의서 조회
			 *************************************************************/
			logger.debug(requestData.getField("MAP_ID"));
			if("'%'".equals(requestData.getField("MAP_ID"))){
				logger.debug(rsMapTbl);
				for(int i = 0 ; i <rsMapTbl.getRecordCount(); i++){
					String tobeMapId  = rsMapTbl.getRecord(i).get("MAP_ID"); /*ASIS*/
					
					mapIdSB.append("'");
					mapIdSB.append(tobeMapId);
					mapIdSB.append("'");
					mapIdSB.append(",");
				}
				
				if(mapIdSB.length() > 0){
					requestData.putField("MAP_ID", mapIdSB.delete(mapIdSB.length() - 1,  mapIdSB.length()).toString());
				}
			}
			
			dsTbl  = dh.selectSql(requestData, namespace+"."+"S001");
			rsTbl  =  dsTbl.getRecordSet("ResultSet");
			
			logger.debug(rsTbl);
			
			responseData.putField("rsCnt", rsTbl.getRecordCount());
			
			if(rsTbl.getRecordCount() ==0){
				rtnMsg = "조회내역이 없습니다.";
			    responseData.putField("rtnMsg", today + " " +rtnMsg);
			    return responseData;
			}
			/*리턴테이블 헤더 값 설정*/
			for(int i = 0 ; i <rsTbl.getHeaderCount(); i++){
				rsTblRtn.addHeader(i, rsTbl.getHeader(i));
			}
			
			for(int i = 0 ; i < rsTbl.getRecordCount(); i++){
				/*테이블 데이터 설정*/
				rsTblRtn.addRecord(rsTbl.getRecord(i));
			}
			
			responseData.putField("rsMapCnt", rsMapTbl.getRecordCount());
			responseData.putRecordSet("rsMapRtn", rsMapTbl);
			responseData.putField("rsTblCnt", rsTblRtn.getRecordCount());
			responseData.putRecordSet("rsTblRtn", rsTblRtn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseData;
		
	}
	
//	/*매핑정의서 변경 이력을 관리함*/
//	public void modifyDefCrr(IDataSet requestData) {
//		
//		/*************************************************************
//		 * Declare Var
//		 *************************************************************/
//		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
//		
//		int    rsCnt = 0;  /*결과 건수*/
//		
//		try {
//			
//			
//			/********************************************************************
//			 *  자료 등록/수정/삭제처리
//			 ********************************************************************/
//			IDataSet dsI000In = new DataSet();
//			
//			if( requestData.getRecordSet("deletedRows") != null){
//				for(int i = 0; i <  requestData.getRecordSet("deletedRows").getRecordCount() ; i++){
//					
//					/*delete*/
//					
//					dsI000In.putField("ID", requestData.getRecordSet("deletedRows").get(i, "ID"));           
//					 
//					rsCnt =  dh.deleteSql(dsI000In, namespace+"."+"D001");
//					
//					if(rsCnt <=  0) {
//						throw new Exception( namespace+"."+"I001"+" 처리 건수 없음.");
//					}
//					
//				}
//			}
//			
//			if( requestData.getRecordSet("updatedRows") != null){
//				for(int i = 0; i <  requestData.getRecordSet("updatedRows").getRecordCount() ; i++){
//					if( StringUtils.isEmpty(String.valueOf(requestData.getRecordSet("updatedRows").get(i, "ID")))){
//						continue;
//					}
//					/*update*/
//										
//					dsI000In.putField("ID"                , requestData.getRecordSet("updateRows").get(i, "ID"               )); 
//					dsI000In.putField("MAP_SORT"          , requestData.getRecordSet("updateRows").get(i, "MAP_SORT"         )); 
//					dsI000In.putField("MAP_ID"            , requestData.getRecordSet("updateRows").get(i, "MAP_ID"           )); 
//					dsI000In.putField("T_SYSTEM_NAME"     , requestData.getRecordSet("updateRows").get(i, "T_SYSTEM_NAME"    )); 
//					dsI000In.putField("T_OWNER"           , requestData.getRecordSet("updateRows").get(i, "T_OWNER"          )); 
//					dsI000In.putField("T_ENG_TABLE_NAME"  , requestData.getRecordSet("updateRows").get(i, "T_ENG_TABLE_NAME" )); 
//					dsI000In.putField("T_KOR_TABLE_NAME"  , requestData.getRecordSet("updateRows").get(i, "T_KOR_TABLE_NAME" )); 
//					dsI000In.putField("T_ENG_COLUMN_NAME" , requestData.getRecordSet("updateRows").get(i, "T_ENG_COLUMN_NAME")); 
//					dsI000In.putField("T_KOR_COLUMN_NAME" , requestData.getRecordSet("updateRows").get(i, "T_KOR_COLUMN_NAME")); 
//					dsI000In.putField("T_DATA_TYPE"       , requestData.getRecordSet("updateRows").get(i, "T_DATA_TYPE"      )); 
//					dsI000In.putField("T_LENGTH1"         , requestData.getRecordSet("updateRows").get(i, "T_LENGTH1"        )); 
//					dsI000In.putField("T_LENGTH2"         , requestData.getRecordSet("updateRows").get(i, "T_LENGTH2"        )); 
//					dsI000In.putField("T_PK"              , requestData.getRecordSet("updateRows").get(i, "T_PK"             )); 
//					dsI000In.putField("A_SYSTEM_NAME"     , requestData.getRecordSet("updateRows").get(i, "A_SYSTEM_NAME"    )); 
//					dsI000In.putField("A_OWNER"           , requestData.getRecordSet("updateRows").get(i, "A_OWNER"          )); 
//					dsI000In.putField("A_ENG_TABLE_NAME"  , requestData.getRecordSet("updateRows").get(i, "A_ENG_TABLE_NAME" )); 
//					dsI000In.putField("A_KOR_TABLE_NAME"  , requestData.getRecordSet("updateRows").get(i, "A_KOR_TABLE_NAME" )); 
//					dsI000In.putField("A_ENG_COLUMN_NAME" , requestData.getRecordSet("updateRows").get(i, "A_ENG_COLUMN_NAME")); 
//					dsI000In.putField("A_KOR_COLUMN_NAME" , requestData.getRecordSet("updateRows").get(i, "A_KOR_COLUMN_NAME")); 
//					dsI000In.putField("A_DATA_TYPE"       , requestData.getRecordSet("updateRows").get(i, "A_DATA_TYPE"      )); 
//					dsI000In.putField("A_LENGTH1"         , requestData.getRecordSet("updateRows").get(i, "A_LENGTH1"        )); 
//					dsI000In.putField("A_LENGTH2"         , requestData.getRecordSet("updateRows").get(i, "A_LENGTH2"        )); 
//					dsI000In.putField("A_PK"              , requestData.getRecordSet("updateRows").get(i, "A_PK"             )); 
//					dsI000In.putField("MOVE_DEFAULT"      , requestData.getRecordSet("updateRows").get(i, "MOVE_DEFAULT"     )); 
//					dsI000In.putField("MOVE_YN"           , requestData.getRecordSet("updateRows").get(i, "MOVE_YN"          )); 
//					dsI000In.putField("MOVE_RULE"         , requestData.getRecordSet("updateRows").get(i, "MOVE_RULE"        )); 
//					dsI000In.putField("MOVE_SQL"          , requestData.getRecordSet("updateRows").get(i, "MOVE_SQL"         )); 
//					dsI000In.putField("ALT_EMP_NO"        , requestData.getRecordSet("updateRows").get(i, "ALT_EMP_NO"       )); 
//					dsI000In.putField("PREE_CDTN"         , requestData.getRecordSet("updateRows").get(i, "PREE_CDTN"        )); 
//					dsI000In.putField("ALT_DT"            , requestData.getRecordSet("updateRows").get(i, "ALT_DT"           )); 
//					dsI000In.putField("JOB_OWNER"         , requestData.getRecordSet("updateRows").get(i, "JOB_OWNER"        )); 
//					dsI000In.putField("CLIENT_OWNER"      , requestData.getRecordSet("updateRows").get(i, "CLIENT_OWNER"     )); 
//					dsI000In.putField("MOVE_OWNER"        , requestData.getRecordSet("updateRows").get(i, "MOVE_OWNER"       )); 
//					dsI000In.putField("MAP_STAT"          , "2"); /*신청중*/
//					dsI000In.putField("MAP_IUD_FLGCD"     , "U"); /*갱신*/
//
//					
//					rsCnt =  dh.updateSql(dsI000In, namespace+"."+"U001");
//					
//					if(rsCnt <=  0) {
//						throw new Exception( namespace+"."+"I001"+" 처리 건수 없음.");
//					}
//					
//				}
//			}
//			
//			if( requestData.getRecordSet("createdRows") != null){
//				for(int i = 0; i <  requestData.getRecordSet("createdRows").getRecordCount() ; i++){
//					
//					/*insert*/
//					dsI000In.putField("ID"                , requestData.getRecordSet("createdRows").get(i, "ID"               )); 
//					dsI000In.putField("MAP_SORT"          , requestData.getRecordSet("createdRows").get(i, "MAP_SORT"         )); 
//					dsI000In.putField("MAP_ID"            , requestData.getRecordSet("createdRows").get(i, "MAP_ID"           )); 
//					dsI000In.putField("T_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "T_SYSTEM_NAME"    )); 
//					dsI000In.putField("T_OWNER"           , requestData.getRecordSet("createdRows").get(i, "T_OWNER"          )); 
//					dsI000In.putField("T_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_ENG_TABLE_NAME" )); 
//					dsI000In.putField("T_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_KOR_TABLE_NAME" )); 
//					dsI000In.putField("T_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_ENG_COLUMN_NAME")); 
//					dsI000In.putField("T_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_KOR_COLUMN_NAME")); 
//					dsI000In.putField("T_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "T_DATA_TYPE"      )); 
//					dsI000In.putField("T_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH1"        )); 
//					dsI000In.putField("T_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH2"        )); 
//					dsI000In.putField("T_PK"              , requestData.getRecordSet("createdRows").get(i, "T_PK"             )); 
//					dsI000In.putField("A_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "A_SYSTEM_NAME"    )); 
//					dsI000In.putField("A_OWNER"           , requestData.getRecordSet("createdRows").get(i, "A_OWNER"          )); 
//					dsI000In.putField("A_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_ENG_TABLE_NAME" )); 
//					dsI000In.putField("A_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_KOR_TABLE_NAME" )); 
//					dsI000In.putField("A_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_ENG_COLUMN_NAME")); 
//					dsI000In.putField("A_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_KOR_COLUMN_NAME")); 
//					dsI000In.putField("A_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "A_DATA_TYPE"      )); 
//					dsI000In.putField("A_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH1"        )); 
//					dsI000In.putField("A_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH2"        )); 
//					dsI000In.putField("A_PK"              , requestData.getRecordSet("createdRows").get(i, "A_PK"             )); 
//					dsI000In.putField("MOVE_DEFAULT"      , requestData.getRecordSet("createdRows").get(i, "MOVE_DEFAULT"     )); 
//					dsI000In.putField("MOVE_YN"           , requestData.getRecordSet("createdRows").get(i, "MOVE_YN"          )); 
//					dsI000In.putField("MOVE_RULE"         , requestData.getRecordSet("createdRows").get(i, "MOVE_RULE"        )); 
//					dsI000In.putField("MOVE_SQL"          , requestData.getRecordSet("createdRows").get(i, "MOVE_SQL"         )); 
//					dsI000In.putField("ALT_EMP_NO"        , requestData.getRecordSet("createdRows").get(i, "ALT_EMP_NO"       )); 
//					dsI000In.putField("PREE_CDTN"         , requestData.getRecordSet("createdRows").get(i, "PREE_CDTN"        )); 
//					dsI000In.putField("ALT_DT"            , requestData.getRecordSet("createdRows").get(i, "ALT_DT"           )); 
//					dsI000In.putField("JOB_OWNER"         , requestData.getRecordSet("createdRows").get(i, "JOB_OWNER"        )); 
//					dsI000In.putField("CLIENT_OWNER"      , requestData.getRecordSet("createdRows").get(i, "CLIENT_OWNER"     )); 
//					dsI000In.putField("MOVE_OWNER"        , requestData.getRecordSet("createdRows").get(i, "MOVE_OWNER"       )); 
//					dsI000In.putField("MAP_STAT"          , "2"); /*신청중*/
//					dsI000In.putField("MAP_IUD_FLGCD"     , "I"); /*등록*/
//					
//					rsCnt =  dh.insertSql(dsI000In, namespace+"."+"I002");
//					
//					if(rsCnt <=  0) {
//						throw new Exception( namespace+"."+"I001"+" 처리 건수 없음.");
//					}
//					
//				}
//			}			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	
	
	public IDataSet modifyDef(IDataSet requestData) throws Exception{
		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		IDataSet dsTbl = null;
		IRecordSet rsTbl = null;
		
		String rtnMsg  = "";
		
		String today = "";
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss초 E(a)");
		int    rsCnt = 0;  /*결과 건수*/
		
		try {
			Date d = gc.getTime();
			/*************************************************************
			 * 1.  입력값 확인
			 *************************************************************/
			today = sdformat.format(d);
			
			int iCnt = 0;
			int uCnt = 0;
			int dCnt = 0;
			int chgCnt = 0;
			/********************************************************************
			 *  자료 등록/수정/삭제처리
			 ********************************************************************/
			IDataSet dsI000In = new DataSet();
			IDataSet dsSel = null;
			/*삭제*/
			if( requestData.getRecordSet("deletedRows") != null){
				
				for(int i = 0; i <  requestData.getRecordSet("deletedRows").getRecordCount() ; i++){
					
					/*deleted*/
					dsI000In.putField("ID"                , requestData.getRecordSet("deletedRows").get(i, "ID"               )); 
					dsI000In.putField("MAP_STAT"          , "2"); /*신청중*/
					dsI000In.putField("MAP_IUD_FLGCD"     , "D"); /*삭제*/
				
					
					rsCnt =  dh.updateSql(dsI000In, namespace+"."+"U002");
					
					if(rsCnt <=  0) {
						throw new Exception( namespace+"."+"U002"+" 처리 건수 없음.");
					}
					
					dsI000In.putField("BF_MAP_SORT"          , requestData.getRecordSet("deletedRows").get(i, "MAP_SORT"         )); 
					dsI000In.putField("BF_MAP_ID"            , requestData.getRecordSet("deletedRows").get(i, "MAP_ID"           )); 
					dsI000In.putField("BF_T_SYSTEM_NAME"     , requestData.getRecordSet("deletedRows").get(i, "T_SYSTEM_NAME"    )); 
					dsI000In.putField("BF_T_OWNER"           , requestData.getRecordSet("deletedRows").get(i, "T_OWNER"          )); 
					dsI000In.putField("BF_T_ENG_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "T_ENG_TABLE_NAME" )); 
					dsI000In.putField("BF_T_KOR_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "T_KOR_TABLE_NAME" )); 
					dsI000In.putField("BF_T_ENG_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "T_ENG_COLUMN_NAME")); 
					dsI000In.putField("BF_T_KOR_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "T_KOR_COLUMN_NAME")); 
					dsI000In.putField("BF_T_DATA_TYPE"       , requestData.getRecordSet("deletedRows").get(i, "T_DATA_TYPE"      )); 
					dsI000In.putField("BF_T_LENGTH1"         , requestData.getRecordSet("deletedRows").get(i, "T_LENGTH1"        )); 
					dsI000In.putField("BF_T_LENGTH2"         , requestData.getRecordSet("deletedRows").get(i, "T_LENGTH2"        )); 
					dsI000In.putField("BF_T_PK"              , requestData.getRecordSet("deletedRows").get(i, "T_PK"             )); 
					dsI000In.putField("BF_A_SYSTEM_NAME"     , requestData.getRecordSet("deletedRows").get(i, "A_SYSTEM_NAME"    )); 
					dsI000In.putField("BF_A_OWNER"           , requestData.getRecordSet("deletedRows").get(i, "A_OWNER"          )); 
					dsI000In.putField("BF_A_ENG_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "A_ENG_TABLE_NAME" )); 
					dsI000In.putField("BF_A_KOR_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "A_KOR_TABLE_NAME" )); 
					dsI000In.putField("BF_A_ENG_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "A_ENG_COLUMN_NAME")); 
					dsI000In.putField("BF_A_KOR_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "A_KOR_COLUMN_NAME")); 
					dsI000In.putField("BF_A_DATA_TYPE"       , requestData.getRecordSet("deletedRows").get(i, "A_DATA_TYPE"      )); 
					dsI000In.putField("BF_A_LENGTH1"         , requestData.getRecordSet("deletedRows").get(i, "A_LENGTH1"        )); 
					dsI000In.putField("BF_A_LENGTH2"         , requestData.getRecordSet("deletedRows").get(i, "A_LENGTH2"        )); 
					dsI000In.putField("BF_A_PK"              , requestData.getRecordSet("deletedRows").get(i, "A_PK"             )); 
					dsI000In.putField("BF_MOVE_DEFAULT"      , requestData.getRecordSet("deletedRows").get(i, "MOVE_DEFAULT"     )); 
					dsI000In.putField("BF_MOVE_YN"           , requestData.getRecordSet("deletedRows").get(i, "MOVE_YN"          )); 
					dsI000In.putField("BF_MOVE_RULE"         , requestData.getRecordSet("deletedRows").get(i, "MOVE_RULE"        )); 
					dsI000In.putField("BF_MOVE_SQL"          , requestData.getRecordSet("deletedRows").get(i, "MOVE_SQL"         )); 
					dsI000In.putField("BF_ALT_EMP_NO"        , requestData.getRecordSet("deletedRows").get(i, "ALT_EMP_NO"       )); 
					dsI000In.putField("BF_PREE_CDTN"         , requestData.getRecordSet("deletedRows").get(i, "PREE_CDTN"        )); 
					dsI000In.putField("BF_ALT_DT"            , requestData.getRecordSet("deletedRows").get(i, "ALT_DT"           )); 
					dsI000In.putField("BF_JOB_OWNER"         , requestData.getRecordSet("deletedRows").get(i, "JOB_OWNER"        )); 
					dsI000In.putField("BF_CLIENT_OWNER"      , requestData.getRecordSet("deletedRows").get(i, "CLIENT_OWNER"     )); 
					dsI000In.putField("BF_MOVE_OWNER"        , requestData.getRecordSet("deletedRows").get(i, "MOVE_OWNER"       )); 
					
					dsI000In.putField("AF_MAP_SORT"          , requestData.getRecordSet("deletedRows").get(i, "MAP_SORT"         )); 
					//dsI000In.putField("AF_MAP_ID"            , requestData.getRecordSet("deletedRows").get(i, "MAP_ID"           )); 
					//dsI000In.putField("AF_T_SYSTEM_NAME"     , requestData.getRecordSet("deletedRows").get(i, "T_SYSTEM_NAME"    )); 
					//dsI000In.putField("AF_T_OWNER"           , requestData.getRecordSet("deletedRows").get(i, "T_OWNER"          )); 
					//dsI000In.putField("AF_T_ENG_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "T_ENG_TABLE_NAME" )); 
					//dsI000In.putField("AF_T_KOR_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "T_KOR_TABLE_NAME" )); 
					//dsI000In.putField("AF_T_ENG_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "T_ENG_COLUMN_NAME")); 
					//dsI000In.putField("AF_T_KOR_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "T_KOR_COLUMN_NAME")); 
					//dsI000In.putField("AF_T_DATA_TYPE"       , requestData.getRecordSet("deletedRows").get(i, "T_DATA_TYPE"      )); 
					//dsI000In.putField("AF_T_LENGTH1"         , requestData.getRecordSet("deletedRows").get(i, "T_LENGTH1"        )); 
					//dsI000In.putField("AF_T_LENGTH2"         , requestData.getRecordSet("deletedRows").get(i, "T_LENGTH2"        )); 
					//dsI000In.putField("AF_T_PK"              , requestData.getRecordSet("deletedRows").get(i, "T_PK"             )); 
					//dsI000In.putField("AF_A_SYSTEM_NAME"     , requestData.getRecordSet("deletedRows").get(i, "A_SYSTEM_NAME"    )); 
					//dsI000In.putField("AF_A_OWNER"           , requestData.getRecordSet("deletedRows").get(i, "A_OWNER"          )); 
					//dsI000In.putField("AF_A_ENG_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "A_ENG_TABLE_NAME" )); 
					//dsI000In.putField("AF_A_KOR_TABLE_NAME"  , requestData.getRecordSet("deletedRows").get(i, "A_KOR_TABLE_NAME" )); 
					//dsI000In.putField("AF_A_ENG_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "A_ENG_COLUMN_NAME")); 
					//dsI000In.putField("AF_A_KOR_COLUMN_NAME" , requestData.getRecordSet("deletedRows").get(i, "A_KOR_COLUMN_NAME")); 
					//dsI000In.putField("AF_A_DATA_TYPE"       , requestData.getRecordSet("deletedRows").get(i, "A_DATA_TYPE"      )); 
					//dsI000In.putField("AF_A_LENGTH1"         , requestData.getRecordSet("deletedRows").get(i, "A_LENGTH1"        )); 
					//dsI000In.putField("AF_A_LENGTH2"         , requestData.getRecordSet("deletedRows").get(i, "A_LENGTH2"        )); 
					//dsI000In.putField("AF_A_PK"              , requestData.getRecordSet("deletedRows").get(i, "A_PK"             )); 
					//dsI000In.putField("AF_MOVE_DEFAULT"      , requestData.getRecordSet("deletedRows").get(i, "MOVE_DEFAULT"     )); 
					//dsI000In.putField("AF_MOVE_YN"           , requestData.getRecordSet("deletedRows").get(i, "MOVE_YN"          )); 
					//dsI000In.putField("AF_MOVE_RULE"         , requestData.getRecordSet("deletedRows").get(i, "MOVE_RULE"        )); 
					//dsI000In.putField("AF_MOVE_SQL"          , requestData.getRecordSet("deletedRows").get(i, "MOVE_SQL"         )); 
					//dsI000In.putField("AF_ALT_EMP_NO"        , requestData.getRecordSet("deletedRows").get(i, "ALT_EMP_NO"       )); 
					//dsI000In.putField("AF_PREE_CDTN"         , requestData.getRecordSet("deletedRows").get(i, "PREE_CDTN"        )); 
					//dsI000In.putField("AF_ALT_DT"            , requestData.getRecordSet("deletedRows").get(i, "ALT_DT"           )); 
					//dsI000In.putField("AF_JOB_OWNER"         , requestData.getRecordSet("deletedRows").get(i, "JOB_OWNER"        )); 
					//dsI000In.putField("AF_CLIENT_OWNER"      , requestData.getRecordSet("deletedRows").get(i, "CLIENT_OWNER"     )); 
					//dsI000In.putField("AF_MOVE_OWNER"        , requestData.getRecordSet("deletedRows").get(i, "MOVE_OWNER"       )); 
					
					/*변경이력 등록*/
					chgCnt =  dh.insertSql(dsI000In, namespace+"."+"I002");
					
					if(chgCnt <=  0) {
						throw new Exception( namespace+"."+"I002"+" 처리 건수 없음.");
					}

					dCnt++;
				}
			}
			
			/*갱신*/
			if( requestData.getRecordSet("updatedRows") != null){
				for(int i = 0; i <  requestData.getRecordSet("updatedRows").getRecordCount() ; i++){
					if( StringUtils.isEmpty(String.valueOf(requestData.getRecordSet("updatedRows").get(i, "ID")))){
						continue;
					}
					/*update*/
										
					dsI000In.putField("ID"                , requestData.getRecordSet("updatedRows").get(i, "ID"               )); 
					dsI000In.putField("MAP_STAT"          , "2"); /*신청중*/
					dsI000In.putField("MAP_IUD_FLGCD"     , "U"); /*갱신*/
					
					rsCnt =  dh.updateSql(dsI000In, namespace+"."+"U002");
					
					if(rsCnt <=  0) {
						throw new Exception( namespace+"."+"U002"+" 처리 건수 없음.");
					}
					
					/*변경전 컬럼매핑정의서 조회*/
					dsTbl  =  dh.selectSql(dsI000In, namespace+"."+"S004");
					rsTbl  =  dsTbl.getRecordSet("ResultSet");
					
					dsI000In.putField("BF_MAP_SORT"          , rsTbl.get(0, "MAP_SORT"         )); 
					dsI000In.putField("BF_MAP_ID"            , rsTbl.get(0, "MAP_ID"           )); 
					dsI000In.putField("BF_T_SYSTEM_NAME"     , rsTbl.get(0, "T_SYSTEM_NAME"    )); 
					dsI000In.putField("BF_T_OWNER"           , rsTbl.get(0, "T_OWNER"          )); 
					dsI000In.putField("BF_T_ENG_TABLE_NAME"  , rsTbl.get(0, "T_ENG_TABLE_NAME" )); 
					dsI000In.putField("BF_T_KOR_TABLE_NAME"  , rsTbl.get(0, "T_KOR_TABLE_NAME" )); 
					dsI000In.putField("BF_T_ENG_COLUMN_NAME" , rsTbl.get(0, "T_ENG_COLUMN_NAME")); 
					dsI000In.putField("BF_T_KOR_COLUMN_NAME" , rsTbl.get(0, "T_KOR_COLUMN_NAME")); 
					dsI000In.putField("BF_T_DATA_TYPE"       , rsTbl.get(0, "T_DATA_TYPE"      )); 
					dsI000In.putField("BF_T_LENGTH1"         , rsTbl.get(0, "T_LENGTH1"        )); 
					dsI000In.putField("BF_T_LENGTH2"         , rsTbl.get(0, "T_LENGTH2"        )); 
					dsI000In.putField("BF_T_PK"              , rsTbl.get(0, "T_PK"             )); 
					dsI000In.putField("BF_A_SYSTEM_NAME"     , rsTbl.get(0, "A_SYSTEM_NAME"    )); 
					dsI000In.putField("BF_A_OWNER"           , rsTbl.get(0, "A_OWNER"          )); 
					dsI000In.putField("BF_A_ENG_TABLE_NAME"  , rsTbl.get(0, "A_ENG_TABLE_NAME" )); 
					dsI000In.putField("BF_A_KOR_TABLE_NAME"  , rsTbl.get(0, "A_KOR_TABLE_NAME" )); 
					dsI000In.putField("BF_A_ENG_COLUMN_NAME" , rsTbl.get(0, "A_ENG_COLUMN_NAME")); 
					dsI000In.putField("BF_A_KOR_COLUMN_NAME" , rsTbl.get(0, "A_KOR_COLUMN_NAME")); 
					dsI000In.putField("BF_A_DATA_TYPE"       , rsTbl.get(0, "A_DATA_TYPE"      )); 
					dsI000In.putField("BF_A_LENGTH1"         , rsTbl.get(0, "A_LENGTH1"        )); 
					dsI000In.putField("BF_A_LENGTH2"         , rsTbl.get(0, "A_LENGTH2"        )); 
					dsI000In.putField("BF_A_PK"              , rsTbl.get(0, "A_PK"             )); 
					dsI000In.putField("BF_MOVE_DEFAULT"      , rsTbl.get(0, "MOVE_DEFAULT"     )); 
					dsI000In.putField("BF_MOVE_YN"           , rsTbl.get(0, "MOVE_YN"          )); 
					dsI000In.putField("BF_MOVE_RULE"         , rsTbl.get(0, "MOVE_RULE"        )); 
					dsI000In.putField("BF_MOVE_SQL"          , rsTbl.get(0, "MOVE_SQL"         )); 
					dsI000In.putField("BF_ALT_EMP_NO"        , rsTbl.get(0, "ALT_EMP_NO"       )); 
					dsI000In.putField("BF_PREE_CDTN"         , rsTbl.get(0, "PREE_CDTN"        )); 
					dsI000In.putField("BF_ALT_DT"            , rsTbl.get(0, "ALT_DT"           )); 
					dsI000In.putField("BF_JOB_OWNER"         , rsTbl.get(0, "JOB_OWNER"        )); 
					dsI000In.putField("BF_CLIENT_OWNER"      , rsTbl.get(0, "CLIENT_OWNER"     )); 
					dsI000In.putField("BF_MOVE_OWNER"        , rsTbl.get(0, "MOVE_OWNER"       )); 
					
					dsI000In.putField("AF_MAP_SORT"          , requestData.getRecordSet("updatedRows").get(i, "MAP_SORT"         )); 
					dsI000In.putField("AF_MAP_ID"            , requestData.getRecordSet("updatedRows").get(i, "MAP_ID"           )); 
					dsI000In.putField("AF_T_SYSTEM_NAME"     , requestData.getRecordSet("updatedRows").get(i, "T_SYSTEM_NAME"    )); 
					dsI000In.putField("AF_T_OWNER"           , requestData.getRecordSet("updatedRows").get(i, "T_OWNER"          )); 
					dsI000In.putField("AF_T_ENG_TABLE_NAME"  , requestData.getRecordSet("updatedRows").get(i, "T_ENG_TABLE_NAME" )); 
					dsI000In.putField("AF_T_KOR_TABLE_NAME"  , requestData.getRecordSet("updatedRows").get(i, "T_KOR_TABLE_NAME" )); 
					dsI000In.putField("AF_T_ENG_COLUMN_NAME" , requestData.getRecordSet("updatedRows").get(i, "T_ENG_COLUMN_NAME")); 
					dsI000In.putField("AF_T_KOR_COLUMN_NAME" , requestData.getRecordSet("updatedRows").get(i, "T_KOR_COLUMN_NAME")); 
					dsI000In.putField("AF_T_DATA_TYPE"       , requestData.getRecordSet("updatedRows").get(i, "T_DATA_TYPE"      )); 
					dsI000In.putField("AF_T_LENGTH1"         , requestData.getRecordSet("updatedRows").get(i, "T_LENGTH1"        )); 
					dsI000In.putField("AF_T_LENGTH2"         , requestData.getRecordSet("updatedRows").get(i, "T_LENGTH2"        )); 
					dsI000In.putField("AF_T_PK"              , requestData.getRecordSet("updatedRows").get(i, "T_PK"             )); 
					dsI000In.putField("AF_A_SYSTEM_NAME"     , requestData.getRecordSet("updatedRows").get(i, "A_SYSTEM_NAME"    )); 
					dsI000In.putField("AF_A_OWNER"           , requestData.getRecordSet("updatedRows").get(i, "A_OWNER"          )); 
					dsI000In.putField("AF_A_ENG_TABLE_NAME"  , requestData.getRecordSet("updatedRows").get(i, "A_ENG_TABLE_NAME" )); 
					dsI000In.putField("AF_A_KOR_TABLE_NAME"  , requestData.getRecordSet("updatedRows").get(i, "A_KOR_TABLE_NAME" )); 
					dsI000In.putField("AF_A_ENG_COLUMN_NAME" , requestData.getRecordSet("updatedRows").get(i, "A_ENG_COLUMN_NAME")); 
					dsI000In.putField("AF_A_KOR_COLUMN_NAME" , requestData.getRecordSet("updatedRows").get(i, "A_KOR_COLUMN_NAME")); 
					dsI000In.putField("AF_A_DATA_TYPE"       , requestData.getRecordSet("updatedRows").get(i, "A_DATA_TYPE"      )); 
					dsI000In.putField("AF_A_LENGTH1"         , requestData.getRecordSet("updatedRows").get(i, "A_LENGTH1"        )); 
					dsI000In.putField("AF_A_LENGTH2"         , requestData.getRecordSet("updatedRows").get(i, "A_LENGTH2"        )); 
					dsI000In.putField("AF_A_PK"              , requestData.getRecordSet("updatedRows").get(i, "A_PK"             )); 
					dsI000In.putField("AF_MOVE_DEFAULT"      , requestData.getRecordSet("updatedRows").get(i, "MOVE_DEFAULT"     )); 
					dsI000In.putField("AF_MOVE_YN"           , requestData.getRecordSet("updatedRows").get(i, "MOVE_YN"          )); 
					dsI000In.putField("AF_MOVE_RULE"         , requestData.getRecordSet("updatedRows").get(i, "MOVE_RULE"        )); 
					dsI000In.putField("AF_MOVE_SQL"          , requestData.getRecordSet("updatedRows").get(i, "MOVE_SQL"         )); 
					dsI000In.putField("AF_ALT_EMP_NO"        , requestData.getRecordSet("updatedRows").get(i, "ALT_EMP_NO"       )); 
					dsI000In.putField("AF_PREE_CDTN"         , requestData.getRecordSet("updatedRows").get(i, "PREE_CDTN"        )); 
					dsI000In.putField("AF_ALT_DT"            , requestData.getRecordSet("updatedRows").get(i, "ALT_DT"           )); 
					dsI000In.putField("AF_JOB_OWNER"         , requestData.getRecordSet("updatedRows").get(i, "JOB_OWNER"        )); 
					dsI000In.putField("AF_CLIENT_OWNER"      , requestData.getRecordSet("updatedRows").get(i, "CLIENT_OWNER"     )); 
					dsI000In.putField("AF_MOVE_OWNER"        , requestData.getRecordSet("updatedRows").get(i, "MOVE_OWNER"       )); 
					
					/*변경이력 등록*/
					chgCnt =  dh.insertSql(dsI000In, namespace+"."+"I002");
					
					if(chgCnt <=  0) {
						throw new Exception( namespace+"."+"I002"+" 처리 건수 없음.");
					}
					
					uCnt++;
				}
			}
			
			/*등록*/
			if( requestData.getRecordSet("createdRows") != null){
				for(int i = 0; i <  requestData.getRecordSet("createdRows").getRecordCount() ; i++){
					
					/*insert*/
					 
					dsI000In.putField("MAP_SORT"          , requestData.getRecordSet("createdRows").get(i, "MAP_SORT"         )); 
					dsI000In.putField("MAP_ID"            , requestData.getRecordSet("createdRows").get(i, "MAP_ID"           )); 
					dsI000In.putField("T_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "T_SYSTEM_NAME"    )); 
					dsI000In.putField("T_OWNER"           , requestData.getRecordSet("createdRows").get(i, "T_OWNER"          )); 
					dsI000In.putField("T_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_ENG_TABLE_NAME" )); 
					dsI000In.putField("T_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_KOR_TABLE_NAME" )); 
					dsI000In.putField("T_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_ENG_COLUMN_NAME")); 
					dsI000In.putField("T_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_KOR_COLUMN_NAME")); 
					dsI000In.putField("T_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "T_DATA_TYPE"      )); 
					dsI000In.putField("T_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH1"        )); 
					dsI000In.putField("T_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH2"        )); 
					dsI000In.putField("T_PK"              , requestData.getRecordSet("createdRows").get(i, "T_PK"             )); 
					dsI000In.putField("A_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "A_SYSTEM_NAME"    )); 
					dsI000In.putField("A_OWNER"           , requestData.getRecordSet("createdRows").get(i, "A_OWNER"          )); 
					dsI000In.putField("A_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_ENG_TABLE_NAME" )); 
					dsI000In.putField("A_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_KOR_TABLE_NAME" )); 
					dsI000In.putField("A_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_ENG_COLUMN_NAME")); 
					dsI000In.putField("A_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_KOR_COLUMN_NAME")); 
					dsI000In.putField("A_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "A_DATA_TYPE"      )); 
					dsI000In.putField("A_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH1"        )); 
					dsI000In.putField("A_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH2"        )); 
					dsI000In.putField("A_PK"              , requestData.getRecordSet("createdRows").get(i, "A_PK"             )); 
					dsI000In.putField("MOVE_DEFAULT"      , requestData.getRecordSet("createdRows").get(i, "MOVE_DEFAULT"     )); 
					dsI000In.putField("MOVE_YN"           , requestData.getRecordSet("createdRows").get(i, "MOVE_YN"          )); 
					dsI000In.putField("MOVE_RULE"         , requestData.getRecordSet("createdRows").get(i, "MOVE_RULE"        )); 
					dsI000In.putField("MOVE_SQL"          , requestData.getRecordSet("createdRows").get(i, "MOVE_SQL"         )); 
					dsI000In.putField("ALT_EMP_NO"        , requestData.getRecordSet("createdRows").get(i, "ALT_EMP_NO"       )); 
					dsI000In.putField("PREE_CDTN"         , requestData.getRecordSet("createdRows").get(i, "PREE_CDTN"        )); 
					dsI000In.putField("ALT_DT"            , requestData.getRecordSet("createdRows").get(i, "ALT_DT"           )); 
					dsI000In.putField("JOB_OWNER"         , requestData.getRecordSet("createdRows").get(i, "JOB_OWNER"        )); 
					dsI000In.putField("CLIENT_OWNER"      , requestData.getRecordSet("createdRows").get(i, "CLIENT_OWNER"     )); 
					dsI000In.putField("MOVE_OWNER"        , requestData.getRecordSet("createdRows").get(i, "MOVE_OWNER"       )); 
					
					dsI000In.putField("MAP_STAT"          , "2"); /*신청중*/
					dsI000In.putField("MAP_IUD_FLGCD"     , "I"); /*등록*/
					
					rsCnt =  dh.insertSql(dsI000In, namespace+"."+"I001");
					
					if(rsCnt <=  0) {
						throw new Exception( namespace+"."+"I001"+" 처리 건수 없음.");
					}
					
					iCnt++;
					
					dsSel = dh.selectSql(dsI000In, namespace+"."+"S003");
					
					dsI000In.putField("ID"  , dsSel.getRecordSet("ResultSet").get(0, "ID"));
					
					//dsI000In.putField("BF_MAP_SORT"          , requestData.getRecordSet("createdRows").get(i, "MAP_SORT"         )); 
					//dsI000In.putField("BF_MAP_ID"            , requestData.getRecordSet("createdRows").get(i, "MAP_ID"           )); 
					//dsI000In.putField("BF_T_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "T_SYSTEM_NAME"    )); 
					//dsI000In.putField("BF_T_OWNER"           , requestData.getRecordSet("createdRows").get(i, "T_OWNER"          )); 
					//dsI000In.putField("BF_T_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_ENG_TABLE_NAME" )); 
					//dsI000In.putField("BF_T_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_KOR_TABLE_NAME" )); 
					//dsI000In.putField("BF_T_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_ENG_COLUMN_NAME")); 
					//dsI000In.putField("BF_T_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_KOR_COLUMN_NAME")); 
					//dsI000In.putField("BF_T_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "T_DATA_TYPE"      )); 
					//dsI000In.putField("BF_T_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH1"        )); 
					//dsI000In.putField("BF_T_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH2"        )); 
					//dsI000In.putField("BF_T_PK"              , requestData.getRecordSet("createdRows").get(i, "T_PK"             )); 
					//dsI000In.putField("BF_A_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "A_SYSTEM_NAME"    )); 
					//dsI000In.putField("BF_A_OWNER"           , requestData.getRecordSet("createdRows").get(i, "A_OWNER"          )); 
					//dsI000In.putField("BF_A_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_ENG_TABLE_NAME" )); 
					//dsI000In.putField("BF_A_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_KOR_TABLE_NAME" )); 
					//dsI000In.putField("BF_A_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_ENG_COLUMN_NAME")); 
					//dsI000In.putField("BF_A_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_KOR_COLUMN_NAME")); 
					//dsI000In.putField("BF_A_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "A_DATA_TYPE"      )); 
					//dsI000In.putField("BF_A_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH1"        )); 
					//dsI000In.putField("BF_A_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH2"        )); 
					//dsI000In.putField("BF_A_PK"              , requestData.getRecordSet("createdRows").get(i, "A_PK"             )); 
					//dsI000In.putField("BF_MOVE_DEFAULT"      , requestData.getRecordSet("createdRows").get(i, "MOVE_DEFAULT"     )); 
					//dsI000In.putField("BF_MOVE_YN"           , requestData.getRecordSet("createdRows").get(i, "MOVE_YN"          )); 
					//dsI000In.putField("BF_MOVE_RULE"         , requestData.getRecordSet("createdRows").get(i, "MOVE_RULE"        )); 
					//dsI000In.putField("BF_MOVE_SQL"          , requestData.getRecordSet("createdRows").get(i, "MOVE_SQL"         )); 
					//dsI000In.putField("BF_ALT_EMP_NO"        , requestData.getRecordSet("createdRows").get(i, "ALT_EMP_NO"       )); 
					//dsI000In.putField("BF_PREE_CDTN"         , requestData.getRecordSet("createdRows").get(i, "PREE_CDTN"        )); 
					//dsI000In.putField("BF_ALT_DT"            , requestData.getRecordSet("createdRows").get(i, "ALT_DT"           )); 
					//dsI000In.putField("BF_JOB_OWNER"         , requestData.getRecordSet("createdRows").get(i, "JOB_OWNER"        )); 
					//dsI000In.putField("BF_CLIENT_OWNER"      , requestData.getRecordSet("createdRows").get(i, "CLIENT_OWNER"     )); 
					//dsI000In.putField("BF_MOVE_OWNER"        , requestData.getRecordSet("createdRows").get(i, "MOVE_OWNER"       )); 
					
					dsI000In.putField("AF_MAP_SORT"          , requestData.getRecordSet("createdRows").get(i, "MAP_SORT"         )); 
					dsI000In.putField("AF_MAP_ID"            , requestData.getRecordSet("createdRows").get(i, "MAP_ID"           )); 
					dsI000In.putField("AF_T_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "T_SYSTEM_NAME"    )); 
					dsI000In.putField("AF_T_OWNER"           , requestData.getRecordSet("createdRows").get(i, "T_OWNER"          )); 
					dsI000In.putField("AF_T_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_ENG_TABLE_NAME" )); 
					dsI000In.putField("AF_T_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "T_KOR_TABLE_NAME" )); 
					dsI000In.putField("AF_T_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_ENG_COLUMN_NAME")); 
					dsI000In.putField("AF_T_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "T_KOR_COLUMN_NAME")); 
					dsI000In.putField("AF_T_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "T_DATA_TYPE"      )); 
					dsI000In.putField("AF_T_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH1"        )); 
					dsI000In.putField("AF_T_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "T_LENGTH2"        )); 
					dsI000In.putField("AF_T_PK"              , requestData.getRecordSet("createdRows").get(i, "T_PK"             )); 
					dsI000In.putField("AF_A_SYSTEM_NAME"     , requestData.getRecordSet("createdRows").get(i, "A_SYSTEM_NAME"    )); 
					dsI000In.putField("AF_A_OWNER"           , requestData.getRecordSet("createdRows").get(i, "A_OWNER"          )); 
					dsI000In.putField("AF_A_ENG_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_ENG_TABLE_NAME" )); 
					dsI000In.putField("AF_A_KOR_TABLE_NAME"  , requestData.getRecordSet("createdRows").get(i, "A_KOR_TABLE_NAME" )); 
					dsI000In.putField("AF_A_ENG_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_ENG_COLUMN_NAME")); 
					dsI000In.putField("AF_A_KOR_COLUMN_NAME" , requestData.getRecordSet("createdRows").get(i, "A_KOR_COLUMN_NAME")); 
					dsI000In.putField("AF_A_DATA_TYPE"       , requestData.getRecordSet("createdRows").get(i, "A_DATA_TYPE"      )); 
					dsI000In.putField("AF_A_LENGTH1"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH1"        )); 
					dsI000In.putField("AF_A_LENGTH2"         , requestData.getRecordSet("createdRows").get(i, "A_LENGTH2"        )); 
					dsI000In.putField("AF_A_PK"              , requestData.getRecordSet("createdRows").get(i, "A_PK"             )); 
					dsI000In.putField("AF_MOVE_DEFAULT"      , requestData.getRecordSet("createdRows").get(i, "MOVE_DEFAULT"     )); 
					dsI000In.putField("AF_MOVE_YN"           , requestData.getRecordSet("createdRows").get(i, "MOVE_YN"          )); 
					dsI000In.putField("AF_MOVE_RULE"         , requestData.getRecordSet("createdRows").get(i, "MOVE_RULE"        )); 
					dsI000In.putField("AF_MOVE_SQL"          , requestData.getRecordSet("createdRows").get(i, "MOVE_SQL"         )); 
					dsI000In.putField("AF_ALT_EMP_NO"        , requestData.getRecordSet("createdRows").get(i, "ALT_EMP_NO"       )); 
					dsI000In.putField("AF_PREE_CDTN"         , requestData.getRecordSet("createdRows").get(i, "PREE_CDTN"        )); 
					dsI000In.putField("AF_ALT_DT"            , requestData.getRecordSet("createdRows").get(i, "ALT_DT"           )); 
					dsI000In.putField("AF_JOB_OWNER"         , requestData.getRecordSet("createdRows").get(i, "JOB_OWNER"        )); 
					dsI000In.putField("AF_CLIENT_OWNER"      , requestData.getRecordSet("createdRows").get(i, "CLIENT_OWNER"     )); 
					dsI000In.putField("AF_MOVE_OWNER"        , requestData.getRecordSet("createdRows").get(i, "MOVE_OWNER"       )); 
					/*변경이력 등록*/
					chgCnt =  dh.insertSql(dsI000In, namespace+"."+"I002");
					
					if(chgCnt <=  0) {
						throw new Exception( namespace+"."+"I002"+" 처리 건수 없음.");
					}
				}
			}
			
			rtnMsg = "처리완료 되었습니다. 등록 : (" + iCnt  + ") 수정 : ("+ uCnt  + ") 삭제 : ("+ dCnt +")";
		    responseData.putField("rtnMsg", today + " " +rtnMsg);
		    return responseData;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
}
