package com.yummet.platform.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yummet.mongodb.repositories.UserRepository;

@Deprecated
@Component
public class RegistrationBean {
	@Autowired
	private UserRepository repository;

	public RegistrationBean() {
	}

	public UserRepository getRepository() {
		return repository;
	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}
}