package com.fh.controller.staffDetail.staffdetail;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.controller.common.CheckSystemDateTime;
import com.fh.controller.common.Common;
import com.fh.controller.common.Corresponding;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.SelectBillCodeOptions;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.CommonBaseAndList;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.StaffFilterInfo;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.enums.BillState;
import com.fh.util.enums.EmplGroupType;
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.enums.TmplType;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.staffDetail.staffdetail.StaffDetailManager;
import com.fh.service.staffFilter.staffFilter.StaffFilterManager;
import com.fh.service.staffsummy.staffsummy.StaffSummyManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysDeptLtdTime.sysDeptLtdTime.impl.SysDeptLtdTimeService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/**
 * 说明：工资明细 创建人：zhangxiaoliu 创建时间：2017-06-16
 */
@Controller
@RequestMapping(value = "/staffdetail")
public class StaffDetailController extends BaseController {

	String menuUrl = "staffdetail/list.do"; // 菜单地址(权限用)
	@Resource(name = "staffdetailService")
	private StaffDetailManager staffdetailService;
	@Resource(name = "staffsummyService")
	private StaffSummyManager staffsummyService;
	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name = "tmplconfigdictService")
	private TmplConfigDictService tmplconfigdictService;
	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name = "departmentService")
	private DepartmentService departmentService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "sysDeptLtdTimeService")
	private SysDeptLtdTimeService sysDeptLtdTimeService;
	@Resource(name = "staffFilterService")
	private StaffFilterManager staffFilterService;

	// 表名
	String TableNameDetail = "TB_STAFF_DETAIL";
	String TableNameSummy = "TB_STAFF_SUMMY_BILL";
	String TableNameBackup = "TB_STAFF_DETAIL_backup";
	// 临时数据
	String SelectBillCodeFirstShow = "临时数据";
	String SelectBillCodeLastShow = "";
	// 个税字段，不能定义公式，
	String TF_SalarySelf = "GROSS_PAY";
	String TF_SalaryTax = "";// "ACCRD_TAX";
	String TF_BonusSelf = "CUST_COL14";
	String TF_BonusTax = "";// "CUST_COL10";

	// 税字段
	String TableFeildSalaryTaxConfigGradeOper = "S_TAX_CONFIG_GRADE_OPER";
	String TableFeildSalaryTaxConfigSumOper = "S_TAX_CONFIG_SUM_OPER";
	String TableFeildSalaryTaxSelfSumOper = "S_TAX_SELF_SUM_OPER";
	String TableFeildBonusTaxConfigGradeOper = "B_TAX_CONFIG_GRADE_OPER";
	String TableFeildBonusTaxConfigSumOper = "B_TAX_CONFIG_SUM_OPER";
	String TableFeildBonusTaxSelfSumOper = "B_TAX_SELF_SUM_OPER";

	// 默认的which值
	String DefaultWhile = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();

	// 页面显示数据的年月
	// String SystemDateTime = "";
	//
	String AdditionalReportColumns = "";
	// 配置表配置成显示，则是必填项
	private List<String> IfShow_MustInputList = Arrays.asList("USER_CODE", "UNITS_CODE", "STAFF_IDENT", "USER_CATG");

	// 界面查询字段
	List<String> QueryFeildList2 = Arrays.asList("DEPT_CODE", "CUST_COL7", "USER_GROP", "BUSI_DATE");
	List<String> QueryFeildList1 = Arrays.asList("DEPT_CODE", "CUST_COL7", "USER_GROP");
	// 导入必填项在字典里没翻译
	List<String> ImportNotHaveTransferList = Arrays.asList("DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE",
			"USER_CATG");
	// 设置必定不用编辑的列 SERIAL_NO 设置字段类型是数字，但不管隐藏 或显示都必须保存的
	List<String> MustNotEditList = Arrays.asList("SERIAL_NO", "BILL_CODE", "BUSI_DATE", "DEPT_CODE", "CUST_COL7",
			"USER_GROP");
	// 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
	List<String> keyListAdd = new ArrayList<String>();
	List<String> keyListBase = getKeyListBase();

	private List<String> getKeyListBase() {
		List<String> list = new ArrayList<String>();
		for (String strFeild : MustNotEditList) {
			if (!list.contains(strFeild)) {
				list.add(strFeild);
			}
		}
		for (String strFeild : keyListAdd) {
			if (!list.contains(strFeild)) {
				list.add(strFeild);
			}
		}
		return list;
	}

	// 设置分组时不求和字段 SERIAL_NO 设置字段类型是数字，但不用求和
	List<String> jqGridGroupNotSumFeild = Arrays.asList("SERIAL_NO");

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表StaffDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		// 当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("staffDetail/staffdetail/staffdetail_list");

		// String TF_SalaryTax
		PageData SalaryTax = new PageData();
		SalaryTax.put("KEY_CODE", SysConfigKeyCode.IndividualIncomeTax);
		TF_SalaryTax = sysConfigManager.getSysConfigByKey(SalaryTax);
		// String TF_BonusTax
		PageData BonusTax = new PageData();
		BonusTax.put("KEY_CODE", SysConfigKeyCode.BonusIncomeTax);
		TF_BonusTax = sysConfigManager.getSysConfigByKey(BonusTax);

		// while
		getPd.put("which", SelectedTableNo);
		// 单号下拉列表
		// getPd.put("SelectNoBillCodeShow", SelectBillCodeFirstShow);
		getPd.put("InitBillCodeOptions",
				SelectBillCodeOptions.getSelectBillCodeOptions(null, SelectBillCodeFirstShow, SelectBillCodeLastShow));
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);

		// CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		// *********************加载单位树 DEPT_CODE*******************************
		String DepartmentSelectTreeSource = DictsUtil.getDepartmentSelectTreeSource(departmentService);
		if (DepartmentSelectTreeSource.equals("0")) {
			getPd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			getPd.put("departTreeSource", 1);
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// ***********************************************************

		mv.addObject("pd", getPd);
		return mv;
	}

	/**
	 * 单号下拉列表
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBillCodeList")
	public @ResponseBody CommonBase getBillCodeList() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 员工组 必须执行，用来设置汇总和传输上报类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		PageData transferPd = new PageData();
		transferPd.put("SelectedCustCol7", SelectedCustCol7);
		transferPd.put("SelectedDepartCode", SelectedDepartCode);
		transferPd.put("SystemDateTime", SystemDateTime);
		transferPd.put("emplGroupType", emplGroupType);
		// 汇总单据状态不为0，就是没汇总或汇总但没作废
		String strCanOperate = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		// tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
		strCanOperate += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		if (!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))) {
			strCanOperate += " and 1 != 1 ";
		} else {
			// tb_sys_sealed_info不是封存state = '1'
			strCanOperate += QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer, SystemDateTime,
					SelectedCustCol7, SelectedDepartCode);
		}
		if (!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))) {
			strCanOperate += " and 1 != 1 ";
		}
		if (!(emplGroupType != null && !emplGroupType.trim().equals(""))) {
			strCanOperate += " and 1 != 1 ";
		}
		transferPd.put("CanOperate", strCanOperate);
		List<String> getCodeList = staffdetailService.getBillCodeList(transferPd);
		// 下拉列表 value和显示一致
		String returnString = SelectBillCodeOptions.getSelectBillCodeOptions(getCodeList, SelectBillCodeFirstShow,
				SelectBillCodeLastShow);
		commonBase.setMessage(returnString);
		commonBase.setCode(0);

		return commonBase;
	}

	/**
	 * 显示结构
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShowColModel")
	public @ResponseBody CommonBase getShowColModel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "getFirstDetailColModel");
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 员工组 必须执行，用来设置汇总和传输上报类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");

		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, departmentService,
				userService, keyListBase, null, AdditionalReportColumns, IfShow_MustInputList, jqGridGroupNotSumFeild);
		String jqGridColModel = tmpl.generateStructure(SelectedTableNo, SelectedDepartCode, SelectedCustCol7, 3,
				MustNotEditList);

		commonBase.setCode(0);
		commonBase.setMessage(jqGridColModel);

		return commonBase;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表StaffDetail");

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SystemDateTime);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList2);
		if (!(SystemDateTime != null && !SystemDateTime.trim().equals(""))) {
			QueryFeild += " and 1 != 1 ";
		}
		if (!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))) {
			QueryFeild += " and 1 != 1 ";
		}
		if (!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))) {
			QueryFeild += " and 1 != 1 ";
		}
		if (!(emplGroupType != null && !emplGroupType.trim().equals(""))) {
			QueryFeild += " and 1 != 1 ";
		}
		// 根据单号，临时数据为like ' %'
		QueryFeild += QueryFeildString.getQueryFeildBillCodeDetail(SelectedBillCode, SelectBillCodeFirstShow);
		// 不是临时数据，tb_sys_sealed_info不是封存、tb_sys_unlock_info表
		// DEL_STATE（融合系统删除状态为1）数据显示，为0不显示、
		if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
			// tb_sys_sealed_info不是封存state = '1'
			QueryFeild += QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer, SystemDateTime, SelectedCustCol7,
					SelectedDepartCode);
			// tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
			QueryFeild += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		// 汇总单据状态不为0，就是没汇总或汇总但没作废
		QueryFeild += QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		getPd.put("QueryFeild", QueryFeild);
		// 多条件过滤条件
		String filters = getPd.getString("filters");
		if (null != filters && !"".equals(filters)) {
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		// 页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if (null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())) {
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
		page.setPd(getPd);
		List<PageData> varList = staffdetailService.JqPage(page); // 列出Betting列表
		int records = staffdetailService.countJqGridExtend(page);
		PageData userdata = null;
		// 底行显示的求和与平均值字段
		StringBuilder SqlUserdata = Common.GetSqlUserdata(SelectedTableNo, SelectedDepartCode, SelectedCustCol7,
				tmplconfigService);
		if (SqlUserdata != null && !SqlUserdata.toString().trim().equals("")) {
			// 底行显示的求和与平均值字段
			getPd.put("Userdata", SqlUserdata.toString());
			userdata = staffdetailService.getFooterSummary(page);
		}

		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		result.setUserdata(userdata);

		return result;
	}

	// 一些必填项的验证
	public List<String> CheckMustInputFeild(PageData p_pd, List<StaffFilterInfo> listStaffFilterInfo,
			Map<String, TmplConfigDetail> map_SetColumnsList, Boolean bolCheckSalary) {
		List<String> sbRetFeild = new ArrayList<String>();
		String USER_CODE = (String) p_pd.get("USER_CODE");
		if (!(USER_CODE != null && !USER_CODE.trim().equals(""))) {
			sbRetFeild.add("人员编码不能为空！");
		}
		String BUSI_DATE = (String) p_pd.get("BUSI_DATE");
		if (!(BUSI_DATE != null && !BUSI_DATE.trim().equals(""))) {
			sbRetFeild.add("区间不能为空！");
		}
		String CUST_COL7 = (String) p_pd.get("CUST_COL7");
		if (!(CUST_COL7 != null && !CUST_COL7.trim().equals(""))) {
			sbRetFeild.add("账套不能为空！");
		}
		String DEPT_CODE = (String) p_pd.get("DEPT_CODE");
		if (!(DEPT_CODE != null && !DEPT_CODE.trim().equals(""))) {
			sbRetFeild.add("单位不能为空！");
		}
		String USER_GROP = (String) p_pd.get("USER_GROP");
		if (!(USER_GROP != null && !USER_GROP.trim().equals(""))) {
			sbRetFeild.add("员工组不能为空！");
		}
		String UNITS_CODE = (String) p_pd.get("UNITS_CODE");
		if (!(UNITS_CODE != null && !UNITS_CODE.trim().equals(""))) {
			sbRetFeild.add("所属二级单位不能为空！");
		}
		// 是否验证身份证号不为空，
		Boolean bolCheckSTAFF_IDENT = true;
		// 不显示就不验证
		TmplConfigDetail tmpl = map_SetColumnsList.get("STAFF_IDENT");
		if (!(tmpl != null && tmpl.getCOL_HIDE() != null && tmpl.getCOL_HIDE().trim().equals("1"))) {
			bolCheckSTAFF_IDENT = false;
		}
		// tb_staff_filter_info有对应的类型、账套、责任中心、工资范围编码或ANY,STAFF_IDENT_STATE =
		// 1验证，！=1不验证
		if (bolCheckSTAFF_IDENT) {
			// 工资范围编码
			String getSAL_RANGE = (String) p_pd.get("SAL_RANGE");
			if (getSAL_RANGE != null && listStaffFilterInfo != null) {
				for (StaffFilterInfo filter : listStaffFilterInfo) {
					if (("ANY").toUpperCase().trim().equals(filter.getSAL_RANGE().toUpperCase().trim())
							|| getSAL_RANGE.equals(filter.getSAL_RANGE())) {
						if (!(filter.getSTAFF_IDENT_STATE() != null && filter.getSTAFF_IDENT_STATE().equals("1"))) {
							bolCheckSTAFF_IDENT = false;
						}
					}
				}
			}
		}
		// 验证身份证号不能为空
		if (bolCheckSTAFF_IDENT) {
			String getSTAFF_IDENT = (String) p_pd.get("STAFF_IDENT");
			if (!(getSTAFF_IDENT != null && !getSTAFF_IDENT.trim().equals(""))) {
				sbRetFeild.add("身份证号不能为空！");
			}
		}
		if (bolCheckSalary) {
			Object SalaryTax = p_pd.get(TF_SalaryTax);
			if (!(SalaryTax != null && SalaryTax.toString() != null && !SalaryTax.toString().equals("")))
				SalaryTax = 0;
			BigDecimal douSalaryTax = new BigDecimal(SalaryTax.toString());
			if (douSalaryTax.compareTo(new BigDecimal(0)) < 0) {
				sbRetFeild.add("工资税不能是负数！");
			}
		}
		return sbRetFeild;
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public @ResponseBody CommonBase edit() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername() + "修改StaffDetail");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// //校验权限

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		// 操作
		String oper = getPd.getString("oper");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		// 判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if (mesDateTime != null && !mesDateTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		// 判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7, ShowDataCustCol7,
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource, SelectedBillCode, ShowDataBillCode);
		if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		// 验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode, sysDeptLtdTimeService);
		if (mesSysDeptLtdTime != null && !mesSysDeptLtdTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}

		// 汇总单据状态不为0，就是没汇总或汇总但没作废
		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
			// tb_sys_sealed_info不是封存state = '1'
			strHelpful += QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer, SystemDateTime, SelectedCustCol7,
					SelectedDepartCode);
			// tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if (!(strHelpful != null && !strHelpful.trim().equals(""))) {
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}
		// 不用验证导入税的情况、验证身份证号
		List<StaffFilterInfo> listStaffFilterInfo = getStaffFilter(SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode);
		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode,
				SelectedCustCol7, tmplconfigService);

		// 必定不用编辑的列 MustNotEditList Arrays.asList("SERIAL_NO", "BILL_CODE",
		// "BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP");
		if (oper.equals("add")) {
			// 默认值
			getPd.put("SERIAL_NO", "");
			getPd.put("BUSI_DATE", SystemDateTime);
			getPd.put("DEPT_CODE", SelectedDepartCode);
			getPd.put("CUST_COL7", SelectedCustCol7);
			getPd.put("USER_GROP", emplGroupType);
			if (SelectedBillCode.equals(SelectBillCodeFirstShow)) {
				getPd.put("BILL_CODE", "");
			} else {
				getPd.put("BILL_CODE", SelectedBillCode);
			}
			String getESTB_DEPT = (String) getPd.get("ESTB_DEPT");
			if (!(getESTB_DEPT != null && !getESTB_DEPT.trim().equals(""))) {
				getPd.put("ESTB_DEPT", SelectedDepartCode);
			}
			// 一些必填项的验证
			List<String> sbRetFeild = CheckMustInputFeild(getPd, listStaffFilterInfo, map_SetColumnsList, true);
			if (sbRetFeild != null && sbRetFeild.size() > 0) {
				StringBuilder getCheckMustInputFeild = new StringBuilder();
				for (String str : sbRetFeild) {
					getCheckMustInputFeild.append(str + "  ");
				}
				if (getCheckMustInputFeild != null && !getCheckMustInputFeild.toString().trim().equals("")) {
					commonBase.setCode(2);
					commonBase.setMessage(getCheckMustInputFeild.toString());
					return commonBase;
				}
			}
			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);
			// 验证单据状态
			String checkState = CheckState(SelectedBillCode, SystemDateTime, SelectedCustCol7, SelectedDepartCode,
					emplGroupType, strTypeCodeTramsfer, listData, "SERIAL_NO", TmplUtil.keyExtra);
			if (checkState != null && !checkState.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			// 计算并保存
			commonBase = CalculationUpdateDatabase(true, commonBase, "", SelectedTableNo, SelectedCustCol7,
					SelectedDepartCode, emplGroupType, listData, strHelpful, SystemDateTime, listStaffFilterInfo);
		} else {
			for (String strFeild : MustNotEditList) {
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
			// 一些必填项的验证
			List<String> sbRetFeild = CheckMustInputFeild(getPd, listStaffFilterInfo, map_SetColumnsList, true);
			if (sbRetFeild != null && sbRetFeild.size() > 0) {
				StringBuilder getCheckMustInputFeild = new StringBuilder();
				for (String str : sbRetFeild) {
					getCheckMustInputFeild.append(str + "  ");
				}
				if (getCheckMustInputFeild != null && !getCheckMustInputFeild.toString().trim().equals("")) {
					commonBase.setCode(2);
					commonBase.setMessage(getCheckMustInputFeild.toString());
					return commonBase;
				}
			}
			Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo,
					tmplconfigService);
			List<PageData> listCheckState = new ArrayList<PageData>();
			listCheckState.add(getPd);
			// 验证单据状态
			String checkState = CheckState(SelectedBillCode, SystemDateTime, SelectedCustCol7, SelectedDepartCode,
					emplGroupType, strTypeCodeTramsfer, listCheckState, "SERIAL_NO", TmplUtil.keyExtra);
			if (checkState != null && !checkState.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			// IsNumFeildButMustInput设置字段类型是数字，但不管隐藏 或显示都必须保存的
			// haveColumnsList和map_SetColumnsList，设置保存的数据列及对应值
			Common.setModelDefault(getPd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
			getPd.put("TableName", TableNameDetail);
			getPd.put("CanOperate", strHelpful);

			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);

			// 此处执行集合添加
			staffdetailService.batchUpdateDatabase(listData);
			commonBase.setCode(0);
		}

		return commonBase;
	}

	/**
	 * 批量修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAll")
	public @ResponseBody CommonBase updateAll() throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		// 判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if (mesDateTime != null && !mesDateTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		// 判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7, ShowDataCustCol7,
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource, SelectedBillCode, ShowDataBillCode);
		if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		// 验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode, sysDeptLtdTimeService);
		if (mesSysDeptLtdTime != null && !mesSysDeptLtdTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}

		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
			// tb_sys_sealed_info不是封存state = '1'
			strHelpful += QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer, SystemDateTime, SelectedCustCol7,
					SelectedDepartCode);
			// tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if (!(strHelpful != null && !strHelpful.trim().equals(""))) {
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}

		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);
		// 验证单据状态
		String checkState = CheckState(SelectedBillCode, SystemDateTime, SelectedCustCol7, SelectedDepartCode,
				emplGroupType, strTypeCodeTramsfer, listData, "SERIAL_NO", TmplUtil.keyExtra);
		if (checkState != null && !checkState.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode,
				SelectedCustCol7, tmplconfigService);
		Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo, tmplconfigService);

		List<StaffFilterInfo> listStaffFilterInfo = getStaffFilter(SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode);
		for (PageData item : listData) {
			for (String strFeild : MustNotEditList) {
				item.put(strFeild, item.get(strFeild + TmplUtil.keyExtra));
			}
			item.put("CanOperate", strHelpful);
			item.put("TableName", TableNameDetail);
			Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);

			// 一些必填项的验证
			List<String> sbRetFeild = CheckMustInputFeild(item, listStaffFilterInfo, map_SetColumnsList, true);
			if (sbRetFeild != null && sbRetFeild.size() > 0) {
				StringBuilder getCheckMustInputFeild = new StringBuilder();
				for (String str : sbRetFeild) {
					getCheckMustInputFeild.append(str + "  ");
				}
				if (getCheckMustInputFeild != null && !getCheckMustInputFeild.toString().trim().equals("")) {
					commonBase.setCode(2);
					commonBase.setMessage(getCheckMustInputFeild.toString());
					return commonBase;
				}
			}
		}
		if (null != listData && listData.size() > 0) {
			// 此处执行集合添加
			staffdetailService.batchUpdateDatabase(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAll")
	public @ResponseBody CommonBase deleteAll() throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "delete")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		// 判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if (mesDateTime != null && !mesDateTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		// 判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7, ShowDataCustCol7,
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource, SelectedBillCode, ShowDataBillCode);
		if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		// 验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode, sysDeptLtdTimeService);
		if (mesSysDeptLtdTime != null && !mesSysDeptLtdTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}

		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
			// tb_sys_sealed_info不是封存state = '1'
			strHelpful += QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer, SystemDateTime, SelectedCustCol7,
					SelectedDepartCode);
			// tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if (!(strHelpful != null && !strHelpful.trim().equals(""))) {
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}

		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);
		// 验证单据状态
		String checkState = CheckState(SelectedBillCode, SystemDateTime, SelectedCustCol7, SelectedDepartCode,
				emplGroupType, strTypeCodeTramsfer, listData, "SERIAL_NO", TmplUtil.keyExtra);
		if (checkState != null && !checkState.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		if (null != listData && listData.size() > 0) {
			for (PageData item : listData) {
				item.put("CanOperate", strHelpful);
			}
			staffdetailService.deleteAll(listData);
			commonBase.setCode(0);
		}

		return commonBase;
	}

	/**
	 * 计算
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/calculation")
	public @ResponseBody CommonBase calculation() throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "calculation")){return
		// null;} //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		// 判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if (mesDateTime != null && !mesDateTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		// 判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7, ShowDataCustCol7,
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource, SelectedBillCode, ShowDataBillCode);
		if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		// 验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode, sysDeptLtdTimeService);
		if (mesSysDeptLtdTime != null && !mesSysDeptLtdTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}

		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
			// tb_sys_sealed_info不是封存state = '1'
			strHelpful += QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer, SystemDateTime, SelectedCustCol7,
					SelectedDepartCode);
			// tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if (!(strHelpful != null && !strHelpful.trim().equals(""))) {
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}

		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);

		List<StaffFilterInfo> listStaffFilterInfo = getStaffFilter(SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode);
		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode,
				SelectedCustCol7, tmplconfigService);
		for (PageData item : listData) {
			for (String strFeild : MustNotEditList) {
				item.put(strFeild, item.get(strFeild + TmplUtil.keyExtra));
			}
			// 一些必填项的验证
			List<String> sbRetFeild = CheckMustInputFeild(item, listStaffFilterInfo, map_SetColumnsList, false);
			if (sbRetFeild != null && sbRetFeild.size() > 0) {
				StringBuilder getCheckMustInputFeild = new StringBuilder();
				for (String str : sbRetFeild) {
					getCheckMustInputFeild.append(str + "  ");
				}
				if (getCheckMustInputFeild != null && !getCheckMustInputFeild.toString().trim().equals("")) {
					commonBase.setCode(2);
					commonBase.setMessage(getCheckMustInputFeild.toString());
					return commonBase;
				}
			}
		}
		// 验证单据状态
		String checkState = CheckState(SelectedBillCode, SystemDateTime, SelectedCustCol7, SelectedDepartCode,
				emplGroupType, strTypeCodeTramsfer, listData, "SERIAL_NO", TmplUtil.keyExtra);
		if (checkState != null && !checkState.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		// 计算并保存
		commonBase = CalculationUpdateDatabase(false, commonBase, "", SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode, emplGroupType, listData, strHelpful, SystemDateTime, listStaffFilterInfo);
		return commonBase;
	}

	/**
	 * 打开上传EXCEL页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		// 判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if (mesDateTime != null && !mesDateTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		}

		if (commonBase.getCode() == -1) {
			// 判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7, ShowDataCustCol7,
					SelectedDepartCode, ShowDataDepartCode, DepartTreeSource, SelectedBillCode, ShowDataBillCode);
			if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			}
		}
		if (commonBase.getCode() == -1) {
			// 验证是否在操作时间内
			String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(SelectedTableNo, SelectedCustCol7,
					SelectedDepartCode, sysDeptLtdTimeService);
			if (mesSysDeptLtdTime != null && !mesSysDeptLtdTime.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(mesSysDeptLtdTime);
			}
		}
		if (commonBase.getCode() == -1) {
			if (!SelectedBillCode.equals(SelectBillCodeFirstShow) && commonBase.getCode() != 2) {
				// 不是临时数据要验证单据状态
				String checkState = CheckState(SelectedBillCode, SystemDateTime, SelectedCustCol7, SelectedDepartCode,
						emplGroupType, strTypeCodeTramsfer, null, "SERIAL_NO", TmplUtil.keyExtra);
				if (checkState != null && !checkState.trim().equals("")) {
					commonBase.setCode(2);
					commonBase.setMessage(checkState);
				}
			}
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "staffdetail");
		mv.addObject("which", SelectedTableNo);
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("SelectedBillCode", SelectedBillCode);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("ShowDataBillCode", ShowDataBillCode);
		mv.addObject("SystemDateTime", SystemDateTime);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	/// *
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return
		// null;}//校验权限

		String strErrorMessage = "";
		// String strImportDataType = "";
		String CurrentDepartCode = Jurisdiction.getCurrentDepartmentID();

		String YXRY = EmplGroupType.YXRY;
		String LWPQ = EmplGroupType.LWPQ;
		// 责任中心-管道分公司廊坊油气储运公司-0100106
		String DEPT_CODE_0100106 = "0100106";
		// 责任中心-华北石油管理局-0100107
		String DEPT_CODE_0100107 = "0100107";
		// 责任中心-中国石油天然气管道局-0100108
		String DEPT_CODE_0100108 = "0100108";
		// 责任中心-华北采油二厂-0100109
		String DEPT_CODE_0100109 = "0100109";

		// 工资范围编码-东零
		String SAL_RANGE_dong_0 = "S12";
		// 企业特定员工分类-管道局劳务-PUT05
		String USER_CATG_GDJLW = "PUT05";
		// 企业特定员工分类-华北油田公司劳务-PUT06
		String USER_CATG_hbytgslw = "PUT06";
		// 企业特定员工分类-华北采油二厂劳务-PUT07
		String USER_CATG_HBCYECLW = "PUT07";
		// 企业特定员工分类-管道公司劳务-PUT08
		String USER_CATG_GDGSLW = "PUT08";

		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strTypeCodeTramsfer = Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		// 判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if (mesDateTime != null && !mesDateTime.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		}
		if (commonBase.getCode() == -1) {
			if (!(CurrentDepartCode != null && !CurrentDepartCode.trim().equals(""))) {
				commonBase.setCode(2);
				commonBase.setMessage("当前登录人责任中心为空，请联系管理员！");
			}
		}
		if (commonBase.getCode() == -1) {
			// 判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7, ShowDataCustCol7,
					SelectedDepartCode, ShowDataDepartCode, DepartTreeSource, SelectedBillCode, ShowDataBillCode);
			if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			}
		}
		if (commonBase.getCode() == -1) {
			// 验证是否在操作时间内
			String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(SelectedTableNo, SelectedCustCol7,
					SelectedDepartCode, sysDeptLtdTimeService);
			if (mesSysDeptLtdTime != null && !mesSysDeptLtdTime.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(mesSysDeptLtdTime);
			}
		}
		if (commonBase.getCode() == -1) {
			// 不是临时数据要验证单据状态
			if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
				String checkState = CheckState(SelectedBillCode, SystemDateTime, SelectedCustCol7, SelectedDepartCode,
						emplGroupType, strTypeCodeTramsfer, null, "SERIAL_NO", TmplUtil.keyExtra);
				if (checkState != null && !checkState.trim().equals("")) {
					commonBase.setCode(2);
					commonBase.setMessage(checkState);
				}
			}
		}
		if (commonBase.getCode() == -1) {
			if (!(SystemDateTime != null && !SystemDateTime.trim().equals("") && SelectedDepartCode != null
					&& !SelectedDepartCode.trim().equals(""))) {
				commonBase.setCode(2);
				commonBase.setMessage("当前区间和当前单位不能为空！");
			}
		}
		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if (commonBase.getCode() == -1) {
			if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
				// tb_sys_sealed_info不是封存state = '1'
				strHelpful += QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer, SystemDateTime,
						SelectedCustCol7, SelectedDepartCode);
				// tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
				strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
			}
			if (!(strHelpful != null && !strHelpful.trim().equals(""))) {
				commonBase.setCode(2);
				commonBase.setMessage(Message.GetHelpfulDetailFalue);
			}
		}
		if (commonBase.getCode() == -1) {
			Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo,
					SelectedDepartCode, SelectedCustCol7, tmplconfigService);
			Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo,
					tmplconfigService);
			Map<String, Object> DicList = Common.GetDicList(SelectedTableNo, SelectedDepartCode, SelectedCustCol7,
					tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService,
					AdditionalReportColumns);
			// 局部变量
			LeadingInExcelToPageData<PageData> testExcel = null;
			Map<Integer, Object> uploadAndReadMap = null;
			try {
				// 定义需要读取的数据
				String formart = "yyyy-MM-dd";
				String propertiesFileName = "config";
				String kyeName = "file_path";
				int sheetIndex = 0;
				Map<String, String> titleAndAttribute = null;
				// 定义对应的标题名与对应属性名
				titleAndAttribute = new LinkedHashMap<String, String>();

				// 配置表设置列
				if (map_SetColumnsList != null && map_SetColumnsList.size() > 0) {
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
					}
				}
				// 调用解析工具包
				testExcel = new LeadingInExcelToPageData<PageData>(formart);
				// 解析excel，获取客户信息集合

				Boolean bolIsDicSetSAL_RANGE = false;
				Boolean bolIsDicSetUSER_CATG = false;
				if ((CurrentDepartCode != null && CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001)
						&& emplGroupType.equals(YXRY) && SelectedDepartCode != null
						&& SelectedDepartCode.equals(DictsUtil.DepartShowAll_01001))
						|| (CurrentDepartCode != null && CurrentDepartCode.equals(DictsUtil.DepartShowAll_00)
								&& emplGroupType.equals(YXRY) && SelectedDepartCode != null
								&& SelectedDepartCode.equals(DictsUtil.DepartShowAll_00))) {
					bolIsDicSetSAL_RANGE = true;
				}
				if ((CurrentDepartCode != null && (CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001)
						|| CurrentDepartCode.equals(DictsUtil.DepartShowAll_00)))
						&& (emplGroupType.equals(YXRY))
						&& (SelectedDepartCode.equals(DEPT_CODE_0100107) || SelectedDepartCode.equals(DEPT_CODE_0100108)
								|| SelectedDepartCode.equals(DEPT_CODE_0100106)
								|| SelectedDepartCode.equals(DEPT_CODE_0100109))) {
					// LWPQ.equals(getUSER_GROP) &&
					// (USER_CATG_GDJLW.equals(getUSER_CATG) ||
					// USER_CATG_hbytgslw.equals(getUSER_CATG))
					bolIsDicSetUSER_CATG = true;
				}
				// bolIsDicSetSAL_RANGE或bolIsDicSetUSER_CATG为true：员工组未在字典里获取到翻译时，Excel里是"劳务用工"，设置员工组为LWPQ
				// = "50210004";// 劳务派遣、 劳务用工
				// bolIsDicSetUSER_CATG为true：企业特定员工分类未在字典里获取到翻译时，管道局劳务（公司结算）、华北油田劳务（公司结算）分别按管道局劳务、华北油田公司劳务获取字典
				uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
						titleAndAttribute, map_HaveColumnsList, map_SetColumnsList, DicList, bolIsDicSetSAL_RANGE,
						bolIsDicSetUSER_CATG, null, ImportNotHaveTransferList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("读取Excel文件错误", e);
				throw new CustomException("读取Excel文件错误:" + e.getMessage(), false);
			}
			boolean judgement = false;

			Map<String, String> returnErrorCostomn = (Map<String, String>) uploadAndReadMap.get(2);
			Map<String, String> returnErrorMust = (Map<String, String>) uploadAndReadMap.get(3);
			// if(returnErrorCostomn != null && returnErrorCostomn.size()>0){
			// strErrorMessage += "字典无此翻译： "; // \n
			// for (String k : returnErrorCostomn.keySet())
			// {
			// strErrorMessage += k + " : " + returnErrorCostomn.get(k);
			// }
			// }

			List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
			List<PageData> listAdd = new ArrayList<PageData>();
			if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString())
					&& listUploadAndRead.size() >= 1) {
				judgement = true;
			}
			if (judgement) {
				int listSize = listUploadAndRead.size();
				if (listSize > 0) {
					List<StaffFilterInfo> listStaffFilterInfo = getStaffFilter(SelectedTableNo, SelectedCustCol7,
							SelectedDepartCode);

					List<String> sbRetFeild = new ArrayList<String>();
					String strRetUserCode = "";
					List<String> sbRetUserGroup = new ArrayList<String>();
					String sbRetMust = "";
					for (int i = 0; i < listSize; i++) {
						PageData pdAdd = listUploadAndRead.get(i);
						if (pdAdd.size() <= 0) {
							continue;
						}
						String getUSER_CODE = (String) pdAdd.get("USER_CODE");
						if (!(getUSER_CODE != null && !getUSER_CODE.trim().equals(""))) {
							strRetUserCode = "导入人员编码不能为空！";
							break;
						} else {
							String getMustMessage = returnErrorMust == null ? "" : returnErrorMust.get(getUSER_CODE);
							String getCustomnMessage = returnErrorCostomn == null ? ""
									: returnErrorCostomn.get(getUSER_CODE);
							if (getMustMessage != null && !getMustMessage.trim().equals("")) {
								sbRetMust += "员工编号" + getUSER_CODE + "：" + getMustMessage + " ";
							}
							if (getCustomnMessage != null && !getCustomnMessage.trim().equals("")) {
								strErrorMessage += "员工编号" + getUSER_CODE + "：" + getCustomnMessage + " ";
							}

							String getCUST_COL7 = (String) pdAdd.get("CUST_COL7");
							if (!(getCUST_COL7 != null && !getCUST_COL7.trim().equals(""))) {
								pdAdd.put("CUST_COL7", SelectedCustCol7);
								getCUST_COL7 = SelectedCustCol7;
							}

							String getUSER_GROP = (String) pdAdd.get("USER_GROP");
							// if(!(getUSER_GROP!=null &&
							// !getUSER_GROP.trim().equals(""))){
							// pdAdd.put("USER_GROP", emplGroupType);
							// getUSER_GROP = emplGroupType;
							// }
							if (!(getUSER_GROP != null && !getUSER_GROP.trim().equals(""))) {
								sbRetUserGroup.add("员工编号" + getUSER_CODE + "：导入员工组不能为空！" + " ");
							}

							// 工资范围编码
							String getSAL_RANGE = (String) pdAdd.get("SAL_RANGE");
							// 企业特定员工分类
							String getUSER_CATG = (String) pdAdd.get("USER_CATG");
							TmplConfigDetail configUSER_CATG = map_SetColumnsList.get("USER_CATG");
							if (configUSER_CATG != null && configUSER_CATG.getCOL_HIDE() != null
									&& configUSER_CATG.getCOL_HIDE().trim().equals("1")) {
								if (!(getUSER_CATG != null && !getUSER_CATG.trim().equals(""))) {
									if (!sbRetFeild.contains("企业特定员工分类不能为空！")) {
										sbRetFeild.add("企业特定员工分类不能为空！");
									}
								}
							}
							if ((CurrentDepartCode != null && CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001)
									&& emplGroupType.equals(YXRY) && SelectedDepartCode != null
									&& SelectedDepartCode.equals(DictsUtil.DepartShowAll_01001))
									|| (CurrentDepartCode != null
											&& CurrentDepartCode.equals(DictsUtil.DepartShowAll_00)
											&& emplGroupType.equals(YXRY) && SelectedDepartCode != null
											&& SelectedDepartCode.equals(DictsUtil.DepartShowAll_00))) {
								if (YXRY.equals(getUSER_GROP)) {
									continue;
								}
								if (LWPQ.equals(getUSER_GROP)) {// &&
																// SAL_RANGE_dong_0.equals(getSAL_RANGE)
									pdAdd.put("USER_GROP", YXRY);
									getUSER_GROP = YXRY;
								}
							}
							if ((CurrentDepartCode != null && (CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001)
									|| CurrentDepartCode.equals(DictsUtil.DepartShowAll_00)))
									&& (emplGroupType.equals(YXRY))
									&& (SelectedDepartCode.equals(DEPT_CODE_0100107)
											|| SelectedDepartCode.equals(DEPT_CODE_0100108)
											|| SelectedDepartCode.equals(DEPT_CODE_0100106)
											|| SelectedDepartCode.equals(DEPT_CODE_0100109))) {
								if (YXRY.equals(getUSER_GROP)) {
									continue;
								}
								if (LWPQ.equals(getUSER_GROP)) {
									if (!((SelectedDepartCode.equals(DEPT_CODE_0100107)
											&& USER_CATG_hbytgslw.equals(getUSER_CATG))
											|| (SelectedDepartCode.equals(DEPT_CODE_0100108)
													&& USER_CATG_GDJLW.equals(getUSER_CATG))
											|| (SelectedDepartCode.equals(DEPT_CODE_0100106)
													&& USER_CATG_GDGSLW.equals(getUSER_CATG))
											|| (SelectedDepartCode.equals(DEPT_CODE_0100109)
													&& USER_CATG_HBCYECLW.equals(getUSER_CATG)))) {
										continue;
									}
									pdAdd.put("USER_GROP", YXRY);
									getUSER_GROP = YXRY;
								}
							}

							if (!emplGroupType.equals(getUSER_GROP)) {
								continue;
							}
							String SCH = EmplGroupType.SCH;
							String HTH = EmplGroupType.HTH;
							String XTNLW = EmplGroupType.XTNLW;
							if ((CurrentDepartCode != null && CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001)
									&& SelectedDepartCode != null
									&& SelectedDepartCode.equals(DictsUtil.DepartShowAll_01001)
									&& (emplGroupType.equals(SCH) || emplGroupType.equals(HTH)
											|| emplGroupType.equals(XTNLW)))
									|| (CurrentDepartCode != null
											&& CurrentDepartCode.equals(DictsUtil.DepartShowAll_00)
											&& SelectedDepartCode != null
											&& SelectedDepartCode.equals(DictsUtil.DepartShowAll_00)
											&& (emplGroupType.equals(SCH) || emplGroupType.equals(HTH)
													|| emplGroupType.equals(XTNLW)))) {
								// 账套-新西气东输公司-9870
								String CUST_COL7_xxqdsgs = "9870";
								// 企业特定员工分类-东部管道机关-PUT02
								String USER_CATG_DBGDJG = "PUT02";
								// 账套-西气东输管道-9100
								String CUST_COL7_xqdsgd = "9100";
								// 企业特定员工分类-西气东输管道机关-PUT04
								String USER_CATG_XQDSGDJG = "PUT04";

								// 工资范围编码-东零 String SAL_RANGE_dong_0 = "S12";
								if (!SAL_RANGE_dong_0.equals(getSAL_RANGE)) {
									continue;
								}

								// 账套-新西气东输公司-9870 String CUST_COL7_xxqdsgs =
								// "9870";
								// 企业特定员工分类-东部管道机关-PUT02 String USER_CATG_DBGDJG
								// = "PUT02";
								if (CUST_COL7_xxqdsgs.equals(getCUST_COL7)) {
									if (!USER_CATG_DBGDJG.equals(getUSER_CATG)) {
										continue;
									}
								}
								// 账套-西气东输管道-9100 String CUST_COL7_xqdsgd =
								// "9100";
								// 企业特定员工分类-西气东输管道机关-PUT04 String
								// USER_CATG_XQDSGDJG = "PUT04";
								if (CUST_COL7_xqdsgd.equals(getCUST_COL7)) {
									if (!USER_CATG_XQDSGDJG.equals(getUSER_CATG)) {
										continue;
									}
								}
							}

							if (!SelectedCustCol7.equals(getCUST_COL7)) {
								if (!sbRetFeild.contains("导入账套和当前账套必须一致！")) {
									sbRetFeild.add("导入账套和当前账套必须一致！");
								}
							}
							// if(!emplGroupType.equals(getUSER_GROP)){
							// if(!sbRet.contains("导入员工组和当前员工组必须一致！")){
							// sbRet.add("导入员工组和当前员工组必须一致！");
							// }
							// }

							pdAdd.put("SERIAL_NO", "");
							String getBILL_CODE = (String) pdAdd.get("BILL_CODE");
							if (!(getBILL_CODE != null && !getBILL_CODE.trim().equals(""))) {
								if (SelectedBillCode.equals(SelectBillCodeFirstShow)) {
									pdAdd.put("BILL_CODE", "");
									getBILL_CODE = "";
								} else {
									pdAdd.put("BILL_CODE", SelectedBillCode);
									getBILL_CODE = SelectedBillCode;
								}
							}
							if (SelectedBillCode.equals(SelectBillCodeFirstShow)) {
								if (!"".equals(getBILL_CODE)) {
									if (!sbRetFeild.contains("导入单号和当前单号必须一致！")) {
										sbRetFeild.add("导入单号和当前单号必须一致！");
									}
								}
							} else {
								if (!SelectedBillCode.equals(getBILL_CODE)) {
									if (!sbRetFeild.contains("导入单号和当前单号必须一致！")) {
										sbRetFeild.add("导入单号和当前单号必须一致！");
									}
								}
							}
							String getBUSI_DATE = (String) pdAdd.get("BUSI_DATE");
							if (!(getBUSI_DATE != null && !getBUSI_DATE.trim().equals(""))) {
								pdAdd.put("BUSI_DATE", SystemDateTime);
								getBUSI_DATE = SystemDateTime;
							}
							if (!SystemDateTime.equals(getBUSI_DATE)) {
								if (!sbRetFeild.contains("导入区间和当前区间必须一致！")) {
									sbRetFeild.add("导入区间和当前区间必须一致！");
								}
							}
							String getDEPT_CODE = (String) pdAdd.get("DEPT_CODE");
							if (!(getDEPT_CODE != null && !getDEPT_CODE.trim().equals(""))) {
								pdAdd.put("DEPT_CODE", SelectedDepartCode);
								getDEPT_CODE = SelectedDepartCode;
							}
							if (!SelectedDepartCode.equals(getDEPT_CODE)) {
								if (!sbRetFeild.contains("导入单位和当前单位必须一致！")) {
									sbRetFeild.add("导入单位和当前单位必须一致！");
								}
							}
							/*
							 * //身份证号 String getSTAFF_IDENT = (String)
							 * pdAdd.get("STAFF_IDENT");
							 * if(!(getSTAFF_IDENT!=null &&
							 * !getSTAFF_IDENT.trim().equals(""))){
							 * if(!sbRetFeild.contains("身份证号不能为空！")){
							 * sbRetFeild.add("身份证号不能为空！"); } } String
							 * getUNITS_CODE = (String) pdAdd.get("UNITS_CODE");
							 * if(!(getUNITS_CODE!=null &&
							 * !getUNITS_CODE.trim().equals(""))){
							 * if(!sbRetFeild.contains("所属二级单位不能为空！")){
							 * sbRetFeild.add("所属二级单位不能为空！"); } }
							 */
							String getESTB_DEPT = (String) pdAdd.get("ESTB_DEPT");
							if (!(getESTB_DEPT != null && !getESTB_DEPT.trim().equals(""))) {
								pdAdd.put("ESTB_DEPT", SelectedDepartCode);
							}
							// 一些必填项的验证
							List<String> getCheckMustInputFeild = CheckMustInputFeild(pdAdd, listStaffFilterInfo,
									map_SetColumnsList, true);
							if (getCheckMustInputFeild != null && getCheckMustInputFeild.size() > 0) {
								for (String str : getCheckMustInputFeild) {
									if (!sbRetFeild.contains(str)) {
										sbRetFeild.add(str);
									}
								}
							}
							listAdd.add(pdAdd);
						}
					}
					if (strRetUserCode != null && !strRetUserCode.trim().equals("")) {
						commonBase.setCode(2);
						commonBase.setMessage(strRetUserCode);
					} else {
						if (sbRetMust != null && !sbRetMust.trim().equals("")) {
							commonBase.setCode(3);
							commonBase.setMessage("字典无此翻译, 不能导入： " + sbRetMust);
						} else {
							if (sbRetUserGroup != null && sbRetUserGroup.size() > 0) {
								StringBuilder sbTitle = new StringBuilder();
								for (String str : sbRetUserGroup) {
									sbTitle.append(str + "  "); // \n
								}
								commonBase.setCode(3);
								commonBase.setMessage(sbTitle.toString());
							} else {
								if (sbRetFeild.size() > 0) {
									StringBuilder sbTitle = new StringBuilder();
									for (String str : sbRetFeild) {
										sbTitle.append(str + "  "); // \n
									}
									commonBase.setCode(3);
									commonBase.setMessage(sbTitle.toString());
								} else {
									if (!(listAdd != null && listAdd.size() > 0)) {
										commonBase.setCode(2);
										commonBase.setMessage("无可处理的数据！");
									} else {
										String strCalculationMessage = "";
										// 考虑到有可能一张Excel表相同身份账号多条记录，合并成一条（金额相加后，才是正确计算出的数据），提示一次
										CommonBaseAndList getCommonBaseAndList = getCalculationData(commonBase,
												SelectedTableNo, SelectedCustCol7, SelectedDepartCode, emplGroupType,
												listAdd, strHelpful, SystemDateTime, listStaffFilterInfo);

										//根据配置判断是否需要验证个税校验
										PageData pdTaxCheck = new PageData();
										pdTaxCheck.put("KEY_CODE", SysConfigKeyCode.TaxCheck);
										String taxCheck = sysConfigManager.getSysConfigByKey(pdTaxCheck);
										if (taxCheck.equals("1")) {
											if (Corresponding.CheckCalculation(SelectedTableNo)) {
												for (PageData pdSet : getCommonBaseAndList.getList()) {
													String pdSetSTAFF_IDENT = pdSet.getString("STAFF_IDENT");
													BigDecimal douSalaryCalTax = new BigDecimal(0);// 计算出的税额
													BigDecimal douSalaryImpTax = new BigDecimal(0);// 导入税额
													BigDecimal douSalaryYDRZE = new BigDecimal(0);
													BigDecimal douSalaryYSZE = new BigDecimal(0);

													BigDecimal douBonusCalTax = new BigDecimal(0);
													BigDecimal douBonusImpTax = new BigDecimal(0);
													BigDecimal douBonusYDRZE = new BigDecimal(0);
													BigDecimal douBonusYSZE = new BigDecimal(0);
													for (PageData pdsum : getCommonBaseAndList.getList()) {
														String pdsumSTAFF_IDENT = pdsum.getString("STAFF_IDENT");
														if (pdSetSTAFF_IDENT != null
																&& pdSetSTAFF_IDENT.equals(pdsumSTAFF_IDENT)) {
															douSalaryCalTax = douSalaryCalTax.add(
																	new BigDecimal(pdsum.get(TF_SalaryTax).toString()));
															douSalaryImpTax = douSalaryImpTax.add(new BigDecimal(pdsum
																	.get(TF_SalaryTax + TmplUtil.keyExtra).toString()));
															douSalaryYDRZE = douSalaryYDRZE.add(
																	new BigDecimal(pdsum.get("S_YDRZE").toString()));
															douSalaryYSZE = douSalaryYSZE.add(
																	new BigDecimal(pdsum.get("S_YSZE").toString()));

															douBonusCalTax = douBonusCalTax.add(
																	new BigDecimal(pdsum.get(TF_BonusTax).toString()));
															douBonusImpTax = douBonusImpTax.add(new BigDecimal(pdsum
																	.get(TF_BonusTax + TmplUtil.keyExtra).toString()));
															douBonusYDRZE = douBonusYDRZE.add(
																	new BigDecimal(pdsum.get("B_YDRZE").toString()));
															douBonusYSZE = douBonusYSZE.add(
																	new BigDecimal(pdsum.get("B_YSZE").toString()));
														}
													}
													pdSet.put(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra,
															douSalaryCalTax);
													pdSet.put(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra
															+ TmplUtil.keyExtra, douSalaryImpTax);
													pdSet.put("S_YDRZE" + TmplUtil.keyExtra, douSalaryYDRZE);
													pdSet.put("S_YSZE" + TmplUtil.keyExtra, douSalaryYSZE);

													pdSet.put(TF_BonusTax + TmplUtil.keyExtra + TmplUtil.keyExtra,
															douBonusCalTax);
													pdSet.put(TF_BonusTax + TmplUtil.keyExtra + TmplUtil.keyExtra
															+ TmplUtil.keyExtra, douBonusImpTax);
													pdSet.put("B_YDRZE" + TmplUtil.keyExtra, douBonusYDRZE);
													pdSet.put("B_YSZE" + TmplUtil.keyExtra, douBonusYSZE);
												}
												List<String> listSTAFF_IDENT = new ArrayList<String>();
												List<String> listRetSTAFF_IDENT = new ArrayList<String>();
												for (PageData pdSet : getCommonBaseAndList.getList()) {
													String pdSetSTAFF_IDENT = pdSet.getString("STAFF_IDENT");
													if (!listSTAFF_IDENT.contains(pdSetSTAFF_IDENT)) {
														String pdSetUSER_CODE = pdSet.getString("USER_CODE");
														String pdSetUSER_NAME = pdSet.getString("USER_NAME");
														BigDecimal douSalaryCalTax = new BigDecimal(pdSet.get(
																TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra)
																.toString());
														BigDecimal douSalaryImpTax = new BigDecimal(pdSet
																.get(TF_SalaryTax + TmplUtil.keyExtra
																		+ TmplUtil.keyExtra + TmplUtil.keyExtra)
																.toString());
														BigDecimal douSalaryYSZE = new BigDecimal(
																pdSet.get("S_YSZE" + TmplUtil.keyExtra).toString());
														BigDecimal douSalaryYDRZE = new BigDecimal(
																pdSet.get("S_YDRZE" + TmplUtil.keyExtra).toString());

														BigDecimal douBonusCalTax = new BigDecimal(pdSet.get(
																TF_BonusTax + TmplUtil.keyExtra + TmplUtil.keyExtra)
																.toString());
														BigDecimal douBonusImpTax = new BigDecimal(pdSet
																.get(TF_BonusTax + TmplUtil.keyExtra + TmplUtil.keyExtra
																		+ TmplUtil.keyExtra)
																.toString());
														BigDecimal douBonusYSZE = new BigDecimal(
																pdSet.get("B_YSZE" + TmplUtil.keyExtra).toString());
														BigDecimal douBonusYDRZE = new BigDecimal(
																pdSet.get("B_YDRZE" + TmplUtil.keyExtra).toString());

														if (!(douSalaryCalTax.compareTo(douSalaryImpTax) == 0)) {
															if (douSalaryCalTax.compareTo(new BigDecimal(0)) < 0
																	&& douSalaryImpTax
																			.compareTo(new BigDecimal(0)) == 0) {
																listRetSTAFF_IDENT.add(pdSetSTAFF_IDENT);
															} else {
																strCalculationMessage += " 员工编号:" + pdSetUSER_CODE
																		+ " 员工姓名:" + pdSetUSER_NAME;
																strCalculationMessage += " 工资： " + " 本年应缴个税总额:"
																		+ douSalaryYSZE + " 本年已导入个税总额:"
																		+ (douSalaryYDRZE.subtract(douSalaryImpTax));
																// + " 本次导入纳税额:"
																// +
																// douSalaryImpTax
																if (douSalaryCalTax.compareTo(new BigDecimal(0)) < 0) {
																	strCalculationMessage += " 本次应导入个税额:0  <br/>";
																} else {
																	strCalculationMessage += " 本次应导入个税额:"
																			+ douSalaryCalTax + "  <br/>";
																}
															}
														}
														if (!(douBonusCalTax.compareTo(douBonusImpTax) == 0)) {
															strCalculationMessage += " 员工编号:" + pdSetUSER_CODE
																	+ " 员工姓名:" + pdSetUSER_NAME;
															strCalculationMessage += " 奖金： " + " 本年应缴奖金税总额:"
																	+ douBonusYSZE + " 本年已导入奖金税总额:"
																	+ (douBonusYDRZE.subtract(douBonusImpTax))
																	// + "
																	// 本次导入纳税额:"
																	// +
																	// douBonusImpTax
																	+ " 本次应导入奖金税额:" + douBonusCalTax + "  <br/>";
														}
													}
													listSTAFF_IDENT.add(pdSetSTAFF_IDENT);
												}
												// 工资导入税额不能小于0，如汇总计算税额小于0，汇总导入纳税额等于0，记录身份证号，不验证税额。
												// 在验证阶段完成后，把与记录身份证号相同身份证号的记录的保存计算后税额字段设为0。
												if (listRetSTAFF_IDENT != null && listRetSTAFF_IDENT.size() > 0) {
													for (PageData pdRetSet : getCommonBaseAndList.getList()) {
														String pdRetSetSTAFF_IDENT = pdRetSet.getString("STAFF_IDENT");
														if (listRetSTAFF_IDENT.contains(pdRetSetSTAFF_IDENT)) {
															pdRetSet.put(TF_SalaryTax, pdRetSet
																	.get(TF_SalaryTax + TmplUtil.keyExtra).toString());
														}
													}
												}
											}
										}

										if (strCalculationMessage != null && !strCalculationMessage.trim().equals("")) {
											commonBase.setCode(3);
											commonBase.setMessage(strCalculationMessage);
										} else {
											commonBase = UpdateDatabase(true, commonBase, strErrorMessage,
													SelectedTableNo, SelectedCustCol7, SelectedDepartCode,
													emplGroupType, getCommonBaseAndList, strHelpful);
										}
									}
								}
							}
						}
					}
				}
			} else {
				commonBase.setCode(-1);
				commonBase.setMessage("TranslateUtil");
			}
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "staffdetail");
		mv.addObject("which", SelectedTableNo);
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("SelectedBillCode", SelectedBillCode);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("ShowDataBillCode", ShowDataBillCode);
		mv.addObject("SystemDateTime", SystemDateTime);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/readExcel") public ModelAndView
	 * readExcel(@RequestParam(value="excel",required=false) MultipartFile file)
	 * throws Exception{ CommonBase commonBase = new CommonBase();
	 * commonBase.setCode(-1); //if(!Jurisdiction.buttonJurisdiction(menuUrl,
	 * "add")){return null;}//校验权限
	 * 
	 * String strErrorMessage = ""; //String strImportDataType = ""; String
	 * CurrentDepartCode = Jurisdiction.getCurrentDepartmentID();
	 * 
	 * String YXRY = EmplGroupType.YXRY; String LWPQ = EmplGroupType.LWPQ;
	 * //责任中心-管道分公司廊坊油气储运公司-0100106 String DEPT_CODE_0100106 = "0100106";
	 * //责任中心-华北石油管理局-0100107 String DEPT_CODE_0100107 = "0100107";
	 * //责任中心-中国石油天然气管道局-0100108 String DEPT_CODE_0100108 = "0100108";
	 * //责任中心-华北采油二厂-0100109 String DEPT_CODE_0100109 = "0100109";
	 * 
	 * //工资范围编码-东零 String SAL_RANGE_dong_0 = "S12"; //企业特定员工分类-管道局劳务-PUT05
	 * String USER_CATG_GDJLW = "PUT05"; //企业特定员工分类-华北油田公司劳务-PUT06 String
	 * USER_CATG_hbytgslw = "PUT06"; //企业特定员工分类-华北采油二厂劳务-PUT07 String
	 * USER_CATG_HBCYECLW = "PUT07"; //企业特定员工分类-管道公司劳务-PUT08 String
	 * USER_CATG_GDGSLW = "PUT08";
	 * 
	 * PageData getPd = this.getPageData(); //员工组 String SelectedTableNo =
	 * Corresponding.getWhileValue(getPd.getString("SelectedTableNo"),
	 * DefaultWhile); String strTypeCodeTramsfer =
	 * Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo); String
	 * emplGroupType =
	 * Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo); //单位 String
	 * SelectedDepartCode = getPd.getString("SelectedDepartCode"); int
	 * departSelf = Common.getDepartSelf(departmentService); if(departSelf ==
	 * 1){ SelectedDepartCode = Jurisdiction.getCurrentDepartmentID(); } //账套
	 * String SelectedCustCol7 = getPd.getString("SelectedCustCol7"); //单号
	 * String SelectedBillCode = getPd.getString("SelectedBillCode"); // String
	 * DepartTreeSource = getPd.getString("DepartTreeSource"); String
	 * ShowDataDepartCode = getPd.getString("ShowDataDepartCode"); String
	 * ShowDataCustCol7 = getPd.getString("ShowDataCustCol7"); String
	 * ShowDataBillCode = getPd.getString("ShowDataBillCode"); //当前区间 String
	 * SystemDateTime = getPd.getString("SystemDateTime");
	 * //判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致 String mesDateTime =
	 * CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime,
	 * sysConfigManager, false); if(mesDateTime!=null &&
	 * !mesDateTime.trim().equals("")){ commonBase.setCode(2);
	 * commonBase.setMessage(mesDateTime); } if(commonBase.getCode()==-1){
	 * if(!(CurrentDepartCode!=null && !CurrentDepartCode.trim().equals(""))){
	 * commonBase.setCode(2); commonBase.setMessage("当前登录人责任中心为空，请联系管理员！"); } }
	 * if(commonBase.getCode()==-1){ //判断选择为必须选择的 String strGetCheckMustSelected
	 * = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7,
	 * ShowDataCustCol7, SelectedDepartCode, ShowDataDepartCode,
	 * DepartTreeSource, SelectedBillCode, ShowDataBillCode);
	 * if(strGetCheckMustSelected!=null &&
	 * !strGetCheckMustSelected.trim().equals("")){ commonBase.setCode(2);
	 * commonBase.setMessage(strGetCheckMustSelected); } }
	 * if(commonBase.getCode()==-1){ //验证是否在操作时间内 String mesSysDeptLtdTime =
	 * CheckSystemDateTime.CheckSysDeptLtdTime(SelectedTableNo,
	 * SelectedCustCol7, SelectedDepartCode, sysDeptLtdTimeService);
	 * if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
	 * commonBase.setCode(2); commonBase.setMessage(mesSysDeptLtdTime); } }
	 * if(commonBase.getCode()==-1){
	 * if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){ //不是临时数据要验证单据状态
	 * String checkState = CheckState(SelectedBillCode, SystemDateTime,
	 * SelectedCustCol7, SelectedDepartCode, emplGroupType, strTypeCodeTramsfer,
	 * null, "SERIAL_NO", TmplUtil.keyExtra); if(checkState!=null &&
	 * !checkState.trim().equals("")){ commonBase.setCode(2);
	 * commonBase.setMessage(checkState); } } } if(commonBase.getCode()==-1){
	 * if(!(SystemDateTime!=null && !SystemDateTime.trim().equals("") &&
	 * SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
	 * commonBase.setCode(2); commonBase.setMessage("当前区间和当前单位不能为空！"); } }
	 * String strHelpful =
	 * QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
	 * if(commonBase.getCode()==-1){
	 * if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
	 * //tb_sys_sealed_info不是封存state = '1' strHelpful +=
	 * QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer,
	 * SystemDateTime, SelectedCustCol7, SelectedDepartCode);
	 * //tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。 strHelpful +=
	 * QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo(); }
	 * if(!(strHelpful != null && !strHelpful.trim().equals(""))){
	 * commonBase.setCode(2);
	 * commonBase.setMessage(Message.GetHelpfulDetailFalue); } }
	 * if(commonBase.getCode()==-1){ Map<String, TmplConfigDetail>
	 * map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo,
	 * SelectedDepartCode, SelectedCustCol7, tmplconfigService); Map<String,
	 * TableColumns> map_HaveColumnsList =
	 * Common.GetHaveColumnsList(SelectedTableNo, tmplconfigService);
	 * Map<String, Object> DicList = Common.GetDicList(SelectedTableNo,
	 * SelectedDepartCode, SelectedCustCol7, tmplconfigService,
	 * tmplconfigdictService, dictionariesService, departmentService,
	 * userService, AdditionalReportColumns); // 局部变量
	 * LeadingInExcelToPageData<PageData> testExcel = null; Map<Integer, Object>
	 * uploadAndReadMap = null; try { // 定义需要读取的数据 String formart =
	 * "yyyy-MM-dd"; String propertiesFileName = "config"; String kyeName =
	 * "file_path"; int sheetIndex = 0; Map<String, String> titleAndAttribute =
	 * null; // 定义对应的标题名与对应属性名 titleAndAttribute = new LinkedHashMap<String,
	 * String>();
	 * 
	 * //配置表设置列 if(map_SetColumnsList != null && map_SetColumnsList.size() > 0){
	 * for (TmplConfigDetail col : map_SetColumnsList.values()) {
	 * titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()),
	 * col.getCOL_CODE()); } } // 调用解析工具包 testExcel = new
	 * LeadingInExcelToPageData<PageData>(formart); // 解析excel，获取客户信息集合
	 * 
	 * Boolean bolIsDicSetSAL_RANGE = false; Boolean bolIsDicSetUSER_CATG =
	 * false; if((CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001) &&
	 * emplGroupType.equals(YXRY) && SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_01001)) ||
	 * (CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_00) &&
	 * emplGroupType.equals(YXRY) && SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_00))){
	 * bolIsDicSetSAL_RANGE = true; } if((CurrentDepartCode!=null &&
	 * (CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001) ||
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_00))) &&
	 * (emplGroupType.equals(YXRY)) &&
	 * (SelectedDepartCode.equals(DEPT_CODE_0100107) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100108) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100106) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100109))){
	 * //LWPQ.equals(getUSER_GROP) && (USER_CATG_GDJLW.equals(getUSER_CATG) ||
	 * USER_CATG_hbytgslw.equals(getUSER_CATG)) bolIsDicSetUSER_CATG = true; }
	 * uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName,
	 * kyeName, sheetIndex, titleAndAttribute, map_HaveColumnsList,
	 * map_SetColumnsList, DicList, bolIsDicSetSAL_RANGE, bolIsDicSetUSER_CATG,
	 * null, ImportNotHaveTransferList); } catch (Exception e) {
	 * e.printStackTrace(); logger.error("读取Excel文件错误", e); throw new
	 * CustomException("读取Excel文件错误:" + e.getMessage(),false); } boolean
	 * judgement = false;
	 * 
	 * Map<String, String> returnErrorCostomn = (Map<String, String>)
	 * uploadAndReadMap.get(2); Map<String, String> returnErrorMust =
	 * (Map<String, String>) uploadAndReadMap.get(3); //if(returnErrorCostomn !=
	 * null && returnErrorCostomn.size()>0){ // strErrorMessage += "字典无此翻译： ";
	 * // \n // for (String k : returnErrorCostomn.keySet()) // { //
	 * strErrorMessage += k + " : " + returnErrorCostomn.get(k); // } //}
	 * 
	 * List<PageData> listUploadAndRead = (List<PageData>)
	 * uploadAndReadMap.get(1); List<PageData> listAdd = new
	 * ArrayList<PageData>(); if (listUploadAndRead != null &&
	 * !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >=
	 * 1) { judgement = true; } if (judgement) { int listSize =
	 * listUploadAndRead.size(); if(listSize > 0){ List<String> sbRetFeild = new
	 * ArrayList<String>(); String strRetUserCode = ""; List<String>
	 * sbRetUserGroup = new ArrayList<String>(); String sbRetMust = ""; for(int
	 * i=0;i<listSize;i++){ PageData pdAdd = listUploadAndRead.get(i);
	 * if(pdAdd.size() <= 0){ continue; } String getUSER_CODE = (String)
	 * pdAdd.get("USER_CODE"); if(!(getUSER_CODE!=null &&
	 * !getUSER_CODE.trim().equals(""))){ strRetUserCode = "导入人员编码不能为空！"; break;
	 * } else { String getMustMessage = returnErrorMust==null ? "" :
	 * returnErrorMust.get(getUSER_CODE); String getCustomnMessage =
	 * returnErrorCostomn==null ? "" : returnErrorCostomn.get(getUSER_CODE);
	 * if(getMustMessage!=null && !getMustMessage.trim().equals("")){ sbRetMust
	 * += "员工编号" + getUSER_CODE + "：" + getMustMessage + " "; }
	 * if(getCustomnMessage!=null && !getCustomnMessage.trim().equals("")){
	 * strErrorMessage += "员工编号" + getUSER_CODE + "：" + getCustomnMessage + " ";
	 * }
	 * 
	 * String getCUST_COL7 = (String) pdAdd.get("CUST_COL7");
	 * if(!(getCUST_COL7!=null && !getCUST_COL7.trim().equals(""))){
	 * pdAdd.put("CUST_COL7", SelectedCustCol7); getCUST_COL7 =
	 * SelectedCustCol7; }
	 * 
	 * String getUSER_GROP = (String) pdAdd.get("USER_GROP");
	 * //if(!(getUSER_GROP!=null && !getUSER_GROP.trim().equals(""))){ //
	 * pdAdd.put("USER_GROP", emplGroupType); // getUSER_GROP = emplGroupType;
	 * //} if(!(getUSER_GROP!=null && !getUSER_GROP.trim().equals(""))){
	 * sbRetUserGroup.add("员工编号" + getUSER_CODE + "：导入员工组不能为空！" + " "); }
	 * 
	 * //工资范围编码 String getSAL_RANGE = (String) pdAdd.get("SAL_RANGE");
	 * //企业特定员工分类 String getUSER_CATG = (String) pdAdd.get("USER_CATG");
	 * TmplConfigDetail configUSER_CATG = map_SetColumnsList.get("USER_CATG");
	 * if(configUSER_CATG!=null && configUSER_CATG.getCOL_HIDE()!=null &&
	 * configUSER_CATG.getCOL_HIDE().trim().equals("1")){
	 * if(!(getUSER_CATG!=null && !getUSER_CATG.trim().equals(""))){
	 * if(!sbRetFeild.contains("企业特定员工分类不能为空！")){
	 * sbRetFeild.add("企业特定员工分类不能为空！"); } } } if((CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001) &&
	 * emplGroupType.equals(YXRY) && SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_01001)) ||
	 * (CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_00) &&
	 * emplGroupType.equals(YXRY) && SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_00))){
	 * if(YXRY.equals(getUSER_GROP)){ continue; } if(LWPQ.equals(getUSER_GROP)
	 * && SAL_RANGE_dong_0.equals(getSAL_RANGE)){ pdAdd.put("USER_GROP", YXRY);
	 * getUSER_GROP = YXRY; } } if((CurrentDepartCode!=null &&
	 * (CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001) ||
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_00))) &&
	 * (emplGroupType.equals(YXRY)) &&
	 * (SelectedDepartCode.equals(DEPT_CODE_0100107) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100108) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100106) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100109))){
	 * if(YXRY.equals(getUSER_GROP)){ continue; } if(LWPQ.equals(getUSER_GROP)){
	 * if(!((SelectedDepartCode.equals(DEPT_CODE_0100107) &&
	 * USER_CATG_hbytgslw.equals(getUSER_CATG)) ||
	 * (SelectedDepartCode.equals(DEPT_CODE_0100108) &&
	 * USER_CATG_GDJLW.equals(getUSER_CATG)) ||
	 * (SelectedDepartCode.equals(DEPT_CODE_0100106) &&
	 * USER_CATG_GDGSLW.equals(getUSER_CATG)) ||
	 * (SelectedDepartCode.equals(DEPT_CODE_0100109) &&
	 * USER_CATG_HBCYECLW.equals(getUSER_CATG)))){ continue; }
	 * pdAdd.put("USER_GROP", YXRY); getUSER_GROP = YXRY; } }
	 * 
	 * if(!emplGroupType.equals(getUSER_GROP)){ continue; } String SCH =
	 * EmplGroupType.SCH; String HTH = EmplGroupType.HTH; String XTNLW =
	 * EmplGroupType.XTNLW; if((CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001) &&
	 * SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_01001) &&
	 * (emplGroupType.equals(SCH) || emplGroupType.equals(HTH) ||
	 * emplGroupType.equals(XTNLW))) || (CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_00) &&
	 * SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_00) &&
	 * (emplGroupType.equals(SCH) || emplGroupType.equals(HTH) ||
	 * emplGroupType.equals(XTNLW)))){ //账套-新西气东输公司-9870 String
	 * CUST_COL7_xxqdsgs = "9870"; //企业特定员工分类-东部管道机关-PUT02 String
	 * USER_CATG_DBGDJG = "PUT02"; //账套-西气东输管道-9100 String CUST_COL7_xqdsgd =
	 * "9100"; //企业特定员工分类-西气东输管道机关-PUT04 String USER_CATG_XQDSGDJG = "PUT04";
	 * 
	 * //工资范围编码-东零 String SAL_RANGE_dong_0 = "S12";
	 * if(!SAL_RANGE_dong_0.equals(getSAL_RANGE)){ continue; }
	 * 
	 * //账套-新西气东输公司-9870 String CUST_COL7_xxqdsgs = "9870";
	 * //企业特定员工分类-东部管道机关-PUT02 String USER_CATG_DBGDJG = "PUT02";
	 * if(CUST_COL7_xxqdsgs.equals(getCUST_COL7)){
	 * if(!USER_CATG_DBGDJG.equals(getUSER_CATG)){ continue; } }
	 * //账套-西气东输管道-9100 String CUST_COL7_xqdsgd = "9100";
	 * //企业特定员工分类-西气东输管道机关-PUT04 String USER_CATG_XQDSGDJG = "PUT04";
	 * if(CUST_COL7_xqdsgd.equals(getCUST_COL7)){
	 * if(!USER_CATG_XQDSGDJG.equals(getUSER_CATG)){ continue; } } }
	 * 
	 * if(!SelectedCustCol7.equals(getCUST_COL7)){
	 * if(!sbRetFeild.contains("导入账套和当前账套必须一致！")){
	 * sbRetFeild.add("导入账套和当前账套必须一致！"); } }
	 * //if(!emplGroupType.equals(getUSER_GROP)){ //
	 * if(!sbRet.contains("导入员工组和当前员工组必须一致！")){ //
	 * sbRet.add("导入员工组和当前员工组必须一致！"); // } //}
	 * 
	 * pdAdd.put("SERIAL_NO", ""); String getBILL_CODE = (String)
	 * pdAdd.get("BILL_CODE"); if(!(getBILL_CODE!=null &&
	 * !getBILL_CODE.trim().equals(""))){
	 * if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
	 * pdAdd.put("BILL_CODE", ""); getBILL_CODE = ""; } else {
	 * pdAdd.put("BILL_CODE", SelectedBillCode); getBILL_CODE =
	 * SelectedBillCode; } }
	 * if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
	 * if(!"".equals(getBILL_CODE)){ if(!sbRetFeild.contains("导入单号和当前单号必须一致！")){
	 * sbRetFeild.add("导入单号和当前单号必须一致！"); } } } else {
	 * if(!SelectedBillCode.equals(getBILL_CODE)){
	 * if(!sbRetFeild.contains("导入单号和当前单号必须一致！")){
	 * sbRetFeild.add("导入单号和当前单号必须一致！"); } } } String getBUSI_DATE = (String)
	 * pdAdd.get("BUSI_DATE"); String getDEPT_CODE = (String)
	 * pdAdd.get("DEPT_CODE"); String getUNITS_CODE = (String)
	 * pdAdd.get("UNITS_CODE"); if(!(getBUSI_DATE!=null &&
	 * !getBUSI_DATE.trim().equals(""))){ pdAdd.put("BUSI_DATE",
	 * SystemDateTime); getBUSI_DATE = SystemDateTime; }
	 * if(!SystemDateTime.equals(getBUSI_DATE)){
	 * if(!sbRetFeild.contains("导入区间和当前区间必须一致！")){
	 * sbRetFeild.add("导入区间和当前区间必须一致！"); } } if(!(getDEPT_CODE!=null &&
	 * !getDEPT_CODE.trim().equals(""))){ pdAdd.put("DEPT_CODE",
	 * SelectedDepartCode); getDEPT_CODE = SelectedDepartCode; }
	 * if(!SelectedDepartCode.equals(getDEPT_CODE)){
	 * if(!sbRetFeild.contains("导入单位和当前单位必须一致！")){
	 * sbRetFeild.add("导入单位和当前单位必须一致！"); } } if(!(getUNITS_CODE!=null &&
	 * !getUNITS_CODE.trim().equals(""))){
	 * if(!sbRetFeild.contains("所属二级单位不能为空！")){ sbRetFeild.add("所属二级单位不能为空！"); }
	 * } String getESTB_DEPT = (String) pdAdd.get("ESTB_DEPT");
	 * if(!(getESTB_DEPT!=null && !getESTB_DEPT.trim().equals(""))){
	 * pdAdd.put("ESTB_DEPT", SelectedDepartCode); } listAdd.add(pdAdd); } }
	 * if(strRetUserCode!=null && !strRetUserCode.trim().equals("")){
	 * commonBase.setCode(2); commonBase.setMessage(strRetUserCode); } else {
	 * if(sbRetMust!=null && !sbRetMust.trim().equals("")){
	 * commonBase.setCode(3); commonBase.setMessage("字典无此翻译, 不能导入： " +
	 * sbRetMust); } else { if(sbRetUserGroup!=null && sbRetUserGroup.size()>0){
	 * StringBuilder sbTitle = new StringBuilder(); for(String str :
	 * sbRetUserGroup){ sbTitle.append(str + "  "); // \n }
	 * commonBase.setCode(3); commonBase.setMessage(sbTitle.toString()); } else
	 * { if(sbRetFeild.size()>0){ StringBuilder sbTitle = new StringBuilder();
	 * for(String str : sbRetFeild){ sbTitle.append(str + "  "); // \n }
	 * commonBase.setCode(3); commonBase.setMessage(sbTitle.toString()); } else
	 * { if(!(listAdd!=null && listAdd.size()>0)){ commonBase.setCode(2);
	 * commonBase.setMessage("无可处理的数据！"); } else { String strCalculationMessageS
	 * = ""; String strCalculationMessageB = ""; String strB_CheckSalarySelf =
	 * ""; CommonBaseAndList getCommonBaseAndList = new CommonBaseAndList();
	 * if(SelectedTableNo.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
	 * || SelectedTableNo.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())){
	 * getCommonBaseAndList = getCalculationData(true, true, commonBase,
	 * SelectedTableNo, SelectedCustCol7, SelectedDepartCode, emplGroupType,
	 * listAdd, strHelpful, SystemDateTime); for(PageData pdSet :
	 * getCommonBaseAndList.getList()){ String pdSetUSER_CODE =
	 * pdSet.getString("USER_CODE"); String pdSetB_CheckSalarySelf =
	 * pdSet.getString("B_CheckSalarySelf"); if(pdSetB_CheckSalarySelf!=null &&
	 * !pdSetB_CheckSalarySelf.trim().equals("")){ strB_CheckSalarySelf +=
	 * " 员工编号:" + pdSetUSER_CODE + " 员工姓名:" + pdSet.getString("USER_NAME") +
	 * pdSetB_CheckSalarySelf; } else { BigDecimal douSalaryCalTax = new
	 * BigDecimal(0);//计算出的税额 BigDecimal douSalaryImpTax = new
	 * BigDecimal(0);//导入税额 BigDecimal douSalaryYDRZE = new BigDecimal(0);
	 * BigDecimal douSalaryYSZE = new BigDecimal(0);
	 * 
	 * BigDecimal douBonusCalTax = new BigDecimal(0); BigDecimal douBonusImpTax
	 * = new BigDecimal(0); BigDecimal douBonusYDRZE = new BigDecimal(0);
	 * BigDecimal douBonusYSZE = new BigDecimal(0); for(PageData pdsum :
	 * getCommonBaseAndList.getList()){ String pdsumUSER_CODE =
	 * pdsum.getString("USER_CODE"); if(pdSetUSER_CODE!=null &&
	 * pdSetUSER_CODE.equals(pdsumUSER_CODE)){ douSalaryCalTax =
	 * douSalaryCalTax.add(new BigDecimal(pdsum.get(TF_SalaryTax).toString()));
	 * douSalaryImpTax = douSalaryImpTax.add(new
	 * BigDecimal(pdsum.get(TF_SalaryTax + TmplUtil.keyExtra).toString()));
	 * douSalaryYDRZE = douSalaryYDRZE.add(new
	 * BigDecimal(pdsum.get("S_YDRZE").toString())); douSalaryYSZE =
	 * douSalaryYSZE.add(new BigDecimal(pdsum.get("S_YSZE").toString()));
	 * 
	 * douBonusCalTax = douBonusCalTax.add(new
	 * BigDecimal(pdsum.get(TF_BonusTax).toString())); douBonusImpTax =
	 * douBonusImpTax.add(new BigDecimal(pdsum.get(TF_BonusTax +
	 * TmplUtil.keyExtra).toString())); douBonusYDRZE = douBonusYDRZE.add(new
	 * BigDecimal(pdsum.get("B_YDRZE").toString())); douBonusYSZE =
	 * douBonusYSZE.add(new BigDecimal(pdsum.get("B_YSZE").toString())); } }
	 * pdSet.put(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra,
	 * douSalaryCalTax); pdSet.put(TF_SalaryTax + TmplUtil.keyExtra +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra, douSalaryImpTax);
	 * pdSet.put("S_YDRZE" + TmplUtil.keyExtra, douSalaryYDRZE);
	 * pdSet.put("S_YSZE" + TmplUtil.keyExtra, douSalaryYSZE);
	 * 
	 * pdSet.put(TF_BonusTax + TmplUtil.keyExtra + TmplUtil.keyExtra,
	 * douBonusCalTax); pdSet.put(TF_BonusTax + TmplUtil.keyExtra +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra, douBonusImpTax);
	 * pdSet.put("B_YDRZE" + TmplUtil.keyExtra, douBonusYDRZE);
	 * pdSet.put("B_YSZE" + TmplUtil.keyExtra, douBonusYSZE); } }
	 * if(!(strB_CheckSalarySelf!=null &&
	 * !strB_CheckSalarySelf.trim().equals(""))){ List<String> listUserCode =
	 * new ArrayList<String>(); for(PageData pdSet :
	 * getCommonBaseAndList.getList()){ String pdSetUSER_CODE =
	 * pdSet.getString("USER_CODE"); if(!listUserCode.contains(pdSetUSER_CODE)){
	 * BigDecimal douSalaryCalTax = new BigDecimal(pdSet.get(TF_SalaryTax +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra).toString()); BigDecimal
	 * douSalaryImpTax = new BigDecimal(pdSet.get(TF_SalaryTax +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
	 * BigDecimal douSalaryYSZE = new BigDecimal(pdSet.get("S_YSZE" +
	 * TmplUtil.keyExtra).toString()); BigDecimal douSalaryYDRZE = new
	 * BigDecimal(pdSet.get("S_YDRZE" + TmplUtil.keyExtra).toString());
	 * 
	 * BigDecimal douBonusCalTax = new BigDecimal(pdSet.get(TF_BonusTax +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra).toString()); BigDecimal
	 * douBonusImpTax = new BigDecimal(pdSet.get(TF_BonusTax + TmplUtil.keyExtra
	 * + TmplUtil.keyExtra + TmplUtil.keyExtra).toString()); BigDecimal
	 * douBonusYSZE = new BigDecimal(pdSet.get("B_YSZE" +
	 * TmplUtil.keyExtra).toString()); BigDecimal douBonusYDRZE = new
	 * BigDecimal(pdSet.get("B_YDRZE" + TmplUtil.keyExtra).toString());
	 * 
	 * if(!(douSalaryCalTax.compareTo(douSalaryImpTax) == 0 &&
	 * douBonusCalTax.compareTo(douBonusImpTax) == 0)){ //strCalculationMessage
	 * += " 员工编号:" + pdSetUSER_CODE + " 员工姓名:" + pdSet.getString("USER_NAME");
	 * if(!(douSalaryCalTax.compareTo(douSalaryImpTax) == 0)){
	 * strCalculationMessageS += " 员工编号:" + pdSetUSER_CODE + " 员工姓名:" +
	 * pdSet.getString("USER_NAME"); strCalculationMessageS += " 工资： " +
	 * " 应税总额:" + douSalaryYSZE + " 已导入纳税额:" +
	 * (douSalaryYDRZE.subtract(douSalaryImpTax)) + " 本次导入纳税额:" +
	 * douSalaryImpTax + " 实际应导入纳税额:" + douSalaryCalTax + "     "; }
	 * if(!(douBonusCalTax.compareTo(douBonusImpTax) == 0)){
	 * strCalculationMessageB += " 员工编号:" + pdSetUSER_CODE + " 员工姓名:" +
	 * pdSet.getString("USER_NAME"); strCalculationMessageB += " 奖金： " +
	 * " 应税总额:" + douBonusYSZE + " 已导入纳税额:" +
	 * (douBonusYDRZE.subtract(douBonusImpTax)) + " 本次导入纳税额:" + douBonusImpTax +
	 * " 实际应导入纳税额:" + douBonusCalTax + "  <br/>"; } } }
	 * listUserCode.add(pdSetUSER_CODE); } } } else {
	 * getCommonBaseAndList.setCommonBase(commonBase);
	 * getCommonBaseAndList.setList(listAdd); } if(strB_CheckSalarySelf!=null &&
	 * !strB_CheckSalarySelf.trim().equals("")){ commonBase.setCode(3);
	 * commonBase.setMessage(strB_CheckSalarySelf); } else
	 * if(strCalculationMessageS!=null &&
	 * !strCalculationMessageS.trim().equals("")){ commonBase.setCode(3);
	 * commonBase.setMessage(strCalculationMessageS); } else
	 * if(strCalculationMessageB!=null &&
	 * !strCalculationMessageB.trim().equals("")){ commonBase.setCode(3);
	 * commonBase.setMessage(strCalculationMessageB); } else { commonBase =
	 * UpdateDatabase(true, commonBase, strErrorMessage, SelectedTableNo,
	 * SelectedCustCol7, SelectedDepartCode, emplGroupType,
	 * getCommonBaseAndList, strHelpful); } } } } } } } } else {
	 * commonBase.setCode(-1); commonBase.setMessage("TranslateUtil"); } }
	 * ModelAndView mv = this.getModelAndView();
	 * mv.setViewName("common/uploadExcel"); mv.addObject("local",
	 * "staffdetail"); mv.addObject("which", SelectedTableNo);
	 * mv.addObject("SelectedDepartCode", SelectedDepartCode);
	 * mv.addObject("SelectedCustCol7", SelectedCustCol7);
	 * mv.addObject("SelectedBillCode", SelectedBillCode);
	 * mv.addObject("DepartTreeSource", DepartTreeSource);
	 * mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
	 * mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
	 * mv.addObject("ShowDataBillCode", ShowDataBillCode);
	 * mv.addObject("SystemDateTime", SystemDateTime);
	 * mv.addObject("commonBaseCode", commonBase.getCode());
	 * mv.addObject("commonMessage", commonBase.getMessage()); return mv; }
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/readExcel") public ModelAndView
	 * readExcel(@RequestParam(value="excel",required=false) MultipartFile file)
	 * throws Exception{ CommonBase commonBase = new CommonBase();
	 * commonBase.setCode(-1); //if(!Jurisdiction.buttonJurisdiction(menuUrl,
	 * "add")){return null;}//校验权限
	 * 
	 * String CurrentDepartCode = Jurisdiction.getCurrentDepartmentID();
	 * 
	 * String YXRY = EmplGroupType.YXRY; //责任中心-管道分公司廊坊油气储运公司-0100106 String
	 * DEPT_CODE_0100106 = "0100106"; //责任中心-华北石油管理局-0100107 String
	 * DEPT_CODE_0100107 = "0100107"; //责任中心-中国石油天然气管道局-0100108 String
	 * DEPT_CODE_0100108 = "0100108"; //责任中心-华北采油二厂-0100109 String
	 * DEPT_CODE_0100109 = "0100109";
	 * 
	 * PageData getPd = this.getPageData(); //员工组 String SelectedTableNo =
	 * Corresponding.getWhileValue(getPd.getString("SelectedTableNo"),
	 * DefaultWhile); String strTypeCodeTramsfer =
	 * Corresponding.getTypeCodeTransferFromTmplType(SelectedTableNo); String
	 * emplGroupType =
	 * Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo); //单位 String
	 * SelectedDepartCode = getPd.getString("SelectedDepartCode"); int
	 * departSelf = Common.getDepartSelf(departmentService); if(departSelf ==
	 * 1){ SelectedDepartCode = Jurisdiction.getCurrentDepartmentID(); } //账套
	 * String SelectedCustCol7 = getPd.getString("SelectedCustCol7"); //单号
	 * String SelectedBillCode = getPd.getString("SelectedBillCode"); // String
	 * DepartTreeSource = getPd.getString("DepartTreeSource"); String
	 * ShowDataDepartCode = getPd.getString("ShowDataDepartCode"); String
	 * ShowDataCustCol7 = getPd.getString("ShowDataCustCol7"); String
	 * ShowDataBillCode = getPd.getString("ShowDataBillCode"); //当前区间 String
	 * SystemDateTime = getPd.getString("SystemDateTime");
	 * //判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致 String mesDateTime =
	 * CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime,
	 * sysConfigManager, false); if(mesDateTime!=null &&
	 * !mesDateTime.trim().equals("")){ commonBase.setCode(2);
	 * commonBase.setMessage(mesDateTime); } if(commonBase.getCode()==-1){
	 * if(!(CurrentDepartCode!=null && !CurrentDepartCode.trim().equals(""))){
	 * commonBase.setCode(2); commonBase.setMessage("当前登录人责任中心为空，请联系管理员！"); } }
	 * if(commonBase.getCode()==-1){ //判断选择为必须选择的 String strGetCheckMustSelected
	 * = CheckMustSelectedAndSame(emplGroupType, SelectedCustCol7,
	 * ShowDataCustCol7, SelectedDepartCode, ShowDataDepartCode,
	 * DepartTreeSource, SelectedBillCode, ShowDataBillCode);
	 * if(strGetCheckMustSelected!=null &&
	 * !strGetCheckMustSelected.trim().equals("")){ commonBase.setCode(2);
	 * commonBase.setMessage(strGetCheckMustSelected); } }
	 * if(commonBase.getCode()==-1){ //验证是否在操作时间内 String mesSysDeptLtdTime =
	 * CheckSystemDateTime.CheckSysDeptLtdTime(SelectedDepartCode,
	 * SelectedTableNo, sysDeptLtdTimeService); if(mesSysDeptLtdTime!=null &&
	 * !mesSysDeptLtdTime.trim().equals("")){ commonBase.setCode(2);
	 * commonBase.setMessage(mesSysDeptLtdTime); } }
	 * if(commonBase.getCode()==-1){
	 * if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){ //不是临时数据要验证单据状态
	 * String checkState = CheckState(SelectedBillCode, SystemDateTime,
	 * SelectedCustCol7, SelectedDepartCode, emplGroupType, strTypeCodeTramsfer,
	 * null, "SERIAL_NO", TmplUtil.keyExtra); if(checkState!=null &&
	 * !checkState.trim().equals("")){ commonBase.setCode(2);
	 * commonBase.setMessage(checkState); } } } if(commonBase.getCode()==-1){
	 * if(!(SystemDateTime!=null && !SystemDateTime.trim().equals("") &&
	 * SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
	 * commonBase.setCode(2); commonBase.setMessage("当前区间和当前单位不能为空！"); } }
	 * String strHelpful =
	 * QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
	 * if(commonBase.getCode()==-1){
	 * if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
	 * //tb_sys_sealed_info不是封存state = '1' strHelpful +=
	 * QueryFeildString.getNotReportBillCode(strTypeCodeTramsfer,
	 * SystemDateTime, SelectedCustCol7, SelectedDepartCode);
	 * //tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。 strHelpful +=
	 * QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo(); }
	 * if(!(strHelpful != null && !strHelpful.trim().equals(""))){
	 * commonBase.setCode(2);
	 * commonBase.setMessage(Message.GetHelpfulDetailFalue); } }
	 * if(commonBase.getCode()==-1){ Map<String, Object> DicList =
	 * Common.GetDicList(SelectedTableNo, SelectedDepartCode, SelectedCustCol7,
	 * tmplconfigService, tmplconfigdictService, dictionariesService,
	 * departmentService, userService, AdditionalReportColumns);
	 * 
	 * Map<String, TmplInputTips> TmplInputTipsListAll =
	 * Common.GetCheckTmplInputTips(SelectedTableNo, SystemDateTime,
	 * SelectedCustCol7, SelectedDepartCode, tmplconfigService); Map<String,
	 * TmplInputTips> TmplInputTipsListDic = new HashMap<String,
	 * TmplInputTips>(); Map<String, TmplInputTips> TmplInputTipsListNull = new
	 * HashMap<String, TmplInputTips>();
	 * Common.checkTmplInputTipsListAll(TmplInputTipsListAll,
	 * TmplInputTipsListDic, TmplInputTipsListNull, DicList, commonBase);
	 * 
	 * if(commonBase.getCode()==-1){ Map<String, TmplConfigDetail>
	 * map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo,
	 * SelectedDepartCode, SelectedCustCol7, tmplconfigService); Map<String,
	 * TableColumns> map_HaveColumnsList =
	 * Common.GetHaveColumnsList(SelectedTableNo, tmplconfigService);
	 * 
	 * // 局部变量 LeadingInExcelToPageData<PageData> testExcel = null; Map<Integer,
	 * Object> uploadAndReadMap = null; try { // 定义需要读取的数据 String formart =
	 * "yyyy-MM-dd"; String propertiesFileName = "config"; String kyeName =
	 * "file_path"; int sheetIndex = 0; Map<String, String> titleAndAttribute =
	 * null; // 定义对应的标题名与对应属性名 titleAndAttribute = new LinkedHashMap<String,
	 * String>();
	 * 
	 * //配置表设置列 if(map_SetColumnsList != null && map_SetColumnsList.size() > 0){
	 * for (TmplConfigDetail col : map_SetColumnsList.values()) {
	 * titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()),
	 * col.getCOL_CODE()); } } // 调用解析工具包 testExcel = new
	 * LeadingInExcelToPageData<PageData>(formart); // 解析excel，获取客户信息集合
	 * 
	 * Boolean bolIsDicSetSAL_RANGE = false; Boolean bolIsDicSetUSER_CATG =
	 * false; if((emplGroupType.equals(YXRY) //&& CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001) &&
	 * SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_01001)) ||
	 * (emplGroupType.equals(YXRY) //&& CurrentDepartCode!=null &&
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_00) &&
	 * SelectedDepartCode!=null &&
	 * SelectedDepartCode.equals(DictsUtil.DepartShowAll_00))){
	 * bolIsDicSetSAL_RANGE = true; } if(emplGroupType.equals(YXRY) //&&
	 * (CurrentDepartCode!=null &&
	 * (CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001) ||
	 * CurrentDepartCode.equals(DictsUtil.DepartShowAll_00))) &&
	 * (SelectedDepartCode.equals(DEPT_CODE_0100107) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100108) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100106) ||
	 * SelectedDepartCode.equals(DEPT_CODE_0100109))){
	 * //LWPQ.equals(getUSER_GROP) && (USER_CATG_GDJLW.equals(getUSER_CATG) ||
	 * USER_CATG_hbytgslw.equals(getUSER_CATG)) bolIsDicSetUSER_CATG = true; }
	 * uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName,
	 * kyeName, sheetIndex, titleAndAttribute, map_HaveColumnsList,
	 * map_SetColumnsList, DicList, bolIsDicSetSAL_RANGE, bolIsDicSetUSER_CATG,
	 * TmplInputTipsListDic, null); } catch (Exception e) { e.printStackTrace();
	 * logger.error("读取Excel文件错误", e); throw new CustomException("读取Excel文件错误:"
	 * + e.getMessage(),false); } boolean judgement = false;
	 * 
	 * Map<String, String> returnErrorCostomn = (Map<String, String>)
	 * uploadAndReadMap.get(2); Map<String, String> returnErrorMust =
	 * (Map<String, String>) uploadAndReadMap.get(3);
	 * 
	 * List<PageData> listUploadAndRead = (List<PageData>)
	 * uploadAndReadMap.get(1); List<PageData> listAdd = new
	 * ArrayList<PageData>(); if (listUploadAndRead != null &&
	 * !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >=
	 * 1) { judgement = true; } if (judgement) { int listSize =
	 * listUploadAndRead.size(); if(listSize > 0){ String allStrRetUserCode =
	 * ""; String allStrErrorMustMessage = ""; String allStrErrorCustomnMessage
	 * = ""; String allStrErrorNotNull = ""; String allStrErrorNotSame = "";
	 * for(int i=0;i<listSize;i++){ PageData pdAdd = listUploadAndRead.get(i);
	 * if(pdAdd.size() <= 0){ continue; } //String userRetUserCode = ""; String
	 * userErrorMustMessage = ""; String userErrorCustomnMessage = ""; Boolean
	 * bolMessageNotNull = false; String userErrorNotNull = ""; String
	 * userErrorNotSame = "";
	 * 
	 * String getUSER_CODE1 = (String) pdAdd.get("USER_CODE");
	 * //if(!(getUSER_CODE!=null && !getUSER_CODE.trim().equals(""))){ //
	 * if(!(userRetUserCode!=null && !userRetUserCode.trim().equals(""))){ //
	 * userRetUserCode = "导入员工编号不能为空！"; // } //} String getMustMessage =
	 * returnErrorMust==null ? "" : returnErrorMust.get(getUSER_CODE1); String
	 * getCustomnMessage = returnErrorCostomn==null ? "" :
	 * returnErrorCostomn.get(getUSER_CODE1); if(getMustMessage!=null &&
	 * !getMustMessage.trim().equals("")){ userErrorMustMessage += "员工编号" +
	 * getUSER_CODE1 + "：" + getMustMessage + " "; } if(getCustomnMessage!=null
	 * && !getCustomnMessage.trim().equals("")){ userErrorCustomnMessage +=
	 * "员工编号" + getUSER_CODE1 + "：" + getCustomnMessage + " "; }
	 * pdAdd.put("SERIAL_NO", ""); String getBILL_CODE1 = (String)
	 * pdAdd.get("BILL_CODE"); if(!(getBILL_CODE1!=null &&
	 * !getBILL_CODE1.trim().equals(""))){
	 * if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
	 * pdAdd.put("BILL_CODE", ""); getBILL_CODE1 = ""; } else {
	 * pdAdd.put("BILL_CODE", SelectedBillCode); getBILL_CODE1 =
	 * SelectedBillCode; } } String getBUSI_DATE1 = (String)
	 * pdAdd.get("BUSI_DATE"); if(!(getBUSI_DATE1!=null &&
	 * !getBUSI_DATE1.trim().equals(""))){ pdAdd.put("BUSI_DATE",
	 * SystemDateTime); getBUSI_DATE1 = SystemDateTime; } String getDEPT_CODE1 =
	 * (String) pdAdd.get("DEPT_CODE"); if(!(getDEPT_CODE1!=null &&
	 * !getDEPT_CODE1.trim().equals(""))){ pdAdd.put("DEPT_CODE",
	 * SelectedDepartCode); getDEPT_CODE1 = SelectedDepartCode; } String
	 * getCUST_COL71 = (String) pdAdd.get("CUST_COL7"); if(!(getCUST_COL71!=null
	 * && !getCUST_COL71.trim().equals(""))){ pdAdd.put("CUST_COL7",
	 * SelectedCustCol7); getCUST_COL71 = SelectedCustCol7; } String
	 * getESTB_DEPT1 = (String) pdAdd.get("ESTB_DEPT"); if(!(getESTB_DEPT1!=null
	 * && !getESTB_DEPT1.trim().equals(""))){ pdAdd.put("ESTB_DEPT",
	 * SelectedDepartCode); } String getUSER_GROP1 = (String)
	 * pdAdd.get("USER_GROP");
	 * 
	 * Boolean bolCondAdd = true; if(TmplInputTipsListNull!=null &&
	 * TmplInputTipsListNull.size()>0){ for(TmplInputTips tip :
	 * TmplInputTipsListNull.values()){ Object objColValue =
	 * pdAdd.get(tip.getCOL_CODE().toUpperCase()); String strColValue =
	 * objColValue==null ? "" : String.valueOf(objColValue); String getCOL_NULL
	 * = tip.getCOL_NULL(); if(String.valueOf(1).equals(getCOL_NULL)){
	 * if(!(strColValue!=null && !strColValue.toString().trim().equals(""))){
	 * bolMessageNotNull = true; userErrorNotNull += tip.getNULL_VALUE_PREFIX()
	 * + "" + tip.getNULL_VALUE_SUFFIX(); } } String getCOL_COND =
	 * tip.getCOL_COND(); if(getCOL_COND!=null &&
	 * !getCOL_COND.trim().equals("")){ String[] arrCOL_COND =
	 * getCOL_COND.replace(" ", "").replace("，", ",").split(",");
	 * if(arrCOL_COND!=null && arrCOL_COND.length>0){ List<String> listCOL_COND
	 * = Arrays.asList(arrCOL_COND); if(listCOL_COND.contains(strColValue)){
	 * String getCOL_MAPPING = tip.getCOL_MAPPING(); if(getCOL_MAPPING!=null &&
	 * !getCOL_MAPPING.trim().equals("")){ String[] arrCOL_MAPPING =
	 * getCOL_MAPPING.replace(" ", "").replace("，", ",").split(",");
	 * List<String> listCOL_MAPPING = Arrays.asList(arrCOL_MAPPING); for(int
	 * num=0; num<listCOL_COND.size(); num++){
	 * if(listCOL_COND.get(num).equals(strColValue)){
	 * pdAdd.put(tip.getCOL_CODE().toUpperCase(), listCOL_MAPPING.get(num)); } }
	 * } } else { bolCondAdd = false; } } } } } if(!bolCondAdd){ continue; }
	 * 
	 * String getBILL_CODE2 = (String) pdAdd.get("BILL_CODE"); String
	 * getBUSI_DATE2 = (String) pdAdd.get("BUSI_DATE"); String getDEPT_CODE2 =
	 * (String) pdAdd.get("DEPT_CODE"); String getCUST_COL72 = (String)
	 * pdAdd.get("CUST_COL7"); String getUSER_GROP2 = (String)
	 * pdAdd.get("USER_GROP");
	 * 
	 * if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
	 * if(!"".equals(getBILL_CODE2)){ userErrorNotSame += "导入单号和当前单号必须一致！"; } }
	 * else { if(!SelectedBillCode.equals(getBILL_CODE2)){ userErrorNotSame +=
	 * "导入单号和当前单号必须一致！"; } } if(!SystemDateTime.equals(getBUSI_DATE2)){
	 * userErrorNotSame += "导入区间和当前区间必须一致！"; }
	 * if(!SelectedCustCol7.equals(getCUST_COL72)){ userErrorNotSame +=
	 * "导入账套和当前账套必须一致！"; } if(!SelectedDepartCode.equals(getDEPT_CODE2)){
	 * userErrorNotSame += "导入单位和当前单位必须一致！"; }
	 * if(!emplGroupType.equals(getUSER_GROP2)){ userErrorNotSame +=
	 * "导入员工组和当前员工组必须一致！"; } listAdd.add(pdAdd); //if(!(allStrRetUserCode!=null
	 * && !allStrRetUserCode.trim().equals(""))){ // allStrRetUserCode +=
	 * userRetUserCode; //} allStrErrorMustMessage += userErrorMustMessage;
	 * allStrErrorCustomnMessage += userErrorCustomnMessage;
	 * if(bolMessageNotNull){ allStrErrorNotNull += "员工编号" + getUSER_CODE1 + "："
	 * + userErrorNotNull + " "; } if(userErrorNotSame!=null &&
	 * !userErrorNotSame.trim().equals("")){ allStrErrorNotSame += "员工编号" +
	 * getUSER_CODE1 + "：" + userErrorNotSame + " "; } }
	 * if(allStrRetUserCode!=null && !allStrRetUserCode.trim().equals("")){
	 * commonBase.setCode(2); commonBase.setMessage(allStrRetUserCode); } else {
	 * if(allStrErrorMustMessage!=null &&
	 * !allStrErrorMustMessage.trim().equals("")){ commonBase.setCode(3);
	 * commonBase.setMessage("字典无此翻译, 不能导入： " + allStrErrorMustMessage); } else
	 * { if(allStrErrorNotNull!=null && !allStrErrorNotNull.trim().equals("")){
	 * commonBase.setCode(3); commonBase.setMessage(allStrErrorNotNull); } else
	 * { if(allStrErrorNotSame!=null && !allStrErrorNotSame.trim().equals("")){
	 * commonBase.setCode(3); commonBase.setMessage(allStrErrorNotSame); } else
	 * { if(!(listAdd!=null && listAdd.size()>0)){ commonBase.setCode(2);
	 * commonBase.setMessage("无可处理的数据！"); } else { String strCalculationMessage
	 * = ""; CommonBaseAndList getCommonBaseAndList = new CommonBaseAndList();
	 * if(SelectedTableNo.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
	 * || SelectedTableNo.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())){
	 * getCommonBaseAndList = getCalculationData(true, true, commonBase,
	 * SelectedTableNo, SelectedCustCol7, SelectedDepartCode, emplGroupType,
	 * listAdd, strHelpful, SystemDateTime); for(PageData pdSet :
	 * getCommonBaseAndList.getList()){ String pdSetUSER_CODE =
	 * pdSet.getString("USER_CODE"); BigDecimal douSalaryCalTax = new
	 * BigDecimal(0);//计算出的税额 BigDecimal douSalaryImpTax = new
	 * BigDecimal(0);//导入税额 BigDecimal douSalaryYDRZE = new BigDecimal(0);
	 * BigDecimal douSalaryYSZE = new BigDecimal(0);
	 * 
	 * BigDecimal douBonusCalTax = new BigDecimal(0); BigDecimal douBonusImpTax
	 * = new BigDecimal(0); BigDecimal douBonusYDRZE = new BigDecimal(0);
	 * BigDecimal douBonusYSZE = new BigDecimal(0); for(PageData pdsum :
	 * getCommonBaseAndList.getList()){ String pdsumUSER_CODE =
	 * pdsum.getString("USER_CODE"); if(pdSetUSER_CODE!=null &&
	 * pdSetUSER_CODE.equals(pdsumUSER_CODE)){ douSalaryCalTax =
	 * douSalaryCalTax.add(new BigDecimal(pdsum.get(TF_SalaryTax).toString()));
	 * douSalaryImpTax = douSalaryImpTax.add(new
	 * BigDecimal(pdsum.get(TF_SalaryTax + TmplUtil.keyExtra).toString()));
	 * douSalaryYDRZE = douSalaryYDRZE.add(new
	 * BigDecimal(pdsum.get("S_YDRZE").toString())); douSalaryYSZE =
	 * douSalaryYSZE.add(new BigDecimal(pdsum.get("S_YSZE").toString()));
	 * 
	 * douBonusCalTax = douBonusCalTax.add(new
	 * BigDecimal(pdsum.get(TF_BonusTax).toString())); douBonusImpTax =
	 * douBonusImpTax.add(new BigDecimal(pdsum.get(TF_BonusTax +
	 * TmplUtil.keyExtra).toString())); douBonusYDRZE = douBonusYDRZE.add(new
	 * BigDecimal(pdsum.get("B_YDRZE").toString())); douBonusYSZE =
	 * douBonusYSZE.add(new BigDecimal(pdsum.get("B_YSZE").toString())); } }
	 * pdSet.put(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra,
	 * douSalaryCalTax); pdSet.put(TF_SalaryTax + TmplUtil.keyExtra +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra, douSalaryImpTax);
	 * pdSet.put("S_YDRZE" + TmplUtil.keyExtra, douSalaryYDRZE);
	 * pdSet.put("S_YSZE" + TmplUtil.keyExtra, douSalaryYSZE);
	 * 
	 * pdSet.put(TF_BonusTax + TmplUtil.keyExtra + TmplUtil.keyExtra,
	 * douBonusCalTax); pdSet.put(TF_BonusTax + TmplUtil.keyExtra +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra, douBonusImpTax);
	 * pdSet.put("B_YDRZE" + TmplUtil.keyExtra, douBonusYDRZE);
	 * pdSet.put("B_YSZE" + TmplUtil.keyExtra, douBonusYSZE); } List<String>
	 * listUserCode = new ArrayList<String>(); for(PageData pdSet :
	 * getCommonBaseAndList.getList()){ String pdSetUSER_CODE =
	 * pdSet.getString("USER_CODE"); if(!listUserCode.contains(pdSetUSER_CODE)){
	 * BigDecimal douSalaryCalTax = new BigDecimal(pdSet.get(TF_SalaryTax +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra).toString()); BigDecimal
	 * douSalaryImpTax = new BigDecimal(pdSet.get(TF_SalaryTax +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
	 * BigDecimal douSalaryYSZE = new BigDecimal(pdSet.get("S_YSZE" +
	 * TmplUtil.keyExtra).toString()); BigDecimal douSalaryYDRZE = new
	 * BigDecimal(pdSet.get("S_YDRZE" + TmplUtil.keyExtra).toString());
	 * 
	 * BigDecimal douBonusCalTax = new BigDecimal(pdSet.get(TF_BonusTax +
	 * TmplUtil.keyExtra + TmplUtil.keyExtra).toString()); BigDecimal
	 * douBonusImpTax = new BigDecimal(pdSet.get(TF_BonusTax + TmplUtil.keyExtra
	 * + TmplUtil.keyExtra + TmplUtil.keyExtra).toString()); BigDecimal
	 * douBonusYSZE = new BigDecimal(pdSet.get("B_YSZE" +
	 * TmplUtil.keyExtra).toString()); BigDecimal douBonusYDRZE = new
	 * BigDecimal(pdSet.get("B_YDRZE" + TmplUtil.keyExtra).toString());
	 * 
	 * if(!(douSalaryCalTax.compareTo(douSalaryImpTax) == 0 &&
	 * douBonusCalTax.compareTo(douBonusImpTax) == 0)){ strCalculationMessage +=
	 * " 员工编号:" + pdSetUSER_CODE + " 员工姓名:" + pdSet.getString("USER_NAME");
	 * if(!(douSalaryCalTax.compareTo(douSalaryImpTax) == 0)){
	 * strCalculationMessage += " 工资： " + " 应税总额:" + douSalaryYSZE + " 已导入纳税额:"
	 * + (douSalaryYDRZE.subtract(douSalaryImpTax)) + " 本次导入纳税额:" +
	 * douSalaryImpTax + " 实际应导入纳税额:" + douSalaryCalTax + "     "; }
	 * if(!(douBonusCalTax.compareTo(douBonusImpTax) == 0)){
	 * strCalculationMessage += " 奖金： " + " 应税总额:" + douBonusYSZE + " 已导入纳税额:" +
	 * (douBonusYDRZE.subtract(douBonusImpTax)) + " 本次导入纳税额:" + douBonusImpTax +
	 * " 实际应导入纳税额:" + douBonusCalTax + "  <br/>"; } } }
	 * listUserCode.add(pdSetUSER_CODE); } } else {
	 * getCommonBaseAndList.setCommonBase(commonBase);
	 * getCommonBaseAndList.setList(listAdd); } if(strCalculationMessage!=null
	 * && !strCalculationMessage.trim().equals("")){ commonBase.setCode(3);
	 * commonBase.setMessage(strCalculationMessage); } else { commonBase =
	 * UpdateDatabase(true, commonBase, allStrErrorCustomnMessage,
	 * SelectedTableNo, SelectedCustCol7, SelectedDepartCode, emplGroupType,
	 * getCommonBaseAndList, strHelpful); } } } } } } } } else {
	 * commonBase.setCode(-1); commonBase.setMessage("TranslateUtil"); } } }
	 * ModelAndView mv = this.getModelAndView();
	 * mv.setViewName("common/uploadExcel"); mv.addObject("local",
	 * "staffdetail"); mv.addObject("which", SelectedTableNo);
	 * mv.addObject("SelectedDepartCode", SelectedDepartCode);
	 * mv.addObject("SelectedCustCol7", SelectedCustCol7);
	 * mv.addObject("SelectedBillCode", SelectedBillCode);
	 * mv.addObject("DepartTreeSource", DepartTreeSource);
	 * mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
	 * mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
	 * mv.addObject("ShowDataBillCode", ShowDataBillCode);
	 * mv.addObject("SystemDateTime", SystemDateTime);
	 * mv.addObject("commonBaseCode", commonBase.getCode());
	 * mv.addObject("commonMessage", commonBase.getMessage()); return mv; }//
	 */

	private CommonBaseAndList getCalculationData(CommonBase commonBase, String SelectedTableNo, String SelectedCustCol7,
			String SelectedDepartCode, String emplGroupType, List<PageData> listData, String strHelpful,
			String SystemDateTimess, List<StaffFilterInfo> listStaffFilterInfo) throws Exception {
		CommonBaseAndList retCommonBaseAndList = new CommonBaseAndList();
		if (listData != null && listData.size() > 0) {

			Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo,
					SelectedDepartCode, SelectedCustCol7, tmplconfigService);
			Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo,
					tmplconfigService);
			// 税字段，不能定义公式
			if (!(TF_SalaryTax != null && map_SetColumnsList.containsKey(TF_SalaryTax.toUpperCase())
					&& TF_BonusTax != null && map_SetColumnsList.containsKey(TF_BonusTax.toUpperCase()))) {
				commonBase.setCode(2);
				commonBase.setMessage("配置表里定义列（个人所得税、全年一次性奖金税）必须在结构配置表中，请联系管理员修改！");
				retCommonBaseAndList.setCommonBase(commonBase);
				return retCommonBaseAndList;
			}
			if (TF_SalaryTax.toUpperCase().equals(TF_BonusTax.toUpperCase())
					|| TF_SalarySelf.toUpperCase().equals(TF_BonusTax.toUpperCase())
					|| TF_SalarySelf.toUpperCase().equals(TF_SalaryTax.toUpperCase())
					|| TF_SalarySelf.toUpperCase().equals(TF_BonusSelf.toUpperCase())
					|| TF_BonusSelf.toUpperCase().equals(TF_BonusTax.toUpperCase())
					|| TF_BonusSelf.toUpperCase().equals(TF_SalaryTax.toUpperCase())) {
				commonBase.setCode(2);
				commonBase.setMessage("配置表里定义列（应发合计、个人所得税、全年一次性奖金税、全年一次性奖金）不能定义为相同列，请联系管理员修改！");
				retCommonBaseAndList.setCommonBase(commonBase);
				return retCommonBaseAndList;
			}
			TmplConfigDetail colTableFeildSalaryTax = map_SetColumnsList.get(TF_SalaryTax.toUpperCase());
			if (colTableFeildSalaryTax.getCOL_FORMULA() != null
					&& !colTableFeildSalaryTax.getCOL_FORMULA().trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage("个人所得税字段不能定义公式，请联系管理员修改！");
				retCommonBaseAndList.setCommonBase(commonBase);
				return retCommonBaseAndList;
			}
			TmplConfigDetail colTableFeildBonusTax = map_SetColumnsList.get(TF_BonusTax.toUpperCase());
			if (colTableFeildBonusTax.getCOL_FORMULA() != null
					&& !colTableFeildBonusTax.getCOL_FORMULA().trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage("全年一次性奖金税字段不能定义公式，请联系管理员修改！");
				retCommonBaseAndList.setCommonBase(commonBase);
				return retCommonBaseAndList;
			}

			for (PageData item : listData) {
				item.put("CanOperate", strHelpful);
				item.put("TableName", TableNameBackup);
				Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
			}
			PageData getQueryFeildPd = new PageData();
			getQueryFeildPd.put("USER_GROP", emplGroupType);
			// ************去掉按责任中心统计*************** jiachao**********//
			// getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
			// *********************************************************//
			getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
			String QueryFeildPart = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList1);

			// ************增加排除4个责任中心统计（管道公司劳务0100106、华北油田劳务0100107、管道局劳务0100108、华北采油二厂劳务0100109）***//
			QueryFeildPart += " and DEPT_CODE not in ('0100106','0100107','0100108','0100109') ";
			// ***********************************************************************//

			if (!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))) {
				QueryFeildPart += " and 1 != 1 ";
			}
			if (!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))) {
				QueryFeildPart += " and 1 != 1 ";
			}
			if (!(emplGroupType != null && !emplGroupType.trim().equals(""))) {
				QueryFeildPart += " and 1 != 1 ";
			}
			String strSystemDateTimeYear = CheckSystemDateTime.getSystemDateTimeYear(SystemDateTimess);
			// String strSystemDateTimeMouth =
			// CheckSystemDateTime.getSystemDateTimeMouth(SystemDateTimess);
			String QueryFeildBusiPreAndNewMonth = " and BUSI_DATE > '" + strSystemDateTimeYear + "00' "
					+ " and BUSI_DATE <= '" + SystemDateTimess + "' ";
			String QueryFeildBusiPreNotNewMonth = " and BUSI_DATE > '" + strSystemDateTimeYear + "00' "
					+ " and BUSI_DATE < '" + SystemDateTimess + "' ";
			String QueryFeildBusiAllYear = " and BUSI_DATE like '" + strSystemDateTimeYear + "%' ";
			// QueryFeild
			// and DEPT_CODE in ('01002') and USER_GROP in ('50210002') and
			// CUST_COL7 in ('9870')
			// strSumInvalidNotInsert
			// and BILL_CODE not in (select BILL_CODE from TB_STAFF_SUMMY_BILL
			// where BILL_STATE = '0')
			// and BILL_CODE not in (select BILL_CODE from tb_gl_cert where
			// REVCERT_CODE not like ' %')
			String strSumInvalidNotInsert = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
			strSumInvalidNotInsert += " and BILL_CODE not in (select BILL_CODE from tb_gl_cert where REVCERT_CODE not like ' %') ";

			// ExemptionTaxSalary
			// 5000
			PageData SalaryExemptionTaxPd = new PageData();
			SalaryExemptionTaxPd.put("KEY_CODE", SysConfigKeyCode.ExemptionTax);
			String ExemptionTaxSalary = sysConfigManager.getSysConfigByKey(SalaryExemptionTaxPd);
			if (!(ExemptionTaxSalary != null && !ExemptionTaxSalary.trim().equals(""))) {
				ExemptionTaxSalary = "0";
			}
			// configFormulaSalary
			// GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-KID_ALLE+TAX_BASE_ADJ
			PageData SalaryPd = new PageData();
			SalaryPd.put("KEY_CODE", SysConfigKeyCode.StaffFormulaSalary);
			String configFormulaSalary = sysConfigManager.getSysConfigByKey(SalaryPd);
			// configFormulaStaffTDSItem
			// CUST_COL1 + CUST_COL2 + CUST_COL3 + CUST_COL4 + CUST_COL5 +
			// CUST_COL6 + REMIT_CUST_COL1
			PageData SalaryStaffTDSItemPd = new PageData();
			SalaryStaffTDSItemPd.put("KEY_CODE", SysConfigKeyCode.StaffTdsRemitItem);
			String configFormulaStaffTDSItem = sysConfigManager.getSysConfigByKey(SalaryStaffTDSItemPd);

			// configFormulaBonus_Not0
			// CUST_COL14
			PageData BonusPd_Not0 = new PageData();
			BonusPd_Not0.put("KEY_CODE", SysConfigKeyCode.StaffFormulaBonus);
			String configFormulaBonus = sysConfigManager.getSysConfigByKey(BonusPd_Not0);

			// select * , ACCRD_TAX ACCRD_TAX__, CUST_COL15 CUST_COL15__,
			// SERIAL_NO SERIAL_NO__, BILL_CODE BILL_CODE__, BUSI_DATE
			// BUSI_DATE__, DEPT_CODE DEPT_CODE__, CUST_COL7 CUST_COL7__,
			// USER_GROP USER_GROP__ from TB_STAFF_DETAIL_backup
			// 获取Excel表中导入的数据
			String sqlRetSelect = Common.GetRetSelectNotCalculationColoumns(TableNameBackup, TF_SalaryTax, TF_BonusTax,
					TmplUtil.keyExtra, keyListBase, tmplconfigService);
			// 按配置结构公式，更新数据
			List<String> listSalaryFeildUpdate = Common.GetSalaryFeildUpdate(SelectedTableNo, TableNameBackup,
					SelectedDepartCode, SelectedCustCol7, tmplconfigService);

			String strSumGroupBy = " STAFF_IDENT ";
			String QueryFeild_PreAndMonth_Tax = QueryFeildPart + QueryFeildBusiPreAndNewMonth + strSumInvalidNotInsert;
			String QueryFeild_PreNotMonth_Month = QueryFeildPart + QueryFeildBusiPreNotNewMonth
					+ strSumInvalidNotInsert;
			// select STAFF_IDENT ,
			// sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-KID_ALLE)
			// - 5000 S_TAX_CONFIG_GRADE_OPER,
			// sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-KID_ALLE)
			// - 5000 S_TAX_CONFIG_SUM_OPER, sum(ACCRD_TAX) S_TAX_SELF_SUM_OPER,
			// sum(GROSS_PAY) GROSS_PAY
			// from TB_STAFF_DETAIL_backup
			// where 1 = 1 and DEPT_CODE in ('01002') and USER_GROP in
			// ('50210002') and CUST_COL7 in ('9870')
			// and BUSI_DATE > '201800' and BUSI_DATE <= '201811'
			// and BILL_CODE not in (select BILL_CODE from TB_STAFF_SUMMY_BILL
			// where BILL_STATE = '0')
			// and BILL_CODE not in (select BILL_CODE from tb_gl_cert where
			// REVCERT_CODE not like ' %')
			// group by STAFF_IDENT
			// 现在取的（TB_STAFF_DETAIL_backup在本月之前符合条件的有正常工资月份的个数 + 1）* 5000
			// GetRetSumByUserColoumnsSalaryGrade只减去一个本月的5000，其余的5000在计算时减掉
			String sqlSumByUserCodeSalary11 = Common.GetRetSumByUserColoumnsSalaryGrade(TableNameBackup, strSumGroupBy,
					QueryFeild_PreAndMonth_Tax, ExemptionTaxSalary, QueryFeild_PreNotMonth_Month, configFormulaSalary,
					TableFeildSalaryTaxConfigGradeOper, TableFeildSalaryTaxConfigSumOper, TF_SalaryTax,
					TableFeildSalaryTaxSelfSumOper, TF_SalarySelf, tmplconfigService);
			// select t. STAFF_IDENT , t.S_TAX_CONFIG_GRADE_OPER -
			// IFNULL(b.STAFF_TDS, 0) S_TAX_CONFIG_GRADE_OPER,
			// t.S_TAX_CONFIG_SUM_OPER - IFNULL(b.STAFF_TDS, 0)
			// S_TAX_CONFIG_SUM_OPER, t.S_TAX_SELF_SUM_OPER, t.GROSS_PAY
			// from ( select STAFF_IDENT ,
			// sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-KID_ALLE)
			// - 5000 S_TAX_CONFIG_GRADE_OPER,
			// sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-KID_ALLE)
			// - 5000 S_TAX_CONFIG_SUM_OPER, sum(ACCRD_TAX) S_TAX_SELF_SUM_OPER,
			// sum(GROSS_PAY) GROSS_PAY from TB_STAFF_DETAIL_backup where 1 = 1
			// and DEPT_CODE in ('01002') and USER_GROP in ('50210002') and
			// CUST_COL7 in ('9870') and BUSI_DATE > '201800' and BUSI_DATE <=
			// '201811' and BILL_CODE not in (select BILL_CODE from
			// TB_STAFF_SUMMY_BILL where BILL_STATE = '0') and BILL_CODE not in
			// (select BILL_CODE from tb_gl_cert where REVCERT_CODE not like '
			// %') group by STAFF_IDENT ) t
			// LEFT JOIN (SELECT STAFF_IDENT , SUM(
			// CUST_COL1+CUST_COL2+CUST_COL3+CUST_COL4+CUST_COL5+CUST_COL6+REMIT_CUST_COL1)
			// STAFF_TDS FROM view_staff_tds_remit where 1 = 1 and BUSI_DATE >
			// '201800' and BUSI_DATE <= '201811' group by STAFF_IDENT ) B
			// ON t. STAFF_IDENT = b. STAFF_IDENT
			// -符合条件、区间取本月及以前月份的扣除项按身份证号汇总
			String sqlSumByUserCodeSalary1 = Common.GetRetSumByUserColoumnsStaffTds(sqlSumByUserCodeSalary11,
					"view_staff_tds_remit", strSumGroupBy, QueryFeildBusiPreAndNewMonth, configFormulaStaffTDSItem,
					"STAFF_TDS", TableFeildSalaryTaxConfigGradeOper, TableFeildSalaryTaxConfigSumOper,
					TableFeildSalaryTaxSelfSumOper, TF_SalarySelf);
			// select STAFF_IDENT , sum(CUST_COL14) B_TAX_CONFIG_GRADE_OPER,
			// sum(CUST_COL14) B_TAX_CONFIG_SUM_OPER, sum(CUST_COL15)
			// B_TAX_SELF_SUM_OPER
			// from TB_STAFF_DETAIL_backup
			// where 1 = 1 and DEPT_CODE in ('01002') and USER_GROP in
			// ('50210002') and CUST_COL7 in ('9870') and BUSI_DATE like '2018%'
			// and BILL_CODE not in (select BILL_CODE from TB_STAFF_SUMMY_BILL
			// where BILL_STATE = '0')
			// and BILL_CODE not in (select BILL_CODE from tb_gl_cert where
			// REVCERT_CODE not like ' %')
			// group by STAFF_IDENT
			// 符合条件、区间取全年的奖金
			String sqlSumByUserCodeBonus1 = Common.GetRetSumByUserColoumnsBonus11(TableNameBackup, strSumGroupBy,
					QueryFeildPart + QueryFeildBusiAllYear + strSumInvalidNotInsert, configFormulaBonus,
					TableFeildBonusTaxConfigGradeOper, TableFeildBonusTaxConfigSumOper, TF_BonusTax,
					TableFeildBonusTaxSelfSumOper, tmplconfigService);

			PageData pdInsetBackup = new PageData();
			pdInsetBackup.put("QueryFeild", QueryFeildPart + QueryFeildBusiAllYear);
			// strInsertFeild
			// SERIAL_NO,BILL_CODE,BUSI_DATE,USER_CODE,USER_NAME,STAFF_IDENT,BANK_CARD,USER_GROP,USER_CATG,DEPT_CODE,UNITS_CODE,ORG_UNIT,SAL_RANGE,POST_SALY,BONUS,CASH_BONUS,WORK_OT,BACK_SALY,RET_SALY,CHK_CASH,INTR_SGL_AWAD,SENY_ALLE,POST_ALLE,NS_ALLE,AREA_ALLE,EXPT_ALLE,TECH_ALLE,LIVE_EXPE,LIVE_ALLE,LEAVE_DM,HOUSE_ALLE,ITEM_ALLE,MEAL_EXPE,TRF_ALLE,TEL_EXPE,HLDY_ALLE,KID_ALLE,COOL_EXPE,EXT_SGL_AWAD,PRE_TAX_PLUS,GROSS_PAY,ENDW_INS,UNEMPL_INS,MED_INS,CASD_INS,HOUSE_FUND,SUP_PESN,TAX_BASE_ADJ,ACCRD_TAX,AFTER_TAX,ACT_SALY,GUESS_DIFF,CUST_COL1,CUST_COL2,CUST_COL3,CUST_COL4,CUST_COL5,CUST_COL6,CUST_COL7,CUST_COL8,CUST_COL9,CUST_COL10,CUST_COL11,CUST_COL12,CUST_COL13,CUST_COL14,CUST_COL15,CUST_COL16,CUST_COL17,CUST_COL18,CUST_COL19,CUST_COL20,ESTB_DEPT,DATA_TYPE,ITEM_CODE
			String strInsertFeild = QueryFeildString.tranferListValueToSelectString(map_HaveColumnsList);
			pdInsetBackup.put("FeildList", strInsertFeild);
			pdInsetBackup.put("SumInvalidNotInsert", strSumInvalidNotInsert);

			try {
				// TF_SalarySelf, TF_SalaryTax, TF_BonusSelf, TF_BonusTax,
				// TableFeildSalaryTaxConfigGradeOper,
				// TableFeildBonusTaxConfigGradeOper,
				// TableFeildSalaryTaxConfigSumOper,
				// TableFeildBonusTaxConfigSumOper,
				// TableFeildSalaryTaxSelfSumOper,
				// TableFeildBonusTaxSelfSumOper,
				// pdInsetBackup,
				// listSalaryFeildUpdate, sqlRetSelect, listData,
				// sqlSumByUserCodeSalary, sqlSumByUserCodeBonus);

				// GROSS_PAY, ACCRD_TAX, CUST_COL14, CUST_COL15,

				// S_TAX_CONFIG_GRADE_OPER, B_TAX_CONFIG_GRADE_OPER,

				// S_TAX_CONFIG_SUM_OPER, B_TAX_CONFIG_SUM_OPER,

				// S_TAX_SELF_SUM_OPER, B_TAX_SELF_SUM_OPER,

				// SumInvalidNotInsert= and BILL_CODE not in (select BILL_CODE
				// from TB_STAFF_SUMMY_BILL where BILL_STATE = '0')
				// and BILL_CODE not in (select BILL_CODE from tb_gl_cert where
				// REVCERT_CODE not like ' %') ,
				// FeildList=SERIAL_NO,BILL_CODE,BUSI_DATE,USER_CODE,USER_NAME,STAFF_IDENT,BANK_CARD,USER_GROP,USER_CATG,DEPT_CODE,UNITS_CODE,ORG_UNIT,SAL_RANGE,POST_SALY,BONUS,CASH_BONUS,WORK_OT,BACK_SALY,RET_SALY,CHK_CASH,INTR_SGL_AWAD,SENY_ALLE,POST_ALLE,NS_ALLE,AREA_ALLE,EXPT_ALLE,TECH_ALLE,LIVE_EXPE,LIVE_ALLE,LEAVE_DM,HOUSE_ALLE,ITEM_ALLE,MEAL_EXPE,TRF_ALLE,TEL_EXPE,HLDY_ALLE,KID_ALLE,COOL_EXPE,EXT_SGL_AWAD,PRE_TAX_PLUS,GROSS_PAY,ENDW_INS,UNEMPL_INS,MED_INS,CASD_INS,HOUSE_FUND,SUP_PESN,TAX_BASE_ADJ,ACCRD_TAX,AFTER_TAX,ACT_SALY,GUESS_DIFF,CUST_COL1,CUST_COL2,CUST_COL3,CUST_COL4,CUST_COL5,CUST_COL6,CUST_COL7,CUST_COL8,CUST_COL9,CUST_COL10,CUST_COL11,CUST_COL12,CUST_COL13,CUST_COL14,CUST_COL15,CUST_COL16,CUST_COL17,CUST_COL18,CUST_COL19,CUST_COL20,ESTB_DEPT,DATA_TYPE,ITEM_CODE,
				// QueryFeild= and DEPT_CODE in ('01002') and USER_GROP in
				// ('50210002') and CUST_COL7 in ('9870') and BUSI_DATE like '"
				// + strSystemDateTimeYear + "%' ,

				// select * , ACCRD_TAX ACCRD_TAX__, CUST_COL15 CUST_COL15__,
				// SERIAL_NO SERIAL_NO__, BILL_CODE BILL_CODE__, BUSI_DATE
				// BUSI_DATE__, DEPT_CODE DEPT_CODE__, CUST_COL7 CUST_COL7__,
				// USER_GROP USER_GROP__ from TB_STAFF_DETAIL_backup,
				// [{AREA_ALLE=0.00, HOUSE_ALLE=0.00, SERIAL_NO=,
				// USER_GROP=50210002, POST_ALLE=0.00, BILL_CODE=,
				// LEAVE_DM=0.00, LIVE_ALLE=0.00, ITEM_ALLE=0.00,
				// LIVE_EXPE=0.00, MEAL_EXPE=368.00, DEPT_CODE=01002,
				// UNITS_CODE=0100110, POST_SALY=3810.00, SAL_RANGE=S12,
				// ACT_SALY=8061.83,
				// InsertLogVale=''201811'',''00075138'',''蔡丽君'',''622101196701010727'',''4228020100100225480'',''50210002'',''PUT04'',''01002'',''0100110'',''S12'',''3810.00'',''2330.00'',''2330.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''420.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''368.00'',''1300.00'',''200.00'',''2000.00'',''0.00'',''0.00'',''0.00'',''0.00'',''12758.00'',''1498.40'',''93.70'',''374.60'',''0.00'',''2248.00'',''374.60'',''0.00'',''106.87'',''0.00'',''8061.83'',''0.00'',''9870'',''8000.0'',''1.0'',''01002'',
				// TableName=TB_STAFF_DETAIL_backup, HLDY_ALLE=2000.00,
				// InsertVale='201811','00075138','蔡丽君','622101196701010727','4228020100100225480','50210002','PUT04','01002','0100110','S12','3810.00','2330.00','2330.00','0.00','0.00','0.00','0.00','0.00','420.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','368.00','1300.00','200.00','2000.00','0.00','0.00','0.00','0.00','12758.00','1498.40','93.70','374.60','0.00','2248.00','374.60','0.00','106.87','0.00','8061.83','0.00','9870','8000.0','1.0','01002',
				// BONUS=2330.00, BUSI_DATE=201811, RET_SALY=0.00, NS_ALLE=0.00,
				// UNEMPL_INS=93.70, CUST_COL7=9870, INTR_SGL_AWAD=0.00,
				// CHK_CASH=0.00, USER_CATG=PUT04, CASH_BONUS=2330.00,
				// CASD_INS=0.00, EXPT_ALLE=0.00, TEL_EXPE=200.00,
				// TAX_BASE_ADJ=0.00, PRE_TAX_PLUS=0.00, MED_INS=374.60,
				// TRF_ALLE=1300.00, SUP_PESN=374.60, BACK_SALY=0.00,
				// EXT_SGL_AWAD=0.00, ENDW_INS=1498.40, KID_ALLE=0.00,
				// COOL_EXPE=0.00, CanOperate= and BILL_CODE not in (select
				// BILL_CODE from TB_STAFF_SUMMY_BILL where BILL_STATE = '0') ,
				// AFTER_TAX=0.00, WORK_OT=0.00, GUESS_DIFF=0.00,
				// CUST_COL14=8000.0, CUST_COL15=1.0, GROSS_PAY=12758.00,
				// USER_NAME=蔡丽君, HOUSE_FUND=2248.00, TECH_ALLE=0.00,
				// USER_CODE=00075138, ESTB_DEPT=01002,
				// STAFF_IDENT=622101196701010727, ACCRD_TAX=106.87,
				// SENY_ALLE=420.00, BANK_CARD=4228020100100225480,
				// InsertField=BUSI_DATE,USER_CODE,USER_NAME,STAFF_IDENT,BANK_CARD,USER_GROP,USER_CATG,DEPT_CODE,UNITS_CODE,SAL_RANGE,POST_SALY,BONUS,CASH_BONUS,WORK_OT,BACK_SALY,RET_SALY,CHK_CASH,INTR_SGL_AWAD,SENY_ALLE,POST_ALLE,NS_ALLE,AREA_ALLE,EXPT_ALLE,TECH_ALLE,LIVE_EXPE,LIVE_ALLE,LEAVE_DM,HOUSE_ALLE,ITEM_ALLE,MEAL_EXPE,TRF_ALLE,TEL_EXPE,HLDY_ALLE,KID_ALLE,COOL_EXPE,EXT_SGL_AWAD,PRE_TAX_PLUS,GROSS_PAY,ENDW_INS,UNEMPL_INS,MED_INS,CASD_INS,HOUSE_FUND,SUP_PESN,TAX_BASE_ADJ,ACCRD_TAX,AFTER_TAX,ACT_SALY,GUESS_DIFF,CUST_COL7,CUST_COL14,CUST_COL15,ESTB_DEPT}],

				//

				// select STAFF_IDENT, sum(CUST_COL14) B_TAX_CONFIG_GRADE_OPER,
				// sum(CUST_COL14) B_TAX_CONFIG_SUM_OPER, sum(CUST_COL15)
				// B_TAX_SELF_SUM_OPER from TB_STAFF_DETAIL_backup where 1 = 1
				// and DEPT_CODE in ('01002') and USER_GROP in ('50210002') and
				// CUST_COL7 in ('9870') and BUSI_DATE like '2018%' and
				// BILL_CODE not in (select BILL_CODE from TB_STAFF_SUMMY_BILL
				// where BILL_STATE = '0') and BILL_CODE not in (select
				// BILL_CODE from tb_gl_cert where REVCERT_CODE not like ' %')
				// group by STAFF_IDENT

				//根据配置判断是否需要验证个税校验
				Boolean bolCalculation=false;
				PageData pdTaxCheck = new PageData();
				pdTaxCheck.put("KEY_CODE", SysConfigKeyCode.TaxCheck);
				String taxCheck = sysConfigManager.getSysConfigByKey(pdTaxCheck);
				if (taxCheck.equals("1")) {
					// 是否计算税
				    bolCalculation = Corresponding.CheckCalculation(SelectedTableNo);
				}

				// tb_staff_filter_info（不用验证税）有对应的类型、账套、责任中心、工资范围编码或ANY,STAFF_IDENT_STATE
				// = 1验证，！=1不验证

				String strSystemDateTimeMouth = CheckSystemDateTime.getSystemDateTimeMouth(SystemDateTimess);
				List<PageData> dataCalculation = staffdetailService.getDataCalculation(TableNameBackup,
						TmplUtil.keyExtra, TF_SalarySelf, TF_SalaryTax, TF_BonusSelf, TF_BonusTax,
						TableFeildSalaryTaxConfigGradeOper, TableFeildBonusTaxConfigGradeOper,
						TableFeildSalaryTaxConfigSumOper, TableFeildBonusTaxConfigSumOper,
						TableFeildSalaryTaxSelfSumOper, TableFeildBonusTaxSelfSumOper, pdInsetBackup,
						listSalaryFeildUpdate, sqlRetSelect, listData, sqlSumByUserCodeSalary1, sqlSumByUserCodeBonus1,
						bolCalculation, listStaffFilterInfo, QueryFeild_PreNotMonth_Month, ExemptionTaxSalary,
						strSystemDateTimeMouth);
				retCommonBaseAndList.setList(dataCalculation);
			} catch (Exception e) {
				commonBase.setCode(2);
				commonBase.setMessage(Message.ImportExcelError);
			}
		}
		retCommonBaseAndList.setCommonBase(commonBase);
		return retCommonBaseAndList;
	}
	/*
	 * private CommonBaseAndList getCalculationData(Boolean IsImport, Boolean
	 * IsAdd, CommonBase commonBase, String SelectedTableNo, String
	 * SelectedCustCol7, String SelectedDepartCode, String emplGroupType,
	 * List<PageData> listData, String strHelpful, String SystemDateTime) throws
	 * Exception{ CommonBaseAndList retCommonBaseAndList = new
	 * CommonBaseAndList(); if(listData!=null && listData.size()>0){ Map<String,
	 * TmplConfigDetail> map_SetColumnsList =
	 * Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode,
	 * SelectedCustCol7, tmplconfigService); Map<String, TableColumns>
	 * map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo,
	 * tmplconfigService); //税字段，不能定义公式 if(!(TF_SalaryTax!=null &&
	 * map_SetColumnsList.containsKey(TF_SalaryTax.toUpperCase()) &&
	 * TF_BonusTax!=null &&
	 * map_SetColumnsList.containsKey(TF_BonusTax.toUpperCase()))){
	 * commonBase.setCode(2);
	 * commonBase.setMessage("配置表里定义列（个人所得税、全年一次性奖金税）必须在结构配置表中，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; }
	 * if(TF_SalaryTax.toUpperCase().equals(TF_BonusTax.toUpperCase()) ||
	 * TF_SalarySelf.toUpperCase().equals(TF_BonusTax.toUpperCase()) ||
	 * TF_SalarySelf.toUpperCase().equals(TF_SalaryTax.toUpperCase()) ||
	 * TF_SalarySelf.toUpperCase().equals(TF_BonusSelf.toUpperCase()) ||
	 * TF_BonusSelf.toUpperCase().equals(TF_BonusTax.toUpperCase()) ||
	 * TF_BonusSelf.toUpperCase().equals(TF_SalaryTax.toUpperCase())){
	 * commonBase.setCode(2); commonBase.setMessage(
	 * "配置表里定义列（应发合计、个人所得税、全年一次性奖金税、全年一次性奖金）不能定义为相同列，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; } TmplConfigDetail colTableFeildSalaryTax =
	 * map_SetColumnsList.get(TF_SalaryTax.toUpperCase());
	 * if(colTableFeildSalaryTax.getCOL_FORMULA()!=null &&
	 * !colTableFeildSalaryTax.getCOL_FORMULA().trim().equals("")){
	 * commonBase.setCode(2); commonBase.setMessage("个人所得税字段不能定义公式，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; } TmplConfigDetail colTableFeildBonusTax =
	 * map_SetColumnsList.get(TF_BonusTax.toUpperCase());
	 * if(colTableFeildBonusTax.getCOL_FORMULA()!=null &&
	 * !colTableFeildBonusTax.getCOL_FORMULA().trim().equals("")){
	 * commonBase.setCode(2);
	 * commonBase.setMessage("全年一次性奖金税字段不能定义公式，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; }
	 * 
	 * for(PageData item : listData){ item.put("CanOperate", strHelpful);
	 * item.put("TableName", TableNameBackup); Common.setModelDefault(item,
	 * map_HaveColumnsList, map_SetColumnsList, MustNotEditList); } PageData
	 * getQueryFeildPd = new PageData(); getQueryFeildPd.put("USER_GROP",
	 * emplGroupType); getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
	 * getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
	 * getQueryFeildPd.put("BUSI_DATE", SystemDateTime); String QueryFeild =
	 * QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList2);
	 * if(!(SystemDateTime != null && !SystemDateTime.trim().equals(""))){
	 * QueryFeild += " and 1 != 1 "; } if(!(SelectedDepartCode != null &&
	 * !SelectedDepartCode.trim().equals(""))){ QueryFeild += " and 1 != 1 "; }
	 * if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
	 * QueryFeild += " and 1 != 1 "; } if(!(emplGroupType!=null &&
	 * !emplGroupType.trim().equals(""))){ QueryFeild += " and 1 != 1 "; }
	 * //QueryFeild //and DEPT_CODE in ('01002') and USER_GROP in ('50210002')
	 * and CUST_COL7 in ('9870') //strSumInvalidNotInsert //and BILL_CODE not in
	 * (select BILL_CODE from TB_STAFF_SUMMY_BILL where BILL_STATE = '0') //and
	 * BILL_CODE not in (select BILL_CODE from tb_gl_cert where REVCERT_CODE not
	 * like ' %') String strSumInvalidNotInsert =
	 * QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
	 * strSumInvalidNotInsert +=
	 * " and BILL_CODE not in (select BILL_CODE from tb_gl_cert where REVCERT_CODE not like ' %') "
	 * ;
	 * 
	 * //ExemptionTaxSalary //5000 PageData SalaryExemptionTaxPd = new
	 * PageData(); SalaryExemptionTaxPd.put("KEY_CODE",
	 * SysConfigKeyCode.ExemptionTax); String ExemptionTaxSalary =
	 * sysConfigManager.getSysConfigByKey(SalaryExemptionTaxPd); String
	 * ExemptionTaxBonus_Not0 = "0"; String ExemptionTaxBonus_Same0 =
	 * ExemptionTaxSalary; //configFormulaSalary
	 * //GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-
	 * KID_ALLE+TAX_BASE_ADJ PageData SalaryPd = new PageData();
	 * SalaryPd.put("KEY_CODE", SysConfigKeyCode.StaffFormulaSalary); String
	 * configFormulaSalary = sysConfigManager.getSysConfigByKey(SalaryPd);
	 * //configFormulaBonus_Not0 //CUST_COL14 PageData BonusPd_Not0 = new
	 * PageData(); BonusPd_Not0.put("KEY_CODE",
	 * SysConfigKeyCode.StaffFormulaBonus_Not0); String configFormulaBonus_Not0
	 * = sysConfigManager.getSysConfigByKey(BonusPd_Not0);
	 * //configFormulaBonus_Same0
	 * //全年奖的应纳税所得额=全年一次性奖金额-当月工资薪金与费用扣除额的差额=8000-[5000-(3000-400)]=5600元
	 * //取当月全年一次性奖金（1）-（当月应发合计-（养老保险(个人)+失业保险(个人)+医疗保险(个人)+大病统筹(个人)+住房公积金(个人)+
	 * 企业年金个人扣缴） PageData BonusPd_Same0 = new PageData();
	 * BonusPd_Same0.put("KEY_CODE", SysConfigKeyCode.StaffFormulaBonus_Same0);
	 * String configFormulaBonus_Same0 =
	 * sysConfigManager.getSysConfigByKey(BonusPd_Same0);
	 * 
	 * //select * , ACCRD_TAX ACCRD_TAX__, CUST_COL15 CUST_COL15__, SERIAL_NO
	 * SERIAL_NO__, BILL_CODE BILL_CODE__, BUSI_DATE BUSI_DATE__, DEPT_CODE
	 * DEPT_CODE__, CUST_COL7 CUST_COL7__, USER_GROP USER_GROP__ from
	 * TB_STAFF_DETAIL_backup String sqlRetSelect =
	 * Common.GetRetSelectNotCalculationColoumns(TableNameBackup, TF_SalaryTax,
	 * TF_BonusTax, TmplUtil.keyExtra, keyListBase, tmplconfigService);
	 * List<String> listSalaryFeildUpdate =
	 * Common.GetSalaryFeildUpdate(SelectedTableNo, TableNameBackup,
	 * SelectedDepartCode, SelectedCustCol7, tmplconfigService);
	 * 
	 * //select USER_CODE,
	 * sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-
	 * KID_ALLE+TAX_BASE_ADJ) - 5000 S_TAX_CONFIG_GRADE_OPER,
	 * sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-
	 * KID_ALLE+TAX_BASE_ADJ) - 5000 S_TAX_CONFIG_SUM_OPER, sum(ACCRD_TAX)
	 * S_TAX_SELF_SUM_OPER from TB_STAFF_DETAIL_backup where 1 = 1 and DEPT_CODE
	 * in ('01002') and USER_GROP in ('50210002') and CUST_COL7 in ('9870') and
	 * BILL_CODE not in (select BILL_CODE from TB_STAFF_SUMMY_BILL where
	 * BILL_STATE = '0') and BILL_CODE not in (select BILL_CODE from tb_gl_cert
	 * where REVCERT_CODE not like ' %') group by USER_CODE String
	 * sqlSumByUserCodeSalary =
	 * Common.GetRetSumByUserColoumnsSalary2(TableNameBackup, QueryFeild +
	 * strSumInvalidNotInsert, ExemptionTaxSalary, configFormulaSalary,
	 * TableFeildSalaryTaxConfigGradeOper, TableFeildSalaryTaxConfigSumOper,
	 * TF_SalaryTax, TableFeildSalaryTaxSelfSumOper, TF_SalarySelf,
	 * tmplconfigService); //select USER_CODE, sum(CUST_COL14) - 0
	 * B_TAX_CONFIG_GRADE_OPER, sum(CUST_COL14) B_TAX_CONFIG_SUM_OPER,
	 * sum(CUST_COL15) B_TAX_SELF_SUM_OPER from TB_STAFF_DETAIL_backup where 1 =
	 * 1 and DEPT_CODE in ('01002') and USER_GROP in ('50210002') and CUST_COL7
	 * in ('9870') and BILL_CODE not in (select BILL_CODE from
	 * TB_STAFF_SUMMY_BILL where BILL_STATE = '0') and BILL_CODE not in (select
	 * BILL_CODE from tb_gl_cert where REVCERT_CODE not like ' %') group by
	 * USER_CODE String sqlSumByUserCodeBonus_Not0 =
	 * Common.GetRetSumByUserColoumnsBonus2(TableNameBackup, QueryFeild +
	 * strSumInvalidNotInsert, ExemptionTaxBonus_Not0, configFormulaBonus_Not0,
	 * TableFeildBonusTaxConfigGradeOper, TableFeildBonusTaxConfigSumOper,
	 * TF_BonusTax, TableFeildBonusTaxSelfSumOper, tmplconfigService); // String
	 * sqlSumByUserCodeBonus_Same0 =
	 * Common.GetRetSumByUserColoumnsBonus2(TableNameBackup, QueryFeild +
	 * strSumInvalidNotInsert, ExemptionTaxBonus_Same0,
	 * configFormulaBonus_Same0, TableFeildBonusTaxConfigGradeOper,
	 * TableFeildBonusTaxConfigSumOper, TF_BonusTax,
	 * TableFeildBonusTaxSelfSumOper, tmplconfigService);
	 * 
	 * PageData pdInsetBackup = new PageData(); pdInsetBackup.put("QueryFeild",
	 * QueryFeild); //strInsertFeild
	 * //SERIAL_NO,BILL_CODE,BUSI_DATE,USER_CODE,USER_NAME,STAFF_IDENT,BANK_CARD
	 * ,USER_GROP,USER_CATG,DEPT_CODE,UNITS_CODE,ORG_UNIT,SAL_RANGE,POST_SALY,
	 * BONUS,CASH_BONUS,WORK_OT,BACK_SALY,RET_SALY,CHK_CASH,INTR_SGL_AWAD,
	 * SENY_ALLE,POST_ALLE,NS_ALLE,AREA_ALLE,EXPT_ALLE,TECH_ALLE,LIVE_EXPE,
	 * LIVE_ALLE,LEAVE_DM,HOUSE_ALLE,ITEM_ALLE,MEAL_EXPE,TRF_ALLE,TEL_EXPE,
	 * HLDY_ALLE,KID_ALLE,COOL_EXPE,EXT_SGL_AWAD,PRE_TAX_PLUS,GROSS_PAY,ENDW_INS
	 * ,UNEMPL_INS,MED_INS,CASD_INS,HOUSE_FUND,SUP_PESN,TAX_BASE_ADJ,ACCRD_TAX,
	 * AFTER_TAX,ACT_SALY,GUESS_DIFF,CUST_COL1,CUST_COL2,CUST_COL3,CUST_COL4,
	 * CUST_COL5,CUST_COL6,CUST_COL7,CUST_COL8,CUST_COL9,CUST_COL10,CUST_COL11,
	 * CUST_COL12,CUST_COL13,CUST_COL14,CUST_COL15,CUST_COL16,CUST_COL17,
	 * CUST_COL18,CUST_COL19,CUST_COL20,ESTB_DEPT,DATA_TYPE,ITEM_CODE String
	 * strInsertFeild =
	 * QueryFeildString.tranferListValueToSelectString(map_HaveColumnsList);
	 * pdInsetBackup.put("FeildList", strInsertFeild);
	 * pdInsetBackup.put("SumInvalidNotInsert", strSumInvalidNotInsert);
	 * 
	 * try{ //TF_SalarySelf, TF_SalaryTax, TF_BonusSelf, TF_BonusTax,
	 * //TableFeildSalaryTaxConfigGradeOper, TableFeildBonusTaxConfigGradeOper,
	 * //TableFeildSalaryTaxConfigSumOper, TableFeildBonusTaxConfigSumOper,
	 * //TableFeildSalaryTaxSelfSumOper, TableFeildBonusTaxSelfSumOper,
	 * //pdInsetBackup, //listSalaryFeildUpdate, sqlRetSelect, listData,
	 * //sqlSumByUserCodeSalary, sqlSumByUserCodeBonus);
	 * 
	 * //GROSS_PAY, ACCRD_TAX, CUST_COL14, CUST_COL15,
	 * 
	 * //S_TAX_CONFIG_GRADE_OPER, B_TAX_CONFIG_GRADE_OPER,
	 * 
	 * //S_TAX_CONFIG_SUM_OPER, B_TAX_CONFIG_SUM_OPER,
	 * 
	 * //S_TAX_SELF_SUM_OPER, B_TAX_SELF_SUM_OPER,
	 * 
	 * //{SumInvalidNotInsert= and BILL_CODE not in (select BILL_CODE from
	 * TB_STAFF_SUMMY_BILL where BILL_STATE = '0') and BILL_CODE not in (select
	 * BILL_CODE from tb_gl_cert where REVCERT_CODE not like ' %') ,
	 * FeildList=SERIAL_NO,BILL_CODE,BUSI_DATE,USER_CODE,USER_NAME,STAFF_IDENT,
	 * BANK_CARD,USER_GROP,USER_CATG,DEPT_CODE,UNITS_CODE,ORG_UNIT,SAL_RANGE,
	 * POST_SALY,BONUS,CASH_BONUS,WORK_OT,BACK_SALY,RET_SALY,CHK_CASH,
	 * INTR_SGL_AWAD,SENY_ALLE,POST_ALLE,NS_ALLE,AREA_ALLE,EXPT_ALLE,TECH_ALLE,
	 * LIVE_EXPE,LIVE_ALLE,LEAVE_DM,HOUSE_ALLE,ITEM_ALLE,MEAL_EXPE,TRF_ALLE,
	 * TEL_EXPE,HLDY_ALLE,KID_ALLE,COOL_EXPE,EXT_SGL_AWAD,PRE_TAX_PLUS,GROSS_PAY
	 * ,ENDW_INS,UNEMPL_INS,MED_INS,CASD_INS,HOUSE_FUND,SUP_PESN,TAX_BASE_ADJ,
	 * ACCRD_TAX,AFTER_TAX,ACT_SALY,GUESS_DIFF,CUST_COL1,CUST_COL2,CUST_COL3,
	 * CUST_COL4,CUST_COL5,CUST_COL6,CUST_COL7,CUST_COL8,CUST_COL9,CUST_COL10,
	 * CUST_COL11,CUST_COL12,CUST_COL13,CUST_COL14,CUST_COL15,CUST_COL16,
	 * CUST_COL17,CUST_COL18,CUST_COL19,CUST_COL20,ESTB_DEPT,DATA_TYPE,
	 * ITEM_CODE, QueryFeild= and DEPT_CODE in ('01002') and USER_GROP in
	 * ('50210002') and CUST_COL7 in ('9870') },
	 * 
	 * //select * , ACCRD_TAX ACCRD_TAX__, CUST_COL15 CUST_COL15__, SERIAL_NO
	 * SERIAL_NO__, BILL_CODE BILL_CODE__, BUSI_DATE BUSI_DATE__, DEPT_CODE
	 * DEPT_CODE__, CUST_COL7 CUST_COL7__, USER_GROP USER_GROP__ from
	 * TB_STAFF_DETAIL_backup, //[{AREA_ALLE=0.00, HOUSE_ALLE=0.00, SERIAL_NO=,
	 * USER_GROP=50210002, POST_ALLE=0.00, BILL_CODE=, LEAVE_DM=0.00,
	 * LIVE_ALLE=0.00, ITEM_ALLE=0.00, LIVE_EXPE=0.00, MEAL_EXPE=368.00,
	 * DEPT_CODE=01002, UNITS_CODE=0100110, POST_SALY=3810.00, SAL_RANGE=S12,
	 * ACT_SALY=8061.83,
	 * InsertLogVale=''201811'',''00075138'',''蔡丽君'',''622101196701010727'',''
	 * 4228020100100225480'',''50210002'',''PUT04'',''01002'',''0100110'',''S12'
	 * ',''3810.00'',''2330.00'',''2330.00'',''0.00'',''0.00'',''0.00'',''0.00''
	 * ,''0.00'',''420.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'',''0.00'
	 * ',''0.00'',''0.00'',''0.00'',''0.00'',''368.00'',''1300.00'',''200.00'','
	 * '2000.00'',''0.00'',''0.00'',''0.00'',''0.00'',''12758.00'',''1498.40'','
	 * '93.70'',''374.60'',''0.00'',''2248.00'',''374.60'',''0.00'',''106.87'','
	 * '0.00'',''8061.83'',''0.00'',''9870'',''8000.0'',''1.0'',''01002'',
	 * TableName=TB_STAFF_DETAIL_backup, HLDY_ALLE=2000.00,
	 * InsertVale='201811','00075138','蔡丽君','622101196701010727','
	 * 4228020100100225480','50210002','PUT04','01002','0100110','S12','3810.00'
	 * ,'2330.00','2330.00','0.00','0.00','0.00','0.00','0.00','420.00','0.00','
	 * 0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','0.00','368.00','
	 * 1300.00','200.00','2000.00','0.00','0.00','0.00','0.00','12758.00','1498.
	 * 40','93.70','374.60','0.00','2248.00','374.60','0.00','106.87','0.00','
	 * 8061.83','0.00','9870','8000.0','1.0','01002', BONUS=2330.00,
	 * BUSI_DATE=201811, RET_SALY=0.00, NS_ALLE=0.00, UNEMPL_INS=93.70,
	 * CUST_COL7=9870, INTR_SGL_AWAD=0.00, CHK_CASH=0.00, USER_CATG=PUT04,
	 * CASH_BONUS=2330.00, CASD_INS=0.00, EXPT_ALLE=0.00, TEL_EXPE=200.00,
	 * TAX_BASE_ADJ=0.00, PRE_TAX_PLUS=0.00, MED_INS=374.60, TRF_ALLE=1300.00,
	 * SUP_PESN=374.60, BACK_SALY=0.00, EXT_SGL_AWAD=0.00, ENDW_INS=1498.40,
	 * KID_ALLE=0.00, COOL_EXPE=0.00, CanOperate= and BILL_CODE not in (select
	 * BILL_CODE from TB_STAFF_SUMMY_BILL where BILL_STATE = '0') ,
	 * AFTER_TAX=0.00, WORK_OT=0.00, GUESS_DIFF=0.00, CUST_COL14=8000.0,
	 * CUST_COL15=1.0, GROSS_PAY=12758.00, USER_NAME=蔡丽君, HOUSE_FUND=2248.00,
	 * TECH_ALLE=0.00, USER_CODE=00075138, ESTB_DEPT=01002,
	 * STAFF_IDENT=622101196701010727, ACCRD_TAX=106.87, SENY_ALLE=420.00,
	 * BANK_CARD=4228020100100225480,
	 * InsertField=BUSI_DATE,USER_CODE,USER_NAME,STAFF_IDENT,BANK_CARD,USER_GROP
	 * ,USER_CATG,DEPT_CODE,UNITS_CODE,SAL_RANGE,POST_SALY,BONUS,CASH_BONUS,
	 * WORK_OT,BACK_SALY,RET_SALY,CHK_CASH,INTR_SGL_AWAD,SENY_ALLE,POST_ALLE,
	 * NS_ALLE,AREA_ALLE,EXPT_ALLE,TECH_ALLE,LIVE_EXPE,LIVE_ALLE,LEAVE_DM,
	 * HOUSE_ALLE,ITEM_ALLE,MEAL_EXPE,TRF_ALLE,TEL_EXPE,HLDY_ALLE,KID_ALLE,
	 * COOL_EXPE,EXT_SGL_AWAD,PRE_TAX_PLUS,GROSS_PAY,ENDW_INS,UNEMPL_INS,MED_INS
	 * ,CASD_INS,HOUSE_FUND,SUP_PESN,TAX_BASE_ADJ,ACCRD_TAX,AFTER_TAX,ACT_SALY,
	 * GUESS_DIFF,CUST_COL7,CUST_COL14,CUST_COL15,ESTB_DEPT}],
	 * 
	 * //select USER_CODE,
	 * sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-
	 * KID_ALLE+TAX_BASE_ADJ) - 5000 S_TAX_CONFIG_GRADE_OPER,
	 * sum(GROSS_PAY-ENDW_INS-MED_INS-CASD_INS-UNEMPL_INS-HOUSE_FUND-SUP_PESN-
	 * KID_ALLE+TAX_BASE_ADJ) - 5000 S_TAX_CONFIG_SUM_OPER, sum(ACCRD_TAX)
	 * S_TAX_SELF_SUM_OPER from TB_STAFF_DETAIL_backup where 1 = 1 and DEPT_CODE
	 * in ('01002') and USER_GROP in ('50210002') and CUST_COL7 in ('9870') and
	 * BILL_CODE not in (select BILL_CODE from TB_STAFF_SUMMY_BILL where
	 * BILL_STATE = '0') and BILL_CODE not in (select BILL_CODE from tb_gl_cert
	 * where REVCERT_CODE not like ' %') group by USER_CODE,
	 * 
	 * //select USER_CODE, sum(CUST_COL14) - 0 B_TAX_CONFIG_GRADE_OPER,
	 * sum(CUST_COL14) B_TAX_CONFIG_SUM_OPER, sum(CUST_COL15)
	 * B_TAX_SELF_SUM_OPER from TB_STAFF_DETAIL_backup where 1 = 1 and DEPT_CODE
	 * in ('01002') and USER_GROP in ('50210002') and CUST_COL7 in ('9870') and
	 * BILL_CODE not in (select BILL_CODE from TB_STAFF_SUMMY_BILL where
	 * BILL_STATE = '0') and BILL_CODE not in (select BILL_CODE from tb_gl_cert
	 * where REVCERT_CODE not like ' %') group by USER_CODE List<PageData>
	 * dataCalculation = staffdetailService.getDataCalculation(TableNameBackup,
	 * TmplUtil.keyExtra, TF_SalarySelf, TF_SalaryTax, TF_BonusSelf,
	 * TF_BonusTax, TableFeildSalaryTaxConfigGradeOper,
	 * TableFeildBonusTaxConfigGradeOper, TableFeildSalaryTaxConfigSumOper,
	 * TableFeildBonusTaxConfigSumOper, TableFeildSalaryTaxSelfSumOper,
	 * TableFeildBonusTaxSelfSumOper, pdInsetBackup, listSalaryFeildUpdate,
	 * sqlRetSelect, listData, sqlSumByUserCodeSalary,
	 * sqlSumByUserCodeBonus_Not0, sqlSumByUserCodeBonus_Same0);
	 * retCommonBaseAndList.setList(dataCalculation); } catch(Exception e){
	 * commonBase.setCode(2); commonBase.setMessage(Message.ImportExcelError); }
	 * } retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; }
	 */

	/*
	 * private CommonBaseAndList getCalculationData(Boolean IsImport, Boolean
	 * IsAdd, CommonBase commonBase, String SelectedTableNo, String
	 * SelectedCustCol7, String SelectedDepartCode, String emplGroupType,
	 * List<PageData> listData, String strHelpful, String SystemDateTime) throws
	 * Exception{ CommonBaseAndList retCommonBaseAndList = new
	 * CommonBaseAndList(); if(listData!=null && listData.size()>0){ Map<String,
	 * TmplConfigDetail> map_SetColumnsList =
	 * Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode,
	 * SelectedCustCol7, tmplconfigService); Map<String, TableColumns>
	 * map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo,
	 * tmplconfigService); //税字段，不能定义公式 if(!(TF_SalaryTax!=null &&
	 * map_SetColumnsList.containsKey(TF_SalaryTax.toUpperCase()) &&
	 * TF_BonusTax!=null &&
	 * map_SetColumnsList.containsKey(TF_BonusTax.toUpperCase()))){
	 * commonBase.setCode(2);
	 * commonBase.setMessage("配置表里定义列（个人所得税、全年一次性奖金税）必须在结构配置表中，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; }
	 * if(TF_SalaryTax.toUpperCase().equals(TF_BonusTax.toUpperCase()) ||
	 * TF_SalarySelf.toUpperCase().equals(TF_BonusTax.toUpperCase()) ||
	 * TF_SalarySelf.toUpperCase().equals(TF_SalaryTax.toUpperCase()) ||
	 * TF_SalarySelf.toUpperCase().equals(TF_BonusSelf.toUpperCase()) ||
	 * TF_BonusSelf.toUpperCase().equals(TF_BonusTax.toUpperCase()) ||
	 * TF_BonusSelf.toUpperCase().equals(TF_SalaryTax.toUpperCase())){
	 * commonBase.setCode(2); commonBase.setMessage(
	 * "配置表里定义列（应发合计、个人所得税、全年一次性奖金税、全年一次性奖金）不能定义为相同列，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; } TmplConfigDetail colTableFeildSalaryTax =
	 * map_SetColumnsList.get(TF_SalaryTax.toUpperCase());
	 * if(colTableFeildSalaryTax.getCOL_FORMULA()!=null &&
	 * !colTableFeildSalaryTax.getCOL_FORMULA().trim().equals("")){
	 * commonBase.setCode(2); commonBase.setMessage("个人所得税字段不能定义公式，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; } TmplConfigDetail colTableFeildBonusTax =
	 * map_SetColumnsList.get(TF_BonusTax.toUpperCase());
	 * if(colTableFeildBonusTax.getCOL_FORMULA()!=null &&
	 * !colTableFeildBonusTax.getCOL_FORMULA().trim().equals("")){
	 * commonBase.setCode(2);
	 * commonBase.setMessage("全年一次性奖金税字段不能定义公式，请联系管理员修改！");
	 * retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; }
	 * 
	 * for(PageData item : listData){ item.put("CanOperate", strHelpful);
	 * item.put("TableName", TableNameBackup); Common.setModelDefault(item,
	 * map_HaveColumnsList, map_SetColumnsList, MustNotEditList); } PageData
	 * getQueryFeildPd = new PageData(); getQueryFeildPd.put("USER_GROP",
	 * emplGroupType); getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
	 * getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
	 * getQueryFeildPd.put("BUSI_DATE", SystemDateTime); String QueryFeildPart =
	 * QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
	 * if(!(SystemDateTime != null && !SystemDateTime.trim().equals(""))){
	 * QueryFeild += " and 1 != 1 "; } if(!(SelectedDepartCode != null &&
	 * !SelectedDepartCode.trim().equals(""))){ QueryFeildPart +=
	 * " and 1 != 1 "; } if(!(SelectedCustCol7 != null &&
	 * !SelectedCustCol7.trim().equals(""))){ QueryFeildPart += " and 1 != 1 ";
	 * } if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
	 * QueryFeildPart += " and 1 != 1 "; } String QueryFeildBusiDate =
	 * " and BUSI_DATE = '" + SystemDateTime + "' "; String QueryFeildBusiYear =
	 * " and BUSI_DATE like '" +
	 * CheckSystemDateTime.getSystemDateTimeYear(SystemDateTime) + "%' "; String
	 * strSumInvalidNotInsert =
	 * QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
	 * strSumInvalidNotInsert +=
	 * " and BILL_CODE not in (select BILL_CODE from tb_gl_cert where REVCERT_CODE not like ' %') "
	 * ;
	 * 
	 * PageData SalaryExemptionTaxPd = new PageData();
	 * SalaryExemptionTaxPd.put("KEY_CODE", SysConfigKeyCode.ExemptionTax);
	 * String salaryExemptionTax =
	 * sysConfigManager.getSysConfigByKey(SalaryExemptionTaxPd); PageData
	 * SalaryPd = new PageData(); SalaryPd.put("KEY_CODE",
	 * SysConfigKeyCode.StaffFormulaSalary); String configFormulaSalary =
	 * sysConfigManager.getSysConfigByKey(SalaryPd); PageData BonusPd = new
	 * PageData(); BonusPd.put("KEY_CODE", SysConfigKeyCode.StaffFormulaBonus);
	 * String configFormulaBonus = sysConfigManager.getSysConfigByKey(BonusPd);
	 * 
	 * String sqlRetSelect =
	 * Common.GetRetSelectNotCalculationColoumns(TableNameBackup, TF_SalaryTax,
	 * TF_BonusTax, TmplUtil.keyExtra, keyListBase, tmplconfigService);
	 * List<String> listSalaryFeildUpdate =
	 * Common.GetSalaryFeildUpdate(SelectedTableNo, TableNameBackup,
	 * SelectedDepartCode, SelectedCustCol7, tmplconfigService);
	 * 
	 * String sqlSumByUserCodeSalary =
	 * Common.GetRetSumByUserColoumnsSalary(TableNameBackup, QueryFeildPart +
	 * QueryFeildBusiDate + strSumInvalidNotInsert, salaryExemptionTax,
	 * configFormulaSalary, TableFeildSalaryTaxConfigGradeOper,
	 * TableFeildSalaryTaxConfigSumOper, TF_SalaryTax,
	 * TableFeildSalaryTaxSelfSumOper, tmplconfigService); String
	 * sqlSumByUserCodeBonus =
	 * Common.GetRetSumByUserColoumnsBonus(TableNameBackup, QueryFeildPart +
	 * QueryFeildBusiYear + strSumInvalidNotInsert, salaryExemptionTax,
	 * configFormulaBonus, TableFeildBonusTaxConfigGradeOper,
	 * TableFeildBonusTaxConfigSumOper, TF_BonusTax,
	 * TableFeildBonusTaxSelfSumOper, tmplconfigService);
	 * 
	 * PageData pdInsetBackup = new PageData(); pdInsetBackup.put("QueryFeild",
	 * QueryFeildPart + QueryFeildBusiYear); String strInsertFeild =
	 * QueryFeildString.tranferListValueToSelectString(map_HaveColumnsList);
	 * pdInsetBackup.put("FeildList", strInsertFeild);
	 * pdInsetBackup.put("SumInvalidNotInsert", strSumInvalidNotInsert);
	 * 
	 * try{ List<PageData> dataCalculation =
	 * staffdetailService.getDataCalculation(TableNameBackup, TmplUtil.keyExtra,
	 * TF_SalarySelf, TF_SalaryTax, TF_BonusSelf, TF_BonusTax,
	 * TableFeildSalaryTaxConfigGradeOper, TableFeildBonusTaxConfigGradeOper,
	 * TableFeildSalaryTaxConfigSumOper, TableFeildBonusTaxConfigSumOper,
	 * TableFeildSalaryTaxSelfSumOper, TableFeildBonusTaxSelfSumOper,
	 * pdInsetBackup, listSalaryFeildUpdate, sqlRetSelect, listData,
	 * sqlSumByUserCodeSalary, sqlSumByUserCodeBonus);
	 * retCommonBaseAndList.setList(dataCalculation); } catch(Exception e){
	 * commonBase.setCode(2); commonBase.setMessage(Message.ImportExcelError); }
	 * } retCommonBaseAndList.setCommonBase(commonBase); return
	 * retCommonBaseAndList; }
	 */

	private CommonBase UpdateDatabase(Boolean IsAdd, CommonBase commonBase, String strErrorMessage,
			String SelectedTableNo, String SelectedCustCol7, String SelectedDepartCode, String emplGroupType,
			CommonBaseAndList getCommonBaseAndList, String strHelpful) throws Exception {
		if (getCommonBaseAndList != null && getCommonBaseAndList.getList() != null
				&& getCommonBaseAndList.getList().size() > 0) {
			Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo,
					SelectedDepartCode, SelectedCustCol7, tmplconfigService);
			Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo,
					tmplconfigService);

			for (PageData each : getCommonBaseAndList.getList()) {
				if (IsAdd) {
					each.put("SERIAL_NO", "");
				}
				Common.setModelDefault(each, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
				each.put("CanOperate", strHelpful);
				each.put("TableName", TableNameDetail);
			}

			// 此处执行集合添加
			staffdetailService.batchUpdateDatabase(getCommonBaseAndList.getList());
			commonBase.setCode(0);
			commonBase.setMessage(strErrorMessage);
		} else {
			commonBase = getCommonBaseAndList.getCommonBase();
		}
		return commonBase;
	}

	private CommonBase CalculationUpdateDatabase(Boolean IsAdd, CommonBase commonBase, String strErrorMessage,
			String SelectedTableNo, String SelectedCustCol7, String SelectedDepartCode, String emplGroupType,
			List<PageData> listData, String strHelpful, String SystemDateTime,
			List<StaffFilterInfo> listStaffFilterInfo) throws Exception {
		CommonBaseAndList getCommonBaseAndList = getCalculationData(commonBase, SelectedTableNo, SelectedCustCol7,
				SelectedDepartCode, emplGroupType, listData, strHelpful, SystemDateTime, listStaffFilterInfo);

		//根据配置判断是否需要验证个税校验
		PageData pdTaxCheck = new PageData();
		pdTaxCheck.put("KEY_CODE", SysConfigKeyCode.TaxCheck);
		String taxCheck = sysConfigManager.getSysConfigByKey(pdTaxCheck);
		if (taxCheck.equals("1")) {
			if (Corresponding.CheckCalculation(SelectedTableNo)) {
				for (PageData pdSet : getCommonBaseAndList.getList()) {
					String pdSetSTAFF_IDENT = pdSet.getString("STAFF_IDENT");
					BigDecimal douSalaryCalTax = new BigDecimal(0);// 计算出的税额
					BigDecimal douSalaryImpTax = new BigDecimal(0);// 导入税额
					for (PageData pdsum : getCommonBaseAndList.getList()) {
						String pdsumSTAFF_IDENT = pdsum.getString("STAFF_IDENT");
						if (pdSetSTAFF_IDENT != null && pdSetSTAFF_IDENT.equals(pdsumSTAFF_IDENT)) {
							douSalaryCalTax = douSalaryCalTax.add(new BigDecimal(pdsum.get(TF_SalaryTax).toString()));
							douSalaryImpTax = douSalaryImpTax
									.add(new BigDecimal(pdsum.get(TF_SalaryTax + TmplUtil.keyExtra).toString()));
						}
					}
					pdSet.put(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra, douSalaryCalTax);
					pdSet.put(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra, douSalaryImpTax);
				}
				List<String> listSTAFF_IDENT = new ArrayList<String>();
				List<String> listRetSTAFF_IDENT = new ArrayList<String>();
				for (PageData pdSet : getCommonBaseAndList.getList()) {
					String pdSetSTAFF_IDENT = pdSet.getString("STAFF_IDENT");
					if (!listSTAFF_IDENT.contains(pdSetSTAFF_IDENT)) {
						BigDecimal douSalaryCalTax = new BigDecimal(
								pdSet.get(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
						BigDecimal douSalaryImpTax = new BigDecimal(pdSet
								.get(TF_SalaryTax + TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
	
						if (!(douSalaryCalTax.compareTo(douSalaryImpTax) == 0)) {
							if (douSalaryCalTax.compareTo(new BigDecimal(0)) < 0
									&& douSalaryImpTax.compareTo(new BigDecimal(0)) == 0) {
								listRetSTAFF_IDENT.add(pdSetSTAFF_IDENT);
							}
						}
					}
					listSTAFF_IDENT.add(pdSetSTAFF_IDENT);
				}
				if (listRetSTAFF_IDENT != null && listRetSTAFF_IDENT.size() > 0) {
					for (PageData pdRetSet : getCommonBaseAndList.getList()) {
						String pdRetSetSTAFF_IDENT = pdRetSet.getString("STAFF_IDENT");
						if (listRetSTAFF_IDENT.contains(pdRetSetSTAFF_IDENT)) {
							pdRetSet.put(TF_SalaryTax, 0);
						}
					}
				}
			}
		}
		return UpdateDatabase(IsAdd, commonBase, strErrorMessage, SelectedTableNo, SelectedCustCol7, SelectedDepartCode,
				emplGroupType, getCommonBaseAndList, strHelpful);
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public ModelAndView downExcel(JqPage page) throws Exception {
		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode,
				SelectedCustCol7, tmplconfigService);
		Map<String, Object> DicList = Common.GetDicList(SelectedTableNo, SelectedDepartCode, SelectedCustCol7,
				tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService,
				AdditionalReportColumns);

		PageData transferPd = this.getPageData();
		// 页面显示数据的二级单位
		transferPd.put("SelectedDepartCode", SelectedDepartCode);
		// 账套
		transferPd.put("SelectedCustCol7", SelectedCustCol7);
		// 员工组
		transferPd.put("emplGroupType", emplGroupType);

		// 页面显示数据的二级单位
		List<PageData> varOList = staffdetailService.exportModel(transferPd);
		return export(varOList, "StaffDetail", map_SetColumnsList, DicList); // 工资明细
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出StaffDetail到excel");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		PageData getPd = this.getPageData();
		// 员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		// 单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if (departSelf == 1) {
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		// 账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		// 单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		// 当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode,
				SelectedCustCol7, tmplconfigService);
		Map<String, Object> DicList = Common.GetDicList(SelectedTableNo, SelectedDepartCode, SelectedCustCol7,
				tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService,
				AdditionalReportColumns);

		// 页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		// 页面显示数据的二级单位
		getPd.put("SelectedDepartCode", SelectedDepartCode);
		// 账套
		getPd.put("SelectedCustCol7", SelectedCustCol7);
		// 员工组
		getPd.put("emplGroupType", emplGroupType);

		String strBillCode = QueryFeildString.getQueryFeildBillCodeDetail(SelectedBillCode, SelectBillCodeFirstShow);
		getPd.put("CheckBillCode", strBillCode);

		page.setPd(getPd);
		List<PageData> varOList = staffdetailService.exportList(page);
		return export(varOList, "", map_SetColumnsList, DicList);
	}

	@SuppressWarnings("unchecked")
	private ModelAndView export(List<PageData> varOList, String ExcelName,
			Map<String, TmplConfigDetail> map_SetColumnsList, Map<String, Object> DicList) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if (map_SetColumnsList != null && map_SetColumnsList.size() > 0) {
			for (TmplConfigDetail col : map_SetColumnsList.values()) {
				if (col.getCOL_HIDE().equals("1")) {
					titles.add(col.getCOL_NAME());
				}
			}
			if (varOList != null && varOList.size() > 0) {
				for (int i = 0; i < varOList.size(); i++) {
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						if (col.getCOL_HIDE().equals("1")) {
							String trans = col.getDICT_TRANS();
							Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
							if (trans != null && !trans.trim().equals("")) {
								String value = "";
								Map<String, String> dicAdd = (Map<String, String>) DicList.getOrDefault(trans,
										new LinkedHashMap<String, String>());
								value = dicAdd.getOrDefault(getCellValue, "");
								vpd.put("var" + j, value);
							} else {
								vpd.put("var" + j, getCellValue.toString());
							}
							j++;
						}
					}
					varList.add(vpd);
				}
			}
		}
		dataMap.put("titles", titles);
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	private String CheckMustSelectedAndSame(String emplGroupType, String CUST_COL7, String ShowDataCustCol7,
			String DEPT_CODE, String ShowDataDepartCode, String DepartTreeSource, String BILL_CODE,
			String ShowDataBillCode)//
			throws Exception {
		String strRut = "";
		if (!(CUST_COL7 != null && !CUST_COL7.trim().equals(""))) {
			strRut += "查询条件中的账套必须选择！";
		} else {
			if (!CUST_COL7.equals(ShowDataCustCol7)) {
				strRut += "查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作！";
			}
		}
		if (!(DEPT_CODE != null && !DEPT_CODE.trim().equals(""))) {
			strRut += "查询条件中的责任中心不能为空！";
		} else {
			if (!String.valueOf(0).equals(DepartTreeSource) && !DEPT_CODE.equals(ShowDataDepartCode)) {
				strRut += "查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作！";
			}
		}
		if (!(BILL_CODE != null && !BILL_CODE.trim().equals(""))) {
			strRut += "查询条件中的单号必须选择！";
		} else {
			if (!BILL_CODE.equals(ShowDataBillCode)) {
				strRut += "查询条件中所选单号与页面显示数据单号不一致，请单击查询再进行操作！";
			}
			// if(!BILL_CODE.equals(SelectBillCodeFirstShow)){
			// strRut += "已汇总记录不能再进行操作！";
			// }
		}
		if (!(emplGroupType != null && !emplGroupType.trim().equals(""))) {
			strRut += Message.StaffSelectedTabOppositeGroupTypeIsNull;
		}
		return strRut;
	}

	private String CheckState(String SelectedBillCode, String SystemDateTime, String SelectedCustCol7,
			String SelectedDepartCode, String emplGroupType, String TypeCodeTransfer, List<PageData> pdList,
			String strFeild, String strFeildExtra) throws Exception {
		String strRut = "";
		if (!SelectedBillCode.equals(SelectBillCodeFirstShow)) {
			String QueryFeild = " and BILL_CODE in ('" + SelectedBillCode + "') ";
			QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
			QueryFeild += " and BILL_CODE not in (SELECT bill_code FROM tb_sys_sealed_info WHERE state = '1') ";
			QueryFeild += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();

			PageData transferPd = new PageData();
			transferPd.put("SystemDateTime", SystemDateTime);
			transferPd.put("CanOperate", QueryFeild);
			List<String> getCodeList = staffsummyService.getBillCodeList(transferPd);

			if (!(getCodeList != null && getCodeList.size() > 0)) {
				strRut = Message.OperDataSumAlreadyChange;
			}
		} else {
			if (pdList != null && pdList.size() > 0) {
				List<Integer> listStringSerialNo = QueryFeildString.getListIntegerFromListPageData(pdList, strFeild,
						strFeildExtra);
				String strSqlInSerialNo = QueryFeildString.tranferListIntegerToGroupbyString(listStringSerialNo);
				String strSERIAL_NO_IN = (strSqlInSerialNo != null && !strSqlInSerialNo.trim().equals(""))
						? strSqlInSerialNo : "''";
				PageData transferPd = new PageData();
				PageData getQueryFeildPd = new PageData();
				getQueryFeildPd.put("USER_GROP", emplGroupType);
				getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
				getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
				getQueryFeildPd.put("BUSI_DATE", SystemDateTime);
				String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList2);
				if (!(SystemDateTime != null && !SystemDateTime.trim().equals(""))) {
					QueryFeild += " and 1 != 1 ";
				}
				if (!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))) {
					QueryFeild += " and 1 != 1 ";
				}
				if (!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))) {
					QueryFeild += " and 1 != 1 ";
				}
				if (!(emplGroupType != null && !emplGroupType.trim().equals(""))) {
					QueryFeild += " and 1 != 1 ";
				}
				QueryFeild += " and BILL_CODE like ' %' ";
				QueryFeild += " and SERIAL_NO in (" + strSERIAL_NO_IN + ") ";
				transferPd.put("QueryFeild", QueryFeild);

				// 页面显示数据的年月
				transferPd.put("SystemDateTime", SystemDateTime);
				transferPd.put("SelectFeildName", strFeild);
				List<PageData> getSerialNo = staffdetailService.getSerialNoBySerialNo(transferPd);
				if (!(listStringSerialNo != null && getSerialNo != null
						&& listStringSerialNo.size() == getSerialNo.size())) {
					strRut = Message.OperDataAlreadyChange;
				} else {
					for (PageData each : getSerialNo) {
						if (!listStringSerialNo.contains((Integer) each.get(strFeild))) {
							strRut = Message.OperDataAlreadyChange;
						}
					}
				}
			}
		}
		return strRut;
	}

	// 不用验证导入税的情况、验证身份证号
	private List<StaffFilterInfo> getStaffFilter(String SelectedTableNo, String SelectedCustCol7,
			String SelectedDepartCode) throws Exception {
		StaffFilterInfo getStaffFilter = new StaffFilterInfo();
		getStaffFilter.setTYPE_CODE(Corresponding.getStaffFilterTypeFromTmplType(SelectedTableNo));
		getStaffFilter.setDEPT_CODE(SelectedDepartCode);
		getStaffFilter.setBILL_OFF(SelectedCustCol7);
		return staffFilterService.getStaffFilter(getStaffFilter);
	}

	/**
	 * 导入提示
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/showErrorTaxMessage")
	public ModelAndView showErrorTaxMessage() throws Exception {
		PageData getPd = this.getPageData();
		String ErrorTaxMessage = getPd.getString("ErrorTaxMessage");

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/ErrorTax");
		mv.addObject("commonMessage", ErrorTaxMessage);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
