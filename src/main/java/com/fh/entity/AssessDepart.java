package com.fh.entity;

import java.util.List;

/** 
 * 说明：培训基础 实体类
 * 创建人：jiachao
 * 创建时间：2019-10-23
 */
public class AssessDepart{ 
	
	private String ASSESSDEPART_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private AssessDepart assessdepart;
	private List<AssessDepart> subAssessDepart;
	private boolean hasAssessDepart = false;
	private String treeurl;
	
	private int DEPART_ID;				
	public int getFDEPART_ID() {
		return DEPART_ID;
	}
	public void setFDEPART_ID(int DEPART_ID) {
		this.DEPART_ID = DEPART_ID;
	}
	private String DEPART_CODE;			
	public String getFDEPART_CODE() {
		return DEPART_CODE;
	}
	public void setFDEPART_CODE(String DEPART_CODE) {
		this.DEPART_CODE = DEPART_CODE;
	}
	private String DEPART_NAME;			
	public String getFDEPART_NAME() {
		return DEPART_NAME;
	}
	public void setFDEPART_NAME(String DEPART_NAME) {
		this.DEPART_NAME = DEPART_NAME;
	}
	private String DEPART_PARENT_CODE;			
	public String getFDEPART_PARENT_CODE() {
		return DEPART_PARENT_CODE;
	}
	public void setFDEPART_PARENT_CODE(String DEPART_PARENT_CODE) {
		this.DEPART_PARENT_CODE = DEPART_PARENT_CODE;
	}

	private String STATE;			
	public String getFSTATE() {
		return STATE;
	}
	public void setFSTATE(String STATE) {
		this.STATE = STATE;
	}

	public String getASSESSDEPART_ID() {
		return ASSESSDEPART_ID;
	}
	public void setASSESSDEPART_ID(String ASSESSDEPART_ID) {
		this.ASSESSDEPART_ID = ASSESSDEPART_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public AssessDepart getAssessDepart() {
		return assessdepart;
	}
	public void setAssessDepart(AssessDepart assessdepart) {
		this.assessdepart = assessdepart;
	}
	public List<AssessDepart> getSubAssessDepart() {
		return subAssessDepart;
	}
	public void setSubAssessDepart(List<AssessDepart> subAssessDepart) {
		this.subAssessDepart = subAssessDepart;
	}
	public boolean isHasAssessDepart() {
		return hasAssessDepart;
	}
	public void setHasAssessDepart(boolean hasAssessDepart) {
		this.hasAssessDepart = hasAssessDepart;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
