package com.fh.controller.common;

import com.fh.service.billnum.BillNumManager;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.base.ConvertUtils;
import com.fh.util.date.DateUtils;

/**
 * 获取系统流水号
 * 
 * @ClassName: BillnumUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2019年9月26日
 *
 */
public class BillnumUtil {

	/**
	 * 获取单据编码
	 * 
	 * @param billNumManager
	 * @param billNumType
	 * @param unitCode
	 * @param departCode
	 * @return
	 * @throws Exception
	 */
	public static String getBillnum(BillNumManager billNumManager, String billNumType, String unitCode,
			String departCode) throws Exception {

		PageData pdBillNum = new PageData();
		String currentDate = DateUtils.getCurrentDateMonth();
		pdBillNum.put("BILL_CODE", billNumType);
		pdBillNum.put("BILL_DATE", currentDate);
		PageData pdBillNumResult = billNumManager.getBillNum(pdBillNum);
		int currentBillNum = 0;
		if (pdBillNumResult != null) {
			currentBillNum = ConvertUtils.strToInt(StringUtil.toString(pdBillNumResult.get("BILL_NUM"), ""), 0);
		}
		String billCode = billNumType + StringUtil.toString(unitCode, "") + StringUtil.toString(departCode, "")
				+ currentDate + StringUtil.getFixedDigitsStr4(currentBillNum + 1);
		return billCode;
	}

	/**
	 * 更新单据当前最大序列号
	 * 
	 * @param billNumManager
	 * @param billNumType
	 * @throws Exception
	 */
	public static void updateBillnum(BillNumManager billNumManager, String billNumType) throws Exception {

		PageData pdBillNum = new PageData();
		String currentDate = DateUtils.getCurrentDateMonth();
		pdBillNum.put("BILL_CODE", billNumType);
		pdBillNum.put("BILL_DATE", currentDate);
		PageData pdBillNumResult = billNumManager.getBillNum(pdBillNum);
		if (pdBillNumResult == null) {
			pdBillNum.put("BILL_DATE", currentDate);
			pdBillNum.put("BILL_NUM", 1);
			billNumManager.save(pdBillNum);
		}else{
			int currentBillNum = ConvertUtils.strToInt(StringUtil.toString(pdBillNumResult.get("BILL_NUM"), ""), 0);
			String billNum = StringUtil.toString(currentBillNum + 1, "");
			pdBillNum.put("BILL_NUM", billNum);
			billNumManager.edit(pdBillNum);
		}
	}
}