package com.fh.service.salaryLaborCostFactSheet.salaryLaborCostFactSheet.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.salaryLaborCostFactSheet.salaryLaborCostFactSheet.SalaryLaborCostFactSheetManager;

/** 
 * 说明：  
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("salaryLaborCostFactSheetService")
public class SalaryLaborCostFactSheetService implements SalaryLaborCostFactSheetManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRptDetailList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SalaryLaborCostFactSheetMapper.getRptDetailList", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> getRptTotalList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SalaryLaborCostFactSheetMapper.getRptTotalList", page);
	}
}

