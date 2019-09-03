package com.fh.service.laborDetail.laborDetail;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明：劳务报酬所得导入
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
public interface LaborDetailManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception;
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception;
	
	/**通过流水号获取单号，用于判断数据是否变更
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getSerialNoBySerialNo(PageData pd)throws Exception;

	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(JqPage page)throws Exception;
	public List<PageData> exportSumList(JqPage page)throws Exception;
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportModel(PageData pd)throws Exception;
	
	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception;
	
	/**获取计算数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getDataCalculation(String tableName, String TmplUtil_KeyExtra,
			PageData pdInsetBackup, List<PageData> listAdd,
			String sqlRetSelect, String sqlSumByUserNameStaffIdent)throws Exception;
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception;
}

