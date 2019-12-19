package com.fh.service.mbp.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ProblemType;
import com.fh.entity.system.Department;
import com.fh.util.PageData;
import com.fh.service.mbp.ProblemTypeManager;

/** 
 * 说明： 知识类别
 * 创建人：jiachao
 * 创建时间：2019-10-10
 * @version
 */
@Service("problemtypeService")
public class ProblemTypeService implements ProblemTypeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ProblemTypeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ProblemTypeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ProblemTypeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProblemTypeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProblemTypeMapper.listAll", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProblemTypeMapper.findById", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProblemType> listByParentId(String parentId) throws Exception {
		return (List<ProblemType>) dao.findForList("ProblemTypeMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<ProblemType> listTree(String parentId) throws Exception {
		List<ProblemType> valueList = this.listByParentId(parentId);
		for(ProblemType fhentity : valueList){
			fhentity.setTreeurl("problemtype/list.do?PROBLEMTYPE_ID="+fhentity.getPROBLEMTYPE_ID());
			fhentity.setSubProblemType(this.listTree(fhentity.getPROBLEMTYPE_ID()));
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
	public List<PageData> listAllProblemTypeToSelect(String parentId,List<PageData> zproblemTypePdList) throws Exception {
		List<PageData>[] arrayProblemType = this.listAllbyPd(parentId,zproblemTypePdList);
		List<PageData> problemTypePdList = arrayProblemType[1];
		for(PageData pd : problemTypePdList){
			this.listAllProblemTypeToSelect(pd.getString("id"),arrayProblemType[0]);
		}
		return arrayProblemType[0];
	}
		
	/**下拉ztree用
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllbyPd(String parentId,List<PageData> zproblemTypePdList) throws Exception {
		List<ProblemType> problemTypeList = this.listSubProblemTypeByParentId(parentId);
		List<PageData> problemTypePdList = new ArrayList<PageData>();
		for(ProblemType problemType : problemTypeList){
			PageData pd = new PageData();
			pd.put("id", problemType.getPROBLEMTYPE_ID());
			pd.put("parentId", problemType.getPARENT_ID());
			pd.put("name", problemType.getNAME());
			pd.put("icon", "static/images/user.gif");
			problemTypePdList.add(pd);
			zproblemTypePdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = zproblemTypePdList;
		arrayDep[1] = problemTypePdList;
		return arrayDep;
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProblemType> listSubProblemTypeByParentId(String parentId) throws Exception {
		return (List<ProblemType>) dao.findForList("ProblemTypeMapper.listSubProblemTypeByParentId", parentId);
	}
}

