package com.fh.service.socialOrganize.pesoactinfo;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 体育社会组织活动、资金、评价情况接口
* @ClassName: PesoactInfoManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zxl
* @date 2017年6月30日
*
 */
public interface PesoactInfoManager{

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
	
	/**列表
	 * @throws Exception
	 */
	public List<PageData> pesoNameList()throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	public List<PageData> hasDuplicateRecord(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

