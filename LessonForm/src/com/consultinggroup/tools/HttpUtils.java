package com.consultinggroup.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.conn.params.ConnConnectionParamBean;

import android.R.integer;

public class HttpUtils {

	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}
	public static String getJsonString(String path){
		try {
			URL url=new URL(path);
			HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(3000);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoInput(true);
			int code=httpURLConnection.getResponseCode();
			if(code==200){
				return changeInputString(httpURLConnection.getInputStream());
			}
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	private static String changeInputString(InputStream inputStream) {
		// TODO Auto-generated method stub
		String jsonString=null;
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		int len=0;
		byte[] data=new byte[1024];
		try {
			while((len=inputStream.read(data))!=-1){
				outputStream.write(data, 0, len);
			}
			jsonString=new String(outputStream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

}
