package com.yummet.bean.rest.controller;

import java.util.Map;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;
import com.yummet.business.bean.User;
import com.yummet.lib.objectProvider.PostCommentProvider;
import com.yummet.lib.objectProvider.PostProvider;
import com.yummet.lib.objectProvider.UserProvider;

/**
 * This class is used for Restful API User Control.
 * 
 * @author yucheng
 * @version 1
 * */
@Controller
public class PostControllerImpl implements PostController {

	private PostProvider postProvider;
	private UserProvider userProvider;
	private Jaxb2Marshaller jaxb2Mashaller;
	
	public void setPostProvider(PostProvider postProvider) {
		this.postProvider = postProvider;
	}
	
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	
	public void setJaxb2Mashaller(Jaxb2Marshaller jaxb2Mashaller) {
		this.jaxb2Mashaller = jaxb2Mashaller;
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=PostRestURIConstants.GET_POST)
	public PostList getPosts(@RequestBody String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.PUT, value=PostRestURIConstants.UPDATE_POST)
	public Post updatePost(String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.POST, value=PostRestURIConstants.CREATE_POST)
	public Post addPost(@RequestBody String body) {
		Map<String, String> postInfo = parsePostRequest(body);
		User user = userProvider.get(postInfo.get("email"), postInfo.get("password"));
		if (user == null) {
			//Console log
		}
		Post newPost = new Post(null, null , postInfo.get("subject"), postInfo.get("location"), Integer.parseInt(postInfo.get("quantity")));
		Post createdPost = postProvider.add(user, newPost);
		return createdPost;
	}

	private Map<String, String> parsePostRequest(String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.DELETE, value=PostRestURIConstants.DELETE_POST)
	public Post removePost(@RequestBody String body) {
		// TODO Auto-generated method stub
		return null;
	}

}
