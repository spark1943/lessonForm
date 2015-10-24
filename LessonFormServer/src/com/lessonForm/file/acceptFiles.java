package com.lessonForm.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.DiskFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;

import com.lessonForm.dao.DaoDealer;

public class acceptFiles extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		File fNew;
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String loadpath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "Files"; // 上传文件存放目录
		DiskFileUpload fu = new DiskFileUpload();
		fu.setSizeMax(1 * 1024 * 1024*100); // 设置允许用户上传文件大小,单位:字节
		fu.setSizeThreshold(4096); // 设置最多只允许在内存中存储的数据,单位:字节
		// 开始读取上传信息
		int index = 0;
		List fileItems = null;

		try {
			fileItems = fu.parseRequest(request);
			System.out.println("fileItems=" + fileItems);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator iter = fileItems.iterator(); // 依次处理每个上传的文件
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();// 忽略其他不是文件域的所有表单信息
			if (!item.isFormField()) {
				String name = item.getName();// 获取上传文件名,包括路径
				System.out.println(name);
				name =new String(name.getBytes("iso-8859-1"), "utf-8");
				System.out.println(name);
				name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
				long size = item.getSize();
				if ((name == null || name.equals("")) && size == 0)
					continue;
				index++;
				fNew = new File(loadpath, name);
				try {
					item.write(fNew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		out.print("success");
		out.flush();
		out.close();

	}
}
