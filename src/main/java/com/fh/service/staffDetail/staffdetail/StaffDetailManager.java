package com.fh.service.staffDetail.staffdetail;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.StaffFilterInfo;
import com.fh.util.PageData;

/** 
 * 说明： 工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-19
 * @version
 */
public interface StaffDetailManager{
	
	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	public List<String> getBillCodeList(PageData pd)throws Exception;
	
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
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception;
	
	/**通过流水号获取流水号，用于判断数据是否变更 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getSerialNoBySerialNo(PageData pd)throws Exception;

	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportList(JqPage page)throws Exception;
	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> exportModel(PageData pd)throws Exception;

	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception;
	
	/**获取计算数据
	 * @param pd
	 * @throws Exception
	 */
	/*public List<PageData> getDataCalculation(String tableName, String TableFeildTax, String TmplUtil_KeyExtra, 
			PageData pdInsetBackup,
			List<String> listSalaryFeildUpdate, String sqlRetSelect, List<PageData> listAddSalary, List<PageData> listAddBonus,
			String sqlSumByUserCodeSalary,  String sqlSumByUserCodeBonus, String TableFeildSum,
			String ExemptionTax)throws Exception;*/
	public List<PageData> getDataCalculation(String tableName, String TmplUtil_KeyExtra, 
			String TableFeildSalarySelf, String TableFeildSalaryTax, String TableFeildBonusSelf, String TableFeildBonusTax,
			String TableFeildSalaryTaxConfigGradeOper, String TableFeildBonusTaxConfigGradeOper,
			String TableFeildSalaryTaxConfigSumOper, String TableFeildBonusTaxConfigSumOper,
			String TableFeildSalaryTaxSelfSumOper, String TableFeildBonusTaxSelfSumOper,
			PageData pdInsetBackup,
			List<String> listSalaryFeildUpdate, String sqlRetSelect, List<PageData> listData, 
			String sqlSumByUserCodeSalary, 
			String sqlSumByUserCodeBonus,
			Boolean bolCalculation, List<StaffFilterInfo> listStaffFilterInfo,
			String QueryFeild_PreNotMonth_Month, String ExemptionTaxSalary,String strSystemDateTimeMouth)throws Exception;
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception;

}

