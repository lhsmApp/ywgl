package com.fh.controller.busidate;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.service.fhdb.timingbackup.TimingBackUpManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.tmplconfig.tmplconfig.TmplConfigManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.QuartzManager;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;

/**
 * 期间设置
 * 
 * @ClassName: SysConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年6月29日
 *
 */
@Controller
@RequestMapping(value = "/busidate")
public class BusidateController extends BaseController {

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;

	@Resource(name = "timingbackupService")
	private TimingBackUpManager timingbackupService;

	@Resource(name = "tmplconfigService")
	private TmplConfigManager tmplconfigService;

	private static String JOB_GROUP_NAME = "BUSIDATE_JOBGROUP_NAME"; // 任务组
	private static String TRIGGER_GROUP_NAME = "BUSIDATE_TRIGGERGROUP_NAME"; // 触发器组

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("busidate/busidate_edit");
		// 此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		// 设置期间
		PageData pd = new PageData();
		pd.put("KEY_CODE", "SystemDataTime");
		String busiDate = sysConfigManager.getSysConfigByKey(pd);
		mv.addObject("BUSI_DATE", busiDate);

		pd.put("TIMINGBACKUP_ID", "1");
		PageData pdJob = timingbackupService.findById(pd);
		mv.addObject("JOB_INFO", pdJob);
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = sysConfigManager.listAll(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		return result;
	}

	/**
	 * 设置业务期间并生成模板
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveBusidate")
	public @ResponseBody CommonBase saveBusidate() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername() + "修改SysConfig");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// String busiDate=pd.getString("BUSI_DATE");
		// 设置期间
		PageData pdConfig = new PageData();
		pdConfig.put("KEY_CODE", "SystemDataTime");
		String busiDate = sysConfigManager.getSysConfigByKey(pdConfig);
		Date dtCur = DateUtils.string2Date(busiDate);
		String nextBusidate = DateUtils.addMothToDate(1, dtCur, DateFormatUtils.DATE_MONTH_FORMAT);
		
		PageData pd = this.getPageData();
		pd.put("KEY_VALUE", nextBusidate);
		pd.put("NEXT_RPT_DUR", nextBusidate);
		pd.put("CUR_RPT_DUR", busiDate);

		String hasTmpl = tmplconfigService.findByRptDur(nextBusidate);
		if (StringUtil.isEmpty(hasTmpl)) {
			pd.put("CopyRptDur", true);
		}
		String hasTmplStruMapping = tmplconfigService.findStruMappingByRptDurSpecial(nextBusidate);
		if (StringUtil.isEmpty(hasTmplStruMapping)) {
			pd.put("CopyStruMapping", true);
		}
		
		String hasTmplTableMapping = tmplconfigService.findTableMappingByRptDurSpecial(nextBusidate);
		if (StringUtil.isEmpty(hasTmplTableMapping)) {
			pd.put("CopyTableMapping", true);
		}
		
		String hasCertParm = tmplconfigService.findCertParmByRptDurSpecial(nextBusidate);
		if (StringUtil.isEmpty(hasCertParm)) {
			pd.put("CopyCertParm", true);
		}
		String hasGlItemUser = tmplconfigService.findGlItemUser(nextBusidate);
		if (StringUtil.isEmpty(hasGlItemUser)) {
			pd.put("CopyGlItemUser", true);
		}
		String hasStaffTds = tmplconfigService.findStaffTds(nextBusidate);
		if (StringUtil.isEmpty(hasStaffTds)) {
			pd.put("CopyStaffTds", true);
		}
		String hasStaffRemit = tmplconfigService.findStaffRemit(nextBusidate);
		if (StringUtil.isEmpty(hasStaffRemit)) {
			pd.put("CopyStaffRemit", true);
		}
		tmplconfigService.updateBusidate(pd);// 变更业务区间
		
		commonBase.setCode(0);
		commonBase.setMessage(nextBusidate);

		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}

	/**
	 * 设置往期业务期间
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/pastBusidate")
	public @ResponseBody CommonBase pastBusidate() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername() + "修改SysConfig");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		PageData pd = this.getPageData();
		String busiDate = pd.getString("BUSI_DATE");

		pd.put("KEY_VALUE", busiDate);

		tmplconfigService.updateBusidate(pd);
		commonBase.setCode(0);

		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}

	/**
	 * 保存任务设置
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveJob")
	public @ResponseBody CommonBase saveJob() throws Exception {
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername() + "saveJob");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// //校验权限
		PageData pd = this.getPageData();
		String JOBNAME = "设置业务期间"; // 任务名称
		pd.put("JOBNAME", JOBNAME); // 任务名称
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 创建时间
		pd.put("STATUS", "2"); // 默认关闭状态
		timingbackupService.edit(pd);
		commonBase.setCode(0);
		/**
		 * 此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		// commonBase.setCode(-1);
		// commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}

	/**
	 * 切换状态
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeStatus")
	public @ResponseBody CommonBase changeStatus() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "切换状态");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		// //校验权限
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		int status = Integer.parseInt(pd.get("STATUS").toString());
		pd = timingbackupService.findById(pd);// 根据ID读取
		String jobName = pd.getString("JOBNAME");// 任务名称
		String jobTime = pd.getString("FHTIME");// 时间规则
		if (status == 2) {
			pd.put("STATUS", 2);
			QuartzManager.removeJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME);
		} else {
			pd.put("STATUS", 1);
			// Map<String, Object> parameter = new HashMap<String, Object>();
			// parameter.put("BUSIDATE", nextBusidate);
			QuartzManager.addJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, BusidateJob.class, jobTime);
		}
		timingbackupService.changeStatus(pd);
		commonBase.setCode(status);
		return commonBase;
	}
}
