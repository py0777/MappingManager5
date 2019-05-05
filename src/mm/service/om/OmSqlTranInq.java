package mm.service.om;

import mm.common.IContextHeader;
import mm.common.IService;
import mm.service.cm.CmSqlTranInq;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;

import org.apache.log4j.Logger;

public class OmSqlTranInq implements IService{
	static Logger logger = Logger.getLogger(OmSqlTranInq.class);
 
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
				responseData = inquireSql( ich,  requestData);
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
	
	private IDataSet inquireSql(IContextHeader ich, IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		logger.debug(requestData.getFieldValues("TOBE_TBL_LIST"));
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		CmSqlTranInq cmSqlTranInq = new CmSqlTranInq();
		try
		{	
			
			/********************************************************************
			 *  입력값 체크
			 ********************************************************************/
			initCheck(requestData);
			
			/********************************************************************
			 *  sql변환 조회
			 ********************************************************************/
			responseData = cmSqlTranInq.inquireSql(ich, requestData);
			
			
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
	/*initCheck*/
	private void initCheck(IDataSet requestData) throws Exception {
		logger.debug("[Start] initCheck");
		
	}		
}
