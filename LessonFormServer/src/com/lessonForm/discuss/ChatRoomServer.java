package com.lessonForm.discuss;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.lessonForm.dao.DaoDealer;

/**
 * 聊天室 Server
 *
 */
public class ChatRoomServer {
	private ServerSocket ss;
	private int port; //端口
	private List<WorkThread> list;  //处理客户端消息的所有线程
	public ChatRoomServer(int port) {
		this.port = port;
		list = new ArrayList<WorkThread>();
	}
	
	//启动服务器
	public void startServer() {
		new Thread(){
			public void run() {
				try {
					ss = new ServerSocket(port);  //开启服务端s
					while(true) {
						Socket s = ss.accept();  //不停的接收客户端请求
						WorkThread wt = new WorkThread(s); //创建一个线程处理客户端消息
						wt.start();  //启动该线程
						list.add(wt);  //添加到 list 
					}
				} catch (Exception e) {
					System.out.println("服务器断开连接..");
				}
			};
		}.start();
	}
	
	//处理客户端消息的线程
	class WorkThread extends Thread {
		private Socket s;  //连接进来的客户端
		private String name;  //客户端名字
		private BufferedReader in;  //输入流,接收数据
		private PrintWriter out;  //输出流,发送数据s
		public WorkThread(Socket s) throws Exception{
			this.s = s;
			in = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
		}
		
		
		//发送消息
		public void sendMsg(String msg) {
			out.println(msg);
			out.flush();
		}
		
		//发送广播,给在线的每个客户端发送消息
		public void sendToAll(String msg) {
			if(list != null) {
				//遍历 list,调用每个 WorkThread 自身的 sendMsg 方法
				for(WorkThread wt: list) {
					wt.sendMsg(msg);
				}
			}
		}
		
//		//发送广播,当前在线人数
//		public  void sendOnlineCount(String msg,int onlineCount) {
//			sendToAll(msg +"&" +onlineCount);
//		}
		
		//run() 方法主要用于不断接收客户端发送过来的消息,在分发出去
		@Override
		public void run() {
			try {
				//首先获取连接进来的客户端的名字
				name = in.readLine();
				System.out.println(name);
				//发送广播, xxx 进入聊天室
				sendToAll(name);
//				String temp=name.substring(0, name.indexOf("?"));
				//广播当前在线人数
//				sendOnlineCount(temp,list.size());
				
				//然后不断的监听客户端发送过来的消息
				String msg;  //用于保存每条消息内容
				while((msg = in.readLine()) != null) {
					sendToAll(msg);
					String id=msg.substring(0, msg.indexOf("/"));
					String message=msg.substring(msg.indexOf("/")+1, msg.indexOf("*"));
					String nickName=msg.substring(msg.indexOf("*")+1, msg.indexOf("?"));
					String time=msg.substring(msg.indexOf("?")+1,msg.indexOf("&"));
					String lessonName=msg.substring(msg.indexOf("&")+1);
					System.out.println(lessonName+"llll");
					new DaoDealer().saveMsg(lessonName,id,nickName,message,time);
					System.out.println(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//如果发送异常或者 收到的消息为 null,则为网络异常,将当前客户端的 WorkThread 移除 list
			list.remove(this);
		}
	}
}
