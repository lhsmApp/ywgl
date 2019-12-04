package com.fh.controller.testquestion.testquestion;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.fh.service.testquestion.testquestion.TestQuestionManager;
import com.fh.service.trainBase.CourseTypeManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.ExamBillNum;
import com.fh.util.enums.QuestionDifficulty;
import com.fh.util.enums.QuestionType;

import net.sf.json.JSONArray;

/** 
 * 说明：题库管理处理类
 * 创建人：xinyuLo
 * 创建时间：2019-10-31
 */
@Controller
@RequestMapping(value="/testquestion")
public class TestQuestionController extends BaseController {
	
	String menuUrl = "testquestion/list.do"; //菜单地址(权限用)
	@Resource(name="testquestionService")
	private TestQuestionManager testquestionService;
	@Resource(name="coursetypeService")
	private CourseTypeManager coursetypeService;
	@Resource(name = "billnumService")
	private BillNumManager billNumService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public CommonBase save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增TestQuestion");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		CommonBase commonBase = new CommonBase();
		User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		PageData detailData = new PageData();
		pd = this.getPageData();
		
		//初始化变量
		String listData = pd.getString("listData");
		String questionItem = null;
		String questionId = BillnumUtil.getExamBillnum(billNumService, ExamBillNum.EXAM_QUESTION);
		String questionType = null;
		JSONArray array = JSONArray.fromObject(listData);
		
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		for (PageData pageData : listTransferData) {
			questionType = pageData.getString("TEST_QUESTION_TYPE");
			detailData.put("TEST_QUESTION_ID",questionId);
			detailData.put("TEST_QUESTION_TITLE",pageData.get("TEST_QUESTION_TITLE"));
			detailData.put("TEST_QUESTION_TYPE",questionType);
			detailData.put("COURSE_TYPE_ID",pageData.get("COURSE_TYPE_ID"));
			detailData.put("TEST_QUESTION_SCORE",pageData.get("TEST_QUESTION_SCORE"));
			detailData.put("TEST_QUESTION_DIFFICULTY",pageData.get("TEST_QUESTION_DIFFICULTY"));
			detailData.put("TEST_ANSWER_NOTE",pageData.get("TEST_ANSWER_NOTE"));
			if( !questionType.equals("3")){
				detailData.put("TEST_QUESTION_ANSWER",pageData.get("ids"));
			}
			//获取出题人信息
			detailData.put("STATE", "1");
			detailData.put("CREATE_USER",user.getUSERNAME());
			detailData.put("CREATE_DATE",DateUtils.getCurrentTime(DateFormatUtils.DATE_FORMAT1)); //YYYY-MM-dd
			questionItem = pageData.getString("TEST_QUESTION_ITEM_CONTENT");
			testquestionService.save(detailData);
		}
		
		//判断简答题
		
			if(null != questionItem && questionItem.length() > 0) {
				this.saveDetail(questionId, questionItem, user.getUSERNAME());
			}
		
		return commonBase;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除TestQuestion");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		testquestionService.delete(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"修改TestQuestion");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		CommonBase commonBase = new CommonBase();
		User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		PageData detailData = new PageData();
		pd = this.getPageData();
		
		//初始化变量
		String questionItem = null;
		String questionId = null;
		String questionType = null;
		String listData = pd.getString("listData");
		JSONArray array = JSONArray.fromObject(listData);
		
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		for (PageData pageData : listTransferData) {
			questionItem = pageData.getString("TEST_QUESTION_ITEM_CONTENT");
			questionId = pageData.getString("TEST_QUESTION_ID");
			questionType = pageData.getString("TEST_QUESTION_TYPE");
			detailData.put("TEST_QUESTION_ID",questionId);
			detailData.put("COURSE_TYPE_ID",pageData.get("COURSE_TYPE_ID"));
			detailData.put("TEST_QUESTION_TITLE",pageData.get("TEST_QUESTION_TITLE"));
			detailData.put("TEST_QUESTION_TYPE",questionType);
			detailData.put("TEST_QUESTION_SCORE",pageData.get("TEST_QUESTION_SCORE"));
			detailData.put("TEST_QUESTION_DIFFICULTY",pageData.get("TEST_QUESTION_DIFFICULTY"));
			detailData.put("TEST_ANSWER_NOTE",pageData.get("TEST_ANSWER_NOTE"));
			if(!questionType.equals("3")) {
				detailData.put("TEST_QUESTION_ANSWER",pageData.get("ids"));
			}
			testquestionService.edit(detailData);
		}
		
		if(null != questionItem && questionItem.length() > 0) {
			testquestionService.deleteItem(questionId);
			this.saveDetail(questionId, questionItem, user.getUSERNAME());
		}
		
		return commonBase;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TestQuestion");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = testquestionService.list(page);	//列出TestQuestion列表
		//进行数据转换
		for (PageData pageData : varList) {
			String testQuestionType = pageData.getString("TEST_QUESTION_TYPE");
			String answerNote = pageData.getString("TEST_ANSWER_NOTE");
			String testQuestionDifficulty = pageData.getString("TEST_QUESTION_DIFFICULTY");
			String enmuType = QuestionType.getValueByKey(testQuestionType);
			String enumDifficulty = QuestionDifficulty.getValueByKey(testQuestionDifficulty);
			pageData.put("TEST_QUESTION_TYPE", enmuType);
			pageData.put("TEST_QUESTION_DIFFICULTY", enumDifficulty);
			if(StringUtil.isNotEmpty(answerNote) && answerNote.length() > 50) {
				//截取显示
				answerNote = answerNote.substring(0, 40) + "...";
				pageData.put("TEST_ANSWER_NOTE", answerNote);
			}
		}
		mv.setViewName("testquestion/testquestion/testquestion_list");
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
		mv.setViewName("testquestion/testquestion/testquestion_edit");
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
		pd = testquestionService.findById(pd);	//根据ID读取
		List<PageData> courseTypePdList = new ArrayList<PageData>(); 
		List<PageData> listById = testquestionService.listById(pd);
		JSONArray arr = JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0",courseTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		mv.setViewName("testquestion/testquestion/testquestion_edit");
		mv.addObject("msg", "edit");
		mv.addObject("varList",listById);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除TestQuestion");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			testquestionService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
   /**
        *  批量保存答案
    * @param questionId 题目ID
    * @param questionItem 试题信息Detail
    * @param userName 操作用户
    * @throws Exception
    */
   private void saveDetail(String questionId,String questionItem,String userName) throws Exception {
		String[] items = questionItem.split(",");
		char index = 65;//A,B,C,D.....
		for (int i = 0; i < items.length; i++) {
			//存答案 title 手动生成
			PageData questionDetail = new PageData();
			questionDetail.put("TEST_QUESTION_ID", questionId);
			questionDetail.put("TEST_QUESTION_ITEM_TITLE",Character.toString(index++));
			questionDetail.put("TEST_QUESTION_ITEM_CONTENT",items[i]);
			questionDetail.put("STATE", "1");
			//获取出题人信息
			questionDetail.put("CREATE_USER",userName);
			questionDetail.put("CREATE_DATE",DateUtils.getCurrentTime(DateFormatUtils.DATE_FORMAT1)); //YYYY-MM-dd
			testquestionService.saveItem(questionDetail);
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
