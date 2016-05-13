package com.nearsoft.questions.search.service.impl;

import com.nearsoft.questions.search.service.SearchService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class HibernateSearchServiceImpl implements SearchService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public <E> List search(Class<E> e, String query, String[] fields) {
        FullTextEntityManager _fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        org.hibernate.search.query.dsl.QueryBuilder qb = _fullTextEntityManager.getSearchFactory().buildQueryBuilder().
                forEntity(e).get();
        org.apache.lucene.search.Query luceneQuery = qb
                .keyword()
                .onFields(fields)
                .matching(query)
                .createQuery();
        javax.persistence.Query jpaQuery =
                _fullTextEntityManager.createFullTextQuery(luceneQuery, e);
        @SuppressWarnings("unchecked")
        List<E> result = jpaQuery.getResultList();
        return result;
    }
}
