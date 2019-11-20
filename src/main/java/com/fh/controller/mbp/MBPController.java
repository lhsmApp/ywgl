package com.fh.controller.mbp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.BillnumUtil;
import com.fh.controller.common.DictsUtil;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.billnum.BillNumManager;
import com.fh.service.mbp.MBPManager;
import com.fh.service.mbp.ProblemTypeManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.BillNumType;
import com.fh.util.enums.ProPriority;
import com.fh.util.enums.ProState;

import net.sf.json.JSONArray;

/**
 * 问题管理模块
* @ClassName: mbpController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月8日
*
 */
@Controller
@RequestMapping(value="/mbp")
public class MBPController extends BaseController {
	
	@Resource(name="mbpService")
	private MBPManager mbpService;
	
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name = "userService")
	private UserManager userService;
	
	@Resource(name = "billnumService")
	private BillNumManager billNumService;
	
	@Resource(name="problemtypeService")
	private ProblemTypeManager problemtypeService;
	
	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mbp/problemManage/problemInfo_list");
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		PageData pd=new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String unitCode = user.getUNIT_CODE();
		String userId = user.getUSER_ID();
		String userName=user.getNAME();
		pd.put("USER_NAME", userName);
		pd.put("CURRENT_DATE", DateUtils.getCurrentTime());
		mv.addObject("pd",pd);
		mv.addObject("systemList", DictsUtil.getDictsByParentBianma(dictionariesService, "SYSTEM"));//系统类型
		PageData pdCondition=new PageData();
		pdCondition.put("UNIT_CODE", unitCode);
		mv.addObject("userList", DictsUtil.getSysUserDicByCondition(userService,pdCondition));//用户
		
		//受理人
		pd.put("KEY_CODE", "ProblemReceiverRole");
		String receiverRole = sysConfigManager.getSysConfigByKey(pd);
		PageData pdCondition1=new PageData();
		pdCondition1.put("ROLE_ID", receiverRole);
		List<PageData> receiveUserList=DictsUtil.getSysUserDicByCondition(userService,pdCondition1);//接收用户
		mv.addObject("receiveUserList",receiveUserList);
		
		//mv.addObject("proTypeList", DictsUtil.getSysUserDic(userService));//问题类型
		mv.addObject("proPriorityList", ProPriority.values());//优先级
		
		List<PageData> zproblemTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(problemtypeService.listAllProblemTypeToSelect("0",zproblemTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}
	
	/**问题提报
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/problemReport")
	public ModelAndView problemReport(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mbp/problemManage/problem_report");

		PageData pd=new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String unitCode = user.getUNIT_CODE();
		
		/*String userName=user.getNAME();
		pd.put("USER_NAME", userName);
		pd.put("CURRENT_DATE", DateUtils.getCurrentTime());*/
		mv.addObject("pd",pd);
		mv.addObject("systemList", DictsUtil.getDictsByParentBianma(dictionariesService, "SYSTEM"));//系统类型
		//上报人
		PageData pdCondition=new PageData();
		pdCondition.put("UNIT_CODE", unitCode);
		mv.addObject("userList", DictsUtil.getSysUserDicByCondition(userService,pdCondition));//用户
		mv.addObject("proPriorityList", ProPriority.values());//优先级
		
		//受理人
		pd.put("KEY_CODE", "ProblemReceiverRole");
		String receiverRole = sysConfigManager.getSysConfigByKey(pd);
		String receiverRoleIn=com.fh.controller.common.QueryFeildString.getSqlInString(receiverRole);
		PageData pdCondition1=new PageData();
		pdCondition1.put("ROLE_ID", receiverRoleIn);
		List<PageData> receiveUserList=DictsUtil.getSysUserDicByCondition(userService,pdCondition1);//接收用户
		mv.addObject("receiveUserList",receiveUserList);
		
		List<PageData> zproblemTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(problemtypeService.listAllProblemTypeToSelect("0",zproblemTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}
	
	/**问题领取
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/problemGet")
	public ModelAndView problemGet(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mbp/problemManage/problem_get");

		PageData pd=new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userName=user.getNAME();
		pd.put("USER_NAME", userName);
		pd.put("CURRENT_DATE", DateUtils.getCurrentTime());
		mv.addObject("pd",pd);
		mv.addObject("systemList", DictsUtil.getDictsByParentBianma(dictionariesService, "SYSTEM"));//系统类型
		mv.addObject("userList", DictsUtil.getSysUserDic(userService));//用户
		mv.addObject("proPriorityList", ProPriority.values());//优先级
		
		List<PageData> zproblemTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(problemtypeService.listAllProblemTypeToSelect("0",zproblemTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}
	
	/**问题分配
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/problemAssign")
	public ModelAndView problemAssign(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mbp/problemManage/problem_assign");

		PageData pd=new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userName=user.getNAME();
		pd.put("USER_NAME", userName);
		pd.put("CURRENT_DATE", DateUtils.getCurrentTime());
		mv.addObject("pd",pd);
		mv.addObject("systemList", DictsUtil.getDictsByParentBianma(dictionariesService, "SYSTEM"));//系统类型
		mv.addObject("userList", DictsUtil.getSysUserDic(userService));//用户
		mv.addObject("proPriorityList", ProPriority.values());//优先级
		
		List<PageData> zproblemTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(problemtypeService.listAllProblemTypeToSelect("0",zproblemTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		return mv;
	}
	
	/**问题回复
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/problemAnswer")
	public ModelAndView problemAnswer(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mbp/problemManage/problem_answer");

		/*PageData pd=new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userName=user.getNAME();
		pd.put("USER_NAME", userName);
		pd.put("CURRENT_DATE", DateUtils.getCurrentTime());
		mv.addObject("pd",pd);
		mv.addObject("systemList", DictsUtil.getDictsByParentBianma(dictionariesService, "SYSTEM"));//系统类型
		mv.addObject("userList", DictsUtil.getSysUserDic(userService));//用户
		mv.addObject("proPriorityList", ProPriority.values());//优先级
		
		List<PageData> zproblemTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(problemtypeService.listAllProblemTypeToSelect("0",zproblemTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));*/
		return mv;
	}
	
	/**问题关闭
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/problemClose")
	public ModelAndView problemClose(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("mbp/problemManage/problem_close");

		/*PageData pd=new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userName=user.getNAME();
		pd.put("USER_NAME", userName);
		pd.put("CURRENT_DATE", DateUtils.getCurrentTime());
		mv.addObject("pd",pd);
		mv.addObject("systemList", DictsUtil.getDictsByParentBianma(dictionariesService, "SYSTEM"));//系统类型
		mv.addObject("userList", DictsUtil.getSysUserDic(userService));//用户
		mv.addObject("proPriorityList", ProPriority.values());//优先级
		
		List<PageData> zproblemTypePdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(problemtypeService.listAllProblemTypeToSelect("0",zproblemTypePdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));*/
		return mv;
	}
	
	/**查询问题信息
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/queryProblemInfo")
	public ModelAndView queryProblemInfo(Page page) throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("BILL_USER", userId);
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = mbpService.list(page);
		
		
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		mv.addObject("pd", pd);
		mv.addObject("proStateList", ProState.values());//问题状态
		mv.addObject("varList", varList);//问题状态
		
		mv.setViewName("mbp/problemManage/problemInfo_query");
		return mv;
	}
	
	/**查看明细页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goView")
	public ModelAndView goView()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdResult = mbpService.findById(pd);	//根据ID读取
		mv.setViewName("mbp/problemManage/problemInfo_view");
		mv.addObject("pd", pdResult);
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listUserInfo")
	public ModelAndView listUserInfo() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("mbp/problemManage/problemInfo_list2");
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		return mv;
	}
	
	/**问题提报列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/listProblemInfo2")
	public ModelAndView listProblemInfo2() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("mbp/problemManage/problemInfo_list3");
		
		/**此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		 * 
		 * 
		 */
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	public @ResponseBody List<PageData> getPageList(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("BILL_USER", userId);
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(pd.get("ProOperType")!=null){
			String sqlFilter="";
			/*else if(pd.getString("ProOperType").equals("PROBLEM_GET")){//领取：受理人或者分配过来的受理人为当前登录人，并状态为已提交
				sqlFilter="(A.PRO_ACCEPT_USER='"+userId+"' OR problemAssign.PRO_ACCEPT_USER='"+userId+"') AND A.PRO_STATE='"+ProState.New.getNameKey()+"'";
				pd.put("PRO_ACCESS", sqlFilter);
			}*/
			
			if(pd.getString("ProOperType").equals("PROBLEM_INFO")){//提报：创建人为当前登录人，状态为所有
				sqlFilter="A.BILL_USER='"+userId+"'";
				pd.put("PRO_ACCESS", sqlFilter);
			}else if(pd.getString("ProOperType").equals("PROBLEM_GET")){//领取：受理人为当前登录人，并状态为已提交
				sqlFilter="A.PRO_ACCEPT_USER='"+userId+"' AND A.PRO_STATE='"+ProState.New.getNameKey()+"'";
				pd.put("PRO_ACCESS", sqlFilter);
			}else if(pd.getString("ProOperType").equals("PROBLEM_ASSIGN")){//分配：受理人为当前登录人，并状态为已提交或者已领取（受理中）
				sqlFilter="A.PRO_ACCEPT_USER='"+userId+"' AND (A.PRO_STATE='"+ProState.New.getNameKey()+"' OR A.PRO_STATE='"+ProState.Accept.getNameKey()+"')";
				pd.put("PRO_ACCESS", sqlFilter);
			}else if(pd.getString("ProOperType").equals("PROBLEM_ANSWER")){//受理（回复）：受理人为当前登录人，并状态为已领取（受理中）
				sqlFilter="A.PRO_ACCEPT_USER='"+userId+"' AND A.PRO_STATE='"+ProState.Accept.getNameKey()+"'";
				pd.put("PRO_ACCESS", sqlFilter);
			}else if(pd.getString("ProOperType").equals("PROBLEM_CLOSE")){//关闭：受理人为当前登录人，并状态为已提交或者已领取（受理中）
				sqlFilter="A.PRO_ACCEPT_USER='"+userId+"' AND (A.PRO_STATE='"+ProState.New.getNameKey()+"' OR A.PRO_STATE='"+ProState.Accept.getNameKey()+"')";
				pd.put("PRO_ACCESS", sqlFilter);
			}
		}
		
		
		page.setPd(pd);
		List<PageData> varList = mbpService.list(page);
		return varList;
		
		
		//int records = mbpService.count(pd);
		//PageResult<PageData> result = new PageResult<PageData>();

		//result.setRows(varList);
		//result.setRecords(records);
		//result.setPage(page.getPage());
		//return result;
	}
	
	/**获取详细信息
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getDetail")
	public @ResponseBody PageData getDetail() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pageData = mbpService.findById(pd);
		return pageData;
	}
	
	/**
	 * 保存问题提报信息
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody CommonBase save() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);

		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		if(pd.getString("PRO_CODE")==null||pd.getString("PRO_CODE").trim().equals("")){
			
			String billCode=BillnumUtil.getBillnum(billNumService, BillNumType.RPOBLEM, pd.getString("PRO_DEPART"), "");
			pd.put("PRO_CODE", billCode);
			String userId = user.getUSER_ID();
			pd.put("BILL_USER", userId);
			pd.put("PRO_DEPART", user.getDEPARTMENT_ID());
			pd.put("BILL_DATE", DateUtils.getCurrentTime());
			pd.put("UPDATE_DATE", DateUtils.getCurrentTime());
			
			mbpService.save(pd);
			commonBase.setCode(0);
		}
		else{
			pd.put("UPDATE_DATE", DateUtils.getCurrentTime());
			pd.put("PRO_DEPART", user.getDEPARTMENT_ID());
			mbpService.edit(pd);
			commonBase.setCode(0);
		}
		/*String [] ids=pd.getString("id").split(",");
		if(ids.length==1)
			mbpService.delete(pd);
		else{
			mbpService.deleteAll(ids);
			commonBase.setCode(0);
		}*/
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		
		if(commonBase.getCode()==0){
			BillnumUtil.updateBillnum(billNumService, BillNumType.RPOBLEM);
		}
		return commonBase;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody CommonBase delete() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		mbpService.delete(pd);
		commonBase.setCode(0);
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题提交
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/commit")
	public @ResponseBody CommonBase commit() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRO_STATE", ProState.New.getNameKey());
		pd.put("UPDATE_DATE", DateUtils.getCurrentTime());
		mbpService.commit(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"发起问题");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题取消提交
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/cancel")
	public @ResponseBody CommonBase cancel() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRO_STATE", ProState.TempSave.getNameKey());
		pd.put("UPDATE_DATE", DateUtils.getCurrentTime());
		mbpService.cancel(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"问题撤回");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题领取
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/proGet")
	public @ResponseBody CommonBase proGet() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRO_STATE", ProState.Accept.getNameKey());
		pd.put("UPDATE_DATE", DateUtils.getCurrentTime());
		mbpService.proGet(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"问题领取");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题领取取消
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/proGetCancel")
	public @ResponseBody CommonBase proGetCancel() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRO_STATE", ProState.New.getNameKey());
		pd.put("UPDATE_DATE", DateUtils.getCurrentTime());
		mbpService.proGet(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"问题领取取消");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题分配
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/addAssign")
	public @ResponseBody CommonBase addAssign() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("BILL_USER", userId);
		pd.put("BILL_DATE", DateUtils.getCurrentTime());
		mbpService.addAssign(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"问题分配");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题分配取消
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAssign")
	public @ResponseBody CommonBase deleteAssign() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		mbpService.deleteAssign(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"问题分配取消");
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getProAnswers")
	public @ResponseBody List<PageData> getProAnswers() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> listPageData = mbpService.getProAnswers(pd);
		return listPageData;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getAnswerContent")
	public @ResponseBody PageData getAnswerContent() throws Exception{
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pageData = mbpService.getAnswerContent(pd);
		return pageData;
	}
	
	
	/**问题回复
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/addAnswer")
	public @ResponseBody CommonBase addAnswer() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("BILL_USER", userId);
		pd.put("BILL_DATE", DateUtils.getCurrentTime());
		
		if(StringUtil.isEmpty(pd.getString("ANSWER_ID"))){
			mbpService.addAnswer(pd);
			commonBase.setCode(0);
		}
		else{
			mbpService.updateAnswer(pd);
			commonBase.setCode(0);
		}
		
		addLog(pd.getString("PRO_CODE"),"问题回复");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题回复作废
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAnswer")
	public @ResponseBody CommonBase deleteAnswer() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		mbpService.deleteAnswer(pd);
		commonBase.setCode(0);
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题关闭
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/addClose")
	public @ResponseBody CommonBase addClose() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("BILL_USER", userId);
		pd.put("BILL_DATE", DateUtils.getCurrentTime());
		pd.put("PRO_STATE", ProState.Closed.getNameKey());
		mbpService.addClose(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"关闭问题");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题关闭取消
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteClose")
	public @ResponseBody CommonBase deleteClose() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRO_STATE", ProState.Accept.getNameKey());
		mbpService.deleteClose(pd);
		commonBase.setCode(0);
		
		addLog(pd.getString("PRO_CODE"),"取消关闭问题");
		
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
	
	/**问题日志列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/getProLog")
	public @ResponseBody List<PageData> getProLog() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = mbpService.getProLog(pd);
		return varList;
	}
	
	/**
	 * 增加日志
	 * @param proEvent
	 */
	private void addLog(String proCode,String proEvent) throws Exception{
		PageData pd=new PageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String userId = user.getUSER_ID();
		pd.put("PRO_CODE", proCode);
		pd.put("CREATE_USER", userId);
		pd.put("CREATE_DATE", DateUtils.getCurrentTime());
		pd.put("PRO_EVENT", proEvent);
		
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("CLIENT_IP", ip);
		mbpService.addLog(pd);
	}
		
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("CategoryName");	//1
		titles.add("ProductName");	//2
		titles.add("Country");	//3
		titles.add("Price");	//4
		titles.add("Quantity");	//5
		dataMap.put("titles", titles);
		List<PageData> varOList = mbpService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("CATEGORYNAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("PRODUCTNAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("COUNTRY"));	    //3
			vpd.put("var4", varOList.get(i).getString("PRICE"));	    //4
			vpd.put("var5", varOList.get(i).get("QUANTITY").toString());	//5
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
