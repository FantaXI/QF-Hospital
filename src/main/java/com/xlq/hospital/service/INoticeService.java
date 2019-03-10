package com.xlq.hospital.service;

import java.util.List;

import com.xlq.hospital.model.Notice;

public interface INoticeService {
	/**
	 * 分页查询公告
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Notice> getNoticeByPage(int pageNum,int pageSize,String type);
	
	int getNoticeCount(String type);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Notice getNoticeById(String id);

}
