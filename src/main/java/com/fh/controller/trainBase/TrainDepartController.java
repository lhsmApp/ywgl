package com.fh.controller.trainBase;

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
import com.fh.service.trainBase.TrainDepartManager;

/**
 * 说明：培训基础 创建人：jiachao 创建时间：2019-10-23
 */
@Controller
@RequestMapping(value = "/traindepart")
public class TrainDepartController extends BaseController {

	String menuUrl = "traindepart/list.do"; // 菜单地址(权限用)
	@Resource(name = "traindepartService")
	private TrainDepartManager traindepartService;

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
		traindepartService.save(pd);
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
	public Object delete(@RequestParam String TRAINDEPART_ID) throws Exception { // 校验权限
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd.put("TRAINDEPART_ID", TRAINDEPART_ID);
		
		pd = traindepartService.findById(pd);
		TRAINDEPART_ID = pd.get("DEPART_CODE").toString();
		String errInfo = "success";
		if (traindepartService.listByParentId(TRAINDEPART_ID).size() > 0) { // 判断是否有子级，是：不允许删除
			errInfo = "false";
		} else {
			pd.put("TRAINDEPART_ID", pd.get("DEPART_ID").toString());
			traindepartService.delete(pd); // 执行删除
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
		traindepartService.edit(pd);
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
		String TRAINDEPART_ID = null == pd.get("TRAINDEPART_ID") ? "" : pd.get("TRAINDEPART_ID").toString();
		if (null != pd.get("id") && !"".equals(pd.get("id").toString())) {
			TRAINDEPART_ID = pd.get("id").toString();
		}
		pd.put("TRAINDEPART_ID", TRAINDEPART_ID); // 上级ID
		pd.put("DEPART_CODE", TRAINDEPART_ID); // 上级ID,返回按钮用
		page.setPd(pd);
		List<PageData> varList = traindepartService.list(page); // 列出TrainDepart列表
		mv.setViewName("trainBase/traindepart/traindepart_list");
		mv.addObject("pd", traindepartService.findByCode(pd)); // 传入上级所有信息
		mv.addObject("keywords", keywords); 
		mv.addObject("TRAINDEPART_ID", TRAINDEPART_ID); // 上级ID
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
	public ModelAndView listTree(Model model, String TRAINDEPART_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			JSONArray arr = JSONArray.fromObject(traindepartService.listTree("0"));
			String json = arr.toString();
			json = json.replaceAll("TRAINDEPART_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name")
					.replaceAll("subTrainDepart", "nodes").replaceAll("hasTrainDepart", "checked")
					.replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("TRAINDEPART_ID", TRAINDEPART_ID);
			mv.addObject("pd", pd);
			mv.setViewName("trainBase/traindepart/traindepart_tree");
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
		String TRAINDEPART_ID = null == pd.get("TRAINDEPART_ID") ? "" : pd.get("TRAINDEPART_ID").toString();
		pd.put("DEPART_CODE", TRAINDEPART_ID); // 上级ID
		mv.addObject("pds", traindepartService.findByCode(pd)); // 传入上级所有信息
		mv.addObject("TRAINDEPART_ID", TRAINDEPART_ID); // 传入ID，作为子级ID用
		mv.setViewName("trainBase/traindepart/traindepart_edit");
		mv.addObject("msg", "save");
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

		pd = traindepartService.findById(pd); // 根据ID读取
		mv.addObject("pd", pd); // 放入视图容器
		pdParent.put("DEPART_CODE", pd.get("DEPART_PARENT_CODE").toString()); // 用作上级信息
		mv.addObject("pds", traindepartService.findByCode(pdParent)); // 传入上级所有信息
		mv.addObject("TRAINDEPART_ID", pd.get("DEPART_PARENT_CODE").toString()); // 传入上级ID，作为子ID用
		
		mv.setViewName("trainBase/traindepart/traindepart_edit");
		mv.addObject("msg", "edit");
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
		logBefore(logger, Jurisdiction.getUsername() + "导出TrainDepart到excel");
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
		List<PageData> varOList = traindepartService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("DEPART_ID").toString()); // 1
			vpd.put("var2", varOList.get(i).getString("DEPART_CODE")); // 2
			vpd.put("var3", varOList.get(i).getString("DEPART_NAME")); // 3
			vpd.put("var4", varOList.get(i).getString("DEPART_PARENT_CODE")); // 4
			vpd.put("var5", varOList.get(i).getString("LEADER")); // 5
			vpd.put("var6", varOList.get(i).getString("STATE")); // 6
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
