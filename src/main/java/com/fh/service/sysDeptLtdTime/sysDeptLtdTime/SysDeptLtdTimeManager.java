package com.fh.service.sysDeptLtdTime.sysDeptLtdTime;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.SysDeptLtdTime;
import com.fh.util.PageData;

/** 
 * 说明： 
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-19
 * @version
 */
public interface SysDeptLtdTimeManager{
	
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
	public List<SysDeptLtdTime> getRepeatList(List<PageData> list)throws Exception;
	
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
	
	/**
	 * @param pd
	 * 张晓柳
	 * @throws Exception
	 */
	public SysDeptLtdTime getUseSysDeptLtdTime(SysDeptLtdTime item)throws Exception;
	
	
	
}

