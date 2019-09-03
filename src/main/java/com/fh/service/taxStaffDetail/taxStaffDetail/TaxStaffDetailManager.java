package com.fh.service.taxStaffDetail.taxStaffDetail;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明：月度工资及个税导入
 * 创建人：jiachao
 * 创建时间：2019-08-07
 */
public interface TaxStaffDetailManager{
	
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
	
	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRepeat(List<PageData> listData)throws Exception;

	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(JqPage page)throws Exception;
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

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception;

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchCoverAdd(List<PageData> listData)throws Exception;
}

