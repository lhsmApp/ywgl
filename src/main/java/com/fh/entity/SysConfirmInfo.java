package com.fh.entity;
 
public class SysConfirmInfo {
	
//Name	Code	Comment	Default Value	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
//单据编码	BILL_CODE	"单据编码1工会经费、教育经费凭证 GZJF2党费凭证 DFJT3社保互推凭证 SBHT4公积金互推凭证 ZHHT5个缴凭证 SBGJ6应付劳务费凭证 YFLW7企业年金提取凭证 NJTQ8补充医疗提取凭证 YLTQ9企业年金发放凭证  NJFF10评估调整凭证 PGTZ"	' '	VARCHAR(20)	20		TRUE	FALSE	TRUE
//单据单位	RPT_DEPT	单据单位	' '	VARCHAR(30)	30		TRUE	FALSE	TRUE
//单据期间	RPT_DUR	"单据期间格式 YYYYMM"	' '	VARCHAR(6)	6		TRUE	FALSE	TRUE
//确认人	RPT_USER	确认人	' '	VARCHAR(6)	6		FALSE	FALSE	TRUE
//确认时间	RPT_DATE	"确认时间格式：YYYY-MM-DD HH:MM:SS"	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
//单据类型	BILL_TYPE	"单据类型1合同化工资传输2市场化工资传输3运行人员工资传输4劳务用工传输5劳务人员在建传输6社保传输7公积金传输"	' '	VARCHAR(2)	2		FALSE	FALSE	TRUE
//状态	STATE	"状态1确认0待确认"	' '	CHAR(1)	1		FALSE	FALSE	TRUE
//账套	BILL_OFF	账套	' '	VARCHAR(8)	8		FALSE	FALSE	TRUE -->
// CERT_CODE
	
	private String BILL_CODE; 
	private String RPT_DEPT; 
	private String RPT_DUR; 
	private String RPT_USER; 
	private String RPT_DATE; 
	private String BILL_TYPE; 
	private String STATE;
	private String BILL_OFF;
	private String CERT_CODE;
	public String getCERT_CODE() {
		return CERT_CODE;
	}
	public void setCERT_CODE(String cERT_CODE) {
		CERT_CODE = cERT_CODE;
	}
	public String getBILL_CODE() {
		return BILL_CODE;
	}
	public void setBILL_CODE(String bILL_CODE) {
		BILL_CODE = bILL_CODE;
	}
	public String getRPT_DEPT() {
		return RPT_DEPT;
	}
	public void setRPT_DEPT(String rPT_DEPT) {
		RPT_DEPT = rPT_DEPT;
	}
	public String getRPT_DUR() {
		return RPT_DUR;
	}
	public void setRPT_DUR(String rPT_DUR) {
		RPT_DUR = rPT_DUR;
	}
	public String getRPT_USER() {
		return RPT_USER;
	}
	public void setRPT_USER(String rPT_USER) {
		RPT_USER = rPT_USER;
	}
	public String getRPT_DATE() {
		return RPT_DATE;
	}
	public void setRPT_DATE(String rPT_DATE) {
		RPT_DATE = rPT_DATE;
	}
	public String getBILL_TYPE() {
		return BILL_TYPE;
	}
	public void setBILL_TYPE(String bILL_TYPE) {
		BILL_TYPE = bILL_TYPE;
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