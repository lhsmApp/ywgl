package com.fh.controller.dataReporting.grcperson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
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
 * 说明：GRC人员信息处理类 创建人：xinyuLo 创建时间：2019-09-20
 */
@Controller
@RequestMapping(value = "/grcperson")
public class GRCPersonController extends BaseController {

	String menuUrl = "grcperson/list.do"; // 菜单地址(权限用)
	@Resource(name = "grcpersonService")
	private GRCPersonManager grcpersonService;
	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

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
		logBefore(logger, Jurisdiction.getUsername() + "新增与修改GRCPerson");
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
		
		//获取所有单位和对应的单位id
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		departmentService.listAllDepartmentToSelect("0", zdepartmentPdList);
		Map <String,String> map=new HashMap<>();
		for(PageData a:zdepartmentPdList) {
			map.put(a.get("name").toString(),a.get("id").toString());
		}
		//判断是否为管理员
		if (this.IS_MANAGE()) {
			pd.put("CUST_COLUMN1", Const.CUST_COLUMN1);
		}else {
			pd.put("CUST_COLUMN1", "");
		}
		@SuppressWarnings("unchecked")
		List<String> listTransferData = (List<String>) JSONArray.toCollection(array, PageData.class);// 过时方法
		for (int i = 0; i < listTransferData.size(); i++) {
			staffId = listTransferData.get(i).trim();
			PageData pageData = new PageData();
			pageData.put("CUST_COLUMN1", pd.getString("CUST_COLUMN1"));
			pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
			pageData.put("STATE", "1");
			pageData.put("BILL_USER", user.getUSER_ID());
			pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
			pageData.put("ID", listTransferData.get(i++));
			pageData.put("STAFF_CODE", listTransferData.get(i++).trim());
			pageData.put("STAFF_NAME", listTransferData.get(i++).trim());
			pageData.put("STAFF_UNIT", listTransferData.get(i++).trim());
			if(pd.getString("CUST_COLUMN1").equals(Const.CUST_COLUMN1)) {
				pageData.put("USER_DEPART", map.get(pageData.getString("STAFF_UNIT")));
			}else {
				pageData.put("USER_DEPART", user.getUNIT_CODE());
			}
			
			pageData.put("STAFF_DEPART", listTransferData.get(i++).trim());
			pageData.put("STAFF_POSITION", listTransferData.get(i++).trim());
			pageData.put("STAFF_JOB", listTransferData.get(i++).trim());
			pageData.put("PHONE", listTransferData.get(i++).trim());
			pageData.put("MOBILE_PHONE", listTransferData.get(i++).trim());
			pageData.put("ZSY_MAIL", listTransferData.get(i).trim());
			if (null != staffId && !"".equals(staffId)) {// 如果有ID则进行修改
				grcpersonService.edit(pageData);
			} else {// 如果无ID则进行新增
				grcpersonService.save(pageData);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表GRCPerson");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData data = new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd = this.getPageData();
		String busiDate = pd.getString("busiDate");
		
		if (this.IS_MANAGE()) {
			pd.put("CUST_COLUMN1", Const.CUST_COLUMN1);
		} else {
			pd.put("USER_DEPART", user.getUNIT_CODE());
			pd.put("CUST_COLUMN1", "");
		}
		pd.put("KEY_CODE", "grcpersonMustKey");
		data.put("BUSI_TYPE", SysDeptTime.GRC_PERSON.getNameKey());
		data.put("DEPT_CODE", user.getUNIT_CODE());
//		String date = sysconfigService.currentSection(pd);
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMM");
		String date = ft.format(dNow);
		// 需要获取必填的内容，然后输出到页面上
		String mandatory = sysconfigService.getSysConfigByKey(pd);
		if (null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		pd.put("month", date);
		pd.put("mandatory", mandatory);
		page.setPd(pd);
		PageData pageData = grcpersonService.findSysDeptTime(data);
		if (null != pageData && pageData.size() > 0) {
			pd.putAll(pageData);
		}
		List<PageData> listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData> varList = grcpersonService.list(page); // 列出GRCPerson列表
		mv.setViewName("dataReporting/grcperson/grcperson_list");
		mv.addObject("varList", varList);
		mv.addObject("listBusiDate", listBusiDate);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		// ***********************************************************
		Map_SetColumnsList.clear();
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入日期", "1", false));
		Map_SetColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetColumnsList.put("STAFF_UNIT", new TmplConfigDetail("STAFF_UNIT", "单位", "1", false));
		Map_SetColumnsList.put("STAFF_DEPART", new TmplConfigDetail("STAFF_DEPART", "部门", "1", false));
		Map_SetColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "办公室电话", "1", false));
		Map_SetColumnsList.put("MOBILE_PHONE", new TmplConfigDetail("MOBILE_PHONE", "手机号", "1", false));
		Map_SetColumnsList.put("ZSY_MAIL", new TmplConfigDetail("ZSY_MAIL", "中国石油邮箱", "1", false));

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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除GRCPerson");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			grcpersonService.deleteAll(ArrayDATA_IDS);
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
		// PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "grcperson");
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
		List<PageData> varOList = grcpersonService.exportModel(getPd);
		return export(varOList, "GRCPerson", Map_SetColumnsList);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出GRCPerson到excel");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}

		PageData pd = new PageData();
		pd = this.getPageData();
		// 页面显示数据的年月
		// getPd.put("SystemDateTime", SystemDateTime);
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		if (this.IS_MANAGE()) {
			pd.put("CUST_COLUMN1", Const.CUST_COLUMN1);
		} else {
			pd.put("USER_DEPART", user.getUNIT_CODE());
			pd.put("CUST_COLUMN1", "");
		}
		pd.put("BUSI_DATE", pd.get("BUSI_DATE")); // 月份
		page.setPd(pd);
		List<PageData> varOList = grcpersonService.exportList(page);
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

		//获取所有单位和对应的单位id
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		departmentService.listAllDepartmentToSelect("0", zdepartmentPdList);
		Map <String,String> map=new HashMap<>();
		for(PageData a:zdepartmentPdList) {
			map.put(a.get("name").toString(),a.get("id").toString());
		}
		
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
			
			boolean isManage = false;
			//判断是否为管理员
			if (this.IS_MANAGE()) {
				isManage = true;
			}
			for (PageData pageData : listUploadAndRead) {
				// 将每条数据插入新内容
				pageData.put("USER_DEPART", StringUtil.toString(user.getUNIT_CODE(), ""));
				pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
				pageData.put("STATE", "1");
				pageData.put("BILL_USER", user.getUSER_ID());
				pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
				
				if (isManage) {
					pageData.put("CUST_COLUMN1", Const.CUST_COLUMN1);
					pageData.put("USER_DEPART", map.get(StringUtil.toString(pageData.getString("STAFF_UNIT"), "")));
				}else {
					pageData.put("USER_DEPART", StringUtil.toString(user.getUNIT_CODE(), ""));
					pageData.put("CUST_COLUMN1", "");
				}
			}
			grcpersonService.grcUpdateDatabase(listUploadAndRead);
			commonBase.setCode(0);
		} else {
			commonBase.setCode(-1);
			commonBase.setMessage("TranslateUtil");
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "grcperson");
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
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryList")
	public ModelAndView queryList(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表GRCPerson");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CUST_COLUMN1", Const.CUST_COLUMN1);
		//pd.put("STAFF_UNIT", pd.getString("UNIT_CODE"));
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		// 判断是否为信息管理部管理角色
		if (this.IS_MANAGE()) {
			pd.put("CUST_COLUMN1", "");
			// 若是则树形结构加载所有单位，并将用户单位查询条件更新为所选单位
			JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
			mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
			if(pd.get("UNIT_CODE") != null && !pd.get("UNIT_CODE").equals("")) {
				pd.put("USER_DEPART", pd.getString("UNIT_CODE"));
			}
		} else {
			pd.put("CUST_COLUMN1", Const.CUST_COLUMN1);
			pd.put("USER_DEPART", user.getUNIT_CODE());
			mv.addObject("zTreeNodes", pd.getString("USER_DEPART"));// 若不是则只显示本单位数据
		}

		page.setPd(pd);
		List<PageData> varList = grcpersonService.list(page); // 列出GRCPerson列表
		mv.setViewName("statisticAnalysis/grcdatastatistics/grcDataStatistic_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 导出GRC人员信息统计表到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/listStatisticExcel")
	public ModelAndView listStatisticExcel(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CUST_COLUMN1", Const.CUST_COLUMN1);
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		// 判断是否为信息管理部管理角色
		if (this.IS_MANAGE()) {
			pd.put("CUST_COLUMN1", "");
			// 若是则树形结构加载所有单位，并将用户单位查询条件更新为所选单位
			if(pd.get("UNIT_CODE") != null && !pd.get("UNIT_CODE").equals("")) {
				pd.put("USER_DEPART", pd.getString("UNIT_CODE"));
			}
		} else {
			pd.put("CUST_COLUMN1", Const.CUST_COLUMN1);
			pd.put("USER_DEPART", user.getUNIT_CODE());
		}
		page.setPd(pd);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("员工编号"); // 1
		titles.add("员工姓名"); // 2
		titles.add("单位"); // 3
		titles.add("部门"); // 4
		titles.add("职务"); // 5
		titles.add("岗位"); // 6
		titles.add("办公室电话"); // 7
		titles.add("手机号"); // 8
		titles.add("中国石油邮箱"); // 9
		dataMap.put("titles", titles);
		List<PageData> varOList = grcpersonService.exportList(page); // 列出GRCPerson列表
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STAFF_CODE")); // 1
			vpd.put("var2", varOList.get(i).get("STAFF_NAME").toString()); // 2
			vpd.put("var3", varOList.get(i).get("STAFF_UNIT").toString()); // 3
			vpd.put("var4", varOList.get(i).get("STAFF_DEPART").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("STAFF_POSITION")); // 5
			vpd.put("var6", varOList.get(i).get("STAFF_JOB").toString()); // 6
			vpd.put("var7", varOList.get(i).get("PHONE").toString()); // 7
			vpd.put("var8", varOList.get(i).get("MOBILE_PHONE").toString()); // 8
			vpd.put("var9", varOList.get(i).get("ZSY_MAIL").toString()); // 9
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	private boolean IS_MANAGE() throws Exception {
		PageData pd = new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String roleId = user.getRole().getROLE_ID();
		pd.put("KEY_CODE", "xxglbRoles");
		String roleStr = sysconfigService.getSysConfigByKey(pd);
		String[] roles = null;
		if (StringUtil.isNotEmpty(roleStr)) {
			if (roleStr.contains(",")) {
				roles = roleStr.split(",");
			} else {
				roles = new String[1];
				roles[0] = roleStr;
			}
		}
		if (null != roles) {
			if (Arrays.asList(roles).contains(roleId)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
