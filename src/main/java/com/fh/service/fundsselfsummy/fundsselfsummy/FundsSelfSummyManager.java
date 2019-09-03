package com.fh.service.fundsselfsummy.fundsselfsummy;

import java.util.List;
import java.util.Map;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
public interface FundsSelfSummyManager{

	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	public List<String> getBillCodeList(PageData pd)throws Exception;
	
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

	/**通过单号判断数据是否变更
	 * @param 
	 * @throws Exception
	 */
	public List<PageData> getCheckStateList(PageData pd)throws Exception;

	/**获取保存数据 
	 * @param 
	 * @throws Exception
	 */
	public List<PageData> getSaveList(String str)throws Exception;

	/**日志
	 * @param 
	 * @throws Exception
	 */
	public void batchSaveLog(Map<String, Object> map)throws Exception;
	/**汇总
	 * @param 
	 * @throws Exception
	 */
	public void batchSummyBill(Map<String, Object> map)throws Exception;
	
	/**取消汇总
	 * @param 
	 * @throws Exception
	 */
	public void batchCancelSummy(List<PageData> pd)throws Exception;
	
}

