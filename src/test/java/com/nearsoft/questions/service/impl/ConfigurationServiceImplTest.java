package com.nearsoft.questions.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nearsoft.questions.config.QuestionsApplication;
import com.nearsoft.questions.domain.config.Configuration;
import com.nearsoft.questions.domain.config.ConfigurationEnum;
import com.nearsoft.questions.repository.config.ConfigurationRepository;
import com.nearsoft.questions.service.ConfigurationService;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This test is ignored because is trying to connect to nsquestions, when deployed in travis,
 * due any profile
 */

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
@SpringApplicationConfiguration(QuestionsApplication.class)
@Transactional
@Rollback
public class ConfigurationServiceImplTest {

    @Autowired
    ConfigurationService configurationService;

    @Test
    public void updateConfigurationTest() {
        Assert.assertEquals(configurationService.getString(ConfigurationEnum.INDEX_PAGE.getConfigName()), "/question/order/unanswered");
        configurationService.updateConfiguration(ConfigurationEnum.INDEX_PAGE, "/hola.index");
        Assert.assertEquals(configurationService.getString(ConfigurationEnum.INDEX_PAGE.getConfigName()), "/hola.index");
        System.err.println(configurationService.getString(ConfigurationEnum.INDEX_PAGE.getConfigName()));
    }

    @Test
    public void findAllTest() {
        List<Configuration> all = configurationService.findAll();
        Assert.assertFalse(all.isEmpty());
    }

    @Test
    public void testGetIntegerReturnsIntegerValue() {
        ConfigurationRepository repositoryStub = mock(ConfigurationRepository.class);
        Configuration configurationStub = mock(Configuration.class);
        when(configurationStub.getValue()).thenReturn("12345");
        when(repositoryStub.findByName(anyString())).thenReturn(configurationStub);
        ConfigurationService configurationService = new ConfigurationServiceImpl(repositoryStub);
        assertThat("ConfigurationService#getInteger should return the integer value of the persisted string value",
                configurationService.getInteger("name", 0), is(12345));
    }
}