package com.xlq.hospital.common;

import com.xlq.hospital.service.impl.FtpService;
import com.xlq.hospital.shiro.MyRealm;
import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadFileController {
	@Autowired
	private FtpService ftpService;

	@RequestMapping(value = "ftp/uploadPic")
	@ResponseBody
	public ResultObject uplooadPic(MultipartFile file,String operate, String id){
		ResultObject resultObject = new ResultObject();
		resultObject = ftpService.uploadFileByOperate(file, operate, id);
		return resultObject;
	}
}
