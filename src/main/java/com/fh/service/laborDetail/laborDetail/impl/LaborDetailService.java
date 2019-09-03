package com.fh.service.laborDetail.laborDetail.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.laborDetail.laborDetail.LaborDetailManager;

/** 
 * 说明： 劳务报酬所得导入
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("laborDetailService")
public class LaborDetailService implements LaborDetailManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("LaborDetailMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("LaborDetailMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("LaborDetailMapper.getFooterSummary", page);
	}

	/**通过流水号获取单号，用于判断数据是否变更
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getSerialNoBySerialNo(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LaborDetailMapper.getSerialNoBySerialNo", pd);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("LaborDetailMapper.exportList", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> exportSumList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("LaborDetailMapper.exportSumList", page);
	}
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LaborDetailMapper.exportModel", pd);
	}

	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("LaborDetailMapper.deleteAll", listData);
	}
	
	/**获取计算数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getDataCalculation(String tableName, String TmplUtil_KeyExtra,
			PageData pdInsetBackup, List<PageData> listAdd,
			String sqlRetSelect, String sqlSumByUserNameStaffIdent)throws Exception{
		return dao.findDataCalculationLaborDetail(tableName, TmplUtil_KeyExtra,
				    "LaborDetailMapper.insetBackup", pdInsetBackup,
				    "LaborDetailMapper.batchDelAndIns", listAdd,
				    sqlRetSelect, sqlSumByUserNameStaffIdent);
	}
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("LaborDetailMapper.batchDelAndIns", listData);
	}
}

