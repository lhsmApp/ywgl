package com.fh.service.dataReporting.erpuserlist;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： ERP用户清单接口
 * 创建人：jiachao
 * 创建时间：2019-11-27
 * @version
 */
public interface ERPUserListManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
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
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
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
	
}

