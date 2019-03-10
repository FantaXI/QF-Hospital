package com.xlq.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xlq.hospital.service.ITestMybatis;

@Controller
public class TestMainController {
	@Autowired
	private ITestMybatis testService;
	@RequestMapping("hello")
	public String test() {
		//
		testService.testMybatis();
		System.out.println("测试成功");
		return "hello";
	}

}
