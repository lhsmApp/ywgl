package com.fh.service.instframe.instframe.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.instframe.Instframe;
import com.fh.util.PageData;
import com.fh.service.instframe.instframe.InstframeManager;

/**
 * 组织机构
* @ClassName: InstframeService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
@Service("instframeService")
public class InstframeService implements InstframeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InstframeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InstframeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InstframeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InstframeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InstframeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InstframeMapper.findById", pd);
	}
	public List<PageData> findByInstCode(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InstframeMapper.findByInstCode", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Instframe> listByParentId(String parentId) throws Exception {
		return (List<Instframe>) dao.findForList("InstframeMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Instframe> listTree(String parentId) throws Exception {
		List<Instframe> valueList = this.listByParentId(parentId);
		for(Instframe fhentity : valueList){
			fhentity.setTreeurl("instframe/list.do?INSTFRAME_ID="+fhentity.getINSTFRAME_ID());
			fhentity.setSubInstframe(this.listTree(fhentity.getINSTFRAME_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
		
}

