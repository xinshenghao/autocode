package com.java.controller;

import java.util.List;

import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.java.mybatis.domain.PrivateMethod;

import base.BaseControllerTest;

public class PrivateMethodCodeControllerTest extends BaseControllerTest {
	
	@SpringBeanByType
	private PrivateMethodCodeController controller;

	@Test
	public void testGetPrivateCodeByUserId() {
		List<PrivateMethod> list = controller.getPrivateCodeByUserId("f90b3727-4bcb-4add-b2bb-84d38e027b01");
		System.out.println(list.get(0).getMethodDescr());
		System.out.println(list.size());
	}
	
	@Test
	public void testUpdatePrivateMethod() {
		String id = "30a05a55-7275-45df-b10a-5116acc9d53d";
		PrivateMethod tmp = new PrivateMethod();
		tmp.setMethodId(id);
		tmp.setMethodName("methodName");
		tmp.setMethodContent("这是内容");
		controller.updatePrivateMethod(tmp);
		
	}
}
