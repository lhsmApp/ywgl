package com.fh.controller.glZrzxFx.glZrzxFx;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.fh.controller.common.QueryFeildString;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Jurisdiction;

import net.sf.json.JSONArray;

import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.glZrzxFx.glZrzxFx.GlZrzxFxManager;
import com.fh.service.system.dictionaries.DictionariesManager;

/** 
 * 说明： 
 * 创建人：zhangxiaoliu
 * 创建时间：2017-09-14
 * @version
 */
@Controller
@RequestMapping(value="/glZrzxFx")
public class GlZrzxFxController extends BaseController {
	
	String menuUrl = "glZrzxFx/list.do"; //菜单地址(权限用)
	@Resource(name="glZrzxFxService")
	private GlZrzxFxManager glZrzxFxService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表GlZrzxFx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("glZrzxFx/glZrzxFx/glZrzxFx_list");
		PageData pd = this.getPageData();
		pd.put("SelectedfxCode", "");
		page.setPd(pd);
		mv.addObject("pd", pd);
		
		//BILL_OFF FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));

		// *********************加载单位树*******************************
		String departTreeSource = DictsUtil.getDepartmentSelectTreeSource(departmentService, DictsUtil.DepartShowAll_01001);
		mv.addObject("zTreeNodes", departTreeSource);
		// ***********************************************************
		// LINE_NO fx 分线
		mv.addObject("fxList", DictsUtil.getDictsByParentBianma(dictionariesService, "LINE_NO"));

		String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);
		
		String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);

		String fxValus = DictsUtil.getDicValue(dictionariesService, "LINE_NO");
		String lineNoStringAll = ":[All];" + fxValus;
		String lineNoStringSelect = ":;" + fxValus;
		mv.addObject("lineNoStrAll", lineNoStringAll);
		mv.addObject("lineNoStrSelect", lineNoStringSelect);
		
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表");

		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//责任中心
		String SelectedDepartCode = getPd.getString("SelectedDepartCode");
		//分线
		String SelectedfxCode = getPd.getString("SelectedfxCode");
		//状态
		String SelectedstateCode = getPd.getString("SelectedstateCode");

		String QueryFeild = "";
		if(SelectedCustCol7 != null && !SelectedCustCol7.trim().equals("")){
			QueryFeild += " and BILL_OFF = '" + SelectedCustCol7 + "' ";
		}
		if(SelectedDepartCode != null && !SelectedDepartCode.trim().equals("")){
			QueryFeild += " and DEPT_CODE in (" + QueryFeildString.getSqlInString(SelectedDepartCode) + ") ";
		}
		if(SelectedfxCode != null && !SelectedfxCode.trim().equals("")){
			QueryFeild += " and LINE_NO = '" + SelectedfxCode + "' ";
		}
		if(SelectedstateCode != null && !SelectedstateCode.trim().equals("")){
			QueryFeild += " and state = '" + SelectedstateCode + "' ";
		}
		getPd.put("QueryFeild", QueryFeild);
		
		//多条件过滤条件
		String filters = getPd.getString("filters");
		if(null != filters && !"".equals(filters)){
			getPd.put("filterWhereResult", SqlTools.constructWhere(filters,null));
		}
		page.setPd(getPd);
		List<PageData> varList = new ArrayList<PageData>();
		varList = glZrzxFxService.JqPage(page);	//列出Betting列表
		int records = glZrzxFxService.countJqGridExtend(page);
		
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setRecords(records);
		result.setPage(page.getPage());
		
		return result;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public @ResponseBody CommonBase save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增GlZrzxFx");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData pd = this.getPageData();
		String oper = pd.getString("oper");
		List<PageData> listData = new ArrayList<PageData>();
		listData.add(pd);
		List<PageData> repeatList = glZrzxFxService.findById(listData);
		if(repeatList!=null && repeatList.size()>0){
			commonBase.setCode(2);
			commonBase.setMessage("关系已存在，请在原有记录基础上修改！");
		} else {
			if(oper.equals("add")){
				glZrzxFxService.save(listData);
				commonBase.setCode(0);
			}
			if(oper.equals("edit")){
				glZrzxFxService.edit(listData);
				commonBase.setCode(0);
			}
		}
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
		logBefore(logger, Jurisdiction.getUsername() + "批量");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String strDataRows = pd.getString("UpdataDataRows");
		JSONArray array = JSONArray.fromObject(strDataRows);
		List<PageData> listData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法

		if (null != listData && listData.size() > 0) {
			glZrzxFxService.edit(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
