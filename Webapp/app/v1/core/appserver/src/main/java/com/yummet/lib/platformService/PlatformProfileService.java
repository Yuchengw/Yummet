package com.yummet.lib.platformService;

import org.springframework.context.annotation.Profile;

import com.yummet.api.rest.AppRestProfileClientImpl;

/**
 * @author yucheng
 * @since 1
 * */
public abstract class PlatformProfileService implements PlatformService{

	public AppRestProfileClientImpl getProfileRestClient() {
		return new AppRestProfileClientImpl(Profile.class);
	}
}
