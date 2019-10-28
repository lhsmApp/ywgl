package com.fh.entity.coursebase;

import java.util.List;

public class CourseTree {

	private String COURSE_TYPE_ID; // 主键
	private String COURSE_TYPE_NAME; // 名称
	private String COURSE_TYPE_PARENT_ID; // 父类ID
	private String target;
	private CourseTree treelist;
	private List<CourseTree> subTreeList;
	private boolean hasTreeList = false;
	public String getCOURSE_TYPE_ID() {
		return COURSE_TYPE_ID;
	}

	public void setCOURSE_TYPE_ID(String cOURSE_TYPE_ID) {
		COURSE_TYPE_ID = cOURSE_TYPE_ID;
	}

	public String getCOURSE_TYPE_NAME() {
		return COURSE_TYPE_NAME;
	}

	public void setCOURSE_TYPE_NAME(String cOURSE_TYPE_NAME) {
		COURSE_TYPE_NAME = cOURSE_TYPE_NAME;
	}

	public String getCOURSE_TYPE_PARENT_ID() {
		return COURSE_TYPE_PARENT_ID;
	}

	public void setCOURSE_TYPE_PARENT_ID(String cOURSE_TYPE_PARENT_ID) {
		COURSE_TYPE_PARENT_ID = cOURSE_TYPE_PARENT_ID;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public CourseTree getTreelist() {
		return treelist;
	}

	public void setTreelist(CourseTree treelist) {
		this.treelist = treelist;
	}

	public List<CourseTree> getSubTreeList() {
		return subTreeList;
	}

	public void setSubTreeList(List<CourseTree> subTreeList) {
		this.subTreeList = subTreeList;
	}

	public boolean isHasTreeList() {
		return hasTreeList;
	}

	public void setHasTreeList(boolean hasTreeList) {
		this.hasTreeList = hasTreeList;
	}
}
