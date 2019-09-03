package com.fh.entity;

public class SysBillOffMapping {
	
//Name	Code	Comment	Default Value	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
//业务类型	TYPE_CODE	"业务类型1工会经费、教育经费凭证2党费凭证3社保互推凭证4公积金互推凭证5个缴凭证6应付劳务费凭证7企业年金提取凭证8补充医疗提取凭证9企业年金发放凭证10评估调整凭证"	' '	VARCHAR(20)	20		TRUE	FALSE	TRUE
//账套编码	BILL_OFF	账套编码	' '	VARCHAR(50)	50		TRUE	FALSE	TRUE
//账套映射编码	MAPPING_CODE	账套映射编码	' '	VARCHAR(50)	50		TRUE	FALSE	TRUE -->

	private String BILL_OFF;
	private String TYPE_CODE;
	private String TYPE_NAME;
	private String MAPPING_CODE;
	public String getTYPE_NAME() {
		return TYPE_NAME;
	}
	public void setTYPE_NAME(String tYPE_NAME) {
		TYPE_NAME = tYPE_NAME;
	}
	public String getTYPE_CODE() {
		return TYPE_CODE;
	}
	public void setTYPE_CODE(String tYPE_CODE) {
		TYPE_CODE = tYPE_CODE;
	}
	public String getBILL_OFF() {
		return BILL_OFF;
	}
	public void setBILL_OFF(String bILL_OFF) {
		BILL_OFF = bILL_OFF;
	}
	public String getMAPPING_CODE() {
		return MAPPING_CODE;
	}
	public void setMAPPING_CODE(String mAPPING_CODE) {
		MAPPING_CODE = mAPPING_CODE;
	}
}