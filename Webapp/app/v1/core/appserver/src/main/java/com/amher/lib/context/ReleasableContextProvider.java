package com.amher.lib.context;

import java.util.concurrent.Callable;

/**
 * Marks a context provider as being established and releasable by "anyone",
 * 
 *@author AmherLib
 *@since 1 
 * */

public interface ReleasableContextProvider <C extends Context> extends ContextProvider<C>, Establishable {
	
	/**
	 * Establish the current context
	 * @param context the context to establish
	 * @throws IllegalStateException if the context is already established. Use the stackable variant and push
	 * */
	void establish(C context) throws IllegalStateException;
	
	/**
	 * Release the context so it can be used again
	 * */
	void release();

	/**
	 * Wrap the given callable to execute in the context given. For some pieces of code ,this maybe 
	 * easier than a try/finally
	 * 
	 * @param context the context to run with
	 * @param callable the method to execute
	 * @return a callable that will establlish the given context before running the function
	 * */
	<V>  Callable<V> wrap(C context, Callable<V> callable);
}
