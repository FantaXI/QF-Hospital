package com.xlq.hospital.dao;

import com.xlq.hospital.model.MyCollection;

import java.util.List;

public interface CollectionDao {
    int deleteByPrimaryKey(String id);

    int insert(MyCollection record);

    MyCollection selectByPrimaryKey(String id);

    List<MyCollection> selectAll();

    int updateByPrimaryKey(MyCollection record);

    List<MyCollection> queryCollection(MyCollection myCollection);
}