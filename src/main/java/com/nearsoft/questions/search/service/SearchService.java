package com.nearsoft.questions.search.service;

import java.util.List;

public interface SearchService {
    <E> List search(Class<E> e, String query, String[] fields);
}
