package com.fh.service.sysSealedInfo.syssealedinfo.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.SysSealed;
import com.fh.util.PageData;
import com.fh.util.enums.BillType;
import com.fh.util.enums.TmplType;
import com.fh.service.sysSealedInfo.syssealedinfo.SysSealedInfoManager;

/** 
 * 说明： 业务封存信息
 * 创建人：FH Q313596790
 * 创建时间：2017-06-16
 * @version
 */
@Service("syssealedinfoService")
public class SysSealedInfoService implements SysSealedInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysSealedInfoMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysSealedInfoMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysSealedInfoMapper.edit", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editTransfer(PageData pd)throws Exception{
		dao.update("SysSealedInfoMapper.editTransfer", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(JqPage page)throws Exception{
		List<PageData> listPageData = (List<PageData>)dao.findForList("SysSealedInfoMapper.datalistJqPage", page);
		for (PageData pageData : listPageData) {
			pageData.put("BILL_TYPE_TR", TmplType.getValueByKey(pageData.getString("BILL_TYPE")));
		}
		return listPageData;
	}
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int count(PageData pd)throws Exception{
		return (int)dao.findForObject("SysSealedInfoMapper.count", pd);
	}
	
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysSealedInfoMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysSealedInfoMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysSealedInfoMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**获取状态 
	 * @param 
	 * @throws Exception
	 */
	public String getState(PageData pd) throws Exception {
		return (String)dao.findForObject("SysSealedInfoMapper.getState", pd);
	}
	public String getStateFromModel(SysSealed item) throws Exception {
		return (String)dao.findForObject("SysSealedInfoMapper.getStateFromModel", item);
	}
	
	/**上报
	 * @param
	 * @throws Exception
	 */
	public void saveReport(List<SysSealed> list)throws Exception{
		dao.saveReport("SysSealedInfoMapper.reportDelete", "SysSealedInfoMapper.reportInsert", list);
	}
	
	/**批量修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateAll(List<PageData> pd)throws Exception{
		dao.update("SysSealedInfoMapper.updateAll", pd);
	}
	
	
	/**批量生成
	 * @param pd
	 * @throws Exception
	 */
	public void insertBatch(List<SysSealed> list)throws Exception{
		dao.update("SysSealedInfoMapper.insertBatch", list);
	}
	
	/**获取封存状态，用于验证解封某业务时是否可以进行解封
	 * @param pd
	 * @throws Exception
	 */
	public String valiState(PageData pd)throws Exception{
		return (String)dao.findForObject("SysSealedInfoMapper.valiState", pd);
	}
	
	
	/**获取封存状态，用于页面状态
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getDeptOffList(String strWhereSql)throws Exception{
		return (List<PageData>)dao.findForList("SysSealedInfoMapper.getDeptOffList", strWhereSql);
	}
}

