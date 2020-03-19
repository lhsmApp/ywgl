package com.fh.service.assess.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ProblemType;
import com.fh.entity.AssessDepart;
import com.fh.util.PageData;
import com.fh.service.assess.AssessDepartManager;

/** 
 * 说明： 考核单位管理接口
 * 创建人：jiachao
 * 创建时间：2020-03-10
 * @version
 */
@Service("assessdepartService")
public class AssessDepartService implements AssessDepartManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AssessDepartMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AssessDepartMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AssessDepartMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AssessDepartMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AssessDepartMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AssessDepartMapper.findById", pd);
	}
	
	/**通过Code获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AssessDepartMapper.findByCode", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessDepart> listByParentId(String parentId) throws Exception {
		return (List<AssessDepart>) dao.findForList("AssessDepartMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<AssessDepart> listTree(String parentId) throws Exception {
		List<AssessDepart> valueList = this.listByParentId(parentId);
		for(AssessDepart fhentity : valueList){
			fhentity.setTreeurl("assessdepart/list.do?ASSESSDEPART_ID="+fhentity.getASSESSDEPART_ID());
			fhentity.setSubAssessDepart(this.listTree(fhentity.getASSESSDEPART_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
		
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllAssessDepartToSelect(String parentId,List<PageData> zassessDepartPdList) throws Exception {
		List<PageData>[] arrayAssessDepart = this.listAllbyPd(parentId,zassessDepartPdList);
		List<PageData> assessDepartPdList = arrayAssessDepart[1];
		for(PageData pd : assessDepartPdList){
			this.listAllAssessDepartToSelect(pd.getString("id"),arrayAssessDepart[0]);
		}
		return arrayAssessDepart[0];
	}
		
	/**下拉ztree用
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllbyPd(String parentId,List<PageData> zassessDepartPdList) throws Exception {
		List<AssessDepart> assessDepartList = this.listSubAssessDepartByParentId(parentId);
		List<PageData> assessDepartPdList = new ArrayList<PageData>();
		for(AssessDepart assessDepart : assessDepartList){
			PageData pd = new PageData();
			pd.put("id", assessDepart.getASSESSDEPART_ID());
			pd.put("parentId", assessDepart.getPARENT_ID());
			pd.put("name", assessDepart.getNAME());
			pd.put("icon", "static/images/user.gif");
			assessDepartPdList.add(pd);
			zassessDepartPdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = zassessDepartPdList;
		arrayDep[1] = assessDepartPdList;
		return arrayDep;
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessDepart> listSubAssessDepartByParentId(String parentId) throws Exception {
		return (List<AssessDepart>) dao.findForList("AssessDepartMapper.listSubAssessDepartByParentId", parentId);
	}
}

