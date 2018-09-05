package com.gooddata.domain;

import java.util.List;
import java.util.Optional;

import com.gooddata.domain.model.Sentence;

/**
 * Business layer for sentences.
 */
public interface SentencesService {

    /**
     * Returns all known sentences.
     *
     * @return the list of sentences
     */
    List<Sentence> findAll();

    Optional<Sentence> getSentetenceById(Long id);

    /**
     * Generate sentence from known words.
     *
     * @return the sentence
     */
    Sentence generate();

}
