package com.fh.service.tmplconfig.tmplconfig.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.TmplInputTips;
import com.fh.util.PageData;
import com.fh.service.tmplconfig.tmplconfig.TmplConfigManager;

/** 
 * 说明： 数据模板详情
 * 创建人：FH Q313596790
 * 创建时间：2017-06-19
 * @version
 */
@Service("tmplconfigService")
public class TmplConfigService implements TmplConfigManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TmplConfigMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TmplConfigMapper.delete", pd);
	}
	
	/**
	 * 保存之前删除
	 * @param pd
	 * @throws Exception
	 */
	public void deleteTable(PageData pd)throws Exception {
		dao.delete("TmplConfigMapper.deleteTable", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TmplConfigMapper.edit", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateItem(PageData pd)throws Exception{
		dao.update("TmplConfigMapper.updateItem", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TmplConfigMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TmplConfigMapper.findById", pd);
	}
	
	/**验证编辑的公式是否正确
	 * @param pd
	 * @throws Exception
	 */
	public PageData validateFormula(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TmplConfigMapper.validateFormula", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TmplConfigMapper.deleteAll", ArrayDATA_IDS);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBase(Page pd) throws Exception {
		return (List<PageData>)dao.findForList("TmplConfigMapper.listBase", pd);
	}
	
	
	/**根据当前单位编码及表名获取字段配置信息 
	 * 张晓柳
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TmplConfigDetail> listNeed(TmplConfigDetail item)throws Exception{
		return (List<TmplConfigDetail>)dao.findForList("TmplConfigMapper.listNeed", item);
	}

	/**临时数据表明细 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> temporaryList(Page pd) throws Exception {
		return (List<PageData>)dao.findForList("TmplConfigMapper.temporaryList", pd);
	}

	/**根据TABLE_NO获取TABLE_CODE
	 * @param pd
	 * @throws Exception
	 */
	public PageData findTableCodeByTableNo(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TmplConfigMapper.findTableCodeByTableNo", pd);
	}
	
	/**根据KpiCode获取TABLE_CODE
	 * @param pd
	 * @throws Exception
	 */
	public PageData findTableCodeByKpiCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TmplConfigMapper.findTableCodeByKpiCode", pd);
	}
	
	/**获取某表的所有列
	 * @param 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TableColumns> getTableColumns(String tableCode)throws Exception{
		return (List<TableColumns>)dao.findForList("TmplConfigMapper.getTableColumns", tableCode);
	}

	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateAll(List<PageData> pd)throws Exception{
		dao.update("TmplConfigMapper.updateAll", pd);
	}

	/**
	 * 复制
	 */
	public void copyAll(PageData pd) throws Exception {
		dao.update("TmplConfigMapper.copyAll", pd);
	}
	
	/**
	 * 根据区间批量生成配置信息
	 */
	//public void insertBatchNextRptDur(PageData pd) throws Exception {
	//	dao.update("TmplConfigMapper.insertBatchNextRptDur", pd);
	//}
	
	/**通过期间获取数据，判断是否已经生成过模板配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findByRptDur(String nextRptDur)throws Exception{
		return (String)dao.findForObject("TmplConfigMapper.findByRptDur", nextRptDur);
	}

	/**********************************特殊表********************************************/
	/**根据帐套、凭证类型、业务期间、表名称获取tb_sys_stru_mapping的结构信息
	 * jiachao
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listNeedSpecial(PageData item)throws Exception{
		return (List<PageData>)dao.findForList("TmplConfigMapper.listNeedSpecial", item);
	}
	
	/**通过期间获取数据，判断是否已经生成过模板配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findStruMappingByRptDurSpecial(String nextRptDur)throws Exception{
		return (String)dao.findForObject("TmplConfigMapper.findStruMappingByRptDurSpecial", nextRptDur);
	}
	
	/**通过期间获取数据，判断是否已经生成过模板配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findTableMappingByRptDurSpecial(String nextRptDur)throws Exception{
		return (String)dao.findForObject("TmplConfigMapper.findTableMappingByRptDurSpecial", nextRptDur);
	}
	
	/**通过期间获取数据，判断是否已经生成过参数配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findCertParmByRptDurSpecial(String nextRptDur)throws Exception{
		return (String)dao.findForObject("TmplConfigMapper.findCertParmByRptDurSpecial", nextRptDur);
	}
	
	/**通过期间获取数据，判断是否已经生成过参数配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findGlItemUser(String nextRptDur)throws Exception{
		return (String)dao.findForObject("TmplConfigMapper.findGlItemUser", nextRptDur);
	}
	
	/**通过期间获取数据，判断是否已经生成过参数配置信息 
	 * @param pd
	 * @throws Exception
	 */
	public String findStaffTds(String nextRptDur)throws Exception{
		return (String)dao.findForObject("TmplConfigMapper.findStaffTds", nextRptDur);
	}
	
	/**通过期间获取数据，判断是否已经生成过StaffRemit
	 * @param pd
	 * @throws Exception
	 */
	public String findStaffRemit(String nextRptDur)throws Exception{
		return (String)dao.findForObject("TmplConfigMapper.findStaffRemit", nextRptDur);
	}
	
	/**
	 * 更新业务期间 
	 */
	public void updateBusidate(PageData pd) throws Exception {
		dao.update("TmplConfigMapper.updateBusidate", pd);
	}
	
	/**
	 * 根据区间批量生成配置信息
	 */
	//public void insertStruMappingBatchNextRptDur(PageData pd) throws Exception {
	//	dao.update("TmplConfigMapper.insertStruMappingBatchNextRptDur", pd);
	//}
	
	/**
	 * 根据区间批量生成配置信息
	 */
	//public void insertTableMappingBatchNextRptDur(PageData pd) throws Exception {
	//	dao.update("TmplConfigMapper.insertTableMappingBatchNextRptDur", pd);
	//}
	
	/**
	 * 根据区间批量生成参数配置信息
	 */
	//public void insertCertParmBatchNextRptDur(PageData pd) throws Exception {
	//	dao.update("TmplConfigMapper.insertCertParmBatchNextRptDur", pd);
	//}
	/**********************************************************************************/

	/**********************************导入校验********************************************/
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPageTmplInputTips(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("TmplConfigMapper.datalistJqPageTmplInputTips", page);
	}
	
	/**
	 * @param pd
	 * @throws Exception
	 */
	public void saveTmplInputTips(List<PageData> list)throws Exception{
		dao.save("TmplConfigMapper.saveTmplInputTips", list);
	}
	
	/**
	 * @param page
	 * @throws Exception
	 */
	public List<TmplInputTips> getCheckTmplInputTips(PageData pd)throws Exception{
		return (List<TmplInputTips>)dao.findForList("TmplConfigMapper.getCheckTmplInputTips", pd);
	}
	/**********************************************************************************/
}

