package com.fh.service.sysBillnum.sysbillnum;

import com.fh.util.PageData;

/**
 *  最大单号维护
* @ClassName: SysBillnumManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月22日
*
 */
public interface SysBillnumManager{

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
}

