package com.fh.controller.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fh.entity.TableColumns;
import com.fh.util.PageData;

/**
 * 单号
* @ClassName: AcconutsShowList
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张晓柳
* @date 2017年8月14日
*
 */
public class AcconutsShowList {

	public static List<PageData> getShowListAll(List<PageData> listFirst, List<PageData> listSecond, List<TableColumns> m_columnsList, List<String> listMatchFeild, String strKeyExtra){
		List<PageData> varList = new ArrayList<PageData>();
		if(listFirst!=null && listFirst.size() > 0){
			if(!(listSecond!=null && listSecond.size()>0)){
				for(PageData fir : listFirst){
					for(TableColumns col : m_columnsList){
						if(Common.IsNumFeild(col.getData_type())){
							BigDecimal firValue = (BigDecimal) fir.get(col.getColumn_name());
							fir.put(col.getColumn_name(), firValue + "");
						}
					}
				}
				varList = listFirst;
				
			} else {
				for(PageData fir : listFirst){
					//listSecond里没有匹配fir的记录
					Boolean bolNotHave = true;
					//两条记录有差异
					Boolean bolHavedifference = false;
					for(PageData sec : listSecond){
						//根据汇总字段查找匹配的记录
						Boolean bolMatch = true;
						for(String sumFeild : listMatchFeild){
							String firValue = (String) fir.get(sumFeild + strKeyExtra);
							String secValue = (String) sec.get(sumFeild + strKeyExtra);
							if(!(firValue!=null && secValue!=null && firValue.equals(secValue))
									|| (firValue==null && secValue==null)){
								bolMatch = false;
							}
						}
						if(bolMatch){
							bolNotHave = false;
							for(TableColumns col : m_columnsList){
								if(Common.IsNumFeild(col.getData_type())){
									BigDecimal firValue = (BigDecimal) fir.get(col.getColumn_name());
									BigDecimal secValue = (BigDecimal) sec.get(col.getColumn_name());
									if(!firValue.equals(secValue)){
										fir.put(col.getColumn_name(), firValue + "(" + secValue +")");
										bolHavedifference = true;
									} else {
										fir.put(col.getColumn_name(), firValue + "");
									}
								}
							}
						}
					}
					if(bolNotHave){
						for(TableColumns col : m_columnsList){
							if(Common.IsNumFeild(col.getData_type())){
								BigDecimal firValue = (BigDecimal) fir.get(col.getColumn_name());
								fir.put(col.getColumn_name(), firValue + "");
							}
						}
					}
					if(bolNotHave || bolHavedifference){
						varList.add(fir);
					}
				}
			}
		}
		return varList;
	}

	public static List<PageData> getShowListPage(List<PageData> varList, int intPage, int intRowNum){
		List<PageData> retList = new ArrayList<PageData>();
		if(varList != null && varList.size() > 0 && intPage > 0 && intRowNum > 0){
			int RowNumAll = varList.size();
			int intStart = (intPage - 1) * intRowNum;
			if(intStart + 1 <= RowNumAll){
				int intEnd = (intPage - 1) * intRowNum + intRowNum - 1;
				intEnd = intEnd > RowNumAll-1 ? RowNumAll-1 : intEnd;
				for(int i = intStart; i <= intEnd; i++){
					retList.add(varList.get(i));
				}
			}
		}
		return retList;
	}

}
	