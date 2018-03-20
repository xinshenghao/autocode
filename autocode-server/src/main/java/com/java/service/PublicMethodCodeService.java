package com.java.service;

public interface PublicMethodCodeService {
	/**
	 * 增加公共方法代码
	 * @param jsonStr Json格式的PublicMethod对象
	 * @return
	 */
	String addPublicMethodCode(String jsonStr);
	/**
	 * 搜索公共代码
	 * @param searchText
	 * @return
	 */
	String searchPublicMethodCode(String searchText);
	
}
