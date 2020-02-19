package com.fh.service.notice.notice.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.notice.notice.NoticeManager;

/** 
 * 说明： 信息通知处理类
 * 创建人：jiachao
 * 创建时间：2019-12-02
 * @version
 */
@Service("noticeService")
public class NoticeService implements NoticeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("NoticeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("NoticeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("NoticeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NoticeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NoticeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("NoticeMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**选择发布范围，展示角色列表
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> showSysRoleList(Page page)throws Exception{
        return (List<PageData>)dao.findForList("NoticeMapper.showSysRoleList", page);
    }
    
    /**选择发布范围，展示人员列表
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> showSysUserList(Page page)throws Exception{
        return (List<PageData>)dao.findForList("NoticeMapper.showSysUserlistPage", page);
    }

    /**
     * 选择发布范围，展示人员列表
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAllSysUserList(PageData pd) throws Exception {
		// TODO 自动生成的方法存根
    	return (List<PageData>)dao.findForList("NoticeMapper.getAllSysUserList", pd);
	}
    
    /**列表(全部)
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> getMyNotice(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("NoticeMapper.getMyNotice", pd);
    }

	
	
}

