package com.fh.service.sysConfig.sysconfig.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;

/**
 * 系统配置
* @ClassName: SysConfigService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
@Service("sysconfigService")
public class SysConfigService implements SysConfigManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("SysConfigMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("SysConfigMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("SysConfigMapper.edit", pd);
	}
	
	/**更新业务期间
	 * @param pd
	 * @throws Exception
	 */
	//public void updateBusidate(String busiDate)throws Exception{
	//	dao.update("SysConfigMapper.updateBusidate", busiDate);
	//}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysConfigMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysConfigMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysConfigMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysConfigMapper.deleteAll", ArrayDATA_IDS);
	}


	/**获取系统期间
	 * 张晓柳
	 * @param pd
	 * @throws Exception
	 */
	public String currentSection(PageData pd)throws Exception{
		return (String) dao.findForObject("SysConfigMapper.currentSection", pd);
	}
	
	/**获取系统期间
	 * 张晓柳
	 * @param pd
	 * @throws Exception
	 */
	public String getSysConfigByKey(PageData pd)throws Exception{
		return (String) dao.findForObject("SysConfigMapper.getSysConfigByKey", pd);
	}
}

