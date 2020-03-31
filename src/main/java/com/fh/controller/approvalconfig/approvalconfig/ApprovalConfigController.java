package com.fh.controller.approvalconfig.approvalconfig;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.util.date.DateUtils;

import net.sf.json.JSONArray;

import com.fh.service.approvalconfig.approvalconfig.ApprovalConfigManager;
import com.fh.service.changeerpxtbg.changeerpjsbg.impl.ChangeErpJsbgService;
import com.fh.service.changeerpxtbg.changeerpxtbg.impl.ChangeErpXtbgService;
import com.fh.service.changegrcxtbg.changegrcqxbg.impl.ChangeGrcQxbgService;
import com.fh.service.changegrcxtbg.changegrczhxz.impl.ChangeGrcZhxzService;
import com.fh.service.changegrcxtbg.changegrczhzx.impl.ChangeGrcZhzxService;
import com.fh.service.fhoa.department.DepartmentManager;

/** 
 * 说明：TB_APPROVAL_BUSINESS_CONFIG
 * 创建人：jiachao
 * 创建时间：2019-10-14
 */
@Controller
@RequestMapping(value="/approvalconfig")
public class ApprovalConfigController extends BaseController {
	
	String menuUrl = "approvalconfig/list.do"; //菜单地址(权限用)
	@Resource(name="approvalconfigService")
	private ApprovalConfigManager approvalconfigService;
	
	@Resource(name="changeerpxtbgService")
	private ChangeErpXtbgService changeerpxtbgService;
	
	@Resource(name="changeErpjsbgService")
	private ChangeErpJsbgService changeerpjsbgService;
	
	@Resource(name="changegrcqxbgService")
	private ChangeGrcQxbgService changegrcqxbgService;
	
	@Resource(name="changegrczhxzService")
	private ChangeGrcZhxzService changegrczhxzService;
	
	@Resource(name="changegrczhzxService")
	private ChangeGrcZhzxService changegrczhzxService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("APPROVALBUSINESSCONFIG_ID", this.get32UUID());	//主键
		approvalconfigService.saveBusiness(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除ApprovalConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		approvalconfigService.deleteBusiness(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ApprovalConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		approvalconfigService.editBusiness(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**显示表单详细信息
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/showDetail")
	public @ResponseBody PageData showDetail() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		switch(pd.getString("BUSINESS_CODE")){
		case "1":   //系统变更;	 
			pd = changeerpxtbgService.findById(pd);
		    break;
		case "2": //角色变更;		   
			pd = changeerpjsbgService.findById(pd);
		    break;	
		case "3": //GRC帐号新增;		   
			pd = changegrczhxzService.findById(pd);
		    break;
		case "4": //GRC权限变更;		   
			pd = changegrcqxbgService.findById(pd);
		    break;
		case "5": //GRC账号撤销;		   
			pd = changegrczhzxService.findById(pd);
		    break;
		default:
			
		    break;
		}	
	
		return pd;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ApprovalConfig");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = approvalconfigService.listBusiness(page);	//列出ApprovalConfig列表
		mv.setViewName("approvalconfig/approvalconfig/approvalconfig_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**显示该用户待审批单据
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody List<PageData> getPageList(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String unitCode=user.getUNIT_CODE();
	    String roleCode=user.getRole().getROLE_ID();    
	    pd.put("ROLE_CODE", roleCode);//单位编码
		pd.put("UNIT_CODE", unitCode);//单位编码
		pd.put("ACTIVE_FLAG", '1');//激活状态为1
		pd.put("APPROVAL_STATE", "0");///审批状态为0	
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList=null;
		switch(pd.getString("BUSINESS_CODE")){
		case "1":   //系统变更;	 
			varList = approvalconfigService.listByBusiness(page,"datalistPageScheme");
		    break;
		case "2": //角色变更;		   
			varList = approvalconfigService.listByBusiness(page,"datalistPageJsbg");
		    break;	
		case "3": //GRC帐号新增;		   
			varList = approvalconfigService.listByBusiness(page,"datalistPageGrcZhxz");
		    break;
		case "4": //GRC权限变更;		   
			varList = approvalconfigService.listByBusiness(page,"datalistPageGrcQxbg");
		    break;
		case "5": //GRC账号撤销;		   
			varList = approvalconfigService.listByBusiness(page,"datalistPageGrcZhzx");
		    break;
		default:
			
		    break;
		}	

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
		return varList;
	}
	/**初始列表,显示当前待审批单据
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/listApproval")
	public ModelAndView listApproval() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据激活标识，审批单位，审批部门，审批角色和审批状态5个条件获取tb_approval_detail信息
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String unitCode=user.getUNIT_CODE();
	    String roleCode=user.getRole().getROLE_ID(); //审批角色   
	    pd.put("ROLE_CODE", roleCode);//角色编码
		pd.put("UNIT_CODE", unitCode);//单位编码
//		pd.put("ACTIVE_FLAG", '1');//激活状态为1
//		pd.put("APPROVAL_STATE", "0");///审批状态为0
		//pd.put("BUSINESS_CODE", "1");///初始显示业务单据类型为系统变更
		List<PageData>	varList = approvalconfigService.listApproval(pd);	//列出ChangeErpXtbg列表
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
		mv.setViewName("approvalconfig/approvalconfig/approval_list");
		mv.addObject("varList", JSON.toJSONString(varList));
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	/**变更上报
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/bgReport")
	@ResponseBody 
	public PageData bgReport() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();//获取变更审批表配置信息
		//首先判断该单据是否上报
		List<PageData> listResult=approvalconfigService.list(pd);
		if(null!=listResult&&listResult.size()>0){
			//判断状态是否为退回
			if(listResult.get(0).getString("APPROVAL_STATE").equals("2")){
				approvalconfigService.delete(pd);
			}else{
				pd.put("msg", "该单据已上报不能重复上报！");
				return pd;
			}
		}
		PageData pd1 = new PageData();
		pd1 = this.getPageData();//获取申请单相关信息
		switch(pd1.getString("BUSINESS_CODE")){
		case "1":   //系统变更;	 
			pd1 = changeerpxtbgService.findById(pd1);
		    break;
		case "2": //角色变更;		   
			pd1 = changeerpjsbgService.findById(pd1);
		    break;	
		case "3": //GRC帐号新增;		   
			pd1 = changegrczhxzService.findById(pd1);
		    break;
		case "4": //GRC权限变更;		   
			pd1 = changegrcqxbgService.findById(pd1);
		    break;
		case "5": //GRC账号撤销;		   
			pd1 = changegrczhzxService.findById(pd1);
		    break;
		default:
			
		    break;
		}	
		String unitCode=pd1.getString("UNIT_CODE");//获取申请人单位
		String userCode=pd1.getString("USER_CODE");//获取申请人编码
		String businessCode=pd.getString("BUSINESS_CODE");//审批业务编码
		String billCode=pd.getString("BILL_CODE");//业务单据编码
		pd1.put("APPLY_USER", userCode);//申请人
		pd1.put("APPLY_DATE", DateUtils.getCurrentTime());//申请日期
		pd1.put("APPROVAL_STATE", "0");//审批状态		
		pd1.put("BUSINESS_CODE", businessCode);
		pd.put("UNIT_CODE", unitCode);
		pd = approvalconfigService.findByIdSchemeUnit(pd);	//根据业务编码、单位和部门读取方案编码
		List<PageData> list = approvalconfigService.findLevelById(pd);//获取审批方案
		if(null!=list&&list.size()>0){
			for(PageData p:list){
				p.put("BUSINESS_CODE", businessCode);//审批业务编码
				p.put("BILL_CODE",billCode);//业务单据编号
				int level= (int)p.get("APPROVAL_LEVEL");//获取审批级别配置表中审批级别
				p.put("CURRENT_LEVEL",level);//当前审批级别
				p.put("APPROVAL_STATE","0");//审批状态
				//当审批级别等于方案配置的级别数量时，无下一审批级别，即下一审批级别为0
				if(level==list.size()){
					p.put("NEXT_LEVEL", 0);
				}else{
					p.put("NEXT_LEVEL", level+1);
				}
				//上报后只对审批级别为1的进行激活
				if(level==1){
					p.put("ACTIVE_FLAG",'1');
				}else{
					p.put("ACTIVE_FLAG",'0');
				}				
			}
		}else{
//			pd1.put("msg", "审批级别配置表中未找到相关信息，请确认！");
//			return pd1;
			//默认路径 yijche，等配置页做好之后再删掉
			PageData p = new PageData();
			p.put("BUSINESS_CODE", businessCode);
			p.put("BILL_CODE",billCode);
			p.put("CURRENT_LEVEL",1);
			p.put("APPROVAL_LEVEL","1");
			p.put("APPROVAL_STATE","0");
			p.put("NEXT_LEVEL", 0);
			p.put("ACTIVE_FLAG",'1');
			p.put("UNIT_CODE","ELH130CUSR");
			p.put("SCHEME_CODE",businessCode+""+p.get("UNIT_CODE"));
			p.put("ROLE_CODE","68a2eb8394484984bb78338c05807533");
			list.add(p);
		}
		pd1.put("listDetail", list);
		approvalconfigService.save(pd1);
		pd1.put("msg", "单据"+billCode+"上报成功！");
		return pd1;
	}
	/**取消系统变更单上报
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/cancleReport")
	@ResponseBody 
	public PageData cancleReport() throws Exception{
		PageData pd = new PageData();		
		pd = this.getPageData();
		//首先判断该单据是否上报
		List<PageData> listResult=approvalconfigService.list(pd);
		if(null==listResult||listResult.size()==0){
			pd.put("msg", "该单据未上报不能撤销！");
			return pd;
		}else if(listResult.get(0).getString("APPROVAL_STATE").equals("1")){
			pd.put("msg", "该单据已完成不能撤销上报！");
			return pd;
		}
		String BILL_CODE = pd.getString("BILL_CODE");
		if(null != BILL_CODE && !"".equals(BILL_CODE)){	
			approvalconfigService.delete(pd);
		}
		pd.put("msg", "单据撤销上报成功！");
		return pd;
	}
	/**审批通过
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/passApproval")
	@ResponseBody
	public PageData passApproval() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		    String unitCode=user.getDEPARTMENT_ID();
		    String userId=user.getUSER_ID();  
		    String roleId=user.getROLE_ID();
		    pd.put("APPROVAL_USER",userId);//实际审批人
			pd.put("APPROVAL_DATE", DateUtils.getCurrentTime());//审批日期
			pd.put("APPROVAL_ADVICE", "同意");
			pd.put("APPROVAL_STATE", "1");
			pd.put("UNIT_CODE",unitCode);//单位编码
			pd.put("ROLE_CODE",roleId);//角色
			//当下一审批级别为0时，即最后一级审批更新审批主表为已完成
			if (Integer.parseInt(pd.get("NEXT_LEVEL").toString())==0){
				pd.put("APPROVAL_STATE_MAIN", "1");//审批完成
			}else{
				pd.put("APPROVAL_STATE_MAIN", "0");//审批中
			}
			//通过业务单据编码和当前审批级别更新审批明细表信息
			approvalconfigService.editDetail(pd);
			pd.put("msg","审批成功");
		}catch(Exception e){
			pd.put("msg", "审批失败!");
		}	
		return pd;
	}
	/**审批退回
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/returnApproval")
	@ResponseBody
	public PageData returnApproval() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		    String userId=user.getUSER_ID();  
		    pd.put("APPROVAL_USER",userId);//实际审批人
			pd.put("APPROVAL_DATE", DateUtils.getCurrentTime());//审批日期
			pd.put("APPROVAL_ADVICE", "退回");
			pd.put("APPROVAL_STATE", "2");
			//通过业务单据编码、审批单位、审批部门、审批角色更新审批明细表信息
			approvalconfigService.editReturnDetail(pd);
			pd.put("msg", "审批退回成功!");
		}catch(Exception e){
			pd.put("msg", "审批退回失败!");
		}	
		return pd;
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
		mv.setViewName("approvalconfig/approvalconfig/approvalconfig_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
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
		pd = approvalconfigService.findByIdBusiness(pd);	//根据ID读取
		mv.setViewName("approvalconfig/approvalconfig/approvalconfig_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ApprovalConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			approvalconfigService.deleteAllBusiness(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出ApprovalConfig到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = approvalconfigService.listAllBusiness(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("BUSINESS_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("BUSINESS_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("NOTE"));	    //3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**变更统计列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listBgStatistic")
	public ModelAndView listBgStatistic(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		//List<PageData>	varList = approvalconfigService.listBusiness(page);	
		List<PageData>	varList = approvalconfigService.listStatistic(page);
		mv.setViewName("statisticAnalysis/bgStatistic/bgStatistic_list");
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}
	/**显示变更统计数据
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listStatistic")
	public @ResponseBody List<PageData> listStatistic(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = approvalconfigService.listStatistic(page);	
	
		return varList;
	}
	 /**导出变更统计表到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/listStatisticExcel")
	public ModelAndView listStatisticExcel(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("变更类型");	//1
		titles.add("提交变更总数");	//2
		titles.add("已处理的变更");	//3
		titles.add("解决率");	//4
		dataMap.put("titles", titles);
		List<PageData> varOList = approvalconfigService.listStatistic(page);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("BUSINESS_NAME"));	    //1
			vpd.put("var2", varOList.get(i).get("total").toString());	    //2
			vpd.put("var3", varOList.get(i).get("solve").toString());	    //3
			vpd.put("var4", varOList.get(i).get("solverate").toString());	    //3
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
	
	/*******************************手机端变更审批*****************************/
	/**获取手机端当前待审批单据
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/listApprovalOfApp")
	public @ResponseBody List<PageData> listApprovalOfApp() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据激活标识，审批单位，审批部门，审批角色和审批状态5个条件获取tb_approval_detail信息
		pd.put("UNIT_CODE", pd.getString("UNIT_CODE"));//审批单位
		pd.put("DEPART_CODE", pd.getString("DEPART_CODE"));//审批部门
		pd.put("ROLE_CODE", pd.getString("ROLE_CODE"));//审批角色
		pd.put("ACTIVE_FLAG", '1');//激活状态为1
		pd.put("APPROVAL_STATE", "0");///审批状态为0
		List<PageData>	varList = approvalconfigService.listApproval(pd);

		return varList;
	}
	
	/**根据查询条件获取手机端变更信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/queryApprovalOfApp")
	public @ResponseBody List<PageData> queryApprovalOfApp() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//只能查自己审批过的单据 
		pd.put("APPROVAL_USER", pd.getString("APPROVAL_USER"));//实际审批人
		//其它界面查询条件
		String keywords = pd.getString("keywords");//关键词检索条件（业务单号或者变更名称）
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("BUSINESS_CODE", pd.getString("BUSINESS_CODE"));//变更业务类型
		pd.put("START_DATE", pd.getString("START_DATE"));//填表日期/申请日期
		pd.put("END_DATE", pd.getString("END_DATE"));//填表日期/申请日期
		pd.put("APPROVAL_STATE", pd.getString("APPROVAL_STATE"));///审批状态
		List<PageData>	varList = approvalconfigService.queryApproval(pd);

		return varList;
	}
	
	/**审批通过
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/passApprovalOfApp")
	@ResponseBody
	public CommonBase passApprovalOfApp() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			pd.put("BILL_CODE", pd.getString("BILL_CODE"));//业务单号
		    pd.put("APPROVAL_USER",pd.getString("USER_ID"));//实际审批人
			pd.put("APPROVAL_DATE", DateUtils.getCurrentTime());//审批日期
			pd.put("APPROVAL_ADVICE",pd.getString("APPROVAL_ADVICE"));
			pd.put("APPROVAL_STATE", "1");//审批状态(0未审批，1已审批)
			pd.put("UNIT_CODE",pd.getString("UNIT_CODE"));//审批单位
			pd.put("DEPART_CODE",pd.getString("DEPART_CODE"));//审批部门
			pd.put("ROLE_CODE",pd.getString("ROLE_CODE"));//审批角色
			pd.put("CURRENT_LEVEL", pd.getString("CURRENT_LEVEL"));
			//当下一审批级别为0时，即最后一级审批更新审批主表为已完成
			if (Integer.parseInt(pd.get("NEXT_LEVEL").toString())==0){
				pd.put("APPROVAL_STATE_MAIN", "1");//审批完成
			}else{
				pd.put("APPROVAL_STATE_MAIN", "0");//审批中
			}
			//通过业务单据编码和当前审批级别更新审批明细表信息
			approvalconfigService.editDetail(pd);
			commonBase.setCode(0);
			commonBase.setMessage("审批成功!");
		}catch(Exception e){
			commonBase.setCode(-1);
			commonBase.setMessage(e.getMessage());
		}	
		return commonBase;
	}
	
	/**审批退回
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/returnApprovalOfApp")
	@ResponseBody
	public CommonBase returnApprovalOfApp() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			pd.put("BILL_CODE", pd.getString("BILL_CODE"));//业务单号
		    pd.put("APPROVAL_USER",pd.getString("USER_ID"));//实际审批人
			pd.put("APPROVAL_DATE", DateUtils.getCurrentTime());//审批日期
			pd.put("APPROVAL_ADVICE", pd.getString("APPROVAL_ADVICE"));
			pd.put("APPROVAL_STATE", "2");
			pd.put("UNIT_CODE",pd.getString("UNIT_CODE"));//审批单位
			pd.put("DEPART_CODE",pd.getString("DEPART_CODE"));//审批部门
			pd.put("ROLE_CODE",pd.getString("ROLE_CODE"));//审批角色
			pd.put("CURRENT_LEVEL", pd.getString("CURRENT_LEVEL"));
			
			//通过业务单据编码、审批单位、审批部门、审批角色更新审批明细表信息
			approvalconfigService.editReturnDetail(pd);
			commonBase.setCode(0);
			commonBase.setMessage("审批退回成功!");
		}catch(Exception e){
			commonBase.setCode(-1);
			commonBase.setMessage(e.getMessage());
		}	
		return commonBase;
	}
	/*******************************手机端变更审批*****************************/
}
