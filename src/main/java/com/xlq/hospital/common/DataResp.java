package com.xlq.hospital.common;

public class DataResp {
	//总记录数
	private int totalSize;
	//总页数
	private int totalPage;
	//当前页数
	private int pageNum;
	//页条数
	private int pageSize;
	//结果数据
	private Object objectResult;
	//结果  0：失败，1：成功
	private int errorCode  = 1;
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	//状态描述
	private String errorDesc;
	
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Object getObjectResult() {
		return objectResult;
	}
	public void setObjectResult(Object objectResult) {
		this.objectResult = objectResult;
	}
	

}
