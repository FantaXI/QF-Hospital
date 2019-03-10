package com.xlq.hospital.dao;

import com.xlq.hospital.model.Comment;
import java.util.List;

public interface CommentDao {
    int deleteByPrimaryKey(String id);

    int insert(Comment record);

    Comment selectByPrimaryKey(String id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);
    
    List<Comment> queryCommentByPage(Comment comment); 
    
    
    int queryCommentByPageCount(Comment comment);
    
}