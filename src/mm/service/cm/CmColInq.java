package mm.service.cm;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import mm.common.DaoHandler;
import mm.common.IContextHeader;
import mm.common.RecordSetResultHandler;
import mm.repository.AbstractRepository;
import mm.service.om.OmSqlTranInq;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.util.StringUtils;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


public class CmColInq {

	static Logger logger = Logger.getLogger(CmColInq.class);
	private final String namespace = "mm.repository.mapper.ColMapper";
	
	
	public IDataSet inquireCol(IContextHeader ich, IDataSet requestData) {
		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData.getField("INPUT"));
				
		/*************************************************************
		 *  Declare Var
		 *************************************************************/
		IDataSet 	responseData 	= new DataSet();
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		
		IDataSet 	dsCol 			= null;
		IRecordSet 	rsCol 			= null;
		IRecordSet 	rsTbl 			= new RecordSet("AA");
		String      rtnMsg			= "";
		String today = "";
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss초 E(a)");

		try
		{
			Date d = gc.getTime();
			today = sdformat.format(d);
			
			if( requestData.getField("IN_ATBL").isEmpty() || "NULL".equals(requestData.getField("IN_ATBL")))
			{
				requestData.putField("IN_ATBL", "%");
			}
			
			if( requestData.getField("IN_ACOL").isEmpty() || "NULL".equals(requestData.getField("IN_ACOL")))
			{
				requestData.putField("IN_ACOL", "%");
			}
			
			if( requestData.getField("IN_TTBL").isEmpty() || "NULL".equals(requestData.getField("IN_TTBL")))
			{
				requestData.putField("IN_TTBL", "%");
			}
			
			if( requestData.getField("IN_TCOL").isEmpty() || "NULL".equals(requestData.getField("IN_TCOL")))
			{
				requestData.putField("IN_TCOL", "%");
			}
			
			/*입력값 null 체크*/
			if(		"%".equals(requestData.getField("IN_ATBL"))
				&&  "%".equals(requestData.getField("IN_ACOL"))
				&&  "%".equals(requestData.getField("IN_TTBL"))
				&&  "%".equals(requestData.getField("IN_TCOL")))
			{
				rtnMsg = "테이블 또는 컬럼을 입력하세요.";
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
			
			/*헤더값 설정*/
			for(int i = 0 ; i < rsCol.getHeaderCount(); i ++ )
			{
				rsTbl.addHeader(i, rsCol.getHeader(i));
			}
			
			/*벼누 설정*/
			String l_T_ENG_TABLE_NAME = "";
			String l_A_ENG_TABLE_NAME = "";
			String l_MAP_ID = "";
			
			int j = 0;
			for(int i=0; i < rsCol.getRecordCount(); i++)
			{	
				
				if( 	StringUtils.isEmpty(rsCol.getRecord(i).get("T_ENG_TABLE_NAME"))
					||	StringUtils.isEmpty(rsCol.getRecord(i).get("A_ENG_TABLE_NAME"))	)
				{
					continue;
				}
				
				if(l_MAP_ID.equals(rsCol.getRecord(i).get("MAP_ID")))
				{
					continue;
				}
				
				if( 	!l_T_ENG_TABLE_NAME.equals(rsCol.getRecord(i).get("T_ENG_TABLE_NAME"))
					||	!l_A_ENG_TABLE_NAME.equals(rsCol.getRecord(i).get("A_ENG_TABLE_NAME"))	)
				{
					logger.debug( rsCol.getRecord(i).get("T_ENG_TABLE_NAME"));
					logger.debug(rsCol.getRecord(i).get("A_ENG_TABLE_NAME"));
					rsTbl.addRecord(j, rsCol.getRecord(i));
					j++;
				}
				
				l_T_ENG_TABLE_NAME = rsCol.getRecord(i).get("T_ENG_TABLE_NAME");
				l_A_ENG_TABLE_NAME = rsCol.getRecord(i).get("A_ENG_TABLE_NAME");
				l_MAP_ID = rsCol.getRecord(i).get("MAP_ID");
			}
			
			logger.debug(rsTbl);
			responseData.putRecordSet("rsTbl", rsTbl);
			responseData.putRecordSet("rsCol", rsCol);
			
			responseData.putField("rsTblCnt", rsTbl.getRecordCount());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		rtnMsg = "조회완료되었습니다.";
		responseData.putField("rtnMsg", today + " " + rtnMsg);
		responseData.setOkResultMessage("OK", new String[]{"조회완료되었습니다."});
		
		return responseData;	
	}
}
