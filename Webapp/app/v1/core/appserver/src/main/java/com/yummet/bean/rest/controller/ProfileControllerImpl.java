package com.yummet.bean.rest.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.User;
import com.yummet.lib.objectProvider.UserProvider;

/**
 * @author yucheng
 * @since 1
 * */
@Controller
public class ProfileControllerImpl implements ProfileController {
	
	private UserProvider userProvider;
	
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=ProfileRestURIConstants.GET_PROFILE)
	public @ResponseBody User getProfile(@RequestHeader("Cookie") String credentials) {
		Map<String, String> userInfo = parseRequest(credentials);
		User user = userProvider.get(userInfo.get("email"), userInfo.get("password"));
		if (user == null) {
			//Console log
		}
		return user;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.POST, value=ProfileRestURIConstants.UPDATE_PROFILE)
	public User updateProfile(String userInfo, String updateInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Map<String, String> parseRequest(String credentials) {
		String decodeMessage= URLDecoder.decode(credentials);
		decodeMessage = StringUtils.remove(decodeMessage, "yummet=");
		JSONParser jsonParser =new JSONParser();
		JSONObject jsonObject = null;
		Map<String, String> postInfoMap = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(decodeMessage);
			String userInfos = (String) ((HashMap) jsonObject.get("currentUser")).get("authdata");
			userInfos = new String(Base64.decodeBase64(userInfos.getBytes()), "UTF-8");
			String[] userInfoSection = userInfos.split(":");
			postInfoMap = ImmutableMap.of(
					"email", userInfoSection[0],
					"password", userInfoSection[1]
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postInfoMap;
	}
}
