package com.consultinggroup.person;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.tools.Tools;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowPicActivity extends Activity {

	private ImageView imageView;
	Bitmap bitmap;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Intent intent=getIntent();
			byte[] data=intent.getByteArrayExtra("image");
			bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
			setContentView(R.layout.activity_show_pic);
			imageView=(ImageView) findViewById(R.id.image);
			imageView.setImageBitmap(bitmap);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			finish();
			return true;
		}
		
		public void savePic(View v){
			if(Tools.hasSdcard()){
				String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/ø·–„";
			    File file=new File(path);
			    
			    if(!file.exists()){
						file.mkdir();
			    }else {
			    	String picname=new Date().getTime()+".jpg";
					File picFile=new File(path,picname);
					try {
						BufferedOutputStream bos = new BufferedOutputStream(
								new FileOutputStream(picFile));
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					bos.flush();
					bos.close();
					Toast.makeText(this, "±£¥Ê≥…π¶", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}


}
