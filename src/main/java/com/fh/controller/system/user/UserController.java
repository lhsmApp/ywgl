package com.fh.controller.system.user;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.trainBase.TrainStudentController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.trainBase.TrainStudentManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.GetPinyin;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead;
import com.fh.util.PageData;
import com.fh.util.ObjectExcelView;
import com.fh.util.PathUtil;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.fh.util.base.ConvertUtils;

import cn.jpush.http.StringUtils;
import net.sf.json.JSONArray;

/**
 * 用户管理
* @ClassName: UserController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	String menuUrl = "user/listUsers.do"; //菜单地址(权限用)
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	@Resource(name="trainstudentService")
	private TrainStudentManager trainstudentService;
	
	@Resource(name = "sysconfigService")
	private SysConfigManager sysconfigService;
	
	/**显示用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		} 
		
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userName = user.getUSERNAME();
		String unitCode=user.getUNIT_CODE();	
		
		if(userName.equals("admin")){
			pd.put("UNIT_CODE", pd.getString("DEPARTMENT_ID"));
		}else{
			Map<String, String> mapRole=Jurisdiction.getHC();//角色权限
			if(ConvertUtils.strToInt(mapRole.get("adds"),0)>=3){
				pd.put("UNIT_CODE", "");
			}else{
				pd.put("UNIT_CODE", unitCode);
			}
		}
		
		page.setPd(pd);
		List<PageData>	userList = userService.listUsers(page);	//列出用户列表
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		
		//列表页面树形下拉框用(保持下拉树里面的数据不变)
		String ZDEPARTMENT_ID = pd.getString("ZDEPARTMENT_ID");
		pd.put("DEPARTMENT_CODE", pd.getString("DEPARTMENT_ID"));
		PageData dpd = departmentService.findByBianma(pd);
		if(null != dpd){
			ZDEPARTMENT_ID = dpd.getString("NAME");
		}
		mv.addObject("depname", ZDEPARTMENT_ID);
		
		
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		//mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("userName", Jurisdiction.getUsername());
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}
	
	/**删除用户
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		userService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除系统用户："+pd);
		out.write("success");
		out.close();
	}
	
	/**去新增用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userUnit=user.getUNIT_CODE();
		pd.put("UNIT_CODE", userUnit);
		
		Map<String, String> mapRole=Jurisdiction.getHC();//角色权限
		pd.put("ROLE_ID", "1");
		pd.put("ADD_QX", mapRole.get("adds"));
		
		List<Role> roleList = roleService.listAllRolesByCurrentID(pd);//列出所有系统用户角色
		mv.setViewName("system/user/user_edit");
		
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		
		pd.put("DEPARTMENT_CODE", pd.getString("UNIT_CODE"));
		PageData pdDepartResultUnit=departmentService.findByBianma(pd);
		//pd.put("DEPARTMENT_ID", pdDepartResult.getString("DEPARTMENT_ID")); //根据编码读取ID
		if(pdDepartResultUnit!=null)
			mv.addObject("depnameUnit", pdDepartResultUnit.getString("NAME"));
		else
			mv.addObject("depnameUnit", null);
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}
	
	/**保存用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增user");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取系统配置中的学员id
		pd.put("KEY_CODE", "studentRoldId");
		String studentRoldId = sysconfigService.getSysConfigByKey(pd);
		
		//pd.put("USER_ID", this.get32UUID());	//ID 主键
		pd.put("LAST_LOGIN", "");				//最后登录时间
		pd.put("IP", "");						//IP
		pd.put("STATUS", "1");					//状态
		pd.put("SKIN", "default");
		pd.put("RIGHTS", "");
		String PW = pd.getString("PASSWORD");
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), PW).toString());	//密码加密
		
		if(null == userService.findByUsername(pd)){	//判断用户名是否存在
			userService.saveU(pd); 					//执行保存
			FHLOG.save(Jurisdiction.getUsername(), "新增系统用户："+pd.getString("USERNAME"));
			//学员
			PageData pd2 = new PageData();
			pd2.put("DEPART_CODE", pd.getString("DEPARTMENT_ID"));
			pd2.put("UNIT_CODE", pd.getString("UNIT_CODE"));
			pd2.put("ACCOUNT", pd.getString("USERNAME"));
			pd2.put("STATE", 1);
			pd2.put("STUDENT_CODE", pd.getString("USERNAME"));
			pd2.put("PASSWORD", new SimpleHash("SHA-1", pd.get("STUDENT_CODE")+"", PW).toString());	//密码加密
			pd2.put("MEMO", "");
			pd2.put("STUDENT_NAME", pd.getString("NAME"));
			pd2.put("STUDENT_ID", pd.getString("USERNAME"));
			pd2.put("ROLE_ID", studentRoldId);
			
			trainstudentService.save(pd2);
			
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 批量添加用户
	 * @throws Exception
	 * @author yijche
	 */
	@RequestMapping(value="/batchAddUser")
	public void batchAddUser() throws Exception{
		PageData pd = new PageData();
		//获取系统配置中的学员id
		pd.put("KEY_CODE", "studentRoldId");
		String studentRoldId = sysconfigService.getSysConfigByKey(pd);
		for(int i=0;i<NewUser.newUserStr.length;i++) {
			pd.clear();
			/***************************/
			pd.put("USERNAME", NewUser.newUserStr[i][0]);
			String PW = NewUser.newUserStr[i][1];
			pd.put("PASSWORD", PW);
			pd.put("NAME", NewUser.newUserStr[i][2]);
			pd.put("ROLE_ID", NewUser.newUserStr[i][3]);
			pd.put("UNIT_CODE", NewUser.newUserStr[i][4]);
			pd.put("DEPARTMENT_ID", NewUser.newUserStr[i][5]);
			pd.put("PHONE", NewUser.newUserStr[i][6]);
			/***************************/
			pd.put("LAST_LOGIN", "");				//最后登录时间
			pd.put("IP", "");						//IP
			pd.put("STATUS", "1");					//状态
			pd.put("SKIN", "default");
			pd.put("RIGHTS", "");		
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), PW).toString());	//密码加密
			
			if(null == userService.findByUsername(pd)){	//判断用户名是否存在
				userService.saveU(pd); 					//执行保存
				//学员
				PageData pd2 = new PageData();
				pd2.put("DEPART_CODE", pd.getString("DEPARTMENT_ID"));
				pd2.put("UNIT_CODE", pd.getString("UNIT_CODE"));
				pd2.put("ACCOUNT", pd.getString("USERNAME"));
				pd2.put("STATE", 1);
				pd2.put("STUDENT_CODE", pd.getString("USERNAME"));
				pd2.put("PASSWORD", new SimpleHash("SHA-1", pd.get("STUDENT_CODE")+"", PW).toString());	//密码加密
				pd2.put("MEMO", "");
				pd2.put("STUDENT_NAME", pd.getString("NAME"));
				pd2.put("STUDENT_ID", pd.getString("USERNAME"));
				pd2.put("ROLE_ID", studentRoldId);
				
				trainstudentService.save(pd2);
			}
		}
	}
	
	/**判断用户名是否存在
	 * @return
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUsername(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断邮箱是否存在
	 * @return
	 */
	@RequestMapping(value="/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUE(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUN(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**去修改用户页面(系统用户列表修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if("1".equals(pd.getString("USER_ID"))){return null;}		//不能修改admin用户
		
		Map<String, String> mapRole=Jurisdiction.getHC();//角色权限
		pd.put("ROLE_ID", "1");
		pd.put("ADD_QX", mapRole.get("adds"));
		List<Role> roleList = roleService.listAllRolesByCurrentID(pd);	//列出所有系统用户角色
		mv.addObject("fx", "user");
		pd = userService.findById(pd);
		pd.put("ADD_QX", mapRole.get("adds"));
		/*String departBianma=pd.getString("DEPARTMENT_ID");//对DEPARTMENT_ID还原本人DEPARTMENT_ID
		PageData pdDepart=new PageData();
		pdDepart.put("BIANMA", departBianma);*/
		
		pd.put("DEPARTMENT_CODE", pd.getString("UNIT_CODE"));
		PageData pdDepartResultUnit=departmentService.findByBianma(pd);
		//pd.put("DEPARTMENT_ID", pdDepartResult.getString("DEPARTMENT_ID")); //根据编码读取ID
		mv.setViewName("system/user/user_edit");
		if(pdDepartResultUnit!=null)
			mv.addObject("depnameUnit", pdDepartResultUnit.getString("NAME"));
		else
			mv.addObject("depnameUnit", null);
		
		pd.put("DEPARTMENT_CODE", pd.getString("DEPARTMENT_ID"));
		PageData pdDepartResult=departmentService.findByBianma(pd);
		//pd.put("DEPARTMENT_ID", pdDepartResult.getString("DEPARTMENT_ID")); //根据编码读取ID
		mv.setViewName("system/user/user_edit");
		if(pdDepartResult!=null)
			mv.addObject("depname", pdDepartResult.getString("NAME"));
		else
			mv.addObject("depname", null);
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect("0",zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}
	
	/**去修改用户页面(个人修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditMyU")
	public ModelAndView goEditMyU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd.put("USERNAME", Jurisdiction.getUsername());
		pd = userService.findByUsername(pd);						//根据用户名读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**去修改用户页面(个人修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditMy")
	public ModelAndView goEditMy() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERNAME", Jurisdiction.getUsername());
		
		HttpSession session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); // 读取session中的用户信息(单独用户信息)
		if (user != null) {
			if(user.getUSER_TYPE().equals("1")){
				pd = userService.findByUsername(pd);	
				mv.setViewName("system/user/user_edit_my");//根据用户名读取
				mv.addObject("msg", "editUMy");
				mv.addObject("pd", pd);
			}else{
				pd = userService.findByStudentCode(pd);	
				mv.setViewName("system/user/student_edit_my");
				mv.addObject("msg", "editStudentMy");
				mv.addObject("pd", pd);
			}
		}
							
		
		return mv;
	}
	
	/**查看用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if("admin".equals(pd.getString("USERNAME"))){return null;}	//不能查看admin用户
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd = userService.findByUsername(pd);						//根据ID读取
		mv.setViewName("system/user/user_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**去修改用户页面(在线管理页面打开)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditUfromOnline")
	public ModelAndView goEditUfromOnline() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if("admin".equals(pd.getString("USERNAME"))){return null;}	//不能查看admin用户
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd = userService.findByUsername(pd);						//根据ID读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ser");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(!Jurisdiction.getUsername().equals(pd.getString("USERNAME"))){		//如果当前登录用户修改用户资料提交的用户名非本人
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}  //校验权限 判断当前操作者有无用户管理查看权限
			//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限判断当前操作者有无用户管理修改权限
			if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改admin
		}
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editU(pd);	//执行修改
		FHLOG.save(Jurisdiction.getUsername(), "修改系统用户："+pd.getString("USERNAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改个人用户
	 */
	@RequestMapping(value="/editUMy")
	public ModelAndView editUMy() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改个人信息");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editUMy(pd);//执行修改
		FHLOG.save(Jurisdiction.getUsername(), "修改个人用户："+pd.getString("USERNAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改学员用户
	 */
	@RequestMapping(value="/editStudentMy")
	public ModelAndView editStudentMy() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改学员个人信息");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editStudentMy(pd);//执行修改
		FHLOG.save(Jurisdiction.getUsername(), "修改学员个人用户："+pd.getString("USERNAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 批量删除
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量删除user");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除user");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if(null != USER_IDS && !"".equals(USER_IDS)){
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			userService.deleteAllU(ArrayUSER_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**导出用户信息到EXCEL
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "导出用户信息到EXCEL");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
				String keywords = pd.getString("keywords");				//关键词检索条件
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
				String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
				if(lastLoginStart != null && !"".equals(lastLoginStart)){
					pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
				}
				if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
					pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
				} 
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("用户名"); 		//1
				titles.add("编号");  		//2
				titles.add("姓名");			//3
				titles.add("职位");			//4
				titles.add("手机");			//5
				titles.add("邮箱");			//6
				titles.add("最近登录");		//7
				titles.add("上次登录IP");	//8
				dataMap.put("titles", titles);
				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<userList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("USERNAME"));		//1
					vpd.put("var2", userList.get(i).getString("NUMBER"));		//2
					vpd.put("var3", userList.get(i).getString("NAME"));			//3
					vpd.put("var4", userList.get(i).getString("ROLE_NAME"));	//4
					vpd.put("var5", userList.get(i).getString("PHONE"));		//5
					vpd.put("var6", userList.get(i).getString("EMAIL"));		//6
					vpd.put("var7", userList.get(i).getString("LAST_LOGIN"));	//7
					vpd.put("var8", userList.get(i).getString("IP"));			//8
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
			//}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			pd.put("RIGHTS", "");					//权限
			pd.put("LAST_LOGIN", "");				//最后登录时间
			pd.put("IP", "");						//IP
			pd.put("STATUS", "0");					//状态
			pd.put("SKIN", "default");				//默认皮肤
			pd.put("ROLE_ID", "1");
			List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());		//设置角色ID为随便第一个
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			for(int i=0;i<listPd.size();i++){		
				//pd.put("USER_ID", this.get32UUID());										//ID
				pd.put("NAME", listPd.get(i).getString("var1"));							//姓名
				
				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"));	//根据姓名汉字生成全拼
				pd.put("USERNAME", USERNAME);	
				if(userService.findByUsername(pd) != null){									//判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"))+Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				pd.put("BZ", listPd.get(i).getString("var4"));								//备注
				if(Tools.checkEmail(listPd.get(i).getString("var3"))){						//邮箱格式不对就跳过
					pd.put("EMAIL", listPd.get(i).getString("var3"));						
					if(userService.findByUE(pd) != null){									//邮箱已存在就跳过
						continue;
					}
				}else{
					continue;
				}
				pd.put("NUMBER", listPd.get(i).getString("var0"));							//编号已存在就跳过
				pd.put("PHONE", listPd.get(i).getString("var2"));							//手机号
				
				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString());	//默认密码123
				if(userService.findByUN(pd) != null){
					continue;
				}
				userService.saveU(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**显示用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listUsersForWindow")
	public ModelAndView listUsersForWindow(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	userList = userService.listUsersBystaff(page);	//列出用户列表(弹窗选择用)
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		mv.setViewName("system/user/window_user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

}
