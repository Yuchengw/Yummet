package com.yummet.lib.platformService;

import java.util.List;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostComment;
import com.yummet.business.bean.PostCommentList;
import com.yummet.business.bean.User;

/**
 * This is the service layer concatenate platform PostCommentEntityObject with appserver PostCommentEntityProvider
 * 
 * @author Yucheng
 * @version 1
 * */
public interface PlatformPostCommentService extends PlatformService{

	public PostComment createPostComment(PostComment postComment);
	public PostComment getPostCommentById(String id);
	public PostCommentList getPostCommentsById(String postid);
	public boolean removeById(String id);
	public List<Post> getPostByNumber(String username, String password,
			int number);
	public List<Post> get(User user, int size, int cursor);
	public Post updatePost(Post updatePost);
}
