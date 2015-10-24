<%@page import="com.lessonForm.dao.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String lessonName = request.getParameter("lessonName");
	String content = request.getParameter("content");
	String time = request.getParameter("time");
	if (id != null && content != null && time != null) {
		content = new String(content.getBytes("iso-8859-1"), "utf-8");
		time = new String(time.getBytes("iso-8859-1"), "utf-8");
		name = new String(name.getBytes("iso-8859-1"), "utf-8");
		lessonName = new String(lessonName.getBytes("iso-8859-1"), "utf-8");
	}
	if (new DaoDealer().saveLYMessage(id, lessonName,name,content, time)) {
	     out.print("success");
	}
%>


