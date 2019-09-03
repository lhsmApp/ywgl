package com.fh.service.stadium.stadium;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 体育场馆管理接口
* @ClassName: StadiumManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jp
* @date 2017年6月30日
*
 */
public interface StadiumManager{

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
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(全部)根据条件搜索
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllByCondition(PageData pd)throws Exception;
	
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
	
	/**通过名字获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByStaName(PageData pd)throws Exception;
	
}

