package com.fh.service.voucher.voucher;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/**
 * 凭证数据传输接口
* @ClassName: VoucherManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月23日
*
 */
public interface VoucherManager{

	/**获取单号下拉列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	public List<String> getBillCodeList(PageData pd)throws Exception;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(JqPage page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(同步删除)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listSyncDelList(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**获取明细表信息
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listDetail(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findSummyDetailList(PageData page)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findSummyDetailListByBillCodes(PageData page)throws Exception;
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGrid(PageData pd)throws Exception;
	
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummary(JqPage page)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**获取汇总信息还没有进行上报的信息
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> getTransferValidate(PageData pd)throws Exception;
	
	/**批量更新凭证号
	 * @param pd
	 * @throws Exception
	 */
	public void updateCertCode(List<PageData> pd)throws Exception;
	
	/**批量更新冲销凭证号
	 * @param pd
	 * @throws Exception
	 */
	public void updateRevCertCode(List<PageData> pd)throws Exception;
	
	
	/*******************特殊凭证相关服务*******************************/
	/**根据帐套获取凭证类型列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getTypeCodeList(PageData pd)throws Exception;
	
	/**根据帐套和凭证类型获取单位列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getDepartCodeList(PageData pd)throws Exception;
	
	/**根据帐套和凭证类型、期间、单位、状态获取单号列表数据源 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getSpecialBillCodeList(PageData pd)throws Exception;
	
	/**列表(全部)特殊表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllSpecial(PageData pd)throws Exception;
	
	/**获取记录总合计
	 * @param pd
	 * @throws Exception
	 */
	public PageData getFooterSummarySpecial(JqPage page)throws Exception;
	
	/**列表(同步删除)特殊表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listSyncDelListSpecial(PageData pd)throws Exception;
	
	/**获取汇总表信息 特殊表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findSummyDetailListSpecial(PageData page)throws Exception;
	
	/**获取明细表信息 特殊表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listDetailSpecial(PageData pd)throws Exception;
	
	/**获取汇总表信息 特殊表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findSummyDetailListByBillCodesSpecial(PageData page)throws Exception;
	
	/**根据凭证类型、账套获取tb_sys_bill_off_mapping帐套映射
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> findBilloffMapping(PageData page)throws Exception;
	/*************************************************************/
}