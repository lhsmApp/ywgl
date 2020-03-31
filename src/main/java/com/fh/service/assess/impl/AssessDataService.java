package com.fh.service.assess.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.Else;
import com.fh.controller.busidate.BusidateController;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.service.assess.AssessDataManager;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.base.ConvertUtils;
import com.mysql.fabric.xmlrpc.base.Array;

import cn.jpush.http.StringUtils;

/**
 * 说明：考核数据导入 创建人：jiachao 创建时间：2019-12-07
 */
@Service("assessDataService")
public class AssessDataService implements AssessDataManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page) throws Exception {
		List<PageData> list;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			list = (List<PageData>) dao.findForList("NewAssetsBpdMapper.datalistJqPage", page);
			break;
		case "Z1AM2":
			list = (List<PageData>) dao.findForList("AmsAotdMapper.datalistJqPage", page);
			break;
		case "Z1FI1":
			list = (List<PageData>) dao.findForList("FmisCfttditMapper.datalistJqPage", page);
			break;
		case "Z1MM1":
			list = (List<PageData>) dao.findForList("PiNitMapper.datalistJqPage", page);
			break;
		case "Z1MM2":
			list = (List<PageData>) dao.findForList("PaToNitMapper.datalistJqPage", page);
			break;
		case "Z1PS1":
			list = (List<PageData>) dao.findForList("ProjectScheduleNitMapper.datalistJqPage", page);
			break;
		case "Z2AM1":
			list = (List<PageData>) dao.findForList("NewAssetsCardMapper.datalistJqPage", page);
			break;
		case "Z2MM1":
			list = (List<PageData>) dao.findForList("UmdMapper.datalistJqPage", page);
			break;
		case "Z2PM1":
			list = (List<PageData>) dao.findForList("EliMapper.datalistJqPage", page);
			break;
		case "Z2PS1":
			list = (List<PageData>) dao.findForList("IncompleteProMapper.datalistJqPage", page);
			break;
		case "Z3AM1":
			list = (List<PageData>) dao.findForList("AssetsCxMapper.datalistJqPage", page);
			break;
		case "Z3FI2":
			list = (List<PageData>) dao.findForList("IntegrateVoucherCxMapper.datalistJqPage", page);
			break;
		default:
			PageData pageData = page.getPd();
			pageData.put("KPI_CODE", pageData.getString("KPI_CODE").split("-")[1]);
			list = (List<PageData>) dao.findForList("KhTotalMapper.datalistJqPage", page);

			break;
		}
		return list;
	}

	/**
	 * 排行榜列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPageRank(PageData pd) throws Exception {
		// List<PageData> list = (List<PageData>)
		// dao.findForList("KhTotalMapper.datalistJqPageRank", page);
		List<PageData> listRank = generateRankData(pd.getString("BUSI_DATE"));
		listRank.sort(new Comparator<PageData>() {
			public int compare(PageData pd1, PageData pd2) {
				Double finalScore1 = ConvertUtils.strToDouble(StringUtil.toString(pd1.get("FINAL_SCORE"), ""), 0);
				Double finalScore2 = ConvertUtils.strToDouble(StringUtil.toString(pd2.get("FINAL_SCORE"), ""), 0);
				return finalScore2.compareTo(finalScore1);
			}
		});
		int index = 0;
		for (PageData pageData : listRank) {
			index++;
			pageData.put("RANK_NUM", index);
		}
		return listRank;

	}

	/**
	 * 获取记录数量
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page) throws Exception {
		int count = 0;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			count = (int) dao.findForObject("NewAssetsBpdMapper.countJqGridExtend", page);
			break;
		case "Z1AM2":
			count = (int) dao.findForObject("AmsAotdMapper.countJqGridExtend", page);
			break;
		case "Z1FI1":
			count = (int) dao.findForObject("FmisCfttditMapper.countJqGridExtend", page);
			break;
		case "Z1MM1":
			count = (int) dao.findForObject("PiNitMapper.countJqGridExtend", page);
			break;
		case "Z1MM2":
			count = (int) dao.findForObject("PaToNitMapper.countJqGridExtend", page);
			break;
		case "Z1PS1":
			count = (int) dao.findForObject("ProjectScheduleNitMapper.countJqGridExtend", page);
			break;
		case "Z2AM1":
			count = (int) dao.findForObject("NewAssetsCardMapper.countJqGridExtend", page);
			break;
		case "Z2MM1":
			count = (int) dao.findForObject("UmdMapper.countJqGridExtend", page);
			break;
		case "Z2PM1":
			count = (int) dao.findForObject("EliMapper.countJqGridExtend", page);
			break;
		case "Z2PS1":
			count = (int) dao.findForObject("IncompleteProMapper.countJqGridExtend", page);
			break;
		case "Z3AM1":
			count = (int) dao.findForObject("AssetsCxMapper.countJqGridExtend", page);
			break;
		case "Z3FI2":
			count = (int) dao.findForObject("IntegrateVoucherCxMapper.countJqGridExtend", page);
			break;
		default:
			count = (int) dao.findForObject("KhTotalMapper.countJqGridExtend", page);
			break;
		}
		return count;
	}

	/**
	 * 获取排行榜记录数量
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtendRank(JqPage page) throws Exception {
		int count = 0;
		count = (int) dao.findForObject("KhTotalMapper.countJqGridExtendRank", page);
		return count;
	}

	/**
	 * 获取记录总合计
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page) throws Exception {
		PageData pageData;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			pageData = (PageData) dao.findForObject("NewAssetsBpdMapper.getFooterSummary", page);
			break;
		case "Z1AM2":
			pageData = (PageData) dao.findForObject("AmsAotdMapper.getFooterSummary", page);
			break;
		case "Z1FI1":
			pageData = (PageData) dao.findForObject("FmisCfttditMapper.getFooterSummary", page);
			break;
		case "Z1MM1":
			pageData = (PageData) dao.findForObject("PiNitMapper.getFooterSummary", page);
			break;
		case "Z1MM2":
			pageData = (PageData) dao.findForObject("PaToNitMapper.getFooterSummary", page);
			break;
		case "Z1PS1":
			pageData = (PageData) dao.findForObject("ProjectScheduleNitMapper.getFooterSummary", page);
			break;
		case "Z2AM1":
			pageData = (PageData) dao.findForObject("NewAssetsCardMapper.getFooterSummary", page);
			break;
		case "Z2MM1":
			pageData = (PageData) dao.findForObject("UmdMapper.getFooterSummary", page);
			break;
		case "Z2PM1":
			pageData = (PageData) dao.findForObject("EliMapper.getFooterSummary", page);
			break;
		case "Z2PS1":
			pageData = (PageData) dao.findForObject("IncompleteProMapper.getFooterSummary", page);
			break;
		case "Z3AM1":
			pageData = (PageData) dao.findForObject("AssetsCxMapper.getFooterSummary", page);
			break;
		case "Z3FI2":
			pageData = (PageData) dao.findForObject("IntegrateVoucherCxMapper.getFooterSummary", page);
			break;
		default:
			pageData = (PageData) dao.findForObject("KhTotalMapper.getFooterSummary", page);
			break;
		}
		return pageData;
	}

	/**
	 * 用于判断数据是否重复
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRepeat(List<PageData> listData) throws Exception {
		return (List<PageData>) dao.findForList("NewAssetsBpdMapper.getRepeat", listData);
	}

	/**
	 * 用于当前月份是否已经导入过数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData checkRepeat(String kpiCode, String busiDate) throws Exception {
		PageData pageData;
		switch (kpiCode) {
		case "Z1AM1":
			pageData = (PageData) dao.findForObject("NewAssetsBpdMapper.checkRepeat", busiDate);
			break;
		case "Z1AM2":
			pageData = (PageData) dao.findForObject("AmsAotdMapper.checkRepeat", busiDate);
			break;
		case "Z1FI1":
			pageData = (PageData) dao.findForObject("FmisCfttditMapper.checkRepeat", busiDate);
			break;
		case "Z1MM1":
			pageData = (PageData) dao.findForObject("PiNitMapper.checkRepeat", busiDate);
			break;
		case "Z1MM2":
			pageData = (PageData) dao.findForObject("PaToNitMapper.checkRepeat", busiDate);
			break;
		case "Z1PS1":
			pageData = (PageData) dao.findForObject("ProjectScheduleNitMapper.checkRepeat", busiDate);
			break;
		case "Z2AM1":
			pageData = (PageData) dao.findForObject("NewAssetsCardMapper.checkRepeat", busiDate);
			break;
		case "Z2MM1":
			pageData = (PageData) dao.findForObject("UmdMapper.checkRepeat", busiDate);
			break;
		case "Z2PM1":
			pageData = (PageData) dao.findForObject("EliMapper.checkRepeat", busiDate);
			break;
		case "Z2PS1":
			pageData = (PageData) dao.findForObject("IncompleteProMapper.checkRepeat", busiDate);
			break;
		case "Z3AM1":
			pageData = (PageData) dao.findForObject("AssetsCxMapper.checkRepeat", busiDate);
			break;
		case "Z3FI2":
			pageData = (PageData) dao.findForObject("IntegrateVoucherCxMapper.checkRepeat", busiDate);
			break;
		default:
			pageData = (PageData) dao.findForObject("KhTotalMapper.checkRepeat", busiDate);
			break;
		}
		return pageData;
	}

	/**
	 * 导出列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page) throws Exception {
		List<PageData> list;
		switch (page.getPd().getString("KPI_CODE")) {
		case "Z1AM1":
			list = (List<PageData>) dao.findForList("NewAssetsBpdMapper.exportList", page);
			break;
		case "Z1AM2":
			list = (List<PageData>) dao.findForList("AmsAotdMapper.exportList", page);
			break;
		case "Z1FI1":
			list = (List<PageData>) dao.findForList("FmisCfttditMapper.exportList", page);
			break;
		case "Z1MM1":
			list = (List<PageData>) dao.findForList("PiNitMapper.exportList", page);
			break;
		case "Z1MM2":
			list = (List<PageData>) dao.findForList("PaToNitMapper.exportList", page);
			break;
		case "Z1PS1":
			list = (List<PageData>) dao.findForList("ProjectScheduleNitMapper.exportList", page);
			break;
		case "Z2AM1":
			list = (List<PageData>) dao.findForList("NewAssetsCardMapper.exportList", page);
			break;
		case "Z2MM1":
			list = (List<PageData>) dao.findForList("UmdMapper.exportList", page);
			break;
		case "Z2PM1":
			list = (List<PageData>) dao.findForList("EliMapper.exportList", page);
			break;
		case "Z2PS1":
			list = (List<PageData>) dao.findForList("IncompleteProMapper.exportList", page);
			break;
		case "Z3AM1":
			list = (List<PageData>) dao.findForList("AssetsCxMapper.exportList", page);
			break;
		case "Z3FI2":
			list = (List<PageData>) dao.findForList("IntegrateVoucherCxMapper.exportList", page);
			break;
		default:
			list = (List<PageData>) dao.findForList("KhTotalMapper.exportList", page);
			break;
		}
		return list;
	}

	/**
	 * 导出模板
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd) throws Exception {
		List<PageData> list;
		switch (pd.getString("KPI_CODE")) {
		case "Z1AM1":
			list = (List<PageData>) dao.findForList("NewAssetsBpdMapper.exportModel", pd);
			break;
		case "Z1AM2":
			list = (List<PageData>) dao.findForList("AmsAotdMapper.exportModel", pd);
			break;
		case "Z1FI1":
			list = (List<PageData>) dao.findForList("FmisCfttditMapper.exportModel", pd);
			break;
		case "Z1MM1":
			list = (List<PageData>) dao.findForList("PiNitMapper.exportModel", pd);
			break;
		case "Z1MM2":
			list = (List<PageData>) dao.findForList("PaToNitMapper.exportModel", pd);
			break;
		case "Z1PS1":
			list = (List<PageData>) dao.findForList("ProjectScheduleNitMapper.exportModel", pd);
			break;
		case "Z2AM1":
			list = (List<PageData>) dao.findForList("NewAssetsCardMapper.exportModel", pd);
			break;
		case "Z2MM1":
			list = (List<PageData>) dao.findForList("UmdMapper.exportModel", pd);
			break;
		case "Z2PM1":
			list = (List<PageData>) dao.findForList("EliMapper.exportModel", pd);
			break;
		case "Z2PS1":
			list = (List<PageData>) dao.findForList("IncompleteProMapper.exportModel", pd);
			break;
		case "Z3AM1":
			list = (List<PageData>) dao.findForList("AssetsCxMapper.exportModel", pd);
			break;
		case "Z3FI2":
			list = (List<PageData>) dao.findForList("IntegrateVoucherCxMapper.exportModel", pd);
			break;
		default:
			list = (List<PageData>) dao.findForList("KhTotalMapper.exportModel", pd);
			break;
		}
		return list;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData, String kpiCode) throws Exception {
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
			dao.delete("KhTotalMapper.deleteAll", listData);
			break;
		}

	}

	/**
	 * 更新数据库
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData, String kpiCode) throws Exception {
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

			dao.update("KhTotalMapper.batchDelAndIns", listData);

			/*
			 * List<PageData>
			 * listRank=generateRankData(listData.get(0).getString("BUSI_DATE"),
			 * kpiCode.split("-")[1]);
			 * dao.update("KhTotalMapper.batchDelAndInsRank", listRank);
			 */
			break;
		}

	}

	/**
	 * 生成排行榜数据
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<PageData> generateRankData(String busiDate) throws Exception {
		PageData pdCondition = new PageData();
		pdCondition.put("BUSI_DATE", busiDate);
		// pdCondition.put("KPI_CODE", kpiCode);
		// **************获取各单位扣分数，即各单位产生的凭证条数***************//
		List<PageData> listVoucherNumOfCompany = (List<PageData>) dao.findForList("KhTotalMapper.getCountOfVoucherNum",
				pdCondition);
		// ***********************************************************//

		// **************获取各单位考核项总数,即集成凭证总数******************//
		List<PageData> listTotalNumOfCompany = (List<PageData>) dao.findForList("KhTotalMapper.getKhTotal",
				pdCondition);
		// ***********************************************************//

		// **************获取指标分值,指标权重*****************************//
		List<PageData> listKpi = (List<PageData>) dao.findForList("KhTotalMapper.listKpi", pdCondition);
		// ***********************************************************//

		// **************获取合并单位***********************************//
		List<PageData> listAssessDepart = (List<PageData>) dao.findForList("AssessDepartMapper.listAll", pdCondition);
		// ***********************************************************//

		// ***************以合并单位为标准，生成总条数*********************//
		List<PageData> listRank = new ArrayList<PageData>();
		Map<String, List<PageData>> mapMergeDepart = new HashMap<String, List<PageData>>();
		List<String> listMergeDepartParent = new ArrayList<String>();
		for (PageData pdAssessDepart : listAssessDepart) {
			if (pdAssessDepart.getString("IS_MERGE_DEPART").equals("0")) {
				PageData pdRank = new PageData();
				pdRank.put("COMPANY_CODE", pdAssessDepart.getString("DEPART_CODE"));
				pdRank.put("COMPANY_NAME", pdAssessDepart.getString("DEPART_NAME"));
				pdRank.put("BUSI_DATE", busiDate);
				pdRank.put("Z1FI1_DEDUCK_SCORE", 0);
				pdRank.put("Z3FI2_DEDUCK_SCORE", 0);
				pdRank.put("Z1FI1_TOTAL_NUM", 0);
				pdRank.put("Z3FI2_TOTAL_NUM", 0);
				for (PageData pdVoucherNumOfCompany : listVoucherNumOfCompany) {
					if (pdVoucherNumOfCompany.getString("COMPANY_CODE")
							.equals(pdAssessDepart.getString("DEPART_CODE"))) {
						if (pdVoucherNumOfCompany.getString("KPI_CODE").equals("Z1FI1")) {
							pdRank.put("Z1FI1_DEDUCK_SCORE", pdVoucherNumOfCompany.get("DEDUCK_SCORE"));
						} else if (pdVoucherNumOfCompany.getString("KPI_CODE").equals("Z3FI2")) {
							pdRank.put("Z3FI2_DEDUCK_SCORE", pdVoucherNumOfCompany.get("DEDUCK_SCORE"));
						}
						// break;
					}

				}
				for (PageData pdTotalNumOfCompany : listTotalNumOfCompany) {
					if (pdTotalNumOfCompany.getString("COMPANY_CODE").equals(pdAssessDepart.getString("DEPART_CODE"))) {
						if (pdTotalNumOfCompany.getString("KPI_CODE").equals("Z1FI1")) {
							pdRank.put("Z1FI1_TOTAL_NUM", pdTotalNumOfCompany.get("KH_TOTAL_NUM"));
						} else if (pdTotalNumOfCompany.getString("KPI_CODE").equals("Z3FI2")) {
							pdRank.put("Z3FI2_TOTAL_NUM", pdTotalNumOfCompany.get("KH_TOTAL_NUM"));
						}
						// break;
					}
				}
				// ****************得分=分值-扣分/总项*分值**************************//
				// 得分=分值-扣分/总项*分值
				// 分值
				// Z1FI1得分=分值-扣分/总项*分值
				BigDecimal scoreZ1FI1 = getScore(listKpi, "Z1FI1",
						ConvertUtils.strToDouble(StringUtil.toString(pdRank.get("Z1FI1_DEDUCK_SCORE"), ""), 0),
						ConvertUtils.strToDouble(StringUtil.toString(pdRank.get("Z1FI1_TOTAL_NUM"), ""), 0));
				pdRank.put("Z1FI1_SCORE", scoreZ1FI1);
				// Z3FI2得分=分值-扣分/总项*分值
				BigDecimal scoreZ3FI2 = getScore(listKpi, "Z3FI2",
						ConvertUtils.strToDouble(StringUtil.toString(pdRank.get("Z3FI2_DEDUCK_SCORE"), ""), 0),
						ConvertUtils.strToDouble(StringUtil.toString(pdRank.get("Z3FI2_TOTAL_NUM"), ""), 0));
				pdRank.put("Z3FI2_SCORE", scoreZ3FI2);
				// **********************************************************//

				// ***************以指标配置为标准，计算总得分*********************//
				BigDecimal finalScore = getFinalScore(listKpi, scoreZ1FI1, scoreZ3FI2);
				pdRank.put("FINAL_SCORE", finalScore);
				// *********************************************************//
				listRank.add(pdRank);
			} else {
				String parentDepart = pdAssessDepart.getString("DEPART_PARENT_CODE");
				if (!listMergeDepartParent.contains(parentDepart)) {// 不存在
					listMergeDepartParent.add(parentDepart);
					List<PageData> listMergeDepart = new ArrayList<PageData>();
					listMergeDepart.add(pdAssessDepart);
					mapMergeDepart.put(parentDepart, listMergeDepart);
				} else {// 存在
					List<PageData> listMergeDepart = mapMergeDepart.get(parentDepart);
					listMergeDepart.add(pdAssessDepart);
				}

			}
		}
		for (Map.Entry<String, List<PageData>> entry : mapMergeDepart.entrySet()) {
			double deduckScoreZ1FI1Sum = 0;
			double deduckScoreZ3FI2Sum = 0;

			double totalNumZ1FI1Sum = 0;
			double totalNumZ3FI2Sum = 0;

			BigDecimal scoreZ1FI1 = new BigDecimal(0);
			BigDecimal scoreZ3FI2 = new BigDecimal(0);

			BigDecimal finalScore = new BigDecimal(0);
			for (PageData item : entry.getValue()) {
				double deduckScoreZ1FI1 = 0;
				double deduckScoreZ3FI2 = 0;
				double totalNumZ1FI1 = 0;
				double totalNumZ3FI2 = 0;
				for (PageData pdVoucherNumOfCompany : listVoucherNumOfCompany) {
					if (pdVoucherNumOfCompany.getString("COMPANY_CODE").equals(item.getString("DEPART_CODE"))) {
						if (pdVoucherNumOfCompany.getString("KPI_CODE").equals("Z1FI1")) {
							deduckScoreZ1FI1 = ConvertUtils.strToDouble(StringUtil.toString(pdVoucherNumOfCompany.get("DEDUCK_SCORE"), ""),
									0);
						} else if (pdVoucherNumOfCompany.getString("KPI_CODE").equals("Z3FI2")) {
							deduckScoreZ3FI2 = ConvertUtils.strToDouble(StringUtil.toString(pdVoucherNumOfCompany.get("DEDUCK_SCORE"),""),
									0);
						}
					}
					// break;
				}
				for (PageData pdTotalNumOfCompany : listTotalNumOfCompany) {
					if (pdTotalNumOfCompany.getString("COMPANY_CODE").equals(item.getString("DEPART_CODE"))) {
						if (pdTotalNumOfCompany.getString("KPI_CODE").equals("Z1FI1")) {
							totalNumZ1FI1 = ConvertUtils.strToDouble(StringUtil.toString(pdTotalNumOfCompany.get("KH_TOTAL_NUM"),""), 0);
						} else if (pdTotalNumOfCompany.getString("KPI_CODE").equals("Z3FI2")) {
							totalNumZ3FI2 = ConvertUtils.strToDouble(StringUtil.toString(pdTotalNumOfCompany.get("KH_TOTAL_NUM"),""), 0);
						}
					}
					// break;
				}
				// Z1FI1扣分
				deduckScoreZ1FI1Sum += deduckScoreZ1FI1;
				// Z3FI2扣分
				deduckScoreZ3FI2Sum += deduckScoreZ3FI2;

				// Z1FI1总项
				totalNumZ1FI1Sum += totalNumZ1FI1;
				// Z3FI2总项
				totalNumZ3FI2Sum += totalNumZ3FI2;

				// ****************得分=分值-扣分/总项*分值**************************//
				// 得分=分值-扣分/总项*分值
				// 分值

				// Z1FI1得分=分值-扣分/总项*分值
				scoreZ1FI1 = getScore(listKpi, "Z1FI1", deduckScoreZ1FI1Sum, totalNumZ1FI1Sum);

				// Z3FI2得分=分值-扣分/总项*分值
				scoreZ3FI2 = getScore(listKpi, "Z3FI2", deduckScoreZ3FI2Sum, totalNumZ3FI2Sum);
				// **********************************************************//

				finalScore = getFinalScore(listKpi, scoreZ1FI1, scoreZ3FI2);
				// pdRank.put("FINAL_SCORE", finalScore);
			}

			for (PageData pdDepartMerge : listRank) {
				if (entry.getKey().equals(pdDepartMerge.get("COMPANY_CODE"))) {
					pdDepartMerge.put("Z1FI1_DEDUCK_SCORE", deduckScoreZ1FI1Sum);
					pdDepartMerge.put("Z1FI1_TOTAL_NUM", totalNumZ1FI1Sum);
					pdDepartMerge.put("Z1FI1_SCORE", scoreZ1FI1);
					pdDepartMerge.put("Z3FI2_DEDUCK_SCORE", deduckScoreZ3FI2Sum);
					pdDepartMerge.put("Z3FI2_TOTAL_NUM", totalNumZ3FI2Sum);
					pdDepartMerge.put("Z3FI2_SCORE", scoreZ3FI2);
					pdDepartMerge.put("FINAL_SCORE", finalScore);
				}
			}
		}

		// *********************************************************//
		return listRank;
	}

	private BigDecimal getScore(List<PageData> listKpi, String kpiCode, double doubleDeduckScore,
			double doubleTotalNum) {
		// 得分=分值-扣分/总项*分值
		// 分值
		BigDecimal kpiScore = new BigDecimal(0);
		for (PageData pdKpi : listKpi) {
			if (pdKpi.getString("KPI_CODE").equals(kpiCode)) {
				kpiScore = new BigDecimal(StringUtil.toString(pdKpi.get("TOTAL_SCORE"), ""));
			}
		}
		// Z1FI1得分=分值-扣分/总项*分值
		BigDecimal deduckScore = new BigDecimal(doubleDeduckScore);
		BigDecimal totalNum = new BigDecimal(doubleTotalNum);
		// Z1FI1得分
		BigDecimal score = new BigDecimal(0);
		if (totalNum.doubleValue() != 0) {
			score = kpiScore.subtract(deduckScore.divide(totalNum, 6, BigDecimal.ROUND_HALF_UP).multiply(kpiScore));
		} else {
			score = kpiScore;
		}
		return score;
	}

	private BigDecimal getFinalScore(List<PageData> listKpi, BigDecimal scoreZ1FI1, BigDecimal scoreZ3FI2) {
		// ***************以指标配置为标准，计算总得分*********************//
		BigDecimal finalScoreZ1FI1 = new BigDecimal(0);
		BigDecimal finalScoreZ3FI2 = new BigDecimal(0);
		BigDecimal finalScore = new BigDecimal(0);
		for (PageData pdKpi : listKpi) {
			if (pdKpi.getString("KPI_CODE").equals("Z1FI1")) {
				finalScoreZ1FI1 = scoreZ1FI1.multiply(
						new BigDecimal(StringUtil.toString(pdKpi.get("PROPORTION"), "")).divide(new BigDecimal(100)));
			} else if (pdKpi.getString("KPI_CODE").equals("Z3FI2")) {
				finalScoreZ3FI2 = scoreZ3FI2.multiply(
						new BigDecimal(StringUtil.toString(pdKpi.get("PROPORTION"), "")).divide(new BigDecimal(100)));
			}
		}
		finalScore = finalScoreZ1FI1.add(finalScoreZ3FI2);
		// pdRank.put("FINAL_SCORE", finalScore);

		return finalScore;
		// *********************************************************//
	}

	/**
	 * 更新数据库
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void batchCoverAdd(List<PageData> listData, String kpiCode) throws Exception {
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

			dao.update("KhTotalMapper.batchCoverAdd", listData);

			/*
			 * List<PageData>
			 * listRank=generateRankData(listData.get(0).getString("BUSI_DATE"),
			 * kpiCode.split("-")[1]);
			 * dao.update("KhTotalMapper.batchDelAndInsRank", listRank);
			 */
			break;
		}

	}
}
