package com.java.mh.frame.aop;


import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig.Interface;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.mh.frame.net.response.SuccessResponse;
import com.java.mh.frame.utils.NetUtil;
import com.java.mh.frame.utils.ResponseUtil;

/**
 * 环绕增强
 * @author 辛晟昊
 *
 */
@Aspect
public class AroundAdvice {
	@Autowired
	private NetUtil net;
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) "
			+" && !@annotation(org.springframework.web.bind.annotation.ResponseBody)")
	//@Around("within(controller.*)")
	public void around(ProceedingJoinPoint pjp) throws Throwable{
		HttpServletResponse response= (HttpServletResponse) net.getAttribute("response");
		Object object= pjp.proceed();
		JSONObject result = buildResult(object);
		
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 构建返回
	 * @param object
	 * @return
	 */
	private JSONObject buildResult(Object object) {
		SuccessResponse respon = new SuccessResponse();
		if (object == null) {
			respon.setContent("");
			//result.put("result", null);
		} else if (isLang(object)) {// 判断object的类型是否为基本类型（lang包里的类）
			respon.setContent(object.toString());
			//result.put("result", object);
		} else if (isArray(object) || isList(object) || isSet(object)) {
			// 如果是数组 如果是列表 如果是Set
			JSONArray tmp = (JSONArray) JSONArray.toJSON(object);
			respon.setContent(tmp.toJSONString());
			//result.put("result", tmp);
		} else {
			JSONObject tmp = (JSONObject) JSONObject.toJSON(object);
			respon.setContent(tmp.toJSONString());
			//result.put("result", tmp);
		}

		return (JSONObject) JSONObject.toJSON(respon);
	}

	/**
	 * 判断是否为基本类，lang中的类
	 * @author: 辛晟昊   
	 * @param: @param object
	 * @param: @return      
	 * @return: boolean
	 */
	private boolean isLang(Object object) {
		String className=object.getClass().getName();
		return className.startsWith("java.lang");
	}
	/**
	 * 判断是否为list接口实现类
	 * @author: 辛晟昊   
	 * @param: @param object
	 * @param: @return      
	 * @return: boolean
	 */
	private boolean isList(Object object){
		return hasParticularInterface("java.util.List", object);
	}
	
	@SuppressWarnings("unchecked")
	private boolean hasParticularInterface(String interfaceName,Object object){
		Class<Interface>[] interfaces=(Class<Interface>[]) object.getClass().getInterfaces();
		for (Class<Interface> inter : interfaces) {
			if(inter.getName().equals(interfaceName)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 是否为数组
	 * @author: 辛晟昊   
	 * @param: @param object
	 * @param: @return      
	 * @return: boolean
	 */
	@SuppressWarnings("unused")
	private boolean isArray(Object object){
		return object.getClass().isArray();
	}
	/**
	 * 是否是Set类型
	 * @author: 辛晟昊   
	 * @param: @param object
	 * @param: @return      
	 * @return: boolean
	 */
	private boolean isSet(Object object){
		return hasParticularInterface("java.util.Set", object);
	}
	
	private boolean isMap(Object object){
		return hasParticularInterface("java.util.Map", object);
	}
	
	
	
	
}