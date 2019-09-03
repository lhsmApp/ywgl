package com.fh.service.accountsquery.accountsquery;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： 对账查询
 * 创建人：张晓柳
 * 创建时间：2017-08-07
 * @version
 */
public interface AccountsQueryManager{
	
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

