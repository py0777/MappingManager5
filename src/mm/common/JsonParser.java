package mm.common;

import java.util.Iterator;

import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IRecordSet;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class JsonParser {

	static Logger logger = Logger.getLogger(JsonParser.class);
	public JSONObject processDataSet(IDataSet resultDataSet) throws Exception  {
		JSONObject dataSetObj = new JSONObject();
		
		IRecordSet recordSet = null; 
		logger.debug("☆☆☆☆☆☆☆☆☆ processDataSet Start"+resultDataSet);
		try 
		{
			Iterator keyIter =resultDataSet.getFieldKeys();
			while(keyIter.hasNext()){
				
				String fieldKey =  (String)keyIter.next();
				logger.debug("☆☆☆☆☆☆☆☆☆:"+fieldKey);
				dataSetObj.put(fieldKey, resultDataSet.getField(fieldKey));
			}
			
			
			Iterator rsKeyIter =resultDataSet.getRecordSetIds();
			while(rsKeyIter.hasNext()){
				JSONArray recordSetListObj = new JSONArray();
				String recordSetId = (String)rsKeyIter.next();
				int recordCnt = resultDataSet.getRecordSet(recordSetId).getRecordCount();
				
				recordSet = resultDataSet.getRecordSet(recordSetId);
				logger.debug("recordSet:"+recordSetId);		
				logger.debug("recordCnt:"+recordCnt);		
				for (int i = 0; i < recordCnt; ++i) {
					recordSetListObj.put(recordSet.getRecordMap(i));

					dataSetObj.put(recordSetId, recordSetListObj);
				}
			}
			logger.debug("dataSetObj2" + dataSetObj);
		}catch (Exception e) {
			dataSetObj.put("retrun_code", -1);
			dataSetObj.put("retrun_msg", e.getMessage());
			e.printStackTrace();
			
		}
		return dataSetObj;
		
	}
}
