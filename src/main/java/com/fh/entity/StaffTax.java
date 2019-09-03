package com.fh.entity;

public class StaffTax  {

	private int LEVEL_NO;			
	private double MIN_VALUE;	
	private double MAX_VALUE;			
	private double TAX_RATE;		
	private double QUICK_DEDUCTION;
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
	public double getTAX_RATE() {
		return TAX_RATE;
	}
	public void setTAX_RATE(double tAX_RATE) {
		TAX_RATE = tAX_RATE;
	}
	public double getQUICK_DEDUCTION() {
		return QUICK_DEDUCTION;
	}
	public void setQUICK_DEDUCTION(double qUICK_DEDUCTION) {
		QUICK_DEDUCTION = qUICK_DEDUCTION;
	}	
	
	
}
