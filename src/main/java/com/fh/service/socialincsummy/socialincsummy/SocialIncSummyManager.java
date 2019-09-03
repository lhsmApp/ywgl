package com.fh.service.socialincsummy.socialincsummy;

import java.util.List;
import java.util.Map;

import com.fh.entity.JqPage;
import com.fh.entity.SysSealed;
import com.fh.util.PageData;

/** 
 * 说明： 社保汇总接口
 * 创建人：zhangxiaoliu
 * 创建时间：2017-07-07
 * @version
 */
public interface SocialIncSummyManager{

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
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findSummyDetailList(PageData page)throws Exception;

	/**作废
	 * @param pd
	 * @throws Exception
	 */
	public void cancelAll(List<PageData> list)throws Exception;
	
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public void saveSummyModelList(Map<String, Object> map)throws Exception;
	
}

