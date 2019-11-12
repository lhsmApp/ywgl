package com.fh.service.testquestion.testquestion.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.testquestion.testquestion.TestQuestionManager;

/** 
 * 说明： 题库管理处理类
 * 创建人：xinyuLo
 * 创建时间：2019-10-31
 * @version
 */
@Service("testquestionService")
public class TestQuestionService implements TestQuestionManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TestQuestionMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TestQuestionMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TestQuestionMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TestQuestionMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TestQuestionMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TestQuestionMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TestQuestionMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 根据试题id获取答案集合
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listById(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TestQuestionMapper.listById", pd);
	}

	/**
	 * 新增题目详细信息
	 * @param questionDetail
	 * @throws Exception
	 */
	public void saveItem(PageData questionDetail) throws Exception {
		dao.save("TestQuestionMapper.saveItem", questionDetail);
	}

	/**
	 * 通过父id删除所有
	 * @param questionId
	 * @throws Exception
	 */
	public void deleteItem(String questionId) throws Exception {
		dao.delete("TestQuestionMapper.deleteItem",questionId);
	}
	
	/**
	 * 通过id查询所有试题数量
	 * @param pd
	 * @throws Exception
	 */
	public PageData countQuestion(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TestQuestionMapper.countQuestion", pd);
	}

	/**
	 * 获取所有该条件下的题目数据
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> randomList(PageData pageData) throws Exception {
		return (List<PageData>)dao.findForList("TestQuestionMapper.randomList", pageData);
	}
	
}

