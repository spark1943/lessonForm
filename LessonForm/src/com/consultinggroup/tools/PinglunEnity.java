package com.consultinggroup.tools;

import android.graphics.Bitmap;

public class PinglunEnity {

	private String username;
	private String figure;
	private String pinglunWords;
	private String pinglunTime;
    private Bitmap bitmap;
    
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public PinglunEnity(String pinglunTime, String pinglunWords,
			String username, Bitmap bitmap) {
		this.username = username;
		this.pinglunWords = pinglunWords;
		this.pinglunTime = pinglunTime;
		this.bitmap = bitmap;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFigure() {
		return figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}

	public String getPinglunWords() {
		return pinglunWords;
	}

	public void setPinglunWords(String pinglunWords) {
		this.pinglunWords = pinglunWords;
	}

	public String getPinglunTime() {
		return pinglunTime;
	}

	public void setPinglunTime(String pinglunTime) {
		this.pinglunTime = pinglunTime;
	}

	public PinglunEnity(String username, String figure, String pinglunWords,
			String pinglunTime) {
		this.username = username;
		this.figure = figure;
		this.pinglunWords = pinglunWords;
		this.pinglunTime = pinglunTime;
	}

	public PinglunEnity() {
	}

}
