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
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.util.date.DateUtils;
import com.fh.service.approvalconfig.approvalconfig.ApprovalConfigManager;
import com.fh.service.changeerpxtbg.changeerpxtbg.impl.ChangeErpXtbgService;

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
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ApprovalConfig");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
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
	/**列表
	 * @param page
	 * @throws Exception
	 */
//	@RequestMapping(value="/showDetail")
//	public @ResponseBody PageData showDetail() throws Exception{
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd = approvalconfigService.findById(pd);	//根据ID读取
//		return pd;
//	}
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
	    String unitCode=user.getDEPARTMENT_ID();
//	    String roleCode=user.getROLE_ID();    
//	    pd.put("ROLE_CODE", roleCode);//单位编码
		pd.put("UNIT_CODE", unitCode);//单位编码
		pd.put("ACTIVE_FLAG", '1');//激活状态为1
		pd.put("APPROVAL_STATE", "0");///审批状态为0	
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = approvalconfigService.listScheme(page);
		
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
	    String unitCode=user.getDEPARTMENT_ID();
//	    String roleCode=user.getROLE_ID(); //审批角色   
//	    pd.put("ROLE_CODE", roleCode);//单位编码
		pd.put("UNIT_CODE", unitCode);//单位编码
		pd.put("ACTIVE_FLAG", '1');//激活状态为1
		pd.put("APPROVAL_STATE", "0");///审批状态为0
		List<PageData>	varList = approvalconfigService.listApproval(pd);	//列出ChangeErpXtbg列表
		mv.setViewName("approvalconfig/approvalconfig/xtbgapproval_list");
		mv.addObject("varList", JSON.toJSONString(varList));
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	/**系统变更单上报
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/xtbgReport")
	@ResponseBody 
	public PageData xtbgReport() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();//获取变更审批表配置信息
		//首先判断该单据是否上报
		List<PageData> listResult=approvalconfigService.list(pd);
		if(null!=listResult&&listResult.size()>0){
			//判断状态是否为退回
			if(listResult.get(0).getString("APPROVAL_STATE").equals("2")){
				approvalconfigService.delete(pd);
			}else{
				pd.put("msg", "该单据已上报！");
				return pd;
			}
		}
		PageData pd1 = new PageData();
		pd1 = this.getPageData();//获取申请单相关信息
		pd1 = changeerpxtbgService.findById(pd1);
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
				p.put("APPROVAL_STATE","0");//当前审批级别
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
			pd.put("msg", "审批级别配置表中未找到相关信息，请确认！");
			return pd;
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
	@RequestMapping(value="/cancleXtbgReport")
	@ResponseBody 
	public PageData cancleXtbgReport() throws Exception{
		PageData pd = new PageData();		
		pd = this.getPageData();
		//首先判断该单据是否上报
		List<PageData> listResult=approvalconfigService.list(pd);
		if(null==listResult||listResult.size()==0){
			pd.put("msg", "该单据未上报不能撤销！");
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
	//	    String unitCode=user.getDEPARTMENT_ID();
		    String userName=user.getUSERNAME();  
	//	    String roleId=user.getROLE_ID();
		    pd.put("APPROVAL_USER",userName);//实际审批人
			pd.put("APPROVAL_DATE", DateUtils.getCurrentTime());//审批日期
			pd.put("APPROVAL_ADVICE", "同意");
			pd.put("APPROVAL_STATE", "1");
	//		pd.put("UNIT_CODE",unitCode);//单位编码
	//		pd.put("ROLE_CODE",roleId);//角色
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
		    String userName=user.getUSERNAME();  
		    pd.put("APPROVAL_USER",userName);//实际审批人
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
