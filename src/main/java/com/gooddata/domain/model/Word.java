package com.gooddata.domain.model;

/**
 * The word of sentence.
 */
public interface Word {

    /**
     * Returns the word (name or mean).
     *
     * @return the word
     */
    String getWord();

    /**
     * Returns the words category.
     *
     * @return the words category
     */
    WordCategory getWordCategory();

}
