package com.blog.search.rest.response;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6863105237022405662L;
	
	private String name;
	private String title;
	private String contents;
	private String link;
	private String bloggerLink;
	private String thumbnail;
	private LocalDate date;
	
	public <T> SearchResponse(T response) {
		BeanUtils.copyProperties(response, this);
	}
}
