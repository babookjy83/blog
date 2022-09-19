package com.blog.search.utils;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PageUtils<T> implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3713551543659710409L;
	
	private List<T> list;
	private int pageNo;
	private long pageSize;
	private int totalPage;
	private int totalCount;
}
