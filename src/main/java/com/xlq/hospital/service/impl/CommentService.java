package com.xlq.hospital.service.impl;


import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xlq.hospital.common.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xlq.hospital.dao.CommentDao;
import com.xlq.hospital.model.Comment;
import com.xlq.hospital.service.ICommentService;
@Service
public class CommentService implements ICommentService{
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<Comment> queryCommentByPage(int pageNum, int pageSize,Comment comment) {
		PageHelper.startPage(pageNum, pageSize);
		return commentDao.queryCommentByPage(comment);
	}

	@Override
	public int queryCommentByPageCount(Comment comment) {
		return commentDao.queryCommentByPageCount(comment);
	}
	
	public int addComment(Comment comment) {
		return commentDao.insert(comment);
	}

	@Override
	public Comment getCommentById(String id) {
		
		return commentDao.selectByPrimaryKey(id);
	}

	@Override
	public ResultObject queryCommentByAdmin(int page, int limit,Comment comment) {
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<Comment> list = commentDao.queryCommentByAdmin(comment);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}

	public int delCommentByInstruct(String id, String delAll){
		Comment comment = commentDao.selectByPrimaryKey(id);
		if ("2".equals(comment.getCommentType())){
				if("Y".equals(delAll)){
				commentDao.deleteByMainId(comment.getMainId());
			}
		}else if("1".equals(comment.getCommentType())){
			commentDao.deleteByMainId(comment.getId());
		}
		return commentDao.deleteByPrimaryKey(comment.getId());
	}


}
