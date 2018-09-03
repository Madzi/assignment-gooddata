package com.gooddata.web.model;

import com.gooddata.domain.model.Sentence;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

public class WebSentence implements Serializable {

    private Long id;
    private String text;
    private long showDisplayCount;
    private long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

    public WebSentence() {
    }

    public WebSentence(final Long id, final String text, final long showDisplayCount, final long timestamp) {
        this.id = id;
        this.text = text;
        this.showDisplayCount = showDisplayCount;
        this.timestamp = timestamp;
    }

    public WebSentence(final Sentence sentence) {
        this(sentence, false);
    }

    public WebSentence(final Sentence sentence, final boolean yoda) {
        id = sentence.getId();
        text = yoda ? sentence.getAdjective().getName() + ' ' + sentence.getNoun().getName() + ' ' + sentence.getVerb().getName()
                    : sentence.getNoun().getName() + ' ' + sentence.getVerb().getName() + ' ' + sentence.getAdjective().getName();
        timestamp = sentence.getTimestamp();
        showDisplayCount = sentence.getShowCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public long getShowDisplayCount() {
        return showDisplayCount;
    }

    public void setShowDisplayCount(final long showDisplayCount) {
        this.showDisplayCount = showDisplayCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

}
