package com.fh.util.base;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 说明：网络相关方法
 * 
 * @author yijche
 * @Date 2020-03-18 3:34:22 PM
 *
 */
public class NetCommBase {
	/**
	 * 说明：以POST方式调用URL
	 * 
	 * @param host
	 * @param msg
	 * @return
	 */
	public static int execPostCurl(String host, String msg, String type) throws Exception {
		String path = "http://" + host + "/sendAll";//
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(type); // 设置本次请求的方式 ， 默认是GET方式， 参数要求都是大写字母
		conn.setConnectTimeout(5000);// 设置连接超时
		conn.setDoInput(true);// 是否打开输入流 ， 此方法默认为true
		conn.setDoOutput(true);// 是否打开输出流， 此方法默认为false
		if (type.equals("POST")) {
			OutputStream os = conn.getOutputStream();
			os.write(("msg=" + msg).getBytes());
			os.flush();
		}
		conn.connect();// 表示连接
		return conn.getResponseCode();
	}
}
