package com.fh.service.coursemanagement.coursedetail.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.coursebase.CourseDetail;
import com.fh.service.coursemanagement.coursedetail.CourseDetailManager;
import com.fh.util.PageData;

/** 
 * 说明： 课程详细信息处理类
 * 创建人：xinyuLo
 * 创建时间：2019-10-28
 * @version
 */
@Service("coursedetailService")
public class CourseDetailService implements CourseDetailManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CourseDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CourseDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CourseDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CourseDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CourseDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CourseDetailMapper.deleteAll", ArrayDATA_IDS);
	}
	
	public List<CourseDetail> listAdd(PageData pd) throws Exception {
		// 查询所有父类id
		List<CourseDetail> valueList = this.listByParentId(pd); // 新增时默认章节父id为0
		// 查询所有小节
		List<PageData> listAll = listAll(pd);
		for (CourseDetail courseDetail : valueList) {
			List<CourseDetail> list = new ArrayList<CourseDetail>();
			for (PageData pageData : listAll) {
				int parentId = (int) pageData.get("CHAPTER_PARENT_ID");
					if (courseDetail.getCHAPTER_ID() == parentId) {
						CourseDetail subCourseDetail = new CourseDetail();
						subCourseDetail.setCHAPTER_ID((int)pageData.get("CHAPTER_ID"));
						subCourseDetail.setCHAPTER_NAME(pageData.getString("CHAPTER_NAME"));
						subCourseDetail.setCHAPTER_PARENT_ID((int)pageData.get("CHAPTER_PARENT_ID"));
						list.add(subCourseDetail);
				}
			}
			courseDetail.setSubCourseDetails(list);
		}

		return valueList;
	}

	/**
	 * 通过父id查询所有新增信息
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CourseDetail> listByParentId(PageData pd) throws Exception {
		return (List<CourseDetail>) dao.findForList("CourseDetailMapper.listByParentId", pd);
	}
	
	/**
	 * 新增章节
	 * @param pd
	 * @throws Exception
	 */
	public void saveChapter(PageData pd) throws Exception {
		dao.save("CourseDetailMapper.saveChapter", pd);
	}
	
	/**
	 * 新增小节
	 * @param pd
	 * @throws Exception
	 */
	public void saveSection(PageData pd) throws Exception {
		dao.save("CourseDetailMapper.saveSection",pd);
	}
	
	/**通过父id获取小节数 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer countByParentId(Integer parentId) throws Exception {
		return (Integer)dao.findForObject("CourseDetailMapper.countByParentId", parentId);
	}
	
	/**
	 * 查询小节信息
	 * @param pd
	 * @return
	 */
	public PageData findSection(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CourseDetailMapper.findSection", pd);
	}
	
}

