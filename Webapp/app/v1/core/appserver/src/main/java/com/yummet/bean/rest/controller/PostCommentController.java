package com.yummet.bean.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yummet.business.bean.PostComment;
import com.yummet.business.bean.PostCommentList;

/**
 * @author yucheng
 * @version 1
 * */
public interface PostCommentController {

	public @ResponseBody PostComment getPostComment(@PathVariable String id);

	public @ResponseBody PostCommentList getAllPostsComment(@PathVariable String postid);

	public @ResponseBody PostComment updatePostComment(@PathVariable String id,
			@RequestBody String body);

	public @ResponseBody PostComment addPostComment(
			@PathVariable String parentPostId, @PathVariable String id,
			@RequestBody String body);

	public @ResponseBody PostCommentList removePostComment(
			@PathVariable String id);

	public @ResponseBody PostComment updatePostComment(String body);
}
