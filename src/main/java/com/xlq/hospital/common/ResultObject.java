package com.xlq.hospital.common;

/**
 * layui指定返回参数格式
 */
public class ResultObject {
	//状态编码
	private int code = 0;
	//状态信息
	private String msg;
	//数据总数
	private int count;
	//数据
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
