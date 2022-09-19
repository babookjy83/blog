package com.blog.search.rest.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NaverSearchBlogRes implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5351618174635244721L;
	
	private long total;
	private int start;
	private int display;
	private List<Item> items = new LinkedList<>();
	
	@Setter
	@Getter
	public static class Item implements Serializable {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = -3062991491338381233L;
		
		private String bloggername;
		private String title;
		private String description;
		private String link;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
		private LocalDate postdate;
		
//        "title": "첫째의 미국 공립 초등학교 입학 이야기, 준비 과정 (TB <b>test</b>... ",
//        "link": "https://blog.naver.com/skyfox1011?Redirect=Log&logNo=222854353409",
//        "description": "미국에 도착해서 준비한 서류 미국에 도착해서 해야 할 일은 TB <b>test</b>와 Health examination 기록 준비다.... &amp; &amp; TB <b>test</b>와 health examination 기록의 경우 소아과에서 받으면 되는데 소아과 의사 예약을 하려고 하니... ",
//        "bloggername": "시간을 담는 상자",
//        "bloggerlink": "https://blog.naver.com/skyfox1011",
//        "postdate": "20220822"
	}

}
