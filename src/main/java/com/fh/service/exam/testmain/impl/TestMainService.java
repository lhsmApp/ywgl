package com.fh.service.exam.testmain.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.exam.testmain.TestMainManager;

/** 
 * 说明： testmain
 * 创建人：jiachao
 * 创建时间：2019-11-06
 * @version
 */
@Service("testmainService")
public class TestMainService implements TestMainManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TestMainMapper.save", pd);
	}
	/**保存考试结果
	 * @param pd
	 * @throws Exception
	 */
	public void saveExamResult(PageData pd)throws Exception{
		dao.save("TestMainMapper.saveExamResult", pd);
	}
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TestMainMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TestMainMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TestMainMapper.datalistPage", page);
	}
	/**查找考试人已考试信息
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByUser(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TestMainMapper.listByUser", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TestMainMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TestMainMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TestMainMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	  *  获取所有考试信息 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> paperListPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("TestMainMapper.paperListPage", page);
	}

	/**
	 * 导出信息到excel
	 * @param page
	 * @return
	 */
	public List<PageData> exportList(Page page) throws Exception {
		return null;
	}
	
}

