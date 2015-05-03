package com.iamhere.utilities;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


/**
 * Logging system for the Platform
 * @author jassica
 * @version 1
 */
public class LogUtil {
	// Create the log provider with the class information
	public static Logger getInstance(Class<?> cla) {
		return (Logger) LoggerFactory.getLogger(cla);
	}
}