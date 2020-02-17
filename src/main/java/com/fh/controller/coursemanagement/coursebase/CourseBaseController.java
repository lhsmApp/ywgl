package com.fh.controller.coursemanagement.coursebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.coursebase.CourseTree;
import com.fh.entity.system.User;
import com.fh.service.coursemanagement.coursebase.CourseBaseManager;
import com.fh.service.trainBase.CourseTypeManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;

import net.sf.json.JSONArray;

/** 
 * 说明：课程列表处理类
 * 创建人：xinyuLo
 * 创建时间：2019-10-14
 */
@Controller
@RequestMapping(value="/coursebase")
public class CourseBaseController extends BaseController {
	
	String menuUrl = "coursebase/list.do"; //菜单地址(权限用)
	@Resource(name="coursebaseService")
	private CourseBaseManager coursebaseService;
	@Resource(name="coursetypeService")
	private CourseTypeManager coursetypeService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增CourseBase");
	//	if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STATE", "1");
		pd.put("CREATE_USER",user.getUSER_ID());
		pd.put("CREATE_DATE",DateUtils.getCurrentTime(DateFormatUtils.DATE_FORMAT1)); //YYYY-MM-dd
		coursebaseService.save(pd);
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
	public CommonBase delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除CourseBase");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		commonBase.setCode(-1);
		String couseId = pd.getString("COURSE_ID");
		if(null !=couseId) {
			coursebaseService.delete(pd);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改CourseBase");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		coursebaseService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 获取左侧下拉树结构
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/treeList")
	@ResponseBody
	public CommonBase treeList() throws Exception{
		CommonBase commonBase = new CommonBase();
		JSONArray arr = JSONArray.fromObject(coursetypeService.listTree("0"));
		String json = arr.toString();
		json = json.replaceAll("COURSETYPE_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subCourseType", "nodes").replaceAll("hasCourseType", "checked");
		commonBase.setMessage(json);
		commonBase.setCode(0);
		return commonBase;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表CourseBase");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setShowCount(8);
		page.setPd(pd);
		List<PageData>	varList = coursebaseService.list(page);	//列出CourseBase列表
		JSONArray arr = JSONArray.fromObject(coursetypeService.listTree("0"));
		String json = arr.toString();
		json = json.replaceAll("COURSETYPE_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name")
				.replaceAll("subCourseType", "nodes").replaceAll("hasCourseType", "checked");		
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("zTreeNodes",json);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("coursemanagement/coursebase/coursebase_list");
		return mv;
		
	}
	
	/**
	 * 通过id获取其子课程
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listById")
	@ResponseBody
	public CommonBase listById() throws Exception{
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(1);
		pd = this.getPageData();
		JSONArray arr = JSONArray.fromObject(coursebaseService.listById(pd));
		String json = arr.toString();
		commonBase.setMessage(json);
		return commonBase;
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
		List<CourseTree> treeList = coursebaseService.listByParentId("0");
		mv.setViewName("coursemanagement/coursebase/coursebase_edit");
		mv.addObject("treeList",treeList);
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	/**根据课程分类获取课程信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getCourse")
	public @ResponseBody List<PageData> getCourse()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		pd.put("STATE",'1');
		List<PageData> varList = coursebaseService.listById(pd);
		return varList;
	}
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(String COURSE_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("COURSE_ID", COURSE_ID);
		pd = coursebaseService.findById(pd);	//根据ID读取
		List<CourseTree> treeList = coursebaseService.listByParentId("0");
		mv.setViewName("coursemanagement/coursebase/coursebase_edit");
		mv.addObject("msg", "edit");
		mv.addObject("treeList",treeList);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除CourseBase");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			coursebaseService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 *  图片上传
	 * @param pic
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadPic")
	public void uploadPic(MultipartFile pic,HttpServletResponse response) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"图片上传CourseBase");
		//TODO 文件上传路径
		String picPath = PathUtil.getClasspath()+Const.FILEPATHIMG;
		String picName = FileUpload.fileUp(pic,picPath,this.get32UUID());
		System.out.println(picPath+picName);
		JSONObject json = new JSONObject();
		//回调文件路径
		json.put("path",Const.FILEPATHIMG+picName); 
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json.toString());
	}
		@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
