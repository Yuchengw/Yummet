package com.yummet.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.yummet.api.config.ApiProtocol;
import com.yummet.api.config.ApiVersion;
import com.yummet.api.util.UserApiUtil;
import com.yummet.shared.util.HttpUtils;
import com.yummet.system.context.YummetContext;

/**
 * @author yucheng
 * @since 1
 * */
public class AppRestApiUtil implements RestApiUtil{

	public static final String PLATFORM_ROOT_URL= Rest.getPlatformBaseURL(true) + "/api";
	private boolean setAuthHeader;
	
	@Override
	public void setSetAuthHeader(boolean setAuthHeader) {
		this.setAuthHeader = setAuthHeader;
	}
	
	public boolean isSetAuthHeader() {
		return setAuthHeader;
	}
	
	@Override
	public int executeRequest(HttpMethod method, ApiProtocol protocol) throws Exception {
		return executeRequest(getHttpClient(), method, protocol);		
	}

	@Override
	public int executeRequest(String url, HttpMethod method, ApiProtocol protocol) throws Exception {
		method.setURI(new URI(url, true));
		return executeRequest(getHttpClient(), method, protocol);
	}
	
	public int executeRequest(HttpClient client, HttpMethod method, ApiProtocol protocol) throws Exception {
		setupHttpMethod(method, protocol);
		int sc = executeMethod(client, method);
		return sc;
	}
	
	private void setupHttpMethod(HttpMethod method, ApiProtocol protocol) throws Exception {
		if (isSetAuthHeader()) {
			setAuthHeader(method);
		}
		if (protocol.acceptJSON()) {
			if (method.getRequestHeader(HttpUtils.HEADER_CONTENT_TYPE) == null) {
				method.setRequestHeader(HttpUtils.HEADER_CONTENT_TYPE, Formats.Json.getMediaType());
			}
			if (method.getRequestHeader("Accept") == null) {
				method.setRequestHeader("Accept", Formats.Json.getMediaType());
			}
		} else {
			throw new IllegalArgumentException("Can not use rest api with " + protocol);
		}
		// add more attributes here
	}
	
	public void setAuthHeader(HttpMethod method) throws Exception {
		setAuthHeader(method, UserApiUtil.getXSRFTokenForUser(YummetContext.getUserContext().getUser()));
	}
	
	@Override
	public <T> T deserializeFromJson(HttpMethod method, Class<T> expectedType) throws JsonParseException, JsonMappingException, IOException {
		return deserializeFromJson(method.getResponseBodyAsStream(), expectedType);
	}
	
	private <T> T deserializeFromJson(InputStream stream, Class<T> expectedType) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(stream, expectedType);
	}
	
	@SuppressWarnings("unused")
	private <T> T deserializeFromJson(String string, Class<T> expectedType) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(string, expectedType);
	}
	
	@SuppressWarnings("unused")
	private String serializeToJson(Map<String, Object> values) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(values);
	}

	public static String getVersionsResourceUrl() {
		return PLATFORM_ROOT_URL;
	}
	
	// obtain Apache HttpClient for APPServer
	public static HttpClient getHttpClient() {
		HttpClient appClient = new HttpClient();
		appClient.getParams().setSoTimeout(120000);
		return appClient;
	}
	
	private static void setAuthHeader(HttpMethod method, String xsrfToken) {
		setAuthHeader(method, "X-CSRF-TOKEN", xsrfToken);
	}
	
	private static void setAuthHeader(HttpMethod method, String headerAttribute, String headerValue) {
		if (headerAttribute == null || headerValue == null) {
			method.removeRequestHeader(headerAttribute);
		} else {
			method.setRequestHeader(headerAttribute, headerValue);
		}
	}

	private int executeMethod(HttpClient client, HttpMethod method) throws HttpException, IOException {
		return client.executeMethod(method);
	}

}
