package com.fh.service.staffFilter.staffFilter.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.StaffFilterInfo;
import com.fh.service.staffFilter.staffFilter.StaffFilterManager;

/**
 * 系统配置
* @ClassName: StaffFilterService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
@Service("staffFilterService")
public class StaffFilterService implements StaffFilterManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**获取数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StaffFilterInfo> getStaffFilter(StaffFilterInfo pd)throws Exception{
		return (List<StaffFilterInfo>)dao.findForList("StaffFilterMapper.getStaffFilter", pd);
	}
}

