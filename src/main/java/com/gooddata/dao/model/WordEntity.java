package com.gooddata.dao.model;

import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.Word;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "words")
public class WordEntity implements Word, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "word", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    public WordEntity() {
    }

    public WordEntity(final Word word) {
        this.name = word.getName();
        this.category = word.getCategory();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Word)) {
            return false;
        }
        var word = (Word) other;
        return category == word.getCategory() && name.equals(word.getName());
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + category.hashCode();
    }

    @Override
    public String toString() {
        return "Word(" + id + ", " + name + ", " + category + ")";
    }

}
