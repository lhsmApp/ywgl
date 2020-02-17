package com.fh.controller.trainplan.trainplan;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.fh.controller.common.BillnumUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.BillNumType;
import com.fh.util.enums.ExamBillNum;
import com.fh.util.Jurisdiction;

import net.sf.json.JSONArray;

import com.fh.service.billnum.BillNumManager;
import com.fh.service.coursemanagement.coursebase.CourseBaseManager;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.trainBase.CourseTypeManager;
import com.fh.service.trainBase.TrainDepartManager;
import com.fh.service.trainBase.TrainStudentManager;
import com.fh.service.trainplan.trainplan.TrainPlanManager;

/** 
 * 说明：trainplan
 * 创建人：liqian
 * 创建时间：2019-11-01
 */
@Controller
@RequestMapping(value="/trainplan")
public class TrainPlanController extends BaseController {
	
	String menuUrl = "trainplan/list.do"; //菜单地址(权限用)
	@Resource(name="trainplanService")
	private TrainPlanManager trainplanService;
	
	@Resource(name="coursetypeService")
	private CourseTypeManager coursetypeService;
	
	@Resource(name="trainstudentService")
	private TrainStudentManager trainstudentService;

	@Resource(name = "billnumService")
	private BillNumManager billNumService;
	
	@Resource(name="coursebaseService")
	private CourseBaseManager coursebaseService;
	
	@Resource(name="traindepartService")
	private TrainDepartManager trainDepartService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public  @ResponseBody CommonBase save() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STATE", "1");
		pd.put("START_DATE", pd.getString("START_DATE").replace("-", ""));//开始日期
		pd.put("END_DATE", pd.getString("END_DATE").replace("-", ""));//结束日期
		if(null==pd.getString("TRAIN_PLAN_ID")||pd.getString("TRAIN_PLAN_ID").trim().equals("")){
			String billCode=BillnumUtil.getExamBillnum(billNumService, ExamBillNum.TRAIN_PLAN);
			pd.put("TRAIN_PLAN_ID", billCode);	//主键
			pd.put("COURSE_ID",pd.getString("COURSE_CODE"));//课程ID
//			pd.put("CREATE_DATE", DateUtils.getCurrentDate());
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();
			String nowDate = df.format(calendar.getTime());
			pd.put("CREATE_DATE",nowDate);
			String trainPersonStr=pd.getString("TRAIN_PLAN_PERSONS");
			String [] trainPersons=trainPersonStr.split(",");
			List<PageData> studentList=new ArrayList<PageData>();
			for(int i=0;i<trainPersons.length;i++){
				PageData p=new PageData();
				p.put("TRAIN_PLAN_ID", billCode);
				p.put("STUDENT_ID", trainPersons[i]);
				p.put("PLAN_TYPE", '2');//计划类型，1考试2培训
				studentList.add(p);
			}
			pd.put("varList", studentList);
			trainplanService.save(pd);
			commonBase.setCode(0);
		}else{
			//pd.put("UPDATE_DATE", DateUtils.getCurrentTime());
			pd.put("COURSE_ID",pd.getString("COURSE_CODE"));//课程ID
			trainplanService.edit(pd);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	/**显示选择的培训人员信息
	 * @param page
	 * @throws Exceptionz
	 */
	@RequestMapping(value="/getChoiceStudent")
	public @ResponseBody List<PageData> getChoiceStudent() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		String studentCode=pd.getString("sturentStr");
		String[] studentArry=studentCode.split(",");		
		List<PageData> varList = trainstudentService.listChoiceStudent(studentArry);		
		return varList;
	}
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除TrainPlan");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		trainplanService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改TrainPlan");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		trainplanService.edit(pd);
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
		//logBefore(logger, Jurisdiction.getUsername()+"列表TrainPlan");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String startDate = pd.getString("START_DATE");               //关键词检索条件
        if(null != startDate && !"".equals(startDate)){
            pd.put("START_DATE", startDate.replace("-",""));
        }
        String endDate = pd.getString("END_DATE");             //关键词检索条件
        if(null != endDate && !"".equals(endDate)){
            pd.put("END_DATE", endDate.replace("-",""));
        }
		page.setPd(pd);
		List<PageData>	varList = trainplanService.list(page);	//列出TrainPlan列表		
		mv.setViewName("trainplan/trainplan/trainplan_list");
		for (PageData i : varList) {
		    i.put("CREATE_DATE", DateFormatUtils.formatString((String) i.get("CREATE_DATE")));//格式化时间格式
		    i.put("START_DATE", DateFormatUtils.formatString((String) i.get("START_DATE")));//格式化时间格式
            i.put("END_DATE", DateFormatUtils.formatString((String) i.get("END_DATE")));//格式化时间格式
        }
		//mv.addObject("varList", JSON.toJSONString(varList));
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		mv.setViewName("trainplan/trainplan/trainplan_Create");
		List<PageData> courseTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0",courseTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
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
		pd = trainplanService.findById(pd);	//根据ID读取
		String[] persons =pd.getString("TRAIN_PLAN_PERSONS").split(",");
		List<PageData> studentList = new ArrayList<PageData>();
		studentList=trainstudentService.listChoiceStudent(persons);
		List<PageData> courseTypePdList = new ArrayList<PageData>();//课程类型集合
		List<PageData> courseList = new ArrayList<PageData>();//课程集合
		String codeStr="";
		String nameStr="";
		for(PageData p:studentList){
			if("".equals(codeStr)) {
			  codeStr += p.get("STUDENT_ID");
			  nameStr += p.getString("STUDENT_NAME");
  		  }else {
  			codeStr += "," +p.get("STUDENT_ID");
  			nameStr += "," +p.getString("STUDENT_NAME");
  		  }
		}
		pd.put("COURSE_TYPE", pd.get("COURSE_TYPE_ID").toString());
		PageData pdCourseType = new PageData();
		pdCourseType = coursetypeService.findById(pd);
		courseList=getCourse();
		if(pdCourseType!=null)
			mv.addObject("coursetype", pdCourseType.getString("COURSE_TYPE_NAME"));
		else
			mv.addObject("coursetype", null);
		JSONArray arr = JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0",courseTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		mv.setViewName("trainplan/trainplan/trainplan_Create");

		mv.addObject("studentList", studentList);
		mv.addObject("courseList", courseList);
		pd.put("TRAIN_PERSONSNAME", nameStr);
		pd.put("TRAIN_PERSONSCODE", codeStr);
		mv.addObject("pd", pd);
		return mv;
	}	
	/**根据课程分类获取课程信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getCourse")
	public  List<PageData> getCourse()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		pd.put("STATE",'1');
		List<PageData> varList = coursebaseService.listById(pd);
		return varList;
	}
	/**获取培训人员列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listStudent")
	public ModelAndView listStudent(Page page) throws Exception{		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = trainstudentService.list(page);	//列出TrainStudent列表
		mv.setViewName("trainplan/trainplan/trainstudent_list");
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(trainDepartService.listAllTrainDepartToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));		
		mv.addObject("varList", varList);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除TrainPlan");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			trainplanService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出TrainPlan到excel");
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
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		dataMap.put("titles", titles);
		List<PageData> varOList = trainplanService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("TRAIN_PLAN_ID").toString());	//1
			vpd.put("var2", varOList.get(i).get("TRAIN_PLAN_NAME").toString());	//2
			vpd.put("var3", varOList.get(i).getString("START_DATE"));	    //3
			vpd.put("var4", varOList.get(i).getString("END_DATE"));	    //4
			vpd.put("var5", varOList.get(i).get("COURSE_TYPE_ID").toString());	//5
			vpd.put("var6", varOList.get(i).get("COURSE_ID").toString());	//6
			vpd.put("var7", varOList.get(i).getString("TRAIN_PLAN_MEMO"));	    //7
			vpd.put("var8", varOList.get(i).getString("TRAIN_PLAN_PERSONS"));	    //8
			vpd.put("var9", varOList.get(i).getString("STATE"));	    //9
			vpd.put("var10", varOList.get(i).getString("CREATE_USER"));	    //10
			vpd.put("var11", varOList.get(i).getString("CREATE_DATE"));	    //11
			vpd.put("var12", varOList.get(i).getString("CUST1"));	    //12
			vpd.put("var13", varOList.get(i).getString("CUST2"));	    //13
			vpd.put("var14", varOList.get(i).getString("CUST3"));	    //14
			vpd.put("var15", varOList.get(i).get("CUST4").toString());	//15
			vpd.put("var16", varOList.get(i).get("CUST5").toString());	//16
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
