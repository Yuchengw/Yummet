package com.yummet.exception;

/**
 * @author yucheng
 * @since 1
 * */
public enum OrderErrorCode implements ErrorCode {
	
	ORDER_NOT_EXISTS(4001);
	
	private final int number;
	OrderErrorCode(int number) {
		this.number = number;
	}
	@Override
	public int getNumber() {
		return this.number;
	}
}
