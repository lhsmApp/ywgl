package com.fh.controller.fundsconfirmquery.fundsconfirmquery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TmplTypeInfo;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Jurisdiction;
import com.fh.util.enums.BillState;
import com.fh.util.enums.FundsConfirmInfoState;
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.enums.SysConfirmInfoBillType;
import com.fh.util.enums.TmplType;

import net.sf.json.JSONArray;

import com.fh.service.fundssummyconfirm.fundssummyconfirm.FundsSummyConfirmManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysConfirmInfo.sysConfirmInfo.impl.SysConfirmInfoService;
import com.fh.service.sysStruMapping.sysStruMapping.impl.SysStruMappingService;
import com.fh.service.sysTableMapping.sysTableMapping.impl.SysTableMappingService;
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
@RequestMapping(value="/fundsconfirmquery")
public class FundsConfirmQueryController extends BaseController {
	
	String menuUrl = "fundsconfirmquery/list.do"; //菜单地址(权限用)
	@Resource(name="fundssummyconfirmService")
	private FundsSummyConfirmManager fundssummyconfirmService;
	@Resource(name="sysConfirmInfoService")
	private SysConfirmInfoService sysConfirmInfoService;
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

	@Resource(name="sysTableMappingService")
	private SysTableMappingService sysTableMappingService;
	@Resource(name="sysStruMappingService")
	private SysStruMappingService sysStruMappingService;

	//表名
	String tb_sys_confirm_info = "tb_sys_confirm_info";
	String tb_staff_detail = "tb_staff_detail";
	String tb_social_inc_detail = "tb_social_inc_detail";
	String tb_house_fund_detail = "tb_house_fund_detail";

	//默认的which值
	String DefaultWhile = TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey();
	////单位
	//String ShowSummyBillColModelDepartCode = DictsUtil.DepartShowAll;
	// 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
	private List<String> keyListBase = Arrays.asList("BILL_CODE", "DEPT_CODE", "CUST_COL7", "BUSI_DATE");

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("USER_GROP", "CUST_COL7", "DEPT_CODE", "BUSI_DATE");
    //设置分组时不求和字段            SERIAL_NO 设置字段类型是数字，但不用求和
    List<String> jqGridGroupNotSumFeild = Arrays.asList("SERIAL_NO");

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FundsConfirmQuery");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fundsconfirmquery/fundsconfirmquery/fundsconfirmquery_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime);
		//while
		getPd.put("which", SelectedTableNo);

		//"USER_GROP", "CUST_COL7"
		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		// *********************加载单位树  DEPT_CODE*******************************
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService, DictsUtil.DepartShowAll_01001);
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// ***********************************************************

		mv.addObject("pd", getPd);
		return mv;
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
		//tab
		String SelectedTabType = getPd.getString("SelectedTabType");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");

		String strShowCalModelDepaet = Jurisdiction.getCurrentDepartmentID();
		if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("") && !SelectedDepartCode.contains(",")){
			strShowCalModelDepaet = SelectedDepartCode;
		}
		String AdditionalCertCodeColumns = "";
		if(SelectedTabType!=null && SelectedTabType.trim().equals("2")){
			AdditionalCertCodeColumns = "CERT_CODE";
		}
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService, keyListBase, null, AdditionalCertCodeColumns, null, jqGridGroupNotSumFeild);
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
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		//tab
		String SelectedTabType = getPd.getString("SelectedTabType");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if (DictsUtil.ifCheckGroupTypeNull(SelectedTableNo)) {
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		}
		
		if(SelectedTabType!=null && SelectedTabType.trim().equals("1")){
			QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
			QueryFeild += " and BILL_CODE in (select bill_code FROM tb_sys_sealed_info WHERE state = '1' AND RPT_DUR = '" + SelectedBusiDate + "') ";
			QueryFeild += " and BILL_CODE not in (select bill_code FROM TB_SYS_CONFIRM_INFO WHERE state = '1' AND RPT_DUR = '" + SelectedBusiDate + "') ";
		} else if(SelectedTabType!=null && SelectedTabType.trim().equals("2")){
			QueryFeild += " and BILL_CODE     in (select bill_code FROM TB_SYS_CONFIRM_INFO WHERE state = '1' AND RPT_DUR = '" + SelectedBusiDate + "') ";
		} else{
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
		//表名
		String getTableNameSummy = getSummyBaseTableCode(SelectedTableNo);
		String tableName = " (select s.*, IFNULL(cert.CERT_CODE, ' ') CERT_CODE from " + getTableNameSummy + " s left join tb_gl_cert cert on s.BILL_CODE = cert.BILL_CODE) t ";
		getPd.put("TableName", tableName);
		page.setPd(getPd);
		
		List<PageData> varList = fundssummyconfirmService.JqPage(page);	//列出Betting列表
		int records = fundssummyconfirmService.countJqGridExtend(page);
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
		    userdata = fundssummyconfirmService.getFooterSummary(page);
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
		TmplTypeInfo implTypeCode = getWhileValueToTypeCode(SelectedTableNo);
		String TypeCodeSummyDetail = implTypeCode.getTypeCodeSummyDetail();
		List<String> SumFieldDetail = implTypeCode.getSumFieldDetail();
		String DEPT_CODE = (String) getPd.get("DataDeptCode");
		String CUST_COL7 = (String) getPd.get("DataCustCol7");
		
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService,SumFieldDetail, null, null, null, jqGridGroupNotSumFeild);
		String detailColModel = tmpl.generateStructureNoEdit(TypeCodeSummyDetail, DEPT_CODE, CUST_COL7);
		
		commonBase.setCode(0);
		commonBase.setMessage(detailColModel);
		
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
		TmplTypeInfo implTypeCode = getWhileValueToTypeCode(SelectedTableNo);
		List<String> SumFieldDetail = implTypeCode.getSumFieldDetail();
		String strBillCode = getPd.getString("DetailListBillCode");
		
		String detailTableName = getSummyDetailTableCode(SelectedTableNo);

		PageData pdCode = new PageData();
		pdCode.put("TableName", detailTableName);
		pdCode.put("BILL_CODE", strBillCode);
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(SumFieldDetail, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			pdCode.put("FieldSelectKey", strFieldSelectKey);
		}
		List<PageData> varList = fundssummyconfirmService.getFirstDetailList(pdCode);	//列出Betting列表
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		
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
		TmplTypeInfo implTypeCode = getWhileValueToTypeCode(SelectedTableNo);
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
		TmplTypeInfo implTypeCode = getWhileValueToTypeCode(SelectedTableNo);
		List<String> SumFieldDetail = implTypeCode.getSumFieldDetail();
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        PageData pdGet = listData.get(0);

		String detailTableName = getImportDetailTableCode(SelectedTableNo);

		PageData pdCode = new PageData();
		String QueryFeild = QueryFeildString.getDetailQueryFeild(pdGet, SumFieldDetail, TmplUtil.keyExtra);
	    if(!(QueryFeild!=null && !QueryFeild.trim().equals(""))){
	    	QueryFeild += " and 1 != 1 ";
	    }
	    pdCode.put("QueryFeild", QueryFeild);
		
	    pdCode.put("TableName", detailTableName);
		List<PageData> varList = fundssummyconfirmService.getSecondDetailList(pdCode);	//列出Betting列表
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		
		return result;
	}

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"导出HouseFundDetail到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}

		/*PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"));
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

		String tableNameSummy = getSummyDetailTableCode(SelectedTableNo);
		PageData pdTransfer = setTransferPd(getPd);
		//表名
		pdTransfer.put("TableName", tableNameSummy);
		page.setPd(pdTransfer);
		List<PageData> varOList = fundsconfirmqueryService.datalistExport(page);
		
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
		mv = new ModelAndView(erv,dataMap); */
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	/**
	 * 根据前端业务表索引获取表名称
	 * 
	 * @param which
	 * @return
	 */
	private String getSummyBaseTableCode(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())) {
				tableCode = "tb_staff_summy_bill";
			} else if (which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())) {
				tableCode = "tb_social_inc_summy_bill";
			} else if (which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())) {
				tableCode = "tb_house_fund_summy_bill";
			}
		}
		return tableCode;
	}
	private String getSummyDetailTableCode(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())) {
				tableCode = "tb_staff_summy";
			} else if (which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())) {
				tableCode = "tb_social_inc_summy";
			} else if (which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())) {
				tableCode = "tb_house_fund_summy";
			}
		}
		return tableCode;
	}
	private String getImportDetailTableCode(String which) {
		String tableCode = "";
		if (which != null){
			if(which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())) {
				tableCode = tb_staff_detail;
			} else if (which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())) {
				tableCode = tb_social_inc_detail;
			} else if (which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())) {
				tableCode = tb_house_fund_detail;
			}
		}
		return tableCode;
	}

	private TmplTypeInfo getWhileValueToTypeCode(String which) throws Exception{
		TmplTypeInfo retItem = new TmplTypeInfo();
		//枚举类型 TmplType
		//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
		//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本retItem
		List<String> SumFieldBillStaff = Arrays.asList("BILL_CODE", "BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP");
		List<String> SumFieldBillSocial = Arrays.asList("BILL_CODE", "BUSI_DATE", "DEPT_CODE", "CUST_COL7");
		if(which.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())){
			//合同化
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.ContractGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())){
			//市场化
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.MarketGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())){
			//系统内劳务
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.SysLaborGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())){
			//运行人员
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT", "USER_CATG", "SAL_RANGE");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.OperLaborGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())){
			//劳务派遣工资
			retItem.setTypeCodeDetail(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey());
			//1、合同化、市场化、运行人员、系统内运行按6列汇总：业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本、企业特定员工分类、工资范围编码
			//2、劳务派遣运行按4列汇总：                                                      业务日期、组织机构、帐套、员工组、所属二级单位、组织单元文本
		    //SumField = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE", "ORG_UNIT");
		    //SumFieldToString = QueryFeildString.tranferListStringToGroupbyString(SumField);
			
			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.LaborGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillStaff);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())){
			//劳务派遣工资
			retItem.setTypeCodeDetail(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey());

			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.SocialIncGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillSocial);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		if(which.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())){
			//劳务派遣工资
			retItem.setTypeCodeDetail(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey());
			retItem.setTypeCodeSummyBill(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey());
			retItem.setTypeCodeSummyDetail(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey());

			PageData pdSysConfig = new PageData();
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.HouseFundGRPCond);
			String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
			List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
			listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBillSocial);
			retItem.setSumFieldDetail(listSumFieldDetail);
		}
		return retItem;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
