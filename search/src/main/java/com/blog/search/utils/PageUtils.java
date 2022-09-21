package com.blog.search.utils;

import java.io.Serializable;
import java.util.List;

import com.blog.search.request.SearchBlogReq;

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
	private long pageNo;
	private long size;
	private long totalPage;
	private long totalCount;
	
	public PageUtils (List<T> list, long totalCount, SearchBlogReq req) {
		this.list = list;
		this.size = req.getSize();
		this.totalCount = totalCount;
		this.totalPage = (totalCount / req.getSize()) < 1 ? 1 : (int) Math.ceil(totalCount / req.getSize());
		this.pageNo = this.totalPage < req.getPage() ? this.totalPage : req.getPage();
	}
	
}
