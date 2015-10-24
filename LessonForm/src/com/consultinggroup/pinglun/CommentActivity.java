package com.consultinggroup.pinglun;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.consultinggroup.login.MyDialog;
import com.consultinggroup.main.CustomListView;
import com.consultinggroup.main.FileItem;
import com.consultinggroup.main.MainActivity;
import com.consultinggroup.pinglun.CommentActivity.pinglunAdapter;
import com.consultinggroup.tools.CircleImageView;
import com.consultinggroup.tools.IP;
import com.consultinggroup.tools.PinglunEnity;
import com.consultinggroup.tools.jsonTools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

public class CommentActivity extends Activity implements OnClickListener {

	private ImageView back, returnHome;
	private ListView pinglunList;
	private ArrayList<PinglunEnity> content = new ArrayList<PinglunEnity>();
	private EditText data;
	private String id;
	private int MsgID;
	protected pinglunAdapter adapter;
	private String time;
	private String text;
	private String nickName;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		back = (ImageView) findViewById(R.id.back);
		returnHome = (ImageView) findViewById(R.id.returnHome);
		data = (EditText) findViewById(R.id.content);
		pinglunList = (ListView) findViewById(R.id.pinglunList);
		back.setOnClickListener(this);
		returnHome.setOnClickListener(this);
		initData();
		dialog=new MyDialog(this).loadDialog3();
		new GetPinglunData(MsgID).start();
	}

	private void initData() {
		MsgID = getIntent().getIntExtra("MsgID", 0);
		id = getSharedPreferences("studentInfo", 0).getString("id", "");
		nickName = getSharedPreferences("studentInfo", 0).getString("nickName",
				"");
	}

	static class PinglunHolder {
		public CircleImageView face;
		public TextView nickName;
		private TextView publish;
		private ImageView dianzanPinglun;
		private TextView content;
	}

	class pinglunAdapter extends BaseAdapter {
		private ArrayList<PinglunEnity> list;
		private PinglunHolder holder;

		public pinglunAdapter(ArrayList<PinglunEnity> list) {
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
			PinglunEnity item = list.get(position);
			if (convertView == null) {
				holder = new PinglunHolder();
				convertView = LayoutInflater.from(CommentActivity.this)
						.inflate(R.layout.pinglunitem, null);
				convertView.setTag(holder);
			} else {
				holder = (PinglunHolder) convertView.getTag();
			}
			holder.face = (CircleImageView) convertView.findViewById(R.id.face);
			holder.nickName = (TextView) convertView.findViewById(R.id.name);
			holder.publish = (TextView) convertView.findViewById(R.id.publish);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.dianzanPinglun = (ImageView) convertView
					.findViewById(R.id.dianzanPinglun);
			holder.face.setImageBitmap(item.getBitmap());
			holder.nickName.setText(item.getUsername());
			holder.publish.setText(item.getPinglunTime());
			holder.content.setText(item.getPinglunWords());
			return convertView;
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		case R.id.returnHome:
			finish();
			break;
		default:
			break;
		}
	}

	public void send(View v) {
		// 发评论MsgID id
		text = data.getText().toString();
		if (!text.equals("")) {
			// times++;
			SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
			time = df.format(new Date());
			/*
			 * 1.将评论存入数据库 2.将最新评论添加显示在listView中
			 */
			// 开启保存评论到数据库的线程
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					new SavePinglun(MsgID, id, text, time).start();
					return null;
				}

				protected void onPostExecute(Void result) {
					handler.sendEmptyMessage(0);
				};

			}.execute();
		} else {
			Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
		}

	}

	Handler handler = new android.os.Handler() {
		private Bitmap bitmap;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				for (int i = 0; i < list.size(); i++) {
					PinglunEnity item = new PinglunEnity();
					item.setBitmap(list.get(i).getBitmap());
					item.setUsername(list.get(i).getUsername());
					item.setPinglunTime(list.get(i).getPinglunTime());
					item.setPinglunWords(list.get(i).getPinglunWords());
					content.add(item);
					// adapter.notifyDataSetChanged();
				}
				dialog.dismiss();
				adapter = new pinglunAdapter(content);
				pinglunList.setAdapter(adapter);
			} else if (msg.what == 0) {
				if (file != null && file.exists()) {
					bitmap = BitmapFactory
							.decodeFile(facepath
									+ "/" + id + ".jpg");
				} else {
					bitmap = BitmapFactory.decodeResource(getResources(),
							R.drawable.icon);
				}
				PinglunEnity item = new PinglunEnity(time, text, nickName,
						bitmap);
				content.add(item);
				adapter.notifyDataSetChanged();
				data.setText("");
				Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT)
						.show();
			}
		}
	};
	public String facepath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/课堂时间";
	File file = new File(facepath, id + ".jpg");
	public List<PinglunEnity> list;

	class GetPinglunData extends Thread {
		private int id;
		private String jsonString;

		public GetPinglunData(int id) {
			this.id = id;
		}

		String url = "http://"+IP.ip+"/LessonForm/PinglunJson";

		@Override
		public void run() {
			HttpPost request = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("MsgID", MsgID + ""));
			try {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
					jsonString = EntityUtils.toString(response.getEntity());
					list = jsonTools.getPinglunEnitys("PinglunEnitys",
							jsonString);
					new Getfigures().start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class Getfigures extends Thread {
		private String url;

		@Override
		public void run() {
			for (int i = 0; i < list.size(); i++) {
				url = "http://"+IP.ip+"/LessonForm/Image/"
						+ list.get(i).getFigure();
				HttpPost request = new HttpPost(url);
				try {
					HttpClient client = new DefaultHttpClient();
					// 执行请求返回相应
					HttpResponse response = client.execute(request);
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					// Bitmap bitmap = BitmapFactory.decodeStream(is,
					// null, bitmapOptions);
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					list.get(i).setBitmap(bitmap);
					// map.put(content.get(i).getID(), bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(1);
		}
	}

	class SavePinglun extends Thread {
		private int Msgid;
		private String id;
		private String text;
		private String time;

		public SavePinglun(int Msgid, String id, String text, String time) {
			this.Msgid = Msgid;
			this.id = id;
			this.text = text;
			this.time = time;
		}

		@Override
		public void run() {
			String url = "http://"+IP.ip+"/LessonForm/Pinglun";
			HttpPost request = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("MsgID", MsgID + ""));
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("content", text));
			params.add(new BasicNameValuePair("time", time));
			try {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient client = new DefaultHttpClient();
				client.execute(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showFace(View v) {

	}

}
