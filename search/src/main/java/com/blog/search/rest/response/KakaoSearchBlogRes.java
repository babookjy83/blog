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
public class KakaoSearchBlogRes implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -999387053022302232L;

	private Meta meta;
	private List<Documents> documents = new LinkedList<>();
	
	@Setter
	@Getter
	public static class Meta implements Serializable {
		
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = -3098698792563935051L;
		
		private int total_count;
		private int pageable_count;
		private boolean is_end;
	}
	
	@Setter
	@Getter
	public static class Documents implements Serializable {
		
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 3448331648520414982L;
		
		private String blogname;
		private String title;
		private String contents;
		private String url;
		private String thumbnail;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
		private LocalDate datetime;
		
//        "blogname": "그래도 해야지",
//        "contents": "@ExtendWith SpringRunner -&gt; SpringExtension @After/@Before -&gt; @AfterEach/@BeforeEach package com.talk.about.web; import org.junit.jupiter.api.<b>Test</b>; import org.junit.jupiter.api.extension.ExtendWith; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot...",
//        "datetime": "2022-09-02T01:38:25.000+09:00",
//        "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/BzW4WayEJiP",
//        "title": "[SpringBoot] <b>test</b> 코드 작성하기, Lombok",
//        "url": "http://jaajaa.tistory.com/253"
	}
}
