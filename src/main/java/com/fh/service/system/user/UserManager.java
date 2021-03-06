package com.fh.service.system.user;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.PageData;


/**
 * 用户接口类
* @ClassName: UserManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
public interface UserManager {
	
	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd)throws Exception;
	
	/**登录判断,查询学员表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStudentByNameAndPwd(PageData pd)throws Exception;
	
	/**登录判断,手机端
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwdOfApp(PageData pd)throws Exception;
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd)throws Exception;
	
	/**通过用户ID获取用户信息和角色信息
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception;
	
	/**通过用户ID获取学员信息和角色信息
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	public User getStudentAndRoleById(String USER_ID) throws Exception;
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception;
	
	/**通过StudentCode获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByStudentCode(PageData pd)throws Exception;
	
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception;
	/**列出某单位下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUserByUnitCode(PageData pd) throws Exception;
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd)throws Exception;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUsers(Page page)throws Exception;
	
	/**SAP用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listSapUsers(Page page)throws Exception;
	
	
	/**用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUsersBystaff(Page page)throws Exception;
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd)throws Exception;
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editUMy(PageData pd)throws Exception;
	
	/**修改学员用户
	 * @param pd
	 * @throws Exception
	 */
	public void editStudentMy(PageData pd)throws Exception;
	
	/**修改个人状态
	 * @param pd
	 * @throws Exception
	 */
	public void editState(PageData pd)throws Exception;
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception;
	
	/**用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUser(PageData pd)throws Exception;
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(String value)throws Exception;
	
	/**用户信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getUserValue(PageData pd)throws Exception;

	/**通过员工编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUserNum(PageData pd)throws Exception;
}
