package com.fh.service.changeerpxtbg.changeerpjsbg.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.changeerpxtbg.changeerpjsbg.ChangeErpJsbgManager;

/** 
 * 说明： tbchangejsbg
 * 创建人：jiachao
 * 创建时间：2019-10-24
 * @version
 */
@Service("changeErpjsbgService")
public class ChangeErpJsbgService implements ChangeErpJsbgManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ChangeErpJsbfMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ChangeErpJsbfMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ChangeErpJsbfMapper.edit", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateEdit(PageData pd)throws Exception{
		dao.update("ChangeErpJsbfMapper.updateEdit", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ChangeErpJsbfMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChangeErpJsbfMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeErpJsbfMapper.findById", pd);
	}
	public PageData findByBillCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeErpJsbfMapper.findByBillCode", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ChangeErpJsbfMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

