package com.lessonForm.lesson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetLesson extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		String name = request.getParameter("studentNum");
		String pwd = request.getParameter("pwd");
		String check = request.getParameter("check");
		String session = request.getParameter("session");
		JSONObject checkObject = new JSONObject();
		JSONObject course;
		JSONArray courseArr = new JSONArray();
		//Spider spider = new Spider();
		//spider.getWebResource(name, pwd, check, session,request);
		Lesson lesson = new Lesson();
		List<Lesson.Course> courseList = lesson.getCourses(name,request);
		if (courseList == null) {
			checkObject.put("check", "false");
		} else {

			checkObject.put("check", "true");

			// System.out.println(lesson.getHtml());
			for (int i = 0; i < courseList.size(); i++) {
				course = new JSONObject();
				course.put("lessonName", courseList.get(i).lessonName);
				course.put("academicTeach", courseList.get(i).academicTeach);
				course.put("areaName", courseList.get(i).areaName);
				course.put("beginTime", courseList.get(i).beginTime);
				course.put("beginWeek", courseList.get(i).beginWeek);
				course.put("classNote", courseList.get(i).classNote);
				course.put("classRoom", courseList.get(i).classRoom);
				course.put("credit", courseList.get(i).credit);
				course.put("day", courseList.get(i).day);
				course.put("detail", courseList.get(i).detail);
				course.put("endTime", courseList.get(i).endTime);
				course.put("endWeek", courseList.get(i).endWeek);
				course.put("grade", courseList.get(i).grade);
				course.put("note", courseList.get(i).note);
				course.put("planType", courseList.get(i).planType);
				course.put("professionName", courseList.get(i).professionName);
				course.put("state", courseList.get(i).state);
				course.put("teacherName", courseList.get(i).teacherName);
				course.put("weekInterVal", courseList.get(i).weekInterVal);
				courseArr.add(course);
			}
		}
		courseArr.add(checkObject);
		String resource = courseArr.toString();
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		out.print(resource);
		out.flush();
		out.close();
	}
}
