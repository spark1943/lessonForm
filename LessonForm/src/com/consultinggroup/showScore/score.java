package com.consultinggroup.showScore;

public class score {
   public long courseId;
    public String courseName;
    public String courseType;
    public double credit;
    public String teacherName;
    public String academy;
    public String studyType;
    public String year;
    public String semester;
    public double grade;
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getStudyType() {
		return studyType;
	}
	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public score() {
		super();
		// TODO Auto-generated constructor stub
	}
	public score(long courseId, String courseName, String courseType,
			double credit, String teacherName, String academy,
			String studyType, String year, String semester, double grade) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseType = courseType;
		this.credit = credit;
		this.teacherName = teacherName;
		this.academy = academy;
		this.studyType = studyType;
		this.year = year;
		this.semester = semester;
		this.grade = grade;
	}
    
}
