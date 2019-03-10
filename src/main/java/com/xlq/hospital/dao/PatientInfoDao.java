package com.xlq.hospital.dao;

import com.xlq.hospital.model.PatientInfo;
import java.util.List;

import javax.print.DocFlavor.STRING;

public interface PatientInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(PatientInfo record);

    PatientInfo selectByPrimaryKey(String id);
    
    PatientInfo getPatientInfoByUserId(String userId);

    List<PatientInfo> selectAll();

    int updateByPrimaryKey(PatientInfo record);

    public List<PatientInfo> getPatientInfoByIdCard(String idCard);

}