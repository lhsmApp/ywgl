package com.fh.service.sysConfirmInfo.sysConfirmInfo;

import java.util.List;
import java.util.Map;

import com.fh.entity.JqPage;
import com.fh.entity.SysConfirmInfo;
import com.fh.util.PageData;

/** 
 * 说明： 工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-19
 * @version
 */
public interface SysConfirmInfoManager{

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
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(JqPage page)throws Exception;
	
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

	/**通过单号判断数据是否变更
	 * @param 
	 * @throws Exception
	 */
	public List<SysConfirmInfo> getCheckStateList(PageData pd)throws Exception;
	
	/**确认
	 * @param 
	 * @throws Exception
	 */
	public void batchEachConfirm(Map<String, Object> map)throws Exception;
	public void batchAllConfirm(Map<String, Object> map)throws Exception;
	
	/**取消确认
	 * @param 
	 * @throws Exception
	 */
	public void batchCancelConfirm(List<SysConfirmInfo> listData)throws Exception;

	/**
	 * @param pd
	 * @throws Exception
	 */
	public List<SysConfirmInfo> getConfirmMappingList(PageData mapping)throws Exception;
	
	
	
	
}

