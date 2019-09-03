package com.fh.service.sysChangevalueMapping.sysChangevalueMapping;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.system.Department;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;

/** 
 * 说明： 工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-19
 * @version
 */
public interface SysChangevalueMappingManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception;
	
	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRepeatList(List<PageData> list)throws Exception;
	
	/**用于判断数据是否有下一步，可否删除
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getChangeValueList(List<PageData> list, Boolean bolDel)throws Exception;
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception;
	
	/**设置值界面下拉列表选项
	 * @param pd
	 * @throws Exception
	 */
	public List<Dictionaries> getSelectBillOffList(PageData pd)throws Exception;
	public List<Dictionaries> getSelectTypeCodeList(PageData pd)throws Exception;
	public List<Dictionaries> getSelectDeptCodeList(PageData pd)throws Exception;
	public List<Dictionaries> getSelectMapCodeList(PageData pd)throws Exception;
	
	/**
	 * 获取
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDeptContainsChangeCols(String parentId, PageData pd) throws Exception;
	
	/**设置值界面下拉列表选项
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getDataInputCheckHavaList(List<PageData> listData)throws Exception;
}

