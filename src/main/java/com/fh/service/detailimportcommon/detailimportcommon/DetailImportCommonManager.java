package com.fh.service.detailimportcommon.detailimportcommon;

import java.util.List;
import java.util.Map;

import com.fh.util.PageData;

/** 
 * 说明： 明细导入共有的
 * 创建人：zhangxiaoliu
 * 创建时间：2018-09-05
 * @version
 */
public interface DetailImportCommonManager{
	

	/**获取汇总里的明细
	 * @param
	 * @throws Exception
	 */
	public List<PageData> getDetailList(PageData pd)throws Exception;
	

	/**获取汇总数据
	 * @param
	 * @throws Exception
	 */
	public List<PageData> getSum(Map<String, String> map)throws Exception;
	
}

