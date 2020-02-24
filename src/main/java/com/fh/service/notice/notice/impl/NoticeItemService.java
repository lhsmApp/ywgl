package com.fh.service.notice.notice.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.notice.notice.NoticeItemManager;

/** 
 * 说明： 信息通知处理类
 * 创建人：jiachao
 * 创建时间：2019-12-02
 * @version
 */
@Service("noticeItemService")
public class NoticeItemService implements NoticeItemManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("NoticeItemMapper.save", pd);
	}
	
	/**批量新增
     * @param pd
     * @throws Exception
     */
    public void saveAll(PageData pd)throws Exception{
        dao.save("NoticeItemMapper.saveAll", pd);
    }
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("NoticeItemMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("NoticeItemMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NoticeItemMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeItemMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeItemMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("NoticeItemMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
}

