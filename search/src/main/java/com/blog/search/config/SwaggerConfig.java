package com.blog.search.config;

import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebApplicationObjectSupport {
	@Autowired
	private TypeResolver typeResolver;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(selector())
    		.build()
	        .pathProvider(
	    		new RelativePathProvider(getServletContext()) {
		        	@Override
		        	protected String applicationPath() {
		        		return RelativePathProvider.ROOT;
		        	}
	    		})
	        .produces(new LinkedHashSet<>(Arrays.asList(MediaType.APPLICATION_JSON_VALUE)))
	        .consumes(new LinkedHashSet<>(Arrays.asList(MediaType.APPLICATION_JSON_VALUE)))
	        .directModelSubstitute(LocalDate.class, String.class)
	        .genericModelSubstitutes(HttpEntity.class)
	        .alternateTypeRules(
	        		AlternateTypeRules.newRule(
			            typeResolver.resolve(DeferredResult.class,
			                typeResolver.resolve(HttpEntity.class, WildcardType.class)),
			            typeResolver.resolve(WildcardType.class)
		            )
    		);
	}

	private Predicate<RequestHandler> selector() {
		return Predicates
				.or(Arrays.asList(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class),
		            RequestHandlerSelectors.withMethodAnnotation(PostMapping.class),
		            RequestHandlerSelectors.withMethodAnnotation(PutMapping.class),
		            RequestHandlerSelectors.withMethodAnnotation(DeleteMapping.class))
				);
	}
}