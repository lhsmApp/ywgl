package com.fh.service.sysConfig.sysconfig;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 系统配置接口
* @ClassName: SysConfigManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
public interface SysConfigManager{

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
	
	/**更新业务期间
	 * @param pd
	 * @throws Exception
	 */
	//public void updateBusidate(String busiDate)throws Exception;
	
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


	/**获取系统期间
	 * 张晓柳
	 * @param pd
	 * @throws Exception
	 */
	public String currentSection(PageData pd)throws Exception;
	
	/**获取系统配置信息
	 * @param pd
	 * @throws Exception
	 */
	public String getSysConfigByKey(PageData pd)throws Exception;
}

