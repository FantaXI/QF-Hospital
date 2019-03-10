package com.xlq.hospital.service;

import java.util.List;

import com.xlq.hospital.model.Comment;

public interface ICommentService {
	/**
	 * 根据comment内容查询全部
	 * @param pageNum
	 * @param pageSize
	 * @param comment
	 * @return
	 */
	List<Comment> queryCommentByPage(int pageNum, int pageSize,Comment comment); 
	
	int queryCommentByPageCount(Comment comment);
	
	public int addComment(Comment comment);
	
	public Comment getCommentById(String id);
}
