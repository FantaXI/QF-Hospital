package com.xlq.hospital.controller;

import com.xlq.hospital.common.IdUtil;
import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.*;
import com.xlq.hospital.service.impl.AppointmentService;
import com.xlq.hospital.service.impl.CollectionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.xlq.hospital.common.DataResp;
import com.xlq.hospital.service.IUserService;
import com.xlq.hospital.shiro.MyRealm;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
		
	@Autowired
	private IUserService userService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private MyRealm myRealm;
	@Autowired
	private CollectionService collectionService;


	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("userLogin")
	@ResponseBody
	public DataResp userLogin(String mobile,String password) {
		
		DataResp dataResp = new DataResp();
		try {
	        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
	        defaultSecurityManager.setRealm(myRealm);
	        
	        SecurityUtils.setSecurityManager(defaultSecurityManager);
	        // 获取Subject对象
	        Subject subject = SecurityUtils.getSubject();
	        UsernamePasswordToken token = new UsernamePasswordToken(mobile, password);
	        
	        subject.login(token);
	        User user = (User)subject.getPrincipal();
	        //获取session并存放当前用户数据
	        Session session = subject.getSession();
	        session.setAttribute("user", user);
	        session.setAttribute("userId", user.getId());
	        dataResp.setObjectResult(user.getRoleType());

	        //if (subject.isAuthenticated()) 
	        return dataResp;
	        
	    	} catch (IncorrectCredentialsException e1) {
	        e1.printStackTrace();
	        dataResp.setErrorCode(0);
	        dataResp.setErrorDesc(e1.getMessage());
	        return dataResp;
	    	} catch (LockedAccountException e2) {
	        e2.printStackTrace();
	        dataResp.setErrorCode(0);
	        dataResp.setErrorDesc(e2.getMessage());
	        return dataResp;
	    	} 
			catch (UnknownAccountException e3) {
	        e3.printStackTrace();
	        dataResp.setErrorCode(0);
	        dataResp.setErrorDesc(e3.getMessage());
	        return dataResp;
	    	} 
			catch (Exception e) {
	        e.printStackTrace();
	        dataResp.setErrorCode(0);
	        dataResp.setErrorDesc("登录失败");
	        return dataResp;
	    	}   
		
		
	}
	@RequestMapping("logout")
	public String userLogout() {
		System.out.println("退出当前用户");
		
        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Session session = subject.getSession();
        session.removeAttribute("user");
		return "index";
	}
	@RequestMapping(value = "user/mine")
	public ModelAndView getCurrentUser() {
		ModelAndView modelAndView = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		User user = userService.getUserByUserId(userId);
		modelAndView.addObject("currentUser", user);
		modelAndView.setViewName("mine");
		return modelAndView;
	}
//	@RequestMapping("/user/upload")
//	public String pictureUpload(MultipartFile file){
//		DataResp dataResp = new DataResp();
//		try {
//			//1、给上传的图片生成新的文件名
//			//1.1获取原始文件名
//			String oldName = file.getOriginalFilename();
//			//1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
//			String newName = IdUtil.getRandomId();
//			newName = newName + oldName.substring(oldName.lastIndexOf("."));
//			//1.3生成文件在服务器端存储的子目录
//			String filePath = "/user";
//
//			//2、把前端输入信息，包括图片的url保存到数据库
//			Subject subject = SecurityUtils.getSubject();
//			Session session = subject.getSession();
//			String userId = (String)session.getAttribute("userId");
//			User user = userService.getUserByUserId(userId);
//			user.setImageUrl(baseUrl + filePath + "/" + newName);
//			// 插入数据库
//			userService.updateUserByuserId(user);
//
//			//3、把图片上传到图片服务器
//			//3.1获取上传的io流
//			InputStream input = file.getInputStream();
//
//			//3.2调用FtpUtil工具类进行上传
//			boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);
//			if(result) {
//				return "redirect:/user/mine";
//			}else {
//				dataResp.setErrorCode(0);
//				dataResp.setErrorDesc("上传失败！");
//				return "redirect:/user/mine";
//			}
//		} catch (IOException e) {
//			dataResp.setErrorCode(0);
//			dataResp.setErrorDesc("上传失败！");
//			return "redirect:/user/mine";
//		}
//	}
	@RequestMapping(value = "doctorlist/department/{departmentId}")
	public ModelAndView doctorListByDepartmentId(@PathVariable String departmentId ){
		ModelAndView mv = new ModelAndView();
		List<User> doctorList = userService.queryDoctorByDepartmentId(departmentId);
		Department department = appointmentService.queryDepartmentById(departmentId);
		mv.addObject("department",department);
		//todo 调用service查询评分、历史单数、被收藏数

		mv.addObject("doctorList",doctorList);
		mv.setViewName("doctor_list");
		return mv;
	}
	@RequestMapping(value = "/quickappointment/doctorlist/department/{departmentId}")
	@ResponseBody
	public ResultObject quickappointmentDoctorListByDepartmentId(@PathVariable String departmentId ){
		ResultObject resultObject = new ResultObject();
		List<User> doctorList = userService.queryDoctorByDepartmentId(departmentId);

		List<Map> list = new ArrayList<>();
		for (User user : doctorList) {
			Map map = new HashMap();
			map.put("id",user.getId());
			map.put("name",user.getName());
			list.add(map);
		}
		resultObject.setData(list);
		return resultObject;
	}


	@RequestMapping(value = "doctorlist/disease/{diseaseId}",method= RequestMethod.GET)
	public ModelAndView doctorListByDiseaseId(@PathVariable String diseaseId ){
		ModelAndView mv = new ModelAndView();
		Disease disease = appointmentService.queryDiseaseById(diseaseId);
		List<User> doctorList = userService.queryDoctorByDepartmentId(disease.getDepartmentId());
		mv.addObject("disease",disease);
		Department department = appointmentService.queryDepartmentById(disease.getDepartmentId());
		mv.addObject("department",department);
		//todo 调用service查询评分、历史单数、被收藏数
		mv.addObject("xlq","111");
		mv.addObject("doctorList",doctorList);
		mv.setViewName("doctor_list");
		return mv;
	}

	/**
	 * 注册新用户
	 * @return
	 */
	@RequestMapping(value = "register")
	public String registerPage(){
		return "register";
	}
	@RequestMapping(value = "user/register")
	@ResponseBody
	public ResultObject userRegister(User user, PatientInfo patientInfo){
		ResultObject resultObject = new ResultObject();
		//判断手机，邮箱，身份证是否重复
		User user1 = userService.getUserByMobile(user.getMobile());
		if(user1!=null){
			resultObject.setCode(-1);
			resultObject.setMsg("该号码已被注册！");
			return resultObject;
		}
		List<User> list1 = userService.getUserByEmail(user.getEmail());
		if(list1.size()>0){
			resultObject.setCode(-1);
			resultObject.setMsg("该邮箱已被注册！");
			return resultObject;
		}
		List<PatientInfo> list2 = userService.getPatientInfoByIdCard(patientInfo.getIdCard());
		if(list2.size()>0){
			resultObject.setCode(-1);
			resultObject.setMsg("该身份证号已被使用！");
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
	@RequestMapping(value = "adminlogin")
	public String adminLogin(){
		return "admin_login";
	}
	@RequestMapping(value = "adminloginpage")
	@ResponseBody
	public DataResp adminLoginPage(String mobile,String password) {
		DataResp dataResp = new DataResp();
		try {
			DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
			defaultSecurityManager.setRealm(myRealm);
			SecurityUtils.setSecurityManager(defaultSecurityManager);
			// 获取Subject对象
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(mobile, password);

			subject.login(token);
			User user = (User)subject.getPrincipal();
			if("1".equals(user.getRoleType())){
				dataResp.setErrorCode(0);
				dataResp.setErrorDesc("仅限管理员和医生登录！");
				return dataResp;
			}
			//获取session并存放当前用户数据
			Session session = subject.getSession();
			session.setAttribute("user", user);
			session.setAttribute("userId", user.getId());
			dataResp.setObjectResult(user.getRoleType());

			//if (subject.isAuthenticated())
			return dataResp;

		} catch (IncorrectCredentialsException e1) {
			e1.printStackTrace();
			dataResp.setErrorCode(0);
			dataResp.setErrorDesc(e1.getMessage());
			return dataResp;
		} catch (LockedAccountException e2) {
			e2.printStackTrace();
			dataResp.setErrorCode(0);
			dataResp.setErrorDesc(e2.getMessage());
			return dataResp;
		}
		catch (UnknownAccountException e3) {
			e3.printStackTrace();
			dataResp.setErrorCode(0);
			dataResp.setErrorDesc(e3.getMessage());
			return dataResp;
		}
		catch (Exception e) {
			e.printStackTrace();
			dataResp.setErrorCode(0);
			dataResp.setErrorDesc("登录失败");
			return dataResp;
		}


	}

	@RequestMapping(value = "user/editInfo")
	@ResponseBody
	public ResultObject editUserInfo(PatientInfo patientInfo){
		ResultObject resultObject = new ResultObject();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		patientInfo.setUserId(userId);
		int result = userService.updatePatientByUserId(patientInfo);
		if(result<1){
			resultObject.setCode(-1);
			resultObject.setMsg("更改失败，请联系管理员！");
			return resultObject;
		}
		return resultObject;
	}

	@RequestMapping(value = "user/psd/edit")
	public String userPswEdit(){
		return "user_psw_edit";
	}
	@RequestMapping(value = "user/editPsw")
	@ResponseBody
	public ResultObject userEditPsw(String oldPassword,String newPassword, String newPassword2){
		ResultObject resultObject = new ResultObject();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		User user = userService.getUserByUserId(userId);
		if(newPassword.length()<6){
			resultObject.setCode(-1);
			resultObject.setMsg("密码长度不能少于6位");
			return resultObject;
		}else if (!user.getPassword().equals(oldPassword)){
			resultObject.setCode(-1);
			resultObject.setMsg("原密码不正确，请重新输入");
			return resultObject;
		}else if (user.getPassword().equals(newPassword)){
			resultObject.setCode(-1);
			resultObject.setMsg("新密码不能与原密码相同，请重新输入");
			return resultObject;
		}else if (!newPassword.equals(newPassword2)){
			resultObject.setCode(-1);
			resultObject.setMsg("两次输入的新密码不一致，请重新输入");
			return resultObject;
		}
		User updateUser = new User();
		updateUser.setId(userId);
		updateUser.setPassword(newPassword);
		int result = userService.updateUserByuserId(updateUser);
		return resultObject;
	}

	@RequestMapping(value = "user/order/list")
	public String userOrderList(){
		return "user_order_list";
	}
	@RequestMapping(value = "user/order/edit/{id}")
	public ModelAndView userOrderEdit(@PathVariable String id){
		ModelAndView mv = new ModelAndView();
		Order order = appointmentService.queryOrderById(id);
		mv.addObject("order",order);
		mv.setViewName("user_order_edit");
		return mv;
	}
	@RequestMapping(value = "user/getOrderList")
	@ResponseBody
	public ResultObject userListOrder(int page, int limit,
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
		searchOrder.setPatientId(userId);
		resultObject= appointmentService.queryOrderByKey(page,limit,searchOrder);
		return  resultObject;

	}

	/**
	 * 收藏医生
	 */
	@RequestMapping(value = "user/collection/add")
	@ResponseBody
	public ResultObject userCollectionAdd(MyCollection myCollection){
		ResultObject resultObject = new ResultObject();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		if (userId==null){
			resultObject.setCode(-1);
			resultObject.setMsg("尚未登录，请登录后再进行收藏");
			return resultObject;
		}
		myCollection.setPatientId(userId);
		//查看是否已收藏
		resultObject = collectionService.queryCollection(0,9999,myCollection);
		if(!ListUtils.isEmpty((List)resultObject.getData())){
			resultObject.setCode(-1);
			resultObject.setMsg("您已收藏该医生，无需重复收藏");
			return resultObject;
		}
		myCollection.setId(IdUtil.getRandomId());
		int result = collectionService.addCollection(myCollection);
		resultObject.setMsg("收藏成功！");
		return resultObject;
	}
	/**
	 * 收藏列表
	 */
	@RequestMapping(value = "user/collection/list")
	public String userCollectionList(){
		return "user_collection_list";
	}
	@RequestMapping(value = "user/getColletionList")
	@ResponseBody
	public ResultObject userGetCollectionList(int page, int limit){
		ResultObject resultObject = new ResultObject();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String userId = (String)session.getAttribute("userId");
		MyCollection collection = new MyCollection();
		collection.setPatientId(userId);
		resultObject = collectionService.queryCollection(page,limit,collection);
		return  resultObject;
	}
	/**
	 * 取消收藏
	 */
	@RequestMapping(value = "user/collection/del")
	@ResponseBody
	public ResultObject userCollectionDel(String id){
		ResultObject resultObject = new ResultObject();
		int result = collectionService.delCollection(id);
		return resultObject;
	}
}
