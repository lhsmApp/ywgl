package com.fh.service.dataReporting.operationstatistics;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 运维工作统计处理模块接口
 * 创建人：xinyuLo
 * 创建时间：2019-10-11
 * @version
 */
public interface OperationStatisticsManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**下载模板
	 * @param PageData
	 * @throws Exception
	 */
	public List<PageData> exportModel(PageData getPd) throws Exception;

	/**
	 * 导出列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(Page page) throws Exception;
	
	/**更新数据
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void grcUpdateDatabase(List<PageData> listData) throws Exception;
	
	/**
	 * 获取业务期间
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listBusiDate(PageData pd)throws Exception;
	
}

