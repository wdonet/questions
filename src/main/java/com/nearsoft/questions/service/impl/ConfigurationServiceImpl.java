package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.domain.config.Configuration;
import com.nearsoft.questions.repository.config.ConfigurationRepository;
import com.nearsoft.questions.service.ConfigurationService;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public String getString(String name, String defaultValue) {
        Configuration config = configurationRepository.findByName(name);
        if (config == null) {
            return defaultValue;
        }
        return config.getValue();
    }

    @Override
    public Double getDouble(String name, double defaultValue) {
        Configuration config = configurationRepository.findByName(name);
        if (config == null) {
            return defaultValue;
        }
        Double value;
        try {
            value = new Double(config.getValue());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public Integer getInteger(String name, int defaultValue) {
        Configuration config = configurationRepository.findByName(name);
        if (config == null) {
            return defaultValue;
        }
        Integer value;
        try {
            value = new Integer(config.getValue());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public Boolean getBoolean(String name, boolean defaultValue) {
        Configuration config = configurationRepository.findByName(name);
        if (config == null) {
            return defaultValue;
        }
        return BooleanUtils.toBoolean(config.getValue());
    }
}
