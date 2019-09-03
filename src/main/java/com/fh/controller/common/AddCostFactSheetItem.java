package com.fh.controller.common;

import java.util.Arrays;
import java.util.List;

import com.fh.entity.ClsCostFactSheet;
import com.fh.util.enums.BindingType;

/**
 * 
* @ClassName: AddCostFactSheetItem
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张晓柳
* @date 2017年8月14日
*
 */
public class AddCostFactSheetItem {
	
	static String strBrShow = "<br/>";
	static String strBrExcel = "";//"\r\n";
	
	static String strHth = "合" + strBrShow + "同" + strBrShow + "化";
	static String strHth_zyxq = "中" + strBrShow + "油" + strBrShow + "西" + strBrShow + "气";
	static String strHth_xqdsgd = "西" + strBrShow + "气" + strBrShow + "东" + strBrShow + "输" + strBrShow + "管" + strBrShow + "道";
	static String strSch = "市" + strBrShow + "场" + strBrShow + "化";
	static String strSch_zyxq = "中" + strBrShow + "油" + strBrShow + "西" + strBrShow + "气";
	static String strSch_xqdsgd = "西" + strBrShow + "气" + strBrShow + "东" + strBrShow + "输" + strBrShow + "管" + strBrShow + "道";
	static String strLwf = "劳" + strBrShow + "务" + strBrShow + "费";
	static String strLwf_xtnlw = "系" + strBrShow + "统" + strBrShow + "内" + strBrShow + "劳" + strBrShow + "务";
	static String strLwf_xtnlw_zyxqxtnlw = "中" + strBrShow + "油" + strBrShow + "西" + strBrShow + "气" + strBrShow + "系" + strBrShow + "统" + strBrShow + "内" + strBrShow + "劳" + strBrShow + "务";
	static String strLwf_lwpq = "劳" + strBrShow + "务" + strBrShow + "派" + strBrShow + "遣";
	static String strLwf_lwpq_zyxqlwbq = "中" + strBrShow + "油" + strBrShow + "西" + strBrShow + "气" + strBrShow + "劳" + strBrShow + "务" + strBrShow + "派" + strBrShow + "遣";
	
	
	public static List<String> groupHeaderTopList1 = Arrays.asList("类别", "类别", "类别", "类别", 
			"总额合计", 
			"其中", "其中", "其中", "其中", "其中", "其中", "其中", "其中", 
			"儿贴", "防暑降温费", "疗养费", "总额外单项奖", "单独制表", "期末人数",
			"", "", "");
	public static List<String> groupHeaderTopList2 = Arrays.asList("类别", "类别", "类别", "类别", 
			"总额合计", 
			"工资", "无房补贴", "交通补贴", "通讯补贴", "节日补贴", "误餐补贴", "项目补贴", "", 
			"儿贴", "防暑降温费", "疗养费", "总额外单项奖", "单独制表", "期末人数",
			"", "", "");
	//工资总额合计
	public static List<String> gzzehj = Arrays.asList("工资总额合计", "工资总额合计", "工资总额合计", "工资总额合计", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"0", BindingType.Total0.getNameKey(), "工资总额合计");
	//"合同化", "合同化", "合同化合计", "合同化合计" 
	public static List<String> hth_hthhj = Arrays.asList(strHth, strHth, "合同化合计", "合同化合计", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"31", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	//"合同化", "合同化", "中油西气", "小计"
	public static List<String> hth_zyxq_xj = Arrays.asList(strHth, strHth, strHth_zyxq, "小计 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"1", BindingType.DetailSCHHTH.getNameKey(), "小计");
	public static List<String> hth_zyxq_zyxqhth = Arrays.asList(strHth, strHth, strHth_zyxq, "中油西气合同化 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"2", BindingType.DetailSCHHTH.getNameKey(), "中油西气合同化");
	public static List<String> hth_zyxq_ychthd = Arrays.asList(strHth, strHth, strHth_zyxq, "银川合同化-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"3", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> hth_zyxq_whhthd = Arrays.asList(strHth, strHth, strHth_zyxq, "武汉合同化-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"4", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	//"合同化", "合同化", "西气东输管道", "小计"
	public static List<String> hth_xqdbgd_xj = Arrays.asList(strHth, strHth, strHth_xqdsgd, "小计 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"5", BindingType.DetailSCHHTH.getNameKey(), "小计");
	public static List<String> hth_xqdbgd_xqdbgdhth = Arrays.asList(strHth, strHth, strHth_xqdsgd, "西气东输管道合同化 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"6", BindingType.DetailSCHHTH.getNameKey(), "西气东输管道合同化");
	public static List<String> hth_xqdbgd_sggs = Arrays.asList(strHth, strHth, strHth_xqdsgd, "其中：深港公司 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"7", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> hth_xqdbgd_ychthx = Arrays.asList(strHth, strHth, strHth_xqdsgd, "银川合同化-西 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"8", BindingType.DetailSCHHTH.getNameKey(), "银川合同化-西");
	public static List<String> hth_xqdbgd_whhthx = Arrays.asList(strHth, strHth, strHth_xqdsgd, "武汉合同化-西 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"9", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	
	//"市场化", "市场化", "市场化合计", "市场化合计"
	public static List<String> sch_schhj = Arrays.asList(strSch, strSch, "市场化合计", "市场化合计", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"32", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	//"市场化", "市场化", "中油西气", "小计"
	public static List<String> sch_zyxq_xj = Arrays.asList(strSch, strSch, strSch_zyxq, "小计 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"10", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> sch_zyxq_zyxqsch = Arrays.asList(strSch, strSch, strSch_zyxq, "中油西气市场化 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"11", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> sch_zyxq_ycschd = Arrays.asList(strSch, strSch, strSch_zyxq, "银川市场化-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"12", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> sch_zyxq_whschd = Arrays.asList(strSch, strSch, strSch_zyxq, "武汉市场化-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"13", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	//"市场化", "市场化", "西气东输管道", "小计"
	public static List<String> sch_xqdbgd_xj = Arrays.asList(strSch, strSch, strSch_xqdsgd, "小计 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"14", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> sch_xqdbgd_xqdbgdsch = Arrays.asList(strSch, strSch, strSch_xqdsgd, "西气东输管道市场化 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"15", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> sch_xqdbgd_sggs = Arrays.asList(strSch, strSch, strSch_xqdsgd, "其中：深港公司 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"16", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> sch_xqdbgd_ycschx = Arrays.asList(strSch, strSch, strSch_xqdsgd, "银川市场化-西 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"17", BindingType.DetailSCHHTH.getNameKey(), "deptCode");
	public static List<String> sch_xqdbgd_whschx = Arrays.asList(strSch, strSch, strSch_xqdsgd, "武汉市场化-西 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"18", BindingType.DetailSCHHTH.getNameKey(), "deptCode");

	//
	public static List<String> groupHeaderCenterList1 = Arrays.asList("类别", "类别", "类别", "类别", 
			"小计", 
			"其中", "其中", "其中", "其中", "其中", "其中", "其中", "其中", 
			"其中", "其中", "其中", "其中", "其中", "期末人数",
			"", "", "");
	//"劳务费", "劳务费合计", "劳务费合计", "劳务费合计"
	public static List<String> lwf_lwfhj = Arrays.asList(strLwf, "劳务费合计", "劳务费合计", "劳务费合计", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"44", BindingType.Total44.getNameKey(), "劳务费合计");
	public static List<String> groupHeaderCenterListLwfXtnlw = Arrays.asList(strLwf, "系统内劳务 ", "系统内劳务 ", "系统内劳务 ", 
			"小计", 
			"工资", "无房补贴", "交通补贴", "通讯补贴", "节日补贴", "误餐补贴", "项目补贴", "", 
			"儿贴", "防暑降温费", "管理费", "可抵税费", "单独制表", "期末人数",
			"", "", "");
	//"劳务费", "系统内劳务", "系统内劳务合计", "系统内劳务合计"
	public static List<String> lwf_xtnlw_xtnlwhj = Arrays.asList(strLwf, strLwf_xtnlw, "系统内劳务合计", "系统内劳务合计", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"19", BindingType.DetailXTNLW.getNameKey(), "系统内劳务合计");
	//"劳务费", "系统内劳务", "中油西气系统内劳务", "小计"
	public static List<String> lwf_xtnlw_zyxqxtnlw_xj = Arrays.asList(strLwf, strLwf_xtnlw, strLwf_xtnlw_zyxqxtnlw, "小计 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"20", BindingType.DetailXTNLW.getNameKey(), "小计");
	public static List<String> lwf_xtnlw_zyxqxtnlw_zyxqxtnlw = Arrays.asList(strLwf, strLwf_xtnlw, strLwf_xtnlw_zyxqxtnlw, "中油西气系统内劳务 ",
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"21", BindingType.DetailXTNLW.getNameKey(), "deptCode");
	public static List<String> lwf_xtnlw_zyxqxtnlw_ycxtnlwd = Arrays.asList(strLwf, strLwf_xtnlw, strLwf_xtnlw_zyxqxtnlw, "银川系统内劳务-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"22", BindingType.DetailXTNLW.getNameKey(), "deptCode");
	public static List<String> lwf_xtnlw_zyxqxtnlw_whxtnlwd = Arrays.asList(strLwf, strLwf_xtnlw, strLwf_xtnlw_zyxqxtnlw, "武汉系统内劳务-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"23", BindingType.DetailXTNLW.getNameKey(), "deptCode");
	public static List<String> lwf_xtnlw_xqdsxtnlw = Arrays.asList(strLwf, strLwf_xtnlw, "西气东输系统内劳务", "西气东输系统内劳务", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "",
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"24", BindingType.DetailXTNLW.getNameKey(), "西气东输系统内劳务");
	
	public static List<String> groupHeaderCenterListLwfLwpq = Arrays.asList(strLwf, strLwf_lwpq, "", "", 
			"小计", 
			"工资", "交通补贴", "通讯补贴", "节日补贴", "误餐补贴", "劳保", "防暑降温费", "社保公积金",
			"税后加项", "工会经费", "管理费", "可抵税费", "单独制表", "期末人数",
			"", "", ""); 
	//"劳务费", "劳务派遣", "劳务派遣合计", "劳务派遣合计"
	public static List<String> lwf_lwpq_lwpqhj = Arrays.asList(strLwf, strLwf_lwpq, "劳务派遣合计", "劳务派遣合计", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"25", BindingType.DetailLWPQ.getNameKey(), "劳务派遣合计");
	//"劳务费", "劳务派遣", "中油西气劳务派遣", "小计"
	public static List<String> lwf_lwpq_zyxqlwpq_xj = Arrays.asList(strLwf, strLwf_lwpq, strLwf_lwpq_zyxqlwbq, "小计 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"26", BindingType.DetailLWPQ.getNameKey(), "小计");
	public static List<String> lwf_lwpq_zyxqlwpq_zyxqlwpq = Arrays.asList(strLwf, strLwf_lwpq, strLwf_lwpq_zyxqlwbq, "中油西气劳务派遣 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"27", BindingType.DetailLWPQ.getNameKey(), "中油西气劳务派遣");
	public static List<String> lwf_lwpq_zyxqlwpq_yclwpqd = Arrays.asList(strLwf, strLwf_lwpq, strLwf_lwpq_zyxqlwbq, "银川劳务派遣-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"28", BindingType.DetailLWPQ.getNameKey(), "银川劳务派遣-东");
	public static List<String> lwf_lwpq_zyxqlwpq_whlwpqd = Arrays.asList(strLwf, strLwf_lwpq, strLwf_lwpq_zyxqlwbq, "武汉劳务派遣-东 ", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"29", BindingType.DetailLWPQ.getNameKey(), "deptCode");
	public static List<String> lwf_lwpq_xqdslwpq = Arrays.asList(strLwf, strLwf_lwpq, "西气东输劳务派遣", "西气东输劳务派遣", 
			"0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
			"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
			"30", BindingType.DetailLWPQ.getNameKey(), "西气东输劳务派遣");

	public static List<String> bz = Arrays.asList("备注：", "备注：", "备注：", "备注：", 
			"备注：", 
			"备注：", "备注：", "备注：", "备注：", "备注：", "备注：", "备注：", "备注：", 
			"备注：", "备注：", "备注：", "备注：", "", "",
			"", "", "");

	public static List<String> zh = Arrays.asList("", "", "", "", 
			"核对人：", 
			"", "", "", "", "", "", "", "", 
			"", "统计人：", "", "", "", "",
			"", "", "");
	
	public static List<ClsCostFactSheet> initStructure(List<ClsCostFactSheet> list, Boolean bolIfExport){
		ClsCostFactSheet headerTopList1 = getAddCostFactSheet(groupHeaderTopList1, bolIfExport);
		headerTopList1.setIsRowAllGroup("true");
		list.add(headerTopList1);
		ClsCostFactSheet headerTopList2 = getAddCostFactSheet(groupHeaderTopList2, bolIfExport);
		headerTopList2.setIsRowAllGroup("true");
		list.add(headerTopList2);

		//工资总额合计
		list.add(getAddCostFactSheet(gzzehj, bolIfExport));
		//"合同化", "合同化", "合同化合计", "合同化合计"
		list.add(getAddCostFactSheet(hth_hthhj, bolIfExport));
		//"合同化", "合同化", "中油西气", "小计"
		list.add(getAddCostFactSheet(hth_zyxq_xj, bolIfExport));
		list.add(getAddCostFactSheet(hth_zyxq_zyxqhth, bolIfExport));
		list.add(getAddCostFactSheet(hth_zyxq_ychthd, bolIfExport));
		list.add(getAddCostFactSheet(hth_zyxq_whhthd, bolIfExport));
		//"合同化", "合同化", "西气东输管道", "小计"
		list.add(getAddCostFactSheet(hth_xqdbgd_xj, bolIfExport));
		list.add(getAddCostFactSheet(hth_xqdbgd_xqdbgdhth, bolIfExport));
		list.add(getAddCostFactSheet(hth_xqdbgd_sggs, bolIfExport));
		list.add(getAddCostFactSheet(hth_xqdbgd_ychthx, bolIfExport));
		list.add(getAddCostFactSheet(hth_xqdbgd_whhthx, bolIfExport));
		
		//"市场化", "市场化", "市场化合计", "市场化合计"
		list.add(getAddCostFactSheet(sch_schhj, bolIfExport));
		//"市场化", "市场化", "中油西气", "小计"
		list.add(getAddCostFactSheet(sch_zyxq_xj, bolIfExport));
		list.add(getAddCostFactSheet(sch_zyxq_zyxqsch, bolIfExport));
		list.add(getAddCostFactSheet(sch_zyxq_ycschd, bolIfExport));
		list.add(getAddCostFactSheet(sch_zyxq_whschd, bolIfExport));
		//"市场化", "市场化", "西气东输管道", "小计"
		list.add(getAddCostFactSheet(sch_xqdbgd_xj, bolIfExport));
		list.add(getAddCostFactSheet(sch_xqdbgd_xqdbgdsch, bolIfExport));
		list.add(getAddCostFactSheet(sch_xqdbgd_sggs, bolIfExport));
		list.add(getAddCostFactSheet(sch_xqdbgd_ycschx, bolIfExport));
		list.add(getAddCostFactSheet(sch_xqdbgd_whschx, bolIfExport));

		//
		ClsCostFactSheet headerCenterList1 = getAddCostFactSheet(groupHeaderCenterList1, bolIfExport);
		headerCenterList1.setIsRowAllGroup("true");
		list.add(headerCenterList1);
		//"劳务费", "劳务费合计", "劳务费合计", "劳务费合计"
		list.add(getAddCostFactSheet(lwf_lwfhj, bolIfExport));
		list.add(getAddCostFactSheet(groupHeaderCenterListLwfXtnlw, bolIfExport));
		//"劳务费", "系统内劳务", "系统内劳务合计", "系统内劳务合计"
		list.add(getAddCostFactSheet(lwf_xtnlw_xtnlwhj, bolIfExport));
		//"劳务费", "系统内劳务", "中油西气系统内劳务", "小计"
		list.add(getAddCostFactSheet(lwf_xtnlw_zyxqxtnlw_xj, bolIfExport));
		list.add(getAddCostFactSheet(lwf_xtnlw_zyxqxtnlw_zyxqxtnlw, bolIfExport));
		list.add(getAddCostFactSheet(lwf_xtnlw_zyxqxtnlw_ycxtnlwd, bolIfExport));
		list.add(getAddCostFactSheet(lwf_xtnlw_zyxqxtnlw_whxtnlwd, bolIfExport));
		list.add(getAddCostFactSheet(lwf_xtnlw_xqdsxtnlw, bolIfExport));
		
		list.add(getAddCostFactSheet(groupHeaderCenterListLwfLwpq, bolIfExport));
		//"劳务费", "劳务派遣", "劳务派遣合计", "劳务派遣合计"
		list.add(getAddCostFactSheet(lwf_lwpq_lwpqhj, bolIfExport));
		//"劳务费", "劳务派遣", "中油西气劳务派遣", "小计"
		list.add(getAddCostFactSheet(lwf_lwpq_zyxqlwpq_xj, bolIfExport));
		list.add(getAddCostFactSheet(lwf_lwpq_zyxqlwpq_zyxqlwpq, bolIfExport));
		list.add(getAddCostFactSheet(lwf_lwpq_zyxqlwpq_yclwpqd, bolIfExport));
		list.add(getAddCostFactSheet(lwf_lwpq_zyxqlwpq_whlwpqd, bolIfExport));
		list.add(getAddCostFactSheet(lwf_lwpq_xqdslwpq, bolIfExport));

		ClsCostFactSheet bottomBz = getAddCostFactSheet(bz, bolIfExport);
		bottomBz.setIsRowAllGroup("true");
		list.add(bottomBz);
		
		if(bolIfExport){
			list.add(getAddCostFactSheet(zh, bolIfExport));
		}
		
		return list;
	}

	private static ClsCostFactSheet getAddCostFactSheet(List<String> show, Boolean bolIfExport){
		ClsCostFactSheet retItem = new ClsCostFactSheet();
		int i = 0;
		if(bolIfExport){
			retItem.setName01(show.get(i++).replace(strBrShow, strBrExcel));
			retItem.setName02(show.get(i++).replace(strBrShow, strBrExcel));
			retItem.setName03(show.get(i++).replace(strBrShow, strBrExcel));
			retItem.setName04(show.get(i++).replace(strBrShow, strBrExcel));
		} else {
			retItem.setName01(show.get(i++));
			retItem.setName02(show.get(i++));
			retItem.setName03(show.get(i++));
			retItem.setName04(show.get(i++));
		}
		retItem.setName05(show.get(i++));
		retItem.setName06(show.get(i++));
		retItem.setName07(show.get(i++));
		retItem.setName08(show.get(i++));
		retItem.setName09(show.get(i++));
		retItem.setName10(show.get(i++));
		retItem.setName11(show.get(i++));
		retItem.setName12(show.get(i++));
		retItem.setName13(show.get(i++));
		retItem.setName14(show.get(i++));
		retItem.setName15(show.get(i++));
		retItem.setName16(show.get(i++));
		retItem.setName17(show.get(i++));
		retItem.setName18(show.get(i++));
		retItem.setName19(show.get(i++));
		retItem.setOrder(show.get(i++));
		retItem.setBindingType(show.get(i++));
		retItem.setDeptCode(show.get(i++));
		return retItem;
	}

}
	