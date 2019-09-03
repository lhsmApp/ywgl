package com.fh.service.financeaccounts.financeaccounts;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 财务对账
 * 创建人：张晓柳
 * 创建时间：2017-07-28
 * @version
 */
public interface FinanceAccountsManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	
	/**获取明细
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> dataListDetail(JqPage page)throws Exception;
	
}

