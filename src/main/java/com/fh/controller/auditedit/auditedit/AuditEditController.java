package com.fh.controller.auditedit.auditedit;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
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
import com.fh.entity.TmplConfigDetail;
import com.fh.exception.CustomException;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.enums.TmplType;
import com.fh.util.excel.LeadingInExcelToPageData;

import net.sf.json.JSONArray;

import com.fh.util.Jurisdiction;
import com.fh.service.auditedit.auditedit.AuditEditManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysSealedInfo.syssealedinfo.impl.SysSealedInfoService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明： 对账数据编辑
 * 创建人：zhangxiaoliu
 * 创建时间：2017-07-26
 * @version
 */
@Controller
@RequestMapping(value="/auditedit")
public class AuditEditController extends BaseController {
	
	String menuUrl = "auditedit/list.do"; //菜单地址(权限用)
	@Resource(name="auditeditService")
	private AuditEditManager auditeditService;
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
	String DefaultWhile = TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey();

	//页面显示数据的年月
	//String SystemDateTime = "";
	//页面显示数据的二级单位
	//String UserDepartCode = "";
	//登录人的二级单位是最末层
	//private int departSelf = 0;
	//底行显示的求和与平均值字段
	//StringBuilder SqlUserdata = new StringBuilder();
	//字典
	//Map<String, Object> DicList = new LinkedHashMap<String, Object>();
	//表结构  
	//Map<String, TableColumns> map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	//Map<String, TmplConfigDetail> map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

	private List<String> MustInputList = Arrays.asList("USER_CODE");
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("DEPT_CODE", "CUST_COL7", "USER_GROP");
	// 设置必定不用编辑的列
	//List<String> MustNotEditList = new ArrayList<String>();
	// 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
    List<String> keyListAdd = Arrays.asList("USER_CODE");
	//List<String> keyListBase = getKeyListBase(MustNotEditList);
	private List<String> getKeyListBase(List<String> MustNotEditList){
		List<String> list = new ArrayList<String>();
		for(String strFeild : MustNotEditList){
			if (!list.contains(strFeild)) {
			    list.add(strFeild);
			}
		}
		for(String strFeild : keyListAdd){
			if (!list.contains(strFeild)) {
				list.add(strFeild);
			}
		}
		return list;
	}
    //设置分组时不求和字段            SERIAL_NO 设置字段类型是数字，但不用求和
    List<String> jqGridGroupNotSumFeild = Arrays.asList("SERIAL_NO");
	
	//String getPageListSelectedCustCol7 = "";
	//String getPageListSelectedDepartCode = "";
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表AuditEdit");

		//getPageListSelectedCustCol7 = "";
		//getPageListSelectedDepartCode = "";
		
		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("auditedit/auditedit/auditedit_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		//当前登录人所在二级单位
		//String UserDepartCode = Jurisdiction.getCurrentDepartmentID();//

		//while
		getPd.put("which", SelectedTableNo);
		mv.addObject("pd", getPd);

		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		// *********************加载单位树  DEPT_CODE*******************************
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService);
		if(DepartmentSelectTreeSource.equals("0"))
		{
			//this.departSelf = 1;
			getPd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			//departSelf = 0;
			getPd.put("departTreeSource", 1);
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// ***********************************************************
		List<String> MustNotEditList = getMustNotEditList(SelectedTableNo);
		List<String> keyListBase = getKeyListBase(MustNotEditList);
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService, keyListBase, null, null, MustInputList, jqGridGroupNotSumFeild);
		//String jqGridColModel = tmpl.generateStructure(SelectedTableNo, UserDepartCode, 3, MustNotEditList);
		
		//SqlUserdata = tmpl.getSqlUserdata();
		//字典
		//DicList = tmpl.getDicList();
		//表结构  
		//map_HaveColumnsList = tmpl.getHaveColumnsList();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		//map_SetColumnsList = tmpl.getSetColumnsList();
		
		//mv.addObject("jqGridColModel", jqGridColModel);
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表AuditEdit");

		//getPageListSelectedCustCol7 = "";
		//getPageListSelectedDepartCode = "";
		
		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String tableNameAudit = getAuditTableCode(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		
		//底行显示的求和与平均值字段
		//StringBuilder SqlUserdata = Common.GetSqlUserdata(SelectedTableNo, SelectedDepartCode, tmplconfigService);
		
		//getPageListSelectedCustCol7 = SelectedCustCol7;
		//getPageListSelectedDepartCode = SelectedDepartCode;
		
		PageData getQueryFeildPd = new PageData();
		//工资分的类型, 只有工资返回值
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(CheckStaffOrNot(SelectedTableNo)){
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		}
		if(QueryFeild!=null && !QueryFeild.equals("")){
			getPd.put("QueryFeild", QueryFeild);
		}
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		//页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		//表名
		getPd.put("TableName", tableNameAudit);
		List<String> MustNotEditList = getMustNotEditList(SelectedTableNo);
		List<String> keyListBase = getKeyListBase(MustNotEditList);
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
		page.setPd(getPd);
		List<PageData> varList = auditeditService.JqPage(page);	//列出Betting列表
		int records = auditeditService.countJqGridExtend(page);
		PageData userdata = null;
		//if(SqlUserdata!=null && !SqlUserdata.toString().trim().equals("")){
		//	//底行显示的求和与平均值字段
		//	getPd.put("Userdata", SqlUserdata.toString());
		//	userdata = auditeditService.getFooterSummary(page);
		//}
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		result.setUserdata(userdata);
		
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
		logBefore(logger, Jurisdiction.getUsername()+"修改AuditEdit");

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String tableName = getAuditTableCode(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		//操作
		String oper = getPd.getString("oper");
		//Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode, tmplconfigService);
		//Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo, tmplconfigService);

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedTableNo, emplGroupType, SelectedCustCol7, SelectedDepartCode,
				ShowDataDepartCode, ShowDataCustCol7, DepartTreeSource);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}

		//必定不用编辑的列
		//MustNotEditList = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP");
		//MustNotEditList = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7");
		List<String> MustNotEditList = getMustNotEditList(SelectedTableNo);
		getPd.put("BILL_CODE", " ");
		getPd.put("StaffOrNot", "");
		if(CheckStaffOrNot(SelectedTableNo)){
			getPd.put("StaffOrNot", "true");
		}
		if(oper.equals("add")){
			getPd.put("BUSI_DATE", SystemDateTime);
			getPd.put("DEPT_CODE", SelectedDepartCode);
			getPd.put("CUST_COL7", SelectedCustCol7);
			if(CheckStaffOrNot(SelectedTableNo)){
				getPd.put("USER_GROP", emplGroupType);
			}
		} else {
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
		}
		//Common.setModelDefault(getPd, map_HaveColumnsList, map_SetColumnsList);
		//表名
		getPd.put("TableName", tableName);
		
		List<PageData> listData = new ArrayList<PageData>();
		listData.add(getPd);
		PageData pdFindByModel = new PageData();
		pdFindByModel.put("TableName", tableName);
		pdFindByModel.put("ListData", listData);
		List<PageData> repeatList = auditeditService.findByModel(pdFindByModel);
		if(repeatList!=null && repeatList.size()>0){
			commonBase.setCode(2);
			commonBase.setMessage("此区间内编码已存在！");
			return commonBase;
		} 
            auditeditService.deleteUpdateAll(listData);
			commonBase.setCode(0);
		return commonBase;
	}
	
	 /**批量修改
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateAll")
	public @ResponseBody CommonBase updateAll() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String tableName = getAuditTableCode(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		//Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode, tmplconfigService);
		//Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo, tmplconfigService);

		Boolean isStaffOrNot = CheckStaffOrNot(SelectedTableNo);

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedTableNo, emplGroupType, SelectedCustCol7, SelectedDepartCode,
				ShowDataDepartCode, ShowDataCustCol7, DepartTreeSource);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        List<String> listUserCodeAdd = new ArrayList<String>();
        for(PageData item : listData){
        	String strUserCode = item.getString("USER_CODE__");
        	if(listUserCodeAdd.contains(strUserCode)){
				commonBase.setCode(2);
				commonBase.setMessage("此区间内编码重复:" + strUserCode);
				return commonBase;
        	}
        	listUserCodeAdd.add(strUserCode);
			item.put("StaffOrNot", "");
    		if(isStaffOrNot){
    			item.put("StaffOrNot", "true");
    		}
        	item.put("BILL_CODE", " ");
        	//Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList);
			//表名
			item.put("TableName", tableName);
        }
		if(null != listData && listData.size() > 0){
			PageData pdFindByModel = new PageData();
			pdFindByModel.put("TableName", tableName);
			pdFindByModel.put("ListData", listData);
			List<PageData> repeatList = auditeditService.findByModel(pdFindByModel);
			if(repeatList!=null && repeatList.size()>0){
				commonBase.setCode(2);
				commonBase.setMessage("此区间内编码已存在！");
				return commonBase;
			}
				auditeditService.deleteUpdateAll(listData);
				commonBase.setCode(0);
		}
		return commonBase;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteAll")
	public @ResponseBody CommonBase deleteAll() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "delete")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String tableName = getAuditTableCode(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		
		Boolean isStaffOrNot = CheckStaffOrNot(SelectedTableNo);

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedTableNo, emplGroupType, SelectedCustCol7, SelectedDepartCode,
				ShowDataDepartCode, ShowDataCustCol7, DepartTreeSource);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        for(PageData item : listData){
			item.put("StaffOrNot", "");
    		if(isStaffOrNot){
    			item.put("StaffOrNot", "true");
    		}
			//表名
			item.put("TableName", tableName);
        }
        if(null != listData && listData.size() > 0){
			PageData pdDeleteAll = new PageData();
			pdDeleteAll.put("TableName", tableName);
			pdDeleteAll.put("ListData", listData);
			auditeditService.deleteAll(pdDeleteAll);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
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
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		}

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedTableNo, emplGroupType, SelectedCustCol7, SelectedDepartCode,
				ShowDataDepartCode, ShowDataCustCol7, DepartTreeSource);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		}
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "auditedit");
		mv.addObject("which", SelectedTableNo);
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("SystemDateTime", SystemDateTime);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}

	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/readExcel")
	//public @ResponseBody CommonBase readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}//校验权限
		
		String strErrorMessage = "";
	    
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
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		} else {
			//Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode, tmplconfigService);
			//Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo, tmplconfigService);
			//Map<String, Object> DicList = Common.GetDicList(SelectedTableNo, SelectedDepartCode, 
			//		tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, "");
			
			String tableName = getAuditTableCode(SelectedTableNo);

			//判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedTableNo, emplGroupType, SelectedCustCol7, SelectedDepartCode,
					ShowDataDepartCode, ShowDataCustCol7, DepartTreeSource);
			if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			} else {
				if(!(SystemDateTime!=null && !SystemDateTime.trim().equals(""))){
					commonBase.setCode(2);
					commonBase.setMessage("当前区间不能为空！");
				} else {
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
						
						//配置表设置列
						//if(map_SetColumnsList != null && map_SetColumnsList.size() > 0){
						//	for (TmplConfigDetail col : map_SetColumnsList.values()) {
						//		titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
						//	}
						//}

						// 调用解析工具包
						testExcel = new LeadingInExcelToPageData<PageData>(formart);
						// 解析excel，获取客户信息集合

						//uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
						//		titleAndAttribute, map_HaveColumnsList, map_SetColumnsList, DicList);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("读取Excel文件错误", e);
						throw new CustomException("读取Excel文件错误:" + e.getMessage(),false);
					}
					boolean judgement = false;

						Map<String, Object> returnError =  (Map<String, Object>) uploadAndReadMap.get(2);
						if(returnError != null && returnError.size()>0){
							strErrorMessage += "字典无此翻译： "; // \n
							for (String k : returnError.keySet())  
						    {
								strErrorMessage += k + " : " + returnError.get(k);
						    }
						}

						List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
						List<PageData> listAdd = new ArrayList<PageData>();
						if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
							judgement = true;
						}
						if (judgement) {
							List<String> sbRet = new ArrayList<String>();
							int listSize = listUploadAndRead.size();
							if(listSize > 0){
								List<String> listUserCode = new ArrayList<String>();
								//获取数据库中不是本部门、员工组和账套中的UserCode
								PageData pdHaveFeild = new PageData();
								pdHaveFeild.put("TableName", tableName);
								pdHaveFeild.put("SystemDateTime", SystemDateTime);
								pdHaveFeild.put("SelectedDepartCode", SelectedDepartCode);
								pdHaveFeild.put("SelectedCustCol7", SelectedCustCol7);
								pdHaveFeild.put("emplGroupType", emplGroupType);
								pdHaveFeild.put("StaffOrNot", "");
								if(CheckStaffOrNot(SelectedTableNo)){
									pdHaveFeild.put("StaffOrNot", true);
								}
								listUserCode = auditeditService.exportHaveUserCode(pdHaveFeild);

								for(int i=0;i<listSize;i++){
									PageData pdAdd = listUploadAndRead.get(i);
									String getUSER_CODE = (String) pdAdd.get("USER_CODE");
									if(getUSER_CODE!=null && !getUSER_CODE.trim().equals("")){
										pdAdd.put("StaffOrNot", "");
										if(CheckStaffOrNot(SelectedTableNo)){
											pdAdd.put("StaffOrNot", true);
											
											String getUSER_GROP = (String) pdAdd.get("USER_GROP");
											if(!(getUSER_GROP!=null && !getUSER_GROP.trim().equals(""))){
												pdAdd.put("USER_GROP", emplGroupType);
												getUSER_GROP = emplGroupType;
											}
											if(!emplGroupType.equals(getUSER_GROP)){
												if(!sbRet.contains("导入员工组和当前员工组必须一致！")){
													sbRet.add("导入员工组和当前员工组必须一致！");
												}
											}
										}
										String getBUSI_DATE = (String) pdAdd.get("BUSI_DATE");
										String getCUST_COL7 = (String) pdAdd.get("CUST_COL7");
										String getDEPT_CODE = (String) pdAdd.get("DEPT_CODE");
										if(!(getBUSI_DATE!=null && !getBUSI_DATE.trim().equals(""))){
											pdAdd.put("BUSI_DATE", SystemDateTime);
											getBUSI_DATE = SystemDateTime;
										}
										if(!SystemDateTime.equals(getBUSI_DATE)){
											if(!sbRet.contains("导入区间和当前区间必须一致！")){
												sbRet.add("导入区间和当前区间必须一致！");
											}
										}
										if(!(getCUST_COL7!=null && !getCUST_COL7.trim().equals(""))){
											pdAdd.put("CUST_COL7", SelectedCustCol7);
											getCUST_COL7 = SelectedCustCol7;
										}
										if(!SelectedCustCol7.equals(getCUST_COL7)){
											if(!sbRet.contains("导入账套和当前账套必须一致！")){
												sbRet.add("导入账套和当前账套必须一致！");
											}
										}
										if(!(getDEPT_CODE!=null && !getDEPT_CODE.trim().equals(""))){
											pdAdd.put("DEPT_CODE", SelectedDepartCode);
											getDEPT_CODE = SelectedDepartCode;
										}
										if(!SelectedDepartCode.equals(getDEPT_CODE)){
											if(!sbRet.contains("导入单位和当前单位必须一致！")){
												sbRet.add("导入单位和当前单位必须一致！");
											}
										}
										if(!(getUSER_CODE!=null && !getUSER_CODE.trim().equals(""))){
											if(!sbRet.contains("人员编码不能为空！")){
												sbRet.add("人员编码不能为空！");
											}
										} else {
											if(listUserCode.contains(getUSER_CODE.trim())){
												String strUserAdd = "编码" + getUSER_CODE + "重复！";
												if(!sbRet.contains(strUserAdd)){
													sbRet.add(strUserAdd);
												}
											} else {
												listUserCode.add(getUSER_CODE.trim());
											}
										}
										String getESTB_DEPT = (String) pdAdd.get("ESTB_DEPT");
										if(!(getESTB_DEPT!=null && !getESTB_DEPT.trim().equals(""))){
											pdAdd.put("ESTB_DEPT", Jurisdiction.getCurrentDepartmentID());
										}
										//Common.setModelDefault(pdAdd, map_HaveColumnsList, map_SetColumnsList);
										//表名
										pdAdd.put("TableName", tableName);
										listAdd.add(pdAdd);
									}
								}
								if(sbRet.size()>0){
									StringBuilder sbTitle = new StringBuilder();
									for(String str : sbRet){
										sbTitle.append(str + "  "); // \n
									}
									commonBase.setCode(2);
									commonBase.setMessage(sbTitle.toString());
								} else {
									//此处执行集合添加 
									auditeditService.batchImport(listAdd);
									commonBase.setCode(0);
									commonBase.setMessage(strErrorMessage);
								}
							}
						} else {
							commonBase.setCode(-1);
							commonBase.setMessage("TranslateUtil");
						}
					
				}
			}
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "auditedit");
		mv.addObject("which", SelectedTableNo);
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("SystemDateTime", SystemDateTime);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	//public void downExcel(HttpServletResponse response)throws Exception{
	public ModelAndView downExcel(JqPage page) throws Exception{
		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String tableName = getAuditTableCode(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode, tmplconfigService);
		//Map<String, Object> DicList = Common.GetDicList(SelectedTableNo, SelectedDepartCode, 
		//		tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, "");
		
		PageData getQueryFeildPd = new PageData();
		//工资分的类型, 只有工资返回值
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		
		if(CheckStaffOrNot(SelectedTableNo)){
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		}
		getPd.put("QueryFeild", QueryFeild);
		//表名
		getPd.put("TableName", tableName);
		page.setPd(getPd);
		
		List<PageData> varOList = auditeditService.exportModel(page);
		return null;//export(varOList, "AuditEdit", map_SetColumnsList, DicList); //工资明细
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出AuditEdit到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String emplGroupType = Corresponding.getUserGroupTypeFromTmplType(SelectedTableNo);
		String tableName = getAuditTableCode(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		
		//Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, SelectedDepartCode, tmplconfigService);
		//Map<String, Object> DicList = Common.GetDicList(SelectedTableNo, SelectedDepartCode, 
		//		tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, "");
		
		PageData getQueryFeildPd = new PageData();
		//工资分的类型, 只有工资返回值
		getQueryFeildPd.put("USER_GROP", emplGroupType);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		
		if(CheckStaffOrNot(SelectedTableNo)){
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
		}
		getPd.put("QueryFeild", QueryFeild);
		//表名
		getPd.put("TableName", tableName);
		//页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		page.setPd(getPd);
		List<PageData> varOList = auditeditService.exportList(page);
		return null;//export(varOList, "", map_SetColumnsList, DicList);
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView export(List<PageData> varOList, String ExcelName, 
			Map<String, TmplConfigDetail> map_SetColumnsList,
			Map<String, Object> DicList){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		dataMap.put("filename", ExcelName);
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
	
	private List<String> getMustNotEditList(String which){
		// 设置必定不用编辑的列
		List<String> MustNotEditList = new ArrayList<String>();
		// 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
		//keyListBase = getKeyListBase();
		if (which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
                ||which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
				||which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
				||which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
				||which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())) {
			MustNotEditList = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7", "USER_GROP");
			//keyListBase = getKeyListBase();
		} else if (which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
				||which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())) {
			MustNotEditList = Arrays.asList("BUSI_DATE", "DEPT_CODE", "CUST_COL7");
			//keyListBase = getKeyListBase();
		}
		return MustNotEditList;
	}

	private String getAuditTableCode(String which) {
		String tableCode = "";
		if (which != null){
			if (which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
                    ||which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())) {
				tableCode = "tb_staff_audit";
			} else if (which.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())) {
				tableCode = "tb_social_inc_audit";
			} else if (which.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())) {
				tableCode = "tb_house_fund_audit";
			}
		}
		return tableCode;
	}
	private Boolean CheckStaffOrNot(String which) {
		if (which != null){
			if (which.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
                    ||which.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
					||which.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
					||which.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())) {
				return true;
			}
		}
		return false;
	}
	
	private String CheckMustSelectedAndSame(String SelectedTableNo, String emplGroupType, 
			String CUST_COL7, String DEPT_CODE, 
			String ShowDataDepartCode, String ShowDataCustCol7, String DepartTreeSource){
		String strRut = "";
		if(!(CUST_COL7 != null && !CUST_COL7.trim().equals(""))){
			strRut += "查询条件中的账套必须选择！";
		} else {
		    if(!CUST_COL7.equals(ShowDataCustCol7)){
				strRut += "查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作！";
		    }
		}
		if(!(DEPT_CODE != null && !DEPT_CODE.trim().equals(""))){
			strRut += "查询条件中的责任中心不能为空！";
		} else {
		    if(!String.valueOf(0).equals(DepartTreeSource) && !DEPT_CODE.equals(ShowDataDepartCode)){
				strRut += "查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作！";
		    }
		}
		if(CheckStaffOrNot(SelectedTableNo)){
			if(!(emplGroupType!=null && !emplGroupType.trim().equals(""))){
				strRut += "工资对应的员工组编码为空！";
			}
		}
		return strRut;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
