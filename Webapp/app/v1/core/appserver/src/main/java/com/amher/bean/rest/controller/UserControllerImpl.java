package com.amher.bean.rest.controller;

import java.io.StringReader;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amher.business.bean.User;
import com.amher.business.bean.UserList;
import com.amher.lib.objectProvider.UserProvider;

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
	public @ResponseBody User getUser(@PathVariable String userEmail){
		User e = userProvider.getUserServiceImpl().getUserByEmail(userEmail);
		return e;
	}
	
	@RequestMapping(method=RequestMethod.PUT, value=UserRestURIConstants.UPDATE_USER)
	public @ResponseBody User updateUser(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		User u = (User) jaxb2Mashaller.unmarshal(source);
		userProvider.update(u);
		return u;
	}
	
	@RequestMapping(method=RequestMethod.POST, value=UserRestURIConstants.CREATE_USER)
	public @ResponseBody User addUser(@RequestBody User user) {
		userProvider.getUserServiceImpl().createUser(user);
		return user;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.DELETE, value=UserRestURIConstants.DELETE_USER)
	public @ResponseBody UserList removeUser(@PathVariable String id) {
		userProvider.remove(Integer.parseInt(id));
		return null;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=UserRestURIConstants.GET_ALL_USER)
	public @ResponseBody UserList getUsers() {
		User e = userProvider.getUserServiceImpl().getUserByEmail("ycwmike@gmail.com");
		UserList userList = new UserList();
		userList.add(e);
		return userList;
	}
}
