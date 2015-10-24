package com.lessonForm.javabean;

public class Student {

	private String nickName;
	private String gender;
	private String studentNum;
	private String password;  
	private String schoolName;
	private String institute;
	private String comeTime;
	private String like;
	private String id;
	private int MsgID;
	private int dianzanNum,pinglunNum,shareNum;
	private String figure;
	private String picture;
	private String message;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	private String time;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getDianzanNum() {
		return dianzanNum;
	}
	public void setDianzanNum(int dianzanNum) {
		this.dianzanNum = dianzanNum;
	}
	public int getPinglunNum() {
		return pinglunNum;
	}
	public void setPinglunNum(int pinglunNum) {
		this.pinglunNum = pinglunNum;
	}
	public String getFigure() {
		return figure;
	}
	public void setFigure(String figure) {
		this.figure = figure;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMsgID() {
		return MsgID;
	}
	public void setMsgID(int msgID) {
		MsgID = msgID;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Student() {
	}
	public Student(String studentNum, String password, String schoolName) {
		this.studentNum = studentNum;
		this.password = password;
		this.schoolName = schoolName;
	}
	public Student(String studentNum, String password, String schoolName,
			String institute, String comeTime) {
		this.studentNum = studentNum;
		this.password = password;
		this.schoolName = schoolName;
		this.institute = institute;
		this.comeTime = comeTime;
	}
	public Student(String nickName, String gender, String studentNum,
			String password, String schoolName, String institute,
			String comeTime, String like) {
		this.nickName = nickName;
		this.gender = gender;
		this.studentNum = studentNum;
		this.password = password;
		this.schoolName = schoolName;
		this.institute = institute;
		this.comeTime = comeTime;
		this.like = like;
	}
	public Student(String schoolName, String institute, String comeTime,
			String id) {
		this.schoolName = schoolName;
		this.institute = institute;
		this.comeTime = comeTime;
		this.id = id;
	}
	public Student(String id) {
		this.id = id;
	}
	
}
