package com.yummet.bean.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.yummet.business.bean.User;

public interface UserController {
	
	public User getUser();

	public ResponseEntity<String> updateUser(@RequestBody final User user);
	
	public ResponseEntity<String> addUser(@RequestBody User user); 

	public ResponseEntity<String> removeUser(@PathVariable User user);
	
}
