package com.yummet.bridge;

import com.yummet.entities.EntityObject;
import com.yummet.entities.PostComment;

public class PlatformPostCommentServiceProviderImpl implements PlatformServiceProvider {

	/**
	 * TODO: should be renamed as getObjectById
	 * */
	public EntityObject getObject(String id) throws Exception {
		if (id == null) {
		}
		return new PostComment(id).load();
	}
}
