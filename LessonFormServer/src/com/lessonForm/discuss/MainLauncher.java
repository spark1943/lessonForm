package com.lessonForm.discuss;
/**
 * 启动服务器 ChatRoomServer
 *
 */
public class MainLauncher {
	private static final int PORT = 33333; //端口号
	public static void main(String[] args) {
		ChatRoomServer server = new ChatRoomServer(PORT);  //分配端口: 4444
		server.startServer();  //开启服务器
		
		System.out.println("聊天室服务器开启成功!!");
	}
}
