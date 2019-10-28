package com.fh.util.enums;


/**
 * 单据编码
  SHBX社会保险,YGGZ员工工资,ZFGJ住房公积金
* @ClassName: BillNumType
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月22日
*
 */
public class BillNumType {
	public static final String SHBX = "SHBX";// 社会保险
	public static final String YGGZ = "YGGZ";// 员工工资
	public static final String ZFGJ = "ZFGJ";// 住房公积金

	public static final String GZJF = "GZJF";// 1工会经费、教育经费凭证 
	public static final String DFJT = "DFJT";// 2党费凭证
	public static final String SBHT = "SBHT";// 3社保互推凭证
	public static final String ZHHT = "ZHHT";// 4公积金互推凭证
	public static final String SBGJ = "SBGJ";// 5个缴凭证
	public static final String YFLW = "YFLW";// 6应付劳务费凭证
	public static final String NJTQ = "NJTQ";// 7企业年金提取凭证
	public static final String YLTQ = "YLTQ";// 8补充医疗提取凭证
	public static final String NJFF = "NJFF";// 9企业年金发放凭证
	public static final String PGTZ = "PGTZ";// 10评估调整凭证
	public static final String SGRQ = "SGRQ";// 11深港天然气
	public static final String SGFY = "SGFY";// 12深港社保劳务及管理费
	public static final String SGDX = "SGDX";// 13深港社保费用及抵消往来
	
	
	public static final String RPOBLEM = "PRO";//问题管理
	
	public static final String GRC_ZHXZ = "GRC-AQ-01";//GRC账号新增 
	public static final String GRC_QXBG = "GRC-AQ-02";//GRC权限变更
	public static final String GRC_ZHZX = "GRC-AQ-03";//GRC账号注销
	public static final String ERP_ZHXZ = "ERP-AQ-01";//ERP账号新增 
	public static final String ERP_ZHZX = "ERP-AQ-02";//ERP账号注销
	public static final String ERP_XTBG = "ERP-BG-01";//ERP系统变更
	public static final String ERP_JSBG = "SAP-AQ-03";//ERP角色变更
}
