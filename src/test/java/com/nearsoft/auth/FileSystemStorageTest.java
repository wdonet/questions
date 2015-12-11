package com.nearsoft.auth;

import com.nearsoft.questions.service.Storage;
import com.nearsoft.questions.service.impl.FileSystemStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import java.io.ByteArrayInputStream;

public class FileSystemStorageTest {

    private Storage storage;
    private final String testFileName = "test.txt";
    private final String testFileNameReplaced = "test2.txt";

    @Before
    public void setup() {

        MockEnvironment environment = new MockEnvironment();
        environment.withProperty("media.location", "media/");
        storage = new FileSystemStorage(environment);

    }

    @Test
    public void saveFile() {
        storage.save(new ByteArrayInputStream("this is a test".getBytes()), testFileName);
        Assert.assertEquals(storage.exists(testFileName), true);
    }

    @Test
    public void replaceFile() {
        storage.replace(new ByteArrayInputStream("this is a second test".getBytes()), testFileName, testFileNameReplaced);
        Assert.assertEquals(storage.exists(testFileName), false);
        Assert.assertEquals(storage.exists(testFileNameReplaced), true);
    }

    @Test
    public void deleteFile() {
        storage.delete(testFileNameReplaced);
        Assert.assertEquals(storage.exists(testFileNameReplaced), false);
    }

}
