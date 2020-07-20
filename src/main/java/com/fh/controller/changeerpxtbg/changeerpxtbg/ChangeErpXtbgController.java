package com.fh.controller.changeerpxtbg.changeerpxtbg;

import java.io.PrintWriter;
import java.net.URLDecoder;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.controller.common.BillnumUtil;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.PageResult2;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.StringUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.BillNumType;
import com.fh.util.enums.ProPriority;
import com.fh.util.enums.ProState;

import net.sf.json.JSONArray;

import com.fh.service.billnum.BillNumManager;
import com.fh.service.changeerpxtbg.changeerpxtbg.ChangeErpXtbgManager;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：changeManage
 * 创建人：jiachao
 * 创建时间：2019-09-20
 */
@Controller
@RequestMapping(value="/changeerpxtbg")
public class ChangeErpXtbgController extends BaseController {
	
	String menuUrl = "changeerpxtbg/list.do"; //菜单地址(权限用)
	@Resource(name="changeerpxtbgService")
	private ChangeErpXtbgManager changeerpxtbgService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	@Resource(name = "billnumService")
	private BillNumManager billNumService;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name = "userService")
	private UserManager userService;
	
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;
	
	
	Map<String, TmplConfigDetail> Map_SetColumnsListXtbg = new LinkedHashMap<String, TmplConfigDetail>();
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public @ResponseBody CommonBase save() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		if(null==pd.getString("BILL_CODE")||pd.getString("BILL_CODE").trim().equals("")){
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		    String userId=user.getUSER_ID();
		    pd.put("BILL_USER", userId);	
		    pd.put("BILL_DATE", DateUtils.getCurrentTime().split(" ")[0]);//创建日期
		    pd.put("ENTRY_DATE", DateUtils.getCurrentTime().split(" ")[0]);//填表日期
			String billCode=BillnumUtil.getBillnum(billNumService, BillNumType.ERP_XTBG, pd.getString("UNIT_CODE"), "");
			pd.put("BILL_CODE", billCode);
			changeerpxtbgService.save(pd);
			commonBase.setCode(0);
		}else{
			pd.put("UPDATE_DATE", DateUtils.getCurrentTime().split(" ")[0]);
			changeerpxtbgService.edit(pd);
			commonBase.setCode(0);
		}		
		if(commonBase.getCode()==0){
			BillnumUtil.updateBillnum(billNumService, BillNumType.ERP_XTBG);
		}
		return commonBase;
	}

	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String billCode=pd.getString("BILL_CODE");
		pd.put("BILL_CODE", URLDecoder.decode(billCode, "UTF-8"));
		changeerpxtbgService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		changeerpxtbgService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String userId=user.getUSER_ID();
	    pd.put("BILL_USER", userId);	
		String keywords = pd.getString("keywords");	
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = changeerpxtbgService.list(page);	//列出ChangeErpXtbg列表
		for(PageData p:varList){
			if(null!=p.getString("APPROVAL_STATE")&&!"".equals(p.getString("APPROVAL_STATE"))){
				if(p.getString("APPROVAL_STATE").equals("0")){
					p.put("APPROVAL_STATE", "审批中");
				}else if(p.getString("APPROVAL_STATE").equals("1")){
					p.put("APPROVAL_STATE", "已完成");
				}else if(p.getString("APPROVAL_STATE").equals("2")){
					p.put("APPROVAL_STATE", "退回");
				}
			}else{
				p.put("APPROVAL_STATE", "未上报");
			}		
		} 	
		mv.addObject("userList", DictsUtil.getSysUserDic(userService));//用户
		pd.put("DEPARTMENT_CODE",  user.getUNIT_CODE());
		page.setPd(pd);
		List<PageData> userDeptList= departmentService.list(page);
		mv.addObject("userDeptList", userDeptList);//用户
//		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
//		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
//		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		mv.setViewName("changeerpxtbg/changeerpxtbg/changeerpxtbg_list");
		mv.addObject("varList", JSON.toJSONString(varList));
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**系统变更查询列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/queryList")
	public ModelAndView queryList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd1 = new PageData();
		PageData pd2 = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String userId=user.getUSER_ID();
	    String roleId = user.getRole().getROLE_ID();//获取当前用户所属角色ID
	    String unitCode=user.getUNIT_CODE();//获取当前用户所属单位
		pd1.put("KEY_CODE", "xxglbRoles");//信息管理部角色
		pd2.put("KEY_CODE", "jcdwczyRoles");//基层单位操作员角色
		String roleStr = sysconfigService.getSysConfigByKey(pd1);
		String roleStr2 = sysconfigService.getSysConfigByKey(pd2);
		String[] roles = null;
		if (StringUtil.isNotEmpty(roleStr)) {
			if (roleStr.contains(",")) {
				roles = roleStr.split(",");
			} else {
				roles = new String[1];
				roles[0] = roleStr;
			}
		}
		String[] roles2 = null;
		if (StringUtil.isNotEmpty(roleStr)) {
			if (roleStr2.contains(",")) {
				roles2 = roleStr2.split(",");
			} else {
				roles2 = new String[1];
				roles2[0] = roleStr2;
			}
		}
	    pd.put("BILL_USER", userId);	
		String keywords = pd.getString("keywords");	
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if (null != roles && !"".equals(roles)) {
			if (Arrays.asList(roles).contains(roleId)){
				pd.put("BILL_USER", "");
			}
		}
		if (null != roles2 && !"".equals(roles2)) {
			if (Arrays.asList(roles2).contains(roleId)){
				pd.put("BILL_USER", "");
				pd.put("UNIT_CODE", unitCode);
			}
		}
		page.setPd(pd);
		List<PageData>	varList = changeerpxtbgService.list(page);	//列出ChangeErpXtbg列表
		mv.setViewName("changeerpxtbg/changeerpxtbg/changeerpxtbgQuery");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		Map_SetColumnsListXtbg.put("BILL_CODE", new TmplConfigDetail("BILL_CODE", "申请单号", "1", false));
		Map_SetColumnsListXtbg.put("BG_NAME", new TmplConfigDetail("BG_NAME", "变更名称", "1", false));
		Map_SetColumnsListXtbg.put("UNIT_NAME", new TmplConfigDetail("UNIT_NAME", "申请单位", "1", false));
		Map_SetColumnsListXtbg.put("DEPT_NAME", new TmplConfigDetail("DEPT_NAME", "申请部门", "1", false));
		Map_SetColumnsListXtbg.put("BG_REASON", new TmplConfigDetail("BG_REASON", "变更原因", "1", false));
		Map_SetColumnsListXtbg.put("USERNAME",new TmplConfigDetail("USERNAME", "申请人", "1", false));
		Map_SetColumnsListXtbg.put("USER_DEPTNAME", new TmplConfigDetail("USER_DEPTNAME", "申请人部门", "1", false));
		Map_SetColumnsListXtbg.put("USER_JOB", new TmplConfigDetail("USER_JOB", "申请人岗位", "1", true));
		Map_SetColumnsListXtbg.put("USER_CONTACT", new TmplConfigDetail("USER_CONTACT", "联系方式", "1", false));
		Map_SetColumnsListXtbg.put("ENTRY_DATE", new TmplConfigDetail("ENTRY_DATE", "申请日期", "1", true));
		Map_SetColumnsListXtbg.put("APPROVAL_STATE", new TmplConfigDetail("APPROVAL_STATE", "处理状态", "1", false));
		return mv;
	}
	/**显示该用户提报的变更申请单
	 * @param page
	 * @throws Exceptionz
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult2<PageData> getPageList(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("BILL_USER", userId);	
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = changeerpxtbgService.list(page);
		for(PageData p:varList){
			if(null!=p.getString("APPROVAL_STATE")&&!"".equals(p.getString("APPROVAL_STATE"))){
				if(p.getString("APPROVAL_STATE").equals("0")){
					p.put("APPROVAL_STATE", "审批中");
				}else if(p.getString("APPROVAL_STATE").equals("1")){
					p.put("APPROVAL_STATE", "已完成");
				}else if(p.getString("APPROVAL_STATE").equals("2")){
					p.put("APPROVAL_STATE", "退回");
				}
			}else{
				p.put("APPROVAL_STATE", "未上报");
			}		
		} 	
		PageResult2<PageData> result = new PageResult2<PageData>();
		result.setRows(varList);
		result.setPage(page);
		result.setPageHtml(page.getPageStr2());
		return result;
	}
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("changeerpxtbg/changeerpxtbg/changeerpxtbg_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}	
	/**显示变更详情
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/detailView")
	public ModelAndView detailView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdResult = changeerpxtbgService.findById(pd);	//根据ID读取
		mv.setViewName("changeerpxtbg/changeerpxtbg/xtbgDetailView");
		mv.addObject("pd", pdResult);
		return mv;
	}
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String billCode=pd.getString("BILL_CODE");
		pd.put("BILL_CODE", URLDecoder.decode(billCode, "UTF-8"));
		pd = changeerpxtbgService.findById(pd);	//根据ID读取
		mv.setViewName("changeerpxtbg/changeerpxtbg/changeerpxtbg_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}	
	 /**打印
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goPrint")
	public ModelAndView goPrint()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = changeerpxtbgService.findById(pd);	//根据ID读取
		JSONArray json = JSONArray.fromObject(pd); 
		mv.setViewName("changeerpxtbg/changeerpxtbg/PrintReport");
		mv.addObject("ReportURL", "static/js/gridReport/grf/changeErpXtbg.grf");
		mv.addObject("DataURL", "changeerpxtbg/PrintXtbg.do?BILL_CODE="+pd.getString("BILL_CODE"));
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("billCode", pd.get("BILL_CODE"));
		
		return mv;
	}	
	@RequestMapping(value="/PrintXtbg")
	@ResponseBody 
	public  PageData PrintXtbg() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据BILL_CODE读取表单信息以及表单上申请人所在的单位及部门名称
		pd = changeerpxtbgService.findByBillCode(pd);	
		List<PageData> listPageData=new ArrayList<PageData>();
		listPageData.add(pd);
		
		PageData pdResult=new PageData();
		pdResult.put("Table", listPageData);
		return 	pdResult;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/showPrint")
	@ResponseBody 
	public PageData showPrint() throws Exception{
		PageData pd1 = new PageData();
		pd1 = this.getPageData();
		String billCode=pd1.getString("BILL_CODE");
		pd1.put("BILL_CODE", URLDecoder.decode(billCode, "UTF-8"));
		pd1 = changeerpxtbgService.findById(pd1);	//根据ID读取
		return pd1;
	}
	 /**html打印
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/printf")
	public ModelAndView printf()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = changeerpxtbgService.findById(pd);	//根据ID读取
		//判断邮箱和电话是否为空
		if("".equals(pd.get("USER_EMAIL")) || null==pd.get("USER_EMAIL")){
			pd.put("CONTACT", pd.get("USER_TEL"));
		}else if("".equals(pd.get("USER_TEL")) || null==pd.get("USER_TEL")){
			pd.put("CONTACT", pd.get("USER_EMAIL"));
		}else{
			pd.put("CONTACT", pd.get("USER_EMAIL")+","+pd.get("USER_TEL"));
		}	
		JSONArray json = JSONArray.fromObject(pd); 
		mv.setViewName("changeerpxtbg/changeerpxtbg/printPage");
		mv.addObject("pd", pd);
		mv.addObject("billCode", pd.get("BILL_CODE"));
		
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/showDetail")
	public @ResponseBody PageData showDetail() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = changeerpxtbgService.findById(pd);	//根据ID读取
		pd.put("DEPARTMENT_CODE", pd.getString("UNIT_CODE"));
		PageData pdDepartResultUnit=departmentService.findByBianma(pd);
		//获取申请单位
		if(pdDepartResultUnit!=null)
			pd.put("depnameUnit", pdDepartResultUnit.getString("NAME"));
		else
			pd.put("depnameUnit", null);
		
		return pd;
	}
	/**根据单位获取用户信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getUsers")
	public @ResponseBody List<PageData> getUsers()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();		
		List<PageData> userList = userService.listUserByUnitCode(pd);
		return userList;
	}
	 /**上报
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/reportChange")
	@ResponseBody
	public Object reportChange() throws Exception{
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			changeerpxtbgService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除changeerpxtbg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			changeerpxtbgService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 导出到excel-系统变更
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportXtbg")
	public ModelAndView exportXtbg(Page page) throws Exception {
		PageData pd = new PageData();
		PageData pd1 = new PageData();
		PageData pd2 = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String userId=user.getUSER_ID();
	    String roleId = user.getRole().getROLE_ID();//获取当前用户所属角色ID
	    String unitCode=user.getUNIT_CODE();//获取当前用户所属单位
		pd1.put("KEY_CODE", "xxglbRoles");//信息管理部角色
		pd2.put("KEY_CODE", "jcdwczyRoles");//基层单位操作员角色
		String roleStr = sysconfigService.getSysConfigByKey(pd1);
		String roleStr2 = sysconfigService.getSysConfigByKey(pd2);
		String[] roles = null;
		if (StringUtil.isNotEmpty(roleStr)) {
			if (roleStr.contains(",")) {
				roles = roleStr.split(",");
			} else {
				roles = new String[1];
				roles[0] = roleStr;
			}
		}
		String[] roles2 = null;
		if (StringUtil.isNotEmpty(roleStr)) {
			if (roleStr2.contains(",")) {
				roles2 = roleStr2.split(",");
			} else {
				roles2 = new String[1];
				roles2[0] = roleStr2;
			}
		}
	    pd.put("BILL_USER", userId);	
		String keywords = pd.getString("keywords");	
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if (null != roles && !"".equals(roles)) {
			if (Arrays.asList(roles).contains(roleId)){
				pd.put("BILL_USER", "");
			}
		}
		if (null != roles2 && !"".equals(roles2)) {
			if (Arrays.asList(roles2).contains(roleId)){
				pd.put("BILL_USER", "");
				pd.put("UNIT_CODE", unitCode);
			}
		}
		page.setPd(pd);
		List<PageData>	varList = changeerpxtbgService.list(page);	//列出ChangeErpXtbg列表
		List<PageData> varOList =new ArrayList<PageData>();
		for(PageData p:varList){
			if(p.containsKey("APPROVAL_STATE")){
				if("0".equals(p.getString("APPROVAL_STATE"))){
					p.put("APPROVAL_STATE", "审批中");
				}else if("2".equals(p.getString("APPROVAL_STATE"))){
					p.put("APPROVAL_STATE", "退回");
				}else if("1".equals(p.getString("APPROVAL_STATE"))){
					p.put("APPROVAL_STATE", "已完成");
				}else{
					p.put("APPROVAL_STATE", "未上报");
				}	
				varOList.add(p);
			}			
		}
		return export(varOList, "ERP系统变更_" + DateUtils.getCurrentTime(DateFormatUtils.DATE_FORMAT1),
				Map_SetColumnsListXtbg);
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
							vpd.put("var" + j, StringUtil.toString(getCellValue, ""));
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
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page) throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"导出changeerpxtbg到excel");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("变更单号");	//1
		titles.add("变更名称");	//2
		titles.add("申请单位");	//3
		titles.add("申请部门");	//4
		titles.add("变更原因");	//5
		titles.add("申请人");	//6
		titles.add("申请人部门");	//7
		titles.add("申请人岗位");	//8
		titles.add("联系方式");	//9
		titles.add("申请日期");	//10
		dataMap.put("titles", titles);
		page.setPd(pd);
		List<PageData> varOList = changeerpxtbgService.list(page);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("BILL_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("BG_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("UNIT_NAME"));	    //3
			vpd.put("var4", varOList.get(i).getString("DEPT_NAME"));	    //4
			vpd.put("var5", varOList.get(i).getString("BG_REASON"));	    //5
			vpd.put("var6", varOList.get(i).getString("USERNAME"));	    //6
			vpd.put("var7", varOList.get(i).getString("USER_DEPTNAME"));	    //7
			vpd.put("var8", varOList.get(i).getString("USER_JOB"));	    //8
			vpd.put("var9", varOList.get(i).getString("USER_CONTACT"));	    //9
			vpd.put("var10", varOList.get(i).getString("ENTRY_DATE"));	    //10
			varList.add(vpd);				
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
