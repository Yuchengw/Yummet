package com.yummet.bean.rest.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ImmutableMap;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;
import com.yummet.business.bean.User;
import com.yummet.lib.objectProvider.PostProvider;
import com.yummet.lib.objectProvider.UserProvider;
import com.yummet.proto.PostRequest;

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
	public Post getPost(@PathVariable String id) {
		return postProvider.get(id);
	}
	
	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.GET, value=PostRestURIConstants.GET_POSTS)
	public PostList getPosts(@PathVariable final String userid, @RequestParam(value="step") final String step,
			@RequestParam(value="cursor") final String cursor) {
		PostList postList = new PostList();
		User user = userProvider.get(userid);
		if (user == null) {
			//Console log
		}
		List<Post> posts = postProvider.get(user, Integer.parseInt(step), Integer.parseInt(cursor));
		postList.setPosts(posts);
		return postList;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.PUT, value=PostRestURIConstants.UPDATE_POST)
	public Post updatePost(@RequestHeader("Cookie") String credentials, @PathVariable String id, @RequestBody PostRequest body) {
		// Check if old post exists
		Post oldPost = postProvider.get(id);
		if(oldPost == null) {
			// Console log
		}
		//Map<String, String> postInfo = parsePostRequest(body, credentials);
		User user = userProvider.get(body.getUser(), credentials);
		User user = userProvider.get((String)postInfo.get("email"), (String)postInfo.get("password"));
		if (user == null) {
			//Console log
		}
		Post newPost = new Post(id, user , body.getSubject(), "", 0); // put quantity as 0 for now
		Post newPost = new Post(id, user , (String)postInfo.get("postsubject"), (String) postInfo.get("location"), 0); // put quantity as 0 for now
		Post updatedPost = postProvider.add(user, newPost);
		return updatedPost;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.DELETE, value=PostRestURIConstants.DELETE_POST)
	public void removePost(@RequestHeader("Cookie") String credentials, @PathVariable String id, @RequestBody PostRequest body) {
		this.postProvider.remove(id);
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method=RequestMethod.POST, value=PostRestURIConstants.CREATE_POST)
	public Post addPost(@RequestHeader("Cookie") String credentials, @RequestBody PostRequest body) {
		//Map<String, String> postInfo = parsePostRequest(body, credentials);
		User user = userProvider.get(body.getUser(), credentials);
//		if (user == null) {
//			//Console log
//		}
		Post newPost = new Post(null, user , body.getSubject(), "", 0); // put quantity as 0 for now
////		Post createdPost = postProvider.add(user, newPost);
//		return createdPost;
		return null;
	}

	@SuppressWarnings({"rawtypes" })
	private Map<String, Object> parsePostRequest(String body, String credentials) {
		String decodeMessage = URLDecoder.decode(credentials);
		decodeMessage = StringUtils.remove(decodeMessage, "yummet=");
		JSONParser jsonParser =new JSONParser();
		JSONObject jsonObject = null;
		Map<String, Object> postInfoMap = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(decodeMessage);
			
			String userInfos = (String) ((HashMap) jsonObject.get("currentUser")).get("authdata");
			userInfos = new String(Base64.decodeBase64(userInfos.getBytes()), "UTF-8");
			String[] userInfoSection = userInfos.split(":");
			
			jsonObject = (JSONObject) jsonParser.parse(body);
			postInfoMap = ImmutableMap.of(
				"email", userInfoSection[0],
				"password", userInfoSection[1],
				"postsubject", jsonObject.get("postsubject")
			);
			postInfoMap.put("postdescription", jsonObject.get("postdescription"));
			postInfoMap.put("postcategory", jsonObject.get("postcategory"));
			postInfoMap.put("usemap", jsonObject.get("usemap"));
			postInfoMap.put("postimage", jsonObject.get("postimage"));
			postInfoMap.put("couldinvite", jsonObject.get("couldinvite"));
			postInfoMap.put("startdate", jsonObject.get("startdate"));
			postInfoMap.put("enddate", jsonObject.get("enddate"));
			postInfoMap.put("type", jsonObject.get("type"));
			
			// TODO: separate the provider post and ask post
			postInfoMap.put("issecrete", jsonObject.get("issecrete"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postInfoMap;
	}
	
	//TODO: the function below should be refined and move to the utilities
	
}
