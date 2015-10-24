package com.consultinggroup.main;

public class FileItem {

	private String time;
	private String nickName;
	private String describe;
	private String linkPath;
	private int times;
    private int fileID;

	public int getFileID() {
		return fileID;
	}


	public FileItem(String time, String nickName, String describe,
			String linkPath, int times, int fileID) {
		this.time = time;
		this.nickName = nickName;
		this.describe = describe;
		this.linkPath = linkPath;
		this.times = times;
		this.fileID = fileID;
	}


	public void setFileID(int fileID) {
		this.fileID = fileID;
	}


	public FileItem() {
	}


	public FileItem(String time, String nickName, String describe,
			String linkPath, int times) {
		this.time = time;
		this.nickName = nickName;
		this.describe = describe;
		this.linkPath = linkPath;
		this.times = times;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getLinkPath() {
		return linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
}
