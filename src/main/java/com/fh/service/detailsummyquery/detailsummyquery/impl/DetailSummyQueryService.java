package com.fh.service.detailsummyquery.detailsummyquery.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.detailsummyquery.detailsummyquery.DetailSummyQueryManager;

/** 
 * 说明： 明细汇总查询
 * 创建人：张晓柳
 * 创建时间：2017-08-09
 * @version
 */
@Service("detailsummyqueryService")
public class DetailSummyQueryService implements DetailSummyQueryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBillCodeList(PageData pd)throws Exception{
		return (List<String>)dao.findForList("DetailSummyQueryMapper.getBillCodeList", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DetailSummyQueryMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("DetailSummyQueryMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("DetailSummyQueryMapper.getFooterSummary", page);
	}
	
	/**明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getFirstDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DetailSummyQueryMapper.getFirstDetailList", pd);
	}
	/**明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getSecondDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DetailSummyQueryMapper.getSecondDetailList", pd);
	}

	/**导出
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> datalistExport(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DetailSummyQueryMapper.datalistExport", page);
	}
}

