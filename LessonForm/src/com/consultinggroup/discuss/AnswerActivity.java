package com.consultinggroup.discuss;

import java.io.File;
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

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.discuss.AnswerActivity.AnswerAdapter;
import com.consultinggroup.main.CustomListView;
import com.consultinggroup.pinglun.CommentActivity;
import com.consultinggroup.tools.CircleImageView;
import com.consultinggroup.tools.IP;
import com.consultinggroup.tools.PinglunEnity;
import com.consultinggroup.tools.jsonTools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerActivity extends Activity implements OnClickListener{

	private CircleImageView face;
	public TextView name;
	public EditText message;
	public TextView time;
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private String idName;
	private ImageView back;
	private ListView word;
	private ArrayList<message> list=new ArrayList<message>();
	private int id;
	private String nickName;
	protected AnswerAdapter adapter;
	protected String jsonString;
	private TextView content;
	private String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer);
		face = (CircleImageView)findViewById(R.id.face);
		back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		name = (TextView)findViewById(R.id.name);
		content = (TextView)findViewById(R.id.content);
		message = (EditText)findViewById(R.id.message);
		time = (TextView)findViewById(R.id.publish);
		word=(ListView) findViewById(R.id.word);
		initData();
		
		new Thread(new Runnable() {
			public void run() {
				String url = "http://"+IP.ip+"/LessonForm/GetFuifu";
				HttpPost request = new HttpPost(url);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", id + ""));
				try {
					request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
					HttpClient client = new DefaultHttpClient();
					client.execute(request);
					HttpResponse response = client.execute(request);
					if (response.getStatusLine().getStatusCode() == 200) {
						jsonString = EntityUtils.toString(response.getEntity());
						list = jsonTools.getFuifus("Fuifus",
								jsonString);
						handler.sendEmptyMessage(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void initData() {
		idName=getSharedPreferences("studentInfo", 0).getString("id", "");
         name.setText(MessageActivity.message.getName());
         content.setText(MessageActivity.message.getContent());
         time.setText(MessageActivity.message.getTime());
         id=MessageActivity.message.getId();
         userID=MessageActivity.message.getUserID();
         nickName=getSharedPreferences("studentInfo", 0).getString("nickName", "");
         File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/课堂时间/"+idName+".jpg");
 		if (file.exists()) {
 			bitmap1=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/课堂时间/"+idName+".jpg");
 		}else {
 			bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.face);
 		}
 		bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.icon);
 		if(idName.equals(userID)){
 		face.setImageBitmap(bitmap1);
 		}else {
			face.setImageBitmap(bitmap2);
		}
	}

	
	static class AnswerHolder {
		public CircleImageView face;
		public TextView nickName;
		private TextView publish;
		private ImageView huifu;
		private TextView content;
	}

	class AnswerAdapter extends BaseAdapter {
		private ArrayList<message> list;
		private AnswerHolder holder;

		public AnswerAdapter(ArrayList<message> list) {
			this.list = list;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			message item = list.get(position);
			if (convertView == null) {
				holder = new AnswerHolder();
				convertView = LayoutInflater.from(AnswerActivity.this)
						.inflate(R.layout.answer, null);
				holder.face = (CircleImageView) convertView.findViewById(R.id.face);
				holder.huifu=(ImageView) convertView.findViewById(R.id.answer);
				holder.nickName = (TextView) convertView.findViewById(R.id.name);
				holder.publish = (TextView) convertView.findViewById(R.id.publish);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				convertView.setTag(holder);
			} else {
				holder = (AnswerHolder) convertView.getTag();
			}
			holder.face.setImageBitmap(item.getBitmap());
			holder.nickName.setText(item.getName());
			holder.publish.setText(item.getTime());
			holder.content.setText(item.getContent());
			holder.huifu.setOnClickListener(null);
			return convertView;
		}

	}
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0){
				message item = new message(bitmap1,nickName,msgstring, timeString);
				list.add(item);
				adapter.notifyDataSetChanged();
				message.setText("");
				Toast.makeText(AnswerActivity.this, "回复成功", Toast.LENGTH_SHORT)
						.show();
			}else if (msg.what==1) {
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getUserID().equals(idName)){
						list.get(i).setBitmap(bitmap1);
					}else {
						list.get(i).setBitmap(bitmap2);
					}
				}
				adapter=new AnswerAdapter(list);
				word.setAdapter(adapter);
			}
			
		};
	};
	private String timeString;
	private String msgstring;
	
	public void send(View v){
		//id idname
		msgstring=message.getText().toString();
		if (msgstring.trim().equals("")) {
			Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
		} else {
			// 设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timeString = df.format(new Date());
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					new Savehuifu(id, idName, msgstring, timeString,nickName).start();
					return null;
				}

				protected void onPostExecute(Void result) {
					handler.sendEmptyMessage(0);
				};

			}.execute();
		}
	}
	class Savehuifu extends Thread {
		private int Msgid;
		private String id;
		private String name;
		private String text;
		private String time;

		public Savehuifu(int Msgid, String idname, String text, String time, String name) {
			this.Msgid = Msgid;
			this.id = id;
			this.text = text;
			this.time = time;
			this.name=name;
		}

		@Override
		public void run() {
			String url = "http://"+IP.ip+"/LessonForm/huifu";
			HttpPost request = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", Msgid + ""));
			params.add(new BasicNameValuePair("userID", idName));
			params.add(new BasicNameValuePair("content", text));
			params.add(new BasicNameValuePair("time", time));
			params.add(new BasicNameValuePair("name", nickName));
			try {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient client = new DefaultHttpClient();
				client.execute(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
     	overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

}
