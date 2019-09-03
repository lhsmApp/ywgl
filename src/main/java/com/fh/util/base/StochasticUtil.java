package com.fh.util.base;

import java.util.Random;
import java.util.UUID;

/**
 * 生成随机数字/字符串/uuid等工具
* @ClassName: StochasticUtil
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年1月16日
*
 */
public class StochasticUtil {

	/**
	 * 随机整数
	 * @param num
	 * @return
	 */
	public static int getRandom(int num){
		Random random = new Random();
		return random.nextInt(num);
	}
	
	
	/**
	 * 随机字母
	 * @param num
	 * @return
	 */
	public static String getRandom(double num){
		String pool = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		String str = "";
		for (long i = 0; i < num; i++) {
			int j = new Random().nextInt(pool.length());
			str += pool.charAt(j);
		}
		return str;
	}
	
	
	/**
	 * 生成随机UUID
	 * @return
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	
	/**
	 * 根据字符串生成固定uuid
	 * @param name
	 * @return
	 */
	public static String getUUID(String name){
		UUID uuid=UUID.nameUUIDFromBytes(name.getBytes());
	    return uuid.toString().replace("-", "");
	}
}
