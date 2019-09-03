package com.fh.service.sysConfirmInfo.sysConfirmInfo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysConfirmInfo;
import com.fh.util.PageData;
import com.fh.service.sysConfirmInfo.sysConfirmInfo.SysConfirmInfoManager;

/** 
 * 说明：  工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("sysConfirmInfoService")
public class SysConfirmInfoService implements SysConfirmInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysConfirmInfoMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysConfirmInfoMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysConfirmInfoMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysConfirmInfoMapper.datalistJqPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysConfirmInfoMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysConfirmInfoMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysConfirmInfoMapper.deleteAll", ArrayDATA_IDS);
	}

	/**通过单号判断数据是否变更
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysConfirmInfo> getCheckStateList(PageData pd)throws Exception{
		return (List<SysConfirmInfo>)dao.findForList("SysConfirmInfoMapper.getCheckStateList", pd);
	}

	/**确认
	 * @param page
	 * @throws Exception
	 */
	public void batchEachConfirm(Map<String, Object> map)throws Exception{
		dao.update("SysConfirmInfoMapper.batchEachConfirm", map);
	}
	public void batchAllConfirm(Map<String, Object> map)throws Exception{
		dao.update("SysConfirmInfoMapper.batchAllConfirm", map);
	}

	/**取消确认
	 * @param page
	 * @throws Exception
	 */
	public void batchCancelConfirm(List<SysConfirmInfo> listData)throws Exception{
		dao.update("SysConfirmInfoMapper.batchCancelConfirm", listData);
	}
	
	/**
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysConfirmInfo> getConfirmMappingList(PageData pd)throws Exception{
		return (List<SysConfirmInfo>)dao.findForList("SysConfirmInfoMapper.getConfirmMappingList", pd);
	}
}

