package com.fh.controller.salaryLaborCostFactSheet.salaryLaborCostFactSheet;

import java.math.BigDecimal;
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
import com.fh.controller.common.AddCostFactSheetItem;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.QueryFeildString;
import com.fh.entity.ClsCostFactSheet;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.salaryLaborCostFactSheet.salaryLaborCostFactSheet.SalaryLaborCostFactSheetManager;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelSalaryLaborCostFactSheet;
import com.fh.util.PageData;
import com.fh.util.enums.BindingType;

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
@RequestMapping(value = "/salaryLaborCostFactSheet")
public class SalaryLaborCostFactSheetController extends BaseController {

	String menuUrl = "salaryLaborCostFactSheet/list.do"; // 菜单地址(权限用)
	@Resource(name = "salaryLaborCostFactSheetService")
	private SalaryLaborCostFactSheetManager salaryLaborCostFactSheetService;

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
		mv.setViewName("salaryLaborCostFactSheet/salaryLaborCostFactSheet/salaryLaborCostFactSheet_list");

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
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"列表SalaryLaborCostFactSheet");

		PageData getPd = this.getPageData();
		
		List<ClsCostFactSheet> setList = getShowList(getPd, page, false);
		String jsonString = JSON.toJSONString(setList); 
        JSONArray array = JSONArray.fromObject(jsonString);  
        List<PageData> varList = (List<PageData>) JSONArray.toCollection(array,PageData.class);
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setPage(page.getPage());
		
		return result;
	}
	
	private List<ClsCostFactSheet> getShowList(PageData getPd, JqPage page, Boolean bolIfExport) throws Exception{
		//账套
		//String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");

		PageData getQueryFeildPd = new PageData();
		getQueryFeildPd.put("BUSI_DATE", SelectedBusiDate);
		String QueryFeildTotal = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedBusiDate != null && !SelectedBusiDate.trim().equals(""))){
			QueryFeildTotal += " and 1 != 1 ";
		}
		getPd.put("QueryFeild", QueryFeildTotal);
		page.setPd(getPd);
		List<PageData> getTotalList = salaryLaborCostFactSheetService.getRptTotalList(page);	//列出Betting列表
		
		//getQueryFeildPd.put("BILL_OFF", SelectedCustCol7);
		String QueryFeildDetail = QueryFeildString.getQueryFeild(getQueryFeildPd, QueryFeildList);
		if(!(SelectedBusiDate != null && !SelectedBusiDate.trim().equals(""))){
			QueryFeildDetail += " and 1 != 1 ";
		}
		//if(!(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals(""))){
		//	QueryFeildDetail += " and 1 != 1 ";
		//}
		getPd.put("QueryFeild", QueryFeildDetail);
		page.setPd(getPd);
		List<PageData> getDetailList = salaryLaborCostFactSheetService.getRptDetailList(page);	//列出Betting列表
		
		List<ClsCostFactSheet> setList = new ArrayList<ClsCostFactSheet>();
		AddCostFactSheetItem.initStructure(setList, bolIfExport);
		
		for(ClsCostFactSheet cost : setList){
			if(cost.getOrder()!=null && !cost.getOrder().trim().equals("")){
				for(PageData pdTotal : getTotalList){
					if(cost.getOrder().equals(pdTotal.getString("dipsorder"))){
						if(cost.getOrder().equals(BindingType.Total0.getNameKey())){
							String str06 = pdTotal.get("calgz").toString();
							String str07 = pdTotal.get("HOUSE_ALLE").toString();
							String str08 = pdTotal.get("TRF_ALLE").toString();
							String str09 = pdTotal.get("TEL_EXPE").toString();
							String str10 = pdTotal.get("HLDY_ALLE").toString();
							String str11 = pdTotal.get("MEAL_EXPE").toString();
							String str12 = pdTotal.get("ITEM_ALLE").toString();
							String str13 = pdTotal.get("Ins").toString();
							String str14 = pdTotal.get("KID_ALLE").toString();
							String str15 = pdTotal.get("COOL_EXPE").toString();
							String str16 = "0.00";
							String str17 = pdTotal.get("EXT_SGL_AWAD").toString();
							
							BigDecimal dec05 = new BigDecimal(0);
							dec05 = dec05.add(new BigDecimal(str06)).add(new BigDecimal(str07)).add(new BigDecimal(str08))
									.add(new BigDecimal(str09)).add(new BigDecimal(str10)).add(new BigDecimal(str11))
									.add(new BigDecimal(str12)).add(new BigDecimal(str13)).add(new BigDecimal(str14))
									.add(new BigDecimal(str15)).add(new BigDecimal(str16)).add(new BigDecimal(str17));
							
							cost.setName05(dec05.toString());//总额合计
							cost.setName06(str06);//工资
							cost.setName07(str07);//无房补贴
							cost.setName08(str08);//交通补贴
							cost.setName09(str09);//通讯补贴
							cost.setName10(str10);//节日补贴
							cost.setName11(str11);//误餐补贴
							cost.setName12(str12);//项目补贴
							cost.setName13(str13);//
							cost.setName14(str14);//儿贴
							cost.setName15(str15);//防暑降温费
							cost.setName16(str16);//疗养费
							cost.setName17(str17);//总额外单项奖
							//cost.setName18("");//单独制表
							//cost.setName19("");//期末人数
						}
						if(cost.getOrder().equals(BindingType.Total44.getNameKey())){
							String str06 = pdTotal.get("calgz").toString();
							String str07 = pdTotal.get("HOUSE_ALLE").toString();
							String str08 = pdTotal.get("TRF_ALLE").toString();
							String str09 = pdTotal.get("TEL_EXPE").toString();
							String str10 = pdTotal.get("HLDY_ALLE").toString();
							String str11 = pdTotal.get("MEAL_EXPE").toString();
							String str12 = pdTotal.get("ITEM_ALLE").toString();
							String str13 = pdTotal.get("Ins").toString();
							String str14 = pdTotal.get("AFTER_TAX").toString();
							String str15 = pdTotal.get("CUST_COL13").toString();
							String str16 = pdTotal.get("CUST_COL17").toString();
							String str17 = pdTotal.get("CUST_COL14").toString();
							
							BigDecimal dec05 = new BigDecimal(0);
							dec05 = dec05.add(new BigDecimal(str06)).add(new BigDecimal(str07)).add(new BigDecimal(str08))
									.add(new BigDecimal(str09)).add(new BigDecimal(str10)).add(new BigDecimal(str11))
									.add(new BigDecimal(str12)).add(new BigDecimal(str13)).add(new BigDecimal(str14))
									.add(new BigDecimal(str15)).add(new BigDecimal(str16)).add(new BigDecimal(str17));
							
							cost.setName05(dec05.toString());//总额合计
							cost.setName06(str06);//工资
							cost.setName07(str07);//无房补贴
							cost.setName08(str08);//交通补贴
							cost.setName09(str09);//通讯补贴
							cost.setName10(str10);//节日补贴
							cost.setName11(str11);//误餐补贴
							cost.setName12(str12);//项目补贴
							cost.setName13(str13);//
							cost.setName14(str14);//
							cost.setName15(str15);//
							cost.setName16(str16);//
							cost.setName17(str17);//
							//cost.setName18("");//单独制表
							//cost.setName19("");//期末人数
						}
					}
				}
				for(PageData pdDetail : getDetailList){
					if(cost.getOrder().equals(pdDetail.getString("dipsorder"))){
						if(cost.getOrder().equals(BindingType.DetailSCHHTH.getNameKey())){
							String str06 = pdDetail.get("calgz").toString();
							String str07 = pdDetail.get("HOUSE_ALLE").toString();
							String str08 = pdDetail.get("TRF_ALLE").toString();
							String str09 = pdDetail.get("TEL_EXPE").toString();
							String str10 = pdDetail.get("HLDY_ALLE").toString();
							String str11 = pdDetail.get("MEAL_EXPE").toString();
							String str12 = pdDetail.get("ITEM_ALLE").toString();
							//String str13 = "0.00";
							String str14 = pdDetail.get("KID_ALLE").toString();
							String str15 = pdDetail.get("COOL_EXPE").toString();
							String str16 = "0.00";
							String str17 = pdDetail.get("EXT_SGL_AWAD").toString();
							
							BigDecimal dec05 = new BigDecimal(0);
							dec05 = dec05.add(new BigDecimal(str06)).add(new BigDecimal(str07)).add(new BigDecimal(str08))
									.add(new BigDecimal(str09)).add(new BigDecimal(str10)).add(new BigDecimal(str11))
									.add(new BigDecimal(str12)).add(new BigDecimal(str14))
									.add(new BigDecimal(str15)).add(new BigDecimal(str16)).add(new BigDecimal(str17));
							
							cost.setName05(dec05.toString());//总额合计
							cost.setName06(str06);//工资
							cost.setName07(str07);//无房补贴
							cost.setName08(str08);//交通补贴
							cost.setName09(str09);//通讯补贴
							cost.setName10(str10);//节日补贴
							cost.setName11(str11);//误餐补贴
							cost.setName12(str12);//项目补贴
							//cost.setName13("");//
							cost.setName14(str14);//儿贴
							cost.setName15(str15);//防暑降温费
							cost.setName16(str16);//疗养费
							cost.setName17(str17);//总额外单项奖
							//cost.setName18("");//单独制表
							//cost.setName19("");//期末人数
						}
						if(cost.getOrder().equals(BindingType.DetailXTNLW.getNameKey())){
							String str06 = pdDetail.get("calgz").toString();
							String str07 = pdDetail.get("HOUSE_ALLE").toString();
							String str08 = pdDetail.get("TRF_ALLE").toString();
							String str09 = pdDetail.get("TEL_EXPE").toString();
							String str10 = pdDetail.get("HLDY_ALLE").toString();
							String str11 = pdDetail.get("MEAL_EXPE").toString();
							String str12 = pdDetail.get("ITEM_ALLE").toString();
							//String str13 = "0.00";
							String str14 = pdDetail.get("KID_ALLE").toString();
							String str15 = pdDetail.get("COOL_EXPE").toString();
							String str16 = pdDetail.get("CUST_COL10").toString();
							String str17 = pdDetail.get("CUST_COL1").toString();
							
							BigDecimal dec05 = new BigDecimal(0);
							dec05 = dec05.add(new BigDecimal(str06)).add(new BigDecimal(str07)).add(new BigDecimal(str08))
									.add(new BigDecimal(str09)).add(new BigDecimal(str10)).add(new BigDecimal(str11))
									.add(new BigDecimal(str12)).add(new BigDecimal(str14))
									.add(new BigDecimal(str15)).add(new BigDecimal(str16)).add(new BigDecimal(str17));
							
							cost.setName05(dec05.toString());//总额合计
							cost.setName06(str06);//工资
							cost.setName07(str07);//无房补贴
							cost.setName08(str08);//交通补贴
							cost.setName09(str09);//通讯补贴
							cost.setName10(str10);//节日补贴
							cost.setName11(str11);//误餐补贴
							cost.setName12(str12);//项目补贴
							//cost.setName13("");//
							cost.setName14(str14);//儿贴
							cost.setName15(str15);//防暑降温费
							cost.setName16(str16);//管理费
							cost.setName17(str17);//可抵税费
							//cost.setName18("");//单独制表
							//cost.setName19("");//期末人数
						}
						if(cost.getOrder().equals(BindingType.DetailLWPQ.getNameKey())){
							String str06 = pdDetail.get("calgz").toString();
							String str07 = pdDetail.get("TRF_ALLE").toString();
							String str08 = pdDetail.get("TEL_EXPE").toString();
							String str09 = pdDetail.get("HLDY_ALLE").toString();
							String str10 = pdDetail.get("MEAL_EXPE").toString();
							String str11 = pdDetail.get("CUST_COL1").toString();
							String str12 = pdDetail.get("COOL_EXPE").toString();
							String str13 = pdDetail.get("Ins").toString();
							String str14 = pdDetail.get("AFTER_TAX").toString();
							String str15 = pdDetail.get("CUST_COL13").toString();
							String str16 = pdDetail.get("CUST_COL17").toString();
							String str17 = pdDetail.get("CUST_COL14").toString();
							
							BigDecimal dec05 = new BigDecimal(0);
							dec05 = dec05.add(new BigDecimal(str06)).add(new BigDecimal(str07)).add(new BigDecimal(str08))
									.add(new BigDecimal(str09)).add(new BigDecimal(str10)).add(new BigDecimal(str11))
									.add(new BigDecimal(str12)).add(new BigDecimal(str13)).add(new BigDecimal(str14))
									.add(new BigDecimal(str15)).add(new BigDecimal(str16)).add(new BigDecimal(str17));
							
							cost.setName05(dec05.toString());//总额合计
							cost.setName06(str06);//工资
							cost.setName07(str07);//交通补贴
							cost.setName08(str08);//通讯补贴
							cost.setName09(str09);//节日补贴
							cost.setName10(str10);//误餐补贴
							cost.setName11(str11);//劳保
							cost.setName12(str12);//防暑降温费
							cost.setName13(str13);//社保公积金
							cost.setName14(str14);//税后加项
							cost.setName15(str15);//工会经费
							cost.setName16(str16);//管理费
							cost.setName17(str17);//可抵税费
							//cost.setName18("");//单独制表
							//cost.setName19("");//期末人数
						}
					}
				}
			}
		}
		return setList;
	}
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SocialIncDetail到excel");

		PageData getPd = this.getPageData();
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");

		List<ClsCostFactSheet> setList = getShowList(getPd, page, true);
		String jsonString = JSON.toJSONString(setList); 
        JSONArray array = JSONArray.fromObject(jsonString);  
        List<PageData> varList = (List<PageData>) JSONArray.toCollection(array,PageData.class);

		ModelAndView mv = new ModelAndView();
		Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
		String fileName = SelectedBusiDate.substring(0,4) + "年" + SelectedBusiDate.substring(4, 6) + "月工资、劳务费情况表";
		dataMap.put("filename", fileName);
		
		dataMap.put("setList", varList);
		ObjectExcelSalaryLaborCostFactSheet erv = new ObjectExcelSalaryLaborCostFactSheet();
		mv = new ModelAndView(erv,dataMap); 
		return mv;
	}
	
}
