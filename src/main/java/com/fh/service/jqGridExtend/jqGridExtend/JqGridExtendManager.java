package com.fh.service.jqGridExtend.jqGridExtend;

import java.util.List;

import com.fh.entity.JqGridModel;
import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： jqtest接口
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-08
 * @version
 */
public interface JqGridExtendManager{

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
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(JqPage page)throws Exception;
	/**明细
	 * @param
	 * @throws Exception
	 */
	public List<PageData> getDetailList(PageData pd)throws Exception;
	
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
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateAll(List<JqGridModel> pd)throws Exception;
	
	/**导入
	 * @param pd
	 * @throws Exception
	 */
	public void batchImport(List<JqGridModel> pd)throws Exception;
	
}

