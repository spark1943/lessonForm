package com.consultinggroup.discuss;

import java.io.File;
import java.util.ArrayList;
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

import com.baidu.a.a.a.a.b;
import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.discuss.MessageActivity.Myadapter;
import com.consultinggroup.main.CustomListView;
import com.consultinggroup.main.CustomListView.OnRefreshListener;
import com.consultinggroup.main.Listitem;
import com.consultinggroup.main.MainActivity;
import com.consultinggroup.tools.CircleImageView;
import com.consultinggroup.tools.IP;
import com.consultinggroup.tools.jsonTools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessageActivity extends Activity implements OnClickListener,OnItemClickListener{
     
	private String lessonName;
	private ImageView write,back;
    private CustomListView word;
    private ArrayList<message> list=new ArrayList<message>();
	protected Myadapter adapter;
	public String jsonString;
	private String idName;
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		back=(ImageView) findViewById(R.id.back);
		write=(ImageView) findViewById(R.id.write);
		back.setOnClickListener(this);
		write.setOnClickListener(this);
		lessonName=getIntent().getStringExtra("lessonName");
		idName=getSharedPreferences("studentInfo", 0).getString("id", "");
		write=(ImageView) findViewById(R.id.write);
		word=(CustomListView) findViewById(R.id.word);
		word.setOnItemClickListener(this);
		word.setonRefreshListener(new OnRefreshListener() {
			
			public void onRefresh() {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						list.clear();
						new Getliuyan().start();
						return null;
					}

					protected void onPostExecute(Void result) {
						handler.sendEmptyMessage(2);
					};

				}.execute();
			}
		});
		File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/课堂时间/"+idName+".jpg");
		if (file.exists()) {
			bitmap1=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/课堂时间/"+idName+".jpg");
		}else {
			bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.face);
		}
		bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.icon);
	    new Getliuyan().start();
	}
	
	
	class Getliuyan extends Thread{
		String url="http://"+IP.ip+"/LessonForm/Getliuyan";
		@Override
		public void run() {
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
					System.out.println(jsonString);
					// saveObject(jsonString, "dianzanMsgID");
					list=jsonTools.getGetliuyan("messages", jsonString);
				    handler.sendEmptyMessage(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	static class ViewHolder{
		private CircleImageView face;
		public TextView name;
		public TextView content;
		public TextView time; 
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getUserID().equals(idName)){
					list.get(i).setBitmap(bitmap1);
				}else {
					list.get(i).setBitmap(bitmap2);
				}
			}
			if(msg.what==1){
			adapter=new Myadapter(list);
			word.setAdapter(adapter);
			}else if (msg.what==2) {
				adapter.notifyDataSetChanged();
				word.onRefreshComplete();
			}
		};
	};
	public static  message message;
	
	class Myadapter extends BaseAdapter{
        private ArrayList<message> list;
		private com.consultinggroup.discuss.MessageActivity.ViewHolder holder;
        
		public Myadapter(ArrayList<message> list) {
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
				holder = new ViewHolder();
					convertView = LayoutInflater.from(MessageActivity.this)
							.inflate(R.layout.message, null);
				holder.face = (CircleImageView) convertView.findViewById(R.id.face);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.time = (TextView) convertView.findViewById(R.id.publish);
				convertView.setTag(holder);
			} else {
					convertView.setTag(holder);
			}
			holder.face.setImageBitmap(item.getBitmap());
			holder.name.setText(item.getName());
			holder.content.setText(item.getContent());
			holder.time.setText(item.getTime());
			return convertView;
		}
		
	}
	
	

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.back:
			  finish();
		     	overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			break;
		case R.id.write:
			Intent intent=new Intent(this,WriteLyActivity.class);
			intent.putExtra("lessonName", lessonName);
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;

		default:
			break;
		}
	}



	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		message=list.get(position-1);
		Intent intent=new Intent(MessageActivity.this,AnswerActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		
	}
}
