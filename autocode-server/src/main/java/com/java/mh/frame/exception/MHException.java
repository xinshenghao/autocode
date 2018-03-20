package com.java.mh.frame.exception;

public class MHException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	private String message;
	/**
	 * 是否输出到Log日志
	 */
	private Boolean ifThrowItToLog=true;
	/**
	 * 是否输出到前台
	 */
	private Boolean ifThrowItToForeground=true;
	
	public MHException(String message) {
		super();
		this.message = message;
	}
	
	public MHException(String message,Boolean  ifThrowItToLog){
		this(message);
		this.ifThrowItToLog=ifThrowItToLog;
	}

	public MHException(String message, Boolean ifThrowItToLog, Boolean ifThrowItToForeground) {
		this(message,ifThrowItToLog);
		this.ifThrowItToForeground = ifThrowItToForeground;
	}

	/**  
	 * 获取message  
	 * @return message message  
	 */
	public String getMessage() {
		return message;
	}
	

	/**  
	 * 设置message  
	 * @param message message  
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	

	/**  
	 * 获取ifThrowItToLog  
	 * @return ifThrowItToLog ifThrowItToLog  
	 */
	public Boolean getIfThrowItToLog() {
		return ifThrowItToLog;
	}
	

	/**  
	 * 获取ifThrowItToForeground  
	 * @return ifThrowItToForeground ifThrowItToForeground  
	 */
	public Boolean getIfThrowItToForeground() {
		return ifThrowItToForeground;
	}
	
	
	

}
