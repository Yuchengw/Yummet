package com.amher.lib.context;

import java.util.Arrays;

//TODO: import google java library
//import com.google.common.collect.Lists;

public interface ContextProvider<C extends Context> {
	
	/**
	 * @return the current context established with this provider
	 * */
	C get();
}
