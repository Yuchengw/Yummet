package com.yummet.bean.rest.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.User;

public interface ProfileController {
	public @ResponseBody User getProfile(@RequestHeader String userInfo);
	public @ResponseBody User updateProfile(@RequestHeader String userInfo, @RequestBody String updateInfo);
}
