package com.atguigu.crm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class DataProcessUtils {
	
	/**
	 * 将页面上传过来的请求参数，再次进行封装，并且返回到请求地址的的参数中
	 * @Desprition:TODO
	 * @param params
	 * @return String
	 * @exception
	 * @author:histjxg
	 * @Time:2016年5月15日上午11:20:26
	 *
	 */
	public static String parseRequestParams2QueryString(Map<String, Object> params) {
		StringBuilder result = new StringBuilder("");
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			String key = entry.getKey();
			Object val = entry.getValue();
			
			if(val == null || val.toString().trim().equals("")){
				continue;
			}
			
			result.append("search_")
			      .append(key)
			      .append("=")
			      .append(val)
			      .append("&");
		}
		
		if(result.length() > 1){
			result = result.replace(result.length()-1, result.length(), "");
		}
		
		return result.toString();
	}
	
	/**
	 * 将页面传过来的参数封装被数据库理解的参数
	 * @Desprition:TODO
	 * @param requestParams
	 * @param dateFormat
	 * @return
	 * @throws ParseException Map<String,Object>
	 * @exception
	 * @author:histjxg
	 * @Time:2016年5月15日上午11:21:20
	 *
	 */
	public static Map<String, Object> parseRequestParams2MyBatisParams(
			Map<String, Object> requestParams,DateFormat dateFormat) throws ParseException {
		Map<String, Object> result = new HashMap<>();
		
		for(Map.Entry<String, Object> entry: requestParams.entrySet()){
			String key = entry.getKey();
			Object val = entry.getValue();
			
			if(key.startsWith("D_")){
				key = key.substring(2);
				val = dateFormat.parse((String)val);
			}
			if(key.startsWith("LIKE_")){
				key = key.substring(5);
				val = "%" + val + "%";
			}
			
			result.put(key, val);
		}
		
		return result;
	}

}
