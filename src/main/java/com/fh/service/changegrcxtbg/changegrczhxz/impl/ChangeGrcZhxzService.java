package com.fh.service.changegrcxtbg.changegrczhxz.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.changegrcxtbg.changegrczhxz.ChangeGrcZhxzManager;

/** 
 * 说明： changeGrcZhxz
 * 创建人：jiachao
 * 创建时间：2019-09-29
 * @version
 */
@Service("changegrczhxzService")
public class ChangeGrcZhxzService implements ChangeGrcZhxzManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ChangeGrcZhxzMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ChangeGrcZhxzMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ChangeGrcZhxzMapper.edit", pd);
	}
	/**更新修改内容
	 * @param pd
	 * @throws Exception
	 */
	public void updateEdit(PageData pd)throws Exception{
		dao.update("ChangeGrcZhxzMapper.updateEdit", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ChangeGrcZhxzMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChangeGrcZhxzMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeGrcZhxzMapper.findById", pd);
	}
	public PageData findByBillCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeGrcZhxzMapper.findByBillCode", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ChangeGrcZhxzMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

