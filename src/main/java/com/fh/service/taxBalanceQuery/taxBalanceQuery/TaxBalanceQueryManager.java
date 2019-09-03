package com.fh.service.taxBalanceQuery.taxBalanceQuery;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/**
 * 个税差额查询
* @ClassName: TaxBalanceQueryManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年8月9日
*
 */
public interface TaxBalanceQueryManager{
	
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
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(JqPage page)throws Exception;
}

