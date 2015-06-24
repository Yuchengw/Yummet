package com.yummet.lib.objectProvider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostComment;
import com.yummet.business.bean.PostCommentList;
import com.yummet.business.bean.User;

/**
 * @author yucheng
 * @version 1
 * */
@Service
public class PostCommentProvider {
	
	private static PostCommentList allPostComments;
	/**
	 * mocking
	 * */
	static {
		allPostComments = new PostCommentList();
		Post p1 = new Post("1", new User("1", "Yucheng", "Wang",
				"ycwmike@gmail.com", "1234"), "Post1", "China", 1);
		Post p2 = new Post("2", new User("2", "George", "Lin",
				"gglin@gmail.com", "1234"), "Post2", "USA", 2);
		PostComment pm1 = new PostComment("1", p1,  "PostComment1");
		PostComment pm2 = new PostComment("2", p2, "PostComment2");
		allPostComments.add(pm1);
		allPostComments.add(pm2);
	}

	public void add(PostComment user) {
		allPostComments.add(user);
	}

	public PostComment get(int index) {
		return allPostComments.get(index);
	}

	public List<PostComment> getAll() {
		return allPostComments.getPostComments();
	}

	public void remove(int id) {
		allPostComments.remove(id);
	}

	/**
	 * update is expensive, think before do it
	 * */
	public void update(PostComment postComment) {
		update(postComment.getId(), postComment);
	}
	
	public void update(String index, PostComment newPostComment) {
		List<PostComment> allPostCommentList = allPostComments.getPostComments();
		allPostCommentList.set(Integer.parseInt(index), newPostComment);
	}
}
