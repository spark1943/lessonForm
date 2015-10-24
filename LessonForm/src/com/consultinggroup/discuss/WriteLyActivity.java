package com.consultinggroup.discuss;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.tools.IP;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteLyActivity extends Activity implements OnClickListener{
	private TextView send;
	private String idName;
	private EditText text;
	private String content;
	private String time;
	private String lessonName;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_ly);
		lessonName=getIntent().getStringExtra("lessonName");
		send=(TextView) findViewById(R.id.send);
		send.setOnClickListener(this);
		text=(EditText) findViewById(R.id.text);
		idName=getSharedPreferences("studentInfo", 0).getString("id", "");
	    name=getSharedPreferences("studentInfo", 0).getString("nickName", "");
	}
	public void onClick(View v) {
		content = text.getText().toString();
		if (content.trim().equals("")) {
			Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
		} else {
			// 设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			time = df.format(new Date());
			sendMyMessage(content);
		}
		
	}
	
	public boolean sendMyMessage(final String message) {
		new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				send(message);
				Looper.loop();
			}
		}).start();

		return true;
	}
	
	
	private void send(String message) {
		try {
			String response = "";
			String path = "http://"+IP.ip+"/LessonForm/message.jsp";
			int data = 0;
			URL url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setInstanceFollowRedirects(true);
			httpURLConnection.addRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			DataOutputStream out = new DataOutputStream(
					httpURLConnection.getOutputStream());
			String param = "id=" + URLEncoder.encode(idName, "utf-8")
					+"&name=" + URLEncoder.encode(name, "utf-8")+ "&content=" + URLEncoder.encode(message, "utf-8")
					+ "&time=" + URLEncoder.encode(time, "utf-8")+ "&lessonName=" + URLEncoder.encode(lessonName, "utf-8");
			out.writeBytes(param);
			out.flush();
			out.close();
			if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) {
				InputStream inputStream = httpURLConnection.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				response = bufferedReader.readLine();
				Toast.makeText(getApplicationContext(), "发表成功",
						Toast.LENGTH_SHORT).show();
				inputStreamReader.close();
				inputStream.close();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "发表失败",
						Toast.LENGTH_SHORT).show();
			}
			httpURLConnection.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
	
	public void Return(View v){
		finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

}
