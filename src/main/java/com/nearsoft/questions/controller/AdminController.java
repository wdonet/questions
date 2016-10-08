package com.nearsoft.questions.controller;

import com.nearsoft.questions.domain.config.ConfigurationEnum;
import com.nearsoft.questions.service.ConfigurationService;
import com.nearsoft.questions.service.StorageService;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    StorageService storageService;

    @Autowired
    ConfigurationService configurationService;


    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("indexPage", configurationService.getString(ConfigurationEnum.INDEX_PAGE.getConfigName()));
        model.addAttribute("indexHeader", configurationService.getString(ConfigurationEnum.INDEX_HEADER.getConfigName()));
        model.addAttribute("listConfiguration", configurationService.findAll());
        return "admin";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            storageService.replaceFile(file.getInputStream(), "static/img/logo.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProperties(@RequestParam @NotEmpty String indexPage, @RequestParam @NotEmpty String indexHeader) {
        configurationService.updateConfiguration(ConfigurationEnum.INDEX_PAGE, indexPage);
        configurationService.updateConfiguration(ConfigurationEnum.INDEX_HEADER, indexHeader);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/updateConfiguration", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.FOUND)
    @ResponseBody
    public void updateConfiguration(@RequestParam String configurationName, @RequestParam String value) {

        Optional<ConfigurationEnum> configurationEnum = Stream.of(ConfigurationEnum.values()).filter(configuration ->
                configuration.getConfigName().equals(configurationName)
        ).findFirst();

        configurationService.updateConfiguration(configurationEnum.get(), value);
    }

}
