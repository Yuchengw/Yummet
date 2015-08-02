package com.yummet.bridge;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.yummet.entities.EntityObject;
import com.yummet.entities.OrderObject;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.utilities.JsonUtil;

/**
 * Base Platform Service for Provider
 * @author jassica
 *
 * @param <T>
 */
public abstract class BasePlatformServiceProvider<T extends EntityObject>{
	/**
	 * General method to get the PlatformObject from Id
	 * @param id
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	protected Response getObjectFromId(String id, Class<T> clazz) throws Exception {
		if (id == null) {
			return Response.status(400).entity("The order id is required")
					.build();
		}
		T result = clazz.newInstance();
		result.setId(id);
		result = (T) result.load();
		return Response.status(200).entity(result.toString()).build();
	}
	
	/**
	 * General method to upsert a platform object to the database
	 * @param incomingData
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	protected Response upsertObject(InputStream incomingData, Class<T> clazz) throws Exception {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		// Get the user Object from the input string
		// Get all the detail information for the object
		Gson gson = jsonHelper.getStandardGsonForPlatformObject();
		T order = gson.fromJson(inputStr.toString(),
				clazz);
		DmlOperationWrapper dmlOperationState = order.save();
		if (!dmlOperationState.isBulkSuccess()) {
			dmlOperationState.toString();
			return Response.status(400)
					.entity(dmlOperationState.getAllErrorsOnEntity(order))
					.build();
		}
		return Response.status(200).entity(order.toString()).build();
	}
}
