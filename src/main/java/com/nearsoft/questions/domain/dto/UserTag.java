package com.nearsoft.questions.domain.dto;

import com.nearsoft.questions.domain.Tag;


public class UserTag {

    private Tag tag;
    private boolean subscribed;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
