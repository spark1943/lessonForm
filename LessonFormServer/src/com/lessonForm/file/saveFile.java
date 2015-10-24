package com.lessonForm.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;

public class saveFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String time = request.getParameter("time");
		String nickName = request.getParameter("nickName");
		nickName=new String(nickName.getBytes("iso-8859-1"), "utf-8");
		String describe = request.getParameter("describe");
		describe=new String(describe.getBytes("iso-8859-1"), "utf-8");
		String linkPath = request.getParameter("linkPath");
		linkPath=new String(linkPath.getBytes("iso-8859-1"), "utf-8");
		new DaoDealer().saveFile(time, nickName, describe, linkPath);
		
	}

}
