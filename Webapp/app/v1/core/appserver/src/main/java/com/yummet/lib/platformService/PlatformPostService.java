package com.yummet.lib.platformService;

import java.util.List;

import com.yummet.api.rest.AppRestPostClientImpl;
import com.yummet.api.rest.AppRestUserClientImpl;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.User;

/**
 * This is the service layer concatenate platform PostEntityObject with appserver PostEntityProvider
 * 
 * @author yucheng
 * @version 1
 * */
public abstract class PlatformPostService implements PlatformService{

	public AppRestPostClientImpl getPostRestClient() {
		return new AppRestPostClientImpl(Post.class);
	}	
	
	public AppRestUserClientImpl getUserRestClient() {
		return new AppRestUserClientImpl(User.class);
	}
}
