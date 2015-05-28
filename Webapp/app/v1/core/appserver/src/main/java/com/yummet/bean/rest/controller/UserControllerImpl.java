package com.yummet.bean.rest.controller;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.codec.binary.Base64;

import com.google.common.collect.ImmutableMap;
import com.yummet.business.bean.User;
import com.yummet.business.bean.UserList;
import com.yummet.lib.objectProvider.UserProvider;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

/**
 * This class is used for Restful API User Control.
 * 
 * @author yucheng
 * @version 1
 * */
@Controller
public class UserControllerImpl implements UserController {
	
	private UserProvider userProvider;
	private Jaxb2Marshaller jaxb2Mashaller;
	
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	
	public void setJaxb2Mashaller(Jaxb2Marshaller jaxb2Mashaller) {
		this.jaxb2Mashaller = jaxb2Mashaller;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=UserRestURIConstants.GET_USER)
	public @ResponseBody User getUser(@RequestHeader("authorization") String userInfo) {
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
	
	@RequestMapping(method=RequestMethod.PUT, value=UserRestURIConstants.UPDATE_USER)
	public @ResponseBody User updateUser(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		User u = (User) jaxb2Mashaller.unmarshal(source);
		userProvider.update(u);
		return u;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.POST, value=UserRestURIConstants.CREATE_USER)
	public @ResponseBody User addUser(@RequestBody String user) {
		Map<String,String> userInfo = parseUserRequest(user);
		User newUser =  new User(null,
						userInfo.get("firstname"), 
						userInfo.get("lastname"), 
						userInfo.get("email"), 
						userInfo.get("password"));
		User createdUser = userProvider.add(newUser);
		return createdUser;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.DELETE, value=UserRestURIConstants.DELETE_USER)
	public @ResponseBody UserList removeUser(@RequestBody String id) {
		userProvider.remove(Integer.parseInt(id));
		return null;
	}
	
	/**
	 * */
	public Map<String,String> parseUserRequest(String user) {
		if (user == null || user.length() <= 0) {
			return null;
		}
		user = StringUtils.removeStart(user, "{");
		user = StringUtils.removeEnd(user, "}");
		user = StringUtils.remove(user, "\"");
		String[] userSections = user.split(",");
		if (userSections.length < 4) {
			return null;
		}
		String[] userFirstName = userSections[0].split(":");
		String[] userLastName = userSections[1].split(":");
		String[] userEmail = userSections[2].split(":");
		String[] userPassword = userSections[3].split(":");
		Map<String,String> userInfoMap = ImmutableMap.of(
				userFirstName[0], userFirstName[1],
				userLastName[0], userLastName[1],
				userEmail[0], userEmail[1], 
				userPassword[0],userPassword[1]);
		return userInfoMap;
	}

}
