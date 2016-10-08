package com.nearsoft.questions.service;

import com.nearsoft.questions.domain.config.ConfigurationEnum;

public interface ConfigurationService {

    String getString(String name);

    String getString(String name, String defaultValue);

    Double getDouble(String name, double defaultValue);

    Integer getInteger(String name, int defaultValue);

    Boolean getBoolean(String name, boolean defaultValue);

    void updateConfiguration(ConfigurationEnum configurationEnum, String newValue);

}
