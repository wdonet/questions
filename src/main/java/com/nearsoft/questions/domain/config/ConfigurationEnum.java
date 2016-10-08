package com.nearsoft.questions.domain.config;


public enum ConfigurationEnum {
    SHOW_ONLY_ONE_ANSWER("show_only_one_answer"),
    OPEN_FOR_DOMAINS("open_for_domains"),
    MAX_NUMBER_AUTOCOMPLETE_SUGGESTIONS("max_number_autocomplete_suggestions"),
    INDEX_PAGE("index_page"),
    INDEX_HEADER("index_header");

    private String configName;

    ConfigurationEnum(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }
}
