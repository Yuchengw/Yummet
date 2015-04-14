package com.amher.lib.request;

import com.amher.lib.provider.BasicUserInfo;

/**
 * 
 * @author AmherLib
 * @since 1
 * */

public interface UserInformation extends BasicUserInfo{
	String getUserId();
	String getFullName();
}
