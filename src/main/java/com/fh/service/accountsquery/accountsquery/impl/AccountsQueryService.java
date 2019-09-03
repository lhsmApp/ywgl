package com.fh.service.accountsquery.accountsquery.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.accountsquery.accountsquery.AccountsQueryManager;

/** 
 * 说明： 对账查询
 * 创建人：张晓柳
 * 创建时间：2017-08-07
 * @version
 */
@Service("accountsqueryService")
public class AccountsQueryService implements AccountsQueryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("AccountsQueryMapper.datalistJqPage", page);
	}
	/**获取明细
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> dataListDetail(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("AccountsQueryMapper.dataListDetail", page);
	}
}

