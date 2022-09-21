package com.blog.search.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.core.jpa.entity.SearchHistory;
import com.blog.search.exception.ApplicationException;
import com.blog.search.request.SearchBlogReq;
import com.blog.search.resources.SearchBlogResource;
import com.blog.search.resources.SearchKeywordHistoryResource;
import com.blog.search.rest.response.SearchResponse;
import com.blog.search.service.SearchService;
import com.blog.search.utils.PageUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Setter
@Getter
@Api(tags = "블로그 목록조회 API")
@EnableWebMvc
@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;

	@ApiOperation("블로그 목록")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "query", value = "검색어", dataType = "string", required = false, paramType = "query"),
		@ApiImplicitParam(name = "sort", value = "정렬 키", allowableValues="accuracy,recency", defaultValue = "accuracy", allowMultiple = false, required = false, paramType = "query"),
		@ApiImplicitParam(name = "page", value = "Page No", dataType = "int", defaultValue = "1", required = false, paramType = "query"),
		@ApiImplicitParam(name = "size", value = "Per Page", dataType = "int", defaultValue = "10", required = false, paramType = "query")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = SearchBlogResource.class)
	})
	@GetMapping("/blogs")
	public HttpEntity<PageUtils<SearchResponse>> getBlogs(@ApiIgnore SearchBlogReq req) throws ApplicationException {
		final PageUtils<SearchResponse> result = searchService.getSearchBlog(req);
		return ResponseEntity.ok(result);
	}
	
	@ApiOperation("KAKAO 블로그 목록")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "query", value = "검색어", dataType = "string", required = false, paramType = "query"),
		@ApiImplicitParam(name = "sort", value = "정렬 키", allowableValues="accuracy,recency", defaultValue = "accuracy", allowMultiple = false, required = false, paramType = "query"),
		@ApiImplicitParam(name = "page", value = "Page No", dataType = "int", defaultValue = "1", required = false, paramType = "query"),
		@ApiImplicitParam(name = "size", value = "Per Page", dataType = "int", defaultValue = "10", required = false, paramType = "query")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = SearchBlogResource.class)
	})
	@GetMapping("/kakao/blogs")
	public HttpEntity<PageUtils<SearchResponse>> getKakaoBlogs(@ApiIgnore @Valid SearchBlogReq req) throws ApplicationException {
		final PageUtils<SearchResponse> result = searchService.getKakaoBlogs(req);
		return ResponseEntity.ok(result);
	}
	
	@ApiOperation("NAVER 블로그 목록")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "query", value = "검색어", dataType = "string", required = false, paramType = "query"),
		@ApiImplicitParam(name = "sort", value = "정렬 키", allowableValues="sim,date", defaultValue = "sim", allowMultiple = false, required = false, paramType = "query"),
		@ApiImplicitParam(name = "page", value = "Page No", dataType = "int", defaultValue = "1", required = false, paramType = "query"),
		@ApiImplicitParam(name = "size", value = "Per Page", dataType = "int", defaultValue = "10", required = false, paramType = "query")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = SearchBlogResource.class)
	})
	@GetMapping("/naver/blogs")
	public HttpEntity<PageUtils<SearchResponse>> getNaverBlogs(@ApiIgnore @Valid SearchBlogReq req) throws ApplicationException {
		final PageUtils<SearchResponse> result = searchService.getNaverBlogs(req);
		return ResponseEntity.ok(result);
	}
	
	@ApiOperation("인기 검색어 목록")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = SearchHistory.class)
	})
	@GetMapping("/history")
	public HttpEntity<List<SearchKeywordHistoryResource>> getKeywordHistoies() {
		final List<SearchKeywordHistoryResource> histories = searchService.getKeywordHistories();
		return ResponseEntity.ok(histories);
	}
}
