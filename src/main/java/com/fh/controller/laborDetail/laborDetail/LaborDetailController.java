package com.fh.controller.laborDetail.laborDetail;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.CommonBaseAndList;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.laborDetail.laborDetail.impl.LaborDetailService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysDeptLtdTime.sysDeptLtdTime.impl.SysDeptLtdTimeService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明：劳务报酬所得导入
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 */
@Controller
@RequestMapping(value="/laborDetail")
public class LaborDetailController extends BaseController {
	
	String menuUrl = "laborDetail/list.do"; //菜单地址(权限用)
	@Resource(name="laborDetailService")
	private LaborDetailService laborDetailService;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name="sysDeptLtdTimeService")
	private SysDeptLtdTimeService sysDeptLtdTimeService;
	
	//表名
	String TableNameDetail = "TB_LABOR_DETAIL";
	String TableNameBackup = "TB_LABOR_DETAIL_backup";

	//页面显示数据的年月
	//String SystemDateTime = "";
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BILL_OFF", "DEPT_CODE");
	//设置字段类型是数字，但不管隐藏 或显示都必须保存的
	List<String> IsNumFeildButMustInput = Arrays.asList("SERIAL_NO");
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表laborDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("laborDetail/laborDetail/laborDetail_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);
		mv.addObject("pd", getPd);

		//BILL_OFF FMISACC 帐套字典
		List<Dictionaries> ListDicFMISACC = DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC");
		mv.addObject("FMISACC", ListDicFMISACC);
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
		
		Map_HaveColumnsList = Common.GetHaveColumnsMapByTableName(TableNameDetail, tmplconfigService);
		
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "当前区间", "0", false));
		Map_SetColumnsList.put("BILL_OFF", new TmplConfigDetail("BILL_OFF", "当前帐套", "0", false));
		Map_SetColumnsList.put("DEPT_CODE", new TmplConfigDetail("DEPT_CODE", "当前单位", "0", false));

		Map_SetColumnsList.put("SERIAL_NO", new TmplConfigDetail("SERIAL_NO", "流水号", "0", false));
		//Map_SetColumnsList.put("USER_CODE", new TmplConfigDetail("USER_CODE", "编码", "1"));
		Map_SetColumnsList.put("USER_NAME", new TmplConfigDetail("USER_NAME", "姓名", "1", false));
		Map_SetColumnsList.put("STAFF_IDENT", new TmplConfigDetail("STAFF_IDENT", "身份证号", "1", false));
		Map_SetColumnsList.put("GROSS_PAY", new TmplConfigDetail("GROSS_PAY", "应发评审费", "1", true));
		Map_SetColumnsList.put("ACCRD_TAX", new TmplConfigDetail("ACCRD_TAX", "个人所得税", "1", true));
		Map_SetColumnsList.put("ACT_SALY", new TmplConfigDetail("ACT_SALY", "实发评审费", "1", true));
		
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表laborDetail");

		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");
		
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SystemDateTime != null && !SystemDateTime.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		
		//页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		page.setPd(getPd);
		List<PageData> varList = laborDetailService.JqPage(page);	//列出Betting列表
		int records = laborDetailService.countJqGridExtend(page);
		PageData userdata = laborDetailService.getFooterSummary(page);
		
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
		logBefore(logger, Jurisdiction.getUsername()+"修改laborDetail");
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
		//
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
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

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, departSelf);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}

		if(oper.equals("add")){
			getPd.put("SERIAL_NO", "");
			getPd.put("BILL_OFF", SelectedCustCol7);
			getPd.put("DEPT_CODE", SelectedDepartCode);
			getPd.put("BUSI_DATE", SystemDateTime);
			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);
			commonBase = CalculationUpdateDatabase(true, commonBase, "", SelectedCustCol7, SelectedDepartCode, SystemDateTime, listData);
		} else {
			List<PageData> listCheckState = new ArrayList<PageData>();
			listCheckState.add(getPd);
			String checkState = CheckState(listCheckState, SelectedCustCol7, SelectedDepartCode, SystemDateTime);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			getPd.put("TableName", TableNameDetail);
			Common.setModelDefault(getPd, Map_HaveColumnsList, Map_SetColumnsList, IsNumFeildButMustInput);
			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);
			laborDetailService.batchUpdateDatabase(listData);
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
		//
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

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, departSelf);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String checkState = CheckState(listData, SelectedCustCol7, SelectedDepartCode, SystemDateTime);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		if(null != listData && listData.size() > 0){
			for(PageData item : listData){
	      	    item.put("TableName", TableNameDetail);
        	    Common.setModelDefault(item, Map_HaveColumnsList, Map_SetColumnsList, IsNumFeildButMustInput);
            }
			laborDetailService.batchUpdateDatabase(listData);
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
		//
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

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, departSelf);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String checkState = CheckState(listData, SelectedCustCol7, SelectedDepartCode, SystemDateTime);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
        if(null != listData && listData.size() > 0){
			laborDetailService.deleteAll(listData);
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
		//
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

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, departSelf);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		String checkState = CheckState(listData, SelectedCustCol7, SelectedDepartCode, SystemDateTime);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		commonBase = CalculationUpdateDatabase(false, commonBase, "", SelectedCustCol7, SelectedDepartCode, SystemDateTime, listData);
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
		//
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
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, departSelf);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		}
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "laborDetail");
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
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
		//
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

			//判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
					SelectedDepartCode, ShowDataDepartCode, departSelf);
			if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			} else {
				if(!(SystemDateTime!=null && !SystemDateTime.trim().equals(""))){
					commonBase.setCode(2);
					commonBase.setMessage("当前区间不能为空！");
				} else {
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
					    //配置表设置列
					    if(Map_SetColumnsList != null && Map_SetColumnsList.size() > 0){
						    for (TmplConfigDetail col : Map_SetColumnsList.values()) {
							    titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
						    }
					    }
							
						// 调用解析工具包
						testExcel = new LeadingInExcelToPageData<PageData>(formart);
						// 解析excel，获取客户信息集合

						uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
								titleAndAttribute, Map_HaveColumnsList, Map_SetColumnsList, DicList, false, false, null, null);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("读取Excel文件错误", e);
						throw new CustomException("读取Excel文件错误:" + e.getMessage(),false);
					}
					boolean judgement = false;

					Map<String, String> returnError =  (Map<String, String>) uploadAndReadMap.get(2);
					if(returnError != null && returnError.size()>0){
						String strGet = "";
						for (String k : returnError.keySet()) {
							if(returnError.get(k)!=null && !returnError.get(k).trim().equals("")){
								strGet += "人员编码" + k + " : " + returnError.get(k);
							}
						}
						if(strGet!=null && !strGet.trim().equals("")){
							strErrorMessage += "字典无此翻译： " + strGet; // \n
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
							for(int i=0;i<listSize;i++){
								PageData pdAdd = listUploadAndRead.get(i);
								if(pdAdd.size() <= 0){
									continue;
								}
								//String getUSER_CODE = (String) pdAdd.get("USER_CODE");
								//if(getUSER_CODE!=null && !getUSER_CODE.trim().equals("")){
								String getUSER_NAME = (String) pdAdd.get("USER_NAME");
								String getSTAFF_IDENT = (String) pdAdd.get("STAFF_IDENT");
								if(getUSER_NAME!=null && !getUSER_NAME.trim().equals("")
								      && getSTAFF_IDENT!=null && !getSTAFF_IDENT.trim().equals("")){
									pdAdd.put("SERIAL_NO", "");
									String getBUSI_DATE = (String) pdAdd.get("BUSI_DATE");
									if(!(getBUSI_DATE!=null && !getBUSI_DATE.trim().equals(""))){
										pdAdd.put("BUSI_DATE", SystemDateTime);
										getBUSI_DATE = SystemDateTime;
									}
									if(!SystemDateTime.equals(getBUSI_DATE)){
										if(!sbRet.contains("导入区间和当前区间必须一致！")){
											sbRet.add("导入区间和当前区间必须一致！");
										}
									}
									String getBILL_OFF = (String) pdAdd.get("BILL_OFF");
									if(!(getBILL_OFF!=null && !getBILL_OFF.trim().equals(""))){
										pdAdd.put("BILL_OFF", SelectedCustCol7);
										getBILL_OFF = SelectedCustCol7;
									}
									if(!SelectedCustCol7.equals(getBILL_OFF)){
										if(!sbRet.contains("导入账套和当前账套必须一致！")){
											sbRet.add("导入账套和当前账套必须一致！");
										}
									}
									String getDEPT_CODE = (String) pdAdd.get("DEPT_CODE");
									if(!(getDEPT_CODE!=null && !getDEPT_CODE.trim().equals(""))){
										pdAdd.put("DEPT_CODE", SelectedDepartCode);
										getDEPT_CODE = SelectedDepartCode;
									}
									if(!SelectedDepartCode.equals(getDEPT_CODE)){
										if(!sbRet.contains("导入单位和当前单位必须一致！")){
											sbRet.add("导入单位和当前单位必须一致！");
										}
									}
									String strGROSS_PAY = "0";
									if(pdAdd.get("GROSS_PAY")!=null && !pdAdd.get("GROSS_PAY").toString().trim().equals("")){
										strGROSS_PAY = pdAdd.get("GROSS_PAY").toString().trim();
									}
									String strACCRD_TAX = "0";
									if(pdAdd.get("ACCRD_TAX")!=null && !pdAdd.get("ACCRD_TAX").toString().trim().equals("")){
										strACCRD_TAX = pdAdd.get("ACCRD_TAX").toString().trim();
									}
									String strACT_SALY = "0";
									if(pdAdd.get("ACT_SALY")!=null && !pdAdd.get("ACT_SALY").toString().trim().equals("")){
										strACT_SALY = pdAdd.get("ACT_SALY").toString().trim();
									}
									BigDecimal douGROSS_PAY = new BigDecimal(strGROSS_PAY).setScale(2, BigDecimal.ROUND_HALF_UP);
									BigDecimal douACCRD_TAX = new BigDecimal(strACCRD_TAX).setScale(2, BigDecimal.ROUND_HALF_UP);
									BigDecimal douACT_SALY = new BigDecimal(strACT_SALY).setScale(2, BigDecimal.ROUND_HALF_UP);

									pdAdd.put("GROSS_PAY", douGROSS_PAY);
									pdAdd.put("ACCRD_TAX", douACCRD_TAX);
									pdAdd.put("ACT_SALY", douACT_SALY);
									
									if(douACT_SALY.compareTo(douGROSS_PAY.subtract(douACCRD_TAX)) != 0){
										sbRet.add(//"员工编号:" + pdSetUSER_CODE + 
												  " 姓名:" + pdAdd.getString("USER_NAME")
												+ " 身份证号:" + pdAdd.getString("STAFF_IDENT")
												+ " 实发评审费 应等于 应发评审费 -个人所得税");
									}
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
								if(!(listAdd!=null && listAdd.size()>0)){
									commonBase.setCode(2);
									commonBase.setMessage("请导入符合条件的数据！");
								} else {
									CommonBaseAndList getCommonBaseAndList = getCalculationData(true, commonBase, SelectedCustCol7, SelectedDepartCode, SystemDateTime, listAdd);
									int i=0;
									for(PageData pdSet : getCommonBaseAndList.getList()){
										i++;
										String strDistinct = "" + i;
										//String pdSetUSER_CODE = pdSet.getString("USER_CODE");
										String pdSetUSER_NAME = pdSet.getString("USER_NAME");
										String pdSetSTAFF_IDENT = pdSet.getString("STAFF_IDENT");
										pdSet.put("DistinctColumn", strDistinct);
										BigDecimal douCalACCRD_TAX = new BigDecimal(0);
										BigDecimal douImpACCRD_TAX = new BigDecimal(0);
										BigDecimal douCalACT_SALY = new BigDecimal(0);
										BigDecimal douImpACT_SALY = new BigDecimal(0);
										BigDecimal douYDRZE = new BigDecimal(0);
										BigDecimal douYSZE = new BigDecimal(0);
										for(PageData pdsum : getCommonBaseAndList.getList()){
											//String pdsumUSER_CODE = pdsum.getString("USER_CODE");
											//if(pdSetUSER_CODE!=null && pdSetUSER_CODE.equals(pdsumUSER_CODE)){
											String pdsumUSER_NAME = pdsum.getString("USER_NAME");
											String pdsumSTAFF_IDENT = pdsum.getString("STAFF_IDENT");
											if(pdSetUSER_NAME.equals(pdsumUSER_NAME) && pdSetSTAFF_IDENT.equals(pdsumSTAFF_IDENT)){
												pdsum.put("DistinctColumn", strDistinct);
												douCalACCRD_TAX = douCalACCRD_TAX.add(new BigDecimal(pdsum.get("ACCRD_TAX").toString()));
												douImpACCRD_TAX = douImpACCRD_TAX.add(new BigDecimal(pdsum.get("ACCRD_TAX" + TmplUtil.keyExtra).toString()));
												douCalACT_SALY = douCalACT_SALY.add(new BigDecimal(pdsum.get("ACT_SALY").toString()));
												douImpACT_SALY = douImpACT_SALY.add(new BigDecimal(pdsum.get("ACT_SALY" + TmplUtil.keyExtra).toString()));

												douYDRZE = douYDRZE.add(new BigDecimal(pdsum.get("YDRZE").toString()));
												
												douYSZE = douYSZE.add(new BigDecimal(pdsum.get("YSZE").toString()));
											}
										}
										pdSet.put("ACCRD_TAX" + TmplUtil.keyExtra + TmplUtil.keyExtra, douCalACCRD_TAX);
										pdSet.put("ACCRD_TAX" + TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra, douImpACCRD_TAX);
										pdSet.put("ACT_SALY" + TmplUtil.keyExtra + TmplUtil.keyExtra, douCalACT_SALY);
										pdSet.put("ACT_SALY" + TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra, douImpACT_SALY);
										pdSet.put("YDRZE" + TmplUtil.keyExtra, douYDRZE);
										pdSet.put("YSZE" + TmplUtil.keyExtra, douYSZE);
									}
									List<String> listDistinct = new ArrayList<String>();
									String strCalculationMessage = "";
									for(PageData pdSet : getCommonBaseAndList.getList()){
											//String pdSetUSER_CODE = pdSet.getString("USER_CODE");
											//if(!listDistinct.contains(pdSetUSER_CODE)){
										    String pdSetDistinctColumn = pdSet.getString("DistinctColumn");
										    if(!listDistinct.contains(pdSetDistinctColumn)){
										    	BigDecimal douCalACCRD_TAX = new BigDecimal(pdSet.get("ACCRD_TAX" + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
										    	BigDecimal douImpACCRD_TAX = new BigDecimal(pdSet.get("ACCRD_TAX" + TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
										    	BigDecimal douCalACT_SALY = new BigDecimal(pdSet.get("ACT_SALY" + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
										    	BigDecimal douImpACT_SALY = new BigDecimal(pdSet.get("ACT_SALY" + TmplUtil.keyExtra + TmplUtil.keyExtra + TmplUtil.keyExtra).toString());
										    	BigDecimal douYSZE = new BigDecimal(pdSet.get("YSZE" + TmplUtil.keyExtra).toString());
										    	BigDecimal douYDRZE = new BigDecimal(pdSet.get("YDRZE" + TmplUtil.keyExtra).toString());
												if(!(douCalACCRD_TAX.compareTo(douImpACCRD_TAX) == 0 
														&& douCalACT_SALY.compareTo(douImpACT_SALY) == 0)){
													strCalculationMessage += //"员工编号:" + pdSetUSER_CODE + 
															  " 姓名:" + pdSet.getString("USER_NAME")
															+ " 身份证号:" + pdSet.getString("STAFF_IDENT")
															+ " 应税总额:" + douYSZE 
															+ " 已导入纳税额:" + (douYDRZE.subtract(douImpACCRD_TAX))
															+ " 本次导入纳税额:" + douImpACCRD_TAX
															+ " 实际应导入纳税额:" + douCalACCRD_TAX
															+ " 本次导入实发评审费:" + douImpACT_SALY
															+ " 实际应导入实发评审费:" + douCalACT_SALY + "<br/>";
												}
											}
											listDistinct.add(pdSetDistinctColumn);
									}
									if(strCalculationMessage!=null && !strCalculationMessage.trim().equals("")){
										commonBase.setCode(3);
										commonBase.setMessage(strCalculationMessage);
									} else {
										commonBase = UpdateDatabase(true, commonBase, strErrorMessage, getCommonBaseAndList);
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
		} 
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "laborDetail");
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("SystemDateTime", SystemDateTime);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}
	
	private CommonBaseAndList getCalculationData(Boolean IsAdd, CommonBase commonBase, 
			String SelectedCustCol7, String SelectedDepartCode, String SystemDateTime,
			List<PageData> listData) throws Exception{
		CommonBaseAndList retCommonBaseAndList = new CommonBaseAndList();
		if(listData!=null && listData.size()>0){
			PageData getQueryFeildPd = new PageData();
			getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
			getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
			String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
			QueryFeild += " and BUSI_DATE = '" + SystemDateTime + "' ";
			if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
			if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}
			if(!(SystemDateTime!=null && !SystemDateTime.trim().equals(""))){
				QueryFeild += " and 1 != 1 ";
			}

	        for(PageData item : listData){
          	    item.put("TableName", TableNameBackup);
	       	    Common.setModelDefault(item, Map_HaveColumnsList, Map_SetColumnsList, IsNumFeildButMustInput);
	        }
	        
			String GROSS_PAY = "GROSS_PAY";
			String ACCRD_TAX = "ACCRD_TAX";
			String ACT_SALY = "ACT_SALY";
			String sqlRetSelect = " select *, " 
			    + ACCRD_TAX + " " + ACCRD_TAX + TmplUtil.keyExtra + ", "
		        + ACT_SALY + " " + ACT_SALY + TmplUtil.keyExtra + " "
                + " from " + TableNameBackup;
			
			String sqlSumByUserNameStaffIdent =  " select USER_NAME, STAFF_IDENT, " //USER_CODE
			        + " sum(" + GROSS_PAY + ") " + GROSS_PAY + ", "
					+ " sum(" + ACCRD_TAX + ") " + ACCRD_TAX + ", "
					+ " sum(" + ACT_SALY + ") " + ACT_SALY + " "
					+ " from " + TableNameBackup 
					+ " where 1=1 "
					+ QueryFeild 
					+ " group by USER_NAME, STAFF_IDENT";

			PageData pdInsetBackup = new PageData();
			pdInsetBackup.put("QueryFeild", QueryFeild);
			String strInsertFeild = QueryFeildString.tranferListValueToSelectString(Map_HaveColumnsList);
			pdInsetBackup.put("FeildList", strInsertFeild);
			
			List<PageData> dataCalculation = laborDetailService.getDataCalculation(TableNameBackup, TmplUtil.keyExtra,
					pdInsetBackup, listData,
					sqlRetSelect, 
					sqlSumByUserNameStaffIdent);
			retCommonBaseAndList.setList(dataCalculation);
		}
		retCommonBaseAndList.setCommonBase(commonBase);
		return retCommonBaseAndList;
	}
	
	private CommonBase UpdateDatabase(Boolean IsAdd, CommonBase commonBase, String strErrorMessage,
			CommonBaseAndList getCommonBaseAndList) throws Exception{
		if(getCommonBaseAndList!=null && getCommonBaseAndList.getList()!=null && getCommonBaseAndList.getList().size()>0){
			for(PageData each : getCommonBaseAndList.getList()){
				if(IsAdd){
					each.put("SERIAL_NO", "");
				}
				Common.setModelDefault(each, Map_HaveColumnsList, Map_SetColumnsList, IsNumFeildButMustInput);
				each.put("TableName", TableNameDetail);
			}
    		
    		//此处执行集合添加 
			laborDetailService.batchUpdateDatabase(getCommonBaseAndList.getList());
    		commonBase.setCode(0);
    		commonBase.setMessage(strErrorMessage);
		} else {
			commonBase = getCommonBaseAndList.getCommonBase();
		}
		return commonBase;
	}
	
	private CommonBase CalculationUpdateDatabase(Boolean IsAdd, CommonBase commonBase, String strErrorMessage,
			String SelectedCustCol7, String SelectedDepartCode, String SystemDateTime,
			List<PageData> listData) throws Exception{
		CommonBaseAndList getCommonBaseAndList = getCalculationData(IsAdd, commonBase,
				SelectedCustCol7, SelectedDepartCode, SystemDateTime, listData);
		return UpdateDatabase(IsAdd, commonBase, strErrorMessage,
				getCommonBaseAndList);
	}

	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
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
		//
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, departSelf);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			//commonBase.setCode(2);
			//commonBase.setMessage(strGetCheckMustSelected);
			//return commonBase;
		}
		
		PageData transferPd = this.getPageData();
		transferPd.put("SystemDateTime", SystemDateTime);
		List<PageData> varOList = laborDetailService.exportModel(transferPd);
		return export(varOList, "LaborDetail", Map_SetColumnsList);
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
		//
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//当前区间
		String SystemDateTime = getPd.getString("SystemDateTime");

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedDepartCode, ShowDataDepartCode, departSelf);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			//commonBase.setCode(2);
			//commonBase.setMessage(strGetCheckMustSelected);
			//return commonBase;
		}
		
		//页面显示数据的年月
		getPd.put("SystemDateTime", SystemDateTime);
		page.setPd(getPd);
		List<PageData> varOList = laborDetailService.exportList(page);
		return export(varOList, "", Map_SetColumnsList);
	}
	
	private ModelAndView export(List<PageData> varOList, String ExcelName, 
			Map<String, TmplConfigDetail> map_SetColumnsList) throws Exception{
		//Map<String, Object> DicList = Common.GetDicList(TypeCodeDetail, SelectedDepartCode, SelectedCustCol7, 
		//		tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService, AdditionalReportColumns);
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
						    //String trans = col.getDICT_TRANS();
						    Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
						    //if(trans != null && !trans.trim().equals("")){
						    //	String value = "";
						    //	Map<String, String> dicAdd = (Map<String, String>) DicList.getOrDefault(trans, new LinkedHashMap<String, String>());
						    //	value = dicAdd.getOrDefault(getCellValue, "");
						    //	vpd.put("var" + j, value);
						    //} else {
							    vpd.put("var" + j, getCellValue.toString());
						    //}
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
	
	private String CheckState(List<PageData> pdSerialNo, String SelectedCustCol7, String SelectedDepartCode, String SystemDateTime) throws Exception{
		String strRut = "";
        if(pdSerialNo!=null && pdSerialNo.size()>0){
        	List<Integer> listStringSerialNo = QueryFeildString.getListIntegerFromListPageData(pdSerialNo, "SERIAL_NO", "");
			String strSqlInSerialNo = QueryFeildString.tranferListIntegerToGroupbyString(listStringSerialNo);
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
    		QueryFeild += " and SERIAL_NO in (" + strSqlInSerialNo + ") ";
    		transferPd.put("QueryFeild", QueryFeild);
    		
    		//页面显示数据的年月
    		transferPd.put("SystemDateTime", SystemDateTime);
    		transferPd.put("SelectFeildName", "SERIAL_NO"); 
    		List<PageData> getSerialNo = laborDetailService.getSerialNoBySerialNo(transferPd);
    		if(!(listStringSerialNo!=null && getSerialNo!=null && listStringSerialNo.size() == getSerialNo.size())){
    			strRut = Message.OperDataAlreadyChange;
    		} else {
    			for(PageData each : getSerialNo){
    				if(!listStringSerialNo.contains((Integer)each.get("SERIAL_NO"))){
    					strRut = Message.OperDataAlreadyChange;
    				}
    			}
    		}
        }
		return strRut;
	}
	
	private String CheckMustSelectedAndSame(String BILL_OFF, String ShowDataCustCol7, 
			String DEPT_CODE, String ShowDataDepartCode, int DepartTreeSource) throws Exception{
		String strRut = "";
		if(!(BILL_OFF != null && !BILL_OFF.trim().equals(""))){
			strRut += "查询条件中的账套必须选择！";
		} else {
		    if(!BILL_OFF.equals(ShowDataCustCol7)){
				strRut += "查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作！";
		    }
		}
		if(!(DEPT_CODE != null && !DEPT_CODE.trim().equals(""))){
			strRut += "查询条件中的责任中心不能为空！";
		} else {
		    if(DepartTreeSource!=1 && !DEPT_CODE.equals(ShowDataDepartCode)){
				strRut += "查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作！";
		    }
		}
		return strRut;
	}
	
	 /**导入提示
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/showErrorTaxMessage")
	public ModelAndView showErrorTaxMessage() throws Exception{
		PageData getPd = this.getPageData();
		String ErrorTaxMessage = getPd.getString("ErrorTaxMessage");
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/ErrorTax");
		mv.addObject("commonMessage", ErrorTaxMessage);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
