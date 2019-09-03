package com.fh.controller.calculateFormula.calculateFormula;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.DictsUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.TmplConfigDictManager;
import com.fh.service.tmplconfig.tmplconfig.TmplConfigManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.enums.TmplType;

import net.sf.json.JSONArray;

/**
 * 数据模板详情
 * 
 * @ClassName: TmplConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年6月19日
 *
 */
@Controller
@RequestMapping(value = "/calculateFormula")
public class CalculateFormulaController extends BaseController {

	String menuUrl = "calculateFormula/list.do"; // 菜单地址(权限用)
	@Resource(name = "tmplconfigService")
	private TmplConfigManager tmplconfigService;
	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	@Resource(name = "tmplconfigdictService")
	private TmplConfigDictManager tmplconfigdictService;

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);

		List<PageData> listBase = tmplconfigService.listBase(page); // 列出TmplConfigBase列表
		List<PageData> treeSource = DictsUtil.getDepartmentSelectTreeSourceList(departmentService);
		if (treeSource != null && treeSource.size() > 0) {
			JSONArray arr = JSONArray.fromObject(treeSource);
			mv.addObject("zTreeNodes", null == arr ? "" : arr.toString());
			PageData rootDepart = treeSource.get(0);
			pd.put("rootDepartCode", rootDepart.getString("id"));
			pd.put("rootDepartName", rootDepart.getString("name"));
		}

		/*String dicTypeValus = DictsUtil.getDicTypeValue(tmplconfigdictService);
		String dictString = " : ;" + dicTypeValus;
		mv.addObject("dictString", dictString);*/
		
		mv.setViewName("calculateFormula/calculateFormula/calculateFormula_list");
		List<PageData> listBaseImport=new ArrayList<PageData>();
		for(PageData tableBase:listBase){
			if(tableBase.get("TABLE_NO").toString().equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
			||tableBase.get("TABLE_NO").toString().equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
			||tableBase.get("TABLE_NO").toString().equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
			||tableBase.get("TABLE_NO").toString().equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
			||tableBase.get("TABLE_NO").toString().equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
			||tableBase.get("TABLE_NO").toString().equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
			||tableBase.get("TABLE_NO").toString().equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())){
				listBaseImport.add(tableBase);
			}
		}
		mv.addObject("listBase", listBaseImport);
		// CUST_COL7 FMISACC 帐套字典
		mv.addObject("fmisacc", DictsUtil.getDictsByParentBianma(dictionariesService, "FMISACC"));
		String dicBillOff = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String dictBillOffString = " : ;" + dicBillOff;
		mv.addObject("dictBillOffString", dictBillOffString);
		
		mv.addObject("pd", pd);

		// 设置期间
		pd.put("KEY_CODE", "SystemDataTime");
		String busiDate = sysConfigManager.getSysConfigByKey(pd);
		pd.put("busiDate", busiDate);
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(Page page) throws Exception {
		PageData pd = this.getPageData();
		PageData tpd = tmplconfigService.findTableCodeByTableNo(pd);
		String tmplTableCode=tpd.getString("TABLE_CODE");
		pd.put("TABLE_CODE",tmplTableCode );
		String filters = pd.getString("filters"); // 多条件过滤条件
		if (null != filters && !"".equals(filters)) {
			pd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		page.setPd(pd);
		List<PageData> varList = tmplconfigService.listAll(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		return result;
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public @ResponseBody CommonBase edit() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername()+"修改SysConfig");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("oper").equals("edit")){
			try {
				pd.put("TABLE_CODE_ORI", DictsUtil.getActualTable(pd.getString("TABLE_CODE")));
				tmplconfigService.validateFormula(pd);
			} catch (Exception e) {
				commonBase.setCode(1);
				commonBase.setMessage("公式验证失败,请检查公式是否拼写正确.");
				return commonBase;
			}
			tmplconfigService.edit(pd);
			commonBase.setCode(0);
		}
		else if(pd.getString("oper").equals("add")){
			tmplconfigService.save(pd);
			commonBase.setCode(0);
		}
		else if(pd.getString("oper").equals("del")){
			String [] ids=pd.getString("id").split(",");
			if(ids.length==1)
				tmplconfigService.delete(pd);
			else
				tmplconfigService.deleteAll(ids);
			commonBase.setCode(0);
		}
		
		/**此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		//commonBase.setCode(-1);
		//commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
}
