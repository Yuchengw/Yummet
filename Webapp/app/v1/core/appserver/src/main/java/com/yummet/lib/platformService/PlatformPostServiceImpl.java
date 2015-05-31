package com.yummet.lib.platformService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yummet.bridge.PlatformPostServiceProviderImpl;
import com.yummet.bridge.PlatformServiceProvider;
import com.yummet.bridge.PlatformUserServiceProviderImpl;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.User;
import com.yummet.entities.PostObject;
import com.yummet.entities.UserObject;

public class PlatformPostServiceImpl extends PlatformPostService {
	PlatformServiceProvider platformPostServiceProvider;
	PlatformServiceProvider platformUserServiceProvider;

	private static final Logger logger = LoggerFactory
			.getLogger(PlatformPostServiceImpl.class);

	public PlatformPostServiceImpl() {
		platformPostServiceProvider = new PlatformPostServiceProviderImpl();
		platformUserServiceProvider = new PlatformUserServiceProviderImpl();
	}

	/**
	 * This function is used for create user in mongodb
	 * 
	 * @throws Exception
	 * */
	public Post createPost(Post post) {
		try {
			UserObject userObject = (UserObject) ((PlatformUserServiceProviderImpl) platformUserServiceProvider)
					.getObject(post.getCreator().getEmail());
			PostObject newPostObject = new PostObject(userObject,
					post.getSubject(), post.getLocation(), post.getQuantity());
			((PlatformPostServiceProviderImpl) platformPostServiceProvider)
					.insertObject(newPostObject);
		} catch (Exception e) {
			logger.debug("there is something wrong when inserting user object"
					+ e.getStackTrace());
		}
		return post;
	}

	@Override
	public PlatformServiceProvider getPlatformServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This function is used from get appserver version post object from
	 * platform
	 * */
	public Post getPostById(String postId) {
		PostObject platformPostObject = null;
		Post post = null;
		try {
			platformPostObject = (PostObject) ((PlatformPostServiceProviderImpl) platformPostServiceProvider).getObject(postId);
			post = new Post();
			copySinglePost(platformPostObject, post);
		} catch (Exception e) {
			logger.debug("Error happens when retriving User object"
					+ e.getStackTrace());
		}
		return post;
	}

	public List<Post> getPostByNumber(String username, String password,
			int number) {
		List<Post> posts = new ArrayList<Post>();
		try {
			@SuppressWarnings("unchecked")
			List<PostObject> platformPostObjects = (List<PostObject>) ((PlatformPostServiceProviderImpl) platformPostServiceProvider)
					.getObjectByUser(username, password, number);
			copyPosts(platformPostObjects, posts);
		} catch (Exception e) {
			logger.debug("Error happens when retriving User object"
					+ e.getStackTrace());
		}
		return posts;
	}

	private void copyPosts(List<PostObject> platformPosts, List<Post> posts) throws Exception {
		for (PostObject platformPost : platformPosts) {
			Post post = new Post();
			if (platformPost == null || post == null) {
				throw new Exception("Can't copy"); // should be more specific
			}
			copySinglePost(platformPost, post);
			posts.add(post);
		}
	}
	
	private void copySinglePost(PostObject platformPost, Post post ) {
		post.setCost(platformPost.getCost());
		post.setCommentsOrDescription(platformPost
				.getCommentsOrDescription());
		post.setCreatedDate(platformPost.getCreatedDate());
		// this is too heavy... we could have a much better way to do this
		post.setCreator(new User(platformPost.getCreator().getId(),
				platformPost.getCreator().getFirstName(), platformPost
						.getCreator().getLastName(), platformPost
						.getCreator().getEmail(), platformPost.getCreator()
						.getPassword()));
		post.setExpireDate(platformPost.getExpireDate());
		post.setImage(platformPost.getImage());
		post.setId(platformPost.getId());
		// post.setLastModifiedBy(platformPost.getLastModifiedBy());
		post.setLastModifiedDate(platformPost.getLastModifiedDate());
		post.setLocation(platformPost.getLocation());
		post.setNumberOfLikes(platformPost.getNumberOfLikes());
		post.setNumberOfOrders(platformPost.getNumberOfOrders());
		post.setSubject(platformPost.getSubject());
		post.setType(platformPost.getType());
		post.setPostCategory(platformPost.getPostCategory());
		post.setQuantity(platformPost.getQuantity());
		// post.setPartners(platformPost.getPartners());
	}

	public Boolean removeById(int postId) {
		// TODO Auto-generated method stub
		return null;
	}

}
