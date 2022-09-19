package com.blog.search.resources;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SearchBlogResource implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1138615046830189691L;

	private String name;
	private String title;
	private String contents;
	private String url;
	private String thumbnail;
	private LocalDate date;
}
