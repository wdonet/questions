package com.nearsoft.questions.service.impl;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.nearsoft.questions.service.StorageService;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public void replaceFile(InputStream inputStream, String fileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + fileName);
        Files.copy(inputStream, file.toPath(), REPLACE_EXISTING);
    }
}
