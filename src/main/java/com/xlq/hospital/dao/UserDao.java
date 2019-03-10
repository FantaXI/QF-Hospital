package com.xlq.hospital.dao;

import com.xlq.hospital.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
    /**
     * 根据手机号码查找用户
     */
    public User getUserByMobile(String mobile);

    public List<User> queryDoctorByDepartmentId(String departmentId);

    public List<User> queryDoctorByDiseaseId(String diseaseId);

    public List<User> queryPatientList(@Param(value="key") String key);

    public List<User> queryDoctorList(@Param(value="key") String key);
    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    public List<User> getUserByEmail(String email);

    public List<User> queryDoctorByDepartmentIdAndScheduleDate(@Param(value = "departmentId") String departmentId, @Param(value = "scheduleDate") String scheduleDate);
}