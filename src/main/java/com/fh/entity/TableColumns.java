package com.fh.entity;

public class TableColumns{
	private String Column_name;
	private String Column_comment;
	private String data_type;
	private String Column_type;
	private String Column_key;
	private String Column_default; 
	public String getColumn_name() {
		return Column_name;
	}
	public void setColumn_name(String column_name) {
		Column_name = column_name;
	}
	public String getColumn_comment() {
		return Column_comment;
	}
	public void setColumn_comment(String column_comment) {
		Column_comment = column_comment;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getColumn_type() {
		return Column_type;
	}
	public void setColumn_type(String column_type) {
		Column_type = column_type;
	}
	public String getColumn_key() {
		return Column_key;
	}
	public void setColumn_key(String column_key) {
		Column_key = column_key;
	}
	public String getColumn_default() {
		return Column_default;
	}
	public void setColumn_default(String column_default) {
		Column_default = column_default;
	}
}