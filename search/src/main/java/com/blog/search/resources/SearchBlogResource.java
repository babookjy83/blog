package com.blog.search.resources;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBlogResource implements Serializable {
	
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
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	public <T> SearchBlogResource(T response) {
		BeanUtils.copyProperties(response, this);
	}
}
