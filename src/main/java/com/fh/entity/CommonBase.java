package com.fh.entity;

/**
 * 所有Po类继承基类，用于同一返回结果 码和错误信息
* @ClassName: CommonBase
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年1月17日
*
 */
public class CommonBase {
	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
