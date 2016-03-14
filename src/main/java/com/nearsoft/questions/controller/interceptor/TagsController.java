package com.nearsoft.questions.controller.interceptor;

import com.nearsoft.questions.controller.BaseController;
import com.nearsoft.questions.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tags")
public class TagsController extends BaseController {
    @Autowired
    TagService _tagService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllTags(Model model) {
        model.addAttribute(_tagService.getAllSortedByName());
        return "showTags";
    }
}
