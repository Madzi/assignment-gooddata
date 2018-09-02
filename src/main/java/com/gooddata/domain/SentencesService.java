package com.gooddata.domain;

import com.gooddata.domain.model.Sentence;
import java.util.List;
import java.util.Optional;

/**
 * Service for generate / read sentences.
 */
public interface SentencesService {

    /**
     * Returns all generated sentences.
     *
     * @return list of sentences
     */
    List<Sentence> getAllSentences();

    /**
     * Returns sentence for specified id.
     *
     * @param id the sentence id
     * @return the sentence
     */
    Optional<Sentence> getSentenceById(String id);

    /**
     * Generate sentence from known words.
     *
     * @return the sentence
     * @throws IllegalStateException when sentence can't be generated
     */
    Sentence generate() throws IllegalStateException;

}
