package com.model2.mvc.common;


public class SearchVO {
	
	private int page; // 검색되는 페이지 수 
	String searchCondition; // 검색 분류 ex) 상품번호 : 0
	String searchKeyword; // 검색한 키워드
	int pageUnit; // 한 페이지에 몇줄 보여주는지
	
	public SearchVO(){
	}
	
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	@Override
	public String toString() {
		return "SearchVO [page=" + page + ", searchCondition=" + searchCondition + ", searchKeyword=" + searchKeyword
				+ ", pageUnit=" + pageUnit + "]";
	}
	
	
}