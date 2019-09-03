package com.fh.service.betting.betting.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.service.betting.betting.BettingManager;

/**
 * 投注站管理
* @ClassName: BettingService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Service("bettingService")
public class BettingService implements BettingManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BettingMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BettingMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BettingMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		//return (List<PageData>)dao.findForList("BettingMapper.datalistPage", page);
		List<PageData> listPageData=(List<PageData>)dao.findForList("BettingMapper.datalistPage", page);
		for (PageData pageData : listPageData) {
			pageData.put("BETT_ADDR_CUT", StringUtil.subString(pageData.getString("BETT_ADDR"), Const.CUT_STRING_NUM));
			pageData.put("BETT_INTR_CUT", StringUtil.subString(pageData.getString("BETT_INTR"), Const.CUT_STRING_NUM));
			pageData.put("REMARK_CUT", StringUtil.subString(pageData.getString("REMARK"), Const.CUT_STRING_NUM));
		}
		return listPageData;
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BettingMapper.listAll", pd);
	}
	
	/**列表(全部)根据条件搜索
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByCondition(PageData pd)throws Exception{
		//return (List<PageData>)dao.findForList("BettingMapper.listAllByCondition", pd);
		List<PageData> listPageData=(List<PageData>)dao.findForList("BettingMapper.listAllByCondition", pd);
		for (PageData pageData : listPageData) {
			//pageData.put("BETT_ADDR_CUT", StringUtil.subString(pageData.getString("BETT_ADDR"), Const.CUT_STRING_NUM));
			pageData.put("BETT_INTR_CUT", StringUtil.subString(pageData.getString("BETT_INTR"), Const.CUT_STRING_NUM));
		}
		return listPageData;
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BettingMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BettingMapper.deleteAll", ArrayDATA_IDS);
	}
	

	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByIDCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BettingMapper.findByIDCode", pd);
	}
}

