package com.fh.controller.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.service.detailimportcommon.detailimportcommon.impl.DetailImportCommonService;
import com.fh.service.glItemUser.glItemUser.GlItemUserManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.PageData;

/**
 * 获取分摊数据
 * 
 * @ClassName: ItemAlloc
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张晓柳
 * @date 2018年9月5日
 *
 */
public class ItemAlloc {

	@SuppressWarnings("unchecked")
	public static List<PageData> getSetItemUser(String SystemDateTime, String QueryFeild, String TableNameSecondDetail, 
			List<String> DetailSerialNoFeild, List<String> DetailUserCodeFeild, 
			List<String> SumFieldBill, DetailImportCommonService detailimportcommonService) throws Exception {
		PageData pdSetItemUser = new PageData();
		pdSetItemUser.put("SystemDateTime", SystemDateTime);
		pdSetItemUser.put("QueryFeild", QueryFeild);
		pdSetItemUser.put("TableName", TableNameSecondDetail);
		List<String> retSetItemUserFeild = new ArrayList<String>();
		for(String strfeild : DetailSerialNoFeild){
			retSetItemUserFeild.add(strfeild.toUpperCase());
		}
		for(String strfeild : DetailUserCodeFeild){
			retSetItemUserFeild.add(strfeild.toUpperCase());
		}
		retSetItemUserFeild = QueryFeildString.extraSumField(retSetItemUserFeild, SumFieldBill);
		String SelectFeildSetItemUser = QueryFeildString.tranferListStringToKeyString(retSetItemUserFeild, TmplUtil.keyExtra, false);
		pdSetItemUser.put("SelectFeild", " *, " + SelectFeildSetItemUser);
		List<PageData> getSetItemUser = detailimportcommonService.getSum(pdSetItemUser);
		return getSetItemUser;
	}

	//根据导入明细，设置工资人员项目信息，
	public static PageData getSaveItem(String SystemDateTime, String strGetSetItemDeptCode, GlItemUserManager glItemUserService,
			String TypeCodeDetail, TmplConfigService tmplconfigService,
			List<PageData> getSetItemUser, 
			String SelectedCustCol7, String TableNameFirstItem,
			List<String> FirstItemMustNotEditList,
			List<String> MustNotItemAllocList) throws Exception {
		//人员项目关系
		PageData pdGlItemUser = new PageData();
		pdGlItemUser.put("BUSI_DATE", SystemDateTime);
		pdGlItemUser.put("BILL_OFF", SelectedCustCol7);
		//pdGlItemUser.put("DEPT_CODE", strGetSetItemDeptCode);
	    List<String> ItemQueryFeildList = Arrays.asList("BUSI_DATE", "BILL_OFF", "DEPT_CODE");
		String ItemQueryFeild = QueryFeildString.getQueryFeild(pdGlItemUser, ItemQueryFeildList);
		pdGlItemUser.put("QueryFeild", ItemQueryFeild);
		List<PageData> getGlItemUserList = glItemUserService.getSumUseList(pdGlItemUser);

		List<PageData> getSaveItem = new ArrayList<PageData>();
		List<PageData> listItemInfo = new ArrayList<PageData>();

		Map<String, TableColumns> map_HaveColumnsListDetail = Common.GetHaveColumnsList(TypeCodeDetail, tmplconfigService);
		//循环导入明细
		for(PageData getItem : getSetItemUser){
			//String strItemBillCode = getItem.getString("BILL_CODE" + TmplUtil.keyExtra);
			String strItemBusiDate = getItem.getString("BUSI_DATE" + TmplUtil.keyExtra);
			String strItemCUST_COL7 = getItem.getString("CUST_COL7" + TmplUtil.keyExtra);
			String strItemUserCode = getItem.getString("USER_CODE" + TmplUtil.keyExtra);

			String strItemDepartCode = getItem.getString("DEPT_CODE" + TmplUtil.keyExtra);
			
			List<PageData> listItemUser = new ArrayList<PageData>();
			BigDecimal douItemBudSum = new BigDecimal(0);
			//循环筛选出的导入明细信息，将其中的人员编码对应的人员项目关系获取到，
			//将人员项目关系由一条（人员多个项目）转换成多条（人员只一个项目），并计算出相应人员编码对应人员只一个项目的条数和人员对应所有项目的总概算，人员编码为空不参与计算。
			Boolean bolSame = false;
			if(getGlItemUserList!=null && getGlItemUserList.size()>0){
		        for(int num=0; num<getGlItemUserList.size(); num++){
		        	if(!bolSame){
			        	PageData glItemUser = getGlItemUserList.get(num);
						String strGlBusiDate = glItemUser.getString("BUSI_DATE");
						String strGlBILL_OFF = glItemUser.getString("BILL_OFF");
						//String strGlDepartCode = glItemUser.getString("DEPT_CODE");
						String strGlUserCode = glItemUser.getString("USER_CODE");
						if(strItemBusiDate.equals(strGlBusiDate) 
								&& strItemCUST_COL7.equals(strGlBILL_OFF) 
								//&& strItemDepartCode.equals(strGlDepartCode) 
								&& strItemUserCode.equals(strGlUserCode)){
							bolSame = true;
							num = getGlItemUserList.size();
							String ITEM1_CODE = glItemUser.getString("ITEM1_CODE");
							if(ITEM1_CODE!=null && !ITEM1_CODE.trim().equals("")) {
								BigDecimal ITEM1_BUD = new BigDecimal(glItemUser.get("ITEM1_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM1_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM1_CODE);
								pdItemUser.put("ITEM_BUD", ITEM1_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM2_CODE = glItemUser.getString("ITEM2_CODE");
							if(ITEM2_CODE!=null && !ITEM2_CODE.trim().equals("")) {
								BigDecimal ITEM2_BUD = new BigDecimal(glItemUser.get("ITEM2_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM2_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM2_CODE);
								pdItemUser.put("ITEM_BUD", ITEM2_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM3_CODE = glItemUser.getString("ITEM3_CODE");
							if(ITEM3_CODE!=null && !ITEM3_CODE.trim().equals("")) {
								BigDecimal ITEM3_BUD = new BigDecimal(glItemUser.get("ITEM3_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM3_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM3_CODE);
								pdItemUser.put("ITEM_BUD", ITEM3_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM4_CODE = glItemUser.getString("ITEM4_CODE");
							if(ITEM4_CODE!=null && !ITEM4_CODE.trim().equals("")) {
								BigDecimal ITEM4_BUD = new BigDecimal(glItemUser.get("ITEM4_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM4_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM4_CODE);
								pdItemUser.put("ITEM_BUD", ITEM4_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM5_CODE = glItemUser.getString("ITEM5_CODE");
							if(ITEM5_CODE!=null && !ITEM5_CODE.trim().equals("")) {
								BigDecimal ITEM5_BUD = new BigDecimal(glItemUser.get("ITEM5_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM5_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM5_CODE);
								pdItemUser.put("ITEM_BUD", ITEM5_BUD);
								listItemUser.add(pdItemUser);
							}

							String ITEM6_CODE = glItemUser.getString("ITEM6_CODE");
							if(ITEM6_CODE!=null && !ITEM6_CODE.trim().equals("")) {
								BigDecimal ITEM6_BUD = new BigDecimal(glItemUser.get("ITEM6_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM6_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM6_CODE);
								pdItemUser.put("ITEM_BUD", ITEM6_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM7_CODE = glItemUser.getString("ITEM7_CODE");
							if(ITEM7_CODE!=null && !ITEM7_CODE.trim().equals("")) {
								BigDecimal ITEM7_BUD = new BigDecimal(glItemUser.get("ITEM7_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM7_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM7_CODE);
								pdItemUser.put("ITEM_BUD", ITEM7_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM8_CODE = glItemUser.getString("ITEM8_CODE");
							if(ITEM8_CODE!=null && !ITEM8_CODE.trim().equals("")) {
								BigDecimal ITEM8_BUD = new BigDecimal(glItemUser.get("ITEM8_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM8_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM8_CODE);
								pdItemUser.put("ITEM_BUD", ITEM8_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM9_CODE = glItemUser.getString("ITEM9_CODE");
							if(ITEM9_CODE!=null && !ITEM9_CODE.trim().equals("")) {
								BigDecimal ITEM9_BUD = new BigDecimal(glItemUser.get("ITEM9_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM9_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM9_CODE);
								pdItemUser.put("ITEM_BUD", ITEM9_BUD);
								listItemUser.add(pdItemUser);
							}
							String ITEM10_CODE = glItemUser.getString("ITEM10_CODE");
							if(ITEM10_CODE!=null && !ITEM10_CODE.trim().equals("")) {
								BigDecimal ITEM10_BUD = new BigDecimal(glItemUser.get("ITEM10_BUD").toString());
								douItemBudSum = douItemBudSum.add(ITEM10_BUD);
								PageData pdItemUser = new PageData();
								pdItemUser.put("ITEM_CODE", ITEM10_CODE);
								pdItemUser.put("ITEM_BUD", ITEM10_BUD);
								listItemUser.add(pdItemUser);
							}
						}
		        	}
				}
			}

			//人员编码对应人员项目的条数为0（即本人没有项目信息），设置人员工资项目表数据项目编码字段为0，其余字段与导入信息一致；条数不为0，循环人员编码对应的多条人员项目关系，将导入的一条信息转换成多条人员工资项目信息（与人员编码对应项目条数相同），设置相对应的项目编码和概算。
			//循环结构配置信息，根据人员对应所有项目的总概算计算人员工资项目信息数值型字段（除流水号）的比例金额（最后一条人员工资项目信息是总额度减去已计算的额度，以确保人员工资项目信息数据与导入信息金额相对应）。
			//如人员只对应一条项目信息，并且概算为0，则人员工资项目金额与导入金额一致；如人员只对应多条项目信息，并且概算都为0，则人员工资项目最后一条金额与导入金额一致，其余金额都为0。
			Map<String, TmplConfigDetail> map_SetColumnsListDetail = Common.GetSetColumnsList(TypeCodeDetail, strItemDepartCode, SelectedCustCol7, tmplconfigService);
			List<PageData> listAddSaveItem = new ArrayList<PageData>();
			if(listItemUser!=null && listItemUser.size()>0){
				BigDecimal douItemBlLast = new BigDecimal(1);
				for(int i=0; i<listItemUser.size(); i++){
					PageData pdItemUser = listItemUser.get(i);
					String ITEM_CODE = pdItemUser.getString("ITEM_CODE");
					BigDecimal ITEM_BUD = new BigDecimal(pdItemUser.get("ITEM_BUD").toString());
					PageData pdItemAdd = Common.copyPdToOther(getItem, map_HaveColumnsListDetail);
					pdItemAdd.put("ITEM_CODE", ITEM_CODE);
					pdItemAdd.put("GlItemUserItemBud", ITEM_BUD);
					listAddSaveItem.add(pdItemAdd);
					
					BigDecimal douItemBlAdd = new BigDecimal(0);
					if(!(douItemBudSum.compareTo(new BigDecimal(0)) == 0)){
						douItemBlAdd = ITEM_BUD.divide(douItemBudSum, 2, BigDecimal.ROUND_HALF_UP);
						if(i == listItemUser.size()-1){
							douItemBlAdd = douItemBlLast;
						} else {
							douItemBlLast = douItemBlLast.subtract(douItemBlAdd);
						}
					}
					PageData pdItemInfoAdd = Common.copyPdToOther(getItem, map_HaveColumnsListDetail);
					pdItemInfoAdd.put("ITEM_CODE", ITEM_CODE);
					pdItemInfoAdd.put("ITEM_BL", douItemBlAdd);
					listItemInfo.add(pdItemInfoAdd);
				}
			    for (TableColumns column : map_HaveColumnsListDetail.values()) {
			    	String column_name = column.getColumn_name().toUpperCase();
			    	String data_type = column.getData_type().toUpperCase();
					if(Common.IsNumFeild(data_type) && !MustNotItemAllocList.contains(column_name)){
						BigDecimal douGetValue = new BigDecimal(getItem.get(column_name).toString());
						BigDecimal douValueLast = douGetValue;
						for(int i=0; i<listAddSaveItem.size(); i++){
							PageData pdSaveItem = listAddSaveItem.get(i);
							BigDecimal douGetBud = new BigDecimal(pdSaveItem.get("GlItemUserItemBud").toString());
							BigDecimal douItemAdd = new BigDecimal(0);
							if(!(douItemBudSum.compareTo(new BigDecimal(0)) == 0 || douGetValue.compareTo(new BigDecimal(0)) == 0)){
								douItemAdd = douGetValue.multiply(douGetBud).divide(douItemBudSum, 2, BigDecimal.ROUND_HALF_UP);
    							if(i == listItemUser.size()-1){
        							douItemAdd = douValueLast;
    							} else {
									douValueLast = douValueLast.subtract(douItemAdd);
    							}
							}
							pdSaveItem.put(column_name, douItemAdd);
						}
					}
			    }
				if(douItemBudSum.compareTo(new BigDecimal(0)) == 0){
					PageData pdItemAdd = Common.copyPdToOther(getItem, map_HaveColumnsListDetail);
					pdItemAdd.put("ITEM_CODE", "");
					listAddSaveItem.add(pdItemAdd);
				}
			} else {
				PageData pdItemAdd = Common.copyPdToOther(getItem, map_HaveColumnsListDetail);
				pdItemAdd.put("ITEM_CODE", "");
				listAddSaveItem.add(pdItemAdd);
	        }
			for(PageData pdItemAdd : listAddSaveItem){
				pdItemAdd.put("TableName", TableNameFirstItem);
                //添加未设置字段默认值
    			Common.setModelDefault(pdItemAdd, map_HaveColumnsListDetail, map_SetColumnsListDetail, FirstItemMustNotEditList);
    			getSaveItem.add(pdItemAdd);
			}
		}
		PageData retPd = new PageData();
		retPd.put("SaveItem", getSaveItem);
		retPd.put("ItemInfo", listItemInfo);
	    return retPd;
	}
}
