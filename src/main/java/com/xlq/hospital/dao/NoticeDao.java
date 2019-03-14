package com.xlq.hospital.dao;

import com.xlq.hospital.model.Notice;
import java.util.List;

public interface NoticeDao {
    int deleteByPrimaryKey(String id);

    int insert(Notice record);

    Notice selectByPrimaryKey(String id);

    List<Notice> selectAll(String type);
    
    int getNoticeCount(String type);
    
    int updateByPrimaryKey(Notice record);

    List<Notice> queryNoticeByKey(Notice notice);
}