package com.fh.service.tmplconfig.tmplconfig;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.TmplInputTips;
import com.fh.util.PageData;

/** 
 * 说明： 数据模板详情接口
 * 创建人：FH Q313596790
 * 创建时间：2017-06-19
 * @version
 */
public interface TmplConfigManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 保存之前删除表
	 * @param pd
	 * @throws Exception
	 */
	public void deleteTable(PageData pd) throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateItem(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**验证编辑的公式是否正确
	 * @param pd
	 * @throws Exception
	 */
	public PageData validateFormula(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 基本配置表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listBase(Page pd) throws Exception;

    /**根据当前单位编码及表名获取字段配置信息
	 * 张晓柳
	 * @param pd
	 * @throws Exception
	 */
	public List<TmplConfigDetail> listNeed(TmplConfigDetail item)throws Exception;
	
	/**
	 * 临时数据表明细
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> temporaryList(Page pd) throws Exception; 
	
	/**
	 * 根据TABLE_NO获取TABLE_CODE
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findTableCodeByTableNo(PageData pd) throws Exception;
	
	/**
	 * 根据KpiCode获取TABLE_CODE
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findTableCodeByKpiCode(PageData pd) throws Exception;
	
	/**获取某表的所有列
	 * @param 
	 * @throws Exception
	 */
	public List<TableColumns> getTableColumns(String tableCode)throws Exception;
	
	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateAll(List<PageData> pd)throws Exception;
	
	
	/**
	 * 复制
	 * @param pd
	 * @throws Exception
	 */
	public void copyAll(PageData pd) throws Exception;
	
	/**
	 * 根据区间批量生成配置信息
	 * @param pd
	 * @throws Exception
	 */
	//public void insertBatchNextRptDur(PageData pd) throws Exception;
	
	/**通过期间获取数据，判断是否已经生成过模板配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findByRptDur(String nextRptDur)throws Exception;
	
	
	/**********************************特殊表********************************************/
	/**根据帐套、凭证类型、业务期间、表名称获取tb_sys_stru_mapping的结构信息
	 * jiachao
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listNeedSpecial(PageData item)throws Exception;
	
	/**通过期间获取数据，判断是否已经生成过模板配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findStruMappingByRptDurSpecial(String nextRptDur)throws Exception;
	
	/**通过期间获取数据，判断是否已经生成过模板配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findTableMappingByRptDurSpecial(String nextRptDur)throws Exception;
	
	/**通过期间获取数据，判断是否已经生成过参数配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findCertParmByRptDurSpecial(String nextRptDur)throws Exception;
	
	/**通过期间获取数据，判断是否已经生成过参数配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findGlItemUser(String nextRptDur)throws Exception;
	
	/**通过期间获取数据，判断是否已经生成过参数配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findStaffTds(String nextRptDur)throws Exception;
	
	/**通过期间获取数据，判断是否已经生成过StaffRemit
	 * @param pd
	 * @throws Exception
	 */
	public String findStaffRemit(String nextRptDur)throws Exception;
	
	/**
	 * 更新业务期间 
	 * @param pd
	 * @throws Exception
	 */
	public void updateBusidate(PageData pd) throws Exception;
	
	/**
	 * 根据区间批量生成配置信息
	 * @param pd
	 * @throws Exception
	 */
	//public void insertStruMappingBatchNextRptDur(PageData pd) throws Exception;
	
	/**
	 * 根据区间批量生成配置信息
	 * @param pd
	 * @throws Exception
	 */
	//public void insertTableMappingBatchNextRptDur(PageData pd) throws Exception;
	
	/**
	 * 根据区间批量生成参数配置信息
	 * @param pd
	 * @throws Exception
	 */
	//public void insertCertParmBatchNextRptDur(PageData pd) throws Exception;
	/**********************************************************************************/

	/**********************************导入校验********************************************/
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPageTmplInputTips(JqPage page)throws Exception;
	
	/**
	 * @param pd
	 * @throws Exception
	 */
	public void saveTmplInputTips(List<PageData> list)throws Exception;

	/**
	 * @param page
	 * @throws Exception
	 */
	public List<TmplInputTips> getCheckTmplInputTips(PageData pd)throws Exception;
	/**********************************************************************************/
}

