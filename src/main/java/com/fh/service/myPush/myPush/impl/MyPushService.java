/**
 * 
 */
package com.fh.service.myPush.myPush.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport;
import com.fh.entity.system.User;
import com.fh.service.myPush.myPush.MyPushManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.base.NetCommBase;

/**
 * @author yijche
 * @Date 2020-03-23 1:44:59 PM
 */
@Service("myPushService")
public class MyPushService implements MyPushManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final static String CUSTOM_GROUP = "CUSTOM_GROUP";

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject saveSend(PageData noticePd) throws Exception {
		// 初始化返回信息
		JSONObject reData = new JSONObject();
		reData.put("iRet", 0);
		reData.put("sMsg", "");

		// 读取配置文件(假装有配置文件)
		Map<String, String> configMap = this.loadConfig();

		/* key和value的数量一定要保证一样 */
		String[] cfgScopekey = configMap.get("key").toString().split(",");
		String[] cfgScopeValue = configMap.get("value").toString().split(",");

		Map<String, String> cfgScopeMap = new HashMap<String, String>();// 通过cfgScopeMap可知key、value的对应关系
		for (int i = 0; i < cfgScopekey.length; i++) {
			cfgScopeMap.put(cfgScopekey[i], cfgScopeValue[i]);
		}

		// 未赋值的字段走默认配置
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userName = user.getUSERNAME();
		String userId = user.getUSER_ID();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());
		noticePd.put("sCreater", userName);
		noticePd.put("dtCreateTime", date);
		noticePd.put("sLastEditors", userName);
		noticePd.put("iCreaterId", userId);
		noticePd.put("iLastEditorsId", userId);
		noticePd.put("iStatus", 1);

		if (noticePd.get("sDetails") == null) {
			reData.put("iRet", 100002);
			reData.put("sMsg", "推送失败，内容未设置");
			return reData;
		}
		// 有方案就取方案的值，没有则查看前端是否传值，都没有则退出
		List<Map<String, String[]>> scopeList = new ArrayList<Map<String, String[]>>();
		// 如果未使用方案
		if (noticePd.get("iGroupId") == null) {
			noticePd.remove(CUSTOM_GROUP);// 防止误操作，删除该值。这个值是标记自定义字段的，不能给其他事务使用
			boolean hasVal = false;
			for (String key : cfgScopekey) {
				PageData scopeListPd = new PageData();
				Object obj = noticePd.get(key);
				if (obj == null) {
					continue;
				}
				scopeListPd.put("name", key);
				String[] strs = obj.toString().split(",");
				List<String> distinctList = new ArrayList<String>();
				for (String str : strs) {
					if (!distinctList.contains(str))
						distinctList.add(str);
				}
				scopeListPd.put("content", distinctList);
				scopeList.add(scopeListPd);
				hasVal = true;
			}
			if (!hasVal) {
				reData.put("iRet", 100003);
				reData.put("sMsg", "推送失败，推送范围未设置");
				return reData;
			}
		} else {
			// iGroupId= 0推送给全员
			if (!noticePd.get("iGroupId").equals("0")) {
				if (dao.findForObject("MyPushMapper.checkGroupById", noticePd) == null) {
					reData.put("iRet", 100004);
					reData.put("sMsg", "推送失败，该方案不存在或者没有使用权限");
					return reData;
				}
			}
			PageData scopePd = new PageData();
			scopePd.put("name", CUSTOM_GROUP);
			List<String> distinctList = new ArrayList<String>();
			distinctList.add(noticePd.get("iGroupId") + "");
			scopePd.put("content", distinctList);
			scopeList.add(scopePd);
		}
		noticePd.put("scopeList", scopeList);

		if (noticePd.get("sTitle") == null) {
			noticePd.put("sTitle", "新消息提示");
		}
		if (noticePd.get("iLevel") == null) {
			noticePd.put("iLevel", 1);
		}

		if (noticePd.get("sCanClickTile") == null) {
			noticePd.put("sCanClickTile", "点击表示已读");
		}
		if (noticePd.get("sCanClickUrl") == null) {
			noticePd.put("sCanClickUrl", "");
		}
		if (noticePd.get("iIsForward") == null) {
			noticePd.put("iIsForward", "0");
		}
		if (noticePd.get("dtBeginTime") == null) {
			noticePd.put("dtBeginTime", date);
		}

		if (noticePd.get("dtOverTime") == null) {
			Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.YEAR, 100);
			noticePd.put("dtOverTime", dateFormat.format(calendar.getTime()));
		}
		if (noticePd.get("sImgUrl") == null) {
			noticePd.put("sImgUrl", "");
		}
		if (noticePd.get("iForkId") == null) {
			Object obj = dao.findForObject("MyPushMapper.getLastForkIdById", noticePd);
			int lastfId = obj == null ? 0 : (int) obj;
			noticePd.put("iForkId", ++lastfId);
		} else {
			// 查看该分支是否存在
			List<PageData> list = (List<PageData>) dao.findForList("MyPushMapper.getNoticeById", noticePd);
			if (list.size() > 0) {
				reData.put("iRet", 100504);
				reData.put("sMsg", "失败，通告id已存在");
				return reData;
			}
		}

		// 开始插入
		PageData scopePd = new PageData();
		scopePd.put("scopeList", noticePd.get("scopeList"));
		scopePd.put("iModuleId", noticePd.get("iModuleId"));
		scopePd.put("iModuleSubId", noticePd.get("iModuleSubId"));
		scopePd.put("iForkId", noticePd.get("iForkId"));

		dao.save("MyPushMapper.saveNotice", noticePd);
		dao.save("MyPushMapper.saveBatchScope", scopePd);

		// 推送消息
		JSONObject json = new JSONObject();
		json.put("level", noticePd.get("iLevel"));
		json.put("id", noticePd.get("iModuleId") + ":" + noticePd.get("iModuleSubId") + ":" + noticePd.get("iForkId"));
		json.put("from", configMap.get("from"));
		sendMsg(json.toString());
		return reData;
	}

	private void sendMsg(String str) throws Exception {
		// 获取websocket配置
		Map<String, Object> map = new HashMap<String, Object>();
		String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);// 读取WEBSOCKET配置
		if (null != strWEBSOCKET && !"".equals(strWEBSOCKET)) {
			String strIW[] = strWEBSOCKET.split(",fh,");
			if (strIW.length == 5) {
				map.put("wimadress", strIW[0] + ":" + strIW[1]); // 即时聊天服务器IP和端口
				map.put("oladress", strIW[2] + ":" + strIW[3]); // 在线管理和站内信服务器IP和端口
				map.put("FHsmsSound", strIW[4]); // 站内信提示音效配置
			}
		}

		NetCommBase.execPostCurl(map.get("oladress").toString(), str);
	}

	/**
	 * 说明：根据名字获取未读记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getNoticeByUserName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MyPushMapper.getNoticeByUserName", pd);
	}

	/**
	 * 说明：设置用户状态
	 * 
	 * @param pd
	 * @throws Exception
	 */
//	@Override
//	public void setUserStatus(PageData pd) throws Exception {
//		dao.update("MyPushMapper.setUserStatus", pd);
//	}

	/**
	 * 说明：根据ID批量获取通告表内容
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> batchGetNoticeById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MyPushMapper.batchGetNoticeById", pd);
	}

	/**
	 * 说明：获取当前用户未读的推送内容
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getMySelfUnRead(PageData pd) throws Exception {
		PageData pd2 = new PageData();
		pd2.put("where", this.getMyIdentityToString());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd2.put("iUserId", user.getUSER_ID());
		List<PageData> notices = (List<PageData>) dao.findForList("MyPushMapper.getMyNotices", pd2);
		// 遍历，剔除重复、已读 和 未开始的信息
		long currentTimeMillis = System.currentTimeMillis();
		List<String> distinctList = new ArrayList<String>();
		List<PageData> outputList = new ArrayList<PageData>();
		List<PageData> overTimeList = new ArrayList<PageData>();
		for (PageData pd3 : notices) {
			String mergeId = pd3.get("iModuleId") + ":" + pd3.get("iModuleSubId") + ":" + pd3.get("iForkId");

			if (!distinctList.contains(mergeId)) {
				distinctList.add(mergeId);
			} else {
				continue;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long dtBeginTimeMillis = sdf.parse(pd3.getString("dtBeginTime")).getTime();
			long dtOverTimeMillis = sdf.parse(pd3.getString("dtOverTime")).getTime();
			if (currentTimeMillis < dtBeginTimeMillis) {
				continue;
			}
			if (currentTimeMillis > dtOverTimeMillis) {
				overTimeList.add(pd3);
				continue;
			}
			if (pd3.get("iUserId") != null) {
				continue;// 跳过已读
			}
			outputList.add(pd3);
		}
		// 把过期的信息标记
		if (overTimeList.size() > 0) {
			PageData p4 = new PageData();
			p4.put("newStatus", -1);
			p4.put("noticeList", overTimeList);
			dao.update("MyPushMapper.setBatchNoticeStatus", p4);
		}
		// 把未读的输出
		return outputList;
	}

	public String getMyIdentityToString() throws Exception {
		Map<String, List<PageData>> myIdentity = this.getMyIdentity();
		// 根据身份信息获取公告
		List<String> l1 = new ArrayList<String>();
		List<String> l2 = new ArrayList<String>();
		for (PageData p : myIdentity.get("userSysList")) {
			l1.add("(tb_push_scope.sType='" + p.getString("key") + "' AND tb_push_scope.sAbout='" + p.get("value")
					+ "')");

		}
		for (PageData p : myIdentity.get("userGroupList")) {
			l2.add("" + p.get("iGroupId"));
		}
		if (l2 != null && l2.size() > 0) {
			l1.add("(tb_push_scope.sType='CUSTOM_GROUP' AND tb_push_scope.sAbout IN (" + String.join(",", l2) + "))");
		}
		return String.join("OR", l1);
	}

	/**
	 * 说明：获取当前用户涉及到的身份信息（包括单位、角色以及自定义的组）
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List<PageData>> getMyIdentity() throws Exception {
		// 读取配置文件(假装有配置文件)
		Map<String, String> configMap = this.loadConfig();

		/* key和value的数量一定要保证一样 */
		String[] cfgScopekey = configMap.get("key").toString().split(",");
		String[] cfgScopeValue = configMap.get("value").toString().split(",");

		Map<String, String> cfgScopeMap = new HashMap<String, String>();// 通过cfgScopeMap可知key、value的对应关系
		for (int i = 0; i < cfgScopekey.length; i++) {
			cfgScopeMap.put(cfgScopekey[i], cfgScopeValue[i]);
		}
		Map<String, String> reverseCfgScopeMap = new HashMap<String, String>();// 通过cfgScopeMap可知key、value的对应关系
		for (int i = 0; i < cfgScopeValue.length; i++) {
			reverseCfgScopeMap.put(cfgScopeValue[i], cfgScopekey[i]);
		}
		// 初始化参数

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
//		String userName = user.getUSERNAME();
		String userId = user.getUSER_ID();

		configMap.put("userIdValue", userId);
		List<PageData> userSysinfo = (List<PageData>) dao.findForList("MyPushMapper.getUserInfo", configMap);// 获取人员信息

		// 这个做兼容处理，把key换成tb_push_group_config能识别的
		List<PageData> userSysList = new ArrayList<PageData>();
		for (PageData p : userSysinfo) {
			Iterator<Entry<String, String>> iter = p.entrySet().iterator();
			while (iter.hasNext()) {
				PageData pp = new PageData();
				Entry<String, String> entry = iter.next();
				pp.put("key", reverseCfgScopeMap.get(entry.getKey()));
				pp.put("value", entry.getValue());
				userSysList.add(pp);
			}
		}

		PageData pd = new PageData();
		pd.put("userSysinfo", userSysList);
		List<PageData> userGroupList = (List<PageData>) dao.findForList("MyPushMapper.getMyGroupByAttr", pd);
		// 用户的自定义组id等于0时，表示全体用户可见
		PageData allUserKey = new PageData();
		allUserKey.put("iGroupId", "0");
		userGroupList.add(allUserKey);

		Map<String, List<PageData>> mergeMap = new HashMap<String, List<PageData>>();
		mergeMap.put("userSysList", userSysList);
		mergeMap.put("userGroupList", userGroupList);
		return mergeMap;
	}

	public Map<String, String> loadConfig() {
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put("from", "ywgl");// 平台
		configMap.put("userTable", "sys_user");// 用户表名称
		configMap.put("userIdField", "USER_ID");// USER_ID根据平台修改
		configMap.put("where", " AND STATUS=1");// 补充where条件，可以为空
		configMap.put("key", "UserList,RoleList,DepartmentList");// 自定义类型英文名；“CUSTOM_GROUP”已被占用，请不要再次使用！
		configMap.put("value", "USER_ID,ROLE_ID,UNIT_CODE");// 根据userTable中配置的内容调整
		return configMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> checkNoticeId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MyPushMapper.checkNoticeId", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getOneNoticeById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MyPushMapper.getNoticeById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> markRead(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MyPushMapper.markRead", pd);
	}

	/**
	 * 修改通告内容和范围（可以选择是否删除已读信息）
	 * 
	 * pd.doCleanMark 是否清理已读用户标记 pd.doSend 是否再次推送通知
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject editSend(PageData noticePd) throws Exception {
		// 初始化返回信息
		JSONObject reData = new JSONObject();
		reData.put("iRet", 0);

		if (noticePd.get("iModuleId") == null || noticePd.get("iModuleSubId") == null
				|| noticePd.get("iForkId") == null) {
			reData.put("iRet", 100021);
			reData.put("sMsg", "失败，通告id未设置");
			return reData;
		}

		// 读取配置文件(假装有配置文件)
		Map<String, String> configMap = this.loadConfig();

		/* key和value的数量一定要保证一样 */
		String[] cfgScopekey = configMap.get("key").toString().split(",");
		String[] cfgScopeValue = configMap.get("value").toString().split(",");

		Map<String, String> cfgScopeMap = new HashMap<String, String>();// 通过cfgScopeMap可知key、value的对应关系
		for (int i = 0; i < cfgScopekey.length; i++) {
			cfgScopeMap.put(cfgScopekey[i], cfgScopeValue[i]);
		}

		// 未赋值的字段走默认配置
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userName = user.getUSERNAME();
		String userId = user.getUSER_ID();
		noticePd.put("sLastEditors", userName);
		noticePd.put("iLastEditorsId", userId);
		// 有方案就取方案的值，没有则查看前端是否传值，都没有则退出
		List<Map<String, String[]>> scopeList = new ArrayList<Map<String, String[]>>();
		// 如果未使用方案
		if (noticePd.get("iGroupId") == null) {
			noticePd.remove(CUSTOM_GROUP);// 防止误操作，删除该值。这个值是标记自定义字段的，不能给其他事务使用
			for (String key : cfgScopekey) {
				PageData scopeListPd = new PageData();
				Object obj = noticePd.get(key);
				if (obj == null) {
					continue;
				}
				scopeListPd.put("name", key);
				String[] strs = obj.toString().split(",");
				List<String> distinctList = new ArrayList<String>();
				for (String str : strs) {
					if (!distinctList.contains(str))
						distinctList.add(str);
				}
				scopeListPd.put("content", distinctList);
				scopeList.add(scopeListPd);
			}
		} else {
			// iGroupId= 0推送给全员
			if (!noticePd.get("iGroupId").equals("0")) {
				if (dao.findForObject("MyPushMapper.checkGroupById", noticePd) == null) {
					reData.put("iRet", 100104);
					reData.put("sMsg", "失败，该方案不存在或者没有使用权限");
					return reData;
				}
			}
			PageData scopePd = new PageData();
			scopePd.put("name", CUSTOM_GROUP);
			List<String> distinctList = new ArrayList<String>();
			distinctList.add(noticePd.get("iGroupId") + "");
			scopePd.put("content", distinctList);
			scopeList.add(scopePd);
		}

		// 开始修改
		if (scopeList.size() > 0) {
			PageData scopePd = new PageData();
			scopePd.put("scopeList", scopeList);
			scopePd.put("iModuleId", noticePd.get("iModuleId"));
			scopePd.put("iModuleSubId", noticePd.get("iModuleSubId"));
			scopePd.put("iForkId", noticePd.get("iForkId"));

			dao.delete("MyPushMapper.deleteScopeByNoticeId", scopePd);
			dao.save("MyPushMapper.saveBatchScope", scopePd);
		}

		// 至少要修改一个内容
		String[] atLeastOne = { "iLevel", "iStatus", "dtBeginTime", "dtOverTime", "sTitle", "sDetails", "sCanClickTile",
				"sCanClickUrl", "sImgUrl", "iIsForward" };
		for (String s : atLeastOne) {
			if (noticePd.get(s) != null) {
				dao.update("MyPushMapper.editNotice", noticePd);
				break;
			}
		}

		if (noticePd.get("doCleanMark") != null && noticePd.get("doCleanMark").equals("1")) {// 是否清空已读
			dao.delete("MyPushMapper.deleteMarkByNoticeId", noticePd);
		}

		if (noticePd.get("doSend") != null && noticePd.get("doSend").equals("1")) {// 是否需要推送
			// 推送消息
			JSONObject json = new JSONObject();
			json.put("level", "1");
			json.put("id",
					noticePd.get("iModuleId") + ":" + noticePd.get("iModuleSubId") + ":" + noticePd.get("iForkId"));
			json.put("from", configMap.get("from"));
			sendMsg(json.toString());
		}
		return reData;
	}

}
