package com.fh.controller.dataInput.dataInput;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.fh.controller.common.CheckSystemDateTime;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.SelectBillCodeOptions;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.util.PageData;
import com.fh.util.SqlTools;

import net.sf.json.JSONArray;

import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.service.dataInput.dataInput.DataInputManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysChangevalueMapping.sysChangevalueMapping.SysChangevalueMappingManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
@Controller
@RequestMapping(value="/dataInput")
public class DataInputController extends BaseController {
	
	String menuUrl = "dataInput/list.do"; //菜单地址(权限用)
	@Resource(name="dataInputService")
	private DataInputManager dataInputService;
	@Resource(name="sysChangevalueMappingService")
	private SysChangevalueMappingManager sysChangevalueMappingService;
	
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	//当前期间,取自tb_system_config的SystemDateTime字段
	//String SystemDateTime = "";
    //设置必定不用编辑的列
    List<String> MustNotEditList = Arrays.asList("TYPE_CODE", "BILL_OFF", "DEPT_CODE", "BUSI_DATE");

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("TYPE_CODE", "BILL_OFF", "DEPT_CODE", "BUSI_DATE");
    
	//下拉列表第一项
	String SelectedTypeCodeFirstShow = "请选择凭证类型";
	String SelectedDepartCodeFirstShow = "请选择责任中心";
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表dataInput");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();

		//单号下拉列表
		getPd.put("SelectedTypeCodeFirstShow", SelectedTypeCodeFirstShow);
		getPd.put("InitSelectedTypeCodeOptions", SelectBillCodeOptions.getSelectDicOptions(null, SelectedTypeCodeFirstShow));
		getPd.put("SelectedDepartCodeFirstShow", SelectedDepartCodeFirstShow);
		getPd.put("InitSelectedDepartCodeOptions", SelectBillCodeOptions.getSelectDicOptions(null, SelectedDepartCodeFirstShow));

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("dataInput/dataInput/dataInput_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);

		/*//BILL_OFF FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		//TYPE_CODE PZTYPE 凭证字典
		mv.addObject("PZTYPE", DictsUtil.getDictsByParentCode(dictionariesService, "PZTYPE"));
		// *********************加载单位树  DEPT_CODE*******************************
		List<PageData> treeSource = DictsUtil.getDepartmentSelectTreeSourceList(departmentService);
		if (treeSource != null && treeSource.size() > 0) {
			JSONArray arr = JSONArray.fromObject(treeSource);
			mv.addObject("zTreeNodes", null == arr ? "" : arr.toString());
		}
		// **********************************************************
		 */
		//BILL_OFF FMISACC 帐套字典
		List<Dictionaries> listBiffOff = sysChangevalueMappingService.getSelectBillOffList(new PageData());
		mv.addObject("FMISACC", listBiffOff);

		/*String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);
		
		String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);

		String typeCodeValus = DictsUtil.getDicValue(dictionariesService, "PZTYPE");
		String typeCodeStringAll = ":[All];" + typeCodeValus;
		String typeCodeStringSelect = ":;" + typeCodeValus;
		mv.addObject("typeCodeStrAll", typeCodeStringAll);
		mv.addObject("typeCodeStrSelect", typeCodeStringSelect);

		String changeColValus = DictsUtil.getDicValue(dictionariesService, "CHANGEVALUE");
		String changeColStringAll = ":[All];" + changeColValus;
		String changeColStringSelect = ":;" + changeColValus;
		mv.addObject("changeColStrAll", changeColStringAll);
		mv.addObject("changeColStrSelect", changeColStringSelect);*/

		mv.addObject("pd", getPd);
		return mv;
	}

	/**凭证列表
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getTypeCodeList")
	public @ResponseBody CommonBase getTypeCodeList() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		
		PageData pdSet = new PageData();
		pdSet.put("BILL_OFF", SelectedCustCol7);
		List<Dictionaries> list = sysChangevalueMappingService.getSelectTypeCodeList(pdSet);
		
		String returnString = SelectBillCodeOptions.getSelectDicOptions(list, SelectedTypeCodeFirstShow);
		commonBase.setMessage(returnString);
		commonBase.setCode(0);
		return commonBase;
	}

	/**责任中心下拉列表
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getDepartCodeList")
	public @ResponseBody CommonBase getDepartCodeList() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		
		PageData pdSet = new PageData();
		pdSet.put("BILL_OFF", SelectedCustCol7);
		pdSet.put("TYPE_CODE", SelectedTypeCode);
		List<Dictionaries> list = sysChangevalueMappingService.getSelectDeptCodeList(pdSet);
		
		String returnString = SelectBillCodeOptions.getSelectDicOptions(list, SelectedDepartCodeFirstShow);
		commonBase.setMessage(returnString);
		commonBase.setCode(0);
		return commonBase;
	}

	/**列选项
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getChangeColsList")
	public @ResponseBody CommonBase getChangeColsList() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		
		PageData pdSet = new PageData();
		pdSet.put("BILL_OFF", SelectedCustCol7);
		pdSet.put("TYPE_CODE", SelectedTypeCode);
		pdSet.put("DEPT_CODE", SelectedDepartCode);
		List<Dictionaries> list = sysChangevalueMappingService.getSelectMapCodeList(pdSet);
		
		StringBuilder returnString = new StringBuilder();
		for (Dictionaries dic : list) {
			if (returnString != null && !returnString.toString().trim().equals("")) {
				returnString.append(";");
			}
			returnString.append(dic.getDICT_CODE() + ":" + dic.getNAME());
		}
		
		commonBase.setMessage(returnString.toString());
		commonBase.setCode(0);
		
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
		
		PageData pdTransfer = setTransferPd(getPd);
		page.setPd(pdTransfer);
		
		List<PageData> varList = dataInputService.JqPage(page);	//列出Betting列表
		int records = dataInputService.countJqGridExtend(page);
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
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
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		String ShowDataTypeCode = getPd.getString("ShowDataTypeCode");
		//责任中心
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		//操作
		String oper = getPd.getString("oper");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		
		//必定不用编辑的列  MustNotEditList Arrays.asList("BUSI_DATE");
		List<PageData> listData = new ArrayList<PageData>();
		if(oper.equals("add")){
			//判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
					SelectedTypeCode, ShowDataTypeCode, 
					SelectedDepartCode, ShowDataDepartCode);
			if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
				return commonBase;
			}
			//MustNotEditList = Arrays.asList("TYPE_CODE", "BILL_OFF", "DEPT_CODE", "BUSI_DATE");
			getPd.put("TYPE_CODE", SelectedTypeCode);
			getPd.put("BILL_OFF", SelectedCustCol7);
			getPd.put("DEPT_CODE", SelectedDepartCode);
			getPd.put("BUSI_DATE", SystemDateTime);
			listData.add(getPd);
		} else {
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
			listData.add(getPd);
		}

		String checkState = CheckState(SystemDateTime, listData);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		
		if(oper.equals("add")){
			dataInputService.save(getPd);
			commonBase.setCode(0);
		} else {
			dataInputService.batchUpdateDatabase(listData);
			commonBase.setCode(0);
		}
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
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		
		if(null != listData && listData.size() > 0){
			for(PageData pdData : listData){
				for(String strFeild : MustNotEditList){
					pdData.put(strFeild, pdData.get(strFeild + TmplUtil.keyExtra));
				}
			}
			String checkState = CheckState(SystemDateTime, listData);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			dataInputService.batchUpdateDatabase(listData);
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
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		
        if(null != listData && listData.size() > 0){
			dataInputService.deleteAll(listData);
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
		//账套
		String SelectedCustCol7 = pd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = pd.getString("SelectedTypeCode");
		//单位
		String SelectedDepartCode = pd.getString("SelectedDepartCode");
		//当前区间
		String SystemDateTime = pd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		//
		//JSONArray array = JSONArray.fromObject(pd.getString("deptIds"));
		//List<String> listCode = (List<String>) JSONArray.toCollection(array, String.class);// 过时方法
		//Boolean bolSelf = false;
		//if(listCode.contains(SelectedDepartCode)){
		//	bolSelf = true;
		//}
		List<String> listSelfDate = getListDate(SystemDateTime, true);
		List<String> listSelfCode = Arrays.asList(SelectedDepartCode);
		if(listSelfDate!=null&&listSelfDate.size()>0 && listSelfCode!=null&&listSelfCode.size()>0){//bolSelf && 
			pd.put("BUSI_DATES_SELF", listSelfDate);
			pd.put("DEPT_CODES_SELF", listSelfCode);
		}

		//List<String> listOthersDate = getListDate(SelectedBusiDate, false);
		//List<String> listOthersCode = listCode;
		//listOthersCode.remove(SelectedDepartCode);
		//if(listOthersDate!=null&&listOthersDate.size()>0 && listOthersCode!=null&&listOthersCode.size()>0){
		//	pd.put("BUSI_DATES_OTHERS", listOthersDate);
		//	pd.put("DEPT_CODES_OTHERS", listOthersCode);
		//}
		
		if ((listSelfDate!=null&&listSelfDate.size()>0 && listSelfCode!=null&&listSelfCode.size()>0)) {//bolSelf && 
				//|| (listOthersDate!=null&&listOthersDate.size()>0 && listOthersCode!=null&&listOthersCode.size()>0)
			String checkState = CheckCopyData(SelectedCustCol7, SelectedTypeCode, SelectedDepartCode, SystemDateTime, pd);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			dataInputService.batchCopyAll(pd);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	private List<String> getListDate(String SelectedBusiDate, Boolean bolSelf){
		List<String> listDate = new ArrayList<String>();
		String strYear = SelectedBusiDate.substring(0, 4);
		int strMonth = Integer.parseInt(SelectedBusiDate.substring(4, 6));
		if(bolSelf){
			strMonth++;
		}
		for(int i=strMonth; i<=12; i++){
			String strI = String.valueOf(i);
			if(i<10){
				strI = "0" + strI;
			}
			listDate.add(strYear + strI);
		}
		return listDate;
	}
	
	private String CheckCopyData(String SelectedCustCol7, String SelectedTypeCode, String SelectedDepartCode,
			String SelectedBusiDate, PageData pd) throws Exception{
		String strRut = "";
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			strRut = "请选择帐套";
		}
		if(!(SelectedTypeCode!=null && !SelectedTypeCode.trim().equals(""))){
			strRut = "请选择凭证类型";
		}
		if(!(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
			strRut = "请选择责任中心";
		}
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			strRut = "当前区间不能为空";
		} else {
			if(SelectedBusiDate.length() != 6){
				strRut = "当前区间格式不对，请联系管理员";
			}
		}
		if(strRut!=null && !strRut.trim().equals("")){
			return strRut;
		}
		List<PageData> getHaveCopyRecord = dataInputService.getHaveCopyRecord(pd);
		if(!(getHaveCopyRecord!=null && getHaveCopyRecord.size()>0)){
    		strRut = Message.NotHaveOperateData;
		}
		return strRut;
	}
	
	private String CheckMustSelectedAndSame(String CUST_COL7, String ShowDataCustCol7,
			String TYPE_CODE, String ShowDataTypeCode, 
			String DEPT_CODE, String ShowDataDepartCode) throws Exception{
		String strRut = "";
		if(!(CUST_COL7 != null && !CUST_COL7.trim().equals(""))){
			strRut += "查询条件中的账套必须选择！";
		} else {
		    if(!CUST_COL7.equals(ShowDataCustCol7)){
				strRut += "查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作！";
		    }
		}
		if(!(TYPE_CODE != null && !TYPE_CODE.trim().equals(""))){
			strRut += "查询条件中的类型必须选择！";
		} else {
		    if(!TYPE_CODE.equals(ShowDataTypeCode)){
				strRut += "查询条件中所选类型与页面显示数据类型不一致，请单击查询再进行操作！";
		    }
		}
		if(!(DEPT_CODE != null && !DEPT_CODE.trim().equals(""))){
			strRut += "查询条件中的责任中心不能为空！";
		} else {
		    if(!DEPT_CODE.equals(ShowDataDepartCode)){
				strRut += "查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作！";
		    }
		}
		return strRut;
	}
	
	private String CheckState(String SystemDateTime, List<PageData> listData) throws Exception{
		String strRut = "";
		if(!(SystemDateTime!=null && !SystemDateTime.trim().equals(""))){
			strRut = Message.SystemDateTimeMustNotKong;
		}
		if(listData!=null && listData.size()>0){
    		List<PageData> getRepeatRecord = dataInputService.getRepeatRecord(listData);
    		if(getRepeatRecord!=null && getRepeatRecord.size()>0){
    			strRut = Message.HaveRepeatRecord;
    		} else {
    			//上一季的数据是否已删除
    			List<PageData> listBiffOff = sysChangevalueMappingService.getDataInputCheckHavaList(listData);
    			if(!(listBiffOff!=null && listBiffOff.size() == listData.size())){
    				strRut = Message.HavaNotHavaChangeCols;
    			}
    		}
		} else {
			strRut = Message.NotTransferOperateData;
		}
		return strRut;
	}
	
	private PageData setTransferPd(PageData getPd) throws Exception{
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SystemDateTime);
		getQueryFeildPd.put("TYPE_CODE", SelectedTypeCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SystemDateTime!=null && !SystemDateTime.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedTypeCode!=null && !SelectedTypeCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		return getPd;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}