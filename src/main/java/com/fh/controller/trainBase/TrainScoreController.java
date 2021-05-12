package com.fh.controller.trainBase;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.service.trainBase.TrainScoreManager;

/** 
 * 说明：培训成绩管理
 * 创建人：liqian
 * 创建时间：2021-02-26
 */
@Controller
@RequestMapping(value="/trainscore")
public class TrainScoreController extends BaseController {
	
	String menuUrl = "trainscore/list.do"; //菜单地址(权限用)
	@Resource(name="trainscoreService")
	private TrainScoreManager trainscoreService;
	
	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	// 表名
	String TableNameDetail = "TB_TRAIN_SCORE";
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("CREAT_USER", user.getUSER_ID());
		pd.put("CREAT_DATE", DateUtils.getCurrentTime());
		trainscoreService.save(pd);
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
		PageData pd = new PageData();	
		pd = this.getPageData();
		trainscoreService.delete(pd);	//执行删除
		out.write("success");
		out.close();
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
		trainscoreService.edit(pd);
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
		page.setPd(pd);
		mv.addObject("keywords",keywords);
		List<PageData>	varList = trainscoreService.list(page);			//列出CourseType列表
		mv.setViewName("trainBase/trainscore/trainscore_mage");
		mv.addObject("varList", varList);
		Map_HaveColumnsList = Common.GetHaveColumnsMapByTableName(TableNameDetail, tmplconfigService);

		Map_SetColumnsList.put("TEST_NAME", new TmplConfigDetail("TEST_NAME", "考试名称", "1", false));
		Map_SetColumnsList.put("TEST_DATE", new TmplConfigDetail("TEST_DATE", "考试时间", "1", false));
		Map_SetColumnsList.put("USERNAME", new TmplConfigDetail("USERNAME", "员工编号(必输)", "1", false));
		Map_SetColumnsList.put("NAME1", new TmplConfigDetail("NAME1", "用户姓名(必输)", "1", false));
		Map_SetColumnsList.put("UNIT_CODE", new TmplConfigDetail("UNIT_CODE", "单位编码", "1", false));
		Map_SetColumnsList.put("MODULE", new TmplConfigDetail("MODULE", "模块(必输)", "1", false));
		Map_SetColumnsList.put("TEST_SCORE", new TmplConfigDetail("TEST_SCORE", "考试分数", "1", true));
		Map_SetColumnsList.put("CERTIFICATE_NUM", new TmplConfigDetail("CERTIFICATE_NUM", "证书编号", "1", false));
		Map_SetColumnsList.put("REMARK", new TmplConfigDetail("REMARK", "备注", "1", false));
//		Map_SetColumnsList.put("QUALIFIED_SCORE", new TmplConfigDetail("QUALIFIED_SCORE", "及格分数(必输)", "1", true));
//		Map_SetColumnsList.put("IF_QUALIFIED", new TmplConfigDetail("IF_QUALIFIED", "是否及格", "1", false));	
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listScore")
	public ModelAndView listScore(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");		
		//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String unitCode=user.getUNIT_CODE();
	    pd.put("UNIT_CODE", unitCode);
		page.setPd(pd);
		mv.addObject("keywords",keywords);
		List<PageData>	varList = trainscoreService.list(page);			//列出CourseType列表
		mv.setViewName("trainBase/trainscore/trainscore_list");
		mv.addObject("varList", varList);
		Map_HaveColumnsList = Common.GetHaveColumnsMapByTableName(TableNameDetail, tmplconfigService);

		Map_SetColumnsList.put("TEST_NAME", new TmplConfigDetail("TEST_NAME", "考试名称", "1", false));
		Map_SetColumnsList.put("TEST_DATE", new TmplConfigDetail("TEST_DATE", "考试时间", "1", false));
		Map_SetColumnsList.put("USERNAME", new TmplConfigDetail("USERNAME", "员工编号", "1", false));
		Map_SetColumnsList.put("NAME1", new TmplConfigDetail("NAME1", "用户姓名", "1", false));
		Map_SetColumnsList.put("UNIT_CODE", new TmplConfigDetail("UNIT_CODE", "单位编码", "1", false));
		Map_SetColumnsList.put("MODULE", new TmplConfigDetail("MODULE", "模块", "1", false));
		Map_SetColumnsList.put("TEST_SCORE", new TmplConfigDetail("TEST_SCORE", "考试分数", "1", true));
		Map_SetColumnsList.put("CERTIFICATE_NUM", new TmplConfigDetail("CERTIFICATE_NUM", "证书编号", "1", false));
		Map_SetColumnsList.put("REMARK", new TmplConfigDetail("REMARK", "备注", "1", false));
//		Map_SetColumnsList.put("QUALIFIED_SCORE", new TmplConfigDetail("QUALIFIED_SCORE", "及格分数", "1", true));
//		Map_SetColumnsList.put("IF_QUALIFIED", new TmplConfigDetail("IF_QUALIFIED", "是否及格", "1", false));	
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
		mv.addObject("pds",trainscoreService.findById(pd));				//传入上级所有信息
		mv.addObject("pd", pd);			
		mv.addObject("COURSETYPE_ID", COURSETYPE_ID);			//传入ID，作为子级ID用
		mv.setViewName("trainBase/trainscore/trainscore_edit");
		mv.addObject("itemUnitList", DictsUtil.getDepartmentSelectTreeSourceList(departmentService));// 获取单位名称
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
		pd = this.getPageData();
		mv.addObject("pd",trainscoreService.findById(pd));//传入上级所有信息
		mv.setViewName("trainBase/trainscore/trainscore_edit");
		mv.addObject("itemUnitList", DictsUtil.getDepartmentSelectTreeSourceList(departmentService));// 获取单位名称
		mv.addObject("msg", "edit");
		return mv;
	}	
	/**
	 * 导出考试成绩
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportScore")
	public ModelAndView exportScore(JqPage page) throws Exception {
		PageData getPd = this.getPageData();
		String keywords = getPd.getString("keywords");		
		//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			getPd.put("keywords", keywords.trim());
		}
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
	    String unitCode=user.getUNIT_CODE();
	    getPd.put("UNIT_CODE", unitCode);
		page.setPd(getPd);
		List<PageData> varOList = trainscoreService.exportList(page);
		return export(varOList, "考试成绩_"+DateUtils.getCurrentTime(DateFormatUtils.DATE_FORMAT1), Map_SetColumnsList);
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
							vpd.put("var" + j, StringUtil.toString(getCellValue, ""));
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
	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public ModelAndView downExcel(JqPage page) throws Exception {
		PageData getPd = this.getPageData();
		List<PageData> varOList = trainscoreService.exportModel(getPd);
		return export(varOList, "trainscore", Map_SetColumnsList);
	}

	/**
	 * 打开上传EXCEL页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "trainscore");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		String strErrorMessage = "";
		PageData getPd = this.getPageData();
		Map<String, Object> DicList = new HashMap<String, Object>();

		// 局部变量
		LeadingInExcelToPageData<PageData> testExcel = null;
		Map<Integer, Object> uploadAndReadMap = null;
		try {
			// 定义需要读取的数据
			String formart = "yyyy-MM-dd";
			String propertiesFileName = "config";
			String kyeName = "file_path";
			int sheetIndex = 0;
			Map<String, String> titleAndAttribute = null;
			// 定义对应的标题名与对应属性名
			titleAndAttribute = new LinkedHashMap<String, String>();

			// 配置表设置列
			if (Map_SetColumnsList != null && Map_SetColumnsList.size() > 0) {
				for (TmplConfigDetail col : Map_SetColumnsList.values()) {
					titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
				}
			}

			// 调用解析工具包
			testExcel = new LeadingInExcelToPageData<PageData>(formart);
			// 解析excel，获取客户信息集合

			uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex, titleAndAttribute,
					Map_HaveColumnsList, Map_SetColumnsList, DicList, false, false, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取Excel文件错误", e);
			throw new CustomException("读取Excel文件错误:" + e.getMessage(), false);
		}
		boolean judgement = false;

		List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
		if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
			judgement = true;
		}
		out:if (judgement) {
			PageData pdSave = new PageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			for (PageData pdItem : listUploadAndRead) {	
				if(null==pdItem.getString("USERNAME")||pdItem.getString("USERNAME").equals("")){
					commonBase.setCode(2);
					commonBase.setMessage("员工编号不能为空,请确认！");	
					break out;
				}
				if(null==pdItem.getString("NAME1")||pdItem.getString("NAME1").equals("")){
					commonBase.setCode(2);
					commonBase.setMessage("用户姓名不能为空,请确认！");	
					break out;
				}
				if(null==pdItem.getString("MODULE")||pdItem.getString("MODULE").equals("")){
					commonBase.setCode(2);
					commonBase.setMessage("模块不能为空,请确认！");	
					break out;
				}
//				if(null==pdItem.getString("TEST_SCORE")||pdItem.getString("TEST_SCORE").equals("")){
//					commonBase.setCode(2);
//					commonBase.setMessage("考试分数不能为空,请确认！");	
//					break out;
//				}
				pdItem.put("CREAT_USER", user.getUSER_ID());
				pdItem.put("CREAT_DATE", DateUtils.getCurrentTime());
			}
			try{
				pdSave.put("listDetail", listUploadAndRead);
				trainscoreService.saveImport(pdSave);
				commonBase.setCode(0);
			}catch (Exception e) {
				commonBase.setCode(2);
				//commonBase.setMessage("导入失败！");	
			}
			
		} else {
			commonBase.setCode(-1);
			commonBase.setMessage("TranslateUtil");
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "trainscore");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	//根据员工编号获取考试成绩
	@RequestMapping(value="/getTrainScore")
	@ResponseBody 
	public  PageData getTrainScore() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = trainscoreService.findByUserCode(pd);		
		return 	pd;
	}
}
