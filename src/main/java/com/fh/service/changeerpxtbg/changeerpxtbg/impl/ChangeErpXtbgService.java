
package com.fh.service.changeerpxtbg.changeerpxtbg.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.changeerpxtbg.changeerpxtbg.ChangeErpXtbgManager;

/** 
 * 说明： changeManage
 * 创建人：jiachao
 * 创建时间：2019-09-20
 * @version
 */
@Service("changeerpxtbgService")
public class ChangeErpXtbgService implements ChangeErpXtbgManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ChangeErpXtbgMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ChangeErpXtbgMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ChangeErpXtbgMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ChangeErpXtbgMapper.datalistPage", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listxtbg(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChangeErpXtbgMapper.datalistByUser", pd);
	}
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChangeErpXtbgMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeErpXtbgMapper.findById", pd);
	}
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBillCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeErpXtbgMapper.findByBillCode", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ChangeErpXtbgMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

