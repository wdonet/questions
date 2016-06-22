package com.nearsoft.questions.service.impl;

import com.nearsoft.questions.service.TemplateGeneratorService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Created by rjimenez on 6/1/16.
 */

@Service
public class TemplateGeneratorServiceImpl implements TemplateGeneratorService {

    @Autowired
    Configuration configuration;

    @Override
    public String getTemplate(String nameTemplate, Object model) throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(nameTemplate), model);
    }
}
