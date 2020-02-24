package com.fh.controller.trainBase;

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
import com.fh.service.trainBase.CourseTypeManager;

/** 
 * 说明：知识结构分类
 * 创建人：jiachao
 * 创建时间：2019-10-25
 */
@Controller
@RequestMapping(value="/coursetype")
public class CourseTypeController extends BaseController {
	
	String menuUrl = "coursetype/list.do"; //菜单地址(权限用)
	@Resource(name="coursetypeService")
	private CourseTypeManager coursetypeService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(StringUtil.isEmpty(pd.getString("COURSE_TYPE_PARENT_ID"))){
			pd.put("COURSE_TYPE_PARENT_ID", "0");
		}
		coursetypeService.save(pd);
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
	public Object delete(@RequestParam String COURSETYPE_ID) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("COURSETYPE_ID", COURSETYPE_ID);
		String errInfo = "success";
		if(coursetypeService.listByParentId(COURSETYPE_ID).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			coursetypeService.delete(pd);	//执行删除
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
		coursetypeService.edit(pd);
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
		String COURSE_TYPE_ID = null == pd.get("COURSE_TYPE_ID")?"":pd.get("COURSE_TYPE_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			COURSE_TYPE_ID = pd.get("id").toString();
		}
		pd.put("COURSE_TYPE_ID", COURSE_TYPE_ID);					//上级ID
		pd.put("COURSETYPE_ID", COURSE_TYPE_ID);					//上级ID(兼容)
		page.setPd(pd);
		mv.addObject("keywords",keywords);
		List<PageData>	varList = coursetypeService.list(page);			//列出CourseType列表
		mv.setViewName("trainBase/coursetype/coursetype_list");
		mv.addObject("pd", coursetypeService.findById(pd));				//传入上级所有信息
		mv.addObject("COURSETYPE_ID", COURSE_TYPE_ID);			//上级ID
		mv.addObject("varList", varList);
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String COURSETYPE_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(coursetypeService.listTree("0"));
			String json = arr.toString();
			json = json.replaceAll("COURSETYPE_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subCourseType", "nodes").replaceAll("hasCourseType", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("COURSETYPE_ID",COURSETYPE_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("trainBase/coursetype/coursetype_tree");
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
		String COURSETYPE_ID = null == pd.get("COURSETYPE_ID")?"":pd.get("COURSETYPE_ID").toString();
		pd.put("COURSETYPE_ID", COURSETYPE_ID);					//上级ID
		pd.put("COURSE_TYPE_ID", COURSETYPE_ID);					//上级ID(兼容）
		pd.put("COURSE_TYPE_PARENT_ID", COURSETYPE_ID);					//上级ID(兼容）
		mv.addObject("pds",coursetypeService.findById(pd));				//传入上级所有信息
		mv.addObject("pd", pd);			
		mv.addObject("COURSETYPE_ID", COURSETYPE_ID);			//传入ID，作为子级ID用
		mv.setViewName("trainBase/coursetype/coursetype_edit");
		mv.addObject("msg", "save");
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
		PageData pdParent = new PageData();//查询父节点名称
		
		pd = this.getPageData();
		pd.put("COURSETYPE_ID",pd.getString("COURSE_TYPE_ID"));//兼容
		pd = coursetypeService.findById(pd);//根据ID获取当前节点数据		
		mv.addObject("pd", pd);	//放入视图容器
		pdParent.put("COURSE_TYPE_ID",pd.get("COURSE_TYPE_PARENT_ID").toString());//将上级的id放入准备查询
		mv.addObject("pds",coursetypeService.findById(pdParent));//传入上级所有信息
		mv.addObject("COURSETYPE_ID", pd.get("COURSE_TYPE_PARENT_ID").toString());//传入上级ID，作为子ID用
		mv.setViewName("trainBase/coursetype/coursetype_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出CourseType到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = coursetypeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("COURSE_TYPE_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("COURSE_TYPE_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("LEADER"));	    //3
			vpd.put("var4", varOList.get(i).getString("STATE"));	    //4
			vpd.put("var5", varOList.get(i).get("COURSE_TYPE_PARENT_ID").toString());	//5
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
