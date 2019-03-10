package com.xlq.hospital.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xlq.hospital.dao.NoticeDao;
import com.xlq.hospital.model.Notice;
import com.xlq.hospital.service.INoticeService;
@Service
public class NoticeService implements INoticeService{
	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public List<Notice> getNoticeByPage(int pageNum, int pageSize,String type) {
		PageHelper.startPage(pageNum, pageSize);
		List<Notice> noticeList = noticeDao.selectAll(type);
		return noticeList;
	}

	@Override
	public Notice getNoticeById(String id) {
		Notice notice = noticeDao.selectByPrimaryKey(id);
		return notice;
	}

	@Override
	public int getNoticeCount(String type) {
		
		return noticeDao.getNoticeCount(type);
	}

}
