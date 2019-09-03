package com.fh.service.staffRemitInfo.staffRemitInfo.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.staffRemitInfo.staffRemitInfo.StaffRemitInfoManager;

/** 
 * 说明：税延养老保险导入
 * 创建人：zhangxiaoliu
 * 创建时间：2019-03-06
 */
@Service("staffRemitInfoService")
public class StaffRemitInfoService implements StaffRemitInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("StaffRemitInfoMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("StaffRemitInfoMapper.countJqGridExtend", page);
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		return (PageData)dao.findForObject("StaffRemitInfoMapper.getFooterSummary", page);
	}

	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRepeat(List<PageData> listData)throws Exception{
		return (List<PageData>)dao.findForList("StaffRemitInfoMapper.getRepeat", listData);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("StaffRemitInfoMapper.exportList", page);
	}
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffRemitInfoMapper.exportModel", pd);
	}

	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("StaffRemitInfoMapper.deleteAll", listData);
	}

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("StaffRemitInfoMapper.batchDelAndIns", listData);
	}

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchCoverAdd(List<PageData> listData)throws Exception{
		dao.update("StaffRemitInfoMapper.batchCoverAdd", listData);
	}

	/**复制
	 * @param pd
	 * @throws Exception
	 */
	public void batchCopyAdd(List<PageData> listData)throws Exception{
		dao.update("StaffRemitInfoMapper.batchCopyAdd", listData);
	}
}

