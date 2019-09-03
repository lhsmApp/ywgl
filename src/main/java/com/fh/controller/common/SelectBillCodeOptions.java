package com.fh.controller.common;

import java.util.List;

import com.fh.util.PageData;
import com.fh.entity.system.Department;
import com.fh.entity.system.Dictionaries;

/**
 * 下拉列表单号数据源
 * 
 * @ClassName: SelectBillCodeOptions
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张晓柳
 * @date 2017年11月21日
 *
 */
public class SelectBillCodeOptions {

	// 下拉列表 value和显示一致
	public static String getSelectBillCodeOptions(List<String> listBillCode, String strSelectBillCodeFirstShow,
			String strSelectBillCodeLastShow) {
		String strReturn = "";
		if (strSelectBillCodeFirstShow != null && !strSelectBillCodeFirstShow.trim().equals("")) {
			strReturn += "<option value='" + strSelectBillCodeFirstShow + "' selected='selected'>"
					+ strSelectBillCodeFirstShow + "</option>";
		}
		if (listBillCode != null) {
			for (String billCode : listBillCode) {
				if (billCode != null && !billCode.trim().equals("")) {
					strReturn += "<option value='" + billCode + "'>" + billCode + "</option>";
				}
			}
		}
		if (strSelectBillCodeLastShow != null && !strSelectBillCodeLastShow.trim().equals("")) {
			strReturn += "<option value='" + strSelectBillCodeLastShow + "'>" + strSelectBillCodeLastShow + "</option>";
		}
		return strReturn;
	}

	public static String getSelectCodeAndNameOptions(List<PageData> listPageData, PageData selectCodeAndNameFirstShow,
			PageData selectCodeAndNameLastShow) {
		String strReturn = "";
		if (selectCodeAndNameFirstShow != null && !selectCodeAndNameFirstShow.getString("NAME").equals("")) {
			strReturn += "<option value='" + selectCodeAndNameFirstShow.getString("CODE") + "' selected='selected'>"
					+ selectCodeAndNameFirstShow.getString("NAME") + "</option>";
		}
		if (listPageData != null) {
			for (PageData pageData : listPageData) {
				if (pageData.getString("CODE") != null && !pageData.getString("CODE").trim().equals("")) {
					strReturn += "<option value='" + pageData.getString("CODE") + "'>" + pageData.getString("NAME")
							+ "</option>";
				}
			}
		}
		if (selectCodeAndNameLastShow != null && !selectCodeAndNameLastShow.getString("NAME").equals("")) {
			strReturn += "<option value='" + selectCodeAndNameLastShow.getString("CODE") + "'>"
					+ selectCodeAndNameLastShow.getString("NAME") + "</option>";
		}
		return strReturn;
	}

	// 二期汇总界面使用
	public static String getSelectBillCodeOptions(List<String> listBillCode, String strSelectBillCodeFirstShow) {
		String strReturn = "";
		if (strSelectBillCodeFirstShow != null && !strSelectBillCodeFirstShow.trim().equals("")) {
			strReturn += "<option value='' selected='selected'>" + strSelectBillCodeFirstShow + "</option>";
		}
		if (listBillCode != null) {
			for (String billCode : listBillCode) {
				if (billCode != null && !billCode.trim().equals("")) {
					strReturn += "<option value='" + billCode + "'>" + billCode + "</option>";
				}
			}
		}
		return strReturn;
	}

	public static String getSelectDicOptions(List<Dictionaries> list, String strSelectBillCodeFirstShow) {
		String strReturn = "";
		if (strSelectBillCodeFirstShow != null && !strSelectBillCodeFirstShow.trim().equals("")) {
			strReturn += "<option value='' selected='selected'>" + strSelectBillCodeFirstShow + "</option>";
		}
		if (list != null) {
			for (Dictionaries dic : list) {
				strReturn += "<option value='" + dic.getDICT_CODE() + "'>" + dic.getNAME() + "</option>";
			}
		}
		return strReturn;
	}

	public static String getSelectDeptOptions(List<Department> list, String strSelectBillCodeFirstShow) {
		String strReturn = "";
		if (strSelectBillCodeFirstShow != null && !strSelectBillCodeFirstShow.trim().equals("")) {
			strReturn += "<option value='' selected='selected'>" + strSelectBillCodeFirstShow + "</option>";
		}
		if (list != null) {
			for (Department dept : list) {
				strReturn += "<option value='" + dept.getDEPARTMENT_CODE() + "'>" + dept.getNAME() + "</option>";
			}
		}
		return strReturn;
	}
}
