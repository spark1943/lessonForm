package com.consultinggroup.showScore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.consultinggroup.classtime.R;
import com.consultinggroup.classtime.R.layout;
import com.consultinggroup.classtime.R.menu;
import com.consultinggroup.login.MyDialog;
import com.consultinggroup.tools.IP;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends Activity  implements OnClickListener{

	private TextView scoreTime;
	private TableLayout tableLayout;
	private ArrayList<score> list;
	private ImageView back;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(this);
		scoreTime=(TextView) findViewById(R.id.scoreTime);
		tableLayout=(TableLayout) findViewById(R.id.tableLayout);
		dialog=new MyDialog(this).loadDialog3();
		new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					Looper.prepare();
					getLesson(getSharedPreferences("studentInfo", 0).getString("studentNum", ""));
				    Looper.loop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "获取成绩失败，请重试", 0).show();
				}
			}
		}).start();
	}

	private void init() {
		// TODO Auto-generated method stub
		TableRow tableHead = new TableRow(this);  
        TextView textView1 = new TextView(this);
        textView1.setTextColor(R.color.black);
        textView1.setTextSize(22);
        textView1.setText("课程名");
        textView1.setGravity(Gravity.CENTER);
        TextView textView2 = new TextView(this);
        textView2.setTextColor(R.color.black);
        textView2.setTextSize(22);
        textView2.setGravity(Gravity.CENTER);
        textView2.setText("学年");
        TextView textView33 = new TextView(this);
        textView33.setText(" ");
        TextView textView3 = new TextView(this);
        textView3.setTextColor(R.color.black);
        textView3.setTextSize(22);
        textView3.setGravity(Gravity.CENTER);
        textView3.setText("学期");
        TextView textView44 = new TextView(this);
        textView44.setText(" ");
        TextView textView4 = new TextView(this);
        textView4.setTextSize(22);
        textView4.setGravity(Gravity.CENTER);
        textView4.setTextColor(R.color.black);
        textView4.setText("成绩");
        tableHead.addView(textView1);
        tableHead.addView(textView2);     
        tableHead.addView(textView33);     
        tableHead.addView(textView3);     
        tableHead.addView(textView44);     
        tableHead.addView(textView4);     
        tableLayout.addView(tableHead);
	}
	
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			init();
			for(int i=0;i<list.size();i++){
				TableRow tableRow = new TableRow(ScoreActivity.this);
				TextView textView3 = new TextView(ScoreActivity.this);
				  textView3.setGravity(Gravity.CENTER);
				  textView3.setTextColor(R.color.black);
				textView3.setText(list.get(i).getCourseName());
		        TextView textView8 = new TextView(ScoreActivity.this);
		        textView8.setGravity(Gravity.CENTER);
		        textView8.setTextColor(R.color.black);
		        textView8.setText(list.get(i).getYear());
		        TextView textView9 = new TextView(ScoreActivity.this);
		        textView9.setGravity(Gravity.CENTER);
		        textView9.setTextColor(R.color.black);
		        textView9.setText(list.get(i).getSemester());
		        TextView textView10 = new TextView(ScoreActivity.this);
		        textView10.setGravity(Gravity.CENTER);
		        textView10.setTextColor(R.color.black);
		        textView10.setText(list.get(i).getGrade()+"");
		        TextView textView33 = new TextView(ScoreActivity.this);
		        textView33.setText(" ");
		        TextView textView44 = new TextView(ScoreActivity.this);
		        textView44.setText(" ");
		        tableRow.addView(textView3);     
		        tableRow.addView(textView8);     
		        tableRow.addView(textView33);   
		        tableRow.addView(textView9);     
		        tableRow.addView(textView44);     
		        tableRow.addView(textView10);     
		        tableLayout.addView(tableRow);
				
			}
			dialog.dismiss();
		};
	};
	
	public void getLesson(String name)
			throws Exception {
		String uri="http://"+IP.ip+"/LessonForm/GradeServlet";
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(uri);
		
		NameValuePair namePair=new BasicNameValuePair("studentNum", name);
		List<NameValuePair> paramas=new ArrayList<NameValuePair>();
		paramas.add(namePair);
		post.setEntity(new UrlEncodedFormEntity(paramas,"gb2312"));
		HttpResponse response=client.execute(post);
		
		int status = response.getStatusLine().getStatusCode();
		
		if(status==200){
		
		String result=EntityUtils.toString(response.getEntity(),"gb2312");
//		try {
//			File sd=Environment.getExternalStorageDirectory();
//			File file=new File(sd.getPath()+"/课堂时间");
//			if(!file.exists()){
//				file.mkdir();
//			}
//			File targetFile=new File(file,"score.txt");
//			System.out.println(sd.getPath());
//			FileOutputStream fos=new FileOutputStream(targetFile);
//			PrintStream ps=new PrintStream(fos);
//			ps.print(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		System.out.println(result);
 		JSONTokener jsonParse=new JSONTokener(result);
		
		JSONArray courseArray=(JSONArray)jsonParse.nextValue();
		list = new ArrayList<score>();
		score score;
		JSONObject courseObject;
		for (int i = 0; i < courseArray.length() - 1; i++) {
			courseObject = courseArray.getJSONObject(i);
			score = new score();
			score.setCourseId(courseObject
					.getLong("courseId"));
			score.setCourseName(courseObject.getString("courseName"));
			score.setCourseType(courseObject.getString("courseType"));
			score.setCredit(courseObject.getDouble("credit"));
			score.setTeacherName(courseObject.getString("teacherName"));
			score.setAcademy(courseObject.getString("academy"));
			score.setStudyType(courseObject.getString("studyType"));
			score.setYear(courseObject.getString("year"));
			score.setSemester(courseObject.getString("semester"));
			score.setGrade(courseObject.getDouble("grade"));
			list.add(score);
		}
		handler.sendEmptyMessage(0);
		
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_right_out);
	}

}
