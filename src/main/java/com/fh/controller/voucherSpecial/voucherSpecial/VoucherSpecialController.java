package com.fh.controller.voucherSpecial.voucherSpecial;

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

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.Else;
import com.alibaba.druid.sql.visitor.functions.If;
import com.fh.controller.base.BaseController;
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.GenerateTransferData;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.SelectBillCodeOptions;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqGridModel;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.SysSealed;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.fundsselfsummy.fundsselfsummy.FundsSelfSummyManager;
import com.fh.service.glZrzxFx.glZrzxFx.GlZrzxFxManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysSealedInfo.syssealedinfo.SysSealedInfoManager;
import com.fh.service.sysUnlockInfo.sysunlockinfo.SysUnlockInfoManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.user.impl.UserService;
import com.fh.service.tmplConfigDict.tmplconfigdict.TmplConfigDictManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.service.voucher.voucher.VoucherManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.SqlTools.CallBack;
import com.fh.util.collectionSql.GroupUtils;
import com.fh.util.collectionSql.GroupUtils.GroupBy;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.BillState;
import com.fh.util.enums.BillType;
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.enums.TmplType;
import com.fh.util.enums.TransferOperType;

import net.sf.json.JSONArray;

/**
 * 特殊凭证数据传输
 * 
 * @ClassName: VoucherController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2018年4月25日
 *
 */
@Controller
@RequestMapping(value = "/voucherSpecial")
public class VoucherSpecialController extends BaseController {

	String menuUrl = "voucherSpecial/list.do"; // 菜单地址(权限用)
	@Resource(name = "voucherService")
	private VoucherManager voucherService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;

	@Resource(name = "tmplconfigdictService")
	private TmplConfigDictManager tmplConfigDictService;

	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;

	@Resource(name = "syssealedinfoService")
	private SysSealedInfoManager syssealedinfoService;

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;

	@Resource(name = "userService")
	private UserManager userService;

	@Resource(name = "sysunlockinfoService")
	private SysUnlockInfoManager sysUnlockInfoService;

	@Resource(name = "glZrzxFxService")
	private GlZrzxFxManager glZrzxFxService;

	@Resource(name="fundsselfsummyService")
	private FundsSelfSummyManager fundsselfsummyService;

	// 底行显示的求和与平均值字段
	private StringBuilder SqlUserdata = new StringBuilder();

	// 判断当前人员的所在组织机构是否只有自己单位
	private int departSelf = 0;

	// 界面查询字段
	List<String> QueryFeildList = Arrays.asList("USER_GROP", "CUST_COL7", "DEPT_CODE");
	// 页面显示数据的年月
	String SystemDateTime = "";
	// 临时数据
	String SelectBillCodeFirstShow = "全部凭证单据";

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		this.departSelf = 0;
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("voucherSpecial/voucherSpecial/voucherSpecial_list");
		PageData pd = this.getPageData();

		// 设置期间
		pd.put("KEY_CODE", "SystemDataTime");
		String busiDate = sysConfigManager.getSysConfigByKey(pd);
		pd.put("busiDate", busiDate);
		// 当前期间,取自tb_system_config的SystemDateTime字段
		SystemDateTime = busiDate;
		mv.addObject("pd", pd);

		// 底行显示的求和与平均值字段
		/*
		 * SqlUserdata = tmplUtil.getSqlUserdata(); boolean hasUserData = false;
		 * if (SqlUserdata != null && !SqlUserdata.toString().trim().equals(""))
		 * { hasUserData = true; } mv.addObject("HasUserData", hasUserData);
		 */
		mv.addObject("HasUserData", true);

		// CUST_COL7 FMISACC 帐套字典
		mv.addObject("fmisacc", DictsUtil.getDictsByParentBianma(dictionariesService, "FMISACC"));
		return mv;
	}

	/**
	 * 显示结构
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShowColModel")
	public @ResponseBody CommonBase getShowColModel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "getShowColModel");
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		String tableCode=getPd.getString("TABLE_CODE");
		String typeCode=getPd.getString("TYPE_CODE");
		String billOff=getPd.getString("FMISACC");
		String busiDate=getPd.getString("BUSI_DATE");
		TmplUtil tmplUtil = new TmplUtil(tmplconfigService, tmplConfigDictService, dictionariesService,
				departmentService, userService);
		String jqGridColModel = tmplUtil.generateStructureNoEditSpecial(tableCode, typeCode, billOff, busiDate);

		// 底行显示的求和与平均值字段
		/*SqlUserdata = tmplUtil.getSqlUserdata();
		boolean hasUserData = false;
		if (SqlUserdata != null && !SqlUserdata.toString().trim().equals("")) {
			hasUserData = true;
		}
		mv.addObject("HasUserData", hasUserData);*/

		commonBase.setCode(0);
		commonBase.setMessage(jqGridColModel);

		return commonBase;
	}

	/**
	 * 单号下拉列表
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSpecialBillCodeList")
	public @ResponseBody CommonBase getSpecialBillCodeList() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		List<String> getCodeList = voucherService.getSpecialBillCodeList(getPd);
		String returnString = SelectBillCodeOptions.getSelectBillCodeOptions(getCodeList, SelectBillCodeFirstShow, "");
		commonBase.setMessage(returnString);
		commonBase.setCode(0);

		return commonBase;
	}
	
	/**
	 * 凭证类型下拉列表
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTypeCodeList")
	public @ResponseBody CommonBase getTypeCodeList() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		List<PageData> getTypeCodeList = voucherService.getTypeCodeList(getPd);
		String returnString = SelectBillCodeOptions.getSelectCodeAndNameOptions(getTypeCodeList, null, null);
		commonBase.setMessage(returnString);
		commonBase.setCode(0);

		return commonBase;
	}
	
	/**
	 * 单位下拉列表
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDepartCodeList")
	public @ResponseBody CommonBase getDepartCodeList() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		List<PageData> getDepartCodeList = voucherService.getDepartCodeList(getPd);
		String returnString = SelectBillCodeOptions.getSelectCodeAndNameOptions(getDepartCodeList, null, null);
		commonBase.setMessage(returnString);
		commonBase.setCode(0);

		return commonBase;
	}

	/**
	 * 传输数据查询
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/voucherSearch")
	public ModelAndView voucherSearch(Page page) throws Exception {
		this.departSelf = 0;
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("voucherSpecial/voucherSpecial/voucherSpecial_search");
		PageData pd = this.getPageData();
		
		// 设置期间
		pd.put("KEY_CODE", "SystemDataTime");
		String busiDate = sysConfigManager.getSysConfigByKey(pd);
		pd.put("busiDate", busiDate);
		mv.addObject("pd", pd);
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息

		mv.addObject("HasUserData", true);
		mv.addObject("fmisacc", DictsUtil.getDictsByParentBianma(dictionariesService, "FMISACC"));
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取待传输列表Voucher");
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.get("BILL_CODE") != null && pd.get("BILL_CODE").equals(this.SelectBillCodeFirstShow)) {
			pd.put("BILL_CODE", "");
		}

		String billOff = pd.getString("FMISACC");
		String busiDate=pd.getString("BUSI_DATE");
		String voucherType = pd.getString("VOUCHER_TYPE");
		String typeCode=pd.getString("TYPE_CODE");
		String deptCode=pd.getString("DEPT_CODE");
		
		pd.put("FMISACC", billOff);//帐套
		pd.put("BUSI_DATE", busiDate);//期间
		pd.put("BILL_TYPE_TRANSFER", typeCode);// 接口封存类型
		pd.put("VOUCHER_TYPE", voucherType);
		pd.put("DEPT_CODE", deptCode);

		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String filters = pd.getString("filters"); // 多条件过滤条件
		if (null != filters && !"".equals(filters)) {
			pd.put("filterWhereResult", SqlTools.constructWhere(filters, new CallBack() {
				public String executeField(String f) {
					if (f.equals("BILL_CODE"))
						return "A.BILL_CODE";
					else
						return f;
				}
			}));
		}

		page.setPd(pd);
		List<PageData> varList = voucherService.listAllSpecial(pd); // 列出Voucher列表
		// int records = voucherService.countJqGrid(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		// result.setRowNum(page.getRowNum());
		// result.setRecords(records);
		// result.setPage(page.getPage());

		// 底行显示的求和与平均值字段
		StringBuilder SqlUserdata = Common.GetSqlUserdataSpecial("tb_gen_bus_summy_bill", billOff, busiDate, typeCode, tmplconfigService);
		PageData userdata = null;
		if (SqlUserdata != null && !SqlUserdata.toString().trim().equals("")) {
			// 底行显示的求和与平均值字段
			pd.put("Userdata", SqlUserdata.toString());
			userdata = voucherService.getFooterSummarySpecial(page);
			result.setUserdata(userdata);
		}
		return result;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSyncDelList")
	public @ResponseBody PageResult<PageData> getSyncDelList(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取待传输列表Voucher");
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.get("BILL_CODE") != null && pd.get("BILL_CODE").equals(this.SelectBillCodeFirstShow)) {
			pd.put("BILL_CODE", "");
		}
		String sealType = pd.getString("TYPE_CODE");// 传输接口类型;
		pd.put("BILL_TYPE", sealType);// 封存类型

		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String filters = pd.getString("filters"); // 多条件过滤条件
		if (null != filters && !"".equals(filters)) {
			pd.put("filterWhereResult", SqlTools.constructWhere(filters, new CallBack() {
				public String executeField(String f) {
					if (f.equals("BILL_CODE"))
						return "A.BILL_CODE";
					else
						return f;
				}
			}));
		}

		page.setPd(pd);
		List<PageData> varList = voucherService.listSyncDelListSpecial(pd); // 列出Voucher列表
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);

		/*
		 * PageData userdata = null; if (SqlUserdata != null &&
		 * !SqlUserdata.toString().trim().equals("")) { // 底行显示的求和与平均值字段
		 * pd.put("Userdata", SqlUserdata.toString()); userdata =
		 * voucherService.getFooterSummary(page); result.setUserdata(userdata);
		 * }
		 */
		return result;
	}

	/**
	 * 明细数据
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFirstDetailList")
	public @ResponseBody PageResult<PageData> getFirstDetailList() throws Exception {
		PageData pd = this.getPageData();
		List<PageData> varList = voucherService.findSummyDetailListSpecial(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);

		return result;
	}

	/**
	 * 列表-获取明细信息
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailList")
	public @ResponseBody PageResult<PageData> getDetailList() throws Exception {
		PageData pd = this.getPageData();

		Object DATA_ROWS = pd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        PageData pdGet = listData.get(0);
		
		/*PageData pdSysConfig = new PageData();
		if(which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())){
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.ContractGRPCond);
		}else if(which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())){
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.MarketGRPCond);
		}else if(which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())){
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.LaborGRPCond);
		}else if(which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())){
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.OperLaborGRPCond);
		}else if(which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())){
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.SysLaborGRPCond);
		}else if(which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())){
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.SocialIncGRPCond);
		}else if(which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())){
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.HouseFundGRPCond);
		}else{
			pdSysConfig.put("KEY_CODE", SysConfigKeyCode.ContractGRPCond);
		}
		
		String strSumFieldDetail = sysConfigManager.getSysConfigByKey(pdSysConfig);
		List<String> listSumFieldDetail = QueryFeildString.tranferStringToList(strSumFieldDetail);
		//listSumFieldDetail = QueryFeildString.extraSumField(listSumFieldDetail, SumFieldBill);
		String strQueryFeild = "";
	    if(listSumFieldDetail!=null && listSumFieldDetail.size()>0){
	    	for(String feild : listSumFieldDetail){
	    		strQueryFeild += " and " + feild + " = '" + pdGet.getString(feild) + "' ";
	    	}
	    }
		pd.put("QueryFeild", strQueryFeild);*/
        
        pd.put("BILL_CODE", pdGet.getString("BILL_CODE"));
		List<PageData> varList = voucherService.listDetailSpecial(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		return result;
	}

	/**
	 * 批量传输
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/voucherTransfer")
	public @ResponseBody CommonBase voucherTransfer() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "凭证传输");
		// String orgCode = Tools.readTxtFile(Const.ORG_CODE); // 读取总部组织机构编码
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("DATA_ROWS");
		JSONArray array = JSONArray.fromObject(strDataRows);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		if (null != listTransferData && listTransferData.size() > 0) {
			/********************** 生成传输数据 ************************/
			PageData pdDetail = new PageData();
			List<String> listBillCodes = new ArrayList<String>();
			for (PageData pdItem : listTransferData) {
				listBillCodes.add(pdItem.getString("BILL_CODE"));
			}
			pdDetail.put("BILL_CODES", listBillCodes);

			List<PageData> listTransferDataDetail = voucherService.findSummyDetailListByBillCodesSpecial(pdDetail);
			addLineNumForTransferData(listTransferDataDetail);

			

			PageData pdFirst = listTransferData.get(0);
			String orgCode = pdFirst.getString("BILL_OFF");
			String busiDate=pdFirst.getString("BUSI_DATE");
			String typeCode=pdFirst.getString("TYPE_CODE");
			
			String tableCodeOnFmis = getTableCodeOnFmis(typeCode,orgCode);
			// 根据表编号和真实表名称获取用于传输的字段列配置信息
			List<TableColumns> tableColumnsForTransfer = getTableColumnsForTransfer(orgCode, busiDate, typeCode);
			GenerateTransferData generateTransferData = new GenerateTransferData();
			Map<String, List<PageData>> mapTransferData = new HashMap<String, List<PageData>>();
			mapTransferData.put(tableCodeOnFmis, listTransferDataDetail);

			/*
			 * String transferData = generateTransferData.generateTransferData(
			 * tableColumnsForTransfer, mapTransferData, orgCode,
			 * TransferOperType.DELETE);
			 */

			// 执行上传FIMS
			Service service = new Service();
			Call call = (Call) service.createCall();
			pd.put("KEY_CODE", "JSynFactTableData");
			String strUrl = sysConfigManager.getSysConfigByKey(pd);
			URL url = new URL(strUrl);
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName("http://JSynFactTableData.j2ee", "synFactData"));
			call.setUseSOAPAction(true);
			/*
			 * String message = (String) call.invoke(new Object[] { transferData
			 * }); System.out.println(message); if (message == null) {
			 * commonBase.setCode(-1); commonBase.setMessage("传输接口出错,请联系管理员！");
			 * } else { if (message.equals("TRUE")) {
			 */
			
			PageData pdBillOffMapping=new PageData();
			pdBillOffMapping.put("BILL_OFF", orgCode);
			pdBillOffMapping.put("TYPE_CODE", typeCode);
			List<PageData> listBillOffMapping = voucherService.findBilloffMapping(pdBillOffMapping);
			
			//上传
			boolean transferSuccessAll=true;
			for (PageData pageData : listBillOffMapping) {
				String billOffMapping=pageData.getString("MAPPING_CODE");
				String transferDataInsert = generateTransferData.generateTransferData(tableColumnsForTransfer,
						mapTransferData, billOffMapping, TransferOperType.INSERT);
				try{
					PageData pdSaveError = new PageData();
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
					pdSaveError.put("sys_log_rec_USER_CODE", user.getUSER_ID());
					pdSaveError.put("sys_log_rec_DEPT_CODE", Jurisdiction.getCurrentDepartmentID());
					pdSaveError.put("sys_log_rec_REC_DATE", DateUtil.getTime());
					pdSaveError.put("sys_log_rec_TYPE_CODE", typeCode);
					pdSaveError.put("sys_log_rec_BILL_CODE", " ");
					pdSaveError.put("StrSaveError", transferDataInsert);
			    	Map<String, Object> map = new HashMap<String, Object>();
			    	map.put("SaveError", pdSaveError);
			    	fundsselfsummyService.batchSaveLog(map);
				} catch(Exception ex) {}
				
				String messageInsert = (String) call.invoke(new Object[] { transferDataInsert });
				if (!messageInsert.equals("TRUE")) {
					transferSuccessAll=false;
					commonBase.setCode(-1);
					commonBase.setMessage(messageInsert);
					break;
				} 
			}
			
			//封存
			if(transferSuccessAll){
				// 执行上传成功后对数据进行封存
				List<SysSealed> listSysSealed = new ArrayList<SysSealed>();
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
				String userId = user.getUSER_ID();
	
				for (PageData pdItem : listTransferData) {
					SysSealed item = new SysSealed();
					// item.setBILL_CODE(pageData.getString("BILL_CODE"));
					// item.setBILL_CODE(" ");
					item.setRPT_DEPT(pdItem.getString("DEPT_CODE"));
					item.setBILL_CODE(pdItem.getString("BILL_CODE"));
					item.setRPT_DUR(pdItem.getString("BUSI_DATE"));
					item.setBILL_OFF(pdItem.getString("BILL_OFF"));
					item.setRPT_USER(userId);
					item.setRPT_DATE(DateUtils.getCurrentTime());// YYYY-MM-DD
																	// HH:MM:SS
					item.setBILL_TYPE(typeCode);
					item.setSTATE("1");// 枚举 1封存,0解封
					listSysSealed.add(item);
				}
				syssealedinfoService.insertBatch(listSysSealed);
				/******************************************************/
				commonBase.setCode(0);
			}
		}
		return commonBase;
	}

	/**
	 * 根据表编号和真实表名称获取用于传输的字段列配置信息
	 * 
	 * @param which
	 * @param tableCode
	 * @return
	 * @throws Exception
	 */
	private List<TableColumns> getTableColumnsForTransfer(String billOff,String busiDate,String typeCode)
			throws Exception {
		// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
		List<TableColumns> tableColumns = tmplconfigService.getTableColumns("tb_gen_summy");
		List<PageData> tmplColumns = Common.getShowColumnListSpecial("tb_gen_summy", busiDate, billOff, typeCode, tmplconfigService);
		List<TableColumns> tableColumnsMerge = new ArrayList<TableColumns>();
		for (PageData tmplConfigDetail : tmplColumns) {
			if (tmplConfigDetail.getString("COL_TRANSFER").equals("1")) {
				TableColumns tableColumnMergeItem = new TableColumns();
				boolean mergeNeed = false;
				for (TableColumns tableColumn : tableColumns) {
					if (tmplConfigDetail.getString("COL_MAPPING_CODE").equals(tableColumn.getColumn_name())) {
						tableColumnMergeItem.setColumn_key(tableColumn.getColumn_key());
						tableColumnMergeItem.setColumn_name(tableColumn.getColumn_name());
						tableColumnMergeItem.setColumn_type(tableColumn.getColumn_type());
						mergeNeed = true;
						break;
					}
				}
				if (mergeNeed)
					tableColumnsMerge.add(tableColumnMergeItem);
			}
		}
		// 增加F_LINE_NO 分线编号 字符型
		TableColumns tableColumnLineNo = new TableColumns();
		tableColumnLineNo.setColumn_name("LINE_NO");
		tableColumnLineNo.setColumn_type("VARCHAR");
		tableColumnsMerge.add(tableColumnLineNo);
		return tableColumnsMerge;
	}
	
	/**
	 * 根据表编号和真实表名称获取用于传输的字段列配置信息
	 * 
	 * @param which
	 * @param tableCode
	 * @return
	 * @throws Exception
	 */
	private List<TableColumns> getTableColumnsForTransferDel(String billOff,String busiDate,String typeCode)
			throws Exception {
		// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
		List<TableColumns> tableColumns = tmplconfigService.getTableColumns("tb_gen_summy");
		List<PageData> tmplColumns = Common.getShowColumnListSpecial("tb_gen_bus_summy_bill", busiDate, billOff, typeCode, tmplconfigService);
		List<TableColumns> tableColumnsMerge = new ArrayList<TableColumns>();
		for (PageData tmplConfigDetail : tmplColumns) {
			if (tmplConfigDetail.getString("COL_TRANSFER").equals("1")) {
				TableColumns tableColumnMergeItem = new TableColumns();
				boolean mergeNeed = false;
				for (TableColumns tableColumn : tableColumns) {
					if (tmplConfigDetail.getString("COL_MAPPING_CODE").equals(tableColumn.getColumn_name())) {
						if(tableColumn.getColumn_name().equals("SERIAL_NO")){
							tableColumnMergeItem.setColumn_key("");
						}else{
							tableColumnMergeItem.setColumn_key(tableColumn.getColumn_key());
						}
						tableColumnMergeItem.setColumn_name(tableColumn.getColumn_name());
						tableColumnMergeItem.setColumn_type(tableColumn.getColumn_type());
						mergeNeed = true;
						break;
					}
				}
				if (mergeNeed)
					tableColumnsMerge.add(tableColumnMergeItem);
			}
		}
		// 增加F_LINE_NO 分线编号 字符型
		TableColumns tableColumnLineNo = new TableColumns();
		tableColumnLineNo.setColumn_name("LINE_NO");
		tableColumnLineNo.setColumn_type("VARCHAR");
		tableColumnsMerge.add(tableColumnLineNo);
		return tableColumnsMerge;
	}

	/**
	 * 根据表编号和真实表名称获取用于传输的字段列配置信息
	 * 
	 * @param which
	 * @param tableCode
	 * @return
	 * @throws Exception
	 */
	private void addLineNumForTransferData(List<PageData> listTransferData) throws Exception {
		// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
		List<PageData> listGlZrzxfx = glZrzxFxService.listAll();
		for (PageData pdSource : listTransferData) {
			for (PageData pdGl : listGlZrzxfx) {
				if (pdSource.getString("DEPT_CODE").equals(pdGl.getString("DEPT_CODE"))
						&& pdSource.getString("BILL_OFF").equals(pdGl.getString("BILL_OFF"))) {
					pdSource.put("LINE_NO", pdGl.getString("LINE_NO"));
				}
			}
		}
	}

	/**
	 * 根据表编号和真实表名称获取用于传输的字段列配置信息
	 * 
	 * @param which
	 * @param tableCode
	 * @return
	 * @throws Exception
	 */
	private List<PageData> filterGroupRow(List<PageData> listTransferData) throws Exception {
		List<PageData> listTransferDataFiltered = new ArrayList<PageData>();
		for (PageData pdSource : listTransferData) {
			if (StringUtil.isEmpty(pdSource.getString("BILL_CODE")))
				continue;
			listTransferDataFiltered.add(pdSource);
		}
		return listTransferDataFiltered;
	}

	/**
	 * 同步删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/syncDel")
	public @ResponseBody CommonBase syncDel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "同步删除");
		// String orgCode = Tools.readTxtFile(Const.ORG_CODE); // 读取总部组织机构编码
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("DATA_ROWS");
		JSONArray array = JSONArray.fromObject(strDataRows);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		if (null != listTransferData && listTransferData.size() > 0) {
			/********************** 生成传输数据 ************************/
			PageData pdDetail = new PageData();
			List<String> listBillCodes = new ArrayList<String>();
			for (PageData pdItem : listTransferData) {
				listBillCodes.add(pdItem.getString("BILL_CODE"));
			}
			pdDetail.put("BILL_CODES", listBillCodes);

			List<PageData> listTransferDataDetail = voucherService.findSummyDetailListByBillCodesSpecial(pdDetail);
			addLineNumForTransferData(listTransferDataDetail);

			PageData pdFirst = listTransferData.get(0);
			String orgCode = pdFirst.getString("BILL_OFF");
			String busiDate=pdFirst.getString("BUSI_DATE");
			String typeCode=pdFirst.getString("TYPE_CODE");
			
			String tableCodeOnFmis = getTableCodeOnFmis(typeCode,orgCode);
			// 根据表编号和真实表名称获取用于传输的字段列配置信息
			List<TableColumns> tableColumnsForTransfer = getTableColumnsForTransfer(orgCode, busiDate, typeCode);
			GenerateTransferData generateTransferData = new GenerateTransferData();
			Map<String, List<PageData>> mapTransferData = new HashMap<String, List<PageData>>();
			mapTransferData.put(tableCodeOnFmis, listTransferDataDetail);
			
			/*
			 * String transferData = generateTransferData.generateTransferData(
			 * tableColumnsForTransfer, mapTransferData, orgCode,
			 * TransferOperType.DELETE);
			 */

			// 执行上传FIMS
			Service service = new Service();
			Call call = (Call) service.createCall();
			pd.put("KEY_CODE", "JSynFactTableData");
			String strUrl = sysConfigManager.getSysConfigByKey(pd);
			URL url = new URL(strUrl);
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName("http://JSynFactTableData.j2ee", "synFactData"));
			call.setUseSOAPAction(true);
			
			/*
			 * String message = (String) call.invoke(new Object[] { transferData
			 * }); System.out.println(message); if (message == null) {
			 * commonBase.setCode(-1); commonBase.setMessage("传输接口出错,请联系管理员！");
			 * } else { if (message.equals("TRUE")) {
			 */
			
			PageData pdBillOffMapping=new PageData();
			pdBillOffMapping.put("BILL_OFF", orgCode);
			pdBillOffMapping.put("TYPE_CODE", typeCode);
			List<PageData> listBillOffMapping = voucherService.findBilloffMapping(pdBillOffMapping);
			
			//上传
			boolean transferSuccessAll=true;
			for (PageData pageData : listBillOffMapping) {
				String billOffMapping=pageData.getString("MAPPING_CODE");
				String transferDataInsert = generateTransferData.generateTransferData(tableColumnsForTransfer,
						mapTransferData, billOffMapping, TransferOperType.DELETE);
				try{
					PageData pdSaveError = new PageData();
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
					pdSaveError.put("sys_log_rec_USER_CODE", user.getUSER_ID());
					pdSaveError.put("sys_log_rec_DEPT_CODE", Jurisdiction.getCurrentDepartmentID());
					pdSaveError.put("sys_log_rec_REC_DATE", DateUtil.getTime());
					pdSaveError.put("sys_log_rec_TYPE_CODE", typeCode);
					pdSaveError.put("sys_log_rec_BILL_CODE", " ");
					pdSaveError.put("StrSaveError", transferDataInsert);
			    	Map<String, Object> map = new HashMap<String, Object>();
			    	map.put("SaveError", pdSaveError);
			    	fundsselfsummyService.batchSaveLog(map);
				} catch(Exception ex) {}
				String message = (String) call.invoke(new Object[] { transferDataInsert });
				if (message == null) {
					transferSuccessAll=false;
					commonBase.setCode(-1);
					commonBase.setMessage("传输接口出错,请联系管理员！");
					break;
				} else {
					if (!message.equals("TRUE")) {
						transferSuccessAll=false;
						commonBase.setCode(-1);
						commonBase.setMessage(message);
						break;
					}
				}
			}
			
			// 更改TB_SYS_UNLOCK_INFO删除状态
			if(transferSuccessAll){
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
				String userId = user.getUSER_ID();
				for (PageData pdItem : listTransferData) {
					pdItem.put("DEL_USER", userId);
					pdItem.put("DEL_DATE", DateUtils.getCurrentTime());// YYYY-MM-DD
																		// HH:MM:SS
				}
				sysUnlockInfoService.edit(listTransferData);
				commonBase.setCode(0);
			}
		}
		/*if (null != listTransferData && listTransferData.size() > 0) {
			///********************** 生成传输数据 ***********************
			//String tableCodeOnFmis = getTableCodeOnFmis(which);

			// 根据表编号和真实表名称获取用于传输的字段列配置信息
			// List<TableColumns> tableColumns =
			// tmplconfigService.getTableColumns(tableCode);
			PageData pdFirst = listTransferData.get(0);
			String orgCode = pdFirst.getString("BILL_OFF");
			String busiDate=pdFirst.getString("BUSI_DATE");
			String typeCode=pdFirst.getString("TYPE_CODE");
			String tableCodeOnFmis = getTableCodeOnFmis(typeCode,orgCode);
			// 根据表编号和真实表名称获取用于传输的字段列配置信息
			List<TableColumns> tableColumnsForTransfer = getTableColumnsForTransferDel(orgCode, busiDate, typeCode);

			GenerateTransferData generateTransferData = new GenerateTransferData();
			Map<String, List<PageData>> mapTransferData = new HashMap<String, List<PageData>>();
			mapTransferData.put(tableCodeOnFmis, listTransferData);

			// 执行上传FIMS
			Service service = new Service();
			Call call = (Call) service.createCall();
			pd.put("KEY_CODE", "JSynFactTableData");
			String strUrl = sysConfigManager.getSysConfigByKey(pd);
			URL url = new URL(strUrl);
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName("http://JSynFactTableData.j2ee", "synFactData"));
			call.setUseSOAPAction(true);
			
			PageData pdBillOffMapping=new PageData();
			pdBillOffMapping.put("BILL_OFF", orgCode);
			pdBillOffMapping.put("TYPE_CODE", typeCode);
			List<PageData> listBillOffMapping = voucherService.findBilloffMapping(pdBillOffMapping);
			
			//上传
			boolean transferSuccessAll=true;
			for (PageData pageData : listBillOffMapping) {
				String billOffMapping=pageData.getString("MAPPING_CODE");
				String transferData = generateTransferData.generateTransferData(tableColumnsForTransfer, mapTransferData,
						billOffMapping, TransferOperType.DELETE);
				String message = (String) call.invoke(new Object[] { transferData });
				if (message == null) {
					transferSuccessAll=false;
					commonBase.setCode(-1);
					commonBase.setMessage("传输接口出错,请联系管理员！");
					break;
				} else {
					if (!message.equals("TRUE")) {
						transferSuccessAll=false;
						commonBase.setCode(-1);
						commonBase.setMessage(message);
						break;
					}
				}
			}
			
			// 更改TB_SYS_UNLOCK_INFO删除状态
			if(transferSuccessAll){
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
				String userId = user.getUSER_ID();
				for (PageData pdItem : listTransferData) {
					pdItem.put("DEL_USER", userId);
					pdItem.put("DEL_DATE", DateUtils.getCurrentTime());// YYYY-MM-DD
																		// HH:MM:SS
				}
				sysUnlockInfoService.edit(listTransferData);
				commonBase.setCode(0);
			}
		}*/
		return commonBase;
	}

	/**
	 * 批量获取凭证号
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchVoucher")
	public @ResponseBody CommonBase batchVoucher() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取凭证号");
		// String orgCode = Tools.readTxtFile(Const.ORG_CODE); // 读取总部组织机构编码
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("DATA_ROWS");
		JSONArray array = JSONArray.fromObject(strDataRows);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		if (null != listTransferData && listTransferData.size() > 0) {
			List<PageData> listTransferDataFiltered = filterGroupRow(listTransferData);
			List<PageData> listVoucherNo = new ArrayList<PageData>();
			// 执行从FIMS获取凭证号
			Service service = new Service();
			Call call = (Call) service.createCall();
			pd.put("KEY_CODE", "JQueryPzInformation");
			String strUrl = sysConfigManager.getSysConfigByKey(pd);
			URL url = new URL(strUrl);
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName("http://JQueryPzInformation.j2ee", "commonQueryPzBh"));
			call.setUseSOAPAction(true);
			// 遍历批量获取凭证号
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			String userId = user.getUSER_ID();
			for (PageData item : listTransferDataFiltered) {
				String invoiceNumber = item.getString("BILL_CODE");// 单据编号
				// String fmisOrg = item.getString("DEPT_CODE");// FMIS组织机构编码
				// String fmisOrg = Tools.readTxtFile(Const.ORG_CODE); //
				// 读取总部组织机构编码
				String fmisOrg = item.getString("BILL_OFF"); // 读取总部组织机构编码
				String busiDate = item.getString("BUSI_DATE"); // 读取业务期间
				String type=item.getString("TYPE_CODE");
				// String tableName = "T_" + getTableCode(which);// 在fmis建立的业务表名
				
				String tableCodeOnFmis="T_" + getTableCodeOnFmis(type, fmisOrg);//在fmis建立的业务表名
				//String tableName = "T_" + "TB_GEN_SUMMY";// 在fmis建立的业务表名
				String result = (String) call.invoke(new Object[] { tableCodeOnFmis, invoiceNumber, fmisOrg });// 对应定义参数
				if (result.length() > 0) {
					String[] stringArr = result.split(";");
					String pzbh = stringArr[0]; // 凭证编号
					// String kjqj = stringArr[1];// 会计期间
					// 执行获取凭证成功后对数据表进行凭证号更新
					PageData pdCert = new PageData();
					pdCert.put("BILL_CODE", item.getString("BILL_CODE"));
					pdCert.put("CERT_CODE", pzbh);
					pdCert.put("BILL_USER", userId);
					pdCert.put("BILL_DATE", DateUtils.getCurrentTime());// YYYY-MM-DD
																		// HH:MM:SS
					pdCert.put("VOUCHER_DATE", stringArr[1]);
					pdCert.put("BUSI_DATE", busiDate);
					listVoucherNo.add(pdCert);
				}
			}
			if (null != listVoucherNo && listVoucherNo.size() > 0) {
				voucherService.updateCertCode(listVoucherNo);
			}
		}
		return commonBase;
	}

	/**
	 * 批量获取冲销凭证号
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchWriteOffVoucher")
	public @ResponseBody CommonBase batchWriteOffVoucher() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取冲销凭证号");
		// String orgCode = Tools.readTxtFile(Const.ORG_CODE); // 读取总部组织机构编码
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("DATA_ROWS");
		JSONArray array = JSONArray.fromObject(strDataRows);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		if (null != listTransferData && listTransferData.size() > 0) {
			List<PageData> listTransferDataFiltered = filterGroupRow(listTransferData);
			/********************** 生成传输数据 ************************/
			String which = pd.getString("TABLE_CODE");
			List<PageData> listVoucherNo = new ArrayList<PageData>();
			// 执行从FIMS获取冲销凭证号
			Service service = new Service();
			Call call = (Call) service.createCall();
			pd.put("KEY_CODE", "JRevertVoucher");
			String strUrl = sysConfigManager.getSysConfigByKey(pd);
			URL url = new URL(strUrl);
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName("http://JRevertVoucher.j2ee", "AmisRevertVoucher"));
			call.setUseSOAPAction(true);
			// 遍历批量获取凭证号
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			String userId = user.getUSER_ID();
			for (PageData item : listTransferDataFiltered) {
				// String fmisOrg = item.getString("DEPT_CODE");// FMIS组织机构编码
				String fmisOrg = item.getString("BILL_OFF"); // 读取总部组织机构编码
				String fmisUrl = strUrl;// FMIS应用地址
				String invoiceNumber = item.getString("BILL_CODE");// 单据编号
				String invoiceState = item.getString("BILL_STATE");// 单据状态
				String voucherDate = item.getString("CERT_BILL_DATE");// 凭证日期
				String voucherNumber = item.getString("CERT_CODE");// 凭证编号
				// String tableName = "T_" + getTableCode(which);// 在fmis建立的业务表名
				//String tableName = "T_" + "TB_GEN_SUMMY";// 在fmis建立的业务表名
				String type=item.getString("TYPE_CODE");
				String tableCodeOnFmis="T_" + getTableCodeOnFmis(type, fmisOrg);//在fmis建立的业务表名
				String workDate = DateUtils.getCurrentTime(DateFormatUtils.DATE_NOFUll_FORMAT);// 当前工作日期格式20170602

				String result = (String) call
						.invoke(new Object[] { tableCodeOnFmis, fmisOrg, voucherDate, voucherNumber, workDate });// 对应定义参数
				String [] resultStrs=result.split(";");
				if(resultStrs.length>0&&resultStrs[0].equals("FALSE")){
					commonBase.setMessage(result);
				}	
				else if(resultStrs.length>0&&resultStrs[0].equals("TRUE")){
					commonBase.setCode(0);
					String reseverNumber = resultStrs[1];// 冲销凭证编号
					// String invoiceNumbers = "";// 冲销凭证编号

					// 执行获取凭证成功后对数据表进行凭证号更新
					PageData pdCert = new PageData();
					pdCert.put("BILL_CODE", item.getString("BILL_CODE"));
					//pdCert.put("CERT_CODE", item.getString("CERT_CODE"));
					pdCert.put("REVCERT_CODE", reseverNumber);
					//pdCert.put("BILL_USER", userId);
					//pdCert.put("BILL_DATE", DateUtils.getCurrentTime());// YYYY-MM-DD
																		// HH:MM:SS
					listVoucherNo.add(pdCert);
				}
			}
			if (null != listVoucherNo && listVoucherNo.size() > 0) {
				voucherService.updateRevCertCode(listVoucherNo);
			}
		}
		return commonBase;
	}
	
	/**
	 * 根据前端业务表索引获取定义在Fmis系统上定义的表名称
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private String getTableCodeOnFmis(String type,String billOff) throws Exception {
		PageData pd = new PageData();
		pd.put("TYPE_CODE", type);
		pd.put("BILL_OFF", billOff);
		List<PageData> listFmisTable = voucherService.findBilloffMapping(pd);
		
		return listFmisTable.get(0).getString("FMIS_TABLE");
	}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
