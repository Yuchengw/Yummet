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
import com.yummet.entities.EntityObject;
import com.yummet.entities.PostObject;
import com.yummet.entities.UserObject;
import com.yummet.platform.adapters.DatabaseProvider;
import com.yummet.platform.adapters.MongoDbProvider;

public class PlatformPostServiceImpl implements PlatformPostService{
	PlatformServiceProvider platformPostServiceProvider;
	PlatformServiceProvider platformUserServiceProvider;
	DatabaseProvider dbProvider;
	
	private static final Logger logger = LoggerFactory.getLogger(PlatformPostServiceImpl.class);
	
	public PlatformPostServiceImpl() {
		platformPostServiceProvider =  new PlatformPostServiceProviderImpl();
		platformUserServiceProvider =  new PlatformUserServiceProviderImpl();
		dbProvider = new MongoDbProvider();
	}
	
	/**
	 * This function is used for create a new post in mongodb
	 * @throws Exception 
	 * */
	public Post createPost(Post post) {
		try {
		UserObject userObject = (UserObject) ((PlatformUserServiceProviderImpl) platformUserServiceProvider).getObject(post.getCreator().getEmail());
		PostObject newPostObject = new PostObject(userObject, post.getSubject(), post.getLocation(), post.getQuantity());
		((PlatformPostServiceProviderImpl) platformPostServiceProvider).insertObject(newPostObject);
		} catch (Exception e) {
			logger.debug("there is something wrong when inserting post object" + e.getStackTrace());
		}
		return post;
	}

	/**
	 * This function is used for update a post in mongodb
	 * @throws Exception 
	 * */
	public Post updatePost(Post post) {
		try {
		UserObject userObject = (UserObject) ((PlatformUserServiceProviderImpl) platformUserServiceProvider).getObject(post.getCreator().getEmail());
		PostObject newPostObject = new PostObject(userObject, post.getSubject(), post.getLocation(), post.getQuantity());
		((PlatformPostServiceProviderImpl) platformPostServiceProvider).updateObject(newPostObject);
		} catch (Exception e) {
			logger.error("there is something wrong when updating post object" + e.getStackTrace());
		}
		return post;
	}
	
	@Override
	public PlatformServiceProvider getPlatformServiceProvider() {
		return platformPostServiceProvider;
	}
	
	/**
	 * This function is used from get appserver version post object from platform
	 * */
	public Post getPostById(String postId) {
		PostObject platformPostObject = null;
		Post post = null;
		try {
			platformPostObject = (PostObject) ((PlatformPostServiceProviderImpl) platformPostServiceProvider).getObject(postId);
			post = new Post();
			copyPost(platformPostObject, post);
		} catch (Exception e) {
			logger.error("Error happens when retriving User object" + e.getStackTrace());
		}
		return post;
	}

	private void copyPost(PostObject platformPost, Post post) throws Exception {
		if (platformPost == null || post  == null) {
			throw new Exception("Can't copy"); // should be more specific
		}
		post.setCost(platformPost.getCost());
		post.setCommentsOrDescription(platformPost.getCommentsOrDescription());
		post.setCreatedDate(platformPost.getCreatedDate());
		// this is too heavy... we could have a much better way to do this
		post.setCreator(new User(platformPost.getCreator().getId(),
								 platformPost.getCreator().getFirstName(),
								 platformPost.getCreator().getLastName(),
								 platformPost.getCreator().getEmail(),
								 platformPost.getCreator().getPassword()));
		post.setExpireDate(platformPost.getExpireDate());
		post.setImage(platformPost.getImage());
		post.setId(platformPost.getId());
//		post.setLastModifiedBy(platformPost.getLastModifiedBy());
		post.setLastModifiedDate(platformPost.getLastModifiedDate());
		post.setLocation(platformPost.getLocation());
		post.setNumberOfLikes(platformPost.getNumberOfLikes());
		post.setNumberOfOrders(platformPost.getNumberOfOrders());
		post.setSubject(platformPost.getSubject());
		post.setType(platformPost.getType());
		post.setPostCategory(platformPost.getPostCategory());
		post.setQuantity(platformPost.getQuantity());
//		post.setPartners(platformPost.getPartners());
	}

	public boolean removeById(String postId) {
		try {
			PostObject newPostObject = new PostObject(postId);
			((PlatformPostServiceProviderImpl) platformPostServiceProvider).deleteObject(newPostObject);
		} catch (Exception e) {
			logger.error("there is something wrong when deleting post object" + e.getStackTrace());
			return false;
		}
		return true;
	}
	
	// TODO: for now size and cursor are not used
	public List<Post> get(User user, int size, int cursor) {
		List<EntityObject> postList = new ArrayList<EntityObject>();
		UserObject userObject;
		try {
			userObject = (UserObject) ((PlatformUserServiceProviderImpl) platformUserServiceProvider).getObject(user.getEmail());
			PostObject po = new PostObject(userObject, null, null, 0);
			postList = dbProvider.getRecordsBasedOnQuery(po);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return convert(postList);
	}
	
	private List<Post> convert(List<EntityObject> postList) {
		List<Post> result = new ArrayList<Post>();
		for(EntityObject postObject : postList) {
			Post post = new Post();
			try {
				copyPost((PostObject)postObject, post);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(post);
		}
		return result;
	}

}
