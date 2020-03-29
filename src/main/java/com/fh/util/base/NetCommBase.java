package com.fh.util.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 说明：网络相关方法
 * @author yijche
 * @Date 2020-03-18 3:34:22 PM
 *
 */
public class NetCommBase {
	/**
	 * 说明：以POST方式调用URL
	 * @param host
	 * @param msg
	 * @return
	 */
	public static String execPostCurl(String host, String msg) throws Exception {
		// 这要改成配置文件形式
		String[] cmds = { "curl", "-XPOST", "http://" + host + "/sendAll", "-d", "msg=" + msg };// 必须分开写，不能有空格
		ProcessBuilder process = new ProcessBuilder(cmds);
		Process p;
		try {
			p = process.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();

		} catch (IOException e) {
			System.out.print("error");
			e.printStackTrace();
		}
		return null;
	}
}
