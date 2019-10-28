package com.fh.service.coursemanagement.coursebase;

import java.util.List;
import com.fh.entity.Page;
import com.fh.entity.coursebase.CourseTree;
import com.fh.util.PageData;

/** 
 * 说明： 课程列表处理类接口
 * 创建人：jiachao
 * 创建时间：2019-10-14
 * @version
 */
public interface CourseBaseManager{

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
	public List<PageData> list(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 显示树列表
	 * @throws Exception
	 */
	public List<CourseTree> listTree(String parentId)throws Exception;
	
	/**
	 * 通过id获取子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<CourseTree> listByParentId(String parentId) throws Exception;
	
	/**
	 * 通过id获取分类数据集合
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listById(PageData pd) throws Exception;
	
}

