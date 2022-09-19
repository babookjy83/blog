package com.blog.search.config;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate kakaoRestTemplate() {
		return new RestTemplate(this.getRequestFactory());
	}
	
	@Bean
	public RestTemplate naverRestTemplate() {
		return new RestTemplate(new BufferingClientHttpRequestFactory(this.getRequestFactory()));
	}

	private HttpComponentsClientHttpRequestFactory getRequestFactory() {
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(30000);
		factory.setReadTimeout(30000);
		factory.setHttpClient(this.getHttpClient());
		
		return factory;
	}
	
	private HttpClient getHttpClient() {
		return HttpClientBuilder.create()
				.setMaxConnTotal(100)
				.setMaxConnPerRoute(100)
				.build();
	}
	
	@Bean
	public Gson gson() {
		return new GsonBuilder().serializeNulls().create();
	}
}
