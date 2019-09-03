package com.fh.service.sysStruMapping.sysStruMapping;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.SysStruMapping;
import com.fh.util.PageData;

/** 
 * 说明： 工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-19
 * @version
 */
public interface SysStruMappingManager{
	
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
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchAllUpdateDatabase(List<PageData> listData)throws Exception;
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchPartDelAndIns(List<PageData> listData)throws Exception;

	/**
	 * @param pd
	 * @throws Exception
	 */
	public List<SysStruMapping> getShowStruList(SysStruMapping mapping)throws Exception;

	
	/**
	 * @param pd
	 * @throws Exception
	 */
	public List<SysStruMapping> getDetailBillCodeSysStruMapping(SysStruMapping mapping)throws Exception;
	
	
	
}

