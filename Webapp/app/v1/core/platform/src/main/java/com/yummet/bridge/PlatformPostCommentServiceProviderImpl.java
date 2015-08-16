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

import com.yummet.entities.PostComment;

/**
 * Provide the rest api service for the post comment objects
 * 
 * @author jassica
 * @since 1
 * */
@Path("/postcomments")
public class PlatformPostCommentServiceProviderImpl extends
		BasePlatformServiceProvider<PostComment> implements
		PlatformServiceProvider {

	public PlatformPostCommentServiceProviderImpl() {
	}

	/**
	 * This function is used for communicating with app server to pass the
	 * userpost comment info from DB to appserver
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
		return getObjectFromId(commentId, PostComment.class);
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
	 * Upsert will automatically: 1. Update a post comment object if the comment
	 * already has the Id 2. Insert a post comment object if the post comment
	 * does not have a Id
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
	protected Response upsertPostComment(InputStream incomingData)
			throws Exception {
		return upsertObject(incomingData, PostComment.class);
	}

	/**
	 * Delete a post comment based on the input post comment. But we only care
	 * about the Id in the code while all other information is ignored.
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/commentDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteObject(InputStream incomingData) throws Exception {
		return deleteObject(incomingData, "id", PostComment.class);
	}
}
