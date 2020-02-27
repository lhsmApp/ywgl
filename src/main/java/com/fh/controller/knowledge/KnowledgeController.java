package com.fh.controller.knowledge;

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
import com.fh.entity.Page;
import com.fh.entity.PageResult2;
import com.fh.entity.system.User;
import com.fh.service.knowledge.KnowledgeManager;
import com.fh.service.knowledge.KnowledgeTypeManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.date.DateUtils;

/** 
 * 说明：知识库
 * 创建人：jiachao
 * 创建时间：2019-10-09
 */
@Controller
@RequestMapping(value="/knowledge")
public class KnowledgeController extends BaseController {
	
	String menuUrl = "knowledge/list.do"; //菜单地址(权限用)
	@Resource(name="knowledgeService")
	private KnowledgeManager knowledgeService;
	
	@Resource(name="knowledgetypeService")
	private KnowledgeTypeManager knowledgetypeService;
	
	/**
	 * 保存问题提报信息
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody CommonBase save() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData pd = this.getPageData();
		if(pd.getString("KNOWLEDGE_ID")==null||pd.getString("KNOWLEDGE_ID").trim().equals("")){
			
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			String userId = user.getUSER_ID();
			pd.put("CREATE_USER", userId);
			pd.put("CREATE_DATE", DateUtils.getCurrentTime());
			
			
			knowledgeService.save(pd);
			commonBase.setCode(0);
		}
		else{
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
			String userId = user.getUSER_ID();
			pd.put("CREATE_USER", userId);
			pd.put("CREATE_DATE", DateUtils.getCurrentTime());
			knowledgeService.edit(pd);
			commonBase.setCode(0);
		}
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody CommonBase delete() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		knowledgeService.delete(pd);
		commonBase.setCode(0);
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**查询
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/queryKnowledgeInfo")
	public ModelAndView queryKnowledgeInfo(Page page) throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		/*User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("BILL_USER", userId);*/
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = knowledgeService.list(page);
		
		
		mv.addObject("pd", pd);
		//List<Dictionaries> dicKnowledgeType=DictsUtil.getDictsByParentBianma(dictionariesService, "KNOWLEDGE");
		List<PageData> KnowledgeTypeList=knowledgetypeService.listAll(null);
		mv.addObject("knowledgeTypeList", KnowledgeTypeList);//系统类型
		mv.addObject("varList", varList);//问题状态
		
		mv.setViewName("knowledge/knowledge_query");
		return mv;
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("knowledge/knowledge_list");
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		//List<Dictionaries> dicKnowledgeType=DictsUtil.getDictsByParentBianma(dictionariesService, "KNOWLEDGE");
		List<PageData> KnowledgeTypeList=knowledgetypeService.listAll(null);
		mv.addObject("knowledgeTypeList", KnowledgeTypeList);//系统类型
		
		/*List<PageData> zproblemTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(problemtypeService.listAllProblemTypeToSelect("0",zproblemTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));*/
		return mv;
	}
	
	/**获取知识库列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult2<PageData> getPageList(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = knowledgeService.list(page);	//列出Knowledge列表

		PageResult2<PageData> result = new PageResult2<PageData>();
		result.setRows(varList);
		result.setPage(page);
		result.setPageHtml(page.getPageStr2());
		return result;
	}
	
	/**获取知识库列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageListKnowledge")
	public @ResponseBody List<PageData> getPageListKnowledge() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		//page.setPd(pd);
		List<PageData>	varList = knowledgeService.listAll(pd);	//列出Knowledge列表
		return varList;
	}
	
	/**获取详细信息
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getDetail")
	public @ResponseBody PageData getDetail() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pageData = knowledgeService.findById(pd);
		return pageData;
	}
	
	/**查看明细页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdResult = knowledgeService.findById(pd);	//根据ID读取
		mv.setViewName("knowledge/knowledge_view");
		mv.addObject("pd", pdResult);
		return mv;
	}
	
		
	
	 
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Knowledge");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			knowledgeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Knowledge到excel");
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
		dataMap.put("titles", titles);
		List<PageData> varOList = knowledgeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("KNOWLEDGE_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("KNOWLEDGE_TITLE"));	    //2
			vpd.put("var3", varOList.get(i).get("KNOWLEDGE_TYPE").toString());	//3
			vpd.put("var4", varOList.get(i).getString("KNOWLEDGE_TAG"));	    //4
			vpd.put("var5", varOList.get(i).getString("AUTHOR"));	    //5
			vpd.put("var6", varOList.get(i).get("READ_NUM").toString());	//6
			vpd.put("var7", varOList.get(i).getString("DETAIL"));	    //7
			vpd.put("var8", varOList.get(i).getString("CREATE_USER"));	    //8
			vpd.put("var9", varOList.get(i).getString("CREATE_DATE"));	    //9
			vpd.put("var10", varOList.get(i).getString("STATE"));	    //10
			vpd.put("var11", varOList.get(i).getString("CUST1"));	    //11
			vpd.put("var12", varOList.get(i).getString("CUST2"));	    //12
			vpd.put("var13", varOList.get(i).getString("CUST3"));	    //13
			vpd.put("var14", varOList.get(i).getString("CUST4"));	    //14
			vpd.put("var15", varOList.get(i).get("CUST5").toString());	//15
			vpd.put("var16", varOList.get(i).get("CUST6").toString());	//16
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
