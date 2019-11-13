package com.fh.service.exam.testpaper;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： testpaper接口
 * 创建人：xinyuLo
 * 创建时间：2019-11-06
 * @version
 */
public interface TestPaperManager{

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
	
	public List<PageData> listExam(Page page)throws Exception;
	public List<PageData> listExamHistory(Page page)throws Exception;
	public List<PageData> listAnswer(Page page)throws Exception;
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
	 * 保存随机生成信息
	 * @param pageData
	 * @throws Exception
	 */
	public void savePaperParam(PageData pageData)throws Exception;
	
	/**
	 * 保存试卷明细
	 * @param paperDetail
	 * @throws Exception
	 */
	public void savePaperDetail(PageData paperDetail)throws Exception;
	
	/**
	 * 删除试卷明细
	 * @param paperData
	 * @throws Exception
	 */
	public void deletePaperDetail(PageData paperData)throws Exception;
	
	/**
	 * 删除随机信息
	 * @param paperData
	 * @throws Exception
	 */
	public void deletePaperParam(PageData paperData)throws Exception;
	
	/**
	 * 通过id查询随机条件
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listParamById(PageData pd)throws Exception;
	
	/**
	 * 查询该试卷下所有试题
	 * @param pd
	 */
	public List<PageData> listPaperDetail(PageData pd)throws Exception;
	
}

