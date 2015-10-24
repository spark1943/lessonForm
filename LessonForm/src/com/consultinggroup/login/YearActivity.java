package com.consultinggroup.login;

import java.util.ArrayList;
import java.util.List;

import com.consultinggroup.classtime.R;
import com.consultinggroup.tools.ExitApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class YearActivity extends Activity implements OnClickListener,OnItemClickListener {
	private ListView listView;
	private ImageView back;
	private List<String> yeardata;
	private ArrayAdapter<String> adapter;
	private EditText year;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_year);
		initView();
		initData();
		adapter= new ArrayAdapter<String>(this,R.layout.simple_list_item,yeardata);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		ExitApplication.getInstance().addActivity(this);
	}
	private void initData() {
		yeardata=new ArrayList<String>();
		yeardata.add("2015");
		yeardata.add("2014");
		yeardata.add("2013");
		yeardata.add("2012");
		yeardata.add("2011");
		yeardata.add("2010");
		yeardata.add("2009");
		yeardata.add("2008");
		yeardata.add("2007");
		yeardata.add("2006");
		yeardata.add("2005");
		yeardata.add("2004");
		yeardata.add("2003");
	}

	private void initView() {
		listView=(ListView) findViewById(R.id.year);
		back=(ImageView) findViewById(R.id.back);
		year=(EditText) findViewById(R.id.yearName);
		back.setOnClickListener(this);
		year.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				YearActivity.this.adapter.getFilter().filter(arg0);
				year.setFocusable(true);
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
		String year=yeardata.get(position);
		SelectData.year=year;
		if(getIntent().getStringExtra("Activity").equals("ResultActivity")){
			finish();
		}else {
		Intent intent = new Intent(this,
				ResultActivity.class);
		intent.putExtra("Activity", "Activity");
		startActivity(intent);
		overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		}
	}

}
