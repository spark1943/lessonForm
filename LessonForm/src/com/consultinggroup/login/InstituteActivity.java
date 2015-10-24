package com.consultinggroup.login;

import java.util.ArrayList;
import java.util.List;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.tools.ExitApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class InstituteActivity extends Activity implements OnClickListener,OnItemClickListener{
	private ListView listView;
	private ImageView back;
	private EditText institute;
	private List<String> instituteldata;
	private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_institute);
		initView();
		initData();
		adapter= new ArrayAdapter<String>(this,R.layout.simple_list_item,instituteldata);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		ExitApplication.getInstance().addActivity(this);
	}
	private void initData() {
		instituteldata=new ArrayList<String>();
		instituteldata.add("国际软件学院");
		instituteldata.add("测绘学院");
		instituteldata.add("资源与环境学院");
		instituteldata.add("遥感信息工程学院");
		instituteldata.add("计算机学院");
		instituteldata.add("新闻与传播学院");
	}

	private void initView() {
		institute=(EditText) findViewById(R.id.instituteName);
		listView=(ListView) findViewById(R.id.school);
		back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		institute.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				InstituteActivity.this.adapter.getFilter().filter(arg0);
				institute.setFocusable(true);
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
		finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String institute=instituteldata.get(position);
		SelectData.institute=institute;
		if(getIntent().getStringExtra("Activity").equals("ResultActivity")){
			finish();
		}else {
		Intent intent = new Intent(this,
				YearActivity.class);
		intent.putExtra("institute", institute);
		intent.putExtra("Activity", "Activity");
		startActivity(intent);
		overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		}
	}

}
