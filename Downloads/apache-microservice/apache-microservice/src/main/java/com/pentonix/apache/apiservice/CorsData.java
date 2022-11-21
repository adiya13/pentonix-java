package com.pentonix.apache.apiservice;

import java.util.List;

public class CorsData {
	List<String> allowedOrigins;
    List<String> allowedMethods;
    List<String> allowedHeaders;
    boolean allowCredentials;
    
	public CorsData() {
		
	}
	public CorsData(List<String> allowedOrigins, List<String> allowedMethods, List<String> allowedHeaders,
			boolean allowCredentials) {
		this.allowedOrigins = allowedOrigins;
		this.allowedMethods = allowedMethods;
		this.allowedHeaders = allowedHeaders;
		this.allowCredentials = allowCredentials;
	}
	public List<String> getAllowedOrigins() {
		return allowedOrigins;
	}
	public void setAllowedOrigins(List<String> allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}
	public List<String> getAllowedMethods() {
		return allowedMethods;
	}
	public void setAllowedMethods(List<String> allowedMethods) {
		this.allowedMethods = allowedMethods;
	}
	public List<String> getAllowedHeaders() {
		return allowedHeaders;
	}
	public void setAllowedHeaders(List<String> allowedHeaders) {
		this.allowedHeaders = allowedHeaders;
	}
	public boolean isAllowCredentials() {
		return allowCredentials;
	}
	public void setAllowCredentials(boolean allowCredentials) {
		this.allowCredentials = allowCredentials;
	}
	
}
