package com.amher.lib.context;

/**
 * An aspect of the current request for the current operation. Generally, implementations of this 
 * should be immutable and referenced only through the ContextProvider that controls the lifecycle 
 * of the context
 * 
 * e.g.. UserContext, MailLoopContext
 * 
 * Note: this interface does not include anthing surrounding the lifecycle of the class.
 * This is also only a marker interface; you should NOT implement this class, but instead one of its
 * child objects
 * 
 * 
 * @author AmherLib
 * @since 1
 * */

public interface Context {
}
