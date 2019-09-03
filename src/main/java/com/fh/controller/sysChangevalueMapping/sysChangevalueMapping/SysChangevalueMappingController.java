package com.fh.controller.sysChangevalueMapping.sysChangevalueMapping;

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
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Jurisdiction;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysChangevalueMapping.sysChangevalueMapping.SysChangevalueMappingManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;

/** 
 * 说明： 
 * 创建人：zhangxiaoliu
 * 创建时间：2017-09-14
 * @version
 */
@Controller
@RequestMapping(value="/sysChangevalueMapping")
public class SysChangevalueMappingController extends BaseController {
	
	String menuUrl = "sysChangevalueMapping/list.do"; //菜单地址(权限用)
	@Resource(name="sysChangevalueMappingService")
	private SysChangevalueMappingManager sysChangevalueMappingService;
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	
	//当前期间,取自tb_system_config的SystemDateTime字段
	//String SystemDateTime = "";
	
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("TYPE_CODE", "BILL_OFF", "DEPT_CODE");
    //设置必定不用编辑的列            SERIAL_NO 设置字段类型是数字，但不管隐藏 或显示都必须保存的
    List<String> MustNotEditList = Arrays.asList("TYPE_CODE", "BILL_OFF", "DEPT_CODE");
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表SysChangevalueMapping");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("sysChangevalueMapping/sysChangevalueMapping/sysChangevalueMapping_list");
		PageData getPd = this.getPageData();
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		
		//BILL_OFF FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));

		//TYPE_CODE PZTYPE 凭证字典
		mv.addObject("PZTYPE", DictsUtil.getDictsByParentCode(dictionariesService, "PZTYPE"));

		// *********************加载单位树  DEPT_CODE*******************************
		List<PageData> treeSource = DictsUtil.getDepartmentSelectTreeSourceList(departmentService);
		if (treeSource != null && treeSource.size() > 0) {
			JSONArray arr = JSONArray.fromObject(treeSource);
			mv.addObject("zTreeNodes", null == arr ? "" : arr.toString());
		}
		// ***********************************************************

		String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);

		//TYPE_CODE PZTYPE 凭证字典
		mv.addObject("PZTYPE", DictsUtil.getDictsByParentCode(dictionariesService, "PZTYPE"));
		String typeCodeValus = DictsUtil.getDicValue(dictionariesService, "PZTYPE");
		String typeCodeStringAll = ":[All];" + typeCodeValus;
		String typeCodeStringSelect = ":;" + typeCodeValus;
		mv.addObject("typeCodeStrAll", typeCodeStringAll);
		mv.addObject("typeCodeStrSelect", typeCodeStringSelect);
		
		String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);

		String mappingCodeValus = DictsUtil.getDicValue(dictionariesService, "CHANGEVALUE");
		String mappingCodeStringAll = ":[All];" + mappingCodeValus;
		String mappingCodeStringSelect = ":;" + mappingCodeValus;
		mv.addObject("mappingCodeStrAll", mappingCodeStringAll);
		mv.addObject("mappingCodeStrSelect", mappingCodeStringSelect);
		
		mv.addObject("pd", getPd);
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表");

		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//责任中心
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//int departSelf = Common.getDepartSelf(departmentService);
		//if(departSelf == 1){
		//	SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		//}

		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("TYPE_CODE", SelectedTypeCode);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedTypeCode!=null && !SelectedTypeCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
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
		page.setPd(getPd);
		List<PageData> varList = sysChangevalueMappingService.JqPage(page);	//列出Betting列表
		int records = sysChangevalueMappingService.countJqGridExtend(page);
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		
		return result;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public @ResponseBody CommonBase save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增SysChangevalueMapping");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData pd = this.getPageData();
		//账套
		String SelectedCustCol7 = pd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = pd.getString("ShowDataCustCol7");
		//凭证字典
		String SelectedTypeCode = pd.getString("SelectedTypeCode");
		String ShowDataTypeCode = pd.getString("ShowDataTypeCode");
		//责任中心
		String SelectedDepartCode = pd.getString("SelectedDepartCode");
		String ShowDataDepartCode = pd.getString("ShowDataDepartCode");
		/*int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
			ShowDataDepartCode = Jurisdiction.getCurrentDepartmentID();
		}*/
		String oper = pd.getString("oper");
		//当前区间
		String SystemDateTime = pd.getString("SystemDateTime");

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
			
			pd.put("TYPE_CODE", SelectedTypeCode);
			pd.put("BILL_OFF", SelectedCustCol7);
			pd.put("DEPT_CODE", SelectedDepartCode);
			listData.add(pd);
		} else {
			for(String strFeild : MustNotEditList){
				pd.put(strFeild, pd.get(strFeild + TmplUtil.keyExtra));
			}
			pd.put("BUSI_DATE", SystemDateTime);
			listData.add(pd);
			String checkState = CheckStateDelOrUpdate(listData, false);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
		}
		
		String checkState = CheckStateAdd(listData);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		sysChangevalueMappingService.batchUpdateDatabase(listData);
		commonBase.setCode(0);
		return commonBase;
	}

	/**
	 * 批量修改
	 * 
	 * @param
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAll")
	public @ResponseBody CommonBase updateAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		
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
	        
			String checkState = CheckStateAdd(listData);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			sysChangevalueMappingService.batchUpdateDatabase(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}*/
	
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
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        if(null != listData && listData.size() > 0){
			for(PageData pdData : listData){
				pdData.put("BUSI_DATE", SystemDateTime);
			}
			String checkState = CheckStateDelOrUpdate(listData, true);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
    	    sysChangevalueMappingService.deleteAll(listData);
			commonBase.setCode(0);
		}
		return commonBase;
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
	
	private String CheckStateAdd(List<PageData> listData) throws Exception{
		String strRet = "";
		List<PageData> repeatList = sysChangevalueMappingService.getRepeatList(listData);
		if(repeatList!=null && repeatList.size()>0){
			strRet = Message.HaveRepeatRecord;
		}
		return strRet;
	}

	private String CheckStateDelOrUpdate(List<PageData> listData, Boolean bolDel) throws Exception{
		String strRet = "";
		List<PageData> repeatList = sysChangevalueMappingService.getChangeValueList(listData, bolDel);
		if(repeatList!=null && repeatList.size()>0){
			strRet = Message.SetValue;
		}
		return strRet;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
