package com.yummet.bean.rest.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;

public interface PostController {

	public @ResponseBody PostList getPosts(@RequestHeader String credentials, @RequestBody String body);

	public @ResponseBody Post addPost(@RequestHeader String credentials, @RequestBody String body);
	
	public @ResponseBody Post updatePost(@RequestBody String body);

	public @ResponseBody Post removePost(@RequestBody String body);
}
