package com.consultinggroup.main;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.consultinggroup.classtime.R;
import com.consultinggroup.files.UploadActivity;
import com.consultinggroup.main.CustomListView.OnRefreshListener;
import com.consultinggroup.main.ScheduleView.OnItemClassClickListener;
import com.consultinggroup.person.ShowPicActivity;
import com.consultinggroup.person.Student;
import com.consultinggroup.person.UserActivity;
import com.consultinggroup.pinglun.CommentActivity;
import com.consultinggroup.showScore.ScoreActivity;
import com.consultinggroup.tools.CircleImageView;
import com.consultinggroup.tools.ExitApplication;
import com.consultinggroup.tools.HttpUtils;
import com.consultinggroup.tools.IP;
import com.consultinggroup.tools.MyhttpClient;
import com.consultinggroup.tools.Tools;
import com.consultinggroup.tools.jsonTools;
import com.consultinggroup.write.WriteActivity;

public class MainActivity extends Activity implements OnClickListener,
		OnMenuItemClickListener {
	private ViewPager viewPager;
	private ImageView lesson, society, person, sendMsg, lessonplus, uploadFile;
	private TextView lessonText, societyText, personText, title;
	private RelativeLayout lessonlayout, societylayout, personlayout;
	private ImageView[] imageViews;
	private int[] normalId, pressId;
	private TextView[] tab;
	private String[] titleText;
	private List<View> fragmentPage = new ArrayList<View>();
	BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	private LayoutInflater inflater;
	private CustomListView writeListView;
	private CustomListView fileListView;
	private View lessonView;
	private CircleImageView face;
	private View societyview;
	private static final int success_fileData = 4;
	public com.consultinggroup.main.MainActivity.ViewHolder holder;
	private ArrayList<Listitem> content = new ArrayList<Listitem>();
	private ArrayList<Listitem> content2 = new ArrayList<Listitem>();
	private ArrayList<FileItem> content3 = new ArrayList<FileItem>();
	private View bookView;
	private ArrayList<FileItem> fileContent;
	String facepath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/课堂时间";
	static final String filepath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/课堂时间/资料";
	private String idName;
	protected com.consultinggroup.main.MainActivity.Myadapter adapter;
	private Dialog dialog;
	protected FileAdapter fileadapter;
	private ProgressDialog progressDialog;
	private ScheduleView scheduleView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		ExitApplication.getInstance().addActivity(this);
		viewPager.setAdapter(new Adapter(fragmentPage));
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		new DianzanMsgID(idName).start();
		new InitShuoShuoData().start();
		new Getfigures().start();
		new Thread(new GetPictureByid()).start();
		new InitFilesData().start();
	}

	private void initView() {
		inflater = LayoutInflater.from(this);
		title = (TextView) findViewById(R.id.title);
		viewPager = (ViewPager) findViewById(R.id.main_pager);
		lesson = (ImageView) findViewById(R.id.lesson);
		society = (ImageView) findViewById(R.id.society);
		person = (ImageView) findViewById(R.id.person);
		lessonplus = (ImageView) findViewById(R.id.lessonplus);
		lessonplus.setOnClickListener(this);
		lessonText = (TextView) findViewById(R.id.lessonText);
		societyText = (TextView) findViewById(R.id.guangchangText);
		personText = (TextView) findViewById(R.id.personText);
		lessonlayout = (RelativeLayout) findViewById(R.id.lessonLayout);
		societylayout = (RelativeLayout) findViewById(R.id.societyLayout);
		personlayout = (RelativeLayout) findViewById(R.id.personLayout);
		lessonlayout.setOnClickListener(this);
		societylayout.setOnClickListener(this);
		personlayout.setOnClickListener(this);
		lessonView = inflater.inflate(R.layout.main, null);
		scheduleView = (ScheduleView) lessonView
				.findViewById(R.id.scheduleView);
		societyview = inflater.inflate(R.layout.society_view, null);
		bookView = inflater.inflate(R.layout.book_view, null);
		uploadFile = (ImageView) bookView.findViewById(R.id.uploadFile);
		uploadFile.setOnClickListener(this);
		sendMsg = (ImageView) societyview.findViewById(R.id.sendMsg);
		sendMsg.setOnClickListener(this);
		face = (CircleImageView) findViewById(R.id.face);
		face.setOnClickListener(this);
		writeListView = (CustomListView) societyview
				.findViewById(R.id.shuoshuo);
		fileListView = (CustomListView) bookView.findViewById(R.id.files);
		writeListView.setonRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						new DianzanMsgID(idName).start();
						new InitShuoShuoData().start();
						new Getfigures2().start();
						new Thread(new GetPictureByid2()).start();
						return null;
					}

					protected void onPostExecute(Void result) {
						handler.sendEmptyMessage(2);
					};

				}.execute();
			}
		});
		fileListView.setonRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						content3.clear();
						new InitFilesData().start();
						return null;
					}

					protected void onPostExecute(Void result) {
						handler.sendEmptyMessage(5);
					};

				}.execute();
			}
		});

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("提示");
		progressDialog.setMessage("正在下载文件");
		progressDialog.setCancelable(true);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	}

	// 开启线程下载发表的图片，根据id,取Bitmap
	class GetPictureByid implements Runnable {

		String url;

		public void run() {
			synchronized (MainActivity.class.getClass()) {
				// map = new HashMap<Integer, Bitmap>();
				for (int i = 0; i < content.size(); i++) {
					if (content.get(i).getType() == Listitem.picType) {
						url = "http://"+IP.ip+"/LessonForm/Pictures/"
								+ content.get(i).getPicture();
						HttpPost request = new HttpPost(url);
						try {
							HttpClient client = new DefaultHttpClient();
							// 执行请求返回相应
							HttpResponse response = client.execute(request);
							HttpEntity entity = response.getEntity();
							InputStream is = entity.getContent();
							Bitmap bitmap = BitmapFactory.decodeStream(is,
									null, bitmapOptions);
							content.get(i).setPicBitmap(bitmap);
							// map.put(content.get(i).getID(), bitmap);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				handler.sendEmptyMessage(0);
			}
		}

	}

	class GetPictureByid2 implements Runnable {

		String url;

		public void run() {
			synchronized (MainActivity.class.getClass()) {
				// map = new HashMap<Integer, Bitmap>();
				for (int i = 0; i < content.size(); i++) {
					if (content.get(i).getType() == Listitem.picType) {
						url = "http://"+IP.ip+"/LessonForm/Pictures/"
								+ content.get(i).getPicture();
						HttpPost request = new HttpPost(url);
						try {
							HttpClient client = new DefaultHttpClient();
							// 执行请求返回相应
							HttpResponse response = client.execute(request);
							HttpEntity entity = response.getEntity();
							InputStream is = entity.getContent();
							Bitmap bitmap = BitmapFactory.decodeStream(is,
									null, bitmapOptions);
							content.get(i).setPicBitmap(bitmap);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	// 点击显示全屏图片
	class ShowPicture implements OnClickListener {

		public void onClick(View v) {
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
			Intent intent = new Intent(MainActivity.this, ShowPicActivity.class);
			intent.putExtra("image", result);
			startActivity(intent);
		}
	}

	// 从服务器获取数据
	class InitShuoShuoData extends Thread {
		String url = "http://"+IP.ip+"/LessonForm/ContentJson";

		@Override
		public void run() {
			synchronized (MainActivity.this.getClass()) {

				String jsonString = HttpUtils.getJsonString(url);
				ArrayList<Student> list = jsonTools.getStudents("Students",
						jsonString);
				// 将数据保存到本地
				// saveObject(jsonString, "cachePersons");
				content.clear();
				for (int i = list.size() - 1; i >= 0; i--) {
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), R.drawable.face);
					if (list.get(i).getPicture().equals("")) {
						Listitem item = new Listitem(Listitem.wordType, list
								.get(i).getTime(), list.get(i).getFigure(),
								list.get(i).getMessage(), list.get(i)
										.getNickName(), list.get(i)
										.getDianzanNum(), list.get(i)
										.getPinglunNum(), list.get(i)
										.getMsgID());
						content.add(item);
					} else {
						Listitem item = new Listitem(Listitem.picType, list
								.get(i).getTime(), list.get(i).getFigure(),
								list.get(i).getMessage(), list.get(i)
										.getNickName(), list.get(i)
										.getDianzanNum(), list.get(i)
										.getPinglunNum(), list.get(i)
										.getMsgID(), list.get(i).getPicture());
						content.add(item);
					}
				}
			}
		}
		// handler.sendEmptyMessage(0);
	}

	class Getfigures extends Thread {
		private String url;

		@Override
		public void run() {
			synchronized (MainActivity.this.getClass()) {
				for (int i = 0; i < content.size(); i++) {
					url = "http://"+IP.ip+"/LessonForm/Image/"
							+ content.get(i).getFigure();
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
						savePic(content.get(i).getFigure(), bitmap);
						content.get(i).setBitmap(bitmap);
						// map.put(content.get(i).getID(), bitmap);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// handler.sendEmptyMessage(1);
			}
		}

	}

	class Getfigures2 extends Thread {
		private String url;

		@Override
		public void run() {
			synchronized (MainActivity.this.getClass()) {
				for (int i = 0; i < content.size(); i++) {
					url = "http://"+IP.ip+"/LessonForm/Image/"
							+ content.get(i).getFigure();
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
						savePic(content.get(i).getFigure(), bitmap);
						content.get(i).setBitmap(bitmap);
						// map.put(content.get(i).getID(), bitmap);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public void savePic(String name, Bitmap bitmap) {
		if (Tools.hasSdcard()) {
			String path = facepath;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			} else {
				File picFile = new File(path, name);
				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(picFile));
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					bos.flush();
					bos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler handler = new android.os.Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
			} else if (msg.what == 2) {
				adapter.changeList(content);
				writeListView.onRefreshComplete();
			} else if (msg.what == 0) {
				adapter = new Myadapter(content);
				writeListView.setAdapter(adapter);

			} else if (msg.what == success_fileData) {
				fileadapter = new FileAdapter(content3);
				fileListView.setAdapter(fileadapter);
			} else if (msg.what == 5) {
				fileadapter.notifyDataSetChanged();
				fileListView.onRefreshComplete();
			}
		}
	};
	public String filename;
	public List<Integer> list;
	private ArrayList<ClassInfo> classList;

	private void initData() {
		bitmapOptions.inSampleSize = 2;
		idName = getSharedPreferences("studentInfo", 0).getString("id", "");
		File file = new File(facepath, idName + ".jpg");// 将要保存图片的路径
		if (file.exists()) {
			face.setImageBitmap(BitmapFactory.decodeFile(facepath + "/"
					+ idName + ".jpg"));
		}
		imageViews = new ImageView[3];
		imageViews[0] = lesson;
		imageViews[1] = society;
		imageViews[2] = person;
		pressId = new int[3];
		pressId[0] = R.drawable.lesson_press;
		pressId[1] = R.drawable.society_press;
		pressId[2] = R.drawable.book_press;
		tab = new TextView[3];
		tab[0] = lessonText;
		tab[1] = societyText;
		tab[2] = personText;
		normalId = new int[3];
		normalId[0] = R.drawable.lesson_nomal;
		normalId[1] = R.drawable.society_normal;
		normalId[2] = R.drawable.book_normal;
		titleText = new String[3];
		titleText[0] = "课表\n" + "第1周";
		titleText[1] = "校园";
		titleText[2] = "资料";
		fragmentPage.add(lessonView);
		fragmentPage.add(societyview);
		fragmentPage.add(bookView);
		content = new ArrayList<Listitem>();
		fileContent = new ArrayList<FileItem>();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.face);
		Bitmap picture = BitmapFactory.decodeResource(getResources(),
				R.drawable.blank);
		try {
			File sd = Environment.getExternalStorageDirectory();
			File targetFile = new File(sd.getPath() + File.separator
					+ "课堂时间/lesson.txt");
			FileInputStream fis = new FileInputStream(targetFile);
			BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
			StringBuilder result = new StringBuilder();
			String line = null;
			while ((line = bf.readLine()) != null) {
				result.append(line);
			}
			bf.close();
			JSONTokener jsonParse = new JSONTokener(result.toString());

			JSONArray courseArray = (JSONArray) jsonParse.nextValue();
			classList = new ArrayList<ClassInfo>();
			ClassInfo classInfo;
			JSONObject courseObject;
			for (int i = 0; i < courseArray.length() - 1; i++) {
				courseObject = courseArray.getJSONObject(i);
				classInfo = new ClassInfo();
				classInfo.setAcademicTeach(courseObject
						.getString("academicTeach"));
				classInfo.setAreaName(courseObject.getString("areaName"));
				classInfo.setBeginTime(courseObject.getString("beginTime"));
				classInfo.setBeginWeek(courseObject.getString("beginWeek"));
				classInfo.setClassNote(courseObject.getString("classNote"));
				classInfo.setClassRoom(courseObject.getString("classRoom"));
				classInfo.setCredit(courseObject.getString("credit"));
				classInfo.setDay(courseObject.getString("day"));
				classInfo.setDetail(courseObject.getString("detail"));
				classInfo.setEndTime(courseObject.getString("endTime"));
				classInfo.setEndWeek(courseObject.getString("endWeek"));
				classInfo.setNote(courseObject.getString("note"));
				classInfo.setPlanType(courseObject.getString("planType"));
				classInfo.setGrade(courseObject.getString("grade"));
				classInfo.setLessonName(courseObject.getString("lessonName"));
				classInfo.setProfessionName(courseObject
						.getString("professionName"));
				classInfo.setState(courseObject.getString("state"));
				classInfo.setTeacherName(courseObject.getString("teacherName"));
				classInfo.setWeekInterVal(courseObject
						.getString("weekInterVal"));
				classInfo.setClassLen();
				// System.out.println(course.lessonName);
				classList.add(classInfo);
			}
		} catch (Exception e) {
			Toast.makeText(this, "课程文件错误！\n请重新获取课程！", Toast.LENGTH_SHORT)
					.show();
		}
		scheduleView.setClassList(classList);
		scheduleView
				.setOnItemClassClickListener(new OnItemClassClickListener() {

					public void onClick(ClassInfo classInfo) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(MainActivity.this, LessonDataActivity.class);
						intent.putExtra("lessonName", classInfo.getLessonName());
						intent.putExtra("lessonType", classInfo.getPlanType());
						intent.putExtra("credit", classInfo.getCredit());
						intent.putExtra("teacher", classInfo.getTeacherName());
						intent.putExtra("detail", classInfo.getDetail());
						intent.putExtra("classNote", classInfo.getClassNote());
						intent.putExtra("professionName", classInfo.getProfessionName());
						startActivity(intent);
						overridePendingTransition(R.anim.slide_left_in,
								R.anim.slide_left_out);
					}
				});
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	class GuidePageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			title.setText(titleText[arg0]);
			for (int i = 0; i < imageViews.length; i++) {
				if (arg0 == i) {
					imageViews[arg0].setBackgroundResource(pressId[i]);
					tab[i].setTextColor(getResources().getColor(
							R.color.tab_press));
				}
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(normalId[i]);
					tab[i].setTextColor(getResources().getColor(
							R.color.tab_normal));
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

		switch (v.getId()) {
		case R.id.lessonLayout:
			viewPager.setCurrentItem(0);
			title.setText(titleText[0]);
			lesson.setBackgroundResource(R.drawable.lesson_press);
			society.setBackgroundResource(R.drawable.society_normal);
			person.setBackgroundResource(R.drawable.book_normal);
			break;
		case R.id.societyLayout:
			viewPager.setCurrentItem(1);
			title.setText(titleText[1]);
			society.setBackgroundResource(R.drawable.society_press);
			lesson.setBackgroundResource(R.drawable.lesson_nomal);
			person.setBackgroundResource(R.drawable.book_normal);
			break;
		case R.id.personLayout:
			viewPager.setCurrentItem(2);
			title.setText(titleText[2]);
			person.setBackgroundResource(R.drawable.book_press);
			lesson.setBackgroundResource(R.drawable.lesson_nomal);
			society.setBackgroundResource(R.drawable.society_normal);
			break;
		case R.id.face:
			Intent intent = new Intent(this, UserActivity.class);
			startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;
		case R.id.sendMsg:
			Intent intent2 = new Intent(this, WriteActivity.class);
			startActivity(intent2);
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			break;
		case R.id.lessonplus:
			Intent intent4=new Intent(this,ScoreActivity.class);
			startActivity(intent4);
			break;
		case R.id.uploadFile:
			Intent intent3 = new Intent(this, UploadActivity.class);
			startActivity(intent3);
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			ExitApplication.getInstance().exit();
		}
	}

	static class ViewHolder {
		public ImageView img;
		public TextView name;
		public TextView content;
		public TextView time;
		public TextView dianzan;
		public TextView pinglun;
		public TextView share;
		public ImageView picture;
		public ImageView support;
		public RelativeLayout support_click;
		public RelativeLayout comment_click;
	}

	class Myadapter extends BaseAdapter {

		private ArrayList<Listitem> content;

		public Myadapter(ArrayList<Listitem> content) {
			this.content = content;
		}

		public void changeList(ArrayList<Listitem> content) {
			this.content = (ArrayList<Listitem>) content.clone();
			// content=null;
			notifyDataSetChanged();
		}

		public void clearList() {
			if (content != null) {
				content.clear();
			}
			notifyDataSetChanged();
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return content.size();
		}

		public Listitem getItem(int position) {
			// TODO Auto-generated method stub
			return content.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// ListView优化：通过convertView+ViewHolder来实现
			Listitem item = content.get(position);
			int type = item.getType();
			if (convertView == null) {
				holder = new ViewHolder();
				if (type == Listitem.wordType) {
					convertView = LayoutInflater.from(MainActivity.this)
							.inflate(R.layout.word, null);
				} else if (type == Listitem.picType) {
					convertView = LayoutInflater.from(MainActivity.this)
							.inflate(R.layout.itempicture, null);
					holder.picture = (ImageView) convertView
							.findViewById(R.id.picture);
				}
				holder.img = (ImageView) convertView.findViewById(R.id.image);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				holder.time = (TextView) convertView.findViewById(R.id.publish);
				holder.support_click = (RelativeLayout) convertView
						.findViewById(R.id.support_click);
				holder.comment_click = (RelativeLayout) convertView
						.findViewById(R.id.comment_click);
				holder.pinglun = (TextView) convertView
						.findViewById(R.id.pinglun);
				holder.dianzan = (TextView) convertView
						.findViewById(R.id.dianzan);
				holder.support = (ImageView) convertView
						.findViewById(R.id.support);
				holder.share = (TextView) convertView.findViewById(R.id.share);
				convertView.setTag(holder);
			} else {
				if (type == Listitem.picType
						&& (convertView.findViewById(R.id.picture)) == null) {
					convertView = LayoutInflater.from(MainActivity.this)
							.inflate(R.layout.itempicture, null);
					holder.picture = (ImageView) convertView
							.findViewById(R.id.picture);
					holder.img = (ImageView) convertView
							.findViewById(R.id.image);
					holder.name = (TextView) convertView
							.findViewById(R.id.name);
					holder.content = (TextView) convertView
							.findViewById(R.id.content);
					holder.time = (TextView) convertView
							.findViewById(R.id.publish);
					holder.support_click = (RelativeLayout) convertView
							.findViewById(R.id.support_click);
					holder.comment_click = (RelativeLayout) convertView
							.findViewById(R.id.comment_click);
					holder.pinglun = (TextView) convertView
							.findViewById(R.id.pinglun);
					holder.dianzan = (TextView) convertView
							.findViewById(R.id.dianzan);
					holder.support = (ImageView) convertView
							.findViewById(R.id.support);
					holder.share = (TextView) convertView
							.findViewById(R.id.share);
					convertView.setTag(holder);
				} else if (type == Listitem.wordType
						&& (convertView.findViewById(R.id.picture)) != null) {
					convertView = LayoutInflater.from(MainActivity.this)
							.inflate(R.layout.word, null);
					holder.img = (ImageView) convertView
							.findViewById(R.id.image);
					holder.name = (TextView) convertView
							.findViewById(R.id.name);
					holder.content = (TextView) convertView
							.findViewById(R.id.content);
					holder.time = (TextView) convertView
							.findViewById(R.id.publish);
					holder.pinglun = (TextView) convertView
							.findViewById(R.id.pinglun);
					holder.support_click = (RelativeLayout) convertView
							.findViewById(R.id.support_click);
					holder.comment_click = (RelativeLayout) convertView
							.findViewById(R.id.comment_click);
					holder.dianzan = (TextView) convertView
							.findViewById(R.id.dianzan);
					holder.support = (ImageView) convertView
							.findViewById(R.id.support);
					holder.share = (TextView) convertView
							.findViewById(R.id.share);
					convertView.setTag(holder);
				}
				holder = (ViewHolder) convertView.getTag();
			}
			holder.img.setImageBitmap(item.getBitmap());
			// holder.img.setOnClickListener(new ShowPicture());
			holder.name.setText(item.getNickName());
			// SpannableString spannableString = new ExpressionUtil()
			// .getExpressionString(getActivity(), item.getContent(),
			// zhengze);
			holder.content.setText(item.getContent());
			if (type == Listitem.picType) {
				holder.picture.setImageBitmap(item.getPicBitmap());
				holder.picture.setOnClickListener(new ShowPicture());
			}
			holder.time.setText(item.getTime());
			holder.pinglun.setText("" + item.getPinglunNum());
			// holder.share.setOnClickListener(new Myshare(item));
			holder.comment_click.setOnClickListener(new PinglunClick(item
					.getMsgID(), position));
			holder.dianzan.setText("" + item.getDianzanNum());
			if (IDfind(item.getMsgID())) {
				holder.support.setBackgroundResource(R.drawable.support_press);
				holder.support_click.setClickable(false);
			} else {
				holder.support_click.setOnClickListener(new Myclicklistenr(item
						.getMsgID(), holder.dianzan));
			}
			return convertView;
		}

		public boolean IDfind(int id) {
			for (int i = 0; i < list.size(); i++) {
				if (id == list.get(i)) {
					return true;
				}
			}
			return false;
		}
	}

	private int position;

	class PinglunClick implements OnClickListener {
		private int id;

		public PinglunClick(int id, int position) {
			this.id = id;
			MainActivity.this.position = position;
		}

		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, CommentActivity.class);
			intent.putExtra("MsgID", id);
			startActivity(intent);
			// startActivityForResult(intent, 1);
		}

	}

	class Myclicklistenr implements OnClickListener {
		private int id;
		private TextView dianzan;

		public Myclicklistenr(int id, TextView dianzan) {
			this.id = id;
			this.dianzan = dianzan;
		}

		public void onClick(View v) {
			v.setClickable(false);
			ImageView support = (ImageView) v.findViewById(R.id.support);
			support.setBackgroundResource(R.drawable.support_press);
			int sum = Integer.parseInt(dianzan.getText().toString());
			sum++;
			dianzan.setText(sum + "");
			// 开启线程去操作数据库，让对应的dianzanNum加1，并记录保存点赞对方的email
			new Dianzan(id, idName).start();
		}
	}

	// 处理点赞事件
	class Dianzan extends Thread {
		private String idName;
		private int id;

		public Dianzan(int id, String idName) {
			this.id = id;
			this.idName = idName;
		}

		@Override
		public void run() {
			String urlStr = "http://"+IP.ip+"/LessonForm/AddDianzan";
			HttpPost request = new HttpPost(urlStr);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String time = df.format(new Date());
			params.add(new BasicNameValuePair("idName", idName));
			params.add(new BasicNameValuePair("id", id + ""));
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

	// 根据用户的id去服务器拿用户自己点赞过的message的ID，制成表单
	// 利用json数组
	class DianzanMsgID extends Thread {
		private String email;

		public DianzanMsgID(String email) {
			this.email = email;
		}

		String url = "http://"+IP.ip+"/LessonForm/dianzanJson";
		private String jsonString;

		@Override
		public void run() {
			synchronized (MainActivity.class.getClass()) {
				HttpPost request = new HttpPost(url);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", idName));
				try {
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpClient client = new DefaultHttpClient();
					HttpResponse response = client.execute(request);
					if (response.getStatusLine().getStatusCode() == 200) {
						jsonString = EntityUtils.toString(response.getEntity());
						// saveObject(jsonString, "dianzanMsgID");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				list = jsonTools.getDianzanMsgID("dianzanMsgID", jsonString);

			}
		}
	}

	static class FileViewHolder {
		public TextView describe;
		public TextView times;
		private TextView uploadTime;
		private TextView name;
		private ImageView download;
	}

	class FileAdapter extends BaseAdapter {
		private ArrayList<FileItem> list;
		private FileViewHolder fileHolder;

		public FileAdapter(ArrayList<FileItem> list) {
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
			FileItem item = list.get(position);
			if (convertView == null) {
				fileHolder = new FileViewHolder();
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.fileitem, null);
				fileHolder.describe = (TextView) convertView
						.findViewById(R.id.describe);
				fileHolder.times = (TextView) convertView
						.findViewById(R.id.times);
				fileHolder.uploadTime = (TextView) convertView
						.findViewById(R.id.uploadTime);
				fileHolder.name = (TextView) convertView
						.findViewById(R.id.uploadname);
				fileHolder.download = (ImageView) convertView
						.findViewById(R.id.download);
				convertView.setTag(fileHolder);
			} else {
				fileHolder = (FileViewHolder) convertView.getTag();
			}
			fileHolder.name.setText(item.getNickName());
			fileHolder.uploadTime.setText(item.getTime());
			fileHolder.describe.setText(item.getDescribe());
			if (item.getTimes() == 0) {
				fileHolder.times.setText("未下载");
			} else {

				fileHolder.times.setText("已下载" + item.getTimes() + "次");
			}
			fileHolder.download.setOnClickListener(new downloadFile(item
					.getLinkPath(), item.getFileID()));
			return convertView;
		}

	}

	class downloadFile implements OnClickListener {
		private String path;
		private int id;

		public downloadFile(String path, int id) {
			filename = path;
			this.id = id;
			this.path = "http://"+IP.ip+"/LessonForm/Files/" + path;
		}

		public void onClick(View v) {
			// 下载文件
			Toast.makeText(getApplicationContext(), "开始下载", 0).show();
			new mytask(id).execute(path);
		}

	}

	class mytask extends AsyncTask<String, Integer, byte[]> {

		private File file;
		private int id;

		public mytask(int id) {
			super();
			this.id = id;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected byte[] doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(arg0[0]);
			byte[] result = null;
			InputStream inputStream = null;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] data = new byte[1024 * 1024];
			int len = 0;
			int total_length = 0;
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				long file_length = httpResponse.getEntity().getContentLength();
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					inputStream = httpResponse.getEntity().getContent();
					while ((len = inputStream.read(data)) != -1) {
						total_length += len;
						int processValue = (int) (total_length / (float) file_length) * 100;
						publishProgress(processValue);
						outputStream.write(data, 0, len);
					}
					// result=EntityUtils.toByteArray(httpResponse.getEntity());
				}
				result = outputStream.toByteArray();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch blockL
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			progressDialog.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(byte[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0,
			// result.length);
			// imageView1.setImageBitmap(bitmap);
			file = new File(filepath);
			if (!file.exists()) {
				file.mkdirs();
			} else {
				File file2 = new File(file, filename);
				try {
					file2.createNewFile();
					OutputStream output = new FileOutputStream(file2);
					BufferedOutputStream bufferedOutput = new BufferedOutputStream(
							output);
					bufferedOutput.write(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			progressDialog.dismiss();
			Toast.makeText(MainActivity.this, "文件保存在根目录的课堂时间文件夹中",Toast.LENGTH_LONG).show();
			new Thread(new Runnable() {
				String url = "http://"+IP.ip+"/LessonForm/saveChangeFile";

				public void run() {

					HttpPost request = new HttpPost(url);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("id", id + ""));
					try {
						// 设置请求参数项
						request.setEntity(new UrlEncodedFormEntity(params,
								HTTP.UTF_8));
						HttpClient client = new MyhttpClient().getHttpClient();
						// 执行请求返回相应
						client.execute(request);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.score:
				Intent intent=new Intent(this,ScoreActivity.class);
				startActivity(intent);
			    
			break;
		case R.id.stop:
			getSharedPreferences("studentInfo", 0).edit().clear().commit();
			ExitApplication.getInstance().exit();
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1) {
			face.setImageBitmap(BitmapFactory.decodeFile(facepath + "/"
					+ idName + ".jpg"));
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 从服务器获取数据
	class InitFilesData extends Thread {
		String url = "http://"+IP.ip+"/LessonForm/FileJson";

		@Override
		public void run() {
			String jsonString = HttpUtils.getJsonString(url);
			ArrayList<FileItem> list = jsonTools.getFiles("Files", jsonString);
			System.out.println(list);
			content3 = list;
			handler.sendEmptyMessage(success_fileData);
		}
		// handler.sendEmptyMessage(0);
	}
}