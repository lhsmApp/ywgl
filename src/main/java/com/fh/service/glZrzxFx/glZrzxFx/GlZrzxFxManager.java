package com.fh.service.glZrzxFx.glZrzxFx;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： 
 * 创建人：zhangxiaoliu
 * 创建时间：2017-09-14
 * @version
 */
public interface GlZrzxFxManager{
	
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
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findById(List<PageData> listData)throws Exception;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(List<PageData> listData)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(List<PageData> listData)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll()throws Exception;
}

