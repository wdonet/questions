package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.config.Configuration;
import com.nearsoft.questions.domain.config.ConfigurationEnum;

import java.util.List;

public interface ConfigurationService {

    String getString(String name);

    String getString(String name, String defaultValue);

    Double getDouble(String name, double defaultValue);

    Integer getInteger(String name, int defaultValue);

    Boolean getBoolean(String name, boolean defaultValue);

    void updateConfiguration(ConfigurationEnum configurationEnum, String newValue);

    List<Configuration> findAll();

}
