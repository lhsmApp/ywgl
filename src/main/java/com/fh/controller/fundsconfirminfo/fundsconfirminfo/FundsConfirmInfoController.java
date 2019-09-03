package com.fh.controller.fundsconfirminfo.fundsconfirminfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.SelectBillCodeOptions;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.system.Department;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.enums.FundsConfirmInfoState;
import com.fh.util.enums.SysConfirmInfoBillType;

import com.fh.util.Jurisdiction;
import com.fh.service.fundsconfirminfo.fundsconfirminfo.FundsConfirmInfoManager;
import com.fh.service.sysBillnum.sysbillnum.SysBillnumManager;
import com.fh.service.certParmConfig.certParmConfig.impl.CertParmConfigService;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysDeptMapping.sysDeptMapping.impl.SysDeptMappingService;
import com.fh.service.sysStruMapping.sysStruMapping.impl.SysStruMappingService;
import com.fh.service.sysTableMapping.sysTableMapping.impl.SysTableMappingService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.impl.TmplConfigDictService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
@Controller
@RequestMapping(value="/fundsconfirminfo")
public class FundsConfirmInfoController extends BaseController {
	
	String menuUrl = "fundsconfirminfo/list.do"; //菜单地址(权限用)
	@Resource(name="fundsconfirminfoService")
	private FundsConfirmInfoManager fundsconfirminfoService;
	
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name="sysTableMappingService")
	private SysTableMappingService sysTableMappingService;
	@Resource(name="sysStruMappingService")
	private SysStruMappingService sysStruMappingService;
	@Resource(name="tmplconfigdictService")
	private TmplConfigDictService tmplconfigdictService;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name = "userService")
	private UserManager userService;
	
	@Resource(name="certParmConfigService")
	private CertParmConfigService certParmConfigService;
	@Resource(name="sysbillnumService")
	private SysBillnumManager sysbillnumService;
	@Resource(name="sysDeptMappingService")
	private SysDeptMappingService sysDeptMappingService;
	
	//表名
	String tb_sys_dept_mapping = "tb_sys_dept_mapping";

	//临时数据
	String SelectedTypeCodeFirstShow = "请选择凭证类型";
	String SelectedDepartCodeFirstShow = "请选择责任中心";

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("TYPE_CODE", "BILL_OFF", "DEPT_CODE");

    //获取汇总条件传的责任中心
    //String DeptCodeSumGroupField = "01";

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表fundsconfirminfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();

		//单号下拉列表
		getPd.put("SelectedTypeCodeFirstShow", SelectedTypeCodeFirstShow);
		getPd.put("InitSelectedTypeCodeOptions", SelectBillCodeOptions.getSelectDicOptions(null, SelectedTypeCodeFirstShow));
		getPd.put("SelectedDepartCodeFirstShow", SelectedDepartCodeFirstShow);
		getPd.put("InitSelectedDepartCodeOptions", SelectBillCodeOptions.getSelectDicOptions(null, SelectedDepartCodeFirstShow));
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fundsconfirminfo/fundsconfirminfo/fundsconfirminfo_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime);

		//BILL_OFF FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		//TYPE_CODE PZTYPE 凭证字典
		//mv.addObject("PZTYPE", DictsUtil.getDictsByParentCode(dictionariesService, "PZTYPE"));

		//BILL_OFF FMISACC 帐套字典
		String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);

		//TYPE_CODE PZTYPE 凭证字典
		mv.addObject("PZTYPE", DictsUtil.getDictsByParentCode(dictionariesService, "PZTYPE"));
		String typeCodeValus = DictsUtil.getDicValue(dictionariesService, "PZTYPE");
		String typeCodeStringAll = ":[All];" + typeCodeValus;
		String typeCodeStringSelect = ":;" + typeCodeValus;
		mv.addObject("typeCodeStrAll", typeCodeStringAll);
		mv.addObject("typeCodeStrSelect", typeCodeStringSelect);
		
		String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);

		String billTypeValus = "";
		SysConfirmInfoBillType[] enumsBillType = SysConfirmInfoBillType.values();  
    	if(enumsBillType!=null){
            for (int i = 0; i < enumsBillType.length; i++) {  
    			if (billTypeValus != null && !billTypeValus.toString().trim().equals("")) {
    				billTypeValus += ";";
    			}
    			billTypeValus += enumsBillType[i].getNameKey() + ":" + enumsBillType[i].getNameValue();
            }  
    	}
		String billTypeStringAll = ":[All];" + billTypeValus;
		String billTypeStringSelect = ":;" + billTypeValus;
		mv.addObject("billTypeStrAll", billTypeStringAll);
		mv.addObject("billTypeStrSelect", billTypeStringSelect);

		String stateValus = "";
		FundsConfirmInfoState[] enumsState = FundsConfirmInfoState.values();  
    	if(enumsState!=null){
            for (int i = 0; i < enumsState.length; i++) {  
    			if (stateValus != null && !stateValus.toString().trim().equals("")) {
    				stateValus += ";";
    			}
    			stateValus += enumsState[i].getNameKey() + ":" + enumsState[i].getNameValue();
            }  
    	}
		String stateStringAll = ":[All];" + stateValus;
		String stateStringSelect = ":;" + stateValus;
		mv.addObject("stateStrAll", stateStringAll);
		mv.addObject("stateStrSelect", stateStringSelect);

		mv.addObject("pd", getPd);
		return mv;
	}

	/**账套列表
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
		
		String strCondition = " and PARENT_CODE = 'PZTYPE' ";
		strCondition += " and DICT_CODE in (select TYPE_CODE from tb_sys_bill_off_mapping where BILL_OFF = '" + SelectedCustCol7 +"') ";
		List<Dictionaries> list = dictionariesService.findByCondition(strCondition);
		
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
		
		String strCondition = " and DEPARTMENT_CODE in (select DEPT_CODE from tb_sys_dept_mapping where BILL_OFF = '" + SelectedCustCol7 +"' and TYPE_CODE = '" + SelectedTypeCode +"' ";
		if(!Jurisdiction.getCurrentDepartmentID().equals(DictsUtil.DepartShowAll_01001)
				&& !Jurisdiction.getCurrentDepartmentID().equals(DictsUtil.DepartShowAll_00)){
			strCondition += "                               and DEPT_CODE = '" + Jurisdiction.getCurrentDepartmentID() +"' ";
		}
		strCondition += "                               ) ";
		List<Department> list = departmentService.findByCondition(strCondition);
		
		String returnString = SelectBillCodeOptions.getSelectDeptOptions(list, SelectedDepartCodeFirstShow);
		commonBase.setMessage(returnString);
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
		
		//List<PageData> varList = new ArrayList<PageData>();
		List<PageData> list = fundsconfirminfoService.JqPage(page);	//列出Betting列表
		if(list !=null){
			for(PageData pdEach : list){
				//已确认，对应条件tb_sys_confirm_info有已确认信息
				String strSTATE = pdEach.getString("STATE");
				if(strSTATE!=null && strSTATE.equals("1")){
					//已汇总
					String strSUMMY_BILL = pdEach.getString("SUMMY_BILL");
					if(strSUMMY_BILL!=null && !strSUMMY_BILL.trim().equals("")){
						pdEach.put("STATE", FundsConfirmInfoState.Summy.getNameKey());
						//已传输
						String strSEALED_INFO = pdEach.getString("SEALED_INFO");
						if(strSEALED_INFO!=null && !strSEALED_INFO.trim().equals("")){
							pdEach.put("STATE", FundsConfirmInfoState.Transfer.getNameKey());
						}
					}
				}
			}
		}
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(list);
		result.setRowNum(page.getRowNum());
		result.setRecords(list.size());
		result.setPage(page.getPage());
		
		return result;
	}
	
	private PageData setTransferPd(PageData getPd) throws Exception{
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		getPd.put("filterSelectedBusiDate", SelectedBusiDate);
		
		//List<CertParmConfig> listCertParmConfig = getSelfCertParmConfig(SelectedTypeCode, SelectedCustCol7, SelectedBusiDate, DeptCodeSumGroupField);
		//String strCheckOrNot = "";
		//String getBillType = "";
		//if(listCertParmConfig!=null && listCertParmConfig.size()>0){
		//	getBillType = listCertParmConfig.get(0).getCUST_PARM1();
		//	strCheckOrNot = listCertParmConfig.get(0).getCUST_PARM1_DESC();
		//}
		
		/*if(SysConfirmInfoBillTypeStart.Start.getNameKey().equals(strCheckOrNot)
				&& getBillType!=null && !getBillType.trim().equals("")){
			String[] listBillType = getBillType.replace(" ", "").replace("'", "").replace("‘", "").replace("，", ",").split(",");
			String BillTypeLeftJoin = "";
        	if(listBillType!=null){
        		for(String strBillType : listBillType){
        			if(strBillType!=null && !strBillType.trim().equals("")){
        				if(BillTypeLeftJoin!=null && !BillTypeLeftJoin.trim().equals("")){
        					BillTypeLeftJoin += " UNION ";
        				}
    					BillTypeLeftJoin += " SELECT DISTINCT '" + strBillType + "' BILL_TYPE FROM tb_sys_dept_mapping ";
        			}
        		}
        	}
			if(BillTypeLeftJoin!=null && !BillTypeLeftJoin.trim().equals("")){
				getPd.put("BillTypeLeftJoin", BillTypeLeftJoin);
			}
		}*/
		
		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		getQueryFeildPd.put("TYPE_CODE", SelectedTypeCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedBusiDate!=null && !SelectedBusiDate.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(SelectedTypeCode!=null && !SelectedTypeCode.trim().equals(""))){
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
	
	/*private List<CertParmConfig> getSelfCertParmConfig(String typeCode, String billOff, String busiDate, String deptCode) throws Exception{
		CertParmConfig certParmConfig = new CertParmConfig();
		certParmConfig.setTYPE_CODE(typeCode);
		certParmConfig.setBILL_OFF(billOff);
		certParmConfig.setBUSI_DATE(busiDate);
		certParmConfig.setDEPT_CODE(deptCode);
		List<CertParmConfig> getCertParmConfigList = certParmConfigService.getSelfCertParmConfig(certParmConfig);
		return getCertParmConfigList;
	}*/
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}