package com.nearsoft.questions.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static InputStream multipartToFile(MultipartFile multipart) {
        try {
            return multipart.getInputStream();
        } catch (IOException e) {
            log.error("The input stream could not be obtained from the multipart object");
            return null;
        }
    }
}
