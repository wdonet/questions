package com.nearsoft.questions.controller;

import com.nearsoft.questions.service.ConfigurationService;
import com.nearsoft.questions.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "admin";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            storageService.replaceFile(file.getInputStream(), "static/img/logo.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/index";
    }
}
