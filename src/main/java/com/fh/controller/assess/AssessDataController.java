package com.fh.controller.assess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.Common;
import com.fh.controller.common.Corresponding;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplStaffTdsRemit;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.enums.TmplType;
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.service.assess.AssessDataManager;
import com.fh.service.assess.KPIManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.impl.UserService;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/**
 * 说明：考核数据导入 创建人：jiachao 创建时间：2019-08-07
 */
@Controller
@RequestMapping(value = "/assessData")
public class AssessDataController extends BaseController {

	String menuUrl = "assessData/list.do"; // 菜单地址(权限用)
	@Resource(name = "assessDataService")
	private AssessDataManager assessDataService;

	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name = "tmplconfigdictService")
	private TmplConfigDictService tmplconfigdictService;
	@Resource(name = "dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name = "departmentService")
	private DepartmentService departmentService;
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "kpiService")
	private KPIManager kpiService;

	// 表名
	// String TableName = "TB_TAX_STAFF_DETAIL";
	// 默认的which值
	String DefaultWhile = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();

	// 界面查询字段
	List<String> QueryFeildList = Arrays.asList("BUSI_DATE");
	// 设置必定不用编辑的列
	List<String> MustNotEditList = Arrays.asList("BUSI_DATE");

	// 获取带__的列，后续删除之类的有用
	private List<String> keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
	// 设置必定不为空的列
	private List<String> MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE");

	Map<String, TableColumns> map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	Map<String, Object> map_DicList = new LinkedHashMap<String, Object>();
	// 底行显示的求和字段
	StringBuilder SqlUserdata = new StringBuilder();

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("assess/assessData/assessData_list");
		mv.addObject("jqGridColModel", "[]");
		mv.addObject("jqGridColModelMessage", "");

		// 当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);

		List<PageData> listKpi = kpiService.listAll(null);
		List<PageData> listKpiAll = new ArrayList<PageData>();
		for (PageData pdItem : listKpi) {
			PageData pdTotal = new PageData();
			pdTotal.put("KPI_CODE", "total-" + pdItem.getString("KPI_CODE"));
			pdTotal.put("KPI_NAME", pdItem.getString("KPI_NAME") + "/" + "指标总数");

			pdItem.put("KPI_NAME", pdItem.getString("KPI_NAME") + "/" + "扣分明细");

			listKpiAll.add(pdItem);
			listKpiAll.add(pdTotal);
		}

		mv.addObject("listKPI", listKpiAll);

		PageData pdCode = new PageData();
		if (getPd.get("KPI_CODE") != null) {
			pdCode.put("KPI_CODE", getPd.getString("KPI_CODE"));
		} else {
			pdCode.put("KPI_CODE", listKpi.get(0).getString("KPI_CODE"));
			getPd.put("KPI_CODE", listKpi.get(0).getString("KPI_CODE"));
		}
		PageData kpi = kpiService.findByCode(pdCode);
		mv.addObject("KPI", kpi);

		switch (kpi.getString("KPI_CODE")) {
		case "Z1AM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			break;
		case "Z1AM2":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			break;
		case "Z1FI1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			break;
		case "Z1MM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			break;
		case "Z1MM2":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "POSITION_NO", "TO_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "POSITION_NO", "TO_NO");
			break;
		case "Z1PS1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			break;
		case "Z2AM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			break;
		case "Z2MM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "MATERIAL_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "MATERIAL_CODE");
			break;
		case "Z2PM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "EQUIPMENT_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "EQUIPMENT_CODE");
			break;
		case "Z2PS1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "WBS_INTER_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "WBS_INTER_CODE");
			break;
		case "Z3AM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			break;
		case "Z3FI2":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			break;
		default:
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "KPI_CODE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "KPI_CODE", "COMPANY_CODE", "TOTAL_SCORE", "PROPORTION");

			MustNotEditList = Arrays.asList("BUSI_DATE", "KPI_CODE", "COMPANY_CODE", "TOTAL_SCORE", "PROPORTION");
			break;
		}

		String jqGridColModel = "";
		SqlUserdata = new StringBuilder();
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, departmentService,
				userService, keyListBase, null, "", MustInputList, null);
		if (kpi.getString("KPI_CODE").contains("total")) {
			this.map_SetColumnsList = Common.GetSetColumnsListByTableName("tb_kh_total", "", "9870", tmplconfigService);
			this.map_HaveColumnsList = Common.GetHaveColumnsMapByTableName("tb_kh_total", tmplconfigService);

			/*
			 * this.map_SetColumnsList.clear();
			 * this.map_SetColumnsList.put("BUSI_DATE", new
			 * TmplConfigDetail("BUSI_DATE", "业务期间", "0", false));
			 * this.map_SetColumnsList.put("KPI_CODE", new
			 * TmplConfigDetail("KPI_CODE", "指标代码", "0", false));
			 * this.map_SetColumnsList.put("COMPANY_CODE", new
			 * TmplConfigDetail("COMPANY_CODE", "公司代码", "1", false));
			 * this.map_SetColumnsList.put("COMPANY_NAME", new
			 * TmplConfigDetail("COMPANY_NAME", "公司代码描述", "1", false));
			 * this.map_SetColumnsList.put("KH_TOTAL_NUM", new
			 * TmplConfigDetail("KH_TOTAL_NUM", "考核项总数", "1", true));
			 */

			jqGridColModel = tmpl.generateStructureByTableCode("tb_kh_total", "", "9870", 3, MustNotEditList);
		} else {
			this.map_SetColumnsList = Common.GetSetColumnsList(getPd.getString("KPI_CODE"), "", "9870",
					tmplconfigService);
			this.map_HaveColumnsList = Common.GetHaveColumnsList(getPd.getString("KPI_CODE"), tmplconfigService);
			/*
			 * this.map_DicList = Common.GetDicList(getPd.getString("KPI_CODE"),
			 * "", "9870", tmplconfigService, tmplconfigdictService,
			 * dictionariesService, departmentService, userService, "");
			 */
			jqGridColModel = tmpl.generateStructure(getPd.getString("KPI_CODE"), "", "9870", 3, MustNotEditList);
		}
		mv.addObject("jqGridColModel", jqGridColModel);
		getPd.put("funcType", "import");
		mv.addObject("pd", getPd);
		return mv;
	}

	/**
	 * 考核查询
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/assessQuery")
	public ModelAndView assessQuery(Page page) throws Exception {
		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("assess/assessData/assessData_query");
		mv.addObject("jqGridColModel", "[]");
		mv.addObject("jqGridColModelMessage", "");

		// 当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);

		List<PageData> listKpi = kpiService.listAll(null);
		List<PageData> listKpiAll = new ArrayList<PageData>();
		for (PageData pdItem : listKpi) {
			PageData pdTotal = new PageData();
			pdTotal.put("KPI_CODE", "total-" + pdItem.getString("KPI_CODE"));
			pdTotal.put("KPI_NAME", pdItem.getString("KPI_NAME") + "/" + "指标总数");

			pdItem.put("KPI_NAME", pdItem.getString("KPI_NAME") + "/" + "扣分明细");

			listKpiAll.add(pdItem);
			listKpiAll.add(pdTotal);
		}
		mv.addObject("listKPI", listKpiAll);

		PageData pdCode = new PageData();
		if (getPd.get("KPI_CODE") != null) {
			pdCode.put("KPI_CODE", getPd.getString("KPI_CODE"));
		} else {
			pdCode.put("KPI_CODE", listKpi.get(0).getString("KPI_CODE"));
			getPd.put("KPI_CODE", listKpi.get(0).getString("KPI_CODE"));
		}
		PageData kpi = kpiService.findByCode(pdCode);
		mv.addObject("KPI", kpi);

		/*
		 * this.map_SetColumnsList =
		 * Common.GetSetColumnsList(getPd.getString("KPI_CODE"), "", "9870",
		 * tmplconfigService); this.map_HaveColumnsList =
		 * Common.GetHaveColumnsList(getPd.getString("KPI_CODE"),
		 * tmplconfigService); this.map_DicList =
		 * Common.GetDicList(getPd.getString("KPI_CODE"), "", "9870",
		 * tmplconfigService, tmplconfigdictService, dictionariesService,
		 * departmentService, userService, "");
		 * 
		 * SqlUserdata = new StringBuilder();
		 * 
		 * TmplUtil tmpl = new TmplUtil(tmplconfigService,
		 * tmplconfigdictService, dictionariesService, departmentService,
		 * userService, keyListBase, null, "", MustInputList, null); String
		 * jqGridColModel = tmpl.generateStructure(getPd.getString("KPI_CODE"),
		 * "", "9870", 3, MustNotEditList);
		 * 
		 * mv.addObject("jqGridColModel", jqGridColModel); mv.addObject("pd",
		 * getPd);
		 */

		String jqGridColModel = "";
		SqlUserdata = new StringBuilder();
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, departmentService,
				userService, keyListBase, null, "", MustInputList, null);
		if (kpi.getString("KPI_CODE").contains("total")) {
			this.map_SetColumnsList = Common.GetSetColumnsListByTableName("tb_kh_total", "", "9870", tmplconfigService);
			this.map_HaveColumnsList = Common.GetHaveColumnsMapByTableName("tb_kh_total", tmplconfigService);
			jqGridColModel = tmpl.generateStructureByTableCode("tb_kh_total", "", "9870", 3, MustNotEditList);
		} else {
			this.map_SetColumnsList = Common.GetSetColumnsList(getPd.getString("KPI_CODE"), "", "9870",
					tmplconfigService);
			this.map_HaveColumnsList = Common.GetHaveColumnsList(getPd.getString("KPI_CODE"), tmplconfigService);
			/*
			 * this.map_DicList = Common.GetDicList(getPd.getString("KPI_CODE"),
			 * "", "9870", tmplconfigService, tmplconfigdictService,
			 * dictionariesService, departmentService, userService, "");
			 */
			jqGridColModel = tmpl.generateStructure(getPd.getString("KPI_CODE"), "", "9870", 3, MustNotEditList);
		}
		mv.addObject("jqGridColModel", jqGridColModel);
		getPd.put("funcType", "query");
		mv.addObject("pd", getPd);
		return mv;
	}

	/**
	 * 考核排行榜
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/assessRank")
	public ModelAndView assessRank(Page page) throws Exception {
		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("assess/assessData/assessData_rank");
		mv.addObject("jqGridColModel", "[]");
		mv.addObject("jqGridColModelMessage", "");

		// 当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());

		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, departmentService,
				userService);
		this.map_SetColumnsList = Common.GetSetColumnsListByTableName("tb_kh_rank", "", "9870", tmplconfigService);
		String jqGridColModel = tmpl.generateStructureNoEditByTableCode("tb_kh_rank", "", "9870");
		/*
		 * this.map_DicList = Common.GetDicList(getPd.getString("KPI_CODE"), "",
		 * "9870", tmplconfigService, tmplconfigdictService,
		 * dictionariesService, departmentService, userService, "");
		 */

		mv.addObject("jqGridColModel", jqGridColModel);
		mv.addObject("pd", getPd);
		return mv;
	}

	/**
	 * 获取Kpi信息
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKpi")
	public @ResponseBody PageData getKpi() throws Exception {
		PageData getPd = this.getPageData();

		PageData kpi = kpiService.findByCode(getPd);
		return kpi;
	}

	/**
	 * 显示结构
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShowColModel")
	public @ResponseBody CommonBase getShowColModel() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();

		switch (getPd.getString("KPI_CODE")) {
		case "Z1AM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			break;
		case "Z1AM2":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			break;
		case "Z1FI1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			break;
		case "Z1MM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			break;
		case "Z1MM2":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "POSITION_NO", "TO_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "POSITION_NO", "TO_NO");
			break;
		case "Z1PS1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "FACTORY_CODE");
			break;
		case "Z2AM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE");
			break;
		case "Z2MM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "MATERIAL_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "MATERIAL_CODE");
			break;
		case "Z2PM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "EQUIPMENT_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "EQUIPMENT_CODE");
			break;
		case "Z2PS1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "WBS_INTER_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "WBS_INTER_CODE");
			break;
		case "Z3AM1":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			break;
		case "Z3FI2":
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "COMPANY_CODE", "VOUCHER_NO");
			break;
		default:
			// 获取带__的列，后续删除之类的有用
			keyListBase = Arrays.asList("BUSI_DATE", "KPI_CODE", "COMPANY_CODE");
			// 设置必定不为空的列
			MustInputList = Arrays.asList("BUSI_DATE", "KPI_CODE", "COMPANY_CODE", "TOTAL_SCORE", "PROPORTION");
			MustNotEditList = Arrays.asList("BUSI_DATE", "KPI_CODE", "COMPANY_CODE", "TOTAL_SCORE", "PROPORTION");
			break;
		}
		String jqGridColModel = "";
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, departmentService,
				userService, keyListBase, null, "", MustInputList, null);
		if (getPd.getString("KPI_CODE").contains("total")) {
			this.map_SetColumnsList = Common.GetSetColumnsListByTableName("tb_kh_total", "", "9870", tmplconfigService);
			this.map_HaveColumnsList = Common.GetHaveColumnsMapByTableName("tb_kh_total", tmplconfigService);
			/*
			 * this.map_SetColumnsList.clear();
			 * 
			 * this.map_SetColumnsList.put("BUSI_DATE", new
			 * TmplConfigDetail("BUSI_DATE", "业务期间", "0", false));
			 * this.map_SetColumnsList.put("KPI_CODE", new
			 * TmplConfigDetail("KPI_CODE", "指标代码", "0", false));
			 * this.map_SetColumnsList.put("COMPANY_CODE", new
			 * TmplConfigDetail("COMPANY_CODE", "公司代码", "1", false));
			 * this.map_SetColumnsList.put("COMPANY_NAME", new
			 * TmplConfigDetail("COMPANY_NAME", "公司代码描述", "1", false));
			 * this.map_SetColumnsList.put("KH_TOTAL_NUM", new
			 * TmplConfigDetail("KH_TOTAL_NUM", "考核项总数", "1", true));
			 */

			jqGridColModel = tmpl.generateStructureByTableCode("tb_kh_total", "", "9870", 3, MustNotEditList);
		} else {

			this.map_SetColumnsList = Common.GetSetColumnsList(getPd.getString("KPI_CODE"), "", "9870",
					tmplconfigService);
			this.map_HaveColumnsList = Common.GetHaveColumnsList(getPd.getString("KPI_CODE"), tmplconfigService);
			/*
			 * this.map_DicList = Common.GetDicList(getPd.getString("KPI_CODE"),
			 * "", "9870", tmplconfigService, tmplconfigdictService,
			 * dictionariesService, departmentService, userService, "");
			 */

			jqGridColModel = tmpl.generateStructure(getPd.getString("KPI_CODE"), "", "9870", 3, MustNotEditList);
		}
		commonBase.setCode(0);
		commonBase.setMessage(jqGridColModel);

		return commonBase;
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
		// 页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");

		TransferPd(getPd, SelectedBusiDate);

		// 多条件过滤条件
		String filters = getPd.getString("filters");
		if (null != filters && !"".equals(filters)) {
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String departCode = user.getUNIT_CODE();
		getPd.put("DEPART_CODE", departCode);
		page.setPd(getPd);
		List<PageData> varList = assessDataService.JqPage(page);
		int records = assessDataService.countJqGridExtend(page);
		PageData userdata = null;
		if (SqlUserdata != null && !SqlUserdata.toString().trim().equals("")) {
			// 底行显示的求和与平均值字段
			getPd.put("Userdata", SqlUserdata.toString());
			userdata = assessDataService.getFooterSummary(page);
		}

		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		result.setUserdata(userdata);

		return result;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRankList")
	public @ResponseBody PageResult<PageData> getRankList() throws Exception {
		PageData getPd = this.getPageData();
		// 页面选择区间
		// String SelectedBusiDate = getPd.getString("SelectedBusiDate");

		// 多条件过滤条件
		String filters = getPd.getString("filters");
		if (null != filters && !"".equals(filters)) {
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		List<PageData> varList = assessDataService.JqPageRank(getPd);
		// int records = assessDataService.countJqGridExtend(page);

		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		// result.setRowNum(page.getRowNum());
		// result.setRecords(records);
		// result.setPage(page.getPage());

		return result;
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public @ResponseBody CommonBase edit() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		String kpiCode = getPd.getString("KPI_CODE_SELECT");
		// 操作
		String oper = getPd.getString("oper");
		List<PageData> listData = new ArrayList<PageData>();
		PageData getKpi = new PageData();
		getKpi.put("KPI_CODE", kpiCode);
		PageData pdKpi = kpiService.findByCode(getKpi);
		if (oper.equals("add")) {
			// 判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
			if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
				return commonBase;
			}
			getPd.put("BUSI_DATE", SelectedBusiDate);
			if (pdKpi != null) {
				getPd.put("TOTAL_SCORE", pdKpi.get("TOTAL_SCORE"));
				getPd.put("PROPORTION", pdKpi.get("PROPORTION"));
			}
			Common.setModelDefault(getPd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
			for (String strFeild : keyListBase) {
				getPd.put(strFeild + TmplUtil.keyExtra, "");
			}
			listData.add(getPd);
		} else {
			for (String strFeild : MustNotEditList) {
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
			if (pdKpi != null) {
				getPd.put("TOTAL_SCORE", pdKpi.get("TOTAL_SCORE"));
				getPd.put("PROPORTION", pdKpi.get("PROPORTION"));
			}
			Common.setModelDefault(getPd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
			listData.add(getPd);
		}
		/*
		 * String checkState = CheckState(listData); if(checkState!=null &&
		 * !checkState.trim().equals("")){ commonBase.setCode(2);
		 * commonBase.setMessage(checkState); return commonBase; }
		 */
		assessDataService.batchUpdateDatabase(listData, kpiCode);
		commonBase.setCode(0);
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
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();

		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);
		String kpiCode = getPd.getString("KPI_CODE");
		if (null != listData && listData.size() > 0) {
			for (PageData pdData : listData) {
				for (String strFeild : MustNotEditList) {
					pdData.put(strFeild, pdData.get(strFeild + TmplUtil.keyExtra));
				}
				PageData pdKpi = kpiService.findByCode(getPd);
				if (pdKpi != null) {
					pdData.put("TOTAL_SCORE", pdKpi.get("TOTAL_SCORE"));
					pdData.put("PROPORTION", pdKpi.get("PROPORTION"));
				}
				Common.setModelDefault(pdData, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
			}
			/*
			 * String checkState = CheckRepet(listData); if(checkState!=null &&
			 * !checkState.trim().equals("")){ commonBase.setCode(2);
			 * commonBase.setMessage(checkState); return commonBase; }
			 */
			assessDataService.batchUpdateDatabase(listData, kpiCode);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAll")
	public @ResponseBody CommonBase deleteAll() throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "delete")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		String kpiCode = getPd.getString("KPI_CODE");
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);
		if (null != listData && listData.size() > 0) {
			assessDataService.deleteAll(listData, kpiCode);
			commonBase.setCode(0);
		}
		return commonBase;
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
		// 页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		// 页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		String kpiCode = getPd.getString("KPI_CODE");
		if (commonBase.getCode() == -1) {
			// 判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
			if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			}
		}

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("tip", "1");
		mv.addObject("local", "assessData");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("KPI_CODE", kpiCode);
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
	/// *
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		// 页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		// 页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		String kpiCode = getPd.getString("KPI_CODE");
		// 判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
		if (strGetCheckMustSelected != null && !strGetCheckMustSelected.trim().equals("")) {
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		}
		List<PageData> listAdd = new ArrayList<PageData>();
		if (commonBase.getCode() == -1) {
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
				if (map_SetColumnsList != null && map_SetColumnsList.size() > 0) {
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
					}
				}

				// 调用解析工具包
				testExcel = new LeadingInExcelToPageData<PageData>(formart);
				// 解析excel，获取客户信息集合

				uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
						titleAndAttribute, map_HaveColumnsList, map_SetColumnsList, map_DicList, false, false, null,
						null);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("读取Excel文件错误", e);
				throw new CustomException("读取Excel文件错误:" + e.getMessage(), false);
			}
			boolean judgement = false;

			Map<String, String> returnErrorCostomn = (Map<String, String>) uploadAndReadMap.get(2);
			Map<String, String> returnErrorMust = (Map<String, String>) uploadAndReadMap.get(3);

			List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);

			if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString())
					&& listUploadAndRead.size() >= 1) {
				judgement = true;
			}
			if (judgement) {
				int listSize = listUploadAndRead.size();
				if (listSize > 0) {
					List<String> sbRetFeild = new ArrayList<String>();
					String strRetUserCode = "";
					String sbRetMust = "";
					String strErrorMessage = "";
					for (int i = 0; i < listSize; i++) {
						PageData pdAdd = listUploadAndRead.get(i);
						if (pdAdd.size() <= 0) {
							continue;
						}

						String getBUSI_DATE = (String) pdAdd.get("BUSI_DATE");
						if (!(getBUSI_DATE != null && !getBUSI_DATE.trim().equals(""))) {
							pdAdd.put("BUSI_DATE", ShowDataBusiDate);
							getBUSI_DATE = ShowDataBusiDate;
						}
						if (kpiCode.contains("total")) {
							String[] kpiCodes = kpiCode.split("-");
							pdAdd.put("KPI_CODE", kpiCodes[1]);
						} else {
							pdAdd.put("KPI_CODE", kpiCode);
						}

						PageData pdKpi = kpiService.findByCode(pdAdd);

						pdAdd.put("TOTAL_SCORE", pdKpi.get("TOTAL_SCORE"));
						pdAdd.put("PROPORTION", pdKpi.get("PROPORTION"));
						if (!ShowDataBusiDate.equals(getBUSI_DATE)) {
							if (!sbRetFeild.contains("导入区间和当前区间必须一致！")) {
								sbRetFeild.add("导入区间和当前区间必须一致！");
							}
						}

						// 必须设置，在查询重复数据时有用
						for (String strFeild : keyListBase) {
							pdAdd.put(strFeild + TmplUtil.keyExtra, pdAdd.get(strFeild));
						}
						// haveColumnsList和map_SetColumnsList，设置保存的数据列及对应值
						Common.setModelDefault(pdAdd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);

						listAdd.add(pdAdd);
					}
					if (strRetUserCode != null && !strRetUserCode.trim().equals("")) {
						commonBase.setCode(2);
						commonBase.setMessage(strRetUserCode);
					} else {
						if (sbRetMust != null && !sbRetMust.trim().equals("")) {
							commonBase.setCode(3);
							commonBase.setMessage("字典无此翻译, 不能导入： " + sbRetMust);
						} else {
							if (sbRetFeild.size() > 0) {
								StringBuilder sbTitle = new StringBuilder();
								for (String str : sbRetFeild) {
									sbTitle.append(str + "  "); // \n
								}
								commonBase.setCode(3);
								commonBase.setMessage(sbTitle.toString());
							} else {
								if (!(listAdd != null && listAdd.size() > 0)) {
									commonBase.setCode(2);
									commonBase.setMessage("请导入符合条件的数据！");
								} else {
									PageData pdCheck = assessDataService.checkRepeat(kpiCode, SelectedBusiDate);
									/*
									 * String checkRepeat =
									 * CheckRepeat(listAdd); if (checkRepeat !=
									 * null && !checkRepeat.trim().equals("")) {
									 * // 重复数据，要在界面提示是否覆盖 //
									 * commonBase.setCode(9);
									 * commonBase.setMessage(checkRepeat); }
									 */
									if (pdCheck != null) {
										// 重复数据，要在界面提示是否覆盖
										commonBase.setCode(9);
										commonBase.setMessage("本月已经上传过考核数据，是否继续导入？继续会将当前指标本月数据清空！");
									} else {

										// 此处执行集合添加
										assessDataService.batchUpdateDatabase(listAdd, kpiCode);
										commonBase.setCode(0);
										commonBase.setMessage(strErrorMessage);
									}
								}
							}
						}
					}
				}
			} else {
				commonBase.setCode(-1);
				commonBase.setMessage("TranslateUtil");
			}
		}

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("tip", "1");
		mv.addObject("local", "assessData");
		mv.addObject("KPI_CODE", kpiCode);
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		mv.addObject("StringDataRows", JSONArray.fromObject(listAdd).toString().replaceAll("'", "%"));//
		return mv;
	}

	/**
	 * 覆盖添加
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/coverAdd")
	public @ResponseBody CommonBase coverAdd() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();

		Object DATA_ROWS = getPd.get("StringDataRows");
		String json = DATA_ROWS.toString();
		json = json.replaceAll("%", "'");
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);
		if (null != listData && listData.size() > 0) {
			String kpiCode = getPd.getString("KPI_CODE");
			// 直接删除添加，不判断重复数据
			assessDataService.batchCoverAdd(listData, kpiCode);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	// public void downExcel(HttpServletResponse response)throws Exception{
	public ModelAndView downExcel(JqPage page) throws Exception {
		PageData getPd = this.getPageData();
		// 页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		// 页面显示数据区间
		// String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		TransferPd(getPd, SelectedBusiDate);

		List<PageData> varOList = assessDataService.exportModel(getPd);
		return export(varOList, getPd.getString("KPI_CODE"), map_SetColumnsList, map_DicList);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception {
		PageData getPd = this.getPageData();

		// 页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		// 多条件过滤条件
		String filters = getPd.getString("filters");
		if (null != filters && !"".equals(filters)) {
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		TransferPd(getPd, SelectedBusiDate);
		page.setPd(getPd);
		List<PageData> varOList = assessDataService.exportList(page);
		return export(varOList, "", map_SetColumnsList, map_DicList);
	}

	@SuppressWarnings("unchecked")
	private ModelAndView export(List<PageData> varOList, String ExcelName, Map<String, TmplConfigDetail> setColumnsList,
			Map<String, Object> dicList) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if (setColumnsList != null && setColumnsList.size() > 0) {
			for (TmplConfigDetail col : setColumnsList.values()) {
				if (col.getCOL_HIDE().equals("1")) {
					titles.add(col.getCOL_NAME());
				}
			}
			if (varOList != null && varOList.size() > 0) {
				for (int i = 0; i < varOList.size(); i++) {
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : setColumnsList.values()) {
						if (col.getCOL_HIDE().equals("1")) {
							String trans = col.getDICT_TRANS();
							Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
							if (getCellValue == null)
								continue;
							if (trans != null && !trans.trim().equals("")) {
								String value = "";
								Map<String, String> dicAdd = (Map<String, String>) dicList.getOrDefault(trans,
										new LinkedHashMap<String, String>());
								value = dicAdd.getOrDefault(getCellValue, "");
								vpd.put("var" + j, value);
							} else {
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

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelRank")
	public ModelAndView exportExcelRank() throws Exception {
		PageData getPd = this.getPageData();
		// 多条件过滤条件
		String filters = getPd.getString("filters");
		if (null != filters && !"".equals(filters)) {
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		List<PageData> varOList = assessDataService.JqPageRank(getPd);
		return export(varOList, "", map_SetColumnsList, map_DicList);
	}

	/**
	 * 查询重复数据
	 * 
	 * @param response
	 * @throws Exception
	 */
	/*
	 * private String CheckRepeat(List<PageData> listData) throws Exception {
	 * String strRut = ""; List<PageData> listRepeat =
	 * assessDataService.getRepeat(listData); if (listRepeat != null &&
	 * listRepeat.size() > 0) { for (PageData each : listRepeat) { strRut +=
	 * each.getString("STAFF_IDENT") + Message.HaveRepeatRecord; } } return
	 * strRut; }
	 */

	private void TransferPd(PageData getPd, String SelectedBusiDate) throws Exception {
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if (!(SelectedBusiDate != null && !SelectedBusiDate.trim().equals(""))) {
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);

		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if (null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())) {
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
	}

	private String CheckMustSelectedAndSame(String BusiDate, String ShowDataBusiDate) throws Exception {
		String strRut = "";
		if (!(BusiDate != null && !BusiDate.trim().equals(""))) {
			strRut += "查询条件中的区间必须设置！";
		} else {
			if (!BusiDate.equals(ShowDataBusiDate)) {
				strRut += "查询条件中区间与页面显示数据区间不一致，请单击查询再进行操作！";
			}
		}
		return strRut;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
