package com.xlq.hospital.dao;

import com.xlq.hospital.model.DoctorInfo;
import java.util.List;

import javax.print.DocFlavor.STRING;

public interface DoctorInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(DoctorInfo record);

    DoctorInfo selectByPrimaryKey(String id);
    
    DoctorInfo getDoctorInfoByUserId(String userId);

    List<DoctorInfo> selectAll();

    int updateByPrimaryKey(DoctorInfo record);
}