package com.amher.lib.context;

/**
 * A resource that can be established and released. This object should be immutable, but generally
 * should be short lived
 * 
 * @author AmherLib
 * @since 1
 * */


public interface Establishable extends Traceable {
	/**
	 * Whether or not the given resouce is currently established
	 * */
	boolean estalished();
	
	/**
	 * Releases the given resource
	 * */
	void release();
}
