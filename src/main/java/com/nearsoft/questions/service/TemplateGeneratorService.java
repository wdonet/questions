package com.nearsoft.questions.service;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Created by rjimenez on 6/1/16.
 */
public interface TemplateGeneratorService {
    String getTemplate(String nameTemplate, Object model) throws IOException, TemplateException;
}
