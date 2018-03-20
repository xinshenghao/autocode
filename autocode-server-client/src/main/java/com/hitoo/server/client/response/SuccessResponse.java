package com.hitoo.server.client.response;

public class SuccessResponse extends AbsResponseBean{
	public SuccessResponse() {
		super(ResponseType.SUCCESS);
	}

	public SuccessResponse(String content) {
		super(ResponseType.SUCCESS);
		this.content = content;
	}

}
