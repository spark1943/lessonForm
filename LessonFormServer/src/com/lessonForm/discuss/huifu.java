package com.lessonForm.discuss;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;

/**
 * Servlet implementation class huifu
 */
public class huifu extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    int msgid=Integer.parseInt(request.getParameter("id"));
		    String id=request.getParameter("userID");
		    String content=request.getParameter("content");
		    content=new String(content.getBytes("iso-8859-1"), "utf-8");
		    String name=request.getParameter("name");
		    name=new String(name.getBytes("iso-8859-1"), "utf-8");
		    String time=request.getParameter("time");
		    response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
			DaoDealer dao = new DaoDealer();
			dao.addHuifuData(msgid, id, content, time,name);
		}
}
