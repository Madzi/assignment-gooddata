package com.gooddata.domain.impl;

import com.gooddata.dao.SentenceEntity;
import com.gooddata.dao.WordEntity;
import com.gooddata.domain.model.WordCategory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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
    @Transactional
    public List<Sentence> findAll() {
        var sentences = sentencesRepository.findAll();
        sentences.forEach(sentenceEntity -> {
            sentenceEntity.incShowCount();
            sentencesRepository.save(sentenceEntity);
        });
        return Collections.unmodifiableList(sentences);
    }

    @Override
    @Transactional
    public Optional<Sentence> findById(final Long id) {
        var optSentence = sentencesRepository.findById(id);
        if (optSentence.isPresent()) {
            var sentence = optSentence.get();
            sentence.incShowCount();
            sentencesRepository.save(sentence);
        }
        return optSentence.map(it -> (Sentence) it);
    }

    @Override
    @Transactional
    public Sentence generate() {
        var sentence = new SentenceEntity(
                (WordEntity) wordsServie.randomWordByCategory(WordCategory.NOUN),
                (WordEntity) wordsServie.randomWordByCategory(WordCategory.VERB),
                (WordEntity) wordsServie.randomWordByCategory(WordCategory.ADJECTIVE)
        );
        sentencesRepository.save(sentence);
        return sentence;
    }

}
