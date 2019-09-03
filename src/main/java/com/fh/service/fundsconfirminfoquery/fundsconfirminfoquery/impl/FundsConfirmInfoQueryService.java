package com.fh.service.fundsconfirminfoquery.fundsconfirminfoquery.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.fundsconfirminfoquery.fundsconfirminfoquery.FundsConfirmInfoQueryManager;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
@Service("fundsconfirminfoqueryService")
public class FundsConfirmInfoQueryService implements FundsConfirmInfoQueryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("FundsConfirmInfoQueryMapper.datalistJqPage", page);
	}
}

