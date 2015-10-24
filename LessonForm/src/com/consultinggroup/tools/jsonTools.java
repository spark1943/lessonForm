package com.consultinggroup.tools;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.consultinggroup.discuss.message;
import com.consultinggroup.discuss.sendMsg;
import com.consultinggroup.main.FileItem;
import com.consultinggroup.person.Student;

import android.R.integer;

public class jsonTools {

	public jsonTools() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> getSchoolName(String key, String jsonString) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				String name = (String) jsonArray.get(i);
				list.add(name);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<Student> getStudents(String key, String jsonString) {
		ArrayList<Student> list = new ArrayList<Student>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Student person = new Student();
				person.setId(jsonObject2.getString("id"));
				person.setMsgID(jsonObject2.getInt("msgID"));
				person.setNickName(jsonObject2.getString("nickName"));
				person.setTime(jsonObject2.getString("time"));
				person.setMessage(jsonObject2.getString("message"));
				person.setFigure(jsonObject2.getString("figure"));
				person.setDianzanNum(jsonObject2.getInt("dianzanNum"));
				person.setPinglunNum(jsonObject2.getInt("pinglunNum"));
				person.setPicture(jsonObject2.getString("picture"));
				list.add(person);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public static ArrayList<message> getGetliuyan(String key, String jsonString) {
		ArrayList<message> list = new ArrayList<message>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				message person = new message();
				person.setId(jsonObject2.getInt("id"));
				person.setUserID(jsonObject2.getString("userID"));
				person.setName(jsonObject2.getString("name"));
				person.setTime(jsonObject2.getString("time"));
				person.setLessonName(jsonObject2.getString("lessonName"));
				person.setContent(jsonObject2.getString("content"));
				list.add(person);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public static ArrayList<message> getFuifus(String key, String jsonString) {
		ArrayList<message> list = new ArrayList<message>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				message person = new message();
				person.setName(jsonObject2.getString("name"));
				person.setUserID(jsonObject2.getString("userID"));
				person.setName(jsonObject2.getString("name"));
				person.setTime(jsonObject2.getString("time"));
				person.setContent(jsonObject2.getString("content"));
				list.add(person);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<FileItem> getFiles(String key, String jsonString) {
		ArrayList<FileItem> list = new ArrayList<FileItem>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				FileItem person = new FileItem();
				person.setFileID(jsonObject2.getInt("fileID"));
				person.setNickName(jsonObject2.getString("nickName"));
				person.setTime(jsonObject2.getString("time"));
				person.setDescribe(jsonObject2.getString("describe"));
				person.setLinkPath(jsonObject2.getString("linkPath"));
				person.setTimes(Integer.parseInt(jsonObject2.getString("times")));
				list.add(person);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public static List<Integer> getDianzanMsgID(String key, String jsonString) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				Integer id = (Integer) jsonArray.get(i);
				list.add(id);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		return list;
	}

	public static List<PinglunEnity> getPinglunEnitys(String key,
			String jsonString) {
		List<PinglunEnity> list = new ArrayList<PinglunEnity>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				PinglunEnity pinglunEnity = new PinglunEnity();
				pinglunEnity.setUsername(jsonObject2.getString("username"));
				pinglunEnity.setFigure(jsonObject2.getString("figure"));
				pinglunEnity.setPinglunWords(jsonObject2
						.getString("pinglunWords"));
				pinglunEnity.setPinglunTime(jsonObject2
						.getString("pinglunTime"));
				list.add(pinglunEnity);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<sendMsg> getsendMsg(String key,
			String jsonString) {
		ArrayList<sendMsg> list = new ArrayList<sendMsg>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				sendMsg enity = new sendMsg();
				enity.setId(jsonObject2.getString("id"));
				enity.setNickName(jsonObject2.getString("nickName"));
				enity.setMessage(jsonObject2
						.getString("message"));
				enity.setTime(jsonObject2
						.getString("time"));
				list.add(enity);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public static String createJsonString(String key,Object value)
    {
    	JSONObject jsonObject=new JSONObject();
    	try {
			jsonObject.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return jsonObject.toString();
    }
}
