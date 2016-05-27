package com.atguigu.crm.orm;

import java.util.List;

public class Page<T> {

	// 当前页数
	private int pageNo;
	// 每页记录数
	private int pageSize = 3;

	private List<T> content;
	// 总记录数
	private int totalElements;

	public Page() {
	}

	public void setPageNo(int pageNo) {
		// 检验pageNo合法性
		if (pageNo < 1) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	public int getPageNo() {
		// 校验pageNo的合法性
		if (this.pageNo > getTotalPages()) {
			this.pageNo = getTotalPages();
		}
		return pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public List<T> getContent() {
		return content;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;

	}

	public int getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		int totalPages = this.totalElements / pageSize;

		if (totalElements % pageSize != 0) {
			 ++totalPages;
		}
		return totalPages;
	}

	public boolean isHasNext() {

		return this.pageNo < this.getTotalPages();
	}

	public int getNext() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}

	public boolean isHasPrev() {

		return this.pageNo > 1;
	}

	public int getPrev() {

		if (isHasPrev()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}
}
