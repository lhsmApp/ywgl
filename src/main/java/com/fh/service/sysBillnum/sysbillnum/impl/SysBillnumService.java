package com.fh.service.sysBillnum.sysbillnum.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.sysBillnum.sysbillnum.SysBillnumManager;
import com.fh.util.PageData;

/**
 *  最大单号维护
* @ClassName: SysBillnumService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月22日
*
 */
@Service("sysbillnumService")
public class SysBillnumService implements SysBillnumManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysBillnumMapper.edit", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysBillnumMapper.findById", pd);
	}
}

