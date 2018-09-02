package com.gooddata.domain;

import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.Word;
import java.util.List;

/**
 * Service for get/put words to system.
 */
public interface WordsService {

    /**
     * Return all known words in system.
     *
     * @return the list of words
     */
    List<Word> getAllWords();

    /**
     * Add new word into system.
     *
     * @param word the word
     */
    void addWord(Word word);

    /**
     * Returns list of words with specified name.
     *
     * @param name the name
     * @return the list of words (possible empty)
     */
    List<Word> getWordsForName(String name);

    /**
     * Returns random word from known words for specified category.
     *
     * @param category the category
     * @return random word
     * @throws IllegalStateException when set of words for category is empty
     */
    Word randomWordByCategory(Category category) throws IllegalStateException;

}
