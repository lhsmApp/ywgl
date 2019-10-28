package com.fh.service.coursemanagement.coursebase.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.coursebase.CourseTree;
import com.fh.service.coursemanagement.coursebase.CourseBaseManager;
import com.fh.util.PageData;

/** 
 * 说明： 课程列表处理类
 * 创建人：xinyuLo
 * 创建时间：2019-10-14
 * @version
 */
@Service("coursebaseService")
public class CourseBaseService implements CourseBaseManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CourseBaseMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CourseBaseMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CourseBaseMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CourseBaseMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CourseBaseMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CourseBaseMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CourseTree> listByParentId(String parentId) throws Exception {
		return (List<CourseTree>) dao.findForList("CourseBaseMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param COURSE_TYPE_ID
	 * @return
	 * @throws Exception
	 */
	public List<CourseTree> listTree(String parentId) throws Exception {
		List<CourseTree> valueList = this.listByParentId(parentId);
		for(CourseTree fhentity : valueList){
			fhentity.setSubTreeList(this.listTree(fhentity.getCOURSE_TYPE_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}

	/**
	 * 通过id获取分类数据集合
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listById(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("CourseBaseMapper.listById", pd);
	}
}

