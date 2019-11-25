package com.fh.service.dataReporting.erpofficialacctapplication.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.dataReporting.erpofficialacctapplication.ERPOfficialAcctApplicationManager;

/** 
 * 说明： 正式账号申请处理模块
 * 创建人：xinyuLo
 * 创建时间：2019-10-10
 * @version
 */
@Service("erpofficialacctapplicationService")
public class ERPOfficialAcctApplicationService implements ERPOfficialAcctApplicationManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ERPOfficialAcctApplicationMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ERPOfficialAcctApplicationMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ERPOfficialAcctApplicationMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ERPOfficialAcctApplicationMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ERPOfficialAcctApplicationMapper.listAll", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ERPOfficialAcctApplicationMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**导出模板
	 * @param PageData
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ERPOfficialAcctApplicationMapper.exportModel", pd);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ERPOfficialAcctApplicationMapper.exportList", page);
	}
	
	/**更新数据
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void grcUpdateDatabase(List<PageData> listData) throws Exception {
		dao.update("ERPOfficialAcctApplicationMapper.delAndIns", listData);
	}
	
	/**
	 *  批量审批
	 * @param pageData
	 * @throws Exception
	 */
	public void editReportState(PageData pageData)throws Exception{
		dao.update("ERPOfficialAcctApplicationMapper.editReportState",pageData);
	}

	/**
	 * 业务期间集合
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBusiDate(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ERPOfficialAcctApplicationMapper.listBusiDate", pd);
	}
}

