package com.yummet.bean.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;

public interface PostController {

	public @ResponseBody Post getPost(@PathVariable String id, @RequestBody String body);
	
	public @ResponseBody PostList getPosts(@PathVariable String id, @RequestParam(value="step") final String step, @RequestParam(value="cursor") final String cursor, @RequestBody String body) throws Exception;

	public @ResponseBody Post addPost(@RequestBody String body) throws Exception;
	
	public @ResponseBody Post updatePost(@PathVariable String id, @RequestBody String body) throws Exception;

	public @ResponseBody void removePost(@PathVariable String id, @RequestBody String body);
}
