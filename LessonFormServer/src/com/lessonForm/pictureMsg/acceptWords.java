package com.lessonForm.pictureMsg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;


public class acceptWords extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name=request.getParameter("name");
		String content=request.getParameter("content");
		content=new String(content.getBytes("iso-8859-1"), "utf-8");
		String time=request.getParameter("time");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(new DaoDealer().savePictureWords(name, content, time)){
			out.print("success");
		}else {
			out.print("fail");
		}
		out.flush();
		out.close();		
	}

}