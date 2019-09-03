package com.fh.service.fundssummyconfirm.fundssummyconfirm.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysConfirmInfo;
import com.fh.util.PageData;
import com.fh.service.fundssummyconfirm.fundssummyconfirm.FundsSummyConfirmManager;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
@Service("fundssummyconfirmService")
public class FundsSummyConfirmService implements FundsSummyConfirmManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("FundsSummyConfirmMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("FundsSummyConfirmMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("FundsSummyConfirmMapper.getFooterSummary", page);
	}
	
	/**明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getFirstDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FundsSummyConfirmMapper.getFirstDetailList", pd);
	}
	/**明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getSecondDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FundsSummyConfirmMapper.getSecondDetailList", pd);
	}
	
	/**
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getOperList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FundsSummyConfirmMapper.getOperList", pd);
	}
}

