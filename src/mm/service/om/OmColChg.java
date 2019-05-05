package mm.service.om;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;

import org.apache.log4j.Logger;

import mm.common.IContextHeader;
import mm.common.IService;
import mm.service.cm.CmColChg;
import mm.service.cm.CmColInq;

public class OmColChg implements IService{

	static Logger logger = Logger.getLogger(OmColChg.class);
	
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
				responseData = inquireColChg( ich,  requestData);
			}else if("RB".equals(ich.getFnCd())){
				/*조회*/
				responseData = inquireColChgDtl( ich,  requestData);
			}else if("CA".equals(ich.getFnCd())){
				/*조회*/
				responseData = colChg( ich,  requestData);
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
	
	private IDataSet inquireColChg(IContextHeader ich, IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		CmColChg cmColChg = new CmColChg();
		try
		{	
			
			/********************************************************************
			 *  입력값 체크
			 ********************************************************************/
			initCheck(requestData);
			
			/********************************************************************
			 *  변경내용 조회
			 ********************************************************************/
			responseData = cmColChg.inquireColChg(ich, requestData);
			
			
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
	
	
	private IDataSet inquireColChgDtl(IContextHeader ich, IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		CmColChg cmColChg = new CmColChg();
		try
		{	
			
			/********************************************************************
			 *  입력값 체크
			 ********************************************************************/
			initCheck(requestData);
			
			/********************************************************************
			 *  변경내용 상세조회
			 ********************************************************************/
			responseData = cmColChg.inquireColChgDtl(ich, requestData);
			
			
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
	
	private IDataSet colChg(IContextHeader ich, IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		CmColChg cmColChg = new CmColChg();
		try
		{	
			
			/********************************************************************
			 *  입력값 체크
			 ********************************************************************/
			initCheck(requestData);
			
			/********************************************************************
			 *  변경내용 상세조회
			 ********************************************************************/
			responseData = cmColChg.colChg(ich, requestData);
			
			
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
