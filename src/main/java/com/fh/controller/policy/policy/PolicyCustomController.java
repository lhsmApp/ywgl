package com.fh.controller.policy.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.policy.policy.PolicyManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

import net.sf.json.JSONArray;

/**
 * 
 * @author JP
 * @version 1.0.0
 * @since 
 * 创建时间：2017年5月9日
 * 功能描述：客户端政策法规展示
 */

@Controller
@RequestMapping(value="/policyCustom")
public class PolicyCustomController extends BaseController{

	@Resource(name="policyService")
	private PolicyManager policyService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listCustom")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Policy");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> policyTypeList = policyService.poliyTypeList(page);//政策分类列表
		String POLI_TYPE = pd.getString("POLI_TYPE");
		if(null != POLI_TYPE && !"".equals(POLI_TYPE)){
			List<PageData> policyTitleList = policyService.policyTitlelistPage(page);//政策分类列表
			mv.addObject("varList", policyTitleList);
		}else{
			List<PageData>	varList = policyService.list(page);	//列出Policy列表
			mv.addObject("varList", varList);
		}
		
		mv.setViewName("policy/policyCustom/policyCustom_list");
//		mv.addObject("varList", varList);
		mv.addObject("policyTypeList", policyTypeList);
//		mv.addObject("policyTitleList", policyTitleList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**列表（政策分类）
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/policyTypeList")
	@ResponseBody
	public Object policyTypeList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"获取所有政策分类列表");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	policyTypeList = policyService.poliyTypeList(page);
		map.put("policyTypeList", policyTypeList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**根据政策分类查询政策
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/policyTitlelistPage")
	@ResponseBody
	public Object policyTitlelistPage(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"根据政策分类查询政策");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> pdList = new ArrayList<PageData>();
		String POLI_TYPE = pd.getString("POLI_TYPE");
		if(null != POLI_TYPE && !"".equals(POLI_TYPE)){
			pd.put("policyTitleList", policyService.policyTitlelistPage(page));
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 显示列表树
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllPolicyType")
	public ModelAndView listAllPolicyType(Page page, Model model,String POLI_TYPE)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> policyTypeList = policyService.poliyTypeList(page);//政策分类列表

			mv.addObject("POLI_TYPE",POLI_TYPE);
			mv.addObject("pd", pd);	
			mv.addObject("policyTypeList", policyTypeList);
			mv.setViewName("policy/policyCustom/policyCustom_tree");
		
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listContent")
	public ModelAndView listContent(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表内容");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>	varList = policyService.list(page);	//列出Policy列表
		List<PageData> policyTypeList = policyService.poliyTypeList(page);//政策分类列表
		List<PageData> policyTitleList = policyService.policyTitlelistPage(page);//政策分类列表
		
		mv.setViewName("policy/policyCustom/policyCustom_list");
		mv.addObject("varList", varList);
		mv.addObject("policyTypeList", policyTypeList);
		mv.addObject("policyTitleList", policyTitleList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
}
