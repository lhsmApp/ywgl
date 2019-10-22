package com.fh.entity;


import java.util.List;

import com.fh.util.Const;
import com.fh.util.Tools;

/**
 * 主要用于Ajax方法设置自定义的分页控件类Page中的pageStr属性
* @ClassName: PageResult2
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月6日
*
* @param <T>
 */
public class PageResult2<T> extends CommonBase{
	private Page page;//Page对象
	private List<T> rows;//返回的实际数据集合
	private String pageHtml;//最终生成的page分页Html字符窜

	public PageResult2(){
		
	}

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public String getPageHtml() {
		return pageHtml;
	}
	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}
}
