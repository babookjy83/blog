package com.blog.search.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.blog.core.jpa.entity.SearchHistory;
import com.blog.core.jpa.repository.SearchHistoryRepository;
import com.blog.search.exception.ApplicationException;
import com.blog.search.resources.SearchKeywordHistoryResource;
import com.blog.search.rest.KakaoApi;
import com.blog.search.rest.NaverApi;
import com.blog.search.rest.request.SearchBlogReq;
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
	ApplicationEventPublisher publisher;
	
	@Override
	public <T> PageUtils<T> getSearchBlog(SearchBlogReq req) {
		publisher.publishEvent(req);
		
		try {
			return kakao.getSearchBlog(req);
		} catch (ApplicationException ae) {
			return naver.getSearchBlog(req);
		}
		
	}
	
	@Override
	public <T> PageUtils<T> getKakaoBlogs(SearchBlogReq req) {
		publisher.publishEvent(req);
		return kakao.getSearchBlog(req);
	}

	@Override
	public <T> PageUtils<T> getNaverBlogs(SearchBlogReq req) {
		publisher.publishEvent(req);
		return naver.getSearchBlog(req);
	}
	
	@Override
	public List<SearchKeywordHistoryResource> getHistories() {
		final List<SearchHistory> list = searchHistoryRepository.findTop10Keywords();
		return list.stream()
				.map(history ->
					SearchKeywordHistoryResource.builder().keyword(history.getKeyword()).searchCount(history.getSearchCount()).build()
				)
				.sorted(Comparator.comparing(SearchKeywordHistoryResource::getSearchCount).reversed())
				.collect(Collectors.toList());
	}
}
