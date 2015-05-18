package com.yummet.bean.rest.controller;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;
import com.yummet.lib.objectProvider.PostCommentProvider;
import com.yummet.lib.objectProvider.PostProvider;

/**
 * This class is used for Restful API User Control.
 * 
 * @author yucheng
 * @version 1
 * */
@Controller
public class PostControllerImpl implements PostController {

	private PostProvider postProvider;
	private Jaxb2Marshaller jaxb2Mashaller;
	
	public void setUserProvider(PostProvider postProvider) {
		this.postProvider = postProvider;
	}
	
	public void setJaxb2Mashaller(Jaxb2Marshaller jaxb2Mashaller) {
		this.jaxb2Mashaller = jaxb2Mashaller;
	}
	
	@Override
	public Post getPost(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post updatePost(String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post addPost(String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostList removePost(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostList getPosts() {
		// TODO Auto-generated method stub
		return null;
	}

}
