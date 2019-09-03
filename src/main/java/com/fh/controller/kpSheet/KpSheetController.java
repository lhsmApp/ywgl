package com.fh.controller.kpSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.controller.common.DictsUtil;
import com.fh.entity.JqPage;
import com.fh.entity.KpSheet;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.service.kpSheet.KpSheetManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.ObjectExcelViewKp;
import com.fh.util.PageData;
import com.fh.util.base.MoneyUtils;

import net.sf.json.JSONArray;

/**
 * 
 * 
 * @ClassName: SalaryLaborCostFactSheetController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张晓柳
 * @date 
 *
 */
@Controller
@RequestMapping(value = "/kpSheet")
public class KpSheetController extends BaseController {

	String menuUrl = "kpSheet/list.do"; // 菜单地址(权限用)
	@Resource(name = "kpSheetService")
	private KpSheetManager kpSheetService;

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("BUSI_DATE", "CUST_COL7");
	
	/**
	 * 列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("kpSheet/kpSheet/kpSheet_list");

		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime);

		//BILL_OFF FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		
		mv.addObject("pd", getPd);
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = kpSheetService.getRptKpList(pd);
		if(varList!=null&&varList.size()>0){
			List<KpSheet> kpSheetList=this.getShowList(varList);

			String jsonString = JSON.toJSONString(kpSheetList); 
	        JSONArray array = JSONArray.fromObject(jsonString);  
	        List<PageData> resultList = (List<PageData>) JSONArray.toCollection(array,PageData.class);
			
			PageResult<PageData> result = new PageResult<PageData>();
			result.setRows(resultList);
			return result;
		}
		return null;
	}
	
	private List<KpSheet> getShowList(List<PageData> varList) throws Exception{
		
		List<KpSheet> setList=new ArrayList<KpSheet>();
		//Header
		KpSheet kpFkdw0=new KpSheet();
		kpFkdw0.setSqdw("申请单位（部门）");
		kpFkdw0.setCwc("财务处");
		kpFkdw0.setKpsy("开票事由");
		kpFkdw0.setLwglf("劳务管理费");
		
		//CUST_COL1
		KpSheet kpFkdw1=new KpSheet();
		kpFkdw1.setSqdw("付款单位");
		kpFkdw1.setCwc("中石油管道有限责任公司西气东输分公司");
		kpFkdw1.setKpsy("开票事由");
		kpFkdw1.setLwglf("劳务费");
		
		KpSheet kpKpje1=new KpSheet();
		String custCol1=varList.get(0).get("CUST_COL1").toString();
		String custCol1ZH=MoneyUtils.number2CNMontray(custCol1);
		kpKpje1.setSqdw("开票金额（大写）");
		kpKpje1.setCwc(custCol1ZH);
		kpKpje1.setKpsy("开票金额");
		kpKpje1.setLwglf(custCol1);
		
		//CUST_COL2
		KpSheet kpFkdw2=new KpSheet();
		kpFkdw2.setSqdw("付款单位");
		kpFkdw2.setCwc("中石油管道有限责任公司西气东输分公司");
		kpFkdw2.setKpsy("开票事由");
		kpFkdw2.setLwglf("劳务费");
		
		KpSheet kpKpje2=new KpSheet();
		String custCol2=varList.get(0).get("CUST_COL2").toString();
		String custCol2ZH=MoneyUtils.number2CNMontray(custCol2);
		kpKpje2.setSqdw("开票金额（大写）");
		kpKpje2.setCwc(custCol2ZH);
		kpKpje2.setKpsy("开票金额");
		kpKpje2.setLwglf(custCol2);
		
		//CUST_COL3
		KpSheet kpFkdw3=new KpSheet();
		kpFkdw3.setSqdw("付款单位");
		kpFkdw3.setCwc("中石油管道有限责任公司西气东输分公司");
		kpFkdw3.setKpsy("开票事由");
		kpFkdw3.setLwglf("劳务费");
		
		KpSheet kpKpje3=new KpSheet();
		String custCol3=varList.get(0).get("CUST_COL3").toString();
		String custCol3ZH=MoneyUtils.number2CNMontray(custCol3);
		kpKpje3.setSqdw("开票金额（大写）");
		kpKpje3.setCwc(custCol3ZH);
		kpKpje3.setKpsy("开票金额");
		kpKpje3.setLwglf(custCol3);
		
		//CUST_COL4
		KpSheet kpFkdw4=new KpSheet();
		kpFkdw4.setSqdw("付款单位");
		kpFkdw4.setCwc("中石油管道有限责任公司西气东输分公司");
		kpFkdw4.setKpsy("开票事由");
		kpFkdw4.setLwglf("代缴公积金");
		
		KpSheet kpKpje4=new KpSheet();
		String custCol4=varList.get(0).get("CUST_COL4").toString();
		String custCol4ZH=MoneyUtils.number2CNMontray(custCol4);
		kpKpje4.setSqdw("开票金额（大写）");
		kpKpje4.setCwc(custCol4ZH);
		kpKpje4.setKpsy("开票金额");
		kpKpje4.setLwglf(custCol4);
		
		//CUST_COL5
		KpSheet kpFkdw5=new KpSheet();
		kpFkdw5.setSqdw("付款单位");
		kpFkdw5.setCwc("中石油管道有限责任公司西气东输分公司");
		kpFkdw5.setKpsy("开票事由");
		kpFkdw5.setLwglf("代缴社保");
		
		KpSheet kpKpje5=new KpSheet();
		String custCol5=varList.get(0).get("CUST_COL5").toString();
		String custCol5ZH=MoneyUtils.number2CNMontray(custCol5);
		kpKpje5.setSqdw("开票金额（大写）");
		kpKpje5.setCwc(custCol5ZH);
		kpKpje5.setKpsy("开票金额");
		kpKpje5.setLwglf(custCol5);
		
		setList.add(kpFkdw0);
		setList.add(kpFkdw1);
		setList.add(kpKpje1);
		setList.add(kpFkdw2);
		setList.add(kpKpje2);
		setList.add(kpFkdw3);
		setList.add(kpKpje3);
		setList.add(kpFkdw4);
		setList.add(kpKpje4);
		setList.add(kpFkdw5);
		setList.add(kpKpje5);
		return setList;
	}
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		PageData pd = new PageData();
		
		pd = this.getPageData();
		List<PageData> varList = kpSheetService.getRptKpList(pd);
		List<PageData> resultList=new ArrayList<>();
		if(varList!=null&&varList.size()>0){
			List<KpSheet> kpSheetList=this.getShowList(varList);
			for(int i=1;i<kpSheetList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", kpSheetList.get(i).getSqdw());	    //1
				vpd.put("var2", kpSheetList.get(i).getCwc());	    //2
				vpd.put("var3", kpSheetList.get(i).getKpsy());	    //3
				vpd.put("var4", kpSheetList.get(i).getLwglf());	    //4
				resultList.add(vpd);
			}
		}
		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("申请单位（部门）");	//1
		titles.add("财务处");	//2
		titles.add("开票事由");	//3
		titles.add("劳务管理费");	//4
		dataMap.put("titles", titles);
		dataMap.put("filename", "中国石油天然气股份有限公司西气东输管道分公司-开票申请单");
		dataMap.put("varList", resultList);
		//当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(pd);
		dataMap.put("BUSI_DATE", SystemDateTime);
		ObjectExcelViewKp erv = new ObjectExcelViewKp();
		mv = new ModelAndView(erv,dataMap); 
		return mv;
	}
	
}
