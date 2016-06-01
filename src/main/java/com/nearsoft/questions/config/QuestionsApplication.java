package com.nearsoft.questions.config;

import com.nearsoft.questions.service.ElasticSearchReindexerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

@SpringBootApplication
@ComponentScan({"com.nearsoft.questions"})
@EnableJpaRepositories({"com.nearsoft.questions.repository"})
@EnableElasticsearchRepositories({"com.nearsoft.questions.repository.search"})
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@EntityScan("com.nearsoft.questions.domain")
@EnableTransactionManagement
@EnableSpringDataWebSupport
public class QuestionsApplication extends SpringBootServletInitializer {

    @Inject
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }

    @PostConstruct
    public void initApplication() throws IOException {
        ElasticSearchReindexerService elasticsearchReindexerService = applicationContext.getBean(ElasticSearchReindexerService.class);
        elasticsearchReindexerService.reindex();
    }
}
