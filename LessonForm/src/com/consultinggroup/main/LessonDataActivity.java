package com.consultinggroup.main;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.discuss.DiscussActivity;
import com.consultinggroup.discuss.MessageActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class LessonDataActivity extends Activity implements OnClickListener{
    private TextView lessonName,scoreText,name,teacher,detail,classNote,lessonType,credit,professionName;
    private ImageView back,messageBtn;
    private RatingBar ratingBar;
	private Intent intent;
	private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson_data);
		lessonName=(TextView) findViewById(R.id.lessonName);
		name=(TextView) findViewById(R.id.name);
		teacher=(TextView) findViewById(R.id.teacher);
		detail=(TextView) findViewById(R.id.detail);
		classNote=(TextView) findViewById(R.id.classNote);
		lessonType=(TextView) findViewById(R.id.lessonType);
		credit=(TextView) findViewById(R.id.credit);
		scoreText=(TextView) findViewById(R.id.scoreText);
		professionName=(TextView) findViewById(R.id.professionName);
		back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		messageBtn=(ImageView) findViewById(R.id.message);
		messageBtn.setOnClickListener(this);
		intent=getIntent();
		title=intent.getStringExtra("lessonName");
		String teacherName=intent.getStringExtra("teacher");
		String detailstring=intent.getStringExtra("detail");
		String classNoteString=intent.getStringExtra("classNote");
		String lessonTypeString=intent.getStringExtra("lessonType");
		String creditString=intent.getStringExtra("credit");
		String professionNameString=intent.getStringExtra("professionName");
		lessonName.setText(title);
		name.setText(title);
		teacher.setText(teacherName);
		detail.setText(detailstring);
		classNote.setText(classNoteString+"°à");
		lessonType.setText(lessonTypeString);
		credit.setText(creditString+"·Ö");
		professionName.setText(professionNameString);
		ratingBar=(RatingBar) findViewById(R.id.score);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(ratingBar.getRating()==0.0){
					scoreText.setText("ÔÝÎ´ÆÀ¼Û");
				}{
				scoreText.setText(ratingBar.getRating()+"");
				}
			}
			
		});
	}
	
	public void enter(View v){
		Intent intent=new Intent(this,MessageActivity.class);
		intent.putExtra("lessonName", title);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			break;
		case R.id.message:
			Intent intent=new Intent(this,DiscussActivity.class);
			intent.putExtra("lessonName", title);
			startActivity(intent);
			overridePendingTransition(R.anim.shake,
					R.anim.my_alpha_action);
			break;
		default:
			break;
	}
	}
}
