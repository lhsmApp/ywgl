package com.fh.service.fundsconfirminfoquery.fundsconfirminfoquery;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
public interface FundsConfirmInfoQueryManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	
}

