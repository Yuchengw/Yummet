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
import com.yummet.entities.ProvidePostObject;
import com.yummet.entities.UserObject;
import com.yummet.platform.adapters.DatabaseProvider;
import com.yummet.platform.adapters.MongoDbProvider;

public class PlatformPostServiceImpl implements PlatformPostService {
	PlatformServiceProvider platformPostServiceProvider;
	PlatformServiceProvider platformUserServiceProvider;
	DatabaseProvider dbProvider;

	private static final Logger logger = LoggerFactory
			.getLogger(PlatformPostServiceImpl.class);

	public PlatformPostServiceImpl() {
		platformPostServiceProvider = new PlatformPostServiceProviderImpl();
		platformUserServiceProvider = new PlatformUserServiceProviderImpl();
		dbProvider = new MongoDbProvider();
	}

	/**
	 * This function is used for create a new post in mongodb
	 * 
	 * @throws Exception
	 * */
	public Post createPost(Post post) {
		try {
			UserObject userObject = (UserObject) ((PlatformUserServiceProviderImpl) platformUserServiceProvider)
					.getObject(post.getCreator().getEmail());
			PostObject newPostObject = //TODO:
					new ProvidePostObject(userObject,
					post.getSubject(), post.getLocation(), post.getQuantity());
			((PlatformPostServiceProviderImpl) platformPostServiceProvider)
					.insertObject(newPostObject);
		} catch (Exception e) {
			logger.debug("there is something wrong when inserting post object"
					+ e.getStackTrace());
		}
		return post;
	}

	/**
	 * This function is used for update a post in mongodb
	 * 
	 * @throws Exception
	 * */
	public Post updatePost(Post post) {
		try {
			UserObject userObject = (UserObject) ((PlatformUserServiceProviderImpl) platformUserServiceProvider)
					.getObject(post.getCreator().getEmail());
			PostObject newPostObject = new ProvidePostObject(userObject,
					post.getSubject(), post.getLocation(), post.getQuantity());
			((PlatformPostServiceProviderImpl) platformPostServiceProvider)
					.updateObject(newPostObject);
		} catch (Exception e) {
			logger.error("there is something wrong when updating post object"
					+ e.getStackTrace());
		}
		return post;
	}

	@Override
	public PlatformServiceProvider getPlatformServiceProvider() {
		return platformPostServiceProvider;
	}

	/**
	 * This function is used from get appserver version post object from
	 * platform
	 * */
	public Post getPostById(String postId) {
		PostObject platformPostObject = null;
		Post post = null;
		try {
			platformPostObject = (PostObject) ((PlatformPostServiceProviderImpl) platformPostServiceProvider)
					.getObject(postId);
			post = new Post();
			copySinglePost(platformPostObject, post);
		} catch (Exception e) {
			logger.error("Error happens when retriving post object"
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
			logger.debug("Error happens when retriving post object"
					+ e.getStackTrace());
		}
		return posts;
	}

	private void copyPosts(List<PostObject> platformPosts, List<Post> posts)
			throws Exception {
		for (PostObject platformPost : platformPosts) {
			Post post = new Post();
			if (platformPost == null || post == null) {
				throw new Exception("Can't copy"); // should be more specific
			}
			copySinglePost(platformPost, post);
			posts.add(post);
		}
	}

	private void copySinglePost(PostObject platformPost, Post post) {
		post.setCost(platformPost.getCost());
		post.setCommentsOrDescription(platformPost.getCommentsOrDescription());
		post.setCreatedDate(platformPost.getCreatedDate().toDate());
		// this is too heavy... we could have a much better way to do this
		post.setCreator(new User(platformPost.getCreator().getId(),
				platformPost.getCreator().getFirstName(), platformPost
						.getCreator().getLastName(), platformPost.getCreator()
						.getEmail(), platformPost.getCreator().getPassword()));
		post.setExpireDate(platformPost.getExpireDate().toDate());
		post.setImage(platformPost.getImage());
		post.setId(platformPost.getId());
		// post.setLastModifiedBy(platformPost.getLastModifiedBy());
		post.setLastModifiedDate(platformPost.getLastModifiedDate().toDate());
		post.setLocation(platformPost.getLocation());
		post.setNumberOfLikes(platformPost.getNumberOfLikes());
		post.setNumberOfOrders(platformPost.getNumberOfOrders());
		post.setSubject(platformPost.getSubject());
//		post.setType(platformPost.getType()); // TODO
		post.setPostCategory(platformPost.getPostCategory());
		post.setQuantity(platformPost.getQuantity());
		// post.setPartners(platformPost.getPartners());
	}

	public boolean removeById(String postId) {
		try {
			PostObject newPostObject = new ProvidePostObject(postId);
			((PlatformPostServiceProviderImpl) platformPostServiceProvider)
					.deleteObject(newPostObject);
		} catch (Exception e) {
			logger.error("there is something wrong when deleting post object"
					+ e.getStackTrace());
			return false;
		}
		return true;
	}

	// TODO: for now size and cursor are not used
	public List<Post> get(User user, int size, int cursor) {
		List<EntityObject> postList = new ArrayList<EntityObject>();
		UserObject userObject;
		try {
			userObject = (UserObject) ((PlatformUserServiceProviderImpl) platformUserServiceProvider)
					.getObject(user.getEmail());
			PostObject po = new ProvidePostObject(userObject, null, null, 0);
			postList = dbProvider.getRecordsBasedOnQuery(po);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return convert(postList);
	}

	private List<Post> convert(List<EntityObject> postList) {
		List<Post> result = new ArrayList<Post>();
		for (EntityObject postObject : postList) {
			Post post = new Post();
			copySinglePost((PostObject) postObject, post);
			result.add(post);
		}
		return result;
	}

}
