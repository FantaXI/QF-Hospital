package com.xlq.hospital.service;

import javax.print.DocFlavor.STRING;

import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.DoctorInfo;
import com.xlq.hospital.model.PatientInfo;
import com.xlq.hospital.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
	
	public User getUserByMobile(String mobile);
	
	public User getUserByUserId(String userId);

	public int updateUserByuserId(User user);

	/**
	 * 根据科室id查询所有医生
	 * @param departmentId
	 * @return
	 */
	public List<User> queryDoctorByDepartmentId(String departmentId);

	/**
	 * 根据疾病id查询所有医生
	 * @param DiseaseId
	 * @return
	 */
	public List<User> queryDoctorByDiseaseId(String DiseaseId);

	/**
	 * 分页查询病患用户
	 * @param page
	 * @param limit
	 * @return
	 */
	public ResultObject queryPatientListByPage(int page, int limit,String key);

	public List<User> getUserByEmail(String email);

	public List<PatientInfo> getPatientInfoByIdCard(String idCard);

	public int updatePatientInfoByPatientId(PatientInfo patientInfo);

	public int addUser(User user);

	public int addPatientInfo(PatientInfo patientInfo);

	/**
	 * 分页查询医生用户
	 * @param page
	 * @param limit
	 * @param key
	 * @return
	 */
	public ResultObject queryDoctorListByPage(int page, int limit, String key);
	public int updateDoctorInfoByUserId(DoctorInfo doctorInfo);
	public int addDoctorInfo(DoctorInfo doctorInfo);
	public List<User> queryDoctorByDepartmentIdAndScheduleDate(String departmentId, String scheduleDate);
	int updatePatientByUserId(PatientInfo patientInfo);
}
