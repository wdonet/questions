package com.nearsoft.questions.service;


import java.io.IOException;
import java.io.InputStream;

public interface StorageService {
    void replaceFile(InputStream inputStream, String fileName) throws IOException;
}
