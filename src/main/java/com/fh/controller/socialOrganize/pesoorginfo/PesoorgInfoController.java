package com.fh.controller.socialOrganize.pesoorginfo;

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
import com.fh.service.socialOrganize.pesoorginfo.PesoorgInfoManager;
import com.fh.service.system.dictionaries.DictionariesManager;

/**
 * 体育社会组织领导机构信息
* @ClassName: PesoorgInfoController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Controller
@RequestMapping(value="/pesoorginfo")
public class PesoorgInfoController extends BaseController {
	
	String menuUrl = "pesoorginfo/list.do"; //菜单地址(权限用)
	@Resource(name="pesoorginfoService")
	private PesoorgInfoManager pesoorginfoService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增PesoorgInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PESOORGINFO_ID", this.get32UUID());	//主键
		pesoorginfoService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除PesoorgInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pesoorginfoService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改PesoorgInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pesoorginfoService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表PesoorgInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = pesoorginfoService.list(page);	//列出PesoorgInfo列表
		mv.setViewName("socialOrganize/pesoorginfo/pesoorginfo_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("sexList", getSexList());
		mv.addObject("postList", DictsUtil.getDictsByParentBianma(dictionariesService,"002"));
		mv.addObject("nameList", getNameList());
		
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
		mv.setViewName("socialOrganize/pesoorginfo/pesoorginfo_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("sexList", getSexList());
		mv.addObject("postList", DictsUtil.getDictsByParentBianma(dictionariesService,"002"));
		mv.addObject("nameList", getNameList());
		
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
		pd = pesoorginfoService.findById(pd);	//根据ID读取
		mv.setViewName("socialOrganize/pesoorginfo/pesoorginfo_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("sexList", getSexList());
		mv.addObject("postList", DictsUtil.getDictsByParentBianma(dictionariesService,"002"));
		mv.addObject("nameList", getNameList());
		
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除PesoorgInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pesoorginfoService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出PesoorgInfo到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("社会组织名称");	//1
		titles.add("社会组织领导职务");	//2
		titles.add("姓名");	//3
		titles.add("性别");	//4
		titles.add("联系电话");	//5
		titles.add("备注");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = pesoorginfoService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("PESO_SHOW"));	    //1
			vpd.put("var2", varOList.get(i).getString("STAFF_JOB"));	    //2
			vpd.put("var3", varOList.get(i).getString("LEADER_NAME"));	    //3
			vpd.put("var4", varOList.get(i).getString("STAFF_SEX"));	    //4
			vpd.put("var5", varOList.get(i).getString("MOBILE_TEL"));	    //5
			vpd.put("var6", varOList.get(i).getString("REMARK"));	    //6
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
			List<PageData> list = pesoorginfoService.hasDuplicateRecord(pd);
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
	
	private List<PageData> getSexList(){
		List<PageData> list=new ArrayList<PageData>();
		PageData pd0=new PageData();
		pd0.put("SEX_ID","1");
		pd0.put("SEX_NAME","男");
		list.add(pd0);
		PageData pd1=new PageData();
		pd1.put("SEX_ID","2");
		pd1.put("SEX_NAME","女");
		list.add(pd1);
		return list;
	}
	
	private List<PageData> getNameList() throws Exception{
		List<PageData> list=pesoorginfoService.pesoNameList();
        return list;
	}
}
