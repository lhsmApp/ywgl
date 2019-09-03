package com.fh.service.syslogrec.syslogrec;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明：  日志
 * 创建人：zhangxiaoliu
 * 创建时间：2018-06-25
 * @version
 */
public interface SysLogRecManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGridExtend(JqPage page)throws Exception;

	/**导出到excel
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> export(JqPage page)throws Exception;
}

