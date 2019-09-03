package com.fh.controller.betting.betting;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.fh.controller.common.DictsUtil;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.service.betting.betting.BettingManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

import net.sf.json.JSONArray;

/**
 * 投注站管理
* @ClassName: BettingController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Controller
@RequestMapping(value="/betting")
public class BettingController extends BaseController {
	
	String menuUrl = "betting/list.do"; //菜单地址(权限用)
	@Resource(name="bettingService")
	private BettingManager bettingService;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Betting");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BETTING_ID", this.get32UUID());	//主键
		bettingService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Betting");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		bettingService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Betting");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		bettingService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Betting");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = bettingService.list(page);	//列出Betting列表
		mv.setViewName("betting/betting/betting_list");
		mv.addObject("varList", varList);
		
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("betting/betting/betting_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = bettingService.findById(pd);	//根据ID读取
		mv.setViewName("betting/betting/betting_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Betting");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			bettingService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Betting到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("营业执照注册号");	//1
		titles.add("业主姓名");	//2
		titles.add("手机号");	//3
		titles.add("办公电话");	//4
		titles.add("传真号");	//5
		titles.add("所属区域");	//6
		titles.add("投注站地址");	//7
		titles.add("投注站面积");	//8
		titles.add("简介");	//9
		titles.add("地理坐标");	//10
		titles.add("备注");	//11
		titles.add("编号");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = bettingService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("LICENSE_NO"));	    //1
			vpd.put("var2", varOList.get(i).getString("USER_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("MOBILE_TEL"));	    //3
			vpd.put("var4", varOList.get(i).getString("OFFICE_TEL"));	    //4
			vpd.put("var5", varOList.get(i).getString("FAX"));	    //5
			vpd.put("var6", varOList.get(i).getString("BELONG_AREA"));	    //6
			vpd.put("var7", varOList.get(i).getString("BETT_ADDR"));	    //7
			vpd.put("var8", varOList.get(i).getString("BETT_AREA"));	    //8
			vpd.put("var9", varOList.get(i).getString("BETT_INTR"));	    //9
			vpd.put("var10", varOList.get(i).getString("GEOG_COOR"));	    //10
			vpd.put("var11", varOList.get(i).getString("REMARK"));	    //11
			vpd.put("var12", varOList.get(i).getString("ID_CODE"));	    //12
			varList.add(vpd);
		}
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
	
	@RequestMapping(value="/mapQuery")
	public ModelAndView mapQuery()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"查询投注站地图");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
	    pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		
		PageData pdAll =new PageData();
		List<PageData>	varList = bettingService.listAll(pdAll);	//列出地图Betting列表
		List<PageData> searchList = bettingService.listAllByCondition(pd);	//搜索地图Betting列表
		mv.setViewName("betting/betting/bettingMap");
		mv.addObject("varList", varList);
		mv.addObject("searchList", searchList);
		JSONArray jsonArray=JSONArray.fromObject(searchList);
		mv.addObject("searchJson",jsonArray);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		return mv;
	}
	
	@RequestMapping(value="/getBettingByBelongarea")
	@ResponseBody
	public Object getBettingByBelongarea() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
	    PageData pd = this.getPageData();
		List<PageData> searchList = bettingService.listAllByCondition(pd);	//搜索地图Betting列表
		map.put("list", searchList);
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasIDCode")
	@ResponseBody
	public Object hasIDCode(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(bettingService.findByIDCode(pd)!= null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
