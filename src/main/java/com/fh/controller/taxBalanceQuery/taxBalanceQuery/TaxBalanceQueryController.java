package com.fh.controller.taxBalanceQuery.taxBalanceQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.fh.controller.common.CheckSystemDateTime;
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.QueryFeildString;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Jurisdiction;


import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.taxBalanceQuery.taxBalanceQuery.TaxBalanceQueryManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;

/**
 * 个税差额查询
* @ClassName: TaxBalanceQueryController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年8月9日
*
 */
@Controller
@RequestMapping(value="/taxBalanceQuery")
public class TaxBalanceQueryController extends BaseController {
	
	String menuUrl = "taxBalanceQuery/list.do"; //菜单地址(权限用)
	@Resource(name="taxBalanceQueryService")
	private TaxBalanceQueryManager taxBalanceQueryService;
	
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name="tmplconfigdictService")
	private TmplConfigDictService tmplconfigdictService;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name = "userService")
	private UserManager userService;
	

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BUSI_DATE", "BILL_OFF","DEPT_CODE");
   
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	Map<String, Object> DicList = new HashMap<String, Object>();

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TaxBalanceQuery");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("taxBalanceQuery/taxBalanceQuery/taxBalanceQuery_list");

		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);
		
		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		// *********************加载单位树  DEPT_CODE*******************************
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService);
		if(DepartmentSelectTreeSource.equals("0"))
		{
			getPd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			getPd.put("departTreeSource", 1);
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// ***********************************************************
		
		String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);
		
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "期间", "1", false));
		TmplConfigDetail BILL_OFF = new TmplConfigDetail("BILL_OFF", "账套", "1", false);
		BILL_OFF.setDICT_TRANS("FMISACC");
		Map_SetColumnsList.put("BILL_OFF", BILL_OFF);
		Common.getDicValue(DicList, BILL_OFF.getDICT_TRANS(), tmplconfigdictService, dictionariesService, departmentService, userService, "");

		TmplConfigDetail DEPT_CODE = new TmplConfigDetail("DEPT_CODE", "责任中心", "1", false);
		DEPT_CODE.setDICT_TRANS("oa_department");
		Map_SetColumnsList.put("DEPT_CODE", DEPT_CODE);
		Common.getDicValue(DicList, DEPT_CODE.getDICT_TRANS(), tmplconfigdictService, dictionariesService, departmentService, userService, "");
		
		
		Map_SetColumnsList.put("USER_NAME", new TmplConfigDetail("USER_NAME", "员工姓名", "1", false));
		Map_SetColumnsList.put("STAFF_IDENT", new TmplConfigDetail("STAFF_IDENT", "身份证号", "1", false));
		Map_SetColumnsList.put("BANK_NAME", new TmplConfigDetail("BANK_NAME", "开户行", "1", false));
		Map_SetColumnsList.put("BANK_CARD", new TmplConfigDetail("BANK_CARD", "银行帐号", "1", false));
		
		Map_SetColumnsList.put("ACT_SALY1", new TmplConfigDetail("ACT_SALY1", "1月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX1", new TmplConfigDetail("ACCRD_TAX1", "1月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY1", new TmplConfigDetail("LABOR_ACT_SALY1", "1月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX1", new TmplConfigDetail("LABOR_ACCRD_TAX1", "1月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY2", new TmplConfigDetail("ACT_SALY2", "2月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX2", new TmplConfigDetail("ACCRD_TAX2", "2月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY2", new TmplConfigDetail("LABOR_ACT_SALY2", "2月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX2", new TmplConfigDetail("LABOR_ACCRD_TAX2", "2月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY3", new TmplConfigDetail("ACT_SALY3", "3月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX3", new TmplConfigDetail("ACCRD_TAX3", "3月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY3", new TmplConfigDetail("LABOR_ACT_SALY3", "3月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX3", new TmplConfigDetail("LABOR_ACCRD_TAX3", "3月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY4", new TmplConfigDetail("ACT_SALY4", "4月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX4", new TmplConfigDetail("ACCRD_TAX4", "4月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY4", new TmplConfigDetail("LABOR_ACT_SALY4", "4月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX4", new TmplConfigDetail("LABOR_ACCRD_TAX4", "4月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY5", new TmplConfigDetail("ACT_SALY5", "5月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX5", new TmplConfigDetail("ACCRD_TAX5", "5月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY5", new TmplConfigDetail("LABOR_ACT_SALY5", "5月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX5", new TmplConfigDetail("LABOR_ACCRD_TAX5", "5月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY6", new TmplConfigDetail("ACT_SALY6", "6月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX6", new TmplConfigDetail("ACCRD_TAX6", "6月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY6", new TmplConfigDetail("LABOR_ACT_SALY6", "6月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX6", new TmplConfigDetail("LABOR_ACCRD_TAX6", "6月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY7", new TmplConfigDetail("ACT_SALY7", "7月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX7", new TmplConfigDetail("ACCRD_TAX7", "7月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY7", new TmplConfigDetail("LABOR_ACT_SALY7", "7月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX7", new TmplConfigDetail("LABOR_ACCRD_TAX7", "7月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY8", new TmplConfigDetail("ACT_SALY8", "8月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX8", new TmplConfigDetail("ACCRD_TAX8", "8月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY8", new TmplConfigDetail("LABOR_ACT_SALY8", "8月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX8", new TmplConfigDetail("LABOR_ACCRD_TAX8", "8月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY9", new TmplConfigDetail("ACT_SALY9", "9月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX9", new TmplConfigDetail("ACCRD_TAX9", "9月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY9", new TmplConfigDetail("LABOR_ACT_SALY9", "9月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX9", new TmplConfigDetail("LABOR_ACCRD_TAX9", "9月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY10", new TmplConfigDetail("ACT_SALY10", "10月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX10", new TmplConfigDetail("ACCRD_TAX10", "10月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY10", new TmplConfigDetail("LABOR_ACT_SALY10", "10月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX10", new TmplConfigDetail("LABOR_ACCRD_TAX10", "10月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY11", new TmplConfigDetail("ACT_SALY11", "11月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX11", new TmplConfigDetail("ACCRD_TAX11", "11月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY11", new TmplConfigDetail("LABOR_ACT_SALY11", "11月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX11", new TmplConfigDetail("LABOR_ACCRD_TAX11", "11月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY12", new TmplConfigDetail("ACT_SALY12", "12月工资金额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX12", new TmplConfigDetail("ACCRD_TAX12", "12月工资税额", "1", true));
		Map_SetColumnsList.put("LABOR_ACT_SALY12", new TmplConfigDetail("LABOR_ACT_SALY12", "12月劳动报酬金额", "1", true));
		Map_SetColumnsList.put("LABOR_ACCRD_TAX12", new TmplConfigDetail("LABOR_ACCRD_TAX12", "12月劳动报酬税额", "1", true));
		
		Map_SetColumnsList.put("ACT_SALY", new TmplConfigDetail("ACT_SALY", "合计发放额", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX", new TmplConfigDetail("ACCRD_TAX", "合计扣税额", "1", true));
		Map_SetColumnsList.put("ACCRD_TEX_EXT", new TmplConfigDetail("LABOR_ACT_SALY", "实际缴税额", "1", true));
		Map_SetColumnsList.put("TAX_BALANCE", new TmplConfigDetail("LABOR_ACCRD_TAX", "缴费差额", "1", true));
		mv.addObject("pd", getPd);
		return mv;
	}
	

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TaxBalanceQuery");

		PageData getPd = this.getPageData();
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//TransferPd(getPd, SelectedBusiDate, SelectedCustCol7,SelectedDepartCode);//, SelectedDepartCode
		
		String strDeptSqlIn = QueryFeildString.getSqlInString(SelectedDepartCode);
		getPd.put("DeptSqlIn", strDeptSqlIn);
		String strSystemDateTimeYear = CheckSystemDateTime.getSystemDateTimeYear(SelectedBusiDate);
		getPd.put("SelectedBusiDateYear", strSystemDateTimeYear);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		page.setPd(getPd);
		List<PageData> varList = taxBalanceQueryService.JqPage(page);
		int records = taxBalanceQueryService.countJqGridExtend(page);
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		
		return result;
	}

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出TaxBalanceQuery到excel");
	    
		PageData getPd = this.getPageData();
		//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//TransferPd(getPd, SelectedBusiDate, SelectedCustCol7,SelectedDepartCode);//, SelectedDepartCode
		
		page.setPd(getPd);
		List<PageData> varOList = taxBalanceQueryService.exportList(page);
		return export(varOList, "");
	}
	
	/*private void TransferPd(PageData getPd, String SelectedBusiDate, String SelectedCustCol7,String SelectedDepartCode) //, String SelectedDepartCode
			throws Exception{
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
	}*/
	
	@SuppressWarnings("unchecked")
	private ModelAndView export(List<PageData> varOList, String ExcelName) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if(Map_SetColumnsList != null && Map_SetColumnsList.size() > 0){
		    for (TmplConfigDetail col : Map_SetColumnsList.values()) {
				if(col.getCOL_HIDE().equals("1")){
					titles.add(col.getCOL_NAME());
				}
			}
			if(varOList!=null && varOList.size()>0){
				for(int i=0;i<varOList.size();i++){
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : Map_SetColumnsList.values()) {
						if(col.getCOL_HIDE().equals("1")){
						String trans = col.getDICT_TRANS();
						Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
						if(trans != null && !trans.trim().equals("")){
							String value = "";
							Map<String, String> dicAdd = (Map<String, String>) DicList.getOrDefault(trans, new LinkedHashMap<String, String>());
							value = dicAdd.getOrDefault(getCellValue, "");
							vpd.put("var" + j, value);
						} else {
					    	if(getCellValue != null && !getCellValue.toString().trim().equals("")){
					    		if(col.getIsNum()){
							    	vpd.put("var" + j, getCellValue.toString());
					    		} else {
							    	vpd.put("var" + j, getCellValue.toString());
					    		}
					    	} else {
					    		if(col.getIsNum()){
							    	vpd.put("var" + j, "0.00");
					    		} else {
							    	vpd.put("var" + j, " ");
					    		}
					    	}
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
		mv = new ModelAndView(erv,dataMap); 
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
