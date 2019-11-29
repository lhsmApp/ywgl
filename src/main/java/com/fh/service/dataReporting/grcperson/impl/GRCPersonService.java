package com.fh.service.dataReporting.grcperson.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.dataReporting.grcperson.GRCPersonManager;
import com.fh.util.PageData;

/** 
 * 说明： GRC人员信息处理类
 * 创建人：xinyuLo
 * 创建时间：2019-09-20
 * @version
 */
@Service("grcpersonService")
public class GRCPersonService implements GRCPersonManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("GRCPersonMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("GRCPersonMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("GRCPersonMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GRCPersonMapper.datalistPage", page);
	}
		
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GRCPersonMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**导出模板
	 * @param PageData
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportModel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GRCPersonMapper.exportModel", pd);
	}
	
	/**导出列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GRCPersonMapper.exportList", page);
	}
	
	/**更新数据
	 * @param List<PageData>
	 * @throws Exception
	 */
	public void grcUpdateDatabase(List<PageData> listData) throws Exception {
		dao.update("GRCPersonMapper.delAndIns", listData);
		
	}

	/**
	 * 获取业务期间
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listBusiDate(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("GRCPersonMapper.listBusiDate", pd);
	}
	
	/**
	 * 获取业务权限日期
	 * @param pd
	 * @return
	 */
	public PageData findSysDeptTime(PageData pd) throws Exception {
		return (PageData)dao.findForObject("GRCPersonMapper.findSysDeptTime", pd);
	}
	
}

