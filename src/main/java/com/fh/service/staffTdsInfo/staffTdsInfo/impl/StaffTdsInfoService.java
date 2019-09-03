package com.fh.service.staffTdsInfo.staffTdsInfo.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.staffTdsInfo.staffTdsInfo.StaffTdsInfoManager;

/** 
 * 说明：个税扣除项导入
 * 创建人：zhangxiaoliu
 * 创建时间：2019-01-28
 */
@Service("staffTdsInfoService")
public class StaffTdsInfoService implements StaffTdsInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("StaffTdsInfoMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("StaffTdsInfoMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("StaffTdsInfoMapper.getFooterSummary", page);
	}

	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRepeat(List<PageData> listData)throws Exception{
		return (List<PageData>)dao.findForList("StaffTdsInfoMapper.getRepeat", listData);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("StaffTdsInfoMapper.exportList", page);
	}
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffTdsInfoMapper.exportModel", pd);
	}

	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("StaffTdsInfoMapper.deleteAll", listData);
	}

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("StaffTdsInfoMapper.batchDelAndIns", listData);
	}

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchCoverAdd(List<PageData> listData)throws Exception{
		dao.update("StaffTdsInfoMapper.batchCoverAdd", listData);
	}

	/**复制
	 * @param pd
	 * @throws Exception
	 */
	public void batchCopyAdd(List<PageData> listData)throws Exception{
		dao.update("StaffTdsInfoMapper.batchCopyAdd", listData);
	}
}

