package com.consultinggroup.login;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.consultinggroup.tools.IP;


import android.R.integer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceActivity.Header;
import android.util.JsonReader;
import android.util.Log;
import android.util.PrintStreamPrinter;
import android.widget.TextView;

public class serviceImp{
	
		

	public void getLesson(String name, String pwd, String check,String session)
			throws Exception {
		String uri="http://"+IP.ip+"/LessonForm/GetLesson";
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(uri);
		
		NameValuePair namePair=new BasicNameValuePair("studentNum", name);
		NameValuePair pwdPair=new BasicNameValuePair("pwd",pwd);
		NameValuePair checkPair = new BasicNameValuePair("check",check);
		NameValuePair sessionPair=new BasicNameValuePair("session", session);
		List<NameValuePair> paramas=new ArrayList<NameValuePair>();
		paramas.add(namePair);
		paramas.add(pwdPair);
		paramas.add(checkPair);
		paramas.add(sessionPair);
		post.setEntity(new UrlEncodedFormEntity(paramas,"gb2312"));
		HttpResponse response=client.execute(post);
		
		int status = response.getStatusLine().getStatusCode();
		
		if(status==200){
		
		String result=EntityUtils.toString(response.getEntity(),"gb2312");
		try {
			File sd=Environment.getExternalStorageDirectory();
			File file=new File(sd.getPath()+"/øŒÃ√ ±º‰");
			if(!file.exists()){
				file.mkdir();
			}
			File targetFile=new File(file,"lesson.txt");
			System.out.println(sd.getPath());
			FileOutputStream fos=new FileOutputStream(targetFile);
			PrintStream ps=new PrintStream(fos);
			ps.print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		JSONTokener jsonParse=new JSONTokener(result);
		
		JSONArray courseArray=(JSONArray)jsonParse.nextValue();
		JSONObject checkJsonObject=courseArray.getJSONObject(courseArray.length()-1);

		boolean checkCode=checkJsonObject.getBoolean("check");
		if(!checkCode){
			throw new Exception("fail lesson");
		}	
		}
	}
}
