package search;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.blog.search.config.RestTemplateConfig;
import com.blog.search.request.SearchBlogReq;
import com.blog.search.request.SearchBlogReq.KakaoSort;
import com.blog.search.request.SearchBlogReq.NaverSort;
import com.blog.search.rest.KakaoApi;
import com.blog.search.rest.NaverApi;
import com.blog.search.rest.response.SearchResponse;
import com.blog.search.service.impl.SearchServiceImpl;
import com.blog.search.utils.PageUtils;

@SpringBootTest(classes = {SearchServiceImpl.class})
@EnableConfigurationProperties(value = {KakaoApi.class, NaverApi.class})
@ContextConfiguration(classes = {RestTemplateConfig.class})
@TestPropertySource("classpath:application.properties")
public class RestApiTest {

	@Autowired
	private KakaoApi kakaoApi;
	@Autowired
	private NaverApi naverApi;
	
	@Test
    @DisplayName("카카오 블로그 목록 조회 테스트")
    void kakaoApiTest() throws Exception {
		// given
		final SearchBlogReq req = new SearchBlogReq("테스트", KakaoSort.ACCURACY.name().toLowerCase(), 1, 1);
		
		// when
		final PageUtils<SearchResponse> response = kakaoApi.getSearchBlog(req);
		
		// then
		this.assertThatOf(response);
    }
	
	@Test
    @DisplayName("네이버 블로그 목록 조회 테스트")
    void kaverApiTest() throws Exception {
		// given
		final SearchBlogReq req = new SearchBlogReq("테스트", NaverSort.SIM.name().toLowerCase(), 1, 1);
		
		// when
		final PageUtils<SearchResponse> response = naverApi.getSearchBlog(req);
		
		// then
		this.assertThatOf(response);
		
    }
	
	private <T> void assertThatOf(PageUtils<T> response) {
		assertThat(response.getList()).isNotEmpty();
		assertThat(response.getSize()).isGreaterThan(0);
		assertThat(response.getList().get(0)).isInstanceOf(SearchResponse.class);
	}
}
