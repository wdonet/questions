package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.config.Configuration;
import com.nearsoft.questions.repository.config.ConfigurationRepository;
import com.nearsoft.questions.service.ConfigurationService;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConfigurationServiceImplTest {
    @Test
    public void shouldReturn() {
        ConfigurationRepository repositoryStub = mock(ConfigurationRepository.class);
        Configuration configurationStub = mock(Configuration.class);
        when(configurationStub.getValue()).thenReturn("12345");
        when(repositoryStub.findByName(anyString())).thenReturn(configurationStub);
        ConfigurationService configurationService = new ConfigurationServiceImpl(repositoryStub);
        assertThat("ConfigurationService#getInteger should return the integer value of the persisted string value",
                configurationService.getInteger("name", 0) , is(12345));
    }
}