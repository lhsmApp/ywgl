package com.fh.service.sysTableMapping.sysTableMapping.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysTableMapping;
import com.fh.util.PageData;
import com.fh.service.sysTableMapping.sysTableMapping.SysTableMappingManager;

/** 
 * 说明：  
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("sysTableMappingService")
public class SysTableMappingService implements SysTableMappingManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysTableMappingMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("SysTableMappingMapper.countJqGridExtend", page);
	}

	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysTableMapping> getRepeatList(List<PageData> list)throws Exception{
		return (List<SysTableMapping>)dao.findForList("SysTableMappingMapper.getRepeatList", list);
	}
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("SysTableMappingMapper.batchDelAndIns", listData);
	}
	
	
	
	
	
	
	
	
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysTableMappingMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysTableMappingMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysTableMappingMapper.edit", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysTableMappingMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysTableMappingMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysTableMappingMapper.deleteAll", ArrayDATA_IDS);
	}

	
	/**
	 * @param pd
	 * 张晓柳
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysTableMapping> getUseTableMapping(SysTableMapping mapping)throws Exception{
		return (List<SysTableMapping>)dao.findForList("SysTableMappingMapper.getUseTableMapping", mapping);
	}
}

