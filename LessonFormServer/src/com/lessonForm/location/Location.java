package com.lessonForm.location;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;
import com.lessonForm.json.JsonTools;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Location extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String location = request.getParameter("location");
		location = new String(location.getBytes("iso-8859-1"), "utf-8");
		String jsonString=JsonTools.createJsonString("schoolName", new DaoDealer().getSchoolName(location));
		 PrintWriter out = response.getWriter();
		 out.write(jsonString);
	}

}
