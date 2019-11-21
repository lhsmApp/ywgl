package com.fh.controller.exam.testplan;

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
import com.fh.controller.base.BaseController;
import com.fh.controller.common.BillnumUtil;
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
import com.fh.util.enums.ExamBillNum;

import net.sf.json.JSONArray;

import com.fh.service.billnum.BillNumManager;
import com.fh.service.exam.testpaper.TestPaperManager;
import com.fh.service.exam.testplan.TestPlanManager;
import com.fh.service.trainBase.TrainDepartManager;
import com.fh.service.trainBase.TrainStudentManager;

/** 
 * 说明：testplan
 * 创建人：jiachao
 * 创建时间：2019-11-08
 */
@Controller
@RequestMapping(value="/testplan")
public class TestPlanController extends BaseController {
	
	String menuUrl = "testplan/list.do"; //菜单地址(权限用)
	@Resource(name="testplanService")
	private TestPlanManager testplanService;
	
	@Resource(name="testpaperService")
	private TestPaperManager testpaperService;
	
	@Resource(name="trainstudentService")
	private TrainStudentManager trainstudentService;

	@Resource(name = "billnumService")
	private BillNumManager billNumService;
	
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
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("CREATE_USER", user.getUSER_ID());//创建人
		pd.put("STATE", "1");//状态
		pd.put("START_DATE", pd.getString("START_DATE").replace("-", ""));//开始日期
		pd.put("END_DATE", pd.getString("END_DATE").replace("-", ""));//结束日期
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		String nowDate = df.format(calendar.getTime());
		pd.put("CREATE_DATE",nowDate);
		if(null==pd.getString("TEST_PLAN_ID")||pd.getString("TEST_PLAN_ID").trim().equals("")){
			String testPlanId=BillnumUtil.getExamBillnum(billNumService, ExamBillNum.EXAM_PLAN);
			pd.put("TEST_PLAN_ID", testPlanId);	//主键
			String testPersonStr=pd.getString("TEST_PLAN_PERSONS");
			String [] trainPersons=testPersonStr.split(",");
			List<PageData> studentList=new ArrayList<PageData>();
			for(int i=0;i<trainPersons.length;i++){
				PageData p=new PageData();
				p.put("TEST_PLAN_ID", testPlanId);
				p.put("STUDENT_ID", trainPersons[i]);
				p.put("PLAN_TYPE", '1');//计划类型，1考试2培训
				studentList.add(p);
			}
			pd.put("varList", studentList);
			testplanService.save(pd);
			commonBase.setCode(0);
		}else{
			testplanService.edit(pd);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	/**显示选择的考试人员信息
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
	/**获取考试人员列表
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
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(trainDepartService.listAllTrainDepartToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));	
		mv.setViewName("exam/testplan/teststudent_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		
		return mv;
	}
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除TestPlan");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		testplanService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改TestPlan");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		testplanService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表TestPlan");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(null!=pd.getString("START_DATE")&&!"".equals(pd.getString("START_DATE"))){
			pd.put("START_DATE", pd.getString("START_DATE").replace("-", ""));
		}
		if(null!=pd.getString("END_DATE")&&!"".equals(pd.getString("END_DATE"))){
			pd.put("END_DATE", pd.getString("END_DATE").replace("-", ""));
		}
		page.setPd(pd);
		List<PageData>	varList = testplanService.list(page);	//列出TestPlan列表
		mv.setViewName("exam/testplan/testplan_list");
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
		pd.put("STATE", "1");
		mv.setViewName("exam/testplan/testplan_Create");
		List <PageData> paperList=testpaperService.listAll(pd);
		mv.addObject("msg", "save");
		mv.addObject("paperList", paperList);
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
		List <PageData> paperList=testpaperService.listAll(pd);
		pd = testplanService.findById(pd);	//根据ID读取
		String[] persons =pd.getString("TEST_PLAN_PERSONS").split(",");
		List<PageData> studentList = new ArrayList<PageData>();
		studentList=trainstudentService.listChoiceStudent(persons);
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
		mv.setViewName("exam/testplan/testplan_Create");
		mv.addObject("studentList", studentList);
		mv.addObject("paperList", paperList);
		pd.put("TEST_PERSONSNAME", nameStr);
		pd.put("TEST_PERSONSCODE", codeStr);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除TestPlan");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			testplanService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出TestPlan到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = testplanService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("TEST_PLAN_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("TEST_PLAN_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("START_DATE"));	    //3
			vpd.put("var4", varOList.get(i).getString("END_DATE"));	    //4
			vpd.put("var5", varOList.get(i).get("COURSE_TYPE_ID").toString());	//5
			vpd.put("var6", varOList.get(i).getString("TEST_PAPER_ID"));	    //6
			vpd.put("var7", varOList.get(i).getString("TEST_PLAN_MEMO"));	    //7
			vpd.put("var8", varOList.get(i).getString("TEST_PLAN_PERSONS"));	    //8
			vpd.put("var9", varOList.get(i).getString("STATE"));	    //9
			vpd.put("var10", varOList.get(i).getString("CREATE_USER"));	    //10
			vpd.put("var11", varOList.get(i).getString("CREATE_DATE"));	    //11
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
