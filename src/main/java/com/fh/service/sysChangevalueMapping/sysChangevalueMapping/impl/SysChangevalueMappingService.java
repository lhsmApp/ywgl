package com.fh.service.sysChangevalueMapping.sysChangevalueMapping.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.system.Department;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;
import com.fh.service.sysChangevalueMapping.sysChangevalueMapping.SysChangevalueMappingManager;

/** 
 * 说明：  工资明细
 * 创建人：zhangxiaoliu
 * 创建时间：2017-06-30
 * @version
 */
@Service("sysChangevalueMappingService")
public class SysChangevalueMappingService implements SysChangevalueMappingManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> JqPage(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("SysChangevalueMappingMapper.datalistJqPage", page);
	}
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception{
		return (int)dao.findForObject("SysChangevalueMappingMapper.countJqGridExtend", page);
	}

	/**用于判断数据是否重复
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getRepeatList(List<PageData> list)throws Exception{
		return (List<PageData>)dao.findForList("SysChangevalueMappingMapper.getRepeatList", list);
	}

	/**用于判断数据是否有下一步，可否删除
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getChangeValueList(List<PageData> list, Boolean bolDel)throws Exception{
		if(bolDel){
			return (List<PageData>)dao.findForList("SysChangevalueMappingMapper.getDelChangeValueList", list);
		} else {
			return (List<PageData>)dao.findForList("SysChangevalueMappingMapper.getUpdateChangeValueList", list);
		}
	}
	
	/**更新数据库
	 * @param pd
	 * @throws Exception
	 */
	public void batchUpdateDatabase(List<PageData> listData)throws Exception{
		dao.update("SysChangevalueMappingMapper.batchDelAndIns", listData);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(List<PageData> listData)throws Exception{
		dao.delete("SysChangevalueMappingMapper.deleteAll", listData);
	}

	/**设置值界面下拉列表选项
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionaries> getSelectBillOffList(PageData pd)throws Exception{
		return (List<Dictionaries>)dao.findForList("SysChangevalueMappingMapper.getSelectBillOffList", pd);
	}
	@SuppressWarnings("unchecked")
	public List<Dictionaries> getSelectTypeCodeList(PageData pd)throws Exception{
		return (List<Dictionaries>)dao.findForList("SysChangevalueMappingMapper.getSelectTypeCodeList", pd);
	}
	@SuppressWarnings("unchecked")
	public List<Dictionaries> getSelectDeptCodeList(PageData pd)throws Exception{
		return (List<Dictionaries>)dao.findForList("SysChangevalueMappingMapper.getSelectDeptCodeList", pd);
	}
	@SuppressWarnings("unchecked")
	public List<Dictionaries> getSelectMapCodeList(PageData pd)throws Exception{
		return (List<Dictionaries>)dao.findForList("SysChangevalueMappingMapper.getSelectMapCodeList", pd);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理ZTreeV3.5)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Department> listAllDeptContainsChangeCols(String parentId, PageData pd) throws Exception {
		List<Department> allDepartmentList=new ArrayList<Department>();
		List<Department> departmentList = (List<Department>) dao.findForList("SysChangevalueMappingMapper.getCopyDeptList", pd);
		for(Department depar : departmentList){
			depar.setPARENT_CODE(parentId);
			depar.setOpen(false);
			allDepartmentList.add(depar);
		}
		return allDepartmentList;
	}

	/**设置值界面下拉列表选项
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getDataInputCheckHavaList(List<PageData> listData)throws Exception{
		return (List<PageData>)dao.findForList("SysChangevalueMappingMapper.getDataInputCheckHavaList", listData);
	}
}

