package com.gooddata.domain.impl;

import com.gooddata.dao.WordEntity;
import com.gooddata.dao.WordsRepository;
import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Word;
import com.gooddata.domain.model.WordCategory;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WordsServiceImpl implements WordsService {

    @Autowired
    private WordsRepository wordsRepository;

    private Random random = new Random();

    @Value("#{'${forbidden.noun}'.split(',')}")
    private List<String> forbiddenNouns;
    @Value("#{'${forbidden.verb}'.split(',')}")
    private List<String> forbiddenVerbs;
    @Value("#{'${forbidden.adjective}'.split(',')}")
    private List<String> forbiddenAdjectives;

    @Override
    public List<Word> findAll() {
        return Collections.unmodifiableList(wordsRepository.findAll());
    }

    @Override
    public List<Word> getByWord(final String word) {
        return Collections.unmodifiableList(wordsRepository.findByWord(word));
    }

    @Override
    @Transactional
    public Word addWord(final Word word) {
        if (word.getWordCategory() == null) {
            throw new IllegalStateException("Unknown word category");
        }
        switch (word.getWordCategory()) {
            case NOUN: if (forbiddenNouns.contains(word.getWord())) { throw new IllegalStateException("Forbidden word detected"); } break;
            case VERB: if (forbiddenVerbs.contains(word.getWord())) { throw new IllegalStateException("Forbidden word detected"); } break;
            case ADJECTIVE: if (forbiddenAdjectives.contains(word.getWord())) { throw new IllegalStateException("Forbidden word detected"); } break;
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
        return suitableWords.get(random.nextInt(suitableWords.size()));
    }

}
