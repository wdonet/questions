package com.nearsoft.questions.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan({"com.nearsoft.questions"})
@EnableJpaRepositories({"com.nearsoft.questions.repository"})
@EnableElasticsearchRepositories({"com.nearsoft.questions.repository.search"})
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@EntityScan("com.nearsoft.questions.domain")
@EnableTransactionManagement
@EnableSpringDataWebSupport
public class QuestionsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(QuestionsApplication.class);
    }

}
