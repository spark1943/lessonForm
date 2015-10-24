package com.lessonForm.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lessonForm.javabean.PinglunEnity;
import com.lessonForm.javabean.Student;
import com.lessonForm.javabean.StudyFile;
import com.lessonForm.javabean.message;
import com.lessonForm.javabean.sendMsg;
import com.mysql.jdbc.Connection;

public class DaoDealer {

	public Connection conn;
	private PreparedStatement sql;
	private String uri;
	private int rs;

	public DaoDealer() {
		JoinDriver();
	}

	public void JoinDriver() {
		uri = "jdbc:mysql://127.0.0.1/LessonForm";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(uri, "root",
					"18702713242");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public ArrayList<String> getSchoolName(String location) {
		ArrayList<String> schoolName = new ArrayList<String>();
		String selectsql = "select schoolName from school where location='"
				+ location + "'";
		try {
			sql = conn.prepareStatement(selectsql);
			ResultSet resultSet = sql.executeQuery();
			while (resultSet.next()) {
				schoolName.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schoolName;
	}

	public boolean saveStudentInfo(String id, String studentNum, String pwd,
			String schoolName, String institute, String comeTime) {
		String insertsql = "insert into student(id,studentNum,password,schoolName,institute,comeTime) values('"
				+ id
				+ "','"
				+ studentNum
				+ "','"
				+ pwd
				+ "','"
				+ schoolName
				+ "','" + institute + "','" + comeTime + "')";
		try {
			sql = conn.prepareStatement(insertsql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean SaveChangeData(String id, String nickName,
			String schoolName, String institute, String gender, String like,
			String email) {
		String updatesql = "update student set schoolName='" + schoolName
				+ "',nickName='" + nickName + "',institute='" + institute
				+ "',gender='" + gender + "',hobby='" + like + "',email='"
				+ email + "' where id='" + id + "'";
		try {
			sql = conn.prepareStatement(updatesql);
			int resultSet = sql.executeUpdate();
			if (resultSet != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fssf");
			return false;
		}
	}

	public boolean saveFigurePath(String id, String path) {
		String updatesql = "update student set figure='" + path
				+ "' where id='" + id + "'";
		int resultSet;
		try {
			sql = conn.prepareStatement(updatesql);
			resultSet = sql.executeUpdate();
			if (resultSet != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveWordMessage(String id, String content, String time) {
		String insertsql = "insert into wordMessage(id,content,time) values('"
				+ id + "','" + content + "','" + time + "')";
		try {
			sql = conn.prepareStatement(insertsql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	public boolean saveLYMessage(String id, String lessonName,String name,String content, String time) {
		String insertsql = "insert into message(userID,lessonName,name,content,time) values('"
				+ id + "','"+ lessonName + "','" + name + "','"+ content + "','" + time + "')";
		try {
			sql = conn.prepareStatement(insertsql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	public List<Student> getlistStudent() {
		ResultSet rs;
		String selectsql = "select A.id,A.nickName,A.figure,B.content,B.time,B.dianzanNum,B.pinglunNum,B.MsgID,B.picture from student A,wordMessage B where A.id=B.id";
		try {
			List<Student> list = new ArrayList<Student>();
			sql = conn.prepareStatement(selectsql);
			rs = sql.executeQuery();
			while (rs.next()) {
				Student person = new Student();
				person.setId(rs.getString(1));
				person.setNickName(rs.getString(2));
				person.setFigure(rs.getString(3));
				person.setMessage(rs.getString(4));
				person.setTime(rs.getString(5));
				person.setDianzanNum(rs.getInt(6));
				person.setPinglunNum(rs.getInt(7));
				person.setMsgID(rs.getInt(8));
				person.setPicture(rs.getString(9));
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<StudyFile> getlistFile() {
		ResultSet rs;
		String selectsql = "select time,nickName,describes,linkPath,times,fileID from Files";
		try {
			List<StudyFile> list = new ArrayList<StudyFile>();
			sql = conn.prepareStatement(selectsql);
			rs = sql.executeQuery();
			while (rs.next()) {
				StudyFile person = new StudyFile();
				person.setTime(rs.getString(1));
				person.setNickName(rs.getString(2));
				person.setDescribe(rs.getString(3));
				person.setLinkPath(rs.getString(4));
				person.setTimes(rs.getInt(5));
				person.setFileID(rs.getInt(6));
				list.add(person);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public boolean savePicturesPath(String id, String name) {
		String insertsql = "insert into wordMessage(id,picture) values('" + id
				+ "','" + name + "')";
		int rs;
		try {
			sql = conn.prepareStatement(insertsql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean savePictureWords(String name, String content, String time) {
		String updatesql = "update wordMessage set content='" + content
				+ "',time='" + time + "' where picture='" + name + "'";
		try {
			sql = conn.prepareStatement(updatesql);
			int rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveFile(String time, String nickName, String describe,
			String linkPath) {
		String updatesql = "insert into Files(time,nickName,describes,linkPath) values('"
				+ time
				+ "','"
				+ nickName
				+ "','"
				+ describe
				+ "','"
				+ linkPath
				+ "')";
		try {
			sql = conn.prepareStatement(updatesql);
			int rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Boolean addFileTimes(int id){
		String updatesql = "update Files set times=times+1 where fileID='"+id+"'";
		try {
			sql = conn.prepareStatement(updatesql);
			int rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Integer> getDianzanMsgID(String id){
		String selectsql = "select MsgID from dianzan where id='"
				+ id + "'";
		ResultSet rs;
		ArrayList<Integer> data = new ArrayList<Integer>();
		try {
			sql = conn.prepareStatement(selectsql);
			rs = sql.executeQuery();
			while (rs.next()) {
				data.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public boolean addWordDianzanEmail(int id, String idName, String time) {
		String insertsql = "insert into dianzan values(?,?,?)";
		int rs;
		try {
			sql = conn.prepareStatement(insertsql);
			sql.setInt(1, id);
			sql.setString(2, idName);
			sql.setString(3, time);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean addWordDianzanNum(int id) {
		String updatesql = "update wordmessage set dianzanNum=dianzanNum+1 where MsgID="
				+ id;
		int rs;
		try {
			sql = conn.prepareStatement(updatesql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean addPinglunData(int msgid, String id, String content,
			String time) {
		String insertsql = "insert into pinglun(MsgID,id,content,time) values('"
				+ msgid + "','" + id + "','" + content + "','" + time + "')";
		int rs;
		try {
			sql = conn.prepareStatement(insertsql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean addHuifuData(int msgid, String id, String content,
			String time,String name) {
		String insertsql = "insert into huifu(messageID,id,content,time,name) values('"
				+ msgid + "','" + id + "','" + content + "','" + time +"','" + name + "')";
		int rs;
		try {
			sql = conn.prepareStatement(insertsql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean addPinglunNum(int id) {
		String updatesql = "update wordmessage set pinglunNum=pinglunNum+1,neverReadPinglunSum=neverReadPinglunSum+1 where MsgID='"
				+ id + "'";
		int rs;
		try {
			sql = conn.prepareStatement(updatesql);
			rs = sql.executeUpdate();
			if (rs != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<PinglunEnity> getPinglunDataByMsgID(int MsgID) {
		String selectsql = "select A.nickName,A.figure,B.content,B.time from student A,pinglun B where A.id=B.id and B.MsgID='"
				+ MsgID + "'";
		ResultSet rs;
		ArrayList<PinglunEnity> data = new ArrayList<PinglunEnity>();
		try {
			sql = conn.prepareStatement(selectsql);
			rs = sql.executeQuery();
			while (rs.next()) {
				PinglunEnity enity = new PinglunEnity();
				enity.setUsername(rs.getString(1));
				enity.setFigure(rs.getString(2));
				enity.setPinglunWords(rs.getString(3));
				enity.setPinglunTime(rs.getString(4));
				data.add(enity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
     public boolean saveMsg(String lessonName,String id,String nickName,String message,String time){
    	 String insertsql = "insert into discuss(lessonName,id,nickName,message,time) values('"
 				+ lessonName+"','"+id + "','" + nickName + "','" + message + "','" + time + "')";
 		int rs;
 		try {
 			sql = conn.prepareStatement(insertsql);
 			rs = sql.executeUpdate();
 			if (rs != 0) {
 				return true;
 			} else {
 				return false;
 			}
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		return false;
    	 
     }
     
     public List<sendMsg> getsendMsg(String lessonName) {
 		ResultSet rs;
 		String selectsql = "select id,nickName,message,time from discuss where lessonName='"+lessonName+"'";
 		try {
 			List<sendMsg> list = new ArrayList<sendMsg>();
 			sql = conn.prepareStatement(selectsql);
 			rs = sql.executeQuery();
 			while (rs.next()) {
 				sendMsg person = new sendMsg();
 				person.setId(rs.getString(1));
 				person.setNickName(rs.getString(2));
 				person.setMessage(rs.getString(3));
 				person.setTime(rs.getString(4));
 				list.add(person);
 			}
 			return list;
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		return null;

 	}
     public List<message> Getliuyan(String lessonName) {
    	 ResultSet rs;
    	 String selectsql = "select id,name,content,time,userID from message where lessonName='"+lessonName+"'";
    	 try {
    		 List<message> list = new ArrayList<message>();
    		 sql = conn.prepareStatement(selectsql);
    		 rs = sql.executeQuery();
    		 while (rs.next()) {
    			 message person = new message();
    			 person.setId(rs.getInt(1));
    			 person.setName(rs.getString(2));
    			 person.setContent(rs.getString(3));
    			 person.setTime(rs.getString(4));
    			 person.setUserID(rs.getString(5));
    			 list.add(person);
    		 }
    		 return list;
    	 } catch (SQLException e) {
    		 // TODO Auto-generated catch block
    		 e.printStackTrace();
    	 }
    	 return null;
    	 
     }
     public List<message> getHuifu(int id) {
    	 ResultSet rs;
    	 String selectsql = "select name,content,time,id from huifu where messageID='"+id+"'";
    	 try {
    		 List<message> list = new ArrayList<message>();
    		 sql = conn.prepareStatement(selectsql);
    		 rs = sql.executeQuery();
    		 while (rs.next()) {
    			 message person = new message();
    			 person.setName(rs.getString(1));
    			 person.setContent(rs.getString(2));
    			 person.setTime(rs.getString(3));
    			 person.setUserID(rs.getString(4));
    			 list.add(person);
    		 }
    		 return list;
    	 } catch (SQLException e) {
    		 // TODO Auto-generated catch block
    		 e.printStackTrace();
    	 }
    	 return null;
    	 
     }
}
