package com.fh.service.fundssummyconfirm.fundssummyconfirm;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.SysConfirmInfo;
import com.fh.util.PageData;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
public interface FundsSummyConfirmManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception;
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception;

	/**明细
	 * @param 
	 * @throws Exception
	 */
	public List<PageData> getFirstDetailList(PageData pd)throws Exception;
	/**明细
	 * @param 
	 * @throws Exception
	 */
	public List<PageData> getSecondDetailList(PageData pd)throws Exception;

	/**
	 * @param 
	 * @throws Exception
	 */
	public List<PageData> getOperList(PageData pd)throws Exception;
	
}

