package com.fh.controller.attachment;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.FileUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.AttachmentType;
import com.fh.service.attachment.AttachmentManager;

/** 
 * 说明：附件
 * 创建人：jiachao
 * 创建时间：2019-10-14
 */
@Controller
@RequestMapping(value="/attachment")
public class AttachmentController extends BaseController {
	
	String menuUrl = "attachment/list.do"; //菜单地址(权限用)
	@Resource(name="attachmentService")
	private AttachmentManager attachmentService;
	
	/**附件列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getAttachmentByType")
	public @ResponseBody List<PageData> getAttachmentByType() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = attachmentService.getAttachmentByType(pd);
		return varList;
		//BufferedReader reader = new BufferedReader(new InputStreamReader(trequest.getInputStream()));
	}
	
	/**
	 *  文件上传
	 * @param pic
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadAttachment")
	public void uploadAttachment(MultipartFile attachment,HttpServletResponse response) throws Exception {
		String attachmentPath = PathUtil.getClasspath()+Const.FILEPATHFILEOA;
		String fileName = new java.text.SimpleDateFormat("yyyyMMddhhmmss").format(new Date());	//获取当前日期
		fileName = fileName + (int)(Math.random()*90000+10000);
		
		String attachmentName = FileUpload.fileUp(attachment,attachmentPath,fileName);
		System.out.println(attachmentPath+attachmentName);
		JSONObject json = new JSONObject();
		//回调文件路径
		json.put("path",attachmentName); 
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json.toString());
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("CREATE_USER", userId);
		pd.put("CREATE_DATE", DateUtils.getCurrentTime());
		pd.put("ATTACHMENT_SIZE", FileUtil.getFilesize(PathUtil.getClasspath() + Const.FILEPATHFILEOA + pd.getString("ATTACHMENT_PATH").trim()));	//文件大小
		attachmentService.save(pd);
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
		pd = attachmentService.findById(pd);
		attachmentService.delete(pd);
		DelAllFile.delFolder(PathUtil.getClasspath()+ Const.FILEPATHFILEOA + pd.getString("ATTACHMENT_PATH")); //删除文件
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
		attachmentService.edit(pd);
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
		List<PageData>	varList = attachmentService.list(page);	//列出Attachment列表
		for (PageData pageData : varList) {
			if(pageData.get("BUSINESS_TYPE")!=null){
				pageData.put("BUSINESS_TYPE_NAME", AttachmentType.getValueByKey(pageData.getString("BUSINESS_TYPE")));
			}
		}
		mv.setViewName("attachment/attachment/attachment_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去查看页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("attachment/attachment/attachment_view");
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
		mv.setViewName("attachment/attachment/attachment_edit");
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
		pd = attachmentService.findById(pd);	//根据ID读取
		mv.setViewName("attachment/attachment/attachment_edit");
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
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			attachmentService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**下载
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/download")
	public void downExcel(HttpServletResponse response)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = attachmentService.findById(pd);
		String fileName = pd.getString("ATTACHMENT_PATH").trim();
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILEOA + fileName, pd.getString("ATTACHMENT_NAME")+fileName.substring(19, fileName.length()));
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
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = attachmentService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ATTACHMENT_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("BUSINESS_TYPE"));	    //2
			vpd.put("var3", varOList.get(i).getString("BILL_CODE"));	    //3
			vpd.put("var4", varOList.get(i).getString("ATTACHMENT_NAME"));	    //4
			vpd.put("var5", varOList.get(i).getString("ATTACHMENT_TYPE"));	    //5
			vpd.put("var6", varOList.get(i).getString("ATTACHMENT_SIZE"));	    //6
			vpd.put("var7", varOList.get(i).getString("ATTACHMENT_PATH"));	    //7
			vpd.put("var8", varOList.get(i).getString("CREATE_USER"));	    //8
			vpd.put("var9", varOList.get(i).getString("CREATE_DATE"));	    //9
			vpd.put("var10", varOList.get(i).getString("STATE"));	    //10
			vpd.put("var11", varOList.get(i).getString("CUST1"));	    //11
			vpd.put("var12", varOList.get(i).getString("CUST2"));	    //12
			vpd.put("var13", varOList.get(i).getString("CUST3"));	    //13
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
