package com.gooddata.web;

import com.gooddata.infra.ApiValidationException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gooddata.domain.WordsService;
import com.gooddata.web.model.WebWord;

/**
 * API Access point to Words service.
 */
@RestController
public class WordsController {

    @Autowired
    private WordsService wordsService;

    @GetMapping(value = "/words", produces = "application/json")
    public List<WebWord> getAllWords() {
        return wordsService.getAllWords().stream().map(WebWord::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/words/{name}", produces = "application/json")
    public List<WebWord> getWord(@PathVariable String name) {
        return wordsService.getWordsForName(name).stream().map(WebWord::new).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/words/{name}", consumes = "application/json", produces = "application/json")
    public WebWord addWord(@RequestBody WebWord webWord, @PathVariable String name) throws ApiValidationException {
        webWord.setName(name);
        wordsService.addWord(webWord);
        return webWord;
    }

}
