package com.fh.entity;

import java.util.List;

public class TmplTypeInfo {

	private String TypeCodeDetail;			
	private String TypeCodeSummyBill;		
	private String TypeCodeSummyDetail;	
	//private String TypeCodeListen;
	
	//汇总
	private List<String> SumFieldDetail;
	
	public List<String> getSumFieldDetail() {
		return SumFieldDetail;
	}
	public void setSumFieldDetail(List<String> sumFieldDetail) {
		SumFieldDetail = sumFieldDetail;
	}
	//核算
    //分组字段
	private String GroupbyFeild;
	//分组字段list  查询表的主键字段，作为标准列，jqgrid添加带__列，mybaits获取带__列
	private List<String> keyListBase;
	
	public String getTypeCodeSummyBill() {
		return TypeCodeSummyBill;
	}
	public void setTypeCodeSummyBill(String typeCodeSummyBill) {
		TypeCodeSummyBill = typeCodeSummyBill;
	}
	public String getTypeCodeSummyDetail() {
		return TypeCodeSummyDetail;
	}
	public void setTypeCodeSummyDetail(String typeCodeSummyDetail) {
		TypeCodeSummyDetail = typeCodeSummyDetail;
	}
	public String getTypeCodeDetail() {
		return TypeCodeDetail;
	}
	public void setTypeCodeDetail(String typeCodeDetail) {
		TypeCodeDetail = typeCodeDetail;
	}
	//public String getTypeCodeListen() {
	//	return TypeCodeListen;
	//}
	//public void setTypeCodeListen(String typeCodeListen) {
	//	TypeCodeListen = typeCodeListen;
	//}
	//public List<String> getSumField() {
	//	return SumField;
	//}
	//public void setSumField(List<String> sumField) {
	//	SumField = sumField;
	//}
	public String getGroupbyFeild() {
		return GroupbyFeild;
	}
	public void setGroupbyFeild(String groupbyFeild) {
		GroupbyFeild = groupbyFeild;
	}
	public List<String> getKeyListBase() {
		return keyListBase;
	}
	public void setKeyListBase(List<String> keyListBase) {
		this.keyListBase = keyListBase;
	}	

}
