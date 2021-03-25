package com.fh.service.trainBase.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.trainBase.TrainScoreManager;

/** 
 * 说明： 知识结构分类
 * 创建人：liqian
 * 创建时间：2021-02-26
 * @version
 */
@Service("trainscoreService")
public class TrainScoreService implements TrainScoreManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TrainScoreMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TrainScoreMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TrainScoreMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TrainScoreMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TrainScoreMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TrainScoreMapper.findById", pd);
	}
	/**通过员工编号获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUserCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TrainScoreMapper.findByUserCode", pd);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("TrainScoreMapper.exportList", page);
	}

	/**导出模板
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TrainScoreMapper.exportModel", pd);
	}

	/**导入
	 * @param pd
	 * @throws Exception
	 */
	public void saveImport(PageData pd)throws Exception{
		dao.save("TrainScoreMapper.saveImport", pd);
	}
	
}

