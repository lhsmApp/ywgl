package com.fh.entity;

import java.util.List;

/** 
 * 说明：知识类别 实体类
 * 创建人：jiachao
 * 创建时间：2019-10-10
 */
public class ProblemType{ 
	
	private String PROBLEMTYPE_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private ProblemType problemtype;
	private List<ProblemType> subProblemType;
	private boolean hasProblemType = false;
	private String treeurl;
	
	private int PRO_TYPE_ID;				//备注1
	public int getFPRO_TYPE_ID() {
		return PRO_TYPE_ID;
	}
	public void setFPRO_TYPE_ID(int PRO_TYPE_ID) {
		this.PRO_TYPE_ID = PRO_TYPE_ID;
	}
	private String PRO_TYPE_NAME;			//备注2
	public String getFPRO_TYPE_NAME() {
		return PRO_TYPE_NAME;
	}
	public void setFPRO_TYPE_NAME(String PRO_TYPE_NAME) {
		this.PRO_TYPE_NAME = PRO_TYPE_NAME;
	}
	private int PRO_TYPE_PARENT_ID;				//备注3
	public int getFPRO_TYPE_PARENT_ID() {
		return PRO_TYPE_PARENT_ID;
	}
	public void setFPRO_TYPE_PARENT_ID(int PRO_TYPE_PARENT_ID) {
		this.PRO_TYPE_PARENT_ID = PRO_TYPE_PARENT_ID;
	}
	private String DEPART_CODE;			//备注4
	public String getFDEPART_CODE() {
		return DEPART_CODE;
	}
	public void setFDEPART_CODE(String DEPART_CODE) {
		this.DEPART_CODE = DEPART_CODE;
	}
	private int STATE;				//备注5
	public int getFSTATE() {
		return STATE;
	}
	public void setFSTATE(int STATE) {
		this.STATE = STATE;
	}
	private String PRO_TYPE_CONTENT;			//备注6
	public String getFPRO_TYPE_CONTENT() {
		return PRO_TYPE_CONTENT;
	}
	public void setFPRO_TYPE_CONTENT(String PRO_TYPE_CONTENT) {
		this.PRO_TYPE_CONTENT = PRO_TYPE_CONTENT;
	}

	public String getPROBLEMTYPE_ID() {
		return PROBLEMTYPE_ID;
	}
	public void setPROBLEMTYPE_ID(String PROBLEMTYPE_ID) {
		this.PROBLEMTYPE_ID = PROBLEMTYPE_ID;
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
	public ProblemType getProblemType() {
		return problemtype;
	}
	public void setProblemType(ProblemType problemtype) {
		this.problemtype = problemtype;
	}
	public List<ProblemType> getSubProblemType() {
		return subProblemType;
	}
	public void setSubProblemType(List<ProblemType> subProblemType) {
		this.subProblemType = subProblemType;
	}
	public boolean isHasProblemType() {
		return hasProblemType;
	}
	public void setHasProblemType(boolean hasProblemType) {
		this.hasProblemType = hasProblemType;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
