package com.fh.service.financeaccounts.financeaccounts.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.financeaccounts.financeaccounts.FinanceAccountsManager;

/** 
 * 说明： 公积金汇总
 * 创建人：张晓柳
 * 创建时间：2017-07-28
 * @version
 */
@Service("financeaccountsService")
public class FinanceAccountsService implements FinanceAccountsManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("FinanceAccountsMapper.datalistJqPage", page);
	}
	/**获取明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> dataListDetail(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("FinanceAccountsMapper.dataListDetail", page);
	}
}

