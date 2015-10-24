package com.lessonForm.lesson;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by KevinTse on 5/26/15.
 */
public class Lesson {
	public class Course {
		public String lessonName;
		public String day;
		public String beginTime;
		public String endTime;
		public String beginWeek;
		public String endWeek;
		public String detail;
		public String classRoom;
		public String weekInterVal;
		public String teacherName;
		public String professionName;
		public String planType;
		public String credit;
		public String areaName;
		public String academicTeach;
		public String grade;
		public String classNote;
		public String note;
		public String state;
	}

	public List<Course> getCourses(String fileName,HttpServletRequest request) {
		try {
			final String path=request.getSession().getServletContext()
					.getRealPath("/")
					+ "Lessons"; // 上传文件 存放目录;;
			File file = new File(path+File.separator+fileName+"_lesson.html");
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), "gb2312"));
			StringBuilder html = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				html.append(line);
				html.append('\n');
			}
			return getCourse(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Course> getCourse(String html) {
		try {
			List<String> lessonName = readText(html, "lessonName");
			List<String> day = readText(html, "day");
			List<String> beginTime = readText(html, "beginTime");
			List<String> endTime = readText(html, "endTime");
			List<String> beginWeek = readText(html, "beginWeek");
			List<String> endWeek = readText(html, "endWeek");
			List<String> detail = readText(html, "detail");
			List<String> weekInterVal = readText(html, "weekInterVal");
			List<String> classRoom = readText(html, "classRoom");
			List<String> teacherName = readText(html, "teacherName");
			List<String> professionName = readText(html, "professionName");
			List<String> planType = readText(html, "planType");
			List<String> credit = readText(html, "credit");
			List<String> areaName = readText(html, "areaName");
			List<String> academicTeach = readText(html, "academicTeach");
			List<String> grade = readText(html, "grade");
			List<String> classNote = readText(html, "classNote");
			List<String> note = readText(html, "note");
			List<String> state = readText(html, "state");
			List<Course> result = new ArrayList<>();
			Course course;
			for (int i = 0; i < lessonName.size(); i++) {
				course = new Course();
				course.lessonName = (String) lessonName.get(i);
				course.day = (String) day.get(i);
				course.beginTime = (String) beginTime.get(i);
				course.endTime = (String) endTime.get(i);
				course.beginWeek = (String) beginWeek.get(i);
				course.endWeek = (String) endWeek.get(i);
				course.detail = (String) detail.get(i);
				course.weekInterVal = (String) weekInterVal.get(i * 2);
				course.classRoom = (String) classRoom.get(i);
				course.teacherName = (String) teacherName.get(i);
				course.professionName = (String) professionName.get(i);
				course.planType = (String) planType.get(i);
				course.credit = (String) credit.get(i);
				course.areaName = (String) areaName.get(i);
				course.academicTeach = (String) academicTeach.get(i);
				course.grade = (String) grade.get(i);
				course.classNote = (String) classNote.get(i * 2);
				course.note = (String) note.get(i);
				course.state = (String) state.get(i);
				result.add(course);
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<String> readText(String html, String keyWord) throws Exception {
		StringReader sr = new StringReader(html);
		BufferedReader br = new BufferedReader(sr);
		String aimLine;
		List<String> myList = new ArrayList<String>();
		String temp = "";
		while (temp != null) {
			temp = br.readLine();
			String regex = ".*var +" + keyWord + " *= *\".*\".*";
			if (temp != null && temp.matches(regex)) {
				aimLine = temp;
				String[] sa = aimLine.split("\"");
				myList.add(sa[1]);

			}
		}
		return myList;

	}
	public Course getCourse() {
		return new Course();
	}
	// public String getHtml() throws IOException {
	// File file = new File(
	// "C://Users//hgf//Desktop//WebResource//lesson.html");
	// BufferedReader bfReader = new BufferedReader(new InputStreamReader(
	// new FileInputStream(file), "utf-8"));
	// StringBuilder sBuilder = new StringBuilder();
	// String line;
	// while ((line = bfReader.readLine()) != null) {
	// sBuilder.append(line);
	// sBuilder.append('\n');
	// }
	// return sBuilder.toString();
	// }
}
