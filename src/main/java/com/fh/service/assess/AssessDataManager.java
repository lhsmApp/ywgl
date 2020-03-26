package com.fh.service.assess;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明：考核数据导入
 * 创建人：jiachao
 * 创建时间：2019-12-07
 */
public interface AssessDataManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	/**排行榜列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPageRank(PageData pd)throws Exception;
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception;
	/**获取排行榜记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtendRank(JqPage page)throws Exception;
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

	/**用于当前月份是否已经导入过数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData checkRepeat(String kpiCode,String busiDate)throws Exception;
	
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
	public void deleteAll(List<PageData> listData,String kpiCode)throws Exception;

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData,String kpiCode)throws Exception;

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchCoverAdd(List<PageData> listData,String kpiCode)throws Exception;
}

