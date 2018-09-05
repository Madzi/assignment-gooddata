package com.gooddata.domain;

import java.util.List;

import com.gooddata.domain.model.Word;
import com.gooddata.domain.model.WordCategory;

/**
 * Business layer for words.
 */
public interface WordsService {

    /**
     * Returns all known words.
     *
     * @return the list of words
     */
    List<Word> findAll();

    /**
     * Returns list of words with specified name.
     * It can be a list because we can have different words with the same name
     * e.g. Word(go:NOUN) (game) and Word(go:VERB) (moving)
     *
     * @param word the name of word
     * @return the list of words
     */
    List<Word> getByWord(String word);

    /**
     * Returns word which was added to list of known words.
     *
     * @param word the word
     * @return the word
     */
    Word addWord(Word word);

    /**
     * Returns filtered list of incoming messages.
     *
     * @param category the category
     * @return words for specified category
     */
    List<Word> getByCategory(WordCategory category);

    /**
     * Returns random word from known words for specified category.
     *
     * @param category the category
     * @return
     */
    Word randomWordByCategory(WordCategory category);

}
