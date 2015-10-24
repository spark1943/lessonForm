package com.lessonForm.javabean;

public class sendMsg {
     private String id;
     private String message;
     private String time;
     private String nickName;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public sendMsg(String id, String message, String time, String nickName) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.nickName = nickName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public sendMsg() {
		// TODO Auto-generated constructor stub
	}
	public sendMsg(String id, String message, String time) {
		this.id = id;
		this.message = message;
		this.time = time;
	}
     
}
