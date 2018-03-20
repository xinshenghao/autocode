package com.java.mh.frame.aop;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.java.mh.frame.exception.MHException;
import com.java.mh.frame.net.response.ErrorResponse;
import com.java.mh.frame.utils.LoggerUtil;
import com.java.mh.frame.utils.NetUtil;
import com.java.mh.frame.utils.ResponseUtil;



/**
 * 抛出异常增强
 * @author 辛晟昊
 */
@Aspect
public class AfterThrowingAdvice{
	@Resource
	private NetUtil net;
	
	LoggerUtil log=null;
	
	//@AfterThrowing(value="execution(* controller.*.test(..))",throwing="e")
	@AfterThrowing(value="@annotation(org.springframework.web.bind.annotation.RequestMapping)",throwing="e")
	public void doAfterThrowing(JoinPoint jp,Exception e){
		if("MHException".equals(e.getClass().getSimpleName())){
			MHException ex=(MHException) e;
			//写入Log日志
			if(ex.getIfThrowItToLog()){doLog(jp.getTarget().getClass(),ex);}
			//返回前台
			if(ex.getIfThrowItToForeground()){returnToForeground(ex);}
		}else{
			doLog(jp.getTarget().getClass(),e);
			returnToForeground(e);
		}
	}
	private void returnToForeground(Exception e) {
		//返回前台数据
		HttpServletResponse response= (HttpServletResponse) net.getAttribute("response");
		ErrorResponse resp = new ErrorResponse(e.getMessage());
		JSONObject json=(JSONObject) JSONObject.toJSON(resp);
		ResponseUtil.write(response,json);		
	}
	/**
	 * 记录到日志中
	 * @author: 辛晟昊   
	 * @param e 
	 * @param:       
	 * @return: void
	 */
	private void doLog(Class c, Exception e) {
		//日志记录
		log=new LoggerUtil(c);
		log.trace(e);
	}
	
	
	
}
