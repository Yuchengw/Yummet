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

import com.yummet.entities.LikePostObject;

/**
 * Provide the rest api service for the platform like objects
 * 
 * @author Jassica
 * @since 1
 * */
@Path("/likes")
public class PlatformLikePostServiceProvider extends
		BasePlatformServiceProvider<LikePostObject> implements
		PlatformServiceProvider {

	public PlatformLikePostServiceProvider() {
	}

	/**
	 * This function is used for communicating with app server to pass the like
	 * info from DB to appserver
	 * 
	 * @param String
	 *            id likeId
	 * @throws Exception
	 * */
	@GET
	@Path("/likeQueryx/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getObject(@PathParam("id") String likeId) throws Exception {
		return getObjectFromId(likeId, LikePostObject.class);
	}

	/**
	 * Update the the like
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/likeUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateObject(InputStream incomingData) throws Exception {
		return upsertLike(incomingData);
	}

	/**
	 * Insert a like
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/likeInsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertObject(InputStream incomingData) throws Exception {
		return upsertLike(incomingData);
	}

	/**
	 * Upsert will automatically make decision on insert or update: 1. Update
	 * requires the entity has the Id 2. Inserting a like does not require the
	 * entity to have id
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/likeUpsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response upsertObject(InputStream incomingData) throws Exception {
		return upsertLike(incomingData);
	}

	/**
	 * This is the underlying method called by both updateObject, insertObject,
	 * and upsertObject
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	protected Response upsertLike(InputStream incomingData) throws Exception {
		// The parent post update is included in the like entity save
		return upsertObject(incomingData, LikePostObject.class);
	}

	/**
	 * Delete a like based on the input entity. But we only care about the Id
	 * in the code while all other information is ignored.
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/likeDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteObject(InputStream incomingData) throws Exception {
		return deleteObject(incomingData, "id", LikePostObject.class);
	}
}
