package com.amher.bean.rest.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amher.business.bean.User;
import com.amher.business.bean.UserList;

public interface UserController {
	
	public @ResponseBody User getUser(@RequestBody String user);

	public @ResponseBody User updateUser(@RequestBody String body);
	
	public @ResponseBody User addUser(@RequestBody User user);

	public @ResponseBody UserList removeUser(@RequestBody String id);
	
}
