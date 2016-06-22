package com.nearsoft.questions.config;

import com.nearsoft.questions.bot.slack.SlackBot;
import com.nearsoft.questions.service.ElasticSearchReindexerService;
import com.nearsoft.questions.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@SpringBootApplication
@ComponentScan({"com.nearsoft.questions"})
@EnableJpaRepositories({"com.nearsoft.questions.repository"})
@EnableElasticsearchRepositories({"com.nearsoft.questions.repository.search"})
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@EntityScan("com.nearsoft.questions.domain")
@EnableTransactionManagement
@EnableSpringDataWebSupport
@EnableAsync
public class QuestionsApplication extends SpringBootServletInitializer {

    @Inject
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }

    @PostConstruct
    public void initApplication() throws Exception {
        ElasticSearchReindexerService elasticsearchReindexerService = applicationContext.getBean(ElasticSearchReindexerService.class);
        elasticsearchReindexerService.reindex();

        //TODO move new service
        Environment environment = applicationContext.getEnvironment();
        String slackApiToken = environment.getProperty("bot.slack.api_token", (String) null);
        if (StringUtils.isNotBlank(slackApiToken)) {
            new SlackBot(slackApiToken, environment.getRequiredProperty("application.site"),
                    applicationContext.getBean(QuestionService.class));
        }
    }
}
