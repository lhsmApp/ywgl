package com.fh.service.dataInput.dataInput.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.util.PageData;
import com.fh.service.dataInput.dataInput.DataInputManager;

/** 
 * 说明： 汇总单据确认
 * 创建人：张晓柳
 * 创建时间：2018-04-11
 * @version
 */
@Service("dataInputService")
public class DataInputService implements DataInputManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DataInputMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("DataInputMapper.countJqGridExtend", page);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DataInputMapper.exportList", page);
	}

	/**通过流水号获取流水号，用于判断数据是否变更 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRepeatRecord(List<PageData> listData)throws Exception{
		return (List<PageData>)dao.findForList("DataInputMapper.getRepeatRecord", listData);
	}

	/**复制
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getHaveCopyRecord(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DataInputMapper.getHaveCopyRecord", pd);
	}

	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.update("DataInputMapper.save", pd);
	}
	/**批量删除
	 * @param 
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("DataInputMapper.deleteAll", listData);
	}
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("DataInputMapper.batchDelAndIns", listData);
	}
	public void batchUpdateDatabaseHorizontal(List<PageData> listData)throws Exception{
		dao.update("DataInputMapper.batchDelAndInsHorizontal", listData);
	}

	/**复制
	 * @param pd
	 * @throws Exception
	 */
	public void batchCopyAll(PageData pd)throws Exception{
		dao.update("DataInputMapper.batchCopyAll", pd);
	}
}

