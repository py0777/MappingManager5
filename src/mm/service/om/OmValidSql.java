package mm.service.om;

import mm.common.IContextHeader;
import mm.common.IService;
import mm.service.cm.CmValidSql;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;

import org.apache.log4j.Logger;

public class OmValidSql implements IService{
	static Logger logger = Logger.getLogger(OmValidSql.class);


	
	public IDataSet service(IContextHeader ich, IDataSet requestData) throws Exception{

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
				responseData = inquireValidSql( ich,  requestData);
			} else{
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
	
	private IDataSet inquireValidSql(IContextHeader ich, IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		logger.debug(requestData.getFieldValues("MAP_ID"));
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		CmValidSql cmValidSql = new CmValidSql();
		try
		{	
			
			/********************************************************************
			 *  입력값 체크
			 ********************************************************************/
			initCheck(requestData);
			
			/********************************************************************
			 *  sql변환 조회
			 ********************************************************************/
			responseData = cmValidSql.inquireValidSql(ich, requestData);
			
			
		}catch (Exception e) {
			
			e.printStackTrace();
		
			throw e;
			
		}
		/*************************************************************
		 * Retrun Result Data
		 *************************************************************/
		
		responseData.setOkResultMessage("OK", new String[]{"OK"});
		logger.debug("[end] responseData" + responseData);
		return responseData;
	}
	
	/*initCheck*/
	private void initCheck(IDataSet requestData) throws Exception {
		logger.debug("[Start] initCheck");
		
	}		
}
