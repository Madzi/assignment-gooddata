package com.gooddata.domain.model;

/**
 * The abstract sentence.
 */
public interface Sentence {

    Long getId();
    Word getNoun();
    Word getVerb();
    Word getAdjective();
    long getTimestamp();
    long getShowCount();

}
