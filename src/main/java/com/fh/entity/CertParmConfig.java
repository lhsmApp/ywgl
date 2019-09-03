package com.fh.entity;

public class CertParmConfig {
	
	//Name	Code	Comment	Default Value	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
	//业务类型	TYPE_CODE	"业务类型"	' '	VARCHAR(20)	20		TRUE	FALSE	TRUE
	//账套	BILL_OFF	账套	' '	VARCHAR(6)	6		FALSE	FALSE	TRUE
	//   BUSI_DATE
	//单位编码	DEPT_CODE	单位编码	' '	VARCHAR(10)	10		FALSE	FALSE	TRUE
	//分组条件	GROUP_COND	分组条件	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//分组条件	GROUP_COND1	分组条件	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//查询条件	QUERY_COND	查询条件	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//自定义参数1	CUST_PARM1	自定义参数1	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//自定义参数1说明	CUST_PARM1_DESC	自定义参数1说明	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//自定义参数2	CUST_PARM2	自定义参数2	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//自定义参数2说明	CUST_PARM2_DESC	自定义参数2说明	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//自定义参数3	CUST_PARM3	自定义参数3	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	//自定义参数3说明	CUST_PARM3_DESC	自定义参数3说明	' '	VARCHAR(20)	20		FALSE	FALSE	TRUE
	
	private String TYPE_CODE;
	private String BILL_OFF;
	private String BUSI_DATE;
	private String DEPT_CODE;
	private String GROUP_COND;
	private String GROUP_COND1;
	private String QUERY_COND;
	private String CUST_PARM1;
	private String CUST_PARM1_DESC;
	private String CUST_PARM2;
	private String CUST_PARM2_DESC;
	private String CUST_PARM3;
	private String CUST_PARM3_DESC;
	public String getBUSI_DATE() {
		return BUSI_DATE;
	}
	public void setBUSI_DATE(String bUSI_DATE) {
		BUSI_DATE = bUSI_DATE;
	}
	public String getGROUP_COND1() {
		return GROUP_COND1;
	}
	public void setGROUP_COND1(String gROUP_COND1) {
		GROUP_COND1 = gROUP_COND1;
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
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}
	public String getGROUP_COND() {
		return GROUP_COND;
	}
	public void setGROUP_COND(String gROUP_COND) {
		GROUP_COND = gROUP_COND;
	}
	public String getQUERY_COND() {
		return QUERY_COND;
	}
	public void setQUERY_COND(String qUERY_COND) {
		QUERY_COND = qUERY_COND;
	}
	public String getCUST_PARM1() {
		return CUST_PARM1;
	}
	public void setCUST_PARM1(String cUST_PARM1) {
		CUST_PARM1 = cUST_PARM1;
	}
	public String getCUST_PARM1_DESC() {
		return CUST_PARM1_DESC;
	}
	public void setCUST_PARM1_DESC(String cUST_PARM1_DESC) {
		CUST_PARM1_DESC = cUST_PARM1_DESC;
	}
	public String getCUST_PARM2() {
		return CUST_PARM2;
	}
	public void setCUST_PARM2(String cUST_PARM2) {
		CUST_PARM2 = cUST_PARM2;
	}
	public String getCUST_PARM2_DESC() {
		return CUST_PARM2_DESC;
	}
	public void setCUST_PARM2_DESC(String cUST_PARM2_DESC) {
		CUST_PARM2_DESC = cUST_PARM2_DESC;
	}
	public String getCUST_PARM3() {
		return CUST_PARM3;
	}
	public void setCUST_PARM3(String cUST_PARM3) {
		CUST_PARM3 = cUST_PARM3;
	}
	public String getCUST_PARM3_DESC() {
		return CUST_PARM3_DESC;
	}
	public void setCUST_PARM3_DESC(String cUST_PARM3_DESC) {
		CUST_PARM3_DESC = cUST_PARM3_DESC;
	}
}