package com.fh.controller.jqGridExtend.jqGridExtend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.fh.entity.CommonBase;
import com.fh.entity.JqGridModel;
import com.fh.entity.JqPage;
import com.fh.entity.PageResult;
import com.fh.exception.CustomException;
import com.fh.service.jqGridExtend.jqGridExtend.JqGridExtendManager;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.excel.LeadingInExcel;

import net.sf.json.JSONArray;

/**
 * JqGridExtend测试练习
* @ClassName: JqGridExtendController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoliu
* @date 2017年6月8日
*
 */
@Controller
@RequestMapping(value="/jqGridExtend")
public class JqGridExtendController extends BaseController {
	
	String menuUrl = "jqGridExtend/list.do"; //菜单地址(权限用)
	@Resource(name="jqGridExtendService")
	private JqGridExtendManager jqGridExtendService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表JqGridExtend");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("jqGridExtend/jqGridExtend/jqGridExtend_list");
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		return mv;
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public @ResponseBody CommonBase edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改JqGridExtend");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		jqGridExtendService.edit(pd);
		commonBase.setCode(0);
		return commonBase;
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
		List<PageData> varList = jqGridExtendService.list(page);	//列出Betting列表
		//int records = jqGridExtendService.countJqGridExtend(page);
		PageData userdata=jqGridExtendService.getFooterSummary(page);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		//result.setRecords(records);
		result.setPage(page.getPage());
		result.setUserdata(userdata);
		
		return result;
	}
	/**明细
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getDetailList")
	public @ResponseBody PageResult<PageData> getDetailList() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"明细Betting");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> varList = jqGridExtendService.getDetailList(pd);	//列出Betting列表
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		
		return result;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	public @ResponseBody CommonBase deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除JqGridExtend");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			jqGridExtendService.deleteAll(ArrayDATA_IDS);
			commonBase.setCode(0);
		}
		pdList.add(pd);
		return commonBase;
	}
	
	 /**批量修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/updateAll")
	public @ResponseBody CommonBase updateAll() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限	
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		Object DATA_ROWS = pd.get("DATA_ROWS");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<JqGridModel> listData = (List<JqGridModel>) JSONArray.toCollection(array,JqGridModel.class);
        
        /* List<JqGridModel>  dtoList=new ArrayList<JqGridModel>();  

        for (int i = 0; i < array.size(); i++) {  
            JSONObject jsonObject = array.getJSONObject(i);  
            JqGridModel item = (JqGridModel) JSONObject.toBean(jsonObject, JqGridModel.class);
            if(item != null){
                dtoList.add(item);  
            }
        }  */

		if(null != listData && listData.size() > 0){
			jqGridExtendService.updateAll(listData);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出JqGridExtend到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID");	//
		titles.add("CategoryName");	//1
		titles.add("ProductName");	//2
		titles.add("Country");	//3
		titles.add("Price");	//4
		titles.add("Quantity");	//5
		dataMap.put("titles", titles);
		List<PageData> varOList = jqGridExtendService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ID").toString());	    //1
			vpd.put("var2", varOList.get(i).getString("CATEGORYNAME"));	    //1
			vpd.put("var3", varOList.get(i).getString("PRODUCTNAME"));	    //2
			vpd.put("var4", varOList.get(i).getString("COUNTRY"));	    //3
			vpd.put("var5", (varOList.get(i).get("PRICE") == null? 0 : varOList.get(i).get("PRICE")).toString());	    //4
			vpd.put("var6", (varOList.get(i).get("QUANTITY") == null? 0 : varOList.get(i).get("QUANTITY")).toString());	//5
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("jqGridExtend/jqGridExtend/uploadExcel");
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "JqGrids.xls", "JqGrids.xls");
	}

	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	//@ApiOperation(value = "导入Excel", httpMethod = "POST", response = CommonBase.class, notes = "导入Excel")
	@RequestMapping(value = "/readExcel")
	public @ResponseBody CommonBase readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		CommonBase commonBase = new CommonBase();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}//校验权限
		
		// 局部变量
		LeadingInExcel<JqGridModel> testExcel = null;
		List<JqGridModel> uploadAndRead = null;
		try {
			// 定义需要读取的数据
			String formart = "yyyy-MM-dd";
			String propertiesFileName = "config";
			String kyeName = "file_path";
			int sheetIndex = 0;
			Class<JqGridModel> clazz = JqGridModel.class;
			Map<String, String> titleAndAttribute = null;
			// 定义对应的标题名与对应属性名
			titleAndAttribute = new HashMap<String, String>();
			titleAndAttribute.put("ID", "ID");
			titleAndAttribute.put("CategoryName", "CATEGORYNAME");
			titleAndAttribute.put("ProductName", "PRODUCTNAME");
			titleAndAttribute.put("Country", "COUNTRY");
			titleAndAttribute.put("Price", "PRICE");
			titleAndAttribute.put("Quantity", "QUANTITY");

			// 调用解析工具包
			testExcel = new LeadingInExcel<JqGridModel>(formart);
			// 解析excel，获取客户信息集合

			//uploadAndRead = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex,
			//		titleAndAttribute, clazz, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取Excel文件错误", e);
			throw new CustomException("读取Excel文件错误",true);
		}
		boolean judgement = false;
		if (uploadAndRead != null && !"[]".equals(uploadAndRead.toString()) && uploadAndRead.size() >= 1) {
			judgement = true;
		}
		if (judgement) {
			// 把客户信息分为没100条数据为一组迭代添加客户信息（注：将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
			int listSize = uploadAndRead.size();
			int toIndex = 100;
			for (int i = 0; i < listSize; i += 100) {
				if (i + 100 > listSize) {
					toIndex = listSize - i;
				}
				List<JqGridModel> subList = uploadAndRead.subList(i, i + toIndex);

				/** 此处执行集合添加 */
				jqGridExtendService.batchImport(subList);
			}
		} else {
			commonBase.setCode(-1);
			commonBase.setMessage("TranslateUtil");
		}
		
		/*
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "jggridexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			
		} */
		return commonBase;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
