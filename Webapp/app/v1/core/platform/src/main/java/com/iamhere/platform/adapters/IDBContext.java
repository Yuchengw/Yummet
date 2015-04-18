package com.iamhere.platform.adapters;

import java.sql.SQLException;

public interface IDBContext {
	/**
	 * Sets whether or not a new connection should be allowed to be made when getConnection() is called
	 * @param isAllowed
	 * @param reason
	 */
	void setIsNewConnectionAllowed(boolean isAllowed, String reason) ;
	
	/**
	 * 
	 * @return whether or not a new coonection should be allowed to be made when getConnection is called
	 */
	boolean isNewConnectionAllowed();
	
	/**
	 * Establishes a connection to the database.
	 * @return true if we established a connection for this dbcontext
	 * @throws SQLException
	 */
	boolean establishConnection() throws SQLException;
	
	void releaseConnection();
	
	/**
	 * Clears the current user's database context
	 * @throws SQLException
	 */
	void clearConect() throws SQLException;
	
}
