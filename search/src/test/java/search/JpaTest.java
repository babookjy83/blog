package search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import com.blog.core.jpa.entity.SearchHistory;
import com.blog.core.jpa.repository.SearchHistoryRepository;
import com.blog.search.Application;
import com.blog.search.request.SearchBlogReq;
import com.blog.search.request.SearchBlogReq.KakaoSort;

@SpringBootTest(classes = Application.class)
public class JpaTest {
	
	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	
	private ExecutorService executor;
	
	@BeforeEach
	void setUp() {
		this.executor = Executors.newFixedThreadPool(10);
	}
	
	@Test
	void testSaveHistory() throws InterruptedException {
		// given
	    final int numberOfThreads = 50;
	    final CountDownLatch latch = new CountDownLatch(numberOfThreads);
	    
	    final int duplicationCount = 10;
	    
	    // when
	    for (int i = 0; i < numberOfThreads; i ++) {
	    	int prefix = i / duplicationCount + 1;
	    	SearchBlogReq req = new SearchBlogReq("테스트" + prefix, KakaoSort.ACCURACY.name().toLowerCase(), 1, 1);
	    	
	    	executor.execute(() -> {
	        	publisher.publishEvent(req);
	            latch.countDown();
	        });
	    }
	    latch.await();
	    
	    // Lock 풀리는데 시간이 0.01초 정도 걸림... 0.1초 sleep
	    Thread.sleep(100l);
	    
	    //then
	    final Map<String, SearchHistory> historyMap = searchHistoryRepository.findAll()
	    		.stream()
	    		.collect(Collectors.toMap(SearchHistory::getKeyword, Function.identity()));
	    		
	    final AtomicInteger atomic = new AtomicInteger(0);
	    historyMap.entrySet()
	    	.stream()
    		.sorted(Map.Entry.<String, SearchHistory>comparingByKey())
    		.collect(
    				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new)
			)
    		.forEach((key, history) -> {
    			final String keyword = "테스트" + String.valueOf(atomic.incrementAndGet());
    			
    			assertThat(key).isEqualTo(keyword);
    			assertThat(history.getSearchCount()).isEqualTo(duplicationCount);
    		});
	}
	
}
