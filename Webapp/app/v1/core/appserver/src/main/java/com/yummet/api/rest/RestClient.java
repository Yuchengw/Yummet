package com.yummet.api.rest;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.codehaus.jackson.map.ObjectMapper;

import com.yummet.api.config.ApiConstants;
import com.yummet.api.config.ApiProtocol;
import com.yummet.api.config.PlatformApiVersion;
import com.yummet.lib.platformService.PlatformResource;

/**
 * @author yucheng
 * @since 1
 * */
public abstract class RestClient<INPUT extends Object, OUTPUT extends Object> implements RestClientIntf, RestConstants, ApiConstants{
	
	private final Class<OUTPUT> expectedType;
	private Map<String, String> requestHeaders;
	private String oauth = null;
	
	public RestClient(Class<OUTPUT> expectedType) {
		if (expectedType == null) {
			//TODO: 
		}
		this.expectedType = expectedType;
	}
	
	public RestClient<INPUT, OUTPUT> addHeaders(Map<String, String> headers) {
		this.requestHeaders = headers;
		return this;
	}
	
	public RestClient<INPUT, OUTPUT> addOauth(String oauth) {
		this.oauth = oauth;
		return this;
	}
	
	public OUTPUT doGet(String uri) {
		GetMethod method = new GetMethod(getUrl(uri));
		method.setFollowRedirects(false);
		return execute(method);
	}
	
	public OUTPUT doPost(String uri, INPUT body) {
		PostMethod method = new PostMethod(getUrl(uri));
		method.setFollowRedirects(false);
		method.setRequestEntity(new ByteArrayRequestEntity(serialize(body)));
		return execute(method);
	}
	
	public OUTPUT doPut(String uri, INPUT body) {
		PutMethod method = new PutMethod(getUrl(uri));
		method.setFollowRedirects(false);
		method.setRequestEntity(new ByteArrayRequestEntity(serialize(body)));
		return execute(method);
	}
	
	private OUTPUT execute(HttpMethod method) {
		try {
			method = applyHeaders(method);
			RestApiUtil restUtil = new AppRestApiUtil(); // we are mainly used for app-platform communication
			if (oauth != null) {
				method = applyAuthorization(method);
				restUtil.setSetAuthHeader(false);
			}
			int status = restUtil.executeRequest(method, ApiProtocol.RestJson);
			if (status == HttpStatusCode.OK.value) {
				OUTPUT rv = restUtil.deserializeFromJson(method, this.expectedType);
				return rv;
			} else {
				List<Map<String, ?>> errors = restUtil.deserializeFromJson(method, List.class);
				Map<String, ?> errorInfo = errors.get(0);
				if (errorInfo.get("errorCode").toString().startsWith("NO_ACCESS")) {
					throw new Exception("Method: "  + method + ": return NO_ACCESS");
				} else if (errorInfo.get("errorCode").toString().startsWith("ADMIN_AUTH_REQUIRED")) {
					throw new Exception("Method: "  + method + ": ADMIN_AUTH_REQUIRED");
				} else if (status == HttpStatusCode.UNAUTHORIZED.value ||
						   status == HttpStatusCode.FORBIDDEN.value) {
					throw new Exception("Method: "  + method + ": FORBIDDEN_ACCESS");
				} else {
					throw new Exception("Method: " + method + ": UNRECOGNIZE_METHOD");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		}
	}
	
	private static String getUrl(String uri) {
		return String.format("%s/%s/%s/%s",
				AppRestApiUtil.getVersionsResourceUrl(),
				PlatformResource.PATH, 
				PlatformApiVersion.CURRENT.getLabel(),
				uri);
	}
	
	private HttpMethod applyHeaders(HttpMethod method) {
		HttpMethod rv = method;
		if (requestHeaders != null) {
			for (Entry<String, String> entry : requestHeaders.entrySet()) {
				rv.addRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		return rv;
	}
	
	private HttpMethod applyAuthorization(HttpMethod method) {
		HttpMethod rv = method;
		if (oauth != null) {
			rv.addRequestHeader("Authorization", "OAuth " + oauth);
		}
		return rv;
	}
	
	private byte[] serialize(Object object) {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsBytes(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			//TODO: performance improvmenet
		}
	} 
}
