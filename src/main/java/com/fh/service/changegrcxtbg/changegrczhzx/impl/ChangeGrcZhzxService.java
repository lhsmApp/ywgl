package com.fh.service.changegrcxtbg.changegrczhzx.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.changegrcxtbg.changegrczhzx.ChangeGrcZhzxManager;

/** 
 * 说明： changegrczhzx
 * 创建人：jiachao
 * 创建时间：2019-10-24
 * @version
 */
@Service("changegrczhzxService")
public class ChangeGrcZhzxService implements ChangeGrcZhzxManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ChangeGrcZhzxMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ChangeGrcZhzxMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ChangeGrcZhzxMapper.edit", pd);
	}
	
	/**变更内容更新
	 * @param pd
	 * @throws Exception
	 */
	public void updateEdit(PageData pd)throws Exception{
		dao.update("ChangeGrcZhzxMapper.updateEdit", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ChangeGrcZhzxMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChangeGrcZhzxMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeGrcZhzxMapper.findById", pd);
	}
	public PageData findByBillCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChangeGrcZhzxMapper.findByBillCode", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ChangeGrcZhzxMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

