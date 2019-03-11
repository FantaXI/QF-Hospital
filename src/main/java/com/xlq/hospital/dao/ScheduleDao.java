package com.xlq.hospital.dao;

import com.xlq.hospital.model.Schedule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScheduleDao {
    int deleteByPrimaryKey(String id);

    int insert(Schedule record);

    Schedule selectByPrimaryKey(String id);

    List<Schedule> selectAll();

    int updateByPrimaryKey(Schedule record);

    /**
     * 根据关键字key和日期查询排期
     */
    List<Schedule> queryScheduleByKeyAndScheduleDate(@Param(value = "key") String key, @Param(value = "scheduleDateBegin")String scheduleDateBegin, @Param(value = "scheduleDateEnd")String scheduleDateEnd);

    Schedule querySchedule(Schedule schedule);

    /**
     * 根据id查询排期
     * @param id
     * @return
     */
    Schedule queryScheduleById(String id);

    List<Date> queryScheduleDateByDoctorIdAndPeriod(@Param(value = "doctorId") String doctorId, @Param(value = "scheduleDateBegin")String scheduleDateBegin, @Param(value = "scheduleDateEnd")String scheduleDateEnd);

    List<Schedule> queryScheduleByDoctorIdAndScheduleDate(@Param(value = "doctorId") String doctorId, @Param(value = "scheduleDateBegin")String scheduleDateBegin, @Param(value = "scheduleDateEnd")String scheduleDateEnd);
}