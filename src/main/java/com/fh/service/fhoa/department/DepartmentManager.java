package com.fh.service.fhoa.department;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.util.PageData;

/**
 *  组织机构接口类
* @ClassName: DepartmentManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
public interface DepartmentManager{

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
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception;

	/**通过条件获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<Department> findByCondition(String strCondition)throws Exception;
	
	/**是否有相同编码 
	 * @param pd
	 * @throws Exception
	 */
	public PageData hasSameDepartmentCode(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception;
	
	/**
	 * 通过ID获取其子级列表及其自身节点
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Department> listSubDepartmentAndSelfByParentId(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理ZTreeV3.5)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartmentZTreeNewV(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllDepartmentToSelect(String parentId, List<PageData> zdepartmentPdList) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表及本身节点(递归处理)下拉ztree用
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllDepartmentAndSelfToSelect(String parentId, List<PageData> zdepartmentPdList) throws Exception;
	
	/**获取某个部门所有下级部门ID(返回拼接字符串 in的形式)
	 * @param DEPARTMENT_ID
	 * @return
	 * @throws Exception
	 */
	public String getDEPARTMENT_CODES(String DEPARTMENT_ID) throws Exception;
	
	/**获取表字典
	 * 张晓柳
	 * @param 
	 * @throws Exception
	 */
	public List<Department> getDepartDic(PageData pd)throws Exception;
	
	/**
	 * 获得全部 名称和id对应关系
	 * 车易家
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllNameAndId(Page page)throws Exception;
	
}

