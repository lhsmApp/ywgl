package com.fh.service.testquestion.testquestion;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 题库管理处理类接口
 * 创建人：xinyuLo
 * 创建时间：2019-10-31
 * @version
 */
public interface TestQuestionManager{

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
	 * 根据试题id获取答案集合
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listById(PageData pd)throws Exception;
	
	/**
	 * 新增题目详细信息
	 * @param questionDetail
	 * @throws Exception
	 */
	public void saveItem(PageData questionDetail)throws Exception;
	
	/**
	 * 通过父id删除所有
	 * @param questionId
	 * @throws Exception
	 */
	public void deleteItem(String questionId)throws Exception;
	
	/**
	 * 通过id查询所有试题数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData countQuestion(PageData pd)throws Exception;

	/**
	 * 获取所有该条件下的题目数据
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> randomList(PageData pageData)throws Exception;
	
}

