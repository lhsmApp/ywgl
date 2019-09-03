package com.fh.controller.busidate;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fh.controller.base.BaseController;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.tmplconfig.tmplconfig.TmplConfigManager;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;

/**
 * 定时任务调度 数据库自动备份工作域
 * 
 * @ClassName: DbBackupQuartzJob
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年7月25日
 *
 */
public class BusidateJob extends BaseController implements Job {

	@Override
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub

		/*
		 * JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		 * Map<String, Object> parameter = (Map<String, Object>)
		 * dataMap.get("parameterList"); // 获取参数 String busidate =
		 * parameter.get("BUSIDATE").toString();
		 */
		try {
			// 普通类从spring容器中拿出service
			WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();
			SysConfigManager sysconfigService = (SysConfigManager) webctx.getBean("sysconfigService"); // 系统配置
			TmplConfigManager tmplConfigService = (TmplConfigManager) webctx.getBean("tmplconfigService"); // 模板配置
			// 获取系统当前期间
			PageData pdConfig = new PageData();
			pdConfig.put("KEY_CODE", "SystemDataTime");
			String busiDate = sysconfigService.getSysConfigByKey(pdConfig);
			Date dtCur = DateUtils.string2Date(busiDate);
			String nextBusidate = DateUtils.addMothToDate(1, dtCur, DateFormatUtils.DATE_MONTH_FORMAT);

			PageData pd = this.getPageData();
			pd.put("KEY_VALUE", nextBusidate);
			pd.put("NEXT_RPT_DUR", nextBusidate);
			pd.put("CUR_RPT_DUR", busiDate);

			String hasTmpl = tmplConfigService.findByRptDur(nextBusidate);
			if (StringUtil.isEmpty(hasTmpl)) {
				pd.put("CopyRptDur", true);
			}
			tmplConfigService.updateBusidate(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
