package com.fh.controller.bankcardInfo.bankcardInfo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.fh.controller.common.CheckSystemDateTime;
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.QueryFeildString;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.CommonBaseAndList;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;
import com.mysql.fabric.xmlrpc.base.Array;

import net.sf.json.JSONArray;

import com.fh.service.bankcardInfo.bankcardInfo.impl.BankcardInfoService;
import com.fh.service.fhoa.department.impl.DepartmentService;
import com.fh.service.laborDetail.laborDetail.impl.LaborDetailService;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysDeptLtdTime.sysDeptLtdTime.impl.SysDeptLtdTimeService;
import com.fh.service.system.dictionaries.impl.DictionariesService;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;

/**
 * 银行卡号信息维护
 * 
 * @ClassName: BankcardInfoController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2019年8月1日
 *
 */
@Controller
@RequestMapping(value = "/bankcardInfo")
public class BankcardInfoController extends BaseController {

	String menuUrl = "bankcardInfo/list.do"; // 菜单地址(权限用)
	@Resource(name = "bankcardInfoService")
	private BankcardInfoService bankcardInfoService;
	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	
	//表名
	String TableNameDetail = "TB_BANKCARD_INFO";
	Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
	Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
	// 查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
    List<String> keyListBase = Arrays.asList("STAFF_IDENT");
    
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表bankcardInfo");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)

		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("bankcardInfo/bankcardInfo/bankcardInfo_list");
		// 当前期间,取自tb_system_config的SystemDateTime字段
		String SystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", SystemDateTime.trim());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String DepartName = user.getDEPARTMENT_NAME();
		mv.addObject("DepartName", DepartName);
		mv.addObject("pd", getPd);
		
		//***********************************************************
		Map_HaveColumnsList = Common.GetHaveColumnsMapByTableName(TableNameDetail, tmplconfigService);
		
		Map_SetColumnsList.put("USER_NAME", new TmplConfigDetail("USER_NAME", "姓名", "1", false));
		Map_SetColumnsList.put("STAFF_IDENT", new TmplConfigDetail("STAFF_IDENT", "身份证号", "1", false));
		Map_SetColumnsList.put("BANK_NAME", new TmplConfigDetail("BANK_NAME", "开户行", "1", false));
		Map_SetColumnsList.put("BANK_CARD", new TmplConfigDetail("BANK_CARD", "银行帐号", "1", false));
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表bankcardInfo");

		PageData getPd = this.getPageData();
		String strFieldSelectKey = QueryFeildString.getFieldSelectKey(keyListBase, TmplUtil.keyExtra);
		if(null != strFieldSelectKey && !"".equals(strFieldSelectKey.trim())){
			getPd.put("FieldSelectKey", strFieldSelectKey);
		}
		
		// 多条件过滤条件
		String filters = getPd.getString("filters");
		if (null != filters && !"".equals(filters)) {
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		
		page.setPd(getPd);
		List<PageData> varList = bankcardInfoService.JqPage(page);
		int records = bankcardInfoService.countJqGridExtend(page);

		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());

		return result;
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public @ResponseBody CommonBase edit() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername() + "修改bankcardInfoService");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// //校验权限

		PageData getPd = this.getPageData();
		for(String strFeild : keyListBase){
			getPd.put(strFeild + TmplUtil.keyExtra, getPd.get(strFeild + TmplUtil.keyExtra));
		}
		List<PageData> listData = new ArrayList<PageData>();
		listData.add(getPd);
		bankcardInfoService.batchUpdateDatabase(listData);
		commonBase.setCode(0);
		return commonBase;
	}

	/**
	 * 批量修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateAll")
	public @ResponseBody CommonBase updateAll() throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);
		bankcardInfoService.batchUpdateDatabase(listData);
		commonBase.setCode(0);
		return commonBase;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAll")
	public @ResponseBody CommonBase deleteAll() throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "delete")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();

		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();
		JSONArray array = JSONArray.fromObject(json);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);
		if (null != listData && listData.size() > 0) {
			bankcardInfoService.deleteAll(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}

	/**
	 * 打开上传EXCEL页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();

		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "bankcardInfo");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
			throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		String strErrorMessage = "";
		PageData getPd = this.getPageData();
		Map<String, Object> DicList = new HashMap<String, Object>();

		// 局部变量
		LeadingInExcelToPageData<PageData> testExcel = null;
		Map<Integer, Object> uploadAndReadMap = null;
		try {
			// 定义需要读取的数据
			String formart = "yyyy-MM-dd";
			String propertiesFileName = "config";
			String kyeName = "file_path";
			int sheetIndex = 0;
			Map<String, String> titleAndAttribute = null;
			// 定义对应的标题名与对应属性名
			titleAndAttribute = new LinkedHashMap<String, String>();
			
			// 配置表设置列
			if (Map_SetColumnsList != null && Map_SetColumnsList.size() > 0) {
				for (TmplConfigDetail col : Map_SetColumnsList.values()) {
					titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
				}
			}

			// 调用解析工具包
			testExcel = new LeadingInExcelToPageData<PageData>(formart);
			// 解析excel，获取客户信息集合

			uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex, titleAndAttribute,
					Map_HaveColumnsList, Map_SetColumnsList, DicList, false, false, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取Excel文件错误", e);
			throw new CustomException("读取Excel文件错误:" + e.getMessage(), false);
		}
		boolean judgement = false;

		List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
		if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
			judgement = true;
		}
		if (judgement) {
			bankcardInfoService.batchUpdateDatabase(listUploadAndRead);
			commonBase.setCode(0);
		} else {
			commonBase.setCode(-1);
			commonBase.setMessage("TranslateUtil");
		}
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("common/uploadExcel");
		mv.addObject("local", "bankcardInfo");
		mv.addObject("commonBaseCode", commonBase.getCode());
		mv.addObject("commonMessage", commonBase.getMessage());
		return mv;
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public ModelAndView downExcel(JqPage page) throws Exception {

		PageData getPd = this.getPageData();
		List<PageData> varOList = bankcardInfoService.exportModel(getPd);
		return export(varOList, "银行卡号信息维护模板", Map_SetColumnsList);
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel(JqPage page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出BankcardInfo到excel");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}

		PageData getPd = this.getPageData();
		// 页面显示数据的年月
		// getPd.put("SystemDateTime", SystemDateTime);
		page.setPd(getPd);
		List<PageData> varOList = bankcardInfoService.exportList(page);
		return export(varOList, "", Map_SetColumnsList);
	}

	private ModelAndView export(List<PageData> varOList, String ExcelName,
			Map<String, TmplConfigDetail> map_SetColumnsList) throws Exception {
		// Map<String, Object> DicList = Common.GetDicList(TypeCodeDetail,
		// SelectedDepartCode, SelectedCustCol7,
		// tmplconfigService, tmplconfigdictService, dictionariesService,
		// departmentService, userService, AdditionalReportColumns);
		ModelAndView mv = new ModelAndView();
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("filename", ExcelName);
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		if (map_SetColumnsList != null && map_SetColumnsList.size() > 0) {
			for (TmplConfigDetail col : map_SetColumnsList.values()) {
				if (col.getCOL_HIDE().equals("1")) {
					titles.add(col.getCOL_NAME());
				}
			}
			if (varOList != null && varOList.size() > 0) {
				for (int i = 0; i < varOList.size(); i++) {
					PageData vpd = new PageData();
					int j = 1;
					for (TmplConfigDetail col : map_SetColumnsList.values()) {
						if (col.getCOL_HIDE().equals("1")) {
							// String trans = col.getDICT_TRANS();
							Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
							// if(trans != null && !trans.trim().equals("")){
							// String value = "";
							// Map<String, String> dicAdd = (Map<String,
							// String>) DicList.getOrDefault(trans, new
							// LinkedHashMap<String, String>());
							// value = dicAdd.getOrDefault(getCellValue, "");
							// vpd.put("var" + j, value);
							// } else {
							vpd.put("var" + j, getCellValue.toString());
							// }
							j++;
						}
					}
					varList.add(vpd);
				}
			}
		}
		dataMap.put("titles", titles);
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
