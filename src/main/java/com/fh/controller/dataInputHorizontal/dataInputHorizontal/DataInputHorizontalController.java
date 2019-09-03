package com.fh.controller.dataInputHorizontal.dataInputHorizontal;

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
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.SelectBillCodeOptions;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
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
@RequestMapping(value="/dataInputHorizontal")
public class DataInputHorizontalController extends BaseController {
	
	String menuUrl = "dataInputHorizontal/list.do"; //菜单地址(权限用)
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
		logBefore(logger, Jurisdiction.getUsername()+"列表dataInputHorizontal");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();

		//单号下拉列表
		getPd.put("SelectedTypeCodeFirstShow", SelectedTypeCodeFirstShow);
		getPd.put("InitSelectedTypeCodeOptions", SelectBillCodeOptions.getSelectDicOptions(null, SelectedTypeCodeFirstShow));
		getPd.put("SelectedDepartCodeFirstShow", SelectedDepartCodeFirstShow);
		getPd.put("InitSelectedDepartCodeOptions", SelectBillCodeOptions.getSelectDicOptions(null, SelectedDepartCodeFirstShow));

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("dataInputHorizontal/dataInputHorizontal/dataInputHorizontal_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);

		//BILL_OFF FMISACC 帐套字典
		List<Dictionaries> listBiffOff = sysChangevalueMappingService.getSelectBillOffList(new PageData());
		mv.addObject("FMISACC", listBiffOff);

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
	@RequestMapping(value="/getShowColModel")
	public @ResponseBody CommonBase getShowColModel() throws Exception{
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
		int columnCount = 2;
		int row = 1;
		int col = 1;
		StringBuilder struString = new StringBuilder();
		for (Dictionaries dic : list) {
			if (struString != null && !struString.toString().trim().equals("")) {
				struString.append(",");
			}
			struString.append(" { ");
			struString.append(" formoptions:{ rowpos:" + row + ", colpos:" + col + " }, ");
			col++;
			if (col > columnCount) {
				row++;
				col = 1;
			}
			struString.append(" label: '" + dic.getNAME() + "', name: '" + dic.getDICT_CODE() + "', editable: true, edittype:'text',search:false, sorttype: 'number',align:'right', searchrules: {number: true},  ");
			struString.append(" formatter: 'number', formatoptions: {thousandsSeparator:',', decimalSeparator:'.', defaulValue:'0.00',decimalPlaces:2}, editoptions: {maxlength:'15', number: true} ");
			struString.append(" } ");
		}

		StringBuilder returnString = new StringBuilder();
		returnString.append("[");
		returnString.append(struString);
		returnString.append("]");
		
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
		List<PageData> getList = dataInputService.JqPage(page);	//列出Betting列表

		List<PageData> varList = new ArrayList<PageData>();
		if(getList!=null && getList.size()>0){
			PageData pdAdd = new PageData();
			for(PageData each : getList){
				pdAdd.put(each.get("CHANGE_COL"), each.get("DATA_VALUE"));
			}
			varList.add(pdAdd);
		}
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
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
				SelectedTypeCode, ShowDataTypeCode, 
				SelectedDepartCode, ShowDataDepartCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		PageData pdSet = new PageData();
		pdSet.put("BILL_OFF", SelectedCustCol7);
		pdSet.put("TYPE_CODE", SelectedTypeCode);
		pdSet.put("DEPT_CODE", SelectedDepartCode);
		List<Dictionaries> list = sysChangevalueMappingService.getSelectMapCodeList(pdSet);
		if(list!=null && list.size()>0){
			List<PageData> listData = new ArrayList<PageData>();
			for(Dictionaries dic : list){
				PageData pdAdd = new PageData();
				pdAdd.put("TYPE_CODE", SelectedTypeCode);
				pdAdd.put("BILL_OFF", SelectedCustCol7);
				pdAdd.put("DEPT_CODE", SelectedDepartCode);
				pdAdd.put("BUSI_DATE", SystemDateTime);
				pdAdd.put("CHANGE_COL", dic.getDICT_CODE());
				String strVal = getPd.getString(dic.getDICT_CODE());
				if(!(strVal!=null && !strVal.trim().equals(""))){
					strVal = "0";
				}
				pdAdd.put("DATA_VALUE", strVal);
				listData.add(pdAdd);
			}
			dataInputService.batchUpdateDatabaseHorizontal(listData);
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
		//当前区间
		String SystemDateTime = pd.getString("SystemDateTime");
		String mesDateTime = CheckSystemDateTime.CheckTranferSystemDateTime(SystemDateTime, sysConfigManager,
				false);
		if(mesDateTime!=null && !mesDateTime.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(mesDateTime);
			return commonBase;
		}
		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedCustCol7, ShowDataCustCol7, 
				SelectedTypeCode, ShowDataTypeCode, 
				SelectedDepartCode, ShowDataDepartCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
			return commonBase;
		}
		
		Object DATA_ROWS = pd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> getListData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		
		if(null != getListData && getListData.size() > 0){
			PageData pdData = getListData.get(0);

			PageData pdSet = new PageData();
			pdSet.put("BILL_OFF", SelectedCustCol7);
			pdSet.put("TYPE_CODE", SelectedTypeCode);
			pdSet.put("DEPT_CODE", SelectedDepartCode);
			List<Dictionaries> list = sysChangevalueMappingService.getSelectMapCodeList(pdSet);
			if(list!=null && list.size()>0){
				List<PageData> listData = new ArrayList<PageData>();
				for(Dictionaries dic : list){
					PageData pdAdd = new PageData();
					pdAdd.put("TYPE_CODE", SelectedTypeCode);
					pdAdd.put("BILL_OFF", SelectedCustCol7);
					pdAdd.put("DEPT_CODE", SelectedDepartCode);
					pdAdd.put("BUSI_DATE", SystemDateTime);
					pdAdd.put("CHANGE_COL", dic.getDICT_CODE());
					String strVal = pdData.getString(dic.getDICT_CODE());
					if(!(strVal!=null && !strVal.trim().equals(""))){
						strVal = "0";
					}
					pdAdd.put("DATA_VALUE", strVal);
					listData.add(pdAdd);
				}
				dataInputService.batchUpdateDatabaseHorizontal(listData);
				commonBase.setCode(0);
			}
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
			String checkCopyState = CheckCopyData(SelectedCustCol7, SelectedTypeCode, SelectedDepartCode, SystemDateTime, pd);
			if(checkCopyState!=null && !checkCopyState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkCopyState);
				return commonBase;
			}
			dataInputService.batchCopyAll(pd);
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
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		String ShowDataTypeCode = getPd.getString("ShowDataTypeCode");
		//责任中心
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		String ShowDataDepartCode = getPd.getString("ShowDataDepartCode");

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
				SelectedTypeCode, ShowDataTypeCode, 
				SelectedDepartCode, ShowDataDepartCode);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
		}
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "dataInputHorizontal");
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("SelectedTypeCode", SelectedTypeCode);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("ShowDataTypeCode", ShowDataTypeCode);
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
		    	    SelectedTypeCode, ShowDataTypeCode, 
		    	    SelectedDepartCode, ShowDataDepartCode);
		    if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
		        commonBase.setCode(2);
		        commonBase.setMessage(strGetCheckMustSelected);
		    } else {
				PageData pdSet = new PageData();
				pdSet.put("BILL_OFF", SelectedCustCol7);
				pdSet.put("TYPE_CODE", SelectedTypeCode);
				pdSet.put("DEPT_CODE", SelectedDepartCode);
				List<Dictionaries> listColumnsList = sysChangevalueMappingService.getSelectMapCodeList(pdSet);
				if(!(listColumnsList != null && listColumnsList.size() > 0)){
			        commonBase.setCode(2);
			        commonBase.setMessage("请在变动列配置功能配置变动列！");
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
						if(listColumnsList != null && listColumnsList.size() > 0){
							for (Dictionaries col : listColumnsList) {
								titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getNAME()), col.getDICT_CODE());
							}
						}

						// 调用解析工具包
						testExcel = new LeadingInExcelToPageData<PageData>(formart);
						// 解析excel，获取客户信息集合

						uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
								titleAndAttribute, null, null, null, false, false, null, null);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("读取Excel文件错误", e);
						throw new CustomException("读取Excel文件错误:" + e.getMessage(),false);
					}
					boolean judgement = false;
					List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
					if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
						judgement = true;
					}
					if (judgement) {
						int listSize = listUploadAndRead.size();
						if(listSize > 0){
							PageData pdUploadAndRead = listUploadAndRead.get(0);
							
							List<PageData> listData = new ArrayList<PageData>();
							for(Dictionaries dic : listColumnsList){
								PageData pdAdd = new PageData();
								pdAdd.put("TYPE_CODE", SelectedTypeCode);
								pdAdd.put("BILL_OFF", SelectedCustCol7);
								pdAdd.put("DEPT_CODE", SelectedDepartCode);
								pdAdd.put("BUSI_DATE", SystemDateTime);
								pdAdd.put("CHANGE_COL", dic.getDICT_CODE());
								String strVal = pdUploadAndRead.getString(dic.getDICT_CODE());
								if(!(strVal!=null && !strVal.trim().equals(""))){
									strVal = "0";
								}
								pdAdd.put("DATA_VALUE", strVal);
								listData.add(pdAdd);
							}
							dataInputService.batchUpdateDatabaseHorizontal(listData);
							commonBase.setCode(0);
					    } else {
							commonBase.setCode(2);
							commonBase.setMessage("请导入数据！");
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
		mv.addObject("local", "dataInputHorizontal");
		mv.addObject("SelectedDepartCode", SelectedDepartCode);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("SelectedTypeCode", SelectedTypeCode);
		mv.addObject("ShowDataDepartCode", ShowDataDepartCode);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("ShowDataTypeCode", ShowDataTypeCode);
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
	public ModelAndView downExcel(JqPage page) throws Exception{
		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//责任中心
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//当前区间
		//String SystemDateTime = getPd.getString("SystemDateTime");

		PageData pdTransfer = setTransferPd(getPd);
		page.setPd(pdTransfer);
		List<PageData> varOList = dataInputService.exportList(page);
		return export(varOList, "DataInput", SelectedCustCol7, SelectedTypeCode, SelectedDepartCode);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SocialIncDetail到excel");

		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//责任中心
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//当前区间
		//String SystemDateTime = getPd.getString("SystemDateTime");

		PageData pdTransfer = setTransferPd(getPd);
		page.setPd(pdTransfer);
		List<PageData> varOList = dataInputService.exportList(page);
		return export(varOList, "", SelectedCustCol7, SelectedTypeCode, SelectedDepartCode);
	}
	
	private ModelAndView export(List<PageData> varOList, String ExcelName,
			String SelectedCustCol7, String SelectedTypeCode, String SelectedDepartCode) throws Exception{
		if(!(varOList!=null && varOList.size()>0)){
			varOList = new ArrayList<PageData>();
		}
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();

		PageData pdSet = new PageData();
		pdSet.put("BILL_OFF", SelectedCustCol7);
		pdSet.put("TYPE_CODE", SelectedTypeCode);
		pdSet.put("DEPT_CODE", SelectedDepartCode);
		List<Dictionaries> list = sysChangevalueMappingService.getSelectMapCodeList(pdSet);
		if(list!=null && list.size()>0){
			PageData vpd = new PageData();
			int j = 1;
			for(Dictionaries dic : list){
				titles.add(dic.getNAME());
			    Object getCellValue = "0";
				for(PageData each : varOList){
					if(dic.getDICT_CODE().toUpperCase().equals(each.getString("CHANGE_COL").toUpperCase())){
						getCellValue = each.get("DATA_VALUE");
					}
				}
				vpd.put("var" + j, getCellValue.toString());
			    j++;
			}
			varList.add(vpd);
		}
		dataMap.put("titles", titles);
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap); 
		return mv;
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
	
	/*private String CheckState(String SystemDateTime, List<PageData> listData) throws Exception{
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
	}*/
	
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