package com.xlq.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xlq.hospital.dao.NoticeDao;
import com.xlq.hospital.model.Notice;
import com.xlq.hospital.service.ITestMybatis;

@Service
public class TestMybatis implements ITestMybatis{

	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public void testMybatis() {
		Notice notice = new Notice();
		notice = noticeDao.selectByPrimaryKey("201812180001");
		System.out.println(notice);
	}
	
}
