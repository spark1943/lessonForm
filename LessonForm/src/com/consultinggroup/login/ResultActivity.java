package com.consultinggroup.login;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.main.MainActivity;
import com.consultinggroup.tools.ExitApplication;

import android.os.Bundle;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends Activity implements OnClickListener {

	private ImageView back;
	private Button button;
	private RelativeLayout school, institute, year, xueli;
	private TextView schoolText, instituteText, yearText, xueliText;
	private Intent intent;
    public  static final int request_school=1;
    public  static final int request_institute=2;
    public  static final int request_year=3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		back = (ImageView) findViewById(R.id.back);
		initView();
		initData();
		back.setOnClickListener(this);
		button.setOnClickListener(this);
		school.setOnClickListener(this);
		institute.setOnClickListener(this);
		year.setOnClickListener(this);
		xueli.setOnClickListener(this);
		ExitApplication.getInstance().addActivity(this);
	}


	private void initView() {
		button=(Button) findViewById(R.id.confirm);
		school = (RelativeLayout) findViewById(R.id.schoollayout);
		institute = (RelativeLayout) findViewById(R.id.institutelayout);
		year = (RelativeLayout) findViewById(R.id.yearlayout);
		xueli = (RelativeLayout) findViewById(R.id.xuelilayout);
		schoolText=(TextView) findViewById(R.id.schoolText);
		instituteText=(TextView) findViewById(R.id.instituteText);
		yearText=(TextView) findViewById(R.id.yearText);
		xueliText=(TextView) findViewById(R.id.xueliText);
	}

	private void initData() {
		
		schoolText.setText(SelectData.school);
		instituteText.setText(SelectData.institute);
		yearText.setText(SelectData.year);
		xueliText.setText(SelectData.xueli);
		
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			break;
         case R.id.confirm:
        	 intent = new Intent(this,
      				LoginActivity.class);
        	 startActivity(intent);
        	 overridePendingTransition(R.anim.slide_left_in,
     				R.anim.slide_left_out);
        	 break;
         case R.id.schoollayout:
        	 intent = new Intent(this,
     				SchoolActivity.class);
     		intent.putExtra("school", SelectData.school);
     		intent.putExtra("Activity", "ResultActivity");
     		startActivityForResult(intent, request_school);
     		overridePendingTransition(R.anim.slide_left_in,
     				R.anim.slide_left_out);
        	 break;
         case R.id.institutelayout:
        	 intent = new Intent(this,
      				InstituteActivity.class);
      		intent.putExtra("institute", SelectData.institute);
      		intent.putExtra("Activity", "ResultActivity");
      		startActivityForResult(intent, request_institute);
      		overridePendingTransition(R.anim.slide_left_in,
      				R.anim.slide_left_out);
        	 break;
         case R.id.yearlayout:
        	 intent = new Intent(this,
       				YearActivity.class);
       		intent.putExtra("year", SelectData.year);
       		intent.putExtra("Activity", "ResultActivity");
       		startActivityForResult(intent, request_year);
       		overridePendingTransition(R.anim.slide_left_in,
       				R.anim.slide_left_out);
        	 break;
         case R.id.xuelilayout:
        	 
        	 break;
		default:
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		   if(requestCode==request_school){
//			   SelectData.school=data.getStringExtra("school");
			   schoolText.setText(SelectData.school);
		   }else if (requestCode==request_institute) {
//			   SelectData.institute=data.getStringExtra("institute");
			   instituteText.setText(SelectData.institute);
		}else if (requestCode==request_year) {
//			 SelectData.year=data.getStringExtra("year");
			  yearText.setText(SelectData.year);
		}
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
