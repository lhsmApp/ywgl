package com.fh.controller.laborQuery.laborQuery;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.QueryFeildString;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.Department;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.enums.SysConfigKeyCode;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;

import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.laborDetail.laborDetail.impl.LaborDetailService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明：劳务报酬所得查询
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 */
@Controller
@RequestMapping(value="/laborQuery")
public class LaborQueryController extends BaseController {
	
	String menuUrl = "laborQuery/list.do"; //菜单地址(权限用)
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
	
	//表名
	String TableNameDetail = "TB_LABOR_DETAIL";

	//页面显示数据的年月
	//String ssSystemDateTime = "";
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BILL_OFF", "DEPT_CODE");
    //有权限导出表的部门
    List<String> DepartCanExportTable = new ArrayList<String>();
    List<Dictionaries> ListDicFMISACC = new ArrayList<Dictionaries>();

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表LaborQuery");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("laborQuery/laborQuery/laborQuery_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String ssSystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", ssSystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);
		//有权限导出表的部门
		DepartCanExportTable = new ArrayList<String>();
		Boolean bolCanExportTable = false;
		PageData pdCanExportTable = new PageData();
		pdCanExportTable.put("KEY_CODE", SysConfigKeyCode.CanExportTable);
		String strCanExportTable = sysConfigManager.getSysConfigByKey(pdCanExportTable);
		if(strCanExportTable == null) strCanExportTable = "";
		String[] list = strCanExportTable.replace(" ", "").split(",");
		if(list!=null && list.length>0){
			DepartCanExportTable = Arrays.asList(list);
			if(DepartCanExportTable.contains(Jurisdiction.getCurrentDepartmentID())){
				bolCanExportTable = true;
			}
		}
		getPd.put("CanExportTable", bolCanExportTable);
		mv.addObject("pd", getPd);

		//BILL_OFF FMISACC 帐套字典
		ListDicFMISACC = DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC");
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
		
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表laborQuery");

		PageData getPd = this.getPageData();
		//日期
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
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
		if(!(SelectedBusiDate != null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		
		//页面显示数据的年月
		getPd.put("SystemDateTime", SelectedBusiDate);
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

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goDownExcel")
	public ModelAndView goDownExcel() throws Exception{
		CommonBase commonBase = new CommonBase();
	    commonBase.setCode(-1);
	    
		PageData getPd = this.getPageData();
		//日期
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
	    
		List<Dictionaries> dicList = new ArrayList<Dictionaries>();
		String DepartTreeSource = "";
		if(DictsUtil.DepartShowAll_01001.equals(Jurisdiction.getCurrentDepartmentID())
				|| DictsUtil.DepartShowAll_00.equals(Jurisdiction.getCurrentDepartmentID())){
			DepartTreeSource = "1";
			Dictionaries itemAll = new Dictionaries();
			itemAll.setDICT_CODE("ALL");
			itemAll.setNAME("全部");
			dicList.add(itemAll);
			Dictionaries itemHome = new Dictionaries();
			itemHome.setDICT_CODE("HOME");
			itemHome.setNAME("公司本部");
			dicList.add(itemHome);
			List<Department> listDepartDic = departmentService.getDepartDic(new PageData());
			for(String strDeptCode : DepartCanExportTable){
				if(strDeptCode!=null && !strDeptCode.trim().equals("")
						&& !strDeptCode.equals(Jurisdiction.getCurrentDepartmentID())){
                  String strDeptName = "";
                  for(Department dicDept : listDepartDic){
  					if(strDeptCode.equals(dicDept.getDEPARTMENT_CODE())){
  						strDeptName = dicDept.getNAME();
  					}
                  }
                  if(strDeptName!=null && !strDeptName.equals("")){
  					Dictionaries itemAdd = new Dictionaries();
  					itemAdd.setDICT_CODE(strDeptCode);
  					itemAdd.setNAME(strDeptName);
  					dicList.add(itemAdd);
                  }
				}
			}
		} else {
			DepartTreeSource = "0";
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/downExcel");
		mv.addObject("local", "laborQuery");
		mv.addObject("SelectedBusiDate", SelectedBusiDate);
		mv.addObject("DepartTreeSource", DepartTreeSource);
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		//FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		//DEPARTMENT 责任中心字典
		mv.addObject("DEPARTMENT", dicList);
		return mv;
	}
	
	/**导出到excel
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		PageData getPd = this.getPageData();
		//日期
		String SelectedBusiDate = getPd.getString("DownSelectedBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("DownSelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("DownSelectedDepartCode");

		String WhereSql = " and BUSI_DATE = '" + SelectedBusiDate + "' ";
		WhereSql += " and BILL_OFF = '" + SelectedCustCol7 + "' ";

		if(DictsUtil.DepartShowAll_01001.equals(Jurisdiction.getCurrentDepartmentID())
				|| DictsUtil.DepartShowAll_00.equals(Jurisdiction.getCurrentDepartmentID())){
			if(!(SelectedDepartCode!=null && !SelectedDepartCode.equals(""))){
				WhereSql += " and 1 != 1 ";
			} else {
				if(SelectedDepartCode.equals("ALL")){
					
				} else if(SelectedDepartCode.equals("HOME")){
					List<String> listDeptSqlNotIn = new ArrayList<String>();
					for(String strDeptCode : DepartCanExportTable){
						if(strDeptCode!=null && !strDeptCode.trim().equals("")
								&& !strDeptCode.equals(DictsUtil.DepartShowAll_01001)
								&& !strDeptCode.equals(DictsUtil.DepartShowAll_00)){
							listDeptSqlNotIn.add(strDeptCode);
						}
					}
					String strDeptSqlNotIn = QueryFeildString.tranferListValueToSqlInString(listDeptSqlNotIn);
					WhereSql += " and DEPT_CODE not in (" + strDeptSqlNotIn + ") ";
				} else {
					WhereSql += " and DEPT_CODE = '" + SelectedDepartCode + "' ";
				}
			}
		} else {
			WhereSql += " and DEPT_CODE = '" + Jurisdiction.getCurrentDepartmentID() + "' ";
		}

		String strSelectFeild = " USER_NAME, STAFF_IDENT, IFNULL((SELECT d.NAME FROM oa_department d WHERE DEPT_CODE = d.DEPARTMENT_CODE), ' ') DEPT_CODE, "
				+ " sum(GROSS_PAY) GROSS_PAY, sum(ACCRD_TAX) ACCRD_TAX ";
		getPd.put("SelectFeild", strSelectFeild);
		getPd.put("GroupByFeild", " USER_NAME, STAFF_IDENT, DEPT_CODE ");
		getPd.put("WhereSql", WhereSql);
		page.setPd(getPd);
		List<PageData> varOList = laborDetailService.exportSumList(page);
		if(varOList!=null && varOList.size()>0){
			for(PageData each : varOList){
				each.put("CERT_TYPE", "居民身份证");
				each.put("TAX_BURDENS", "自行负担");
			}
		}
		
		Map<String, TmplConfigDetail> map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
		map_SetColumnsList.put("工号", new TmplConfigDetail("工号", "工号", "1", false));
		map_SetColumnsList.put("USER_NAME", new TmplConfigDetail("USER_NAME", "姓名", "1", false));
		map_SetColumnsList.put("CERT_TYPE", new TmplConfigDetail("CERT_TYPE", "证件类型", "1", false));
		map_SetColumnsList.put("STAFF_IDENT", new TmplConfigDetail("STAFF_IDENT", "证件号码", "1", false));
		map_SetColumnsList.put("TAX_BURDENS", new TmplConfigDetail("TAX_BURDENS", "税款负担方式", "1", false));
		map_SetColumnsList.put("DEPT_CODE", new TmplConfigDetail("DEPT_CODE", "责任中心", "1", false));
		map_SetColumnsList.put("GROSS_PAY", new TmplConfigDetail("GROSS_PAY", "收入额", "1", true));
		map_SetColumnsList.put("ACCRD_TAX", new TmplConfigDetail("ACCRD_TAX", "税额", "1", true));
		map_SetColumnsList.put("免税所得", new TmplConfigDetail("免税所得", "免税所得", "1", true));
		map_SetColumnsList.put("基本养老保险费", new TmplConfigDetail("基本养老保险费", "基本养老保险费", "1", true));
		map_SetColumnsList.put("基本医疗保险费", new TmplConfigDetail("基本医疗保险费", "基本医疗保险费", "1", true));
		map_SetColumnsList.put("失业保险费", new TmplConfigDetail("失业保险费", "失业保险费", "1", true));
		map_SetColumnsList.put("住房公积金", new TmplConfigDetail("住房公积金", "住房公积金", "1", true));
		map_SetColumnsList.put("允许扣除的税费", new TmplConfigDetail("允许扣除的税费", "允许扣除的税费", "1", true));
		map_SetColumnsList.put("商业健康保险费", new TmplConfigDetail("商业健康保险费", "商业健康保险费", "1", true));
		map_SetColumnsList.put("其他扣除", new TmplConfigDetail("其他扣除", "其他扣除", "1", true));
		map_SetColumnsList.put("实际捐赠额", new TmplConfigDetail("实际捐赠额", "实际捐赠额", "1", true));
		map_SetColumnsList.put("允许列支的捐赠比例", new TmplConfigDetail("允许列支的捐赠比例", "允许列支的捐赠比例", "1", false));
		map_SetColumnsList.put("准予扣除的捐赠额", new TmplConfigDetail("准予扣除的捐赠额", "准予扣除的捐赠额", "1", true));
		map_SetColumnsList.put("减免税额", new TmplConfigDetail("减免税额", "减免税额", "1", true));
		map_SetColumnsList.put("备注", new TmplConfigDetail("备注", "备注", "1", false));
		
		String strBillOffName = "";
		if(ListDicFMISACC != null){
			for(Dictionaries dic : ListDicFMISACC){
				if(SelectedCustCol7.equals(dic.getDICT_CODE())){
					strBillOffName = dic.getNAME();
				}
			}
		}
		
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		String fileName = SelectedBusiDate + "_" + strBillOffName + "_" + "劳务报酬所得表";
		dataMap.put("filename", new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
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
						    if(getCellValue==null) getCellValue = "";
						    //if(trans != null && !trans.trim().equals("")){
							//    String value = "";
							//    Map<String, String> dicAdd = (Map<String, String>) DicList.getOrDefault(trans, new LinkedHashMap<String, String>());
							//    value = dicAdd.getOrDefault(getCellValue, "");
							//    vpd.put("var" + j, value);
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
