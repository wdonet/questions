package com.nearsoft.questions.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.nearsoft.questions.service", "com.nearsoft.questions.controller"})
public class QuestionsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(QuestionsApplication.class);
    }
}
