package com.fh.service.housefundsummy.housefundsummy.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysSealed;
import com.fh.util.PageData;
import com.fh.service.housefundsummy.housefundsummy.HouseFundSummyManager;

/** 
 * 说明： 公积金汇总
 * 创建人：zhangxiaoliu
 * 创建时间：2017-07-07
 * @version
 */ 
@Service("housefundsummyService")
public class HouseFundSummyService implements HouseFundSummyManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBillCodeList(PageData pd)throws Exception{
		return (List<String>)dao.findForList("HouseFundSummyMapper.getBillCodeList", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("HouseFundSummyMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("HouseFundSummyMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("HouseFundSummyMapper.getFooterSummary", page);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findSummyDetailList(PageData page)throws Exception{
		return (List<PageData>)dao.findForList("HouseFundSummyMapper.findSummyDetailList", page);
	}
	
	/**作废
	 * @param 
	 * @throws Exception
	 */
	public void cancelAll(List<PageData> list)throws Exception{
		dao.batchUpdate("HouseFundSummyMapper.updateBillState", list);
	}
	
	/**汇总
	 * @param 
	 * @throws Exception
	 */
	public void saveSummyModelList(Map<String, Object> map)throws Exception{
		dao.update("HouseFundSummyMapper.saveSummy", map);
	}
}

