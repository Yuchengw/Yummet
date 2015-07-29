package com.yummet.exception;

/**
 * @author yucheng
 * @since 1
 * */
public enum PostErrorCode implements ErrorCode {
	POST_NOT_EXISTS(2001);

	private final int number;
	
	PostErrorCode(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return 0;
	}

}
