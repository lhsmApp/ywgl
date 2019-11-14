package com.fh.service.exam.testpaper.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.exam.testpaper.TestPaperManager;

/** 
 * 说明： testpaper
 * 创建人：xinyuLo
 * 创建时间：2019-11-06
 * @version
 */
@Service("testpaperService")
public class TestPaperService implements TestPaperManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TestPaperMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TestPaperMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TestPaperMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TestPaperMapper.datalistPage", page);
	}
	
	/**考試信息列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listExam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TestPaperMapper.datalistExam", page);
	}
	
	/**考試历史信息列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listExamHistory(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TestPaperMapper.listExamHistory", page);
	}
	/**考試信息列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAnswer(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TestPaperMapper.listAnswer", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TestPaperMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TestPaperMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TestPaperMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 保存随机生成信息
	 * @param pageData
	 * @throws Exception
	 */
	public void savePaperParam(PageData pageData) throws Exception {
		dao.save("TestPaperMapper.savePaperParam", pageData);
	}

	/**
	 * 保存试卷明细
	 * @param paperDetail
	 * @throws Exception
	 */
	public void savePaperDetail(PageData paperDetail) throws Exception {
		dao.save("TestPaperMapper.savePaperDetail", paperDetail);
	}

	/**
	 * 删除试卷明细
	 * @param paperData
	 * @throws Exception
	 */
	public void deletePaperDetail(PageData paperData) throws Exception {
		dao.delete("TestPaperMapper.deletePaperDetail", paperData);
	}

	/**
	 * 删除随机信息
	 * @param paperData
	 * @throws Exception
	 */
	public void deletePaperParam(PageData paperData) throws Exception {
		dao.delete("TestPaperMapper.deletePaperParam", paperData);
	}

	/**
	 * 通过id查询随机条件
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listParamById(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TestPaperMapper.listParamById", pd);
	}

	/**
	 * 查询该试卷下所有试题
	 * @param pd
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPaperDetail(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TestPaperMapper.listPaperDetail", pd);
	}
	
}

