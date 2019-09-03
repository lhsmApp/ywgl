package com.fh.service.auditedit.auditedit.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.auditedit.auditedit.AuditEditManager;

/** 
 * 说明： 对账数据编辑
 * 创建人：zhangxiaoliu
 * 创建时间：2017-07-26
 * @version
 */
@Service("auditeditService")
public class AuditEditService implements AuditEditManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("AuditEditMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("AuditEditMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("AuditEditMapper.getFooterSummary", page);
	}

	/**获取数据
	 * 张晓柳
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AuditEditMapper.findByModel", pd);
	}
	@SuppressWarnings("unchecked")
	public List<String> exportHaveUserCode(PageData listData)throws Exception{
		return (List<String>)dao.findForList("AuditEditMapper.exportHaveUserCode", listData);
	}

	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception{
		dao.delete("AuditEditMapper.deleteAll", pd);
	}
	
	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void deleteUpdateAll(List<PageData> listData)throws Exception{
		//dao.batchUpdateDatabase("AuditEditMapper.delete", "AuditEditMapper.save", listData);
	}
	
	/**导入
	 * @param pd
	 * @throws Exception
	 */
	public void batchImport(List<PageData> listData)throws Exception{
		//dao.batchUpdateDatabase("AuditEditMapper.importDelete", "AuditEditMapper.save", listData);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("AuditEditMapper.exportList", page);
	}
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("AuditEditMapper.exportModel", page);
	}

}

