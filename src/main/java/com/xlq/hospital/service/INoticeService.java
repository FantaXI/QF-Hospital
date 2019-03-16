package com.xlq.hospital.service;

import java.util.List;

import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.Notice;
import com.xlq.hospital.model.PatientInfo;

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

	ResultObject queryNoticeByKey(int page, int limit, Notice notice);

	ResultObject addNotice(Notice notice);

	int updateNotice(Notice notice);

	int delNotice(String id);


}
