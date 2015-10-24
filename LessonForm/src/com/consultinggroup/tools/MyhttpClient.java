package com.consultinggroup.tools;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class MyhttpClient {
	private static final int REQUEST_TIMEOUT = 5 * 1000;// 设置请求超时10秒钟
	private static final int SO_TIMEOUT = 10 * 1000; // 设置等待数据超时时间10秒钟
	public HttpClient getHttpClient() {
	BasicHttpParams httpParams = new BasicHttpParams();
	HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
	HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
	HttpClient client = new DefaultHttpClient(httpParams);
	return client;
	}
}
