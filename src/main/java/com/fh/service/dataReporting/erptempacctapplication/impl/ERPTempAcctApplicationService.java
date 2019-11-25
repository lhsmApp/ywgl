package com.fh.service.dataReporting.erptempacctapplication.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.dataReporting.erptempacctapplication.ERPTempAcctApplicationManager;

/** 
 * 说明： ERP临时账号申请处理模块
 * 创建人：xinyuLo
 * 创建时间：2019-10-09
 * @version
 */
@Service("erptempacctapplicationService")
public class ERPTempAcctApplicationService implements ERPTempAcctApplicationManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ERPTempAcctApplicationMapper.save", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ERPTempAcctApplicationMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ERPTempAcctApplicationMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ERPTempAcctApplicationMapper.listAll", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ERPTempAcctApplicationMapper.deleteAll", ArrayDATA_IDS);
	}

	/**导出模板
	 * @param PageData
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ERPTempAcctApplicationMapper.exportModel", pd);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ERPTempAcctApplicationMapper.exportList", page);
	}
	
	/**更新数据
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void grcUpdateDatabase(List<PageData> listData) throws Exception {
		dao.update("ERPTempAcctApplicationMapper.delAndIns", listData);
	}

	/**
	 * 批量审批/驳回
	 * @param pd
	 * @throws Exception
	 */
	public void editReportState(PageData pd) throws Exception {
		dao.update("ERPTempAcctApplicationMapper.editReportState",pd);
	}

	/**
	 * 业务期间集合
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBusiDate(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ERPTempAcctApplicationMapper.listBusiDate", pd);
	}
}

