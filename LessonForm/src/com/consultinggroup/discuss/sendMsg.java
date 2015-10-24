package com.consultinggroup.discuss;

import android.graphics.Bitmap;

public class sendMsg {
     private String id;
     private String message;
     private String time;
     private String nickName;
     private Bitmap bitmap;
     private boolean isCome=true;
     
	public boolean isCome() {
		return isCome;
	}
	public void setCome(boolean isCome) {
		this.isCome = isCome;
	}
	public sendMsg(String id, String message, String time, String nickName,
			Bitmap bitmap, boolean isCome) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.nickName = nickName;
		this.bitmap = bitmap;
		this.isCome = isCome;
	}
	public sendMsg(String id, String message, String time, String nickName,
			Bitmap bitmap) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.nickName = nickName;
		this.bitmap = bitmap;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public sendMsg(String id, String message, String time, String nickName) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.nickName = nickName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
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
