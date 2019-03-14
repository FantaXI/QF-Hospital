package com.xlq.hospital.service;

import java.util.List;

import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.Comment;
import com.xlq.hospital.model.Notice;

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

	public ResultObject queryCommentByAdmin(int page, int limit, Comment comment);

	public int delCommentByInstruct(String id, String delAll);
}
