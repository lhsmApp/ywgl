package com.fh.entity;


import java.util.List;

import com.fh.util.Const;
import com.fh.util.Tools;

/**
 * 主要用于JqDataGrid的固定数据格式绑定（total,rows,records,page），包括分页和数据结合
* @ClassName: PageResult
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月6日
*
* @param <T>
 */
public class PageResult<T> extends CommonBase{
	private int rowNum; //每页显示记录数
	private int total;//总页数
	private int records;//总记录数
	private int page;//当前页
	private List<T> rows;//返回的实际数据集合
	private Object userdata;//自定义返回的数据，例如总列表在底部返回的合计：userDataOnFooter: true, // the calculated sums and/or strings from server are put at footer row.
	//private PageData pageData;//返回的查询时的条件

	public PageResult(){
		
	}
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		if (rowNum != 0)
			this.rowNum = rowNum;
		else {
			try {
				this.rowNum = Integer.parseInt(Tools.readTxtFile(Const.PAGE));
			} catch (Exception e) {
				this.rowNum = 100;
			}
		}
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
		if(records%rowNum==0)
			total = records/rowNum;
		else
			total = records/rowNum+1;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public Object getUserdata() {
		return userdata;
	}

	public void setUserdata(Object userdata) {
		this.userdata = userdata;
	}
}
