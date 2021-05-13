package com.fh.controller.dataReporting.erp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.fh.controller.common.DictsUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.service.dataReporting.erpdelacctapplication.ERPDelAcctApplicationManager;
import com.fh.service.dataReporting.erpofficialacctapplication.ERPOfficialAcctApplicationManager;
import com.fh.service.dataReporting.erptempacctapplication.ERPTempAcctApplicationManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

import net.sf.json.JSONArray;

/** 
 * 说明：ERP删除账号申请处理模块
 * 创建人：xinyuLo
 * 创建时间：2019-10-11
 */
@Controller
@RequestMapping(value="/erp")
public class ERPController extends BaseController {
	
	String menuUrl = "erp/erpOaaList.do"; //菜单地址(权限用)
	@Resource(name="erpdelacctapplicationService")
	private ERPDelAcctApplicationManager erpdelacctapplicationService;
	@Resource(name="erptempacctapplicationService")
	private ERPTempAcctApplicationManager erptempacctapplicationService;
	@Resource(name="erpofficialacctapplicationService")
	private ERPOfficialAcctApplicationManager erpofficialacctapplicationService;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetAddColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	Map<String, TmplConfigDetail> Map_SetDelColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/erpApprovalList")
	public ModelAndView erpApprovalList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表erpOaaList");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String  applyType = pd.getString("APPLY_TYPE");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","SystemDataTime");
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "2"); //2待审批 3已审批
		}
		if(null == applyType || StringUtil.isEmpty(applyType)||"1".equals(applyType)) {
			pd.put("APPLY_TYPE", "1"); 
			pd.put("APPLY_TYPE1", "3"); 
		}else{
			pd.put("APPLY_TYPE1", ""); 
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		page.setPd(pd);
		//获取业务期间
		List<PageData>  listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData>	varList = erpofficialacctapplicationService.listAllForm(page);	//列出ERPOfficialAcctApplication列表
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
		mv.setViewName("dataReporting/erp/erpapproval");
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		//***********************************************************新增账号申请导出列
		Map_SetAddColumnsList.clear();//清空,重新添加防止导出时污染输出源
		//Map_SetAddColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetAddColumnsList.put("STAFF_ID", new TmplConfigDetail("STAFF_ID", "用户ID", "1", false));
		Map_SetAddColumnsList.put("YG_TYPE", new TmplConfigDetail("YG_TYPE", "员工类型", "1", false));
		Map_SetAddColumnsList.put("GS", new TmplConfigDetail("GS", "公司", "1", false));
		Map_SetAddColumnsList.put("GLY", new TmplConfigDetail("GLY", "管理员", "1", false));
		Map_SetAddColumnsList.put("MING", new TmplConfigDetail("MING", "名", "1", false));
		Map_SetAddColumnsList.put("SNC_NAME", new TmplConfigDetail("SNC_NAME", "SNC名", "1", false));
		Map_SetAddColumnsList.put("UNSEC_SNC", new TmplConfigDetail("UNSEC_SNC", "UNSEC_SNC", "1", false));
		Map_SetAddColumnsList.put("ACCNO", new TmplConfigDetail("ACCNO", "ACCNO", "1", false));
		Map_SetAddColumnsList.put("USER_DEPART", new TmplConfigDetail("USER_DEPART", "用户组", "1", false));
		Map_SetAddColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "姓名", "1", false));
		Map_SetAddColumnsList.put("ZW", new TmplConfigDetail("ZW", "职位", "1", false));
		Map_SetAddColumnsList.put("EMPJOB", new TmplConfigDetail("EMPJOB", "EMPJOB", "1", false));				
		Map_SetAddColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "人员编号", "1", false));
		Map_SetAddColumnsList.put("PERSONNELAREA", new TmplConfigDetail("PERSONNELAREA", "PERSONNELAREA", "1", false));		
		Map_SetAddColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetAddColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "电话号码", "1", false));
		Map_SetAddColumnsList.put("USER_DEPART", new TmplConfigDetail("USER_DEPART", "部门", "1", false));
		Map_SetAddColumnsList.put("POSITION", new TmplConfigDetail("POSITION", "位置", "1", false));
		Map_SetAddColumnsList.put("COSTCENTER", new TmplConfigDetail("COSTCENTER", "COSTCENTER", "1", false));		
		Map_SetAddColumnsList.put("ORZ", new TmplConfigDetail("ORZ", "组织", "1", false));	
		Map_SetAddColumnsList.put("FULL_NAME", new TmplConfigDetail("FULL_NAME", "全名", "1", false));	
		Map_SetAddColumnsList.put("FAX", new TmplConfigDetail("FAX", "传真", "1", false));
		Map_SetAddColumnsList.put("ACADEMIC_TITLE", new TmplConfigDetail("ACADEMIC_TITLE", "ACADEMIC_TITLE", "1", false));
		Map_SetAddColumnsList.put("COMM_METHOD", new TmplConfigDetail("COMM_METHOD", "COMM_METHOD", "1", false));		
		Map_SetAddColumnsList.put("BM", new TmplConfigDetail("BM", "别名", "1", false));
		Map_SetAddColumnsList.put("YWFW", new TmplConfigDetail("YWFW", "业务范围", "1", false));		
		Map_SetAddColumnsList.put("KSCD", new TmplConfigDetail("KSCD", "开始菜单", "1", false));		
		Map_SetAddColumnsList.put("LAG", new TmplConfigDetail("LAG", "语言", "1", false));
		Map_SetAddColumnsList.put("SZGS", new TmplConfigDetail("SZGS", "数字格式", "1", false));
		Map_SetAddColumnsList.put("RQGS", new TmplConfigDetail("RQGS", "日期格式", "1", false));
		Map_SetAddColumnsList.put("GN", new TmplConfigDetail("GN", "功能,", "1", false));
		Map_SetAddColumnsList.put("ROOM_NUM", new TmplConfigDetail("ROOM_NUM", "房间号", "1", false));
		Map_SetAddColumnsList.put("FLOORS", new TmplConfigDetail("FLOORS", "楼层", "1", false));
		Map_SetAddColumnsList.put("BUILDING", new TmplConfigDetail("BUILDING", "办公楼", "1", false));
		Map_SetAddColumnsList.put("USER_TYPE", new TmplConfigDetail("USER_TYPE", "用户类型", "1", false));
		//***********************************************************删除账号申请导出列		
		Map_SetDelColumnsList.clear();
		Map_SetDelColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "姓名", "1", false));
		Map_SetDelColumnsList.put("STAFF_ID", new TmplConfigDetail("STAFF_ID", "用户帐号", "1", false));
		Map_SetDelColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetDelColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "人员编号", "1", false));
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/erpDaaList")
	public ModelAndView erpDaaList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ERPDelAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","SystemDataTime");
		//String date = sysconfigService.getSysConfigByKey(pd);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "2"); //2待审批 3已审批
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		page.setPd(pd);
		//获取业务期间
		List<PageData>  listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData>	varList = erpdelacctapplicationService.list(page);	//列出ERPDelAcctApplication列表
//		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService,"0");
//		if(DepartmentSelectTreeSource.equals("0"))
//		{
//			pd.put("departTreeSource", DepartmentSelectTreeSource);
//		} else {
//			pd.put("departTreeSource", 1);
//		}
//		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// 若是则树形结构加载所有单位，并将用户单位查询条件更新为所选单位
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
		mv.setViewName("dataReporting/erp/erpdaa_list");
		mv.addObject("varList", varList);
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		Map_SetAddColumnsList.clear();
        Map_SetAddColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetAddColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetAddColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetAddColumnsList.put("DEPART_CODE", new TmplConfigDetail("DEPART_CODE", "二级单位", "1", false));
		Map_SetAddColumnsList.put("UNITS_DEPART", new TmplConfigDetail("UNITS_DEPART", "三级单位", "1", false));
		Map_SetAddColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetAddColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetAddColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
		Map_SetAddColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
		Map_SetAddColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetAddColumnsList.put("PERMISSION_CHANGE", new TmplConfigDetail("PERMISSION_CHANGE", "权限变更", "1", false));
		Map_SetAddColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
		Map_SetAddColumnsList.put("ACCOUNT_DELETE_REASON", new TmplConfigDetail("ACCOUNT_DELETE_REASON", "账号删除原因", "1", false));
		Map_SetAddColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));
		Map_SetAddColumnsList.put("BILL_USERNAME", new TmplConfigDetail("BILL_USERNAME", "上报人姓名", "1", false));
		Map_SetAddColumnsList.put("BILL_PHONE", new TmplConfigDetail("BILL_PHONE", "上报人手机号", "1", false));
		
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/erpOaaList")
	public ModelAndView erpOaaList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表erpOaaList");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","SystemDataTime");
//		String date = sysconfigService.getSysConfigByKey(pd);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "2"); //2待审批 3已审批
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		page.setPd(pd);
		//获取业务期间
		List<PageData>  listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData>	varList = erpofficialacctapplicationService.list(page);	//列出ERPOfficialAcctApplication列表
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
//		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService,"00");
//		if(DepartmentSelectTreeSource.equals("0"))
//		{
//			pd.put("departTreeSource", DepartmentSelectTreeSource);
//		} else {
//			pd.put("departTreeSource", 1);
//		}
		mv.setViewName("dataReporting/erp/erpapproval");
//		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		//***********************************************************
		Map_SetAddColumnsList.clear();//清空,重新添加防止导出时污染输出源
		Map_SetAddColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetAddColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetAddColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetAddColumnsList.put("DEPART_CODE", new TmplConfigDetail("DEPART_CODE", "二级单位", "1", false));
		Map_SetAddColumnsList.put("UNITS_DEPART", new TmplConfigDetail("UNITS_DEPART", "三级单位", "1", false));
		Map_SetAddColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetAddColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetAddColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
		Map_SetAddColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
		Map_SetAddColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetAddColumnsList.put("IF_TRAINING", new TmplConfigDetail("IF_TRAINING", "是否培训", "1", false));
		Map_SetAddColumnsList.put("TRAINING_METHOD", new TmplConfigDetail("TRAINING_METHOD", "培训方式", "1", false));
		Map_SetAddColumnsList.put("TRAINING_TIME", new TmplConfigDetail("TRAINING_TIME", "培训时间", "1", false));
		Map_SetAddColumnsList.put("TRAINING_RECORD", new TmplConfigDetail("TRAINING_RECORD", "培训成绩", "1", false));
		Map_SetAddColumnsList.put("CERTIFICATE_NUM", new TmplConfigDetail("CERTIFICATE_NUM", "证书编号", "1", false));
		Map_SetAddColumnsList.put("UKEY_NUM", new TmplConfigDetail("UKEY_NUM", "UKey编号", "1", false));
		Map_SetAddColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
		Map_SetAddColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));
		Map_SetAddColumnsList.put("BILL_USERNAME", new TmplConfigDetail("BILL_USERNAME", "上报人姓名", "1", false));
		Map_SetAddColumnsList.put("BILL_PHONE", new TmplConfigDetail("BILL_PHONE", "上报人手机号", "1", false));
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/erpTaaList")
	public ModelAndView erpTaaList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ERPTempAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","SystemDataTime");
//		String date = sysconfigService.getSysConfigByKey(pd);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "2"); //2待审批 3已审批
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		page.setPd(pd);
		List<PageData>	varList = erptempacctapplicationService.list(page);	//列出ERPTempAcctApplication列表
		//获取业务期间
		List<PageData>  listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0", zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
//		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService,"00");
//		if(DepartmentSelectTreeSource.equals("0"))
//		{
//			pd.put("departTreeSource", DepartmentSelectTreeSource);
//		} else {
//			pd.put("departTreeSource", 1);
//		}
		mv.setViewName("dataReporting/erp/erptaa_list");
//		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		//***********************************************************
		Map_SetAddColumnsList.clear();
		Map_SetAddColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetAddColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetAddColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetAddColumnsList.put("DEPART_CODE", new TmplConfigDetail("DEPART_CODE", "二级单位", "1", false));
		Map_SetAddColumnsList.put("UNITS_DEPART", new TmplConfigDetail("UNITS_DEPART", "三级单位", "1", false));
		Map_SetAddColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetAddColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetAddColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
		Map_SetAddColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
		Map_SetAddColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetAddColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
		Map_SetAddColumnsList.put("CANCEL_DATE", new TmplConfigDetail("CANCEL_DATE", "撤销日期", "1", false));
		Map_SetAddColumnsList.put("APPLY_TEMP_REASON", new TmplConfigDetail("APPLY_TEMP_REASON", "申请临时原因", "1", false));
		Map_SetAddColumnsList.put("UKEY_NUM", new TmplConfigDetail("UKEY_NUM", "UKey编号", "1", false));
		Map_SetAddColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));
		Map_SetAddColumnsList.put("BILL_USERNAME", new TmplConfigDetail("BILL_USERNAME", "上报人姓名", "1", false));
		Map_SetAddColumnsList.put("BILL_PHONE", new TmplConfigDetail("BILL_PHONE", "上报人手机号", "1", false));
		return mv;
	}
	
	/**
	 * 批量审批/驳回
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/oaaReport")
	@ResponseBody
	public CommonBase oaaReport() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "erp批量审批/驳回oaa");
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		commonBase.setCode(-1);
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String arrayDATA_IDS[] = DATA_IDS.split(",");
			List<String> formalList=new ArrayList<String>();
			List<String> tempList=new ArrayList<String>();
		      for(int i=0;i<arrayDATA_IDS.length;i++){
		    	  	int num=arrayDATA_IDS[i].indexOf("&");
		    	  	String id=arrayDATA_IDS[i].substring(0, num);
		    	  	String type=arrayDATA_IDS[i].substring(num+1, arrayDATA_IDS[i].length());
		           if(type.equals("1")){// 正式帐号
		        	   formalList.add(id);
		           }else if (type.equals("2")){//临时帐号
		        	   tempList.add(id);
		           }
		        }			
		    String[] arrayDATA_IDS1 = formalList.toArray(new String[0]); 
		    String[] arrayDATA_IDS2 = tempList.toArray(new String[0]); 
			pd.put("arrayDATA_IDS1", arrayDATA_IDS1);
			pd.put("arrayDATA_IDS2", arrayDATA_IDS2);
			if(arrayDATA_IDS1.length>0){
				erpofficialacctapplicationService.editReportState(pd);
			}
			if(arrayDATA_IDS2.length>0){
				erptempacctapplicationService.editReportState(pd);
			}						
//			String arrayDATA_IDS[] = DATA_IDS.split(",");
//			pd.put("arrayDATA_IDS", arrayDATA_IDS);
			
			commonBase.setCode(0);
		}
		
		return commonBase;
	}
	
	
	
	/**
	 * 批量上报/驳回
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/taaReport")
	@ResponseBody
	public CommonBase taaReport() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "taa批量上报/驳回erp");
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		commonBase.setCode(-1);
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String arrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("arrayDATA_IDS", arrayDATA_IDS);
			erptempacctapplicationService.editReportState(pd);
			commonBase.setCode(0);
		}
		
		return commonBase;
	}
	
	/**
	 * 批量上报/驳回
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/daaReport")
	@ResponseBody
	public CommonBase daaReport() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "daa批量上报/驳回erp");
		CommonBase commonBase = new CommonBase();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		commonBase.setCode(-1);
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String arrayDATA_IDS1[] = DATA_IDS.split(",");
			 String arrayDATA_IDS[] = new String[arrayDATA_IDS1.length];
			 for(int i=0;i<arrayDATA_IDS1.length;i++){
		    	  	String id=arrayDATA_IDS1[i].replace("&", "");
		   				 arrayDATA_IDS[i]=id;
		        }	
			pd.put("arrayDATA_IDS", arrayDATA_IDS);
			erpdelacctapplicationService.editReportState(pd);
			commonBase.setCode(0);
		}
		
		return commonBase;
	}
	
	
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/oaaExcel")
	public ModelAndView oaaExportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERP审批数据到excel");
		PageData pd = this.getPageData();
		pd.put("confirmState", pd.get("confirmState")); //2待审批 3已审批
		String DATA_IDS = pd.getString("DATA_IDS");
		pd.put("BUSI_DATE", pd.get("busiDate"));	
		List<PageData> varOList=new ArrayList<PageData>();
		if("1".equals( pd.get("APPLY_TYPE"))) {
			pd.put("APPLY_TYPE", "1"); 
			pd.put("APPLY_TYPE1", "3"); 
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String arrayDATA_IDS[] = DATA_IDS.split(",");
				List<String> formalList=new ArrayList<String>();
				List<String> tempList=new ArrayList<String>();
			      for(int i=0;i<arrayDATA_IDS.length;i++){
			    	  	int num=arrayDATA_IDS[i].indexOf("&");
			    	  	String id=arrayDATA_IDS[i].substring(0, num);
			    	  	String type=arrayDATA_IDS[i].substring(num+1, arrayDATA_IDS[i].length());
			           if(type.equals("1")){// 正式帐号
			        	   formalList.add(id);
			           }else if (type.equals("2")){//临时帐号
			        	   tempList.add(id);
			           }
			        }	
			      if(formalList.size()>0&&null!=formalList){
			    	  String[] arrayDATA_IDS1 = formalList.toArray(new String[0]);
			    	  pd.put("arrayDATA_IDS1", arrayDATA_IDS1);
			      }
			      if(tempList.size()>0&&null!=tempList){
			    	  String[] arrayDATA_IDS2 = tempList.toArray(new String[0]); 				
			    	  pd.put("arrayDATA_IDS2", arrayDATA_IDS2);	
			      }
			}
		      page.setPd(pd);
				varOList = erpofficialacctapplicationService.exportListAdd(page);
				return export(varOList, "", Map_SetAddColumnsList);	
				
		}else{
			pd.put("APPLY_TYPE1", ""); 
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String arrayDATA_IDS1[] = DATA_IDS.split(",");
				 String arrayDATA_IDS[] = new String[arrayDATA_IDS1.length];
				 for(int i=0;i<arrayDATA_IDS1.length;i++){
			    	  	String id=arrayDATA_IDS1[i].replace("&", "");
			   				 arrayDATA_IDS[i]=id;
			        }	
					pd.put("arrayDATA_IDS3", arrayDATA_IDS);
			}
			page.setPd(pd);
			varOList = erpofficialacctapplicationService.exportListDel(page);
			return export(varOList, "", Map_SetDelColumnsList);	
		}
	
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/taaExcel")
	public ModelAndView taaExportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERP审批数据到excel");
		PageData pd = this.getPageData();
		pd.put("confirmState", pd.get("confirmState")); //2待审批 3已审批
		pd.put("BUSI_DATE", pd.get("busiDate"));
		page.setPd(pd);
		List<PageData> varOList = erptempacctapplicationService.exportList(page);
		return export(varOList, "", Map_SetAddColumnsList);
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/daaExcel")
	public ModelAndView daaExportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERP审批数据到excel");
		PageData pd = this.getPageData();
		pd.put("confirmState", pd.get("confirmState")); //2待审批 3已审批
		pd.put("BUSI_DATE", pd.get("busiDate"));
		page.setPd(pd);
		List<PageData> varOList = erpdelacctapplicationService.exportList(page);
		return export(varOList, "", Map_SetAddColumnsList);
	}
	
	private ModelAndView export(List<PageData> varOList, String ExcelName,
			Map<String, TmplConfigDetail> Map_SetAddColumnsList) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if (Map_SetAddColumnsList != null && Map_SetAddColumnsList.size() > 0) {
			for (TmplConfigDetail col : Map_SetAddColumnsList.values()) {
				if (col.getCOL_HIDE().equals("1")) {
					titles.add(col.getCOL_NAME());
				}
			}
			if (varOList != null && varOList.size() > 0) {
				for (int i = 0; i < varOList.size(); i++) {
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : Map_SetAddColumnsList.values()) {
						if (col.getCOL_HIDE().equals("1")) {
							Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
							if(null == getCellValue){
								getCellValue = "";
							}
							vpd.put("var" + j, getCellValue.toString());
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
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
