package com.fh.controller.dataReporting.erpofficialacctapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.service.dataReporting.erpofficialacctapplication.ERPOfficialAcctApplicationManager;
import com.fh.service.dataReporting.erptempacctapplication.ERPTempAcctApplicationManager;
import com.fh.service.dataReporting.grcperson.GRCPersonManager;
import com.fh.service.myPush.myPush.MyPushManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.service.trainBase.TrainScoreManager;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.SysDeptTime;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

/** 
 * 说明：正式账号申请处理模块
 * 创建人：xinyuLo
 * 创建时间：2019-10-10 TB_DI_ERP_OAA
 */
@Controller
@RequestMapping(value="/erpofficialacctapplication")
public class ERPOfficialAcctApplicationController extends BaseController {
	
	String menuUrl = "erpofficialacctapplication/list.do"; //菜单地址(权限用)
	@Resource(name="erpofficialacctapplicationService")
	private ERPOfficialAcctApplicationManager erpofficialacctapplicationService;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;
	@Resource(name="grcpersonService")
	private GRCPersonManager grcpersonService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "myPushService")
	private MyPushManager myPushService;
	@Resource(name="trainscoreService")
	private TrainScoreManager trainscoreService;
	@Resource(name="erptempacctapplicationService")
	private ERPTempAcctApplicationManager erptempacctapplicationService;
	
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	
	/**保存与修改合并类
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveAndEdit")
	@ResponseBody 
	public CommonBase saveAndEdit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"保存与修改ERPOfficialAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		//初始化变量
		String listData = null;	
		String staffId = null;
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		commonBase.setCode(-1);
		pd = this.getPageData();
		listData = pd.getString("listData");
		JSONArray array = JSONArray.fromObject(listData);
		@SuppressWarnings("unchecked")
		List<String> listTransferData = (List<String>) JSONArray.toCollection(array, PageData.class);// 过时方法
		//当本次提交的数据集合中存在重复字段时
//		Set<String> stringSet=new HashSet<>(listTransferData);
//	    if (listTransferData.size() != stringSet.size()) {
//	    	commonBase.setCode(6);
//			return commonBase;
//	    } 
		PageData pd1 = new PageData();
		for (int i = 0; i < listTransferData.size(); i++) {
			staffId = listTransferData.get(i).trim();
			PageData pageData = new PageData();
			pageData.put("CONFIRM_STATE", "1"); //1未上报 2已上报 3撤销上报 4已驳回
			pageData.put("USER_DEPART",user.getUNIT_CODE());
			pageData.put("BUSI_DATE",DateUtils.getCurrentDateMonth()); //业务期间
			pageData.put("STATE","1");
			pageData.put("BILL_USER",user.getUSER_ID());
			pageData.put("BILL_DATE",DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
			String str=listTransferData.get(i++);
			if(!str.equals("")&&str!=null){
				int num=str.indexOf("&");
	    	  	String id=str.substring(0, num);
				pageData.put("ID",id);
			}else{
				pageData.put("ID",str);
			}
			pageData.put("STAFF_CODE",listTransferData.get(i++).trim());
			pageData.put("STAFF_NAME",listTransferData.get(i++).trim());
			pageData.put("DEPART_CODE",listTransferData.get(i++).trim()); 
			pageData.put("UNITS_DEPART",listTransferData.get(i++).trim());
			pageData.put("STAFF_POSITION",listTransferData.get(i++).trim());
			pageData.put("POSITION_LEVEL",listTransferData.get(i++).trim());
			pageData.put("STAFF_JOB",listTransferData.get(i++).trim());
			pageData.put("STAFF_MODULE",listTransferData.get(i++).trim());
			pageData.put("PHONE",listTransferData.get(i++).trim());
			pageData.put("MAIL",listTransferData.get(i++).trim());

//			pageData.put("CERTIFICATE_NUM",listTransferData.get(i++).trim());
//			pageData.put("UKEY_NUM",listTransferData.get(i++).trim());
//			pageData.put("APPLY_DATE",listTransferData.get(i++).trim());
			pageData.put("APPLY_DATE",DateUtils.getCurrentTime(DateFormatUtils.DATE_NOFUll_FORMAT));
			pageData.put("NOTE",listTransferData.get(i++).trim());
			String account_type = listTransferData.get(i).trim();//获取账号类别
			//判断用户是否存在
			pageData.put("USERNAME", pageData.get("STAFF_CODE"));
			pd1=userService.findByUserNum(pageData);
			if(pd1!=null&&!pd1.isEmpty()){
				if(pd1.getString("USER_PROPERTY").equals("1")){//等于1用户已存在且为SAP用户
					commonBase.setCode(2);
					return commonBase;
				}else{//等于0用户已存在且不是SAP用户
					commonBase.setCode(3);
					return commonBase;
				}
			}
			//只有第一次新增账号是才做此判断
			if(!"1".equals(account_type)&&!"2".equals(account_type)){
				//判断该用户是否存在有效的正式账号申请单
				pd1=erpofficialacctapplicationService.findByStaffCode(pageData);
				if(pd1!=null&&!pd1.isEmpty()){
					commonBase.setCode(4);
					return commonBase;
				}
				//判断该用户是否存在有效的临时账号申请单
				pd1=erptempacctapplicationService.findByStaffCode(pageData);
				if(pd1!=null&&!pd1.isEmpty()){
					commonBase.setCode(5);
					return commonBase;
				}
			}

			//获取该用户培训及考试信息
			PageData trainPd = new PageData();			
			trainPd=trainscoreService.findByUserCode(pageData);
			if(trainPd!=null&&!trainPd.isEmpty()){//有考试成绩
				pageData.put("IF_TRAINING","是");
				pageData.put("TRAINING_METHOD",trainPd.getString("TRAIN_WAY"));//培训方式
				pageData.put("TRAINING_TIME",trainPd.getString("TEST_DATE"));//培训时间
				pageData.put("TRAINING_RECORD",trainPd.get("TEST_SCORE"));//考试分数
//				if(trainPd.get("TEST_SCORE")!=null&&trainPd.get("QUALIFIED_SCORE")!=null){
//					double socre=Double.parseDouble(trainPd.get("TEST_SCORE").toString())-Double.parseDouble(trainPd.get("QUALIFIED_SCORE").toString());
				//if(socre>0||pageData.getString("POSITION_LEVEL").equals("处级")){
				//根据有没有证书（有证书）或者职级（为处级）确定为正式账号，否则为临时账号
				if((null!=trainPd.get("CERTIFICATE_NUM")&&!"".equals(trainPd.get("CERTIFICATE_NUM")))||pageData.getString("POSITION_LEVEL").equals("处级")){
					pageData.put("ACCOUNT_SIGN",1);//正式账号标记
					pageData.put("APPLY_TYPE","1");//申请类别为新增正式账号
					if(null != staffId && !"".equals(staffId)) {//如果有ID则进行修改
						//当修改员工编号或职级等信息后，用户账号性质可能发生变化，判断是否不一致
						if(account_type.equals(pageData.get("ACCOUNT_SIGN").toString())){//一致
							erpofficialacctapplicationService.edit(pageData);
						}else{
							erpofficialacctapplicationService.delTempAndInsertData(pageData);//不一致则删除原临时账号新增正式账号新增
						}							
					}else {//如果无ID则进行新增
						erpofficialacctapplicationService.save(pageData);
					}
				}else{//没有证书且为非处级，账号性质应为临时账号
					pageData.put("ACCOUNT_SIGN",2);//临时账号标记
					pageData.put("APPLY_TYPE","3");//申请类别为新增临时账号
					if(null != staffId && !"".equals(staffId)) {//如果有ID则进行修改
						if(account_type.equals(pageData.get("ACCOUNT_SIGN").toString())){//一致
							erptempacctapplicationService.edit(pageData);
						}else{
							erptempacctapplicationService.delAndInsertTempData(pageData);//不一致则删除原正式账号新增临时账号新增
						}						
					}else {//如果无ID则进行新增
						erptempacctapplicationService.save(pageData);
					}	
				}
			}else{//无考试成绩
				pageData.put("IF_TRAINING","否");
				pageData.put("TRAINING_METHOD","");//培训方式
				pageData.put("TRAINING_TIME","");//培训时间
				pageData.put("TRAINING_RECORD",0);//考试分数
				if(pageData.getString("POSITION_LEVEL").equals("处级")){				
					pageData.put("ACCOUNT_SIGN",1);//正式账号标记
					pageData.put("APPLY_TYPE","1");//申请类别为新增正式账号
					if(null != staffId && !"".equals(staffId)) {//如果有ID则进行修改
						//当修改员工编号或职级等信息后，用户账号性质可能发生变化，判断是否不一致
						if(account_type.equals(pageData.get("ACCOUNT_SIGN").toString())){//一致
							erpofficialacctapplicationService.edit(pageData);
						}else{
							erpofficialacctapplicationService.delTempAndInsertData(pageData);//不一致则删除原临时账号新增正式账号新增
						}							
					}else {//如果无ID则进行新增
						erpofficialacctapplicationService.save(pageData);
					}
				}else{
					pageData.put("ACCOUNT_SIGN",2);//临时账号标记
					pageData.put("APPLY_TYPE","3");//申请类别为新增临时账号
					if(null != staffId && !"".equals(staffId)) {//如果有ID则进行修改
						if(account_type.equals(pageData.get("ACCOUNT_SIGN").toString())){//一致
							erptempacctapplicationService.edit(pageData);
						}else{
							erptempacctapplicationService.delAndInsertTempData(pageData);//不一致则删除原正式账号新增临时账号新增
						}						
					}else {//如果无ID则进行新增
						erptempacctapplicationService.save(pageData);
					}	
				}
			}
				
		} 
		commonBase.setCode(0);
		return commonBase;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ERPOfficialAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData data = new PageData();
		User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);	
		String userRole=user.getRole().getROLE_ID();//获取用户角色
		pd = this.getPageData();
		String confirmState = pd.getString("confirmState");
		String busiDate = pd.getString("busiDate");
		pd.put("KEY_CODE","erpaddMustKey");
		data.put("BUSI_TYPE",SysDeptTime.ERP_OAA.getNameKey());
		data.put("DEPT_CODE",user.getUNIT_CODE());
		//String date = sysconfigService.currentSection(pd);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
		// 需要获取必填的内容，然后输出到页面上
        String mandatory = sysconfigService.getSysConfigByKey(pd);
		
		if(null == confirmState || StringUtil.isEmpty(confirmState)) {
			pd.put("confirmState", "1");  //1未上报 2已上报 3撤销上报 4已驳回
		}
		if(null == busiDate || StringUtil.isEmpty(busiDate)) {
//			pd.put("busiDate",date);
		}
		pd.put("month",date);
		pd.put("DEPART_CODE",user.getUNIT_NAME());
		pd.put("USER_DEPART",user.getUNIT_CODE());
		pd.put("mandatory", mandatory);
		PageData pageData = grcpersonService.findSysDeptTime(data);
		if(null != pageData && pageData.size()>0) {
			pd.putAll(pageData);
		}
		pd.put("KEY_CODE", "ejdwxtglyjs");//获取配置表中二级单位系统管理员角色
		String userRoldId = sysconfigService.getSysConfigByKey(pd);
		if(userRole.equals(userRoldId)){
			pd.put("ROLE_TYPE", "ejdw");//角色类型为二级单位
		}else{
			pd.put("ROLE_TYPE", "gly");//角色类型为管理员
		}
		page.setPd(pd);
		List<PageData> listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
		List<PageData>	varList = erpofficialacctapplicationService.list(page);	//列出ERPOfficialAcctApplication列表
		mv.setViewName("dataReporting/erpofficialacctapplication/erpofficialacctapplication_list");
		mv.addObject("listBusiDate",listBusiDate);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		//***********************************************************
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
		Map_SetColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
		Map_SetColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
		Map_SetColumnsList.put("DEPART_CODE", new TmplConfigDetail("DEPART_CODE", "二级单位", "1", false));
		Map_SetColumnsList.put("UNITS_DEPART", new TmplConfigDetail("UNITS_DEPART", "三级单位", "1", false));
		Map_SetColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
		Map_SetColumnsList.put("POSITION_LEVEL", new TmplConfigDetail("POSITION_LEVEL", "职级", "1", false));
		Map_SetColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
		Map_SetColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
		Map_SetColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
		Map_SetColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
		Map_SetColumnsList.put("IF_TRAINING", new TmplConfigDetail("IF_TRAINING", "是否培训", "1", false));
		Map_SetColumnsList.put("TRAINING_METHOD", new TmplConfigDetail("TRAINING_METHOD", "培训方式", "1", false));
		Map_SetColumnsList.put("TRAINING_TIME", new TmplConfigDetail("TRAINING_TIME", "培训时间", "1", false));
		Map_SetColumnsList.put("TRAINING_RECORD", new TmplConfigDetail("TRAINING_RECORD", "培训成绩", "1", false));
		Map_SetColumnsList.put("CERTIFICATE_NUM", new TmplConfigDetail("CERTIFICATE_NUM", "证书编号", "1", false));
		Map_SetColumnsList.put("UKEY_NUM", new TmplConfigDetail("UKEY_NUM", "UKey编号", "1", false));
		Map_SetColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
		Map_SetColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));
		return mv;
	}
	
	/**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public CommonBase deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ERPOfficialAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
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
			//String ArrayDATA_IDS[] = DATA_IDS.split(",");
		      if(arrayDATA_IDS1.length>0){
		    	  erpofficialacctapplicationService.deleteAll(arrayDATA_IDS1);
		      }
		      if(arrayDATA_IDS2.length>0){	
				erptempacctapplicationService.deleteAll(arrayDATA_IDS2);
		      }
			commonBase.setCode(0);
		}else{
			commonBase.setCode(-1);
		}
		return commonBase;
	}
	
	/**批量上报/撤销上报
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/report")
	@ResponseBody
	public CommonBase editReport() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量上报/撤销上报ERPOfficialAcctApplication");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
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
	
			if("2".equals(pd.get("CONFIRM_STATE"))) {
				//推送给管理员
				PageData pd2 = new PageData();
				pd2.put("iModuleId", 244);
				pd2.put("iModuleSubId", 1);
				pd2.put("iForkId", 1);
				pd2.put("sCanClickTile", "前往审批");
				pd2.put("sCanClickUrl", "erp/erpOaaList.do");
				pd2.put("iIsForward", "1");
				pd2.put("sTitle", "ERP正式账号审批");
				pd2.put("sDetails", " ");
				pd2.put("iGroupId", 1);//ERP账号新增删除管理员
				pd2.put("rebootScope","1");
				pd2.put("rebootMark","1");
//				com.alibaba.fastjson.JSONObject json2 = myPushService.saveSend(pd2);
			}
			commonBase.setCode(0);
		}else{
			commonBase.setCode(-1);
		}
		return commonBase;
	}
	
	/**
	 * 打开上传EXCEL页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "erpofficialacctapplication");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出ERPOfficialAcctApplication到excel");
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("confirmState", pd.get("confirmState")); //1未上报 2已上报 3撤销上报 4已驳回
		pd.put("BUSI_DATE", pd.get("BUSI_DATE")); //月份
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("USER_DEPART", user.getUNIT_CODE()); //单位
		page.setPd(pd);
		List<PageData> varOList = erpofficialacctapplicationService.exportList(page);
		return export(varOList, "", Map_SetColumnsList);
	}
	
	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public ModelAndView downExcel(Page page) throws Exception {

		PageData getPd = this.getPageData();
		List<PageData> varOList = erpofficialacctapplicationService.exportModel(getPd);
		return export(varOList, "ERPOfficialAcctApplication",Map_SetColumnsList);
	}
	
	private ModelAndView export(List<PageData> varOList, String ExcelName,
			Map<String, TmplConfigDetail> map_SetColumnsList) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if (map_SetColumnsList != null && map_SetColumnsList.size() > 0) {
			for (TmplConfigDetail col : map_SetColumnsList.values()) {
				if (col.getCOL_HIDE().equals("1")) {
					titles.add(col.getCOL_NAME());
				}
			}
			if (varOList != null && varOList.size() > 0) {
				for (int i = 0; i < varOList.size(); i++) {
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						if (col.getCOL_HIDE().equals("1")) {
							Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
							if(null == getCellValue) {
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
	
	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		//String strErrorMessage = "";
		//PageData getPd = this.getPageData();
		Map<String, Object> DicList = new HashMap<String, Object>();

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
			
			// 配置表设置列
			if (Map_SetColumnsList != null && Map_SetColumnsList.size() > 0) {
				for (TmplConfigDetail col : Map_SetColumnsList.values()) {
					titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
				}
			}

			// 调用解析工具包
			testExcel = new LeadingInExcelToPageData<PageData>(formart);
			// 解析excel，获取客户信息集合

			uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex, titleAndAttribute,
					Map_HaveColumnsList, Map_SetColumnsList, DicList, false, false, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取Excel文件错误", e);
			throw new CustomException("读取Excel文件错误:" + e.getMessage(), false);
		}
		boolean judgement = false;

		List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
		if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
			judgement = true;
		}
		out:if (judgement) {
			User user = (User)Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			for (PageData pageData : listUploadAndRead) {
				if(!isNumericUserCose(pageData.getString("STAFF_CODE"))){
					commonBase.setCode(2);
					commonBase.setMessage("员工编号必须为8为数字,请确认！");	
					break out;
				}
				if(!isNumericMobile(pageData.getString("PHONE"))){
					commonBase.setCode(2);
					commonBase.setMessage("联络电话必须为7或11位数字,请确认！");	
					 break out;
				}
			//将每条数据插入新内容
				pageData.put("CONFIRM_STATE", "1"); //1未上报 2已上报 3撤销上报 4已驳回
				pageData.put("USER_DEPART",StringUtil.toString(user.getUNIT_CODE(), ""));
				pageData.put("BUSI_DATE",DateUtils.getCurrentDateMonth()); //业务期间
				pageData.put("STATE","1");
				pageData.put("BILL_USER",user.getUSER_ID());
				pageData.put("BILL_DATE",DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
				pageData.put("APPLY_TYPE","1");//申请类别为新增正式账号
			}
			erpofficialacctapplicationService.grcUpdateDatabase(listUploadAndRead);
			commonBase.setCode(0);
		} else {
			commonBase.setCode(-1);
			commonBase.setMessage("TranslateUtil");
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "erpofficialacctapplication");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}
	//判断文本串是否是7或11位的数字文本
    private boolean isNumericMobile(String str)
    {
          Pattern pattern = Pattern.compile("\\d{7}|\\d{11}");
          Matcher isNum = pattern.matcher(str);
          if( !isNum.matches() )
          {
                return false;
          }
          return true;
    }
	//判断文本串是否是8位的数字文本
    private boolean isNumericUserCose(String str)
    {
          Pattern pattern = Pattern.compile("\\d{8}");
          Matcher isNum = pattern.matcher(str);
          if( !isNum.matches() )
          {
                return false;
          }
          return true;
    }
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
