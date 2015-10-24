package com.lessonForm.json;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lessonForm.javabean.sendMsg;


public class JsonTools {

	public JsonTools() {
	}
    public static String createJsonString(String key,Object value)
    {
    	JSONObject jsonObject=new JSONObject();
    	jsonObject.put(key, value);
    	return jsonObject.toString();
    }
    
    
}
