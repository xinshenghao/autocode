package com.java.service;

import java.util.List;

import com.java.mybatis.domain.PrivateMethod;

public interface PrivateMethodCodeService {

	/**
	 * 根据用户ID获取私有方法
	 * @param userId
	 * @return
	 */
	public List<PrivateMethod> getPrivateMethodByUserId(String userId);
	
	/**
	 * 增加私有代码
	 * @param privateMethod
	 * @return
	 */
	public String addPrivateMethod(PrivateMethod privateMethod);
	
	/**
	 * 修改私有代码
	 * @param privateMethod
	 * @return
	 */
	public void updatePrivateMethod(PrivateMethod privateMethod);
	
	/**
	 * 删除私有代码
	 * @param methodId
	 */
	public void deletePrivateMethod(String methodId);
	
}
