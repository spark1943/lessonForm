package com.lessonForm.studentInfo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;

public class StudentInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String schoolName = request.getParameter("schoolName");
		schoolName=new String(schoolName.getBytes("iso-8859-1"), "utf-8");
		String institute = request.getParameter("institute");
		institute=new String(institute.getBytes("iso-8859-1"), "utf-8");
		String comeTime = request.getParameter("comeTime");
		String studentNum = request.getParameter("studentNum");
		String pwd = request.getParameter("pwd");
		String id=GenerateID.getFormID();
	    boolean flag=new DaoDealer().saveStudentInfo(id,studentNum,pwd,schoolName, institute, comeTime);
	    PrintWriter out = response.getWriter();
	    if(flag){
			 out.write(id);
	    }else {
	    	 out.write("fail");
		}
	    out.flush();
	    out.close();
	}

}
