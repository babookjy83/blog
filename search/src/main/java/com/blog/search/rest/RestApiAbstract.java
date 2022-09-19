package com.blog.search.rest;

import java.net.URI;

import org.springframework.http.HttpHeaders;

import com.blog.search.rest.request.SearchBlogReq;

public abstract class RestApiAbstract {

	protected abstract HttpHeaders getHeader();
	protected abstract URI getURI(SearchBlogReq req) throws Exception;
	
}
