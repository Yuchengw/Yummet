package com.yummet.bean.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;

public interface PostController {
	
	public @ResponseBody Post getPost(@PathVariable String id);

	public @ResponseBody Post updatePost(@RequestBody String body);
	
	public @ResponseBody Post addPost(@RequestBody String body);

	public @ResponseBody PostList removePost(@PathVariable String id);
	
	public @ResponseBody PostList getPosts();
}
