package com.fh.service.jqgrid.jggrid.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.service.jqgrid.jggrid.JgGridManager;
import com.fh.util.PageData;

/**
 * jqtest
* @ClassName: JgGridService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaojianping
* @date 2017年6月30日
*
 */
@Service("jqgridService")
public class JgGridService implements JgGridManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("JgGridMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("JgGridMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("JgGridMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("JgGridMapper.datalistJqPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("JgGridMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("JgGridMapper.findById", pd);
	}
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGrid(PageData pd)throws Exception{
		return (int)dao.findForObject("JgGridMapper.countJqGrid", pd);
	}

	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("JgGridMapper.deleteAll", ArrayDATA_IDS);
	}

	
	
}

