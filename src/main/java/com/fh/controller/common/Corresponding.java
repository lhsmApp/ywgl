package com.fh.controller.common;

import java.util.Arrays;
import java.util.List;

import com.fh.entity.TmplTypeInfo;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.util.PageData;
import com.fh.util.enums.BillNumType;
import com.fh.util.enums.EmplGroupType;
import com.fh.util.enums.PZTYPE;
import com.fh.util.enums.StaffFilterType;
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.enums.SysConfirmInfoBillType;
import com.fh.util.enums.TmplType;

/**
 * 
 * 
 * @ClassName: Corresponding
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张晓柳
 * @date 2017年8月18日
 *
 */
public class Corresponding {
	
	public static String tb_gl_cert = "tb_gl_cert";
	public static String tb_sys_confirm_info = "tb_sys_confirm_info";
	
	public static String tb_staff_detail = "tb_staff_detail";
	public static String tb_social_inc_detail = "tb_social_inc_detail";
	public static String tb_house_fund_detail = "tb_house_fund_detail";
	public static String tb_staff_summy = "tb_staff_summy";
	public static String tb_social_inc_summy = "tb_social_inc_summy";
	public static String tb_house_fund_summy = "tb_house_fund_summy";
	public static String tb_staff_summy_bill = "tb_staff_summy_bill";
	public static String tb_social_inc_summy_bill = "tb_social_inc_summy_bill";
	public static String tb_house_fund_summy_bill = "tb_house_fund_summy_bill";

	public static String tb_item_staff_detail = "tb_item_staff_detail";
	public static String tb_item_social_inc_detail = "tb_item_social_inc_detail";
	public static String tb_item_house_fund_detail = "tb_item_house_fund_detail";

	//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
	//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本retItem
	public static List<String> SumFieldBillStaff = Arrays.asList("BILL_CODE", "BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP");
	public static List<String> SumFieldBillSocHou = Arrays.asList("BILL_CODE", "BUSI_DATE", "DEPT_CODE", "CUST_COL7");
	
	public static String getWhileValue(String value, String DefaultWhile){
        String which = DefaultWhile;
		if(value != null && !value.trim().equals("")){
			which = value;
		}
		return which;
	}
	
	public static Boolean CheckStaffOrNot(String which) {
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据模板基本类型获取SysConfirmInfoBillType
	 * 
	 * @param which
	 * @return
	 */
	public static String getSysConfirmInfoBillTypeFromTmplType(String which) {
		String billType = "";
		if (which != null){
			if (which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())) {
				billType = SysConfirmInfoBillType.STAFF_CONTRACT.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())) {
				billType = SysConfirmInfoBillType.STAFF_MARKET.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())) {
				billType = SysConfirmInfoBillType.STAFF_SYS_LABOR.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
				billType = SysConfirmInfoBillType.STAFF_OPER_LABOR.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
				billType = SysConfirmInfoBillType.STAFF_LABOR.getNameKey();
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
				billType = SysConfirmInfoBillType.SOCIAL_INC.getNameKey();
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
				billType = SysConfirmInfoBillType.HOUSE_FUND.getNameKey();
			}
		}
		return billType;
	}

	/**
	 * 根据凭证类型获取单号前缀
	 * @return
	 * @throws Exception
	 */
	public static String getBillTypeFromPZTYPE(String SelectedTypeCode) {
		String strReturn = "";
		if (SelectedTypeCode != null && !SelectedTypeCode.trim().equals("")) { 
			if(SelectedTypeCode.equals(PZTYPE.GFZYJF)){
				strReturn = BillNumType.GZJF;// 1工会经费、教育经费凭证 
			} else if(SelectedTypeCode.equals(PZTYPE.DF)){
				strReturn = BillNumType.DFJT;// 2党费凭证
			}  else if(SelectedTypeCode.equals(PZTYPE.SB)){
				strReturn = BillNumType.SBHT;// 3社保互推凭证
			} else if(SelectedTypeCode.equals(PZTYPE.GJJ)){
				strReturn = BillNumType.ZHHT;// 4公积金互推凭证
			} else if(SelectedTypeCode.equals(PZTYPE.GJ)){
				strReturn = BillNumType.SBGJ;// 5个缴凭证
			} else if(SelectedTypeCode.equals(PZTYPE.YFLWF)){
				strReturn = BillNumType.YFLW;// 6应付劳务费凭证
			} else if(SelectedTypeCode.equals(PZTYPE.QYNJTQ)){
				strReturn = BillNumType.NJTQ;// 7企业年金提取凭证
			} else if(SelectedTypeCode.equals(PZTYPE.BCYLTQ)){
				strReturn = BillNumType.YLTQ;// 8补充医疗提取凭证
			} else if(SelectedTypeCode.equals(PZTYPE.QYNJFF)){
				strReturn = BillNumType.NJFF;// 9企业年金发放凭证
			} else if(SelectedTypeCode.equals(PZTYPE.PGTZ)){
				strReturn = BillNumType.PGTZ;// 10评估调整凭证
			} else if(SelectedTypeCode.equals(PZTYPE.SGRQ)){
				strReturn = BillNumType.SGRQ;// 11深港天然气
			} else if(SelectedTypeCode.equals(PZTYPE.SGFY)){
				strReturn = BillNumType.SGFY;// 12深港社保劳务及管理费
			} else if(SelectedTypeCode.equals(PZTYPE.SGDX)){
				strReturn = BillNumType.SGDX;// 13深港社保费用及抵消往来
			}
		}
		return strReturn;
	}

	public static String getTypeCodeTransferFromTmplType(String which) throws Exception{
		String strReturn = "";
		if (which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())) {
			//合同化
			strReturn = TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
			//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
			|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
			|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
			|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())) {
			//市场化
			strReturn = TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
		//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())) {
			//系统内劳务
			strReturn = TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
		//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
			//运行人员
			strReturn = TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
		//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
			//劳务派遣工资
			strReturn = TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey();
		} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
				//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
			strReturn = TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey();
		} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
				//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
			strReturn = TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey();
		}
		return strReturn;
	}

	/**
	 * 根据模板基本类型获取员工组编码
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static String getUserGroupTypeFromTmplType(String which) throws Exception {
		String emplGroupType = "";
		if (which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())) {
			emplGroupType = EmplGroupType.HTH;
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())) {
			emplGroupType = EmplGroupType.SCH;
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())) {
			emplGroupType = EmplGroupType.XTNLW;
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
			emplGroupType = EmplGroupType.YXRY;
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
			emplGroupType = EmplGroupType.LWPQ;
		}
		return emplGroupType;
	}
	public static String getTmplTypeTranferFromUserGroupType(String userGroup) throws Exception {
		String retTmplTypeTranfer = "";// 数据库真实业务数据表
		if (userGroup.equals(EmplGroupType.HTH)) {
			retTmplTypeTranfer = TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey();
		} else if (userGroup.equals(EmplGroupType.SCH)) {
			retTmplTypeTranfer = TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey();
		} else if (userGroup.equals(EmplGroupType.XTNLW)) {
			retTmplTypeTranfer = TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey();
		} else if (userGroup.equals(EmplGroupType.YXRY)) {
			retTmplTypeTranfer = TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey();
		} else if (userGroup.equals(EmplGroupType.LWPQ)) {
			retTmplTypeTranfer = TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey();
		}
		return retTmplTypeTranfer;
	}

	public static String getTypeCodeDetailFromTmplType(String which) throws Exception{
		String strReturn = "";
		if (which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())) {
			//合同化
			strReturn = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
			//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
			|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
			|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
			|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())) {
			//市场化
			strReturn = TmplType.TB_STAFF_DETAIL_MARKET.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
		//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())) {
			//系统内劳务
			strReturn = TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
		//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
			//运行人员
			strReturn = TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey();
		} else if (which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
		//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
		|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
			//劳务派遣工资
			strReturn = TmplType.TB_STAFF_DETAIL_LABOR.getNameKey();
		} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
				//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
			strReturn = TmplType.TB_SOCIAL_INC_DETAIL.getNameKey();
		} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
				//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
			strReturn = TmplType.TB_HOUSE_FUND_DETAIL.getNameKey();
		}
		return strReturn;
	}
	
	/**
	 * 根据前端业务表索引获取表名称
	 * 
	 * @param which
	 * @return
	 */
	public static String getSumBillTableNameFromTmplType(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
				tableCode = tb_staff_summy_bill;
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
				tableCode = tb_social_inc_summy_bill;
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
				tableCode = tb_house_fund_summy_bill;
			}
		}
		return tableCode;
	}
	
	public static String getSummyTableNameFromTmplType(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
				tableCode = tb_staff_summy;
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
				tableCode = tb_social_inc_summy;
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
				tableCode = tb_house_fund_summy;
			}
		}
		return tableCode;
	}
	
	public static String getDetailTableNameFromTmplType(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
				tableCode = tb_staff_detail;
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
				tableCode = tb_social_inc_detail;
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
				tableCode = tb_house_fund_detail;
			}
		}
		return tableCode;
	}
	
	public static String getItemAllocDetailTableNameFromTmplType(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
				tableCode = tb_item_staff_detail;
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
				tableCode = tb_item_social_inc_detail;
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
					//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
					|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
				tableCode = tb_item_house_fund_detail;
			}
		}
		return tableCode;
	}

	public static TmplTypeInfo getWhileValueToTypeCode(String which, SysConfigManager sysConfigManager) throws Exception{
		TmplTypeInfo retItem = new TmplTypeInfo();
		//枚举类型 TmplType
		if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())){
			//合同化
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.ContractGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())){
			//市场化
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.MarketGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())){
			//系统内劳务
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.SysLaborGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())){
			//运行人员
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.OperLaborGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
				//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
				|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())){
			//劳务派遣工资
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.LaborGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
				//|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY_BILL.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
				|| which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())){
			//劳务派遣工资
			retItem.setTypeCodeDetail(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey());

			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.SocialIncGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillSocHou);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
				//|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY_BILL.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
				|| which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())){
			//劳务派遣工资
			retItem.setTypeCodeDetail(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey());

			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.HouseFundGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillSocHou);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		return retItem;
	}
	
	/**
	 * 根据模板基本类型获取SysConfirmInfoBillType
	 * 
	 * @param which
	 * @return
	 */
	public static String getStaffFilterTypeFromTmplType(String which) {
		String billType = "";
		if (which != null){
			if (which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())) {
				billType = StaffFilterType.StaffFilterType_CONTRACT.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())) {
				billType = StaffFilterType.StaffFilterType_MARKET.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())) {
				billType = StaffFilterType.StaffFilterType_SYS_LABOR.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
				billType = StaffFilterType.StaffFilterType_OPER_LABOR.getNameKey();
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
				billType = StaffFilterType.StaffFilterType_LABOR.getNameKey();
			}
		}
		return billType;
	}
	//是否计算税
	public static Boolean CheckCalculation(String which) {
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())
					
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					//|| which.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					|| which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 凭证字典获取SysConfirmInfoBillType业务类型
	STAFF_CONTRACT("1","合同化工资传输"),//
	STAFF_MARKET("2","市场化工资传输"),
	STAFF_SYS_LABOR("3","运行人员工资传输"),
	STAFF_OPER_LABOR("4","劳务用工传输"),
	STAFF_LABOR("5","劳务人员在建传输"),
	SOCIAL_INC("6","社保传输"),
	HOUSE_FUND("7","公积金传输");
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	/*public static String getSysConfirmInfoBillTypeFromPZTYPE(String pzTYPE) throws Exception {
		String vocherType = "";// 数据库真实业务数据表
		if(pzTYPE != null){
			if (pzTYPE.equals(PZTYPE.GFZYJF.getNameKey())) {
				vocherType = SysConfirmInfoBillType.GFZYJF.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.DF.getNameKey())) {
				vocherType = SysConfirmInfoBillType.DF.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.SB.getNameKey())) {
				vocherType = SysConfirmInfoBillType.SB.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.GJJ.getNameKey())) {
				vocherType = SysConfirmInfoBillType.GJJ.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.GJ.getNameKey())) {
				vocherType = SysConfirmInfoBillType.GJ.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.YFLWF.getNameKey())) {
				vocherType = SysConfirmInfoBillType.YFLWF.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.QYNJTQ.getNameKey())) {
				vocherType = SysConfirmInfoBillType.QYNJTQ.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.BCYLTQ.getNameKey())) {
				vocherType = SysConfirmInfoBillType.BCYLTQ.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.QYNJFF.getNameKey())) {
				vocherType = SysConfirmInfoBillType.QYNJFF.getNameKey();
			} else if (pzTYPE.equals(PZTYPE.PGTZ.getNameKey())) {
				vocherType = SysConfirmInfoBillType.PGTZ.getNameKey();
			}
		}
		return vocherType;
	}*/

}
