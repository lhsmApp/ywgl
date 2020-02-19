package com.fh.service.mbp;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * jqtest接口
* @ClassName: JgGridManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaojingping
* @date 2017年6月30日
*
 */
public interface MBPManager{

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
	/**问题统计列表（按类型）
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listProTypeStatistic(Page page)throws Exception;
	/**问题统计列表（按模块）
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listProModStatistic(Page page)throws Exception;
	
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
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int count(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**问题提交
	 * @param pd
	 * @throws Exception
	 */
	public void commit(PageData pd)throws Exception;
	
	/**问题取消提交
	 * @param pd
	 * @throws Exception
	 */
	public void cancel(PageData pd)throws Exception;
	
	/**问题领取
	 * @param pd
	 * @throws Exception
	 */
	public void proGet(PageData pd)throws Exception;
	
	/**获取问题分配列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getProAssigns(PageData pd)throws Exception;
	
	/**问题分配
	 * @param pd
	 * @throws Exception
	 */
	public void addAssign(PageData pd)throws Exception;
	
	/**问题分配取消
	 * @param pd
	 * @throws Exception
	 */
	public void deleteAssign(PageData pd)throws Exception;
	
	/**问题回复
	 * @param pd
	 * @throws Exception
	 */
	public void addAnswer(PageData pd)throws Exception;
	
	/**问题回复作废
	 * @param pd
	 * @throws Exception
	 */
	public void deleteAnswer(PageData pd)throws Exception;
	
	/**获取问题关闭列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getProCloses(PageData pd)throws Exception;
	
	/**获取关闭详情
	 * @param pd
	 * @throws Exception
	 */
	public PageData getCloseContent(PageData pd)throws Exception;
	
	/**问题关闭
	 * @param pd
	 * @throws Exception
	 */
	public void addClose(PageData pd)throws Exception;
	
	/**问题关闭取消
	 * @param pd
	 * @throws Exception
	 */
	public void deleteClose(PageData pd)throws Exception;
	
	/**获取问题回复信息列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getProAnswers(PageData pd)throws Exception;
	
	/**获取问题回复信息内容
	 * @param pd
	 * @throws Exception
	 */
	public PageData getAnswerContent(PageData pd)throws Exception;
	
	/**修改回复内容
	 * @param pd
	 * @throws Exception
	 */
	public void updateAnswer(PageData pd)throws Exception;
	
	/**获取问题日志信息列表
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getProLog(PageData pd)throws Exception;
	/**获取问题回复信息各节点时间信息
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> getProLogTime(Page page)throws Exception;
	/**问题日志
	 * @param pd
	 * @throws Exception
	 */
	public void addLog(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> JqPage(JqPage page)throws Exception;
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int countJqGrid(JqPage page)throws Exception;
}

