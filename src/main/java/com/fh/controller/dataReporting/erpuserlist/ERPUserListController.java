package com.fh.controller.dataReporting.erpuserlist;

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
import com.fh.service.dataReporting.erpuserlist.ERPUserListManager;
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
 * 说明：ERP用户清单 创建人：xunyuLo 创建时间：2019-11-27
 */
@Controller
@RequestMapping(value = "/erpuserlist")
public class ERPUserListController extends BaseController {

	String menuUrl = "erpuserlist/list.do"; // 菜单地址(权限用)
	@Resource(name = "erpuserlistService")
	private ERPUserListManager erpuserlistService;
	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;
	@Resource(name = "grcpersonService")
	private GRCPersonManager grcpersonService;

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
		logBefore(logger, Jurisdiction.getUsername() + "新增与修改ERPUserList");
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
		@SuppressWarnings("unchecked")
		List<String> listTransferData = (List<String>) JSONArray.toCollection(array, PageData.class);// 过时方法
		for (int i = 0; i < listTransferData.size(); i++) {
			staffId = listTransferData.get(i).trim();
			PageData pageData = new PageData();
			pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
			pageData.put("STATE", "1");
			pageData.put("BILL_USER", user.getUSER_ID());
			pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
			pageData.put("ID", listTransferData.get(i++));
			pageData.put("USER_NAME", listTransferData.get(i++).trim());
			pageData.put("NAME", listTransferData.get(i++).trim());
			pageData.put("USER_GROUP", listTransferData.get(i++).trim());
			pageData.put("ACCOUNT_STATE", listTransferData.get(i++).trim());
			pageData.put("START_DATE", listTransferData.get(i++).trim());
			pageData.put("END_DATE", listTransferData.get(i++).trim());
			pageData.put("DEPART", listTransferData.get(i++).trim());
			pageData.put("USER_DEPART", map.get(pageData.getString("DEPART")));//翻译单位名
			pageData.put("JOB", listTransferData.get(i++).trim());
			pageData.put("CHANGE_NO", listTransferData.get(i++).trim());
			pageData.put("PHONE", listTransferData.get(i).trim());
			if (null != staffId && !"".equals(staffId)) {// 如果有ID则进行修改
				erpuserlistService.edit(pageData);
			} else {// 如果无ID则进行新增
				erpuserlistService.save(pageData);
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
		logBefore(logger, Jurisdiction.getUsername() + "列表ERPUserList");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData data = new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd = this.getPageData();
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE", "erpuserlistMustKey");
		data.put("BUSI_TYPE", SysDeptTime.ERP_USER_LIST.getNameKey());
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
		List<PageData> varList = erpuserlistService.list(page); // 列出GRCPerson列表
		mv.setViewName("dataReporting/erpuserlist/erpuserlist_list");
		mv.addObject("varList", varList);
		mv.addObject("listBusiDate", listBusiDate);
		mv.addObject("isTheSameMonth", theSameMonth.equals(pd.get("busiDate").toString()) ? 1 : 0);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限

		// ***********************************************************
		Map_SetColumnsList.put("USER_NAME", new TmplConfigDetail("USER_NAME", "用户名", "1", false));
		Map_SetColumnsList.put("NAME", new TmplConfigDetail("NAME", "姓名", "1", false));
		Map_SetColumnsList.put("USER_GROUP", new TmplConfigDetail("USER_GROUP", "用户组", "1", false));
		Map_SetColumnsList.put("ACCOUNT_STATE", new TmplConfigDetail("ACCOUNT_STATE", "账号状态", "1", false));
		Map_SetColumnsList.put("START_DATE", new TmplConfigDetail("START_DATE", "有效期自", "1", false));
		Map_SetColumnsList.put("END_DATE", new TmplConfigDetail("END_DATE", "有效期至", "1", false));
		Map_SetColumnsList.put("DEPART", new TmplConfigDetail("DEPART", "单位", "1", false));
		Map_SetColumnsList.put("JOB", new TmplConfigDetail("JOB", "岗位", "1", false));
		Map_SetColumnsList.put("CHANGE_NO", new TmplConfigDetail("CHANGE_NO", "变更序号", "1", false));
		Map_SetColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "电话", "1", false));

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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除ERPUserList");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			erpuserlistService.deleteAll(ArrayDATA_IDS);
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
		mv.addObject("local", "erpuserlist");
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
		List<PageData> varOList = erpuserlistService.exportModel(getPd);
		return export(varOList, "ERPUserList", Map_SetColumnsList);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERPUserList到excel");
		PageData getPd = this.getPageData();
		page.setPd(getPd);
		List<PageData> varOList = erpuserlistService.exportList(page);
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
			//获取所有单位和对应的单位id
			List<PageData> zdepartmentPdList = new ArrayList<PageData>();
			departmentService.listAllDepartmentToSelect("0", zdepartmentPdList);
			Map <String,String> map=new HashMap<>();
			for(PageData a:zdepartmentPdList) {
				map.put(a.get("name").toString(),a.get("id").toString());
			}
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			for (PageData pageData : listUploadAndRead) {
				// 将每条数据插入新内容
				pageData.put("USER_DEPART", map.get(pageData.get("DEPART").toString().trim()));
				pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
				pageData.put("STATE", "1");
				pageData.put("BILL_USER", user.getUSER_ID());
				pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
			}
			erpuserlistService.grcUpdateDatabase(listUploadAndRead);
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
	 * 查询统计列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryList")
	public ModelAndView queryList(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表ERPUserList");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd = this.getPageData();
		pd.put("USER_DEPART", user.getUNIT_CODE());
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		
		// 若是则树形结构加载所有单位，并将用户单位查询条件更新为所选单位
		JSONArray arr = JSONArray
				.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
		page.setPd(pd);
		List<PageData> varList = erpuserlistService.list(page); // 列出GRCPerson列表
		mv.setViewName("statisticAnalysis/erpuserliststatistics/erpuUserListStatistic_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	
	/**
	 * 导出用户清单统计表到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/listStatisticExcel")
	public ModelAndView listStatisticExcel(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd = this.getPageData();
		pd.put("USER_DEPART", user.getUNIT_CODE());
		page.setPd(pd);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户名"); // 1
		titles.add("姓名"); // 2
		titles.add("用户组"); // 3
		titles.add("账号状态"); // 4
		titles.add("有效期自"); // 5
		titles.add("有效期至"); // 6
		titles.add("单位"); // 7
		titles.add("岗位"); // 8
		titles.add("变更序号"); // 9
		titles.add("电话"); // 10
		dataMap.put("titles", titles);
		List<PageData> varOList = erpuserlistService.list(page); // 列出GRCPerson列表
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USER_NAME")); // 1
			vpd.put("var2", varOList.get(i).get("NAME").toString()); // 2
			vpd.put("var3", varOList.get(i).get("USER_GROUP").toString()); // 3
			vpd.put("var4", varOList.get(i).get("ACCOUNT_STATE").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("START_DATE")); // 5
			vpd.put("var6", varOList.get(i).get("END_DATE").toString()); // 6
			vpd.put("var7", varOList.get(i).get("DEPART").toString()); // 7
			vpd.put("var8", varOList.get(i).get("JOB").toString()); // 8
			vpd.put("var9", varOList.get(i).get("CHANGE_NO").toString()); // 9
			vpd.put("var10", varOList.get(i).get("PHONE").toString()); // 10
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
