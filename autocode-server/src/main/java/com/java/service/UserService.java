package com.java.service;

public interface UserService {

	/**
	 * 登录
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	String login(String userName, String password);
}
