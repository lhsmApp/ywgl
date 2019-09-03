package com.fh.controller.staffTdsInfo.staffTdsInfo;

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
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.staffTdsInfo.staffTdsInfo.StaffTdsInfoManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.impl.UserService;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明：个税扣除项导入
 * 创建人：zhangxiaoliu
 * 创建时间：2019-01-28
 */
@Controller
@RequestMapping(value="/staffTdsInfo")
public class StaffTdsInfoController extends BaseController {
	
	String menuUrl = "staffTdsInfo/list.do"; //菜单地址(权限用)
	@Resource(name="staffTdsInfoService")
	private StaffTdsInfoManager staffTdsInfoService;

	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name="tmplconfigdictService")
	private TmplConfigDictService tmplconfigdictService;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name="userService")
	private UserService userService;
	
	//表名
	String TableName = "TB_STAFF_TDS_INFO";
	String TableNameSummy = "TB_STAFF_SUMMY_BILL";

    //设置必定不用编辑的列
    List<String> MustNotEditList = Arrays.asList("BUSI_DATE");
    //获取带__的列，后续删除之类的有用
	private List<String> keyListBase = Arrays.asList("BUSI_DATE", "STAFF_IDENT");
	//设置必定不为空的列
	private List<String> MustInputList = Arrays.asList("BUSI_DATE", "STAFF_IDENT");
    
	Map<String, TableColumns> map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
    Map<String, TmplConfigDetail> map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	Map<String, Object> map_DicList = new LinkedHashMap<String, Object>();
	//底行显示的求和字段
    StringBuilder SqlUserdata = new StringBuilder();    
    //StaffTdsRestDeptCode 01001 个税扣除可操作除银川、武汉、深港的责任中心
    List<String> SysConfigStaffTdsRestDeptCode = new ArrayList<String>();
    //StaffTdsSelfDeptCode 01009,01017,01022 个税扣除可操作自己的责任中心（银川、武汉、深港）
    List<String> SysConfigStaffTdsSelfDeptCode = new ArrayList<String>();


	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表StaffTdsInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("staffTdsInfo/staffTdsInfo/staffTdsInfo_list");
		mv.addObject("jqGridColModel", "[]");
		mv.addObject("jqGridColModelMessage", "");

		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);

		map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	    map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
		map_DicList = new LinkedHashMap<String, Object>();
	    SqlUserdata = new StringBuilder();

	    //StaffTdsRestDeptCode 01001 个税扣除可操作除银川、武汉、深港的责任中心
        PageData pdStaffTdsRestDeptCode = new PageData();
        pdStaffTdsRestDeptCode.put("KEY_CODE", SysConfigKeyCode.StaffTdsRemitRestDeptCode);
        String StaffTdsRestDeptCode = sysConfigManager.getSysConfigByKey(pdStaffTdsRestDeptCode);
        SysConfigStaffTdsRestDeptCode = Arrays.asList(StaffTdsRestDeptCode.split(","));
        if(SysConfigStaffTdsRestDeptCode==null) SysConfigStaffTdsRestDeptCode = new ArrayList<String>();

        //StaffTdsSelfDeptCode 01009,01017,01022 个税扣除可操作自己的责任中心（银川、武汉、深港）
        PageData pdStaffTdsSelfDeptCode = new PageData();
        pdStaffTdsSelfDeptCode.put("KEY_CODE", SysConfigKeyCode.StaffTdsRemitSelfDeptCode);
        String StaffTdsSelfDeptCode = sysConfigManager.getSysConfigByKey(pdStaffTdsSelfDeptCode);
        SysConfigStaffTdsSelfDeptCode = Arrays.asList(StaffTdsSelfDeptCode.split(","));
        if(SysConfigStaffTdsSelfDeptCode==null) SysConfigStaffTdsSelfDeptCode = new ArrayList<String>();

	    //获取数据库中表的结构信息,确定界面显示的全部列
        List<TableColumns> List_HaveColumnsList = Common.GetHaveColumnsListByTableName(TableName, tmplconfigService);
		//配置表中StaffTDSFuncDisp和StaffTDSFuncDispCN，确定界面列的隐藏与显示
        PageData pdStaffTDSFuncDisp = new PageData();
        pdStaffTDSFuncDisp.put("KEY_CODE", SysConfigKeyCode.StaffTDSFuncDisp);
        String staffTDSFuncDisp = sysConfigManager.getSysConfigByKey(pdStaffTDSFuncDisp);
        String[] SysConfigCodeList = staffTDSFuncDisp.split(",");
        PageData pdStaffTDSFuncDispCN = new PageData();
        pdStaffTDSFuncDispCN.put("KEY_CODE", SysConfigKeyCode.StaffTDSFuncDispCN);
        String staffTDSFuncDispCN = sysConfigManager.getSysConfigByKey(pdStaffTDSFuncDispCN);
        String[] SysConfigNameList = staffTDSFuncDispCN.split(",");
        
        if(SysConfigCodeList.length != SysConfigNameList.length){
    		mv.addObject("jqGridColModelMessage", "配置参数StaffTDSFuncDisp和StaffTDSFuncDispCN不对应！");
        } else {
    		List<TmplConfigDetail> List_SetColumnsList = new ArrayList<TmplConfigDetail>();
        	//根据配置表中StaffTDSFuncDisp和StaffTDSFuncDispCN拼成map_DicList、List_SetColumnsList和map_SetColumnsList的显示列
        	for(int i=0; i<SysConfigCodeList.length; i++){
        		String strCode = SysConfigCodeList[i].toUpperCase().trim();
        		TmplConfigDetail add = new TmplConfigDetail(strCode, SysConfigNameList[i].trim(), "1", false);
                if(("DEPT_CODE").toUpperCase().equals(strCode) || ("UNITS_CODE").toUpperCase().equals(strCode)){
                	add.setDICT_TRANS("oa_department");
                }
                List_SetColumnsList.add(add);
        		map_SetColumnsList.put(strCode, add);
				String getDICT_TRANS = add.getDICT_TRANS();
				if (getDICT_TRANS != null && !getDICT_TRANS.trim().equals("") && !map_DicList.containsKey(getDICT_TRANS)) {
					Common.getDicValue(map_DicList, getDICT_TRANS,
							tmplconfigdictService, dictionariesService, 
							departmentService, userService, null);
				}
        	}
        	//List_HaveColumnsList设置map_HaveColumnsList、SqlUserdata、map_DicList、List_SetColumnsList和map_SetColumnsList的显示列
        	for(TableColumns haveCol : List_HaveColumnsList){
        		String strColumn_name = haveCol.getColumn_name().toUpperCase().trim();
        		map_HaveColumnsList.put(strColumn_name, haveCol);
        		Boolean haveInSet = false;
    			Boolean isNum = Common.IsNumFeild(haveCol.getData_type());
    			if(isNum){
    				if(SqlUserdata!=null && !SqlUserdata.toString().trim().equals("")){
    					SqlUserdata.append(", ");
    				}
    				SqlUserdata.append(" sum(" + strColumn_name + ") " + strColumn_name);
    			}
        		for(TmplConfigDetail setCol : List_SetColumnsList){
        			if(strColumn_name.equals(setCol.getCOL_CODE())){
        				setCol.setIsNum(isNum);
        				haveInSet = true;
        			}
        		}
        		if(!haveInSet){
            		TmplConfigDetail add = new TmplConfigDetail(strColumn_name, strColumn_name, "0", isNum);
                    if(("DEPT_CODE").toUpperCase().equals(strColumn_name) || ("UNITS_CODE").toUpperCase().equals(strColumn_name)){
                    	add.setDICT_TRANS("oa_department");
                    }
                    List_SetColumnsList.add(add);
            		map_SetColumnsList.put(strColumn_name, add);
    				String getDICT_TRANS = add.getDICT_TRANS();
    				if (getDICT_TRANS != null && !getDICT_TRANS.trim().equals("") && !map_DicList.containsKey(getDICT_TRANS)) {
    					Common.getDicValue(map_DicList, getDICT_TRANS,
    							tmplconfigdictService, dictionariesService, 
    							departmentService, userService, null);
    				}
        		}
        	}
        	//List_HaveColumnsList, List_SetColumnsList获取界面显示结构
        	TmplStaffTdsRemit tmpl = new TmplStaffTdsRemit(tmplconfigdictService, dictionariesService, 
    				departmentService,userService, keyListBase, MustInputList);
    		String jqGridColModel = tmpl.generateStructure(List_HaveColumnsList, List_SetColumnsList, MustNotEditList);
    		mv.addObject("jqGridColModel", jqGridColModel);
        }
		mv.addObject("pd", getPd);
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表StaffTdsInfo");

		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		//String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		//页面显示数据的年月
		getPd.put("SelectedBusiDate", SelectedBusiDate);
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		String QueryFeild = "";
		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
			QueryFeild += " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
			QueryFeild += " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
		} else {
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
		page.setPd(getPd);
		List<PageData> varList = staffTdsInfoService.JqPage(page);	//列出Betting列表
		int records = staffTdsInfoService.countJqGridExtend(page);
		PageData userdata = null;
		if(SqlUserdata!=null && !SqlUserdata.toString().trim().equals("")){
			//底行显示的求和与平均值字段
			getPd.put("Userdata", SqlUserdata.toString());
		    userdata = staffTdsInfoService.getFooterSummary(page);
		}
		
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
		logBefore(logger, Jurisdiction.getUsername()+"修改StaffTdsInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限

		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//操作
		String oper = getPd.getString("oper");

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		if(!(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID) || SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID))){
			commonBase.setCode(2);
			commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
			return commonBase;
		}
		
		//判断ShowDataBusiDate和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(ShowDataBusiDate, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}

		String getDEPT_CODE = (String) getPd.get("DEPT_CODE");
		if(!(getDEPT_CODE!=null && !getDEPT_CODE.trim().equals(""))){
			getPd.put("DEPT_CODE", dEPARTMENT_ID);
			getDEPT_CODE = dEPARTMENT_ID;
		}
		if(oper.equals("add")){
		    //必定不用编辑的列  MustNotEditList Arrays.asList("BUSI_DATE");
			getPd.put("BUSI_DATE", ShowDataBusiDate);
			//必须设置，在查询重复数据时有用
			getPd.put("BUSI_DATE" + TmplUtil.keyExtra, " ");
			getPd.put("STAFF_IDENT" + TmplUtil.keyExtra, " ");
		} else {
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
		}
		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
			if(!dEPARTMENT_ID.equals(getDEPT_CODE)){
				commonBase.setCode(2);
			    commonBase.setMessage("只能添加登录人责任中心！");
				return commonBase;
			}
		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
			if(SysConfigStaffTdsSelfDeptCode.contains(getDEPT_CODE)){
				commonBase.setCode(2);
			    commonBase.setMessage("只能添加除了特定责任中心的记录！");
				return commonBase;
			}
		} else {
			commonBase.setCode(2);
		    commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
			return commonBase;
		}
		//查询重复数据
		List<PageData> listCheckRepeat = new ArrayList<PageData>();
		listCheckRepeat.add(getPd);
		String checkRepeat = CheckRepeat(listCheckRepeat);
		if(checkRepeat!=null && !checkRepeat.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkRepeat);
			return commonBase;
		}
		//haveColumnsList和map_SetColumnsList，设置保存的数据列及对应值
		Common.setModelDefault(getPd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
		String strCanDel = "";
		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
			strCanDel = " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
			strCanDel = " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
		} else {
			strCanDel = " and 1 != 1 ";
		}
		getPd.put("CanDel", strCanDel);
		
		List<PageData> listData = new ArrayList<PageData>();
		listData.add(getPd);
		staffTdsInfoService.batchUpdateDatabase(listData);
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
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		if(!(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID) || SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID))){
			commonBase.setCode(2);
			commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
			return commonBase;
		}
		//判断ShowDataBusiDate和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(ShowDataBusiDate, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		
		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		if(null != listData && listData.size() > 0){
	        for(PageData item : listData){
				for(String strFeild : MustNotEditList){
					item.put(strFeild, item.get(strFeild + TmplUtil.keyExtra));
				}
				String getDEPT_CODE = (String) item.get("DEPT_CODE");
				if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
					if(!dEPARTMENT_ID.equals(getDEPT_CODE)){
						commonBase.setCode(2);
					    commonBase.setMessage("只能添加登录人责任中心！");
						return commonBase;
					}
				} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
					if(SysConfigStaffTdsSelfDeptCode.contains(getDEPT_CODE)){
						commonBase.setCode(2);
					    commonBase.setMessage("只能添加除了特定责任中心的记录！");
						return commonBase;
					}
				} else {
					commonBase.setCode(2);
				    commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
					return commonBase;
				}
				//haveColumnsList和map_SetColumnsList，设置保存的数据列及对应值
	        	Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
	    		String strCanDel = "";
	    		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
	    			strCanDel = " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
	    		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
	    			strCanDel = " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
	    		} else {
	    			strCanDel = " and 1 != 1 ";
	    		}
	    		item.put("CanDel", strCanDel);
	        }
			//查询重复数据
			String checkRepeat = CheckRepeat(listData);
			if(checkRepeat!=null && !checkRepeat.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkRepeat);
				return commonBase;
			}
			staffTdsInfoService.batchUpdateDatabase(listData);
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
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		if(!(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID) || SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID))){
			commonBase.setCode(2);
			commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
			return commonBase;
		}
		//判断ShowDataBusiDate和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(ShowDataBusiDate, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}

		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        if(null != listData && listData.size() > 0){
			staffTdsInfoService.deleteAll(listData);
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
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		if(!(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID) || SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID))){
			commonBase.setCode(2);
			commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
		}
		if(commonBase.getCode()==-1){
			//判断ShowDataBusiDate和配置表里的当前区间是否一致
			String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(ShowDataBusiDate, sysConfigManager, false);
			if(mesDateTime!=null && !mesDateTime.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(mesDateTime);
			}
		}

		if(commonBase.getCode()==-1){
			//判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
			if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			}
		}
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "staffTdsInfo");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		mv.addObject("StringDataRows", "");
		return mv;
	}

	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
    ///*
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		String strErrorMessage = "";

		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		if(!(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID) || SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID))){
			commonBase.setCode(2);
			commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
		}
		if(commonBase.getCode()==-1){
		//判断ShowDataBusiDate和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(ShowDataBusiDate, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		}
		}
		if(commonBase.getCode()==-1){
			//判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
			if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			} 
		}
		List<PageData> listAdd = new ArrayList<PageData>();
		if(commonBase.getCode()==-1){
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
				if(map_SetColumnsList != null && map_SetColumnsList.size() > 0){
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
					}
				}

				// 调用解析工具包
				testExcel = new LeadingInExcelToPageData<PageData>(formart);
				// 解析excel，获取客户信息集合

				uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
						titleAndAttribute, map_HaveColumnsList, map_SetColumnsList, map_DicList, false, false,
						null, null);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("读取Excel文件错误", e);
				throw new CustomException("读取Excel文件错误:" + e.getMessage(),false);
			}
			boolean judgement = false;

			Map<String, String> returnErrorCostomn =  (Map<String, String>) uploadAndReadMap.get(2);
			Map<String, String> returnErrorMust =  (Map<String, String>) uploadAndReadMap.get(3);

			List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
			if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
				judgement = true;
			}
			if (judgement) {
				int listSize = listUploadAndRead.size();
				if(listSize > 0){
					List<String> sbRetFeild = new ArrayList<String>();
					String strRetUserCode = "";
					String sbRetMust = "";
					for(int i=0;i<listSize;i++){
						PageData pdAdd = listUploadAndRead.get(i);
						if(pdAdd.size() <= 0){
							continue;
						}
						String getUSER_CODE = (String) pdAdd.get("USER_CODE");
						String getSTAFF_IDENT = (String) pdAdd.get("STAFF_IDENT");
					    if(!(getUSER_CODE!=null && !getUSER_CODE.trim().equals(""))){
					    	strRetUserCode = "导入人员编码不能为空！";
					    	break;
					    } else if (!(getSTAFF_IDENT!=null && !getSTAFF_IDENT.trim().equals(""))){
					    	strRetUserCode = "导入身份证号不能为空！";
					    	break;
					    } else {
							String getMustMessage = returnErrorMust==null ? "" : returnErrorMust.get(getUSER_CODE);
							String getCustomnMessage = returnErrorCostomn==null ? "" : returnErrorCostomn.get(getUSER_CODE);
							if(getMustMessage!=null && !getMustMessage.trim().equals("")){
								sbRetMust += "员工编号" + getUSER_CODE + "身份证号" + getSTAFF_IDENT + "：" + getMustMessage + " ";
							}
							if(getCustomnMessage!=null && !getCustomnMessage.trim().equals("")){
								strErrorMessage += "员工编号" + getUSER_CODE + "身份证号" + getSTAFF_IDENT + "：" + getCustomnMessage + " ";
							}
							String getBUSI_DATE = (String) pdAdd.get("BUSI_DATE");
							if(!(getBUSI_DATE!=null && !getBUSI_DATE.trim().equals(""))){
								pdAdd.put("BUSI_DATE", ShowDataBusiDate);
								getBUSI_DATE = ShowDataBusiDate;
							}
							if(!ShowDataBusiDate.equals(getBUSI_DATE)){
								if(!sbRetFeild.contains("导入区间和当前区间必须一致！")){
									sbRetFeild.add("导入区间和当前区间必须一致！");
								}
							}
							String getDEPT_CODE = (String) pdAdd.get("DEPT_CODE");
							if(!(getDEPT_CODE!=null && !getDEPT_CODE.trim().equals(""))){
								pdAdd.put("DEPT_CODE", dEPARTMENT_ID);
								getDEPT_CODE = dEPARTMENT_ID;
							}
							if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
								if(!dEPARTMENT_ID.equals(getDEPT_CODE)){
									if(!sbRetFeild.contains("只能导入登录人责任中心！")){
										sbRetFeild.add("只能导入登录人责任中心！");
									}
								}
							} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
								if(SysConfigStaffTdsSelfDeptCode.contains(getDEPT_CODE)){
									if(!sbRetFeild.contains("只能导入除了特定责任中心的记录！")){
										sbRetFeild.add("只能导入除了特定责任中心的记录！");
									}
								}
							} else {
								if(!sbRetFeild.contains("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！")){
									sbRetFeild.add("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
								}
							}
							/*
							String getUNITS_CODE = (String) pdAdd.get("UNITS_CODE");
							if(!(getUNITS_CODE!=null && !getUNITS_CODE.trim().equals(""))){
								if(!sbRetFeild.contains("所属二级单位不能为空！")){
									sbRetFeild.add("所属二级单位不能为空！");
								}
							}*/
							//必须设置，在查询重复数据时有用
							pdAdd.put("BUSI_DATE" + TmplUtil.keyExtra, " ");
							pdAdd.put("STAFF_IDENT" + TmplUtil.keyExtra, " ");
							//haveColumnsList和map_SetColumnsList，设置保存的数据列及对应值
							Common.setModelDefault(pdAdd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
				    		String strCanDel = "";
				    		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
				    			strCanDel = " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
				    		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
				    			strCanDel = " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
				    		} else {
				    			strCanDel = " and 1 != 1 ";
				    		}
				    		pdAdd.put("CanDel", strCanDel);
							listAdd.add(pdAdd);
						}
					}
					if(strRetUserCode!=null && !strRetUserCode.trim().equals("")){
						commonBase.setCode(2);
						commonBase.setMessage(strRetUserCode);
					} else {
						if(sbRetMust!=null && !sbRetMust.trim().equals("")){
							commonBase.setCode(3);
							commonBase.setMessage("字典无此翻译, 不能导入： " + sbRetMust);
						} else {
							if(sbRetFeild.size()>0){
								StringBuilder sbTitle = new StringBuilder();
								for(String str : sbRetFeild){
									sbTitle.append(str + "  "); // \n
								}
								commonBase.setCode(3);
								commonBase.setMessage(sbTitle.toString());
							} else {
								if(!(listAdd!=null && listAdd.size()>0)){
									commonBase.setCode(2);
									commonBase.setMessage("请导入符合条件的数据！");
								} else {
									String checkRepeat = CheckRepeat(listAdd);
									if(checkRepeat!=null && !checkRepeat.trim().equals("")){
										//重复数据，要在界面提示是否覆盖
										commonBase.setCode(9);
										commonBase.setMessage(checkRepeat);
									} else {
										//此处执行集合添加 
										staffTdsInfoService.batchUpdateDatabase(listAdd);
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
		mv.addObject("local", "staffTdsInfo");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		//InsertField、InsertVale、InsertLogVale字段全设置成""，listAdd可变成listAdd传递
		for(PageData pdAdd : listAdd){
			Common.setModelDefault(pdAdd);
    		pdAdd.put("CanDel", "");
		}
		mv.addObject("StringDataRows", JSONArray.fromObject(listAdd).toString().replaceAll("'", "\""));//
		return mv;
	}
	 /**覆盖添加
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/coverAdd")
	public @ResponseBody CommonBase coverAdd() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//判断ShowDataBusiDate和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(ShowDataBusiDate, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		
		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		if(!(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID) || SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID))){
			commonBase.setCode(2);
			commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
			return commonBase;
		}
		
	    Object DATA_ROWS = getPd.get("StringDataRows");
	    String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		if(null != listData && listData.size() > 0){
			String strCanDel = "";
    		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
    			strCanDel = " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
    		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
    			strCanDel = " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
    		} else {
    			strCanDel = " and 1 != 1 ";
    		}
	        for(PageData item : listData){
	        	Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
    			item.put("CanDel",  strCanDel);
	        }
	        //直接删除添加，不判断重复数据
			staffTdsInfoService.batchCopyAdd(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	/**复制
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/copyAdd")
	public @ResponseBody CommonBase copyAdd() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		
		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		if(!(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID) || SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID))){
			commonBase.setCode(2);
			commonBase.setMessage("配置表中个税扣除可导入的责任中心未包括登录人责任中心，登录人不可以操作！");
			return commonBase;
		}
		
	    Object DATA_ROWS = getPd.get("DataRows");
	    String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		if(null != listData && listData.size() > 0){
			String strCanDel = "";
    		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
    			strCanDel = " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
    		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
    			strCanDel = " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
    		} else {
    			strCanDel = " and 1 != 1 ";
    		}
	       /* for(PageData item : listData){
	        	Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
    			item.put("CanDel",  strCanDel);
	        }*/
	        //直接删除添加，不判断重复数据
	        //当前期间,取自tb_system_config的SystemDateTime字段
			String getSystemDateTime = sysConfigManager.currentSection(new PageData());
			String busiDateYear=CheckSystemDateTime.getSystemDateTimeYear(getSystemDateTime);
			if(getSystemDateTime!=null&&getSystemDateTime.trim()!=""){
				int month=Integer.parseInt(CheckSystemDateTime.getSystemDateTimeMouth(getSystemDateTime));
				for(int i=0;i<month;i++){
					String copyBusiDate=busiDateYear+String.format("%02d", i+1);
					for (PageData item : listData) {
						item.put("CanDel",  strCanDel);
						item.put("BUSI_DATE",  copyBusiDate);
						Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
					}
					staffTdsInfoService.batchCopyAdd(listData);
				}
			}
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	//public void downExcel(HttpServletResponse response)throws Exception{
	public ModelAndView downExcel(JqPage page) throws Exception{
		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		//String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		
		PageData transferPd = this.getPageData();
		transferPd.put("SelectedBusiDate", SelectedBusiDate);
		
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		String QueryFeild = "";
		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
			QueryFeild += " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
			QueryFeild += " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
		} else {
			QueryFeild += " and 1 != 1 ";
		}
		transferPd.put("QueryFeild", QueryFeild);
		List<PageData> varOList = staffTdsInfoService.exportModel(transferPd);
		return export(varOList, "StaffTdsInfo", map_SetColumnsList, map_DicList); //社保明细
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出StaffTdsInfo到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
	    
		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		//String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");

		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		//页面显示数据的年月
		getPd.put("SelectedBusiDate", SelectedBusiDate);
		
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String dEPARTMENT_ID = user.getDEPARTMENT_ID();
		String QueryFeild = "";
		if(SysConfigStaffTdsSelfDeptCode.contains(dEPARTMENT_ID)){
			QueryFeild += " and DEPT_CODE in ('" + dEPARTMENT_ID + "') ";
		} else if(SysConfigStaffTdsRestDeptCode.contains(dEPARTMENT_ID)){
			QueryFeild += " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffTdsSelfDeptCode) + ") ";
		} else {
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		page.setPd(getPd);
		List<PageData> varOList = staffTdsInfoService.exportList(page);
		return export(varOList, "", map_SetColumnsList, map_DicList);
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView export(List<PageData> varOList, String ExcelName, 
			Map<String, TmplConfigDetail> setColumnsList, Map<String, Object> dicList) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if(setColumnsList != null && setColumnsList.size() > 0){
		    for (TmplConfigDetail col : setColumnsList.values()) {
				if(col.getCOL_HIDE().equals("1")){
					titles.add(col.getCOL_NAME());
				}
			}
			if(varOList!=null && varOList.size()>0){
				for(int i=0;i<varOList.size();i++){
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : setColumnsList.values()) {
						if(col.getCOL_HIDE().equals("1")){
						String trans = col.getDICT_TRANS();
						Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
						if(trans != null && !trans.trim().equals("")){
							String value = "";
							Map<String, String> dicAdd = (Map<String, String>) dicList.getOrDefault(trans, new LinkedHashMap<String, String>());
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
	

	/**查询重复数据
	 * @param response
	 * @throws Exception
	 */
	private String CheckRepeat(List<PageData> listData) throws Exception{
		String strRut = "";
		List<PageData> listRepeat = staffTdsInfoService.getRepeat(listData);
		if(listRepeat!=null && listRepeat.size()>0){
			for(PageData each : listRepeat){
				strRut += each.getString("STAFF_IDENT") + Message.HaveRepeatRecord;
			}
		}
		return strRut;
	}
	
	private String CheckMustSelectedAndSame(String BusiDate, String ShowDataBusiDate) throws Exception{
		String strRut = "";
		if(!(BusiDate != null && !BusiDate.trim().equals(""))){
			strRut += "查询条件中的区间必须设置！";
		} else {
		    if(!BusiDate.equals(ShowDataBusiDate)){
				strRut += "查询条件中区间与页面显示数据区间不一致，请单击查询再进行操作！";
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
