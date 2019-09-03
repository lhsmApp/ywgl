package com.fh.entity;

public class ClsVoucherStruFeild{

	private String SqlInsFeild; 
	private String SqlNvlFeild; 
	private String SqlSelFeild; 
	private String SqlWhere;
	public ClsVoucherStruFeild(){
		SqlInsFeild = "";
		SqlSelFeild = "";
		SqlNvlFeild = "";
		SqlWhere = "";
	}
	public String getSqlNvlFeild() {
		return SqlNvlFeild;
	}
	public void setSqlNvlFeild(String sqlNvlFeild) {
		SqlNvlFeild = sqlNvlFeild;
	}
	public String getSqlInsFeild() {
		return SqlInsFeild;
	}
	public void setSqlInsFeild(String sqlInsFeild) {
		SqlInsFeild = sqlInsFeild;
	}
	public String getSqlSelFeild() {
		return SqlSelFeild;
	}
	public void setSqlSelFeild(String sqlSelFeild) {
		SqlSelFeild = sqlSelFeild;
	}
	public String getSqlWhere() {
		return SqlWhere;
	}
	public void setSqlWhere(String sqlWhere) {
		SqlWhere = sqlWhere;
	} 
    
}