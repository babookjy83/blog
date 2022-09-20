package com.blog.search.service;

import java.util.List;

import com.blog.search.request.SearchBlogReq;
import com.blog.search.resources.SearchKeywordHistoryResource;
import com.blog.search.rest.response.SearchResponse;
import com.blog.search.utils.PageUtils;

public interface SearchService {
	PageUtils<SearchResponse> getSearchBlog(SearchBlogReq req);
	PageUtils<SearchResponse> getKakaoBlogs(SearchBlogReq req);
	PageUtils<SearchResponse> getNaverBlogs(SearchBlogReq req);
	List<SearchKeywordHistoryResource> getKeywordHistories();
}
