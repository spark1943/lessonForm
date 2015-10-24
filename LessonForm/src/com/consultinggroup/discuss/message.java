package com.consultinggroup.discuss;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.consultinggroup.tools.CircleImageView;

public class message {
	private Bitmap bitmap;
	public String name;
	public String content;
	public String lessonName;
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public String time;
	public int id;
	public String userID;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public message(Bitmap bitmap, String name, String content, String time) {
		super();
		this.bitmap = bitmap;
		this.name = name;
		this.content = content;
		this.time = time;
	}
	public message() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
}
