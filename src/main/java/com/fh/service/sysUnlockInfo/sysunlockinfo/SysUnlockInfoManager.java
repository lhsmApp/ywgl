package com.fh.service.sysUnlockInfo.sysunlockinfo;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 传输临时表接口
 * 创建人：lhsmplus
 * 创建时间：2017-08-22
 * @version
 */
public interface SysUnlockInfoManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(List<PageData> pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(List<PageData> pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(获取当前接口类型， 当前二级单位当前期间当前封存状态为封存的的传输列表)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listSyncDelUnlock(PageData pd)throws Exception;
	
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
	
}

