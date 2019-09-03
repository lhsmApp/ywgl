package com.fh.controller.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fh.entity.TableColumns;
import com.fh.entity.SysStruMapping;
import com.fh.entity.SysTableMapping;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysStruMapping.sysStruMapping.impl.SysStruMappingService;
import com.fh.service.sysTableMapping.sysTableMapping.impl.SysTableMappingService;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.TmplConfigDictManager;
import com.fh.service.tmplconfig.tmplconfig.TmplConfigManager;

/**
 * 模板通用类
 * 
 * @ClassName: TmplVoucherUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangxiaoliu
 * @date 2018年04月19日
 *
 */
public class TmplVoucherUtil {

	private SysTableMappingService sysTableMappingService;
	private SysStruMappingService sysStruMappingService;
	private TmplConfigManager tmplconfigService;
	private TmplConfigDictManager tmplConfigDictService;
	private DictionariesManager dictionariesService;
	private DepartmentManager departmentService;
	private UserManager userService;

	// 查询表的主键字段后缀，区别于主键字段，用于修改或删除
	public static String keyExtra = "__";
	// 查询表的主键字段
	private List<String> keyList = new ArrayList<String>();

	// 字典
	private Map<String, Object> m_dicList = new LinkedHashMap<String, Object>();
	public Map<String, Object> getDicList() {
		return m_dicList;
	}

	public TmplVoucherUtil(SysTableMappingService sysTableMappingService, 
			               SysStruMappingService sysStruMappingService,
			               TmplConfigManager tmplconfigService, 
			               TmplConfigDictManager tmplConfigDictService,
                           DictionariesManager dictionariesService, 
                           DepartmentManager departmentService,
                           UserManager userService,
			               List<String> keyList) {
		this.sysTableMappingService = sysTableMappingService;
		this.sysStruMappingService = sysStruMappingService;
		this.tmplconfigService = tmplconfigService;
		this.tmplConfigDictService = tmplConfigDictService;
		this.dictionariesService = dictionariesService;
		this.departmentService = departmentService;
		this.userService=userService;
		this.keyList = keyList;
	}
	

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateStructureNoEdit(String pzType, String struTableName, String struMappingName, String busiDate, String billOff, String tableMappingName, Boolean bol) throws Exception {
		// 字典
		m_dicList = new LinkedHashMap<String, Object>();

		StringBuilder jqGridColModelCustom = new StringBuilder();
		
		List<SysTableMapping> getSysTableMappingList = SysStruMappingList.getUseTableMapping(pzType, busiDate, billOff, tableMappingName, sysTableMappingService);
		if(getSysTableMappingList != null && getSysTableMappingList.size() == 1){
			// 用语句查询出数据库表的所有字段及其属性；拼接成jqgrid全部列
			List<TableColumns> tableColumns = tmplconfigService.getTableColumns(struMappingName);
			String struTableNameTranfer = struTableName;
			if(!(struTableNameTranfer!=null && !struTableNameTranfer.trim().trim().equals(""))){
				struTableNameTranfer = getSysTableMappingList.get(0).getTABLE_NAME();
			}
			// 前端数据表格界面字段,动态取自SysStruMapping，根据当前单位编码及表名获取字段配置信息
			List<SysStruMapping> getSysStruMappingList = SysStruMappingList.getSysStruMappingList(pzType, struTableNameTranfer, struMappingName, busiDate, billOff, sysStruMappingService, bol);
			
			Map<String, Map<String, Object>> listColModelAll = jqGridColModelAllNoEdit(tableColumns);
			
			// 添加配置表设置列，字典（未设置就使用表默认，text或number）、隐藏、表头显示
			if (getSysStruMappingList != null && getSysStruMappingList.size() > 0) {
				for (int i = 0; i < getSysStruMappingList.size(); i++) {
					if (listColModelAll.containsKey(getSysStruMappingList.get(i).getCOL_MAPPING_CODE().toUpperCase())) {
						Map<String, Object> itemColModel = listColModelAll.get(getSysStruMappingList.get(i).getCOL_MAPPING_CODE());
						String name = (String) itemColModel.get("name");
						if (name != null && !name.trim().equals("")) {
							if (jqGridColModelCustom!=null && !jqGridColModelCustom.toString().trim().equals("")) {
								jqGridColModelCustom.append(", ");
							}
							jqGridColModelCustom.append("{");
							jqGridColModelCustom.append(name).append(", ").append(" editable: false, ");
						} else {
							continue;
						}
						// 配置表中的字典
						if (getSysStruMappingList.get(i).getDICT_TRANS() != null
								&& !getSysStruMappingList.get(i).getDICT_TRANS().trim().equals("")) {
							String strDicValue = Common.getDicValue(m_dicList, getSysStruMappingList.get(i).getDICT_TRANS(),//
									tmplConfigDictService, dictionariesService, 
									departmentService, userService, null);

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
						int intHide = Integer.parseInt(getSysStruMappingList.get(i).getCOL_HIDE());
						jqGridColModelCustom.append(" hidden: ").append(intHide == 1 ? "false" : "true").append(", ");
						// 底行显示的求和与平均值字段
						// 1汇总 0不汇总,默认0
						if (Integer.parseInt(getSysStruMappingList.get(i).getCOL_SUM()) == 1) {
							jqGridColModelCustom.append(" summaryType:'sum', summaryTpl:'<b>sum:{0}</b>', ");
						}
						// 0不计算 1计算 默认0
						else if (Integer.parseInt(getSysStruMappingList.get(i).getCOL_AVE()) == 1) {
							jqGridColModelCustom.append(" summaryType:'avg', summaryTpl:'<b>avg:{0}</b>', ");
						}
						// 配置表中的表头显示
						jqGridColModelCustom.append(" label: '").append(getSysStruMappingList.get(i).getCOL_MAPPING_NAME()).append("' ");

						jqGridColModelCustom.append("}");
					}
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
				jqGridColModelKey.append(" {name: '").append(key.toUpperCase()).append(keyExtra).append("', hidden: true, editable: false} ");
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
	private Map<String, Map<String, Object>> jqGridColModelAllNoEdit(List<TableColumns> columns) throws Exception {
		Map<String, Map<String, Object>> list = new LinkedHashMap<String, Map<String, Object>>();

		for (TableColumns col : columns) {
			Map<String, Object> MapAdd = new LinkedHashMap<String, Object>();

			StringBuilder model_name = new StringBuilder();

			int intLength = Common.getColumnLength(col.getColumn_type(), col.getData_type());
			if (col.getData_type() != null && Common.IsNumFeild(col.getData_type())) {
				model_name.append(" width: '150', ");
				model_name.append(" align: 'right', search: false, sorttype: 'number', formatter: 'number',summaryTpl: 'sum: {0}', summaryType: 'sum', ");
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
