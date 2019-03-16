package com.xlq.hospital.service.impl;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xlq.hospital.common.IdUtil;
import com.xlq.hospital.common.ResultObject;
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

	@Override
	public ResultObject queryNoticeByKey(int page, int limit, Notice notice) {
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<Notice> list = noticeDao.queryNoticeByKey(notice);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}
	@Override
	public ResultObject addNotice(Notice notice){
		ResultObject resultObject = new ResultObject();
		notice.setId(IdUtil.getRandomId());
		int result = noticeDao.insert(notice);
		if (result < 1){
			resultObject.setCode(-1);
			resultObject.setMsg("新增失败，请联系管理员！");
			return resultObject;
		}
		return resultObject;
	}

	@Override
	public int updateNotice(Notice notice) {
		return noticeDao.updateByPrimaryKey(notice);
	}

	@Override
	public int delNotice(String id) {
		return noticeDao.deleteByPrimaryKey(id);
	}


}
