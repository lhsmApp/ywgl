package com.fh.controller.staffTransferReg.stafftransferreg;

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
import com.fh.controller.common.QueryFeildString;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.staffTransferReg.stafftransferreg.StaffTransferRegManager;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * 说明：内部员工调动记录管理 创建人：FH Q313596790 创建时间：2019-05-14
 */
@Controller
@RequestMapping(value = "/stafftransferreg")
public class StaffTransferRegController extends BaseController {

	String menuUrl = "stafftransferreg/list.do"; // 菜单地址(权限用)
	@Resource(name = "stafftransferregService")
	private StaffTransferRegManager stafftransferregService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("staffTransferReg/stafftransferreg/stafftransferreg_list");

		// *********************加载单位树*******************************
		String departTreeSource = DictsUtil.getDepartmentSelectTreeSource(departmentService,
				DictsUtil.DepartShowAll_01001);
		mv.addObject("zTreeNodes", departTreeSource);
		// ***********************************************************

		String departmentValus = DictsUtil.getDepartmentValue(departmentService);
		String departmentStringAll = ":[All];" + departmentValus;
		String departmentStringSelect = ":;" + departmentValus;
		mv.addObject("departmentStrAll", departmentStringAll);
		mv.addObject("departmentStrSelect", departmentStringSelect);
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//责任中心
		String SelectedDepartCode = pd.getString("SelectedDepartCode");
		String QueryFeild = "";
		if(SelectedDepartCode != null && !SelectedDepartCode.trim().equals("")){
			QueryFeild += " and DEPT_CODE in (" + QueryFeildString.getSqlInString(SelectedDepartCode) + ") ";
		}
		pd.put("QueryFeild", QueryFeild);
				
		List<PageData> varList = stafftransferregService.listAll(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
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

		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.getString("oper").equals("edit")) {
			stafftransferregService.edit(pd);
			commonBase.setCode(0);
		} else if (pd.getString("oper").equals("add")) {
			stafftransferregService.save(pd);
			commonBase.setCode(0);
		} else if (pd.getString("oper").equals("del")) {
			String[] ids = pd.getString("id").split(",");
			if (ids.length == 1)
				stafftransferregService.delete(pd);
			else
				stafftransferregService.deleteAll(ids);
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

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出StaffTransferReg到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("员工编号"); // 1
		titles.add("员工名称"); // 2
		titles.add("身份证号"); // 3
		titles.add("责任中心"); // 4
		titles.add("所属二级单位"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = stafftransferregService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USER_CODE")); // 1
			vpd.put("var2", varOList.get(i).getString("USER_NAME")); // 2
			vpd.put("var3", varOList.get(i).getString("STAFF_IDENT")); // 3
			vpd.put("var4", varOList.get(i).getString("DEPT_CODE")); // 4
			vpd.put("var5", varOList.get(i).getString("UNITS_CODE")); // 5
			varList.add(vpd);
		}
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
