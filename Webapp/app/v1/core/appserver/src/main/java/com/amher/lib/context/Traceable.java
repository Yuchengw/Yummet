package com.amher.lib.context;

/**
 * Represents an aspect of an object where it tracks where, when, and by whom it was established;
 * allowing you to trace an object's creation or when something was checked out or established
 * 
 * @author AmherLib
 * @since 1
 * */

public interface Traceable {
	
	/**
	 * Gets the System.currentTimeMills() of when the resource was established.
	 * 
	 * @return -1 if not currently established 
	 * */
	long getWhenEstablished();
	
	/**
	 * The name of the thread at the time of establishing
	 * 
	 * @return null if not established
	 * */
	String getWhoEstablished();
	
	/**
	 * The backtrace of the thread that triggered establishing.
	 * 
	 * @return null if not being tracked (we hope we could tracke this stacktrace in production also)
	 * */
	StackTraceElement[] getHowEstablished();
	
	/***
	 * @return true if the current thread is the owner of resource
	 * */
	boolean isOwnerThread();

}
