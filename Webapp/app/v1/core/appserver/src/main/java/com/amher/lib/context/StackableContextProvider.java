package com.amher.lib.context;

import java.util.List;

/**
 * Interface to a context provider that allows a stack of contexts
 * 
 * The implementation REQUIRES that the use of push and pop are confined
 * to a single method with a try/finally.
 * 
 * This should only be used for immutalbe contexts
 * 
 * @author AmherLib
 * @since 1
 * */

public interface StackableContextProvider<C extends Context> extends ReleasableContextProvider {
	
	/**
	 * @return the current context if it is established, or null if the context is not established,
	 * Note: this does NOT auto-establish the context
	 * */
	C getOrNull();
	
	/**
	 * The next context of a process
	 * @param context the context to push onto the stack 
	 * */
	AutoCloseable push(C context);
	
	/**
	 * @param assertContext the expected context which should get popped
	 * @return the last context in the stack and remove it from the stack
	 * @throws IllegalStateException if you are trying to pop the "last" value
	 * */
	C pop(C assertContext) throws IllegalStateException;
	
	/**
	 *@return the last context on the stack 
	 * */
	C peek();
	
	/**
	 *@return an umodifiable list of all of the contexts on the stack
	 * */
	List<C> getContexts();
}
