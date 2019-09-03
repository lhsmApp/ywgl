package com.fh.entity;

import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;

/**
 * JqGrid分页
 * 
 * @ClassName: JqPage
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年6月6日
 *
 */
public class JqPage {

	private int rows;// Jqgrid传递过来的每页显示记录数
	private int rowNum; // 每页显示记录数
	private String sord; // 排序方式
	private int page; // 当前页
	private int currentResult; // 当前记录起始索引
	private boolean _search;// 是否查询
	private String sidx;// 排序字段
	private String filters;// 高级搜索的查询条件
	private String filterWhereResult;// 经过处理filters后的生成的where结果

	private String searchField;// 单条件查询条件字段
	private String searchOper;// 单条件查询操作符
	private String searchString;// 单条件查询搜索的关键字

	private boolean entityOrField; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private PageData pd = new PageData();

	public JqPage() {
		/*try {
			this.rowNum = Integer.parseInt(Tools.readTxtFile(Const.PAGE));
		} catch (Exception e) {
			this.rowNum = 15;
		}*/
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		if (rows != 0)
			this.rowNum = rows;
		else {
			try {
				this.rowNum = Integer.parseInt(Tools.readTxtFile(Const.PAGE));
			} catch (Exception e) {
				this.rowNum = 15;
			}
		}
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public boolean is_search() {
		return _search;
	}

	public void set_search(boolean _search) {
		this._search = _search;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;

		// 测试获取FilterWhereResult
		/*
		 * String query = new SqlTools().constructWhere(js, new CallBack(){
		 * public String executeData(String f, String o, String d) {
		 * if(o.equals("bw") || o.equals("bn")) return "'" + d + "%'"; else if
		 * (o.equals("ew") || o.equals("en")) return "'%"+d+"'"; else if
		 * (o.equals("cn") || o.equals("nc")) return "'%" +d+ "%'"; else return
		 * "'" +d+ "'"; } public String executeField(String f) {
		 * if(f.equals("deptName"))return "o.short_name"; else
		 * if(f.equals("fromType"))return "t.from_type"; else
		 * if(f.equals("orderCode"))return "t.order_code"; else
		 * if(f.equals("applicantName"))return "ta.fullname"; else
		 * if(f.equals("transType"))return "t.trans_type"; else
		 * if(f.equals("createTime"))return "t.create_time"; else
		 * if(f.equals("startTime"))return "t.start_time"; else
		 * if(f.equals("realFinishTime"))return "t.real_finish_time"; else
		 * if(f.equals("status"))return "t.status"; else return f; } });
		 */
	}

	public String getFilterWhereResult() {
		return filterWhereResult;
	}

	public void setFilterWhereResult(String filterWhereResult) {
		this.filterWhereResult = filterWhereResult;
	}

	public int getCurrentResult() {
		currentResult = (page - 1) * rowNum;
		if (currentResult < 0)
			currentResult = 0;
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
