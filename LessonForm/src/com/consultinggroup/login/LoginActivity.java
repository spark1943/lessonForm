package com.consultinggroup.login;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.main.MainActivity;
import com.consultinggroup.tools.ExitApplication;
import com.consultinggroup.tools.IP;
import com.consultinggroup.tools.MyhttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private ImageView back,codePic;
	private Button login;
	private Intent intent;
	private Dialog dialog;
	private Bitmap bitmap;
	private EditText studentNum,pwd,confirm;
    private static final int success_getCodePic=1;
    private static final int success_getLesson=2;
    private static final int success_saveInfo=3;
    private static final int CHECKCODE_GEN_SUCCESS=4;
    private static final int fail_getLesson=5;
    private String studentNumString,pwdString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		back = (ImageView) findViewById(R.id.back);
		codePic=(ImageView) findViewById(R.id.codePic);
		codePic.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetCodePic().start();
			}
		});
		login = (Button) findViewById(R.id.login);
		pwd=(EditText) findViewById(R.id.password);
		studentNum=(EditText) findViewById(R.id.studentNum);
		confirm=(EditText) findViewById(R.id.confirm);
		back.setOnClickListener(this);
		login.setOnClickListener(this);
		ExitApplication.getInstance().addActivity(this);
		new GetCodePic().start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
            if(msg.what==success_getLesson){
            	dialog.dismiss();
            	SharedPreferences sp = getSharedPreferences("studentInfo", 0);
            	Editor editor = sp.edit();
            	editor.putString("id", id);
            	editor.putString("studentNum", studentNumString);
            	editor.putString("pwd", pwdString);
            	editor.putString("nickName", "匿名");
            	editor.putString("schoolName", SelectData.school);
            	editor.putString("institute", SelectData.institute);
            	editor.putString("comeTime", SelectData.year);
            	editor.putString("gender","");
            	editor.putString("email", "");
            	editor.putString("like", "");
            	editor.commit();
            	Toast.makeText(getApplicationContext(), "导入成功", 0).show();
            	intent = new Intent(LoginActivity.this, MainActivity.class);
            	startActivity(intent);
            	overridePendingTransition(R.anim.scale_rotate,
            			R.anim.my_alpha_action);
			}else if (msg.what==CHECKCODE_GEN_SUCCESS) {
				codePic.setImageBitmap(bitmap);
			}else if (msg.what==fail_getLesson) {
				Toast.makeText(getApplicationContext(), "输入信息错误", 0).show();
				dialog.dismiss();
			}

		};
	};
	public String id;
	public String session;
	private String check;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		case R.id.login:
			studentNumString=studentNum.getText().toString().trim();
			pwdString=pwd.getText().toString().trim();
			check=confirm.getText().toString().trim();
			if(!studentNumString.equals("")&&!pwdString.equals("")&&!check.equals("")){
			dialog = new MyDialog(this).loadLesson();
			new SaveStudentInfo().start();
			}else {
				Toast.makeText(this, "请填入完整信息", 0).show();
			}
			break;
		default:
		}

	}
    class SaveStudentInfo extends Thread{ 
    	@Override
    	public void run() {
    		String urlStr = "http://"+IP.ip+"/LessonForm/StudentInfo";
			HttpPost request = new HttpPost(urlStr);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("studentNum", studentNumString));
			params.add(new BasicNameValuePair("pwd", pwdString));
			params.add(new BasicNameValuePair("schoolName", SelectData.school));
			params.add(new BasicNameValuePair("institute", SelectData.institute));
			params.add(new BasicNameValuePair("comeTime", SelectData.year));
			try {
				// 设置请求参数项
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient client =new MyhttpClient().getHttpClient();
				// 执行请求返回相应
				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
					 id = EntityUtils.toString(response
							.getEntity());
					 SelectData.id=id;
//					handler.sendEmptyMessage(success_saveInfo);
					 new GetClass().start();
				}
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    }
    }
    
	class GetClass extends Thread{
		@Override
		public void run() {
			try {
				new serviceImp().getLesson(studentNumString, pwdString, check, session);
				handler.sendEmptyMessage(success_getLesson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				handler.sendEmptyMessage(fail_getLesson);
			}
	}
	}
	class GetCodePic extends Thread {
		@Override
		public void run() {
			URL url;
			try {
				url = new URL("http://210.42.121.132/servlet/GenImg");
			URLConnection con = url.openConnection();
			session = con.getHeaderField("Set-Cookie").split(";")[0];
			bitmap = BitmapFactory.decodeStream(con.getInputStream());
			handler.sendEmptyMessage(CHECKCODE_GEN_SUCCESS);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
