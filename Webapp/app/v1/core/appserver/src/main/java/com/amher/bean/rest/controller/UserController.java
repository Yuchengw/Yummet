package com.amher.bean.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amher.business.bean.User;
import com.amher.business.bean.UserList;

public interface UserController {
	
	public @ResponseBody User getUser(@PathVariable String id);

	public @ResponseBody User updateUser(@RequestBody String body);
	
	public @ResponseBody User addUser(@RequestBody String body);

	public @ResponseBody UserList removeUser(@PathVariable String id);
	
	public @ResponseBody UserList getUsers();
	
}
