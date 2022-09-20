package search;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.blog.search.controller.SearchController;
import com.blog.search.request.SearchBlogReq;
import com.blog.search.resources.SearchKeywordHistoryResource;
import com.blog.search.rest.response.SearchResponse;
import com.blog.search.service.impl.SearchServiceImpl;
import com.blog.search.utils.PageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("SearchController 테스트")
@AutoConfigureMockMvc
@SpringBootTest(classes = SearchController.class)
public class SearchControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
    private SearchServiceImpl searchService;
	
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		this.objectMapper = new ObjectMapper();
	}
	
	@Test
    public void blogsTest() throws Exception {
        //given
		final SearchBlogReq request = new SearchBlogReq("test", null, 1, 10);
		
    	final List<SearchResponse> list = new ArrayList<>();
        final SearchResponse response = new SearchResponse("카카오 블로그 이름"
				, "카카오 블로그 글 제목"
				, "카카오 블로그 글 요약"
				, "카카오 블로그 주소"
				, ""
				, "카카오 블로그 미리보기 이미지 URL"
				, LocalDate.now().minusDays(30));
        
        list.add(response);
        final PageUtils<SearchResponse> page = new PageUtils<>(list, 1, request);
        
        given(searchService.getSearchBlog(request)).willReturn(page);

        // when
        final ResultActions actions = mockMvc.perform(
	        		get("/search/blogs")
	        		.content(this.objectMapper.writeValueAsString(request))
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.accept("application/json;charset=UTF-8")
        		)
                .andDo(print());

        //then
        actions.andExpect(status().isOk())
        	.andExpect(jsonPath("$.list").exists());

    }

    @Test
    public void keywordHistoryTest() throws Exception {
        //given
    	final List<SearchKeywordHistoryResource> list = new ArrayList<>();
        final SearchKeywordHistoryResource history =
        		SearchKeywordHistoryResource.builder().keyword("test").searchCount(10).build();
        list.add(history);

        given(searchService.getKeywordHistories()).willReturn(list);

        //when
        final ResultActions actions = mockMvc.perform(
        		get("/search/history")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()
    		);

        //then
        actions.andExpect(status().isOk())
        		.andExpect(jsonPath("$.[0].keyword").value("test"))
                .andExpect(jsonPath("$.[0].search_count").value(10));

    }
}
