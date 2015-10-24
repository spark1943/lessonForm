package com.consultinggroup.write;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import com.consultinggroup.photo.util.Bimp;
import com.consultinggroup.photo.util.FileUtils;
import com.consultinggroup.photo.util.ImageItem;
import com.consultinggroup.photo.util.PublicWay;
import com.consultinggroup.photo.util.Res;
import com.consultinggroup.tools.IP;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WriteActivity extends Activity implements OnClickListener{
	private View parentView;
	private PopupWindow pop;
	private LinearLayout ll_popup;
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private String imageName;
	public static Bitmap bimap;
	private EditText text;
	private TextView send;
	private String idName;
	private static final int dismiss_dialog=4;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_write,
				null);
		setContentView(parentView);
		send=(TextView) findViewById(R.id.send);
		send.setOnClickListener(this);
		text=(EditText) findViewById(R.id.text);
		Init();
	}

	public void Init() {
		idName=getSharedPreferences("studentInfo", 0).getString("id", "");
		pop = new PopupWindow(WriteActivity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				photo();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(WriteActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				// overridePendingTransition(R.anim.activity_translate_in,
				// R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							WriteActivity.this, R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
					 } else {
					 Intent intent = new Intent(WriteActivity.this,
					 GalleryActivity.class);
					 intent.putExtra("position", "1");
					 intent.putExtra("ID", arg2);
					 startActivity(intent);
				}
			}
		});

	}

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				adapter.notifyDataSetChanged();
				break;
			case dismiss_dialog:
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "发表成功",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
	private String content;
	private String time;
	private Dialog dialog;

	public void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp.max == Bimp.tempSelectBitmap.size()) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else {
						Bimp.max += 1;
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				}
			}
		}).start();
	}
   //每次返回时更新
	@Override
	protected void onResume() {
		adapter.update();
		super.onRestart();
	}
	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);
				
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}
	
	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for(int i=0;i<PublicWay.activityList.size();i++){
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
		}
		return true;
	}
	
	public void Return(View v){
		finish();
		overridePendingTransition(R.anim.push_down_in,
				R.anim.push_down_out);
	}
	
	public void onClick(View v) {
		content = text.getText().toString();
		if(Bimp.tempSelectBitmap.size()>0){
			dialog=new MyDialog(this).loadupload();
			//保存发表的图片到服务器端及发表的文字(id picture)
			uploadPicture(Bimp.tempSelectBitmap.get(0).getImagePath(),content);
		}else {
			
		if (content.trim().equals("")) {
			Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
		} else {
			// 设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			time = df.format(new Date());
			sendMyMessage(content);
		}
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
				String path = "http://"+IP.ip+"/LessonForm/word.jsp";
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
						+ "&content=" + URLEncoder.encode(message, "utf-8")
						+ "&time=" + URLEncoder.encode(time, "utf-8");
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
	
	private void uploadPicture(final String filepath,final String content) {
		new Thread(new Runnable() {

			public void run() {
				Looper.prepare();
				uploadFile(filepath,content);
				Looper.loop();
			}
		}).start();
	}	
	
	private void uploadFile(String filePath,String content) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			String newName = idName + ".jpg";
			String actionUrl = "http://"+IP.ip+"/LessonForm/acceptPictures";
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
			con.setRequestProperty("Charset", "UTF-8");
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
			if (!b.toString().equals("fail")) {
				String name=b.toString();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String time = df.format(new Date());
				if(!content.equals("")){
					//上传文字 time name content 
					new upWords(name, content, time).start();
//					Toast.makeText(getApplicationContext(), "发表成功",
//							Toast.LENGTH_SHORT).show();
//					finish();
				}
			} else{
				Toast.makeText(getApplicationContext(), "发表失败",
						Toast.LENGTH_SHORT).show();
			}
			/* 关闭DataOutputStream */
			ds.close();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "发表失败", Toast.LENGTH_SHORT)
					.show();
		}
	}
	class upWords extends Thread{
		//id content time
		private String name;
		private String content;
		private String time;
		
		public upWords(String name, String content, String time) {
			this.name = name;
			this.content = content;
			this.time = time;
		}

		@Override
		public void run() {
			String Url = "http://"+IP.ip+"/LessonForm/acceptWords";
			HttpPost request = new HttpPost(Url);
			// 如果传递参数多的话，可以丢传递的参数进行封装
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 添加邮箱和密码
			params.add(new BasicNameValuePair("name", name+""));
			params.add(new BasicNameValuePair("content", content));
			params.add(new BasicNameValuePair("time", time));
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
	
	

}
