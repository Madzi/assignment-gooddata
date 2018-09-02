package com.gooddata.domain.model;

/**
 * The abstract word.
 */
public interface Word {

    /**
     * Returns name of word.
     *
     * @return the name
     */
    String getName();

    /**
     * Returns category of word.
     *
     * @return the category
     */
    Category getCategory();

}
