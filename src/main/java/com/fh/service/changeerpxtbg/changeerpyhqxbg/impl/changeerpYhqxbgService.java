package com.fh.service.changeerpxtbg.changeerpyhqxbg.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.changeerpxtbg.changeerpyhqxbg.ChangeErpYhqxManager;

/** 
 * 说明：tbchangeerpyhqxbg
 * 创建人：liqian
 * 创建时间：2020-07-21
 * @version
 */
@Service("changeerpyhqxbgService")
public class changeerpYhqxbgService implements ChangeErpYhqxManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ChangeErpYhqxbgMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ChangeErpYhqxbgMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ChangeErpYhqxbgMapper.edit", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateEdit(PageData pd)throws Exception{
		dao.update("ChangeErpYhqxbgMapper.updateEdit", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ChangeErpYhqxbgMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChangeErpYhqxbgMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeErpYhqxbgMapper.findById", pd);
	}
	public PageData findByBillCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeErpYhqxbgMapper.findByBillCode", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ChangeErpYhqxbgMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

