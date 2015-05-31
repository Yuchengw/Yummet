package com.yummet.bean.rest.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;

public interface PostController {

	public @ResponseBody Post getPost(@PathVariable String id, @RequestBody String body);
	
	public @ResponseBody PostList getPosts(@PathVariable String id, @RequestParam(value="step") final String step, @RequestParam(value="cursor") final String cursor, @RequestBody String body);

	public @ResponseBody Post addPost(@RequestHeader String credentials, @RequestBody String body);
	
	public @ResponseBody Post updatePost(@RequestHeader String credentials, @PathVariable String id, @RequestBody String body);

	public @ResponseBody void removePost(@RequestHeader String credentials, @PathVariable String id, @RequestBody String body);
}
