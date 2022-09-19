package com.blog.search.rest.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KakaoSearchResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -999387053022302232L;

	private Meta meta = new Meta();
	private List<Documents> documents = new LinkedList<>();
	
	@Setter
	@Getter
	public static class Meta {
		@JsonProperty("total_count")
		private long totalCount;
	}
	
	@Setter
	@Getter
	public static class Documents {
		@JsonProperty("blogname")
		private String name;
		private String title;
		private String contents;
		@JsonProperty("url")
		private String link;
		private String thumbnail;
		@JsonProperty("datetime")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
		private LocalDate date;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList() {
		return (List<T>) documents.stream().map(SearchResponse::new).collect(Collectors.toList());
	}
}
