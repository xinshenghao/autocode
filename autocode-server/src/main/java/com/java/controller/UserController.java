package com.java.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return 返回用户ID
	 */
	@RequestMapping("/login")
	public String login(String userName, String password) {
		return userService.login(userName, password);
	}
	
}
