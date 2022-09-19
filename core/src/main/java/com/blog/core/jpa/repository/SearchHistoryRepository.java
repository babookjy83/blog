package com.blog.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.blog.core.jpa.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
	@Transactional
	@Query(value =
		"SELECT ID, KEYWORD, SEARCH_COUNT FROM TB_SEARCH_HISTORY ORDER BY SEARCH_COUNT DESC LIMIT 10 FOR UPDATE;"
        , nativeQuery = true
    )
    List<SearchHistory> findTop10Keywords();
	
	SearchHistory findByKeyword(String keyword);
}
