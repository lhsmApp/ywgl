package com.fh.service.approvalconfig.approvalconfig.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.approvalconfig.approvalconfig.ApprovalConfigManager;

/** 
 * 说明： TB_APPROVAL_BUSINESS_CONFIG
 * 创建人：jiachao
 * 创建时间：2019-10-14
 * @version
 */
@Service("approvalconfigService")
public class ApprovalConfigService implements ApprovalConfigManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ApprovalConfigMapper.save", pd);
	}
	public void saveBusiness(PageData pd)throws Exception{
		dao.save("ApprovalConfigMapper.saveBusiness", pd);
	}
	public void saveDetail(PageData pd)throws Exception{
		dao.save("ApprovalConfigMapper.saveDetail", pd);
	}
	public void saveLevel(PageData pd)throws Exception{
		dao.save("ApprovalConfigMapper.saveLevel", pd);
	}
	public void saveMain(PageData pd)throws Exception{
		dao.save("ApprovalConfigMapper.saveMain", pd);
	}
	public void saveScheme(PageData pd)throws Exception{
		dao.save("ApprovalConfigMapper.saveScheme", pd);
	}
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ApprovalConfigMapper.delete", pd);
	}
	public void deleteBusiness(PageData pd)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteBusiness", pd);
	}
	public void deleteDetail(PageData pd)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteDetail", pd);
	}
	public void deleteLevel(PageData pd)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteLevel", pd);
	}
	public void deleteMain(PageData pd)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteMain", pd);
	}
	public void deleteScheme(PageData pd)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteScheme", pd);
	}
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ApprovalConfigMapper.edit", pd);
	}
	public void editBusiness(PageData pd)throws Exception{
		dao.update("ApprovalConfigMapper.editBusiness", pd);
	}
	public void editDetail(PageData pd)throws Exception{
		dao.update("ApprovalConfigMapper.editDetail", pd);
	}
	public void editLevel(PageData pd)throws Exception{
		dao.update("ApprovalConfigMapper.editLevel", pd);
	}
	public void editMain(PageData pd)throws Exception{
		dao.update("ApprovalConfigMapper.editMain", pd);
	}
	public void editScheme(PageData pd)throws Exception{
		dao.update("ApprovalConfigMapper.editScheme", pd);
	}
	public void editReturnDetail(PageData pd)throws Exception{
		dao.update("ApprovalConfigMapper.editReturnDetail", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listApprovalDetailAndMain", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listBusiness(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.datalistPageBusiness", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listDetail(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.datalistPageDetail", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listLevel(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.datalistPageLevel", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listMain(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.datalistPageMain", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listScheme(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.datalistPageScheme", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listByBusiness(Page page,String mapStr)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper."+mapStr, page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listStatistic(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listStatistic", page);
	}
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listAll", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listAllBusiness(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listAllBusiness", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listAllDetail(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listAllDetail", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listAllLevel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listAllLevel", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listAllMain(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listAllMain", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listAllScheme(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listAllScheme", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listApproval(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.listApproval", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> queryApproval(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.queryApproval", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> ListDetailByBillCode(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.ListDetailByBillCode", pd);
	}
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByIdBusiness(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ApprovalConfigMapper.findByIdBusiness", pd);
	}
	public PageData findByIdDetail(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ApprovalConfigMapper.findByIdDetail", pd);
	}
	public PageData findByIdLevel(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ApprovalConfigMapper.findByIdLevel", pd);
	}
	public PageData findByIdMain(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ApprovalConfigMapper.findByIdMain", pd);
	}
	public PageData findByIdScheme(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ApprovalConfigMapper.findByIdScheme", pd);
	}
	public PageData findByIdSchemeUnit(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ApprovalConfigMapper.findByIdSchemeUnit", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> findLevelById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ApprovalConfigMapper.findByIdLevel", pd);
	}
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllBusiness(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteAllBusiness", ArrayDATA_IDS);
	}
	public void deleteAllDetail(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteAllDetail", ArrayDATA_IDS);
	}
	public void deleteAllLevel(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteAllLevel", ArrayDATA_IDS);
	}
	public void deleteAllMain(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteAllMain", ArrayDATA_IDS);
	}
	public void deleteAllScheme(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ApprovalConfigMapper.deleteAllScheme", ArrayDATA_IDS);
	}
	
}

