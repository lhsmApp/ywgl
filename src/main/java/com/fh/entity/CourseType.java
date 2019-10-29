package com.fh.entity;

import java.util.List;

/** 
 * 说明：知识结构分类 实体类
 * 创建人：jiachao
 * 创建时间：2019-10-25
 */
public class CourseType{ 
	
	private String COURSETYPE_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private CourseType coursetype;
	private List<CourseType> subCourseType;
	private boolean hasCourseType = false;
	private String treeurl;
	
	private int COURSE_TYPE_ID;				//备注1
	public int getFCOURSE_TYPE_ID() {
		return COURSE_TYPE_ID;
	}
	public void setFCOURSE_TYPE_ID(int COURSE_TYPE_ID) {
		this.COURSE_TYPE_ID = COURSE_TYPE_ID;
	}
	private String COURSE_TYPE_NAME;			//备注2
	public String getFCOURSE_TYPE_NAME() {
		return COURSE_TYPE_NAME;
	}
	public void setFCOURSE_TYPE_NAME(String COURSE_TYPE_NAME) {
		this.COURSE_TYPE_NAME = COURSE_TYPE_NAME;
	}
	private String LEADER;			//备注3
	public String getFLEADER() {
		return LEADER;
	}
	public void setFLEADER(String LEADER) {
		this.LEADER = LEADER;
	}
	private String STATE;			//备注4
	public String getFSTATE() {
		return STATE;
	}
	public void setFSTATE(String STATE) {
		this.STATE = STATE;
	}
	private int COURSE_TYPE_PARENT_ID;				//备注5
	public int getFCOURSE_TYPE_PARENT_ID() {
		return COURSE_TYPE_PARENT_ID;
	}
	public void setFCOURSE_TYPE_PARENT_ID(int COURSE_TYPE_PARENT_ID) {
		this.COURSE_TYPE_PARENT_ID = COURSE_TYPE_PARENT_ID;
	}

	public String getCOURSETYPE_ID() {
		return COURSETYPE_ID;
	}
	public void setCOURSETYPE_ID(String COURSETYPE_ID) {
		this.COURSETYPE_ID = COURSETYPE_ID;
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
	public CourseType getCourseType() {
		return coursetype;
	}
	public void setCourseType(CourseType coursetype) {
		this.coursetype = coursetype;
	}
	public List<CourseType> getSubCourseType() {
		return subCourseType;
	}
	public void setSubCourseType(List<CourseType> subCourseType) {
		this.subCourseType = subCourseType;
	}
	public boolean isHasCourseType() {
		return hasCourseType;
	}
	public void setHasCourseType(boolean hasCourseType) {
		this.hasCourseType = hasCourseType;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
