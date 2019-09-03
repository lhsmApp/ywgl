package com.fh.controller.taxStaffDetail.taxStaffDetail;

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
import com.fh.controller.common.Corresponding;
import com.fh.controller.common.DictsUtil;
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
import com.fh.util.enums.TmplType;
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.staffRemitInfo.staffRemitInfo.StaffRemitInfoManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.impl.UserService;
import com.fh.service.taxStaffDetail.taxStaffDetail.TaxStaffDetailManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明：月度工资及个税导入
 * 创建人：jiachao
 * 创建时间：2019-08-07
 */
@Controller
@RequestMapping(value="/taxStaffDetail")
public class TaxStaffDetailController extends BaseController {
	
	String menuUrl = "taxStaffDetail/list.do"; //菜单地址(权限用)
	@Resource(name="taxStaffDetailService")
	private TaxStaffDetailManager taxStaffDetailService;

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
	String TableName = "TB_TAX_STAFF_DETAIL";
	// 默认的which值
	String DefaultWhile = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();
	String AdditionalReportColumns = "";
	// 配置表配置成显示，则是必填项
	//private List<String> IfShow_MustInputList = Arrays.asList("USER_CODE", "UNITS_CODE", "STAFF_IDENT", "USER_CATG");
	// 设置分组时不求和字段 SERIAL_NO 设置字段类型是数字，但不用求和
	List<String> jqGridGroupNotSumFeild = Arrays.asList("SERIAL_NO");
	
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BUSI_DATE","CUST_COL7");//,"DEPT_CODE"
    //设置必定不用编辑的列
    List<String> MustNotEditList = Arrays.asList("BUSI_DATE");
    
    
    //获取带__的列，后续删除之类的有用
	private List<String> keyListBase = Arrays.asList("BUSI_DATE","STAFF_IDENT");
	//设置必定不为空的列
	private List<String> MustInputList = Arrays.asList("BUSI_DATE", "STAFF_IDENT");
    
	Map<String, TableColumns> map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
    Map<String, TmplConfigDetail> map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	Map<String, Object> map_DicList = new LinkedHashMap<String, Object>();
	//底行显示的求和字段
    StringBuilder SqlUserdata = new StringBuilder();    
    //StaffRemitRestDeptCode 不能操作银川、武汉责任中心
    List<String> SysConfigStaffRemitRestDeptCode = Arrays.asList("01009", "01017");
    List<String> SysConfigStaffPut = Arrays.asList("PUT03", "PUT04");


	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TaxStaffDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("taxStaffDetail/taxStaffDetail/taxStaffDetail_list");
		mv.addObject("jqGridColModel", "[]");
		mv.addObject("jqGridColModelMessage", "");

		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);
		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));

		// 员工组 必须执行，用来设置汇总和传输上报类型
 		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		 		
		this.map_SetColumnsList = Common.GetSetColumnsList(SelectedTableNo, "",
				"9870", tmplconfigService);
		this.map_HaveColumnsList = Common.GetHaveColumnsList(SelectedTableNo, tmplconfigService);
		this.map_DicList = Common.GetDicList(SelectedTableNo, "", "9870",
				tmplconfigService, tmplconfigdictService, dictionariesService, departmentService, userService,
				AdditionalReportColumns);
		
	    SqlUserdata = new StringBuilder();

	    
        TmplUtil tmpl = new TmplUtil(tmplconfigService, tmplconfigdictService, dictionariesService, departmentService,
				userService, keyListBase, null, AdditionalReportColumns, MustInputList, jqGridGroupNotSumFeild);
		String jqGridColModel = tmpl.generateStructure(SelectedTableNo, "", "9870", 3,
				MustNotEditList);
		
		//String jqGridColModel = tmpl.generateStructure(List_HaveColumnsList, List_SetColumnsList, MustNotEditList);
		String result="";
		if(jqGridColModel!=null&&jqGridColModel.trim()!=""){
			result=jqGridColModel.replace("{ width: '130',  name: 'BILL_CODE' ,  edittype:'text',  editoptions:{maxlength:'20'} ,  hidden: false,  editable: true,  formoptions:{ rowpos:1, colpos:1 },  label: '业务单号' },", "")
					.replace("label: '责任中心'", "hidden: true, label: '责任中心'")
					.replace("label: '项目'", "hidden: true, label: '项目'");
		}else{
			result=jqGridColModel;
		}
		mv.addObject("jqGridColModel", result);
		mv.addObject("pd", getPd);
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表TaxStaffDetail");

		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		//String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		
		TransferPd(getPd, SelectedBusiDate, SelectedCustCol7);//, SelectedDepartCode

		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		//页面显示数据的年月
		/*getPd.put("SelectedBusiDate", SelectedBusiDate);
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}*/
		page.setPd(getPd);
		List<PageData> varList = taxStaffDetailService.JqPage(page);	//列出Betting列表
		int records = taxStaffDetailService.countJqGridExtend(page);
		PageData userdata = null;
		if(SqlUserdata!=null && !SqlUserdata.toString().trim().equals("")){
			//底行显示的求和与平均值字段
			getPd.put("Userdata", SqlUserdata.toString());
		    userdata = taxStaffDetailService.getFooterSummary(page);
		}
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		result.setUserdata(userdata);
		
		return result;
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
			taxStaffDetailService.deleteAll(listData);
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
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");

		if(commonBase.getCode()==-1){
			//判断选择为必须选择的
			String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate,SelectedCustCol7, ShowDataCustCol7);
			if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(strGetCheckMustSelected);
			}
		}
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "taxStaffDetail");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
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
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		String strErrorMessage = "";
		String CurrentDepartCode = Jurisdiction.getCurrentDepartmentID();
		if (!(CurrentDepartCode != null && !CurrentDepartCode.trim().equals(""))) {
			commonBase.setCode(2);
			commonBase.setMessage("当前登录人责任中心为空，请联系管理员！");
		}
		if (CurrentDepartCode != null && !CurrentDepartCode.equals(DictsUtil.DepartShowAll_01001)){
			commonBase.setCode(3);
			commonBase.setMessage("当前登录人责任中心非机关人员，不能导入！");
		}
		

		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");

		//判断选择为必须选择的
		String strGetCheckMustSelected = CheckMustSelectedAndSame(SelectedBusiDate, ShowDataBusiDate,SelectedCustCol7,ShowDataCustCol7);
		if(strGetCheckMustSelected!=null && !strGetCheckMustSelected.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(strGetCheckMustSelected);
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
					    	strRetUserCode = "导入证照号码不能为空！";
					    	break;
					    } else {
							String getMustMessage = returnErrorMust==null ? "" : returnErrorMust.get(getUSER_CODE);
							String getCustomnMessage = returnErrorCostomn==null ? "" : returnErrorCostomn.get(getUSER_CODE);
							if(getMustMessage!=null && !getMustMessage.trim().equals("")){
								sbRetMust += "员工编号" + getUSER_CODE + "证照号码" + getSTAFF_IDENT + "：" + getMustMessage + " ";
							}
							if(getCustomnMessage!=null && !getCustomnMessage.trim().equals("")){
								strErrorMessage += "员工编号" + getUSER_CODE + "证照号码" + getSTAFF_IDENT + "：" + getCustomnMessage + " ";
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
							String getBILL_OFF = (String) pdAdd.get("CUST_COL7");
							if(!(getBILL_OFF!=null && !getBILL_OFF.trim().equals(""))){
								pdAdd.put("CUST_COL7", SelectedCustCol7);
								getBILL_OFF = SelectedCustCol7;
							}
							/*if(!SelectedCustCol7.equals(getBILL_OFF)){
								if(!sbRetFeild.contains("导入账套和当前账套必须一致！")){
									sbRetFeild.add("导入账套和当前账套必须一致！");
								}
							}*/
							
							String getUserCatg = (String) pdAdd.get("USER_CATG");
							if(SysConfigStaffPut.contains(getUserCatg)){
								pdAdd.put("CUST_COL7", DictsUtil.BillOffOld);
							}else{
								pdAdd.put("CUST_COL7", DictsUtil.BillOffNew);
							}
							
							String getDEPT_CODE = (String) pdAdd.get("UNITS_CODE");
							if(SysConfigStaffRemitRestDeptCode.contains(getDEPT_CODE)){
								/*if(!sbRetFeild.contains("不能导入责任中心为银川和武汉的记录！")){
									sbRetFeild.add("不能导入责任中心为银川和武汉的记录！");
									//sbRetFeild.add("只能导入除了特定责任中心的记录！");
								}*/
								pdAdd.put("DEPT_CODE", getDEPT_CODE);
							}else{
								pdAdd.put("DEPT_CODE", DictsUtil.DepartShowAll_01001);
							}
							/*
							String getUNITS_CODE = (String) pdAdd.get("UNITS_CODE");
							if(!(getUNITS_CODE!=null && !getUNITS_CODE.trim().equals(""))){
								if(!sbRetFeild.contains("所属二级单位不能为空！")){
									sbRetFeild.add("所属二级单位不能为空！");
								}
							}*/
							//必须设置，在查询重复数据时有用
							/*pdAdd.put("BUSI_DATE" + TmplUtil.keyExtra, " ");
							pdAdd.put("STAFF_IDENT" + TmplUtil.keyExtra, " ");*/
							pdAdd.put("BUSI_DATE" + TmplUtil.keyExtra, pdAdd.get("BUSI_DATE"));
							//pdAdd.put("CUST_COL7" + TmplUtil.keyExtra, pdAdd.get("CUST_COL7"));
							pdAdd.put("STAFF_IDENT" + TmplUtil.keyExtra, pdAdd.get("STAFF_IDENT"));
							
							//haveColumnsList和map_SetColumnsList，设置保存的数据列及对应值
							Common.setModelDefault(pdAdd, map_HaveColumnsList, map_SetColumnsList, MustNotEditList);
				    		/*String strCanDel = "";
				    		if(SysConfigStaffRemitRestDeptCode.contains(getDEPT_CODE)){
				    			strCanDel = " and DEPT_CODE not in (" + QueryFeildString.tranferListValueToSqlInString(SysConfigStaffRemitRestDeptCode) + ") ";
				    		} else {
				    			strCanDel = " and 1 != 1 ";
				    		}
				    		pdAdd.put("CanDel", strCanDel);*/
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
									/*String checkRepeat = CheckRepeat(listAdd);
									if(checkRepeat!=null && !checkRepeat.trim().equals("")){
										//重复数据，要在界面提示是否覆盖
										commonBase.setCode(9);
										commonBase.setMessage(checkRepeat);
									} else {*/
										//此处执行集合添加 
										taxStaffDetailService.batchUpdateDatabase(listAdd);
										commonBase.setCode(0);
										commonBase.setMessage(strErrorMessage);
									//}
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
		mv.addObject("local", "taxStaffDetail");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("ShowDataBusiDate", ShowDataBusiDate);
		mv.addObject("SelectedCustCol7", SelectedCustCol7);
		mv.addObject("ShowDataCustCol7", ShowDataCustCol7);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		//InsertField、InsertVale、InsertLogVale字段全设置成""，listAdd可变成listAdd传递
//		for(PageData pdAdd : listAdd){
//			Common.setModelDefault(pdAdd);
//    		pdAdd.put("CanDel", "");
//		}
//		mv.addObject("StringDataRows", JSONArray.fromObject(listAdd).toString().replaceAll("'", "\""));//
		return mv;
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
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		
		TransferPd(getPd, SelectedBusiDate, SelectedCustCol7);//, SelectedDepartCode
	
		List<PageData> varOList = taxStaffDetailService.exportModel(getPd);
		return export(varOList, "TaxStaffDetail", map_SetColumnsList, map_DicList);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出TaxStaffDetail到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
	    
		PageData getPd = this.getPageData();
		//页面选择区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//页面显示数据区间
		//String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");

		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		//页面显示数据的年月
		/*getPd.put("SelectedBusiDate", SelectedBusiDate);
		page.setPd(getPd);*/
		TransferPd(getPd, SelectedBusiDate, SelectedCustCol7);//, SelectedDepartCode
		List<PageData> varOList = taxStaffDetailService.exportList(page);
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
		    	if(col.getCOL_CODE().toUpperCase().equals("BILL_CODE")) continue;
		    	if(col.getCOL_CODE().toUpperCase().equals("DEPT_CODE")) continue;
		    	if(col.getCOL_CODE().toUpperCase().equals("ITEM_CODE")) continue;
				if(col.getCOL_HIDE().equals("1")){
					titles.add(col.getCOL_NAME());
				}
			}
			if(varOList!=null && varOList.size()>0){
				for(int i=0;i<varOList.size();i++){
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : setColumnsList.values()) {
						if(col.getCOL_CODE().toUpperCase().equals("BILL_CODE")) continue;
						if(col.getCOL_CODE().toUpperCase().equals("DEPT_CODE")) continue;
						if(col.getCOL_CODE().toUpperCase().equals("ITEM_CODE")) continue;
						if(col.getCOL_HIDE().equals("1")){
						String trans = col.getDICT_TRANS();
						Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
						if(getCellValue==null) continue;
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
		List<PageData> listRepeat = taxStaffDetailService.getRepeat(listData);
		if(listRepeat!=null && listRepeat.size()>0){
			for(PageData each : listRepeat){
				strRut += each.getString("STAFF_IDENT") + Message.HaveRepeatRecord;
			}
		}
		return strRut;
	}
	
	private void TransferPd(PageData getPd, String SelectedBusiDate, String SelectedCustCol7) //, String SelectedDepartCode
			throws Exception{
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		getQueryFeildPd.put("CUST_COL7", SelectedCustCol7);
		//getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		/*if(!(SelectedDepartCode != null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}*/
		/*if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}*/
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
	}
	
	private String CheckMustSelectedAndSame(String BusiDate, String ShowDataBusiDate,String SelectDataCustCol7, String ShowDataCustCol7) throws Exception{
		String strRut = "";
		/*if(!(SelectDataCustCol7 != null && !SelectDataCustCol7.trim().equals(""))){
			strRut += "查询条件中的账套必须选择！";
		} else {
		    if(!SelectDataCustCol7.equals(ShowDataCustCol7)){
				strRut += "查询条件中所选账套与页面显示数据账套不一致，请单击查询再进行操作！";
		    }
		}*/
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
