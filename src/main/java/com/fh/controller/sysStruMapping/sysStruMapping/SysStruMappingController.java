package com.fh.controller.sysStruMapping.sysStruMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.Common;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.Message;
import com.fh.controller.common.SysStruMappingList;
import com.fh.controller.common.TmplUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.SysTableMapping;
import com.fh.entity.TableColumns;
import com.fh.entity.system.Dictionaries;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.TmplConfigDictManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.service.sysStruMapping.sysStruMapping.impl.SysStruMappingService;
import com.fh.service.sysTableMapping.sysTableMapping.impl.SysTableMappingService;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.enums.StruMappingBillState;
//import com.fh.util.enums.SysTableMapTableType;

import net.sf.json.JSONArray;

/**
 * 数据模板详情
 * 
 * @ClassName: SysStruMappingController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张晓柳
 * @date 2018年5月16日
 *
 */
@Controller
@RequestMapping(value = "/sysStruMapping")
public class SysStruMappingController extends BaseController {

	String menuUrl = "sysStruMapping/list.do"; // 菜单地址(权限用)
	@Resource(name = "sysStruMappingService")
	private SysStruMappingService sysStruMappingService;
	@Resource(name = "sysTableMappingService")
	private SysTableMappingService sysTableMappingService;
	
	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	@Resource(name="tmplconfigService")
	private TmplConfigService tmplconfigService;
	@Resource(name = "tmplconfigdictService")
	private TmplConfigDictManager tmplconfigdictService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;

	//页面显示数据的年月
	//String ssSystemDateTime = "";

	//表
	String TB_GEN_BUS_SUMMY_BILL = "TB_GEN_BUS_SUMMY_BILL";
	String TB_GEN_SUMMY = "TB_GEN_SUMMY";
	String TB_GEN_BUS_DETAIL = "TB_GEN_BUS_DETAIL";
	String BILL_STATE = "BILL_STATE";
	//界面查询字段
    List<String> QueryFeildList = Arrays.asList("TYPE_CODE", "BILL_OFF", "BUSI_DATE");
    //设置必定不用编辑的列            SERIAL_NO 设置字段类型是数字，但不管隐藏 或显示都必须保存的
    List<String> MustNotEditList = Arrays.asList("BILL_STATE", "BUSI_DATE", "TYPE_CODE", "BILL_OFF", "TABLE_NAME", "TABLE_NAME_MAPPING", "COL_MAPPING_CODE");
	
    //空格
	String strSplitBlank = " ";
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		PageData getPd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("sysStruMapping/sysStruMapping/sysStruMapping_list");
		//当前期间,取自tb_system_config的SystemDateTime字段
		String ssSystemDateTime = sysConfigManager.currentSection(getPd);
		mv.addObject("SystemDateTime", ssSystemDateTime);
		
		//BILL_OFF FMISACC 帐套字典
		mv.addObject("FMISACC", DictsUtil.getDictsByParentCode(dictionariesService, "FMISACC"));
		//TYPE_CODE PZTYPE 凭证字典
		mv.addObject("PZTYPE", DictsUtil.getDictsByParentCode(dictionariesService, "PZTYPE"));
		//TYPE_CODE MappingTable 对应表
		List<Dictionaries> listTableName = new ArrayList<Dictionaries>();
		listTableName.add(new Dictionaries("TB_GEN_BUS_DETAIL", "TB_GEN_BUS_DETAIL"));
		listTableName.add(new Dictionaries("TB_GEN_SUMMY", "TB_GEN_SUMMY"));
		listTableName.add(new Dictionaries("TB_GEN_BUS_SUMMY_BILL", "TB_GEN_BUS_SUMMY_BILL"));
		mv.addObject("MappingTable", listTableName);

		//BILL_OFF FMISACC 帐套字典
		String billOffValus = DictsUtil.getDicValue(dictionariesService, "FMISACC");
		String billOffStringAll = ":[All];" + billOffValus;
		String billOffStringSelect = ":;" + billOffValus;
		mv.addObject("billOffStrAll", billOffStringAll);
		mv.addObject("billOffStrSelect", billOffStringSelect);

		//TYPE_CODE PZTYPE 凭证字典
		String typeCodeValus = DictsUtil.getDicValue(dictionariesService, "PZTYPE");
		String typeCodeStringAll = ":[All];" + typeCodeValus;
		String typeCodeStringSelect = ":;" + typeCodeValus;
		mv.addObject("typeCodeStrAll", typeCodeStringAll);
		mv.addObject("typeCodeStrSelect", typeCodeStringSelect);

		String dicTypeValus = DictsUtil.getDicTypeValue(tmplconfigdictService);
		String dictStringAll = ":[All];" + dicTypeValus;
		String dictStringSelect = " : ;" + dicTypeValus;
		mv.addObject("dictStrAll", dictStringAll);
		mv.addObject("dictStrSelect", dictStringSelect);
		
		//BILL_STATE 列状态
		String billStateValus = "";
    	StruMappingBillState[] enums = StruMappingBillState.values();  
    	if(enums!=null){
            for (int i = 0; i < enums.length; i++) {  
    			if (billStateValus != null && !billStateValus.toString().trim().equals("")) {
    				billStateValus += ";";
    			}
    			billStateValus += enums[i].getNameKey() + ":" + enums[i].getNameValue();
            }  
    	}
		String billStateStringAll = ":[All];" + billStateValus;
		String billStateStringSelect = ":;" + billStateValus;
		mv.addObject("billStateStrAll", billStateStringAll);
		mv.addObject("billStateStrSelect", billStateStringSelect);

		mv.addObject("pd", getPd);
		return mv;
	}
	
	/**获取COL_CODE数据源
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getColCodeSource")
	public @ResponseBody CommonBase getColCodeSource() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//
		String SelectedStruMappingTableName = getPd.getString("SelectedStruMappingTableName");
		
		String strRet = "";
		List<SysTableMapping> getSysTableMappingList = SysStruMappingList.getUseTableMapping(SelectedTypeCode, SelectedBusiDate, SelectedCustCol7, TB_GEN_BUS_DETAIL, sysTableMappingService);
		if(getSysTableMappingList != null && getSysTableMappingList.size() == 1){
			SysTableMapping sysTableMapping = getSysTableMappingList.get(0);
			String struTableNameTranfer = "";
			//String strTableType = SysTableMapTableType.Table.getNameKey();
			if(SelectedStruMappingTableName.toUpperCase().equals(TB_GEN_BUS_DETAIL.toUpperCase())){
				struTableNameTranfer = sysTableMapping.getTABLE_NAME();
				//strTableType = sysTableMapping.getTABLE_TYPE();
			}
			if(SelectedStruMappingTableName.toUpperCase().equals(TB_GEN_SUMMY.toUpperCase())){
				struTableNameTranfer = TB_GEN_BUS_DETAIL;
			}
			if(SelectedStruMappingTableName.toUpperCase().equals(TB_GEN_BUS_SUMMY_BILL.toUpperCase())){
				struTableNameTranfer = TB_GEN_SUMMY;
			}
			/*// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
			List<TableColumns> tableColumns = null;
			if(strTableType!=null && !strTableType.trim().equals("")){
				if(strTableType.equals(SysTableMapTableType.Table.getNameKey()) 
						|| strTableType.equals(SysTableMapTableType.View.getNameKey())){
					// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
					tableColumns = tmplconfigService.getTableColumns(struTableNameTranfer);
				}
			}*/
			// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
			List<TableColumns> tableColumns = tmplconfigService.getTableColumns(struTableNameTranfer);
	    	if(tableColumns!=null){
	            for (TableColumns col: tableColumns) {  
	    			if (strRet != null && !strRet.toString().trim().equals("")) {
	    				strRet += ";";
	    			}
	    			//String Column_comment = col.getColumn_comment();
	    			//if(Column_comment!=null && Column_comment.contains(strSplitBlank)){
	    			//	Column_comment = Column_comment.split(strSplitBlank)[0];
	    			//}
	    			strRet += col.getColumn_name() + ":" + col.getColumn_name();//Column_comment + " " + 
	            }  
	    	}
		}
		commonBase.setMessage(strRet);
		commonBase.setCode(0);
		return commonBase;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList(JqPage page) throws Exception {
		PageData getPd = this.getPageData();
		//账套
		String SelectedCustCol7 = getPd.getString("SelectedCustCol7");
		//凭证字典
		String SelectedTypeCode = getPd.getString("SelectedTypeCode");
		//业务区间
		String SelectedBusiDate = getPd.getString("SelectedBusiDate");
		//
		String SelectedStruMappingTableName = getPd.getString("SelectedStruMappingTableName");
		
		List<PageData> varList = new ArrayList<PageData>();
		List<SysTableMapping> getSysTableMappingList = SysStruMappingList.getUseTableMapping(SelectedTypeCode, SelectedBusiDate, SelectedCustCol7, TB_GEN_BUS_DETAIL, sysTableMappingService);
		if(getSysTableMappingList != null && getSysTableMappingList.size() == 1){
			// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
			List<TableColumns> tableColumns = tmplconfigService.getTableColumns(SelectedStruMappingTableName);
			String struTableNameTranfer = "";
			if(SelectedStruMappingTableName.toUpperCase().equals(TB_GEN_BUS_DETAIL.toUpperCase())){
				struTableNameTranfer = getSysTableMappingList.get(0).getTABLE_NAME();
			}
			if(SelectedStruMappingTableName.toUpperCase().equals(TB_GEN_SUMMY.toUpperCase())){
				struTableNameTranfer = TB_GEN_BUS_DETAIL;
			}
			if(SelectedStruMappingTableName.toUpperCase().equals(TB_GEN_BUS_SUMMY_BILL.toUpperCase())){
				struTableNameTranfer = TB_GEN_SUMMY;
			}
			getPd.put("SelectedStruMappingTableName", SelectedStruMappingTableName);
			getPd.put("SelectedTableNameTranfer", struTableNameTranfer);
			page.setPd(getPd);
			List<PageData> struList = sysStruMappingService.JqPage(page);	//列出Betting列表
			
			List<TableColumns> tableColumnsNotInStruList = new ArrayList<TableColumns>();
			if(tableColumns!=null && tableColumns.size()>0){
				for(TableColumns tableCol : tableColumns){
					Boolean bolTableColNotInStruList = true;
					String strTableColumn_name = tableCol.getColumn_name().toUpperCase();
					if (struList != null && struList.size() > 0) {
						for (PageData struEach : struList) {
							String strStruMappingCode = struEach.getString("COL_MAPPING_CODE").toUpperCase();
							if(strTableColumn_name.equals(strStruMappingCode)){
								bolTableColNotInStruList = false;
								struEach.put(BILL_STATE, StruMappingBillState.Normal.getNameKey());
							}
						}
					}
					if(bolTableColNotInStruList){
						tableColumnsNotInStruList.add(tableCol);
					}
				}
			}
			if (struList != null && struList.size() > 0) {
				for (PageData struEach : struList) {
					Object strBILL_STATE = struEach.get(BILL_STATE);
					if(strBILL_STATE!=null && !strBILL_STATE.toString().trim().equals("") 
							&& strBILL_STATE.toString().equals(StruMappingBillState.Normal.getNameKey())){
						struEach.put(BILL_STATE, StruMappingBillState.Normal.getNameKey());
						varList.add(struEach);
					}
				}
			}
			if (struList != null && struList.size() > 0) {
				for (PageData struEach : struList) {
					Object strBILL_STATE = struEach.get(BILL_STATE);
					if(!(strBILL_STATE!=null && !strBILL_STATE.toString().trim().equals("") 
							&& strBILL_STATE.toString().equals(StruMappingBillState.Normal.getNameKey()))){
						struEach.put(BILL_STATE, StruMappingBillState.StruNotInTable.getNameKey());
						varList.add(struEach);
					}
				}
			}
			if(tableColumnsNotInStruList!=null && tableColumnsNotInStruList.size()>0){
				for(TableColumns tableColNotInStru : tableColumnsNotInStruList){
					PageData pdAdd = new PageData();
					pdAdd.put("TYPE_CODE__", SelectedTypeCode);//
				    pdAdd.put("BILL_OFF__", SelectedCustCol7);//
			        pdAdd.put("BUSI_DATE__", SelectedBusiDate);//
			        pdAdd.put("TABLE_NAME__", struTableNameTranfer);//
			        pdAdd.put("TABLE_NAME_MAPPING__", SelectedStruMappingTableName);//
			        pdAdd.put("COL_MAPPING_CODE__", tableColNotInStru.getColumn_name());//
					pdAdd.put(BILL_STATE, StruMappingBillState.TableNotInStru.getNameKey());
					
					pdAdd.put("TYPE_CODE", SelectedTypeCode);//业务类型	TYPE_CODE	' '	VARCHAR(20)	20		TRUE	FALSE	TRUE
					pdAdd.put("TABLE_NAME", struTableNameTranfer);//业务表	TABLE_NAME	业务表	' '	VARCHAR(30)	30		TRUE	FALSE	TRUE
					pdAdd.put("TABLE_NAME_MAPPING", SelectedStruMappingTableName);//映射业务表	TABLE_NAME_MAPPING	映射业务表	' '	VARCHAR(30)	30		FALSE	FALSE	TRUE
					pdAdd.put("COL_CODE", " ");//业务表列编码	COL_CODE	业务表列编码	’ ‘	VARCHAR(20)	20		FALSE	FALSE	TRUE
					pdAdd.put("COL_MAPPING_CODE", tableColNotInStru.getColumn_name());//映射列列编码	COL_MAPPING_CODE	映射列列编码	’ ‘	VARCHAR(20)	20		FALSE	FALSE	TRUE
					String strColMappingName = tableColNotInStru.getColumn_comment();
					if(strColMappingName!=null && strColMappingName.contains(strSplitBlank)){
						strColMappingName = strColMappingName.split(strSplitBlank)[0];
					}
					pdAdd.put("COL_MAPPING_NAME", strColMappingName);//映射名称	COL_MAPPING_NAME	映射名称	’ ‘	VARCHAR(30)	30		FALSE	FALSE	TRUE
					pdAdd.put("COL_VALUE", " ");//业务表列值	COL_VALUE	业务表列值	’ ‘	VARCHAR(40)	40		FALSE	FALSE	TRUE
					pdAdd.put("COL_MAPPING_VALUE", " ");//业务表列值	COL_MAPPING_VALUE	业务表列值	’ ‘	VARCHAR(40)	40		FALSE	FALSE	TRUE
					pdAdd.put("BILL_OFF", SelectedCustCol7);//账套	BILL_OFF	账套	‘ ’	VARCHAR(6)	6		TRUE	FALSE	TRUE
					pdAdd.put("BUSI_DATE", SelectedBusiDate);//业务期间	BUSI_DATE	业务期间	‘ ’	VARCHAR(8)	8		TRUE	FALSE	TRUE
					pdAdd.put("DISP_ORDER", 99);//映射列显示序号	DISP_ORDER	映射列显示序号	0	integer			FALSE	FALSE	TRUE
					pdAdd.put("COL_DGT", 15);//映射列位数	COL_DGT	映射列数值位数	10	integer			FALSE	FALSE	TRUE
					pdAdd.put("DEC_PRECISION", 0);//映射列小数位数	DEC_PRECISION	映射列小数位数		integer			FALSE	FALSE	TRUE
					if(Common.IsNumHavePointFeild(tableColNotInStru.getData_type())){
						pdAdd.put("DEC_PRECISION", 2);//映射列小数位数	DEC_PRECISION	映射列小数位数		integer			FALSE	FALSE	TRUE
					}
					pdAdd.put("DICT_TRANS", " ");//字典翻译	DICT_TRANS	字典翻译	' '	VARCHAR(30)	30		FALSE	FALSE	TRUE
					pdAdd.put("COL_HIDE", "0");//列隐藏	COL_HIDE	"列隐藏0隐藏 1显示 默认1"	'1'	CHAR(1)	1		FALSE	FALSE	TRUE
					pdAdd.put("COL_SUM", "0");//列汇总	COL_SUM	"列汇总1汇总 0不汇总,默认0"	'0'	CHAR(1)	1		FALSE	FALSE	TRUE
					pdAdd.put("COL_AVE", "0");//列平均值	COL_AVE	"列平均值0不计算 1计算 默认0"	'0'	CHAR(1)	1		FALSE	FALSE	TRUE
					pdAdd.put("COL_TRANSFER", "0");//列传输	COL_TRANSFER			VARCHAR(1)	1		FALSE	FALSE	TRUE 
					pdAdd.put("COL_ENABLE", "0");//列启用 COL_ENABLE 列启用 1启用0停用	'1'	CHAR(1)	1		FALSE	FALSE	TRUE
					varList.add(pdAdd);
				}
			}
		}
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		result.setRowNum(page.getRowNum());
		result.setPage(page.getPage());
		return result;
	}
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public @ResponseBody CommonBase save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增SysDeptMapping");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		
		PageData pd = this.getPageData();
		String oper = pd.getString("oper");
		if(oper.toUpperCase().equals(("edit").toUpperCase())){
			for(String strFeild : MustNotEditList){
				pd.put(strFeild, pd.get(strFeild + TmplUtil.keyExtra));
			}
		}
		String checkState = CheckTableMapState(pd);
		if(checkState!=null && !checkState.trim().equals("")){
			commonBase.setCode(2);
			commonBase.setMessage(checkState);
			return commonBase;
		}
		List<PageData> listData = new ArrayList<PageData>();
		listData.add(pd);
		sysStruMappingService.batchPartDelAndIns(listData);
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
		logBefore(logger, Jurisdiction.getUsername() + "批量");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData getPd = this.getPageData();
		
		Object DATA_ROWS = getPd.get("DataRows");
		String json = DATA_ROWS.toString();  
        JSONArray array = JSONArray.fromObject(json);  
        List<PageData> listData = (List<PageData>) JSONArray.toCollection(array,PageData.class);
        
		if(null != listData && listData.size() > 0){
        	for(PageData pd : listData){
        		for(String strFeild : MustNotEditList){
        			pd.put(strFeild, pd.get(strFeild + TmplUtil.keyExtra));
        		}
        	}
    		String checkState = CheckTableMapState(listData.get(0));
    		if(checkState!=null && !checkState.trim().equals("")){
    			commonBase.setCode(2);
    			commonBase.setMessage(checkState);
    			return commonBase;
    		}
			sysStruMappingService.batchAllUpdateDatabase(listData);
			commonBase.setCode(0);
		}
		return commonBase;
	}
	
	private String CheckTableMapState(PageData pd) throws Exception{
		String strRut = "";
		String TypeCode = "";
		String BusiDate = "";
		String CustCol7 = "";
		if(pd!=null){
			TypeCode = pd.getString("TYPE_CODE__");
			BusiDate = pd.getString("BUSI_DATE__");
			CustCol7 = pd.getString("BILL_OFF__");
		}
		List<SysTableMapping> getSysTableMappingList = SysStruMappingList.getUseTableMapping(TypeCode, BusiDate, CustCol7, TB_GEN_BUS_DETAIL, sysTableMappingService);
		if(!(getSysTableMappingList != null && getSysTableMappingList.size() == 1)){
			strRut = Message.OperDataAlreadyChange;
		}
		return strRut;
	}
}
