package com.blog.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EntityScan(basePackages = {"com.blog.core.jpa.entity"})
@EnableJpaRepositories(basePackages = {"com.blog.core.jpa.repository"})
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args){
    	SpringApplication application = new SpringApplication(Application.class);
    	application.run(args);
    }
}