package com.yummet.bridge;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yummet.entities.UserObject;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.utilities.JsonUtil;

/**
 * Platform provide the services for the user object
 * 
 * @author yucheng
 * @since 1
 * 
 * @author jassica
 * @since 2
 * */
@Path("/users")
public class PlatformUserServiceProviderImpl implements PlatformServiceProvider {

	public PlatformUserServiceProviderImpl() {
	}

	/**
	 * This function is used for communicating with app server to pass the user
	 * info from DB to appserver
	 * 
	 * @param String
	 *            userEmail
	 * @return Platform User object
	 * @throws Exception
	 * */
	@GET
	@Path("/userQueryx/{email}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getObject(@PathParam("email") String userEmail)
			throws Exception {
		if (userEmail == null) {
			return Response.status(400).entity("The user email is required")
					.build();
		}
		UserObject result = new UserObject(userEmail).load();
		return Response.status(200).entity(result.toString()).build();
	}

	/**
	 * Update the the user object
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/userUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateObject(InputStream incomingData) throws Exception {
		return upsertUser(incomingData);
	}

	/**
	 * Insert a user
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/userInsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertObject(InputStream incomingData) throws Exception {
		return upsertUser(incomingData);
	}

	/**
	 * Upsert will automatically: 1. Update a user object if the user already
	 * has the Id 2. Insert a user object if the user does not have a Id
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/userUpsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response upsertObject(InputStream incomingData) throws Exception {
		return upsertUser(incomingData);
	}

	/**
	 * This is the underlying method called by both updateObject, insertObject,
	 * and upsertObject
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	protected Response upsertUser(InputStream incomingData) throws Exception {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		// Get the user Object from the input string
		// Get all the detail information for the object
		Gson gson = jsonHelper.getStandardGsonForPlatformObject();
		UserObject userInfo = gson.fromJson(inputStr.toString(),
				UserObject.class);
		DmlOperationWrapper dmlOperationState = userInfo.save();
		if (!dmlOperationState.isBulkSuccess()) {
			dmlOperationState.toString();
			return Response.status(400)
					.entity(dmlOperationState.getAllErrorsOnEntity(userInfo))
					.build();
		}
		return Response.status(200).entity(userInfo.toString()).build();
	}

	/**
	 * Delete a user based on the input user object. But we only care about the
	 * userId in the code while all other information is ignored.
	 * 
	 * @param incomingData
	 * @return
	 */
	@POST
	@Path("/userDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteObject(InputStream incomingData) {// @PathParam("email")
															// String userEmail)
															// {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		JSONObject jsonObj = new JSONObject(inputStr.toString());
		String userId = null;
		try {
			userId = jsonObj.getString("id");
		} catch (Exception e) {
			return Response.status(400).entity("The user email is required")
					.build();
		}
		UserObject userInfo = new UserObject(userId, null);
		boolean isRemoved = userInfo.remove();
		if (isRemoved) {
			return Response.status(200).entity("Removing user successfully")
					.build();
		} else {
			return Response.status(400).entity("Removing user fails").build();
		}
	}

}
