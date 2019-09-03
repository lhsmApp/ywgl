package com.fh.service.jqGridExtend.jqGridExtend.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqGridModel;
import com.fh.entity.JqPage;
import com.fh.service.jqGridExtend.jqGridExtend.JqGridExtendManager;
import com.fh.util.PageData;

/** 
 * 说明： jqtest
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-08
 * @version
 */
@Service("jqGridExtendService")
public class JqGridExtendService implements JqGridExtendManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("JqGridExtendMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("JqGridExtendMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("JqGridExtendMapper.edit", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("JqGridExtendMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("JqGridExtendMapper.findById", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("JqGridExtendMapper.datalistJqPage", page);
	}
	
	/**明细
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("JqGridExtendMapper.getDetailList", pd);
	}
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("JqGridExtendMapper.countJqGridExtend", page);
	}
	
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("JqGridExtendMapper.getFooterSummary", page);
	}

	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("JqGridExtendMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateAll(List<JqGridModel> pd)throws Exception{
		dao.update("JqGridExtendMapper.updateAll", pd);
	}
	
	/**导入
	 * @param pd
	 * @throws Exception
	 */
	public void batchImport(List<JqGridModel> pd)throws Exception{
		dao.update("JqGridExtendMapper.batchImport", pd);
	}
	
}

