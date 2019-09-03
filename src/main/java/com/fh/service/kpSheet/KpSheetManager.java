package com.fh.service.kpSheet;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.util.PageData;

/** 
 * 说明： 
 * 创建人：jiachao
 * 创建时间：2019-04-12
 * @version
 */
public interface KpSheetManager{
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> getRptKpList(PageData pd)throws Exception;
}

