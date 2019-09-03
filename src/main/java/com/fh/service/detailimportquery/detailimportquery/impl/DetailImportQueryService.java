package com.fh.service.detailimportquery.detailimportquery.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.detailimportquery.detailimportquery.DetailImportQueryManager;

/** 
 * 说明： 明细导入查询
 * 创建人：张晓柳
 * 创建时间：2017-08-07
 * @version
 */
@Service("detailimportqueryService")
public class DetailImportQueryService implements DetailImportQueryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBillCodeList(PageData pd)throws Exception{
		return (List<String>)dao.findForList("DetailImportQueryMapper.getBillCodeList", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DetailImportQueryMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("DetailImportQueryMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("DetailImportQueryMapper.getFooterSummary", page);
	}

	/**导出
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> datalistExport(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DetailImportQueryMapper.datalistExport", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> exportSumList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DetailImportQueryMapper.exportSumList", page);
	}
}

