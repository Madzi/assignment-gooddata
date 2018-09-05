package com.gooddata.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.Word;
import com.gooddata.web.dto.WebWord;

@RestController
public class WordsController {

    @Autowired
    private WordsService wordsService;

    @GetMapping(value = "/words", produces = "application/json")
    public List<WebWord> getWordList() {
        return toWebWords(wordsService.findAll());
    }

    @GetMapping(value = "/words/{word}", produces = "application/json")
    public List<WebWord> getByWord(@PathVariable final String word) {
        return toWebWords(wordsService.getByWord(word));
    }

    @PostMapping(value = "/words/{word}", produces = "application/json")
    public WebWord addWord(@PathVariable final String word, @RequestBody final WebWord webWord) {
        webWord.setWord(word);
        return toWebWord(wordsService.addWord(webWord));
    }

    private WebWord toWebWord(final Word word) {
        return new WebWord(word);
    }

    private List<WebWord> toWebWords(final List<Word> words) {
        return words.stream().map(WebWord::new).collect(Collectors.toList());
    }

}
