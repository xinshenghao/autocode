package com.java.mh.frame.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

public class LoggerUtil {
	private Class clas;
	private Logger log;
	
	public LoggerUtil(Class clas) {
		this.clas = clas;
		this.log = Logger.getLogger(clas);
	}
	
	public void info(String message){
		log.info(message);
	}
	
	public void trace(Exception e){
		StringBuilder sb=new StringBuilder("在["+clas.getName()+"]类中出错,");
		sb.append("错误为:"+e.getMessage()+"\r\n");
		StackTraceElement[] stack=e.getStackTrace();
		for(int i=0;i<stack.length;i++){
			sb.append("		"+stack[i].toString()+"\r\n");
		}
		log.error(sb);
	}
	
	public void error(String message){
		log.error(message);
	}
	
	public void debug(String message){
		log.debug(message);
	}

	
	
	
	
}
