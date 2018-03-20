package com.java.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.service.PublicMethodCodeService;

@Controller
@RequestMapping("/publicMethodCode")
public class PublicMethodCodeController {

	@Resource
	private PublicMethodCodeService publicMethodCodeService;
	
	/**
	 * 增加公共代码
	 * @param jsonStr
	 */
	@RequestMapping("/addPublicMethodCode")
	public String addPublicMethodCode(String jsonStr) {
		return publicMethodCodeService.addPublicMethodCode(jsonStr);
	}
	/**
	 * 搜索公共代码
	 * @param searchText
	 * @return
	 */
	@RequestMapping("/searchPublicMethodCode")
	public String searchPublicMethodCode(String searchText) {
		return publicMethodCodeService.searchPublicMethodCode(searchText);
	}
}
