package com.fh.service.taxExternalSystemData.taxExternalSystemData;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/**
 * 税务系统数据导入
* @ClassName: TaxExternalSystemDataManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年8月7日
*
 */
public interface TaxExternalSystemDataManager{
	
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
	
	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRepeatList(List<PageData> list)throws Exception;
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception;
	
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

	
	/**获取汇总要用数据
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> getSumUseList(PageData pd)throws Exception;
}

