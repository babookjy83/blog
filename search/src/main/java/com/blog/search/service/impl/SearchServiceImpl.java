package com.blog.search.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.blog.core.jpa.entity.SearchHistory;
import com.blog.core.jpa.repository.SearchHistoryRepository;
import com.blog.search.exception.ApplicationException;
import com.blog.search.request.SearchBlogReq;
import com.blog.search.resources.SearchBlogResource;
import com.blog.search.resources.SearchKeywordHistoryResource;
import com.blog.search.rest.KakaoApi;
import com.blog.search.rest.NaverApi;
import com.blog.search.service.SearchService;
import com.blog.search.utils.PageUtils;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private KakaoApi kakao;
	@Autowired
	private NaverApi naver;
	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Override
	public PageUtils<SearchBlogResource> getSearchBlog(SearchBlogReq req) {
		publisher.publishEvent(req);
		
		try {
			return kakao.getSearchBlog(req);
		} catch (ApplicationException ae) {
			return naver.getSearchBlog(req);
		}
		
	}
	
	@Override
	public PageUtils<SearchBlogResource> getKakaoBlogs(SearchBlogReq req) {
		publisher.publishEvent(req);
		return kakao.getSearchBlog(req);
	}

	@Override
	public PageUtils<SearchBlogResource> getNaverBlogs(SearchBlogReq req) {
		publisher.publishEvent(req);
		return naver.getSearchBlog(req);
	}
	
	@Override
	public List<SearchKeywordHistoryResource> getKeywordHistories() {
		final List<SearchHistory> list = searchHistoryRepository.findTop10Keywords();
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		
		return list.stream()
				.map(history ->
					SearchKeywordHistoryResource.builder().keyword(history.getKeyword()).searchCount(history.getSearchCount()).build()
				)
				.sorted(Comparator.comparing(SearchKeywordHistoryResource::getSearchCount).reversed())
				.collect(Collectors.toList());
	}
}
