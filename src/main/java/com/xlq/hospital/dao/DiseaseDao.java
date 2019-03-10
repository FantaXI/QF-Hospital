package com.xlq.hospital.dao;

import com.xlq.hospital.model.Disease;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiseaseDao {
    int deleteByPrimaryKey(String id);

    int insert(Disease record);

    Disease selectByPrimaryKey(String id);

    List<Disease> selectAll();

    int updateByPrimaryKey(Disease record);

    List<Disease> queryDiseaseByClassId(String classId);

    List<Disease> queryDiseaseByKey(@Param(value = "key") String key);
}