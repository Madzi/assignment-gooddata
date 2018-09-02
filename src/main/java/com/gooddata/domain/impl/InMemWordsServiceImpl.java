package com.gooddata.domain.impl;

import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.Word;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service("wordsService")
public class InMemWordsServiceImpl implements WordsService {

    private List<Word> words = new CopyOnWriteArrayList<>();
    private Random random = new Random();

    @Override
    public List<Word> getAllWords() {
        return words.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void addWord(final Word word) {
        words.add(word);
    }

    @Override
    public List<Word> getWordsForName(String name) {
        return name == null ? Collections.emptyList() : words.stream().filter(word -> name.equals(word.getName())).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Word randomWordByCategory(Category category) throws IllegalStateException {
        var suitableWords = words.stream().filter(word -> word.getCategory() == category).collect(Collectors.toList());
        if (suitableWords.isEmpty()) {
            throw new IllegalStateException("No words for " + category);
        }
        var index = random.nextInt() % suitableWords.size();
        return suitableWords.get(index);
    }

}
