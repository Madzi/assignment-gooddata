package com.gooddata.domain.model;

import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.Word;

public class TestWord implements Word {

    private String name;
    private Category category;

    public TestWord(final String name, final Category category) {
        this.name = name;
        this.category = category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Category getCategory() {
        return category;
    }
}
