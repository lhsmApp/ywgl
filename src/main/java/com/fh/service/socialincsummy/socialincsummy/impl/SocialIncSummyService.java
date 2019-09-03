package com.fh.service.socialincsummy.socialincsummy.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysSealed;
import com.fh.util.PageData;
import com.fh.service.socialincsummy.socialincsummy.SocialIncSummyManager;

/** 
 * 说明： 社保汇总
 * 创建人：zhangxiaoliu
 * 创建时间：2017-07-07
 * @version
 */
@Service("socialincsummyService")
public class SocialIncSummyService implements SocialIncSummyManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBillCodeList(PageData pd)throws Exception{
		return (List<String>)dao.findForList("SocialIncSummyMapper.getBillCodeList", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SocialIncSummyMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("SocialIncSummyMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("SocialIncSummyMapper.getFooterSummary", page);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findSummyDetailList(PageData page)throws Exception{
		return (List<PageData>)dao.findForList("SocialIncSummyMapper.findSummyDetailList", page);
	}
	
	/**作废
	 * @param 
	 * @throws Exception
	 */
	public void cancelAll(List<PageData> list)throws Exception{
		dao.batchUpdate("SocialIncSummyMapper.updateBillState", list);
	}
	
	/**汇总
	 * @param 
	 * @throws Exception
	 */
	public void saveSummyModelList(Map<String, Object> map)throws Exception{
		dao.update("SocialIncSummyMapper.saveSummy", map);
	}
}

