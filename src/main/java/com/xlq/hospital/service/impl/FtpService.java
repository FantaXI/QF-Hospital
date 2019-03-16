package com.xlq.hospital.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.xlq.hospital.common.FtpUtil;
import com.xlq.hospital.common.IdUtil;
import com.xlq.hospital.common.ResultObject;
import com.xlq.hospital.model.Department;
import com.xlq.hospital.model.Disease;
import com.xlq.hospital.model.User;
import com.xlq.hospital.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
@Service
public class FtpService {

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private IUserService userService;

	@Value("${ftp.username}")
	private String userName;
	@Value("${ftp.password}")
	private String passWord;
	@Value("${ftp.address}")
	private String address;
	@Value("${ftp.port}")
	private int port;
	@Value("${ftp.baseUrl}")
	private String baseUrl;
	@Value("${ftp.address}")
	private String host;
	@Value("${ftp.basePath}")
	private String basePath;


	public ResultObject uploadFileByOperate(MultipartFile file, String operate, String id){
		ResultObject resultObject = new ResultObject();
		Map<String,Object> map = new HashMap<>();
		try {
			//1、给上传的图片生成新的文件名
			//1.1获取原始文件名
			String oldName = file.getOriginalFilename();
			//1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
			String newName = IdUtil.getRandomId();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			//1.3生成文件在服务器端存储的子目录
			//测试文件夹
			String filePath = "/test";
			if("editByUser".equals(operate)){
				//用户更改头像
				filePath = "/user";
				Subject subject = SecurityUtils.getSubject();
				Session session = subject.getSession();
				String userId = (String)session.getAttribute("userId");
				User user = userService.getUserByUserId(userId);
				user.setImageUrl(baseUrl + filePath + "/" + newName);
				// 插入数据库
				userService.updateUserByuserId(user);
			} else if ("user".equals(operate)){
				filePath = "/user";
				if(!StringUtils.isEmpty(id)){
					User user = new User();
					user.setImageUrl(baseUrl + filePath + "/" + newName);
					user.setId(id);
					userService.updateUserByuserId(user);
				}
			}else if("department".equals(operate)){
				filePath = "/department";
				if(!StringUtils.isEmpty(id)){
					Department department= new Department();
					department.setImageUrl(baseUrl + filePath + "/" + newName);
					department.setId(id);
					appointmentService.updateDepartment(department);
				}
			}else if("disease".equals(operate)){
				filePath = "/disease";
				if(!StringUtils.isEmpty(id)){
					Disease disease = new Disease();
					disease.setImageUrl(baseUrl + filePath + "/" + newName);
					disease.setId(id);
					appointmentService.updateDisease(disease);
				}
			}
			String src = baseUrl + filePath + "/" + newName;
			//2.设置图片的路径和参数等。。。
			map.put("src",src);
			//3、把图片上传到图片服务器
			//3.1获取上传的io流
			InputStream input = file.getInputStream();

			//3.2调用FtpUtil工具类进行上传
			boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);
			if(result) {
				resultObject.setData(map);
				return resultObject;

			}else {
				resultObject.setCode(-1);
				resultObject.setMsg("上传失败！");

			}
		} catch (IOException e) {
			resultObject.setCode(-1);
			resultObject.setMsg("上传失败！");

		}
		return resultObject;
	}


}
