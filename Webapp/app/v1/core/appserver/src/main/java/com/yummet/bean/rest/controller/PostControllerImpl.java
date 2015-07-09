package com.yummet.bean.rest.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ImmutableMap;
import com.yummet.business.bean.User;
import com.yummet.lib.objectProvider.PostProvider;
import com.yummet.lib.objectProvider.UserProvider;
import com.yummet.proto.Post;
import com.yummet.proto.PostList;
import com.yummet.proto.PostRequest;

/**
 * This class is used for Restful API User Control.
 * 
 * @author yucheng
 * @version 1
 * */
@Controller
public class PostControllerImpl implements PostController {

	@Autowired
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
	@RequestMapping(method = RequestMethod.GET, value = PostRestURIConstants.GET_POST)
	public Post getPost(@PathVariable String id, @RequestBody PostRequest body) {
		com.yummet.business.bean.Post post = postProvider.get(id);
		return convertFromPost(post);
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method = RequestMethod.GET, value = PostRestURIConstants.GET_POSTS)
	public com.yummet.proto.PostList getPosts(
			@PathVariable final String id,
			@RequestParam(value = "step") final String step,
			@RequestParam(value = "cursor", required = false) final String cursor,
			@RequestBody String body) {
		System.out.println("Id: " + id);
		System.out.println("Step: " + step);
		System.out.println("Cursor: " + cursor);
		PostList postList = new PostList();
		User user = userProvider.get(id);
		if (user == null) {
			// Console log
		}
		List<com.yummet.business.bean.Post> posts = postProvider.get(user,
				Integer.parseInt(step), NumberUtils.createInteger(cursor));
		for (com.yummet.business.bean.Post post : posts) {
			postList.getPostList().add(convertFromPost(post));
		}
		return postList;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method = RequestMethod.GET, value = PostRestURIConstants.GET_POSTS_STUB)
	public PostList getPosts(@PathVariable final String userid,
			@PathVariable final String step, @PathVariable final String cursor) {
		System.out.println("Id: " + userid);
		System.out.println("Step: " + step);
		System.out.println("Cursor: " + cursor);
		PostList postList = new PostList();

		List<com.yummet.business.bean.Post> posts = postProvider.get(null,
				Integer.parseInt(step), NumberUtils.createInteger(cursor));
		System.out.println("Postsize: " + posts.size());
		for (com.yummet.business.bean.Post post : posts) {
			postList.getPostList().add(convertFromPost(post));
		}
		return postList;
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method = RequestMethod.PUT, value = PostRestURIConstants.UPDATE_POST)
	public Post updatePost(@RequestHeader("Cookie") String credentials,
			@PathVariable String id, @RequestBody PostRequest body) {
		// Check if old post exists
		com.yummet.business.bean.Post oldPost = postProvider.get(id);
		if (oldPost == null) {
			// Console log
		}
		Map<String, Object> postInfo = parsePostRequest(body.getBody(),
				credentials);
		// TODO: finish the password encryption
		User user = userProvider.get((String) postInfo.get("email"),
				(String) postInfo.get("password"));
		if (user == null) {
			// Console log
		}
		// TODO: specify the location
		com.yummet.business.bean.Post newPost = new com.yummet.business.bean.Post(id, user, (String) postInfo.get("postsubject"),
				(String) postInfo.get("location"), 0); // put quantity as 0 for
														// now
		return convertFromPost(postProvider.add(user, newPost));
	}

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method = RequestMethod.POST, value = PostRestURIConstants.CREATE_POST)
	public Post addPost(@RequestHeader("Cookie") String credentials,
			@RequestBody PostRequest body) {
		// Map<String, Object> postInfo = parsePostRequest(body, credentials);
		// User user = userProvider.get(postInfo.get("email"),
		// postInfo.get("password"));
		// if (user == null) {
		// //Console log
		// }
		// // Post newPost = new Post(null, user , postInfo.get("subject"),
		// postInfo.get("location"), 0); // put quantity as 0 for now
		// // Post createdPost = postProvider.add(user, newPost);
		// return createdPost;
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	private Map<String, Object> parsePostRequest(String body, String credentials) {
		String decodeMessage = URLDecoder.decode(credentials);
		decodeMessage = StringUtils.remove(decodeMessage, "yummet=");
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		Map<String, Object> postInfoMap = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(decodeMessage);

			String userInfos = (String) ((HashMap) jsonObject
					.get("currentUser")).get("authdata");
			userInfos = new String(Base64.decodeBase64(userInfos.getBytes()),
					"UTF-8");
			String[] userInfoSection = userInfos.split(":");

			jsonObject = (JSONObject) jsonParser.parse(body);
			postInfoMap = ImmutableMap.of("email", userInfoSection[0],
					"password", userInfoSection[1], "postsubject",
					jsonObject.get("postsubject"));
			postInfoMap.put("postdescription",
					jsonObject.get("postdescription"));
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

	@net.bull.javamelody.MonitoredWithSpring
	@RequestMapping(method = RequestMethod.DELETE, value = PostRestURIConstants.DELETE_POST)
	public void removePost(@RequestHeader("Cookie") String credentials,
			@PathVariable String id, @RequestBody PostRequest body) {
		this.postProvider.remove(id);
	}

	// TODO: the function below should be refined and move to the utilities

	private Post convertFromPost(com.yummet.business.bean.Post post) {
		Post result = new Post();
		result.setBody(post.getCommentsOrDescription());
		result.setId(post.getId());
		result.setSubject(post.getSubject());
		//result.setUser(post.getCreator().getId());
		return result;
	}
}
