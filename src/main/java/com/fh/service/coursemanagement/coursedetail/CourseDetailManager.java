package com.fh.service.coursemanagement.coursedetail;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.coursebase.CourseDetail;
import com.fh.util.PageData;

/** 
 * 说明： 课程详细信息处理类接口
 * 创建人：xinyuLo
 * 创建时间：2019-10-28
 * @version
 */
public interface CourseDetailManager{

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
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
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
	 * 课程内容新增回显
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<CourseDetail> listAdd(PageData pd)throws Exception;
	
	/**
	 * 新增章节
	 * @param pd
	 * @throws Exception
	 */
	public void saveChapter(PageData pd)throws Exception;
	
	/**
	 * 新增小节
	 * @param pd
	 * @throws Exception
	 */
	public void saveSection(PageData pd)throws Exception;
	
	/**通过父id获取小节数 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer countByParentId(Integer parentId)throws Exception;
	
	/**
	 * 查询小节信息
	 * @param pd
	 * @return
	 */
	public PageData findSection(PageData pd)throws Exception;
	
}

