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
import com.yummet.entities.OrderObject;

/**
 * Provide the rest api service for the platform order objects
 * 
 * @author Jassica
 * @since 1
 * */
@Path("/orders")
public class PlatformOrderServiceProvider extends
		BasePlatformServiceProvider<OrderObject> implements
		PlatformServiceProvider {

	public PlatformOrderServiceProvider() {
	}

	/**
	 * This function is used for communicating with app server to pass the order
	 * info from DB to appserver
	 * 
	 * @param String
	 *            id orderId
	 * @throws Exception
	 * */
	@GET
	@Path("/orderQueryx/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getObject(@PathParam("id") String orderId) throws Exception {
		return getObjectFromId(orderId, OrderObject.class);
	}

	/**
	 * Update the the order
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/orderUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateObject(InputStream incomingData) throws Exception {
		return upsertOrder(incomingData);
	}

	/**
	 * Insert a order
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/orderInsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertObject(InputStream incomingData) throws Exception {
		return upsertOrder(incomingData);
	}

	/**
	 * Upsert will automatically make decision on insert or update: 1. Update
	 * requires the entity has the Id 2. Inserting a order does not require the
	 * entity to have id
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/orderUpsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response upsertObject(InputStream incomingData) throws Exception {
		return upsertOrder(incomingData);
	}

	/**
	 * This is the underlying method called by both updateObject, insertObject,
	 * and upsertObject
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	protected Response upsertOrder(InputStream incomingData) throws Exception {
		// The parent post update is included in the order entity save
		return upsertObject(incomingData, OrderObject.class);
	}

	/**
	 * Delete a order based on the input entity. But we only care about the Id
	 * in the code while all other information is ignored.
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/orderDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteObject(InputStream incomingData) throws Exception {
		return deleteObject(incomingData, "id", OrderObject.class);
	}
}
