package mm.service.om;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import mm.common.IContextHeader;
import mm.common.IService;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;

import org.apache.log4j.Logger;

import py0777.orasql.SQLBeautifier;

public class OmSqlFormatter implements IService{

	static Logger logger = Logger.getLogger(OmSqlFormatter.class);
	 
	public IDataSet service(IContextHeader ich,IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		
		try
		{	
			if("RA".equals(ich.getFnCd())){
				/*조회*/
				responseData = inquireFormatter( ich,  requestData);
			}else{
				/*기능 코드가 올바르지 않습니다.*/
				throw new Exception("기능 코드가 올바르지 않습니다. fnCd:" + ich.getFnCd());
			}
		}catch (Exception e) {
			
			e.printStackTrace();
		
			throw e;
			
		}
		/*************************************************************
		 * Retrun Result Data
		 *************************************************************/
		
		responseData.setOkResultMessage("OK", new String[]{"OK"});
		
		return responseData;
	}
	
	private IDataSet inquireFormatter(IContextHeader ich, IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
	
		String rtnMsg = "";

		String query = "";
		String today = "";
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss초 E(a)");
		try
		{	
			Date d = gc.getTime();
			/********************************************************************
			 *  sql변환 조회
			 ********************************************************************/
			today = sdformat.format(d);
			query = requestData.getField("QUERY");

			logger.debug(query);

			SQLBeautifier sbf = new SQLBeautifier();
			
			responseData.putField("QUERY", "");
			
			responseData.putField("RESULT", sbf.beautify(query));	
			
			rtnMsg = "조회완료되었습니다.";

			responseData.putField("rtnMsg", today + " " + rtnMsg);
			
		}catch (Exception e) {
			
			e.printStackTrace();
		
			throw e;
			
		}
		finally{
			
			logger.debug("finally");
			
		}
		/*************************************************************
		 * Retrun Result Data
		 *************************************************************/
		
		responseData.setOkResultMessage("OK", new String[]{"OK"});
		
		return responseData;
	}
}
