package com.fh.entity.coursebase;

import java.util.List;

public class CourseDetail {

	private int CHAPTER_ID;
	private String CHAPTER_NAME;
	private int CHAPTER_PARENT_ID;
	private List<CourseDetail> subCourseDetails;

	public CourseDetail() {
	}

	public int getCHAPTER_ID() {
		return CHAPTER_ID;
	}

	public void setCHAPTER_ID(int cHAPTER_ID) {
		CHAPTER_ID = cHAPTER_ID;
	}

	public String getCHAPTER_NAME() {
		return CHAPTER_NAME;
	}

	public void setCHAPTER_NAME(String cHAPTER_NAME) {
		CHAPTER_NAME = cHAPTER_NAME;
	}

	public int getCHAPTER_PARENT_ID() {
		return CHAPTER_PARENT_ID;
	}

	public void setCHAPTER_PARENT_ID(int cHAPTER_PARENT_ID) {
		CHAPTER_PARENT_ID = cHAPTER_PARENT_ID;
	}

	public List<CourseDetail> getSubCourseDetails() {
		return subCourseDetails;
	}

	public void setSubCourseDetails(List<CourseDetail> subCourseDetails) {
		this.subCourseDetails = subCourseDetails;
	}

}
