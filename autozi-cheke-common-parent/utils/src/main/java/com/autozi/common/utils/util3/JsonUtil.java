package com.autozi.common.utils.util3;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	public static String toJson(Object o){
		
		if(o instanceof Map){
			return toJson((Map)o);
		}else if(o instanceof List){
			return toJson((List)o);
		}else{
			return null;
			
		}
	}
	public static String toJson(Map map){
		if(map.isEmpty()){
			return "{}";
		}
		StringBuffer sb=new StringBuffer("{");
		for(Iterator iterator=map.keySet().iterator();iterator.hasNext();){
			String key=(String)iterator.next();
			
			Object object=map.get(key);
			if (object instanceof String) {
				sb.append("\"").append(key).append("\"").append(":\"").append(object).append("\",");
			}else if(object instanceof Map) {
				sb.append("\"").append(key).append("\"").append(":").append(toJson((Map)object)).append(",");
			}else if(object instanceof List) {
				sb.append("\"").append(key).append("\":").append(toJson((List)object)).append(",");
			}else if(object instanceof Boolean){
				sb.append("\"").append(key).append("\"").append(":\"").append(object).append("\",");
			}else if(object instanceof Long){
				sb.append("\"").append(key).append("\"").append(":\"").append(object).append("\",");
			}else if(object instanceof Double){
				sb.append("\"").append(key).append("\"").append(":\"").append(object).append("\",");
			}
		}
		sb.deleteCharAt(sb.length()-1).append("}");
		return sb.toString().replaceAll("\r", "").replaceAll("\n", "");
	}
	public static String toJson(List list){
		if(list.isEmpty()){
			return "[]";
		}
        StringBuffer sb = new StringBuffer("[");
        for (Iterator iterator = list.iterator(); iterator
				.hasNext();) {
			Object object = (Object) iterator.next();
			if (object instanceof Map) {
				Map dataMap = (Map) object;
				sb.append(toJson(dataMap)).append(",");
			}else if(object instanceof String){
				sb.append("\"").append(object).append("\"").append(",");
			}
			

		}
       
		sb.deleteCharAt(sb.length() - 1).append("]");
		return sb.toString();
	}
	public static String beantoJson(Object bean,Class objclass){
		JsonConfig jsonConfig = new JsonConfig();    
		jsonConfig.setRootClass(objclass);    
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(bean,jsonConfig );
		String json=jsonObject.toString();
		
		return json;
	}
	public static Object jsontobean(String json,Class objclass,Map<String, Class> map){
		Object obj=null;
		JSONObject js=JSONObject.fromObject(json);
		if(js!=null){ 
          if(map!=null&&!map.isEmpty())
			obj=JSONObject.toBean(js, objclass, map);
          else
        	  obj=JSONObject.toBean(js, objclass);  
		}
		
		return obj;
	}
	
}
