package com.fh.entity;

import java.io.Serializable;

public class TmplConfigDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String RPT_DUR;
	private String DEPT_CODE;
	private String TABLE_CODE;	
	private String COL_CODE;
	private String COL_NAME;
	private Integer DISP_ORDER;
	private String DICT_TRANS;
	private String COL_HIDE;
	private String COL_SUM;
	private String COL_AVE;
	private String COL_TRANSFER;
	private String COL_FORMULA;
	private int CAL_ORDER;
	private String NUM_DGT;
	private String DEC_PRECISION;
	private String BILL_OFF;
	private Boolean IsNum;
	public Boolean getIsNum() {
		return IsNum;
	}
	public void setIsNum(Boolean isNum) {
		IsNum = isNum;
	}
	public TmplConfigDetail(){
	}
	public TmplConfigDetail(String cOL_CODE, String cOL_NAME, String cOL_HIDE, Boolean isNum){
		COL_CODE = cOL_CODE;
		COL_NAME = cOL_NAME;
		COL_HIDE = cOL_HIDE;
		IsNum = isNum;
	}
	
	public String getBILL_OFF() {
		return BILL_OFF;
	}
	public void setBILL_OFF(String bILL_OFF) {
		BILL_OFF = bILL_OFF;
	}
	public int getCAL_ORDER() {
		return CAL_ORDER;
	}
	public void setCAL_ORDER(int cAL_ORDER) {
		CAL_ORDER = cAL_ORDER;
	}
	public String getNUM_DGT() {
		return NUM_DGT;
	}
	public void setNUM_DGT(String nUM_DGT) {
		NUM_DGT = nUM_DGT;
	}
	public String getDEC_PRECISION() {
		return DEC_PRECISION;
	}
	public void setDEC_PRECISION(String dEC_PRECISION) {
		DEC_PRECISION = dEC_PRECISION;
	}
	public String getCOL_FORMULA() {
		return COL_FORMULA;
	}
	public void setCOL_FORMULA(String cOL_FORMULA) {
		COL_FORMULA = cOL_FORMULA;
	}
	public String getRPT_DUR() {
		return RPT_DUR;
	}
	public void setRPT_DUR(String rPT_DUR) {
		RPT_DUR = rPT_DUR;
	}
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}
	public String getTABLE_CODE() {
		return TABLE_CODE;
	}
	public void setTABLE_CODE(String tABLE_CODE) {
		TABLE_CODE = tABLE_CODE;
	}
	public String getCOL_CODE() {
		return COL_CODE;
	}
	public void setCOL_CODE(String cOL_CODE) {
		COL_CODE = cOL_CODE;
	}
	public String getCOL_NAME() {
		return COL_NAME;
	}
	public void setCOL_NAME(String cOL_NAME) {
		COL_NAME = cOL_NAME;
	}
	public Integer getDISP_ORDER() {
		return DISP_ORDER;
	}
	public void setDISP_ORDER(Integer dISP_ORDER) {
		DISP_ORDER = dISP_ORDER;
	}
	public String getDICT_TRANS() {
		return DICT_TRANS;
	}
	public void setDICT_TRANS(String dICT_TRANS) {
		DICT_TRANS = dICT_TRANS;
	}
	public String getCOL_HIDE() {
		return COL_HIDE;
	}
	public void setCOL_HIDE(String cOL_HIDE) {
		COL_HIDE = cOL_HIDE;
	}
	public String getCOL_SUM() {
		return COL_SUM;
	}
	public void setCOL_SUM(String cOL_SUM) {
		COL_SUM = cOL_SUM;
	}
	public String getCOL_AVE() {
		return COL_AVE;
	}
	public void setCOL_AVE(String cOL_AVE) {
		COL_AVE = cOL_AVE;
	}
	public String getCOL_TRANSFER() {
		return COL_TRANSFER;
	}
	public void setCOL_TRANSFER(String cOL_TRANSFER) {
		COL_TRANSFER = cOL_TRANSFER;
	}
	
}