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
import java.util.Random;

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
import com.fh.service.billnum.BillNumManager;
import com.fh.service.exam.testmain.TestMainManager;
import com.fh.service.exam.testpaper.TestPaperManager;
import com.fh.service.testquestion.testquestion.TestQuestionManager;
import com.fh.service.trainBase.CourseTypeManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.ExamBillNum;
import com.fh.util.enums.PaperType;
import com.fh.util.enums.QuestionDifficulty;
import com.fh.util.enums.QuestionType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 说明：试卷信息处理类
 * 创建人：xinyuLo
 * 创建时间：2019-11-06
 */
@Controller
@RequestMapping(value="/testpaper")
public class TestPaperController extends BaseController {
	
	String menuUrl = "testpaper/list.do"; //菜单地址(权限用)
	@Resource(name="testpaperService")
	private TestPaperManager testpaperService;
	@Resource(name="coursetypeService")
	private CourseTypeManager coursetypeService;
	@Resource(name="testquestionService")
	private TestQuestionManager testquestionService;
	@Resource(name="testmainService")
	private TestMainManager testmainService;
	@Resource(name = "billnumService")
	private BillNumManager billNumService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public CommonBase save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增TestPaper");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		CommonBase commonBase = new CommonBase();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		String listData = pd.getString("listData");
		String paperId = BillnumUtil.getExamBillnum(billNumService, ExamBillNum.EXAM_PAPER);
		JSONArray array = JSONArray.fromObject(listData);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		
		PageData paperData = listTransferData.get(0); //所有试卷基本信息
		paperData.put("STATE", "1");
		paperData.put("CREATE_USER", user.getUSER_ID());
		paperData.put("CREATE_DATE", DateUtils.getCurrentDate());
		paperData.put("TEST_PAPER_ID",paperId);
		testpaperService.save(paperData);
		
		//向试卷明细表插入数据
		String questionIds = paperData.getString("questionIds");
		if(StringUtil.isNotEmpty(questionIds)) {
			String[] ids = questionIds.split(",");
			PageData paperDetail = new PageData();
			for (int i = 0; i < ids.length; i++) {
				paperDetail.put("TEST_PAPER_ID",paperId);
				paperDetail.put("TEST_QUESTION_ID",ids[i]);
				paperDetail.put("DISP_ORDER",i);
				testpaperService.savePaperDetail(paperDetail);
			}
		}
		
		//判断是否为随机试卷
		String paperType = paperData.getString("TEST_PAPER_TYPE");
		if(StringUtil.isNotEmpty(paperType) && paperType.equals("2")) {
			//保存以后删掉该集合内的基本信息进行添加查询条件
			listTransferData.remove(0);
			for (PageData pageData : listTransferData) {
				pageData.put("TEST_PAPER_ID",paperId);
				pageData.put("CREATE_USER", user.getUSER_ID());
				pageData.put("CREATE_DATE", DateUtils.getCurrentDate());
				testpaperService.savePaperParam(pageData);
			}
		}
		
		commonBase.setCode(1);
		return commonBase;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除TestPaper");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
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
	@ResponseBody
	public CommonBase edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改TestPaper");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		CommonBase commonBase = new CommonBase();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		String listData = pd.getString("listData");
		JSONArray array = JSONArray.fromObject(listData);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		PageData paperData = listTransferData.get(0); //所有试卷基本信息
		testpaperService.edit(paperData);
		String paperId = paperData.getString("TEST_PAPER_ID");
		//向试卷明细表插入数据,全部删除重新插入
		testpaperService.deletePaperDetail(paperData);
		String questionIds = paperData.getString("questionIds");
		if(StringUtil.isNotEmpty(questionIds)) {
			String[] ids = questionIds.split(",");
			PageData paperDetail = new PageData();
			for (int i = 0; i < ids.length; i++) {
				paperDetail.put("TEST_PAPER_ID",paperId);
				paperDetail.put("TEST_QUESTION_ID",ids[i]);
				paperDetail.put("DISP_ORDER",i);
				testpaperService.savePaperDetail(paperDetail);
			}
		}
		
		//判断是否为随机试卷
		String paperType = paperData.getString("TEST_PAPER_TYPE");
		if(StringUtil.isNotEmpty(paperType) && paperType.equals("2")) {
			testpaperService.deletePaperParam(paperData);
			//保存以后删掉该集合内的基本信息进行添加查询条件
			listTransferData.remove(0);
			for (PageData pageData : listTransferData) {
				pageData.put("TEST_PAPER_ID",paperId);
				pageData.put("CREATE_USER", user.getUSER_ID());
				pageData.put("CREATE_DATE", DateUtils.getCurrentDate());
				testpaperService.savePaperParam(pageData);
			}
		}
		return commonBase;
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
		for (PageData pageData : varList) {
			String testPaperType = pageData.getString("TEST_PAPER_TYPE");
			String testQuestionDifficulty = pageData.getString("TEST_PAPER_DIFFICULTY");
			
			String enumDifficulty = QuestionDifficulty.getValueByKey(testQuestionDifficulty);
			String enmuType = PaperType.getValueByKey(testPaperType);
			
			pageData.put("TEST_PAPER_TYPE", enmuType);
			pageData.put("TEST_PAPER_DIFFICULTY", enumDifficulty);
		}
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
		List<PageData> courseTypePdList = new ArrayList<PageData>(); 
		JSONArray arr = JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0",courseTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
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
		String questionIds = "";
		//树形下拉框
		List<PageData> courseTypePdList = new ArrayList<PageData>(); 
		JSONArray arr = JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0",courseTypePdList));
		//随机条件
		List<PageData> listParam = testpaperService.listParamById(pd);
		//通过id查询该试卷所选试题
		List<PageData> listPaperDetail = testpaperService.listPaperDetail(pd);
		for (PageData pageData : listPaperDetail) {
			if(questionIds.equals("")) questionIds += pageData.getString("TEST_QUESTION_ID");
			else questionIds += "," + pageData.getString("TEST_QUESTION_ID");
		}
		
		mv.addObject("questionIds",questionIds);
		mv.addObject("listParam",listParam);
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
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
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
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
	
	/**去新增试题页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddQuestion")
	public ModelAndView goAddQuestion(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = testquestionService.list(page);
		//进行数据转换
		for (PageData pageData : varList) {
			String testQuestionType = pageData.getString("TEST_QUESTION_TYPE");
			String testQuestionDifficulty = pageData.getString("TEST_QUESTION_DIFFICULTY");
			String enmuType = QuestionType.getValueByKey(testQuestionType);
			String enumDifficulty = QuestionDifficulty.getValueByKey(testQuestionDifficulty);
			pageData.put("TEST_QUESTION_TYPE", enmuType);
			pageData.put("TEST_QUESTION_DIFFICULTY", enumDifficulty);
		}
		mv.addObject("varList",varList);
		mv.setViewName("exam/testpaper/testpaper_add");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	  * 通过id查询试题列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listByQuestionId")
	@ResponseBody
	public CommonBase listByQuestionId()throws Exception{
		CommonBase commonBase = new CommonBase();
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ids = pd.getString("ids");
		String[] TEST_QUESTION_ID = StringUtil.StrList(ids);
		for (int i = 0; i < TEST_QUESTION_ID.length; i++) {
			pd.put("TEST_QUESTION_ID", TEST_QUESTION_ID[i]);
			PageData pageData = testquestionService.findById(pd);
			//进行数据转换
			String testQuestionType = pageData.getString("TEST_QUESTION_TYPE");
			String testQuestionDifficulty = pageData.getString("TEST_QUESTION_DIFFICULTY");
			String enmuType = QuestionType.getValueByKey(testQuestionType);
			String enumDifficulty = QuestionDifficulty.getValueByKey(testQuestionDifficulty);
			pageData.put("TEST_QUESTION_TYPE", enmuType);
			pageData.put("TEST_QUESTION_DIFFICULTY", enumDifficulty);
			varList.add(pageData);
		}
		//转换成json数据
		JSONArray jsonArr = JSONArray.fromObject(varList);
		commonBase.setCode(1);
		commonBase.setMessage(StringUtil.toString(jsonArr,""));
		return commonBase;
	}
	
	/**
	 * 随机生成试题
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/radomPaper")
	@ResponseBody
	public CommonBase radomPaper()throws Exception{
		CommonBase commonBase = new CommonBase();
		List<PageData> varList = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//进行数据初始化
		long baseNum = 0; //数据库实际题数
		long num = 0;	  //输入题数
		int index = 0;    //随机试题索引
		//先进行数据验证
		String listData = pd.getString("listData");
		JSONArray array = JSONArray.fromObject(listData);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		//先查询是否有该分数试题
		for (PageData pageData : listTransferData) {
			//判断题数
			PageData countQuestion = testquestionService.countQuestion(pageData);
			baseNum = (long) countQuestion.get("number");
			num = Long.parseLong(pageData.getString("TEST_QUESTION_NUM"));

			if (baseNum == 0) {
				commonBase.setCode(0);
				commonBase.setMessage("当前暂无该条件试题!!!请重新配置生成条件!");
				return	commonBase;
			}
			if(num > baseNum) {//如果为空则返回
				commonBase.setCode(0);
				commonBase.setMessage("当前题库数量不足!请重新配置生成条件!!!");
				return	commonBase;
			}
			//校验通过随机试题
			List<PageData> randomData = testquestionService.randomList(pageData);
			Random random = new Random();
			for (long i = 0; i < num; i++) {
				index = random.nextInt(randomData.size());
				pd  = randomData.get(index);
				//进行数据转换
				String testQuestionType = pd.getString("TEST_QUESTION_TYPE");
				String testQuestionDifficulty = pd.getString("TEST_QUESTION_DIFFICULTY");
				String enmuType = QuestionType.getValueByKey(testQuestionType);
				String enumDifficulty = QuestionDifficulty.getValueByKey(testQuestionDifficulty);
				pd.put("TEST_QUESTION_TYPE", enmuType);
				pd.put("TEST_QUESTION_DIFFICULTY", enumDifficulty);
				varList.add(pd);
				//添加后去除此id防止重复
				randomData.remove(index);
				
			}
			
		}
		//转换成json数据
		JSONArray jsonArr = JSONArray.fromObject(varList);
		commonBase.setCode(1);
		commonBase.setMessage(StringUtil.toString(jsonArr,""));
		return commonBase;
	}	
	
	/**
	  * 通过id查询所有试题数量
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/countQuestion")
	@ResponseBody
	public CommonBase countQuestion()throws Exception{
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		String str = "*目前该分类有";
		/*
		  *单选题1、多选题2、简答题3 
		  * 简单1、中等2、困难3
		 */
		for (int i = 1; i <= 3; i++) {
			pd.put("TEST_QUESTION_TYPE",i);
			switch (i) {
				case 1:
					str += "单选题 :";
					break;
				case 2:
					str += "多选题 :";
					break;
				case 3:
					str += "简答题 :";
					break;
			}
			for (int j = 1; j <=3; j++) {
				pd.put("TEST_QUESTION_DIFFICULTY",j);
				PageData pageData = testquestionService.countQuestion(pd);
				switch (j) {
					case 1:
						str += "( 简单:";
						break;
					case 2:
						str += "中等 :";
						break;
					case 3:
						str += "困难 :";
						break;
				} 
				str += pageData.get("number").toString();
				str +="题 ";
			}
			str+=" ) ";
		}
		commonBase.setMessage(str);
		return commonBase;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	/**进入答题页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goExam")
	public ModelAndView goExam(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("TEST_PAPER_ID","EP20191107144218001");
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String userId=user.getUSER_ID();
		pd.put("TEST_USER", userId);
		//pd.put("TEST_PLAN_ID", "EP20191107150901187");//考试计划
		//根据考试计划、试卷ID和考试人判断该用户是否已参加过考试
//		List<PageData> userExamList=testmainService.listByUser(pd);
//		if(null!=userExamList&&userExamList.size()>0){
//			mv.setViewName("exam/testonline/Warning");
//			mv.addObject("pd", pd);
//			return mv;
//		}
		page.setPd(pd);
		List<PageData>	varList = testpaperService.listExam(page);	//列出TestPaper列表
		List<PageData>	answerList = testpaperService.listAnswer(page);	//列出TestPaper列表
		double score=0;
		if(null!=varList&&varList.size()>0){
			 score= Double.parseDouble(varList.get(0).get("TEST_PAPER_SCORE").toString());
		}
		if(null!=answerList&&answerList.size()>0){
			pd.put("ANSWER_TIME", Integer.parseInt(varList.get(0).get("ANSWER_TIME").toString()));
		}
		mv.setViewName("exam/testonline/examOnline");
		mv.addObject("varList", varList);
		mv.addObject("answerList", answerList);
		pd.put("TEST_PAPER_SCORE", score);
		pd.put("TEST_QUESTION_NUM", varList.size());
		pd.put("TEST_USER", userId);
		pd.put("EXAM_TIME", DateUtils.getCurrentTime());	
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
		List<PageData> answerList=new ArrayList<PageData>();//答案集合		 
		JSONObject jsonobject = JSONObject.fromObject(pd.getString("result"));
		//根据考试计划、试卷ID和考试人判断该用户是否已参加过考试
		List<PageData> userExamList=testmainService.listByUser(pd);
		if(null!=userExamList&&userExamList.size()>0){
			pd.put("msg","该用户已参加过考试！");
			return pd;
		}
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
				//判断该多选题是否作答
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
			}else{
				String rightAnswer=p.getString("TEST_QUESTION_ANSWER");
				PageData pdAnswer = new PageData();//答案
				//判断该简答题是否作答
				if (jsonobject.has(p.getString("TEST_QUESTION_ID"))){
					String answer=jsonobject.getString(p.getString("TEST_QUESTION_ID"));			       
					pdAnswer.put("TEST_QUESTION_ID", p.getString("TEST_QUESTION_ID"));
					pdAnswer.put("TEST_ANSWER", answer);
					pdAnswer.put("TEST_CORRECT_ANSWER", rightAnswer);					
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
		pd.put("STATE", "1");//考试完成，考试主表状态置为1
		pd.put("answerList", answerList);
		pd.put("TEST_COUNT", 1);
		pd.put("CREATE_USER", userId);//创建人
		pd.put("TEST_QUESTION_NUM", varList.size());//题目数
		pd.put("CREATE_DATE", DateUtils.getCurrentTime().substring(0, 10));//创建日期
		//将考试成绩存入考试主表和明细表
		testmainService.saveExamResult(pd);
		return pd;
	}	
	/**试卷回顾
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goReviewExam")
	public ModelAndView goReviewExam(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String userId=user.getUSER_ID();
		pd.put("TEST_USER", userId);
		page.setPd(pd);
		List<PageData>	varList = testpaperService.listExamHistory(page);	//列出TestPaper列表
		List<PageData>	answerList = testpaperService.listAnswer(page);	//列出TestPaper列表
		double score=0;
		String if_qualified="";
		if(null!=varList&&varList.size()>0){
			 score= Double.parseDouble(varList.get(0).get("TEST_SCORE").toString());
			 if_qualified=varList.get(0).get("IF_QUALIFIED").toString();
		}
		mv.setViewName("exam/testonline/examReview");
		mv.addObject("varList", varList);
		mv.addObject("answerList", answerList);
		pd.put("TEST_SCORE", score);//考试分数	
		if(if_qualified.equals("1")){//是否合格
			pd.put("IF_QUALIFIED", "考试合格");
		}else{
			pd.put("IF_QUALIFIED", "考试成绩不合格");
		}
		
		mv.addObject("pd", pd);
		return mv;		
	}	
}
