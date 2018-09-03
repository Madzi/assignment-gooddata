package com.gooddata.domain.impl;

import com.gooddata.dao.WordsRepository;
import com.gooddata.dao.model.WordEntity;
import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.Word;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordsServiceImpl implements WordsService {

    @Autowired
    private WordsRepository wordsRepository;
    private Random random = new Random();

    @Override
    public List<Word> getAllWords() {
        return Collections.unmodifiableList(wordsRepository.findAll());
    }

    @Override
    public void addWord(final Word word) {
        var entity = new WordEntity(word);
        wordsRepository.save(entity);
    }

    @Override
    public List<Word> getWordsForName(final String name) {
        return Collections.unmodifiableList(wordsRepository.findByName(name));
    }

    @Override
    public Word randomWordByCategory(final Category category) throws IllegalStateException {
        var suitableWords = wordsRepository.findByCategory(category);
        if (suitableWords.isEmpty()) {
            throw new IllegalStateException("No words into category: " + category.name());
        }
        var index = suitableWords.size() == 1 ? 0 : random.nextInt() % suitableWords.size();
        return suitableWords.get(index);
    }

}
