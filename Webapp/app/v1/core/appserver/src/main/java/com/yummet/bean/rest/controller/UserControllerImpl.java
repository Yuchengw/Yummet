package com.yummet.bean.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yummet.business.bean.User;
import com.yummet.lib.objectProvider.UserProvider;
import com.yummet.web.security.UserAuthentication;

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
	
	/**
	 * Get current user, return usercontext via Spring security context
	 * @return User Bean
	 * */
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=UserRestURIConstants.GET_USER)
	public User getUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//TODO: check information leak
		if (authentication instanceof UserAuthentication) {
			return ((UserAuthentication) authentication).getDetails();
		}
		return new User("anonymous@yummet.com"); //anonymous user
	}
	
	@RequestMapping(method=RequestMethod.PUT, value=UserRestURIConstants.UPDATE_USER)
	public ResponseEntity<String> updateUser(@RequestBody final User user) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userProvider.get(authentication.getName());
		
		//TODO: should add more checks, because we have on-line transaction functionality
		if (user.getNewPassword() == null || user.getNewPassword().length() <= 6) {
			return new ResponseEntity<String>("updated password too short", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		if (!pwEncoder.matches(user.getPassword(), currentUser.getPassword())) {
			return new ResponseEntity<String>("old password mismatch", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		currentUser.setPassword(pwEncoder.encode(user.getNewPassword()));
		userProvider.update(user);
		return new ResponseEntity<String>("user upadate success", HttpStatus.OK);	
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.POST, value=UserRestURIConstants.CREATE_USER)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		
		// TODO: add Email regex
		if (user.getEmail() == null) {
			return new ResponseEntity<String>("Email not eligible", HttpStatus.UNPROCESSABLE_ENTITY);
		}  
		
		if (userProvider.exists(user.getEmail())) {
			return new ResponseEntity<String>("User already exists", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (user.getPassword() == null || user.getPassword().length() <= 6) {
			return new ResponseEntity<String>("new password too short", HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		
		final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		user.setPassword(pwEncoder.encode(user.getPassword()));
		userProvider.add(user);
		return new ResponseEntity<String>("User creation success", HttpStatus.OK);
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.DELETE, value=UserRestURIConstants.DELETE_USER)
	public ResponseEntity<String> removeUser(@PathVariable User user) {
		
		if (user == null) {
			return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		userProvider.remove(Integer.parseInt(user.getId()));
		return new ResponseEntity<String>("User Deleted successful", HttpStatus.OK);
	}

}
