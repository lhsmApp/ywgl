package com.fh.service.kpSheet.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.service.kpSheet.KpSheetManager;
import com.fh.util.PageData;

/** 
 * 说明：  
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("kpSheetService")
public class KpSheetService implements KpSheetManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRptKpList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("KpSheetMapper.getRptKpList", pd);
	}
}

