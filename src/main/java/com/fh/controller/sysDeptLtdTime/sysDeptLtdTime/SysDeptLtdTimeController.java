package com.fh.controller.sysDeptLtdTime.sysDeptLtdTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.Corresponding;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.SysDeptLtdTime;
import com.fh.entity.system.Dictionaries;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysDeptLtdTime.sysDeptLtdTime.SysDeptLtdTimeManager;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.StringUtil;
import com.fh.util.enums.TmplType;

import net.sf.json.JSONArray;

/**
 * 设置可操作的最后日期和时辰
 * 
 * @ClassName: SysDeptLtdTimeController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张晓柳
 * @date 2018年8月15日
 *
 */
@Controller
@RequestMapping(value = "/sysDeptLtdTime")
public class SysDeptLtdTimeController extends BaseController {

	String menuUrl = "sysDeptLtdTime/list.do"; // 菜单地址(权限用)
	@Resource(name = "sysDeptLtdTimeService")
	private SysDeptLtdTimeManager sysDeptLtdTimeService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;
	@Resource(name="dictionariesService")
	private DictionariesService dictionariesService;

	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BUSI_TYPE", "BILL_OFF", "DEPT_CODE");
    //设置必定不用编辑的列
    List<String> MustNotEditList = Arrays.asList("BUSI_TYPE", "BILL_OFF", "DEPT_CODE");

	//默认的which值
	String DefaultWhile = TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey();
	
	/**
	 * 列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		PageData getPd = this.getPageData();
		//业务类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		//while
		getPd.put("which", SelectedTableNo);
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("sysDeptLtdTime/sysDeptLtdTime/sysDeptLtdTime_list");

		//CUST_COL7 FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);
		// *********************加载单位树  DEPT_CODE*******************************
		List<PageData> treeSource = DictsUtil.getDepartmentSelectTreeSourceList(departmentService);
		if (treeSource != null && treeSource.size() > 0) {
			JSONArray arr = JSONArray.fromObject(treeSource);
			mv.addObject("zTreeNodes", null == arr ? "" : arr.toString());
		}
		// ***********************************************************
		String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);

		//增加批量每月日
		List<Dictionaries> dicDays=new ArrayList<Dictionaries>();
		
		String lidDayValus = "";
		for(int i=1;i<=31;i++){
			String strDay = String.valueOf(i);
			if(i<10){
				strDay = "0" + String.valueOf(i);
			}
			if (lidDayValus != null && !lidDayValus.toString().trim().equals("")) {
				lidDayValus += ";";
			}
			lidDayValus += strDay + ":" + " " + strDay + " ";
			//增加批量每月日
			dicDays.add(new Dictionaries(strDay,strDay));
		}
		String lidDayStringAll = ":[All];" + lidDayValus;
		String lidDayStringSelect = ":;" + lidDayValus;
		mv.addObject("lidDayStrAll", lidDayStringAll);
		mv.addObject("lidDayStrSelect", lidDayStringSelect);
		
		mv.addObject("Days", dicDays);
		
		mv.addObject("pd", getPd);
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"列表SysDeptLtdTime");

		PageData getPd = this.getPageData();
		//业务类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strBusiType = Corresponding.getTypeCodeDetailFromTmplType(SelectedTableNo);
		//单位
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");

		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("BUSI_TYPE", strBusiType);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		getQueryFeildPd.put("DEPT_CODE", SelectedDepartCode);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		if(!(strBusiType!=null && !strBusiType.trim().equals(""))){
			QueryFeild += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		page.setPd(getPd);
		List<PageData> varList = sysDeptLtdTimeService.JqPage(page);	//列出Betting列表
		int records = sysDeptLtdTimeService.countJqGridExtend(page);
		
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
		logBefore(logger, Jurisdiction.getUsername()+"修改SysDeptLtdTime");

		PageData getPd = this.getPageData();
		//操作
		String oper = getPd.getString("oper");
		
		if(oper.toUpperCase().equals(("edit").toUpperCase())){
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
			List<PageData> listData = new ArrayList<PageData>();
			listData.add(getPd);
			String checkState = CheckState(listData);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
			sysDeptLtdTimeService.batchUpdateDatabase(listData);
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
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);

		if(null != listData && listData.size() > 0){
        	for(PageData pd : listData){
        		for(String strFeild : MustNotEditList){
        			pd.put(strFeild, pd.get(strFeild + TmplUtil.keyExtra));
        		}
        	}
			String checkState = CheckState(listData);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
		    sysDeptLtdTimeService.batchUpdateDatabase(listData);
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
    	    sysDeptLtdTimeService.deleteAll(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	/**批量开启、关闭
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/batchUpdate")
	public @ResponseBody CommonBase batchUpdate() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		//业务类型
		String SelectedTableNo = Corresponding.getWhileValue(getPd.getString("SelectedTableNo"), DefaultWhile);
		String strBusiType = Corresponding.getTypeCodeDetailFromTmplType(SelectedTableNo);
		String operate=getPd.getString("Operate");
		String selectedCustCol7=getPd.getString("SelectedCustCol7");
		String selectedDepartCode=getPd.getString("SelectedDepartCode");
		String selectedDay=getPd.getString("SelectedDay");
		
		
		List<PageData> listData = new ArrayList<PageData>();
		if (StringUtil.isNotEmpty(selectedDepartCode)) {
			String[] strDeptCodes = selectedDepartCode.split(",");
			for(String deptCode : strDeptCodes){
				PageData item=new PageData();
				item.put("BUSI_TYPE__", strBusiType);
				item.put("BILL_OFF__", selectedCustCol7);
				item.put("DEPT_CODE__", deptCode);
				
				item.put("BUSI_TYPE", strBusiType);
				item.put("BILL_OFF", selectedCustCol7);
				item.put("DEPT_CODE", deptCode);
				item.put("LTD_DAY", selectedDay);
				item.put("STATE", operate);
				listData.add(item);
			}
		}
		sysDeptLtdTimeService.batchUpdateDatabase(listData);
		commonBase.setCode(0);
		return commonBase;
	}
	
	private String CheckState(List<PageData> listData) throws Exception{
		String strRet = "";
		List<SysDeptLtdTime> repeatList = sysDeptLtdTimeService.getRepeatList(listData);
		if(repeatList!=null && repeatList.size()>0){
			strRet = Message.HaveRepeatRecord;
		}
		return strRet;
	}
	
}
