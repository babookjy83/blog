package com.blog.search.request;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchBlogReq implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1763989311630841225L;
	
	@NotEmpty(message = "검색어를 입력해 주세요.")
	private String query;
	private String sort;
	private int page;
	@Max(value = 50, message = "최대 50개까지 조회 가능합니다.")
	private long size;
	
	public enum KakaoSort {
		ACCURACY, RECENCY;
	}
	
	public enum NaverSort {
		SIM, DATE;
		
		public static String getSort(String sort) {
			if (!StringUtils.hasText(sort) || sort.equals(KakaoSort.ACCURACY.name().toLowerCase()) || sort.equals(SIM.name().toLowerCase())) {
				return SIM.name().toLowerCase();
			} else {
				return DATE.name().toLowerCase();
			}
		}
	}
}
