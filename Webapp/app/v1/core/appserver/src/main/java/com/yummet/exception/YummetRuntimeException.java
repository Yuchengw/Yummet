package com.yummet.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yucheng
 * @since 1
 * */
@SuppressWarnings("serial")
public class YummetRuntimeException extends RuntimeException {

	private ErrorCode errorCode;
	private final Map<String, Object> properties = new TreeMap<String, Object>();
	
	public YummetRuntimeException() {
		super();
	}
	
	public YummetRuntimeException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public YummetRuntimeException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public YummetRuntimeException(Throwable cause, ErrorCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public YummetRuntimeException(String message, Throwable cause, ErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public static YummetRuntimeException wrap(Throwable exception,
			ErrorCode errorCode) {
		if (exception instanceof YummetRuntimeException) {
			YummetRuntimeException se = (YummetRuntimeException) exception;
			if (errorCode != null && errorCode != se.getErrorCode()) {
				return new YummetRuntimeException(exception.getMessage(),
						exception, errorCode);
			}
			return se;
		} else {
			return new YummetRuntimeException(exception.getMessage(),
					exception, errorCode);
		}
	}

	public static YummetRuntimeException wrap(Throwable exception) {
		return wrap(exception, null);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public YummetRuntimeException setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) properties.get(name);
	}

	public YummetRuntimeException set(String name, Object value) {
		properties.put(name, value);
		return this;
	}

	public void printStackTrace(PrintStream s) {
		synchronized (s) {
			printStackTrace(new PrintWriter(s));
		}
	}

	public void printStackTrace(PrintWriter s) {
		synchronized (s) {
			s.println(this);
			s.println("\t-------------------------------");
			if (errorCode != null) {
				s.println("\t" + errorCode + ":"
						+ errorCode.getClass().getName());
			}
			for (String key : properties.keySet()) {
				s.println("\t" + key + "=[" + properties.get(key) + "]");
			}
			s.println("\t-------------------------------");
			StackTraceElement[] trace = getStackTrace();
			for (int i = 0; i < trace.length; i++)
				s.println("\tat " + trace[i]);

			Throwable ourCause = getCause();
			if (ourCause != null) {
				ourCause.printStackTrace(s);
			}
			s.flush();
		}
	}
}
