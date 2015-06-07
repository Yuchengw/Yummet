package com.yummet.bean.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostComment;
import com.yummet.business.bean.PostCommentList;
import com.yummet.lib.objectProvider.PostCommentProvider;
import com.yummet.lib.objectProvider.PostProvider;

/**
 * This class is used for Restful API User Control.
 * 
 * @author yucheng
 * @version 1
 * */
@Controller
public class PostCommentControllerImpl implements PostCommentController {

	@Autowired
	private PostCommentProvider postCommentProvider;
	
	@Autowired
	private PostProvider postProvider;
	
	@Autowired
	private Jaxb2Marshaller jaxb2Mashaller;
	
	public void setPostCommentProvider(PostCommentProvider postCommentProvider) {
		this.postCommentProvider = postCommentProvider;
	}
	
	public void setJaxb2Mashaller(Jaxb2Marshaller jaxb2Mashaller) {
		this.jaxb2Mashaller = jaxb2Mashaller;
	}
	
	public void setPostProvider(PostProvider postProvider) {
		this.postProvider = postProvider;
	}
	
	@Override
	public PostComment getPostComment(String id) {
		return postCommentProvider.get(id);
	}

	@Override
	public PostComment updatePostComment(String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostCommentList removePostComment(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostCommentList getAllPostsComment(@PathVariable String postid) {
		Post this.postProvider.get(postid);
		this.postCommentProvider.getAll(postid);
		return null;
	}

	@Override
	public PostComment updatePostComment(String id, String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostComment addPostComment(String parentPostId, String id,
			String body) {
		// TODO Auto-generated method stub
		return null;
	}

}
