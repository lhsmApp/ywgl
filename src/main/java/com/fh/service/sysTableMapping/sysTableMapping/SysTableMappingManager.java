package com.fh.service.sysTableMapping.sysTableMapping;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.SysTableMapping;
import com.fh.util.PageData;

/** 
 * 说明： 
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-19
 * @version
 */
public interface SysTableMappingManager{
	
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
	public List<SysTableMapping> getRepeatList(List<PageData> list)throws Exception;
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception;
	
	
	
	
	
	
	

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
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	
	/**
	 * @param pd
	 * 张晓柳
	 * @throws Exception
	 */
	public List<SysTableMapping> getUseTableMapping(SysTableMapping mapping)throws Exception;
	
	
	
}

