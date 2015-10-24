package com.lessonForm.studentInfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;

public class SaveChangeData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String schoolName = request.getParameter("schoolName");
		schoolName=new String(schoolName.getBytes("iso-8859-1"), "utf-8");
		String nickName = request.getParameter("nickName");
		nickName=new String(nickName.getBytes("iso-8859-1"), "utf-8");
		String institute = request.getParameter("institute");
		institute=new String(institute.getBytes("iso-8859-1"), "utf-8");
		String gender = request.getParameter("gender");
		gender=new String(gender.getBytes("iso-8859-1"), "utf-8");
		String like = request.getParameter("like");
		like=new String(like.getBytes("iso-8859-1"), "utf-8");
		String email = request.getParameter("email");
		String id = request.getParameter("id");
		new DaoDealer().SaveChangeData(id, nickName,schoolName, institute, gender, like, email);
	}

}
