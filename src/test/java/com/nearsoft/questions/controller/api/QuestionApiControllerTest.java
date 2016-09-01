package com.nearsoft.questions.controller.api;

import com.nearsoft.questions.domain.Question;
import com.nearsoft.questions.repository.search.QuestionSearchRepository;
import com.nearsoft.questions.service.ConfigurationService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionApiControllerTest {
    @Test
    public void suggestionsReturnsASingletonMapWithKeySuggestions() {
        QuestionSearchRepository searchRepository =
            createSearchRepositoryStubWithStubbedSuggestions(Collections.emptyList());
        ConfigurationService configurationService = mock(ConfigurationService.class);
        when(configurationService.getInteger("max_number_autocomplete_suggestions", 10)).thenReturn(10);
        Map<String, List<String>> suggestionsMap = createController(configurationService, searchRepository)
            .suggestions("term");
        String reason = "method suggestions() should return a singleton map with key suggestions";
        assertThat(reason, suggestionsMap.entrySet(), hasSize(1));
        assertThat(reason, suggestionsMap, hasKey("suggestions"));
    }

    private QuestionSearchRepository createSearchRepositoryStubWithStubbedSuggestions(List<Question> suggestions) {
        QuestionSearchRepository searchRepository = mock(QuestionSearchRepository.class);
        when(searchRepository.findByTitleLike(any(String.class))).thenReturn(suggestions);
        return searchRepository;
    }

    private QuestionApiController createController(ConfigurationService configurationService,
                QuestionSearchRepository searchRepository) {
        return new QuestionApiController(configurationService, searchRepository, null, null);
    }

    @Test
    public void suggestionsReturnsASingletonMapWithPartialMatchesSearchResult() {
        String reason = "method suggestions() should return a singleton map with a list of suggested questions as " +
            "value. Tha list should be the result of the partial match search of questions titles.";
        List<Question> questionList = createQuestionListWithTitles("suggestion 1", "suggestion 2", "suggestion 3");
        QuestionSearchRepository searchRepository = createSearchRepositoryStubWithStubbedSuggestions(questionList);
        ConfigurationService configurationService = mock(ConfigurationService.class);
        when(configurationService.getInteger("max_number_autocomplete_suggestions", 10)).thenReturn(10);
        Map<String, List<String>> suggestionsMap = createController(configurationService, searchRepository)
            .suggestions("term");
        assertThat(reason, suggestionsMap.entrySet(), hasSize(1));
        assertThat(reason, suggestionsMap.get("suggestions"), hasSize(3));
        assertThat(reason, suggestionsMap.get("suggestions"), hasItems("suggestion 1", "suggestion 2", "suggestion 3"));
    }

    private List<Question> createQuestionListWithTitles(String... titles) {
        List<Question> questionList = new ArrayList<>();
        for (String title : titles) {
            questionList.add(createQuestionWithTitle(title));
        }
        return questionList;
    }

    private Question createQuestionWithTitle(String title) {
        Question question = new Question();
        question.setTitle(title);
        return question;
    }

    @Test
    public void suggestionsReturnsAListWithTheSpecificMaximumSize() {
        List<Question> questionList = createQuestionListWithTitles("s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8",
            "s9", "s10", "s11");
        QuestionSearchRepository searchRepository = createSearchRepositoryStubWithStubbedSuggestions(questionList);
        ConfigurationService configurationService = mock(ConfigurationService.class);
        when(configurationService.getInteger("max_number_autocomplete_suggestions", 10)).thenReturn(10);
        assertThat("The number of suggestions should be limited to the specific number",
            createController(configurationService, searchRepository).suggestions("term").get("suggestions"),
            hasSize(10));
    }
}