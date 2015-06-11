package com.yummet.lib.platformService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yummet.bridge.PlatformServiceProvider;
import com.yummet.bridge.PlatformPostCommentServiceProviderImpl;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostComment;
import com.yummet.business.bean.PostCommentList;
import com.yummet.business.bean.User;

/**
 * @author yucheng
 * @since 1
 * */
public class PlatformPostCommentServiceImpl implements PlatformPostCommentService{
	PlatformServiceProvider platformPostCommentServiceProvder;
	private static final Logger logger = LoggerFactory.getLogger(PlatformPostCommentServiceImpl.class);
	
	@Override
	public PlatformServiceProvider getPlatformServiceProvider() {
		return this.platformPostCommentServiceProvder;
	}

	@Override
	public PostComment createPostComment(PostComment postComment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostComment getPostCommentById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostCommentList getPostCommentsById(String postid) {
		//((PlatformPostCommentServiceProviderImpl)platformPostCommentServiceProvder).
		return null;
	}

	@Override
	public boolean removeById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Post> getPostByNumber(String username, String password,
			int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> get(User user, int size, int cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post updatePost(Post updatePost) {
		// TODO Auto-generated method stub
		return null;
	}

}
