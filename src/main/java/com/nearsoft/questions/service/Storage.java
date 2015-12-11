package com.nearsoft.questions.service;

import java.io.File;
import java.io.InputStream;

public interface Storage {

    String save(InputStream stream, String name);

    File retrieve(String name);

    void delete(String name);

    String replace(InputStream stream, String newFileName, String toRemoveFileName);

}
