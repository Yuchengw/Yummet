package com.yummet.api.rest;

/**
 * @author yucheng
 * @since 1
 * */
import java.io.IOException;

import org.apache.commons.httpclient.HttpMethod;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.yummet.api.config.ApiProtocol;
import com.yummet.api.config.ApiVersion;

public interface RestApiUtil {

	void setSetAuthHeader(boolean b);

	public <T> T deserializeFromJson(HttpMethod method, Class<T> expectedType) throws JsonParseException, JsonMappingException, IOException;

	int executeRequest(String url, HttpMethod method, ApiProtocol protocol) throws Exception;

	int executeRequest(HttpMethod method, ApiProtocol restjson) throws Exception;
}
