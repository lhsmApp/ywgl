package com.fh.service.dataReporting.erptempacctapplication;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： ERP临时账号申请处理模块接口
 * 创建人：xinyuLo
 * 创建时间：2019-10-09
 * @version
 */
public interface ERPTempAcctApplicationManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**生成/撤销延期单据
	 * @param pd
	 * @throws Exception
	 */
	public void editDelayData(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**下载模板
	 * @param PageData
	 * @throws Exception
	 */
	public List<PageData> exportModel(PageData getPd) throws Exception;

	/**
	 * 导出列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(Page page) throws Exception;
	
	/**更新数据
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void grcUpdateDatabase(List<PageData> listData) throws Exception;
	
	/**删除正式帐号插入临时帐号
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void delAndInsertTempData(PageData pd) throws Exception;
	
	/**
	 * 批量审批/驳回
	 * @param pd
	 * @throws Exception
	 */
	public void editReportState(PageData pd)throws Exception;
	
	/**
	 * 业务期间集合
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listBusiDate(PageData pd)throws Exception;
	
	/**按员工编号查找
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByStaffCode(PageData pd)throws Exception;
}

