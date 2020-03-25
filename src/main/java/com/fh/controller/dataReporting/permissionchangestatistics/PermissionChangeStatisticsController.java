package com.fh.controller.dataReporting.permissionchangestatistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.service.dataReporting.grcperson.GRCPersonManager;
import com.fh.service.dataReporting.permissionchangestatistics.PermissionChangeStatisticsManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.DateWeek;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.SysDeptTime;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

/**
 * 说明：权限变更统计处理模块 创建人：xinyuLo 创建时间：2019-10-11
 */
@Controller
@RequestMapping(value = "/permissionchangestatistics")
public class PermissionChangeStatisticsController extends BaseController {

	String menuUrl = "permissionchangestatistics/list.do"; // 菜单地址(权限用)
	@Resource(name = "permissionchangestatisticsService")
	private PermissionChangeStatisticsManager permissionchangestatisticsService;
	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;
	@Resource(name = "grcpersonService")
	private GRCPersonManager grcpersonService;

	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

	/**
	 * 保存与修改合并类
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAndEdit")
	@ResponseBody
	public CommonBase saveAndEdit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "保存与修改PermissionChangeStatistics");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		// 初始化变量
		String listData = null;
		String staffId = null;
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		commonBase.setCode(-1);
		pd = this.getPageData();
		listData = pd.getString("listData");
		JSONArray array = JSONArray.fromObject(listData);
		@SuppressWarnings("unchecked")
		List<String> listTransferData = (List<String>) JSONArray.toCollection(array, PageData.class);// 过时方法
		for (int i = 0; i < listTransferData.size(); i++) {
			staffId = listTransferData.get(i).trim();
			PageData pageData = new PageData();
			pageData.put("USER_DEPART", user.getUNIT_CODE());
			pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
			pageData.put("STATE", "1");
			pageData.put("BILL_USER", user.getUSER_ID());
			pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
			pageData.put("ID", listTransferData.get(i++));
			pageData.put("COMPANY_NAME", listTransferData.get(i++).trim());
			pageData.put("ACCOUNT_DELAY", listTransferData.get(i++).trim());
			pageData.put("ACCOUNT_UNLOCK", listTransferData.get(i++).trim());
			pageData.put("NEW_ROLES", listTransferData.get(i++).trim());
			pageData.put("DELETE_ROLES", listTransferData.get(i++).trim());
			pageData.put("NEW_ACCOUNTS", listTransferData.get(i++).trim());
			pageData.put("DELETE_ACCOUNTS", listTransferData.get(i++).trim());
			pageData.put("NEW_FMIS_ROLES", listTransferData.get(i++).trim());
			pageData.put("DELETE_FMIS_ROLES", listTransferData.get(i++).trim());
			pageData.put("CHANGE_USER_GROUP", listTransferData.get(i++).trim());
			pageData.put("CHANGE_PERSON_COUNT", listTransferData.get(i).trim());
			if (null != staffId && !"".equals(staffId)) {// 如果有ID则进行修改
				permissionchangestatisticsService.edit(pageData);
			} else {// 如果无ID则进行新增
				permissionchangestatisticsService.save(pageData);
			}
		}
		commonBase.setCode(0);
		return commonBase;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表PermissionChangeStatistics");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData data = new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd = this.getPageData();
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE", "permissionchaMustKey");
		pd.put("USER_DEPART", user.getUNIT_CODE());
		data.put("BUSI_TYPE", SysDeptTime.PERMISSION_CHANGE_STATISTICS.getNameKey());
		data.put("DEPT_CODE", user.getUNIT_CODE());
		String date = sysconfigService.currentSection(pd);

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMM");
		String theSameMonth = ft.format(dNow);
		// 需要获取必填的内容，然后输出到页面上
		String mandatory = sysconfigService.getSysConfigByKey(pd);
		if (null == busiDate || StringUtil.isEmpty(busiDate)) {
			pd.put("busiDate", date);
		}
		pd.put("month", date);
		pd.put("mandatory", mandatory);
		page.setPd(pd);
		PageData pageData = grcpersonService.findSysDeptTime(data);
		if (null != pageData && pageData.size() > 0) {
			pd.putAll(pageData);
		}
		List<PageData> listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData> varList = permissionchangestatisticsService.list(page); // 列出PermissionChangeStatistics列表
		mv.setViewName("dataReporting/permissionchangestatistics/permissionchangestatistics_list");
		mv.addObject("varList", varList);
		mv.addObject("isTheSameMonth", theSameMonth.equals(pd.get("busiDate").toString()) ? 1 : 0);
		mv.addObject("listBusiDate", listBusiDate);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		Map_SetColumnsList.clear();
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入日期", "1", false));
		Map_SetColumnsList.put("COMPANY_NAME", new TmplConfigDetail("COMPANY_NAME", "公司名称", "1", false));
		Map_SetColumnsList.put("ACCOUNT_DELAY", new TmplConfigDetail("ACCOUNT_DELAY", "账号延期", "1", false));
		Map_SetColumnsList.put("ACCOUNT_UNLOCK", new TmplConfigDetail("ACCOUNT_UNLOCK", "账号解除锁定", "1", false));
		Map_SetColumnsList.put("NEW_ROLES", new TmplConfigDetail("NEW_ROLES", "新增角色", "1", false));
		Map_SetColumnsList.put("DELETE_ROLES", new TmplConfigDetail("DELETE_ROLES", "删除角色", "1", false));
		Map_SetColumnsList.put("NEW_ACCOUNTS", new TmplConfigDetail("NEW_ACCOUNTS", "账号新增", "1", false));
		Map_SetColumnsList.put("DELETE_ACCOUNTS", new TmplConfigDetail("DELETE_ACCOUNTS", "账号删除", "1", false));
		Map_SetColumnsList.put("NEW_FMIS_ROLES", new TmplConfigDetail("NEW_FMIS_ROLES", "增加FMIS角色", "1", false));
		Map_SetColumnsList.put("DELETE_FMIS_ROLES", new TmplConfigDetail("DELETE_FMIS_ROLES", "删除FMIS角色", "1", false));
		Map_SetColumnsList.put("CHANGE_USER_GROUP", new TmplConfigDetail("CHANGE_USER_GROUP", "变更用户组", "1", false));
		Map_SetColumnsList.put("CHANGE_PERSON_COUNT", new TmplConfigDetail("CHANGE_PERSON_COUNT", "变更人次", "1", false));

		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public CommonBase deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除PermissionChangeStatistics");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			permissionchangestatisticsService.deleteAll(ArrayDATA_IDS);
			commonBase.setCode(0);
		} else {
			commonBase.setCode(-1);
		}
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
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "permissionchangestatistics");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public ModelAndView downExcel(Page page) throws Exception {

		PageData getPd = this.getPageData();
		List<PageData> varOList = permissionchangestatisticsService.exportModel(getPd);
		return export(varOList, "PermissionChangeStatistics", Map_SetColumnsList);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出PermissionChangeStatistics到excel");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}

		PageData getPd = this.getPageData();
		// 页面显示数据的年月
		// getPd.put("SystemDateTime", SystemDateTime);
		page.setPd(getPd);
		List<PageData> varOList = permissionchangestatisticsService.exportList(page);
		return export(varOList, "", Map_SetColumnsList);
	}

	private ModelAndView export(List<PageData> varOList, String ExcelName,
			Map<String, TmplConfigDetail> map_SetColumnsList) throws Exception {
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
							Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
							if (null == getCellValue) {
								getCellValue = "";
							}
							vpd.put("var" + j, getCellValue.toString());
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

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		// String strErrorMessage = "";
		// PageData getPd = this.getPageData();
		Map<String, Object> DicList = new HashMap<String, Object>();

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
			if (Map_SetColumnsList != null && Map_SetColumnsList.size() > 0) {
				for (TmplConfigDetail col : Map_SetColumnsList.values()) {
					titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
				}
			}

			// 调用解析工具包
			testExcel = new LeadingInExcelToPageData<PageData>(formart);
			// 解析excel，获取客户信息集合

			uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex, titleAndAttribute,
					Map_HaveColumnsList, Map_SetColumnsList, DicList, false, false, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取Excel文件错误", e);
			throw new CustomException("读取Excel文件错误:" + e.getMessage(), false);
		}
		boolean judgement = false;

		List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
		if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
			judgement = true;
		}
		if (judgement) {
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			for (PageData pageData : listUploadAndRead) {
				// 将每条数据插入新内容
				pageData.put("USER_DEPART", StringUtil.toString(user.getUNIT_CODE(), ""));
				pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
				pageData.put("STATE", "1");
				pageData.put("BILL_USER", user.getUSER_ID());
				pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
			}
			permissionchangestatisticsService.grcUpdateDatabase(listUploadAndRead);
			commonBase.setCode(0);
		} else {
			commonBase.setCode(-1);
			commonBase.setMessage("TranslateUtil");
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "permissionchangestatistics");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/**
	 * 显示问题统计列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/listPermissonChangeStatistic")
	public ModelAndView listPermissonChangeStatistic(Page page) throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		if (null != pd.getString("YEAR_MONTH") && !"".equals(pd.getString("YEAR_MONTH"))) {
			pd.put("YEAR_MONTH", pd.getString("YEAR_MONTH").replace("-", ""));
		}
		if (null != pd.getString("WEEKSTEXT") && !"".equals(pd.getString("WEEKSTEXT"))) {
			String startDate = pd.getString("WEEKSTEXT").substring(4, 14);
			String endDate = pd.getString("WEEKSTEXT").substring(15, 25);
			pd.put("START_DATE", startDate.replace("-", ""));
			pd.put("END_DATE", endDate.replace("-", ""));
			// 当选择"周"时，按周查询，不按期间查询
			pd.put("YEAR_MONTH", "");
		}
		List<PageData> varList = permissionchangestatisticsService.listByUnit(page);
		mv.setViewName("statisticAnalysis/permissionchangestatistics/permissionChangeStatistics_list");
		mv.addObject("varList", varList);
		return mv;
	}

	/**
	 * 显示问题统计列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPermissonData")
	public @ResponseBody List<PageData> queryPermissonData(Page page) throws Exception {
		PageData pd = this.getPageData();
		page.setPd(pd);
		if (null != pd.getString("YEAR_MONTH") && !"".equals(pd.getString("YEAR_MONTH"))) {
			pd.put("YEAR_MONTH", pd.getString("YEAR_MONTH").replace("-", ""));
		}
		if (null != pd.getString("WEEKSTEXT") && !"".equals(pd.getString("WEEKSTEXT"))) {
			// 从字符串中获取出日期
			String[] strArr1 = pd.getString("WEEKSTEXT").split("\\(");
			String[] strArr2 = strArr1[1].split("/");
			String[] strArr3 = strArr2[1].split("\\)");

			String startDate = strArr2[0];
			String endDate = strArr3[0];
			pd.put("START_DATE", startDate.replace("-", ""));
			pd.put("END_DATE", endDate.replace("-", ""));
			// 当选择"周"时，按周查询，不按期间查询
			pd.put("YEAR_MONTH", "");
		}
		List<PageData> varList = permissionchangestatisticsService.listByUnit(page);

		return varList;
	}

	/**
	 * 按月获取周及日期范围
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWeeks")
	public @ResponseBody List<PageData> getWeeks() throws Exception {
		PageData pd = this.getPageData();
		List<PageData> lists = new ArrayList<PageData>();
		if (null != pd.getString("YEAR_MONTH") && !"".equals(pd.getString("YEAR_MONTH"))) {
			String year = pd.getString("YEAR_MONTH").split("-")[0];
			String month = pd.getString("YEAR_MONTH").split("-")[1];
			int i = 1;
			// 当前月第一天
			String weekStart = pd.getString("YEAR_MONTH") + "-01";
			// 当前月第一天是星期几
			int dayOfWeek = DateWeek.getTheFirstDayWeekOfMonth(Integer.parseInt(year), Integer.parseInt(month), 1);
			// 该月的最后一天
			String monEnd = DateWeek.getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
			// 该月第一周结束日期
			String weekEnd = dayOfWeek == 0 ? weekStart : DateWeek.addDays(weekStart, (7 - dayOfWeek));
			String str = "第" + i + "周(" + weekStart + "/" + weekEnd + ")";
			PageData p = new PageData();
			p.put("ID", i);
			p.put("Name", str);
			lists.add(p);
			// 当日期小于或等于该月的最后一天
			while (DateWeek.addDays(weekEnd, 1).compareTo(monEnd) <= 0) {
				i++;
				// 该周的开始时间
				weekStart = DateWeek.addDays(weekEnd, 1);
				// 该周结束时间
				weekEnd = (DateWeek.addDays(weekEnd, 1).compareTo(monEnd) > 0) ? monEnd : DateWeek.addDays(weekEnd, 7);
				str = "第" + i + "周(" + weekStart + "/" + weekEnd + ")";
				PageData p1 = new PageData();
				p1.put("ID", i);
				p1.put("Name", str);
				lists.add(p1);
			}
		}
		return lists;
	}

	/**
	 * 导出权限变更统计表到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/qxStatisticExcel")
	public ModelAndView listStatisticExcel(Page page) throws Exception {
		PageData pd = this.getPageData();
		page.setPd(pd);
		if (null != pd.getString("YEAR_MONTH") && !"".equals(pd.getString("YEAR_MONTH"))) {
			pd.put("YEAR_MONTH", pd.getString("YEAR_MONTH").replace("-", ""));
		}
		if (null != pd.getString("WEEKSTEXT") && !"".equals(pd.getString("WEEKSTEXT"))) {
			String startDate = pd.getString("WEEKSTEXT").substring(4, 14);
			String endDate = pd.getString("WEEKSTEXT").substring(15, 25);
			pd.put("START_DATE", startDate.replace("-", ""));
			pd.put("END_DATE", endDate.replace("-", ""));
			// 当选择"周"时，按周查询，不按期间查询
			pd.put("YEAR_MONTH", "");
		}
		List<PageData> varOList = permissionchangestatisticsService.listByUnit(page);
		
		//这里重新定义，是因为不想要BUSI_DATE字段
		Map_SetColumnsList.clear();
		Map_SetColumnsList.put("COMPANY_NAME", new TmplConfigDetail("COMPANY_NAME", "公司名称", "1", false));
		Map_SetColumnsList.put("ACCOUNT_DELAY", new TmplConfigDetail("ACCOUNT_DELAY", "账号延期", "1", false));
		Map_SetColumnsList.put("ACCOUNT_UNLOCK", new TmplConfigDetail("ACCOUNT_UNLOCK", "账号解除锁定", "1", false));
		Map_SetColumnsList.put("NEW_ROLES", new TmplConfigDetail("NEW_ROLES", "新增角色", "1", false));
		Map_SetColumnsList.put("DELETE_ROLES", new TmplConfigDetail("DELETE_ROLES", "删除角色", "1", false));
		Map_SetColumnsList.put("NEW_ACCOUNTS", new TmplConfigDetail("NEW_ACCOUNTS", "账号新增", "1", false));
		Map_SetColumnsList.put("DELETE_ACCOUNTS", new TmplConfigDetail("DELETE_ACCOUNTS", "账号删除", "1", false));
		Map_SetColumnsList.put("NEW_FMIS_ROLES", new TmplConfigDetail("NEW_FMIS_ROLES", "增加FMIS角色", "1", false));
		Map_SetColumnsList.put("DELETE_FMIS_ROLES", new TmplConfigDetail("DELETE_FMIS_ROLES", "删除FMIS角色", "1", false));
		Map_SetColumnsList.put("CHANGE_USER_GROUP", new TmplConfigDetail("CHANGE_USER_GROUP", "变更用户组", "1", false));
		Map_SetColumnsList.put("CHANGE_PERSON_COUNT", new TmplConfigDetail("CHANGE_PERSON_COUNT", "变更人次", "1", false));
		return export(varOList, "", Map_SetColumnsList);
	}
}
