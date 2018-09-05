package com.gooddata.dao;

import com.gooddata.domain.model.WordCategory;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for words object.
 */
public interface WordsRepository extends CrudRepository<WordEntity, Long> {

    /**
     * Returns all words.
     *
     * @return the list of words
     */
    @Override
    List<WordEntity> findAll();

    /**
     * Returns all matches for specific word.
     *
     * @param Word the word
     * @return the list of words
     */
    List<WordEntity> findByWord(String Word);

    /**
     * Returns all matches for specific word category.
     *
     * @param wordCategory the word category
     * @return the list of words
     */
    List<WordEntity> findByWordCategory(WordCategory wordCategory);

}
