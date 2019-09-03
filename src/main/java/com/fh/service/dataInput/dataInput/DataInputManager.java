package com.fh.service.dataInput.dataInput;

import java.util.List;
import java.util.Map;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
public interface DataInputManager{
	
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
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(JqPage page)throws Exception;

	/**通过流水号获取流水号，用于判断数据是否变更 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getRepeatRecord(List<PageData> listData)throws Exception;

	/**复制
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getHaveCopyRecord(PageData pd)throws Exception;

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception;
	public void batchUpdateDatabaseHorizontal(List<PageData> listData)throws Exception;
	
	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception;
	
	/**复制
	 * @param 
	 * @throws Exception
	 */
	public void batchCopyAll(PageData pd)throws Exception;
	
}

