package com.nearsoft.questions.search;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The responsibility of this class is to create an initial Lucene index for the data already present in the database.
 * Its method onApplicationEvent(ContextRefreshedEvent event) is call during Spring's startup.
 */
@Component
public class SearchIndexBuilder implements ApplicationListener<ContextRefreshedEvent> {
    @PersistenceContext
    private EntityManager _entityManager;
    private final Logger _logger = LoggerFactory.getLogger(SearchIndexBuilder.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        _logger.debug("Starting the process that creates Lucene indexes for the data already in the DB.");
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(_entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
            _logger.debug("Lucene indexes already created.");
        } catch (InterruptedException e) {
            _logger.error("There was an exception during the creation of Lucene indexe for existing data." +
                    " They were not created.");
        }
    }
}