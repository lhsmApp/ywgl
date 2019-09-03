package com.fh.controller.socialOrganize.pesoinfo;

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
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.fh.service.socialOrganize.pesoinfo.PesoInfoManager;
import com.fh.service.system.dictionaries.DictionariesManager;

/**
 * 社会组织
* @ClassName: PesoInfoController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Controller
@RequestMapping(value="/pesoinfo")
public class PesoInfoController extends BaseController {
	
	String menuUrl = "pesoinfo/list.do"; //菜单地址(权限用)
	@Resource(name="pesoinfoService")
	private PesoInfoManager pesoinfoService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PesoInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PESOINFO_ID", this.get32UUID());	//主键
		pesoinfoService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除PesoInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pesoinfoService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PesoInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pesoinfoService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PesoInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = pesoinfoService.list(page);	//列出PesoInfo列表
		mv.setViewName("socialOrganize/pesoinfo/pesoinfo_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		mv.addObject("stateList", getStateList());
			
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
		mv.setViewName("socialOrganize/pesoinfo/pesoinfo_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		mv.addObject("stateList", getStateList());
		
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
		pd = pesoinfoService.findById(pd);	//根据ID读取
		mv.setViewName("socialOrganize/pesoinfo/pesoinfo_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		mv.addObject("stateList", getStateList());
		
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PesoInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pesoinfoService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PesoInfo到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("社会组织名称");	//1
		titles.add("成立时间");	//2
		titles.add("所属区域");	//3
		titles.add("统一社会信用代码");	//4
		titles.add("负责人");	//5
		titles.add("负责人手机");	//6
		titles.add("电子邮箱");	//7
		titles.add("办公电话");	//8
		titles.add("办公地址");	//9
		titles.add("传真");	//10
		titles.add("职能简介");	//11
		titles.add("注册资金");	//12
		titles.add("状态");	//13
		titles.add("地理坐标");	//14
		titles.add("备注");	//15
		dataMap.put("titles", titles);
		List<PageData> varOList = pesoinfoService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("PESO_NAME"));	    //1
			//vpd.put("var2", varOList.get(i).getString("ESTA_TIME"));	    //2
			vpd.put("var2", varOList.get(i).get("ESTA_TIME").toString());	    //2
			vpd.put("var3", varOList.get(i).getString("BELONG_AREA"));	    //3
			vpd.put("var4", varOList.get(i).getString("USCC"));	    //4
			vpd.put("var5", varOList.get(i).getString("HEAD_NAME"));	    //5
			vpd.put("var6", varOList.get(i).getString("HEAD_TEL"));	    //6
			vpd.put("var7", varOList.get(i).getString("E_MAIL"));	    //7
			vpd.put("var8", varOList.get(i).getString("OFFICE_TEL"));	    //8
			vpd.put("var9", varOList.get(i).getString("OFFICE_ADDR"));	    //9
			vpd.put("var10", varOList.get(i).getString("FAX"));	    //10
			vpd.put("var11", varOList.get(i).getString("PESO_INTR"));	    //11
			vpd.put("var12", varOList.get(i).get("REGI_CAPI").toString());	//12
			vpd.put("var13", varOList.get(i).getString("STATE"));	    //13
			vpd.put("var14", varOList.get(i).getString("GEOG_COOR"));	    //14
			vpd.put("var15", varOList.get(i).getString("REMARK"));	    //15
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**判断重复记录是否存在
	 * @return
	 */
	@RequestMapping(value="/hasDuplicateRecord")
	@ResponseBody
	public Object hasDuplicateRecord(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> list = pesoinfoService.hasDuplicateRecord(pd);
			if(list != null&&list.size()>0){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
			errInfo = "出错："+e.toString();
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
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
		mv.setViewName("socialOrganize/pesoinfo/pesoinfo_map");
		PageData pd=new PageData();
	    pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}

		List<PageData> dicList = pesoinfoService.listDic(pd);	//搜索地图列表
		mv.addObject("dicList", dicList);
		
		List<PageData>	getList = pesoinfoService.queryListByCondition(pd);	//列出地图列表
		mv.addObject("getList", getList);

		JSONArray jsonArray=JSONArray.fromObject(getList);
		mv.addObject("searchJson",jsonArray);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		return mv;
	}
	
	@RequestMapping(value="/getListByCondition")
	@ResponseBody
	public Object getListByCondition() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
	    PageData pd = this.getPageData();
		List<PageData> searchList = pesoinfoService.listDic(pd);	//搜索地图列表
		map.put("list", searchList);
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	private List<PageData> getStateList(){
		List<PageData> areaList=new ArrayList<PageData>();
		PageData pd0 = new PageData();
		pd0.put("STATE_ID", "1");
		pd0.put("STATE_NAME", "正常");
		areaList.add(pd0);
		PageData pd1 = new PageData();
		pd1.put("STATE_ID", "2");
		pd1.put("STATE_NAME", "注销");
		areaList.add(pd1);
		PageData pd2 = new PageData();
		pd2.put("STATE_ID", "3");
		pd2.put("STATE_NAME", "解散");
		areaList.add(pd2);
        return areaList;
	}
}
