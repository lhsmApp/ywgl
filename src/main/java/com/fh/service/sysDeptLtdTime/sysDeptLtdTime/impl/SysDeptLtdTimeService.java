package com.fh.service.sysDeptLtdTime.sysDeptLtdTime.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysDeptLtdTime;
import com.fh.util.PageData;
import com.fh.service.sysDeptLtdTime.sysDeptLtdTime.SysDeptLtdTimeManager;

/** 
 * 说明：  
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("sysDeptLtdTimeService")
public class SysDeptLtdTimeService implements SysDeptLtdTimeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysDeptLtdTimeMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("SysDeptLtdTimeMapper.countJqGridExtend", page);
	}

	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysDeptLtdTime> getRepeatList(List<PageData> list)throws Exception{
		return (List<SysDeptLtdTime>)dao.findForList("SysDeptLtdTimeMapper.getRepeatList", list);
	}
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("SysDeptLtdTimeMapper.batchDelAndIns", listData);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("SysDeptLtdTimeMapper.deleteAll", listData);
	}

	/**
	 * @param pd
	 * 张晓柳
	 * @throws Exception
	 */
	public SysDeptLtdTime getUseSysDeptLtdTime(SysDeptLtdTime item)throws Exception{
		return (SysDeptLtdTime)dao.findForObject("SysDeptLtdTimeMapper.getUseSysDeptLtdTime", item);
	}
}

