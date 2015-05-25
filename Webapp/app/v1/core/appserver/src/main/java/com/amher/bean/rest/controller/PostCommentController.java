package com.amher.bean.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amher.business.bean.PostComment;
import com.amher.business.bean.PostCommentList;

/**
 * @author yucheng
 * @version 1
 * */
public interface PostCommentController {
	
	public @ResponseBody PostComment getPostComment(@PathVariable String id);

	public @ResponseBody PostComment updatePostComment(@RequestBody String body);
	
	public @ResponseBody PostComment addPostComment(@RequestBody String body);

	public @ResponseBody PostCommentList removePostComment(@PathVariable String id);
}
