package com.blog.search.service;

import java.util.List;

import com.blog.search.request.SearchBlogReq;
import com.blog.search.resources.SearchBlogResource;
import com.blog.search.resources.SearchKeywordHistoryResource;
import com.blog.search.utils.PageUtils;

public interface SearchService {
	PageUtils<SearchBlogResource> getSearchBlog(SearchBlogReq req);
	PageUtils<SearchBlogResource> getKakaoBlogs(SearchBlogReq req);
	PageUtils<SearchBlogResource> getNaverBlogs(SearchBlogReq req);
	List<SearchKeywordHistoryResource> getKeywordHistories();
}
