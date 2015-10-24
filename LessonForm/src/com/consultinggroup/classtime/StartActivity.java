package com.consultinggroup.classtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.consultinggroup.login.SchoolActivity;
import com.consultinggroup.main.MainActivity;
import com.consultinggroup.tools.ExitApplication;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends Activity implements OnClickListener {
	private ViewPager startPager;
	private ViewGroup dots;
	private ImageView[] imageViews;
	private ImageView dot;
	private ArrayList<View> advPics;
	private Button login;
	private Timer time = new Timer();
	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(0);
		}
	};
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getSharedPreferences("studentInfo", 0).getString("id", "").equals("")){
		setContentView(R.layout.activity_start);
		initView();
		initData();
		startPager.setAdapter(new Adapter(advPics));
		startPager.setOnPageChangeListener(new GuidePageChangeListener());
		ExitApplication.getInstance().addActivity(this);
		}else {
			setContentView(R.layout.activity_start2);
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
						Intent intent = new Intent(StartActivity.this,
								MainActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_left_in,
								R.anim.slide_left_out);
						finish();
					}
			};
			
			time.schedule(task, 1300);
		}
	}

	private void initView() {
		startPager = (ViewPager) findViewById(R.id.start_pager);
		dots = (ViewGroup) findViewById(R.id.viewGroup);
		login = (Button) findViewById(R.id.login);
		// login.setOnClickListener(this);
		// register.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	private void initData() {
		advPics = new ArrayList<View>();
		ImageView img1 = new ImageView(this);
		img1.setBackgroundResource(R.drawable.start4);
		advPics.add(img1);
		ImageView img2 = new ImageView(this);
		img2.setBackgroundResource(R.drawable.start2);
		advPics.add(img2);
		ImageView img3 = new ImageView(this);
		img3.setBackgroundResource(R.drawable.start3);
		advPics.add(img3);

		imageViews = new ImageView[advPics.size()];

		for (int i = 0; i < advPics.size(); i++) {
			dot = new ImageView(this);
			LayoutParams params = new LayoutParams(25, 25);
			dot.setLayoutParams(params);
			imageViews[i] = dot;
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.ic_page_selected);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.ic_page_normal);
			}
			dots.addView(imageViews[i]);
		}

	}

	private class GuidePageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int arg0) {
			if (arg0 == 2) {
				login.setVisibility(View.VISIBLE);
			} else {
				login.setVisibility(View.INVISIBLE);
			}
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.ic_page_selected);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.ic_page_normal);
				}

			}

		}

	}

	private class Adapter extends PagerAdapter {
		private List<View> views = null;

		public Adapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

	}

	public void onClick(View v) {
		if(getSharedPreferences("studentInfo", 0).getString("id", "").equals("")){
			Intent intent1 = new Intent(this, SchoolActivity.class);
			intent1.putExtra("Activity", "StartActivity");
			startActivity(intent1);
			overridePendingTransition(R.anim.push_up_in,
					R.anim.push_up_out);
		}else {
			Intent intent2 = new Intent(this, MainActivity.class);
			startActivity(intent2);
			overridePendingTransition(R.anim.push_up_in,
					R.anim.push_up_out);
		}
	}

}
