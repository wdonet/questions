package com.nearsoft.questions.controller;

import java.util.List;
import com.nearsoft.questions.controller.form.QuestionForm;
import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.service.QuestionService;
import com.nearsoft.questions.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @Autowired
    QuestionService _questionService;

    @Autowired
    TagService _tagService;

    @RequestMapping(method = RequestMethod.POST)
    public String add(@ModelAttribute QuestionForm form, RedirectAttributes redirectAttributes) {
        if (form != null) {
            _log.debug("Trying to add " + form);
            Question question = new Question(form, _tagService.getPersistedTagsFromTagNameList(form.getNormalizedTagList()));
            _questionService.save(question);
            redirectAttributes.addAttribute("id", question.getId());
            return "redirect:/question/{id}";
        }
        return "redirect:/ask";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable long id, Model model) {
        _log.info("question with id " + id);
        model.addAttribute(_questionService.get(id));
        return "show1Question";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> search(@RequestParam String query) {
        _log.debug("Query : " + query);
        return _questionService.search(query);
    }

}
