package com.yummet.lib.platformService;

import java.util.List;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.User;

/**
 * This is the service layer concatenate platform PostEntityObject with appserver PostEntityProvider
 * 
 * @author yucheng
 * @version 1
 * */
public interface PlatformPostService extends PlatformService{

	public Post createPost(Post post);
	public Post getPostById(String id);
	public boolean removeById(String id);
	public List<Post> get(User user, int size, int cursor);
}
