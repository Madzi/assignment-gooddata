package com.gooddata.web.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.gooddata.domain.model.Sentence;

public class WebSentence implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String text;
    private Long showDisplayCount;
    private Long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

    public WebSentence() {
    }

    public WebSentence(final Long id, final String text, final Long showDisplayCount) {
        this();
        this.id = id;
        this.text = text;
        this.showDisplayCount = showDisplayCount;
    }

    public WebSentence(final Sentence sentence, final boolean yoda) {
        this(sentence.getId(), (yoda ? getYodaSentence(sentence) : getSentence(sentence)), sentence.getShowCount());
    }

    public WebSentence(final Sentence sentence) {
        this(sentence, false);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
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

    public Long getShowDisplayCount() {
        return showDisplayCount;
    }

    public void setShowDisplayCount(final Long showDisplayCount) {
        this.showDisplayCount = showDisplayCount;
    }


    private static String getSentence(final Sentence sentence) {
        return sentence.getNoun().getWord() + ' ' + sentence.getVerb().getWord() + ' ' + sentence.getAdjective().getWord();
    }

    private static String getYodaSentence(final Sentence sentence) {
        return sentence.getVerb().getWord() + ' ' + sentence.getAdjective().getWord() + ' ' + sentence.getNoun().getWord();
    }

}
