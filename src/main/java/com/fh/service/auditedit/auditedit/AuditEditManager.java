package com.fh.service.auditedit.auditedit;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 对账数据编辑
 * 创建人：zhangxiaoliu
 * 创建时间：2017-07-26
 * @version
 */
public interface AuditEditManager{
	
	/**获取数据
	 * 张晓柳
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByModel(PageData pd)throws Exception;
	public List<String> exportHaveUserCode(PageData listData)throws Exception;

	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(JqPage page)throws Exception;
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportModel(JqPage page)throws Exception;
	
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
	
	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception;
	
	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void deleteUpdateAll(List<PageData> listData)throws Exception;
	
	/**导入
	 * @param pd
	 * @throws Exception
	 */
	public void batchImport(List<PageData> listData)throws Exception;

}

