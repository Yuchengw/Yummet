package com.yummet.lib.platformService;

import org.springframework.context.annotation.Profile;

import com.yummet.api.rest.AppRestUserClientImpl;
import com.yummet.api.rest.RestClient;
import com.yummet.business.bean.Order;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostComment;
import com.yummet.business.bean.User;

/**
 * This is the service layer concatenate platform UserEntityObject with appserver UserEntityProvider
 * 
 * Use Strategy pattern to decouple different ways of communicating with Platform code
 * 
 * @author yucheng
 * @since 1
 * */
public abstract class PlatformUserService implements PlatformService {
	
	public AppRestUserClientImpl getUserRestClient() {
		return new AppRestUserClientImpl(User.class);
	}
}
