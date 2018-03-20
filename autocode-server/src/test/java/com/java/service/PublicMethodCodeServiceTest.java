package com.java.service;

import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import base.BaseServiceTest;

public class PublicMethodCodeServiceTest extends BaseServiceTest{

	@SpringBeanByType
	private PublicMethodCodeService publicMethodCodeService;
	
	@Test
	public void search() {
		System.out.println(publicMethodCodeService.searchPublicMethodCode("String"));
	}
	
}
