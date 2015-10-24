package com.lessonForm.dianzan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lessonForm.dao.DaoDealer;


public class AddDianzan extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 int id=Integer.parseInt(request.getParameter("id"));
         String idName=request.getParameter("idName");
         String time=request.getParameter("time");
		//²Ù×÷Êý¾Ý¿â
		DaoDealer daoDealer=new DaoDealer();
		if(daoDealer.addWordDianzanEmail(id, idName,time)){
			daoDealer.addWordDianzanNum(id);
		}
	}

}
