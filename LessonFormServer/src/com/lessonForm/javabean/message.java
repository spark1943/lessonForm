package com.lessonForm.javabean;



public class message {
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
	public message(String name, String content, String time) {
		super();
		this.name = name;
		this.content = content;
		this.time = time;
	}
	public message() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
}
