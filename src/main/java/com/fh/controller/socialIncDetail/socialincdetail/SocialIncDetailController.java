package com.fh.controller.socialIncDetail.socialincdetail;

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
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.SelectBillCodeOptions;
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
import com.fh.util.enums.BillState;
import com.fh.util.enums.TmplType;
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.socialIncDetail.socialincdetail.SocialIncDetailManager;
import com.fh.service.socialincsummy.socialincsummy.SocialIncSummyManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysDeptLtdTime.sysDeptLtdTime.impl.SysDeptLtdTimeService;
import com.fh.service.sysSealedInfo.syssealedinfo.impl.SysSealedInfoService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明：社保明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 */
@Controller
@RequestMapping(value="/socialincdetail")
public class SocialIncDetailController extends BaseController {
	
	String menuUrl = "socialincdetail/list.do"; //菜单地址(权限用)
	@Resource(name="socialincdetailService")
	private SocialIncDetailManager socialincdetailService;
	@Resource(name="socialincsummyService")
	private SocialIncSummyManager socialincsummyService;
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
	@Resource(name="sysDeptLtdTimeService")
	private SysDeptLtdTimeService sysDeptLtdTimeService;
	
	//表名
	String TableNameDetail = "tb_social_inc_detail";
	String TableNameSummy = "tb_social_inc_summy_BILL";
	String TableNameBackup = "tb_social_inc_detail_backup";
	//临时数据
	String SelectBillCodeFirstShow = "临时数据";    
	String SelectBillCodeLastShow = "";
	//枚举类型  1工资明细,2工资汇总,3公积金明细,4公积金汇总,5社保明细,6社保汇总,7工资接口,8公积金接口,9社保接口
    String TypeCodeDetail = TmplType.TB_SOCIAL_INC_DETAIL.getNameKey();
    String TypeCodeTransfer = TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey();

	//页面显示数据的年月
	//String SystemDateTime = "";
    //
	String AdditionalReportColumns = "";
	//
	private List<String> MustInputList = Arrays.asList("USER_CODE", "UNITS_CODE");
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("CUST_COL7", "DEPT_CODE");
	//导入必填项在字典里没翻译
    List<String> ImportNotHaveTransferList = Arrays.asList("DEPT_CODE", "CUST_COL7", "USER_GROP", "UNITS_CODE");
    //设置必定不用编辑的列            SERIAL_NO 设置字段类型是数字，但不管隐藏 或显示都必须保存的
    List<String> MustNotEditList = Arrays.asList("SERIAL_NO", "BILL_CODE", "BUSI_DATE", "DEPT_CODE", "CUST_COL7");
	// 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
    List<String> keyListAdd = new ArrayList<String>();
	List<String> keyListBase = getKeyListBase();
	private List<String> getKeyListBase(){
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

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表SocialIncDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("socialIncDetail/socialincdetail/socialincdetail_list");
		//单号下拉列表
		//getPd.put("SelectNoBillCodeShow", SelectBillCodeFirstShow);
		getPd.put("InitBillCodeOptions", SelectBillCodeOptions.getSelectBillCodeOptions(null, SelectBillCodeFirstShow, SelectBillCodeLastShow));
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);

		//USER_GROP EMPLGRP 员工组字典
		mv.addObject("EMPLGRP", DictsUtil.getDictsByParentCode(dictionariesService, "EMPLGRP"));
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

	/**单号下拉列表
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getBillCodeList")
	public @ResponseBody CommonBase getBillCodeList() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		
		PageData transferPd = new PageData();
		transferPd.put("SelectedCustCol7", SelectedCustCol7);
		transferPd.put("SelectedDepartCode", SelectedDepartCode);
		transferPd.put("SystemDateTime", SystemDateTime);
	    //汇总单据状态不为0，就是没汇总或汇总但没作废
		String strCanOperate = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
	    //tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
		strCanOperate += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			strCanOperate += " and 1 != 1 ";
		} else {
			//tb_sys_sealed_info不是封存state = '1'
			strCanOperate += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
		}
		if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
			strCanOperate += " and 1 != 1 ";
		}
		transferPd.put("CanOperate", strCanOperate);
		List<String> getCodeList = socialincdetailService.getBillCodeList(transferPd);
		// 下拉列表 value和显示一致
		String returnString = SelectBillCodeOptions.getSelectBillCodeOptions(getCodeList, SelectBillCodeFirstShow, SelectBillCodeLastShow);
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
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		
		TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, 
				departmentService,userService, keyListBase, null, AdditionalReportColumns, MustInputList, jqGridGroupNotSumFeild);
		String jqGridColModel = tmpl.generateStructure(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, 3, MustNotEditList);
		
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
		logBefore(logger, Jurisdiction.getUsername()+"列表SocialIncDetail");

		PageData getPd = this.getPageData();
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		// 单号下拉列表 value和显示一致
		QueryFeild += QueryFeildString.getQueryFeildBillCodeDetail(SelectedBillCode, SelectBillCodeFirstShow);
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			//tb_sys_sealed_info不是封存state = '1'
			QueryFeild += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
		    //tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
			QueryFeild += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
	    //汇总单据状态不为0，就是没汇总或汇总但没作废
		QueryFeild += QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		//页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
		page.setPd(getPd);
		List<PageData> varList = socialincdetailService.JqPage(page);	//列出Betting列表
		int records = socialincdetailService.countJqGridExtend(page);
		PageData userdata = null;
		//底行显示的求和与平均值字段
		StringBuilder SqlUserdata = Common.GetSqlUserdata(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);
		if(SqlUserdata!=null && !SqlUserdata.toString().trim().equals("")){
			//底行显示的求和与平均值字段
			getPd.put("Userdata", SqlUserdata.toString());
		    userdata=socialincdetailService.getFooterSummary(page);
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
		logBefore(logger, Jurisdiction.getUsername()+"修改SocialIncDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限

		PageData getPd = this.getPageData();
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		//操作
		String oper = getPd.getString("oper");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		//判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource,
				SelectedBillCode, ShowDataBillCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		//验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(TypeCodeDetail, SelectedCustCol7, SelectedDepartCode, sysDeptLtdTimeService);
		if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}

	    //汇总单据状态不为0，就是没汇总或汇总但没作废
		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			//tb_sys_sealed_info不是封存state = '1'
			strHelpful += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
		    //tb_sys_unlock_info表 DEL_STATE（融合系统删除状态为1）数据显示。为0不显示。
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if(!(strHelpful != null && !strHelpful.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}

		//必定不用编辑的列  MustNotEditList Arrays.asList("SERIAL_NO", "BILL_CODE", "BUSI_DATE", "DEPT_CODE", "CUST_COL7");
		if(oper.equals("add")){
			getPd.put("SERIAL_NO", "");
			getPd.put("BUSI_DATE", SystemDateTime);
			getPd.put("CUST_COL7", SelectedCustCol7);
			getPd.put("DEPT_CODE", SelectedDepartCode);
			if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
				getPd.put("BILL_CODE", "");
			} else {
				getPd.put("BILL_CODE", SelectedBillCode);
			}
			String getESTB_DEPT = (String) getPd.get("ESTB_DEPT");
			if(!(getESTB_DEPT!=null && !getESTB_DEPT.trim().equals(""))){
				getPd.put("ESTB_DEPT", SelectedDepartCode);
			}
			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);
			String checkState = CheckState(SelectedBillCode, SystemDateTime,
					SelectedCustCol7, SelectedDepartCode, listData, "SERIAL_NO", TmplUtil.keyExtra);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			commonBase = CalculationUpdateDatabase(true, commonBase, "", SelectedDepartCode, SelectedCustCol7, listData, strHelpful);
		} else {
			Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);
			Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(TypeCodeDetail, tmplconfigService);
			List<PageData> listCheckState = new ArrayList<PageData>();
			listCheckState.add(getPd);
			String checkState = CheckState(SelectedBillCode, SystemDateTime,
					SelectedCustCol7, SelectedDepartCode, listCheckState, "SERIAL_NO", TmplUtil.keyExtra);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
			Common.setModelDefault(getPd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
			getPd.put("TableName", TableNameDetail);
			getPd.put("CanOperate", strHelpful);
			
			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);
			socialincdetailService.batchUpdateDatabase(listData);
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
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		//判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		
		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource,
				SelectedBillCode, ShowDataBillCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		//验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(TypeCodeDetail, SelectedCustCol7, SelectedDepartCode, sysDeptLtdTimeService);
		if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}

		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			strHelpful += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if(!(strHelpful != null && !strHelpful.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String checkState = CheckState(SelectedBillCode, SystemDateTime,
				SelectedCustCol7, SelectedDepartCode, listData, "SERIAL_NO", TmplUtil.keyExtra);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}

		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);
		Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(TypeCodeDetail, tmplconfigService);
        for(PageData item : listData){
        	item.put("CanOperate", strHelpful);
      	    item.put("TableName", TableNameDetail);
        	Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
        }
		if(null != listData && listData.size() > 0){
				socialincdetailService.batchUpdateDatabase(listData);
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
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		//判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource,
				SelectedBillCode, ShowDataBillCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		//验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(TypeCodeDetail, SelectedCustCol7, SelectedDepartCode, sysDeptLtdTimeService);
		if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}
		
		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			strHelpful += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if(!(strHelpful != null && !strHelpful.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String checkState = CheckState(SelectedBillCode, SystemDateTime,
				SelectedCustCol7, SelectedDepartCode, listData, "SERIAL_NO", TmplUtil.keyExtra);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
        if(null != listData && listData.size() > 0){
        	for(PageData item : listData){
        	    item.put("CanOperate", strHelpful);
            }
			socialincdetailService.deleteAll(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	 /**计算
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/calculation")
	public @ResponseBody CommonBase calculation() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "calculation")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData getPd = this.getPageData();
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		//判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource,
				SelectedBillCode, ShowDataBillCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		//验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(TypeCodeDetail, SelectedCustCol7, SelectedDepartCode, sysDeptLtdTimeService);
		if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
			return commonBase;
		}

		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			strHelpful += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if(!(strHelpful != null && !strHelpful.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String checkState = CheckState(SelectedBillCode, SystemDateTime,
				SelectedCustCol7, SelectedDepartCode, listData, "SERIAL_NO", TmplUtil.keyExtra);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
        /*for(PageData item : listData){
      	    item.put("CanOperate", strHelpful);
      	    item.put("TableName", TableNameBackup);
      	    Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList);
        }
		if(null != listData && listData.size() > 0){
			String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
			String sqlRetSelect = Common.GetRetSelectColoumns(map_HaveColumnsList, TypeCodeDetail, TableNameBackup, SelectedDepartCode, strFieldSelectKey, tmplconfigService);
			List<PageData> dataCalculation = socialincdetailService.getDataCalculation(TableNameBackup, sqlRetSelect, listData);
			String strJson =JSONArray.fromObject(dataCalculation.get(0)).toString();
			if(strJson.startsWith("[")) strJson = strJson.substring(1);
			if(strJson.endsWith("]")) strJson = strJson.substring(0, strJson.length()-1);
			commonBase.setCode(0);
			commonBase.setMessage(strJson);
		}*/
		commonBase = CalculationUpdateDatabase(false, commonBase, "", SelectedDepartCode, SelectedCustCol7, listData, strHelpful);
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
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		//判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		}

	if(commonBase.getCode()==-1){
		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource,
				SelectedBillCode, ShowDataBillCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		}
	}
	if(commonBase.getCode()==-1){
		//验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(TypeCodeDetail, SelectedCustCol7, SelectedDepartCode, sysDeptLtdTimeService);
		if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
		}
	}
	if(commonBase.getCode()==-1){
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow) && commonBase.getCode() != 2){
			String checkState = CheckState(SelectedBillCode, SystemDateTime,
					SelectedCustCol7, SelectedDepartCode, null, "SERIAL_NO", TmplUtil.keyExtra);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
			}
		}
	}
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "socialincdetail");
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("SelectedBillCode", SelectedBillCode);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("ShowDataBillCode", ShowDataBillCode);
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
    ///*
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(value = "/readExcel")
	//public @ResponseBody CommonBase readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}//校验权限
		
		String strErrorMessage = "";

		PageData getPd = this.getPageData();
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		//判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		}
	if(commonBase.getCode()==-1){
		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource,
				SelectedBillCode, ShowDataBillCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		} 
	}
	if(commonBase.getCode()==-1){
		//验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(TypeCodeDetail, SelectedCustCol7, SelectedDepartCode, sysDeptLtdTimeService);
		if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
		}
	}
	if(commonBase.getCode()==-1){
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			String checkState = CheckState(SelectedBillCode, SystemDateTime,
					SelectedCustCol7, SelectedDepartCode, null, "SERIAL_NO", TmplUtil.keyExtra);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
			}
		}
	}
	if(commonBase.getCode()==-1){
		if(!(SystemDateTime!=null && !SystemDateTime.trim().equals("")
				&& SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage("当前区间和当前单位不能为空！");
		} 
	}
		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
	if(commonBase.getCode()==-1){
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			strHelpful += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if(!(strHelpful != null && !strHelpful.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
		}
	}
		if(commonBase.getCode()==-1){
							Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);
							Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(TypeCodeDetail, tmplconfigService);
							Map<String, Object> DicList = Common.GetDicList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, 
									tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, AdditionalReportColumns);
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
										titleAndAttribute, map_HaveColumnsList, map_SetColumnsList, DicList, false, false,
										null, ImportNotHaveTransferList);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error("读取Excel文件错误", e);
								throw new CustomException("读取Excel文件错误:" + e.getMessage(),false);
							}
							boolean judgement = false;


							Map<String, String> returnErrorCostomn =  (Map<String, String>) uploadAndReadMap.get(2);
							Map<String, String> returnErrorMust =  (Map<String, String>) uploadAndReadMap.get(3);

								List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
								List<PageData> listAdd = new ArrayList<PageData>();
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
										    if(!(getUSER_CODE!=null && !getUSER_CODE.trim().equals(""))){
										    	strRetUserCode = "导入人员编码不能为空！";
										    	break;
										    } else {
												String getMustMessage = returnErrorMust==null ? "" : returnErrorMust.get(getUSER_CODE);
												String getCustomnMessage = returnErrorCostomn==null ? "" : returnErrorCostomn.get(getUSER_CODE);
												if(getMustMessage!=null && !getMustMessage.trim().equals("")){
													sbRetMust += "员工编号" + getUSER_CODE + "：" + getMustMessage + " ";
												}
												if(getCustomnMessage!=null && !getCustomnMessage.trim().equals("")){
													strErrorMessage += "员工编号" + getUSER_CODE + "：" + getCustomnMessage + " ";
												}
												
												pdAdd.put("SERIAL_NO", "");
												String getCUST_COL7 = (String) pdAdd.get("CUST_COL7");
											    //if(!SelectedCustCol7.equals(getCUST_COL7)){
											    //	continue;
											    //}
												if(!(getCUST_COL7!=null && !getCUST_COL7.trim().equals(""))){
													pdAdd.put("CUST_COL7", SelectedCustCol7);
													getCUST_COL7 = SelectedCustCol7;
												}
												if(!SelectedCustCol7.equals(getCUST_COL7)){
													if(!sbRetFeild.contains("导入账套和当前账套必须一致！")){
														sbRetFeild.add("导入账套和当前账套必须一致！");
													}
												}
												String getBILL_CODE = (String) pdAdd.get("BILL_CODE");
												if(!(getBILL_CODE!=null && !getBILL_CODE.trim().equals(""))){
													if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
														pdAdd.put("BILL_CODE", "");
														getBILL_CODE = "";
													} else {
														pdAdd.put("BILL_CODE", SelectedBillCode);
														getBILL_CODE = SelectedBillCode;
													}
												}
												if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
													if(!"".equals(getBILL_CODE)){
														if(!sbRetFeild.contains("导入单号和当前单号必须一致！")){
															sbRetFeild.add("导入单号和当前单号必须一致！");
														}
													}
												} else {
													if(!SelectedBillCode.equals(getBILL_CODE)){
														if(!sbRetFeild.contains("导入单号和当前单号必须一致！")){
															sbRetFeild.add("导入单号和当前单号必须一致！");
														}
													}
												}
												String getDEPT_CODE = (String) pdAdd.get("DEPT_CODE");
												String getBUSI_DATE = (String) pdAdd.get("BUSI_DATE");
												String getUNITS_CODE = (String) pdAdd.get("UNITS_CODE");
												if(!(getBUSI_DATE!=null && !getBUSI_DATE.trim().equals(""))){
													pdAdd.put("BUSI_DATE", SystemDateTime);
													getBUSI_DATE = SystemDateTime;
												}
												if(!SystemDateTime.equals(getBUSI_DATE)){
													if(!sbRetFeild.contains("导入区间和当前区间必须一致！")){
														sbRetFeild.add("导入区间和当前区间必须一致！");
													}
												}
												if(!(getDEPT_CODE!=null && !getDEPT_CODE.trim().equals(""))){
													pdAdd.put("DEPT_CODE", SelectedDepartCode);
													getDEPT_CODE = SelectedDepartCode;
												}
												if(!SelectedDepartCode.equals(getDEPT_CODE)){
													if(!sbRetFeild.contains("导入单位和当前单位必须一致！")){
														sbRetFeild.add("导入单位和当前单位必须一致！");
													}
												}
												if(!(getUSER_CODE!=null && !getUSER_CODE.trim().equals(""))){
													if(!sbRetFeild.contains("人员编码不能为空！")){
														sbRetFeild.add("人员编码不能为空！");
													}
												}
												if(!(getUNITS_CODE!=null && !getUNITS_CODE.trim().equals(""))){
													if(!sbRetFeild.contains("所属二级单位不能为空！")){
														sbRetFeild.add("所属二级单位不能为空！");
													}
												}
												String getESTB_DEPT = (String) pdAdd.get("ESTB_DEPT");
												if(!(getESTB_DEPT!=null && !getESTB_DEPT.trim().equals(""))){
													pdAdd.put("ESTB_DEPT", SelectedDepartCode);
												}
											    String getUSER_GROP = (String) pdAdd.get("USER_GROP");
											    if(!(getUSER_GROP!=null && !getUSER_GROP.trim().equals(""))){
													if(!sbRetFeild.contains("员工组不能为空！")){
														sbRetFeild.add("员工组不能为空！");
													}
										        }
												//Common.setModelDefault(pdAdd, map_HaveColumnsList, map_SetColumnsList);
												//pdAdd.put("CanOperate", strHelpful);
												//pdAdd.put("TableName", TableNameBackup);
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
														commonBase = CalculationUpdateDatabase(true, commonBase, strErrorMessage, SelectedDepartCode, SelectedCustCol7, listAdd, strHelpful);
														
														//String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
														//String sqlRetSelect = Common.GetRetSelectColoumns(map_HaveColumnsList, TypeCodeDetail, TableNameBackup, SelectedDepartCode, strFieldSelectKey, tmplconfigService);
														
														//List<PageData> dataCalculation = socialincdetailService.getDataCalculation(TableNameBackup, sqlRetSelect, listAdd);
														//if(dataCalculation!=null){
														//	for(PageData each : dataCalculation){
														//		each.put("SERIAL_NO", "");
														//		Common.setModelDefault(each, map_HaveColumnsList, map_SetColumnsList);
														//		each.put("CanOperate", strHelpful);
														//		each.put("TableName", TableNameDetail);
														//	}
														//}
														
														//此处执行集合添加 
														//socialincdetailService.batchUpdateDatabase(dataCalculation);
														//commonBase.setCode(0);
														//commonBase.setMessage(strErrorMessage);
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
		mv.addObject("local", "socialincdetail");
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("SelectedBillCode", SelectedBillCode);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("ShowDataBillCode", ShowDataBillCode);
		mv.addObject("SystemDateTime", SystemDateTime);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}//*/
    /*
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(value = "/readExcel")
	//public @ResponseBody CommonBase readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}//校验权限

		PageData getPd = this.getPageData();
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//
		String DepartTreeSource = getPd.getString("DepartTreeSource");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		String ShowDataBillCode = getPd.getString("ShowDataBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		//判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager, false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
		}
	    if(commonBase.getCode()==-1){
		    //判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, DepartTreeSource,
				SelectedBillCode, ShowDataBillCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		} 
	    }
	    if(commonBase.getCode()==-1){
		//验证是否在操作时间内
		String mesSysDeptLtdTime = CheckSystemDateTime.CheckSysDeptLtdTime(SelectedDepartCode, TypeCodeDetail, sysDeptLtdTimeService);
		if(mesSysDeptLtdTime!=null && !mesSysDeptLtdTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesSysDeptLtdTime);
		}
	    }
	    if(commonBase.getCode()==-1){
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			String checkState = CheckState(SelectedBillCode, SystemDateTime,
					SelectedCustCol7, SelectedDepartCode, null, "SERIAL_NO", TmplUtil.keyExtra);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
			}
		}
	    }
	    if(commonBase.getCode()==-1){
		if(!(SystemDateTime!=null && !SystemDateTime.trim().equals("")
				&& SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage("当前区间和当前单位不能为空！");
		} 
	    }
		String strHelpful = QueryFeildString.getBillCodeNotInSumInvalidDetail(TableNameSummy);
	    if(commonBase.getCode()==-1){
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			strHelpful += QueryFeildString.getNotReportBillCode(TypeCodeTransfer, SystemDateTime, SelectedCustCol7, SelectedDepartCode);
			strHelpful += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
		}
		if(!(strHelpful != null && !strHelpful.trim().equals(""))){
			commonBase.setCode(2);
			commonBase.setMessage(Message.GetHelpfulDetailFalue);
		}
	    }
	    if(commonBase.getCode()==-1){
			Map<String, Object> DicList = Common.GetDicList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, 
					tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, AdditionalReportColumns);
			
	        Map<String, TmplInputTips> TmplInputTipsListAll = Common.GetCheckTmplInputTips(TypeCodeDetail, SystemDateTime, SelectedCustCol7, SelectedDepartCode, tmplconfigService);
	        Map<String, TmplInputTips> TmplInputTipsListDic = new HashMap<String, TmplInputTips>();
	        Map<String, TmplInputTips> TmplInputTipsListNull = new HashMap<String, TmplInputTips>();
	        Common.checkTmplInputTipsListAll(TmplInputTipsListAll, TmplInputTipsListDic, TmplInputTipsListNull, DicList, commonBase);
	        
	        if(commonBase.getCode()==-1){
				Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);
				Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(TypeCodeDetail, tmplconfigService);
				
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
							titleAndAttribute, map_HaveColumnsList, map_SetColumnsList, DicList, false, false,
							TmplInputTipsListDic, null);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("读取Excel文件错误", e);
					throw new CustomException("读取Excel文件错误:" + e.getMessage(),false);
				}
				boolean judgement = false;
				Map<String, String> returnErrorCostomn =  (Map<String, String>) uploadAndReadMap.get(2);
				Map<String, String> returnErrorMust =  (Map<String, String>) uploadAndReadMap.get(3);

				List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
				List<PageData> listAdd = new ArrayList<PageData>();
				if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
					judgement = true;
				}
				if (judgement) {
					int listSize = listUploadAndRead.size();
					if(listSize > 0){
						String allStrRetUserCode = "";
						String allStrErrorMustMessage = "";
						String allStrErrorCustomnMessage = "";
						String allStrErrorNotNull = "";
						String allStrErrorNotSame = "";
						for(int i=0;i<listSize;i++){
							PageData pdAdd = listUploadAndRead.get(i);
							if(pdAdd.size() <= 0){
								continue;
							}
							//String userRetUserCode = "";
							String userErrorMustMessage = "";
							String userErrorCustomnMessage = "";
							Boolean bolMessageNotNull = false;
							String userErrorNotNull = "";
							String userErrorNotSame = "";
							
							String getUSER_CODE1 = (String) pdAdd.get("USER_CODE");	
                            //if(!(getUSER_CODE!=null && !getUSER_CODE.trim().equals(""))){
                            //    if(!(userRetUserCode!=null && !userRetUserCode.trim().equals(""))){
							//    	userRetUserCode = "导入员工编号不能为空！";
                            //    }
                            //}
							String getMustMessage = returnErrorMust==null ? "" : returnErrorMust.get(getUSER_CODE1);
							String getCustomnMessage = returnErrorCostomn==null ? "" : returnErrorCostomn.get(getUSER_CODE1);
							if(getMustMessage!=null && !getMustMessage.trim().equals("")){
								userErrorMustMessage += "员工编号" + getUSER_CODE1 + "：" + getMustMessage + " ";
							}
							if(getCustomnMessage!=null && !getCustomnMessage.trim().equals("")){
								userErrorCustomnMessage += "员工编号" + getUSER_CODE1 + "：" + getCustomnMessage + " ";
							}
        					
							pdAdd.put("SERIAL_NO", "");
							String getBILL_CODE1 = (String) pdAdd.get("BILL_CODE");
							if(!(getBILL_CODE1!=null && !getBILL_CODE1.trim().equals(""))){
								if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
									pdAdd.put("BILL_CODE", "");
									getBILL_CODE1 = "";
								} else {
									pdAdd.put("BILL_CODE", SelectedBillCode);
									getBILL_CODE1 = SelectedBillCode;
								}
							}
							String getBUSI_DATE1 = (String) pdAdd.get("BUSI_DATE");
							if(!(getBUSI_DATE1!=null && !getBUSI_DATE1.trim().equals(""))){
								pdAdd.put("BUSI_DATE", SystemDateTime);
								getBUSI_DATE1 = SystemDateTime;
							}
							String getCUST_COL71 = (String) pdAdd.get("CUST_COL7");
							if(!(getCUST_COL71!=null && !getCUST_COL71.trim().equals(""))){
								pdAdd.put("CUST_COL7", SelectedCustCol7);
								getCUST_COL71 = SelectedCustCol7;
							}
							String getDEPT_CODE1 = (String) pdAdd.get("DEPT_CODE");
							if(!(getDEPT_CODE1!=null && !getDEPT_CODE1.trim().equals(""))){
								pdAdd.put("DEPT_CODE", SelectedDepartCode);
								getDEPT_CODE1 = SelectedDepartCode;
							}
							String getESTB_DEPT1 = (String) pdAdd.get("ESTB_DEPT");
							if(!(getESTB_DEPT1!=null && !getESTB_DEPT1.trim().equals(""))){
								pdAdd.put("ESTB_DEPT", SelectedDepartCode);
							}

							Boolean bolCondAdd = true;
							if(TmplInputTipsListNull!=null && TmplInputTipsListNull.size()>0){
            					for(TmplInputTips tip : TmplInputTipsListNull.values()){
        							Object objColValue = pdAdd.get(tip.getCOL_CODE().toUpperCase());
        							String strColValue = objColValue==null ? "" : String.valueOf(objColValue);
            						String getCOL_NULL = tip.getCOL_NULL();
            						if(String.valueOf(1).equals(getCOL_NULL)){
            							if(!(strColValue!=null && !strColValue.toString().trim().equals(""))){
            								bolMessageNotNull = true;
            								userErrorNotNull += tip.getNULL_VALUE_PREFIX() + "" + tip.getNULL_VALUE_SUFFIX();
            							}
            						}
            						String getCOL_COND = tip.getCOL_COND();
            						if(getCOL_COND!=null && !getCOL_COND.trim().equals("")){
            							String[] arrCOL_COND = getCOL_COND.replace(" ", "").replace("，", ",").split(",");
            							if(arrCOL_COND!=null && arrCOL_COND.length>0){
                    						List<String> listCOL_COND = Arrays.asList(arrCOL_COND);
                    						if(listCOL_COND.contains(strColValue)){
                        						String getCOL_MAPPING = tip.getCOL_MAPPING();
                        						if(getCOL_MAPPING!=null && !getCOL_MAPPING.trim().equals("")){
                        							String[] arrCOL_MAPPING = getCOL_MAPPING.replace(" ", "").replace("，", ",").split(",");
                            						List<String> listCOL_MAPPING = Arrays.asList(arrCOL_MAPPING);
                            						for(int num=0; num<listCOL_COND.size(); num++){
                            							if(listCOL_COND.get(num).equals(strColValue)){
                            								pdAdd.put(tip.getCOL_CODE().toUpperCase(), listCOL_MAPPING.get(num));
                            							}
                            						}
                        						}
                    						} else {
                    							bolCondAdd = false;
                    						}
            							}
            						}
            					}
							}
							if(!bolCondAdd){
								continue;
							}

							String getBILL_CODE2 = (String) pdAdd.get("BILL_CODE");
							String getBUSI_DATE2 = (String) pdAdd.get("BUSI_DATE");
							String getDEPT_CODE2 = (String) pdAdd.get("DEPT_CODE");
						    String getCUST_COL72 = (String) pdAdd.get("CUST_COL7");
							if(SelectedBillCode.equals(SelectBillCodeFirstShow)){
								if(!"".equals(getBILL_CODE2)){
									userErrorNotSame += "导入单号和当前单号必须一致！";
								}
							} else {
								if(!SelectedBillCode.equals(getBILL_CODE2)){
									userErrorNotSame += "导入单号和当前单号必须一致！";
								}
							}
							if(!SystemDateTime.equals(getBUSI_DATE2)){
								userErrorNotSame += "导入区间和当前区间必须一致！";
							}
							if(!SelectedCustCol7.equals(getCUST_COL72)){
								userErrorNotSame += "导入账套和当前账套必须一致！";
							}
							if(!SelectedDepartCode.equals(getDEPT_CODE2)){
								userErrorNotSame += "导入单位和当前单位必须一致！";
							}

							//Common.setModelDefault(pdAdd, map_HaveColumnsList, map_SetColumnsList);
							//pdAdd.put("CanOperate", strHelpful);
							//pdAdd.put("TableName", TableNameBackup);
							listAdd.add(pdAdd);
                            //if(!(allStrRetUserCode!=null && !allStrRetUserCode.trim().equals(""))){
                            //    allStrRetUserCode += userRetUserCode;
                            //}
                            allStrErrorMustMessage += userErrorMustMessage;
                            allStrErrorCustomnMessage += userErrorCustomnMessage;
							if(bolMessageNotNull){
								allStrErrorNotNull += "员工编号" + getUSER_CODE1 + "：" + userErrorNotNull + " ";
							}
							if(userErrorNotSame!=null && !userErrorNotSame.trim().equals("")){
								allStrErrorNotSame += "员工编号" + getUSER_CODE1 + "：" + userErrorNotSame + " ";
							}
						}
					    if(allStrRetUserCode!=null && !allStrRetUserCode.trim().equals("")){
						    commonBase.setCode(2);
						    commonBase.setMessage(allStrRetUserCode);
					    } else {
						    if(allStrErrorMustMessage!=null && !allStrErrorMustMessage.trim().equals("")){
						        commonBase.setCode(3);
						        commonBase.setMessage("字典无此翻译, 不能导入： " + allStrErrorMustMessage);
						    } else {
							    if(allStrErrorNotNull!=null && !allStrErrorNotNull.trim().equals("")){
							        commonBase.setCode(3);
							        commonBase.setMessage(allStrErrorNotNull);
							    } else {
								    if(allStrErrorNotSame!=null && !allStrErrorNotSame.trim().equals("")){
										commonBase.setCode(3);
										commonBase.setMessage(allStrErrorNotSame);
									} else {
										if(!(listAdd!=null && listAdd.size()>0)){
											commonBase.setCode(2);
											commonBase.setMessage("请导入符合条件的数据！");
										} else {
											commonBase = CalculationUpdateDatabase(true, commonBase, allStrErrorCustomnMessage, SelectedDepartCode, SelectedCustCol7, listAdd, strHelpful);
											
											//String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
											//String sqlRetSelect = Common.GetRetSelectColoumns(map_HaveColumnsList, TypeCodeDetail, TableNameBackup, SelectedDepartCode, strFieldSelectKey, tmplconfigService);
											
											//List<PageData> dataCalculation = socialincdetailService.getDataCalculation(TableNameBackup, sqlRetSelect, listAdd);
											//if(dataCalculation!=null){
											//	for(PageData each : dataCalculation){
											//		each.put("SERIAL_NO", "");
											//		Common.setModelDefault(each, map_HaveColumnsList, map_SetColumnsList);
											//		each.put("CanOperate", strHelpful);
											//		each.put("TableName", TableNameDetail);
											//	}
											//}
											
											////此处执行集合添加 
											//socialincdetailService.batchUpdateDatabase(dataCalculation);
											//commonBase.setCode(0);
											//commonBase.setMessage(strErrorMessage);
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
		}
 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "socialincdetail");
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("SelectedBillCode", SelectedBillCode);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("ShowDataBillCode", ShowDataBillCode);
		mv.addObject("SystemDateTime", SystemDateTime);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}//*/
    
	private CommonBase CalculationUpdateDatabase(Boolean IsAdd, CommonBase commonBase, String strErrorMessage,
    		String SelectedDepartCode, String SelectedCustCol7, 
    		List<PageData> listAdd, String strHelpful) throws Exception{
    	if(listAdd!=null && listAdd.size()>0){
    		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);
    		Map<String, TableColumns> map_HaveColumnsList = Common.GetHaveColumnsList(TypeCodeDetail, tmplconfigService);
            for(PageData item : listAdd){
          	    item.put("CanOperate", strHelpful);
          	    item.put("TableName", TableNameBackup);
          	    Common.setModelDefault(item, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
            }

        	//查询记录，带公式
    		String sqlRetSelect = Common.GetRetSelectColoumns(map_HaveColumnsList, 
    				TypeCodeDetail, TableNameBackup, SelectedDepartCode, SelectedCustCol7, 
    				//"", 
    				TmplUtil.keyExtra, keyListBase, 
    				tmplconfigService);

			try{
				//tb_social_inc_detail_backup（导入时用来计算的明细）先删后插数据，带公式查询出记录
	    		List<PageData> dataCalculation = socialincdetailService.getDataCalculation(TableNameBackup, sqlRetSelect, listAdd);
	    		if(dataCalculation!=null){
	    			for(PageData each : dataCalculation){
	    				if(IsAdd){
	    					each.put("SERIAL_NO", "");
	    				}
	    				Common.setModelDefault(each, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
	    				each.put("CanOperate", strHelpful);
	    				each.put("TableName", TableNameDetail);
	    			}
	    		}
	    		
	    		//此处执行集合添加 
	    		socialincdetailService.batchUpdateDatabase(dataCalculation);
	    		commonBase.setCode(0);
	    		commonBase.setMessage(strErrorMessage);
			} catch(Exception e){
				commonBase.setCode(2);
				commonBase.setMessage(Message.ImportExcelError);
			}
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
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);

		PageData transferPd = this.getPageData();
		//页面显示数据的二级单位
		transferPd.put("SelectedDepartCode", SelectedDepartCode);
		//账套
		transferPd.put("SelectedCustCol7", SelectedCustCol7);
		////员工组
		//transferPd.put("emplGroupType", emplGroupType);
		List<PageData> varOList = socialincdetailService.exportModel(transferPd);
		return export(SelectedDepartCode, SelectedCustCol7, varOList, "SocialIncDetail", map_SetColumnsList); //社保明细
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SocialIncDetail到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
	    
		PageData getPd = this.getPageData();
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单号
		String SelectedBillCode = getPd.getString("SelectedBillCode");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		Map<String, TmplConfigDetail> map_SetColumnsList = Common.GetSetColumnsList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, tmplconfigService);
		
		//页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		//账套
		getPd.put("SelectedCustCol7", SelectedCustCol7);
		//页面显示数据的二级单位
		getPd.put("SelectedDepartCode", SelectedDepartCode);

		String strBillCode = QueryFeildString.getQueryFeildBillCodeDetail(SelectedBillCode, SelectBillCodeFirstShow);
		getPd.put("CheckBillCode", strBillCode);
		
		page.setPd(getPd);
		List<PageData> varOList = socialincdetailService.exportList(page);
		return export(SelectedDepartCode, SelectedCustCol7, varOList, "", map_SetColumnsList);
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView export(String SelectedDepartCode, String SelectedCustCol7, 
			List<PageData> varOList, String ExcelName, Map<String, TmplConfigDetail> map_SetColumnsList) throws Exception{
		Map<String, Object> DicList = Common.GetDicList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, 
				tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, AdditionalReportColumns);
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
	
	
	private String CheckState(String SelectedBillCode, String SystemDateTime,
			String SelectedCustCol7, String SelectedDepartCode, 
			List<PageData> pdList, String strFeild, String strFeildExtra) throws Exception{
		String strRut = "";
		if(!SelectedBillCode.equals(SelectBillCodeFirstShow)){
			String QueryFeild = " and BILL_CODE in ('" + SelectedBillCode + "') ";
			QueryFeild += " and BILL_STATE = '" + BillState.Normal.getNameKey() + "' ";
			QueryFeild += " and BILL_CODE not in (SELECT bill_code FROM tb_sys_sealed_info WHERE state = '1') ";
			QueryFeild += QueryFeildString.getBillCodeNotInInvalidSysUnlockInfo();
			
			PageData transferPd = new PageData();
			transferPd.put("SystemDateTime", SystemDateTime);
			transferPd.put("CanOperate", QueryFeild);
			List<String> getCodeList = socialincsummyService.getBillCodeList(transferPd);
			
			if(!(getCodeList != null && getCodeList.size()>0)){
				strRut = Message.OperDataSumAlreadyChange;
			}
		} else {
	        if(pdList!=null && pdList.size()>0){
	        	List<Integer> listStringSerialNo = QueryFeildString.getListIntegerFromListPageData(pdList, strFeild, strFeildExtra);
				String strSqlInSerialNo = QueryFeildString.tranferListIntegerToGroupbyString(listStringSerialNo);
				String strSERIAL_NO_IN = (strSqlInSerialNo!=null && !strSqlInSerialNo.trim().equals("")) ? strSqlInSerialNo : "''";
	    		PageData transferPd = new PageData();
	    		PageData getQueryFeildPd = new PageData();
	    		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
	    		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
	    		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
	    		if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
	    			QueryFeild += " and 1 != 1 ";
	    		}
	    		if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
	    			QueryFeild += " and 1 != 1 ";
	    		}
	    		QueryFeild += " and BILL_CODE like ' %' ";
	    		QueryFeild += " and SERIAL_NO in (" + strSERIAL_NO_IN + ") ";
	    		transferPd.put("QueryFeild", QueryFeild);
	    		
	    		//页面显示数据的年月
	    		transferPd.put("SystemDateTime", SystemDateTime);
	    		transferPd.put("SelectFeildName", strFeild);
	    		List<PageData> getSerialNo = socialincdetailService.getSerialNoBySerialNo(transferPd);
	    		if(!(listStringSerialNo!=null && getSerialNo!=null && listStringSerialNo.size() == getSerialNo.size())){
	    			strRut = Message.OperDataAlreadyChange;
	    		} else {
	    			for(PageData each : getSerialNo){
	    				if(!listStringSerialNo.contains((Integer)each.get(strFeild))){
	    					strRut = Message.OperDataAlreadyChange;
	    				}
	    			}
	    		}
	        }
		}
		return strRut;
	}
	
	private String CheckMustSelectedAndSame(String CUST_COL7, String ShowDataCustCol7, 
			String DEPT_CODE, String ShowDataDepartCode, String DepartTreeSource,
			String BILL_CODE, String ShowDataBillCode) throws Exception{
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
		if(!(BILL_CODE != null && !BILL_CODE.trim().equals(""))){
			strRut += "查询条件中的单号必须选择！";
		} else {
		    if(!BILL_CODE.equals(ShowDataBillCode)){
				strRut += "查询条件中所选单号与页面显示数据单号不一致，请单击查询再进行操作！";
		    }
			//if(!BILL_CODE.equals(SelectBillCodeFirstShow)){
			//	strRut += "已汇总记录不能再进行操作！";
			//}
		}
		return strRut;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
