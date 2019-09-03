package com.fh.controller.tmplInputTips.tmplInputTips;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
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
 * 
 * @ClassName: TmplInputTipsController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 
 * @date 
 */
@Controller
@RequestMapping(value = "/tmplInputTips")
public class TmplInputTipsController extends BaseController {

	String menuUrl = "tmplInputTips/list.do"; // 菜单地址(权限用)
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
	
    //
    List<String> MustNotEditList = Arrays.asList("RPT_DUR", "DEPT_CODE", "BILL_OFF", "TABLE_CODE", "COL_CODE");

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page pagess) throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("tmplInputTips/tmplInputTips/tmplInputTips_list");
		// 设置期间
		String SystemDateTime = sysConfigManager.currentSection(pd);
		pd.put("SystemDateTime", SystemDateTime);
		
		String QueryFeild = " and UPPER(TABLE_CODE) like UPPER('%_DETAIL%')";
		PageData pdtransfer = this.getPageData();
		pdtransfer.put("QueryFeild", QueryFeild);
		Page pgtransfer = new Page();
		pgtransfer.setPd(pdtransfer);
		List<PageData> listBase = tmplconfigService.listBase(pgtransfer); // 列出TmplConfigBase列表
		mv.addObject("listBase", listBase);
		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);
		
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

		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception {
		PageData getPd = this.getPageData();
		//配置表
		String SelectedTableCode = getPd.getString("SelectedTableCode");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		
		//当前区间
		String SystemDateTime = sysConfigManager.currentSection(new PageData());
		getPd.put("ShowStruRptDur", SystemDateTime);
		getPd.put("ShowStruTableCode", SelectedTableCode);
		getPd.put("ShowStruBillOff", SelectedCustCol7);
		
		String strShowDepartCode = Common.getShowColumnDepart(SelectedTableCode, SelectedCustCol7, SelectedDepartCode, tmplconfigService);
		getPd.put("ShowStruDepartCode", strShowDepartCode);
		
		String filters = getPd.getString("filters"); // 多条件过滤条件
		if (null != filters && !"".equals(filters)) {
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		//String strFieldSelectKey = QueryFeildString.getFieldSelectKey(MustNotEditList, TmplUtil.keyExtra);
		//if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
		//	getPd.put("FieldSelectKey", strFieldSelectKey);
		//}
		page.setPd(getPd);
		List<PageData> varList = tmplconfigService.JqPageTmplInputTips(page);
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setPage(page.getPage());
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
		logBefore(logger, Jurisdiction.getUsername()+"修改");

		PageData getPd = this.getPageData();
		//操作
		String oper = getPd.getString("oper");

		if(oper.equals("edit")){
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);
			tmplconfigService.saveTmplInputTips(listData);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAll")
	public @ResponseBody CommonBase updateAll() throws Exception {
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("DATA_ROWS");
		JSONArray array = JSONArray.fromObject(strDataRows);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法

		if (null != listData && listData.size() > 0) {
    		for(PageData item : listData){
    			for(String strFeild : MustNotEditList){
    				item.put(strFeild, item.get(strFeild + TmplUtil.keyExtra));
    			}
            }
    		tmplconfigService.saveTmplInputTips(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
}
