package com.yummet.bean.rest.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;
import com.yummet.business.bean.User;
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
	public Post getPost(@PathVariable String id, @RequestBody String body) {
		return postProvider.get(id);
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=PostRestURIConstants.GET_POSTS)
	public PostList getPosts(@PathVariable final String id, @RequestParam(value="step") final String step,
			@RequestParam(value="cursor") final String cursor, String body) throws Exception {
		PostList postList = new PostList();
		User user = userProvider.get(id);
		Authentication userContext = SecurityContextHolder.getContext().getAuthentication();
		if (user == null) {
			throw new Exception("user object not find: " + userContext.getName());
		}
		List<Post> posts = postProvider.get(user, Integer.parseInt(step), Integer.parseInt(cursor));
		postList.setPosts(posts);
		return postList;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.PUT, value=PostRestURIConstants.UPDATE_POST)
	public Post updatePost(@PathVariable String id, @RequestBody String body) throws Exception {
		// Check if old post exists
		Post oldPost = postProvider.get(id);
		if(oldPost == null) {
			throw new Exception("Post does not exist: " + id);
		}
		Authentication userContext = SecurityContextHolder.getContext().getAuthentication();
		User user = userProvider.get(userContext.getName());
		if (user == null) {
			throw new Exception("user object not find: " + userContext.getName());
		}
		// TODO: specify the location
		Post newPost = new ObjectMapper().readValue(body, Post.class);
		newPost.setId(id);
		Post updatedPost = postProvider.add(user, newPost);
		return updatedPost;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.POST, value=PostRestURIConstants.CREATE_POST)
	public Post addPost(@RequestBody String body) throws Exception {
		Authentication userContext = SecurityContextHolder.getContext().getAuthentication();
		User user = userProvider.get(userContext.getName());
		if (user == null) {
			throw new Exception("user object not find: " + userContext.getName());
		}
		Post newPost = null;
		try {
			newPost = new ObjectMapper().readValue(body, Post.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Post createdPost = postProvider.add(user, newPost);
		return createdPost;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.DELETE, value=PostRestURIConstants.DELETE_POST)
	public void removePost(@PathVariable String id, @RequestBody String body) {
		this.postProvider.remove(id);
	}
	
	//TODO: the function below should be refined and move to the utilities
	
}
