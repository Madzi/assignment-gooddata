package com.gooddata.domain.impl;

import com.gooddata.dao.WordEntity;
import com.gooddata.dao.WordsRepository;
import com.gooddata.domain.model.WordCategory;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Word;

@Service
public class WordsServiceImpl implements WordsService {

    @Autowired
    private WordsRepository wordsRepository;

    private Random random = new Random();

    @Override
    public List<Word> findAll() {
        return Collections.unmodifiableList(wordsRepository.findAll());
    }

    @Override
    public List<Word> getByWord(final String word) {
        return Collections.unmodifiableList(wordsRepository.findByWord(word));
    }

    @Override
    public Word addWord(final Word word) {
        if (word.getWordCategory() == null) {
            throw new IllegalStateException();
        }
        var entity = new WordEntity(word);
        return wordsRepository.save(entity);
    }

    @Override
    public List<Word> getByCategory(final WordCategory category) {
        return Collections.unmodifiableList(wordsRepository.findByWordCategory(category));
    }

    @Override
    public Word randomWordByCategory(final WordCategory category) {
        var suitableWords = wordsRepository.findByWordCategory(category);
        if (suitableWords.isEmpty()) {
            throw new IllegalStateException();
        }
        var index = suitableWords.size() == 1 ? 0 : random.nextInt() % suitableWords.size();
        return suitableWords.get(index);
    }

}
