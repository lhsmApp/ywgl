package com.fh.controller.common;

import java.util.List;

import com.fh.entity.ClsVoucherStruFeild;
import com.fh.entity.SysStruMapping;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.enums.BillState;

/**
 * 模板通用类
 * 
 * @ClassName: getSqlToSave
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangxiaoliu
 * @date 2018年04月19日
 *
 */
public class SqlFeildToSave {

    //表结构where条件的开头和结尾
    private static String StruMappingWhereStartEndWith = "@";

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ClsVoucherStruFeild getSqlFeildToSave(String SelectedCustCol7, String SelectedTypeCode, String SelectedDepartCode, String SystemDateTime, 
			String BILL_CODE, List<SysStruMapping> getSysStruMappingList) throws Exception {
		String sqlInsFeild = "";
		String sqlSelFeild = "";
		String SqlNvlFeild = "";
		String sqlWhere = "";
		if (getSysStruMappingList != null && getSysStruMappingList.size() > 0) { 
			for(SysStruMapping struMapping : getSysStruMappingList){
				String COL_CODE = struMapping.getCOL_CODE();
				String COL_MAPPING_CODE = struMapping.getCOL_MAPPING_CODE();
				String COL_VALUE = struMapping.getCOL_VALUE().toUpperCase().trim();
				String COL_MAPPING_VALUE = struMapping.getCOL_MAPPING_VALUE().toUpperCase().trim();
				if(COL_CODE!=null && !COL_CODE.trim().equals("")){
					if(sqlSelFeild!=null && !sqlSelFeild.trim().equals("")){
						sqlSelFeild += ", ";
					}
					sqlSelFeild += COL_CODE + " " + COL_MAPPING_CODE;
					if(SqlNvlFeild!=null && !SqlNvlFeild.trim().equals("")){
						SqlNvlFeild += ", ";
					}
					SqlNvlFeild += COL_CODE + " " + COL_MAPPING_CODE;
					if(sqlInsFeild!=null && !sqlInsFeild.trim().equals("")){
						sqlInsFeild += ", ";
					}
					sqlInsFeild += COL_MAPPING_CODE;
				} else {
					if(COL_MAPPING_VALUE!=null && !COL_MAPPING_VALUE.trim().equals("")){
						if(sqlInsFeild!=null && !sqlInsFeild.trim().equals("")){
							sqlInsFeild += ", ";
						}
						sqlInsFeild += COL_MAPPING_CODE;
						//COL_MAPPING_VALUE 
						//1）列作用：本列作用于凭证数据汇总功能；
						//2）变量说明：用于接收传入的常量，如单位、时间、帐套、单号的字符值。
						//a、@BILLNO@，接收单据号变量；
						//b、@DATE@，接收期间变量；
						//c、@STATE@，接收单据状态变量；
						//d、@USER@，接收用户变量；
						//e、@DEPART@,接收单位变量；
						if(sqlSelFeild!=null && !sqlSelFeild.trim().equals("")){
							sqlSelFeild += ", ";
						}
						if(COL_MAPPING_VALUE.equals(("@BILLOFF@").toUpperCase())){
							sqlSelFeild += " '" + SelectedCustCol7 + "' " + COL_MAPPING_CODE;
						} else if(COL_MAPPING_VALUE.equals(("@BILLNO@").toUpperCase())){
							sqlSelFeild += " '" + BILL_CODE + "' " + COL_MAPPING_CODE;
						} else if(COL_MAPPING_VALUE.equals(("@USER@").toUpperCase())){
							sqlSelFeild += " '" + Jurisdiction.getCurrentDepartmentID() + "' " + COL_MAPPING_CODE;
						} else if(COL_MAPPING_VALUE.equals(("@DATE@").toUpperCase())){
							sqlSelFeild += " '" + SystemDateTime + "' " + COL_MAPPING_CODE;
						} else if(COL_MAPPING_VALUE.equals(("@DEPART@").toUpperCase())){
							sqlSelFeild += " '" + SelectedDepartCode + "' " + COL_MAPPING_CODE;
						} else if(COL_MAPPING_VALUE.equals(("@CERTTYPE@").toUpperCase())){
							sqlSelFeild += " '" + SelectedTypeCode + "' " + COL_MAPPING_CODE;
						} else if(COL_MAPPING_VALUE.equals(("@STATE@").toUpperCase())){
							sqlSelFeild += " '" + BillState.Normal.getNameKey() + "' " + COL_MAPPING_CODE;
						} else if(COL_MAPPING_VALUE.equals(("@LONGDATE@").toUpperCase())){
							sqlSelFeild += " '" + DateUtil.getTime() + "' " + COL_MAPPING_CODE;
						}
					}
				}
				if(COL_VALUE!=null && !COL_VALUE.trim().equals("")){
					if(sqlWhere!=null && !sqlWhere.trim().equals("")){
						sqlWhere += " and ";
					} else {
						sqlWhere += " where ";
					}
					if(COL_VALUE.endsWith((StruMappingWhereStartEndWith).toUpperCase()) && COL_VALUE.endsWith((StruMappingWhereStartEndWith).toUpperCase())){
						//COL_VALUE
						//1）列作用：本列作于生成查询条件；
						//2）说明： 变量部分：
						//a、 @DEPARTMAP@，为单位映射变量；用于设置单位查询条件，查询SQL如下，TYPE_CODE凭证类型,BILL_OFF账套,DEPT_CODE单位，以上三条件均在汇总时根据选择的凭证类型，选择的帐套及以所选择操作的单位获得，以下示例为获取凭证1帐套9100机关01001单位的单位映射：
						//               SELECT mapping_code FROM tb_sys_dept_mapping WHERE  TYPE_CODE = '1'  AND BILL_OFF = '9100' AND DEPT_CODE = '01001'
						//b、@DATE@,接收传入的当前期间值做为条件；
						//c、@BILLNOMAP@，为已确认单据映射变量；用于设置单据编号查询条件，查询SQL如下：TYPE_CODE凭证类型,BILL_OFF账套,DEPT_CODE单位，以下示例 为获取凭证1帐套9100机关01001单位已确认完成的业务单号。
						//               SELECT  bill_code  FROM	tb_sys_confirm_info WHERE	rpt_dept IN ( SELECT mapping_code FROM tb_sys_dept_mapping WHERE  TYPE_CODE = '1'  AND BILL_OFF = '9100' AND DEPT_CODE = '01001'）AND state = '1'
						if(COL_VALUE.equals(("@BILLNOMAP@").toUpperCase())){
							sqlWhere += COL_CODE + " in (select BILL_CODE from TB_SYS_CONFIRM_INFO where STATE = '" + BillState.Normal.getNameKey() +"') ";
						} else if(COL_VALUE.equals(("@BILLNO@").toUpperCase())){
							sqlWhere += COL_CODE + " in ('" + BILL_CODE +"') ";
						} else if(COL_VALUE.equals(("@DATE@").toUpperCase())){
							sqlWhere += COL_CODE + " in ('" + SystemDateTime +"') ";
						} else if(COL_VALUE.equals(("@DEPARTMAP@").toUpperCase())){
							sqlWhere += COL_CODE + " in (SELECT mapping_code FROM tb_sys_dept_mapping WHERE TYPE_CODE = '" + SelectedTypeCode + "'  AND BILL_OFF = '" + SelectedCustCol7 + "' AND DEPT_CODE = '" + SelectedDepartCode + "') ";
						} else if(COL_VALUE.equals(("@DEPART@").toUpperCase())){
							sqlWhere += COL_CODE + " in ('" + SelectedDepartCode +"') ";
						} else if(COL_VALUE.equals(("@CERTTYPE@").toUpperCase())){
							sqlWhere += COL_CODE + " in ('" + SelectedTypeCode + "') ";
						} else if(COL_VALUE.equals(("@BILLOFF@").toUpperCase())){
							sqlWhere += COL_CODE + " in ('" + SelectedCustCol7 + "') ";
						} 
					} else {
						sqlWhere += COL_CODE + " in (" + COL_VALUE +") ";
					}
				}
			}
		}
		ClsVoucherStruFeild sqlFeild = new ClsVoucherStruFeild();
		sqlFeild.setSqlInsFeild(sqlInsFeild);
		sqlFeild.setSqlSelFeild(sqlSelFeild);
		sqlFeild.setSqlWhere(sqlWhere);
		return sqlFeild;
	}
}
