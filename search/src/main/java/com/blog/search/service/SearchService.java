package com.blog.search.service;

import java.util.List;

import com.blog.search.utils.PageUtils;
import com.blog.search.resources.SearchKeywordHistoryResource;
import com.blog.search.rest.request.SearchBlogReq;

public interface SearchService {
	<T> PageUtils<T> getSearchBlog(SearchBlogReq req);
	<T> PageUtils<T> getKakaoBlogs(SearchBlogReq req);
	<T> PageUtils<T> getNaverBlogs(SearchBlogReq req);
	List<SearchKeywordHistoryResource> getHistories();
}
