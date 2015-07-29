package com.yummet.system.context;

public class YummetContext implements ContextIntf{

	public static final UserContextImpl getUserContext() {
		return new UserContextImpl();
	}
}
