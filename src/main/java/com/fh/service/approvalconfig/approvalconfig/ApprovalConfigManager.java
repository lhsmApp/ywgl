package com.fh.service.approvalconfig.approvalconfig;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： TB_APPROVAL_BUSINESS_CONFIG接口
 * 创建人：jiachao
 * 创建时间：2019-10-14
 * @version
 */
public interface ApprovalConfigManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	public void saveBusiness(PageData pd)throws Exception;
	public void saveDetail(PageData pd)throws Exception;
	public void saveLevel(PageData pd)throws Exception;
	public void saveMain(PageData pd)throws Exception;
	public void saveScheme(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	public void deleteBusiness(PageData pd)throws Exception;
	public void deleteDetail(PageData pd)throws Exception;
	public void deleteLevel(PageData pd)throws Exception;
	public void deleteMain(PageData pd)throws Exception;
	public void deleteScheme(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	public void editBusiness(PageData pd)throws Exception;
	public void editDetail(PageData pd)throws Exception;
	public void editLevel(PageData pd)throws Exception;
	public void editMain(PageData pd)throws Exception;
	public void editScheme(PageData pd)throws Exception;
	public void editReturnDetail(PageData pd)throws Exception;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(PageData pd)throws Exception;
	public List<PageData> listBusiness(Page page)throws Exception;
	public List<PageData> listDetail(Page page)throws Exception;
	public List<PageData> listLevel(Page page)throws Exception;
	public List<PageData> listMain(Page page)throws Exception;
	public List<PageData> listScheme(Page page)throws Exception;
	public List<PageData> listByBusiness(Page page,String mapStr)throws Exception;
	public List<PageData> listStatistic(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	public List<PageData> listAllBusiness(PageData pd)throws Exception;
	public List<PageData> listAllDetail(PageData pd)throws Exception;
	public List<PageData> listAllLevel(PageData pd)throws Exception;
	public List<PageData> listAllMain(PageData pd)throws Exception;
	public List<PageData> listAllScheme(PageData pd)throws Exception;
	public List<PageData> listApproval(PageData pd)throws Exception;
	public List<PageData> queryApproval(PageData pd)throws Exception;
	public List<PageData> ListDetailByBillCode(PageData pd)throws Exception;
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByIdBusiness(PageData pd)throws Exception;
	public PageData findByIdDetail(PageData pd)throws Exception;
	public PageData findByIdLevel(PageData pd)throws Exception;
	public PageData findByIdMain(PageData pd)throws Exception;
	public PageData findByIdScheme(PageData pd)throws Exception;
	public PageData findByIdSchemeUnit(PageData pd)throws Exception;
	public List<PageData> findLevelById(PageData pd)throws Exception;
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllBusiness(String[] ArrayDATA_IDS)throws Exception;
	public void deleteAllDetail(String[] ArrayDATA_IDS)throws Exception;
	public void deleteAllLevel(String[] ArrayDATA_IDS)throws Exception;
	public void deleteAllMain(String[] ArrayDATA_IDS)throws Exception;
	public void deleteAllScheme(String[] ArrayDATA_IDS)throws Exception;
	
}

