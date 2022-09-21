package search;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.blog.core.jpa.entity.SearchHistory;
import com.blog.core.jpa.repository.SearchHistoryRepository;
import com.blog.search.Application;
import com.blog.search.request.SearchBlogReq.KakaoSort;

@DisplayName("Controller MVC 테스트")
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class MvcTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	
	@Test
    public void testBlogs() throws Exception {
        // when
        final ResultActions actions = mockMvc.perform(get("/search/blogs")
	        		.param("query", "test")
	                .param("sort", KakaoSort.ACCURACY.name().toLowerCase())
	                .param("page", "1")
	                .param("size", "10")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.accept(MediaType.APPLICATION_JSON)
        		)
                .andDo(print());

        //then
        actions.andExpect(status().isOk())
        	.andExpect(jsonPath("$.list").isArray())
        	.andExpect(jsonPath("$.pageNo").value(1))
        	.andExpect(jsonPath("$.size").value(10))
        	.andExpect(jsonPath("$.totalPage").exists())
        	.andExpect(jsonPath("$.totalCount").exists());

    }

    @Test
    public void testKeywordHistory() throws Exception {
        //given
    	searchHistoryRepository.save(new SearchHistory("유닛테스트", 10));

        //when
        final ResultActions actions = mockMvc.perform(get("/search/history")
                	.contentType(MediaType.APPLICATION_JSON)
                ).andDo(print());

        //then
        actions.andExpect(status().isOk())
        		.andExpect(jsonPath("$.[0].keyword").value("유닛테스트"))
                .andExpect(jsonPath("$.[0].search_count").value(10));

    }
}
