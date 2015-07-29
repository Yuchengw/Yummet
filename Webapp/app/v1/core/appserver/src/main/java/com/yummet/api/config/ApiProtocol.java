package com.yummet.api.config;

public enum ApiProtocol {
	
	RestJson("java.util", "/api/", "application/json", ApiFamily.Rest);
	
	private final String rootClassName;
	private final String rootPath;
	private final String contentType;
	private final ApiFamily apiFamily;
	private final ObjectFamily objectFamily;
	
	private ApiProtocol(String rootClassName, String rootPath, String contentType, ApiFamily apiFamily) {
		this.rootClassName = rootClassName;
		this.rootPath = rootPath;
		this.contentType = contentType;
		this.apiFamily = apiFamily;
		this.objectFamily = apiFamily.isRestApi() ? ObjectFamily.JSON : null;
	}
	
	public String getRootClassName() {
		return rootClassName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public String getContentType() {
		return contentType;
	}

	public ApiFamily getApiFamily() {
		return apiFamily;
	}

	public ObjectFamily getObjectFamily() {
		return objectFamily;
	}

	public boolean acceptJSON() {
		return this == RestJson;
	}
}
