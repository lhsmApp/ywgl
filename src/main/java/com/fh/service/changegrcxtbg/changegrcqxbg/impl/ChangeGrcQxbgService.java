package com.fh.service.changegrcxtbg.changegrcqxbg.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.changegrcxtbg.changegrcqxbg.ChangeGrcQxbgManager;

/** 
 * 说明： changeGrcQxbg
 * 创建人：jiachao
 * 创建时间：2019-09-29
 * @version
 */
@Service("changegrcqxbgService")
public class ChangeGrcQxbgService implements ChangeGrcQxbgManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ChangeGrcQxbgMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ChangeGrcQxbgMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ChangeGrcQxbgMapper.edit", pd);
	}
	/**更新修改内容
	 * @param pd
	 * @throws Exception
	 */
	public void updateEdit(PageData pd)throws Exception{
		dao.update("ChangeGrcQxbgMapper.updateEdit", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ChangeGrcQxbgMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChangeGrcQxbgMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeGrcQxbgMapper.findById", pd);
	}
	public PageData findByBillCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeGrcQxbgMapper.findByBillCode", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ChangeGrcQxbgMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

