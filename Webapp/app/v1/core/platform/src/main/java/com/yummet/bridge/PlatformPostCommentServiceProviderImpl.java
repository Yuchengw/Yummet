package com.yummet.bridge;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.yummet.entities.PostComment;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.utilities.JsonUtil;

/**
 * Provide the rest api service for the post comment objects
 * 
 * @author jassica
 * @since 1
 * */
@Path("/postcomments")
public class PlatformPostCommentServiceProviderImpl implements PlatformServiceProvider {
	
	public PlatformPostCommentServiceProviderImpl() {
	}

	/**
	 * This function is used for communicating with app server to pass the userpost comment
	 * info from DB to appserver
	 * 
	 * @param String
	 *            id postCommentId
	 * @throws Exception
	 * */
	@GET
	@Path("/commentQueryx/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getObject(@PathParam("id") String commentId)
			throws Exception {
		if (commentId == null) {
			return Response.status(400).entity("The post comment id is required")
					.build();
		}
		PostComment result = new PostComment(commentId).load();
		return Response.status(200).entity(result.toString()).build();
	}

	/**
	 * Update the the post comment object
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/commentUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateObject(InputStream incomingData) throws Exception {
		return upsertPostComment(incomingData);
	}

	/**
	 * Insert a post comment
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/commentInsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertObject(InputStream incomingData) throws Exception {
		return upsertPostComment(incomingData);
	}

	/**
	 * Upsert will automatically: 1. Update a post comment object if the comment already
	 * has the Id 2. Insert a post comment object if the post comment does not have a Id
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/commentUpsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response upsertObject(InputStream incomingData) throws Exception {
		return upsertPostComment(incomingData);
	}

	/**
	 * This is the underlying method called by both updateObject, insertObject,
	 * and upsertObject
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	protected Response upsertPostComment(InputStream incomingData) throws Exception {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		// Get the user Object from the input string
		// Get all the detail information for the object
		Gson gson = jsonHelper.getStandardGsonForPlatformObject();
		PostComment postCommentInfo = gson.fromJson(inputStr.toString(),
				PostComment.class);
		DmlOperationWrapper dmlOperationState = postCommentInfo.save();
		if (!dmlOperationState.isBulkSuccess()) {
			dmlOperationState.toString();
			return Response.status(400)
					.entity(dmlOperationState.getAllErrorsOnEntity(postCommentInfo))
					.build();
		}
		return Response.status(200).entity(postCommentInfo.toString()).build();
	}

	/**
	 * Delete a post comment based on the input post comment. But we only care about the
	 * Id in the code while all other information is ignored.
	 * 
	 * @param incomingData
	 * @return
	 */
	@POST
	@Path("/commentDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteObject(InputStream incomingData) {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		JSONObject jsonObj = new JSONObject(inputStr.toString());
		String id = null;
		try {
			id = jsonObj.getString("id");
		} catch (Exception e) {
			return Response.status(400).entity("The post comment is required")
					.build();
		}
		PostComment postComment = new PostComment(id);
		boolean isRemoved = postComment.remove();
		if (isRemoved) {
			return Response.status(200).entity("Removing comment successfully")
					.build();
		} else {
			return Response.status(400).entity("Removing comment fails").build();
		}
	}
}
