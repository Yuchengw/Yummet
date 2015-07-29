package com.yummet.lib.platformService;

import com.yummet.api.rest.AppRestOrderClientImpl;
import com.yummet.business.bean.Order;

/**
 * This is the service layer concatenate platform OrderEntityObject with appserver OrderEntityProvider
 * 
 * @author Yucheng
 * @since 1
 * */
public abstract class PlatformOrderService implements PlatformService{
	
	public AppRestOrderClientImpl getOrderRestClient() {
		return new AppRestOrderClientImpl(Order.class);
	}
}