package com.consultinggroup.main;

import android.graphics.Bitmap;

public class Listitem {
		public static final int wordType = 1;
		public static final int picType = 2;
		private int type;
		private int MsgID;
		private String time;
		public int getMsgID() {
			return MsgID;
		}

		public void setMsgID(int msgID) {
			MsgID = msgID;
		}

		private Bitmap bitmap;
		private Bitmap picBitmap;
		private String content;
		private String nickName;
		private String picture;
		private String figure;
		private int dianzanNum;
		private int pinglunNum;

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getFigure() {
			return figure;
		}

		public void setFigure(String figure) {
			this.figure = figure;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}

		public int getPinglunNum() {
			return pinglunNum;
		}

		public void setPinglunNum(int pinglunNum) {
			this.pinglunNum = pinglunNum;
		}

		public int getType() {
			return type;
		}


		public Listitem(int type, String time, Bitmap bitmap, String content,
				String nickName, int dianzanNum, int pinglunNum, int ID,
				String picture) {
			this.type = type;
			this.time = time;
			this.bitmap = bitmap;
			this.content = content;
			this.nickName = nickName;
			this.dianzanNum = dianzanNum;
			this.pinglunNum = pinglunNum;
			this.picture = picture;
			this.MsgID=MsgID;
		}
		public Listitem(int type, String time, Bitmap bitmap, String content,
				String nickName, int dianzanNum, int pinglunNum, int MsgID,
				Bitmap picture) {
			this.type = type;
			this.time = time;
			this.bitmap = bitmap;
			this.content = content;
			this.nickName = nickName;
			this.dianzanNum = dianzanNum;
			this.pinglunNum = pinglunNum;
			this.MsgID=MsgID;
			this.picBitmap = picture;
		}
		public Listitem(int type, String time, String figure, String content,
				String nickName, int dianzanNum, int pinglunNum, int MsgID,
				String picture) {
			this.type = type;
			this.time = time;
			this.figure = figure;
			this.content = content;
			this.nickName = nickName;
			this.dianzanNum = dianzanNum;
			this.pinglunNum = pinglunNum;
			this.MsgID = MsgID;
			this.picture = picture;
		}

		public Listitem(int type, String time, Bitmap bitmap, String content,
				String nickName, int dianzanNum, int pinglunNum, int MsgID) {
			this.type = type;
			this.time = time;
			this.bitmap = bitmap;
			this.content = content;
			this.nickName = nickName;
			this.dianzanNum = dianzanNum;
			this.pinglunNum = pinglunNum;
			this.MsgID=MsgID;
		}
		public Listitem(int type, String time, String figure, String content,
				String nickName, int dianzanNum, int pinglunNum, int MsgID) {
			this.type = type;
			this.time = time;
			this.figure = figure;
			this.content = content;
			this.nickName = nickName;
			this.dianzanNum = dianzanNum;
			this.pinglunNum = pinglunNum;
			this.MsgID=MsgID;
		}

		public Bitmap getPicBitmap() {
			return picBitmap;
		}

		public void setPicBitmap(Bitmap picBitmap) {
			this.picBitmap = picBitmap;
		}

		public Listitem(int type) {
			this.type = type;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public Bitmap getBitmap() {
			return bitmap;
		}

		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}


		public int getDianzanNum() {
			return dianzanNum;
		}

		public void setDianzanNum(int dianzanNum) {
			this.dianzanNum = dianzanNum;
		}

}
