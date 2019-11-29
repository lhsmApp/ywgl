package com.fh.service.dataReporting.erpuserlist.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.dataReporting.erpuserlist.ERPUserListManager;

/** 
 * 说明： ERP用户清单
 * 创建人：jiachao
 * 创建时间：2019-11-27
 * @version
 */
@Service("erpuserlistService")
public class ERPUserListService implements ERPUserListManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ERPUserListMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ERPUserListMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ERPUserListMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ERPUserListMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ERPUserListMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ERPUserListMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ERPUserListMapper.deleteAll", ArrayDATA_IDS);
	}

	/**导出模板
	 * @param PageData
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ERPUserListMapper.exportModel", pd);
	}

	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ERPUserListMapper.exportList", page);
	}

	/**更新数据
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void grcUpdateDatabase(List<PageData> listData) throws Exception {
		dao.update("ERPUserListMapper.delAndIns", listData);
		
	}
	
}

