package mm.service.cm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import mm.common.DaoHandler;
import mm.common.IContextHeader;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.util.StringUtils;

import org.apache.log4j.Logger;

import py0777.orasql.SQLBeautifier;

public class CmValidSql {
	static Logger logger = Logger.getLogger(CmValidSql.class);
	private final String namespace = "mm.repository.mapper.ValidSqlMapper";


	public IDataSet inquireValidSql(IContextHeader ich, IDataSet requestData) {

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		logger.debug(requestData.getFieldValues("MAP_ID"));
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		
		IDataSet dsTbl = null;
		IDataSet dsCol = null;
		
		IRecordSet rsTbl = null;
		IRecordSet rsCol = null;
		
		IRecordSet rsTblRtn = new RecordSet("RETURN_TBL");		
		StringBuffer mapIdSB = new StringBuffer();
		String[] inputMapIdTbl;
		String rtnMsg  = "";
		
		StringBuffer sb = new StringBuffer();

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
			inputMapIdTbl = requestData.getFieldValues("MAP_ID");
			
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
				
				requestData.putField("MAP_ID", rsTbl.get(i, "MAP_ID"));
				
				dsCol = dh.selectSql(requestData, namespace+"."+"S002");
				rsCol = dsCol.getRecordSet("ResultSet");
				
				logger.debug(rsCol);
				
				if(rsCol.getRecordCount() > 0){
					sb.append("/* " + rsCol.getRecord(0).get("A_ENG_TABLE_NAME") + "    MINUS  검증  */  \n\n\n" );
					sb.append("SELECT" );
					for(int j = 0; j < rsCol.getRecordCount(); j++){
						sb.append(rsCol.get(j, "T_ENG_COLUMN_NAME") + "\n");
					}
					sb.append("FROM  ");
					sb.append(rsCol.get(0, "T_ENG_TABLE_NAME") + "    /*"+ rsCol.get(0, "T_KOR_TABLE_NAME")+"*/");
					sb.append("\n");
					sb.append("MINUS " +"\n");
					
					sb.append("SELECT" );
					for(int j = 0; j < rsCol.getRecordCount(); j++){
						sb.append(rsCol.get(j, "A_ENG_COLUMN_NAME") + "\n");
					}
					sb.append("FROM  ");
					sb.append(rsCol.get(0, "A_ENG_TABLE_NAME") + "    /*"+ rsCol.get(0, "A_KOR_TABLE_NAME")+"*/");
					
					sb.append("; \n\n\n  ");
					
					sb.append("/*  " + rsCol.get(0, "A_ENG_TABLE_NAME")  +" 건수 검증 */  \n\n");
					sb.append("SELECT  COUNT(*)  AS TOBE_CNT   \n");
					sb.append(", 0 AS ASIS_CNT  \n ");
					sb.append("FROM  ");
					sb.append(rsCol.get(0, "T_ENG_TABLE_NAME") + "    /*"+ rsCol.get(0, "T_KOR_TABLE_NAME")+"*/");
					sb.append("\n  ");
					sb.append("UNION ALL");
					sb.append("\n  ");
					sb.append("SELECT  0  AS TOBE_CNT   \n");
					sb.append(", COUNT(*) AS ASIS_CNT  \n ");
					sb.append("FROM  ");
					sb.append(rsCol.get(0, "A_ENG_TABLE_NAME") + "    /*"+ rsCol.get(0, "A_KOR_TABLE_NAME")+"*/");
					sb.append("\n  ");
					sb.append("; \n\n\n\n   ");
					
				}
			}
			
			logger.debug("rsTblRtn : " + rsTblRtn);
			
			SQLBeautifier sbf = new SQLBeautifier();
			
			responseData.putField("RESULT", sbf.beautify(String.valueOf(sb)));
			
			responseData.putField("rsTblCnt", rsTblRtn.getRecordCount());
			responseData.putRecordSet("rsTblRtn", rsTblRtn);			
			responseData.putRecordSet("rsTbl", rsTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*************************************************************
		 * Retrun Result Data
		 *************************************************************/
		if (rsTblRtn.getRecordCount() == 0) {
			rtnMsg = "입력한 테이블 또는 MAP_ID가 존재하지 않습니다.";
		} else {
			rtnMsg = "조회완료되었습니다.";
		}

		responseData.putField("rtnMsg", today + " " + rtnMsg);
		responseData.setOkResultMessage("NCOM0000", null);
		return responseData;
	}
}
