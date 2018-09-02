package com.gooddata.domain.impl;

import com.gooddata.domain.SentencesService;
import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Sentence;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InMemorySentencesServiceImpl implements SentencesService {

    @Autowired
    private WordsService wordsService;

    @Override
    public List<Sentence> getAllSentences() {
        return null;
    }

    @Override
    public Optional<Sentence> getSentenceById(String id) {
        return Optional.empty();
    }

    @Override
    public Sentence generate() throws IllegalStateException {
        return null;
    }
}
