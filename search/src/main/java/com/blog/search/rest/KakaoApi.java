package com.blog.search.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.blog.search.utils.PageUtils;
import com.blog.search.exception.ApplicationException;
import com.blog.search.resources.SearchBlogResource;
import com.blog.search.rest.request.SearchBlogReq;
import com.blog.search.rest.response.KakaoSearchBlogRes;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConfigurationProperties(value = "prop.rest.kakao")
public class KakaoApi extends RestApiAbstract {
	
	@Setter
	private String host;
	@Setter
	private String apiKey;
	@Autowired
	private RestTemplate kakaoRestTemplate;
	
	private final static String SEARCH_LIST_URL = "/v2/search/blog";
	private final static String SEARCH_ERROR_MESSAGE = "카카오 블로그 검색중에 에러가 발생하였습니다.";
	
	public <T> PageUtils<T> getSearchBlog(SearchBlogReq req) {
		final HttpEntity<String> entity = new HttpEntity<String>(this.getHeader());
		
        try {
        	final ResponseEntity<KakaoSearchBlogRes> response =
            		kakaoRestTemplate.exchange(this.getURI(req), HttpMethod.GET, entity, KakaoSearchBlogRes.class);
        	
        	final KakaoSearchBlogRes body = response.getBody();
        	return this.convertToResource(req, body);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
			throw new ApplicationException("4001", SEARCH_ERROR_MESSAGE);
        }
        
	}
	
	@SuppressWarnings("unchecked")
	private <T> PageUtils<T> convertToResource(SearchBlogReq req, KakaoSearchBlogRes body) {
		final List<T> list = body.getDocuments()
				.stream()
				.map(doc ->
						(T) SearchBlogResource.builder()
							.name(doc.getBlogname())
							.title(doc.getTitle())
							.contents(doc.getContents())
							.url(doc.getUrl())
							.thumbnail(doc.getThumbnail())
							.date(doc.getDatetime())
							.build()
				)
				.collect(Collectors.toList());
		PageUtils aa = new PageUtils();
		aa.setList(list);
		return aa;
	}
	
	protected HttpHeaders getHeader() {
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", this.apiKey);
		
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
				.queryParam("page", "{page}")
				.queryParam("size", "{size}")
				.buildAndExpand(requestMap)
				.toUri();
	}
}
