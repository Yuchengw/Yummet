package com.yummet.api.util;

import com.yummet.business.bean.User;
import com.yummet.web.security.TokenHandler;

/**
 * @author yucheng
 * @since 1
 * */
public class UserApiUtil {

	public static String getXSRFTokenForUser(User user) {
		TokenHandler tokenHandler = new TokenHandler();
		return tokenHandler.createTokenForUser(user);
	}
}
