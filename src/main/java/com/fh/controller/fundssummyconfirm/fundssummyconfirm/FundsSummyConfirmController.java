package com.fh.controller.fundssummyconfirm.fundssummyconfirm;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
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
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.SysConfirmInfo;
import com.fh.entity.TmplTypeInfo;
import com.fh.entity.system.User;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.BillState;
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.enums.TmplType;

import net.sf.json.JSONArray;

import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
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
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
@Controller
@RequestMapping(value="/fundssummyconfirm")
public class FundsSummyConfirmController extends BaseController {
	
	String menuUrl = "fundssummyconfirm/list.do"; //菜单地址(权限用)
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

	//当前期间,取自tb_system_config的SystemDateTime字段
	//String SystemDateTime = "";
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
		logBefore(logger, Jurisdiction.getUsername()+"列表fundssummyconfirm");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fundssummyconfirm/fundssummyconfirm/fundssummyconfirm_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		//while
		getPd.put("which", SelectedTableNo);

		//"USER_GROP", "CUST_COL7"
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
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//默认登录人，界面只选一个就取界面设置
		String strShowCalModelDepaet = Jurisdiction.getCurrentDepartmentID();
		if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("") && !SelectedDepartCode.contains(",")){
			strShowCalModelDepaet = SelectedDepartCode;
		}
		//已确认tab页，界面显示配置信息外再加上CERT_CODE
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
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");

		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		List<String> AllDeptCode = Common.getAllDeptCode(departmentService, Jurisdiction.getCurrentDepartmentID());
		QueryFeild += " and DEPT_CODE in (" + QueryFeildString.tranferListValueToSqlInString(AllDeptCode) + ") ";
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
		String getTableNameSummy = Corresponding.getSumBillTableNameFromTmplType(SelectedTableNo);
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
		
		String detailTableName = Corresponding.getSummyTableNameFromTmplType(SelectedTableNo);

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

		String detailTableName = Corresponding.getDetailTableNameFromTmplType(SelectedTableNo);

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

	/**确认
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/summyBillConfirm")
	public @ResponseBody CommonBase summyBillConfirm() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "delete")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		/*//当前区间
		String SelectedBusiDate = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}*/
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        List<String> listBillCode = new ArrayList<String>();
        for(PageData each : listData){
        	String BILL_CODE = each.getString("BILL_CODE" + TmplUtil.keyExtra);
        	listBillCode.add(BILL_CODE);
        }

		String QueryFeild = " and BILL_CODE in (" + QueryFeildString.tranferListValueToSqlInString(listBillCode) + ") ";
		List<String> AllDeptCode = Common.getAllDeptCode(departmentService, Jurisdiction.getCurrentDepartmentID());
		QueryFeild += " and DEPT_CODE in (" + QueryFeildString.tranferListValueToSqlInString(AllDeptCode) + ") ";
		QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
		QueryFeild += " and BILL_CODE in (select bill_code FROM tb_sys_sealed_info WHERE state = '1') ";
		
    	sysConfirmInfoService.batchEachConfirm(getSaveConfirm(false, "", QueryFeild));
		commonBase.setCode(0);
		return commonBase;
	}

	/**取消确认
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/summyBillCancel")
	public @ResponseBody CommonBase summyBillCancel() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "delete")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		//tab
		String SelectedTabType = getPd.getString("SelectedTabType");
		/*//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}*/
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		
		List<SysConfirmInfo> listTransfer = new ArrayList<SysConfirmInfo>();
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        List<String> listBillCode = new ArrayList<String>();
        for(PageData each : listData){
        	String BILL_CODE = each.getString("BILL_CODE" + TmplUtil.keyExtra);
        	listBillCode.add(BILL_CODE);
        	SysConfirmInfo itemAdd = new SysConfirmInfo();
        	itemAdd.setBILL_CODE(BILL_CODE);
        	itemAdd.setRPT_USER(userId);
        	itemAdd.setRPT_DATE(DateUtil.getTime());
        	itemAdd.setSTATE(BillState.Invalid.getNameKey());
        	listTransfer.add(itemAdd);
        }
		String checkState = CheckState(SelectedTableNo, SelectedTabType, QueryFeildString.tranferListValueToSqlInString(listBillCode));
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
        if(null != listData && listData.size() > 0){
        	sysConfirmInfoService.batchCancelConfirm(listTransfer);
			commonBase.setCode(0);
		}
		
		return commonBase;
	}

	/**确认
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/confirmAll")
	public @ResponseBody CommonBase confirmAll() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "delete")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		/*PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"));
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String tmplType = DictsUtil.getTranferTmplType(SelectedTableNo);
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		//String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		//getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SystemDateTime);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SystemDateTime!=null && !SystemDateTime.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(tmplType!=null && !tmplType.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if (DictsUtil.ifCheckGroupTypeNull(SelectedTableNo)) {
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		}
		QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
		QueryFeild += " and BILL_CODE in (select bill_code FROM tb_sys_sealed_info WHERE state = '1' AND RPT_DUR = '" + SystemDateTime + "') ";
		getPd.put("QueryFeild", QueryFeild);
		
		//表名
		String tableNameSummy = getSummyBaseTableCode(SelectedTableNo);
		getPd.put("TableName", tableNameSummy);
		List<PageData> getList = fundssummyconfirmService.getOperList(getPd);	//列出Betting列表

        String strMessage = "";
        List<String> listBillCode = new ArrayList<String>();
		List<SysConfirmInfo> listTransferConfirm = new ArrayList<SysConfirmInfo>();
		List<PageData> listTransferCert = new ArrayList<PageData>();
		if(getList!=null && getList.size()>0){
			// 执行从FIMS获取凭证号
			Service servicePzbh = new Service();
			Call callPzbh = (Call) servicePzbh.createCall();
			PageData pdKeyCodePzbh = new PageData();
			pdKeyCodePzbh.put("KEY_CODE", "JQueryPzInformation");
			String strUrlPzbh = sysConfigManager.getSysConfigByKey(pdKeyCodePzbh);
			URL urlPzbh = new URL(strUrlPzbh);
			callPzbh.setTargetEndpointAddress(urlPzbh);
			callPzbh.setOperationName(new QName("http://JQueryPzInformation.j2ee", "commonQueryPzBh"));
			callPzbh.setUseSOAPAction(true);

			// 执行从FIMS获取冲销凭证号
			Service serviceCxpz = new Service();
			Call callCxpz = (Call) serviceCxpz.createCall();
			PageData pdKeyCodeCxpz = new PageData();
			pdKeyCodeCxpz.put("KEY_CODE", "JRevertVoucher");
			String strUrlCxpz = sysConfigManager.getSysConfigByKey(pdKeyCodeCxpz);
			URL urlCxpz = new URL(strUrlCxpz);
			callCxpz.setTargetEndpointAddress(urlCxpz);
			callCxpz.setOperationName(new QName("http://JRevertVoucher.j2ee", "AmisRevertVoucher"));
			callCxpz.setUseSOAPAction(true);

			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			String userId = user.getUSER_ID();
	        for(PageData each : getList){
	        	String BILL_CODE = each.getString("BILL_CODE");// 单据编号
	        	listBillCode.add(BILL_CODE);
	        	String BUSI_DATE = each.getString("BUSI_DATE");
	        	String CUST_COL7 = each.getString("CUST_COL7"); 
				String tableName = "T_" + DictsUtil.getTableCodeOnFmis(tmplType, sysConfigManager);// 在fmis建立的业务表名

				String strPzbh = ""; // 凭证编号
				String strPzrq = "";
				String strCxpz = "";
				String resultPzbh = (String) callPzbh.invoke(new Object[] { tableName, BILL_CODE, CUST_COL7 });// 对应定义参数
				if (resultPzbh.length() > 0) {
					String[] stringArrPzbh = resultPzbh.split(";");
					strPzbh = stringArrPzbh[0]; // 凭证编号
					strPzrq = stringArrPzbh[1];
					if(strPzbh!=null && !strPzbh.trim().equals("")){
						String workDate = DateUtils.getCurrentTime(DateFormatUtils.DATE_NOFUll_FORMAT);// 当前工作日期格式20170602
						String resultCxpz = (String) callCxpz.invoke(new Object[] { tableName, CUST_COL7, strPzrq, strPzbh, workDate });// 对应定义参数
						String [] resultStrsCxpz = resultCxpz.split(";");
						if(resultStrsCxpz.length>0&&resultStrsCxpz[0].equals("FALSE")){
							//strMessage += " " + BILL_CODE + "获取冲销凭证编号失败！";
						}	
						else if(resultStrsCxpz.length>0&&resultStrsCxpz[0].equals("TRUE")){
							if(resultStrsCxpz.length>1){
								strCxpz = resultStrsCxpz[1];// 冲销凭证编号
							}
						}
					}
				}
				if(!(strPzrq!=null && !strPzrq.trim().equals(""))){
					strPzrq = " ";
				}
				if(!(strCxpz!=null && !strCxpz.trim().equals(""))){
					strCxpz = " ";
				}
				if(strPzbh!=null && !strPzbh.trim().equals("")){
					PageData pdCert = new PageData();
					pdCert.put("BILL_CODE", BILL_CODE);
					pdCert.put("CERT_CODE", strPzbh);
					pdCert.put("VOUCHER_DATE", strPzrq);
					pdCert.put("BILL_USER", userId);
					pdCert.put("BILL_DATE", DateUtils.getCurrentTime());// YYYY-MM-DD HH:MM:SS
					pdCert.put("BUSI_DATE", BUSI_DATE);
					pdCert.put("REVCERT_CODE", strCxpz);
					listTransferCert.add(pdCert);
					
					if(!(strCxpz!=null && !strCxpz.trim().equals(""))){
			        	SysConfirmInfo itemAdd = new SysConfirmInfo();
			        	itemAdd.setBILL_CODE(BILL_CODE);
			        	itemAdd.setCERT_CODE(strPzbh);
			        	itemAdd.setRPT_USER(userId);
			        	itemAdd.setRPT_DATE(DateUtil.getTime());
			        	itemAdd.setBILL_OFF(CUST_COL7);
			        	String DEPT_CODE = each.getString("DEPT_CODE");
			        	itemAdd.setRPT_DEPT(DEPT_CODE);
			        	itemAdd.setRPT_DUR(BUSI_DATE);
			        	String BILL_TYPE = getSysConfirmInfoBillType(SelectedTableNo);
			        	itemAdd.setBILL_TYPE(BILL_TYPE);
			        	itemAdd.setSTATE(BillState.Normal.getNameKey());
			        	listTransferConfirm.add(itemAdd);
					}
				}
	        }
		}
		if(strMessage!=null && !strMessage.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strMessage);
			return commonBase;
		}
    	String sqlInBillCode = QueryFeildString.tranferListValueToSqlInString(listBillCode);

    	Map<String, Object> map = new HashMap<String, Object>();
        if(getList!=null && getList.size()>0){
    		PageData pdCert = new PageData();
    		pdCert.put("BUSI_DATE", SystemDateTime);
    		pdCert.put("CanOperateBillCode", sqlInBillCode);
        	map.put("TransferCertDel", pdCert);
        }

		PageData pdConfirm = new PageData();
		pdConfirm.put("CanOperateBillCode", sqlInBillCode);
		pdConfirm.put("RPT_DUR", SystemDateTime);
		pdConfirm.put("BILL_TYPE", getSysConfirmInfoBillType(SelectedTableNo));
		pdConfirm.put("BILL_OFF", SelectedCustCol7);
    	map.put("TransferConfirmDel", pdConfirm);
    	
        if(null != listTransferCert && listTransferCert.size() > 0){
        	map.put("TransferCertAdd", listTransferCert);
		}
    	
        if(listTransferConfirm!=null && listTransferConfirm.size()>0){
        	map.put("TransferConfirmAdd", listTransferConfirm);
        }
    	sysConfirmInfoService.batchAllConfirm(map);
		commonBase.setCode(0);
		return commonBase;*/
		
		PageData getViewPd = this.getPageData();
		//员工组
		//String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"));
		//String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		//String tmplType = DictsUtil.getTranferTmplType(SelectedTableNo);
		//账套
		//String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		////String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//当前区间
		String SelectedBusiDate = getViewPd.getString("SelectedBusiDate");
		/*String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}*/
		
		PageData getQueryFeildPd = new PageData();
		//getQueryFeildPd.put("USER_GROP", emplGroupType);
		//getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		//getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		List<String> AllDeptCode = Common.getAllDeptCode(departmentService, Jurisdiction.getCurrentDepartmentID());
		QueryFeild += " and DEPT_CODE in (" + QueryFeildString.tranferListValueToSqlInString(AllDeptCode) + ") ";
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		//if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
		//	QueryFeild += " and 1 != 1 ";
		//}
		//if(!(tmplType!=null && !tmplType.trim().equals(""))){
		//	QueryFeild += " and 1 != 1 ";
		//}
		//if (DictsUtil.ifCheckGroupTypeNull(SelectedTableNo)) {
		//	if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
		//		QueryFeild += " and 1 != 1 ";
		//	}
		//}
		QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
		QueryFeild += " and BILL_CODE in (select bill_code FROM tb_sys_sealed_info WHERE state = '1' AND RPT_DUR = '" + SelectedBusiDate + "') ";
		
    	sysConfirmInfoService.batchAllConfirm(getSaveConfirm(true, SelectedBusiDate, QueryFeild));
		commonBase.setCode(0);
		return commonBase;
	}
	
	private Map<String, Object> getSaveConfirm(Boolean bolAll, String SelectedBusiDate,
			String QueryFeild) throws Exception{
		PageData getDataPd = new PageData();
		getDataPd.put("QueryFeild", QueryFeild);
		
		getDataPd.put("TableName", Corresponding.tb_staff_summy_bill);
		List<PageData> getListStaff = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
		getDataPd.put("TableName", Corresponding.tb_social_inc_summy_bill);
		List<PageData> getListSocial = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
		getDataPd.put("TableName", Corresponding.tb_house_fund_summy_bill);
		List<PageData> getListHouse = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
		
		List<SysConfirmInfo> listTransferConfirm = new ArrayList<SysConfirmInfo>();
		List<PageData> listTransferCert = new ArrayList<PageData>();
		setConfirm(bolAll, getListStaff, listTransferConfirm, listTransferCert, "");
		setConfirm(bolAll, getListSocial, listTransferConfirm, listTransferCert, TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey());
		setConfirm(bolAll, getListHouse, listTransferConfirm, listTransferCert, TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey());

    	Map<String, Object> map = new HashMap<String, Object>();
    	//确认是单独确认，判断未作废、部门权限、已封存tb_sys_sealed_info，然后根据单号先删掉再插入。
    	//批量确认是取区间内未作废、部门权限、已封存tb_sys_sealed_info的所有记录（工资、社保、公积金），然后删除区间内所有数据，再插入。
    	if(bolAll){
        	PageData pdCert = new PageData();
        	pdCert.put("BUSI_DATE", SelectedBusiDate);
            map.put("TransferCertDel", pdCert);

    		PageData pdConfirm = new PageData();
    		pdConfirm.put("RPT_DUR", SelectedBusiDate);
        	map.put("TransferConfirmDel", pdConfirm);
    	}
    	
        if(null != listTransferCert && listTransferCert.size() > 0){
        	map.put("TransferCertAdd", listTransferCert);
		}
    	
        if(listTransferConfirm!=null && listTransferConfirm.size()>0){
        	map.put("TransferConfirmAdd", listTransferConfirm);
        }
        return map;
	}
	
	private void setConfirm(Boolean bolAll, List<PageData> getList, 
			List<SysConfirmInfo> listTransferConfirm, List<PageData> listTransferCert,
			String strTmplTypeTransfer) throws Exception{
		if(getList!=null && getList.size()>0){
			// 执行从FIMS获取凭证号
			Service servicePzbh = new Service();
			Call callPzbh = (Call) servicePzbh.createCall();
			PageData pdKeyCodePzbh = new PageData();
			pdKeyCodePzbh.put("KEY_CODE", "JQueryPzInformation");
			String strUrlPzbh = sysConfigManager.getSysConfigByKey(pdKeyCodePzbh);
			URL urlPzbh = new URL(strUrlPzbh);
			callPzbh.setTargetEndpointAddress(urlPzbh);
			callPzbh.setOperationName(new QName("http://JQueryPzInformation.j2ee", "commonQueryPzBh"));
			callPzbh.setUseSOAPAction(true);

			// 执行从FIMS获取冲销凭证号
			Service serviceCxpz = new Service();
			Call callCxpz = (Call) serviceCxpz.createCall();
			PageData pdKeyCodeCxpz = new PageData();
			pdKeyCodeCxpz.put("KEY_CODE", "JRevertVoucher");
			String strUrlCxpz = sysConfigManager.getSysConfigByKey(pdKeyCodeCxpz);
			URL urlCxpz = new URL(strUrlCxpz);
			callCxpz.setTargetEndpointAddress(urlCxpz);
			callCxpz.setOperationName(new QName("http://JRevertVoucher.j2ee", "AmisRevertVoucher"));
			callCxpz.setUseSOAPAction(true);

			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			String userId = user.getUSER_ID();
	        for(PageData each : getList){
	        	String BILL_CODE = each.getString("BILL_CODE");// 单据编号
	        	String BUSI_DATE = each.getString("BUSI_DATE");
	        	String CUST_COL7 = each.getString("CUST_COL7"); 
	        	String USER_GROP = each.getString("USER_GROP"); 
	        	String tmplType = Corresponding.getTmplTypeTranferFromUserGroupType(USER_GROP);
	        	if(strTmplTypeTransfer!=null && !strTmplTypeTransfer.trim().equals("")){
	        		tmplType = strTmplTypeTransfer;
	        	}
				String tableName = "T_" + DictsUtil.getTableCodeOnFmis(tmplType, sysConfigManager);// 在fmis建立的业务表名

				String strPzbh = ""; // 凭证编号
				String strPzrq = ""; // 凭证日期
				String strCxpz = ""; // 冲销凭证编号
				String resultPzbh = (String) callPzbh.invoke(new Object[] { tableName, BILL_CODE, CUST_COL7 });// 对应定义参数
				if (resultPzbh.length() > 0) {
					String[] stringArrPzbh = resultPzbh.split(";");
					if(stringArrPzbh.length>0){
						strPzbh = stringArrPzbh[0]; // 凭证编号
					}
					if(stringArrPzbh.length>1){
						strPzrq = stringArrPzbh[1];
					}
					if(strPzbh!=null && !strPzbh.trim().equals("")){
						String workDate = DateUtils.getCurrentTime(DateFormatUtils.DATE_NOFUll_FORMAT);// 当前工作日期格式20170602
						String resultCxpz = (String) callCxpz.invoke(new Object[] { tableName, CUST_COL7, strPzrq, strPzbh, workDate });// 对应定义参数
						String [] resultStrsCxpz = resultCxpz.split(";");
						if(resultStrsCxpz.length>0&&resultStrsCxpz[0].equals("FALSE")){
							//strMessage += " " + BILL_CODE + "获取冲销凭证编号失败！";
						}	
						else if(resultStrsCxpz.length>0&&resultStrsCxpz[0].equals("TRUE")){
							if(resultStrsCxpz.length>1){
								strCxpz = resultStrsCxpz[1];// 冲销凭证编号
							}
						}
					}
				}
				if(!(strPzbh!=null && !strPzbh.equals(""))){
					strPzbh = " ";
				}
				if(!(strPzrq!=null && !strPzrq.equals(""))){
					strPzrq = " ";
				}
				if(!(strCxpz!=null && !strCxpz.equals(""))){
					strCxpz = " ";
				}
				//凭证编号不为空，保存TB_GL_CERT表
				if(strPzbh!=null && !strPzbh.trim().equals("")){
					PageData pdCert = new PageData();
					pdCert.put("BILL_CODE", BILL_CODE);
					pdCert.put("CERT_CODE", strPzbh);
					pdCert.put("VOUCHER_DATE", strPzrq);
					pdCert.put("BILL_USER", userId);
					pdCert.put("BILL_DATE", DateUtils.getCurrentTime());// YYYY-MM-DD HH:MM:SS
					pdCert.put("BUSI_DATE", BUSI_DATE);
					pdCert.put("REVCERT_CODE", strCxpz);
					listTransferCert.add(pdCert);
				}
				//单独确认，获取凭证编号，无论有无凭证编号都存TB_SYS_CONFIRM_INFO确认表
				//批量确认，获取凭证编号，有凭证编号无冲销凭证号存TB_SYS_CONFIRM_INFO确认表
	        	SysConfirmInfo itemAdd = new SysConfirmInfo();
	        	itemAdd.setBILL_CODE(BILL_CODE);
	        	itemAdd.setCERT_CODE(strPzbh);
	        	itemAdd.setRPT_USER(userId);
	        	itemAdd.setRPT_DATE(DateUtil.getTime());
	        	itemAdd.setBILL_OFF(CUST_COL7);
	        	String DEPT_CODE = each.getString("DEPT_CODE");
	        	itemAdd.setRPT_DEPT(DEPT_CODE);
	        	itemAdd.setRPT_DUR(BUSI_DATE);
	        	String BILL_TYPE = Corresponding.getSysConfirmInfoBillTypeFromTmplType(tmplType);
	        	itemAdd.setBILL_TYPE(BILL_TYPE);
	        	itemAdd.setSTATE(BillState.Normal.getNameKey());
				if(!bolAll || (strPzbh!=null && !strPzbh.trim().equals("") && !(strCxpz!=null && !strCxpz.trim().equals("")))){
		        	listTransferConfirm.add(itemAdd);
				}
	        }
		}
	}
	
	//判断单据状态
	private String CheckState(String SelectedTableNo, String SelectedTabType, String strSqlInBillCode) throws Exception{
		String strRut = "";
		
		String QueryFeild = " and BILL_CODE in (" + strSqlInBillCode + ") ";
		QueryFeild += " and state = '1' ";

		String strSqlCancelBillCodeIn = "";
		if(!(SelectedTabType!=null && (SelectedTabType.trim().equals("1") || SelectedTabType.trim().equals("2")))){
			strRut = Message.Error;
			return strRut;
		} else if(SelectedTabType!=null && SelectedTabType.trim().equals("2")){
			/*String strImportDetailTableCode = getImportDetailTableCode(SelectedTableNo);
			//List<SysTableMapping> getSysTableMappingList = SysStruMappingList.getDetailBillCodeSysTableMapping(SystemDateTime, strImportDetailTableCode, sysTableMappingService);
			//if(getSysTableMappingList != null && getSysTableMappingList.size()>0){
				//for(SysTableMapping tableMap : getSysTableMappingList){
					//String tableName = tableMap.getTABLE_NAME();
					//String tableNameMapping = tableMap.getTABLE_NAME_MAPPING();
					// 前端数据表格界面字段,动态取自SysStruMapping，根据当前单位编码及表名获取字段配置信息
					//List<SysStruMapping> getSysStruMappingList = SysStruMappingList.getDetailBillCodeSysStruMapping(SystemDateTime, tableName, tableNameMapping, ("bill_code").toUpperCase(), sysStruMappingService);
			        List<SysStruMapping> getSysStruMappingList = SysStruMappingList.getDetailBillCodeSysStruMapping(SystemDateTime, strImportDetailTableCode, "", ("bill_code").toUpperCase(), sysStruMappingService);
					// 添加配置表设置列，字典（未设置就使用表默认，text或number）、隐藏、表头显示
					if (getSysStruMappingList != null && getSysStruMappingList.size() > 0) {
						for(SysStruMapping struMap : getSysStruMappingList){
							if(struMap.getCOL_CODE().toUpperCase().equals(("bill_code").toUpperCase())){
								if(strSqlCancelBillCodeIn!=null && !strSqlCancelBillCodeIn.trim().equals("")){
									strSqlCancelBillCodeIn += " UNION ALL ";
								}
								String TABLE_NAME_MAPPING = struMap.getTABLE_NAME_MAPPING();
								String COL_MAPPING_CODE = struMap.getCOL_MAPPING_CODE();
								strSqlCancelBillCodeIn += " select " + COL_MAPPING_CODE + " from " + TABLE_NAME_MAPPING + " where bill_code in (select bill_code from tb_gen_bus_summy_bill where BILL_STATE = '" + BillState.Normal.getNameKey() + "') ";
							}
						}
					} else {
						//strRut = Message.BillCodeNotHaveGenFeild;
						//return strRut;
					}
				//}
			//}
			if(strSqlCancelBillCodeIn!=null && !strSqlCancelBillCodeIn.trim().equals("")){
				//已确认，要取消，判断是否已汇总
				QueryFeild += " and BILL_CODE in (" + strSqlCancelBillCodeIn + ")";
			}*/
		}
		
		PageData tranPd = new PageData();
		tranPd.put("CanOperate", QueryFeild);
		tranPd.put("TableName", tb_sys_confirm_info);
		List<SysConfirmInfo> getCodeList = sysConfirmInfoService.getCheckStateList(tranPd);
		if(SelectedTabType!=null && SelectedTabType.trim().equals("2")
				&& !(strSqlCancelBillCodeIn!=null && !strSqlCancelBillCodeIn.trim().equals(""))){
			strRut = "";
		} else {
			if(getCodeList!=null && getCodeList.size()>0){
				strRut = Message.OperDataSumAlreadyChange;
			}
		}
		return strRut;
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