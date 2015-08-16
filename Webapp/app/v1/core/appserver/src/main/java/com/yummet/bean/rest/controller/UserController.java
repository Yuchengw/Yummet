package com.yummet.bean.rest.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.User;
import com.yummet.business.bean.UserList;

public interface UserController {
	
	public @ResponseBody User getUser(@RequestBody String user);

	public @ResponseBody User updateUser(@RequestBody String body);
	
	public @ResponseBody User addUser(@RequestBody String user);

	public @ResponseBody UserList removeUser(@RequestBody String id);
	
}
