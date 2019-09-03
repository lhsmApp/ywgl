package com.fh.service.jqgrid.jggrid.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqGridModel;
import com.fh.entity.JqPage;
import com.fh.service.jqgrid.jggrid.JgGridManagerJia;
import com.fh.util.PageData;

/**
 * jqtest
* @ClassName: JgGridServiceJia
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaojianping
* @date 2017年6月30日
*
 */
@Service("jqgridServiceJia")
public class JgGridServiceJia implements JgGridManagerJia{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("JgGridMapperJia.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("JgGridMapperJia.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("JgGridMapperJia.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("JgGridMapperJia.datalistJqPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("JgGridMapperJia.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("JgGridMapperJia.findById", pd);
	}
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGrid(PageData pd)throws Exception{
		return (int)dao.findForObject("JgGridMapperJia.countJqGrid", pd);
	}

	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("JgGridMapperJia.deleteAll", ArrayDATA_IDS);
	}
	
	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateAll(List<PageData> pd)throws Exception{
		dao.update("JgGridMapperJia.updateAll", pd);
	}
	
}

