package com.gooddata.domain.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooddata.dao.SentencesRepository;
import com.gooddata.domain.SentencesService;
import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Sentence;

@Service
public class SentencesServiceImpl implements SentencesService {

    @Autowired
    private SentencesRepository sentencesRepository;

    @Autowired
    private WordsService wordsServie;

    @Override
    public List<Sentence> findAll() {
        return Collections.unmodifiableList(sentencesRepository.findAll());
    }

    @Override
    public Optional<Sentence> getSentetenceById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Sentence generate() {
        // TODO Auto-generated method stub
        return null;
    }

}
