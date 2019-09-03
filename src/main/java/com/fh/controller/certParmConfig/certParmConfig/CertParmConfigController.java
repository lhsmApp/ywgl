package com.fh.controller.certParmConfig.certParmConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.enums.SysConfirmInfoBillType;
import com.fh.util.enums.SysConfirmInfoBillTypeStart;
import com.fh.util.Jurisdiction;

import net.sf.json.JSONArray;

import com.fh.service.certParmConfig.certParmConfig.CertParmConfigManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;

/** 
 * 说明： 
 * 创建人：zhangxiaoliu
 * 创建时间：2018-05-14
 * @version
 */
@Controller
@RequestMapping(value="/certParmConfig")
public class CertParmConfigController extends BaseController {
	
	String menuUrl = "certParmConfig/list.do"; //菜单地址(权限用)
	@Resource(name="certParmConfigService")
	private CertParmConfigManager certParmConfigService;

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("TYPE_CODE", "BILL_OFF", "BUSI_DATE");
    //设置必定不用编辑的列            SERIAL_NO 设置字段类型是数字，但不管隐藏 或显示都必须保存的
    List<String> MustNotEditList = Arrays.asList("BUSI_DATE", "DEPT_CODE");

    //获取汇总条件传的责任中心
    String DeptCodeSumGroupField = "01";
    
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表CertParmConfig");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("certParmConfig/certParmConfig/certParmConfig_list");
		PageData getPd = this.getPageData();

		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime);
		
		//CUST_PARM1 确认类型
		String custParma1Valus = "";
		SysConfirmInfoBillType[] enums = SysConfirmInfoBillType.values();  
    	if(enums!=null){
            for (int i = 0; i < enums.length; i++) {  
    			if (custParma1Valus != null && !custParma1Valus.toString().trim().equals("")) {
    				custParma1Valus += ";";
    			}
    			custParma1Valus += enums[i].getNameKey() + ":" + enums[i].getNameValue();
            }  
    	}
		String custParma1StringAll = ":[All];" + custParma1Valus;
		String custParma1StringSelect = ":;" + custParma1Valus;
		mv.addObject("custParma1StrAll", custParma1StringAll);
		mv.addObject("custParma1StrSelect", custParma1StringSelect);
		
		
		
		
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

		//CUST_PARM1_DESC 启动确认类型
		String custParma1DescValus = "";
		SysConfirmInfoBillTypeStart[] enumsBillTypeStart = SysConfirmInfoBillTypeStart.values();  
    	if(enumsBillTypeStart!=null){
            for (int i = 0; i < enumsBillTypeStart.length; i++) {  
    			if (custParma1DescValus != null && !custParma1DescValus.toString().trim().equals("")) {
    				custParma1DescValus += ";";
    			}
    			custParma1DescValus += enumsBillTypeStart[i].getNameKey() + ":" + enumsBillTypeStart[i].getNameValue();
            }  
    	}
		String custParma1DescStringAll = ":[All];" + custParma1DescValus;
		String custParma1DescStringSelect = ":;" + custParma1DescValus;
		mv.addObject("custParma1DescStrAll", custParma1DescStringAll);
		mv.addObject("custParma1DescStrSelect", custParma1DescStringSelect);
		
		mv.addObject("pd", getPd);
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表");

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
		List<PageData> varList = certParmConfigService.JqPage(page);	//列出Betting列表
		int records = certParmConfigService.countJqGridExtend(page);
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		
		return result;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public @ResponseBody CommonBase save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增CertParmConfig");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData pd = this.getPageData();
		//账套
		//String SelectedCustCol7 = pd.getString("SelectedCustCol7");
		//凭证字典
		//String SelectedTypeCode = pd.getString("SelectedTypeCode");
		//业务区间
		String SelectedBusiDate = pd.getString("SelectedBusiDate");
		//操作
		String oper = pd.getString("oper");

		if(oper.equals("add")){
			pd.put("BUSI_DATE", SelectedBusiDate);
			pd.put("DEPT_CODE", DeptCodeSumGroupField);
		} else {
			for(String strFeild : MustNotEditList){
				pd.put(strFeild, pd.get(strFeild + TmplUtil.keyExtra));
			}
		}
		List<PageData> listData = new ArrayList<PageData>();
		listData.add(pd);
		String checkState = CheckState(listData);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		certParmConfigService.batchUpdateDatabase(listData);
		commonBase.setCode(0);
		return commonBase;
	}

	/**
	 * 批量修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAll")
	public @ResponseBody CommonBase updateAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
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
			certParmConfigService.batchUpdateDatabase(listData);
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
    	    certParmConfigService.deleteAll(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	private String CheckState(List<PageData> listData) throws Exception{
		String strRet = "";
		List<PageData> repeatList = certParmConfigService.getRepeatList(listData);
		if(repeatList!=null && repeatList.size()>0){
			strRet = Message.HaveRepeatRecord;
		}
		return strRet;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
