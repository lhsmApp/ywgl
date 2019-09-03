package com.fh.service.sysUnlockInfo.sysunlockinfo.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.sysUnlockInfo.sysunlockinfo.SysUnlockInfoManager;

/** 
 * 说明： 传输临时表
 * 创建人：lhsmplus
 * 创建时间：2017-08-22
 * @version
 */
@Service("sysunlockinfoService")
public class SysUnlockInfoService implements SysUnlockInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(List<PageData> pd)throws Exception{
		dao.save("SysUnlockInfoMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysUnlockInfoMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(List<PageData> pd)throws Exception{
		dao.update("SysUnlockInfoMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysUnlockInfoMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysUnlockInfoMapper.listAll", pd);
	}
	
	/**列表(获取当前接口类型， 当前二级单位当前期间当前封存状态为封存的的传输列表)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listSyncDelUnlock(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysUnlockInfoMapper.listSyncDelUnlock", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysUnlockInfoMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysUnlockInfoMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

