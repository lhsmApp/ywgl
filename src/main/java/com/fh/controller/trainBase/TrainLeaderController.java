package com.fh.controller.trainBase;

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

import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.trainBase.TrainDepartManager;
import com.fh.service.trainBase.TrainLeaderManager;

/** 
 * 说明：培训负责人
 * 创建人：jiachao
 * 创建时间：2019-10-23
 */
@Controller
@RequestMapping(value="/trainleader")
public class TrainLeaderController extends BaseController {
	
	String menuUrl = "trainleader/list.do"; //菜单地址(权限用)
	@Resource(name="trainleaderService")
	private TrainLeaderManager trainleaderService;
	
	@Resource(name="traindepartService")
	private TrainDepartManager trainDepartService;
	
	@Resource(name = "userService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		trainleaderService.save(pd);
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
		
		PageData pd = new PageData();
		pd = this.getPageData();
		trainleaderService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		trainleaderService.edit(pd);
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
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = trainleaderService.list(page);	//列出TrainLeader列表
		mv.setViewName("trainBase/trainleader/trainleader_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);

		
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
		mv.setViewName("trainBase/trainleader/trainleader_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(trainDepartService.listAllTrainDepartToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		mv.addObject("userList", DictsUtil.getSysUserDic(userService));//用户
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
		pd = trainleaderService.findById(pd);	//根据ID读取
		mv.setViewName("trainBase/trainleader/trainleader_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		
		String departName ="";
		pd.put("DEPART_CODE", pd.getString("DEPART_CODE"));
		PageData dpd1 = trainDepartService.findByCode(pd);
		if(null != dpd1){
			departName = dpd1.getString("DEPART_NAME");
		}
		mv.addObject("departName", departName);
		
		String unitName = "";
		pd.put("DEPART_CODE", pd.getString("UNIT_CODE"));
		PageData dpd = trainDepartService.findByCode(pd);
		if(null != dpd){
			unitName = dpd.getString("DEPART_NAME");
		}
		mv.addObject("unitName", unitName);
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(trainDepartService.listAllTrainDepartToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		mv.addObject("userList", DictsUtil.getSysUserDic(userService));//用户
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			trainleaderService.deleteAll(ArrayDATA_IDS);
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
		dataMap.put("titles", titles);
		List<PageData> varOList = trainleaderService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("LEADER_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("LEADER_CODE"));	    //2
			vpd.put("var3", varOList.get(i).getString("LEADER_NAME"));	    //3
			vpd.put("var4", varOList.get(i).get("ACCOUNT").toString());	//4
			vpd.put("var5", varOList.get(i).getString("UNIT_CODE"));	    //5
			vpd.put("var6", varOList.get(i).getString("DEPART_CODE"));	    //6
			vpd.put("var7", varOList.get(i).getString("MEMO"));	    //7
			vpd.put("var8", varOList.get(i).getString("STATE"));	    //8
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
