package com.fh.controller.mbp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.mbp.ProblemTypeManager;

/** 
 * 说明：知识类别
 * 创建人：jiachao
 * 创建时间：2019-10-10
 */
@Controller
@RequestMapping(value="/problemtype")
public class ProblemTypeController extends BaseController {
	
	String menuUrl = "problemtype/list.do"; //菜单地址(权限用)
	@Resource(name="problemtypeService")
	private ProblemTypeManager problemtypeService;
	
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
		if(StringUtil.isEmpty(pd.getString("PRO_TYPE_PARENT_ID"))){
			pd.put("PRO_TYPE_PARENT_ID", "0");
		}
		problemtypeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String PROBLEMTYPE_ID) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("PROBLEMTYPE_ID", PROBLEMTYPE_ID);
		String errInfo = "success";
		if(problemtypeService.listByParentId(PROBLEMTYPE_ID).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			problemtypeService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
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
		problemtypeService.edit(pd);
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
		String keywords = pd.getString("keywords");								//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String PROBLEMTYPE_ID = null == pd.get("PROBLEMTYPE_ID")?"":pd.get("PROBLEMTYPE_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			PROBLEMTYPE_ID = pd.get("id").toString();
		}
		pd.put("PROBLEMTYPE_ID", PROBLEMTYPE_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = problemtypeService.list(page);			//列出ProblemType列表
		mv.setViewName("mbp/problemtype/problemtype_list");
		PageData pageData=problemtypeService.findById(pd);
		if(pageData==null){
			pageData=new PageData();
		}
		if(null != keywords && !"".equals(keywords)){
			pageData.put("keywords", keywords.trim());
		}
		mv.addObject("pd", pageData);				//传入上级所有信息
		mv.addObject("PROBLEMTYPE_ID", PROBLEMTYPE_ID);			//上级ID
		mv.addObject("varList", varList);
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String PROBLEMTYPE_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(problemtypeService.listTree("0"));
			String json = arr.toString();
			json = json.replaceAll("PROBLEMTYPE_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subProblemType", "nodes").replaceAll("hasProblemType", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("PROBLEMTYPE_ID",PROBLEMTYPE_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("mbp/problemtype/problemtype_tree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
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
		String PROBLEMTYPE_ID = null == pd.get("PROBLEMTYPE_ID")?"":pd.get("PROBLEMTYPE_ID").toString();
		pd.put("PROBLEMTYPE_ID", PROBLEMTYPE_ID);					//上级ID
		mv.addObject("pds",problemtypeService.findById(pd));				//传入上级所有信息
		mv.addObject("PROBLEMTYPE_ID", PROBLEMTYPE_ID);			//传入ID，作为子级ID用
		mv.setViewName("mbp/problemtype/problemtype_edit");
		mv.addObject("msg", "save");
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
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
		String PROBLEMTYPE_ID = pd.getString("PROBLEMTYPE_ID");
		pd = problemtypeService.findById(pd);							//根据ID读取		
		mv.addObject("pd", pd);													//放入视图容器
		pd.put("PROBLEMTYPE_ID",pd.get("PRO_TYPE_PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",problemtypeService.findById(pd));				//传入上级所有信息
		mv.addObject("PROBLEMTYPE_ID", pd.get("PRO_TYPE_PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("PROBLEMTYPE_ID",PROBLEMTYPE_ID);					//复原本ID
		pd = problemtypeService.findById(pd);							//根据ID读取
		mv.setViewName("mbp/problemtype/problemtype_edit");
		mv.addObject("msg", "edit");
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		PageData pdDepartResult=new PageData();
		pdDepartResult.put("DEPARTMENT_CODE", pd.getString("DEPART_CODE"));
	    pdDepartResult=departmentService.findByBianma(pdDepartResult);
	    if(pdDepartResult!=null)
			mv.addObject("proTypeName", pdDepartResult.getString("NAME"));
		else
			mv.addObject("proTypeName", null);
		return mv;
	}	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出ProblemType到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = problemtypeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("PRO_TYPE_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("PRO_TYPE_NAME"));	    //2
			vpd.put("var3", varOList.get(i).get("PRO_TYPE_PARENT_ID").toString());	//3
			vpd.put("var4", varOList.get(i).getString("DEPART_CODE"));	    //4
			vpd.put("var5", varOList.get(i).get("STATE").toString());	//5
			vpd.put("var6", varOList.get(i).getString("PRO_TYPE_CONTENT"));	    //6
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
