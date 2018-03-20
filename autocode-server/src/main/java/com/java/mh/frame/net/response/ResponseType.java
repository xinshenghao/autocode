package com.java.mh.frame.net.response;

import com.alibaba.fastjson.JSONObject;

public enum ResponseType {
	SUCCESS("success"),ERROR("error")
	;
	private String type;
	
	private ResponseType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.type;
	}
}
