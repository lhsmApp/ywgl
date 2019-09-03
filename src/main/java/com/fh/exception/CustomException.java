package com.fh.exception;

/**
 * 系统 自定义异常类，针对预期的异常，需要在程序中抛出此类的异常
* @ClassName: CustomException
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年1月17日
*
 */
public class CustomException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//异常信息
	private String message;
	//是否是Ajax异常
	private boolean isAjaxExp;
	
	public CustomException(String message,boolean isAjaxExp){
		super(message);
		this.message = message;
		this.isAjaxExp=isAjaxExp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAjaxExp() {
		return isAjaxExp;
	}

	public void setAjaxExp(boolean isAjaxExp) {
		this.isAjaxExp = isAjaxExp;
	}
}
