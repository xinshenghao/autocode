package com.hitoo.server.client.response;

public class ErrorResponse extends AbsResponseBean{
	
	public ErrorResponse() {
		super(ResponseType.ERROR);
	}

	public ErrorResponse(String content) {
		super(ResponseType.ERROR);
		this.content = content;
	}

}
