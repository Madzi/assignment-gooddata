package com.gooddata.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gooddata.domain.model.Sentence;
import com.gooddata.domain.model.Word;

@Entity
@Table(name = "sentences")
public class SentenceEntity implements Sentence, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private WordEntity noun;
    @ManyToOne(fetch = FetchType.LAZY)
    private WordEntity verb;
    @ManyToOne(fetch = FetchType.LAZY)
    private WordEntity adjective;
    @Column(name = "gen_time", nullable = false)
    private Long timestamp;
    @Column(name = "show_count", nullable = false)
    private Long showCount;

    public SentenceEntity() {
        timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        showCount = 0L;
    }

    public SentenceEntity(final WordEntity noun, final WordEntity verb, final WordEntity adjective) {
        this();
        this.noun = noun;
        this.verb = verb;
        this.adjective = adjective;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Word getNoun() {
        return noun;
    }

    public void setNoun(final WordEntity noun) {
        this.noun = noun;
    }

    @Override
    public Word getVerb() {
        return verb;
    }

    public void setVerb(final WordEntity verb) {
        this.verb = verb;
    }

    @Override
    public Word getAdjective() {
        return adjective;
    }

    public void setAdjective(final WordEntity adjective) {
        this.adjective = adjective;
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public Long getShowCount() {
        return showCount;
    }

    public void setShowCount(final Long showCount) {
        this.showCount = showCount;
    }

    public void incShowCount() {
        showCount++;
    }

}
