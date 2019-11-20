package com.fh.controller.coursemanagement.coursedetail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.fh.entity.coursebase.CourseDetail;
import com.fh.entity.system.User;
import com.fh.service.coursemanagement.coursedetail.CourseDetailManager;
import com.fh.util.Const;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;

import net.sf.json.JSONArray;

/** 
 * 说明：课程详细信息处理类
 * 创建人：xinyuLo
 * 创建时间：2019-10-28
 */
@Controller
@RequestMapping(value="/coursedetail")
public class CourseDetailController extends BaseController {
	
	String menuUrl = "coursedetail/list.do"; //菜单地址(权限用)
	@Resource(name="coursedetailService")
	private CourseDetailManager coursedetailService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表CourseDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CHAPTER_PARENT_ID", "0");
		List<PageData> varList = coursedetailService.listAll(pd); //列出CourseDetail列表
		for (PageData pageData : varList) {
			//通过父ID查询子id获取小节数
			Integer parentId = (int) pageData.get("CHAPTER_ID");
			Integer count =  coursedetailService.countByParentId(parentId);
			pageData.put("count", count);
		}
		//查询所有小节
		page.setPd(pd);
		List<PageData> listSection = coursedetailService.list(page);
		mv.addObject("varList", varList);
		mv.addObject("COURSE_ID",pd.get("COURSE_ID"));
		mv.addObject("listSection", listSection);
		mv.setViewName("coursemanagement/coursedetail/coursedetail_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public CommonBase delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除CourseDetail");
		CommonBase commonBase = new CommonBase();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return commonBase;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		coursedetailService.delete(pd);
		commonBase.setCode(0);
		return commonBase;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改CourseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		coursedetailService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value = "/findsectionlist")
	@ResponseBody
	public CommonBase findSectionList()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改CourseDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONArray sectionList = JSONArray.fromObject(coursedetailService.listAll(pd));
		String json = sectionList.toString();
		commonBase.setMessage(json);
		return commonBase;
	}
	/**去新增列表显示页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增列表CourseDetail");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("parentId", "0");
		List<CourseDetail> varList = coursedetailService.listAdd(pd);
		mv.setViewName("coursemanagement/coursedetail/coursedetail_add");
		mv.addObject("msg", "save");
		mv.addObject("COURSE_ID",pd.get("COURSE_ID"));
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**去新增章节页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/gochapter")
	public ModelAndView goChapter()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("coursemanagement/coursedetail/coursechapter");
		mv.addObject("msg", "add");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去新增小节页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/gosection")
	public ModelAndView goSection()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("coursemanagement/coursedetail/coursesection");
		mv.addObject("msg", "add");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 新增章节
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/addchapter")
	public ModelAndView addChapter() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增章节CourseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STATE", "1");
		pd.put("CREATE_USER",user.getUSER_ID());
		pd.put("CHAPTER_PARENT_ID","0");
		pd.put("CREATE_TIME",DateUtils.getCurrentTime(DateFormatUtils.DATE_FORMAT1)); //YYYY-MM-dd
		coursedetailService.saveChapter(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 新增小节
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/addsection")
	public ModelAndView addSection() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增小节CourseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STATE", "1");
		pd.put("CREATE_USER",user.getUSER_ID());
		pd.put("CREATE_TIME",DateUtils.getCurrentTime(DateFormatUtils.DATE_FORMAT1)); //YYYY-MM-dd
		coursedetailService.saveSection(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改章节页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goeditchapter")
	public ModelAndView goEditChapter() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData Chapter = coursedetailService.findById(pd);
		mv.setViewName("coursemanagement/coursedetail/coursechapter");
		mv.addObject("msg", "edit");
		mv.addObject("pd", Chapter);
		return mv;
	}
	
	/**去修改小节页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goeditsection")
	public ModelAndView goEditSection()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData section = coursedetailService.findById(pd);
		mv.setViewName("coursemanagement/coursedetail/coursesection");
		mv.addObject("msg", "edit");
		mv.addObject("pd", section);
		return mv;
	}
	
	/**保存章节
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editchapter")
	public ModelAndView editChapter() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改章节CourseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		coursedetailService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**保存小节
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editsection")
	public ModelAndView editSection() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改小节CourseDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		coursedetailService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 *  视频上传
	 * @param pic
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadVideo")
	public void uploadPic(MultipartFile video,HttpServletResponse response) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"视频上传CourseBase");
		String videoPath = PathUtil.getClasspath()+Const.FILEPATHVIDEO;
		String videoName = FileUpload.fileUp(video,videoPath,this.get32UUID());
		System.out.println(videoPath+videoName);
		JSONObject json = new JSONObject();
		//回调文件路径
		json.put("path",Const.FILEPATHVIDEO+videoName); 
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json.toString());
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
