package com.java.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.mybatis.domain.PrivateMethod;
import com.java.service.PrivateMethodCodeService;

@Controller
@RequestMapping("/privateMethodCode")
public class PrivateMethodCodeController {

	@Resource
	private PrivateMethodCodeService privateMethodCodeService;
	
	/**
	 * 根据用户主键获取该用户的私有代码
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getPrivateCodeByUserId")
	public List<PrivateMethod> getPrivateCodeByUserId(String userId){
		return privateMethodCodeService.getPrivateMethodByUserId(userId);
	}
	
	/**
	 * 增加私有代码
	 * @param privateMethod
	 * @return
	 */
	@RequestMapping("/addPrivateMethod")
	public String addPrivateMethod(PrivateMethod privateMethod) {
		return privateMethodCodeService.addPrivateMethod(privateMethod);
	}
	
	/**
	 * 修改私有代码
	 * @param privateMethod
	 * @return
	 */
	@RequestMapping("/updatePrivateMethod")
	public void updatePrivateMethod(PrivateMethod privateMethod) {
		privateMethodCodeService.updatePrivateMethod(privateMethod);
	}
	
	/**
	 * 删除私有代码
	 * @param methodId
	 */
	@RequestMapping("/deletePrivateMethod")
	public void deletePrivateMethod(String methodId) {
		privateMethodCodeService.deletePrivateMethod(methodId);
	}
	
}
