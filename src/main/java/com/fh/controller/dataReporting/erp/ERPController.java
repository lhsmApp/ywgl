package com.fh.controller.dataReporting.erp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.DictsUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.service.dataReporting.erpdelacctapplication.ERPDelAcctApplicationManager;
import com.fh.service.dataReporting.erpofficialacctapplication.ERPOfficialAcctApplicationManager;
import com.fh.service.dataReporting.erptempacctapplication.ERPTempAcctApplicationManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/** 
 * 说明：ERP删除账号申请处理模块
 * 创建人：xinyuLo
 * 创建时间：2019-10-11
 */
@Controller
@RequestMapping(value="/erp")
public class ERPController extends BaseController {
	
	String menuUrl = "erp/erpOaaList.do"; //菜单地址(权限用)
	@Resource(name="erpdelacctapplicationService")
	private ERPDelAcctApplicationManager erpdelacctapplicationService;
	@Resource(name="erptempacctapplicationService")
	private ERPTempAcctApplicationManager erptempacctapplicationService;
	@Resource(name="erpofficialacctapplicationService")
	private ERPOfficialAcctApplicationManager erpofficialacctapplicationService;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/erpDaaList")
	public ModelAndView erpDaaList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ERPDelAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","SystemDataTime");
		//String date = sysconfigService.getSysConfigByKey(pd);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "2"); //2待审批 3已审批
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		page.setPd(pd);
		//获取业务期间
		List<PageData>  listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData>	varList = erpdelacctapplicationService.list(page);	//列出ERPDelAcctApplication列表
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService,"00");
		if(DepartmentSelectTreeSource.equals("0"))
		{
			pd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			pd.put("departTreeSource", 1);
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		mv.setViewName("dataReporting/erp/erpdaa_list");
		mv.addObject("varList", varList);
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		Map_SetColumnsList.clear();
        Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetColumnsList.put("DEPART_CODE", new TmplConfigDetail("DEPART_CODE", "二级单位", "1", false));
		Map_SetColumnsList.put("UNITS_DEPART", new TmplConfigDetail("UNITS_DEPART", "三级单位", "1", false));
		Map_SetColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
		Map_SetColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
		Map_SetColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetColumnsList.put("PERMISSION_CHANGE", new TmplConfigDetail("PERMISSION_CHANGE", "权限变更", "1", false));
		Map_SetColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
		Map_SetColumnsList.put("ACCOUNT_DELETE_REASON", new TmplConfigDetail("ACCOUNT_DELETE_REASON", "账号删除原因", "1", false));
		Map_SetColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));
		
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/erpOaaList")
	public ModelAndView erpOaaList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表erpOaaList");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","SystemDataTime");
//		String date = sysconfigService.getSysConfigByKey(pd);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "2"); //2待审批 3已审批
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		page.setPd(pd);
		//获取业务期间
		List<PageData>  listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData>	varList = erpofficialacctapplicationService.list(page);	//列出ERPOfficialAcctApplication列表
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService,"00");
		if(DepartmentSelectTreeSource.equals("0"))
		{
			pd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			pd.put("departTreeSource", 1);
		}
		mv.setViewName("dataReporting/erp/erpoaa_list");
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		//***********************************************************
		Map_SetColumnsList.clear();//清空,重新添加防止导出时污染输出源
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetColumnsList.put("DEPART_CODE", new TmplConfigDetail("DEPART_CODE", "二级单位", "1", false));
		Map_SetColumnsList.put("UNITS_DEPART", new TmplConfigDetail("UNITS_DEPART", "三级单位", "1", false));
		Map_SetColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
		Map_SetColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
		Map_SetColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetColumnsList.put("IF_TRAINING", new TmplConfigDetail("IF_TRAINING", "是否培训", "1", false));
		Map_SetColumnsList.put("TRAINING_METHOD", new TmplConfigDetail("TRAINING_METHOD", "培训方式", "1", false));
		Map_SetColumnsList.put("TRAINING_TIME", new TmplConfigDetail("TRAINING_TIME", "培训时间", "1", false));
		Map_SetColumnsList.put("TRAINING_RECORD", new TmplConfigDetail("TRAINING_RECORD", "培训成绩", "1", false));
		Map_SetColumnsList.put("CERTIFICATE_NUM", new TmplConfigDetail("CERTIFICATE_NUM", "证书编号", "1", false));
		Map_SetColumnsList.put("UKEY_NUM", new TmplConfigDetail("UKEY_NUM", "UKey编号", "1", false));
		Map_SetColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
		Map_SetColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/erpTaaList")
	public ModelAndView erpTaaList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ERPTempAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","SystemDataTime");
//		String date = sysconfigService.getSysConfigByKey(pd);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "2"); //2待审批 3已审批
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		page.setPd(pd);
		List<PageData>	varList = erptempacctapplicationService.list(page);	//列出ERPTempAcctApplication列表
		//获取业务期间
		List<PageData>  listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService,"00");
		if(DepartmentSelectTreeSource.equals("0"))
		{
			pd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			pd.put("departTreeSource", 1);
		}
		mv.setViewName("dataReporting/erp/erptaa_list");
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		//***********************************************************
		Map_SetColumnsList.clear();
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetColumnsList.put("STAFF_UNIT_LEVEL2", new TmplConfigDetail("STAFF_UNIT", "二级单位", "1", false));
		Map_SetColumnsList.put("STAFF_UNIT_LEVEL3", new TmplConfigDetail("STAFF_DEPART", "三级单位", "1", false));
		Map_SetColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
		Map_SetColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
		Map_SetColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
		Map_SetColumnsList.put("CANCEL_DATE", new TmplConfigDetail("CANCEL_DATE", "撤销日期", "1", false));
		Map_SetColumnsList.put("APPLY_TEMP_REASON", new TmplConfigDetail("APPLY_TEMP_REASON", "申请临时原因", "1", false));
		Map_SetColumnsList.put("UKEY_NUM", new TmplConfigDetail("UKEY_NUM", "UKey编号", "1", false));
		Map_SetColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));
		return mv;
	}
	
	/**
	 * 批量审批/驳回
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/oaaReport")
	@ResponseBody
	public CommonBase oaaReport() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "erp批量审批/驳回oaa");
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		commonBase.setCode(-1);
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String arrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("arrayDATA_IDS", arrayDATA_IDS);
			erpofficialacctapplicationService.editReportState(pd);
			commonBase.setCode(0);
		}
		
		return commonBase;
	}
	
	
	
	/**
	 * 批量上报/驳回
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/taaReport")
	@ResponseBody
	public CommonBase taaReport() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "taa批量上报/驳回erp");
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		commonBase.setCode(-1);
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String arrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("arrayDATA_IDS", arrayDATA_IDS);
			erptempacctapplicationService.editReportState(pd);
			commonBase.setCode(0);
		}
		
		return commonBase;
	}
	
	/**
	 * 批量上报/驳回
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/daaReport")
	@ResponseBody
	public CommonBase daaReport() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "daa批量上报/驳回erp");
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		commonBase.setCode(-1);
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String arrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("arrayDATA_IDS", arrayDATA_IDS);
			erpdelacctapplicationService.editReportState(pd);
			commonBase.setCode(0);
		}
		
		return commonBase;
	}
	
	
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/oaaExcel")
	public ModelAndView oaaExportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERP审批数据到excel");
		PageData pd = this.getPageData();
		pd.put("confirmState", pd.get("confirmState")); //2待审批 3已审批
		pd.put("BUSI_DATE", pd.get("busiDate"));
		
		page.setPd(pd);
		List<PageData> varOList = erpofficialacctapplicationService.exportList(page);
		return export(varOList, "", Map_SetColumnsList);
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/taaExcel")
	public ModelAndView taaExportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERP审批数据到excel");
		PageData pd = this.getPageData();
		pd.put("confirmState", pd.get("confirmState")); //2待审批 3已审批
		pd.put("BUSI_DATE", pd.get("busiDate"));
		page.setPd(pd);
		List<PageData> varOList = erptempacctapplicationService.exportList(page);
		return export(varOList, "", Map_SetColumnsList);
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/daaExcel")
	public ModelAndView daaExportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERP审批数据到excel");
		PageData pd = this.getPageData();
		pd.put("confirmState", pd.get("confirmState")); //2待审批 3已审批
		pd.put("BUSI_DATE", pd.get("busiDate"));
		page.setPd(pd);
		List<PageData> varOList = erpdelacctapplicationService.exportList(page);
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
							if(null == getCellValue){
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
