package com.fh.controller.jqgrid.jggrid;

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
import com.fh.entity.CommonBase;
import com.fh.entity.JqGridModel;
import com.fh.entity.JqPage;
import com.fh.entity.PageResult;
import com.fh.service.jqgrid.jggrid.JgGridManagerJia;
import com.fh.service.sysBillnum.sysbillnum.SysBillnumManager;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;

import net.sf.json.JSONArray;

/**
 * JqGrid测试练习
* @ClassName: JgGridController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月8日
*
 */
@Controller
@RequestMapping(value="/jqgridJia")
public class JgGridControllerJia extends BaseController {
	
	String menuUrl = "jqgridJia/list.do"; //菜单地址(权限用)
	@Resource(name="jqgridServiceJia")
	private JgGridManagerJia jqgridServiceJia;
	
	@Resource(name="sysbillnumService")
	private SysBillnumManager sysbillnumService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		/***************获取最大单号及更新最大单号********************
		PageData pdBillNum=new PageData();
		pdBillNum.put("BILL_CODE", BillNumType.SHBX);
		pdBillNum.put("BILL_DATE", DateUtil.getMonth());
		PageData pdBillNumResult=sysbillnumService.findById(pdBillNum);
		int billNum=ConvertUtils.strToInt(pdBillNumResult.getString("BILL_NUMBER"),0);
		pdBillNum.put("BILL_NUMBER", billNum++);
		sysbillnumService.edit(pdBillNum);
		/***************************************************/
		
		logBefore(logger, Jurisdiction.getUsername()+"列表JgGrid");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("jqgrid/jggrid/jggrid_listJia");
		
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
		String filters = pd.getString("filters");				//多条件过滤条件
		if(null != filters && !"".equals(filters)){
			pd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		
		page.setPd(pd);
		List<PageData> varList = jqgridServiceJia.list(page);	//列出Betting列表
		int records = jqgridServiceJia.countJqGrid(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		PageData userData=new PageData();
		userData.put("PRICE", 2622.99);
		result.setUserdata(userData);
		
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
		pd = jqgridServiceJia.findById(pd);	//根据ID读取
		mv.setViewName("jqgrid/jggrid/jggrid_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public @ResponseBody CommonBase edit() throws Exception{
		CommonBase commonBase = new CommonBase();
		logBefore(logger, Jurisdiction.getUsername()+"修改JgGrid");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("oper").equals("edit")){
			jqgridServiceJia.edit(pd);
			commonBase.setCode(0);
		}
		else if(pd.getString("oper").equals("add")){
			jqgridServiceJia.save(pd);
			commonBase.setCode(0);
		}
		else if(pd.getString("oper").equals("del")){
			String [] ids=pd.getString("id").split(",");
			if(ids.length==1)
				jqgridServiceJia.delete(pd);
			else
				jqgridServiceJia.deleteAll(ids);
			commonBase.setCode(0);
		}
		
		/**此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		//commonBase.setCode(-1);
		//commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}

	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	public @ResponseBody CommonBase deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除JgGrid");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		PageData pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			jqgridServiceJia.deleteAll(ArrayDATA_IDS);
			commonBase.setCode(0);
		}
		return commonBase;	
	}
	
	/**批量修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/updateAll")
	/*@RequestBody RequestBase<JqGridModel> jqGridModel*/
	public @ResponseBody CommonBase updateAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除JgGrid");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("DATA_ROWS");
        JSONArray array = JSONArray.fromObject(strDataRows);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);// 过时方法
        /*List<JqGridModel>  dtoList=new ArrayList<JqGridModel>();  
        for (int i = 0; i < array.size(); i++) {  
            JSONObject jsonObject = array.getJSONObject(i);  
            JqGridModel item = (JqGridModel) JSONObject.toBean(jsonObject, JqGridModel.class);
            if(item != null){
                dtoList.add(item);  
            }
        }  */
		if(null != listData && listData.size() > 0){
			jqgridServiceJia.updateAll(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
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
		List<PageData> varOList = jqgridServiceJia.listAll(pd);
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
}
