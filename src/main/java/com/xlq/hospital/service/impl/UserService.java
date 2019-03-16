package com.xlq.hospital.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.DoctorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xlq.hospital.dao.DoctorInfoDao;
import com.xlq.hospital.dao.PatientInfoDao;
import com.xlq.hospital.dao.UserDao;
import com.xlq.hospital.model.PatientInfo;
import com.xlq.hospital.model.User;
import com.xlq.hospital.service.IUserService;

import javax.print.Doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserDao userDao;
	@Autowired
	private DoctorInfoDao doctorInfoDao;
	@Autowired
	private PatientInfoDao patientInfoDao;
	@Override
	public User getUserByMobile(String mobile) {
		User user = userDao.getUserByMobile(mobile);
		return user;
	}
	@Override
	public User getUserByUserId(String userId) {
		User user = userDao.selectByPrimaryKey(userId);
		if (user!=null) {
			if ("1".equals(user.getRoleType())) {
				user.setPatientInfo(patientInfoDao.getPatientInfoByUserId(userId));
			}else if ("2".equals(user.getRoleType())) {
				user.setDoctorInfo(doctorInfoDao.getDoctorInfoByUserId(userId));
			}
			return user;
		}
		return null;
	}

	@Override
	public int updateUserByuserId(User user) {
		return userDao.updateByPrimaryKey(user);
	}
	/**
	 * 根据科室id查询所有医生
	 * @param departmentId
	 * @return
	 */
	@Override
	public List<User> queryDoctorByDepartmentId(String departmentId) {
		return userDao.queryDoctorByDepartmentId(departmentId);
	}
	/**
	 * 根据疾病id查询所有医生
	 * @param diseaseId
	 * @return
	 */
	@Override
	public List<User> queryDoctorByDiseaseId(String diseaseId) {
		return userDao.queryDoctorByDiseaseId(diseaseId);
	}

	/**
	 * 分页查询病患用户
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public ResultObject queryPatientListByPage(int page, int limit, String key) {
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<User> patientList = userDao.queryPatientList(key);
		resultObject.setData(patientList);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		return resultObject;
	}

	@Override
	public List<User> getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	public List<PatientInfo> getPatientInfoByIdCard(String idCard) {
		return patientInfoDao.getPatientInfoByIdCard(idCard);
	}

	@Override
	public int updatePatientInfoByPatientId(PatientInfo patientInfo) {
		return patientInfoDao.updateByPrimaryKey(patientInfo);
	}

	@Override
	public int addUser(User user) {
		return userDao.insert(user);
	}

	@Override
	public int addPatientInfo(PatientInfo patientInfo) {
		return patientInfoDao.insert(patientInfo);
	}

	/**
	 * 分页查询医生用户
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public ResultObject queryDoctorListByPage(int page, int limit, String key) {
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<User> doctorList = userDao.queryDoctorList(key);
		resultObject.setData(doctorList);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		return resultObject;
	}
	/**
	 * 编辑医生用户信息
	 */
	@Override
	public int updateDoctorInfoByUserId(DoctorInfo doctorInfo) {
		return doctorInfoDao.updateByPrimaryKey(doctorInfo);
	}

	@Override
	public int addDoctorInfo(DoctorInfo doctorInfo) {
		return doctorInfoDao.insert(doctorInfo);
	}

	@Override
	public List<User> queryDoctorByDepartmentIdAndScheduleDate(String departmentId, String scheduleDate){
		return userDao.queryDoctorByDepartmentIdAndScheduleDate(departmentId,scheduleDate);
	}

	@Override
	public int updatePatientByUserId(PatientInfo patientInfo) {
		return patientInfoDao.updatePatientByUserId(patientInfo);
	}
}
