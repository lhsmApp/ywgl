package com.fh.service.dataReporting.operationstatistics.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.dataReporting.operationstatistics.OperationStatisticsManager;

/** 
 * 说明： 运维工作统计处理模块
 * 创建人：xinyuLo
 * 创建时间：2019-10-11
 * @version
 */
@Service("operationstatisticsService")
public class OperationStatisticsService implements OperationStatisticsManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("OperationStatisticsMapper.save", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("OperationStatisticsMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OperationStatisticsMapper.datalistPage", page);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OperationStatisticsMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**导出模板
	 * @param PageData
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OperationStatisticsMapper.exportModel", pd);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OperationStatisticsMapper.exportList", page);
	}
	
	/**更新数据
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void grcUpdateDatabase(List<PageData> listData) throws Exception {
		dao.update("OperationStatisticsMapper.delAndIns", listData);
		
	}
	
	/**
	 * 获取业务期间
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBusiDate(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("OperationStatisticsMapper.listBusiDate", pd);
	}
}

