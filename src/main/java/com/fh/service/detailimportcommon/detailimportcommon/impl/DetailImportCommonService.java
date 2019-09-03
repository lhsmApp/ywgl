package com.fh.service.detailimportcommon.detailimportcommon.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.fh.service.detailimportcommon.detailimportcommon.DetailImportCommonManager;

/** 
 * 说明： 明细导入共有的
 * 创建人：zhangxiaoliu
 * 创建时间：2018-09-05
 * @version
 */
@Service("detailimportcommonService")
public class DetailImportCommonService implements DetailImportCommonManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	/**获取汇总里的明细
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getDetailList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DetailImportCommonMapper.getDetailList", pd);
	}
	
	/**获取汇总数据
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getSum(Map<String, String> map)throws Exception{
		return (List<PageData>)dao.findForList("DetailImportCommonMapper.getSum", map);
	}

	
	
	
}

