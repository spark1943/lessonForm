package com.lessonForm.lesson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.mysql.jdbc.Field;

@SuppressWarnings("deprecation")
public class Spider {
	public  void getWebResource(String stu_id,String stu_pwd,
			String checkCode,String session,HttpServletRequest request) {
		final String LOGIN_URL = "http://210.42.121.132/servlet/Login";
		final String SCORE_URL="http://210.42.121.132/servlet/Svlt_QueryStuScore?year=0&term=&learnType=&scoreFlag=0&t=" 
		+ System.currentTimeMillis();
		final String LESSON_URL = "http://210.42.121.132/servlet/Svlt_QueryStuLsn?action=queryStuLsn&term=%C9%CF&year=2015";
		final String SAVE_PATH = request.getSession().getServletContext()
				.getRealPath("/")
				+ "Lessons"; // 上传文件 存放目录;
		String id = stu_id;
		String pwd = stu_pwd;
		String xdvfb =checkCode;
		String stu_lesson=stu_id+"_lesson.html";
		final String stu_score=stu_id+"_score.html";
		final String fsession=session;
		
		final HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(LOGIN_URL);
		post.addRequestHeader("Cookie",session);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultMethodRetryHandler());
		org.apache.commons.httpclient.NameValuePair[] data = {
				new NameValuePair("id", id), new NameValuePair("pwd", pwd),
				new NameValuePair("xdvfb", xdvfb) };
		post.setRequestBody(data);
		try {
			int statusCode = httpClient.executeMethod(post);
			if(statusCode==HttpStatus.SC_OK)return;
			if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statusCode == HttpStatus.SC_SEE_OTHER)
					|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				GetMethod getMethod = new GetMethod(LESSON_URL);
				getMethod.addRequestHeader("Cookie",session);
				httpClient.executeMethod(getMethod);
				String lesson=getMethod.getResponseBodyAsString();
				 saveAsHtmlFile(SAVE_PATH+File.separator+stu_lesson,lesson);
				 Thread thread=new Thread(new Runnable() {
					
					@Override
					public void run() {
		
						 try {
							 GetMethod scoreGetMethod=new GetMethod(SCORE_URL);
							 scoreGetMethod.addRequestHeader("Cookie",fsession);
							httpClient.executeMethod(scoreGetMethod);
							String scoreString =scoreGetMethod.getResponseBodyAsString();
							 saveAsHtmlFile(SAVE_PATH+File.separator+stu_score,scoreString);
						} catch (HttpException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						  
					}
				});
				 thread.start();
				//InputStream in=getMethod.getResponseBodyAsStream();
				//BufferedReader reader=new BufferedReader(new InputStreamReader(in,"gb2312"));
				//String msg=null;
				//while((msg=reader.readLine())!=null) {
				//	 saveAsHtmlFile(SAVE_PATH+"//lesson.html", msg);
				//}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		post.releaseConnection();
	}
	   private void saveAsHtmlFile(String path, String content) throws IOException {
	        File file = new File(path);
	        if (!file.exists()){ 
	        	file.createNewFile();
	        	}
	        FileWriter writer = new FileWriter(file);
	        writer.write(content);
	        writer.close();
	    }
}
