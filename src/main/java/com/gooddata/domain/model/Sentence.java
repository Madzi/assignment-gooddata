package com.gooddata.domain.model;

/**
 * The abstract sentence.
 */
public interface Sentence {

    String getId();
    Word getNoun();
    Word getVerb();
    Word getAdjective();
    long getTimestamp();
    long getShowCount();

}
