package mm.service.cm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import mm.common.DaoHandler;
import mm.common.IContextHeader;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.util.StringUtils;

import org.apache.log4j.Logger;

public class CmColChg {

	static Logger logger = Logger.getLogger(CmColInq.class);
	private final String namespace = "mm.repository.mapper.ColChgMapper";
	
	public IDataSet inquireColChg(IContextHeader ich, IDataSet requestData) {
		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
				
		/*************************************************************
		 *  Declare Var
		 *************************************************************/
		IDataSet 	responseData 	= new DataSet();
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		
		IDataSet 	dsCol 			= null;
		IRecordSet 	rsCol 			= null;
		String      rtnMsg			= "";
		String today = "";
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss초 E(a)");

		try
		{
			Date d = gc.getTime();
			today = sdformat.format(d);
			
			if( requestData.getField("MAP_STAT").isEmpty() || "NULL".equals(requestData.getField("MAP_STAT")))
			{
				requestData.putField("MAP_STAT", "0");
			}
			
			/*입력값 null 체크*/
			if(	StringUtils.isEmpty(requestData.getField("STRT_DT")))
			{
				rtnMsg = "시작일자를 입력하세요.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);
				return responseData;
			}
			
			if(	StringUtils.isEmpty(requestData.getField("END_DT")))
			{
				rtnMsg = "종료일자를 입력하세요.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);
				return responseData;
			}
			
			dsCol = dh.selectSql(requestData, namespace+"."+"S001");
			rsCol = dsCol.getRecordSet("ResultSet");
			
			logger.debug(rsCol);
			
			responseData.putField("rsCnt", rsCol.getRecordCount());
			
			if(rsCol.getRecordCount() == 0)
			{
				rtnMsg = "조회내역이 없습니다.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);				
				return responseData;
			}
			
			
			responseData.putRecordSet("rsCol", rsCol);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		rtnMsg = "조회완료되었습니다.";
		responseData.putField("rtnMsg", today + " " + rtnMsg);
		responseData.setOkResultMessage("OK", new String[]{"조회완료되었습니다."});
		
		return responseData;	
	}
	
	
	
	public IDataSet  inquireColChgDtl(IContextHeader ich, IDataSet requestData) {
		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
				
		/*************************************************************
		 *  Declare Var
		 *************************************************************/
		IDataSet 	responseData 	= new DataSet();
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		
		IDataSet 	dsCol 			= null;
		IRecordSet 	rsCol 			= null;
		
		String      rtnMsg			= "";
		String today = "";
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss초 E(a)");

		try
		{
			Date d = gc.getTime();
			today = sdformat.format(d);
						
			/*입력값 null 체크*/
			if(	StringUtils.isEmpty(requestData.getField("CHNG_DT")))
			{
				rtnMsg = "시작일자를 입력하세요.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);
				return responseData;
			}
			
			if(	StringUtils.isEmpty(requestData.getField("CHNG_SEQ")))
			{
				rtnMsg = "변경순번을 입력하세요.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);
				return responseData;
			}
			
			dsCol = dh.selectSql(requestData, namespace+"."+"S002");
			rsCol = dsCol.getRecordSet("ResultSet");
			
			logger.debug(rsCol);
			
			responseData.putField("rsCnt", rsCol.getRecordCount());
			
			if(rsCol.getRecordCount() == 0)
			{
				rtnMsg = "조회내역이 없습니다.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);				
				return responseData;
			}
			
			
			responseData.putRecordSet("rsCol", rsCol);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		rtnMsg = "조회완료되었습니다.";
		responseData.putField("rtnMsg", today + " " + rtnMsg);
		responseData.setOkResultMessage("OK", new String[]{"조회완료되었습니다."});
		
		return responseData;	
	}
	
	
	public IDataSet colChg(IContextHeader ich, IDataSet requestData) throws Exception{
		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
				
		/*************************************************************
		 *  Declare Var
		 *************************************************************/
		IDataSet 	responseData 	= new DataSet();
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		
		IDataSet 	dsCol 			= null;
		IRecordSet 	rsCol 			= null;
		IDataSet dsI000In = new DataSet();
		
		int    rsCnt = 0;  /*결과 건수*/
		
		String      rtnMsg			= "";
		String today = "";
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss초 E(a)");

		try
		{
			Date d = gc.getTime();
			today = sdformat.format(d);
						
			/*입력값 null 체크*/
			if(	StringUtils.isEmpty(requestData.getField("CHNG_DT")))
			{
				rtnMsg = "변경일자를 입력하세요.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);
				return responseData;
			}
			
			if(	StringUtils.isEmpty(requestData.getField("CHNG_SEQ")))
			{
				rtnMsg = "변경순번을 입력하세요.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);
				return responseData;
			}
			
			/*MAP_ID 값 설정*/
			StringBuffer mapIdSB = new StringBuffer();
			String[] inputChngDt;
			String[] inputChngSeq;
			inputChngDt = requestData.getField("CHNG_DT").split(",");
			inputChngSeq = requestData.getField("CHNG_SEQ").split(",");
			
			if(StringUtils.isEmpty(inputChngDt[0]) || "null".equals(inputChngDt[0]) || "NULL".equals(inputChngDt[0])){
				requestData.putField("CHNG_DT", "'%'");
			}else{
				for(int i = 0 ; i <inputChngDt.length; i++){
					String tobeChngDt  = inputChngDt[i]; /*ASIS*/
					String tobeChngSeq  = inputChngSeq[i]; /*ASIS*/
					
					mapIdSB.append("('");
					mapIdSB.append(tobeChngDt);
					mapIdSB.append("'");
					mapIdSB.append(",");
					mapIdSB.append("'");
					mapIdSB.append(tobeChngSeq);
					mapIdSB.append("'");
					mapIdSB.append("),");
				}
				
				if(mapIdSB.length() > 0){
					requestData.putField("CHNG_DT_SEQ", mapIdSB.delete(mapIdSB.length() - 1,  mapIdSB.length()).toString());
				}else{
					requestData.putField("CHNG_DT_SEQ", "('%','%')");					
				}
			}
			logger.debug(mapIdSB);
			
			dsCol = dh.selectSql(requestData, namespace+"."+"S003");
			rsCol = dsCol.getRecordSet("ResultSet");
			
			logger.debug(rsCol);
			
			//responseData.putField("rsCnt", rsCol.getRecordCount());
			
			if(rsCol.getRecordCount() == 0)
			{
				rtnMsg = "조회내역이 없습니다.";
				responseData.putField("rtnMsg", today + " " + rtnMsg);				
				return responseData;
			}
			
			for(int i = 0; i <  rsCol.getRecordCount() ; i++){
				/********************************************************************
				 *  자료 등록/수정/삭제처리
				 ********************************************************************/
				dsI000In.putField("ID"                , rsCol.get(i, "ID"               )); 
				dsI000In.putField("MAP_SORT"          , rsCol.get(i, "MAP_SORT"         )); 
				dsI000In.putField("MAP_ID"            , rsCol.get(i, "MAP_ID"           )); 
				dsI000In.putField("T_SYSTEM_NAME"     , rsCol.get(i, "T_SYSTEM_NAME"    )); 
				dsI000In.putField("T_OWNER"           , rsCol.get(i, "T_OWNER"          )); 
				dsI000In.putField("T_ENG_TABLE_NAME"  , rsCol.get(i, "T_ENG_TABLE_NAME" )); 
				dsI000In.putField("T_KOR_TABLE_NAME"  , rsCol.get(i, "T_KOR_TABLE_NAME" )); 
				dsI000In.putField("T_ENG_COLUMN_NAME" , rsCol.get(i, "T_ENG_COLUMN_NAME")); 
				dsI000In.putField("T_KOR_COLUMN_NAME" , rsCol.get(i, "T_KOR_COLUMN_NAME")); 
				dsI000In.putField("T_DATA_TYPE"       , rsCol.get(i, "T_DATA_TYPE"      )); 
				dsI000In.putField("T_LENGTH1"         , rsCol.get(i, "T_LENGTH1"        )); 
				dsI000In.putField("T_LENGTH2"         , rsCol.get(i, "T_LENGTH2"        )); 
				dsI000In.putField("T_PK"              , rsCol.get(i, "T_PK"             )); 
				dsI000In.putField("A_SYSTEM_NAME"     , rsCol.get(i, "A_SYSTEM_NAME"    )); 
				dsI000In.putField("A_OWNER"           , rsCol.get(i, "A_OWNER"          )); 
				dsI000In.putField("A_ENG_TABLE_NAME"  , rsCol.get(i, "A_ENG_TABLE_NAME" )); 
				dsI000In.putField("A_KOR_TABLE_NAME"  , rsCol.get(i, "A_KOR_TABLE_NAME" )); 
				dsI000In.putField("A_ENG_COLUMN_NAME" , rsCol.get(i, "A_ENG_COLUMN_NAME")); 
				dsI000In.putField("A_KOR_COLUMN_NAME" , rsCol.get(i, "A_KOR_COLUMN_NAME")); 
				dsI000In.putField("A_DATA_TYPE"       , rsCol.get(i, "A_DATA_TYPE"      )); 
				dsI000In.putField("A_LENGTH1"         , rsCol.get(i, "A_LENGTH1"        )); 
				dsI000In.putField("A_LENGTH2"         , rsCol.get(i, "A_LENGTH2"        )); 
				dsI000In.putField("A_PK"              , rsCol.get(i, "A_PK"             )); 
				dsI000In.putField("MOVE_DEFAULT"      , rsCol.get(i, "MOVE_DEFAULT"     )); 
				dsI000In.putField("MOVE_YN"           , rsCol.get(i, "MOVE_YN"          )); 
				dsI000In.putField("MOVE_RULE"         , rsCol.get(i, "MOVE_RULE"        )); 
				dsI000In.putField("MOVE_SQL"          , rsCol.get(i, "MOVE_SQL"         )); 
				dsI000In.putField("ALT_EMP_NO"        , rsCol.get(i, "ALT_EMP_NO"       )); 
				dsI000In.putField("PREE_CDTN"         , rsCol.get(i, "PREE_CDTN"        )); 
				dsI000In.putField("ALT_DT"            , rsCol.get(i, "ALT_DT"           )); 
				dsI000In.putField("JOB_OWNER"         , rsCol.get(i, "JOB_OWNER"        )); 
				dsI000In.putField("CLIENT_OWNER"      , rsCol.get(i, "CLIENT_OWNER"     )); 
				dsI000In.putField("MOVE_OWNER"        , rsCol.get(i, "MOVE_OWNER"       )); 
							
				/*************************************************************
				 * 처리상태
				 *************************************************************/
				if(  "1".equals(requestData.getField("MAP_STAT"))
				   ||"3".equals(requestData.getField("MAP_STAT")) /*반려*/
				   ||"4".equals(requestData.getField("MAP_STAT")))/*취소는 원복*/
				{
					/*승인*/
					dsI000In.putField("MAP_STAT"          , "1"); /*반영완료*/
				}
				
				/*************************************************************
				 * 변경상태
				 *************************************************************/
				if("I".equals(rsCol.get(0, "MAP_IUD_FLGCD"))){
					rsCnt =  dh.updateSql(dsI000In, namespace+"."+"U001");
					
					if(rsCnt <=  0) {
						throw new Exception( namespace+"."+"U001"+" 처리 건수 없음.");
					}
				}else if("U".equals(rsCol.get(0, "MAP_IUD_FLGCD"))){
					rsCnt =  dh.updateSql(dsI000In, namespace+"."+"U001");
					
					if(rsCnt <=  0) {
						throw new Exception( namespace+"."+"U001"+" 처리 건수 없음.");
					}
				}else if("D".equals(rsCol.get(0, "MAP_IUD_FLGCD"))){
					rsCnt =  dh.deleteSql(dsI000In, namespace+"."+"D001");
					
					if(rsCnt <=  0) {
						throw new Exception( namespace+"."+"D001"+" 처리 건수 없음.");
					}
				}
				
				/*************************************************************
				 * 이력 변경
				 *************************************************************/
				dsI000In.putField("CHNG_DT_SEQ"  , requestData.getField("CHNG_DT_SEQ"));
//				dsI000In.putField("CHNG_DT"      , requestData.getField("CHNG_DT")); 
//				dsI000In.putField("CHNG_SEQ"     , requestData.getField("CHNG_SEQ")); 
				dsI000In.putField("MAP_STAT"     , requestData.getField("MAP_STAT")); /*반영완료*/
				
				rsCnt =  dh.updateSql(dsI000In, namespace+"."+"U002");
				
				if(rsCnt <=  0) {
					throw new Exception( namespace+"."+"U001"+" 처리 건수 없음.");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		rtnMsg = "처리 완료되었습니다.";
		responseData.putField("rtnMsg", today + " " + rtnMsg);
		responseData.setOkResultMessage("OK", new String[]{"처리 완료되었습니다."});
		
		return responseData;	
	}
}
