package com.fh.controller.common;

import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.enums.BillState;
import com.fh.entity.SysDeptLtdTime;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.sysDeptLtdTime.sysDeptLtdTime.impl.SysDeptLtdTimeService;

/**
 * 模板通用类
 * 
 * @ClassName: CheckSystemDateTime
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhangxiaoliu
 * @date 2018年04月19日
 *
 */
public class CheckSystemDateTime {

	/**
	 * 判断传过来的TranferSystemDateTime和配置表里的当前区间是否一致
	 * @return
	 * @throws Exception
	 */
	public static String CheckTranferSystemDateTime(String TranferSystemDateTime, SysConfigManager sysConfigManager,
			Boolean bolSysDeptLtdTime) 
			throws Exception {
		String strReturn = "";
		//当前期间,取自tb_system_config的SystemDateTime字段
		String getSystemDateTime = sysConfigManager.currentSection(new PageData());
		if(getSystemDateTime!=null && !getSystemDateTime.trim().equals("")){
			if(!getSystemDateTime.equals(TranferSystemDateTime)){
				strReturn += Message.SystemDateTimeNotSameTranferSystemDateTime;
			}
		} else {
			strReturn += Message.SystemDateTimeMustNotKong;
		}
		return strReturn;
	}

	/**
	 * SystemDateTime取年、月
	 * @return
	 * @throws Exception
	 */
	public static String getSystemDateTimeYear(String SystemDateTime) 
			throws Exception {
		String strReturn = SystemDateTime.substring(0, SystemDateTime.length() - 2);
		return strReturn;
	}
	public static String getSystemDateTimeMouth(String SystemDateTime) 
			throws Exception {
		String strReturn = SystemDateTime.substring(SystemDateTime.length() - 2, SystemDateTime.length());
		return strReturn;
	}

	/**
	 * 验证是否在操作时间内
	 * @return
	 * @throws Exception
	 */
	public static String CheckSysDeptLtdTime(String SelectedTableNo, String BILL_OFF, String DEPT_CODE, SysDeptLtdTimeService sysDeptLtdTimeService) 
			throws Exception {
		String strReturn = "";
		SysDeptLtdTime sysTransfer = new SysDeptLtdTime();
		String BUSI_TYPE = Corresponding.getTypeCodeDetailFromTmplType(SelectedTableNo);
		sysTransfer.setBUSI_TYPE(BUSI_TYPE);
		sysTransfer.setBILL_OFF(BILL_OFF);
		sysTransfer.setDEPT_CODE(DEPT_CODE);
		SysDeptLtdTime getSysDeptLtdTime = sysDeptLtdTimeService.getUseSysDeptLtdTime(sysTransfer);
		if(getSysDeptLtdTime!=null && getSysDeptLtdTime.getBUSI_TYPE()!=null 
				&& !getSysDeptLtdTime.getBUSI_TYPE().trim().equals("")){
			if(getSysDeptLtdTime.getSTATE()!=null && getSysDeptLtdTime.getSTATE().equals(BillState.Normal.getNameKey())){
				String LTD_DAY = getSysDeptLtdTime.getLTD_DAY();
				if(!(LTD_DAY!=null && !LTD_DAY.trim().equals(""))){
					LTD_DAY = String.valueOf(0);
				}
				String strCurrentDay = DateUtil.getCurrentDay();
				if(Integer.valueOf(strCurrentDay) > Integer.valueOf(LTD_DAY)){
					strReturn += getSysDeptLtdTime.getDEPT_NAME() + " " + Message.CurrentDay_LTD_DAY;
				}
			}
		} else {
			strReturn += Message.LTD_DAY_Not_Have;
		}
		return strReturn;
	}
}
