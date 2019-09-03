package com.fh.service.salaryLaborCostFactSheet.salaryLaborCostFactSheet;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： 
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-19
 * @version
 */
public interface SalaryLaborCostFactSheetManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> getRptDetailList(JqPage page)throws Exception;
	public List<PageData> getRptTotalList(JqPage page)throws Exception;
}

