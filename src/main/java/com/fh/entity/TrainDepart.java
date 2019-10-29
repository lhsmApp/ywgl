package com.fh.entity;

import java.util.List;

/** 
 * 说明：培训基础 实体类
 * 创建人：jiachao
 * 创建时间：2019-10-23
 */
public class TrainDepart{ 
	
	private String TRAINDEPART_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private TrainDepart traindepart;
	private List<TrainDepart> subTrainDepart;
	private boolean hasTrainDepart = false;
	private String treeurl;
	
	private int DEPART_ID;				//备注1
	public int getFDEPART_ID() {
		return DEPART_ID;
	}
	public void setFDEPART_ID(int DEPART_ID) {
		this.DEPART_ID = DEPART_ID;
	}
	private String DEPART_CODE;			//备注2
	public String getFDEPART_CODE() {
		return DEPART_CODE;
	}
	public void setFDEPART_CODE(String DEPART_CODE) {
		this.DEPART_CODE = DEPART_CODE;
	}
	private String DEPART_NAME;			//备注3
	public String getFDEPART_NAME() {
		return DEPART_NAME;
	}
	public void setFDEPART_NAME(String DEPART_NAME) {
		this.DEPART_NAME = DEPART_NAME;
	}
	private String DEPART_PARENT_CODE;			//备注4
	public String getFDEPART_PARENT_CODE() {
		return DEPART_PARENT_CODE;
	}
	public void setFDEPART_PARENT_CODE(String DEPART_PARENT_CODE) {
		this.DEPART_PARENT_CODE = DEPART_PARENT_CODE;
	}
	private String LEADER;			//备注5
	public String getFLEADER() {
		return LEADER;
	}
	public void setFLEADER(String LEADER) {
		this.LEADER = LEADER;
	}
	private String STATE;			//备注6
	public String getFSTATE() {
		return STATE;
	}
	public void setFSTATE(String STATE) {
		this.STATE = STATE;
	}

	public String getTRAINDEPART_ID() {
		return TRAINDEPART_ID;
	}
	public void setTRAINDEPART_ID(String TRAINDEPART_ID) {
		this.TRAINDEPART_ID = TRAINDEPART_ID;
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
	public TrainDepart getTrainDepart() {
		return traindepart;
	}
	public void setTrainDepart(TrainDepart traindepart) {
		this.traindepart = traindepart;
	}
	public List<TrainDepart> getSubTrainDepart() {
		return subTrainDepart;
	}
	public void setSubTrainDepart(List<TrainDepart> subTrainDepart) {
		this.subTrainDepart = subTrainDepart;
	}
	public boolean isHasTrainDepart() {
		return hasTrainDepart;
	}
	public void setHasTrainDepart(boolean hasTrainDepart) {
		this.hasTrainDepart = hasTrainDepart;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
