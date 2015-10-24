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

public class GradeServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
     doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		String name=request.getParameter("studentNum");
		System.out.println(name);
		Grade grade=new Grade();
		List<Score> list=grade.getScore(request,name);
		JSONObject gradeObject;
		JSONArray gradeArray=new JSONArray();
		for(int i=0;i<list.size();i++) {
			gradeObject=new JSONObject();
			gradeObject.put("courseId", list.get(i).courseId);
			gradeObject.put("academy", list.get(i).academy);
			gradeObject.put("courseName", list.get(i).courseName);
			gradeObject.put("courseType", list.get(i).courseType);
			gradeObject.put("credit", list.get(i).credit);
			gradeObject.put("grade", list.get(i).grade);
			gradeObject.put("semester", list.get(i).semester);
			gradeObject.put("studyType", list.get(i).studyType);
			gradeObject.put("teacherName", list.get(i).teacherName);
			gradeObject.put("year", list.get(i).year);
			gradeArray.add(gradeObject);
		}
		String resource=gradeArray.toString();
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		out.print(resource);
		out.flush();
		out.close();
	}

}
