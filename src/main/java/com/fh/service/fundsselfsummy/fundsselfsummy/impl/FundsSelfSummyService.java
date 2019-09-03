package com.fh.service.fundsselfsummy.fundsselfsummy.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.fundsselfsummy.fundsselfsummy.FundsSelfSummyManager;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
@Service("fundsselfsummyService")
public class FundsSelfSummyService implements FundsSelfSummyManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBillCodeList(PageData pd)throws Exception{
		return (List<String>)dao.findForList("FundsSelfSummyMapper.getBillCodeList", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("FundsSelfSummyMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("FundsSelfSummyMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("FundsSelfSummyMapper.getFooterSummary", page);
	}
	
	/**明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getFirstDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FundsSelfSummyMapper.getFirstDetailList", pd);
	}
	/**明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getSecondDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FundsSelfSummyMapper.getSecondDetailList", pd);
	}

	/**通过单号判断数据是否变更
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getCheckStateList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FundsSelfSummyMapper.getCheckStateList", pd);
	}

	/**获取保存数据 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getSaveList(String str)throws Exception{
		return (List<PageData>)dao.findForList("FundsSelfSummyMapper.getSaveList", str);
	}

	/**日志
	 * @param page
	 * @throws Exception
	 */
	public void batchSaveLog(Map<String, Object> map)throws Exception{
		dao.update("FundsSelfSummyMapper.batchSaveLog", map);
	}
	/**汇总
	 * @param page
	 * @throws Exception
	 */
	public void batchSummyBill(Map<String, Object> map)throws Exception{
		dao.update("FundsSelfSummyMapper.batchSummyBill", map);
	}

	/**取消汇总
	 * @param page
	 * @throws Exception
	 */
	public void batchCancelSummy(List<PageData> pd)throws Exception{
		dao.update("FundsSelfSummyMapper.batchCancelSummy", pd);
	}
}

