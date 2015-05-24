package com.yummet.bridge;

import com.yummet.entities.EntityObject;
import com.yummet.entities.PostObject;
import com.yummet.platform.func.DmlOperationWrapper;

/**
 * @author yucheng
 * @since 1
 * */
public class PlatformPostServiceProviderImpl implements PlatformServiceProvider {
	
	public PlatformPostServiceProviderImpl() {
	}
	
	/**
	 * */
	public EntityObject getObject(String id) throws Exception {
		if (id == null) {
		}
		return new PostObject(id).load();
	}
	
	/**
	 * 
	 * */
	public EntityObject updateObject(PostObject postInfo) {
		DmlOperationWrapper dmlOperationState = postInfo.save();
		if (!dmlOperationState.isBulkSuccess()){
		} 
		return (EntityObject) dmlOperationState.getEntityObjectsWithoutError();
	}
	
	/**
	 * 
	 * */
	public EntityObject insertObject(PostObject postInfo) throws Exception {
		DmlOperationWrapper dmlOperationState = postInfo.save();
		if (!dmlOperationState.isBulkSuccess()) {
			throw new Exception("Saving post " + postInfo.getId() + " failed");
		}
		return postInfo;
	}
	
	/**
	 * 
	 * */
	public Boolean deleteObject(PostObject postInfo) {
		return postInfo.remove();
	}
}
