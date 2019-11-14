package com.fh.service.exam.testmain;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： testmain接口
 * 创建人：jiachao
 * 创建时间：2019-11-06
 * @version
 */
public interface TestMainManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	/**保存考试结果
	 * @param pd
	 * @throws Exception
	 */
	public void saveExamResult(PageData pd)throws Exception;
	
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
	
	/**查找考试人已考试信息
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listByUser(PageData pd)throws Exception;
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
	  *  获取所有考试信息 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> paperListPage(Page page)throws Exception;
	
	/**
	 * 导出信息到excel
	 * @param page
	 * @return
	 */
	public List<PageData> exportList(Page page)throws Exception;
	
}

