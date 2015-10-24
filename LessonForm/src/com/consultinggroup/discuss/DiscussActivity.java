package com.consultinggroup.discuss;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import com.baidu.a.a.a.a.b;
import com.baidu.location.l;
import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.main.Listitem;
import com.consultinggroup.main.MainActivity;
import com.consultinggroup.person.Student;
import com.consultinggroup.tools.HttpUtils;
import com.consultinggroup.tools.IP;
import com.consultinggroup.tools.jsonTools;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DiscussActivity extends Activity implements OnClickListener{
    private ImageView back;
    private String name,id,nickName;
	private Button mBtnSend;// 发送btn
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private Bitmap bitmap1,bitmap2;
	// 定义出IP地址和端口号
		final String IP_ADDRESS = IP.ip;
		final int PORT = 33333;
		private Socket s;
		protected BufferedReader in;
		protected PrintWriter out;
		private String message;
		public List<sendMsg> content=new ArrayList<sendMsg>();
		private String lessonName;
		public String jsonString;
		// 定义Han()对象
		private static final int APPEND_MSG = 1;// Handler 状态常量: 更新收到的消息
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);
		back=(ImageView) findViewById(R.id.back);
		mBtnSend=(Button) findViewById(R.id.btn_send);
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		back.setOnClickListener(this);
		mBtnSend.setOnClickListener(this);
		initData();
		new GetsendMsg().start();
		connectServer();
	}
	
	
	class GetsendMsg extends Thread{
			String url = "http://"+IP.ip+"/LessonForm/GetsendMsg";
			@Override
			public void run() {
				synchronized (DiscussActivity.this.getClass()) {
					HttpPost request = new HttpPost(url);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("lessonName", lessonName));
					try {
						request.setEntity(new UrlEncodedFormEntity(params,
								HTTP.UTF_8));
						HttpClient client = new DefaultHttpClient();
						HttpResponse response = client.execute(request);
						if (response.getStatusLine().getStatusCode() == 200) {
							jsonString = EntityUtils.toString(response.getEntity());
							// saveObject(jsonString, "dianzanMsgID");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					content.clear();
					content = jsonTools.getsendMsg("sendMsg",
							jsonString);
					for (int i = 0; i<content.size(); i++) {
						        if(content.get(i).getId().equals(id)){
						        	content.get(i).setBitmap(bitmap1);
						        	content.get(i).setCome(false);
						        }else {
						        	content.get(i).setBitmap(bitmap2);
								}
						}
					System.out.println(content.toString()+"jjjj");
					handler.sendEmptyMessage(2);
				}
					}
	}
	public void initData() {
		lessonName=getIntent().getStringExtra("lessonName");
        id=getSharedPreferences("studentInfo", 0).getString("id", "");
        nickName=getSharedPreferences("studentInfo", 0).getString("nickName", "");
		File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/课堂时间/"+id+".jpg");
		if (file.exists()) {
			bitmap1=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/课堂时间/"+id+".jpg");
		}else {
			bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.face);
		}
		bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			break;
		case R.id.btn_send:
			message = mEditTextContent.getText().toString();
			if (message.length() > 0) {
			mEditTextContent.setText("");
				new Thread(){
					public void run() {
						out.println(id+"/"+message+"*"+nickName+"?"+getDate()+"&"+lessonName);
						out.flush();
					};
				}.start();
			}
				break;
		default:
			break;
		}
	}
	
	
	private void connectServer() {
		new Thread(){
			public void run() {
				synchronized (DiscussActivity.this.getClass()) {
				try {
					String ip = IP_ADDRESS;
					String name = nickName;
					
					s = new Socket(ip, PORT);
					in = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
					out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
					//首先发送自己的昵称
					out.println(name+"?"+lessonName);
					out.flush();
					//然后循环接收聊天室的内容
//					while (true) {
//						
					String line="";
					while((line = in.readLine()) != null) {
						Message.obtain(handler, APPEND_MSG, line).sendToTarget();
					}
//					}
				} catch (Exception e) {
					System.out.println("聊天时异常..");
				}
				}
			};
		}.start();
		
	}
	

	// Handler 接收线程消息,并更新UI
		Handler	handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch(msg.what) {
					case APPEND_MSG:
						//新消息
						final String msgString=(String) msg.obj;
						if(msgString.contains("*")){
							String id=msgString.substring(0, msgString.indexOf("/"));
							String message=msgString.substring(msgString.indexOf("/")+1, msgString.indexOf("*"));
							String nickName=msgString.substring(msgString.indexOf("*")+1, msgString.indexOf("?"));
							String time=msgString.substring(msgString.indexOf("?")+1,msgString.indexOf("&"));
							boolean isCome=true;
							if(id.equals(DiscussActivity.this.id)){
								isCome=false;
								content.add(new sendMsg(id,message,time,nickName,bitmap1, isCome));
							}else {
								content.add(new sendMsg(id,message,time,nickName,bitmap2, isCome));
							}
						    mAdapter.notifyDataSetChanged();
						    mListView.setSelection(mListView.getCount() - 1);
						
						}else{
							if(msgString.contains("?")){
							String message=msgString.substring(msgString.indexOf("?")+1);
							String name=msgString.substring(0, msgString.indexOf("?"));
							if(message.equals(lessonName)){
								Toast.makeText(getApplicationContext(),name+"进入了讨论组", 0).show();
							}
							}
						}
						break;
					case 2:
						mAdapter = new ChatMsgViewAdapter(DiscussActivity.this, content);
						mListView.setAdapter(mAdapter);
						break;
					default:
						break;
					}
					
				}
			};
	
	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		if(!s.isClosed()){
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
