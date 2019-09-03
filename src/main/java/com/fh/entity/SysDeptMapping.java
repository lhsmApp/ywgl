package com.fh.entity;

public class SysDeptMapping {
	
//Name	Code	Comment	Default Value	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
//业务类型	TYPE_CODE	"业务类型  1工会经费、教育经费凭证,2党费凭证,3社保互推凭证,4公积金互推凭证,5个缴凭证,6应付劳务费凭证,7企业年金提取凭证,8补充医疗提取凭证,9企业年金发放凭证,10评估调整凭证"	' '	VARCHAR(20)	20		TRUE	FALSE	TRUE
//单位编码	DEPT_CODE	单位编码	' '	VARCHAR(50)	50		TRUE	FALSE	TRUE
//单位映射编码	MAPPING_CODE	单位映射编码	' '	VARCHAR(50)	50		TRUE	FALSE	TRUE -->

	private String TYPE_CODE;
	private String BILL_OFF;
	private String DEPT_CODE;
	private String MAPPING_CODE;
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
	public String getMAPPING_CODE() {
		return MAPPING_CODE;
	}
	public void setMAPPING_CODE(String mAPPING_CODE) {
		MAPPING_CODE = mAPPING_CODE;
	}
}