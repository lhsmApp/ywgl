package com.fh.entity;

public class LaborTax  {

	private int LEVEL_NO;			
	private double MIN_VALUE;	
	private double MAX_VALUE;			
	private String TAX_FORMULA;		
	public String getTAX_FORMULA() {
		return TAX_FORMULA;
	}
	public void setTAX_FORMULA(String tAX_FORMULA) {
		TAX_FORMULA = tAX_FORMULA;
	}
	public int getLEVEL_NO() {
		return LEVEL_NO;
	}
	public void setLEVEL_NO(int lEVEL_NO) {
		LEVEL_NO = lEVEL_NO;
	}
	public double getMIN_VALUE() {
		return MIN_VALUE;
	}
	public void setMIN_VALUE(double mIN_VALUE) {
		MIN_VALUE = mIN_VALUE;
	}
	public double getMAX_VALUE() {
		return MAX_VALUE;
	}
	public void setMAX_VALUE(double mAX_VALUE) {
		MAX_VALUE = mAX_VALUE;
	}
	
	
}
