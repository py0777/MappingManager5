package mm.common;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import mm.service.om.OmSqlTranInq;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.representation.Form;


@Path("restapi")
public class PreServiceHandler {
	
	static Logger logger = Logger.getLogger(PreServiceHandler.class);


	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	 public String getMessage(@QueryParam("SERVICE_NAME")String serviceName,@QueryParam("QUERY")String query,@QueryParam("COMMENT_YN")String comentYn,@QueryParam("SQLFORMAT_YN")String sqlFormatYn,MultivaluedMap<String, String> formParams ,@Context UriInfo uriInfo) throws Exception {
		 System.out.println("START getMessage1");
		 /*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		IDataSet requestData = new DataSet();
		JSONObject json = new JSONObject();
		JSONArray rsRtn = new JSONArray();
		JSONArray rsTblRtn = new JSONArray();
		
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		
		OmSqlTranInq omSqlTranInq = new OmSqlTranInq();
		
		try 
		{
			 if("SqlTran".equals(serviceName)) {
					logger.debug("########### SqlTran START #########");
					requestData.putField("QUERY", query);
					requestData.putField("COMMENT_YN", comentYn);
					requestData.putField("SQLFORMAT_YN", sqlFormatYn);
					logger.debug("requestData.getFieldMap()"+requestData.getField("QUERY"));
					
					//responseData= omSqlTranInq.omSqlTranInq(requestData);
					logger.debug("responseData.getFieldMap()"+responseData.getFieldMap());
					
			 }
			 
			for( Map.Entry<String, Object> entry : responseData.getFieldMap().entrySet() ) {	
				String key = entry.getKey();
				Object value = entry.getValue();
				json.put(key, value);
			}
			
			for(int i=0; i < Integer.parseInt(responseData.getField("rsCnt")); i++){
				
				rsRtn.put(responseData.getRecordSet("rsRtn").getRecordMap(i));
			}
			
			for(int i=0; i < Integer.parseInt(responseData.getField("rsTblCnt")); i++){
				
				rsTblRtn.put(responseData.getRecordSet("rsTblRtn").getRecordMap(i));
			}
			json.put("retrun_code", 0);
			json.put("rsRtn", rsRtn);
			json.put("rsTblRtn", rsTblRtn);
			dh.getSession().commit();
			
		}catch (Exception e) {
			json.put("retrun_code", -1);
			json.put("retrun_msg", e.getMessage());
			e.printStackTrace();
			dh.getSession().rollback();
			/*에러는 무시하고 롤백만 함.*/	
		} finally {
			dh.closeSession();
		}
		 
	      return String.valueOf(json);
	 }
	 
//	 @POST
//	 @Path("/validSql")
//	 @Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
//	 public String  getMessage(@QueryParam("TABLE_NAME") String tableName, @QueryParam("MAP_ID") String mapId) throws Exception {
//		 System.out.println("START getMessage2");
//		 /*************************************************************
//		 * Declare Var
//		 *************************************************************/
//		IDataSet responseData = new DataSet();
//		IDataSet requestData = new DataSet();
//		JSONObject json = new JSONObject();
//		//JSONArray rsTbl = new JSONArray();
//		JSONArray rsTblRtn = new JSONArray();
//		
//		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
//		
//		OmValidSql ovs = new OmValidSql();
//		
//		try 
//		{
//			 
//			logger.debug("########### SqlTran START #########");
//			requestData.putField("TABLE_NAME", tableName);
//			requestData.putFieldValues("MAP_ID", mapId.split(","));
//		
//			responseData= ovs.omValidSql(requestData);
//			logger.debug("responseData.getFieldMap()"+responseData.getFieldMap());
//	
//			 
//			for( Map.Entry<String, Object> entry : responseData.getFieldMap().entrySet() ) {	
//				String key = entry.getKey();
//				Object value = entry.getValue();
//				json.put(key, value);
//			}
//		
//			for(int i=0; i < Integer.parseInt(responseData.getField("rsTblCnt")); i++){
//				
//				rsTblRtn.put(responseData.getRecordSet("rsTblRtn").getRecordMap(i));
//			}
//			json.put("retrun_code", 0);
//			//json.put("rsTbl", rsTbl);
//			json.put("rsTblRtn", rsTblRtn);
//			dh.getSession().commit();
//			
//		}catch (Exception e) {
//			json.put("retrun_code", -1);
//			json.put("retrun_msg", e.getMessage());
//			e.printStackTrace();
//			dh.getSession().rollback();
//			/*에러는 무시하고 롤백만 함.*/	
//		} finally {
//			dh.closeSession();
//		}
//	    return String.valueOf(json);
//	 }
	 
//	 @POST
//	 @Path("/upDef")
//	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	 @Produces(MediaType.APPLICATION_JSON  + ";charset=UTF-8")
//	 public String  getMessageTest(   @FormParam("updatedRows") String updatedRows, @FormParam("createdRows") String createdRows,Form form,@Context HttpHeaders  headers) throws Exception {
//		 System.out.println("START getMessage2");
//		 /*************************************************************
//		 * Declare Var
//		 *************************************************************/
//		IDataSet responseData = new DataSet();
//		IDataSet requestData = new DataSet();
//		JSONObject json = new JSONObject();
//		//JSONArray rsTbl = new JSONArray();
//		JSONArray rsTblRtn = new JSONArray();
//		MultivaluedMap<String, String> conTextHeader = headers.getRequestHeaders();
//		logger.debug("queryParams -- "+ form.keySet());
//		
//		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
//		
//		OmUpDef oud = new OmUpDef();
//		
//		try 
//		{
//			 
//			
//			logger.debug("########### SqlTran START #########");
//				
//			logger.debug("ALL query parameters -- "+ form.getFirst("updatedRows"));
//			logger.debug("ALL query parameters -- "+ form.values());
//			//logger.debug("ALL query parameters -- "+ updatedRows.stringify());
//			
//			//requestData.putField("TABLE_NAME", tableName);
//		
//			responseData= oud.omUpDef(requestData);
//			logger.debug("responseData.getFieldMap()"+responseData.getFieldMap());
//	
//			 
//			for( Map.Entry<String, Object> entry : responseData.getFieldMap().entrySet() ) {	
//				String key = entry.getKey();
//				Object value = entry.getValue();
//				json.put(key, value);
//			}
//		
//			for(int i=0; i < Integer.parseInt(responseData.getField("rsTblCnt")); i++){
//				
//				rsTblRtn.put(responseData.getRecordSet("rsTblRtn").getRecordMap(i));
//			}
//			json.put("retrun_code", 0);
//			json.put("rsTblRtn", rsTblRtn);
//			
//			logger.debug("rsTblRtn:"+rsTblRtn);
//			dh.getSession().commit();
//			
//		}catch (Exception e) {
//			json.put("retrun_code", -1);
//			json.put("retrun_msg", e.getMessage());
//			e.printStackTrace();
//			dh.getSession().rollback();
//			/*에러는 무시하고 롤백만 함.*/	
//		} finally {
//			dh.closeSession();
//		}
//	    return String.valueOf(json);
//	 }
	 
//	 @POST
//	 @Path("/modifyData")
//	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	 @Produces(MediaType.APPLICATION_JSON  + ";charset=UTF-8")
//	 public String  modifyData(  @FormParam("updatedRows") String updatedRows, @FormParam("createdRows") String createdRows, @FormParam("deletedRows") String deletedRows,MultivaluedMap<String, String> formParams ,@Context UriInfo uriInfo) throws Exception {
//		 System.out.println("START modifyData");
//		 /*************************************************************
//		 * Declare Var
//		 *************************************************************/
//		IDataSet responseData = new DataSet();
//		IDataSet requestData = new DataSet();
//		JSONObject json = new JSONObject();
//		
//		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
//		OmUpDef oud = new OmUpDef();
//		try 
//		{			
//			logger.debug("########### modifyData START #########");
//			MultivaluedMap<String, String> params = formParams;
//		    Map<String,String> parameters = new HashMap<String,String>();
//
//			JSONArray allJsonArray = null;
//			logger.debug("queryParams -- "+ params.size());
//			logger.debug("queryParams -- "+ params.toString());
//			
//			logger.debug("queryParams -- "+ params.entrySet());
//			logger.debug("queryParams -- "+ params.entrySet().iterator().next());
//			for(String str : params.keySet()){
//			     parameters.put(str, params.getFirst(str));
//			     logger.debug("key -- "+ str);
//			     logger.debug("value -- "+ parameters.get(str));
//			}
//			
//			logger.debug("parameters -- "+ parameters);
//			
//			
//			logger.debug("updatedRows -- "+ updatedRows);
//			logger.debug("createdRows -- "+ createdRows);
//			logger.debug("deletedRows -- "+ deletedRows);
//			//logger.debug("allJsonArray -- "+ new JSONArray(params.toString()));
//			requestData.putField("C", createdRows);
//			requestData.putField("U", updatedRows);
//			requestData.putField("D", deletedRows);
//			
////			responseData= oud.omModifyData(requestData);
//			
//			dh.getSession().commit();
//		}catch (Exception e) {
//			json.put("retrun_code", -1);
//			json.put("retrun_msg", e.getMessage());
//			e.printStackTrace();
//			dh.getSession().rollback();
//			/*에러는 무시하고 롤백만 함.*/	
//		} finally {
//			dh.closeSession();
//		}
//	    return String.valueOf(json);
//	 }
	 
	 @POST
	 @Path("/preServ")
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	 public String  preServ( @FormParam("updatedRows") String updatedRows, MultivaluedMap<String, Object> formParams ,Form form, @Context UriInfo uriInfo, @Context HttpHeaders  headers) throws Exception {
		 System.out.println("START preServ");
		 /*************************************************************
		 * Declare Var
		 *************************************************************/
		 JSONObject json = new JSONObject();
		 
		DaoHandler dh = new DaoHandler();  /*DAO Handler*/
		ServiceHandler srh = new ServiceHandler();
		JsonParser     jp  = new JsonParser();
		IDataSet result = null;
		try 
		{			
			logger.debug("########### preServ START #########");
			MultivaluedMap<String, String> conTextHeader = headers.getRequestHeaders();


			logger.debug("Request -- "+ updatedRows);
			logger.debug("conTextHeader -- "+ conTextHeader.toString());
			logger.debug("params -- "+ form.values().size());
			logger.debug("params -- "+ form.values());
			logger.debug("params -- "+ form.keySet());
			logger.debug("params -- "+ form.entrySet());
			logger.debug("params -- "+ formParams.toString());

			
			result =srh.serviceHandler(conTextHeader,  form);
			
			json = jp.processDataSet(result);
			json.put("retrun_code", 200);
			
			dh.getSession().commit();
		}catch (Exception e) {
			e.printStackTrace();
			dh.getSession().rollback();
			/*에러는 무시하고 롤백만 함.*/	
		} finally {
			dh.closeSession();
		}
	    return String.valueOf(json);
	 }
}
