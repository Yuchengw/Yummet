package com.yummet.exception;

/**
 * @author yucheng
 * @since 1
 * */
public enum UserErrorCode implements ErrorCode{
	USER_NOT_EXISTS(1001);

	private final int number;

	UserErrorCode(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return this.number;
	}

}
