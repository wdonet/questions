package com.nearsoft.questions.service.impl;

import java.util.List;
import com.nearsoft.questions.domain.Tag;
import com.nearsoft.questions.repository.TagRepository;
import com.nearsoft.questions.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getAllSortedByName() {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        return (List<Tag>) tagRepository.findAll(sort);
    }

    @Override
    public List<Tag> getPersistedTagsFromTagNameList(List<String> tagNameList) {
        return tagRepository.findByNameIn(tagNameList, new PageRequest(0, Integer.MAX_VALUE));
    }
}
