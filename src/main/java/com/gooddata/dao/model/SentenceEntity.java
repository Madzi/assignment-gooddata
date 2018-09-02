package com.gooddata.dao.model;

import com.gooddata.domain.model.Sentence;
import com.gooddata.domain.model.Word;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sentences")
public class SentenceEntity implements Sentence, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;
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

    @Override
    public String getId() {
        return id;
    }
    public void setId(final String id) {
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
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public long getShowCount() {
        return showCount;
    }

    public void setShowCount(final Long showCount) {
        this.showCount = showCount;
    }

}
