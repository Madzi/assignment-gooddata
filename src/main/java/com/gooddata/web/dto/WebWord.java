package com.gooddata.web.dto;

import java.io.Serializable;

import com.gooddata.domain.model.Word;
import com.gooddata.domain.model.WordCategory;

/**
 * DTO for word.
 */
public class WebWord implements Word, Serializable {

    private static final long serialVersionUID = 1L;

    private String word;
    private WordCategory wordCategory;

    public WebWord() {
    }

    public WebWord(final String word, final WordCategory wordCategory) {
        this();
        this.word = word;
        this.wordCategory = wordCategory;
    }

    public WebWord(final Word word) {
        this(word.getWord(), word.getWordCategory());
    }

    @Override
    public String toString() {
        return "Word(" + word + ":" + wordCategory.name() + ")";
    }

    @Override
    public int hashCode() {
        return (word == null ? 0 : word.hashCode()) * 4 + wordCategory.ordinal();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Word)) {
            return false;
        }
        Word word = (Word) other;
        return wordCategory == word.getWordCategory() && this.word != null && this.word.equals(word.getWord());
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public WordCategory getWordCategory() {
        return wordCategory;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public void setWordCategory(final WordCategory wordCategory) {
        this.wordCategory = wordCategory;
    }

}
