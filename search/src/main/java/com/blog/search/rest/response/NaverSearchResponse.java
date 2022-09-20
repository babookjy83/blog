package com.blog.search.rest.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class NaverSearchResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5351618174635244721L;
	
	@JsonProperty("total")
	private long totalCount;
	
	private List<Item> items = new LinkedList<>();
	
	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Item {
		@JsonProperty("bloggername")
		private String name;
		private String title;
		@JsonProperty("description")
		private String contents;
		private String link;
		@JsonProperty("bloggerlink")
		private String bloggerLink;
		@JsonProperty("postdate")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
		private LocalDate date;
	}

	public List<SearchResponse> getList() {
		return (List<SearchResponse>) items.stream().map(SearchResponse::new).collect(Collectors.toList());
	}
}
