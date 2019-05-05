package mm.common;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import mm.service.om.OmSqlFormatter;
import nexcore.framework.core.data.IDataSet;

import com.sun.jersey.api.representation.Form;


public class ServiceHandler {
	static Logger logger = Logger.getLogger(OmSqlFormatter.class);
	public IDataSet serviceHandler(MultivaluedMap<String, String> conTextHeader, Form form) {
		// TODO Auto-generated method stub
		IDataSet requestData = null;
		IDataSet responseData = null;
		/*IOHandler 호출*/
		IOHandler ioh = new IOHandler();
		IContextHeader ich = new IContextHeader();
		
		requestData = ioh.ioHandler( form);
		
		
		String className = null;		// FQCN
		
		String pkgNm = "mm.service.";
		
		//서비스명 가져오기
		for(String str : conTextHeader.keySet()){
		     if("trid".equals(str)){
		    	 logger.debug("trid -- "+ conTextHeader.getFirst(str));
		    	 if("upDef".equals(conTextHeader.getFirst(str))){
		    		 className = pkgNm+"om.OmUpDef";
		    	 }else if("validSql".equals(conTextHeader.getFirst(str))){
		    		 className = pkgNm+"om.OmValidSql";
		    	 }else if("sqlTran".equals(conTextHeader.getFirst(str))){
		    		 className = pkgNm+"om.OmSqlTranInq";
		    	 }else if("sqlFormatter".equals(conTextHeader.getFirst(str))){
		    		 className = pkgNm+"om.OmSqlFormatter";
		    	 }
		    	 else if("colinq".equals(conTextHeader.getFirst(str))){
		    		 className = pkgNm+"om.OmColInq";
		    	 }
		    	 else if("colChg".equals(conTextHeader.getFirst(str))){
		    		 className = pkgNm+"om.OmColChg";
		    	 }
		    	 /*trid설정*/
		    	 ich.setTrId(conTextHeader.getFirst(str));
		     }
		     
		     if("fncd".equals(str)){
		    	 
		    	 /*fnCd설정*/
		    	 ich.setFnCd(conTextHeader.getFirst(str));
		     }
		}
		logger.debug("className :"+className);
	
		Class clazz = null;
		Object obj = null;
		IService service = null;
		
		try {
			clazz = Class.forName(className);

			obj = clazz.newInstance();
			
			service = (IService) obj;

			responseData = service.service(ich, requestData);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseData;

	}

}
