package com.gooddata.dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gooddata.domain.model.Word;
import com.gooddata.domain.model.WordCategory;

@Entity
@Table(name = "words")
public class WordEntity implements Word, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "word", nullable = false)
    private String word;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private WordCategory wordCategory;

    public WordEntity() {
        super();
    }

    public WordEntity(final String word, final WordCategory wordCategory) {
        this();
        this.word = word;
        this.wordCategory = wordCategory;
    }

    public WordEntity(final Word word) {
        this(word.getWord(), word.getWordCategory());
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public WordCategory getWordCategory() {
        return wordCategory;
    }

    public void setWorkCategory(final WordCategory wordCategory) {
        this.wordCategory = wordCategory;
    }

    @Override
    public int hashCode() {
        return 31 * word.hashCode() + wordCategory.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Word)) {
            return false;
        }
        var otherWord = (Word) object;
        return wordCategory.equals(otherWord.getWordCategory()) && word.equals(otherWord.getWord());
    }

    @Override
    public String toString() {
        return "Word(" + id + " :" + word + ", " + wordCategory;
    }

}

