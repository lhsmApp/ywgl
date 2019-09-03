package com.fh.controller.jqgrid.jggrid;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.jqGrid;
import com.fh.service.jqgrid.jggrid.JgGridManager;
import com.fh.service.jqgridDetail.jqgriddetail.impl.JqGridDetailService;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.mysql.jdbc.RowData;

import net.sf.json.JSONObject;

/**
 * JqGrid测试练习
* @ClassName: JgGridController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月8日
*
 */
@Controller
@RequestMapping(value="/jqgrid")
public class JgGridController extends BaseController {
	
	String menuUrl = "jqgrid/list.do"; //菜单地址(权限用)
	@Resource(name="jqgridService")
	private JgGridManager jqgridService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增JgGrid");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("JGGRID_ID", this.get32UUID());	//主键
		jqgridService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除JgGrid");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		jqgridService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改JgGrid");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		String type = pd.getString("oper");
		if (type.equals("edit")) {
			jqgridService.edit(pd);
		} else if (type.equals("add")) {
			jqgridService.save(pd);
		} else if (type.equals("del")) {
			String DATA_IDS = pd.getString("id");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				jqgridService.deleteAll(ArrayDATA_IDS);
			}
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/doPost")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String rowData = request.getParameter("rowData");  
     if (rowData!=null&&!"".equals(rowData)) {  
            JSONObject json=JSONObject.fromObject(rowData);  
            int size = json.size();  
            List<jqGrid> list= new ArrayList<jqGrid>();  
            for (int i = 0; i < size; i++) {  
                JSONObject o = (JSONObject) json.get(""+i);  
                jqGrid b = (jqGrid) JSONObject.toBean(o,jqGrid.class);  
                System.out.println(b.getCategoryName());  
                list.add(b);  
            }  
                      
        }   
    }  
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表JgGrid");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("jqgrid/jggrid/jggrid_list");
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Betting");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = jqgridService.list(page);	//列出Betting列表
		int records = jqgridService.countJqGrid(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		
		PageData userdata = new PageData();
		userdata.put("PRICE", "2000");
		result.setUserdata(userdata);
		//搜索
		String search = pd.getString("_search");
		String searchField = pd.getString("searchField");
		String searchString = pd.getString("searchString");
		String searchOper = pd.getString("searchOper");
		
		
		result.setRows(varList);
		result.setRecords(records);
		result.setPage(page.getPage());
		
		return result;
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
		mv.setViewName("jqgrid/jggrid/jggrid_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
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
		pd = jqgridService.findById(pd);	//根据ID读取
		mv.setViewName("jqgrid/jggrid/jggrid_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除JgGrid");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			jqgridService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	
//	public Object editAll() throws Exception {
//		PageData pd = new PageData();
//		Map<String, Object> map = new HashMap<String,Object>();
//		pd = this.getPageData();
//		
//		
//	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出JgGrid到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("CategoryName");	//1
		titles.add("ProductName");	//2
		titles.add("Country");	//3
		titles.add("Price");	//4
		titles.add("Quantity");	//5
		dataMap.put("titles", titles);
		List<PageData> varOList = jqgridService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("CATEGORYNAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("PRODUCTNAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("COUNTRY"));	    //3
			vpd.put("var4", varOList.get(i).getString("PRICE"));	    //4
			vpd.put("var5", varOList.get(i).get("QUANTITY").toString());	//5
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
	
	
//	@RequestMapping(value="/detailList")
//	public Object getDetailList(Page page) throws Exception{
//		PageData pd = new PageData();
//		Map<String,Object> map = new HashMap<String,Object>();
//		pd = this.getPageData();
//		page.setPd(pd);
//		List<PageData> pdList = new ArrayList<PageData>();
//		String ParentId = pd.getString("ParentId");
//		if (null != ParentId && !"".equals(ParentId)) {
//			List<PageData> varList = jqgridService.detailList(page);
//			return varList;
//		}
//		pdList.add(pd);
//		map.put("list", pdList);
//		return AppUtil.returnObject(pd, map);
//	}
//	
	
	
	
	/**列表(练习）
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listTest")
	public ModelAndView listTest() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表JgGrid");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("jqgridTest/jggridTest/jggrid_list_test");
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		return mv;
	}
	
}
