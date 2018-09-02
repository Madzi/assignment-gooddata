package com.gooddata.domain.impl;

import com.gooddata.dao.WordsRepository;
import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.Word;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for words.
 */
@Service
public class WordsServiceImpl implements WordsService {

    @Autowired
    private WordsRepository wordsRepository;

    @Override
    public List<Word> getAllWords() {
        return null;
    }

    @Override
    public void addWord(Word word) {

    }

    @Override
    public List<Word> getWordsForName(String name) {
        return null;
    }

    @Override
    public Word randomWordByCategory(Category category) throws IllegalStateException {
        return null;
    }

}
