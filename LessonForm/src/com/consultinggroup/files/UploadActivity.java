package com.consultinggroup.files;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.login.MyDialog;
import com.consultinggroup.tools.ExitApplication;
import com.consultinggroup.tools.IP;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends Activity implements OnClickListener {

	private Button selectFile;
	private EditText content, filePath;
	private TextView send;
	private String path = "";
	private String nickName;
	private Dialog dialog;
	private static final int dismiss_dialog = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		selectFile = (Button) findViewById(R.id.selectFile);
		send = (TextView) findViewById(R.id.send);
		send.setOnClickListener(this);
		content = (EditText) findViewById(R.id.text);
		filePath = (EditText) findViewById(R.id.filePath);
		selectFile.setOnClickListener(this);
		nickName = getSharedPreferences("studentInfo", 0).getString("nickName",
				"");
		ExitApplication.getInstance().addActivity(this);
	}

	public void Return(View v) {
		finish();
		overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selectFile:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, 1);
			break;
		case R.id.send:
			final String data = content.getText().toString().trim();
			if (data.equals("")) {
				Toast.makeText(this, "请描述一下要上传的资料", 0).show();
			} else {
				if (!path.equals("")) {
					dialog = new MyDialog(this).loaduploadFile();
					new Thread(new Runnable() {

						public void run() {
							uploadFile(filePath.getText().toString(), data);
						}
					}).start();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1&&data!=null) {
			path = data.toString();
			path = path.substring(path.indexOf("///") + 2, path.length() - 2);
			filePath.setText(path);
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void uploadFile(String filePath, String content) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			String newName = new Date().getTime()+""+path.substring(path.lastIndexOf("."),
					path.length());
			String actionUrl = "http://"+IP.ip+"/LessonForm/acceptFiles";
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "utf-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + newName + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(filePath);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */
			// showDialog("上传成功" + b.toString().trim());
			if (b.toString().equals("success")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String time = df.format(new Date());
				if (!content.equals("")) {
					// 上传文字 time name content
					new upWords(nickName, content, time, newName).start();
					// Toast.makeText(getApplicationContext(), "发表成功",
					// Toast.LENGTH_SHORT).show();
					// finish();
				}
			} else {
				Toast.makeText(getApplicationContext(), "上传失败",
						Toast.LENGTH_SHORT).show();
			}
			/* 关闭DataOutputStream */
			ds.close();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT)
					.show();
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == dismiss_dialog) {
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "上传成功",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		};
	};

	class upWords extends Thread {
		// id content time
		private String name;
		private String fileName;
		private String content;
		private String time;

		public upWords(String name, String content, String time, String fileName) {
			this.name = name;
			this.content = content;
			this.time = time;
			this.fileName = fileName;
		}

		@Override
		public void run() {
			String Url = "http://"+IP.ip+"/LessonForm/saveFile";
			HttpPost request = new HttpPost(Url);
			// 如果传递参数多的话，可以丢传递的参数进行封装
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 添加邮箱和密码
			params.add(new BasicNameValuePair("nickName", name + ""));
			params.add(new BasicNameValuePair("describe", content));
			params.add(new BasicNameValuePair("time", time));
			params.add(new BasicNameValuePair("linkPath", fileName));
			try {
				// 设置请求参数项
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient client = new DefaultHttpClient();
				client.execute(request);
				handler.sendEmptyMessage(dismiss_dialog);
				// 执行请求返回相应
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void send(View v) {

	}
}
