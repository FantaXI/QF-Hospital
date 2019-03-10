package com.xlq.hospital.service.impl;


import java.util.List;

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
	
}
