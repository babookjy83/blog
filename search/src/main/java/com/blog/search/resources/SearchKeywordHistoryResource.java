package com.blog.search.resources;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchKeywordHistoryResource implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6201867301491575241L;

	private String keyword;
	private int searchCount;
	
}
