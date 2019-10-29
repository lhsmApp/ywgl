package com.fh.service.trainBase.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.CourseType;
import com.fh.entity.CourseType;
import com.fh.util.PageData;
import com.fh.service.trainBase.CourseTypeManager;

/** 
 * 说明： 知识结构分类
 * 创建人：jiachao
 * 创建时间：2019-10-25
 * @version
 */
@Service("coursetypeService")
public class CourseTypeService implements CourseTypeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CourseTypeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CourseTypeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CourseTypeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CourseTypeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CourseTypeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CourseTypeMapper.findById", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CourseType> listByParentId(String parentId) throws Exception {
		return (List<CourseType>) dao.findForList("CourseTypeMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<CourseType> listTree(String parentId) throws Exception {
		List<CourseType> valueList = this.listByParentId(parentId);
		for(CourseType fhentity : valueList){
			fhentity.setTreeurl("coursetype/list.do?COURSETYPE_ID="+fhentity.getCOURSETYPE_ID());
			fhentity.setSubCourseType(this.listTree(fhentity.getCOURSETYPE_ID()));
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
	public List<PageData> listAllCourseTypeToSelect(String parentId,List<PageData> zcourseTypePdList) throws Exception {
		List<PageData>[] arrayCourseType = this.listAllbyPd(parentId,zcourseTypePdList);
		List<PageData> courseTypePdList = arrayCourseType[1];
		for(PageData pd : courseTypePdList){
			this.listAllCourseTypeToSelect(pd.getString("id"),arrayCourseType[0]);
		}
		return arrayCourseType[0];
	}
	
	/**下拉ztree用
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllbyPd(String parentId,List<PageData> zcourseTypePdList) throws Exception {
		List<CourseType> courseTypeList = this.listSubCourseTypeByParentId(parentId);
		List<PageData> courseTypePdList = new ArrayList<PageData>();
		for(CourseType courseType : courseTypeList){
			PageData pd = new PageData();
			pd.put("id", courseType.getCOURSETYPE_ID());
			pd.put("parentId", courseType.getPARENT_ID());
			pd.put("name", courseType.getNAME());
			pd.put("icon", "static/images/user.gif");
			courseTypePdList.add(pd);
			zcourseTypePdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = zcourseTypePdList;
		arrayDep[1] = courseTypePdList;
		return arrayDep;
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CourseType> listSubCourseTypeByParentId(String parentId) throws Exception {
		return (List<CourseType>) dao.findForList("CourseTypeMapper.listSubCourseTypeByParentId", parentId);
	}
}

