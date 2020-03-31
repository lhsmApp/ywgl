package com.fh.controller.myPush.myPush;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.myPush.myPush.MyPushManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/mypush")
public class MyPushController extends BaseController {
	String menuUrl = "notice/list.do"; // 菜单地址(权限用)

	@Resource(name = "myPushService")
	private MyPushManager myPushService;

	/**
	 * 查询是否有我的消息
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyNotice")
	public void getMyNotice(HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		// //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();

		List<PageData> retData = myPushService.getMySelfUnRead(pd);

		JSONObject json = new JSONObject();
		json.put("iRet", 0);
		json.put("sMsg", "success");
		json.put("reData", retData);

		out.write(json.toString());
		out.close();
	}

	/**
	 * 说明：判断新消息是否可以显示，如果可以则返回通告内容
	 */
	@RequestMapping(value = "/newPushHousekeeper")
	public void newPushHousekeeper(HttpServletResponse response) throws Exception {
		// 初始化返回值
		JSONObject json = new JSONObject();
		json.put("iRet", 0);
		json.put("sMsg", "success");

		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		PageData pd = new PageData();
		pd = this.getPageData();

		List<PageData> reData = new ArrayList<PageData>();
		if (chcekNoticeId(pd)) {
			reData = myPushService.getOneNoticeById(pd);
		}
		json.put("reData", reData);
		out.write(json.toString());
		out.close();
	}

	/**
	 * 说明：把前端传来的通告id格式化
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private List<PageData> formatNoticeId(PageData pd) throws Exception {
		PageData rePd = new PageData();
		List<PageData> list = new ArrayList<PageData>();
		if (pd.get("findById") != null && pd.get("findById") != "") {
			String[] ids = pd.get("findById").toString().split(":");
			String[] keys = { "iModuleId", "iModuleSubId", "iForkId" };
			if (ids.length != 3) {
				return list;
			}
			for (int i = 0; i < ids.length; i++) {
				pd.put(keys[i], ids[i]);
				list.add(pd);
			}
		}
		return list;
	}

	/**
	 * 说明：检查前端传来的通告id是否属于当前用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	private boolean chcekNoticeId(PageData pd) throws Exception {
		List<PageData> tmpList = formatNoticeId(pd);
		if(tmpList.size()==0) {
			return false;
		}
		pd.put("where",myPushService.getMyIdentityToString() );
		
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("iUserId", user.getUSER_ID());

		List<PageData> notice = myPushService.checkNoticeId(pd);

		if (notice.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 消息提醒，记录用户已读情况
	 */
	@RequestMapping(value = "/tagRead")
	public void tagRead(HttpServletResponse response) throws Exception {
		//初始化返回值
		JSONObject json = new JSONObject();
		json.put("iRet", 0);
		json.put("sMsg", "success");
		
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("iUserId", user.getUSER_ID());
		pd.put("sUserName", user.getUSERNAME());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pd.put("dtMarkTime", format.format(new Date()));

		List<PageData> tmpList = formatNoticeId(pd);
		if(tmpList.size()==0) {
			json.put("iRet", "100322");
			json.put("sMsg", "失败，没有权限");
			out.write(json.toString());
			out.close();
			return ;
		}
		myPushService.markRead(pd);
		
		out.write(json.toString());
		out.close();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
