package com.fh.service.sysBillOffMapping.sysBillOffMapping.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.sysBillOffMapping.sysBillOffMapping.SysBillOffMappingManager;

/** 
 * 说明：  工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("sysBillOffMappingService")
public class SysBillOffMappingService implements SysBillOffMappingManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysBillOffMappingMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("SysBillOffMappingMapper.countJqGridExtend", page);
	}

	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRepeatList(List<PageData> list)throws Exception{
		return (List<PageData>)dao.findForList("SysBillOffMappingMapper.getRepeatList", list);
	}
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("SysBillOffMappingMapper.batchDelAndIns", listData);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("SysBillOffMappingMapper.deleteAll", listData);
	}
	
	
	
	
	
	
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysBillOffMappingMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysBillOffMappingMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysBillOffMappingMapper.edit", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysBillOffMappingMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysBillOffMappingMapper.findById", pd);
	}
	
	/**
	 * @param pd
	 * @throws Exception
	 */
	//@SuppressWarnings("unchecked")
	//public List<SysBillOffMapping> getMappingListByBillOffType(SysBillOffMapping mapping)throws Exception{
	//	return (List<SysBillOffMapping>)dao.findForList("SysBillOffMappingMapper.getMappingListByBillOffType", mapping);
	//}
}

