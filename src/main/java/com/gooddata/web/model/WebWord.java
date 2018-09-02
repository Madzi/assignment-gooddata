package com.gooddata.web.model;

import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.Word;
import java.io.Serializable;

/**
 * Web Model for words controller.
 */
public class WebWord implements Word, Serializable {

    private String name;
    private Category wordCategory;

    public WebWord() {
    }

    public WebWord(final String name, final Category category) {
        this.name = name;
        this.wordCategory = category;
    }

    public WebWord(final Word word) {
        this(word.getName(), word.getCategory());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Category getCategory() {
        return wordCategory;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCategory(final Category wordCategory) {
        this.wordCategory = wordCategory;
    }

}
