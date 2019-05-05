package mm.service.om;

import mm.common.IContextHeader;
import mm.common.IService;
import mm.service.cm.CmUpDef;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;

import org.apache.log4j.Logger;


public class OmUpDef implements IService {
	static Logger logger = Logger.getLogger(OmUpDef.class);

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
				responseData = inquireDef( ich,  requestData);
			}else if("CA".equals(ich.getFnCd())){
				/*등록*/
				responseData = modifyDef( ich,  requestData);
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
	
	private IDataSet modifyDef(IContextHeader ich, IDataSet requestData) throws Exception{
		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		CmUpDef cmUpDef = new CmUpDef();
		try
		{	
			
			/********************************************************************
			 *  입력값 체크
			 ********************************************************************/
			initCheck(requestData);
			
			/********************************************************************
			 *  자료 등록/수정/삭제처리
			 ********************************************************************/
			responseData = cmUpDef.modifyDef(requestData);
			
			
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
	
	
	private IDataSet inquireDef(IContextHeader ich, IDataSet requestData) throws Exception{

		logger.debug("###########  START #########");
		logger.debug(getClass().getName());
		
		logger.debug(requestData);
		logger.debug(requestData.getFieldValues("MAP_ID"));
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		CmUpDef cmUpDef = new CmUpDef();
		try
		{	
			
			/********************************************************************
			 *  입력값 체크
			 ********************************************************************/
			initCheck(requestData);
			
			/********************************************************************
			 *  매핑정의서 조회
			 ********************************************************************/
			responseData = cmUpDef.inquireDef(ich,requestData);
			
			
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
	
	
	
	/*initCheck*/
	private void initCheck(IDataSet requestData) throws Exception {
		logger.debug("[Start] initCheck");
		
	}	
}
