package com.java.mh.frame.utils;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class NetUtil {
	@Autowired
	private  HttpServletRequest request;
	
	
	
	
	public HttpSession getSession(){
	 	return request.getSession();
	}
	/**
	 * 返回request中的值
	 * @author: 辛晟昊   
	 * @param: @param name
	 * @param: @return      
	 * @return: Object
	 */
	public Object getAttribute(String name){
		return request.getAttribute(name);
	}
	/**
	 * 返回session中的值
	 * @author: 辛晟昊   
	 * @param: @param name
	 * @param: @return      
	 * @return: Object
	 */
	public Object get(String name){
		return getSession().getAttribute(name);
	}
	
	/***
	 * 返回session中对应实体的对应属性值
	 * @author: 辛晟昊   
	 * @param: @param beanName
	 * @param: @param attributeName
	 * @param: @return      
	 * @return: Object
	 */
	public Object getBeanAttributeInSession(String beanName,String attributeName){
		Object object=getSession().getAttribute(beanName);
		return getFieldValueByName(attributeName, object);
	}
	
	
	
	
	/**  
	 * 获取request  
	 * @return request request  
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**  
	 * 设置request  
	 * @param request request  
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/** 
	    * 根据属性名获取属性值 
	    * */  
	  private static Object getFieldValueByName(String fieldName, Object o) {  
	      try {    
	          String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	          String getter = "get" + firstLetter + fieldName.substring(1);    
	          Method method = o.getClass().getMethod(getter, new Class[] {});    
	          Object value = method.invoke(o, new Object[] {});    
	          return value;    
	      } catch (Exception e) {      
	          return null;    
	      }    
	  }   
	
}
