package com.fh.service.sysStruMapping.sysStruMapping.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysStruMapping;
import com.fh.util.PageData;
import com.fh.service.sysStruMapping.sysStruMapping.SysStruMappingManager;

/** 
 * 说明：  工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("sysStruMappingService")
public class SysStruMappingService implements SysStruMappingManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysStruMappingMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("SysStruMappingMapper.countJqGridExtend", page);
	}
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchAllUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("SysStruMappingMapper.batchAllDelAndIns", listData);
	}
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchPartDelAndIns(List<PageData> listData)throws Exception{
		dao.update("SysStruMappingMapper.batchPartDelAndIns", listData);
	}
	
	/**
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysStruMapping> getShowStruList(SysStruMapping mapping)throws Exception{
		return (List<SysStruMapping>)dao.findForList("SysStruMappingMapper.getShowStruList", mapping);
	}

	
	/**
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysStruMapping> getDetailBillCodeSysStruMapping(SysStruMapping mapping)throws Exception{
		return (List<SysStruMapping>)dao.findForList("SysStruMappingMapper.getDetailBillCodeSysStruMapping", mapping);
	}
}

