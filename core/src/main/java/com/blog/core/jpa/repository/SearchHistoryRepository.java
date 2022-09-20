package com.blog.core.jpa.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.blog.core.jpa.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
	// count를 누적 update하면 저장할때 Lock때문에 성능이 떨어질거 같고,, Row 데이터로 저장하면 keyword로 group counting을 해야해서 조회 시 성능이 떨어질거 같음... 
	@Query(value =
		"SELECT ID, KEYWORD, SEARCH_COUNT FROM TB_SEARCH_HISTORY ORDER BY SEARCH_COUNT DESC LIMIT 10;"
        , nativeQuery = true
    )
    List<SearchHistory> findTop10Keywords();
	
	// 데이터 정합성을 강조하셨으니 비관적Lock의 Write로..
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	SearchHistory findByKeyword(String keyword);
}
