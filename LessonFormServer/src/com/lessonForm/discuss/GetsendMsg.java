package com.lessonForm.discuss;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;
import com.lessonForm.json.JsonTools;

public class GetsendMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("kkkkk");
		String lessonName=request.getParameter("lessonName");
		System.out.println(lessonName);
		lessonName=new String(lessonName.getBytes("iso-8859-1"), "utf-8");
		System.out.println(lessonName);
		String jsonString=JsonTools.createJsonString("sendMsg", new DaoDealer().getsendMsg(lessonName));
		response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
        out.flush();
        out.close();
	}

}
