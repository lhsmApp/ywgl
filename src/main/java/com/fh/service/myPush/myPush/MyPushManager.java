/**
 * 
 */
package com.fh.service.myPush.myPush;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;

/**
 * @author yijche
 * @Date 2020-03-23 1:41:15 PM
 */
public interface MyPushManager {
	/**
	 * 说明：将推送的内容保存，并执行推送消息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject saveSend(PageData noticePd) throws Exception;

	/**
	 * 说明：获取当前用户的未读消息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getNoticeByUserName(PageData pd) throws Exception;

	/**
	 * 说明：设置用户状态
	 * 
	 * @param pd
	 * @throws Exception
	 */
//	public void setUserStatus(PageData pd) throws Exception;

	public String getMyIdentityToString() throws Exception;

	public List<PageData> checkNoticeId(PageData pd) throws Exception;

	public List<PageData> getOneNoticeById(PageData pd) throws Exception;

	/**
	 * 说明：根据ID批量获取通告表内容
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> batchGetNoticeById(PageData pd) throws Exception;

	/**
	 * 说明：获取当前用户未读的推送内容
	 * 
	 * @Date 2020-03-28 11:46:17 AM
	 */
	public List<PageData> getMySelfUnRead(PageData pd) throws Exception;

	public List<PageData> markRead(PageData pd) throws Exception;
	
	public JSONObject editSend(PageData pd)throws Exception;
}
