package com.nearsoft.questions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ElasticSearchReindexerService {
    private final Logger log = LoggerFactory.getLogger(ElasticSearchReindexerService.class);

    @Inject
    private ApplicationContext applicationContext;

    @Async
    public void reindex() {

        Set<String> elasticSearchRepositoryBeans = applicationContext.getBeansOfType(ElasticsearchRepository.class).keySet();

        elasticSearchRepositoryBeans.stream()
                .map(beanName -> beanName.split("SearchRepository"))
                .map(beanNameSplit -> {
                    String repositoryName = beanNameSplit[0] + "Repository";
                    String searchRepositoryName = beanNameSplit[0] + "SearchRepository";
                    try {
                        CrudRepository jpaRepository = applicationContext.getBean(repositoryName, CrudRepository.class);
                        ElasticsearchRepository elasticsearchRepository =
                                applicationContext.getBean(searchRepositoryName, ElasticsearchRepository.class);
                        return new AbstractMap.SimpleEntry<>(jpaRepository, elasticsearchRepository);
                    } catch (BeansException ex) {
                        log.info("Not indexing for: " + searchRepositoryName + " missing JpaRepository: " + repositoryName);
                    }
                    return null;
                }).filter(entry -> null != entry)
                .forEach(this::reindexEntity);
        log.info("Reindexing done");
    }

    private void reindexEntity(Map.Entry<CrudRepository, ElasticsearchRepository> entry) {
        CrudRepository jpaRepository = entry.getKey();
        ElasticsearchRepository elasticsearchRepository = entry.getValue();

        Iterable entities = jpaRepository.findAll();
        elasticsearchRepository.save(entities);
    }
}

