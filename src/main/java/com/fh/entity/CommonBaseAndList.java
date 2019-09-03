package com.fh.entity;

import java.util.List;

import com.fh.util.PageData;

/**
*
 */
public class CommonBaseAndList{
	private List<PageData> list;
	private CommonBase commonBase;
	public List<PageData> getList() {
		return list;
	}
	public void setList(List<PageData> list) {
		this.list = list;
	}
	public CommonBase getCommonBase() {
		return commonBase;
	}
	public void setCommonBase(CommonBase commonBase) {
		this.commonBase = commonBase;
	}
}