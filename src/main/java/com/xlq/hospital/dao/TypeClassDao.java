package com.xlq.hospital.dao;

import com.xlq.hospital.model.TypeClass;
import java.util.List;

public interface TypeClassDao {
    int deleteByPrimaryKey(String id);

    int insert(TypeClass record);

    TypeClass selectByPrimaryKey(String id);

    List<TypeClass> selectAll();

    List<TypeClass> selectAllByType(String type);

    int updateByPrimaryKey(TypeClass record);
}