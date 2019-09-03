package com.fh.controller.taxExternalSystemData.taxExternalSystemData;

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
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
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
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageDataNoUserCode;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysSealedInfo.syssealedinfo.impl.SysSealedInfoService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.taxExternalSystemData.taxExternalSystemData.TaxExternalSystemDataManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/**
 * 税务系统数据导入
* @ClassName: TaxExternalSystemDataController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年8月7日
*
 */
@Controller
@RequestMapping(value="/taxExternalSystemData")
public class TaxExternalSystemDataController extends BaseController {
	
	String menuUrl = "taxExternalSystemData/list.do"; //菜单地址(权限用)
	@Resource(name="taxExternalSystemDataService")
	private TaxExternalSystemDataManager taxExternalSystemDataService;
	
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
	
	//表名
	String TableName = "TB_TAX_EXTERNAL_SYSTEM_DATA";

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BUSI_DATE", "BILL_OFF");//,"DEPT_CODE"
    //设置必定不用编辑的列
    List<String> MustNotEditList = Arrays.asList("BUSI_DATE", "BILL_OFF");//, "DEPT_CODE"
    // 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
    List<String> keyListBase = Arrays.asList("BUSI_DATE", "BILL_OFF","STAFF_IDENT");//, "DEPT_CODE"
	//导入必填项在字典里没翻译
    List<String> ImportNotHaveTransferList = Arrays.asList("BILL_OFF");//, "DEPT_CODE"
    
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	Map<String, Object> DicList = new HashMap<String, Object>();

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TaxExternalSystemData");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("taxExternalSystemData/taxExternalSystemData/taxExternalSystemData_list");

		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);
		
		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		// *********************加载单位树  DEPT_CODE*******************************
		/*String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService);
		if(DepartmentSelectTreeSource.equals("0"))
		{
			getPd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			getPd.put("departTreeSource", 1);
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);*/
		// ***********************************************************
		
		/*String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);*/
		
		Map_HaveColumnsList = Common.GetHaveColumnsMapByTableName(TableName, tmplconfigService);
		
		Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "期间", "1", false));
		Map_SetColumnsList.put("USER_NAME", new TmplConfigDetail("USER_NAME", "姓名", "1", false));
		Map_SetColumnsList.put("STAFF_IDENT", new TmplConfigDetail("STAFF_IDENT", "证照号码", "1", false));
		
		
		TmplConfigDetail BILL_OFF = new TmplConfigDetail("BILL_OFF", "账套", "1", false);
		BILL_OFF.setDICT_TRANS("FMISACC");
		Map_SetColumnsList.put("BILL_OFF", BILL_OFF);
		Common.getDicValue(DicList, BILL_OFF.getDICT_TRANS(), tmplconfigdictService, dictionariesService, departmentService, userService, "");

		/*TmplConfigDetail DEPT_CODE = new TmplConfigDetail("DEPT_CODE", "责任中心", "1", false);
		DEPT_CODE.setDICT_TRANS("oa_department");
		Map_SetColumnsList.put("DEPT_CODE", DEPT_CODE);
		Common.getDicValue(DicList, DEPT_CODE.getDICT_TRANS(), tmplconfigdictService, dictionariesService, departmentService, userService, "");*/
		
		Map_SetColumnsList.put("GROSS_PAY", new TmplConfigDetail("ACCRD_TAX", "应扣缴税额", "1", true));
		
		mv.addObject("pd", getPd);
		return mv;
	}
	

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TaxExternalSystemData");

		PageData getPd = this.getPageData();
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		/*String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}*/
		TransferPd(getPd, SelectedBusiDate, SelectedCustCol7);//, SelectedDepartCode
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		page.setPd(getPd);
		List<PageData> varList = taxExternalSystemDataService.JqPage(page);
		int records = taxExternalSystemDataService.countJqGridExtend(page);
		
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
		logBefore(logger, Jurisdiction.getUsername()+"修改TaxExternalSystemDataService");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限

		PageData getPd = this.getPageData();
		//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//单位
		/*String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
			ShowDataDepartCode = Jurisdiction.getCurrentDepartmentID();
		}*/
		//操作
		String oper = getPd.getString("oper");

		List<PageData> listData = new ArrayList<PageData>();
		if(oper.equals("add")){
			//判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate, 
					SelectedCustCol7, ShowDataCustCol7);//, SelectedDepartCode, ShowDataDepartCode
			if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
				return commonBase;
			}
			getPd.put("BUSI_DATE", SelectedBusiDate);
			getPd.put("BILL_OFF", SelectedCustCol7);
			//User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			//getPd.put("DEPT_CODE", user.getDEPARTMENT_ID());//SelectedDepartCode
			Common.setModelDefault(getPd, Map_HaveColumnsList, Map_SetColumnsList, MustNotEditList);
			for(String strFeild : keyListBase){
				getPd.put(strFeild + TmplUtil.keyExtra, "");
			}
			listData.add(getPd);
		} else {
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
			Common.setModelDefault(getPd, Map_HaveColumnsList, Map_SetColumnsList, MustNotEditList);
			listData.add(getPd);
		}
		String checkState = CheckState(listData);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		taxExternalSystemDataService.batchUpdateDatabase(listData);
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
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);

		if(null != listData && listData.size() > 0){
			for(PageData pdData : listData){
				for(String strFeild : MustNotEditList){
					pdData.put(strFeild, pdData.get(strFeild + TmplUtil.keyExtra));
				}
				Common.setModelDefault(pdData, Map_HaveColumnsList, Map_SetColumnsList, MustNotEditList);
			}
			String checkState = CheckState(listData);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			taxExternalSystemDataService.batchUpdateDatabase(listData);
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

		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        if(null != listData && listData.size() > 0){
        	taxExternalSystemDataService.deleteAll(listData);
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
		//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//单位
		/*String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
			ShowDataDepartCode = Jurisdiction.getCurrentDepartmentID();
		}*/

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate, 
				SelectedCustCol7, ShowDataCustCol7);//, SelectedDepartCode, ShowDataDepartCode
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		}

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "taxExternalSystemData");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		/*mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);*/
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
		
		String strErrorMessage = "";

		PageData getPd = this.getPageData();
		//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//单位
		/*String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
			ShowDataDepartCode = Jurisdiction.getCurrentDepartmentID();
		}*/

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate, 
				SelectedCustCol7, ShowDataCustCol7);//, SelectedDepartCode, ShowDataDepartCode
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		}
		
		if(commonBase.getCode()==-1){
							// 局部变量
							LeadingInExcelToPageDataNoUserCode<PageData> testExcel = null;
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
								testExcel = new LeadingInExcelToPageDataNoUserCode<PageData>(formart);
								// 解析excel，获取客户信息集合

								uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
										titleAndAttribute, Map_HaveColumnsList, Map_SetColumnsList, DicList, false, false, null, ImportNotHaveTransferList);
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
									User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
									List<String> sbRetFeild = new ArrayList<String>();
									String strRetUserCode = "";
									String sbRetMust = "";
									for(int i=0; i<listSize; i++){
										PageData pdAdd = listUploadAndRead.get(i);
										if(pdAdd.size() <= 0){
											continue;
										}
										String getSTAFF_IDENT = (String) pdAdd.get("STAFF_IDENT");
									    if(!(getSTAFF_IDENT!=null && !getSTAFF_IDENT.trim().equals(""))){
									    	strRetUserCode = "导入人员证照号码不能为空！";
									    	break;
									    } else {
											String getMustMessage = returnErrorMust==null ? "" : returnErrorMust.get(getSTAFF_IDENT);
											String getCustomnMessage = returnErrorCostomn==null ? "" : returnErrorCostomn.get(getSTAFF_IDENT);
											if(getMustMessage!=null && !getMustMessage.trim().equals("")){
												sbRetMust += "人员证照号吗" + getSTAFF_IDENT + "：" + getMustMessage + " ";
											}
											if(getCustomnMessage!=null && !getCustomnMessage.trim().equals("")){
												strErrorMessage += "人员证照号吗" + getSTAFF_IDENT + "：" + getCustomnMessage + " ";
											}

											String getBUSI_DATE = (String) pdAdd.get("BUSI_DATE");
											if(!(getBUSI_DATE!=null && !getBUSI_DATE.trim().equals(""))){
												pdAdd.put("BUSI_DATE", SelectedBusiDate);
												getBUSI_DATE = SelectedBusiDate;
											}
											if(!SelectedBusiDate.equals(getBUSI_DATE)){
												if(!sbRetFeild.contains("导入区间和当前区间必须一致！")){
													sbRetFeild.add("导入区间和当前区间必须一致！");
												}
											}
											String getBILL_OFF = (String) pdAdd.get("BILL_OFF");
											if(!(getBILL_OFF!=null && !getBILL_OFF.trim().equals(""))){
												pdAdd.put("BILL_OFF", SelectedCustCol7);
												getBILL_OFF = SelectedCustCol7;
											}
											if(!SelectedCustCol7.equals(getBILL_OFF)){
												if(!sbRetFeild.contains("导入账套和当前账套必须一致！")){
													sbRetFeild.add("导入账套和当前账套必须一致！");
												}
											}
											/*String getDEPT_CODE = (String) pdAdd.get("DEPT_CODE");
											if(!(getDEPT_CODE!=null && !getDEPT_CODE.trim().equals(""))){
												pdAdd.put("DEPT_CODE", SelectedDepartCode);
												getDEPT_CODE = SelectedDepartCode;
											}
											if(!SelectedDepartCode.equals(getDEPT_CODE)){
												if(!sbRetFeild.contains("导入责任中心和当前责任中心必须一致！")){
													sbRetFeild.add("导入责任中心和当前责任中心必须一致！");
												}
											}*/
											Common.setModelDefault(pdAdd, Map_HaveColumnsList, Map_SetColumnsList, MustNotEditList);
											for(String strFeild : keyListBase){
												pdAdd.put(strFeild + TmplUtil.keyExtra, "");
											}
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
													commonBase.setMessage("无可处理的数据！");
												} else {
													String checkState = CheckState(listAdd);
													if(checkState!=null && !checkState.trim().equals("")){
														commonBase.setCode(2);
														commonBase.setMessage(checkState);
													} else {
														taxExternalSystemDataService.batchUpdateDatabase(listAdd);
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
		mv.addObject("local", "taxExternalSystemData");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		/*mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);*/
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public ModelAndView downExcel(JqPage page) throws Exception{
		PageData getPd = this.getPageData();
		//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//单位
		/*String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
			ShowDataDepartCode = Jurisdiction.getCurrentDepartmentID();
		}*/
		TransferPd(getPd, SelectedBusiDate, SelectedCustCol7);//, SelectedDepartCode
		//页面显示数据的二级单位
		List<PageData> varOList = taxExternalSystemDataService.exportModel(getPd);
		return export(varOList, "TaxExternalSystemData");
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出TaxExternalSystemData到excel");
	    
		PageData getPd = this.getPageData();
		//当前区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//单位
		/*String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
			ShowDataDepartCode = Jurisdiction.getCurrentDepartmentID();
		}*/
		TransferPd(getPd, SelectedBusiDate, SelectedCustCol7);//, SelectedDepartCode
		
		page.setPd(getPd);
		List<PageData> varOList = taxExternalSystemDataService.exportList(page);
		return export(varOList, "");
	}
	
	private void TransferPd(PageData getPd, String SelectedBusiDate, String SelectedCustCol7) //, String SelectedDepartCode
			throws Exception{
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		//getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		/*if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}*/
		if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView export(List<PageData> varOList, String ExcelName) throws Exception{
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if(Map_SetColumnsList != null && Map_SetColumnsList.size() > 0){
		    for (TmplConfigDetail col : Map_SetColumnsList.values()) {
				if(col.getCOL_HIDE().equals("1")){
					titles.add(col.getCOL_NAME());
				}
			}
			if(varOList!=null && varOList.size()>0){
				for(int i=0;i<varOList.size();i++){
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : Map_SetColumnsList.values()) {
						if(col.getCOL_HIDE().equals("1")){
						String trans = col.getDICT_TRANS();
						Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
						if(trans != null && !trans.trim().equals("")){
							String value = "";
							Map<String, String> dicAdd = (Map<String, String>) DicList.getOrDefault(trans, new LinkedHashMap<String, String>());
							value = dicAdd.getOrDefault(getCellValue, "");
							vpd.put("var" + j, value);
						} else {
					    	if(getCellValue != null && !getCellValue.toString().trim().equals("")){
					    		if(col.getIsNum()){
							    	vpd.put("var" + j, getCellValue.toString());
					    		} else {
							    	vpd.put("var" + j, getCellValue.toString());
					    		}
					    	} else {
					    		if(col.getIsNum()){
							    	vpd.put("var" + j, "0.00");
					    		} else {
							    	vpd.put("var" + j, " ");
					    		}
					    	}
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
	
	private String CheckState(List<PageData> listData) throws Exception{
		String strRet = "";
		List<PageData> repeatList = taxExternalSystemDataService.getRepeatList(listData);
		if(repeatList!=null && repeatList.size()>0){
			for(int i=0; i<repeatList.size(); i++){
				if(i==0) {
					strRet += "员工证照号码：";
				} else {
					strRet += ", ";
				}
				PageData pd = repeatList.get(i);
				String str = pd.getString("STAFF_IDENT");
				strRet += str;
			}
			strRet += Message.HaveRepeatRecord;
		}
		return strRet;
	}
	
	private String CheckMustSelectedAndSame(String BusiDate, String ShowDataBusiDate, 
			String BILL_CODE, String ShowDataCustCol7) throws Exception{//, String DEPT_CODE, String ShowDataDepartCode
		String strRut = "";
		if(!(BILL_CODE != null && !BILL_CODE.trim().equals(""))){
			strRut += "查询条件中的账套必须选择！";
		} else {
		    if(!BILL_CODE.equals(ShowDataCustCol7)){
				strRut += "查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作！";
		    }
		}
		if(!(BusiDate != null && !BusiDate.trim().equals(""))){
			strRut += "查询条件中的区间必须填写！";
		} else {
		    if(!BusiDate.equals(ShowDataBusiDate)){
				strRut += "查询条件中填写区间与页面显示数据区间不一致，请单击查询再进行操作！";
		    }
		}
		/*if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			strRut += "查询条件中的责任中心不能为空！";
		} else {
		    if(!SelectedDepartCode.equals(ShowDataDepartCode)){
				strRut += "查询条件中所选责任中心与页面显示数据责任中心不一致，请单击查询再进行操作！";
		    }
		}*/
		return strRut;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
