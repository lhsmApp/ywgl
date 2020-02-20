package com.fh.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.entity.system.Department;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.fhoa.department.DepartmentManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.tmplConfigDict.tmplconfigdict.TmplConfigDictManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.Tools;
import com.fh.util.enums.TmplType;

import net.sf.json.JSONArray;

/**
 * 字典信息通用类
 * 
 * @ClassName: DictsUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年5月10日
 *
 */
public class DictsUtil {
	/**
	 * 根据字典父编码获取所有字典详情信息
	 * 
	 * @param parentBianma
	 *            字典父编码
	 * @return
	 * @throws Exception
	 */
	
	public static String Id = "id";
	//机关编码
	public static String DepartShowAll_01001 = "01001";
	public static String DepartShowAll_00 = "00";
	
	//帐套编码
	public static String BillOffOld = "9100";
	public static String BillOffNew = "9870";
	
	public static List<Dictionaries> getDictsByParentBianma(DictionariesManager dictionariesService,
			String parentBianma) throws Exception {
		List<Dictionaries> listDict = dictionariesService.getSysDictionaries(parentBianma);
		return listDict;
	}

	public static List<Dictionaries> getDictsByParentCode(DictionariesManager dictionariesService, String dicName)
			throws Exception {
		List<Dictionaries> dicList = dictionariesService.getSysDictionaries(dicName);
		return dicList;
	}

	/**
	 * 根据字典名称获取字典信息，生成Jqgrid editOptions和SearchOptions所需的Select格式。
	 * 
	 * @param dicName
	 *            字典名称
	 * @return
	 * @throws Exception
	 */
	public static String getDicValue(DictionariesManager dictionariesService, String dicName) throws Exception {
		StringBuilder ret = new StringBuilder();
		Map<String, String> dicAdd = new HashMap<String, String>();
		List<Dictionaries> dicList = dictionariesService.getSysDictionaries(dicName);
		for (Dictionaries dic : dicList) {
			if (ret != null && !ret.toString().trim().equals("")) {
				ret.append(";");
			}
			ret.append(dic.getDICT_CODE() + ":" + dic.getNAME());
			dicAdd.put(dic.getDICT_CODE(), dic.getNAME());
		}
		return ret.toString();
	}

	/**
	 * 获取组织结构信息，生成Jqgrid editOptions和SearchOptions所需的Select格式。
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static String getDepartmentValue(DepartmentManager departmentService) throws Exception {
		StringBuilder ret = new StringBuilder();
		PageData pd = new PageData();
		List<Department> listPara = (List<Department>) departmentService.getDepartDic(pd);
		for (Department dic : listPara) {
			if (ret != null && !ret.toString().trim().equals("")) {
				ret.append(";");
			}
			ret.append(dic.getDEPARTMENT_CODE() + ":" + dic.getNAME());
		}
		return ret.toString();
	}

	/**
	 * 获取组织结构信息，生成Jqgrid editOptions和SearchOptions所需的Select格式。
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static String getSysUserValue(UserManager userService) throws Exception {
		StringBuilder ret = new StringBuilder();
		PageData pd = new PageData();
		List<PageData> listPara = (List<PageData>) userService.getUserValue(pd);
		for (PageData dic : listPara) {
			if (ret != null && !ret.toString().trim().equals("")) {
				ret.append(";");
			}
			ret.append(StringUtil.toString(dic.get("USER_ID"), "") + ":" + dic.getString("NAME"));
		}
		return ret.toString();
	}
	
	/**
	 * 获取组织结构信息，生成Jqgrid editOptions和SearchOptions所需的Select格式。
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static List<PageData> getSysUserDic(UserManager userService) throws Exception {
		PageData pd = new PageData();
		List<PageData> listPara = (List<PageData>) userService.getUserValue(pd);
		//USER_ID NAME
		return listPara;
	}
	
	/**
	 * 获取组织结构信息，生成Jqgrid editOptions和SearchOptions所需的Select格式。
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static List<PageData> getSysUserDicByCondition(UserManager userService,PageData pd) throws Exception {
		List<PageData> listPara = (List<PageData>) userService.getUserValue(pd);
		//USER_ID NAME
		return listPara;
	}

	/**
	 * 获取自定类型信息，生成Jqgrid editOptions和SearchOptions所需的Select格式。
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static String getDicTypeValue(TmplConfigDictManager tmplconfigdictService) throws Exception {
		StringBuilder ret = new StringBuilder();
		PageData pd = new PageData();
		List<PageData> listPara = (List<PageData>) tmplconfigdictService.listAll(pd);
		for (PageData dic : listPara) {
			if (ret != null && !ret.toString().trim().equals("")) {
				ret.append(";");
			}
			ret.append(dic.getString("DICT_CODE") + ":" + dic.getString("DICT_NAME"));
		}
		return ret.toString();
	}

	/**
	 * 获取组织机构树数据源
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static String getDepartmentSelectTreeSource(DepartmentManager departmentService) throws Exception {
		String curUserDepartCode = Jurisdiction.getCurrentDepartmentID();//当前登录人所在二级单位
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		String departName = user.getDEPARTMENT_NAME();
		String parentDepartCode="";
		String parentDepartName="";
		String orgCode = Tools.readTxtFile(Const.ORG_CODE); 
		String [] orgInfo=orgCode.split(",");
		
		if(curUserDepartCode.equals(DepartShowAll_01001) || curUserDepartCode.equals(DepartShowAll_00)){//机关
			parentDepartCode=orgInfo[0];
			parentDepartName=orgInfo[1];
		}
		else{
			parentDepartCode=curUserDepartCode;
			parentDepartName=departName;
		}
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd.put(DictsUtil.Id, parentDepartCode);
		pd.put("parentId", "");
		pd.put("name",parentDepartName);
		pd.put("icon", "static/images/user.gif");
		zdepartmentPdList.add(pd);
		List<PageData> listResult=departmentService.listAllDepartmentAndSelfToSelect(parentDepartCode,zdepartmentPdList);
		if(zdepartmentPdList.size()==1) return "0";
		JSONArray arr = JSONArray.fromObject(listResult);
		return (null == arr ? "" : arr.toString());
	}

	/**
	 * 获取组织机构树数据源
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static String getDepartmentSelectTreeSource(DepartmentManager departmentService, String curUserDepartCode) throws Exception {
		String departName = curUserDepartCode;
		String parentDepartCode="";
		String parentDepartName="";
		String orgCode = Tools.readTxtFile(Const.ORG_CODE); 
		String [] orgInfo=orgCode.split(",");
		
		if(curUserDepartCode.equals(DepartShowAll_01001) || curUserDepartCode.equals(DepartShowAll_00)){//机关
			parentDepartCode=orgInfo[0];
			parentDepartName=orgInfo[1];
		}
		else{
			parentDepartCode=curUserDepartCode;
			parentDepartName=orgInfo[1];
		}
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd.put(DictsUtil.Id, parentDepartCode);
		pd.put("parentId", "");
		pd.put("name",parentDepartName);
		pd.put("icon", "static/images/user.gif");
		zdepartmentPdList.add(pd);
		List<PageData> listResult=departmentService.listAllDepartmentAndSelfToSelect(parentDepartCode,zdepartmentPdList);
		if(zdepartmentPdList.size()==1) return "0";
		JSONArray arr = JSONArray.fromObject(listResult);
		return (null == arr ? "" : arr.toString());
	}

	/**
	 * 获取组织机构树数据源
	 * 
	 * @param departmentService
	 * @return
	 * @throws Exception
	 */
	public static List<PageData> getDepartmentSelectTreeSourceList(DepartmentManager departmentService)
			throws Exception {
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		return departmentService.listAllDepartmentToSelect("0", zdepartmentPdList);
	}

	/**
	 * 根据模板基本类型获取员工组编码
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static String getTranferTmplType(String tmplCode) throws Exception {
		String tmplType = "";
		if (tmplCode.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())) {
			tmplType = TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey();
		} else if (tmplCode.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())) {
			tmplType = TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey();
		} else if (tmplCode.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())) {
			tmplType = TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey();
		} else if (tmplCode.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
			tmplType = TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey();
		} else if (tmplCode.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
			tmplType = TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey();
		} else if (tmplCode.equals(TmplType.TB_SOCIAL_INC_DETAIL.getNameKey())
				|| tmplCode.equals(TmplType.TB_SOCIAL_INC_SUMMY.getNameKey())
				|| tmplCode.equals(TmplType.TB_SOCIAL_INC_AUDIT.getNameKey())
				|| tmplCode.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
			tmplType = TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey();
		} else if (tmplCode.equals(TmplType.TB_HOUSE_FUND_DETAIL.getNameKey())
				|| tmplCode.equals(TmplType.TB_HOUSE_FUND_SUMMY.getNameKey())
				|| tmplCode.equals(TmplType.TB_HOUSE_FUND_AUDIT.getNameKey())
				|| tmplCode.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
			tmplType = TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey();
		}
		return tmplType;
	}

	/**
	 * 根据模板基本类型获取员工组编码
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static Boolean ifCheckGroupTypeNull(String tmplCode) throws Exception {
		Boolean bolRet = true;
		if (!(tmplCode.equals(TmplType.TB_STAFF_DETAIL_CONTRACT.getNameKey())
				//|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_BILL_CONTRACT.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_CONTRACT.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_CONTRACT.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())
                 
				||tmplCode.equals(TmplType.TB_STAFF_DETAIL_MARKET.getNameKey())
				//|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_BILL_MARKET.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_MARKET.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_MARKET.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())
                
				||tmplCode.equals(TmplType.TB_STAFF_DETAIL_SYS_LABOR.getNameKey())
				//|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_BILL_SYS_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_SYS_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_SYS_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())
                
				||tmplCode.equals(TmplType.TB_STAFF_DETAIL_OPER_LABOR.getNameKey())
				//|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_BILL_OPER_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_OPER_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_OPER_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())
                
				||tmplCode.equals(TmplType.TB_STAFF_DETAIL_LABOR.getNameKey())
				//|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_BILL_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_SUMMY_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_AUDIT_LABOR.getNameKey())
				|| tmplCode.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey()))) {
			bolRet = false;
		}
		return bolRet;
	}

	/**
	 * 根据模板基本表名称获取对应的实际数据库表名称
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public static String getActualTable(String tableCodeTmpl) throws Exception {
		String tableCodeOri = tableCodeTmpl;// 数据库真实业务数据表
		if (tableCodeTmpl.startsWith("TB_STAFF_DETAIL")) {
			tableCodeOri = "TB_STAFF_DETAIL";
		} else if (tableCodeTmpl.startsWith("TB_STAFF_SUMMY")) {
			tableCodeOri = "TB_STAFF_SUMMY";
		} else if (tableCodeTmpl.startsWith("TB_STAFF_AUDIT")) {
			tableCodeOri = "TB_STAFF_AUDIT";
		} else if (tableCodeTmpl.startsWith("TB_STAFF_TRANSFER")) {
			tableCodeOri = "TB_STAFF_SUMMY";
		} else if (tableCodeTmpl.equals("TB_SOCIAL_INC_DETAIL")) {
			tableCodeOri = "TB_SOCIAL_INC_DETAIL";
		} else if (tableCodeTmpl.equals("TB_SOCIAL_INC_SUMMY")) {
			tableCodeOri = "TB_SOCIAL_INC_SUMMY";
		} else if (tableCodeTmpl.equals("TB_SOCIAL_INC_AUDIT")) {
			tableCodeOri = "TB_SOCIAL_INC_AUDIT";
		} else if (tableCodeTmpl.equals("TB_SOCIAL_INC_TRANSFER")) {
			tableCodeOri = "TB_SOCIAL_INC_SUMMY";
		} else if (tableCodeTmpl.equals("TB_HOUSE_FUND_DETAIL")) {
			tableCodeOri = "TB_HOUSE_FUND_DETAIL";
		} else if (tableCodeTmpl.equals("TB_HOUSE_FUND_SUMMY")) {
			tableCodeOri = "TB_HOUSE_FUND_SUMMY";
		} else if (tableCodeTmpl.equals("TB_HOUSE_FUND_AUDIT")) {
			tableCodeOri = "TB_HOUSE_FUND_AUDIT";
		} else if (tableCodeTmpl.equals("TB_HOUSE_FUND_TRANSFER")) {
			tableCodeOri = "TB_HOUSE_FUND_SUMMY";
		}
		return tableCodeOri;
	}

	/**
	 * 根据前端业务表索引获取定义在Fmis系统上定义的表名称
	 * 
	 * @param which
	 *            1、合同化工资 2、社保 3、公积金 4、市场化工资 5、系统内劳务工资 6、运行人员工资 7、劳务派遣工资
	 * @return
	 * @throws Exception
	 */
	public static String getTableCodeOnFmis(String which, SysConfigManager sysConfigManager) throws Exception {
		PageData pd = new PageData();
		String tableCodeOri = "";// 数据库真实业务数据表
		if (which.equals(TmplType.TB_STAFF_TRANSFER_CONTRACT.getNameKey())) {
			pd.put("KEY_CODE", "StaffTransferHT");
			tableCodeOri = sysConfigManager.getSysConfigByKey(pd);
		} else if (which.equals(TmplType.TB_STAFF_TRANSFER_MARKET.getNameKey())) {
			pd.put("KEY_CODE", "StaffTransferSC");
			tableCodeOri = sysConfigManager.getSysConfigByKey(pd);
		} else if (which.equals(TmplType.TB_STAFF_TRANSFER_SYS_LABOR.getNameKey())) {
			pd.put("KEY_CODE", "StaffTransferXT");
			tableCodeOri = sysConfigManager.getSysConfigByKey(pd);
		} else if (which.equals(TmplType.TB_STAFF_TRANSFER_OPER_LABOR.getNameKey())) {
			pd.put("KEY_CODE", "StaffTransferYX");
			tableCodeOri = sysConfigManager.getSysConfigByKey(pd);
		} else if (which.equals(TmplType.TB_STAFF_TRANSFER_LABOR.getNameKey())) {
			pd.put("KEY_CODE", "StaffTransferLW");
			tableCodeOri = sysConfigManager.getSysConfigByKey(pd);
		} else if (which.equals(TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey())) {
			tableCodeOri = "TB_SOCIAL_INC_SUMMY";
		} else if (which.equals(TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey())) {
			tableCodeOri = "TB_HOUSE_FUND_SUMMY";
		} else {
			pd.put("KEY_CODE", "StaffTransferHT");
			tableCodeOri = sysConfigManager.getSysConfigByKey(pd);
		}
		return tableCodeOri;
	}
}
