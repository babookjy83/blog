package com.blog.search.listener;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.blog.core.jpa.entity.SearchHistory;
import com.blog.core.jpa.repository.SearchHistoryRepository;
import com.blog.search.request.SearchBlogReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class BlogSearchListener {
	
	private Lock lock = new ReentrantLock();
	
	@Autowired
	private SearchHistoryRepository searchHistoryRepository;

	@Async
	@EventListener
	public void saveKeywordHistory(SearchBlogReq req) {
		// 많은 트래픽과 데이터 정합성을 위해 성능이 떨어져도 Lock을...
		try {
			lock.lock();
			
			SearchHistory history = searchHistoryRepository.findByKeyword(req.getQuery());
			if (history == null) {
				history = new SearchHistory(req.getQuery());
			}
			
			history.setSearchCount(history.getSearchCount() + 1);
			searchHistoryRepository.save(history);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			lock.unlock();
		}
	}
	
}
