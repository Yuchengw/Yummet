package com.yummet.lib.platformService;

import com.yummet.api.rest.AppRestPostCommentClientImpl;
import com.yummet.business.bean.PostComment;

/**
 * This is the service layer concatenate platform PostCommentEntityObject with appserver PostCommentEntityProvider
 * 
 * @author Yucheng
 * @since 1
 * */
public abstract class PlatformPostCommentService implements PlatformService{
	
	/**
	 * This should not be used often. Post Api should take care of its brother: comment very well.
	 * */
	public AppRestPostCommentClientImpl getPostCommentRestClient() {
		return new AppRestPostCommentClientImpl(PostComment.class);
	}
}
