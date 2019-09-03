package com.fh.controller.common;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.fh.service.fundssummyconfirm.fundssummyconfirm.FundsSummyConfirmManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.util.PageData;
import com.fh.util.enums.TmplType;

/**
 * 单号
* @ClassName: CheckCertCode
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张晓柳
* @date 2018年11月29日
*
 */
public class CheckCertCode {
	
	public static String getCheckCertCode(List<String> listBillCode, 
			FundsSummyConfirmManager fundssummyconfirmService, SysConfigManager sysConfigManager) throws Exception{
		String strRet = "";
		
		String strSqlBillCode = " and BILL_CODE in (" + QueryFeildString.tranferListValueToSqlInString(listBillCode) + ") ";

		PageData getDataPd = new PageData();
		
		getDataPd.put("QueryFeild", strSqlBillCode + " and CERT_CODE not like ' %' ");
		getDataPd.put("TableName", Corresponding.tb_sys_confirm_info);
		List<PageData> getListConfirm = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
        for(PageData each : getListConfirm){
        	String BILL_CODE = each.getString("BILL_CODE");// 单据编号
			strRet += BILL_CODE + "已经生成凭证" + ", ";// + strPzbh
        }
		if(strRet!=null && !strRet.trim().equals("")){
	        return strRet;
		}
		
		getDataPd.put("QueryFeild", strSqlBillCode + " and CERT_CODE not like ' %' ");
		getDataPd.put("TableName", Corresponding.tb_gl_cert);
		List<PageData> getListCert = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
        for(PageData each : getListCert){
        	String BILL_CODE = each.getString("BILL_CODE");// 单据编号
			strRet += BILL_CODE + "已经生成凭证" + ", ";// + strPzbh
        }
		if(strRet!=null && !strRet.trim().equals("")){
	        return strRet;
		}
		
		getDataPd.put("QueryFeild", strSqlBillCode);
		getDataPd.put("TableName", Corresponding.tb_staff_summy_bill);
		List<PageData> getListStaff = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
		getDataPd.put("QueryFeild", strSqlBillCode);
		getDataPd.put("TableName", Corresponding.tb_social_inc_summy_bill);
		List<PageData> getListSocial = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
		getDataPd.put("QueryFeild", strSqlBillCode);
		getDataPd.put("TableName", Corresponding.tb_house_fund_summy_bill);
		List<PageData> getListHouse = fundssummyconfirmService.getOperList(getDataPd);	//列出Betting列表
		
		strRet += setConfirm(getListStaff, sysConfigManager, "");
		strRet += setConfirm(getListSocial, sysConfigManager, TmplType.TB_SOCIAL_INC_TRANSFER.getNameKey());
		strRet += setConfirm(getListHouse, sysConfigManager, TmplType.TB_HOUSE_FUND_TRANSFER.getNameKey());

        return strRet;
	}
	
	public static String setConfirm(List<PageData> getList, SysConfigManager sysConfigManager, String strTmplTypeTransfer) throws Exception{
		String strRet = "";
		if(getList!=null && getList.size()>0){
			// 执行从FIMS获取凭证号
			Service servicePzbh = new Service();
			Call callPzbh = (Call) servicePzbh.createCall();
			PageData pdKeyCodePzbh = new PageData();
			pdKeyCodePzbh.put("KEY_CODE", "JQueryPzInformation");
			String strUrlPzbh = sysConfigManager.getSysConfigByKey(pdKeyCodePzbh);
			URL urlPzbh = new URL(strUrlPzbh);
			callPzbh.setTargetEndpointAddress(urlPzbh);
			callPzbh.setOperationName(new QName("http://JQueryPzInformation.j2ee", "commonQueryPzBh"));
			callPzbh.setUseSOAPAction(true);

	        List<String> listBillCode = new ArrayList<String>();
	        for(PageData each : getList){
	        	String BILL_CODE = each.getString("BILL_CODE");// 单据编号
	        	if(!listBillCode.contains(BILL_CODE)){
		        	//String BUSI_DATE = each.getString("BUSI_DATE");
		        	String CUST_COL7 = each.getString("CUST_COL7"); 
		        	String USER_GROP = each.getString("USER_GROP"); 
		        	String tmplType = Corresponding.getTmplTypeTranferFromUserGroupType(USER_GROP);
		        	if(strTmplTypeTransfer!=null && !strTmplTypeTransfer.trim().equals("")){
		        		tmplType = strTmplTypeTransfer;
		        	}
					String tableName = "T_" + DictsUtil.getTableCodeOnFmis(tmplType, sysConfigManager);// 在fmis建立的业务表名

					String strPzbh = ""; // 凭证编号
					String resultPzbh = (String) callPzbh.invoke(new Object[] { tableName, BILL_CODE, CUST_COL7 });// 对应定义参数
					if (resultPzbh.length() > 0) {
						String[] stringArrPzbh = resultPzbh.split(";");
						strPzbh = stringArrPzbh[0]; // 凭证编号
						if(strPzbh!=null && !strPzbh.trim().equals("")){
			        		listBillCode.add(BILL_CODE);
							strRet += BILL_CODE + "已经生成凭证" + ", ";// + strPzbh
						}
					}
	        	}
	        }
		}
        return strRet;
	}
}