package com.yummet.api.rest;

public enum HttpStatusCode {
	OK(200, false),
	CREATED(201, true),
	ACCEPTED(202, true),
	FOUND(302,true),
	BAD_REQUEST(400, false),
	UNAUTHORIZED(401, false),
	FORBIDDEN(403, false),
	NOT_FOUND(404,true),
	TIME_OUT(408, true),
	SERVER_UNAVAILABLE(503, true);
	
	public final int value;
	public final boolean canHaveEmptyResponseBody;
	
	HttpStatusCode(int val, boolean canHaveEmptyResponseBody) {
		this.value = val;
		this.canHaveEmptyResponseBody = canHaveEmptyResponseBody;
	}
	
	public static HttpStatusCode getStatusCode(int sc) {
		for (HttpStatusCode httpStatusCode : HttpStatusCode.values()) {
			if (httpStatusCode.value == sc) {
				return httpStatusCode;
			}
		}
		return null;
	}
}
