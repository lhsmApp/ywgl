package com.fh.controller.fundsconfirminfoquery.fundsconfirminfoquery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.fh.controller.common.Common;
import com.fh.controller.common.Corresponding;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.QueryFeildString;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Jurisdiction;
import com.fh.util.enums.FundsConfirmInfoState;
import com.fh.util.enums.SysConfirmInfoBillType;
import com.fh.util.enums.TmplType;

import com.fh.service.fundsconfirminfoquery.fundsconfirminfoquery.FundsConfirmInfoQueryManager;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;

/** 
 * 说明： 查询
 * 创建人：张晓柳
 * 创建时间：2017-08-09
 * @version
 */
@Controller
@RequestMapping(value="/fundsconfirminfoquery")
public class FundsConfirmInfoQueryController extends BaseController {
	
	String menuUrl = "fundsconfirminfoquery/list.do"; //菜单地址(权限用)
	@Resource(name="fundsconfirminfoqueryService")
	private FundsConfirmInfoQueryManager fundsconfirminfoqueryService;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name="sysconfigService")
	private SysConfigManager sysConfigManager;

	//默认的which值
	String DefaultWhile = TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey();

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FundsConfirmInfoQuery");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fundsconfirminfoquery/fundsconfirminfoquery/fundsconfirminfoquery_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime);
		//while
		getPd.put("which", SelectedTableNo);

		//"USER_GROP", "CUST_COL7"
		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		// *********************加载单位树  DEPT_CODE*******************************
		String DepartmentSelectTreeSource=DictsUtil.getDepartmentSelectTreeSource(departmentService);
		if(DepartmentSelectTreeSource.equals("0")){
			getPd.put("departTreeSource", DepartmentSelectTreeSource);
		} else {
			getPd.put("departTreeSource", 1);
		}
		mv.addObject("zTreeNodes", DepartmentSelectTreeSource);
		// ***********************************************************

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
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表FinanceAccounts");

		PageData getPd = this.getPageData();
		//员工组
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		//tab
		String SelectedTabType = getPd.getString("SelectedTabType");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		int departSelf = Common.getDepartSelf(departmentService);
		if(departSelf == 1){
			SelectedDepartCode = Jurisdiction.getCurrentDepartmentID();
		}
		List<String> AllDeptCode = Common.getAllDeptCode(departmentService, Jurisdiction.getCurrentDepartmentID());

		String BILL_TYPE = Corresponding.getSysConfirmInfoBillTypeFromTmplType(SelectedTableNo);
		getPd.put("BILL_TYPE", BILL_TYPE);
		getPd.put("RPT_DUR", SelectedBusiDate);
		getPd.put("BUSI_DATE", SelectedBusiDate);
		getPd.put("BILL_OFF", SelectedCustCol7);

		String QueryFeild = " and DEPARTMENT_CODE in (" + QueryFeildString.tranferListValueToSqlInString(AllDeptCode) + ") ";
		if(SelectedDepartCode!=null && !SelectedDepartCode.trim().equals("")){
			QueryFeild += " and DEPARTMENT_CODE in (" + QueryFeildString.getSqlInString(SelectedDepartCode) + ") ";
		}
		if(!(SelectedCustCol7!=null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		page.setPd(getPd);
		List<PageData> listAll = fundsconfirminfoqueryService.JqPage(page);	//列出Betting列表
		List<PageData> varList = new ArrayList<PageData>();	//列出Betting列表
		if(listAll!=null){
			for(PageData pdAdd : listAll){
			    //NAME, DEPARTMENT_CODE, PARENT_CODE, 
			    //IFNULL(gen_bus_summy_bill.TYPE_CODE, ' ') TYPE_CODE,
			    //IFNULL(sys_confirm_info.STATE, ' ') STATE,
			    //IFNULL(gen_bus_summy_bill.STATE, ' ') SUMMY_BILL,
	            //IFNULL(sys_sealed_info.STATE, ' ') SEALED_INFO
				pdAdd.put("BILL_OFF", SelectedCustCol7);
				pdAdd.put("BUSI_DATE", SelectedBusiDate);
				pdAdd.put("BILL_TYPE", BILL_TYPE);
				
				if(SelectedTabType!=null && SelectedTabType.trim().equals("1")){//未确认
					//已确认
					String strSTATE = pdAdd.getString("STATE");
					if(!(strSTATE!=null && strSTATE.equals("1"))){
						varList.add(pdAdd);
					}
				} else if(SelectedTabType!=null && SelectedTabType.trim().equals("2")){//已确认
					//已确认
					String strSTATE = pdAdd.getString("STATE");
					if(strSTATE!=null && strSTATE.equals("1")){
						//已汇总
						String strSUMMY_BILL = pdAdd.getString("SUMMY_BILL");
						if(strSUMMY_BILL!=null && !strSUMMY_BILL.trim().equals("")){
							pdAdd.put("STATE", FundsConfirmInfoState.Summy.getNameKey());
							//已传输
							String strSEALED_INFO = pdAdd.getString("SEALED_INFO");
							if(strSEALED_INFO!=null && !strSEALED_INFO.trim().equals("")){
								pdAdd.put("STATE", FundsConfirmInfoState.Transfer.getNameKey());
							}
						}
						varList.add(pdAdd);
					}
				}
			}
		}
		int records = 0;
		if(varList!=null){
			records = varList.size();
		}
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		
		return result;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
