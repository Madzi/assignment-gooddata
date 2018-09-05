package com.gooddata.domain.model;

public interface Sentence {

    Long getId();

    Word getNoun();

    Word getVerb();

    Word getAdjective();

    Long getTimestamp();

    Long getShowCount();

}
