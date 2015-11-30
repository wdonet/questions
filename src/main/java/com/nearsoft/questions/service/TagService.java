package com.nearsoft.questions.service;

import java.util.List;
import com.nearsoft.questions.domain.Tag;

public interface TagService {

    List<Tag> getPersistedTagsFromTagNameList(List<String> tagNameList);
}
