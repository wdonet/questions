package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.service.Storage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Random;

@Service
public class FileSystemStorage implements Storage {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Environment environment;

    private Random random = new Random();

    @Autowired
    public FileSystemStorage(Environment environment) {
        this.environment = environment;
    }

    private Path getAvailableName(String fileName) {
        Path path = Paths.get(environment.getProperty("media.location") + fileName);

        if (path.toFile().exists()) {
            String extension = FilenameUtils.getExtension(fileName);
            String name = FilenameUtils.getBaseName(fileName);

            while (path.toFile().exists()) {
                path = Paths.get(
                        environment.getProperty("media.location")
                                + name + String.valueOf(random.nextInt(999999)) + "." + extension
                );
            }
        }
        return path;
    }


    @Override
    public String save(InputStream inputStream, @NotNull String fileName) {
        Objects.requireNonNull(fileName, "fileName can not be null");

        try {
            Path path = getAvailableName(fileName);
            byte[] bytes = IOUtils.toByteArray(inputStream);

            //TODO @imarban: Write by block
            String name = Files.write(path, bytes).toString();
            log.info("The file has been saved to " + path.toString());
            return name;
        } catch (IOException e) {
            log.error("An error has occurred trying to save the file");
            return null;
        }

    }

    @Override
    public String replace(InputStream inputStream, @NotNull String toReplace, @NotNull String newFileName) {

        delete(toReplace);
        String name = save(inputStream, newFileName);

        log.info(MessageFormat.format("The file {} has been replaced", toReplace));
        return name;

    }

    @Override
    public File retrieve(String name) {
        Objects.requireNonNull(name, "fileName can not be null");

        return null;
    }

    @Override
    public void delete(String name) {
        Objects.requireNonNull(name, "fileName can not be null");

    }
}
