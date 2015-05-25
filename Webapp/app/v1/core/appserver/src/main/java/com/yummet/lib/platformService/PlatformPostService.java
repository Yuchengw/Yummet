package com.yummet.lib.platformService;

import com.yummet.business.bean.Post;

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
}
