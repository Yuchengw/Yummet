package com.yummet.bridge;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.gson.Gson;
import com.yummet.entities.ProvidePostObject;
import com.yummet.entities.RequestPostObject;
import com.yummet.platform.adapters.DatabaseProvider;
import com.yummet.platform.adapters.SystemContext;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.utilities.JsonUtil;

/**
 * Provide the rest api service for the post objects, including
 * ProvidePostObject and RequestPostObject
 * 
 * @author yucheng
 * @since 1
 * 
 * @author jassica
 * @since 2
 * */
@Path("/posts")
public class PlatformPostServiceProviderImpl implements PlatformServiceProvider {

	public PlatformPostServiceProviderImpl() {
	}

	/**
	 * Return the ProvidePostObject through the Id
	 * */
	@GET
	@Path("/providePostQueryx/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProvidePostById(@PathParam("id") String id)
			throws Exception {
		if (id == null) {
			return Response.status(400)
					.entity("The post id is required for retrieve").build();
		}
		ProvidePostObject result = (ProvidePostObject) new ProvidePostObject(id)
				.load();
		return Response.status(200).entity(result.toString()).build();
	}

	/**
	 * Return the RequestPostObject through the Id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/requestPostQueryx/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRequestPostById(@PathParam("id") String id)
			throws Exception {
		if (id == null) {
			return Response.status(400)
					.entity("The post id is required for retrieve").build();
		}
		RequestPostObject result = (RequestPostObject) new RequestPostObject(id)
				.load();
		return Response.status(200).entity(result.toString()).build();
	}

	/**
	 * Query a certain amount of ProvidePostObject based on the start index For
	 * example, /posts/providePostLimitQuery/test@test.com/1/2 Get 2
	 * ProvidePostObject where the creator's email is test@test.com but we will
	 * skip the top 1 post
	 * 
	 * @param username
	 * @param index
	 * @param number
	 * @return
	 */
	@GET
	@Path("/providePostLimitQuery/{username}/{index}/{number}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProvidePostByUser(
			@PathParam("username") String username,
			@PathParam("index") int index, @PathParam("number") int number) {
		if (index < 0) {
			return Response.status(400)
					.entity("The request should from index 0").build();
		}
		if (number <= 0) {
			return Response.status(400)
					.entity("The request post number should be greater than 0")
					.build();
		}
		// For version1, using the db query to find all the posts
		DatabaseProvider dbContext = SystemContext.getContext();
		// Set up the query
		Query query = new Query();
		Criteria criteria = Criteria.where("creator.email").is(username);
		query.addCriteria(criteria);
		query.with(new Sort(new Sort.Order(Direction.DESC, "lastModifiedDate")));
		query.skip(index);
		query.limit(number);
		MongoTemplate dbAccess = (MongoTemplate) dbContext.getDbThread();
		List<ProvidePostObject> dbRecords = (List<ProvidePostObject>) dbAccess
				.find(query, ProvidePostObject.class);
		return Response.status(200).entity(dbRecords.toString()).build();
	}

	/**
	 * Query a certain amount of RequestPostObject based on the start index For
	 * example, /posts/requestPostLimitQuery/test@test.com/1/2 Get 2
	 * RequestPostObject where the creator's email is test@test.com but we will
	 * skip the top 1 post
	 * 
	 * @param username
	 * @param index
	 * @param number
	 * @return
	 */
	@GET
	@Path("/requestPostLimitQuery/{username}/{index}/{number}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRequestPostByUser(
			@PathParam("username") String username,
			@PathParam("index") int index, @PathParam("number") int number) {
		if (index < 0) {
			return Response.status(400)
					.entity("The request should from index 0").build();
		}
		if (number <= 0) {
			return Response.status(400)
					.entity("The request post number should be greater than 0")
					.build();
		}
		// For version1, using the db query to find all the posts
		DatabaseProvider dbContext = SystemContext.getContext();
		// Set up the query
		Query query = new Query();
		Criteria criteria = Criteria.where("creator.email").is(username);
		query.addCriteria(criteria);
		query.with(new Sort(new Sort.Order(Direction.DESC, "lastModifiedDate")));
		query.skip(index);
		query.limit(number);
		MongoTemplate dbAccess = (MongoTemplate) dbContext.getDbThread();
		List<RequestPostObject> dbRecords = (List<RequestPostObject>) dbAccess
				.find(query, RequestPostObject.class);
		return Response.status(200).entity(dbRecords.toString()).build();
	}

	/**
	 * Update a provide post
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/providePostUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProvidePost(InputStream incomingData)
			throws Exception {
		return upsertProvidePostX(incomingData);
	}

	/**
	 * Insert a provide post
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/providePostInsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertProvidePost(InputStream incomingData)
			throws Exception {
		return upsertProvidePostX(incomingData);
	}

	/**
	 * Upsert a provide post 1. If the post id is null and the createdDate is
	 * null, we treat it as Create 2. Otherwisse update
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/providePostUpsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response upsertProvidePost(InputStream incomingData)
			throws Exception {
		return upsertProvidePostX(incomingData);
	}

	/**
	 * Internal method to upsert a ProvidePostObject 1. If the post id is null
	 * and the createdDate is null, we treat it as Create 2. Otherwisse update
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	protected Response upsertProvidePostX(InputStream incomingData)
			throws Exception {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		// Get the user Object from the input string
		// Get all the detail information for the object
		Gson gson = jsonHelper.getStandardGsonForPlatformObject();
		ProvidePostObject postObject = gson.fromJson(inputStr.toString(),
				ProvidePostObject.class);

		DmlOperationWrapper dmlOperationState = postObject.save();
		if (!dmlOperationState.isBulkSuccess()) {
			dmlOperationState.toString();
			return Response.status(400)
					.entity(dmlOperationState.getAllErrorsOnEntity(postObject))
					.build();
		}
		return Response.status(200).entity(postObject.toString()).build();
	}

	/**
	 * Delete a ProvidePostObject based on the json object information. But we
	 * only care about the id of the post.
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 */
	@POST
	@Path("/providePostDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProvidePost(InputStream incomingData) {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		JSONObject jsonObj = new JSONObject(inputStr.toString());
		String postId = null;
		try {
			postId = jsonObj.getString("id");
		} catch (Exception e) {
			return Response.status(400).entity("The post id is required")
					.build();
		}
		ProvidePostObject postInfo = new ProvidePostObject(postId);
		boolean isRemoved = postInfo.remove();
		if (isRemoved) {
			return Response.status(200).entity("Removing post successfully")
					.build();
		} else {
			return Response.status(400).entity("Removing post fails").build();
		}
	}
	
	/**
	 * Update a request post
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/requestPostUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRequestPost(InputStream incomingData)
			throws Exception {
		return upsertRequestPostX(incomingData);
	}

	/**
	 * Insert a request post
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/requestPostInsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertRequestPost(InputStream incomingData)
			throws Exception {
		return upsertRequestPostX(incomingData);
	}

	/**
	 * Upsert a request post 1. If the post id is null and the createdDate is
	 * null, we treat it as Create 2. Otherwisse update
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/requestPostUpsert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response upsertRequestPost(InputStream incomingData)
			throws Exception {
		return upsertRequestPostX(incomingData);
	}

	/**
	 * Internal method to upsert a RequestPostObject 1. If the post id is null
	 * and the createdDate is null, we treat it as Create 2. Otherwisse update
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 * @throws Exception
	 */
	protected Response upsertRequestPostX(InputStream incomingData)
			throws Exception {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		// Get the user Object from the input string
		// Get all the detail information for the object
		Gson gson = jsonHelper.getStandardGsonForPlatformObject();
		RequestPostObject postObject = gson.fromJson(inputStr.toString(),
				RequestPostObject.class);

		DmlOperationWrapper dmlOperationState = postObject.save();
		if (!dmlOperationState.isBulkSuccess()) {
			dmlOperationState.toString();
			return Response.status(400)
					.entity(dmlOperationState.getAllErrorsOnEntity(postObject))
					.build();
		}
		return Response.status(200).entity(postObject.toString()).build();
	}

	/**
	 * Delete a RequestPostObject based on the json object information. But we
	 * only care about the id of the post.
	 * 
	 * @param incomingData
	 *            the json representation for a post object
	 * @return
	 */
	@POST
	@Path("/requestPostDelete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRequestPost(InputStream incomingData) {
		JsonUtil jsonHelper = new JsonUtil();
		StringBuilder inputStr = jsonHelper
				.convertJsonStringToStringBuilder(incomingData);
		JSONObject jsonObj = new JSONObject(inputStr.toString());
		String postId = null;
		try {
			postId = jsonObj.getString("id");
		} catch (Exception e) {
			return Response.status(400).entity("The post id is required")
					.build();
		}
		RequestPostObject postInfo = new RequestPostObject(postId);
		boolean isRemoved = postInfo.remove();
		if (isRemoved) {
			return Response.status(200).entity("Removing post successfully")
					.build();
		} else {
			return Response.status(400).entity("Removing post fails").build();
		}
	}
}
