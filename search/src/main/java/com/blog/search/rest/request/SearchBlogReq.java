package com.blog.search.rest.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchBlogReq {
	@NotEmpty(message = "검색어를 입력해 주세요.")
	private String query;
	private String sort;
	private int page;
	private long size;
}
