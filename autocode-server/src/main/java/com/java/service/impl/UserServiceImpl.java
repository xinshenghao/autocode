package com.java.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.lang.UsesSunHttpServer;
import org.springframework.stereotype.Service;

import com.java.mybatis.domain.User;
import com.java.mybatis.domain.UserExample;
import com.java.mybatis.idao.UserMapper;
import com.java.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userDao;

	@Override
	public String login(String userName, String password) {
		UserExample userExample = new UserExample();
		userExample.or().andUserNameEqualTo(userName)
			.andPasswordEqualTo(password);
		List<User> users = userDao.selectByExample(userExample);
		if(null==users || users.size()==0) {
			return null;
		}
		return users.get(0).getUserId();
	}

}
