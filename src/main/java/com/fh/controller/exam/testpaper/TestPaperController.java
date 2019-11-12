package com.fh.controller.exam.testpaper;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fh.service.exam.testmain.TestMainManager;
import com.fh.service.exam.testpaper.TestPaperManager;

/** 
 * 说明：testpaper
 * 创建人：jiachao
 * 创建时间：2019-11-06
 */
@Controller
@RequestMapping(value="/testpaper")
public class TestPaperController extends BaseController {
	
	String menuUrl = "testpaper/list.do"; //菜单地址(权限用)
	@Resource(name="testpaperService")
	private TestPaperManager testpaperService;
	
	@Resource(name="testmainService")
	private TestMainManager testmainService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增TestPaper");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TESTPAPER_ID", this.get32UUID());	//主键
		testpaperService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除TestPaper");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		testpaperService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改TestPaper");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		testpaperService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表TestPaper");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = testpaperService.list(page);	//列出TestPaper列表
		mv.setViewName("exam/testpaper/testpaper_list");
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
		mv.setViewName("exam/testpaper/testpaper_edit");
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
		pd = testpaperService.findById(pd);	//根据ID读取
		mv.setViewName("exam/testpaper/testpaper_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除TestPaper");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			testpaperService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出TestPaper到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = testpaperService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("TEST_PAPER_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("TEST_PAPER_TITLE"));	    //2
			vpd.put("var3", varOList.get(i).get("COURSE_TYPE_ID").toString());	//3
			vpd.put("var4", varOList.get(i).getString("TEST_PAPER_TYPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("TEST_PAPER_DIFFICULTY"));	    //5
			vpd.put("var6", varOList.get(i).getString("TEST_QUESTION_SOURCE"));	    //6
			vpd.put("var7", varOList.get(i).get("TEST_QUESTION_NUM").toString());	//7
			vpd.put("var8", varOList.get(i).getString("TEST_PAPER_SCORE"));	    //8
			vpd.put("var9", varOList.get(i).get("ANSWER_TIME").toString());	//9
			vpd.put("var10", varOList.get(i).get("TEST_CHANCE").toString());	//10
			vpd.put("var11", varOList.get(i).getString("QUALIFIED_SCORE"));	    //11
			vpd.put("var12", varOList.get(i).getString("STATE"));	    //12
			vpd.put("var13", varOList.get(i).getString("CREATE_USER"));	    //13
			vpd.put("var14", varOList.get(i).getString("CREATE_DATE"));	    //14
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
	/**進入答題页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goExam")
	public ModelAndView goExam(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TEST_PAPER_ID","EP20191107144218001");
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String userId=user.getUSER_ID();
		pd.put("TEST_USER", userId);
		pd.put("TEST_PLAN_ID", "EP20191111113555283");//考试计划
		//根据考试计划、试卷ID和考试人判断该用户是否已参加过考试
		List<PageData> userExamList=testmainService.listByUser(pd);
		if(null!=userExamList&&userExamList.size()>0){
			mv.setViewName("exam/testonline/Warning");
			mv.addObject("pd", pd);
			return mv;
		}
		page.setPd(pd);
		List<PageData>	varList = testpaperService.listExam(page);	//列出TestPaper列表
		List<PageData>	answerList = testpaperService.listAnswer(page);	//列出TestPaper列表
		double score= Double.parseDouble(varList.get(0).get("TEST_PAPER_SCORE").toString());
//		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
//	    String userId=user.getUSER_ID();
		mv.setViewName("exam/testonline/examOnline");
		mv.addObject("varList", varList);
		mv.addObject("answerList", answerList);
		pd.put("TEST_PAPER_SCORE", score);
		pd.put("TEST_QUESTION_NUM", varList.size());
		pd.put("TEST_USER", userId);
		pd.put("EXAM_TIME", DateUtils.getCurrentTime());
		pd.put("ANSWER_TIME", Integer.parseInt(varList.get(0).get("ANSWER_TIME").toString()));
		mv.addObject("pd", pd);
		return mv;
	}	
	/**计算考试结果
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/examResult")
	public @ResponseBody PageData examResult(Page page)throws Exception{	
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String userId=user.getUSER_ID();
		pd.put("TEST_USER", userId);
		pd.put("TEST_PLAN_ID", "EP20191111113555283");//考试计划
		//根据考试计划、试卷ID和考试人判断该用户是否已参加过考试
		List<PageData> userExamList=testmainService.listByUser(pd);
		if(null!=userExamList&&userExamList.size()>0){
			pd.put("msg", "该用户已参加过考试，不允许再考！");
			return pd;
		}
		List<PageData> answerList=new ArrayList<PageData>();//答案集合		 
		JSONObject jsonobject = JSONObject.fromObject(pd.getString("result"));
		//考试分数
		int score=0;
		page.setPd(pd);
		//获取该卷纸试题相关信息,包括答案得分等
		List<PageData>	varList = testpaperService.listExam(page);	//列出TestPaper列表
		for(PageData p:varList){	
			//单选
			if(p.getString("TEST_QUESTION_TYPE").equals("1")){
				PageData pdAnswer = new PageData();//答案
				String rightAnswer=p.getString("TEST_QUESTION_ANSWER");
				//判断该单选题题是否作答
				if (jsonobject.has(p.getString("TEST_QUESTION_ID"))){
					String answer=jsonobject.getString(p.getString("TEST_QUESTION_ID"));	
					pdAnswer.put("TEST_QUESTION_ID", p.getString("TEST_QUESTION_ID"));
					pdAnswer.put("TEST_ANSWER", answer);
					pdAnswer.put("TEST_CORRECT_ANSWER", rightAnswer);
					if(answer.equals(rightAnswer)){
						score=score+Integer.parseInt(p.get("TEST_QUESTION_SCORE").toString());
						}
				}else{
					//未作答，答案为空
					pdAnswer.put("TEST_QUESTION_ID", p.getString("TEST_QUESTION_ID"));
					pdAnswer.put("TEST_ANSWER", "");
					pdAnswer.put("TEST_CORRECT_ANSWER", rightAnswer);
				}
				answerList.add(pdAnswer);
			}else if(p.getString("TEST_QUESTION_TYPE").equals("2")){
				String rightAnswer=p.getString("TEST_QUESTION_ANSWER");
				PageData pdAnswer = new PageData();//答案
				//判断该多选题题是否作答
				if (jsonobject.has(p.getString("TEST_QUESTION_ID"))){
					String answer=jsonobject.getString(p.getString("TEST_QUESTION_ID"));
			        if (answer.indexOf("#&#")!=-1){
			        	answer=answer.replaceAll("#&#", ",");
			        }
					pdAnswer.put("TEST_QUESTION_ID", p.getString("TEST_QUESTION_ID"));
					pdAnswer.put("TEST_ANSWER", answer);
					pdAnswer.put("TEST_CORRECT_ANSWER", rightAnswer);
					//将多选题答案进行排序并比较
					byte[] b1 = answer.getBytes();
					byte[] b2 = rightAnswer.getBytes();
					Arrays.sort(b1);
					Arrays.sort(b2);
					answer = new String(b1);
					rightAnswer = new String(b2);
					if (answer.equals(rightAnswer)) {
						score=score+Integer.parseInt(p.get("TEST_QUESTION_SCORE").toString());
					}
				}else{
					pdAnswer.put("TEST_QUESTION_ID", p.getString("TEST_QUESTION_ID"));
					pdAnswer.put("TEST_ANSWER", "");
					pdAnswer.put("TEST_CORRECT_ANSWER", rightAnswer);
				}
				answerList.add(pdAnswer);
			}			
		}
		//纸卷分数
		double paperScore= Double.parseDouble(varList.get(0).get("TEST_PAPER_SCORE").toString());
		//合格分数
		double qualifiedScore= Double.parseDouble(varList.get(0).get("QUALIFIED_SCORE").toString());
		pd.put("TEST_TIME",DateUtils.getCurrentTime() );//考试时间
		pd.put("TEST_PAPER_SCORE", paperScore);
		pd.put("QUALIFIED_SCORE", qualifiedScore);
		pd.put("TEST_SCORE", score);//考试分分数
		if(score>=qualifiedScore){
			pd.put("IF_QUALIFIED", '1');//是否合格
		}else{
			pd.put("IF_QUALIFIED", '0');//是否合格
		}
		pd.put("answerList", answerList);
		pd.put("TEST_COUNT", 1);
		pd.put("CREATE_USER", userId);//创建人
		pd.put("TEST_QUESTION_NUM", varList.size());//题目数
		pd.put("CREATE_DATE", DateUtils.getCurrentTime().substring(0, 10));//创建日期
		//将考试成绩存入考试主表和明细表
		testmainService.saveExamResult(pd);
		return pd;
	}	
}
