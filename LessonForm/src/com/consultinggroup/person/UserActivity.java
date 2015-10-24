package com.consultinggroup.person;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.id;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.tools.CircleImageView;
import com.consultinggroup.tools.ExitApplication;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends Activity implements OnClickListener{
	private ImageView back,changeData;
	private TextView schoolName,institute,gender,like,nickName,identify;
	private CircleImageView face;
	String facepath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/课堂时间";
	private String idName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		back=(ImageView) findViewById(R.id.back);
		changeData=(ImageView) findViewById(R.id.changeData);
		schoolName=(TextView) findViewById(R.id.school);
		nickName=(TextView) findViewById(R.id.nickName);
		institute=(TextView) findViewById(R.id.institute);
		identify=(TextView) findViewById(R.id.identify);
		gender=(TextView) findViewById(R.id.gender);
		like=(TextView) findViewById(R.id.like);
		face=(CircleImageView) findViewById(R.id.face);
		face.setOnClickListener(new ShowPicture());
		back.setOnClickListener(this);
		changeData.setOnClickListener(this);
		initData();
		ExitApplication.getInstance().addActivity(this);
	}
	private void initData() {
		idName=getSharedPreferences("studentInfo", 0).getString("id", "");
        schoolName.setText(getSharedPreferences("studentInfo", 0).getString("schoolName", ""));		
        nickName.setText(getSharedPreferences("studentInfo", 0).getString("nickName", ""));		
        institute.setText(getSharedPreferences("studentInfo", 0).getString("institute", ""));		
	    gender.setText(getSharedPreferences("studentInfo", 0).getString("gender", ""));
	    like.setText(getSharedPreferences("studentInfo", 0).getString("like", ""));
	    identify.setText("用户id:"+getSharedPreferences("studentInfo", 0).getString("id", ""));
	    File file = new File(facepath,idName+".jpg");// 将要保存图片的路径
	    if(file.exists()){
	    	face.setImageBitmap(BitmapFactory.decodeFile(facepath+"/"+idName+".jpg"));
	    }
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			
			break;
		case R.id.changeData:
			Intent intent=new Intent(this,EditActivity.class);
			intent.putExtra("schoolName", schoolName.getText());
			intent.putExtra("nickName", nickName.getText());
			intent.putExtra("institute", institute.getText());
			intent.putExtra("gender", gender.getText());
			intent.putExtra("like", like.getText());
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;
		default:
			break;
		}
		
	}
	class ShowPicture implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			ImageView view = (ImageView) v;
			Drawable drawable = view.getDrawable();
			BitmapDrawable bd = (BitmapDrawable) drawable;
			Bitmap bitmap = bd.getBitmap();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, output);
			byte[] result = output.toByteArray();
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(UserActivity.this, ShowPicActivity.class);
			intent.putExtra("image", result);
			startActivity(intent);
		}
	}
       @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	   if(requestCode==1){
    		   schoolName.setText(getSharedPreferences("studentInfo", 0).getString("schoolName", ""));		
    		   nickName.setText(getSharedPreferences("studentInfo", 0).getString("nickName", ""));		
    	        institute.setText(getSharedPreferences("studentInfo", 0).getString("institute", ""));		
    		    gender.setText(getSharedPreferences("studentInfo", 0).getString("gender", ""));
    		    like.setText(getSharedPreferences("studentInfo", 0).getString("like", ""));
    		    File file = new File(facepath,idName+".jpg");// 将要保存图片的路径
    		    if(file.exists()){
    		    	face.setImageBitmap(BitmapFactory.decodeFile(facepath+"/"+idName+".jpg"));
    		    }
    	   }
    	   
    	super.onActivityResult(requestCode, resultCode, data);
    }

       
       public void stop(View v){
    	   getSharedPreferences("studentInfo", 0).edit().clear().commit();
			ExitApplication.getInstance().exit();
       }
}
