package com.fh.service.staffFilter.staffFilter;

import java.util.List;
import com.fh.entity.StaffFilterInfo;

/**
 * 系统配置接口
* @ClassName: StaffFilterManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
public interface StaffFilterManager{
	
	/**获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<StaffFilterInfo> getStaffFilter(StaffFilterInfo pd)throws Exception;
}

