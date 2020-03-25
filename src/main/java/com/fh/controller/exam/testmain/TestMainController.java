package com.fh.controller.exam.testmain;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.fh.controller.common.Common;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.service.exam.testmain.TestMainManager;
import com.fh.service.exam.testplan.TestPlanManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/** 
 * 说明：testMain
 * 创建人：xinyuLo
 * 创建时间：2019-11-06
 */
@Controller
@RequestMapping(value="/testmain")
public class TestMainController extends BaseController {
	
	String menuUrl = "testmain/list.do"; //菜单地址(权限用)
	@Resource(name="testmainService")
	private TestMainManager testmainService;
	@Resource(name="testplanService")
	private TestPlanManager testplanService;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	
	String TableNameDetail = "TB_DI_GRC_PERSON"; //表名
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增TestMain");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TESTMAIN_ID", this.get32UUID());	//主键
		testmainService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除TestMain");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		testmainService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改TestMain");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		testmainService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表TestMain");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		//初始化参数
		ModelAndView mv = this.getModelAndView();
		List<PageData> paperPage = new ArrayList<PageData>();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		String userName = user.getUSERNAME();
		String nowDate = DateUtil.getDays();
		pd.put("STUDENT_ID", userName);
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		/*------------------未考试------------------*/
		List<PageData> paperList = testmainService.paperListPage(page);
		for (PageData pageData : paperList) {
			// 先判断时间
			String endDate = pageData.getString("END_DATE");
			String startDate = pageData.getString("START_DATE");
			if (DateUtil.compareDates(endDate, nowDate) && DateUtil.compareDates(nowDate, startDate)) {
				pageData.put("TEST_USER", userName);
				List<PageData> userExamList = testmainService.listByUser(pageData);
				if (null == userExamList || userExamList.size() == 0) {
					paperPage.add(pageData);
				}
			}
		}
		
		/*------------------已考试------------------*/
		List<PageData> varList = testmainService.list(page); // 列出TestMain列表
		mv.addObject("varList", varList);
		mv.addObject("paperList",paperPage);
		mv.addObject("pd", pd);
		mv.setViewName("exam/testmain/testmain_list");
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
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
		mv.setViewName("exam/testmain/testmain_edit");
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
		pd = testmainService.findById(pd);	//根据ID读取
		mv.setViewName("exam/testmain/testmain_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除TestMain");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			testmainService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryList")
	public ModelAndView queryList(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "统计列表TestMain");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = testmainService.list(page); // 列出TestMain列表
		List<PageData> planList = testplanService.planList(pd);
		
		//***********************************************************导出
		Map_HaveColumnsList = Common.GetHaveColumnsMapByTableName(TableNameDetail, tmplconfigService);
		Map_SetColumnsList.put("TEST_TIME", new TmplConfigDetail("TEST_TIME", "考试时间", "1", false));
		Map_SetColumnsList.put("TEST_PAPER_TITLE", new TmplConfigDetail("TEST_PAPER_TITLE", "考试试卷名称", "1", false));
		Map_SetColumnsList.put("TEST_QUESTION_NUM", new TmplConfigDetail("TEST_QUESTION_NUM", "题目数", "1", false));
		Map_SetColumnsList.put("STUDENT_NAME", new TmplConfigDetail("STUDENT_NAME", "考试人", "1", false));
		Map_SetColumnsList.put("TEST_SCORE", new TmplConfigDetail("TEST_SCORE", "分数", "1", false));
		Map_SetColumnsList.put("IF_QUALIFIED", new TmplConfigDetail("IF_QUALIFIED", "是否合格", "1", false));
		
		mv.addObject("varList", varList);
		mv.addObject("planList", planList);
		mv.addObject("pd", pd);
		mv.setViewName("exam/testmain/testmain_query_list");
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出TestMain到excel");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		PageData getPd = this.getPageData();
		// 页面显示数据的年月
		// getPd.put("SystemDateTime", SystemDateTime);
		page.setPd(getPd);
		List<PageData> varOList = testmainService.list(page);
		return export(varOList, "", Map_SetColumnsList);
	}

	private ModelAndView export(List<PageData> varOList, String ExcelName,
			Map<String, TmplConfigDetail> map_SetColumnsList) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if (map_SetColumnsList != null && map_SetColumnsList.size() > 0) {
			for (TmplConfigDetail col : map_SetColumnsList.values()) {
				if (col.getCOL_HIDE().equals("1")) {
					titles.add(col.getCOL_NAME());
				}
			}
			if (varOList != null && varOList.size() > 0) {
				for (int i = 0; i < varOList.size(); i++) {
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						if (col.getCOL_HIDE().equals("1")) {
							Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
							if(null == getCellValue) {
								getCellValue = "";
							}
							if(j==6) {//判断是否合格
								if(getCellValue.toString().equals("1")) {
									vpd.put("var" + j, "及格");
								}else if (getCellValue.toString().equals("0")) {
									vpd.put("var" + j, "不及格");
								}else {
									vpd.put("var" + j, getCellValue.toString());
								}
							}else {
								vpd.put("var" + j, getCellValue.toString());
							}
							
							j++;
						}
					}
					varList.add(vpd);
				}
			}
		}
		dataMap.put("titles", titles);
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
