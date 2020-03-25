package com.fh.controller.assess;

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
import com.fh.service.assess.AssessDepartManager;
import com.fh.service.fhoa.department.DepartmentManager;

/**
 * 说明：考核单位管理 创建人：jiachao 创建时间：2020-03-10
 */
@Controller
@RequestMapping(value = "/assessdepart")
public class AssessDepartController extends BaseController {

	String menuUrl = "assessdepart/list.do"; // 菜单地址(权限用)
	@Resource(name = "assessdepartService")
	private AssessDepartManager assessdepartService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (StringUtil.isEmpty(pd.getString("DEPART_PARENT_CODE"))) {
			pd.put("DEPART_PARENT_CODE", "0");
		}
		assessdepartService.save(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam String ASSESSDEPART_ID) throws Exception { // 校验权限
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd.put("ASSESSDEPART_ID", ASSESSDEPART_ID);
		
		pd = assessdepartService.findById(pd);
		ASSESSDEPART_ID = pd.get("DEPART_CODE").toString();
		String errInfo = "success";
		if (assessdepartService.listByParentId(ASSESSDEPART_ID).size() > 0) { // 判断是否有子级，是：不允许删除
			errInfo = "false";
		} else {
			pd.put("ASSESSDEPART_ID", pd.get("DEPART_ID").toString());
			assessdepartService.delete(pd); // 执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		assessdepartService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String ASSESSDEPART_ID = null == pd.get("ASSESSDEPART_ID") ? "" : pd.get("ASSESSDEPART_ID").toString();
		if (null != pd.get("id") && !"".equals(pd.get("id").toString())) {
			ASSESSDEPART_ID = pd.get("id").toString();
		}
		pd.put("ASSESSDEPART_ID", ASSESSDEPART_ID); // 上级ID
		pd.put("DEPART_CODE", ASSESSDEPART_ID); // 上级ID,返回按钮用
		page.setPd(pd);
		List<PageData> varList = assessdepartService.list(page); // 列出TrainDepart列表
		mv.setViewName("assess/assessdepart/assessdepart_list");
		mv.addObject("pd", assessdepartService.findByCode(pd)); // 传入上级所有信息
		mv.addObject("keywords", keywords); 
		mv.addObject("ASSESSDEPART_ID", ASSESSDEPART_ID); // 上级ID
		mv.addObject("varList", varList); // 按钮权限
		return mv;
	}

	/**
	 * 显示列表ztree
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listTree")
	public ModelAndView listTree(Model model, String ASSESSDEPART_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			JSONArray arr = JSONArray.fromObject(assessdepartService.listTree("0"));
			String json = arr.toString();
			json = json.replaceAll("ASSESSDEPART_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name")
					.replaceAll("subAssessDepart", "nodes").replaceAll("hasAssessDepart", "checked")
					.replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ASSESSDEPART_ID", ASSESSDEPART_ID);
			mv.addObject("pd", pd);
			mv.setViewName("assess/assessdepart/assessdepart_tree");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ASSESSDEPART_ID = null == pd.get("ASSESSDEPART_ID") ? "" : pd.get("ASSESSDEPART_ID").toString();
		pd.put("DEPART_CODE", ASSESSDEPART_ID); // 上级ID
		mv.addObject("pds", assessdepartService.findByCode(pd)); // 传入上级所有信息
		mv.addObject("ASSESSDEPART_ID", ASSESSDEPART_ID); // 传入ID，作为子级ID用
		mv.setViewName("assess/assessdepart/assessdepart_edit");
		mv.addObject("msg", "save");
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pdParent = new PageData();

		pd = this.getPageData();

		pd = assessdepartService.findById(pd); // 根据ID读取
		mv.addObject("pd", pd); // 放入视图容器
		pdParent.put("DEPART_CODE", pd.get("DEPART_PARENT_CODE").toString()); // 用作上级信息
		mv.addObject("pds", assessdepartService.findByCode(pdParent)); // 传入上级所有信息
		mv.addObject("ASSESSDEPART_ID", pd.get("DEPART_PARENT_CODE").toString()); // 传入上级ID，作为子ID用
		
		mv.setViewName("assess/assessdepart/assessdepart_edit");
		mv.addObject("msg", "edit");
		
		pd.put("DEPARTMENT_CODE", pd.getString("LOCAL_DEPART_CODE"));
		PageData pdDepartResultUnit=departmentService.findByBianma(pd);
		if(pdDepartResultUnit!=null)
			mv.addObject("unitName", pdDepartResultUnit.getString("NAME"));
		else
			mv.addObject("unitName", null);
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出AssessDepart到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1"); // 1
		titles.add("备注2"); // 2
		titles.add("备注3"); // 3
		titles.add("备注4"); // 4
		titles.add("备注5"); // 5
		titles.add("备注6"); // 6
		dataMap.put("titles", titles);
		List<PageData> varOList = assessdepartService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("DEPART_ID").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("DEPART_CODE")); // 2
			vpd.put("var3", varOList.get(i).getString("DEPART_NAME")); // 3
			vpd.put("var4", varOList.get(i).getString("DEPART_PARENT_CODE")); // 4
			vpd.put("var5", varOList.get(i).getString("LOCAL_DEPART_CODE")); // 5
			vpd.put("var6", varOList.get(i).getString("IS_MERGE_DEPART")); // 5
			vpd.put("var7", varOList.get(i).getString("STATE")); // 6
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
