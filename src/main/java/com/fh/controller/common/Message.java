package com.fh.controller.common;


/**
 * 
 * 
 * @ClassName: Message
 * @Description: 提示信息
 * @author 张晓柳
 * @date 2017年9月20日
 *
 */
public class Message {
	public static String OperDataSumAlreadyChange = "操作的数据汇总已传输或已作废或不存在，请刷新！";
	public static String OperDataAlreadySum = "操作的数据已汇总，请刷新！";
	public static String RowDataTypeMustInput = "记录数据类型必须选择！";
	public static String RowDataTypeInputError = "记录数据类型选择有误！";
	public static String SelectCanSumOption = "请选择具体单号或临时数据!";
	public static String OperDataAlreadyChange = "操作的数据已改变，请刷新！";
	public static String Error = "出错！";
	public static String NotGetBillTypeFromVoucher = "单据编号类型获取失败，请联系管理员！";
	public static String NotHaveOperateData = "没有可操作的数据";
	public static String BillCodeNotHaveGenFeild = "导入数据单号再表中没有对应值！";
	public static String HaveRepeatRecord = "重复数据！";
	public static String SystemDateTimeMustNotKong = "当前区间不能为空，请联系管理员！";
	public static String SystemDateTimeNotSameTranferSystemDateTime = "界面区间与配置的当前区间不一致！";
	public static String CurrentDay_LTD_DAY = "当前业务已超过关账日期，请联系总部！";
	public static String LTD_DAY_Not_Have = "未获取到系统设置关账日期，请联系总部！";
	public static String NotHaveConfirmData = "没有已确认数据，请到【汇总单据确认】功能确认相关数据！";
	public static String SetValue = "勾选记录已设置值信息，请删除后再删除此记录！！";
	public static String HavaNotHavaChangeCols = "存在未对应的数据列，请点击【查询】按钮！";
	public static String HavaNotSumOrDetail = "操作的数据没有明细信息，请刷新！";
	public static String SetSumCodeError = "设置汇总单号出错！";
	public static String TmplInputTipsSet = "请重新配置导入规则！";
	public static String TmplInputTipsCoudMapNotSameNum = TmplInputTipsSet + "条件列与映射列不匹配！";
	public static String TmplInputTipsCoudMapNotHaveDic = TmplInputTipsSet + "条件列与映射列未获取到字典信息！";

	public static String ImportExcelError = "导入文件格式数据有误，请检查导入文件！";
	
	public static String GetHelpfulDetailFalue = "获取可操作的数据的条件失败，请联系管理员！";
	public static String ReportTypeIsNull = "上报类型为空！，请联系管理员！";
	public static String SelectedTabOppositeReportTypeIsNull = "选项卡对应的上报类型为空！，请联系管理员！";
	public static String StaffSelectedTabOppositeGroupTypeIsNull = "工资选项卡对应的员工组编码为空！，请联系管理员！";
	public static String NotTransferOperateData = "没有传入可操作的数据";
	public static String CurrentDurationBeSealed = "当前期间已封存！";

}
