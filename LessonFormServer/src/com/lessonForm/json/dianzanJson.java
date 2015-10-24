package com.lessonForm.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;


public class dianzanJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String id=request.getParameter("id");
			ArrayList<Integer> list=new DaoDealer().getDianzanMsgID(id);
	        String msg=JsonTools.createJsonString("dianzanMsgID", list);
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.print(msg);
	        out.flush();
	        out.close();
	}

}
