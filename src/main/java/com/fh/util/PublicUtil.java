package com.fh.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP处理
* @ClassName: PublicUtil
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
public class PublicUtil {
	
	public static void main(String[] args) {
		System.out.println("本机的ip=" + PublicUtil.getIp());
	}
	
	public static String getPorjectPath(){
		String nowpath = "";
		nowpath=System.getProperty("user.dir")+"/";
		
		return nowpath;
	}
	
	/**
	 * 获取本机访问地址
	 * @return
	 */
	public static String getIp(){
		String ip = "";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ip = inet.getHostAddress();
			//System.out.println("本机的ip=" + ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return ip;
	}
	
}