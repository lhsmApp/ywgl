package com.fh.controller.changeerpzhzx.changeerpzhzx;

import java.io.PrintWriter;
import java.net.URLDecoder;
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

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;

import net.sf.json.JSONArray;

import com.fh.service.changeerpzhzx.changeerpzhzx.ChangeErpZhzxManager;
import com.fh.service.fhoa.department.DepartmentManager;

/** 
 * 说明：changeErpZhzx
 * 创建人：jiachao
 * 创建时间：2019-09-29
 */
@Controller
@RequestMapping(value="/changeerpzhzx")
public class ChangeErpZhzxController extends BaseController {
	
	String menuUrl = "changeerpzhzx/list.do"; //菜单地址(权限用)
	@Resource(name="changeerpzhzxService")
	private ChangeErpZhzxManager changeerpzhzxService;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ChangeErpZhzx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CHANGEERPZHZX_ID", this.get32UUID());	//主键
		changeerpzhzxService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除ChangeErpZhzx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		changeerpzhzxService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ChangeErpZhzx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		changeerpzhzxService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ChangeErpZhzx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = changeerpzhzxService.list(page);	//列出ChangeErpZhzx列表
		mv.setViewName("changeerpzhzx/changeerpzhzx/changeerpzhzx_list");
		//mv.addObject("varList", varList);
		mv.addObject("varList", JSON.toJSONString(varList));
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		mv.setViewName("changegrcxtbg/changeerpzhzx/changeerpzhzx_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
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
		pd = changeerpzhzxService.findById(pd);	//根据ID读取
		mv.setViewName("changegrcxtbg/changeerpzhzx/changeerpzhzx_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}	
	 /**打印
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/goPrint")
		public ModelAndView goPrint()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = changeerpzhzxService.findById(pd);	//根据ID读取
			JSONArray json = JSONArray.fromObject(pd); 
			mv.setViewName("changeerpzhzx/changeerpzhzx/PrintReport");
			mv.addObject("report", "static/js/gridReport/grf/erpSystemChange.grf");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("jsonStr", json);
			return mv;
		}	
		/**列表
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value="/showPrint")
		@ResponseBody 
		public PageData showPrint() throws Exception{
			PageData pd1 = new PageData();
			pd1 = this.getPageData();
			String billCode=pd1.getString("BILL_CODE");
			pd1.put("BILL_CODE", URLDecoder.decode(billCode, "UTF-8"));
			pd1 = changeerpzhzxService.findById(pd1);	//根据ID读取
			return pd1;
		}
		/**列表
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value="/showDetail")
		@ResponseBody 
		public PageData showDetail() throws Exception{
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = changeerpzhzxService.findById(pd);	//根据ID读取
			return pd;
		}
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ChangeErpZhzx");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			changeerpzhzxService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ChangeErpZhzx到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		titles.add("备注17");	//17
		titles.add("备注18");	//18
		titles.add("备注19");	//19
		titles.add("备注20");	//20
		titles.add("备注21");	//21
		dataMap.put("titles", titles);
		List<PageData> varOList = changeerpzhzxService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("BILL_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("UNIT_CODE"));	    //2
			vpd.put("var3", varOList.get(i).getString("DEPT_CODE"));	    //3
			vpd.put("var4", varOList.get(i).getString("ENTRY_DATE"));	    //4
			vpd.put("var5", varOList.get(i).getString("SERIAL_NUM"));	    //5
			vpd.put("var6", varOList.get(i).getString("USER_CODE"));	    //6
			vpd.put("var7", varOList.get(i).getString("USER_DEPT"));	    //7
			vpd.put("var8", varOList.get(i).getString("USER_JOB"));	    //8
			vpd.put("var9", varOList.get(i).getString("USER_CONTACT"));	    //9
			vpd.put("var10", varOList.get(i).getString("EFFECTIVE_DATE"));	    //10
			vpd.put("var11", varOList.get(i).getString("SYSTEM"));	    //11
			vpd.put("var12", varOList.get(i).getString("ACCOUNT_REMOVE"));	    //12
			vpd.put("var13", varOList.get(i).getString("ACCOUNT_REASON"));	    //13
			vpd.put("var14", varOList.get(i).getString("BILL_STATE"));	    //14
			vpd.put("var15", varOList.get(i).getString("BILL_USER"));	    //15
			vpd.put("var16", varOList.get(i).getString("BILL_DATE"));	    //16
			vpd.put("var17", varOList.get(i).getString("CUS_COLUMN1"));	    //17
			vpd.put("var18", varOList.get(i).getString("CUS_COLUMN2"));	    //18
			vpd.put("var19", varOList.get(i).getString("CUS_COLUMN3"));	    //19
			vpd.put("var20", varOList.get(i).getString("CUS_COLUMN4"));	    //20
			vpd.put("var21", varOList.get(i).getString("CUS_COLUMN5"));	    //21
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
}
