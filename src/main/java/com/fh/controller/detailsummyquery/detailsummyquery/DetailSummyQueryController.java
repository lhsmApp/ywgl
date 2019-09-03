package com.fh.controller.detailsummyquery.detailsummyquery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.fh.controller.common.Corresponding;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.SelectBillCodeOptions;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.TmplTypeInfo;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Jurisdiction;
import com.fh.util.enums.BillState;
import com.fh.util.enums.TmplType;

import net.sf.json.JSONArray;

import com.fh.service.detailimportcommon.detailimportcommon.impl.DetailImportCommonService;
import com.fh.service.detailsummyquery.detailsummyquery.DetailSummyQueryManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明： 明细汇总查询
 * 创建人：张晓柳
 * 创建时间：2017-08-09
 * @version
 */
@Controller
@RequestMapping(value="/detailsummyquery")
public class DetailSummyQueryController extends BaseController {
	
	String menuUrl = "detailsummyquery/list.do"; //菜单地址(权限用)
	@Resource(name="detailsummyqueryService")
	private DetailSummyQueryManager detailsummyqueryryService;
	@Resource(name="detailimportcommonService")
	private DetailImportCommonService detailimportcommonService;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name="tmplconfigdictService")
	private TmplConfigDictService tmplconfigdictService;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;

	//全部凭证单据
	String SelectBillCodeFirstShow = "全部凭证单据";
	String SelectBillCodeLastShow = "";
	//默认的which值
	String DefaultWhile = TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey();
	// 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
	private List<String> keyListBase = Arrays.asList("BILL_CODE", "DEPT_CODE", "CUST_COL7");

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BUSI_DATE", "DEPT_CODE", "USER_GROP", "CUST_COL7");
    //设置分组时不求和字段            SERIAL_NO 设置字段类型是数字，但不用求和
    List<String> jqGridGroupNotSumFeild = Arrays.asList("SERIAL_NO");

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表detailsummyquery");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("detailsummyquery/detailsummyquery/detailsummyquery_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime);
		//while
		getPd.put("which", SelectedTableNo);
		//单号下拉列表
		getPd.put("SelectNoBillCodeShow", SelectBillCodeFirstShow);
		getPd.put("InitBillCodeOptions", SelectBillCodeOptions.getSelectBillCodeOptions(null, SelectBillCodeFirstShow, SelectBillCodeLastShow));
		
		//"BUSI_DATE", "DEPT_CODE", "USER_GROP", "CUST_COL7"
		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		// *********************加载单位树  DEPT_CODE*******************************
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService);
		if(DepartmentSelectTreeSource.equals("0")){
			getPd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			getPd.put("departTreeSource", 1);
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// ***********************************************************

		mv.addObject("pd", getPd);
		return mv;
	}

	/**单号下拉列表
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getBillCodeList")
	public @ResponseBody CommonBase getBillCodeList() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//日期
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		List<String> AllDeptCode = Common.getAllDeptCode(departmentService, Jurisdiction.getCurrentDepartmentID());

		String tableNameSummy = Corresponding.getSumBillTableNameFromTmplType(SelectedTableNo);
		
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		if(departSelf == 1){
			getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		}
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
		QueryFeild += " and DEPT_CODE in (" + QueryFeildString.tranferListValueToSqlInString(AllDeptCode) + ") ";//工资无账套无数据
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(departSelf == 1){
			if(!(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		} else {
			if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("")){
				QueryFeild += " and DEPT_CODE in (" + Common.getSqlNextDeptCode(SelectedDepartCode) + ") ";
			}
		}
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//表名
		getPd.put("TableName", tableNameSummy);
		
		List<String> getCodeList = detailsummyqueryryService.getBillCodeList(getPd);
		String returnString = SelectBillCodeOptions.getSelectBillCodeOptions(getCodeList, SelectBillCodeFirstShow, SelectBillCodeLastShow);
		commonBase.setMessage(returnString);
		commonBase.setCode(0);

		commonBase.setMessage(returnString);
		commonBase.setCode(0);
		
		return commonBase;
	}

	/**显示结构
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getShowColModel")
	public @ResponseBody CommonBase getShowColModel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"getFirstDetailColModel");
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");

		String strShowCalModelDepaet = Jurisdiction.getCurrentDepartmentID();
		if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("") && !SelectedDepartCode.contains(",")){
			strShowCalModelDepaet = SelectedDepartCode;
		}
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService, keyListBase, null, null, null, jqGridGroupNotSumFeild);
		String jqGridColModel = tmpl.generateStructureNoEdit(SelectedTableNo, strShowCalModelDepaet, SelectedCustCol7);

		commonBase.setCode(0);
		commonBase.setMessage(jqGridColModel);
		
		return commonBase;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FinanceAccounts");

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		
		PageData pdTransfer = setTransferPd(getPd);
		//表名
		String tableNameSummy = Corresponding.getSumBillTableNameFromTmplType(SelectedTableNo);
		pdTransfer.put("TableName", tableNameSummy);
		page.setPd(pdTransfer);
		
		List<PageData> varList = detailsummyqueryryService.JqPage(page);	//列出Betting列表
		int records = detailsummyqueryryService.countJqGridExtend(page);
		PageData userdata = null;
		String strShowCalModelDepaet = Jurisdiction.getCurrentDepartmentID();
		if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("") && !SelectedDepartCode.contains(",")){
			strShowCalModelDepaet = SelectedDepartCode;
		}
		//底行显示的求和与平均值字段
		StringBuilder SqlUserdata = Common.GetSqlUserdata(SelectedTableNo, strShowCalModelDepaet, SelectedCustCol7, tmplconfigService);
		if(SqlUserdata!=null && !SqlUserdata.toString().trim().equals("")){
			//底行显示的求和与平均值字段
			getPd.put("Userdata", SqlUserdata.toString());
		    userdata = detailsummyqueryryService.getFooterSummary(page);
		}
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		result.setUserdata(userdata);
		
		return result;
	}

	/**明细显示结构
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getFirstDetailColModel")
	public @ResponseBody CommonBase getFirstDetailColModel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"getFirstDetailColModel");
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//员工组 必须执行，用来设置汇总和传输上报类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		TmplTypeInfo implTypeCode = Corresponding.getWhileValueToTypeCode(SelectedTableNo, sysConfigManager);
		String TypeCodeSummyDetail = implTypeCode.getTypeCodeSummyDetail();
		String TypeCodeDetail = implTypeCode.getTypeCodeDetail();
		List<String> SumFieldDetail = implTypeCode.getSumFieldDetail();
		String DEPT_CODE = (String) getPd.get("DataDeptCode");
		String CUST_COL7 = (String) getPd.get("DataCustCol7");
		String strBillCode = getPd.getString("DetailListBillCode");
		String TableNameFirstItem = Corresponding.getItemAllocDetailTableNameFromTmplType(SelectedTableNo);

		Boolean bolHaveItemList = Common.checkHaveItemList(strBillCode, TableNameFirstItem, detailimportcommonService);
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService,SumFieldDetail, null, null, null, jqGridGroupNotSumFeild);
		if(bolHaveItemList){
			String detailColModel = tmpl.generateStructureNoEdit(TypeCodeDetail, DEPT_CODE, CUST_COL7);
			commonBase.setCode(8);
			commonBase.setMessage(detailColModel);
		} else {
			String detailColModel = tmpl.generateStructureNoEdit(TypeCodeSummyDetail, DEPT_CODE, CUST_COL7);
			commonBase.setCode(9);
			commonBase.setMessage(detailColModel);
		}
		return commonBase;
	}

	/**明细数据
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getFirstDetailList")
	public @ResponseBody PageResult<PageData> getFirstDetailList() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"getFirstDetailList");

		PageData getPd = this.getPageData();
		//员工组 必须执行，用来设置汇总和传输上报类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		TmplTypeInfo implTypeCode = Corresponding.getWhileValueToTypeCode(SelectedTableNo, sysConfigManager);
		List<String> SumFieldDetail = implTypeCode.getSumFieldDetail();
		String strBillCode = getPd.getString("DetailListBillCode");
		
		String detailTableName = Corresponding.getSummyTableNameFromTmplType(SelectedTableNo);
		String TableNameFirstItem = Corresponding.getItemAllocDetailTableNameFromTmplType(SelectedTableNo);
		
		Boolean bolHaveItemList = Common.checkHaveItemList(strBillCode, TableNameFirstItem, detailimportcommonService);
		PageResult<PageData> result = new PageResult<PageData>();
		if(bolHaveItemList){
			PageData pdCode = new PageData();
			String QueryFeild = " and BILL_CODE = '" + strBillCode + "' ";
		    pdCode.put("QueryFeild", QueryFeild);
		    pdCode.put("OrderbyFeild", "ITEM_CODE");
		    pdCode.put("TableName", TableNameFirstItem);
			List<PageData> varList = detailimportcommonService.getDetailList(pdCode);	//列出Betting列表
			result.setRows(varList);
		} else {
			PageData pdCode = new PageData();
			pdCode.put("TableName", detailTableName);
			pdCode.put("BILL_CODE", strBillCode);
			String strFieldSelectKey = QueryFeildString.getFieldSelectKey(SumFieldDetail, TmplUtil.keyExtra);
			if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
				pdCode.put("FieldSelectKey", strFieldSelectKey);
			}
			List<PageData> varList = detailsummyqueryryService.getFirstDetailList(pdCode);	//列出Betting列表
			result.setRows(varList);
		}
		
		return result;
	}

	/**明细显示结构
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getSecondDetailColModel")
	public @ResponseBody CommonBase getSecondDetailColModel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"getSecondDetailColModel");
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		//员工组 必须执行，用来设置汇总和传输上报类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		TmplTypeInfo implTypeCode = Corresponding.getWhileValueToTypeCode(SelectedTableNo, sysConfigManager);
		String TypeCodeDetail = implTypeCode.getTypeCodeDetail();
		
		String DEPT_CODE = (String) getPd.get("DataDeptCode");
		String CUST_COL7 = (String) getPd.get("DataCustCol7");

		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService);
		String detailColModel = tmpl.generateStructureNoEdit(TypeCodeDetail, DEPT_CODE, CUST_COL7);
		
		commonBase.setCode(0);
		commonBase.setMessage(detailColModel);
		
		return commonBase;
	}

	/**明细数据
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getSecondDetailList")
	public @ResponseBody PageResult<PageData> getSecondDetailList() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"getSecondDetailList");

		PageData getPd = this.getPageData();
		//员工组 必须执行，用来设置汇总和传输上报类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		TmplTypeInfo implTypeCode = Corresponding.getWhileValueToTypeCode(SelectedTableNo, sysConfigManager);
		List<String> SumFieldDetail = implTypeCode.getSumFieldDetail();
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        PageData pdGet = listData.get(0);

		String detailTableName = Corresponding.getDetailTableNameFromTmplType(SelectedTableNo);

		PageData pdCode = new PageData();
		String QueryFeild = QueryFeildString.getDetailQueryFeild(pdGet, SumFieldDetail, TmplUtil.keyExtra);
	    if(!(QueryFeild!=null && !QueryFeild.trim().equals(""))){
	    	QueryFeild += " and 1 != 1 ";
	    }
	    pdCode.put("QueryFeild", QueryFeild);
		
	    pdCode.put("TableName", detailTableName);
		List<PageData> varList = detailsummyqueryryService.getSecondDetailList(pdCode);	//列出Betting列表
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		
		return result;
	}

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"导出HouseFundDetail到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		
		String strShowCalModelDepaet = Jurisdiction.getCurrentDepartmentID();
		if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("") && !SelectedDepartCode.contains(",")){
			strShowCalModelDepaet = SelectedDepartCode;
		}
		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, strShowCalModelDepaet, SelectedCustCol7, tmplconfigService);
		Map<String, Object> DicList = Common.GetDicList(SelectedTableNo, strShowCalModelDepaet, SelectedCustCol7, 
				tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, "");
		
		PageData pdTransfer = setTransferPd(getPd);
		//表名
		String tableNameSummy = Corresponding.getSumBillTableNameFromTmplType(SelectedTableNo);
		pdTransfer.put("TableName", tableNameSummy);
		page.setPd(pdTransfer);
		List<PageData> varOList = detailsummyqueryryService.datalistExport(page);

		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		dataMap.put("filename", "");
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if(map_SetColumnsList != null && map_SetColumnsList.size() > 0){
		    for (TmplConfigDetail col : map_SetColumnsList.values()) {
				if(col.getCOL_HIDE().equals("1")){
					titles.add(col.getCOL_NAME());
				}
			}
			if(varOList!=null && varOList.size()>0){
				for(int i=0;i<varOList.size();i++){
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						if(col.getCOL_HIDE().equals("1")){
						    String trans = col.getDICT_TRANS();
						    Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
						    if(trans != null && !trans.trim().equals("")){
							    String value = "";
							    Map<String, String> dicAdd = (Map<String, String>) DicList.getOrDefault(trans, new LinkedHashMap<String, String>());
							    value = dicAdd.getOrDefault(getCellValue, "");
							    vpd.put("var" + j, value);
						    } else {
						    	if(getCellValue != null){
							    	vpd.put("var" + j, getCellValue.toString());
						    	} else {
							    	vpd.put("var" + j, "");
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
	
	private PageData setTransferPd(PageData getPd) throws Exception{
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//日期
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		List<String> AllDeptCode = Common.getAllDeptCode(departmentService, Jurisdiction.getCurrentDepartmentID());
		
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		if(departSelf == 1){
			getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		}
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		QueryFeild += QueryFeildString.getQueryFeildBillCodeSummy(SelectedBillCode, SelectBillCodeFirstShow, SelectBillCodeLastShow);
		QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
		QueryFeild += " and DEPT_CODE in (" + QueryFeildString.tranferListValueToSqlInString(AllDeptCode) + ") ";
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(departSelf == 1){
			if(!(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		} else {
			if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("")){
				QueryFeild += " and DEPT_CODE in (" + Common.getSqlNextDeptCode(SelectedDepartCode) + ") ";
			}
		}
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
		return getPd;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
