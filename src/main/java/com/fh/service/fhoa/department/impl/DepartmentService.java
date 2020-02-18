package com.fh.service.fhoa.department.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.service.fhoa.department.DepartmentManager;

/**
 * 组织机构
* @ClassName: DepartmentService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Service("departmentService")
public class DepartmentService implements DepartmentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DepartmentMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DepartmentMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DepartmentMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DepartmentMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findById", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findByBianma", pd);
	}
	
	/**通过条件获取数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Department> findByCondition(String strCondition)throws Exception{
		return (List<Department>)dao.findForList("DepartmentMapper.findByCondition", strCondition);
	}
	
	/**是否有相同编码 
	 * @param pd
	 * @throws Exception
	 */
	public PageData hasSameDepartmentCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.hasSameDepartmentCode", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception {
		return (List<Department>) dao.findForList("DepartmentMapper.listSubDepartmentByParentId", parentId);
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Department> listSubDepartmentAndSelfByParentId(String parentId) throws Exception {
		return (List<Department>) dao.findForList("DepartmentMapper.listSubDepartmentAndSelfByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理ZTreeV3.5)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartmentZTreeNewV(String parentId) throws Exception {
		List<Department> allDepartmentList=new ArrayList<Department>();
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			this.getZTreeNewV(allDepartmentList,depar.getDEPARTMENT_CODE(),depar);
		}
		return allDepartmentList;
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理ZTreeV3.5)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	private void getZTreeNewV(List<Department> allDepartmentList,String parentId,Department curDepartment) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		if(departmentList!=null&&departmentList.size()>0){
			curDepartment.setOpen(true);
			allDepartmentList.add(curDepartment);
			for(Department depar : departmentList){
				this.getZTreeNewV(allDepartmentList,depar.getDEPARTMENT_CODE(),depar);
			}
		}else{
			curDepartment.setOpen(false);
			allDepartmentList.add(curDepartment);
		}
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("department/list.do?DEPARTMENT_CODE="+depar.getDEPARTMENT_CODE());
			depar.setSubDepartment(this.listAllDepartment(depar.getDEPARTMENT_CODE()));
			depar.setTarget("treeFrame");
			depar.setIcon("static/images/user.gif");
		}
		return departmentList;
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllDepartmentToSelect(String parentId,List<PageData> zdepartmentPdList) throws Exception {
		List<PageData>[] arrayDep = this.listAllbyPd(parentId,zdepartmentPdList);
		List<PageData> departmentPdList = arrayDep[1];
		for(PageData pd : departmentPdList){
			this.listAllDepartmentToSelect(pd.getString("id"),arrayDep[0]);
		}
		return arrayDep[0];
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表及本身节点(递归处理)下拉ztree用
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllDepartmentAndSelfToSelect(String parentId,List<PageData> zdepartmentPdList) throws Exception {
		List<PageData>[] arrayDep = this.listAllbyPd(parentId,zdepartmentPdList);
		List<PageData> departmentPdList = arrayDep[1];
		for(PageData pdItem : departmentPdList){
			this.listAllDepartmentAndSelfToSelect(pdItem.getString("id"),arrayDep[0]);
		}
		return arrayDep[0];
	}
	
	/**下拉ztree用
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllbyPd(String parentId,List<PageData> zdepartmentPdList) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		List<PageData> departmentPdList = new ArrayList<PageData>();
		for(Department depar : departmentList){
			PageData pd = new PageData();
			pd.put("id", depar.getDEPARTMENT_CODE());
			pd.put("parentId", depar.getPARENT_CODE());
			pd.put("name", depar.getNAME());
			pd.put("icon", "static/images/user.gif");
			departmentPdList.add(pd);
			zdepartmentPdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = zdepartmentPdList;
		arrayDep[1] = departmentPdList;
		return arrayDep;
	}
	
	/**下拉ztree用
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllAndSelfbyPd(String parentId,List<PageData> zdepartmentPdList) throws Exception {
		List<Department> departmentList = this.listSubDepartmentAndSelfByParentId(parentId);
		List<PageData> departmentPdList = new ArrayList<PageData>();
		for(Department depar : departmentList){
			PageData pd = new PageData();
			pd.put("id", depar.getDEPARTMENT_CODE());
			pd.put("parentId", depar.getPARENT_CODE());
			pd.put("name", depar.getNAME());
			pd.put("icon", "static/images/user.gif");
			departmentPdList.add(pd);
			zdepartmentPdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = zdepartmentPdList;
		arrayDep[1] = departmentPdList;
		return arrayDep;
	}
	
	/**获取某个部门所有下级部门ID(返回拼接字符串 in的形式， ('a','b','c'))
	 * @param DEPARTMENT_ID
	 * @return
	 * @throws Exception
	 */
	public String getDEPARTMENT_CODES(String DEPARTMENT_CODE) throws Exception {
		DEPARTMENT_CODE = Tools.notEmpty(DEPARTMENT_CODE)?DEPARTMENT_CODE:"0";
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		zdepartmentPdList = this.listAllDepartmentToSelect(DEPARTMENT_CODE,zdepartmentPdList);
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(PageData dpd : zdepartmentPdList){
			sb.append("'");
			sb.append(dpd.getString("id"));
			sb.append("'");
			sb.append(",");
		}
		sb.append("'fh')");
		return sb.toString();
	}
	
	
	/**获取表字典
	 * 张晓柳
	 * @param 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Department> getDepartDic(PageData pd)throws Exception{
		return (List<Department>)dao.findForList("DepartmentMapper.getDepartDic", pd);
	}
	/**
	 * 获得全部 名称和id对应关系
	 * 车易家
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAllNameAndId(Page page) throws Exception {
		// TODO 自动生成的方法存根
		return (List<PageData>)dao.findForList("DepartmentMapper.getAllNameAndId", page);
	}
}

