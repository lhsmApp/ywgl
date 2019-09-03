package com.fh.controller.stadium.stadium;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.tools.Trace;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.DictsUtil;
import com.fh.entity.Nature;
import com.fh.entity.Page;
import com.fh.service.stadium.stadium.StadiumManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

import net.sf.json.JSONArray;

/**
 * 体育场馆管理
* @ClassName: StadiumController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Controller
@RequestMapping(value="/stadium")
public class StadiumController extends BaseController {
	
	String menuUrl = "stadium/list.do"; //菜单地址(权限用)
	@Resource(name="stadiumService")
	private StadiumManager stadiumService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpSession httpSession, 
			@RequestParam(value = "STAD_NAME", required = true) String STAD_NAME,
			@RequestParam(value = "STADI_ADDR", required = true) String STADI_ADDR,
			@RequestParam(value = "HEAD_NAME", required = true) String HEAD_NAME,
			@RequestParam(value = "HEAD_TEL", required = true) String HEAD_TEL,
			@RequestParam(value = "BELONG_AREA", required = true) String BELONG_AREA,
			@RequestParam(value = "OPER_NATURE", required = true) String OPER_NATURE,
			@RequestParam(value = "STADI_INTR", required = true) String STADI_INTR,
			@RequestParam(value = "GEOG_COOR", required = true) String GEOG_COOR,
			@RequestParam(value = "REMARK", required = true) String REMARK,
			@RequestParam(value = "STADI_SPOT", required = true) String STADI_SPOT,
			@RequestParam MultipartFile[] fileLogos) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Stadium");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STADIUM_ID", this.get32UUID());	//主键
		
		pd.put("STAD_NAME", STAD_NAME);
		pd.put("STADI_ADDR", STADI_ADDR);
		pd.put("HEAD_NAME", HEAD_NAME);
		pd.put("HEAD_TEL", HEAD_TEL);
		pd.put("BELONG_AREA", BELONG_AREA);
		pd.put("OPER_NATURE", OPER_NATURE);
		pd.put("STADI_INTR", STADI_INTR);
		pd.put("GEOG_COOR", GEOG_COOR);
		pd.put("REMARK", REMARK);
		pd.put("STADI_SPOT", STADI_SPOT);
			
			for (MultipartFile fileLogo : fileLogos) {
				String path = null;// 文件路径
				String type = null;// 文件类型
				String fileName = fileLogo.getOriginalFilename();// 文件原名称
				System.out.println("上传的文件原名称:" + fileName);
				// 判断文件类型
				type = fileName.indexOf(".") != -1
						? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
				if (type != null) {// 判断文件类型是否为空
					if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase())
							|| "JPG".equals(type.toUpperCase())) {
						// 项目在容器中实际发布运行的根路径
						String realPath = httpSession.getServletContext().getRealPath("/") + "uploadFiles/uploadImgs";
						// 自定义的文件名称
						String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
						// 设置存放图片文件的路径
						path = realPath + File.separator + trueFileName;
						System.out.println("存放图片文件的路径:" + path);
						pd.put("PHOTO_ADDR", trueFileName);
						// 转存文件到指定的路径
						fileLogo.transferTo(new File(path));
						
					} 
				} 
			}
		
		stadiumService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**判断体育馆名是否存在
	 * @return
	 */
	@RequestMapping(value="/hasStaName")
	@ResponseBody
	public Object hasStaName(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(stadiumService.findByStaName(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Stadium");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		stadiumService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpSession httpSession, 
			@RequestParam(value = "ID", required = true) String ID,
			@RequestParam(value = "STAD_NAME", required = true) String STAD_NAME,
			@RequestParam(value = "STADI_ADDR", required = true) String STADI_ADDR,
			@RequestParam(value = "HEAD_NAME", required = true) String HEAD_NAME,
			@RequestParam(value = "HEAD_TEL", required = true) String HEAD_TEL,
			@RequestParam(value = "BELONG_AREA", required = true) String BELONG_AREA,
			@RequestParam(value = "OPER_NATURE", required = true) String OPER_NATURE,
			@RequestParam(value = "STADI_INTR", required = true) String STADI_INTR,
			@RequestParam(value = "GEOG_COOR", required = true) String GEOG_COOR,
			@RequestParam(value = "REMARK", required = true) String REMARK,
			@RequestParam(value = "STADI_SPOT", required = true) String STADI_SPOT,
			@RequestParam MultipartFile[] fileLogos) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Stadium");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", ID);
		pd.put("STAD_NAME", STAD_NAME);
		pd.put("STADI_ADDR", STADI_ADDR);
		pd.put("HEAD_NAME", HEAD_NAME);
		pd.put("HEAD_TEL", HEAD_TEL);
		pd.put("BELONG_AREA", BELONG_AREA);
		pd.put("OPER_NATURE", OPER_NATURE);
		pd.put("STADI_INTR", STADI_INTR);
		pd.put("GEOG_COOR", GEOG_COOR);
		pd.put("REMARK", REMARK);
		pd.put("STADI_SPOT", STADI_SPOT);
			
			for (MultipartFile fileLogo : fileLogos) {
				String path = null;// 文件路径
				String type = null;// 文件类型
				String fileName = fileLogo.getOriginalFilename();// 文件原名称
				System.out.println("上传的文件原名称:" + fileName);
				// 判断文件类型
				type = fileName.indexOf(".") != -1
						? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
				if (type != null) {// 判断文件类型是否为空
					if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase())
							|| "JPG".equals(type.toUpperCase())) {
						// 项目在容器中实际发布运行的根路径
						String realPath = httpSession.getServletContext().getRealPath("/") + "uploadFiles/uploadImgs";
						// 自定义的文件名称
						String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
						// 设置存放图片文件的路径
						path = realPath + File.separator + trueFileName;
						System.out.println("存放图片文件的路径:" + path);
						pd.put("PHOTO_ADDR", trueFileName);
						// 转存文件到指定的路径
						fileLogo.transferTo(new File(path));
						
					} 
				} 
			}
		
		stadiumService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Stadium");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = stadiumService.list(page);	//列出Stadium列表
		mv.setViewName("stadium/stadium/stadium_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		mv.addObject("natureList", Nature.values());
		
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
		mv.setViewName("stadium/stadium/stadium_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);	
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		mv.addObject("natureList", Nature.values());
		
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
		pd = stadiumService.findById(pd);	//根据ID读取
		mv.setViewName("stadium/stadium/stadium_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		mv.addObject("natureList", Nature.values());
		
		
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Stadium");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			stadiumService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Stadium到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("场馆名称");	//1
		titles.add("场馆地址");	//2
		titles.add("负责人姓名");	//3
		titles.add("负责人电话");	//4
		titles.add("所属区域");	//5
		titles.add("运营性质");	//6
		titles.add("场馆基本情况");	//7
		titles.add("地理坐标");	//8
		titles.add("备注");	//9
		dataMap.put("titles", titles);
		List<PageData> varOList = stadiumService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STAD_NAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("STADI_ADDR"));	    //2
			vpd.put("var3", varOList.get(i).getString("HEAD_NAME"));	    //3
			vpd.put("var4", varOList.get(i).getString("HEAD_TEL"));	    //4
			vpd.put("var5", varOList.get(i).getString("BELONG_AREA"));	    //5
			vpd.put("var6", varOList.get(i).getString("OPER_NATURE"));	    //6
			vpd.put("var7", varOList.get(i).getString("STADI_INTR"));	    //7
			vpd.put("var8", varOList.get(i).getString("GEOG_COOR"));	    //8
			vpd.put("var9", varOList.get(i).getString("REMARK"));	    //9
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
		logBefore(logger, Jurisdiction.getUsername()+"查询体育场馆地图");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
	    pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		
		PageData pdAll =new PageData();
		List<PageData>	varList = stadiumService.listAll(pdAll);	//列出地图Stadium列表
		List<PageData> searchList = stadiumService.listAllByCondition(pd);	//搜索地图Stadium列表
		mv.setViewName("stadium/stadium/stadiumMap");
		mv.addObject("varList", varList);
		mv.addObject("searchList", searchList);
		JSONArray jsonArray=JSONArray.fromObject(searchList);
		mv.addObject("searchJson",jsonArray);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("areaList", DictsUtil.getDictsByParentBianma(dictionariesService,"001"));
		return mv;
	}
	
	@RequestMapping(value="/getStadiumByBelongarea")
	@ResponseBody
	public Object getBettingByBelongarea() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
	    PageData pd = this.getPageData();
		List<PageData> searchList = stadiumService.listAllByCondition(pd);	//搜索地图Stadium列表
		map.put("list", searchList);
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
