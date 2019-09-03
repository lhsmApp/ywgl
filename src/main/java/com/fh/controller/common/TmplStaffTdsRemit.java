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

/**
 * 模板通用类
 * 
 * @ClassName: TmplStaffTds
 * @Description: 获取个税扣除项导入界面显示结构
 * @author zhangxiaoliu
 * @date 2019年1月29日
 *
 */
public class TmplStaffTdsRemit {

	private TmplConfigDictManager tmplConfigDictService;
	private DictionariesManager dictionariesService;
	private DepartmentManager departmentService;
	private UserManager userService;

	// 查询表的主键字段后缀，区别于主键字段，用于修改或删除
	public static String keyExtra = "__";
	// 查询表的主键字段
	private List<String> keyList = new ArrayList<String>();
	private List<String> MustInputList = new ArrayList<String>();

	public TmplStaffTdsRemit(TmplConfigDictManager tmplConfigDictService,
			DictionariesManager dictionariesService, DepartmentManager departmentService,UserManager userService,
			List<String> keyList, List<String> MustInputList) {
		this.tmplConfigDictService = tmplConfigDictService;
		this.dictionariesService = dictionariesService;
		this.departmentService = departmentService;
		this.userService=userService;
		this.keyList = keyList;
		this.MustInputList = MustInputList;
		
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructure(List<TableColumns> tableColumns, List<TmplConfigDetail> m_columnsList, List<String> MustNotEditFeildList) throws Exception {
		
		Map<String, Map<String, Object>> listColModelAll = jqGridColModelAll(tableColumns);
		
		StringBuilder jqGridColModelCustom = new StringBuilder();
		// 添加配置表设置列，字典（未设置就使用表默认，text或number）、隐藏、表头显示
		if (m_columnsList != null && m_columnsList.size() > 0) {
			for (int i = 0; i < m_columnsList.size(); i++) {
				String getCOL_CODE = m_columnsList.get(i).getCOL_CODE();
				if (listColModelAll.containsKey(getCOL_CODE.toUpperCase())) {
					Map<String, Object> itemColModel = listColModelAll.get(getCOL_CODE);
					String name = (String) itemColModel.get("name");
					String edittype = (String) itemColModel.get("edittype");
					if (name != null && !name.trim().equals("")) {
						if (jqGridColModelCustom!=null && !jqGridColModelCustom.toString().trim().equals("")) {
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
						String strDicValue = Common.getDicValue(null, m_columnsList.get(i).getDICT_TRANS(), //
								tmplConfigDictService, dictionariesService, 
								departmentService, userService, "");
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
					} else {
						if (MustNotEditFeildList.contains(getCOL_CODE)) {
							jqGridColModelCustom.append(" editable: false, editrules: {edithidden: false}, ");
						} else {
							jqGridColModelCustom.append(" editable: true, ");
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
				if (jqGridColModelKey!=null && !jqGridColModelKey.toString().trim().equals("")) {
					jqGridColModelKey.append(", ");
				}
				jqGridColModelKey.append(" {name: '").append(key.toUpperCase()).append(keyExtra)
						.append("', hidden: true, editable: true, editrules: {edithidden: false} ");
				jqGridColModelKey.append(" } ");
			}
		}
		// 拼接真正设置的jqGrid的ColModel
		StringBuilder sbJqGridColModelAll = new StringBuilder();
		sbJqGridColModelAll.append("[");
		sbJqGridColModelAll.append(jqGridColModelCustom);
		if (jqGridColModelCustom!=null && !jqGridColModelCustom.toString().trim().equals("")) {
			if (jqGridColModelKey!=null && !jqGridColModelKey.toString().trim().equals("")) {
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

			if (MustInputList!=null && MustInputList.contains(col.getColumn_name())) {
				model_name.append(" editrules:{required:true}, ");
			}
			model_name.append(" name: '" + col.getColumn_name() + "' ");

			MapAdd.put("name", model_name.toString());
			MapAdd.put("edittype", model_edittype.toString());
			list.put(col.getColumn_name(), MapAdd);
		}

		return list;
	}
	
}
