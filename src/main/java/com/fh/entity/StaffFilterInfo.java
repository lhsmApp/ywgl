package com.fh.entity;

public class StaffFilterInfo {
	
	private String TYPE_CODE;
	private String BILL_OFF;
	private String DEPT_CODE;
	private String SAL_RANGE;
	private String USER_CODE_STATE;
	private String STAFF_IDENT_STATE;
	public String getBILL_OFF() {
		return BILL_OFF;
	}
	public void setBILL_OFF(String bILL_OFF) {
		BILL_OFF = bILL_OFF;
	}
	public String getTYPE_CODE() {
		return TYPE_CODE;
	}
	public void setTYPE_CODE(String tYPE_CODE) {
		TYPE_CODE = tYPE_CODE;
	}
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}
	public String getSAL_RANGE() {
		return SAL_RANGE;
	}
	public void setSAL_RANGE(String sAL_RANGE) {
		SAL_RANGE = sAL_RANGE;
	}
	public String getUSER_CODE_STATE() {
		return USER_CODE_STATE;
	}
	public void setUSER_CODE_STATE(String uSER_CODE_STATE) {
		USER_CODE_STATE = uSER_CODE_STATE;
	}
	public String getSTAFF_IDENT_STATE() {
		return STAFF_IDENT_STATE;
	}
	public void setSTAFF_IDENT_STATE(String sTAFF_IDENT_STATE) {
		STAFF_IDENT_STATE = sTAFF_IDENT_STATE;
	}
}