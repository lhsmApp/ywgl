package com.fh.service.notice.notice;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 信息通知处理类接口
 * 创建人：jiachao
 * 创建时间：2019-12-02
 * @version
 */
public interface NoticeManager{

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

	/**选择发布范围，展示角色列表
     * @param page
     * @throws Exception
     */
    public List<PageData> showSysRoleList(Page page)throws Exception;
    
    /**选择发布范围，展示人员列表
     * @param page
     * @throws Exception
     */
    public List<PageData> showSysUserList(Page page)throws Exception;

    /**新增
     * @param pd
     * @throws Exception
     */
    public List<PageData> getMyNotice(PageData pd)throws Exception;

	public List<PageData> getAllSysUserList(PageData pd)throws Exception;
}

