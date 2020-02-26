package com.fh.service.assess.impl;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.service.assess.AssessDataManager;
import com.fh.util.PageData;

/** 
 * 说明：考核数据导入
 * 创建人：jiachao
 * 创建时间：2019-12-07
 */
@Service("assessDataService")
public class AssessDataService implements AssessDataManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		List<PageData> list;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			list= (List<PageData>)dao.findForList("NewAssetsBpdMapper.datalistJqPage", page);
			break;
		case "Z1AM2":
			list= (List<PageData>)dao.findForList("AmsAotdMapper.datalistJqPage", page);
			break;
		case "Z1FI1":
			list= (List<PageData>)dao.findForList("FmisCfttditMapper.datalistJqPage", page);
			break;
		case "Z1MM1":
			list= (List<PageData>)dao.findForList("PiNitMapper.datalistJqPage", page);
			break;
		case "Z1MM2":
			list= (List<PageData>)dao.findForList("PaToNitMapper.datalistJqPage", page);
			break;
		case "Z1PS1":
			list= (List<PageData>)dao.findForList("ProjectScheduleNitMapper.datalistJqPage", page);
			break;
		case "Z2AM1":
			list= (List<PageData>)dao.findForList("NewAssetsCardMapper.datalistJqPage", page);
			break;
		case "Z2MM1":
			list= (List<PageData>)dao.findForList("UmdMapper.datalistJqPage", page);
			break;
		case "Z2PM1":
			list= (List<PageData>)dao.findForList("EliMapper.datalistJqPage", page);
			break;
		case "Z2PS1":
			list= (List<PageData>)dao.findForList("IncompleteProMapper.datalistJqPage", page);
			break;
		case "Z3AM1":
			list= (List<PageData>)dao.findForList("AssetsCxMapper.datalistJqPage", page);
			break;
		case "Z3FI2":
			list= (List<PageData>)dao.findForList("IntegrateVoucherCxMapper.datalistJqPage", page);
			break;
		default:
			list= (List<PageData>)dao.findForList("NewAssetsBpdMapper.datalistJqPage", page);
			break;
		}
		return list;
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		int count=0;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			count=(int)dao.findForObject("NewAssetsBpdMapper.countJqGridExtend", page);
			break;
		case "Z1AM2":
			count=(int)dao.findForObject("AmsAotdMapper.countJqGridExtend", page);
			break;
		case "Z1FI1":
			count=(int)dao.findForObject("FmisCfttditMapper.countJqGridExtend", page);
			break;
		case "Z1MM1":
			count=(int)dao.findForObject("PiNitMapper.countJqGridExtend", page);
			break;
		case "Z1MM2":
			count=(int)dao.findForObject("PaToNitMapper.countJqGridExtend", page);
			break;
		case "Z1PS1":
			count=(int)dao.findForObject("ProjectScheduleNitMapper.countJqGridExtend", page);
			break;
		case "Z2AM1":
			count=(int)dao.findForObject("NewAssetsCardMapper.countJqGridExtend", page);
			break;
		case "Z2MM1":
			count=(int)dao.findForObject("UmdMapper.countJqGridExtend", page);
			break;	
		case "Z2PM1":
			count=(int)dao.findForObject("EliMapper.countJqGridExtend", page);
			break;	
		case "Z2PS1":
			count=(int)dao.findForObject("IncompleteProMapper.countJqGridExtend", page);
			break;	
		case "Z3AM1":
			count=(int)dao.findForObject("AssetsCxMapper.countJqGridExtend", page);
			break;	
		case "Z3FI2":
			count=(int)dao.findForObject("IntegrateVoucherCxMapper.countJqGridExtend", page);
			break;	
		default:
			count=(int)dao.findForObject("NewAssetsBpdMapper.countJqGridExtend", page);
			break;
		}
		return count;
	}
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception{
		PageData pageData;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			pageData=(PageData)dao.findForObject("NewAssetsBpdMapper.getFooterSummary", page);
			break;
		case "Z1AM2":
			pageData=(PageData)dao.findForObject("AmsAotdMapper.getFooterSummary", page);
			break;
		case "Z1FI1":
			pageData=(PageData)dao.findForObject("FmisCfttditMapper.getFooterSummary", page);
			break;
		case "Z1MM1":
			pageData=(PageData)dao.findForObject("PiNitMapper.getFooterSummary", page);
			break;
		case "Z1MM2":
			pageData=(PageData)dao.findForObject("PaToNitMapper.getFooterSummary", page);
			break;
		case "Z1PS1":
			pageData=(PageData)dao.findForObject("ProjectScheduleNitMapper.getFooterSummary", page);
			break;
		case "Z2AM1":
			pageData=(PageData)dao.findForObject("NewAssetsCardMapper.getFooterSummary", page);
			break;
		case "Z2MM1":
			pageData=(PageData)dao.findForObject("UmdMapper.getFooterSummary", page);
			break;
		case "Z2PM1":
			pageData=(PageData)dao.findForObject("EliMapper.getFooterSummary", page);
			break;
		case "Z2PS1":
			pageData=(PageData)dao.findForObject("IncompleteProMapper.getFooterSummary", page);
			break;
		case "Z3AM1":
			pageData=(PageData)dao.findForObject("AssetsCxMapper.getFooterSummary", page);
			break;
		case "Z3FI2":
			pageData=(PageData)dao.findForObject("IntegrateVoucherCxMapper.getFooterSummary", page);
			break;
		default:
			pageData=(PageData)dao.findForObject("NewAssetsBpdMapper.getFooterSummary", page);
			break;
		}
		return pageData;
	}

	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRepeat(List<PageData> listData)throws Exception{
		return (List<PageData>)dao.findForList("NewAssetsBpdMapper.getRepeat", listData);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		List<PageData> list;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			list= (List<PageData>)dao.findForList("NewAssetsBpdMapper.exportList", page);
			break;
		case "Z1AM2":
			list= (List<PageData>)dao.findForList("AmsAotdMapper.exportList", page);
			break;
		case "Z1FI1":
			list= (List<PageData>)dao.findForList("FmisCfttditMapper.exportList", page);
			break;
		case "Z1MM1":
			list= (List<PageData>)dao.findForList("PiNitMapper.exportList", page);
			break;
		case "Z1MM2":
			list= (List<PageData>)dao.findForList("PaToNitMapper.exportList", page);
			break;
		case "Z1PS1":
			list= (List<PageData>)dao.findForList("ProjectScheduleNitMapper.exportList", page);
			break;
		case "Z2AM1":
			list= (List<PageData>)dao.findForList("NewAssetsCardMapper.exportList", page);
			break;
		case "Z2MM1":
			list= (List<PageData>)dao.findForList("UmdMapper.exportList", page);
			break;
		case "Z2PM1":
			list= (List<PageData>)dao.findForList("EliMapper.exportList", page);
			break;
		case "Z2PS1":
			list= (List<PageData>)dao.findForList("IncompleteProMapper.exportList", page);
			break;
		case "Z3AM1":
			list= (List<PageData>)dao.findForList("AssetsCxMapper.exportList", page);
			break;
		case "Z3FI2":
			list= (List<PageData>)dao.findForList("IntegrateVoucherCxMapper.exportList", page);
			break;
		default:
			list= (List<PageData>)dao.findForList("NewAssetsBpdMapper.exportList", page);
			break;
		}
		return list; 
	}
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		List<PageData> list;
		switch (pd.getString("KPI_CODE")) {
		case "Z1AM1":
			list= (List<PageData>)dao.findForList("NewAssetsBpdMapper.exportModel", pd);
			break;
		case "Z1AM2":
			list= (List<PageData>)dao.findForList("AmsAotdMapper.exportModel", pd);
			break;
		case "Z1FI1":
			list= (List<PageData>)dao.findForList("FmisCfttditMapper.exportModel", pd);
			break;
		case "Z1MM1":
			list= (List<PageData>)dao.findForList("PiNitMapper.exportModel", pd);
			break;
		case "Z1MM2":
			list= (List<PageData>)dao.findForList("PaToNitMapper.exportModel", pd);
			break;
		case "Z1PS1":
			list= (List<PageData>)dao.findForList("ProjectScheduleNitMapper.exportModel", pd);
			break;
		case "Z2AM1":
			list= (List<PageData>)dao.findForList("NewAssetsCardMapper.exportModel", pd);
			break;
		case "Z2MM1":
			list= (List<PageData>)dao.findForList("UmdMapper.exportModel", pd);
			break;
		case "Z2PM1":
			list= (List<PageData>)dao.findForList("EliMapper.exportModel", pd);
			break;
		case "Z2PS1":
			list= (List<PageData>)dao.findForList("IncompleteProMapper.exportModel", pd);
			break;
		case "Z3AM1":
			list= (List<PageData>)dao.findForList("AssetsCxMapper.exportModel", pd);
			break;
		case "Z3FI2":
			list= (List<PageData>)dao.findForList("IntegrateVoucherCxMapper.exportModel", pd);
			break;
		default:
			list= (List<PageData>)dao.findForList("NewAssetsBpdMapper.exportModel", pd);
			break;
		}
		return list; 
	}

	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData,String kpiCode)throws Exception{
		switch (kpiCode) {
		case "Z1AM1":
			dao.delete("NewAssetsBpdMapper.deleteAll", listData);
			break;
		case "Z1AM2":
			dao.delete("AmsAotdMapper.deleteAll", listData);
			break;
		case "Z1FI1":
			dao.delete("FmisCfttditMapper.deleteAll", listData);
			break;
		case "Z1MM1":
			dao.delete("PiNitMapper.deleteAll", listData);
			break;
		case "Z1MM2":
			dao.delete("PaToNitMapper.deleteAll", listData);
			break;
		case "Z1PS1":
			dao.delete("ProjectScheduleNitMapper.deleteAll", listData);
			break;
		case "Z2AM1":
			dao.delete("NewAssetsCardMapper.deleteAll", listData);
			break;
		case "Z2MM1":
			dao.delete("UmdMapper.deleteAll", listData);
			break;
		case "Z2PM1":
			dao.delete("EliMapper.deleteAll", listData);
			break;
		case "Z2PS1":
			dao.delete("IncompleteProMapper.deleteAll", listData);
			break;
		case "Z3AM1":
			dao.delete("AssetsCxMapper.deleteAll", listData);
			break;
		case "Z3FI2":
			dao.delete("IntegrateVoucherCxMapper.deleteAll", listData);
			break;
		default:
			dao.delete("NewAssetsBpdMapper.deleteAll", listData);
			break;
		}
		
	}

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData,String kpiCode)throws Exception{
		switch (kpiCode) {
		case "Z1AM1":
			dao.update("NewAssetsBpdMapper.batchDelAndIns", listData);
			break;
		case "Z1AM2":
			dao.update("AmsAotdMapper.batchDelAndIns", listData);
			break;
		case "Z1FI1":
			dao.update("FmisCfttditMapper.batchDelAndIns", listData);
			break;
		case "Z1MM1":
			dao.update("PiNitMapper.batchDelAndIns", listData);
			break;
		case "Z1MM2":
			dao.update("PaToNitMapper.batchDelAndIns", listData);
			break;
		case "Z1PS1":
			dao.update("ProjectScheduleNitMapper.batchDelAndIns", listData);
			break;
		case "Z2AM1":
			dao.update("NewAssetsCardMapper.batchDelAndIns", listData);
			break;
		case "Z2MM1":
			dao.update("UmdMapper.batchDelAndIns", listData);
			break;
		case "Z2PM1":
			dao.update("EliMapper.batchDelAndIns", listData);
			break;
		case "Z2PS1":
			dao.update("IncompleteProMapper.batchDelAndIns", listData);
			break;
		case "Z3AM1":
			dao.update("AssetsCxMapper.batchDelAndIns", listData);
			break;
		case "Z3FI2":
			dao.update("IntegrateVoucherCxMapper.batchDelAndIns", listData);
			break;
		default:
			dao.update("NewAssetsBpdMapper.batchDelAndIns", listData);
			break;
		}
		
	}

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchCoverAdd(List<PageData> listData,String kpiCode)throws Exception{
		switch (kpiCode) {
		case "Z1AM1":
			dao.update("NewAssetsBpdMapper.batchCoverAdd", listData);
			break;
		case "Z1AM2":
			dao.update("AmsAotdMapper.batchCoverAdd", listData);
			break;
		case "Z1FI1":
			dao.update("FmisCfttditMapper.batchCoverAdd", listData);
			break;
		case "Z1MM1":
			dao.update("PiNitMapper.batchCoverAdd", listData);
			break;
		case "Z1MM2":
			dao.update("PaToNitMapper.batchCoverAdd", listData);
			break;
		case "Z1PS1":
			dao.update("ProjectScheduleNitMapper.batchCoverAdd", listData);
			break;
		case "Z2AM1":
			dao.update("NewAssetsCardMapper.batchCoverAdd", listData);
			break;
		case "Z2MM1":
			dao.update("UmdMapper.batchCoverAdd", listData);
			break;
		case "Z2PM1":
			dao.update("EliMapper.batchCoverAdd", listData);
			break;
		case "Z2PS1":
			dao.update("IncompleteProMapper.batchCoverAdd", listData);
			break;
		case "Z3AM1":
			dao.update("AssetsCxMapper.batchCoverAdd", listData);
			break;
		case "Z3FI2":
			dao.update("IntegrateVoucherCxMapper.batchCoverAdd", listData);
			break;
		default:
			dao.update("NewAssetsBpdMapper.batchCoverAdd", listData);
			break;
		}
		
	}
}

