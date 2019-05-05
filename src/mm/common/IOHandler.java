package mm.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.sun.jersey.api.representation.Form;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.util.StringUtils;

public class IOHandler {
	Logger logger = Logger.getLogger(this.getClass());
	private IRecordSet rs;
	private String[] headerNm;
	private String recordSetId = "S1";
	IRecord record = null;
	
	public IDataSet ioHandler(Form params) {
		
		/*************************************************************
		 * Declare Var
		 *************************************************************/
		IDataSet responseData = new DataSet();
		Map<String,String> parameters = new HashMap<String,String>();
		//HashMap<String, List<String>> hsm  = params;
		Set<String> rsHeader = new HashSet<>();
		Set<String> rsNm = new HashSet<>();
		
		//logger.debug("hsm -- "+ hsm);
		/*Field 값과 RecordSet Name 설정*/
		for(String str : params.keySet()){
			
			String vStr = StringUtils.replaceAll(str, "[", "~~");
			vStr = StringUtils.replaceAll(vStr, "]", "");
			vStr = StringUtils.trim(vStr);
			
			String [] list = vStr.split("~~");
			logger.debug("vStr -- "+vStr+" "  + list.length);
			if(list.length == 1){
				responseData.putField(str, params.getFirst(str));
			}else if(list.length > 1){
				rsNm.add(list[0]);
			}
			
			logger.debug("key1 -- "+ str);
		}
		/*Header Name 설정*/
		Iterator<String> rsNmIter = rsNm.iterator();
		while(rsNmIter.hasNext()){
			int rsCnt = 0;
			int tmpCnt = 0;
			int tmp1 = 0;
			String strRsNm = rsNmIter.next();
			
			for(String str : params.keySet()){
				
				
				String vStr = StringUtils.replaceAll(str, "[", "~~");
				vStr = StringUtils.replaceAll(vStr, "]", "");
				vStr = StringUtils.trim(vStr);
				String [] list = vStr.split("~~");
				logger.debug("vStr -- "+vStr+" "  + list.length);
				
				if(strRsNm.equals(list[0])){
					rsHeader.add(list[2]); /*header 값 설정*/
					
					tmpCnt = Integer.parseInt(list[1]);
					if(tmpCnt > rsCnt){
						rsCnt = tmpCnt;
					}
					//responseData.putField(str, params.getFirst(str));
				}
			}
			
			headerNm = new String[rsHeader.size()];
			Iterator<String> rsHeaderIter = rsHeader.iterator();
			while(rsHeaderIter.hasNext()){
				headerNm[tmp1++] = rsHeaderIter.next();
			}
			rs = new RecordSet(strRsNm, headerNm);
			
			for(int i = 0 ; i < rsCnt ; i++){
				for (int j = 0; j < headerNm.length; j++) {
					//strRsNm+"["+i+"]"+[+headerNm[i]+]
				}
			}
			
			for(int i = 0 ; i <= rsCnt ; i++){
				record = rs.newRecord();
			
				for(String str : params.keySet()){
					for( int j=0; j < headerNm.length ; j++)
					if(str.equals(strRsNm+"["+i+"]["+headerNm[j]+"]")){
						record.set(headerNm[j], params.getFirst(str));
						logger.debug("str -- "+ str);
					}
										
				}
				rs.addRecord(record);
			}
			
            logger.debug("iter.next() -- "+ rs);
            responseData.putRecordSet(rs);
        }
		
		
		/*KEY MAP 스타일을 resultSet으로 바꾸는 방법 연구*/
		return responseData;
		
	}
}
