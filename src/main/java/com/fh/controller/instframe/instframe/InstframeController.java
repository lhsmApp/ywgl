package com.fh.controller.instframe.instframe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.instframe.instframe.InstframeManager;

/**
 * 组织机构
* @ClassName: InstframeController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoliu
* @date 2017年6月30日
*
 */
@Controller
@RequestMapping(value="/instframe")
public class InstframeController extends BaseController {
	
	String menuUrl = "instframe/list.do"; //菜单地址(权限用)
	@Resource(name="instframeService")
	private InstframeManager instframeService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Instframe");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INSTFRAME_ID", this.get32UUID());	//主键
		instframeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String INSTFRAME_ID) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Instframe");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} 					//校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("INSTFRAME_ID", INSTFRAME_ID);
		String errInfo = "success";
		if(instframeService.listByParentId(INSTFRAME_ID).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			instframeService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Instframe");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		instframeService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Instframe");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 	//校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");								//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String INSTFRAME_ID = null == pd.get("INSTFRAME_ID")?"":pd.get("INSTFRAME_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			INSTFRAME_ID = pd.get("id").toString();
		}
		pd.put("INSTFRAME_ID", INSTFRAME_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = instframeService.list(page);			//列出Instframe列表
		mv.setViewName("instframe/instframe/instframe_list");
		mv.addObject("pd", instframeService.findById(pd));				//传入上级所有信息
		mv.addObject("INSTFRAME_ID", INSTFRAME_ID);			//上级ID
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());								//按钮权限
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String INSTFRAME_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(instframeService.listTree("0"));
			String json = arr.toString();
			json = json.replaceAll("INSTFRAME_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subInstframe", "nodes").replaceAll("hasInstframe", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("INSTFRAME_ID",INSTFRAME_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("instframe/instframe/instframe_tree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		String INSTFRAME_ID = null == pd.get("INSTFRAME_ID")?"":pd.get("INSTFRAME_ID").toString();
		pd.put("INSTFRAME_ID", INSTFRAME_ID);					//上级ID
		mv.addObject("pds",instframeService.findById(pd));				//传入上级所有信息
		mv.addObject("INSTFRAME_ID", INSTFRAME_ID);			//传入ID，作为子级ID用
		mv.setViewName("instframe/instframe/instframe_edit");
		mv.addObject("msg", "save");
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
		String INSTFRAME_ID = pd.getString("INSTFRAME_ID");
		pd = instframeService.findById(pd);							//根据ID读取		
		mv.addObject("pd", pd);													//放入视图容器
		pd.put("INSTFRAME_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",instframeService.findById(pd));				//传入上级所有信息
		mv.addObject("INSTFRAME_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("INSTFRAME_ID",INSTFRAME_ID);					//复原本ID
		pd = instframeService.findById(pd);							//根据ID读取
		mv.setViewName("instframe/instframe/instframe_edit");
		mv.addObject("msg", "edit");
		return mv;
	}		
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Instframe到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("组织机构编码");	//1
		titles.add("组织机构名称");	//2
		titles.add("组织机构父编码");	//3
		titles.add("姓名");	//4
		titles.add("职务");	//5
		titles.add("电话");	//6
		titles.add("职能");	//7
		titles.add("地址");	//8
		titles.add("备注");	//9
		dataMap.put("titles", titles);
		List<PageData> varOList = instframeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INST_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("INST_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("INST_FATHER_CODE"));	    //3
			vpd.put("var4", varOList.get(i).getString("LEADER_NAME"));	    //4
			vpd.put("var5", varOList.get(i).getString("STAFF_JOB"));	    //5
			vpd.put("var6", varOList.get(i).getString("MOBILE_TEL"));	    //6
			vpd.put("var7", varOList.get(i).getString("FUNCTION"));	    //7
			vpd.put("var8", varOList.get(i).getString("ADDRESS"));	    //8
			vpd.put("var9", varOList.get(i).getString("REMARK"));	    //9
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasInstCode")
	@ResponseBody
	public Object hasInstCode(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> list=instframeService.findByInstCode(pd);
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
	
	@RequestMapping(value="/getInstframeGuest")
	public ModelAndView getInstframeGuest() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表InstframeGuest");
		ModelAndView mv = this.getModelAndView();
		
		//List<PageData>	varList = instframeService.list(page);			//列出Instframe列表
		mv.setViewName("instframe/instframe/instframe_guest");
		//mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());								//按钮权限
		return mv;
	}
}
