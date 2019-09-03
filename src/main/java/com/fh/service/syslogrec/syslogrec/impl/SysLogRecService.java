package com.fh.service.syslogrec.syslogrec.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.syslogrec.syslogrec.SysLogRecManager;

/** 
 * 说明：  日志
 * 创建人：zhangxiaoliu
 * 创建时间：2018-06-25
 * @version
 */
@Service("syslogrecService")
public class SysLogRecService implements SysLogRecManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysLogRecMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("SysLogRecMapper.countJqGridExtend", page);
	}

	/**导出到excel
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> export(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysLogRecMapper.export", page);
	}
}

