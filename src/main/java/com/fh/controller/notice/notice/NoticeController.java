package com.fh.controller.notice.notice;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.myPush.myPush.MyPushManager;
import com.fh.service.notice.notice.NoticeDetailManager;
import com.fh.service.notice.notice.NoticeItemManager;
import com.fh.service.notice.notice.NoticeManager;
import com.fh.service.trainBase.CourseTypeManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

import net.sf.json.JSONArray;

/**
 * 说明：信息通知处理类 创建人：jiachao 创建时间：2019-12-02
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController extends BaseController {

	String menuUrl = "notice/list.do"; // 菜单地址(权限用)
	@Resource(name = "noticeService")
	private NoticeManager noticeService;

	@Resource(name = "coursetypeService")
	private CourseTypeManager coursetypeService;

	@Resource(name = "noticeDetailService")
	private NoticeDetailManager noticeDetailService;

	@Resource(name = "noticeItemService")
	private NoticeItemManager noticeItemService;

	@Resource(name = "departmentService")
	private DepartmentManager departmentService;

	@Resource(name = "myPushService")
	private MyPushManager myPushService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/save")
	public void save(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Notice");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		// //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		String NOTICE_CONTENT = pd.get("NOTICE_CONTENT").toString();
		if (NOTICE_CONTENT.length() > 100) {
			out.write("{\"ret\":\"-1\",\"sMsg\":\"NOTICE_CONTENT too lang\"}");
			out.close();
			return;
		}

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("NOTICE_USER", user.getUSER_ID());
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		pd.put("CREATE_DATE", date);
		pd.put("USER_DEPART", user.getUNIT_CODE());
		pd.put("CREATE_USER", user.getUSER_ID());
		pd.put("STATE", 1);

		if (isNumericzidai(pd.get("NOTICE_ID").toString())) {
			noticeService.edit(pd);
		} else {
			noticeService.save(pd);// 向tb_notice表中插入一条数据
		}
		Object scope_Arr;
		List<PageData> detailArray = new ArrayList<PageData>();
		List<PageData> itemArray = new ArrayList<PageData>();

		// 如果发布范围是系统内所有用户，先获取全部用户信息
		if (pd.get("NOTICE_TYPE").equals("0")) {
			List<PageData> volist = noticeService.getAllSysUserList(pd);
			String str = "[";
			for (int i = 0; i < volist.size(); i++) {
				str += "{\"id\":\"" + volist.get(i).get("USER_ID") + "\",\"name\":\"" + volist.get(i).get("NAME")
						+ "\"},";
			}
			str = str.substring(0, str.length() - 1) + "]";
			scope_Arr = str;
		} else {
			scope_Arr = pd.get("scope_Arr");
		}
		JSONArray json = JSONArray.fromObject(scope_Arr);
		Object nId = isNumericzidai(pd.get("NOTICE_ID").toString()) ? pd.get("NOTICE_ID") : pd.get("id");

		List<String> scoList = new ArrayList<String>();
		for (int i = 0; i < json.size(); i++) {
			net.sf.json.JSONObject job = json.getJSONObject(i);
			PageData p = new PageData();
			p.put("NOTICE_ID", nId);
			p.put("USER_ID", job.get("id"));
			scoList.add(job.get("id").toString());
			p.put("STATE", 0);
			detailArray.add(p);
		}
		String scoStr = StringUtils.join(scoList, ",");
		if (!pd.get("NOTICE_TYPE").equals("0")) {
			for (int i = 0; i < json.size(); i++) {
				net.sf.json.JSONObject job = json.getJSONObject(i);
				PageData p = new PageData();
				p.put("NOTICE_ID", nId);
				p.put("ITEM_ID", i);
				p.put("BUSINESS_CODE", job.get("id"));
				p.put("BUSINESS_NAME", job.get("name"));
				itemArray.add(p);
			}
		}

		pd.put("varList", detailArray);
		pd.put("varList2", itemArray);
		if (isNumericzidai(pd.get("NOTICE_ID").toString())) {
			if (detailArray.size() > 0) {
				noticeDetailService.delete(pd);
				noticeDetailService.saveAll(pd);
			}
			if (itemArray.size() > 0) {
				noticeItemService.delete(pd);
				noticeItemService.saveAll(pd);
			}
		} else {
			noticeDetailService.saveAll(pd);
			if (!pd.get("NOTICE_TYPE").equals("0")) {
				noticeItemService.saveAll(pd);
			}
		}

		out.write("{\"ret\":\"0\",\"sMsg\":\"success\"}");
		out.close();
		
		PageData pd2 = new PageData();
		if (isNumericzidai(pd.get("NOTICE_ID").toString())) {
			pd2.put("iModuleId", 243);
			pd2.put("iModuleSubId", pd.get("NOTICE_ID").toString());
			pd2.put("iForkId", 1);
			pd2.put("iGroupId", "1");
			pd2.put("doCleanMark", "1");
			pd2.put("doSend", "1");
			if (pd.getString("NOTICE_TYPE").equals("0")) {
				pd2.put("iGroupId", "0");
			} else {
				pd2.put("UserList", scoStr);
			}
			pd2.put("sDetails", pd.getString("NOTICE_CONTENT"));
			pd2.put("dtBeginTime", pd.getString("START_TIME"));
			pd2.put("dtOverTime", pd.getString("END_TIME"));
			pd2.put("sCanClickUrl", "notice/list.do?NOTICE_CONTENT="+pd.getString("NOTICE_CONTENT"));
			com.alibaba.fastjson.JSONObject json2 = myPushService.editSend(pd2);// 新建成功后推送消息 用json格式
			System.out.println(json2);// test
		}else {
			// 新建成功后推送消息
			pd2.put("iModuleId", 243);
			pd2.put("iModuleSubId", nId);
			pd2.put("iForkId", 1);
			pd2.put("sDetails", pd.getString("NOTICE_CONTENT"));
			if (pd.getString("NOTICE_TYPE").equals("0")) {
				pd2.put("iGroupId", "0");
			} else {
				pd2.put("UserList", scoStr);
			}
			pd2.put("dtBeginTime", pd.getString("START_TIME"));
			pd2.put("dtOverTime", pd.getString("END_TIME"));
			pd2.put("sCanClickUrl", "notice/list.do?NOTICE_CONTENT="+pd.getString("NOTICE_CONTENT"));
			pd2.put("iIsForward", "1");
		}
		

		com.alibaba.fastjson.JSONObject json2 = myPushService.saveSend(pd2);// 新建成功后推送消息 用json格式
		System.out.println(json2);// test
	}

	private boolean isNumericzidai(String str) throws Exception {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Notice");
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {return;} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		String NOTICE_ID = pd.getString("NOTICE_ID");
		if (null != NOTICE_ID && !"".equals(NOTICE_ID)) {
			pd.put("NOTICE_ID", NOTICE_ID);
		}
		noticeService.delete(pd);
		noticeDetailService.delete(pd);
		noticeItemService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改Notice");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		noticeService.edit(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public ModelAndView listComm(Page page, int accessSourceType) throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String NOTICE_CONTENT = pd.getString("NOTICE_CONTENT");
		if (null != NOTICE_CONTENT && !"".equals(NOTICE_CONTENT)) {
			pd.put("NOTICE_CONTENT", NOTICE_CONTENT.trim());
		}
		String NAME = pd.getString("NAME");
		if (null != NAME && !"".equals(NAME)) {
			pd.put("NOTICE_USER", NAME.trim());
		}
		String lastStart = pd.getString("lastStart");
		if (null != lastStart && !"".equals(lastStart)) {
			pd.put("lastStart", lastStart.trim());
		}
		String lastEnd = pd.getString("lastEnd");
		if (null != lastEnd && !"".equals(lastEnd)) {
			pd.put("lastEnd", lastEnd.trim());
		}

		page.setPd(pd);
		List<PageData> varList = noticeService.list(page); // 列出Notice列表
		mv.setViewName("notice/notice/notice_list");
		mv.addObject("varList", varList);
		mv.addObject("accessSourceType", accessSourceType);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 管理员 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Notice1");
		return this.listComm(page, 2);
	}

	/**
	 * 用户 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list2")
	public ModelAndView list2(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Notice2");
		return this.listComm(page, 1);
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("notice/notice/notice_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData repd = noticeService.findById(pd); // 根据ID读取

		if (null != repd && !repd.get("NOTICE_TYPE").equals("0")) {
			List<PageData> reItem = noticeItemService.findById(pd);
			mv.addObject("reItem", reItem);
		}
		mv.setViewName("notice/notice/notice_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", repd);

		return mv;
	}

	/**
	 * 去选择发布范围
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goSelectScope")
	public ModelAndView goSelectScope(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = noticeService.showSysUserList(page); // 列出角色列表

		List<PageData> listPara = departmentService.getAllNameAndId(page);
		HashMap<String, String> hashMap = new HashMap<String, String>();

		for (int i = 0; i < listPara.size(); i++) {
			hashMap.put((String) listPara.get(i).get("DEPARTMENT_CODE"), (String) listPara.get(i).get("NAME"));
		}
		for (int j = 0; j < varList.size(); j++) {
			varList.get(j).put("DEPARTMENT_ID", hashMap.get(varList.get(j).get("DEPARTMENT_ID")));
			varList.get(j).put("UNIT_CODE", hashMap.get(varList.get(j).get("UNIT_CODE")));
		}
		// List<PageData> varList = noticeService.showSysRoleList(page); // 列出角色列表
		mv.setViewName("notice/notice/select_scope/user");
		// mv.setViewName("notice/notice/select_scope/role");
		// List<PageData> courseTypePdList = new ArrayList<PageData>();
		// JSONArray arr =
		// JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0",
		// courseTypePdList));
		// mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Notice");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			noticeService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 查询是否有我的消息
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyNotice")
	public void getMyNotice(PrintWriter out) throws Exception {
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		// //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("USER_ID", user.getUSER_ID());
		List<PageData> retData = noticeService.getMyNotice(pd);
		String json = JSON.toJSONString(retData);
		out.write("{\"sMsg\":\"success\",\"retData\":" + URLEncoder.encode(json, "UTF-8") + "}");
		out.close();
	}

	/**
	 * 下载
	 * 
	 * /System/Volumes/Data/Users/lhsm/Documents/work_CYJ/code/eclipseWorkSpace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/ywgl/uploadFiles/uploadVideo/a2c390c363d94fe5b45834dd8d8d3417.mov
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download")
	public void downExcel(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String fileName = pd.getString("address").trim();
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILEOA + fileName,
				pd.getString("address"));
	}

	/**
	 * 附件
	 * 
	 * @param pic
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile")
	public void uploadPic(MultipartFile video, HttpServletResponse response) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "上传");
		String filePath = PathUtil.getClasspath() + Const.FILEPATHFILEOA;
		String fileName = FileUpload.fileUp(video, filePath, this.get32UUID());
		System.out.println(filePath + fileName);
		JSONObject json = new JSONObject();
		// 回调文件路径
		json.put("path", fileName);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 消息提醒，记录用户已读情况
	 */
	@RequestMapping(value = "/tagRead")
	public void tagRead(PrintWriter out) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("USER_ID", user.getUSER_ID());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pd.put("CHECK_TIME", format.format(new Date()));
		pd.put("STATE", 1);

		noticeDetailService.edit(pd);
		out.write("{\"sMsg\":\"success\"}");
		out.close();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
