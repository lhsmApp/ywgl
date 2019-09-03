package com.fh.entity;

public class Policy {
	private String poli_type;		//政策分类
	private String title;			//标题
	private String pub_date;		//发布时间
	private String pub_user;		//发布人
	private String titl_cont;		//政策内容
	public String getPoli_type() {
		return poli_type;
	}
	public void setPoli_type(String poli_type) {
		this.poli_type = poli_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPub_date() {
		return pub_date;
	}
	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}
	public String getPub_user() {
		return pub_user;
	}
	public void setPub_user(String pub_user) {
		this.pub_user = pub_user;
	}
	public String getTitl_cont() {
		return titl_cont;
	}
	public void setTitl_cont(String titl_cont) {
		this.titl_cont = titl_cont;
	}
}
