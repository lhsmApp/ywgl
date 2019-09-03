package com.fh.entity;


import java.util.List;

import com.fh.util.Const;
import com.fh.util.Tools;

public class RequestBase<T>{
	private List<T> rows;//返回的实际数据集合

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}