package com.nearsoft.questions.service;

import java.io.File;
import java.io.InputStream;

/**
 * Storage interface represents the contract an storage service must fulfill to save, delete, replace and retrieve files.
 * Possible implementations of this interface are LocalStorage, InMemoryStorage or
 * something else based on any cloud service
 */
public interface Storage {

    String save(InputStream stream, String name);

    File retrieve(String name);

    void delete(String name);

    String replace(InputStream stream, String newFileName, String toRemoveFileName);

}
