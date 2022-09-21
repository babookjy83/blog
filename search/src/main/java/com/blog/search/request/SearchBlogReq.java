package com.blog.search.request;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBlogReq implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1763989311630841225L;
	
	@NotEmpty(message = "검색어를 입력해 주세요.")
	private String query;
	private String sort;
	@Min(value = 1, message = "1페이지 이상 조회 가능합니다.")
	private int page;
	@Min(value = 1, message = "최소 1개부터 조회 가능합니다.")
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
