package com.blog.search.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.search.exception.ApplicationException;
import com.blog.search.request.SearchBlogReq;
import com.blog.search.rest.response.NaverSearchResponse;
import com.blog.search.utils.PageUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConfigurationProperties(value = "prop.rest.naver")
public class NaverApi extends RestApiAbstract {

	@Setter
	private String host;
	@Setter
	private String clientId;
	@Setter
	private String clientSecret;
	
	@Autowired
	private RestTemplate naverRestTemplate;
	
	private final static String SEARCH_LIST_URL = "/v1/search/blog.json";
	private final static String SEARCH_ERROR_MESSAGE = "네이버 블로그 검색중에 에러가 발생하였습니다.";
	
	public <T> PageUtils<T> getSearchBlog(SearchBlogReq req) {
		req.setSort(SearchBlogReq.NaverSort.getSort(req.getSort()));
		
		final HttpEntity<String> entity = new HttpEntity<String>(this.getHeader());
		
        try {
        	final ResponseEntity<NaverSearchResponse> response =
        			naverRestTemplate.exchange(this.getURI(req), HttpMethod.GET, entity, NaverSearchResponse.class);
        	
        	final NaverSearchResponse body = response.getBody();
        	return new PageUtils<>(body.getList(), body.getTotalCount(), req);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        	throw new ApplicationException("4001", SEARCH_ERROR_MESSAGE);
        }
	}
	
	protected HttpHeaders getHeader() {
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Naver-Client-Id", this.clientId);
		headers.set("X-Naver-Client-Secret", this.clientSecret);
		
		return headers;
	}
	
	protected URI getURI(SearchBlogReq req) throws Exception {
		final Map<String, String> requestMap = BeanUtils.describe(req);
		
		return UriComponentsBuilder.newInstance()
				.scheme("https")
				.host(this.host)
				.path(SEARCH_LIST_URL)
				.queryParam("query", "test")
				.queryParam("sort", "{sort}")
				.queryParam("start", "{page}")
				.queryParam("display", "{size}")
				.buildAndExpand(requestMap)
				.toUri();
	}
}
