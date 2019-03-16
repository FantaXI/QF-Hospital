package com.xlq.hospital.controller;


import com.sun.deploy.panel.ITreeNode;
import com.xlq.hospital.common.IdUtil;
import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.*;
import com.xlq.hospital.service.ICommentService;
import com.xlq.hospital.service.INoticeService;
import com.xlq.hospital.service.IUserService;
import com.xlq.hospital.service.impl.AppointmentService;
import com.xlq.hospital.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.security.Key;
import java.security.interfaces.RSAKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class AdminController {
	@Autowired
	private IUserService userService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private INoticeService noticeService;



	@RequestMapping(value = "admin/main")

	public String adminMain(){
		return "admin_main";
	}
	@RequestMapping(value = "admin/home")
	public String adminHome(){
		return "admin_home";
	}

	/**
	 * 病患用户列表
	 * @return
	 */
	@RequestMapping(value = "admin/patient/list")
	public String patientList(){
		return "admin_patient_list";
	}

	@RequestMapping(value = "admin/getPatientList")
	@ResponseBody
	public ResultObject getPatientList(int page, int limit, @RequestParam(required = false) String key){
		ResultObject resultObject = new ResultObject();
		resultObject= userService.queryPatientListByPage(page,limit,key);
		return  resultObject;
	}

	/**
	 * 病患用户编辑
	 * @return
	 */
	@RequestMapping(value = "admin/patient/edit")
	public  String patientEdit(){
		return "admin_patient_edit";
	}
	@RequestMapping(value = "admin/editPatient")
	@ResponseBody
	public ResultObject editPatient(User user,PatientInfo patientInfo,String userId,String patientId){
		ResultObject resultObject = new ResultObject();
		user.setId(userId);
		patientInfo.setId(patientId);

		//判断手机，邮箱，身份证是否重复
		User user1 = userService.getUserByMobile(user.getMobile());
		if(user1!=null && !user.getId().equals(user1.getId())){
			resultObject.setCode(-1);
			resultObject.setMsg("该号码已存在！");
			return resultObject;
		}
		List<User> list1 = userService.getUserByEmail(user.getEmail());
		if(list1.size()>0) {
			if (!user.getId().equals(list1.get(0).getId())) {
				resultObject.setCode(-1);
				resultObject.setMsg("该邮箱已存在！");
				return resultObject;
			}
		}
		List<PatientInfo> list2 = userService.getPatientInfoByIdCard(patientInfo.getIdCard());
		if(list2.size()>0) {
			if (!patientInfo.getId().equals(list2.get(0).getId())) {
				resultObject.setCode(-1);
				resultObject.setMsg("该身份证号已存在！");
				return resultObject;
			}
		}
		int editUserCount = userService.updateUserByuserId(user);
		int editPatientInfoCount = userService.updatePatientInfoByPatientId(patientInfo);
		return resultObject;
	}

	/**
	 * 病患用户增加
	 * @return
	 */
	@RequestMapping(value = "admin/patient/add")
	public  String patientAdd(){
		return "admin_patient_add";
	}
	@RequestMapping(value = "admin/addPatient")
	@ResponseBody
	public ResultObject addPatient(User user,PatientInfo patientInfo){
		ResultObject resultObject = new ResultObject();
		//判断手机，邮箱，身份证是否重复
		User user1 = userService.getUserByMobile(user.getMobile());
		if(user1!=null){
			resultObject.setCode(-1);
			resultObject.setMsg("该号码已存在！");
			return resultObject;
		}
		List<User> list1 = userService.getUserByEmail(user.getEmail());
		if(list1.size()>0){
			resultObject.setCode(-1);
			resultObject.setMsg("该邮箱已存在！");
			return resultObject;
		}
		List<PatientInfo> list2 = userService.getPatientInfoByIdCard(patientInfo.getIdCard());
		if(list2.size()>0){
			resultObject.setCode(-1);
			resultObject.setMsg("该身份证号已存在！");
			return resultObject;
		}
		//获取随机的id
		String userId = IdUtil.getRandomId();
		String patientId = IdUtil.getRandomId();
		//插入user表
		user.setId(userId);
		user.setState("A");
		user.setRoleType("1");
		userService.addUser(user);
		//插入patientInfo表
		patientInfo.setId(patientId);
		patientInfo.setUserId(userId);
		userService.addPatientInfo(patientInfo);

		return resultObject;
	}

	/**
	 * 启用/禁用用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "admin/user/state")
	@ResponseBody
	public ResultObject changeUserState(User user){
		ResultObject resultObject = new ResultObject();
		int result = userService.updateUserByuserId(user);
		if(result < 1){
			resultObject.setCode(-1);
			resultObject.setMsg("更改状态失败，请联系系统管理员");
			return resultObject;
		}
		return  resultObject;
	}


	/**
	 * 根据type查询疾病/科室大类
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "admin/class/{type}",method= RequestMethod.GET)
	@ResponseBody
	public ResultObject getClassByType(@PathVariable String type){
		ResultObject resultObject = new ResultObject();
		List<TypeClass> typeClassList = appointmentService.queryAllContentByClass(type);
		resultObject.setData(typeClassList);
		return resultObject;
	}

	/**
	 * 科室列表
	 * @return
	 */
	@RequestMapping(value = "admin/department/list")
	public String departmentList(){
		return "admin_department_list";
	}
	@RequestMapping(value = "admin/getDepartmentList")
	@ResponseBody
	public ResultObject getDepartmentList(int page, int limit, String key){
		ResultObject resultObject = new ResultObject();
		resultObject = appointmentService.queryDepartment(page,limit, key);
		return resultObject;
	}



	/**
	 * 添加科室
	 * @return
	 */
	@RequestMapping(value = "admin/department/add")
	public String departmentAdd(){
		return "admin_department_add";
	}
	@RequestMapping(value = "admin/addDepartment")
	@ResponseBody
	public ResultObject addDepartment(Department department){
		ResultObject resultObject = new ResultObject();
		String id = IdUtil.getRandomId();
		department.setId(id);
		int result = appointmentService.addDepartment(department);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("新增科室失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}

	/**
	 * 编辑科室
	 * @return
	 */
	@RequestMapping(value = "admin/department/edit")
	public String departmentEdit(){
		return "admin_department_edit";
	}
	@RequestMapping(value = "admin/editDepartment")
	@ResponseBody
	public ResultObject editDepartment(Department department){
		ResultObject resultObject = new ResultObject();
		int result = appointmentService.updateDepartment(department);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("编辑科室失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}

	/**
	 * 删除科室
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "admin/delDepartment")
	@ResponseBody
	public ResultObject delDepartment(String id){
		ResultObject resultObject = new ResultObject();
		int result = appointmentService.deleteDepartment(id);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("删除科室失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}

	/**
	 * 疾病列表
	 * @return
	 */
	@RequestMapping(value = "admin/disease/list")
	public String diseaseList(){
		return "admin_disease_list";
	}
	@RequestMapping(value = "admin/getDiseaseList")
	@ResponseBody
	public ResultObject getDiseaseList(int page, int limit, String key){
		ResultObject resultObject = new ResultObject();
		resultObject = appointmentService.queryDisease(page,limit, key);
		return resultObject;
	}
	/**
	 * 添加疾病
	 * @return
	 */
	@RequestMapping(value = "admin/disease/add")
	public String diseaseAdd(){
		return "admin_disease_add";
	}
	@RequestMapping(value = "admin/addDisease")
	@ResponseBody
	public ResultObject addDisease(Disease disease){
		ResultObject resultObject = new ResultObject();
		String id = IdUtil.getRandomId();
		disease.setId(id);
		int result = appointmentService.addDisease(disease);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("新增科室失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}

	/**
	 * 编辑疾病
	 * @return
	 */
	@RequestMapping(value = "admin/disease/edit")
	public String diseaseEdit(){
		return "admin_disease_edit";
	}
	@RequestMapping(value = "admin/editDisease")
	@ResponseBody
	public ResultObject editDepartment(Disease disease){
		ResultObject resultObject = new ResultObject();
		int result = appointmentService.updateDisease(disease);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("编辑失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}
	/**
	 * 删除疾病
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "admin/delDisease")
	@ResponseBody
	public ResultObject delDisease(String id){
		ResultObject resultObject = new ResultObject();
		int result = appointmentService.deleteDisease(id);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("删除科室失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}

	/**
	 * 根据classId查询列表
	 */
	@RequestMapping(value = "admin/listbyclass")
	@ResponseBody
	public ResultObject queryByclassId(String classId){
		ResultObject resultObject = appointmentService.queryAllByclassId(classId);
		return  resultObject;
	}

	/**
	 * 医生用户列表
	 * @return
	 */
	@RequestMapping(value = "admin/doctor/list")
	public String doctorList(){
		return "admin_doctor_list";
	}

	@RequestMapping(value = "admin/getDoctorList")
	@ResponseBody
	public ResultObject getDoctorList(int page, int limit, @RequestParam(required = false) String key){
		ResultObject resultObject = new ResultObject();
		resultObject= userService.queryDoctorListByPage(page,limit,key);
		return  resultObject;
	}
	/**
	 * 医生用户编辑
	 * @return
	 */
	@RequestMapping(value = "admin/doctor/edit")
	public  String doctorEdit(){
		return "admin_doctor_edit";
	}
	@RequestMapping(value = "admin/editDoctor")
	@ResponseBody
	public ResultObject editDoctor(User user,DoctorInfo doctorInfo,String userId,String doctorId){
		ResultObject resultObject = new ResultObject();
		user.setId(userId);
		doctorInfo.setId(doctorId);

		//判断手机，邮箱，身份证是否重复
		User user1 = userService.getUserByMobile(user.getMobile());
		if(user1!=null && !user.getId().equals(user1.getId())){
			resultObject.setCode(-1);
			resultObject.setMsg("该号码已存在！");
			return resultObject;
		}
		List<User> list1 = userService.getUserByEmail(user.getEmail());
		if(list1.size()>0) {
			if (!user.getId().equals(list1.get(0).getId())) {
				resultObject.setCode(-1);
				resultObject.setMsg("该邮箱已存在！");
				return resultObject;
			}
		}
		int editUserCount = userService.updateUserByuserId(user);
		int editPatientInfoCount = userService.updateDoctorInfoByUserId(doctorInfo);
		return resultObject;
	}
	/**
	 * 医生用户增加
	 * @return
	 */
	@RequestMapping(value = "admin/doctor/add")
	public  String doctorAdd(){
		return "admin_doctor_add";
	}
	@RequestMapping(value = "admin/addDoctor")
	@ResponseBody
	public ResultObject addDoctor(User user,DoctorInfo doctorInfo){
		ResultObject resultObject = new ResultObject();
		//判断手机，邮箱，身份证是否重复
		User user1 = userService.getUserByMobile(user.getMobile());
		if(user1!=null){
			resultObject.setCode(-1);
			resultObject.setMsg("该号码已存在！");
			return resultObject;
		}
		List<User> list1 = userService.getUserByEmail(user.getEmail());
		if(list1.size()>0){
			resultObject.setCode(-1);
			resultObject.setMsg("该邮箱已存在！");
			return resultObject;
		}

		//获取随机的id
		String userId = IdUtil.getRandomId();
		String doctorId = IdUtil.getRandomId();
		//插入user表
		user.setId(userId);
		user.setState("A");
		user.setRoleType("2");
		userService.addUser(user);
		//插入patientInfo表
		doctorInfo.setId(doctorId);
		doctorInfo.setUserId(userId);
		userService.addDoctorInfo(doctorInfo);

		return resultObject;
	}

	/**
	 *  根据部门id和日期查询当天无排期的医生
	 */
	@RequestMapping(value = "admin/didandsd")
	@ResponseBody
	public ResultObject queryDoctorByDepartmentIdAndScheduleDate(String departmentId, String scheduleDate){
		ResultObject resultObject = new ResultObject();
		List<User> list = userService.queryDoctorByDepartmentIdAndScheduleDate(departmentId,scheduleDate);
		resultObject.setData(list);
		return resultObject;
	}
	/**
	 * 排期列表
	 * @return
	 */
	@RequestMapping(value = "admin/schedule/list")
	public String scheduleList(){
		return "admin_schedule_list";
	}

	@RequestMapping(value = "admin/getScheduleList")
	@ResponseBody
	public ResultObject getDoctorList(int page, int limit,
	                                  @RequestParam(required = false) String key,
	                                  @RequestParam(required = false) String scheduleDateBegin,
	                                  @RequestParam(required = false) String scheduleDateEnd ){
		ResultObject resultObject = new ResultObject();
		resultObject= appointmentService.queryScheduleByKeyAndScheduleDate(page,limit,key,scheduleDateBegin,scheduleDateEnd);
		return  resultObject;
	}
	/**
	 * 排期设置增加
	 * @return
	 */
	@RequestMapping(value = "admin/schedule/add")
	public  String scheduleAdd(){
		return "admin_schedule_add";
	}
	@RequestMapping(value = "admin/addSchedule")
	@ResponseBody
	public ResultObject addSchedule(@RequestParam(required = false, value = "doctorIdList[]")List<String> doctorIdList, String scheduleDate, String total, String charge){
		ResultObject resultObject = new ResultObject();
		if(ListUtils.isEmpty(doctorIdList)){
			resultObject.setCode(-1);
			resultObject.setMsg("请勾选至少一个医生");
			return resultObject;
		}
		DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
		Date date = null;
		try {
			date =  formatter.parse(scheduleDate);}
		catch (Exception e){
			e.printStackTrace();
		}
		for (String doctorId:doctorIdList) {
			Schedule schedule = new Schedule();
			//获取随机的id
			schedule.setId(IdUtil.getRandomId());
			schedule.setTotal(Integer.parseInt(total));
			schedule.setCount(0);
			schedule.setCharge(new BigDecimal(charge));
			schedule.setScheduleDate(date);
			schedule.setDoctorId(doctorId);
			int result = appointmentService.addSchedule(schedule);
		}

		return resultObject;
	}

	/**
	 * 排期设置编辑
	 * @return
	 */
	@RequestMapping(value = "admin/schedule/edit")
	public  String scheduleEdit(){
		return "admin_schedule_edit";
	}
	@RequestMapping(value = "admin/editSchedule")
	@ResponseBody
	public ResultObject editSchedule(Schedule schedule){
		ResultObject resultObject = new ResultObject();
		if(schedule.getTotal() < schedule.getCount()){
			resultObject.setCode(-1);
			resultObject.setMsg("当天预约总数不能超过已预约数");
			return resultObject;
		}
		int result = appointmentService.updateSchedule(schedule);
		return resultObject;
	}

	/**
	 * 删除疾病
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "admin/delSchedule")
	@ResponseBody
	public ResultObject delSchedule(String id){
		ResultObject resultObject = new ResultObject();
		int result = appointmentService.deleteSchedule(id);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("删除排期设置失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}

	/**
	 * 留言列表
	 *
	 */
	@RequestMapping(value = "admin/comment/list")
	public String commentList(){
		return "admin_comment_list";
	}
	@RequestMapping(value = "admin/getCommentList")
	@ResponseBody
	public ResultObject getCommentList(int page, int limit,
	                                  @RequestParam(required = false) String key,
	                                  @RequestParam(required = false) String commentType){
		ResultObject resultObject = new ResultObject();
		Comment comment = new Comment();
		comment.setKey(key);
		comment.setCommentType(commentType);
		resultObject= commentService.queryCommentByAdmin(page,limit,comment);
		return  resultObject;
	}

	/**
	 * 删除留言
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "admin/comment/delete")
	@ResponseBody
	public ResultObject delComment(String id, String delAll){
		ResultObject resultObject = new ResultObject();
		int result = commentService.delCommentByInstruct(id,delAll);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("删除留言设置失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}


	/**
	 * 公告列表
	 */
	@RequestMapping(value = "admin/notice/list")
	public String noticeList(){
		return "admin_notice_list";
	}
	@RequestMapping(value = "admin/getNoticeList")
	@ResponseBody
	public ResultObject getNoticeList(int page, int limit,
	                                   @RequestParam(required = false) String key,
	                                   @RequestParam(required = false) String state){
		ResultObject resultObject = new ResultObject();
		Notice notice = new Notice();
		notice.setNoticeTitle(key);
		notice.setState(state);
		resultObject= noticeService.queryNoticeByKey(page,limit,notice);
		return  resultObject;
	}
	/**
	 * 新增公告
	 */
	@RequestMapping(value = "admin/notice/add")
	public String adminNoticeAdd(){
		return "admin_notice_add";
	}
	@RequestMapping(value = "admin/addNotice")
	@ResponseBody
	public ResultObject adminAddNotice(Notice notice){
		ResultObject resultObject = noticeService.addNotice(notice);
		return resultObject;
	}
	/**
	 * 预览文章
	 */
	@RequestMapping(value = "admin/notice/preview")
	public String adminNoticePreview(){
		return "admin_notice_preview";
	}

	/**
	 * 编辑文章
	 */
	@RequestMapping(value = "admin/notice/edit")
	public  String noticeEdit(){
		return "admin_notice_edit";
	}
	@RequestMapping(value = "admin/editNotice")
	@ResponseBody
	public ResultObject editNotice(Notice notice){
		ResultObject resultObject = new ResultObject();
		int result = noticeService.updateNotice(notice);
		return resultObject;
	}
	/**
	 * 删除文章
	 */
	@RequestMapping(value = "admin/delNotice")
	@ResponseBody
	public ResultObject delNotice(String id){
		ResultObject resultObject = new ResultObject();
		int result = noticeService.delNotice(id);
		if (result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("删除失败，请联系管理员");
			return resultObject;
		}
		return resultObject;
	}

}
