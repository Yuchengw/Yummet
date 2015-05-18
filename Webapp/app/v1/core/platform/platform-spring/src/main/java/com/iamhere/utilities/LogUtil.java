package com.yummet.utilities;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * Logging system for the Platform
 * @author jassica
 *
 */
public class LogUtil {
	// Create the log provider with the class information
	public static Logger getInstance(Class<?> cla) {
		return (Logger) LoggerFactory.getLogger(cla);
	}
}
