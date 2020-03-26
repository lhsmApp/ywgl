package com.fh.controller.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.TmplConfigDictManager;
import com.fh.service.tmplconfig.tmplconfig.TmplConfigManager;
import com.fh.util.PageData;

/**
 * 模板通用类
 * 
 * @ClassName: TmplUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangxiaoliu
 * @date 2017年6月23日
 *
 */
public class TmplUtil {

	private static TmplConfigManager tmplconfigService;
	private TmplConfigDictManager tmplConfigDictService;
	private DictionariesManager dictionariesService;
	private DepartmentManager departmentService;
	private UserManager userService;

	// 查询表的主键字段后缀，区别于主键字段，用于修改或删除
	public static String keyExtra = "__";
	// 查询表的主键字段
	private List<String> keyList = new ArrayList<String>();
	// 底行显示的求和与平均值字段
	StringBuilder m_sqlUserdata = new StringBuilder();

	public StringBuilder getSqlUserdata() {
		return m_sqlUserdata;
	}

	// 界面分组字段
	private List<String> jqGridGroupColumn = new ArrayList<String>();
	// 分组字段是否显示在表中
	List<String> m_jqGridGroupColumnShow = new ArrayList<String>();

	public List<String> getJqGridGroupColumnShow() {
		return m_jqGridGroupColumnShow;
	}

	// 字典
	private Map<String, Object> m_dicList = new LinkedHashMap<String, Object>();

	public Map<String, Object> getDicList() {
		return m_dicList;
	}

	// 界面分组 数值型但不求和字段
	private List<String> jqGridGroupNotSumFeild = new ArrayList<String>();

	// 表结构
	// private Map<String, TableColumns> map_HaveColumnsList = new
	// LinkedHashMap<String, TableColumns>();
	// public Map<String, TableColumns> getHaveColumnsList() {
	// return map_HaveColumnsList;
	// }

	// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
	// private Map<String, TmplConfigDetail> map_SetColumnsList = new
	// LinkedHashMap<String, TmplConfigDetail>();
	// public Map<String, TmplConfigDetail> getSetColumnsList() {
	// return map_SetColumnsList;
	// }

	// 另加的列、配置模板之外的列
	private String AdditionalReportColumns = "";
	private List<String> MustInputList = new ArrayList<String>();

	public TmplUtil(TmplConfigManager tmplconfigService, TmplConfigDictManager tmplConfigDictService,
			DictionariesManager dictionariesService, DepartmentManager departmentService, UserManager userService) {
		TmplUtil.tmplconfigService = tmplconfigService;
		this.tmplConfigDictService = tmplConfigDictService;
		this.dictionariesService = dictionariesService;
		this.departmentService = departmentService;
		this.userService = userService;
		this.jqGridGroupColumn = new ArrayList<String>();
		InitJqGridGroupColumnShow();
		// 界面分组 数值型但不求和字段
		this.jqGridGroupNotSumFeild = new ArrayList<String>();
		this.AdditionalReportColumns = "";
	}

	public TmplUtil(TmplConfigManager tmplconfigService, TmplConfigDictManager tmplConfigDictService,
			DictionariesManager dictionariesService, DepartmentManager departmentService, UserManager userService,
			List<String> keyList, List<String> jqGridGroupColumn, String AdditionalReportColumns,
			List<String> MustInputList, List<String> jqGridGroupNotSumFeild) {
		TmplUtil.tmplconfigService = tmplconfigService;
		this.tmplConfigDictService = tmplConfigDictService;
		this.dictionariesService = dictionariesService;
		this.departmentService = departmentService;
		this.userService = userService;
		this.keyList = keyList;
		this.jqGridGroupColumn = jqGridGroupColumn;
		InitJqGridGroupColumnShow();
		// 界面分组 数值型但不求和字段
		this.jqGridGroupNotSumFeild = jqGridGroupNotSumFeild;
		this.AdditionalReportColumns = AdditionalReportColumns;
		this.MustInputList = MustInputList;

	}

	// 分组字段是否显示在表中
	private void InitJqGridGroupColumnShow() {
		m_jqGridGroupColumnShow = new ArrayList<String>();
		if (jqGridGroupColumn != null) {
			int size = jqGridGroupColumn.size();
			for (int i = 0; i < size; i++) {
				m_jqGridGroupColumnShow.add(String.valueOf(true));
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructure(String kpiCode, String departCode, String billOff, int columnCount,
			List<String> MustNotEditFeildList) throws Exception {
		// 分组字段是否显示在表中
		InitJqGridGroupColumnShow();
		// 字典
		m_dicList = new LinkedHashMap<String, Object>();
		// 表结构
		// map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		// map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
		// 底行显示的求和与平均值字段
		m_sqlUserdata = new StringBuilder();

		PageData pd = new PageData();
		pd.put("KPI_CODE", kpiCode);
		PageData pdResult = tmplconfigService.findTableCodeByKpiCode(pd);
		String tableCodeTmpl = pdResult.getString("TABLE_CODE");
		String tableCodeOri = DictsUtil.getActualTable(tableCodeTmpl);// 数据库真实业务数据表
		String returnString=executeGenerateStructure(tableCodeOri,tableCodeTmpl,departCode,billOff,columnCount,
				MustNotEditFeildList);
		return returnString;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructureByTableCode(String tableCodeTmpl,String departCode, String billOff, int columnCount,
			List<String> MustNotEditFeildList) throws Exception {
		// 分组字段是否显示在表中
		InitJqGridGroupColumnShow();
		// 字典
		m_dicList = new LinkedHashMap<String, Object>();
		// 表结构
		// map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		// map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();
		// 底行显示的求和与平均值字段
		m_sqlUserdata = new StringBuilder();

		PageData pd = new PageData();
		
		String tableCodeOri = DictsUtil.getActualTable(tableCodeTmpl);// 数据库真实业务数据表
		String returnString=executeGenerateStructure(tableCodeOri,tableCodeTmpl,departCode,billOff,columnCount,
				MustNotEditFeildList);
		return returnString;
	}

	private String executeGenerateStructure(String tableCodeOri,String tableCodeTmpl, String departCode, String billOff, int columnCount,
			List<String> MustNotEditFeildList) throws Exception {
		// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
		List<TableColumns> tableColumns = tmplconfigService.getTableColumns(tableCodeOri);
		Map<String, Map<String, Object>> listColModelAll = jqGridColModelAll(tableColumns);
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		List<TmplConfigDetail> m_columnsList = Common.getShowColumnList(tableCodeTmpl, departCode, billOff,
				tmplconfigService);
		int row = 1;
		int col = 1;

		StringBuilder jqGridColModelCustom = new StringBuilder();
		// 添加配置表设置列，字典（未设置就使用表默认，text或number）、隐藏、表头显示
		if (m_columnsList != null && m_columnsList.size() > 0) {
			for (int i = 0; i < m_columnsList.size(); i++) {
				String getCOL_CODE = m_columnsList.get(i).getCOL_CODE();
				// map_SetColumnsList.put(getCOL_CODE, m_columnsList.get(i));
				if (listColModelAll.containsKey(getCOL_CODE.toUpperCase())) {
					Map<String, Object> itemColModel = listColModelAll.get(getCOL_CODE);
					String name = (String) itemColModel.get("name");
					String edittype = (String) itemColModel.get("edittype");
					// String notedit = (String) itemColModel.get("notedit");
					// Boolean editable = (Boolean)
					// itemColModel.get("editable");
					if (name != null && !name.trim().equals("")) {
						if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
							jqGridColModelCustom.append(", ");
						}
						jqGridColModelCustom.append("{");
						jqGridColModelCustom.append(name).append(", ");
					} else {
						continue;
					}
					// 配置表中的字典
					if (m_columnsList.get(i).getDICT_TRANS() != null
							&& !m_columnsList.get(i).getDICT_TRANS().trim().equals("")) {
						String strDicValue = Common.getDicValue(m_dicList, m_columnsList.get(i).getDICT_TRANS(), //
								tmplConfigDictService, dictionariesService, departmentService, userService,
								AdditionalReportColumns);
						String strSelectValue = ":";
						if (strDicValue != null && !strDicValue.trim().equals("")) {
							strSelectValue += ";" + strDicValue;
						}
						// 选择
						jqGridColModelCustom.append(" edittype:'select', ");
						jqGridColModelCustom.append(" editoptions:{value:'" + strSelectValue + "'}, ");
						// 翻译
						jqGridColModelCustom.append(" formatter: 'select', ");
						jqGridColModelCustom.append(" formatoptions: {value: '" + strDicValue + "'}, ");
						// 查询
						jqGridColModelCustom.append(" stype: 'select', ");
						jqGridColModelCustom.append(" searchoptions: {value: ':[All];" + strDicValue + "'}, ");
					} else {
						if (edittype != null && !edittype.trim().equals("")) {
							jqGridColModelCustom.append(edittype).append(", ");
						}
					}
					// 设置必定不用编辑的列
					// 配置表中的隐藏
					int intHide = Integer.parseInt(m_columnsList.get(i).getCOL_HIDE());
					jqGridColModelCustom.append(" hidden: ").append(intHide == 1 ? "false" : "true").append(", ");
					// intHide != 1 隐藏
					if (intHide != 1) {
						jqGridColModelCustom.append(" editable:true, editrules: {edithidden: false}, ");
						if (jqGridGroupColumn != null) {
							int groupColumnSize = jqGridGroupColumn.size();
							for (int intGroupColumn = 0; intGroupColumn < groupColumnSize; intGroupColumn++) {
								if (jqGridGroupColumn.get(intGroupColumn).toUpperCase()
										.equals(m_columnsList.get(i).getCOL_CODE().toUpperCase())) {
									m_jqGridGroupColumnShow.set(intGroupColumn, String.valueOf(false));
								}
							}
						}
					} else {
						if (MustNotEditFeildList.contains(getCOL_CODE)) {
							jqGridColModelCustom.append(" editable: false, editrules: {edithidden: false}, ");
						} else {
							jqGridColModelCustom.append(" editable: true, ");
							jqGridColModelCustom.append(" formoptions:{ rowpos:" + row + ", colpos:" + col + " }, ");
							col++;
							if (col > columnCount) {
								row++;
								col = 1;
							}
						}
					}
					// 底行显示的求和与平均值字段
					// 1汇总 0不汇总,默认0
					if (Integer.parseInt(m_columnsList.get(i).getCOL_SUM()) == 1) {
						if (m_sqlUserdata != null && !m_sqlUserdata.toString().trim().equals("")) {
							m_sqlUserdata.append(", ");
						}
						m_sqlUserdata.append(" sum(" + getCOL_CODE + ") " + getCOL_CODE);
					}
					// 0不计算 1计算 默认0
					else if (Integer.parseInt(m_columnsList.get(i).getCOL_AVE()) == 1) {
						if (m_sqlUserdata != null && !m_sqlUserdata.toString().trim().equals("")) {
							m_sqlUserdata.append(", ");
						}
						m_sqlUserdata
								.append(" round(avg(" + getCOL_CODE + "), 2) " + m_columnsList.get(i).getCOL_CODE());
					}
					// 配置表中的表头显示
					jqGridColModelCustom.append(" label: '").append(m_columnsList.get(i).getCOL_NAME()).append("' ");

					jqGridColModelCustom.append("}");
				}
			}
		}
		StringBuilder jqGridColModelKey = new StringBuilder();
		row++;
		col = 1;
		// 添加关键字的保存列
		if (keyList != null && keyList.size() > 0) {
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
					jqGridColModelKey.append(", ");
				}
				jqGridColModelKey.append(" {name: '").append(key.toUpperCase()).append(keyExtra)
						.append("', hidden: true, editable: true, editrules: {edithidden: false}, ");
				jqGridColModelKey.append(" formoptions:{ rowpos:" + row + ", colpos:" + col + " }} ");
				col++;
				if (col > columnCount) {
					row++;
					col = 1;
				}
			}
		}
		// 拼接真正设置的jqGrid的ColModel
		StringBuilder sbJqGridColModelAll = new StringBuilder();
		sbJqGridColModelAll.append("[");
		sbJqGridColModelAll.append(jqGridColModelCustom);
		if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
			if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
				sbJqGridColModelAll.append(", ");
				sbJqGridColModelAll.append(jqGridColModelKey);
			}
		}
		sbJqGridColModelAll.append("]");
		return sbJqGridColModelAll.toString();
	}

	/**
	 * 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
	 * 
	 * @param columns
	 * @return
	 * @throws Exception
	 */
	private Map<String, Map<String, Object>> jqGridColModelAll(List<TableColumns> columns) throws Exception {
		Map<String, Map<String, Object>> list = new LinkedHashMap<String, Map<String, Object>>();

		for (TableColumns col : columns) {

			Map<String, Object> MapAdd = new LinkedHashMap<String, Object>();

			StringBuilder model_name = new StringBuilder();
			StringBuilder model_edittype = new StringBuilder();
			// StringBuilder model_notedit = new StringBuilder();
			// Boolean editable = null;

			int intLength = Common.getColumnLength(col.getColumn_type(), col.getData_type());
			if (col.getData_type() != null && Common.IsNumFeild(col.getData_type())) {
				model_name.append(" width: '150', ");
				model_name.append(" align: 'right', search: false, sorttype: 'number', editrules: {number: true}, ");
				model_edittype.append(" edittype:'text', formatter: 'number', editoptions:{maxlength:'" + intLength
						+ "', number: true} ");
			} else {
				if (intLength > 50) {
					model_name.append(" width: '200', ");
					model_edittype.append(" edittype:'textarea', ");
				} else {
					model_name.append(" width: '130', ");
					model_edittype.append(" edittype:'text', ");
				}
				model_edittype.append(" editoptions:{maxlength:'" + intLength + "'} ");
			}

			if (MustInputList != null && MustInputList.contains(col.getColumn_name())) {
				model_name.append(" editrules:{required:true}, ");
			}
			model_name.append(" name: '" + col.getColumn_name() + "' ");

			MapAdd.put("name", model_name.toString());
			MapAdd.put("edittype", model_edittype.toString());
			list.put(col.getColumn_name(), MapAdd);
		}

		return list;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructureNoEdit(String kpiCode, String departCode, String billOff) throws Exception {
		PageData pd = new PageData();
		pd.put("KPI_CODE", kpiCode);
		PageData pdResult = tmplconfigService.findTableCodeByKpiCode(pd);
		String tableCodeTmpl = pdResult.getString("TABLE_CODE");
		return executeGenerateStructureNoEdit(tableCodeTmpl,departCode,billOff);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructureNoEditByTableCode(String tableCodeTmpl, String departCode, String billOff) throws Exception {
		return executeGenerateStructureNoEdit(tableCodeTmpl,departCode,billOff);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String executeGenerateStructureNoEdit(String tableCodeTmpl, String departCode, String billOff) throws Exception {
		// 分组字段是否显示在表中
		InitJqGridGroupColumnShow();
		// 底行显示的求和与平均值字段
		m_sqlUserdata = new StringBuilder();
		// 字典
		m_dicList = new LinkedHashMap<String, Object>();
		// 表结构
		// map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		// map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

		Boolean bolGroupTotalShow = false;

		StringBuilder jqGridColModelAdditionalColumns = new StringBuilder();
		if (AdditionalReportColumns != null && !AdditionalReportColumns.trim().equals("")) {
			if (jqGridColModelAdditionalColumns != null
					&& !jqGridColModelAdditionalColumns.toString().trim().equals("")) {
				jqGridColModelAdditionalColumns.append(", ");
			}
			jqGridColModelAdditionalColumns.append(" { ");
			jqGridColModelAdditionalColumns.append(" name: '").append(AdditionalReportColumns).append("', ");
			jqGridColModelAdditionalColumns.append(" label: 'FMIS凭证号', ");
			if (!bolGroupTotalShow) {
				jqGridColModelAdditionalColumns.append(" summaryType:'count', summaryTpl:'<b>({0})total</b>', ");
				bolGroupTotalShow = true;
			}
			jqGridColModelAdditionalColumns.append(" hidden: false, editable: false ");
			jqGridColModelAdditionalColumns.append(" } ");
		}

		String tableCodeOri = DictsUtil.getActualTable(tableCodeTmpl);
		// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
		List<TableColumns> tableColumns = tmplconfigService.getTableColumns(tableCodeOri);
		Map<String, Map<String, Object>> listColModelAll = jqGridColModelAllNoEdit(tableColumns);
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		List<TmplConfigDetail> m_columnsList = Common.getShowColumnList(tableCodeTmpl, departCode, billOff,
				tmplconfigService);

		StringBuilder jqGridColModelCustom = new StringBuilder();
		// 添加配置表设置列，字典（未设置就使用表默认，text或number）、隐藏、表头显示
		if (m_columnsList != null && m_columnsList.size() > 0) {
			for (int i = 0; i < m_columnsList.size(); i++) {
				// map_SetColumnsList.put(m_columnsList.get(i).getCOL_CODE(),
				// m_columnsList.get(i));
				if (listColModelAll.containsKey(m_columnsList.get(i).getCOL_CODE().toUpperCase())) {
					Map<String, Object> itemColModel = listColModelAll.get(m_columnsList.get(i).getCOL_CODE());
					String name = (String) itemColModel.get("name");
					String group = (String) itemColModel.get("group");
					if (name != null && !name.trim().equals("")) {
						if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
							jqGridColModelCustom.append(", ");
						}
						jqGridColModelCustom.append("{");
						jqGridColModelCustom.append(name).append(", ").append(" editable: false, ");
					} else {
						continue;
					}

					// 配置表中的字典
					if (m_columnsList.get(i).getDICT_TRANS() != null
							&& !m_columnsList.get(i).getDICT_TRANS().trim().equals("")) {
						String strDicValue = Common.getDicValue(m_dicList, m_columnsList.get(i).getDICT_TRANS(), //
								tmplConfigDictService, dictionariesService, departmentService, userService,
								AdditionalReportColumns);

						String strSelectValue = ":";
						if (strDicValue != null && !strDicValue.trim().equals("")) {
							strSelectValue += ";" + strDicValue;
						}
						// 选择
						jqGridColModelCustom.append(" edittype:'select', ");
						jqGridColModelCustom.append(" editoptions:{value:'" + strSelectValue + "'}, ");
						// 翻译
						jqGridColModelCustom.append(" formatter: 'select', ");
						jqGridColModelCustom.append(" formatoptions: {value: '" + strDicValue + "'}, ");
						// 查询
						jqGridColModelCustom.append(" stype: 'select', ");
						jqGridColModelCustom.append(" searchoptions: {value: ':[All];" + strDicValue + "'}, ");
					}
					// 配置表中的隐藏
					int intHide = Integer.parseInt(m_columnsList.get(i).getCOL_HIDE());
					jqGridColModelCustom.append(" hidden: ").append(intHide == 1 ? "false" : "true").append(", ");
					// intHide != 1 隐藏
					if (intHide != 1) {
						if (jqGridGroupColumn != null) {
							int groupColumnSize = jqGridGroupColumn.size();
							for (int intGroupColumn = 0; intGroupColumn < groupColumnSize; intGroupColumn++) {
								if (jqGridGroupColumn.get(intGroupColumn).toUpperCase()
										.equals(m_columnsList.get(i).getCOL_CODE().toUpperCase())) {
									m_jqGridGroupColumnShow.set(intGroupColumn, String.valueOf(false));
								}
							}
						}
					} else {
						if (!bolGroupTotalShow) {
							jqGridColModelCustom.append(" summaryType:'count', summaryTpl:'<b>({0})total</b>', ");
							bolGroupTotalShow = true;
						}
					}
					// 底行显示的求和与平均值字段
					// 1汇总 0不汇总,默认0
					if (Integer.parseInt(m_columnsList.get(i).getCOL_SUM()) == 1) {
						if (m_sqlUserdata != null && !m_sqlUserdata.toString().trim().equals("")) {
							m_sqlUserdata.append(", ");
						}
						m_sqlUserdata.append(" sum(" + m_columnsList.get(i).getCOL_CODE() + ") "
								+ m_columnsList.get(i).getCOL_CODE());
						jqGridColModelCustom.append(" summaryType:'sum', summaryTpl:'<b>{0}</b>', ");
					}
					// 0不计算 1计算 默认0
					else if (Integer.parseInt(m_columnsList.get(i).getCOL_AVE()) == 1) {
						if (m_sqlUserdata != null && !m_sqlUserdata.toString().trim().equals("")) {
							m_sqlUserdata.append(", ");
						}
						m_sqlUserdata.append(" round(avg(" + m_columnsList.get(i).getCOL_CODE() + "), 2) "
								+ m_columnsList.get(i).getCOL_CODE());
						jqGridColModelCustom.append(" summaryType:'avg', summaryTpl:'<b>{0}</b>', ");
					} else {
						if (!jqGridGroupNotSumFeild.contains(m_columnsList.get(i).getCOL_CODE())) {
							jqGridColModelCustom.append(group);
						}
					}
					// 配置表中的表头显示
					jqGridColModelCustom.append(" label: '").append(m_columnsList.get(i).getCOL_NAME()).append("' ");

					jqGridColModelCustom.append("}");
				}
			}
		}

		StringBuilder jqGridColModelKey = new StringBuilder();
		// 添加关键字的保存列
		if (keyList != null && keyList.size() > 0) {
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
					jqGridColModelKey.append(", ");
				}
				jqGridColModelKey.append(" {name: '").append(key.toUpperCase()).append(keyExtra)
						.append("', hidden: true, editable: false} ");
			}
		}
		// 拼接真正设置的jqGrid的ColModel
		StringBuilder sbJqGridColModelAll = new StringBuilder();
		sbJqGridColModelAll.append("[");
		sbJqGridColModelAll.append(jqGridColModelAdditionalColumns);
		if (jqGridColModelAdditionalColumns != null && !jqGridColModelAdditionalColumns.toString().trim().equals("")) {
			if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
				sbJqGridColModelAll.append(", ");
			}
		}
		sbJqGridColModelAll.append(jqGridColModelCustom);
		if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
			if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
				sbJqGridColModelAll.append(", ");
				sbJqGridColModelAll.append(jqGridColModelKey);
			}
		}
		sbJqGridColModelAll.append("]");
		return sbJqGridColModelAll.toString();
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructureNoEditSpecial(String tableCode, String typeCode, String billOff, String busiDate)
			throws Exception {
		// 底行显示的求和与平均值字段
		m_sqlUserdata = new StringBuilder();
		// 字典
		m_dicList = new LinkedHashMap<String, Object>();
		// 表结构
		// map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		// map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

		Boolean bolGroupTotalShow = false;

		// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
		List<TableColumns> tableColumns = tmplconfigService.getTableColumns(tableCode);
		Map<String, Map<String, Object>> listColModelAll = jqGridColModelAllNoEdit(tableColumns);
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		List<PageData> m_columnsList = Common.getShowColumnListSpecial(tableCode, busiDate, billOff, typeCode,
				tmplconfigService);

		StringBuilder jqGridColModelCustom = new StringBuilder();
		// 添加配置表设置列，字典（未设置就使用表默认，text或number）、隐藏、表头显示
		if (m_columnsList != null && m_columnsList.size() > 0) {
			for (int i = 0; i < m_columnsList.size(); i++) {
				// map_SetColumnsList.put(m_columnsList.get(i).getCOL_CODE(),
				// m_columnsList.get(i));
				if (listColModelAll.containsKey(m_columnsList.get(i).getString("COL_MAPPING_CODE").toUpperCase())) {
					Map<String, Object> itemColModel = listColModelAll
							.get(m_columnsList.get(i).getString("COL_MAPPING_CODE"));
					String name = (String) itemColModel.get("name");
					String group = (String) itemColModel.get("group");
					if (name != null && !name.trim().equals("")) {
						if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
							jqGridColModelCustom.append(", ");
						}
						jqGridColModelCustom.append("{");
						jqGridColModelCustom.append(name).append(", ").append(" editable: false, ");
					} else {
						continue;
					}
					// 配置表中的字典
					if (m_columnsList.get(i).getString("DICT_TRANS") != null
							&& !m_columnsList.get(i).getString("DICT_TRANS").trim().equals("")) {
						String strDicValue = Common.getDicValue(m_dicList, m_columnsList.get(i).getString("DICT_TRANS"), //
								tmplConfigDictService, dictionariesService, departmentService, userService,
								AdditionalReportColumns);

						String strSelectValue = ":";
						if (strDicValue != null && !strDicValue.trim().equals("")) {
							strSelectValue += ";" + strDicValue;
						}
						// 选择
						jqGridColModelCustom.append(" edittype:'select', ");
						jqGridColModelCustom.append(" editoptions:{value:'" + strSelectValue + "'}, ");
						// 翻译
						jqGridColModelCustom.append(" formatter: 'select', ");
						jqGridColModelCustom.append(" formatoptions: {value: '" + strDicValue + "'}, ");
						// 查询
						jqGridColModelCustom.append(" stype: 'select', ");
						jqGridColModelCustom.append(" searchoptions: {value: ':[All];" + strDicValue + "'}, ");
					}
					// 配置表中的隐藏
					int intHide = Integer.parseInt(m_columnsList.get(i).getString("COL_HIDE"));
					jqGridColModelCustom.append(" hidden: ").append(intHide == 1 ? "false" : "true").append(", ");
					// intHide != 1 隐藏
					if (intHide != 1) {
						if (jqGridGroupColumn != null) {
							int groupColumnSize = jqGridGroupColumn.size();
							for (int intGroupColumn = 0; intGroupColumn < groupColumnSize; intGroupColumn++) {
								if (jqGridGroupColumn.get(intGroupColumn).toUpperCase()
										.equals(m_columnsList.get(i).getString("COL_MAPPING_CODE").toUpperCase())) {
									m_jqGridGroupColumnShow.set(intGroupColumn, String.valueOf(false));
								}
							}
						}
					} else {
						if (!bolGroupTotalShow) {
							jqGridColModelCustom.append(" summaryType:'count', summaryTpl:'<b>({0})total</b>', ");
							bolGroupTotalShow = true;
						}
					}
					// 底行显示的求和与平均值字段
					// 1汇总 0不汇总,默认0
					if (Integer.parseInt(m_columnsList.get(i).getString("COL_SUM")) == 1) {
						if (m_sqlUserdata != null && !m_sqlUserdata.toString().trim().equals("")) {
							m_sqlUserdata.append(", ");
						}
						m_sqlUserdata.append(" sum(" + m_columnsList.get(i).getString("COL_MAPPING_CODE") + ") "
								+ m_columnsList.get(i).getString("COL_MAPPING_CODE"));
						jqGridColModelCustom.append(" summaryType:'sum', summaryTpl:'<b>{0}</b>', ");
					}
					// 0不计算 1计算 默认0
					else if (Integer.parseInt(m_columnsList.get(i).getString("COL_AVE")) == 1) {
						if (m_sqlUserdata != null && !m_sqlUserdata.toString().trim().equals("")) {
							m_sqlUserdata.append(", ");
						}
						m_sqlUserdata.append(" round(avg(" + m_columnsList.get(i).getString("COL_MAPPING_CODE")
								+ "), 2) " + m_columnsList.get(i).getString("COL_MAPPING_CODE"));
						jqGridColModelCustom.append(" summaryType:'avg', summaryTpl:'<b>{0}</b>', ");
					} else {
						if (!jqGridGroupNotSumFeild.contains(m_columnsList.get(i).getString("COL_MAPPING_CODE"))) {
							jqGridColModelCustom.append(group);
						}
					}
					// 配置表中的表头显示
					jqGridColModelCustom.append(" label: '").append(m_columnsList.get(i).getString("COL_MAPPING_NAME"))
							.append("' ");

					jqGridColModelCustom.append("}");
				}
			}
		}

		StringBuilder jqGridColModelKey = new StringBuilder();
		// 添加关键字的保存列
		if (keyList != null && keyList.size() > 0) {
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
					jqGridColModelKey.append(", ");
				}
				jqGridColModelKey.append(" {name: '").append(key.toUpperCase()).append(keyExtra)
						.append("', hidden: true, editable: false} ");
			}
		}
		// 拼接真正设置的jqGrid的ColModel
		StringBuilder sbJqGridColModelAll = new StringBuilder();
		sbJqGridColModelAll.append("[");
		sbJqGridColModelAll.append(jqGridColModelCustom);
		if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
			if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
				sbJqGridColModelAll.append(", ");
				sbJqGridColModelAll.append(jqGridColModelKey);
			}
		}
		sbJqGridColModelAll.append("]");
		return sbJqGridColModelAll.toString();
	}

	/**
	 * 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
	 * 
	 * @param columns
	 * @return
	 * @throws Exception
	 */
	private Map<String, Map<String, Object>> jqGridColModelAllNoEdit(List<TableColumns> columns) throws Exception {
		Map<String, Map<String, Object>> list = new LinkedHashMap<String, Map<String, Object>>();

		for (TableColumns col : columns) {

			Map<String, Object> MapAdd = new LinkedHashMap<String, Object>();

			StringBuilder model_name = new StringBuilder();
			StringBuilder model_group = new StringBuilder();

			int intLength = Common.getColumnLength(col.getColumn_type(), col.getData_type());
			if (col.getData_type() != null && Common.IsNumFeild(col.getData_type())) {
				if(col.getData_type().equals("INT")){
					model_name.append(" width: '90', ");
					model_name.append(" align: 'right', search: false, sorttype: 'number', ");
					model_group.append(" summaryTpl: '<b>{0}</b>', summaryType: 'sum', ");
				}else{
					model_name.append(" width: '150', ");
					model_name.append(" align: 'right', search: false, sorttype: 'number', formatter: 'number', ");
					model_group.append(" summaryTpl: '<b>{0}</b>', summaryType: 'sum', ");
				}
				
			} else {
				if (intLength > 50) {
					model_name.append(" width: '200', ");
				} else {
					model_name.append(" width: '130', ");
				}
			}
			model_name.append(" name: '" + col.getColumn_name() + "' ");
			MapAdd.put("name", model_name.toString());
			MapAdd.put("group", model_group.toString());
			list.put(col.getColumn_name(), MapAdd);
		}
		return list;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructureAccount(String kpiCode, String departCode, String billOff) throws Exception {
		// 分组字段是否显示在表中
		InitJqGridGroupColumnShow();
		// 底行显示的求和与平均值字段
		m_sqlUserdata = new StringBuilder();
		// 字典
		m_dicList = new LinkedHashMap<String, Object>();
		// 表结构
		// map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		// map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

		PageData pd = new PageData();
		pd.put("KPI_CODE", kpiCode);
		PageData pdResult = tmplconfigService.findTableCodeByKpiCode(pd);
		String tableCodeTmpl = pdResult.getString("TABLE_CODE");
		String tableCodeOri = DictsUtil.getActualTable(tableCodeTmpl);// 数据库真实业务数据表

		// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
		List<TableColumns> tableColumns = tmplconfigService.getTableColumns(tableCodeOri);
		Map<String, Map<String, Object>> listColModelAll = jqGridColModelAccount(tableColumns);
		// 前端数据表格界面字段,动态取自tb_tmpl_config_detail，根据当前单位编码及表名获取字段配置信息
		List<TmplConfigDetail> m_columnsList = Common.getShowColumnList(tableCodeTmpl, departCode, billOff,
				tmplconfigService);

		StringBuilder jqGridColModelCustom = new StringBuilder();
		// 添加配置表设置列，字典（未设置就使用表默认，text或number）、隐藏、表头显示
		if (m_columnsList != null && m_columnsList.size() > 0) {
			for (int i = 0; i < m_columnsList.size(); i++) {
				// map_SetColumnsList.put(m_columnsList.get(i).getCOL_CODE(),
				// m_columnsList.get(i));
				if (listColModelAll.containsKey(m_columnsList.get(i).getCOL_CODE().toUpperCase())) {
					Map<String, Object> itemColModel = listColModelAll.get(m_columnsList.get(i).getCOL_CODE());
					String name = (String) itemColModel.get("name");
					if (name != null && !name.trim().equals("")) {
						if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
							jqGridColModelCustom.append(", ");
						}
						jqGridColModelCustom.append("{");
						jqGridColModelCustom.append(name).append(", ");
					} else {
						continue;
					}
					// 配置表中的字典
					if (m_columnsList.get(i).getDICT_TRANS() != null
							&& !m_columnsList.get(i).getDICT_TRANS().trim().equals("")) {
						String strDicValue = Common.getDicValue(m_dicList, m_columnsList.get(i).getDICT_TRANS(), //
								tmplConfigDictService, dictionariesService, departmentService, userService,
								AdditionalReportColumns);

						String strSelectValue = ":";
						if (strDicValue != null && !strDicValue.trim().equals("")) {
							strSelectValue += ";" + strDicValue;
						}
						// 选择
						jqGridColModelCustom.append(" edittype:'select', ");
						jqGridColModelCustom.append(" editoptions:{value:'" + strSelectValue + "'}, ");
						// 翻译
						jqGridColModelCustom.append(" formatter: 'select', ");
						jqGridColModelCustom.append(" formatoptions: {value: '" + strDicValue + "'}, ");
						// 查询
						jqGridColModelCustom.append(" stype: 'select', ");
						jqGridColModelCustom.append(" searchoptions: {value: ':[All];" + strDicValue + "'}, ");
					}
					// 配置表中的隐藏
					int intHide = Integer.parseInt(m_columnsList.get(i).getCOL_HIDE());
					jqGridColModelCustom.append(" hidden: ").append(intHide == 1 ? "false" : "true").append(", ");
					// intHide != 1 隐藏
					if (intHide != 1) {
						if (jqGridGroupColumn != null) {
							int groupColumnSize = jqGridGroupColumn.size();
							for (int intGroupColumn = 0; intGroupColumn < groupColumnSize; intGroupColumn++) {
								if (jqGridGroupColumn.get(intGroupColumn).toUpperCase()
										.equals(m_columnsList.get(i).getCOL_CODE().toUpperCase())) {
									m_jqGridGroupColumnShow.set(intGroupColumn, String.valueOf(false));
								}
							}
						}
					}

					// 配置表中的表头显示
					jqGridColModelCustom.append(" label: '").append(m_columnsList.get(i).getCOL_NAME()).append("' ");

					jqGridColModelCustom.append("}");
				}
			}
		}
		StringBuilder jqGridColModelKey = new StringBuilder();
		// 添加关键字的保存列
		if (keyList != null && keyList.size() > 0) {
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
					jqGridColModelKey.append(", ");
				}
				jqGridColModelKey.append(" {name: '").append(key.toUpperCase()).append(keyExtra)
						.append("', hidden: true, editable: false} ");
			}
		}
		// 拼接真正设置的jqGrid的ColModel
		StringBuilder sbJqGridColModelAll = new StringBuilder();
		sbJqGridColModelAll.append("[");
		sbJqGridColModelAll.append(jqGridColModelCustom);
		if (jqGridColModelCustom != null && !jqGridColModelCustom.toString().trim().equals("")) {
			if (jqGridColModelKey != null && !jqGridColModelKey.toString().trim().equals("")) {
				sbJqGridColModelAll.append(", ");
				sbJqGridColModelAll.append(jqGridColModelKey);
			}
		}
		sbJqGridColModelAll.append("]");
		return sbJqGridColModelAll.toString();
	}

	/**
	 * 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
	 * 
	 * @param columns
	 * @return
	 * @throws Exception
	 */
	private Map<String, Map<String, Object>> jqGridColModelAccount(List<TableColumns> columns) throws Exception {
		Map<String, Map<String, Object>> list = new LinkedHashMap<String, Map<String, Object>>();

		for (TableColumns col : columns) {
			// 表结构
			// map_HaveColumnsList.put(col.getColumn_name(), col);

			Map<String, Object> MapAdd = new LinkedHashMap<String, Object>();

			StringBuilder model_name = new StringBuilder();
			model_name.append(" editable: false, ");

			int intLength = Common.getColumnLength(col.getColumn_type(), col.getData_type());
			if (col.getData_type() != null && Common.IsNumFeild(col.getData_type())) {
				model_name.append(" width: '150', ");
				model_name.append(" align: 'right', search: false, sorttype: 'number', ");
			} else {
				if (intLength > 50) {
					model_name.append(" width: '200', ");
				} else {
					model_name.append(" width: '130', ");
				}
			}
			model_name.append(" name: '" + col.getColumn_name() + "' ");
			MapAdd.put("name", model_name.toString());
			list.put(col.getColumn_name(), MapAdd);
		}
		return list;
	}

}
