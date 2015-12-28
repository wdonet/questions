package com.nearsoft.questions.search.service;

import java.util.List;

public interface HibernateSearchService {
    <E> List search(Class<E> e, String query, String[] fields);
}
