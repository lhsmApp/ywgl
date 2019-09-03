package com.fh.entity;

public class SysTableMapping {
	
//Name	Code	Comment	Default Value	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
//业务类型	TYPE_CODE	"业务类型 1工会经费、教育经费凭证 2党费凭证3社保互推凭证4公积金互推凭证5个缴凭证6应付劳务费凭证7企业年金提取凭证8补充医疗提取凭证9企业年金发放凭证10评估调整凭证"	' '	VARCHAR(20)	20		TRUE	FALSE	TRUE
//业务期间	BUSI_DATE	业务期间	‘ ’	VARCHAR(8)	8		TRUE	FALSE	TRUE
//业务表	TABLE_NAME	业务表	' '	VARCHAR(30)	30		TRUE	FALSE	TRUE
//映射业务表	TABLE_NAME_MAPPING	映射业务表	' '	VARCHAR(30)	30		FALSE	FALSE	TRUE
//状态	STATE	"状态1封存,0解封"	' '	CHAR(1)	1		FALSE	FALSE	TRUE
//账套	BILL_OFF		‘ ’	VARCHAR(6)	6		TRUE	FALSE	TRUE -->
	
	private String TYPE_CODE;
	private String BUSI_DATE;
	private String TABLE_NAME;
	private String TABLE_NAME_MAPPING;
	private String STATE;
	private String BILL_OFF;
	/*private String TABLE_TYPE;
	public String getTABLE_TYPE() {
		return TABLE_TYPE;
	}
	public void setTABLE_TYPE(String tABLE_TYPE) {
		TABLE_TYPE = tABLE_TYPE;
	}*/
	public String getTYPE_CODE() {
		return TYPE_CODE;
	}
	public void setTYPE_CODE(String tYPE_CODE) {
		TYPE_CODE = tYPE_CODE;
	}
	public String getBUSI_DATE() {
		return BUSI_DATE;
	}
	public void setBUSI_DATE(String bUSI_DATE) {
		BUSI_DATE = bUSI_DATE;
	}
	public String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	public String getTABLE_NAME_MAPPING() {
		return TABLE_NAME_MAPPING;
	}
	public void setTABLE_NAME_MAPPING(String tABLE_NAME_MAPPING) {
		TABLE_NAME_MAPPING = tABLE_NAME_MAPPING;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getBILL_OFF() {
		return BILL_OFF;
	}
	public void setBILL_OFF(String bILL_OFF) {
		BILL_OFF = bILL_OFF;
	}

}