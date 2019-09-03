package com.fh.controller.common;

import java.text.NumberFormat;
import java.util.List;

import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 单号
* @ClassName: BillCodeUtil
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张晓柳
* @date 2017年7月14日
*
 */
public class BillCodeUtil {

	public static String getBillCode(String BillNumType, String month, int billNum) throws Exception {
		String billCode = BillNumType + month + TansferBillNum(billNum);
		return billCode;
	}
	private static String TansferBillNum(int billNum){
		NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(4);
        //设置最小整数位数    
        nf.setMinimumIntegerDigits(4);
		return nf.format(billNum);
		//String str = String.format("%04d", youNumber);
    }
}
	