package com.xlq.hospital.dao;

import com.xlq.hospital.model.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentDao {
    int deleteByPrimaryKey(String id);

    int insert(Department record);

    Department selectByPrimaryKey(String id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);

    List<Department> queryDepartmentByClassId(String classId);

    List<Department> queryDepartmentByKey(@Param(value = "key") String key);
}