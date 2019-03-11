package com.xlq.hospital.controller;

import com.xlq.hospital.common.DateUtil;
import com.xlq.hospital.common.IdUtil;
import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.Order;
import com.xlq.hospital.model.Schedule;
import com.xlq.hospital.model.TypeClass;
import com.xlq.hospital.model.User;
import com.xlq.hospital.service.IUserService;
import com.xlq.hospital.service.impl.AppointmentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private IUserService userService;

	/**
	 * 根据类型查询列表（JB：疾病，KS：科室）
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "appointment/class/{type}",method= RequestMethod.GET)
	public ModelAndView appointByClass(@PathVariable String type){
		ModelAndView mv = new ModelAndView();
		List<TypeClass> typeClassList = appointmentService.queryAllContentByClass(type);
		mv.addObject("classList",typeClassList);
		if ("JB".equals(type)){
			mv.setViewName("class_disease");
		}else if("KS".equals(type)){
			mv.setViewName("class_department");
		}
		return mv;
	}

	/**
	 * 根据医生id查询医生未来七天排期
	 * @param id  医生id
	 * @return
	 */
	@RequestMapping(value = "appointment/doctor/{id}")
	public ModelAndView appointByDoctor(@PathVariable String id){
		ModelAndView mv = new ModelAndView();
		List<Schedule> scheduleList = new ArrayList<>();
		User user = userService.getUserByUserId(id);


		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Format f = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		Schedule searchSchedule = new Schedule();
		searchSchedule.setDoctorId(id);
		for (int i=0;i<7;i++){
			Schedule realSchedule = new Schedule();
			if(i>0){
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			Date thatDay = c.getTime();
			//获取时间戳
			String day = f.format(thatDay);
			//获取周几
			int w = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0){
				w = 0;
			}
			String weekName = weekDays[w];
			searchSchedule.setScheduleDate(thatDay);
			Schedule thatSchedule = appointmentService.querySchedule(searchSchedule);
			if (thatSchedule==null){
				//若当天无排班
				realSchedule.setDetail("");
			}else{
				realSchedule = thatSchedule;
				int count = realSchedule.getCount();
				int total = realSchedule.getTotal();
				if(count==total){
					realSchedule.setDetail("已满");
				}else {
					realSchedule.setDetail(String.valueOf(total-count)+"/"+String.valueOf(total));
				}
			}
			realSchedule.setWeek(weekName);
			realSchedule.setThatDay(day);

			scheduleList.add(realSchedule);
		}
		mv.addObject("user",user);
		mv.addObject("scheduleList",scheduleList);
		mv.setViewName("appointment_doctor");
		return mv;
	}

	/**
	 * 填写预约信息
	 * @param id   排期设置id
	 * @return
	 */
	@RequestMapping(value = "appointment/info/{id}")
	public ModelAndView appointmentInfo(@PathVariable String id){
		ModelAndView mv = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		//获取当前登录用户信息
		User currentUser = userService.getUserByUserId(userId);

		//获取当天排期信息
		Schedule schedule = appointmentService.queryScheduleById(id);

		mv.addObject("currentUser",currentUser);
		mv.addObject("schedule",schedule);
		mv.setViewName("appointment_info");
		return mv;
	}

	/**
	 *   生成一个新的订单
	 * @param scheduleId  排期设置id
	 * @return
	 */
	@RequestMapping(value = "appointment/submit")
	@ResponseBody
	public ResultObject appointmentSubmit(String scheduleId, String remark){
		ResultObject resultObject = new ResultObject();
		Schedule searchSchedule = new Schedule();
		searchSchedule.setId(scheduleId);
		//当天排期信息
		Schedule schedule = appointmentService.queryScheduleById(scheduleId);
		if(schedule.getCount()>=schedule.getTotal()){
			resultObject.setCode(-1);
			resultObject.setMsg("抱歉，预约人数已满，请修改时间或选择其他医生");
			return resultObject;
		}
		//获取预约医生信息
		String doctorId = schedule.getDoctorId();
		User doctor = userService.getUserByUserId(doctorId);

		//获取当前登录用户信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		User patient = userService.getUserByUserId(userId);

		//随机生成订单id和订单编码
		String orderId = IdUtil.getRandomId();
		//String orderCode = IdUtil.getRandomId();
		Order order = new Order();
		//设置订单信息
		order.setId(orderId);
		//order.setOrderCode(orderCode);
		order.setPatientId(patient.getId());
		order.setDoctorId(doctor.getId());
		order.setDepartmentId(doctor.getDoctorInfo().getDepartmentId());
		order.setDoctorName(doctor.getName());
		order.setDoctorMobile(doctor.getMobile());
		order.setPatientName(patient.getName());
		order.setPatientMobile(patient.getMobile());
		order.setPatientIdCard(patient.getPatientInfo().getIdCard());
		order.setAppointmentTime(schedule.getScheduleDate());
		order.setAppointmentFee(schedule.getCharge());
		order.setRemark(remark);
		order.setStatus("DZZ");
		order.setStatusDesc("待诊治");
		//新增订单
		int addResult = appointmentService.addOrder(order);
		schedule.setCount(schedule.getCount()+1);
		//修改已预约人数
		int updateResult = appointmentService.updateSchedule(schedule);

		if (addResult<1){
			resultObject.setCode(-1);
			resultObject.setMsg("预约失败，请联系系统管理员");
			return resultObject;
		}

		return resultObject;
	}


	/**
	 * 医生功能：
	 * 1 排期列表
	 */
	@RequestMapping(value = "doctor/schedule/list")
	public String doctorScheduleList(){
		return "doctor_schedule_list";
	}
	@RequestMapping(value = "doctor/getScheduleList")
	@ResponseBody
	public ResultObject doctorListSchedule(int page, int limit,
	                                       @RequestParam(required = false) String scheduleDateBegin,
	                                       @RequestParam(required = false) String scheduleDateEnd){
		ResultObject resultObject = new ResultObject();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		resultObject= appointmentService.queryScheduleByDoctorIdAndScheduleDate(page,limit,userId,scheduleDateBegin,scheduleDateEnd);
		return  resultObject;

	}

	/**
	 * 医生功能：
	 * 2 批量增加排期
	 */
	@RequestMapping(value = "doctor/schedule/add")
	public String doctorScheduleAdd(){
		return "doctor_schedule_add";
	}
	@RequestMapping(value = "doctor/addSchedule")
	@ResponseBody
	public ResultObject doctorAddSchedule(String scheduleDateBegin, String scheduleDateEnd, String total, String charge){
		ResultObject resultObject = new ResultObject();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		List<Date> aleadyDateList = appointmentService.queryScheduleDateByDoctorIdAndPeriod(userId,scheduleDateBegin,scheduleDateEnd);
		List<Date> newDateList = DateUtil.getDaysByDatePeriod(scheduleDateBegin,scheduleDateEnd);
		//排除已有的时间
		newDateList.removeAll(aleadyDateList);
		//批量新增排期
		for (Date date : newDateList) {
			Schedule schedule = new Schedule();
			//获取随机的id
			schedule.setId(IdUtil.getRandomId());
			schedule.setTotal(Integer.parseInt(total));
			schedule.setCount(0);
			schedule.setCharge(new BigDecimal(charge));
			schedule.setScheduleDate(date);
			schedule.setDoctorId(userId);
			int result = appointmentService.addSchedule(schedule);
		}
		resultObject.setMsg("新增成功，合计新增了"+newDateList.size()+"条");
		return resultObject;
	}

	/**
	 * 医生功能
	 * 3.医生订单列表功能
	 * @return
	 */
	@RequestMapping(value = "doctor/order/list")
	public String doctorOrderList(){
		return "doctor_order_list";
	}
	@RequestMapping(value = "doctor/getOrderList")
	@ResponseBody
	public ResultObject doctorListOrder(int page, int limit,
	                                       @RequestParam(required = false) String key,
	                                       @RequestParam(required = false) String status,
	                                       @RequestParam(required = false) String appointmentTimeBegin,
	                                       @RequestParam(required = false) String appointmentTimeEnd){
		ResultObject resultObject = new ResultObject();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		Order searchOrder = new Order();
		searchOrder.setKey(key);
		searchOrder.setStatus(status);
		searchOrder.setAppointmentTimeBegin(appointmentTimeBegin);
		searchOrder.setAppointmentTimeEnd(appointmentTimeEnd);

		resultObject= appointmentService.queryOrderByKey(page,limit,searchOrder);
		return  resultObject;

	}




}
