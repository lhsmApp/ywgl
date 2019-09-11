package com.fh.service.mbp.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.service.mbp.MBPManager;
import com.fh.util.PageData;

/**
 * 问题管理
* @ClassName: MBPService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年9月9日
*
 */
@Service("mbpService")
public class MBPService implements MBPManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MBPMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MBPMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MBPMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("MBPMapper.datalistJqPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MBPMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MBPMapper.findById", pd);
	}
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGrid(PageData pd)throws Exception{
		return (int)dao.findForObject("MBPMapper.countJqGrid", pd);
	}

	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MBPMapper.deleteAll", ArrayDATA_IDS);
	}

	
	
}

