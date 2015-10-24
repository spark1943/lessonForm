package com.consultinggroup.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.consultinggroup.classtime.R;
import com.consultinggroup.tools.ExitApplication;
import com.consultinggroup.tools.IP;
import com.consultinggroup.tools.MyhttpClient;
import com.consultinggroup.tools.jsonTools;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SchoolActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private ListView listView;
	private ImageView back;
	private EditText school;
	private TextView locationText;
	private ArrayAdapter<String> adapter;
	public ArrayList<String> schoolName;
	public String location;
	private static final int Success_GetSchName = 1;
	private LocationClient mLocationClient;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school);
		initView();
		findLocation();
		// initData();
		ExitApplication.getInstance().addActivity(this);
	}

	private void findLocation() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		mLocationClient.registerLocationListener(new MyLocationListener());
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		dialog=new MyDialog(this).loadplace();
		mLocationClient.start();
	}
	public class MyLocationListener implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append(location.getCity());
			String place=sb.toString();
			SchoolActivity.this.location=place;
			new GetSchoolName(place).start();
		}
	}
	protected void onStop() {
		super.onStop();
		if(mLocationClient.isStarted()){
		mLocationClient.stop();
		}
	};
	protected void onPause() {
		super.onPause();
		if(mLocationClient.isStarted()){
			mLocationClient.stop();
			}
	};
	protected void onRestart() {
		super.onStart();
		if(!mLocationClient.isStarted())
		mLocationClient.start();
	};
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == Success_GetSchName) {
				locationText.setText(location);
				adapter = new ArrayAdapter<String>(SchoolActivity.this,
						R.layout.simple_list_item, schoolName);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(SchoolActivity.this);
				SchoolActivity.this.dialog.dismiss();
			}

		};
	};

	private void initView() {
		school = (EditText) findViewById(R.id.schoolName);
		listView = (ListView) findViewById(R.id.school);
		back = (ImageView) findViewById(R.id.back);
		locationText = (TextView) findViewById(R.id.locationText);
		back.setOnClickListener(this);
		school.setFocusable(true);
		school.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				SchoolActivity.this.adapter.getFilter().filter(arg0);
				school.setFocusable(true);
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String school = schoolName.get(position);
		SelectData.school = school;
		if (getIntent().getStringExtra("Activity").equals("ResultActivity")) {
			finish();
		} else {
			Intent intent = new Intent(this, InstituteActivity.class);
			intent.putExtra("school", school);
			intent.putExtra("Activity", "Activity");
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
		}
	}

	class GetSchoolName extends Thread {
		private String url = "http://"+IP.ip+"/LessonForm/Location";
		private String location;

		public GetSchoolName(String location) {
			this.location = location;
		}

		@Override
		public void run() {
			HttpPost request = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("location", location));
			try {
				// 设置请求参数项
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient client = new MyhttpClient().getHttpClient();
				// 执行请求返回相应
				HttpResponse response = client.execute(request);

				// 判断是否请求成功
				if (response.getStatusLine().getStatusCode() == 200) {
					// 获得响应信息
					String jsonString = EntityUtils.toString(response
							.getEntity());
					schoolName = jsonTools.getSchoolName("schoolName",
							jsonString);
					handler.sendEmptyMessage(Success_GetSchName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
