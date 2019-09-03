package com.fh.service.taxBalanceQuery.taxBalanceQuery.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.taxBalanceQuery.taxBalanceQuery.TaxBalanceQueryManager;

/**
 * 个税差额查询
* @ClassName: TaxBalanceQueryService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年8月9日
*
 */
@Service("taxBalanceQueryService")
public class TaxBalanceQueryService implements TaxBalanceQueryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("TaxBalanceQueryMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("TaxBalanceQueryMapper.countJqGridExtend", page);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("TaxBalanceQueryMapper.exportList", page);
	}
}

