package com.fh.service.policy.policy.impl;

import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.policy.policy.PolicyManager;

/**
 * 政策法规
* @ClassName: PolicyService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaojianping
* @date 2017年6月30日
*
 */
@Service("policyService")
public class PolicyService implements PolicyManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PolicyMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PolicyMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PolicyMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PolicyMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PolicyMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PolicyMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PolicyMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 列表（政策分类）
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> poliyTypeList(Page page) throws Exception {
		return (List<PageData>)dao.findForList("PolicyMapper.policyTypeList", page);
		
	}

	/**
	 * 根据政策分类获取政策标题
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> policyTitlelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("PolicyMapper.policyTitlelistPage", page);
	}
	
}

