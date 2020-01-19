package com.fh.controller.tmplconfig.tmplconfig;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.service.assess.KPIManager;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.TmplConfigDictManager;
import com.fh.service.tmplconfig.tmplconfig.TmplConfigManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SqlTools;

import net.sf.json.JSONArray;

/**
 * 数据模板详情
 * 
 * @ClassName: TmplConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年6月19日
 *
 */
@Controller
@RequestMapping(value = "/tmplconfig")
public class TmplConfigController extends BaseController {

	String menuUrl = "tmplconfig/list.do"; // 菜单地址(权限用)
	@Resource(name = "tmplconfigService")
	private TmplConfigManager tmplconfigService;
	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	@Resource(name = "tmplconfigdictService")
	private TmplConfigDictManager tmplconfigdictService;

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name = "kpiService")
	private KPIManager kpiService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);

		//List<PageData> listBase = tmplconfigService.listBase(page); // 列出TmplConfigBase列表
		//mv.addObject("listBase", listBase);
		List<PageData> listKpi = kpiService.listAll(null);
		mv.addObject("listBase", listKpi);
				
		List<PageData> treeSource = DictsUtil.getDepartmentSelectTreeSourceList(departmentService);
		if (treeSource != null && treeSource.size() > 0) {
			JSONArray arr = JSONArray.fromObject(treeSource);
			mv.addObject("zTreeNodes", null == arr ? "" : arr.toString());
			PageData rootDepart = treeSource.get(0);
			pd.put("rootDepartCode", rootDepart.getString("id"));
			pd.put("rootDepartName", rootDepart.getString("name"));
		}

		String dicTypeValus = DictsUtil.getDicTypeValue(tmplconfigdictService);
		
		String dictString = " : ;" + dicTypeValus;
		mv.addObject("dictString", dictString);
		mv.setViewName("tmplconfig/tmplconfig/tmplconfig_list");
		
		// CUST_COL7 BILL_OFF 帐套字典
		mv.addObject("fmisacc", DictsUtil.getDictsByParentBianma(dictionariesService, "FMISACC"));
		mv.addObject("pd", pd);
		String dicBillOff = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String dictBillOffString = " : ;" + dicBillOff;
		mv.addObject("dictBillOffString", dictBillOffString);
		

		// 设置期间
		pd.put("KEY_CODE", "SystemDataTime");
		String busiDate = sysConfigManager.getSysConfigByKey(pd);
		pd.put("busiDate", busiDate);
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(Page page) throws Exception {
		PageData pd = this.getPageData();
		PageData tpd = tmplconfigService.findTableCodeByKpiCode(pd);
		String tmplTableCode=tpd.getString("TABLE_CODE");
		pd.put("TABLE_CODE",tmplTableCode );
		String filters = pd.getString("filters"); // 多条件过滤条件
		if (null != filters && !"".equals(filters)) {
			pd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		page.setPd(pd);
		List<PageData> varList = tmplconfigService.listAll(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		/*
		 * if (varList.size() != 0) { result.setRows(varList); } else {
		 * List<PageData> temporaryList = tmplconfigService.temporaryList(page);
		 * result.setRows(temporaryList); }
		 */
		/*String tableCodeOri = DictsUtil.getActualTable(tmplTableCode);// 数据库真实业务数据表
		pd.put("TABLE_CODE", tableCodeOri);*/
		List<PageData> temporaryList = tmplconfigService.temporaryList(page);
		if (varList!=null&&varList.size() != 0) {
			List<PageData> plusList = new ArrayList<PageData>();
			for (PageData temp : temporaryList) {
				boolean plus=true;
				for (PageData item : varList) {
					if (temp.getString("COL_CODE").equals(item.getString("COL_CODE"))) {
						plus=false;
						break;
					}
				}
				if(plus){
					temp.put("TABLE_CODE", tmplTableCode);
					plusList.add(temp);
					//temp.put("TABLE_CODE", item.get("TABLE_CODE"));
				}
			}
			for (PageData plusItem : plusList) {
				varList.add(plusItem);
			}
			result.setRows(varList);
		}else{
			for (PageData temp : temporaryList) {
				temp.put("TABLE_CODE", tmplTableCode);
				result.setRows(temporaryList);
			}
		}

		/*if (varList.size() != 0) {
			for (PageData temp : temporaryList) {
				for (PageData item : varList) {
					if (temp.getString("COL_CODE").equals(item.getString("COL_CODE"))) {
						temp.put("TABLE_CODE", item.get("TABLE_CODE"));
						temp.put("COL_NAME", item.get("COL_NAME"));
						temp.put("DISP_ORDER", item.get("DISP_ORDER"));
						temp.put("DICT_TRANS", item.get("DICT_TRANS"));
						temp.put("COL_HIDE", item.get("COL_HIDE"));
						temp.put("COL_SUM", item.get("COL_SUM"));
						temp.put("COL_AVE", item.get("COL_AVE"));
						temp.put("COL_TRANSFER", item.get("COL_TRANSFER"));
					}
				}
			}
		}
		result.setRows(temporaryList);*/
		return result;
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public @ResponseBody CommonBase edit() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername()+"修改SysTableMapping");

		PageData getPd = this.getPageData();
		//操作
		String oper = getPd.getString("oper");

		if(oper.equals("edit")){
			tmplconfigService.updateItem(getPd);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	/**
	 * 批量修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAll")
	/* @RequestBody RequestBase<JqGridModel> jqGridModel */
	public @ResponseBody CommonBase updateAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除JgGrid");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("DATA_ROWS");
		JSONArray array = JSONArray.fromObject(strDataRows);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法

		if (null != listData && listData.size() > 0) {
			// 根据这两个条件删除表
			pd.put("DEPT_CODE", listData.get(0).get("DEPT_CODE"));
			pd.put("TABLE_CODE", listData.get(0).get("TABLE_CODE"));
			pd.put("RPT_DUR", listData.get(0).get("RPT_DUR"));
			pd.put("BILL_OFF", listData.get(0).get("BILL_OFF"));
			tmplconfigService.deleteTable(pd);

			tmplconfigService.updateAll(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	/**
	 * 复制
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/copyAll")
	public @ResponseBody CommonBase copyAll() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		PageData tpd = tmplconfigService.findTableCodeByKpiCode(pd);
		pd.put("TABLE_CODE", tpd.getString("TABLE_CODE"));
		pd.put("DEPT_CODE", pd.getString("DEPARTMENT_CODE"));
		pd.put("RPT_DUR", pd.getString("RPT_DUR"));
		String strCode = pd.getString("deptIds");
		JSONArray array = JSONArray.fromObject(strCode);
		@SuppressWarnings("unchecked")
		List<String> listCode = (List<String>) JSONArray.toCollection(array, String.class);// 过时方法
		listCode.remove(pd.getString("DEPARTMENT_CODE"));
		if (null != listCode && listCode.size() > 0) {
			pd.put("DEPT_CODES", listCode);
			tmplconfigService.copyAll(pd);
			commonBase.setCode(0);
		}
		return commonBase;
	}
}
