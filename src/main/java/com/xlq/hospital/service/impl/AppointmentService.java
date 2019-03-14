package com.xlq.hospital.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.dao.*;
import com.xlq.hospital.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
	@Autowired
	private TypeClassDao typeClassDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private DiseaseDao diseaseDao;
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private OrderDao orderDao;

	public List<TypeClass> queryAllContentByClass(String type){
		List<TypeClass> list = new ArrayList<>();
		list = typeClassDao.selectAllByType(type);
		for (TypeClass t:list) {
			if ("JB".equals(type)){
				List<Disease> diseaseList = diseaseDao.queryDiseaseByClassId(t.getId());
				t.setDiseaseList(diseaseList);
			}else if("KS".equals(type)){
				List<Department> departmentList = departmentDao.queryDepartmentByClassId(t.getId());
				t.setDepartmentList(departmentList);
			}
		}
		return  list;
	}

	/**
	 * 根据key查询科室
	 * @param page
	 * @param limit
	 * @param key
	 * @return
	 */
	public ResultObject queryDepartment(int page, int limit, String key){
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<Department> list = departmentDao.queryDepartmentByKey(key);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}

	/**
	 * 根据部门id查询部门
	 * @param id
	 * @return
	 */
	public Department queryDepartmentById(String id){
		return departmentDao.selectByPrimaryKey(id);
	}
	/**
	 * 根据疾病id查询疾病
	 * @param id
	 * @return
	 */
	public Disease queryDiseaseById(String id){
		return diseaseDao.selectByPrimaryKey(id);
	}
	/**
	 * 新增科室
	 */
	public int addDepartment(Department department){
		return departmentDao.insert(department);
	}

	/**
	 * 编辑科室
	 */
	public int updateDepartment(Department department){
		return departmentDao.updateByPrimaryKey(department);
	}
	/**
	 * 删除科室
	 */
	public int deleteDepartment(String id){
		return departmentDao.deleteByPrimaryKey(id);
	}


	/**
	 * 查询疾病
	 */
	public ResultObject queryDisease(int page, int limit, String key){
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<Disease> list = diseaseDao.queryDiseaseByKey(key);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}
	/**
	 * 新增疾病
	 */
	public int addDisease(Disease disease){
		return diseaseDao.insert(disease);
	}

	/**
	 * 编辑科室
	 */
	public int updateDisease(Disease disease){
		return diseaseDao.updateByPrimaryKey(disease);
	}
	/**
	 * 删除科室
	 */
	public int deleteDisease(String id){
		return diseaseDao.deleteByPrimaryKey(id);
	}

	/**
	 * 根据classId查询科室/疾病
	 */
	public ResultObject queryAllByclassId(String classId){
		ResultObject resultObject = new ResultObject();
		List<Department> departmentList = departmentDao.queryDepartmentByClassId(classId);
		resultObject.setData(departmentList);
		return resultObject;
	}

	/**
	 * 根据关键字key和日期查询排期
	 */
	public ResultObject queryScheduleByKeyAndScheduleDate(int page, int limit, String key, String scheduleDateBegin,
	                                                 String scheduleDateEnd){
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<Schedule> list = scheduleDao.queryScheduleByKeyAndScheduleDate(key,scheduleDateBegin,scheduleDateEnd);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}

	/**
	 * 增加排期设置
	 */
	public int addSchedule(Schedule schedule){
		return scheduleDao.insert(schedule);
	}
	/**
	 * 编辑排期设置
	 */
	public int updateSchedule(Schedule schedule){
		return scheduleDao.updateByPrimaryKey(schedule);
	}
	/**
	 * 删除排期设置
	 */
	public int deleteSchedule(String id){
		return scheduleDao.deleteByPrimaryKey(id);
	}

	public Schedule querySchedule(Schedule schedule){
		return scheduleDao.querySchedule(schedule);
	}
	public Schedule queryScheduleById(String id){
		return scheduleDao.queryScheduleById(id);
	}
	/**
	 * 根据关键字doctorId和日期查询排期
	 */
	public ResultObject queryScheduleByDoctorIdAndScheduleDate(int page, int limit, String doctrorId, String scheduleDateBegin,
	                                                      String scheduleDateEnd){
		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<Schedule> list = scheduleDao.queryScheduleByDoctorIdAndScheduleDate(doctrorId,scheduleDateBegin,scheduleDateEnd);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}

	/**
	 * 新增订单
	 */
	public int addOrder(Order order){
		return orderDao.insert(order);
	}

	public List<Date> queryScheduleDateByDoctorIdAndPeriod(String doctorId,
	                                                       String scheduleDateBegin, String scheduleDateEnd){
		return scheduleDao.queryScheduleDateByDoctorIdAndPeriod(doctorId,scheduleDateBegin,scheduleDateEnd);
	}

	/**
	 * 根据传入订单信息和时间段查询订单
	 * @param page
	 * @param limit
	 * @param order
	 * @return
	 */
	public ResultObject queryOrderByKey(int page, int limit, Order order){

		ResultObject resultObject = new ResultObject();
		Page objectPage = PageHelper.startPage(page,limit);
		List<Order> list = orderDao.queryOrderByKey(order);
		// 取分页信息
		PageInfo pageInfo = new PageInfo(objectPage);
		int total = (int)pageInfo.getTotal(); //获取总记录数
		resultObject.setCount(total);
		resultObject.setData(list);
		return resultObject;
	}

	public int updateOrder(Order order){
		return orderDao.updateByPrimaryKey(order);
	}
}
