package com.hitoo.server.client.response;

public abstract class AbsResponseBean {

	String content;
	ResponseType responseType;

	public AbsResponseBean(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ResponseType getResponseType() {
		return responseType;
	}
}
