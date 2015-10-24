package com.consultinggroup.main;

import android.R.integer;

public class ClassInfo {
	/**
		 * 
		 */
	private int fromX;
	private int fromY;
	private int toX;
	private int toY;
	
	private String lessonName;// 课程名
	private int day;// 星期几上课 服务器发送的数据是数字，需转化为星期
	private int beginTime;// 上课时间，从第几节课开始
	private int endTime;// 上课时间，到第几节课结束
	private int classLen;
	private int beginWeek; // 上课时间，从第几周开始
	private int endWeek;// 上课时间，到第几周结束
	private String detail;// 课程的详细信息
	private String classRoom;// 教室编号
	private String weekInterVal;// 隔几周一次
	private String teacherName;// 任课老师
	private String professionName;// 教师职称
	private String planType;// 课程类型，
	private String credit;// 课程学分
	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public int getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = Integer.parseInt(day);
	}

	public int getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime =Integer.parseInt(beginTime);
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = Integer.parseInt(endTime);
	}

	public int getBeginWeek() {
		return beginWeek;
	}

	public void setBeginWeek(String beginWeek) {
		this.beginWeek =Integer.parseInt(beginWeek);
	}

	public int getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(String endWeek) {
		this.endWeek = Integer.parseInt(endWeek);
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getWeekInterVal() {
		return weekInterVal;
	}

	public void setWeekInterVal(String weekInterVal) {
		this.weekInterVal = weekInterVal;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAcademicTeach() {
		return academicTeach;
	}

	public void setAcademicTeach(String academicTeach) {
		this.academicTeach = academicTeach;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassNote() {
		return classNote;
	}

	public void setClassNote(String classNote) {
		this.classNote = classNote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private String areaName;// 教室地点
	private String academicTeach;// 授课学院
	private String grade;// 年级
	private String classNote;// 班级
	private String note;
	private String state;// 描述课程缴费状态

	// stateStr;
	// if(state == 0){
	// stateStr = '未结算';
	// }else if(state == 12 || state == 5){
	// stateStr = '待缴费';
	// }else if(state == 11){
	// stateStr = '待确认';
	// }else if(state == 9 ||state == 13 || state == 15 || state == 17){
	// stateStr = '已确认';
	// }

	public void setPoint(int fromX, int fromY, int toX, int toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}

	public int getFromX() {
		return fromX;
	}

	public void setFromX(int fromX) {
		this.fromX = fromX;
	}

	public int getFromY() {
		return fromY;
	}

	public void setFromY(int fromY) {
		this.fromY = fromY;
	}

	public int getToX() {
		return toX;
	}

	public void setToX(int toX) {
		this.toX = toX;
	}

	public int getToY() {
		return toY;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}

	

	public int getClassLen() {
		return classLen;
	}

	public void setClassLen() {
		this.classLen =this.endTime-this.beginTime+1;
				}
	
	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

}
