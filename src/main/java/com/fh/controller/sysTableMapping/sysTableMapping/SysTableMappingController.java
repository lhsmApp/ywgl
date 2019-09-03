package com.fh.controller.sysTableMapping.sysTableMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.SysTableMapping;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.sysTableMapping.sysTableMapping.SysTableMappingManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
//import com.fh.util.enums.SysTableMapTableType;

import net.sf.json.JSONArray;

/**
 * 数据模板详情
 * 
 * @ClassName: SysTableMappingController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张晓柳
 * @date 2018年5月16日
 *
 */
@Controller
@RequestMapping(value = "/sysTableMapping")
public class SysTableMappingController extends BaseController {

	String menuUrl = "sysTableMapping/list.do"; // 菜单地址(权限用)
	@Resource(name = "sysTableMappingService")
	private SysTableMappingManager sysTableMappingService;

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;

	//TABLE_NAME_MAPPING
	String TABLE_NAME_MAPPING = "tb_gen_bus_detail";
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("TYPE_CODE", "BILL_OFF", "BUSI_DATE");
    //设置必定不用编辑的列            SERIAL_NO 设置字段类型是数字，但不管隐藏 或显示都必须保存的
    List<String> MustNotEditList = Arrays.asList("BUSI_DATE", "TABLE_NAME_MAPPING");
	
	/**
	 * 列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("sysTableMapping/sysTableMapping/sysTableMapping_list");

		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime);

		//TYPE_CODE PZTYPE 凭证字典
		mv.addObject("PZTYPE", DictsUtil.getDictsByParentCode(dictionariesService, "PZTYPE"));
		String typeCodeValus = DictsUtil.getDicValue(dictionariesService, "PZTYPE");
		String typeCodeStringAll = ":[All];" + typeCodeValus;
		String typeCodeStringSelect = ":;" + typeCodeValus;
		mv.addObject("typeCodeStrAll", typeCodeStringAll);
		mv.addObject("typeCodeStrSelect", typeCodeStringSelect);
		//BILL_OFF FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);
		
		/*//TABLE_TYPE 业务表类型
		String tableTypeValus = "";
		SysTableMapTableType[] enums = SysTableMapTableType.values();  
    	if(enums!=null){
            for (int i = 0; i < enums.length; i++) {  
    			if (tableTypeValus != null && !tableTypeValus.toString().trim().equals("")) {
    				tableTypeValus += ";";
    			}
    			tableTypeValus += enums[i].getNameKey() + ":" + enums[i].getNameValue();
            }  
    	}
		String tableTypeStringAll = ":[All];" + tableTypeValus;
		String tableTypeStringSelect = ":;" + tableTypeValus;
		mv.addObject("tableTypeStrAll", tableTypeStringAll);
		mv.addObject("tableTypeStrSelect", tableTypeStringSelect);*/
		
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
		logBefore(logger, Jurisdiction.getUsername()+"列表SysTableMapping");

		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");

		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("TYPE_CODE", SelectedTypeCode);
		getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeild = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
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
		List<PageData> varList = sysTableMappingService.JqPage(page);	//列出Betting列表
		int records = sysTableMappingService.countJqGridExtend(page);
		
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
		logBefore(logger, Jurisdiction.getUsername()+"修改SysTableMapping");

		PageData getPd = this.getPageData();
		//账套
		//String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//String ShowDataCustCol7 = getPd.getString("ShowDataCustCol7");
		//凭证字典
		//String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//String ShowDataTypeCode = getPd.getString("ShowDataTypeCode");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//String ShowDataBusiDate = getPd.getString("ShowDataBusiDate");
		//操作
		String oper = getPd.getString("oper");

		if(oper.equals("add")){
			getPd.put("BUSI_DATE", SelectedBusiDate);
			getPd.put("TABLE_NAME_MAPPING", TABLE_NAME_MAPPING);
		} else {
			for(String strFeild : MustNotEditList){
				getPd.put(strFeild, getPd.get(strFeild + TmplUtil.keyExtra));
			}
		}
		
		List<PageData> listData = new ArrayList<PageData>();
		listData.add(getPd);
		String checkState = CheckState(listData);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		sysTableMappingService.batchUpdateDatabase(listData);
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
			}
	        
			String checkState = CheckState(listData);
			if(checkState!=null && !checkState.trim().equals("")){
				commonBase.setCode(2);
				commonBase.setMessage(checkState);
				return commonBase;
			}
		    sysTableMappingService.batchUpdateDatabase(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	private String CheckState(List<PageData> listData) throws Exception{
		String strRet = "";
		List<SysTableMapping> repeatList = sysTableMappingService.getRepeatList(listData);
		if(repeatList!=null && repeatList.size()>0){
			strRet = Message.HaveRepeatRecord;
		}
		return strRet;
	}
	
}
