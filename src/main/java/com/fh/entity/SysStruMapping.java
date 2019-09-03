package com.fh.entity;

public class SysStruMapping  {

	
//Name	Code	Comment	Default Value	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
//业务类型	TYPE_CODE	"业务类型1工会经费、教育经费凭证2党费凭证3社保互推凭证4公积金互推凭证5个缴凭证6应付劳务费凭证7企业年金提取凭证8补充医疗提取凭证9企业年金发放凭证10评估调整凭证"	' '	VARCHAR(20)	20		TRUE	FALSE	TRUE
//业务表	TABLE_NAME	业务表	' '	VARCHAR(30)	30		TRUE	FALSE	TRUE
//映射业务表	TABLE_NAME_MAPPING	映射业务表	' '	VARCHAR(30)	30		FALSE	FALSE	TRUE
//业务表列编码	COL_CODE	业务表列编码	’ ‘	VARCHAR(20)	20		FALSE	FALSE	TRUE
//映射列列编码	COL_MAPPING_CODE	映射列列编码	’ ‘	VARCHAR(20)	20		FALSE	FALSE	TRUE
//映射名称	COL_MAPPING_NAME	映射名称	’ ‘	VARCHAR(30)	30		FALSE	FALSE	TRUE
//业务表列值	COL_VALUE	业务表列值	’ ‘	VARCHAR(40)	40		FALSE	FALSE	TRUE
//账套	BILL_OFF	账套	‘ ’	VARCHAR(6)	6		TRUE	FALSE	TRUE
//业务期间	BUSI_DATE	业务期间	‘ ’	VARCHAR(8)	8		TRUE	FALSE	TRUE
//映射列显示序号	DISP_ORDER	映射列显示序号	0	integer			FALSE	FALSE	TRUE
//映射列位数	COL_DGT	映射列数值位数	10	integer			FALSE	FALSE	TRUE
//映射列小数位数	DEC_PRECISION	映射列小数位数		integer			FALSE	FALSE	TRUE
//字典翻译	DICT_TRANS	字典翻译	' '	VARCHAR(30)	30		FALSE	FALSE	TRUE
//列隐藏	COL_HIDE	"列隐藏0隐藏 1显示 默认1"	'1'	CHAR(1)	1		FALSE	FALSE	TRUE
//列汇总	COL_SUM	"列汇总1汇总 0不汇总,默认0"	'0'	CHAR(1)	1		FALSE	FALSE	TRUE
//列平均值	COL_AVE	"列平均值0不计算 1计算 默认0"	'0'	CHAR(1)	1		FALSE	FALSE	TRUE
//列传输	COL_TRANSFER			VARCHAR(1)	1		FALSE	FALSE	TRUE -->
	//列启用 COL_ENABLE 列启用 1启用0停用	'1'	CHAR(1)	1		FALSE	FALSE	TRUE
	
	private String TYPE_CODE; 
	private String TABLE_NAME; 
	private String TABLE_NAME_MAPPING; 
	private String COL_CODE; 
	private String COL_MAPPING_CODE;
    private String COL_MAPPING_NAME; 
	private String COL_VALUE; 
    private String COL_MAPPING_VALUE; 
    private String BILL_OFF; 
    private String BUSI_DATE; 
    private int DISP_ORDER; 
    private int COL_DGT; 
    private int DEC_PRECISION; 
    private String DICT_TRANS; 
    private String COL_HIDE; 
    private String COL_SUM; 
    private String COL_AVE; 
    private String COL_TRANSFER;
    private String COL_ENABLE;
	public String getCOL_ENABLE() {
		return COL_ENABLE;
	}
	public void setCOL_ENABLE(String cOL_ENABLE) {
		COL_ENABLE = cOL_ENABLE;
	}
	public String getTYPE_CODE() {
		return TYPE_CODE;
	}
	public void setTYPE_CODE(String tYPE_CODE) {
		TYPE_CODE = tYPE_CODE;
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
	public String getCOL_CODE() {
		return COL_CODE;
	}
	public void setCOL_CODE(String cOL_CODE) {
		COL_CODE = cOL_CODE;
	}
	public String getCOL_MAPPING_CODE() {
		return COL_MAPPING_CODE;
	}
	public void setCOL_MAPPING_CODE(String cOL_MAPPING_CODE) {
		COL_MAPPING_CODE = cOL_MAPPING_CODE;
	}
	public String getCOL_MAPPING_NAME() {
		return COL_MAPPING_NAME;
	}
	public void setCOL_MAPPING_NAME(String cOL_MAPPING_NAME) {
		COL_MAPPING_NAME = cOL_MAPPING_NAME;
	}
	public String getCOL_VALUE() {
		return COL_VALUE;
	}
	public void setCOL_VALUE(String cOL_VALUE) {
		COL_VALUE = cOL_VALUE;
	}
    public String getCOL_MAPPING_VALUE() {
		return COL_MAPPING_VALUE;
	}
	public void setCOL_MAPPING_VALUE(String cOL_MAPPING_VALUE) {
		COL_MAPPING_VALUE = cOL_MAPPING_VALUE;
	}
	public String getBILL_OFF() {
		return BILL_OFF;
	}
	public void setBILL_OFF(String bILL_OFF) {
		BILL_OFF = bILL_OFF;
	}
	public String getBUSI_DATE() {
		return BUSI_DATE;
	}
	public void setBUSI_DATE(String bUSI_DATE) {
		BUSI_DATE = bUSI_DATE;
	}
	public int getDISP_ORDER() {
		return DISP_ORDER;
	}
	public void setDISP_ORDER(int dISP_ORDER) {
		DISP_ORDER = dISP_ORDER;
	}
	public int getCOL_DGT() {
		return COL_DGT;
	}
	public void setCOL_DGT(int cOL_DGT) {
		COL_DGT = cOL_DGT;
	}
	public int getDEC_PRECISION() {
		return DEC_PRECISION;
	}
	public void setDEC_PRECISION(int dEC_PRECISION) {
		DEC_PRECISION = dEC_PRECISION;
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
