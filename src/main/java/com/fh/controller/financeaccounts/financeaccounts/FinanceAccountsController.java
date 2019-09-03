package com.fh.controller.financeaccounts.financeaccounts;

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
import com.fh.controller.common.AcconutsShowList;
import com.fh.controller.common.CheckSystemDateTime;
import com.fh.controller.common.Common;
import com.fh.controller.common.Corresponding;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.TmplTypeInfo;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.enums.DurState;
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.enums.TmplType;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.financeaccounts.financeaccounts.FinanceAccountsManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysSealedInfo.syssealedinfo.impl.SysSealedInfoService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明：财务对账
 * 创建人：张晓柳
 * 创建时间：2017-07-28
 */
@Controller
@RequestMapping(value="/financeaccounts")
public class FinanceAccountsController extends BaseController {
	
	String menuUrl = "financeaccounts/list.do"; //菜单地址(权限用)
	@Resource(name="financeaccountsService")
	private FinanceAccountsManager financeaccountsService;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name="syssealedinfoService")
	private SysSealedInfoService syssealedinfoService;
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
	
	//默认的which值
	String DefaultWhile = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();

	//页面显示数据的年月
	//String SystemDateTime = "";
	//页面显示数据的二级单位
	//String UserDepartCode = "";
	//登录人的二级单位是最末层
	//private int departSelf = 0;

	String tbHouseFundSummy = "tb_house_fund_summy";
	String tbSocialIncSummy = "tb_social_inc_summy";
	String tbStaffSummy = "TB_STAFF_summy";

	//枚举类型  
	//String TypeCodeStaffDetail = "";
	//String TypeCodeStaffSummy = "";
	//String TypeCodeStaffListen = "";
	String TypeCodeSocialDetail = TmplType.TB_SOCIAL_INC_DETAIL.getNameKey();
	String TypeCodeSocialSummy = TmplType.TB_SOCIAL_INC_SUMMY.getNameKey();
	String TypeCodeSocialListen = TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey();
	String TypeCodeGoldDetail = TmplType.TB_HOUSE_FUND_DETAIL.getNameKey();
	String TypeCodeGoldSummy = TmplType.TB_HOUSE_FUND_SUMMY.getNameKey();
	String TypeCodeGoldListen = TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey();

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("DEPT_CODE", "USER_GROP");
    //分组字段
	//String GroupbyFeild = new String();
	//分组字段list  查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
    //List<String> keyListBase = new ArrayList<String>();
    //查询的所有可操作的责任中心
    //List<String> AllDeptCode = new ArrayList<String>();
    //设置必定不用汇总的数值列            SERIAL_NO 设置字段类型是数字，但不用汇总
    List<String> MustNotSumList = Arrays.asList("SERIAL_NO");
    //设置分组时不求和字段            SERIAL_NO 设置字段类型是数字，但不用求和
    List<String> jqGridGroupNotSumFeild = Arrays.asList("SERIAL_NO");
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FinanceAccounts");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		//查询的所有可操作的责任中心
	    //AllDeptCode = new ArrayList<String>();

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		TmplTypeInfo implTypeCode = getWhileValueToTypeCode(SelectedTableNo);
		List<String> keyListBase = implTypeCode.getKeyListBase();

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("financeaccounts/financeaccounts/financeaccounts_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		//当前登录人所在二级单位
		String UserDepartCode = Jurisdiction.getCurrentDepartmentID();//
		
		//while
		getPd.put("which", SelectedTableNo);
		mv.addObject("pd", getPd);
		
		// *********************加载单位树  DEPT_CODE*******************************
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService);
		if(DepartmentSelectTreeSource.equals("0"))
		{
			//this.departSelf = 1;
			getPd.put("departTreeSource", DepartmentSelectTreeSource);
			//AllDeptCode.add(UserDepartCode);
		} else {
			//departSelf = 0;
			getPd.put("departTreeSource", 1);
	        //JSONArray jsonArray = JSONArray.fromObject(DepartmentSelectTreeSource);  
			//List<PageData> listDepart = (List<PageData>) JSONArray.toCollection(jsonArray, PageData.class);
			//if(listDepart!=null && listDepart.size()>0){
			//	for(PageData pdDept : listDepart){
			//		AllDeptCode.add(pdDept.getString(DictsUtil.Id));
			//	}
			//}
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// ***********************************************************
				
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService, keyListBase, null, null, null, jqGridGroupNotSumFeild);
		//String jqGridColModel = tmpl.generateStructureAccount(SelectedTableNo, UserDepartCode);
		//mv.addObject("jqGridColModel", jqGridColModel);

		return mv;
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
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		List<String> AllDeptCode = Common.getAllDeptCode(departmentService, Jurisdiction.getCurrentDepartmentID());
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		TmplTypeInfo implTypeCode = getWhileValueToTypeCode(SelectedTableNo);
		String TypeCodeStaffSummy = "";//implTypeCode.getTypeCodeSummy();
		//String TypeCodeStaffListen = implTypeCode.getTypeCodeListen();
		String GroupbyFeild = implTypeCode.getGroupbyFeild();
		List<String> keyListBase = implTypeCode.getKeyListBase();

		PageData getQueryFeildPd = new PageData();
		//工资分的类型, 只有工资返回值
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		//if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
		//	QueryFeild += " and 1 != 1 ";
		//}
		if(CheckStaffOrNot(SelectedTableNo)){
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		}
		QueryFeild += " and DEPT_CODE in (" + QueryFeildString.tranferListValueToSqlInString(AllDeptCode) + ") ";
		getPd.put("QueryFeild", QueryFeild);

		String summyTableName = getSummyTableCode(SelectedTableNo);
		String auditeTableName = getAuditeTableCode(SelectedTableNo);
		String detailTableName = getDetailTableCode(SelectedTableNo);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		//页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		//汇总字段
		getPd.put("GroupbyFeild", GroupbyFeild);
		
		//界面对比的表结构
		List<TableColumns> tableSumColumns = tmplconfigService.getTableColumns(summyTableName);
		
		//获取明细汇总信息
		List<TableColumns> tableDetailColumns = tmplconfigService.getTableColumns(detailTableName);
		String detailSelectFeild = Common.getSumFeildSelect(keyListBase, tableDetailColumns, MustNotSumList, TmplUtil.keyExtra);
		getPd.put("SelectFeild", detailSelectFeild);
		//表名
		getPd.put("TableName", detailTableName);
		//上报
		String detailReport = " and (BUSI_DATE, DEPT_CODE, CUST_COL7) in (";
		detailReport += "                                      select RPT_DUR, RPT_DEPT, BILL_OFF ";
		detailReport += "                                      from tb_sys_sealed_info ";
		detailReport += "                                      where STATE = '" + DurState.Sealed.getNameKey() + "' ";
		detailReport += "                                      and BILL_TYPE = '" + getDetailTypeCode(SelectedTableNo) + "' ";
		detailReport += "                                      ) ";
		detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbHouseFundSummy);
		detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbSocialIncSummy);
		detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbStaffSummy);
		
		//detailReport += FilterBillCode.getReportListenNotSummy(tbHouseFundSummy, TypeCodeGoldSummy, TypeCodeGoldListen);
		//detailReport += FilterBillCode.getReportListenNotSummy(tbSocialIncSummy, TypeCodeSocialSummy, TypeCodeSocialListen);
		//detailReport += FilterBillCode.getReportListenNotSummy(tbStaffSummy, TypeCodeStaffSummy, TypeCodeStaffListen);
		
		getPd.put("CheckReport", detailReport);
		page.setPd(getPd);
		List<PageData> detailSummayList = financeaccountsService.JqPage(page);

		//获取对账汇总信息
		List<TableColumns> tableAuditeColumns = tmplconfigService.getTableColumns(auditeTableName);
		String auditeSelectFeild = Common.getSumFeildSelect(keyListBase, tableAuditeColumns, MustNotSumList, TmplUtil.keyExtra);
		getPd.put("SelectFeild", auditeSelectFeild);
		//表名
		getPd.put("TableName", auditeTableName);
		//上报
		getPd.put("CheckReport", "");
		page.setPd(getPd);
		List<PageData> auditeSummayList = financeaccountsService.JqPage(page);
		
		List<PageData> varList = AcconutsShowList.getShowListAll(detailSummayList, auditeSummayList, tableSumColumns, keyListBase, TmplUtil.keyExtra);
		int records = varList.size();
		List<PageData> showList = AcconutsShowList.getShowListPage(varList, page.getPage(), page.getRowNum());
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(showList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		
		return result;
	}

	/**明细显示结构
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getDetailColModel")
	public @ResponseBody CommonBase getDetailColModel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"getDetailColModel");
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		// 字典
		excel_dicList = new LinkedHashMap<String, Object>();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		excel_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
		//导出数据
		excelListData = new ArrayList<PageData>();

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String SelectedTabType = getPd.getString("SelectedTabType");
		
		String strTapTypeCode = getDetailTypeCode(SelectedTableNo, SelectedTabType);

		Object DATA_ROWS = getPd.get("GetDetailTransferList");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json); 
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String DEPT_CODE = "";
		if(listData!=null && listData.size()>0){
			listData.get(0).getString("DEPT_CODE__");
		}
		
		List<String> resetList = Arrays.asList("USER_CODE");
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService,resetList, null, null, null, jqGridGroupNotSumFeild);
		//String detailColModel = tmpl.generateStructureAccount(strTapTypeCode, DEPT_CODE);

		// 字典
		//excel_dicList = tmpl.getDicList();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		//excel_SetColumnsList = tmpl.getSetColumnsList();
		
		commonBase.setCode(0);
		//commonBase.setMessage(detailColModel);
		
		return commonBase;
	}

	/**明细数据
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getDetailList")
	public @ResponseBody PageResult<PageData> getDetailList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"getDetailList");
		//导出数据
		excelListData = new ArrayList<PageData>();

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String SelectedTabType = getPd.getString("SelectedTabType");
		////单位
		//String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//if(departSelf == 1){
		//	SelectedDepartCode = UserDepartCode;
		//}
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		TmplTypeInfo implTypeCode = getWhileValueToTypeCode(SelectedTableNo);
		String TypeCodeStaffSummy = "";//implTypeCode.getTypeCodeSummy();
		//String TypeCodeStaffListen = implTypeCode.getTypeCodeListen();
		List<String> keyListBase = implTypeCode.getKeyListBase();

		Object DATA_ROWS = getPd.get("GetDetailListTransferList");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json); 
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String DEPT_CODE = "";
		if(listData!=null && listData.size()>0){
			DEPT_CODE = listData.get(0).getString("DEPT_CODE__");
		}
		
		String whereSql = "";
		String whereSqlFirst = "";
		String whereSqlSecond = "";
		whereSql += " where BUSI_DATE = '" + SystemDateTime + "' ";

		PageData getQueryFeildPd = new PageData();
		//工资分的类型, 只有工资返回值
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", DEPT_CODE);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(DEPT_CODE != null && !DEPT_CODE.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(CheckStaffOrNot(SelectedTableNo)){
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		}
		whereSql += QueryFeild;
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters.trim())){
			whereSql += SqlTools.constructWhere(filters,null);
		}
		//分组字段
		if(keyListBase!=null){
			for(String feild : keyListBase){
				if(feild != null && !feild.trim().equals("")){
					whereSql += " and " + feild + " = '" + listData.get(0).get(feild + TmplUtil.keyExtra) + "' ";
				}
			}
		}
		whereSqlFirst = whereSql;
		//whereSqlFirst += getDetailHelpful(SelectedTabType, true, TypeCodeStaffSummy, TypeCodeStaffListen);
		whereSqlSecond = whereSql;
		//whereSqlSecond += getDetailHelpful(SelectedTabType, false, TypeCodeStaffSummy, TypeCodeStaffListen);
		
		String firstTableName = getDetailTableCode(SelectedTableNo, SelectedTabType, true);
		getPd.put("TableName", firstTableName);
		getPd.put("whereSql", whereSqlFirst);
		page.setPd(getPd);
		List<PageData> listFirst = financeaccountsService.dataListDetail(page);
		
		String secondTableName = getDetailTableCode(SelectedTableNo, SelectedTabType, false);
		getPd.put("TableName", secondTableName);
		getPd.put("whereSql", whereSqlSecond);
		page.setPd(getPd);
		List<PageData> listSecond = financeaccountsService.dataListDetail(page);

		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		//TmplConfigDetail item = new TmplConfigDetail();
		//item.setDEPT_CODE(departCode);
		//item.setTABLE_CODE(falseTableName);
		//List<TmplConfigDetail> m_columnsList = tmplconfigService.listNeed(item);
		//界面对比的表结构
		List<TableColumns> tableSumColumns = tmplconfigService.getTableColumns(firstTableName);

		List<String> listMatchFeild = Arrays.asList("USER_CODE");
		List<PageData> varList = AcconutsShowList.getShowListAll(listFirst, listSecond, tableSumColumns, listMatchFeild, "");
		//导出数据
		excelListData = varList;
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		return result;
	}


	// 字典
	private Map<String, Object> excel_dicList = new LinkedHashMap<String, Object>();
	// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	private Map<String, TmplConfigDetail> excel_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	//导出数据
	List<PageData> excelListData = new ArrayList<PageData>();
	
	/**导出数据
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/excel")
	public ModelAndView excel() throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		dataMap.put("filename", "");
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if(excel_SetColumnsList != null && excel_SetColumnsList.size() > 0){
		    for (TmplConfigDetail col : excel_SetColumnsList.values()) {
				if(col.getCOL_HIDE().equals("1")){
					titles.add(col.getCOL_NAME());
				}
			}
			if(excelListData!=null && excelListData.size()>0){
				for(int i=0;i<excelListData.size();i++){
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : excel_SetColumnsList.values()) {
						if(col.getCOL_HIDE().equals("1")){
						    String trans = col.getDICT_TRANS();
						    Object getCellValue = excelListData.get(i).get(col.getCOL_CODE().toUpperCase());
						    if(trans != null && !trans.trim().equals("")){
							    String value = "";
							    Map<String, String> dicAdd = (Map<String, String>) excel_dicList.getOrDefault(trans, new LinkedHashMap<String, String>());
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
		mv = new ModelAndView(erv,dataMap); 
		return mv;
	}

	private TmplTypeInfo getWhileValueToTypeCode(String which) throws Exception{
		TmplTypeInfo retItem = new TmplTypeInfo();

		String strKeyCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())) {
				strKeyCode = SysConfigKeyCode.ChkStaffContractGRP;
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())) {
				strKeyCode = SysConfigKeyCode.ChkStaffMarketGRP;
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())) {
				strKeyCode = SysConfigKeyCode.ChkStaffSysLaborGRP;
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())) {
				strKeyCode = SysConfigKeyCode.ChkStaffOperLaborGRP;
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				strKeyCode = SysConfigKeyCode.ChkStaffLaborGRP;
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())) {
				strKeyCode = SysConfigKeyCode.ChkSocialGRP;
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())) {
				strKeyCode = SysConfigKeyCode.ChkHouseGRP;
			}
		}
		String patGroupbyFeild = "";
		if(strKeyCode != null && !strKeyCode.trim().equals("")){
			PageData pd = new PageData();
			pd.put("KEY_CODE", strKeyCode);
			patGroupbyFeild = sysConfigManager.getSysConfigByKey(pd);
		}
		List<String> listGroupbyFeild = QueryFeildString.tranferStringToList(patGroupbyFeild);
		if(!(listGroupbyFeild!=null&&listGroupbyFeild.contains("BUSI_DATE"))){
			if(patGroupbyFeild!=null && !patGroupbyFeild.trim().equals("")){
				patGroupbyFeild += ",";
			}
			patGroupbyFeild += "BUSI_DATE";
			listGroupbyFeild.add("BUSI_DATE");
		}
		if(!(listGroupbyFeild!=null&&listGroupbyFeild.contains("DEPT_CODE"))){
			if(patGroupbyFeild!=null && !patGroupbyFeild.trim().equals("")){
				patGroupbyFeild += ",";
			}
			patGroupbyFeild += "DEPT_CODE";
			listGroupbyFeild.add("DEPT_CODE");
		}
	    //分组字段
		retItem.setGroupbyFeild(patGroupbyFeild);
		//分组字段list  查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
		retItem.setKeyListBase(listGroupbyFeild);
	    
		//枚举类型 TmplType
		String TypeCodeStaffDetail = "";
		String TypeCodeStaffSummy = "";
		String TypeCodeStaffListen = "";
		if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())){
			//合同化
			TypeCodeStaffDetail = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();
			TypeCodeStaffSummy = TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey();
			TypeCodeStaffListen = TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey();
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())){
			//市场化
			TypeCodeStaffDetail = TmplType.TB_STAFF_DETAIL_MARKET.getNameKey();
			TypeCodeStaffSummy = TmplType.TB_STAFF_SUMMY_MARKET.getNameKey();
			TypeCodeStaffListen = TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey();
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())){
			//系统内劳务
			TypeCodeStaffDetail = TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey();
			TypeCodeStaffSummy = TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey();
			TypeCodeStaffListen = TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey();
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())){
			//运行人员
			TypeCodeStaffDetail = TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey();
			TypeCodeStaffSummy = TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey();
			TypeCodeStaffListen = TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey();
		}
		if(which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())){
			//劳务派遣工资
			TypeCodeStaffDetail = TmplType.TB_STAFF_DETAIL_LABOR.getNameKey();
			TypeCodeStaffSummy = TmplType.TB_STAFF_SUMMY_LABOR.getNameKey();
			TypeCodeStaffListen = TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey();
		}
		retItem.setTypeCodeDetail(TypeCodeStaffDetail);
		//retItem.setTypeCodeSummy(TypeCodeStaffSummy);
		//retItem.setTypeCodeListen(TypeCodeStaffListen);
		return retItem;
	}
    private String getDetailTypeCode(String which){
		String strKeyCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())) {
				strKeyCode = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())) {
				strKeyCode = TmplType.TB_STAFF_DETAIL_MARKET.getNameKey();
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())) {
				strKeyCode = TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey();
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())) {
				strKeyCode = TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey();
			} else if(which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				strKeyCode = TmplType.TB_STAFF_DETAIL_LABOR.getNameKey();
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())) {
				strKeyCode = TmplType.TB_SOCIAL_INC_DETAIL.getNameKey();
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())) {
				strKeyCode = TmplType.TB_HOUSE_FUND_DETAIL.getNameKey();
			}
		}
		return strKeyCode;
    }
	private Boolean CheckStaffOrNot(String which) {
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据前端业务表索引获取表名称
	 * 
	 * @param which
	 * @return
	 */
	private String getDetailTableCode(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				tableCode = "tb_staff_detail";
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())) {
				tableCode = "tb_social_inc_detail";
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())) {
				tableCode = "tb_house_fund_detail";
			}
		}
		return tableCode;
	}
	private String getSummyTableCode(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				tableCode = tbStaffSummy;
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())) {
				tableCode = tbSocialIncSummy;
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())) {
				tableCode = tbHouseFundSummy;
			}
		}
		return tableCode;
	}
	private String getAuditeTableCode(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				tableCode = "tb_staff_audit";
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())) {
				tableCode = "tb_social_inc_audit";
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())) {
				tableCode = "tb_house_fund_audit";
			}
		}
		return tableCode;
	}
    private String getDetailTableCode(String which, String tabType, Boolean bolFirst) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				if("1".equals(tabType)){
					tableCode = bolFirst ? "tb_staff_detail" : "tb_staff_audit";
				} else if("2".equals(tabType)){
					tableCode = bolFirst ? "tb_staff_audit" : "tb_staff_detail";
				}
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())) {
				if("1".equals(tabType)){
					tableCode = bolFirst ? "tb_social_inc_detail" : "tb_social_inc_audit";
				} else if("2".equals(tabType)){
					tableCode = bolFirst ? "tb_social_inc_audit" : "tb_social_inc_detail";
				}
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())) {
				if("1".equals(tabType)){
					tableCode = bolFirst ? "tb_house_fund_detail" : "tb_house_fund_audit";
				} else if("2".equals(tabType)){
					tableCode = bolFirst ? "tb_house_fund_audit" : "tb_house_fund_detail";
				}
			}
		}
		return tableCode;
	}
    private String getDetailTypeCode(String which, String tabType) {
		String typeCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())) {
				if("1".equals(tabType)){
					typeCode = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();
				} else if("2".equals(tabType)){
					typeCode = TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey();
				}
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())) {
				if("1".equals(tabType)){
					typeCode = TmplType.TB_STAFF_DETAIL_MARKET.getNameKey();
				} else if("2".equals(tabType)){
					typeCode = TmplType.TB_STAFF_AUDIT_MARKET.getNameKey();
				}
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())) {
				if("1".equals(tabType)){
					typeCode = TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey();
				} else if("2".equals(tabType)){
					typeCode = TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey();
				}
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())) {
				if("1".equals(tabType)){
					typeCode = TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey();
				} else if("2".equals(tabType)){
					typeCode = TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey();
				}
			} else if (which.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())) {
				if("1".equals(tabType)){
					typeCode = TmplType.TB_STAFF_DETAIL_LABOR.getNameKey();
				} else if("2".equals(tabType)){
					typeCode = TmplType.TB_STAFF_AUDIT_LABOR.getNameKey();
				}
			} else if (which.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())) {
				if("1".equals(tabType)){
					typeCode = TmplType.TB_SOCIAL_INC_DETAIL.getNameKey();
				} else if("2".equals(tabType)){
					typeCode = TmplType.TB_SOCIAL_INC_AUDIT.getNameKey();
				}
			} else if (which.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())) {
				if("1".equals(tabType)){
					typeCode = TmplType.TB_HOUSE_FUND_DETAIL.getNameKey();
				} else if("2".equals(tabType)){
					typeCode = TmplType.TB_HOUSE_FUND_AUDIT.getNameKey();
				}
			}
		}
		return typeCode;
	}
    
	private String getDetailHelpful(String tabType, Boolean bolFirst,
			String TypeCodeStaffSummy, String TypeCodeStaffListen) {
		String detailReport = "";
		if("1".equals(tabType)){
			if(bolFirst){
				detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbHouseFundSummy);
				detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbSocialIncSummy);
				detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbStaffSummy);
				
			//	detailReport += FilterBillCode.getReportListenNotSummy(tbHouseFundSummy, TypeCodeGoldSummy, TypeCodeGoldListen);
				//detailReport += FilterBillCode.getReportListenNotSummy(tbSocialIncSummy, TypeCodeSocialSummy, TypeCodeSocialListen);
				//detailReport += FilterBillCode.getReportListenNotSummy(tbStaffSummy, TypeCodeStaffSummy, TypeCodeStaffListen);
			}
		} else if("2".equals(tabType)){
			if(!bolFirst){
				detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbHouseFundSummy);
				detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbSocialIncSummy);
				detailReport += QueryFeildString.getBillCodeNotInSumInvalidDetail(tbStaffSummy);
				
				//detailReport += FilterBillCode.getReportListenNotSummy(tbHouseFundSummy, TypeCodeGoldSummy, TypeCodeGoldListen);
				//detailReport += FilterBillCode.getReportListenNotSummy(tbSocialIncSummy, TypeCodeSocialSummy, TypeCodeSocialListen);
				//detailReport += FilterBillCode.getReportListenNotSummy(tbStaffSummy, TypeCodeStaffSummy, TypeCodeStaffListen);
			}
		}
		return detailReport;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
