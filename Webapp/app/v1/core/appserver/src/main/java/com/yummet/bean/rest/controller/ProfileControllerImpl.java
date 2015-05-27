package com.yummet.bean.rest.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.User;
import com.yummet.lib.objectProvider.UserProvider;

public class ProfileControllerImpl implements ProfileController {
	
	private UserProvider userProvider;
	
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=ProfileRestURIConstants.GET_PROFILE)
	public @ResponseBody User getProfile(@RequestHeader("authorization") String userInfo) {
		// TODO: this also used in @link{UserControllerImpl} should be refactor...
		String userEmailAndPassword = userInfo.split(" ")[1];
		if (userEmailAndPassword == null) {
			//TODO: should throw errors back to front-end
			return null;
		}
		try {
			userEmailAndPassword = new String(Base64.decodeBase64(userEmailAndPassword.getBytes()), "UTF-8");
			String[] emailAndPassword = userEmailAndPassword.split(":");
			User e = userProvider.getUserServiceImpl().getUserByEmailAndPassword(emailAndPassword[0], emailAndPassword[1]);
			return e;
		} catch (Exception e1) {
			// continue;
		} finally {
		}
		return null;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.POST, value=ProfileRestURIConstants.UPDATE_PROFILE)
	public User updateProfile(String userInfo, String updateInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
