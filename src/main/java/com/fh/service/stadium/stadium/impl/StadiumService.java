package com.fh.service.stadium.stadium.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.service.stadium.stadium.StadiumManager;

/**
 * 体育场馆管理
* @ClassName: StadiumService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoliu
* @date 2017年6月30日
*
 */
@Service("stadiumService")
public class StadiumService implements StadiumManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("StadiumMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("StadiumMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("StadiumMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		
		List<PageData> listPageData = (List<PageData>)dao.findForList("StadiumMapper.datalistPage", page);
		for (PageData pageData : listPageData) {
			pageData.put("STADI_INTR_CUT", StringUtil.subString(pageData.getString("STADI_INTR"), Const.CUT_STRING_NUM));
			pageData.put("REMARK_CUT", StringUtil.subString(pageData.getString("REMARK"), Const.CUT_STRING_NUM));
			pageData.put("STADI_SPOT_CUT", StringUtil.subString(pageData.getString("STADI_SPOT"), Const.CUT_STRING_NUM));
		}
		return listPageData;
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StadiumMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StadiumMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StadiumMapper.deleteAll", ArrayDATA_IDS);
	}

	/**列表(全部)根据条件搜索
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAllByCondition(PageData pd) throws Exception {
		List<PageData> listPageData = (List<PageData>)dao.findForList("StadiumMapper.listAllByCondition", pd);
		for (PageData pageData : listPageData) {
			pageData.put("STADI_INTR_CUT", StringUtil.subString(pageData.getString("STADI_INTR"), Const.CUT_STRING_NUM));
			pageData.put("REMARK_CUT", StringUtil.subString(pageData.getString("REMARK"), Const.CUT_STRING_NUM));
			pageData.put("STADI_SPOT_CUT", StringUtil.subString(pageData.getString("STADI_SPOT"), Const.CUT_STRING_NUM));
		}
		return listPageData;		
	}

	@Override
	public PageData findByStaName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("StadiumMapper.findByStaName", pd);
	}
	
}

