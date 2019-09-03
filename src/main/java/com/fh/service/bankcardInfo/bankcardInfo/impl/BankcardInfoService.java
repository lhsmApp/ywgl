package com.fh.service.bankcardInfo.bankcardInfo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.bankcardInfo.bankcardInfo.BankcardInfoManager;

/**
 * 银行卡号信息维护
* @ClassName: BankcardInfoService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年8月2日
*
 */
@Service("bankcardInfoService")
public class BankcardInfoService implements BankcardInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("BankcardInfoMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("BankcardInfoMapper.countJqGridExtend", page);
	}

	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("BankcardInfoMapper.exportList", page);
	}

	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BankcardInfoMapper.exportModel", pd);
	}

	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("BankcardInfoMapper.deleteAll", listData);
	}
	

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("BankcardInfoMapper.batchDelAndIns", listData);
	}
}

