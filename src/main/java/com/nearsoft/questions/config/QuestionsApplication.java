package com.nearsoft.questions.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan({"com.nearsoft.questions.controller", "com.nearsoft.questions.service"})
@EnableJpaRepositories({"com.nearsoft.questions.repository"})
@EntityScan("com.nearsoft.questions.domain")
@EnableTransactionManagement
@Import(value = {WebSecurityConfig.class, SocialConfig.class, WebMvcConfig.class})
public class QuestionsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(QuestionsApplication.class);
    }

}
