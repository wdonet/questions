package com.nearsoft.questions.service.impl;

import java.util.List;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.repository.TagRepository;
import com.nearsoft.questions.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository _tagRepository;

    @Override
    public List<Tag> getPersistedTagsFromTagNameList(List<String> tagNameList) {
        return _tagRepository.findByNameIn(tagNameList);
    }
}
